package com.lichkin.application.mappers.impl.out;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PssStorageDetailOut {

	private String id;

	/** 订单日期 */
	private String billDate;

	/** 订单编号 */
	private String orderNo;

	/** 出入库类型 */
	private String storageType;

	/** 仓库 */
	private String storageName;

	/** 产品编码 */
	private String productCode;

	/** 产品名称 */
	private String productName;

	/** 条形码 */
	private String barcode;

	/** 单位 */
	private String unit;

	/** 数量 */
	private int quantity;

}
