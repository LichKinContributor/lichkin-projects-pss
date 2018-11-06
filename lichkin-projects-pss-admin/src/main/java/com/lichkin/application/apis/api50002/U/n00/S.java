package com.lichkin.application.apis.api50002.U.n00;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lichkin.application.services.bus.impl.SysPssProductCategoryBusService;
import com.lichkin.framework.defines.enums.LKCodeEnum;
import com.lichkin.springframework.entities.impl.SysPssProductCategoryEntity;
import com.lichkin.springframework.services.LKApiBusUpdateService;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Service("SysPssProductCategoryU00Service")
public class S extends LKApiBusUpdateService<I, SysPssProductCategoryEntity> {

	@Autowired
	private SysPssProductCategoryBusService busService;


	@Getter
	@RequiredArgsConstructor
	enum ErrorCodes implements LKCodeEnum {

		SysPssProductCategory_EXIST(60000),

		;

		private final Integer code;

	}


	@Override
	protected boolean needCheckExist(I sin, String locale, String compId, String loginId, SysPssProductCategoryEntity entity, String id) {
		if (!entity.getCategoryName().equals(sin.getCategoryName())) {
			return true;
		}
		return false;
	}


	@Override
	protected List<SysPssProductCategoryEntity> findExist(I sin, String locale, String compId, String loginId, SysPssProductCategoryEntity entity, String id) {
		return busService.findExist(id, compId, null, entity.getParentCode(), sin.getCategoryName());
	}


	@Override
	protected LKCodeEnum existErrorCode(I sin, String locale, String compId, String loginId) {
		return ErrorCodes.SysPssProductCategory_EXIST;
	}

}
