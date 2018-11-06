package com.lichkin.application.apis.api50000.I.n00;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lichkin.application.services.bus.impl.SysPssStorageBusService;
import com.lichkin.framework.defines.enums.LKCodeEnum;
import com.lichkin.framework.defines.enums.impl.LKUsingStatusEnum;
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
	protected List<SysPssStorageEntity> findExist(I sin, String locale, String compId, String loginId) {
		return busService.findExist(null, compId, sin.getCompId(), sin.getStorageCode(), sin.getStorageName());
	}


	@Override
	protected boolean forceCheck(I sin, String locale, String compId, String loginId) {
		return false;
	}


	@Override
	protected LKCodeEnum existErrorCode(I sin, String locale, String compId, String loginId) {
		return ErrorCodes.SysPssStorage_EXIST;
	}


	@Override
	protected void beforeRestore(I sin, String locale, String compId, String loginId, SysPssStorageEntity entity, SysPssStorageEntity exist) {
		entity.setUsingStatus(LKUsingStatusEnum.USING);
		entity.setCompId(exist.getCompId());
	}


	@Override
	protected void beforeAddNew(I sin, String locale, String compId, String loginId, SysPssStorageEntity entity) {
		entity.setCompId(getCompId(compId, sin.getCompId()));
	}

}
