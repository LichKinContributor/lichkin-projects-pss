package com.lichkin.application.apis.api50004.O.n00;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.lichkin.framework.defines.LKFrameworkStatics;
import com.lichkin.framework.web.annotations.LKApiType;
import com.lichkin.framework.web.enums.ApiType;
import com.lichkin.springframework.controllers.LKApiBusGetOneController;
import com.lichkin.springframework.entities.impl.SysPssStoreEntity;
import com.lichkin.springframework.services.LKApiBusGetOneService;

@RestController("SysPssStoreO00Controller")
@RequestMapping(value = LKFrameworkStatics.WEB_MAPPING_API + "/SysPssStore/O")
@LKApiType(apiType = ApiType.COMPANY_BUSINESS)
public class C extends LKApiBusGetOneController<I, O, SysPssStoreEntity> {

	@Autowired
	private S service;


	@Override
	protected LKApiBusGetOneService<I, O, SysPssStoreEntity> getService(I cin) {
		return service;
	}

}
