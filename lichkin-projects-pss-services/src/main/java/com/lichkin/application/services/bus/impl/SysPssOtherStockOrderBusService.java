package com.lichkin.application.services.bus.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.lichkin.framework.db.beans.DeleteSQL;
import com.lichkin.framework.db.beans.SysPssOtherStockOrderProductR;
import com.lichkin.framework.json.LKJsonUtils;
import com.lichkin.framework.utils.LKDateTimeUtils;
import com.lichkin.framework.utils.LKRandomUtils;
import com.lichkin.springframework.entities.impl.SysPssOtherStockOrderProductEntity;
import com.lichkin.springframework.services.LKDBService;

@Service
public class SysPssOtherStockOrderBusService extends LKDBService {

	public String analysisOrderNo() {
		return LKDateTimeUtils.now() + LKRandomUtils.create(15);
	}


	public void addPssOtherStockOrderProduct(String id, String productList) {
		List<SysPssOtherStockOrderProductEntity> listProduct = LKJsonUtils.toList(productList, SysPssOtherStockOrderProductEntity.class);
		for (int i = 0; i < listProduct.size(); i++) {
			SysPssOtherStockOrderProductEntity product = listProduct.get(i);
			product.setProductId(product.getId());
			product.setId(null);
			product.setOrderId(id);
			product.setSortId(i);
			if (product.getCanOutQuantity() == null) {
				product.setCanOutQuantity(0);
			}
			if (product.getStockQuantity() == null) {
				product.setStockQuantity(0);
			}
		}
		dao.persistList(listProduct);
	}


	public void clearPssOtherStockOrderProduct(String id) {
		DeleteSQL sql = new DeleteSQL(SysPssOtherStockOrderProductEntity.class);
		sql.eq(SysPssOtherStockOrderProductR.orderId, id);
		dao.delete(sql);
	}

}
