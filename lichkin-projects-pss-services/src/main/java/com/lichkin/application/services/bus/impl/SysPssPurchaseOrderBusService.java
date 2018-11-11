package com.lichkin.application.services.bus.impl;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.lichkin.application.apis.api50100.SI;
import com.lichkin.framework.db.beans.DeleteSQL;
import com.lichkin.framework.db.beans.QuerySQL;
import com.lichkin.framework.db.beans.SysPssPurchaseOrderProductR;
import com.lichkin.framework.defines.enums.impl.InventoryStatusEnum;
import com.lichkin.framework.json.LKJsonUtils;
import com.lichkin.framework.utils.LKDateTimeUtils;
import com.lichkin.framework.utils.LKRandomUtils;
import com.lichkin.springframework.entities.impl.SysPssPurchaseOrderEntity;
import com.lichkin.springframework.entities.impl.SysPssPurchaseOrderProductEntity;
import com.lichkin.springframework.entities.suppers.PssOrderProductEntity;
import com.lichkin.springframework.services.LKDBService;

@Service
public class SysPssPurchaseOrderBusService extends LKDBService {

	public String analysisOrderAmount(SI sin) {
		BigDecimal bdOrderAmount = new BigDecimal("0").setScale(2, RoundingMode.HALF_UP);
		List<SysPssPurchaseOrderProductEntity> listProduct = LKJsonUtils.toList(sin.getProductList(), SysPssPurchaseOrderProductEntity.class);
		sin.setListProduct(listProduct);
		for (int i = 0; i < listProduct.size(); i++) {
			SysPssPurchaseOrderProductEntity product = listProduct.get(i);
			product.setProductId(product.getId());
			product.setId(null);
			product.setSortId(i);
			BigDecimal unitPrice = new BigDecimal(product.getUnitPrice()).setScale(2, RoundingMode.HALF_UP);
			product.setUnitPrice(unitPrice.toString());
			BigDecimal subTotalPrice = unitPrice.multiply(new BigDecimal(product.getQuantity())).setScale(2, RoundingMode.HALF_UP);
			product.setSubTotalPrice(subTotalPrice.toString());
			bdOrderAmount = bdOrderAmount.add(subTotalPrice);
		}
		return bdOrderAmount.toString();
	}


	public String analysisOrderNo() {
		return LKDateTimeUtils.now() + LKRandomUtils.create(15);
	}


	public void clearPssPurchaseOrderProduct(String id) {
		DeleteSQL sql = new DeleteSQL(SysPssPurchaseOrderProductEntity.class);
		sql.eq(SysPssPurchaseOrderProductR.orderId, id);
		dao.delete(sql);
	}


	public void addPssPurchaseOrderProduct(String id, List<SysPssPurchaseOrderProductEntity> listProduct) {
		for (SysPssPurchaseOrderProductEntity product : listProduct) {
			product.setOrderId(id);
		}
		dao.persistList(listProduct);
	}


	/**
	 * 修改采购单已入库产品数量
	 * @param id 采购单ID
	 * @param orderProductList 入库产品信息
	 */
	public void changePurchaseOrderProductInventoryQuantity(String id, List<PssOrderProductEntity> orderProductList) {
		// 查询采购单中的所有产品
		QuerySQL sql = new QuerySQL(SysPssPurchaseOrderProductEntity.class);
		sql.eq(SysPssPurchaseOrderProductR.orderId, id);
		List<SysPssPurchaseOrderProductEntity> listPurchaseOrderProd = dao.getList(sql, SysPssPurchaseOrderProductEntity.class);

		// 采购入库单产品数量（按照产品ID合并数量）
		Map<String, Integer> purchaseStockInProdQtyMap = orderProductList.stream().collect(Collectors.groupingBy(o -> o.getProductId(), Collectors.summingInt(o -> o.getQuantity())));

		for (Map.Entry<String, Integer> entry : purchaseStockInProdQtyMap.entrySet()) {
			String prodId = entry.getKey();
			Integer qty = entry.getValue();
			for (SysPssPurchaseOrderProductEntity product : listPurchaseOrderProd) {
				if (prodId.equals(product.getProductId())) {
					int remainingQty = product.getQuantity() - product.getInventoryQuantity();
					if (remainingQty >= qty) {
						product.setInventoryQuantity(product.getInventoryQuantity() + qty);
						break;
					} else {
						// 剩余数小于入库的单数量 继续扣下一个相同的产品
						product.setInventoryQuantity(product.getQuantity());
						qty = qty - remainingQty;
					}
				}
			}
		}
		dao.mergeList(listPurchaseOrderProd);

		// 判断采购单是否完全入库
		boolean allIn = true;
		for (SysPssPurchaseOrderProductEntity pp : listPurchaseOrderProd) {
			if (pp.getQuantity() > pp.getInventoryQuantity()) {
				allIn = false;
				break;
			}
		}
		SysPssPurchaseOrderEntity purchaseOrderEntity = dao.findOneById(SysPssPurchaseOrderEntity.class, id);
		if (allIn) {// 完全入库
			purchaseOrderEntity.setInventoryStatus(InventoryStatusEnum.ALL);
		} else {// 部分入库
			purchaseOrderEntity.setInventoryStatus(InventoryStatusEnum.PART);
		}
		dao.mergeOne(purchaseOrderEntity);
	}
}
