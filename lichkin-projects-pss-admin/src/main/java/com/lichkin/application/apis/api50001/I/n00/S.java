package com.lichkin.application.apis.api50001.I.n00;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lichkin.application.services.bus.impl.SysPssSupplierBusService;
import com.lichkin.framework.defines.enums.LKCodeEnum;
import com.lichkin.framework.defines.enums.impl.LKUsingStatusEnum;
import com.lichkin.springframework.controllers.ApiKeyValues;
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
	protected List<SysPssSupplierEntity> findExist(I sin, ApiKeyValues<I> params) {
		return busService.findExist(null, params, sin.getSupplierCode(), sin.getSupplierName());
	}


	@Override
	protected boolean forceCheck(I sin, ApiKeyValues<I> params) {
		return false;
	}


	@Override
	protected LKCodeEnum existErrorCode(I sin, ApiKeyValues<I> params) {
		return ErrorCodes.SysPssSupplier_EXIST;
	}


	@Override
	protected void beforeRestore(I sin, ApiKeyValues<I> params, SysPssSupplierEntity entity, SysPssSupplierEntity exist) {
		entity.setUsingStatus(LKUsingStatusEnum.USING);
		entity.setCompId(exist.getCompId());
	}


	@Override
	protected void beforeAddNew(I sin, ApiKeyValues<I> params, SysPssSupplierEntity entity) {
		entity.setCompId(params.getCompId(true));
	}

}
