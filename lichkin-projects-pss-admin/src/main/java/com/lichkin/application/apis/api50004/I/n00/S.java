package com.lichkin.application.apis.api50004.I.n00;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lichkin.application.services.bus.impl.SysPssStoreBusService;
import com.lichkin.framework.defines.enums.LKCodeEnum;
import com.lichkin.springframework.controllers.ApiKeyValues;
import com.lichkin.springframework.entities.impl.SysPssStoreEntity;
import com.lichkin.springframework.services.LKApiBusInsertService;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Service("SysPssStoreI00Service")
public class S extends LKApiBusInsertService<I, SysPssStoreEntity> {

	@Autowired
	private SysPssStoreBusService busService;


	@Getter
	@RequiredArgsConstructor
	enum ErrorCodes implements LKCodeEnum {

		SysPssStore_EXIST(60000),

		;

		private final Integer code;

	}


	@Override
	protected List<SysPssStoreEntity> findExist(I sin, ApiKeyValues<I> params) {
		return busService.findExist(params, sin.getStoreCode(), sin.getStoreName());
	}


	@Override
	protected boolean forceCheck(I sin, ApiKeyValues<I> params) {
		return false;
	}


	@Override
	protected LKCodeEnum existErrorCode(I sin, ApiKeyValues<I> params) {
		return ErrorCodes.SysPssStore_EXIST;
	}

}
