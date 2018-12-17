package com.lichkin.application.apis.api50101.L.n00;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.lichkin.framework.defines.LKFrameworkStatics;
import com.lichkin.framework.web.annotations.LKApiType;
import com.lichkin.framework.web.enums.ApiType;
import com.lichkin.springframework.controllers.ApiKeyValues;
import com.lichkin.springframework.controllers.LKApiBusGetListController;
import com.lichkin.springframework.entities.impl.SysPssPurchaseOrderProductEntity;
import com.lichkin.springframework.services.LKApiBusGetListService;

@RestController("SysPssPurchaseOrderProductL00Controller")
@RequestMapping(value = LKFrameworkStatics.WEB_MAPPING_API + "/SysPssPurchaseOrderProduct/L")
@LKApiType(apiType = ApiType.COMPANY_BUSINESS)
public class C extends LKApiBusGetListController<I, O, SysPssPurchaseOrderProductEntity> {

	@Autowired
	private S service;


	@Override
	protected LKApiBusGetListService<I, O, SysPssPurchaseOrderProductEntity> getService(I cin, ApiKeyValues<I> params) {
		return service;
	}

}
