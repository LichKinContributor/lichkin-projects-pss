package com.lichkin.application.services.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lichkin.application.services.ActivitiApprovedService;
import com.lichkin.application.services.bus.impl.SysPssPurchaseOrderBusService;
import com.lichkin.application.services.bus.impl.SysPssStockBusService;
import com.lichkin.framework.db.beans.QuerySQL;
import com.lichkin.framework.db.beans.SysPssPurchaseStockOrderProductR;
import com.lichkin.springframework.entities.impl.SysActivitiFormDataEntity;
import com.lichkin.springframework.entities.impl.SysPssPurchaseStockOrderEntity;
import com.lichkin.springframework.entities.impl.SysPssPurchaseStockOrderProductEntity;
import com.lichkin.springframework.entities.suppers.PssOrderProductEntity;
import com.lichkin.springframework.services.LKDBService;

@Service("PSS_PURCHASE_STOCK_IN_ORDER")
public class SysPssPurchaseStockOrderApprovedService extends LKDBService implements ActivitiApprovedService {

	@Autowired
	private SysPssStockBusService stockBusService;

	@Autowired
	private SysPssPurchaseOrderBusService purchaseOrderBusService;


	@Override
	public void handleBusiness(SysActivitiFormDataEntity formDataEntity) {
		SysPssPurchaseStockOrderEntity entity = dao.findOneById(SysPssPurchaseStockOrderEntity.class, formDataEntity.getField1());
		if (entity != null) {
			changeStockQty(entity);
		}
	}


	public void changeStockQty(SysPssPurchaseStockOrderEntity entity) {
		// 改库存
		QuerySQL sql = new QuerySQL(false, SysPssPurchaseStockOrderProductEntity.class);
		sql.eq(SysPssPurchaseStockOrderProductR.orderId, entity.getId());
		List<PssOrderProductEntity> orderProductList = dao.getList(sql, PssOrderProductEntity.class);
		stockBusService.changeStockQuantity(entity, orderProductList);
		if (entity.getOrderType().equals(Boolean.TRUE)) {
			// 修改采购单入库数量及状态
			purchaseOrderBusService.changePurchaseOrderProductInventoryQuantity(entity.getOrderId(), orderProductList);
		}

	}

}
