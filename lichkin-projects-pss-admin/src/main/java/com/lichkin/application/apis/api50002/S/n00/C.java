package com.lichkin.application.apis.api50002.S.n00;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.lichkin.framework.defines.LKFrameworkStatics;
import com.lichkin.framework.defines.beans.impl.LKTreeBean;
import com.lichkin.framework.defines.exceptions.LKException;
import com.lichkin.framework.utils.LKTreeUtils;
import com.lichkin.framework.web.annotations.LKApiType;
import com.lichkin.framework.web.enums.ApiType;
import com.lichkin.springframework.controllers.LKApiYYController;
import com.lichkin.springframework.entities.impl.SysPssProductCategoryEntity;
import com.lichkin.springframework.services.LKApiService;

@RestController("SysPssProductCategoryS00Controller")
@RequestMapping(value = LKFrameworkStatics.WEB_MAPPING_API + "/SysPssProductCategory/S")
@LKApiType(apiType = ApiType.COMPANY_BUSINESS)
public class C extends LKApiYYController<I, List<LKTreeBean>, I, List<SysPssProductCategoryEntity>> {

	@Autowired
	private S service;


	@Override
	protected LKApiService<I, List<SysPssProductCategoryEntity>> getService(I cin) {
		return service;
	}


	@Override
	protected List<LKTreeBean> afterInvokeService(I cin, I sin, List<SysPssProductCategoryEntity> sout) throws LKException {
		return LKTreeUtils.toTree(sout, "id", "categoryName", "categoryCode", "parentCode");
	}

}
