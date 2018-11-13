package com.lichkin.application.mappers.impl;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.lichkin.application.mappers.impl.in.PssStorageDetailIn;
import com.lichkin.application.mappers.impl.out.PssStorageDetailOut;

@Mapper
public interface PssStockReportMapper {

	Long findStorageDetailTotalCount(PssStorageDetailIn in);


	/** 查询入库明细 */
	List<PssStorageDetailOut> findStorageDetail(PssStorageDetailIn in);

}
