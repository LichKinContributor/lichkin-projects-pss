package com.lichkin.application.apis.api50103.L.n00;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class O {

	/** 采购单产品ID */
	private String purchaseOrderProductId;

	/** 采购数量 */
	private int purchaseQty;

	/** 已入库数量 */
	private int inventoryQuantity;

	/** 已退货数量 */
	private int returnedQuantity;

	/** 可入库数量 */
	private int canStockInQty;

	/** 采购单价 */
	private String unitPrice;

	private int quantity;

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
