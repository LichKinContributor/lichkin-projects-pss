package com.lichkin.application.apis.api50100.D.n00;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lichkin.application.services.bus.impl.SysPssPurchaseOrderBusService;
import com.lichkin.framework.beans.impl.LKRequestIDsBean;
import com.lichkin.framework.db.beans.SysPssPurchaseOrderR;
import com.lichkin.framework.defines.enums.impl.ApprovalStatusEnum;
import com.lichkin.framework.defines.enums.impl.LKErrorCodesEnum;
import com.lichkin.framework.defines.exceptions.LKRuntimeException;
import com.lichkin.springframework.controllers.ApiKeyValues;
import com.lichkin.springframework.entities.impl.SysPssPurchaseOrderEntity;
import com.lichkin.springframework.services.LKApiBusDeleteService;

@Service("SysPssPurchaseOrderD00Service")
public class S extends LKApiBusDeleteService<LKRequestIDsBean, SysPssPurchaseOrderEntity> {

	@Override
	protected int getIdColumnResId() {
		return SysPssPurchaseOrderR.id;
	}


	@Autowired
	private SysPssPurchaseOrderBusService busService;


	@Override
	protected void beforeRealDelete(LKRequestIDsBean sin, ApiKeyValues<LKRequestIDsBean> params, SysPssPurchaseOrderEntity entity, String id) {
		ApprovalStatusEnum approvalStatus = entity.getApprovalStatus();
		switch (approvalStatus) {
			case PENDING:
				busService.clearPssPurchaseOrderProduct(id);
			break;
			default:
				throw new LKRuntimeException(LKErrorCodesEnum.PARAM_ERROR);
		}
	}

}
