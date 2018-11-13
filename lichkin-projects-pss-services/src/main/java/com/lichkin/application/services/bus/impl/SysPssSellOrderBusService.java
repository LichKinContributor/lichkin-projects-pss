package com.lichkin.application.services.bus.impl;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

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
import com.lichkin.springframework.entities.suppers.PssOrderProductEntity;
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
	 * @param id 销售单ID
	 * @param orderProductList 出库产品信息
	 */
	public void changeSellOrderProductInventoryQuantity(String id, List<PssOrderProductEntity> orderProductList) {
		// 查询销售单中的所有产品
		QuerySQL sql = new QuerySQL(SysPssSellOrderProductEntity.class);
		sql.eq(SysPssSellOrderProductR.orderId, id);
		List<SysPssSellOrderProductEntity> listSellOrderProd = dao.getList(sql, SysPssSellOrderProductEntity.class);

		// 销售出库单产品数量（按照产品ID合并数量）
		Map<String, Integer> sellStockOutProdQtyMap = orderProductList.stream().collect(Collectors.groupingBy(o -> o.getProductId(), Collectors.summingInt(o -> o.getQuantity())));

		for (Map.Entry<String, Integer> entry : sellStockOutProdQtyMap.entrySet()) {
			String prodId = entry.getKey();
			Integer qty = entry.getValue();
			for (SysPssSellOrderProductEntity product : listSellOrderProd) {
				if (prodId.equals(product.getProductId())) {
					int remainingQty = product.getQuantity() - product.getInventoryQuantity();
					if (remainingQty >= qty) {
						product.setInventoryQuantity(product.getInventoryQuantity() + qty);
						break;
					} else {
						// 剩余数小于出库数量 继续扣下一个相同的产品
						product.setInventoryQuantity(product.getQuantity());
						qty = qty - remainingQty;
					}
				}
			}
		}
		dao.mergeList(listSellOrderProd);

		// 判断销售单是否完全出库
		boolean allOut = true;
		for (SysPssSellOrderProductEntity pp : listSellOrderProd) {
			if (pp.getQuantity() > pp.getInventoryQuantity()) {
				allOut = false;
				break;
			}
		}
		SysPssSellOrderEntity sellOrderEntity = dao.findOneById(SysPssSellOrderEntity.class, id);
		if (allOut) {// 完全出库
			sellOrderEntity.setInventoryStatus(InventoryStatusEnum.ALL);
		} else {// 部分出库
			sellOrderEntity.setInventoryStatus(InventoryStatusEnum.PART);
		}
		dao.mergeOne(sellOrderEntity);
	}
}
