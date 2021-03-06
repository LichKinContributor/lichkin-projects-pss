package com.lichkin.application.apis.api50202.P.n00;

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

	/** 销售订单号 */
	private String sellOrderNo;

	/** 销售订单日期 */
	private String sellBillDate;

	/** 销售订单金额 */
	private String sellOrderAmount;

	/** 销售人姓名 */
	private String salesName;

	/** 仓库名称 */
	private String storageName;

}
