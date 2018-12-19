package com.lichkin.application.mappers.impl.out;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PurchaseOrderSavedStockInQtyOut {

	/** 产品ID */
	private String purchaseOrderProductId;

	/** 产品数量 */
	private int quantity;
}
