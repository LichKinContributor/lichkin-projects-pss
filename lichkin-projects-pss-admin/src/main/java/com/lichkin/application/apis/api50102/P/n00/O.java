package com.lichkin.application.apis.api50102.P.n00;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class O {

	private String id;

	private String usingStatus;

	private String usingStatusDictCode;// for usingStatus

	private String insertTime;

	private String approvalStatus;

	private String approvalStatusDictCode;// for approvalStatus

	private String approvalTime;

	private String orderNo;

	private String billDate;

	/** 采购订单号 */
	private String purchaseOrderNo;

	/** 采购订单日期 */
	private String purchaserBillDate;

	/** 采购订单金额 */
	private String purchaseOrderAmount;

	/** 供应商名称 */
	private String supplierName;

	/** 采购人姓名 */
	private String purchaserName;

}
