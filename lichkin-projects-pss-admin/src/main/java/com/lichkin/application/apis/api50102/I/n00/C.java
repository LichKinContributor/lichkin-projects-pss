package com.lichkin.application.apis.api50102.I.n00;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.lichkin.framework.defines.LKFrameworkStatics;
import com.lichkin.framework.web.annotations.LKApiType;
import com.lichkin.framework.web.enums.ApiType;
import com.lichkin.springframework.controllers.ApiKeyValues;
import com.lichkin.springframework.controllers.LKApiBusInsertController;
import com.lichkin.springframework.entities.impl.SysPssPurchaseStockOrderEntity;
import com.lichkin.springframework.services.LKApiBusInsertWithoutCheckerService;

@RestController("SysPssPurchaseStockOrderI00Controller")
@RequestMapping(value = LKFrameworkStatics.WEB_MAPPING_API + "/SysPssPurchaseStockOrder/I")
@LKApiType(apiType = ApiType.COMPANY_BUSINESS)
public class C extends LKApiBusInsertController<I, SysPssPurchaseStockOrderEntity> {

	@Autowired
	private S service;


	@Override
	protected LKApiBusInsertWithoutCheckerService<I, SysPssPurchaseStockOrderEntity> getService(I cin, ApiKeyValues<I> params) {
		return service;
	}

}
