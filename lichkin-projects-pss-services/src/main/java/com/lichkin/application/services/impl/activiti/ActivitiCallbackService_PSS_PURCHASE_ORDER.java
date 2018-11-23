package com.lichkin.application.services.impl.activiti;

import org.springframework.stereotype.Service;

import com.lichkin.application.services.ActivitiCallbackService;
import com.lichkin.application.services.WithoutActivitiCallbackService;
import com.lichkin.defines.PssStatics;
import com.lichkin.framework.defines.enums.impl.ApprovalStatusEnum;
import com.lichkin.framework.utils.LKDateTimeUtils;
import com.lichkin.springframework.entities.impl.SysActivitiFormDataEntity;
import com.lichkin.springframework.entities.impl.SysPssPurchaseOrderEntity;
import com.lichkin.springframework.services.LKDBService;

@Service(PssStatics.PSS_PURCHASE_ORDER)
public class ActivitiCallbackService_PSS_PURCHASE_ORDER extends LKDBService implements ActivitiCallbackService, WithoutActivitiCallbackService<SysPssPurchaseOrderEntity> {

	@Override
	public void directFinish(SysPssPurchaseOrderEntity processEntity, String compId, String loginId) {
	}


	@Override
	public void approve(SysActivitiFormDataEntity formDataEntity, byte step) {
	}


	@Override
	public void finish(SysActivitiFormDataEntity formDataEntity) {
		SysPssPurchaseOrderEntity processEntity = dao.findOneById(SysPssPurchaseOrderEntity.class, formDataEntity.getField1());
		processEntity.setApprovalStatus(ApprovalStatusEnum.APPROVED);
		processEntity.setApprovalTime(LKDateTimeUtils.now());
		dao.mergeOne(processEntity);
	}


	@Override
	public void reject(SysActivitiFormDataEntity formDataEntity) {
		SysPssPurchaseOrderEntity processEntity = dao.findOneById(SysPssPurchaseOrderEntity.class, formDataEntity.getField1());
		processEntity.setApprovalStatus(ApprovalStatusEnum.REJECT);
		processEntity.setApprovalTime(LKDateTimeUtils.now());
		dao.mergeOne(processEntity);
	}

}
