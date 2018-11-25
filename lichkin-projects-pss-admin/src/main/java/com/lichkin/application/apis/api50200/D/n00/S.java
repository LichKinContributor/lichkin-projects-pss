package com.lichkin.application.apis.api50200.D.n00;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lichkin.application.services.bus.impl.SysPssSellOrderBusService;
import com.lichkin.framework.db.beans.SysPssSellOrderR;
import com.lichkin.framework.defines.enums.impl.ApprovalStatusEnum;
import com.lichkin.framework.defines.enums.impl.LKErrorCodesEnum;
import com.lichkin.framework.defines.exceptions.LKRuntimeException;
import com.lichkin.springframework.controllers.ApiKeyValues;
import com.lichkin.springframework.entities.impl.SysPssSellOrderEntity;
import com.lichkin.springframework.services.LKApiBusDeleteService;

@Service("SysPssSellOrderD00Service")
public class S extends LKApiBusDeleteService<I, SysPssSellOrderEntity> {

	@Autowired
	private SysPssSellOrderBusService busService;


	@Override
	protected int getIdColumnResId() {
		return SysPssSellOrderR.id;
	}


	@Override
	protected boolean realDelete(I sin, ApiKeyValues<I> params) {
		return true;
	}


	@Override
	protected void beforeRealDelete(I sin, ApiKeyValues<I> params, SysPssSellOrderEntity entity, String id) {
		ApprovalStatusEnum approvalStatus = entity.getApprovalStatus();
		switch (approvalStatus) {
			case PENDING:
				busService.clearPssSellOrderProduct(id);
			break;
			default:
				throw new LKRuntimeException(LKErrorCodesEnum.PARAM_ERROR);
		}
	}

}
