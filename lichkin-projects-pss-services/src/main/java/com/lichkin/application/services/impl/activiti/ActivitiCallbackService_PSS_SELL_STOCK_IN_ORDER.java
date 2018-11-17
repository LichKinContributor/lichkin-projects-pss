package com.lichkin.application.services.impl.activiti;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lichkin.application.services.ActivitiCallbackService;
import com.lichkin.application.services.WithoutActivitiCallbackService;
import com.lichkin.application.services.bus.impl.SysPssSellOrderBusService;
import com.lichkin.application.services.bus.impl.SysPssStockBusService;
import com.lichkin.defines.PssStatics;
import com.lichkin.framework.db.beans.QuerySQL;
import com.lichkin.framework.db.beans.SysPssSellStockOrderProductR;
import com.lichkin.springframework.entities.impl.SysActivitiFormDataEntity;
import com.lichkin.springframework.entities.impl.SysPssSellStockOrderEntity;
import com.lichkin.springframework.entities.impl.SysPssSellStockOrderProductEntity;
import com.lichkin.springframework.entities.suppers.PssOrderProductEntity;
import com.lichkin.springframework.services.LKDBService;

@Service(PssStatics.PSS_SELL_STOCK_IN_ORDER)
public class ActivitiCallbackService_PSS_SELL_STOCK_IN_ORDER extends LKDBService implements ActivitiCallbackService, WithoutActivitiCallbackService<SysPssSellStockOrderEntity> {

	@Autowired
	private SysPssStockBusService stockBusService;

	@Autowired
	private SysPssSellOrderBusService sellOrderBusService;


	@Override
	public void directFinish(SysPssSellStockOrderEntity processEntity, String compId, String loginId) {
		QuerySQL sql = new QuerySQL(false, SysPssSellStockOrderProductEntity.class);
		sql.eq(SysPssSellStockOrderProductR.orderId, processEntity.getId());
		List<PssOrderProductEntity> orderProductList = dao.getList(sql, PssOrderProductEntity.class);
		stockBusService.changeStockQuantity(processEntity, orderProductList);
		sellOrderBusService.changeSellOrderProductInventoryQuantity(processEntity.getOrderId(), orderProductList);
	}


	@Override
	public void approve(SysActivitiFormDataEntity formDataEntity, byte step) {
	}


	@Override
	public void finish(SysActivitiFormDataEntity formDataEntity) {
		directFinish(dao.findOneById(SysPssSellStockOrderEntity.class, formDataEntity.getField1()), formDataEntity.getCompId(), formDataEntity.getApproverLoginId());
	}


	@Override
	public void reject(SysActivitiFormDataEntity formDataEntity) {
	}

}
