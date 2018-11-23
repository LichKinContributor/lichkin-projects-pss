package com.lichkin.application.services.impl.activiti;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lichkin.application.services.ActivitiCallbackService;
import com.lichkin.application.services.WithoutActivitiCallbackService;
import com.lichkin.application.services.bus.impl.SysPssStockBusService;
import com.lichkin.defines.PssStatics;
import com.lichkin.framework.db.beans.QuerySQL;
import com.lichkin.framework.db.beans.SysPssAllotOrderProductR;
import com.lichkin.framework.defines.enums.impl.ApprovalStatusEnum;
import com.lichkin.framework.utils.LKDateTimeUtils;
import com.lichkin.springframework.entities.impl.SysActivitiFormDataEntity;
import com.lichkin.springframework.entities.impl.SysPssAllotOrderEntity;
import com.lichkin.springframework.entities.impl.SysPssAllotOrderProductEntity;
import com.lichkin.springframework.entities.suppers.PssOrderProductEntity;
import com.lichkin.springframework.services.LKDBService;

@Service(PssStatics.PSS_ALLOT_ORDER)
public class ActivitiCallbackService_PSS_ALLOT_ORDER extends LKDBService implements ActivitiCallbackService, WithoutActivitiCallbackService<SysPssAllotOrderEntity> {

	@Autowired
	private SysPssStockBusService stockBusService;


	@Override
	public void directFinish(SysPssAllotOrderEntity processEntity, String compId, String loginId) {
		QuerySQL sql = new QuerySQL(false, SysPssAllotOrderProductEntity.class);
		sql.eq(SysPssAllotOrderProductR.orderId, processEntity.getId());
		List<PssOrderProductEntity> orderProductList = dao.getList(sql, PssOrderProductEntity.class);
		stockBusService.changeStockQuantityByAllotOrder(processEntity, orderProductList);
	}


	@Override
	public void approve(SysActivitiFormDataEntity formDataEntity, byte step) {
	}


	@Override
	public void finish(SysActivitiFormDataEntity formDataEntity) {
		SysPssAllotOrderEntity processEntity = dao.findOneById(SysPssAllotOrderEntity.class, formDataEntity.getField1());
		processEntity.setApprovalStatus(ApprovalStatusEnum.APPROVED);
		processEntity.setApprovalTime(LKDateTimeUtils.now());
		dao.mergeOne(processEntity);

		directFinish(processEntity, formDataEntity.getCompId(), formDataEntity.getApproverLoginId());
	}


	@Override
	public void reject(SysActivitiFormDataEntity formDataEntity) {
		SysPssAllotOrderEntity processEntity = dao.findOneById(SysPssAllotOrderEntity.class, formDataEntity.getField1());
		processEntity.setApprovalStatus(ApprovalStatusEnum.REJECT);
		processEntity.setApprovalTime(LKDateTimeUtils.now());
		dao.mergeOne(processEntity);
	}

}
