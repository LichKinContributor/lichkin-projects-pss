package com.lichkin.application.apis.api50205.L.n00;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.lichkin.framework.defines.LKFrameworkStatics;
import com.lichkin.framework.web.annotations.LKApiType;
import com.lichkin.framework.web.enums.ApiType;
import com.lichkin.springframework.controllers.ApiKeyValues;
import com.lichkin.springframework.controllers.LKApiBusGetListController;
import com.lichkin.springframework.entities.impl.SysPssSellReturnNotStockOutOrderProductEntity;
import com.lichkin.springframework.services.LKApiBusGetListService;

@RestController("SysPssSellReturnNotStockOutOrderProductL00Controller")
@RequestMapping(value = LKFrameworkStatics.WEB_MAPPING_API + "/SysPssSellReturnNotStockOutOrderProduct/L")
@LKApiType(apiType = ApiType.COMPANY_BUSINESS)
public class C extends LKApiBusGetListController<I, O, SysPssSellReturnNotStockOutOrderProductEntity> {

	@Autowired
	private S service;


	@Override
	protected LKApiBusGetListService<I, O, SysPssSellReturnNotStockOutOrderProductEntity> getService(I cin, ApiKeyValues<I> params) {
		return service;
	}

}
