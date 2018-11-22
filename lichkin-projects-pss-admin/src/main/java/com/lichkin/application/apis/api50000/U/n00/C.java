package com.lichkin.application.apis.api50000.U.n00;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.lichkin.framework.defines.LKFrameworkStatics;
import com.lichkin.framework.web.annotations.LKApiType;
import com.lichkin.framework.web.enums.ApiType;
import com.lichkin.springframework.controllers.LKApiBusUpdateController;
import com.lichkin.springframework.entities.impl.SysPssStorageEntity;
import com.lichkin.springframework.services.LKApiBusUpdateService;

@RestController("SysPssStorageU00Controller")
@RequestMapping(value = LKFrameworkStatics.WEB_MAPPING_API + "/SysPssStorage/U")
@LKApiType(apiType = ApiType.COMPANY_BUSINESS)
public class C extends LKApiBusUpdateController<I, SysPssStorageEntity> {

	@Autowired
	private S service;


	@Override
	protected LKApiBusUpdateService<I, SysPssStorageEntity> getService(I cin) {
		return service;
	}


	@Override
	protected String getSubOperBusType(I cin) {
		return "";
	}

}
