package com.lichkin.application.apis.api50102.D.n00;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lichkin.application.services.bus.impl.SysPssPurchaseStockOrderBusService;
import com.lichkin.framework.db.beans.SysPssPurchaseStockOrderR;
import com.lichkin.framework.defines.enums.impl.ApprovalStatusEnum;
import com.lichkin.framework.defines.enums.impl.LKErrorCodesEnum;
import com.lichkin.framework.defines.exceptions.LKRuntimeException;
import com.lichkin.springframework.entities.impl.SysPssPurchaseStockOrderEntity;
import com.lichkin.springframework.services.LKApiBusDeleteService;

@Service("SysPssPurchaseStockOrderD00Service")
public class S extends LKApiBusDeleteService<I, SysPssPurchaseStockOrderEntity> {

	@Override
	protected int getIdColumnResId() {
		return SysPssPurchaseStockOrderR.id;
	}


	@Override
	protected boolean realDelete(I sin, String locale, String compId, String loginId) {
		return true;
	}


	@Autowired
	private SysPssPurchaseStockOrderBusService busService;


	@Override
	protected void beforeRealDelete(I sin, String locale, String compId, String loginId, SysPssPurchaseStockOrderEntity entity, String id) {
		ApprovalStatusEnum approvalStatus = entity.getApprovalStatus();
		switch (approvalStatus) {
			case PENDING:
				busService.clearPssPurchaseStockOrderProduct(id);
			break;
			default:
				throw new LKRuntimeException(LKErrorCodesEnum.PARAM_ERROR);
		}
	}

}
