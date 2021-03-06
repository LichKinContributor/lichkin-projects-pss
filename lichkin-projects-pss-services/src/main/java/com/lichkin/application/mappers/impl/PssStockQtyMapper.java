package com.lichkin.application.mappers.impl;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.lichkin.application.mappers.impl.in.PssStockOutQtyIn;
import com.lichkin.application.mappers.impl.in.PurchaseOrderSavedStockInQtyIn;
import com.lichkin.application.mappers.impl.in.SellOrderSavedStockOutQtyIn;
import com.lichkin.application.mappers.impl.out.PssStockOutQtyOut;
import com.lichkin.application.mappers.impl.out.PurchaseOrderSavedStockInQtyOut;
import com.lichkin.application.mappers.impl.out.SellOrderSavedStockOutQtyOut;

@Mapper
public interface PssStockQtyMapper {

	/** 查询所有类型已填写的出库数量 */
	List<PssStockOutQtyOut> findStockOutQty(PssStockOutQtyIn in);


	/** 查询单个采购单已填写的入库的数量（订单非驳回 非删除状态） */
	List<PurchaseOrderSavedStockInQtyOut> findPurchaseOrderSavedStockInQty(PurchaseOrderSavedStockInQtyIn in);


	/** 查询单个销售单已填写的出库的数量（订单非驳回 非删除状态） */
	List<SellOrderSavedStockOutQtyOut> findSellOrderSavedStockOutQty(SellOrderSavedStockOutQtyIn in);

}
