package com.lichkin.application.apis.api50006.P.n00;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class O {

	private String id;

	/** 库存数量 */
	private int quantity;

	/** 可用库存数量 */
	private int canOutQty;

	/** 仓库ID */
	private String storageId;

	/** 仓库名称 */
	private String storageName;

	/** 仓库编码 */
	private String storageCode;

	/** 产品ID */
	private String productId;

	/** 产品名称 */
	private String productName;

	/** 产品编码 */
	private String productCode;

	/** 条形码 */
	private String barcode;

}
