package com.lichkin.application.apis.api50401.L.n00;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class O {

	/** 产品ID */
	private String id;

	/** 库存数量 */
	private int stockQuantity;

	/** 可用库存数量 */
	private int canOutQty;

	private int quantity;

	/** 产品编码 */
	private String productCode;

	/** 产品名称 */
	private String productName;

	/** 条形码 */
	private String barcode;

	/** 单位 */
	private String unit;

	/** 仓库Id */
	private String storageId;

}
