package com.lichkin.application.apis.api50600.O.n00;

import com.lichkin.framework.defines.enums.impl.ApprovalStatusEnum;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class O {

	private String id;

	private ApprovalStatusEnum approvalStatus;

	private String approvalTime;

	private String orderNo;

	private String billDate;

	private String remarks;

	private Boolean orderType;

	private String orderId;

	private String supplierId;

	private String purchaserId;

	private String orderAmount;
}
