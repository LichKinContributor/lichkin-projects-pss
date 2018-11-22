package com.lichkin.application.apis.api50200.P.n00;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.lichkin.framework.defines.LKFrameworkStatics;
import com.lichkin.framework.web.annotations.LKApiType;
import com.lichkin.framework.web.enums.ApiType;
import com.lichkin.springframework.controllers.LKApiBusGetPageController;
import com.lichkin.springframework.entities.impl.SysPssSellOrderEntity;
import com.lichkin.springframework.services.LKApiBusGetPageService;

@RestController("SysPssSellOrderP00Controller")
@RequestMapping(value = LKFrameworkStatics.WEB_MAPPING_API + "/SysPssSellOrder/P")
@LKApiType(apiType = ApiType.COMPANY_BUSINESS)
public class C extends LKApiBusGetPageController<I, O, SysPssSellOrderEntity> {

	@Autowired
	private S service;


	@Override
	protected LKApiBusGetPageService<I, O, SysPssSellOrderEntity> getService(I cin) {
		return service;
	}

}
