package com.lichkin.application.apis.api50204.P.n00;

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

	/** 销售订单号 */
	private String sellOrderNo;

	/** 销售人姓名 */
	private String salesName;

	/** 开始日期 */
	private String startDate;

	/** 结束日期 */
	private String endDate;

	/** 退货员姓名 */
	private String returnedName;

}
