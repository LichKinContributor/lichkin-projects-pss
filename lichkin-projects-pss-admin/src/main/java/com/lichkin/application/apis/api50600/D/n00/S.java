package com.lichkin.application.apis.api50600.D.n00;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lichkin.application.services.bus.impl.SysPssPurchaseReturnNotStockInOrderBusService;
import com.lichkin.framework.db.beans.SysPssPurchaseReturnNotStockInOrderR;
import com.lichkin.framework.defines.enums.impl.ApprovalStatusEnum;
import com.lichkin.framework.defines.enums.impl.LKErrorCodesEnum;
import com.lichkin.framework.defines.exceptions.LKRuntimeException;
import com.lichkin.springframework.controllers.ApiKeyValues;
import com.lichkin.springframework.entities.impl.SysPssPurchaseReturnNotStockInOrderEntity;
import com.lichkin.springframework.services.LKApiBusDeleteService;

@Service("SysPssPurchaseReturnNotStockInOrderD00Service")
public class S extends LKApiBusDeleteService<I, SysPssPurchaseReturnNotStockInOrderEntity> {

	@Override
	protected int getIdColumnResId() {
		return SysPssPurchaseReturnNotStockInOrderR.id;
	}


	@Autowired
	private SysPssPurchaseReturnNotStockInOrderBusService busService;


	@Override
	protected void beforeRealDelete(I sin, ApiKeyValues<I> params, SysPssPurchaseReturnNotStockInOrderEntity entity, String id) {
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
