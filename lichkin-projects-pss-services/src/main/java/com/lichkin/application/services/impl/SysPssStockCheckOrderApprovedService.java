package com.lichkin.application.services.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lichkin.application.services.ActivitiApprovedService;
import com.lichkin.application.services.bus.impl.SysPssStockBusService;
import com.lichkin.framework.db.beans.QuerySQL;
import com.lichkin.framework.db.beans.SysPssStockCheckOrderProductR;
import com.lichkin.springframework.entities.impl.SysActivitiFormDataEntity;
import com.lichkin.springframework.entities.impl.SysPssStockCheckOrderEntity;
import com.lichkin.springframework.entities.impl.SysPssStockCheckOrderProductEntity;
import com.lichkin.springframework.services.LKDBService;

@Service("PSS_STOCK_CHECK_ORDER")
public class SysPssStockCheckOrderApprovedService extends LKDBService implements ActivitiApprovedService {

	@Autowired
	private SysPssStockBusService stockBusService;


	@Override
	public void handleBusiness(SysActivitiFormDataEntity formDataEntity) {
		SysPssStockCheckOrderEntity entity = dao.findOneById(SysPssStockCheckOrderEntity.class, formDataEntity.getField1());
		if (entity != null) {
			changeStockQty(entity);
		}
	}


	public void changeStockQty(SysPssStockCheckOrderEntity entity) {
		QuerySQL sql = new QuerySQL(false, SysPssStockCheckOrderProductEntity.class);
		sql.eq(SysPssStockCheckOrderProductR.orderId, entity.getId());
		List<SysPssStockCheckOrderProductEntity> orderProductList = dao.getList(sql, SysPssStockCheckOrderProductEntity.class);
		// 处理库存
		stockBusService.changeStockQuantityByCheckOrder(entity, orderProductList);
	}

}
