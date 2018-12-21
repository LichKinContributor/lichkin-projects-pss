package com.lichkin.application.apis.api50206.P.n00;

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

	/** 订单金额 */
	private String orderAmount;

	/** 退货员姓名 */
	private String returnedName;

	/** 仓库 */
	private String storageName;
}
