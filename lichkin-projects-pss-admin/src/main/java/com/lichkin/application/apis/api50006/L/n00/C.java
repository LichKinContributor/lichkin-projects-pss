package com.lichkin.application.apis.api50006.L.n00;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.lichkin.framework.defines.LKFrameworkStatics;
import com.lichkin.framework.web.annotations.LKApiType;
import com.lichkin.framework.web.enums.ApiType;
import com.lichkin.springframework.controllers.LKApiBusGetListController;
import com.lichkin.springframework.entities.impl.SysPssStockEntity;
import com.lichkin.springframework.services.LKApiBusGetListService;

@RestController("SysPssStockL00Controller")
@RequestMapping(value = LKFrameworkStatics.WEB_MAPPING_API + "/SysPssStock/L")
@LKApiType(apiType = ApiType.COMPANY_BUSINESS)
public class C extends LKApiBusGetListController<I, O, SysPssStockEntity> {

	@Autowired
	private S service;


	@Override
	protected LKApiBusGetListService<I, O, SysPssStockEntity> getService(I cin) {
		return service;
	}

}
