package com.lichkin.application.apis.api50200.P.n00;

import com.lichkin.framework.beans.impl.LKRequestPageBean;
import com.lichkin.framework.defines.entities.I_CompId;
import com.lichkin.framework.defines.enums.impl.ApprovalStatusEnum;
import com.lichkin.framework.defines.enums.impl.InventoryStatusEnum;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class I extends LKRequestPageBean implements I_CompId {

	private ApprovalStatusEnum approvalStatus;

	private String orderNo;

	private InventoryStatusEnum inventoryStatus;

	/** 销售人姓名 */
	private String salesName;

}
