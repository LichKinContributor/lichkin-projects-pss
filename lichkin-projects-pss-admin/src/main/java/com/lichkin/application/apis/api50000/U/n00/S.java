package com.lichkin.application.apis.api50000.U.n00;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lichkin.application.services.bus.impl.SysPssStorageBusService;
import com.lichkin.framework.defines.enums.LKCodeEnum;
import com.lichkin.springframework.entities.impl.SysPssStorageEntity;
import com.lichkin.springframework.services.LKApiBusUpdateService;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Service("SysPssStorageU00Service")
public class S extends LKApiBusUpdateService<I, SysPssStorageEntity> {

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
	protected boolean needCheckExist(I sin, String locale, String compId, String loginId, SysPssStorageEntity entity, String id) {
		String storageCodeSaved = entity.getStorageCode();
		String storageCodeIn = sin.getStorageCode();
		if (((storageCodeSaved == null) && (storageCodeIn != null)) || ((storageCodeSaved != null) && ((storageCodeIn == null) || !storageCodeSaved.equals(storageCodeIn)))) {
			return true;
		}
		if (!entity.getStorageName().equals(sin.getStorageName())) {
			return true;
		}
		return false;
	}


	@Override
	protected List<SysPssStorageEntity> findExist(I sin, String locale, String compId, String loginId, SysPssStorageEntity entity, String id) {
		return busService.findExist(id, compId, null, sin.getStorageCode(), sin.getStorageName());
	}


	@Override
	protected LKCodeEnum existErrorCode(I sin, String locale, String compId, String loginId) {
		return ErrorCodes.SysPssStorage_EXIST;
	}

}
