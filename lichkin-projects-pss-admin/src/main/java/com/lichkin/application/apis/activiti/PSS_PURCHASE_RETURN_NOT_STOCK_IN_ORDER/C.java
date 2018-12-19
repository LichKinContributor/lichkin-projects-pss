package com.lichkin.application.apis.activiti.PSS_PURCHASE_RETURN_NOT_STOCK_IN_ORDER;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.lichkin.defines.ActivitiStatics;
import com.lichkin.defines.PssStatics;
import com.lichkin.framework.defines.LKFrameworkStatics;
import com.lichkin.framework.web.annotations.LKApiType;
import com.lichkin.framework.web.enums.ApiType;
import com.lichkin.springframework.controllers.ApiKeyValues;
import com.lichkin.springframework.controllers.LKApiBusStartProcessController;
import com.lichkin.springframework.entities.impl.SysPssPurchaseReturnNotStockInOrderEntity;
import com.lichkin.springframework.services.LKApiBusStartProcessService;

@RestController(PssStatics.PSS_PURCHASE_RETURN_NOT_STOCK_IN_ORDER + ActivitiStatics.START_PROCESS + "Controller")
@RequestMapping(value = LKFrameworkStatics.WEB_MAPPING_API + "/" + PssStatics.PSS_PURCHASE_RETURN_NOT_STOCK_IN_ORDER + "/" + ActivitiStatics.START_PROCESS)
@LKApiType(apiType = ApiType.COMPANY_BUSINESS)
public class C extends LKApiBusStartProcessController<I, SysPssPurchaseReturnNotStockInOrderEntity> {

	@Autowired
	private S service;


	@Override
	protected LKApiBusStartProcessService<I, SysPssPurchaseReturnNotStockInOrderEntity> getService(I cin, ApiKeyValues<I> params) {
		return service;
	}

}
