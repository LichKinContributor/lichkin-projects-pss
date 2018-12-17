package com.lichkin.application.apis.api50202.D.n00;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lichkin.application.services.bus.impl.SysPssSellStockOrderBusService;
import com.lichkin.framework.beans.impl.LKRequestIDsBean;
import com.lichkin.framework.db.beans.SysPssSellStockOrderR;
import com.lichkin.framework.defines.enums.impl.ApprovalStatusEnum;
import com.lichkin.framework.defines.enums.impl.LKErrorCodesEnum;
import com.lichkin.framework.defines.exceptions.LKRuntimeException;
import com.lichkin.springframework.controllers.ApiKeyValues;
import com.lichkin.springframework.entities.impl.SysPssSellStockOrderEntity;
import com.lichkin.springframework.services.LKApiBusDeleteService;

@Service("SysPssSellStockOrderD00Service")
public class S extends LKApiBusDeleteService<LKRequestIDsBean, SysPssSellStockOrderEntity> {

	@Autowired
	private SysPssSellStockOrderBusService busService;


	@Override
	protected int getIdColumnResId() {
		return SysPssSellStockOrderR.id;
	}


	@Override
	protected void beforeRealDelete(LKRequestIDsBean sin, ApiKeyValues<LKRequestIDsBean> params, SysPssSellStockOrderEntity entity, String id) {
		ApprovalStatusEnum approvalStatus = entity.getApprovalStatus();
		switch (approvalStatus) {
			case PENDING:
				busService.clearPssSellStockOrderProduct(id);
			break;
			default:
				throw new LKRuntimeException(LKErrorCodesEnum.PARAM_ERROR);
		}
	}

}
