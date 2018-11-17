package com.lichkin.application.apis.activiti.PSS_ALLOT_ORDER;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.lichkin.defines.ActivitiStatics;
import com.lichkin.defines.PssStatics;
import com.lichkin.framework.defines.LKFrameworkStatics;
import com.lichkin.framework.web.annotations.LKApiType;
import com.lichkin.framework.web.enums.ApiType;
import com.lichkin.springframework.controllers.LKApiBusStartProcessController;
import com.lichkin.springframework.entities.impl.SysPssAllotOrderEntity;
import com.lichkin.springframework.services.LKApiBusStartProcessService;

@RestController(PssStatics.PSS_ALLOT_ORDER + ActivitiStatics.START_PROCESS + "Controller")
@RequestMapping(value = LKFrameworkStatics.WEB_MAPPING_API_WEB_ADMIN + "/" + PssStatics.PSS_ALLOT_ORDER + "/" + ActivitiStatics.START_PROCESS)
@LKApiType(apiType = ApiType.COMPANY_BUSINESS)
public class C extends LKApiBusStartProcessController<I, SysPssAllotOrderEntity> {

	@Autowired
	private S service;


	@Override
	protected LKApiBusStartProcessService<I, SysPssAllotOrderEntity> getService(I cin) {
		return service;
	}

}
