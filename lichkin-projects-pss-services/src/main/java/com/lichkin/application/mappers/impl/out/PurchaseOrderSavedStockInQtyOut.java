package com.lichkin.application.mappers.impl.out;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PurchaseOrderSavedStockInQtyOut {

	/** 产品ID */
	private String productId;

	/** 产品数量 */
	private int quantity;
}
