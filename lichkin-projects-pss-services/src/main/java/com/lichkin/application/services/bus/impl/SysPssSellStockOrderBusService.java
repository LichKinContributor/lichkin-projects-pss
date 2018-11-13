package com.lichkin.application.services.bus.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lichkin.application.mappers.impl.PssStockQtyMapper;
import com.lichkin.application.mappers.impl.in.SellOrderSavedStockOutQtyIn;
import com.lichkin.application.mappers.impl.out.SellOrderSavedStockOutQtyOut;
import com.lichkin.framework.db.beans.DeleteSQL;
import com.lichkin.framework.db.beans.QuerySQL;
import com.lichkin.framework.db.beans.SysPssSellOrderProductR;
import com.lichkin.framework.db.beans.SysPssSellStockOrderProductR;
import com.lichkin.framework.json.LKJsonUtils;
import com.lichkin.framework.utils.LKDateTimeUtils;
import com.lichkin.framework.utils.LKRandomUtils;
import com.lichkin.springframework.entities.impl.SysPssProductEntity;
import com.lichkin.springframework.entities.impl.SysPssSellOrderProductEntity;
import com.lichkin.springframework.entities.impl.SysPssSellStockOrderProductEntity;
import com.lichkin.springframework.services.LKDBService;

@Service
public class SysPssSellStockOrderBusService extends LKDBService {

	@Autowired
	private PssStockQtyMapper pssStockOutQtyMapper;

	@Autowired
	private SysPssStockBusService sysPssStockBusService;


	public String analysisOrderNo() {
		return LKDateTimeUtils.now() + LKRandomUtils.create(15);
	}


	public void clearPssSellStockOrderProduct(String id) {
		DeleteSQL sql = new DeleteSQL(SysPssSellStockOrderProductEntity.class);
		sql.eq(SysPssSellStockOrderProductR.orderId, id);
		dao.delete(sql);
	}


	public void addPssSellStockOrderProduct(String id, String productList) {
		List<SysPssSellStockOrderProductEntity> listProduct = LKJsonUtils.toList(productList, SysPssSellStockOrderProductEntity.class);
		for (int i = 0; i < listProduct.size(); i++) {
			SysPssSellStockOrderProductEntity product = listProduct.get(i);
			product.setProductId(product.getId());
			product.setId(null);
			product.setOrderId(id);
			product.setSortId(i);
		}
		dao.persistList(listProduct);
	}


	/**
	 * @param id 销售出库单ID
	 * @param storageId 出库仓库ID
	 * @param sellOrderId 销售单ID
	 * @param productList 出库产品json列表
	 * @return
	 */
	public String checkProductQty(String id, String storageId, String sellOrderId, String productList) {
		String errorMsg = "";
		// 查询销售单中的所有产品
		QuerySQL sql = new QuerySQL(SysPssSellOrderProductEntity.class);
		sql.eq(SysPssSellOrderProductR.orderId, sellOrderId);
		List<SysPssSellOrderProductEntity> listSellOrderProd = dao.getList(sql, SysPssSellOrderProductEntity.class);
		// 销售单产品数量（按照产品ID合并数量）
		Map<String, Integer> sellOrderProdQtyMap = listSellOrderProd.stream().collect(Collectors.groupingBy(o -> o.getProductId(), Collectors.summingInt(o -> o.getQuantity())));

		List<SysPssProductEntity> listProd = LKJsonUtils.toList(productList, SysPssProductEntity.class);

		StringBuffer prodIds = new StringBuffer();

		Map<String, String> prodInfoMap = new HashMap<>();
		// 判断产品是否在销售单中
		for (int i = 0; i < listProd.size(); i++) {
			SysPssProductEntity prod = listProd.get(i);

			prodInfoMap.put(prod.getId(), prod.getProductName());

			prodIds.append("'" + prod.getId() + "'");
			if (i < (listProd.size() - 1)) {
				prodIds.append(",");
			}

			// 产品不存在
			if (sellOrderProdQtyMap.get(prod.getId()) == null) {
				errorMsg += prod.getProductName() + ";";
			}
		}
		if (StringUtils.isNotBlank(errorMsg)) {
			return errorMsg + "不在当前销售单中。";
		}

		// 销售单已保存的出库数量
		List<SellOrderSavedStockOutQtyOut> listSellOrderSavedStockOutQty = pssStockOutQtyMapper.findSellOrderSavedStockOutQty(new SellOrderSavedStockOutQtyIn(prodIds.toString(), sellOrderId, id));
		Map<String, Integer> sellOrderSavedStockOutQtyMap = listSellOrderSavedStockOutQty.stream().collect(Collectors.groupingBy(o -> o.getProductId(), Collectors.summingInt(o -> o.getQuantity())));

		// 当前销售出库单填写的产品出库数量
		List<SysPssSellStockOrderProductEntity> listSellStockProduct = LKJsonUtils.toList(productList, SysPssSellStockOrderProductEntity.class);
		// 按照产品ID合并数量
		Map<String, Integer> sellStockOutProdQtyMap = listSellStockProduct.stream().collect(Collectors.groupingBy(o -> o.getId(), Collectors.summingInt(o -> o.getQuantity())));

		// 判断产品出库数量是否超出销售单数量
		for (Map.Entry<String, Integer> entry : sellStockOutProdQtyMap.entrySet()) {
			String prodId = entry.getKey();
			// 填写数量
			Integer productQty = entry.getValue();
			// 销售数量
			Integer salesProdQty = sellOrderProdQtyMap.get(prodId);
			// 已填写的入库数量
			Integer savedProdStockOutQty = sellOrderSavedStockOutQtyMap.get(prodId);

			if (savedProdStockOutQty == null) {
				savedProdStockOutQty = 0;
			}
			if (productQty > (salesProdQty - savedProdStockOutQty)) {
				errorMsg += prodInfoMap.get(prodId) + ";";
			}
		}
		if (StringUtils.isNotBlank(errorMsg)) {
			return errorMsg + "出库总量超过销售数量。";
		}

		// 判断产品出库数量是否大于库存可出库数量
		return sysPssStockBusService.checkProductStockOut(storageId, productList, id);
	}
}
