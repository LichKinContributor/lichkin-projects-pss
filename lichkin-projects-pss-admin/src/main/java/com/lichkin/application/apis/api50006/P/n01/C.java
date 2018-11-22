package com.lichkin.application.apis.api50006.P.n01;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.lichkin.application.mappers.impl.PssStockReportMapper;
import com.lichkin.application.mappers.impl.in.PssStorageDetailIn;
import com.lichkin.application.mappers.impl.out.PssStorageDetailOut;
import com.lichkin.framework.defines.LKFrameworkStatics;
import com.lichkin.framework.defines.exceptions.LKException;
import com.lichkin.framework.utils.LKBeanUtils;
import com.lichkin.framework.web.annotations.LKApiType;
import com.lichkin.framework.web.enums.ApiType;
import com.lichkin.springframework.controllers.LKApiY0Controller;

@RestController("SysPssStockP01Controller")
@RequestMapping(value = LKFrameworkStatics.WEB_MAPPING_API + "/SysPssStock/P01")
@LKApiType(apiType = ApiType.COMPANY_BUSINESS)
public class C extends LKApiY0Controller<I, Page<PssStorageDetailOut>> {

	@Autowired
	private PssStockReportMapper pssStockReportMapper;


	@Deprecated
	@Override
	protected Page<PssStorageDetailOut> doInvoke(I cin) throws LKException {
		PssStorageDetailIn in = LKBeanUtils.newInstance(cin, PssStorageDetailIn.class);
		in.setCompId(cin.getDatas().getCompId());

		List<PssStorageDetailOut> list = pssStockReportMapper.findStorageDetail(in);
		Long total = pssStockReportMapper.findStorageDetailTotalCount(in);

		// 返回结果
		return new PageImpl<>(list, PageRequest.of(in.getPageNumber(), in.getPageSize()), total);
	}

}
