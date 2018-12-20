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
		// 销售单产品数量
		Map<String, Integer> sellOrderProdQtyMap = listSellOrderProd.stream().collect(Collectors.toMap(o -> o.getId(), o -> o.getQuantity()));

		// 当前销售出库单填写的产品
		List<SysPssProductEntity> listProd = LKJsonUtils.toList(productList, SysPssProductEntity.class);
		List<SysPssSellStockOrderProductEntity> listSellStockProduct = LKJsonUtils.toList(productList, SysPssSellStockOrderProductEntity.class);

		StringBuffer prodIds = new StringBuffer();

		// 销售单产品Id->产品名称
		Map<String, String> prodInfoMap = new HashMap<>();

		for (int i = 0; i < listProd.size(); i++) {
			SysPssProductEntity prod = listProd.get(i);

			// 设置产品名称
			for (SysPssSellStockOrderProductEntity sellStockOrderProd : listSellStockProduct) {
				if (sellStockOrderProd.getId().equals(prod.getId())) {
					prodInfoMap.put(sellStockOrderProd.getSellOrderProductId(), prod.getProductName());
				}
			}

			prodInfoMap.put(prod.getId(), prod.getProductName());

			prodIds.append("'" + prod.getId() + "'");
			if (i < (listProd.size() - 1)) {
				prodIds.append(",");
			}
		}

		// 销售单已保存的出库、退货数量
		List<SellOrderSavedStockOutQtyOut> listSellOrderSavedStockOutQty = pssStockOutQtyMapper.findSellOrderSavedStockOutQty(new SellOrderSavedStockOutQtyIn(prodIds.toString(), sellOrderId, id));
		Map<String, Integer> sellOrderSavedStockOutQtyMap = listSellOrderSavedStockOutQty.stream().collect(Collectors.groupingBy(o -> o.getSellOrderProductId(), Collectors.summingInt(o -> o.getQuantity())));

		// 按照产品ID合并数量
		Map<String, Integer> sellStockOutProdQtyMap = listSellStockProduct.stream().collect(Collectors.groupingBy(o -> o.getSellOrderProductId(), Collectors.summingInt(o -> o.getQuantity())));

		// 判断产品出库数量是否超出销售单数量
		for (Map.Entry<String, Integer> entry : sellStockOutProdQtyMap.entrySet()) {
			String sellOrderProductId = entry.getKey();
			// 填写数量
			Integer productQty = entry.getValue();
			// 销售数量
			Integer salesProdQty = sellOrderProdQtyMap.get(sellOrderProductId);
			// 已填写的数量
			Integer savedProdStockOutQty = sellOrderSavedStockOutQtyMap.get(sellOrderProductId);

			if (savedProdStockOutQty == null) {
				savedProdStockOutQty = 0;
			}
			if (productQty > (salesProdQty - savedProdStockOutQty)) {
				errorMsg += prodInfoMap.get(sellOrderProductId) + ";";
			}
		}
		if (StringUtils.isNotBlank(errorMsg)) {
			return errorMsg + "出库总量超过销售数量。";
		}

		// 判断产品出库数量是否大于库存可出库数量
		return sysPssStockBusService.checkProductStockOut(storageId, productList, id);
	}
}
