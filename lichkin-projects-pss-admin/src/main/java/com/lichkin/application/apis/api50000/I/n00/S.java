package com.lichkin.application.apis.api50000.I.n00;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lichkin.application.services.bus.impl.SysPssStorageBusService;
import com.lichkin.framework.defines.enums.LKCodeEnum;
import com.lichkin.springframework.controllers.ApiKeyValues;
import com.lichkin.springframework.entities.impl.SysPssStorageEntity;
import com.lichkin.springframework.services.LKApiBusInsertService;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Service("SysPssStorageI00Service")
public class S extends LKApiBusInsertService<I, SysPssStorageEntity> {

	@Autowired
	private SysPssStorageBusService busService;


	@Getter
	@RequiredArgsConstructor
	enum ErrorCodes implements LKCodeEnum {

		SysPssStorage_EXIST(60000),

		;

		private final Integer code;

	}


	@Override
	protected List<SysPssStorageEntity> findExist(I sin, ApiKeyValues<I> params) {
		return busService.findExist(params, sin.getStorageCode(), sin.getStorageName());
	}


	@Override
	protected boolean forceCheck(I sin, ApiKeyValues<I> params) {
		return false;
	}


	@Override
	protected LKCodeEnum existErrorCode(I sin, ApiKeyValues<I> params) {
		return ErrorCodes.SysPssStorage_EXIST;
	}

}
