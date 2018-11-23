package com.lichkin.application.services.bus.impl;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

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
import com.lichkin.springframework.entities.impl.SysPssPurchaseStockOrderEntity;
import com.lichkin.springframework.entities.impl.SysPssPurchaseStockOrderProductEntity;
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
	 * @param stockOrder 采购出入库订单
	 * @param orderProductList 入库产品信息
	 */
	public void changePurchaseOrderProductInventoryQuantity(SysPssPurchaseStockOrderEntity stockOrder, List<SysPssPurchaseStockOrderProductEntity> orderProductList) {
		// 查询采购单中的所有产品
		QuerySQL sql = new QuerySQL(SysPssPurchaseOrderProductEntity.class);
		sql.eq(SysPssPurchaseOrderProductR.orderId, stockOrder.getOrderId());
		List<SysPssPurchaseOrderProductEntity> listPurchaseOrderProd = dao.getList(sql, SysPssPurchaseOrderProductEntity.class);

		// 入库
		if (stockOrder.getOrderType().equals(Boolean.TRUE)) {
			for (SysPssPurchaseStockOrderProductEntity stockInProd : orderProductList) {
				for (SysPssPurchaseOrderProductEntity purchaseProduct : listPurchaseOrderProd) {
					if (stockInProd.getPurchaseOrderProductId().equals(purchaseProduct.getId())) {
						purchaseProduct.setInventoryQuantity(purchaseProduct.getInventoryQuantity() + stockInProd.getQuantity());
						break;
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
			SysPssPurchaseOrderEntity purchaseOrderEntity = dao.findOneById(SysPssPurchaseOrderEntity.class, stockOrder.getOrderId());
			if (allIn) {// 完全入库
				purchaseOrderEntity.setInventoryStatus(InventoryStatusEnum.ALL);
			} else {// 部分入库
				purchaseOrderEntity.setInventoryStatus(InventoryStatusEnum.PART);
			}
			dao.mergeOne(purchaseOrderEntity);
		}
	}
}
