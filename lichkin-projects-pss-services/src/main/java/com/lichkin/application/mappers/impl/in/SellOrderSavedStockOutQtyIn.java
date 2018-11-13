package com.lichkin.application.mappers.impl.in;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class SellOrderSavedStockOutQtyIn {

	/** 产品ID */
	private String productIds;

	/** 销售订单ID */
	private String sellOrderId;

	/** 当前销售出库单ID */
	private String orderId;
}
