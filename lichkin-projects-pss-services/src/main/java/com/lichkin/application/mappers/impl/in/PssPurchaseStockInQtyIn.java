package com.lichkin.application.mappers.impl.in;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class PssPurchaseStockInQtyIn {

	/** 仓库ID */
	private String storageIds;

	/** 产品ID */
	private String productIds;

	/** 采购单ID */
	private String orderId;

}
