package com.lichkin.application.services.impl.activiti;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lichkin.application.services.ActivitiCallbackService;
import com.lichkin.application.services.WithoutActivitiCallbackService;
import com.lichkin.application.services.bus.impl.SysPssStockBusService;
import com.lichkin.defines.PssStatics;
import com.lichkin.framework.db.beans.QuerySQL;
import com.lichkin.framework.db.beans.SysPssOtherStockOrderProductR;
import com.lichkin.springframework.entities.impl.SysActivitiFormDataEntity;
import com.lichkin.springframework.entities.impl.SysPssOtherStockOrderEntity;
import com.lichkin.springframework.entities.impl.SysPssOtherStockOrderProductEntity;
import com.lichkin.springframework.entities.suppers.PssOrderProductEntity;
import com.lichkin.springframework.services.LKDBService;

@Service(PssStatics.PSS_OTHER_STOCK_IN_ORDER)
public class ActivitiCallbackService_PSS_OTHER_STOCK_IN_ORDER extends LKDBService implements ActivitiCallbackService, WithoutActivitiCallbackService<SysPssOtherStockOrderEntity> {

	@Autowired
	private SysPssStockBusService stockBusService;


	@Override
	public void directFinish(SysPssOtherStockOrderEntity processEntity, String compId, String loginId) {
		QuerySQL sql = new QuerySQL(false, SysPssOtherStockOrderProductEntity.class);
		sql.eq(SysPssOtherStockOrderProductR.orderId, processEntity.getId());
		List<PssOrderProductEntity> orderProductList = dao.getList(sql, PssOrderProductEntity.class);
		stockBusService.changeStockQuantity(processEntity, orderProductList);
	}


	@Override
	public void approve(SysActivitiFormDataEntity formDataEntity, byte step) {
	}


	@Override
	public void finish(SysActivitiFormDataEntity formDataEntity) {
		directFinish(dao.findOneById(SysPssOtherStockOrderEntity.class, formDataEntity.getField1()), formDataEntity.getCompId(), formDataEntity.getApproverLoginId());
	}


	@Override
	public void reject(SysActivitiFormDataEntity formDataEntity) {
	}

}
