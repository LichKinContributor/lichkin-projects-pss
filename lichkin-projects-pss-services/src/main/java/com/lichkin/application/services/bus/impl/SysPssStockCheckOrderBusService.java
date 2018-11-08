package com.lichkin.application.services.bus.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.lichkin.framework.db.beans.DeleteSQL;
import com.lichkin.framework.db.beans.SysPssStockCheckOrderProductR;
import com.lichkin.framework.json.LKJsonUtils;
import com.lichkin.framework.utils.LKDateTimeUtils;
import com.lichkin.framework.utils.LKRandomUtils;
import com.lichkin.springframework.entities.impl.SysPssStockCheckOrderProductEntity;
import com.lichkin.springframework.services.LKDBService;

@Service
public class SysPssStockCheckOrderBusService extends LKDBService {

	public String analysisOrderNo() {
		return LKDateTimeUtils.now() + LKRandomUtils.create(15);
	}


	public void addPssStockCheckOrderProduct(String id, String productList) {
		List<SysPssStockCheckOrderProductEntity> listProduct = LKJsonUtils.toList(productList, SysPssStockCheckOrderProductEntity.class);
		for (int i = 0; i < listProduct.size(); i++) {
			SysPssStockCheckOrderProductEntity product = listProduct.get(i);
			product.setProductId(product.getId());
			product.setId(null);
			product.setOrderId(id);
			product.setSortId(i);
		}
		dao.persistList(listProduct);
	}


	public void clearPssStockCheckOrderProduct(String id) {
		DeleteSQL sql = new DeleteSQL(SysPssStockCheckOrderProductEntity.class);
		sql.eq(SysPssStockCheckOrderProductR.orderId, id);
		dao.delete(sql);
	}
}
