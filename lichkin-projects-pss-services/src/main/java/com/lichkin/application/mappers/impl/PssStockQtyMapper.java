package com.lichkin.application.mappers.impl;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.lichkin.application.mappers.impl.in.PssPurchaseStockInQtyIn;
import com.lichkin.application.mappers.impl.in.PssStockOutQtyIn;
import com.lichkin.application.mappers.impl.out.PssPurchaseStockInQtyOut;
import com.lichkin.application.mappers.impl.out.PssStockOutQtyOut;

@Mapper
public interface PssStockQtyMapper {

	List<PssStockOutQtyOut> findStockOutQty(PssStockOutQtyIn in);


	List<PssPurchaseStockInQtyOut> findPurchaseStockInQty(PssPurchaseStockInQtyIn in);
}
