package com.lichkin.application.apis.api50100.O.n00;

import com.lichkin.framework.defines.enums.impl.InventoryStatusEnum;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class O {

	private String id;

//	private LKUsingStatusEnum usingStatus;

//	private String insertTime;

//	private String compId;

//	private ApprovalStatusEnum approvalStatus;

//	private String approvalTime;

//	private String orderNo;

	private String billDate;

	private String remarks;

	private String supplierId;

	private String purchaserId;

	private String orderAmount;

	private InventoryStatusEnum inventoryStatus;

}
