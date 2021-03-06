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
		// 采购单产品数量
		Map<String, Integer> purchaseProdQtyMap = listPurchaseOrderProd.stream().collect(Collectors.toMap(o -> o.getId(), o -> o.getQuantity()));

		// 采购入库单填写的产品
		List<SysPssProductEntity> listProd = LKJsonUtils.toList(productList, SysPssProductEntity.class);
		List<SysPssPurchaseStockOrderProductEntity> listPurchaseStockProduct = LKJsonUtils.toList(productList, SysPssPurchaseStockOrderProductEntity.class);

		StringBuffer prodIds = new StringBuffer();

		// 采购单产品Id->产品名称
		Map<String, String> prodInfoMap = new HashMap<>();

		for (int i = 0; i < listProd.size(); i++) {
			SysPssProductEntity prod = listProd.get(i);

			// 设置产品名称
			for (SysPssPurchaseStockOrderProductEntity purchaseStockOrderProd : listPurchaseStockProduct) {
				if (purchaseStockOrderProd.getId().equals(prod.getId())) {
					prodInfoMap.put(purchaseStockOrderProd.getPurchaseOrderProductId(), prod.getProductName());
				}
			}

			prodIds.append("'" + prod.getId() + "'");
			if (i < (listProd.size() - 1)) {
				prodIds.append(",");
			}

		}

		// 采购单已填写的入库、退货数量
		List<PurchaseOrderSavedStockInQtyOut> listPurchaseProdStockInQty = pssStockOutQtyMapper.findPurchaseOrderSavedStockInQty(new PurchaseOrderSavedStockInQtyIn(prodIds.toString(), purchaseOrderId, id));
		Map<String, Integer> purchaseProdStockInQtyMap = listPurchaseProdStockInQty.stream().collect(Collectors.groupingBy(o -> o.getPurchaseOrderProductId(), Collectors.summingInt(o -> o.getQuantity())));

		// 按照产品ID合并数量
		Map<String, Integer> purchaseStockInProdQtyMap = listPurchaseStockProduct.stream().collect(Collectors.groupingBy(o -> o.getPurchaseOrderProductId(), Collectors.summingInt(o -> o.getQuantity())));

		// 判断产品入库数量是否超出采购数量
		for (Map.Entry<String, Integer> entry : purchaseStockInProdQtyMap.entrySet()) {
			String purchaseOrderProductId = entry.getKey();
			// 填写数量
			Integer productQty = entry.getValue();
			// 采购数量
			Integer purchaseProdQty = purchaseProdQtyMap.get(purchaseOrderProductId);
			// 已填写的数量
			Integer purchaseProdStockInQty = purchaseProdStockInQtyMap.get(purchaseOrderProductId);

			if (purchaseProdStockInQty == null) {
				purchaseProdStockInQty = 0;
			}
			if (productQty > (purchaseProdQty - purchaseProdStockInQty)) {
				errorMsg += prodInfoMap.get(purchaseOrderProductId) + ";";
			}
		}
		if (StringUtils.isNotBlank(errorMsg)) {
			return errorMsg + "入库总量超过采购数量。";
		}

		return "";
	}

}
