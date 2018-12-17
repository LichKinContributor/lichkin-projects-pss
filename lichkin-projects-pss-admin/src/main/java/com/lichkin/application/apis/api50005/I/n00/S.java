package com.lichkin.application.apis.api50005.I.n00;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lichkin.application.services.bus.impl.SysPssStoreCashierBusService;
import com.lichkin.framework.defines.enums.LKCodeEnum;
import com.lichkin.springframework.controllers.ApiKeyValues;
import com.lichkin.springframework.entities.impl.SysPssStoreCashierEntity;
import com.lichkin.springframework.services.LKApiBusInsertService;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Service("SysPssStoreCashierI00Service")
public class S extends LKApiBusInsertService<I, SysPssStoreCashierEntity> {

	@Autowired
	private SysPssStoreCashierBusService busService;


	@Getter
	@RequiredArgsConstructor
	enum ErrorCodes implements LKCodeEnum {

		SysPssStoreCashier_EXIST(60000),

		;

		private final Integer code;

	}


	@Override
	protected List<SysPssStoreCashierEntity> findExist(I sin, ApiKeyValues<I> params) {
		return busService.findExist(params, sin.getCashier());
	}


	@Override
	protected boolean forceCheck(I sin, ApiKeyValues<I> params) {
		return true;
	}


	@Override
	protected LKCodeEnum existErrorCode(I sin, ApiKeyValues<I> params) {
		return ErrorCodes.SysPssStoreCashier_EXIST;
	}

}
