package com.lichkin.application.services.bus.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.lichkin.framework.db.beans.DeleteSQL;
import com.lichkin.framework.db.beans.SysPssAllotOrderProductR;
import com.lichkin.framework.json.LKJsonUtils;
import com.lichkin.framework.utils.LKDateTimeUtils;
import com.lichkin.framework.utils.LKRandomUtils;
import com.lichkin.springframework.entities.impl.SysPssAllotOrderProductEntity;
import com.lichkin.springframework.services.LKDBService;

@Service
public class SysPssAllotOrderBusService extends LKDBService {

	public String analysisOrderNo() {
		return LKDateTimeUtils.now() + LKRandomUtils.create(15);
	}


	public void addPssAllotOrderProduct(String id, String productList) {
		List<SysPssAllotOrderProductEntity> listProduct = LKJsonUtils.toList(productList, SysPssAllotOrderProductEntity.class);
		for (int i = 0; i < listProduct.size(); i++) {
			SysPssAllotOrderProductEntity product = listProduct.get(i);
			product.setProductId(product.getId());
			product.setId(null);
			product.setOrderId(id);
			product.setSortId(i);
		}
		dao.persistList(listProduct);
	}


	public void clearPssAllotOrderProduct(String id) {
		DeleteSQL sql = new DeleteSQL(SysPssAllotOrderProductEntity.class);
		sql.eq(SysPssAllotOrderProductR.orderId, id);
		dao.delete(sql);
	}

}
