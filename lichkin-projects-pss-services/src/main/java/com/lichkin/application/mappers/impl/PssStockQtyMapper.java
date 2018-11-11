package com.lichkin.application.mappers.impl;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.lichkin.application.mappers.impl.in.PssPurchaseStockInQtyIn;
import com.lichkin.application.mappers.impl.in.PssStockOutQtyIn;
import com.lichkin.application.mappers.impl.in.PurchaseProdStockInQtyIn;
import com.lichkin.application.mappers.impl.out.PssPurchaseStockInQtyOut;
import com.lichkin.application.mappers.impl.out.PssStockOutQtyOut;
import com.lichkin.application.mappers.impl.out.PurchaseProdStockInQtyOut;

@Mapper
public interface PssStockQtyMapper {

	/** 查询所有类型已填写的出库数量 */
	List<PssStockOutQtyOut> findStockOutQty(PssStockOutQtyIn in);


	/** 查询单个采购单实际已经入库的数量（订单审核通过） */
	List<PssPurchaseStockInQtyOut> findPurchaseStockInQty(PssPurchaseStockInQtyIn in);


	/** 查询单个采购单已填写的入库的数量（订单非驳回 非删除状态） */
	List<PurchaseProdStockInQtyOut> findPurchaseProdStockInQty(PurchaseProdStockInQtyIn in);
}
