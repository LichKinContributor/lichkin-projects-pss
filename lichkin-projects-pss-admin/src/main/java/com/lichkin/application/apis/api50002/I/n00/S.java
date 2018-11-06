package com.lichkin.application.apis.api50002.I.n00;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lichkin.application.services.bus.impl.SysPssProductCategoryBusService;
import com.lichkin.framework.defines.enums.LKCodeEnum;
import com.lichkin.framework.defines.enums.impl.LKUsingStatusEnum;
import com.lichkin.framework.defines.exceptions.LKRuntimeException;
import com.lichkin.springframework.entities.impl.SysPssProductCategoryEntity;
import com.lichkin.springframework.services.LKApiBusInsertService;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Service("SysPssProductCategoryI00Service")
public class S extends LKApiBusInsertService<I, SysPssProductCategoryEntity> {

	@Autowired
	private SysPssProductCategoryBusService busService;


	@Getter
	@RequiredArgsConstructor
	enum ErrorCodes implements LKCodeEnum {

		SysPssProductCategory_EXIST(60000),

		SysPssProductCategory_parent_code_can_not_modify_when_restore(60000),

		;

		private final Integer code;

	}


	@Override
	protected List<SysPssProductCategoryEntity> findExist(I sin, String locale, String compId, String loginId) {
		return busService.findExist(null, compId, sin.getCompId(), sin.getParentCode(), sin.getCategoryName());
	}


	@Override
	protected boolean forceCheck(I sin, String locale, String compId, String loginId) {
		return false;
	}


	@Override
	protected LKCodeEnum existErrorCode(I sin, String locale, String compId, String loginId) {
		return ErrorCodes.SysPssProductCategory_EXIST;
	}


	@Override
	protected void beforeRestore(I sin, String locale, String compId, String loginId, SysPssProductCategoryEntity entity, SysPssProductCategoryEntity exist) {
		entity.setUsingStatus(LKUsingStatusEnum.USING);
		entity.setCompId(exist.getCompId());
		if (!exist.getParentCode().equals(entity.getParentCode())) {
			throw new LKRuntimeException(ErrorCodes.SysPssProductCategory_parent_code_can_not_modify_when_restore).withParam("#parentCode", exist.getParentCode());
		}
		entity.setCategoryCode(exist.getCategoryCode());
	}


	@Override
	protected void beforeAddNew(I sin, String locale, String compId, String loginId, SysPssProductCategoryEntity entity) {
		entity.setCompId(getCompId(compId, sin.getCompId()));
		entity.setCategoryCode(busService.analysisCategoryCode(compId, sin.getParentCode()));
	}

}
