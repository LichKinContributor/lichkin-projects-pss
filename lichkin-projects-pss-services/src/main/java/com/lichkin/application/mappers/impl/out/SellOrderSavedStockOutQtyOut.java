package com.lichkin.application.mappers.impl.out;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SellOrderSavedStockOutQtyOut {

	/** 产品ID */
	private String sellOrderProductId;

	/** 产品数量 */
	private int quantity;
}
