package com.lichkin.application.apis.api50400.D.n00;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.lichkin.framework.beans.impl.LKRequestIDsBean;
import com.lichkin.framework.defines.LKFrameworkStatics;
import com.lichkin.framework.web.annotations.LKApiType;
import com.lichkin.framework.web.enums.ApiType;
import com.lichkin.springframework.controllers.ApiKeyValues;
import com.lichkin.springframework.controllers.LKApiBusDeleteController;
import com.lichkin.springframework.entities.impl.SysPssAllotOrderEntity;
import com.lichkin.springframework.services.LKApiBusDeleteService;

@RestController("SysPssAllotOrderD00Controller")
@RequestMapping(value = LKFrameworkStatics.WEB_MAPPING_API + "/SysPssAllotOrder/D")
@LKApiType(apiType = ApiType.COMPANY_BUSINESS)
public class C extends LKApiBusDeleteController<LKRequestIDsBean, SysPssAllotOrderEntity> {

	@Autowired
	private S service;


	@Override
	protected LKApiBusDeleteService<LKRequestIDsBean, SysPssAllotOrderEntity> getService(LKRequestIDsBean cin, ApiKeyValues<LKRequestIDsBean> params) {
		return service;
	}

}
