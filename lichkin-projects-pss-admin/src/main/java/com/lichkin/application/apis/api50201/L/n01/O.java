package com.lichkin.application.apis.api50201.L.n01;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class O {

	/** 产品ID */
	private String id;

	/** 销售数量 */
	private int salesQuantity;

	/** 已出库数量 */
	private int inventoryQuantity;

	/** 可出库数量 */
	private int canStockOutQty;

	/** 产品编码 */
	private String productCode;

	/** 产品名称 */
	private String productName;

	/** 条形码 */
	private String barcode;

	/** 单位 */
	private String unit;

}
