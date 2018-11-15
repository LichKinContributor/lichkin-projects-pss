package com.lichkin.application.services.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lichkin.application.services.bus.impl.SysPssStockBusService;
import com.lichkin.framework.db.beans.QuerySQL;
import com.lichkin.framework.db.beans.SysPssOtherStockOrderProductR;
import com.lichkin.springframework.entities.impl.SysActivitiFormDataEntity;
import com.lichkin.springframework.entities.impl.SysPssOtherStockOrderEntity;
import com.lichkin.springframework.entities.impl.SysPssOtherStockOrderProductEntity;
import com.lichkin.springframework.entities.suppers.PssOrderProductEntity;
import com.lichkin.springframework.services.LKDBService;

@Service
public class SysPssOtherStockOrderService extends LKDBService {

	@Autowired
	private SysPssStockBusService stockBusService;


	public void handleByActiviti(SysActivitiFormDataEntity formDataEntity) {
		SysPssOtherStockOrderEntity entity = dao.findOneById(SysPssOtherStockOrderEntity.class, formDataEntity.getField1());
		if (entity != null) {
			changeStockQty(entity);
		}
	}


	public void changeStockQty(SysPssOtherStockOrderEntity entity) {
		QuerySQL sql = new QuerySQL(false, SysPssOtherStockOrderProductEntity.class);
		sql.eq(SysPssOtherStockOrderProductR.orderId, entity.getId());
		List<PssOrderProductEntity> orderProductList = dao.getList(sql, PssOrderProductEntity.class);
		// 处理库存
		stockBusService.changeStockQuantity(entity, orderProductList);
	}

}
