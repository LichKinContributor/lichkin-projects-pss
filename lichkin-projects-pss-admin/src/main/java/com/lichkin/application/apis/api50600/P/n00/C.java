package com.lichkin.application.apis.api50600.P.n00;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.lichkin.framework.defines.LKFrameworkStatics;
import com.lichkin.framework.web.annotations.LKApiType;
import com.lichkin.framework.web.enums.ApiType;
import com.lichkin.springframework.controllers.ApiKeyValues;
import com.lichkin.springframework.controllers.LKApiBusGetPageController;
import com.lichkin.springframework.entities.impl.SysPssPurchaseReturnNotStockInOrderEntity;
import com.lichkin.springframework.services.LKApiBusGetPageService;

@RestController("SysPssPurchaseReturnNotStockInOrderP00Controller")
@RequestMapping(value = LKFrameworkStatics.WEB_MAPPING_API + "/SysPssPurchaseReturnNotStockInOrder/P")
@LKApiType(apiType = ApiType.COMPANY_BUSINESS)
public class C extends LKApiBusGetPageController<I, O, SysPssPurchaseReturnNotStockInOrderEntity> {

	@Autowired
	private S service;


	@Override
	protected LKApiBusGetPageService<I, O, SysPssPurchaseReturnNotStockInOrderEntity> getService(I cin, ApiKeyValues<I> params) {
		return service;
	}

}
