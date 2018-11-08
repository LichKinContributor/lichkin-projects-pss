package com.lichkin.application.apis.api50400.I.n00;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lichkin.application.services.bus.impl.SysPssAllotOrderBusService;
import com.lichkin.framework.defines.enums.LKCodeEnum;
import com.lichkin.framework.defines.enums.impl.ApprovalStatusEnum;
import com.lichkin.framework.defines.exceptions.LKRuntimeException;
import com.lichkin.springframework.entities.impl.SysPssAllotOrderEntity;
import com.lichkin.springframework.services.LKApiBusInsertWithoutCheckerService;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Service("SysPssAllotOrderI00Service")
public class S extends LKApiBusInsertWithoutCheckerService<I, SysPssAllotOrderEntity> {

	@Autowired
	private SysPssAllotOrderBusService busService;


	@Getter
	@RequiredArgsConstructor
	enum ErrorCodes implements LKCodeEnum {

		PSS_OUT_STORAGE_CAN_NOT_IS_IN_STORAGE(60000),

		;

		private final Integer code;

	}


	@Override
	protected void beforeAddNew(I sin, String locale, String compId, String loginId, SysPssAllotOrderEntity entity) {
		if (entity.getOutStorageId().equals(entity.getInStorageId())) {
			throw new LKRuntimeException(ErrorCodes.PSS_OUT_STORAGE_CAN_NOT_IS_IN_STORAGE);
		}
		entity.setCompId(getCompId(compId, sin.getCompId()));
		entity.setOrderNo(busService.analysisOrderNo());
	}


	@Override
	protected void beforeSaveMain(I sin, String locale, String compId, String loginId, SysPssAllotOrderEntity entity) {
		entity.setApprovalStatus(ApprovalStatusEnum.PENDING);
	}


	@Override
	protected void addSubs(I sin, String locale, String compId, String loginId, SysPssAllotOrderEntity entity, String id) {
		busService.addPssAllotOrderProduct(id, sin.getProductList());
	}

}
