package com.lichkin.application.apis.api50102.O.n00;

import com.lichkin.framework.defines.enums.impl.ApprovalStatusEnum;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class O {

	private String id;

//	private LKUsingStatusEnum usingStatus;

//	private String insertTime;

	private ApprovalStatusEnum approvalStatus;

	private String approvalTime;

	private String orderNo;

	private String billDate;

	private String remarks;

	private String storageId;

	private Boolean orderType;

	private String orderId;

}
