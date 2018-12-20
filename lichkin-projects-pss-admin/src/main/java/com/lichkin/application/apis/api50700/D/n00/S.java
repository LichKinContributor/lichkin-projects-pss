package com.lichkin.application.apis.api50700.D.n00;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lichkin.application.services.bus.impl.SysPssPurchaseReturnStockInOrderBusService;
import com.lichkin.framework.db.beans.SysPssPurchaseReturnStockInOrderR;
import com.lichkin.framework.defines.enums.impl.ApprovalStatusEnum;
import com.lichkin.framework.defines.enums.impl.LKErrorCodesEnum;
import com.lichkin.framework.defines.exceptions.LKRuntimeException;
import com.lichkin.springframework.controllers.ApiKeyValues;
import com.lichkin.springframework.entities.impl.SysPssPurchaseReturnStockInOrderEntity;
import com.lichkin.springframework.services.LKApiBusDeleteService;

@Service("SysPssPurchaseReturnStockInOrderD00Service")
public class S extends LKApiBusDeleteService<I, SysPssPurchaseReturnStockInOrderEntity> {

	@Override
	protected int getIdColumnResId() {
		return SysPssPurchaseReturnStockInOrderR.id;
	}


	@Autowired
	private SysPssPurchaseReturnStockInOrderBusService busService;


	@Override
	protected void beforeRealDelete(I sin, ApiKeyValues<I> params, SysPssPurchaseReturnStockInOrderEntity entity, String id) {
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
