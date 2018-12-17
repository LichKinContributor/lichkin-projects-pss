package com.lichkin.application.apis.api50100.P.n00;

import com.lichkin.framework.beans.impl.LKRequestPageBean;
import com.lichkin.framework.defines.enums.impl.ApprovalStatusEnum;
import com.lichkin.framework.defines.enums.impl.InventoryStatusEnum;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class I extends LKRequestPageBean {

	private ApprovalStatusEnum approvalStatus;

	private String orderNo;

	private InventoryStatusEnum inventoryStatus;

	/** 供应商名称 */
	private String supplierName;

	/** 采购人姓名 */
	private String purchaserName;

}
