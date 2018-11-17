package com.lichkin.application.services.impl.activiti;

import org.springframework.stereotype.Service;

import com.lichkin.application.services.ActivitiCallbackService;
import com.lichkin.application.services.WithoutActivitiCallbackService;
import com.lichkin.defines.PssStatics;
import com.lichkin.springframework.entities.impl.SysActivitiFormDataEntity;
import com.lichkin.springframework.entities.impl.SysPssSellOrderEntity;
import com.lichkin.springframework.services.LKDBService;

@Service(PssStatics.PSS_SELL_RETURN_ORDER)
public class ActivitiCallbackService_PSS_SELL_RETURN_ORDER extends LKDBService implements ActivitiCallbackService, WithoutActivitiCallbackService<SysPssSellOrderEntity> {

	@Override
	public void directFinish(SysPssSellOrderEntity processEntity, String compId, String loginId) {
	}


	@Override
	public void approve(SysActivitiFormDataEntity formDataEntity, byte step) {
	}


	@Override
	public void finish(SysActivitiFormDataEntity formDataEntity) {
	}


	@Override
	public void reject(SysActivitiFormDataEntity formDataEntity) {
	}

}
