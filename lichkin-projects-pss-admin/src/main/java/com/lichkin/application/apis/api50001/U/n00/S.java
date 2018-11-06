package com.lichkin.application.apis.api50001.U.n00;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lichkin.application.services.bus.impl.SysPssSupplierBusService;
import com.lichkin.framework.defines.enums.LKCodeEnum;
import com.lichkin.springframework.entities.impl.SysPssSupplierEntity;
import com.lichkin.springframework.services.LKApiBusUpdateService;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Service("SysPssSupplierU00Service")
public class S extends LKApiBusUpdateService<I, SysPssSupplierEntity> {

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
	protected boolean needCheckExist(I sin, String locale, String compId, String loginId, SysPssSupplierEntity entity, String id) {
		String supplierCodeSaved = entity.getSupplierCode();
		String supplierCodeIn = sin.getSupplierCode();
		if (((supplierCodeSaved == null) && (supplierCodeIn != null)) || ((supplierCodeSaved != null) && ((supplierCodeIn == null) || !supplierCodeSaved.equals(supplierCodeIn)))) {
			return true;
		}
		if (!entity.getSupplierName().equals(sin.getSupplierName())) {
			return true;
		}
		return false;
	}


	@Override
	protected List<SysPssSupplierEntity> findExist(I sin, String locale, String compId, String loginId, SysPssSupplierEntity entity, String id) {
		return busService.findExist(id, compId, null, sin.getSupplierCode(), sin.getSupplierName());
	}


	@Override
	protected LKCodeEnum existErrorCode(I sin, String locale, String compId, String loginId) {
		return ErrorCodes.SysPssSupplier_EXIST;
	}

}
