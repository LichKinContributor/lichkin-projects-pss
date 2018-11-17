package com.lichkin.application.services.impl.activiti;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lichkin.application.services.ActivitiCallbackService;
import com.lichkin.application.services.WithoutActivitiCallbackService;
import com.lichkin.application.services.bus.impl.SysPssStockBusService;
import com.lichkin.defines.PssStatics;
import com.lichkin.framework.db.beans.QuerySQL;
import com.lichkin.framework.db.beans.SysPssStockCheckOrderProductR;
import com.lichkin.springframework.entities.impl.SysActivitiFormDataEntity;
import com.lichkin.springframework.entities.impl.SysPssStockCheckOrderEntity;
import com.lichkin.springframework.entities.impl.SysPssStockCheckOrderProductEntity;
import com.lichkin.springframework.services.LKDBService;

@Service(PssStatics.PSS_STOCK_CHECK_ORDER)
public class ActivitiCallbackService_PSS_STOCK_CHECK_ORDER extends LKDBService implements ActivitiCallbackService, WithoutActivitiCallbackService<SysPssStockCheckOrderEntity> {

	@Autowired
	private SysPssStockBusService stockBusService;


	@Override
	public void directFinish(SysPssStockCheckOrderEntity processEntity, String compId, String loginId) {
		QuerySQL sql = new QuerySQL(false, SysPssStockCheckOrderProductEntity.class);
		sql.eq(SysPssStockCheckOrderProductR.orderId, processEntity.getId());
		List<SysPssStockCheckOrderProductEntity> orderProductList = dao.getList(sql, SysPssStockCheckOrderProductEntity.class);
		stockBusService.changeStockQuantityByCheckOrder(processEntity, orderProductList);
	}


	@Override
	public void approve(SysActivitiFormDataEntity formDataEntity, byte step) {
	}


	@Override
	public void finish(SysActivitiFormDataEntity formDataEntity) {
		directFinish(dao.findOneById(SysPssStockCheckOrderEntity.class, formDataEntity.getField1()), formDataEntity.getCompId(), formDataEntity.getApproverLoginId());
	}


	@Override
	public void reject(SysActivitiFormDataEntity formDataEntity) {
	}

}
