package com.lichkin.application.apis.api50600.P.n00;

import com.lichkin.framework.beans.impl.LKRequestPageBean;
import com.lichkin.framework.defines.entities.I_CompId;
import com.lichkin.framework.defines.enums.impl.ApprovalStatusEnum;
import com.lichkin.framework.defines.enums.impl.LKUsingStatusEnum;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class I extends LKRequestPageBean implements I_CompId {

	private LKUsingStatusEnum usingStatus;

	private String compId;

	private ApprovalStatusEnum approvalStatus;

	private String orderNo;

	/** 采购订单号 */
	private String purchaseOrderNo;

	/** 供应商名称 */
	private String supplierName;

	/** 采购人姓名 */
	private String purchaserName;

	/** 退货人姓名 */
	private String returnedName;

	/** 开始日期 */
	private String startDate;

	/** 结束日期 */
	private String endDate;

}
