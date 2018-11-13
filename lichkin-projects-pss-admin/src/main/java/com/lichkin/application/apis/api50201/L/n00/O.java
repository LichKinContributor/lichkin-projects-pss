package com.lichkin.application.apis.api50201.L.n00;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class O {

	private int quantity;

	private int inventoryQuantity;

	private String unitPrice;

	private String subTotalPrice;

	/** 产品ID */
	private String id;

	/** 产品编码 */
	private String productCode;

	/** 产品名称 */
	private String productName;

	/** 条形码 */
	private String barcode;

	/** 单位 */
	private String unit;

}
