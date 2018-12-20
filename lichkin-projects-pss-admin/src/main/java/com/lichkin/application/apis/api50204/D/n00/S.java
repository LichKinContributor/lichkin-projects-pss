package com.lichkin.application.apis.api50204.D.n00;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lichkin.application.services.bus.impl.SysPssSellReturnNotStockOutOrderBusService;
import com.lichkin.framework.db.beans.SysPssSellReturnNotStockOutOrderR;
import com.lichkin.framework.defines.enums.impl.ApprovalStatusEnum;
import com.lichkin.framework.defines.enums.impl.LKErrorCodesEnum;
import com.lichkin.framework.defines.exceptions.LKRuntimeException;
import com.lichkin.springframework.controllers.ApiKeyValues;
import com.lichkin.springframework.entities.impl.SysPssSellReturnNotStockOutOrderEntity;
import com.lichkin.springframework.services.LKApiBusDeleteService;

@Service("SysPssSellReturnNotStockOutOrderD00Service")
public class S extends LKApiBusDeleteService<I, SysPssSellReturnNotStockOutOrderEntity> {

	@Autowired
	private SysPssSellReturnNotStockOutOrderBusService busService;


	@Override
	protected int getIdColumnResId() {
		return SysPssSellReturnNotStockOutOrderR.id;
	}


	@Override
	protected void beforeRealDelete(I sin, ApiKeyValues<I> params, SysPssSellReturnNotStockOutOrderEntity entity, String id) {
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
