package com.lichkin.application.apis.api50700.P.n00;

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

	/** 供应商名称 */
	private String supplierName;

	/** 退货员姓名 */
	private String returnedName;

	/** 仓库 */
	private String storageId;

	/** 开始日期 */
	private String startDate;

	/** 结束日期 */
	private String endDate;

}
