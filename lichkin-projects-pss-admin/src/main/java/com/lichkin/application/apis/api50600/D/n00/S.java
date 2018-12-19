package com.lichkin.application.apis.api50600.D.n00;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lichkin.application.services.bus.impl.SysPssPurchaseReturnOrderBusService;
import com.lichkin.framework.db.beans.SysPssPurchaseReturnOrderR;
import com.lichkin.framework.defines.enums.impl.ApprovalStatusEnum;
import com.lichkin.framework.defines.enums.impl.LKErrorCodesEnum;
import com.lichkin.framework.defines.exceptions.LKRuntimeException;
import com.lichkin.springframework.controllers.ApiKeyValues;
import com.lichkin.springframework.entities.impl.SysPssPurchaseReturnOrderEntity;
import com.lichkin.springframework.services.LKApiBusDeleteService;

@Service("SysPssPurchaseReturnOrderD00Service")
public class S extends LKApiBusDeleteService<I, SysPssPurchaseReturnOrderEntity> {

	@Override
	protected int getIdColumnResId() {
		return SysPssPurchaseReturnOrderR.id;
	}


	@Autowired
	private SysPssPurchaseReturnOrderBusService busService;


	@Override
	protected void beforeRealDelete(I sin, ApiKeyValues<I> params, SysPssPurchaseReturnOrderEntity entity, String id) {
		ApprovalStatusEnum approvalStatus = entity.getApprovalStatus();
		switch (approvalStatus) {
			case PENDING:
				busService.clearPurchaseReturnOrderProduct(id);
			break;
			default:
				throw new LKRuntimeException(LKErrorCodesEnum.PARAM_ERROR);
		}
	}

}
