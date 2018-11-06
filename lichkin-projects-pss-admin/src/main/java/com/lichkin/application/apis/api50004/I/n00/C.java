package com.lichkin.application.apis.api50004.I.n00;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.lichkin.framework.defines.LKFrameworkStatics;
import com.lichkin.framework.web.annotations.LKApiType;
import com.lichkin.framework.web.enums.ApiType;
import com.lichkin.springframework.controllers.LKApiBusInsertController;
import com.lichkin.springframework.entities.impl.SysPssStoreEntity;
import com.lichkin.springframework.services.LKApiBusInsertService;

@RestController("SysPssStoreI00Controller")
@RequestMapping(value = LKFrameworkStatics.WEB_MAPPING_API_WEB_ADMIN + "/SysPssStore/I")
@LKApiType(apiType = ApiType.COMPANY_BUSINESS)
public class C extends LKApiBusInsertController<I, SysPssStoreEntity> {

	@Autowired
	private S service;


	@Override
	protected LKApiBusInsertService<I, SysPssStoreEntity> getService(I cin) {
		return service;
	}


	@Override
	protected String getSubOperBusType(I cin) {
		return StringUtils.isBlank(cin.getCompId()) ? "" : "Comp";
	}

}
