package com.lichkin.application.apis.api50004.U.n00;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lichkin.application.services.bus.impl.SysPssStoreBusService;
import com.lichkin.framework.defines.enums.LKCodeEnum;
import com.lichkin.springframework.controllers.ApiKeyValues;
import com.lichkin.springframework.entities.impl.SysPssStoreEntity;
import com.lichkin.springframework.services.LKApiBusUpdateService;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Service("SysPssStoreU00Service")
public class S extends LKApiBusUpdateService<I, SysPssStoreEntity> {

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
	protected boolean needCheckExist(I sin, ApiKeyValues<I> params, SysPssStoreEntity entity, String id) {
		String storeCodeSaved = entity.getStoreCode();
		String storeCodeIn = sin.getStoreCode();
		if (((storeCodeSaved == null) && (storeCodeIn != null)) || ((storeCodeSaved != null) && ((storeCodeIn == null) || !storeCodeSaved.equals(storeCodeIn)))) {
			return true;
		}
		if (!entity.getStoreName().equals(sin.getStoreName())) {
			return true;
		}
		return false;
	}


	@Override
	protected List<SysPssStoreEntity> findExist(I sin, ApiKeyValues<I> params, SysPssStoreEntity entity, String id) {
		return busService.findExist(params, sin.getStoreCode(), sin.getStoreName());
	}


	@Override
	protected LKCodeEnum existErrorCode(I sin, ApiKeyValues<I> params) {
		return ErrorCodes.SysPssStore_EXIST;
	}

}
