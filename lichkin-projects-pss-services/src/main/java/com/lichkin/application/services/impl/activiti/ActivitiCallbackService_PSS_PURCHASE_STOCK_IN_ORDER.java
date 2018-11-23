package com.lichkin.application.services.impl.activiti;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lichkin.application.services.ActivitiCallbackService;
import com.lichkin.application.services.WithoutActivitiCallbackService;
import com.lichkin.application.services.bus.impl.SysPssPurchaseOrderBusService;
import com.lichkin.application.services.bus.impl.SysPssStockBusService;
import com.lichkin.defines.PssStatics;
import com.lichkin.framework.db.beans.QuerySQL;
import com.lichkin.framework.db.beans.SysPssPurchaseStockOrderProductR;
import com.lichkin.springframework.entities.impl.SysActivitiFormDataEntity;
import com.lichkin.springframework.entities.impl.SysPssPurchaseStockOrderEntity;
import com.lichkin.springframework.entities.impl.SysPssPurchaseStockOrderProductEntity;
import com.lichkin.springframework.services.LKDBService;

@Service(PssStatics.PSS_PURCHASE_STOCK_IN_ORDER)
public class ActivitiCallbackService_PSS_PURCHASE_STOCK_IN_ORDER extends LKDBService implements ActivitiCallbackService, WithoutActivitiCallbackService<SysPssPurchaseStockOrderEntity> {

	@Autowired
	private SysPssStockBusService stockBusService;

	@Autowired
	private SysPssPurchaseOrderBusService purchaseOrderBusService;


	@Override
	public void directFinish(SysPssPurchaseStockOrderEntity processEntity, String compId, String loginId) {
		QuerySQL sql = new QuerySQL(false, SysPssPurchaseStockOrderProductEntity.class);
		sql.eq(SysPssPurchaseStockOrderProductR.orderId, processEntity.getId());
		List<SysPssPurchaseStockOrderProductEntity> stockOrderProductList = dao.getList(sql, SysPssPurchaseStockOrderProductEntity.class);
		stockBusService.changeStockQuantity(processEntity, stockOrderProductList);
		purchaseOrderBusService.changePurchaseOrderProductInventoryQuantity(processEntity, stockOrderProductList);
	}


	@Override
	public void approve(SysActivitiFormDataEntity formDataEntity, byte step) {
	}


	@Override
	public void finish(SysActivitiFormDataEntity formDataEntity) {
		directFinish(dao.findOneById(SysPssPurchaseStockOrderEntity.class, formDataEntity.getField1()), formDataEntity.getCompId(), formDataEntity.getApproverLoginId());
	}


	@Override
	public void reject(SysActivitiFormDataEntity formDataEntity) {
	}

}
