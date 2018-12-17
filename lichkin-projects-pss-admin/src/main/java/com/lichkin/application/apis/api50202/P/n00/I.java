package com.lichkin.application.apis.api50202.P.n00;

import com.lichkin.framework.beans.impl.LKRequestPageBean;
import com.lichkin.framework.defines.enums.impl.ApprovalStatusEnum;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class I extends LKRequestPageBean {

	private ApprovalStatusEnum approvalStatus;

	private String orderNo;

	private String storageId;

	private Boolean orderType;

	/** 销售订单号 */
	private String sellOrderNo;

	/** 销售人姓名 */
	private String salesName;

}
