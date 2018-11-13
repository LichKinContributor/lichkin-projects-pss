package com.lichkin.application.apis.api50202.D.n00;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.lichkin.framework.defines.LKFrameworkStatics;
import com.lichkin.framework.web.annotations.LKApiType;
import com.lichkin.framework.web.enums.ApiType;
import com.lichkin.springframework.controllers.LKApiBusDeleteController;
import com.lichkin.springframework.entities.impl.SysPssSellStockOrderEntity;
import com.lichkin.springframework.services.LKApiBusDeleteService;

@RestController("SysPssSellStockOrderD00Controller")
@RequestMapping(value = LKFrameworkStatics.WEB_MAPPING_API_WEB_ADMIN + "/SysPssSellStockOrder/D")
@LKApiType(apiType = ApiType.COMPANY_BUSINESS)
public class C extends LKApiBusDeleteController<I, SysPssSellStockOrderEntity> {

	@Autowired
	private S service;


	@Override
	protected LKApiBusDeleteService<I, SysPssSellStockOrderEntity> getService(I cin) {
		return service;
	}

}
