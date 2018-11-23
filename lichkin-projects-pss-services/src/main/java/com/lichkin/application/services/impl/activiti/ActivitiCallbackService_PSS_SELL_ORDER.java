package com.lichkin.application.services.impl.activiti;

import org.springframework.stereotype.Service;

import com.lichkin.application.services.ActivitiCallbackService;
import com.lichkin.application.services.WithoutActivitiCallbackService;
import com.lichkin.defines.PssStatics;
import com.lichkin.framework.defines.enums.impl.ApprovalStatusEnum;
import com.lichkin.framework.utils.LKDateTimeUtils;
import com.lichkin.springframework.entities.impl.SysActivitiFormDataEntity;
import com.lichkin.springframework.entities.impl.SysPssSellOrderEntity;
import com.lichkin.springframework.services.LKDBService;

@Service(PssStatics.PSS_SELL_ORDER)
public class ActivitiCallbackService_PSS_SELL_ORDER extends LKDBService implements ActivitiCallbackService, WithoutActivitiCallbackService<SysPssSellOrderEntity> {

	@Override
	public void directFinish(SysPssSellOrderEntity processEntity, String compId, String loginId) {
	}


	@Override
	public void approve(SysActivitiFormDataEntity formDataEntity, byte step) {
	}


	@Override
	public void finish(SysActivitiFormDataEntity formDataEntity) {
		SysPssSellOrderEntity processEntity = dao.findOneById(SysPssSellOrderEntity.class, formDataEntity.getField1());
		processEntity.setApprovalStatus(ApprovalStatusEnum.APPROVED);
		processEntity.setApprovalTime(LKDateTimeUtils.now());
		dao.mergeOne(processEntity);
	}


	@Override
	public void reject(SysActivitiFormDataEntity formDataEntity) {
		SysPssSellOrderEntity processEntity = dao.findOneById(SysPssSellOrderEntity.class, formDataEntity.getField1());
		processEntity.setApprovalStatus(ApprovalStatusEnum.REJECT);
		processEntity.setApprovalTime(LKDateTimeUtils.now());
		dao.mergeOne(processEntity);
	}

}
