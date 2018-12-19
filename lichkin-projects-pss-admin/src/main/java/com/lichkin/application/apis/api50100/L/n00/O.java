package com.lichkin.application.apis.api50100.L.n00;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class O {

	private String id;

	private String insertTime;

	private String approvalTime;

	private String orderNo;

	private String billDate;

	private String orderAmount;

	private String inventoryStatus;

	private String inventoryStatusDictCode;// for inventoryStatus

	/** 供应商ID */
	private String supplierId;

	/** 供应商名称 */
	private String supplierName;

	/** 采购人姓名 */
	private String purchaserName;

}
