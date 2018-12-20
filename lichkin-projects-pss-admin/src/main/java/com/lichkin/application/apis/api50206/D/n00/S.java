package com.lichkin.application.apis.api50206.D.n00;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lichkin.application.services.bus.impl.SysPssSellReturnStockOutOrderBusService;
import com.lichkin.framework.db.beans.SysPssSellReturnStockOutOrderR;
import com.lichkin.framework.defines.enums.impl.ApprovalStatusEnum;
import com.lichkin.framework.defines.enums.impl.LKErrorCodesEnum;
import com.lichkin.framework.defines.exceptions.LKRuntimeException;
import com.lichkin.springframework.controllers.ApiKeyValues;
import com.lichkin.springframework.entities.impl.SysPssSellReturnStockOutOrderEntity;
import com.lichkin.springframework.services.LKApiBusDeleteService;

@Service("SysPssSellReturnStockOutOrderD00Service")
public class S extends LKApiBusDeleteService<I, SysPssSellReturnStockOutOrderEntity> {

	@Autowired
	private SysPssSellReturnStockOutOrderBusService busService;


	@Override
	protected int getIdColumnResId() {
		return SysPssSellReturnStockOutOrderR.id;
	}


	@Override
	protected void beforeRealDelete(I sin, ApiKeyValues<I> params, SysPssSellReturnStockOutOrderEntity entity, String id) {
		ApprovalStatusEnum approvalStatus = entity.getApprovalStatus();
		switch (approvalStatus) {
			case PENDING:
				busService.clearOrderProduct(id);
			break;
			default:
				throw new LKRuntimeException(LKErrorCodesEnum.PARAM_ERROR);
		}
	}

}
