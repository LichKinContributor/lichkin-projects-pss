package com.lichkin.application.apis.api50701.L.n00;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class O {

	/** 产品ID */
	private String id;

	/** 产品数量 */
	private int quantity;

	/** 产品库存数量 */
	private int stockQuantity;

	/** 可用库存数量 */
	private int canOutQuantity;

	/** 产品编码 */
	private String productCode;

	/** 产品名称 */
	private String productName;

	/** 条形码 */
	private String barcode;

	/** 单位 */
	private String unit;

	/** 采购单价 */
	private String unitPrice;

	/** 小计 */
	private String subTotalPrice;

	/** 仓库Id */
	private String storageId;

}
