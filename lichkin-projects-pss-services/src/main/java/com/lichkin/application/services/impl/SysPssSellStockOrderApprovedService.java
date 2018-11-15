package com.lichkin.application.services.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lichkin.application.services.ActivitiApprovedService;
import com.lichkin.application.services.bus.impl.SysPssSellOrderBusService;
import com.lichkin.application.services.bus.impl.SysPssStockBusService;
import com.lichkin.framework.db.beans.QuerySQL;
import com.lichkin.framework.db.beans.SysPssSellStockOrderProductR;
import com.lichkin.springframework.entities.impl.SysActivitiFormDataEntity;
import com.lichkin.springframework.entities.impl.SysPssSellStockOrderEntity;
import com.lichkin.springframework.entities.impl.SysPssSellStockOrderProductEntity;
import com.lichkin.springframework.entities.suppers.PssOrderProductEntity;
import com.lichkin.springframework.services.LKDBService;

@Service("PSS_SELL_STOCK_OUT_ORDER")
public class SysPssSellStockOrderApprovedService extends LKDBService implements ActivitiApprovedService {

	@Autowired
	private SysPssStockBusService stockBusService;

	@Autowired
	private SysPssSellOrderBusService sellOrderBusService;


	@Override
	public void handleBusiness(SysActivitiFormDataEntity formDataEntity) {
		SysPssSellStockOrderEntity entity = dao.findOneById(SysPssSellStockOrderEntity.class, formDataEntity.getField1());
		if (entity != null) {
			changeStockQty(entity);
		}
	}


	public void changeStockQty(SysPssSellStockOrderEntity entity) {
		QuerySQL sql = new QuerySQL(false, SysPssSellStockOrderProductEntity.class);
		sql.eq(SysPssSellStockOrderProductR.orderId, entity.getId());
		List<PssOrderProductEntity> orderProductList = dao.getList(sql, PssOrderProductEntity.class);
		// 处理库存
		stockBusService.changeStockQuantity(entity, orderProductList);

		if (entity.getOrderType().equals(Boolean.FALSE)) {
			// 修改销售单出库数量及状态
			sellOrderBusService.changeSellOrderProductInventoryQuantity(entity.getOrderId(), orderProductList);
		}
	}

}
