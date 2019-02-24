package com.lichkin.application.apis.api50102.P.n00;

import com.lichkin.framework.beans.impl.LKRequestPageBean;
import com.lichkin.framework.defines.entities.I_CompId;
import com.lichkin.framework.defines.enums.impl.ApprovalStatusEnum;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class I extends LKRequestPageBean implements I_CompId {

	private ApprovalStatusEnum approvalStatus;

	private String orderNo;

	private String storageId;

	private Boolean orderType;

	/** 采购订单号 */
	private String purchaseOrderNo;

	/** 供应商名称 */
	private String supplierName;

	/** 采购人姓名 */
	private String purchaserName;

}
