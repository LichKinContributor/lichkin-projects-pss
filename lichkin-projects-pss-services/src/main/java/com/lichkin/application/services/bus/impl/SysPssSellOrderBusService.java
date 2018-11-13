package com.lichkin.application.services.bus.impl;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

import org.springframework.stereotype.Service;

import com.lichkin.application.apis.api50200.SI;
import com.lichkin.framework.db.beans.DeleteSQL;
import com.lichkin.framework.db.beans.SysPssSellOrderProductR;
import com.lichkin.framework.json.LKJsonUtils;
import com.lichkin.framework.utils.LKDateTimeUtils;
import com.lichkin.framework.utils.LKRandomUtils;
import com.lichkin.springframework.entities.impl.SysPssSellOrderProductEntity;
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
}
