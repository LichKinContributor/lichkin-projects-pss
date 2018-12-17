package com.lichkin.application.apis.api50004.P.n00;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.lichkin.framework.defines.LKFrameworkStatics;
import com.lichkin.framework.web.annotations.LKApiType;
import com.lichkin.framework.web.enums.ApiType;
import com.lichkin.springframework.controllers.ApiKeyValues;
import com.lichkin.springframework.controllers.LKApiBusGetPageController;
import com.lichkin.springframework.entities.impl.SysPssStoreEntity;
import com.lichkin.springframework.services.LKApiBusGetPageService;

@RestController("SysPssStoreP00Controller")
@RequestMapping(value = LKFrameworkStatics.WEB_MAPPING_API + "/SysPssStore/P")
@LKApiType(apiType = ApiType.COMPANY_BUSINESS)
public class C extends LKApiBusGetPageController<I, O, SysPssStoreEntity> {

	@Autowired
	private S service;


	@Override
	protected LKApiBusGetPageService<I, O, SysPssStoreEntity> getService(I cin, ApiKeyValues<I> params) {
		return service;
	}

}
