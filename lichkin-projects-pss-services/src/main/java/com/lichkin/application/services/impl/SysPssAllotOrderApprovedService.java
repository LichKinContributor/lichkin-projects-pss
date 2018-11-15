package com.lichkin.application.services.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lichkin.application.services.ActivitiApprovedService;
import com.lichkin.application.services.bus.impl.SysPssStockBusService;
import com.lichkin.framework.db.beans.QuerySQL;
import com.lichkin.framework.db.beans.SysPssAllotOrderProductR;
import com.lichkin.springframework.entities.impl.SysActivitiFormDataEntity;
import com.lichkin.springframework.entities.impl.SysPssAllotOrderEntity;
import com.lichkin.springframework.entities.impl.SysPssAllotOrderProductEntity;
import com.lichkin.springframework.entities.suppers.PssOrderProductEntity;
import com.lichkin.springframework.services.LKDBService;

@Service("PSS_ALLOT_ORDER")
public class SysPssAllotOrderApprovedService extends LKDBService implements ActivitiApprovedService {

	@Autowired
	private SysPssStockBusService stockBusService;


	@Override
	public void handleBusiness(SysActivitiFormDataEntity formDataEntity) {
		SysPssAllotOrderEntity entity = dao.findOneById(SysPssAllotOrderEntity.class, formDataEntity.getField1());
		if (entity != null) {
			changeStockQty(entity);
		}
	}


	public void changeStockQty(SysPssAllotOrderEntity entity) {
		QuerySQL sql = new QuerySQL(false, SysPssAllotOrderProductEntity.class);
		sql.eq(SysPssAllotOrderProductR.orderId, entity.getId());
		List<PssOrderProductEntity> orderProductList = dao.getList(sql, PssOrderProductEntity.class);
		// 处理库存
		stockBusService.changeStockQuantityByAllotOrder(entity, orderProductList);
	}

}
