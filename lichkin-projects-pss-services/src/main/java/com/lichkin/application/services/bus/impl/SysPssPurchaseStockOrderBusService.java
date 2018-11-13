package com.lichkin.application.services.bus.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lichkin.application.mappers.impl.PssStockQtyMapper;
import com.lichkin.application.mappers.impl.in.PurchaseOrderSavedStockInQtyIn;
import com.lichkin.application.mappers.impl.out.PurchaseOrderSavedStockInQtyOut;
import com.lichkin.framework.db.beans.DeleteSQL;
import com.lichkin.framework.db.beans.QuerySQL;
import com.lichkin.framework.db.beans.SysPssPurchaseOrderProductR;
import com.lichkin.framework.db.beans.SysPssPurchaseStockOrderProductR;
import com.lichkin.framework.json.LKJsonUtils;
import com.lichkin.framework.utils.LKDateTimeUtils;
import com.lichkin.framework.utils.LKRandomUtils;
import com.lichkin.springframework.entities.impl.SysPssProductEntity;
import com.lichkin.springframework.entities.impl.SysPssPurchaseOrderProductEntity;
import com.lichkin.springframework.entities.impl.SysPssPurchaseStockOrderProductEntity;
import com.lichkin.springframework.services.LKDBService;

@Service
public class SysPssPurchaseStockOrderBusService extends LKDBService {

	@Autowired
	private PssStockQtyMapper pssStockOutQtyMapper;


	public String analysisOrderNo() {
		return LKDateTimeUtils.now() + LKRandomUtils.create(15);
	}


	public void clearPssPurchaseStockOrderProduct(String id) {
		DeleteSQL sql = new DeleteSQL(SysPssPurchaseStockOrderProductEntity.class);
		sql.eq(SysPssPurchaseStockOrderProductR.orderId, id);
		dao.delete(sql);
	}


	public void addPssPurchaseStockOrderProduct(String id, String productList) {
		List<SysPssPurchaseStockOrderProductEntity> listProduct = LKJsonUtils.toList(productList, SysPssPurchaseStockOrderProductEntity.class);
		for (int i = 0; i < listProduct.size(); i++) {
			SysPssPurchaseStockOrderProductEntity product = listProduct.get(i);
			product.setProductId(product.getId());
			product.setId(null);
			product.setOrderId(id);
			product.setSortId(i);
		}
		dao.persistList(listProduct);
	}


	/**
	 * @param id 采购入库单ID
	 * @param purchaseOrderId 采购单ID
	 * @param productList 入库产品json列表
	 * @return
	 */
	public String checkProductQty(String id, String purchaseOrderId, String productList) {
		String errorMsg = "";
		// 查询采购单中的所有产品
		QuerySQL sql = new QuerySQL(SysPssPurchaseOrderProductEntity.class);
		sql.eq(SysPssPurchaseOrderProductR.orderId, purchaseOrderId);
		List<SysPssPurchaseOrderProductEntity> listPurchaseOrderProd = dao.getList(sql, SysPssPurchaseOrderProductEntity.class);
		// 采购单产品数量（按照产品ID合并数量）
		Map<String, Integer> purchaseProdQtyMap = listPurchaseOrderProd.stream().collect(Collectors.groupingBy(o -> o.getProductId(), Collectors.summingInt(o -> o.getQuantity())));

		List<SysPssProductEntity> listProd = LKJsonUtils.toList(productList, SysPssProductEntity.class);

		StringBuffer prodIds = new StringBuffer();

		Map<String, String> prodInfoMap = new HashMap<>();
		// 判断产品是否在采购单中
		for (int i = 0; i < listProd.size(); i++) {
			SysPssProductEntity prod = listProd.get(i);

			prodInfoMap.put(prod.getId(), prod.getProductName());

			prodIds.append("'" + prod.getId() + "'");
			if (i < (listProd.size() - 1)) {
				prodIds.append(",");
			}

			// 产品不存在
			if (purchaseProdQtyMap.get(prod.getId()) == null) {
				errorMsg += prod.getProductName() + ";";
			}
		}
		if (StringUtils.isNotBlank(errorMsg)) {
			return errorMsg + "不在当前采购单中。";
		}

		// 采购单已填写的入库数量
		List<PurchaseOrderSavedStockInQtyOut> listPurchaseProdStockInQty = pssStockOutQtyMapper.findPurchaseOrderSavedStockInQty(new PurchaseOrderSavedStockInQtyIn(prodIds.toString(), purchaseOrderId, id));
		Map<String, Integer> purchaseProdStockInQtyMap = listPurchaseProdStockInQty.stream().collect(Collectors.groupingBy(o -> o.getProductId(), Collectors.summingInt(o -> o.getQuantity())));

		// 采购入库单填写的产品入库数量
		List<SysPssPurchaseStockOrderProductEntity> listPurchaseStockProduct = LKJsonUtils.toList(productList, SysPssPurchaseStockOrderProductEntity.class);
		// 按照产品ID合并数量
		Map<String, Integer> purchaseStockInProdQtyMap = listPurchaseStockProduct.stream().collect(Collectors.groupingBy(o -> o.getId(), Collectors.summingInt(o -> o.getQuantity())));

		// 判断产品入库数量是否超出采购数量
		for (Map.Entry<String, Integer> entry : purchaseStockInProdQtyMap.entrySet()) {
			String prodId = entry.getKey();
			// 填写数量
			Integer productQty = entry.getValue();
			// 采购数量
			Integer purchaseProdQty = purchaseProdQtyMap.get(prodId);
			// 已填写的入库数量
			Integer purchaseProdStockInQty = purchaseProdStockInQtyMap.get(prodId);

			if (purchaseProdStockInQty == null) {
				purchaseProdStockInQty = 0;
			}
			if (productQty > (purchaseProdQty - purchaseProdStockInQty)) {
				errorMsg += prodInfoMap.get(prodId) + ";";
			}
		}
		if (StringUtils.isNotBlank(errorMsg)) {
			return errorMsg + "入库总量超过采购数量。";
		}

		return "";
	}

}
