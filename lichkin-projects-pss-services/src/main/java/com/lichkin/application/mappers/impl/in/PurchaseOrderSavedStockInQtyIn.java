package com.lichkin.application.mappers.impl.in;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class PurchaseOrderSavedStockInQtyIn {

	/** 产品ID */
	private String productIds;

	/** 采购订单ID */
	private String purchaseOrderId;

	/** 当前采购入库单ID */
	private String orderId;
}
