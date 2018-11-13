package com.lichkin.application.apis.api50201.P.n00;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class O {

	private String id;

	private int quantity;

	private int inventoryQuantity;

	private String unitPrice;

	private String subTotalPrice;

	/** 产品编码 */
	private String productCode;

	/** 产品名称 */
	private String productName;

	/** 条形码 */
	private String barcode;

	/** 单位 */
	private String unit;

	/** 订单日期 */
	private String billDate;

	/** 订单编号 */
	private String orderNo;

	/** 销售员 */
	private String salesName;

}
