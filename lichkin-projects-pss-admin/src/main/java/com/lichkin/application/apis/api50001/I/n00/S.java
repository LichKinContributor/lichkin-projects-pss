package com.lichkin.application.apis.api50001.I.n00;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lichkin.application.services.bus.impl.SysPssSupplierBusService;
import com.lichkin.framework.defines.enums.LKCodeEnum;
import com.lichkin.framework.defines.enums.impl.LKUsingStatusEnum;
import com.lichkin.springframework.entities.impl.SysPssSupplierEntity;
import com.lichkin.springframework.services.LKApiBusInsertService;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Service("SysPssSupplierI00Service")
public class S extends LKApiBusInsertService<I, SysPssSupplierEntity> {

	@Autowired
	private SysPssSupplierBusService busService;


	@Getter
	@RequiredArgsConstructor
	enum ErrorCodes implements LKCodeEnum {

		SysPssSupplier_EXIST(60000),

		;

		private final Integer code;

	}


	@Override
	protected List<SysPssSupplierEntity> findExist(I sin, String locale, String compId, String loginId) {
		return busService.findExist(null, compId, sin.getCompId(), sin.getSupplierCode(), sin.getSupplierName());
	}


	@Override
	protected boolean forceCheck(I sin, String locale, String compId, String loginId) {
		return false;
	}


	@Override
	protected LKCodeEnum existErrorCode(I sin, String locale, String compId, String loginId) {
		return ErrorCodes.SysPssSupplier_EXIST;
	}


	@Override
	protected void beforeRestore(I sin, String locale, String compId, String loginId, SysPssSupplierEntity entity, SysPssSupplierEntity exist) {
		entity.setUsingStatus(LKUsingStatusEnum.USING);
		entity.setCompId(exist.getCompId());
	}


	@Override
	protected void beforeAddNew(I sin, String locale, String compId, String loginId, SysPssSupplierEntity entity) {
		entity.setCompId(getCompId(compId, sin.getCompId()));
	}

}
