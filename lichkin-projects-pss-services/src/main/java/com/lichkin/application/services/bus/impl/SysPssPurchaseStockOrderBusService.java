package com.lichkin.application.services.bus.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.lichkin.framework.db.beans.DeleteSQL;
import com.lichkin.framework.db.beans.SysPssPurchaseStockOrderProductR;
import com.lichkin.framework.json.LKJsonUtils;
import com.lichkin.framework.utils.LKDateTimeUtils;
import com.lichkin.framework.utils.LKRandomUtils;
import com.lichkin.springframework.entities.impl.SysPssPurchaseStockOrderProductEntity;
import com.lichkin.springframework.services.LKDBService;

@Service
public class SysPssPurchaseStockOrderBusService extends LKDBService {

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
		for (SysPssPurchaseStockOrderProductEntity product : listProduct) {
			product.setOrderId(id);
		}
		dao.persistList(listProduct);
	}

}
