package com.lichkin.application.apis.api50400.D.n00;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lichkin.application.services.bus.impl.SysPssAllotOrderBusService;
import com.lichkin.framework.db.beans.SysPssAllotOrderR;
import com.lichkin.framework.defines.enums.impl.ApprovalStatusEnum;
import com.lichkin.framework.defines.enums.impl.LKErrorCodesEnum;
import com.lichkin.framework.defines.exceptions.LKRuntimeException;
import com.lichkin.springframework.controllers.ApiKeyValues;
import com.lichkin.springframework.entities.impl.SysPssAllotOrderEntity;
import com.lichkin.springframework.services.LKApiBusDeleteService;

@Service("SysPssAllotOrderD00Service")
public class S extends LKApiBusDeleteService<I, SysPssAllotOrderEntity> {

	@Autowired
	private SysPssAllotOrderBusService busService;


	@Override
	protected int getIdColumnResId() {
		return SysPssAllotOrderR.id;
	}


	@Override
	protected boolean realDelete(I sin, ApiKeyValues<I> params) {
		return true;
	}


	@Override
	protected void beforeRealDelete(I sin, ApiKeyValues<I> params, SysPssAllotOrderEntity entity, String id) {
		ApprovalStatusEnum approvalStatus = entity.getApprovalStatus();
		switch (approvalStatus) {
			case PENDING:
				busService.clearPssAllotOrderProduct(id);
			break;
			default:
				throw new LKRuntimeException(LKErrorCodesEnum.PARAM_ERROR);
		}
	}

}
