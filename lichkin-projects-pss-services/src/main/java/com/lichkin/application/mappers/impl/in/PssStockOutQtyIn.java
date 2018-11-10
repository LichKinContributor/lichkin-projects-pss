package com.lichkin.application.mappers.impl.in;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class PssStockOutQtyIn {

	/** 仓库ID */
	private String storageIds;

	/** 产品ID */
	private String productIds;

	/** 当前订单ID */
	private String orderId;

}
