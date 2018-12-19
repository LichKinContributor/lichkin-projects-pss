package com.lichkin.application.apis.api50600.U.n00;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.lichkin.framework.defines.LKFrameworkStatics;
import com.lichkin.framework.web.annotations.LKApiType;
import com.lichkin.framework.web.enums.ApiType;
import com.lichkin.springframework.controllers.ApiKeyValues;
import com.lichkin.springframework.controllers.LKApiBusUpdateController;
import com.lichkin.springframework.entities.impl.SysPssPurchaseReturnNotStockInOrderEntity;
import com.lichkin.springframework.services.LKApiBusUpdateWithoutCheckerService;

@RestController("SysPssPurchaseReturnNotStockInOrderU00Controller")
@RequestMapping(value = LKFrameworkStatics.WEB_MAPPING_API + "/SysPssPurchaseReturnNotStockInOrder/U")
@LKApiType(apiType = ApiType.COMPANY_BUSINESS)
public class C extends LKApiBusUpdateController<I, SysPssPurchaseReturnNotStockInOrderEntity> {

	@Autowired
	private S service;


	@Override
	protected LKApiBusUpdateWithoutCheckerService<I, SysPssPurchaseReturnNotStockInOrderEntity> getService(I cin, ApiKeyValues<I> params) {
		return service;
	}

}
