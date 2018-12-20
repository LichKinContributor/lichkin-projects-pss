package com.lichkin.application.services.bus.impl;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

import org.springframework.stereotype.Service;

import com.lichkin.application.apis.api50200.SI;
import com.lichkin.framework.db.beans.DeleteSQL;
import com.lichkin.framework.db.beans.QuerySQL;
import com.lichkin.framework.db.beans.SysPssSellOrderProductR;
import com.lichkin.framework.defines.enums.impl.InventoryStatusEnum;
import com.lichkin.framework.json.LKJsonUtils;
import com.lichkin.framework.utils.LKDateTimeUtils;
import com.lichkin.framework.utils.LKRandomUtils;
import com.lichkin.springframework.entities.impl.SysPssSellOrderEntity;
import com.lichkin.springframework.entities.impl.SysPssSellOrderProductEntity;
import com.lichkin.springframework.entities.impl.SysPssSellReturnNotStockOutOrderEntity;
import com.lichkin.springframework.entities.impl.SysPssSellReturnNotStockOutOrderProductEntity;
import com.lichkin.springframework.entities.impl.SysPssSellStockOrderEntity;
import com.lichkin.springframework.entities.impl.SysPssSellStockOrderProductEntity;
import com.lichkin.springframework.services.LKDBService;

@Service
public class SysPssSellOrderBusService extends LKDBService {

	public String analysisOrderAmount(SI sin) {
		BigDecimal bdOrderAmount = new BigDecimal("0").setScale(2, RoundingMode.HALF_UP);
		List<SysPssSellOrderProductEntity> listProduct = LKJsonUtils.toList(sin.getProductList(), SysPssSellOrderProductEntity.class);
		sin.setListProduct(listProduct);
		for (int i = 0; i < listProduct.size(); i++) {
			SysPssSellOrderProductEntity product = listProduct.get(i);
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


	public void clearPssSellOrderProduct(String id) {
		DeleteSQL sql = new DeleteSQL(SysPssSellOrderProductEntity.class);
		sql.eq(SysPssSellOrderProductR.orderId, id);
		dao.delete(sql);
	}


	public void addPssSellOrderProduct(String id, List<SysPssSellOrderProductEntity> listProduct) {
		for (SysPssSellOrderProductEntity product : listProduct) {
			product.setOrderId(id);
		}
		dao.persistList(listProduct);
	}


	/**
	 * 修改销售单已出库产品数量
	 * @param sellStockOrder 销售出入库订单
	 * @param orderProductList 出库产品信息
	 */
	public void changeSellOrderProductInventoryQuantity(SysPssSellStockOrderEntity sellStockOrder, List<SysPssSellStockOrderProductEntity> orderProductList) {
		// 查询销售单中的所有产品
		QuerySQL sql = new QuerySQL(SysPssSellOrderProductEntity.class);
		sql.eq(SysPssSellOrderProductR.orderId, sellStockOrder.getOrderId());
		List<SysPssSellOrderProductEntity> listSellOrderProd = dao.getList(sql, SysPssSellOrderProductEntity.class);

		if (sellStockOrder.getOrderType().equals(Boolean.FALSE)) {
			for (SysPssSellStockOrderProductEntity stockOrderProduct : orderProductList) {
				for (SysPssSellOrderProductEntity sellOrderproduct : listSellOrderProd) {
					if (stockOrderProduct.getSellOrderProductId().equals(sellOrderproduct.getId())) {
						sellOrderproduct.setInventoryQuantity(sellOrderproduct.getInventoryQuantity() + stockOrderProduct.getQuantity());
						break;
					}
				}
			}
			dao.mergeList(listSellOrderProd);

			// 判断销售单是否完全出库
			boolean allOut = true;
			for (SysPssSellOrderProductEntity pp : listSellOrderProd) {
				int leftQty = pp.getQuantity() - pp.getInventoryQuantity() - pp.getReturnedQuantity();
				if (leftQty > 0) {
					allOut = false;
					break;
				}
			}
			SysPssSellOrderEntity sellOrderEntity = dao.findOneById(SysPssSellOrderEntity.class, sellStockOrder.getOrderId());
			if (allOut) {// 完全出库
				sellOrderEntity.setInventoryStatus(InventoryStatusEnum.ALL);
			} else {// 部分出库
				sellOrderEntity.setInventoryStatus(InventoryStatusEnum.PART);
			}
			dao.mergeOne(sellOrderEntity);
		}
	}


	/**
	 * 修改销售单退货产品数量
	 * @param returnOrder 退货订单
	 * @param orderProductList 产品信息
	 */
	public void changeSellOrderProductReturnedQuantity(SysPssSellReturnNotStockOutOrderEntity returnOrder, List<SysPssSellReturnNotStockOutOrderProductEntity> orderProductList) {
		// 查询销售单中的所有产品
		QuerySQL sql = new QuerySQL(SysPssSellOrderProductEntity.class);
		sql.eq(SysPssSellOrderProductR.orderId, returnOrder.getOrderId());
		List<SysPssSellOrderProductEntity> listSellOrderProd = dao.getList(sql, SysPssSellOrderProductEntity.class);

		for (SysPssSellReturnNotStockOutOrderProductEntity returnProd : orderProductList) {
			for (SysPssSellOrderProductEntity sellProduct : listSellOrderProd) {
				if (returnProd.getSellOrderProductId().equals(sellProduct.getId())) {
					sellProduct.setReturnedQuantity(sellProduct.getReturnedQuantity() + returnProd.getQuantity());
					break;
				}
			}
		}
		dao.mergeList(listSellOrderProd);

		// 判断销售单是否完全出库
		boolean allOut = true;
		for (SysPssSellOrderProductEntity pp : listSellOrderProd) {
			int leftQty = pp.getQuantity() - pp.getInventoryQuantity() - pp.getReturnedQuantity();
			if (leftQty > 0) {
				allOut = false;
				break;
			}
		}
		if (allOut) {// 完全入库
			SysPssSellOrderEntity sellOrderEntity = dao.findOneById(SysPssSellOrderEntity.class, returnOrder.getOrderId());
			sellOrderEntity.setInventoryStatus(InventoryStatusEnum.ALL);
			dao.mergeOne(sellOrderEntity);
		}
	}
}
