package com.lichkin.application.apis.api50600.D.n00;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.lichkin.framework.defines.LKFrameworkStatics;
import com.lichkin.framework.web.annotations.LKApiType;
import com.lichkin.framework.web.enums.ApiType;
import com.lichkin.springframework.controllers.ApiKeyValues;
import com.lichkin.springframework.controllers.LKApiBusDeleteController;
import com.lichkin.springframework.entities.impl.SysPssPurchaseReturnNotStockInOrderEntity;
import com.lichkin.springframework.services.LKApiBusDeleteService;

@RestController("SysPssPurchaseReturnNotStockInOrderD00Controller")
@RequestMapping(value = LKFrameworkStatics.WEB_MAPPING_API + "/SysPssPurchaseReturnNotStockInOrder/D")
@LKApiType(apiType = ApiType.COMPANY_BUSINESS)
public class C extends LKApiBusDeleteController<I, SysPssPurchaseReturnNotStockInOrderEntity> {

	@Autowired
	private S service;


	@Override
	protected LKApiBusDeleteService<I, SysPssPurchaseReturnNotStockInOrderEntity> getService(I cin, ApiKeyValues<I> params) {
		return service;
	}

}
