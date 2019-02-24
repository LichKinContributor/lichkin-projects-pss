package com.lichkin.application.apis.api50400.P.n00;

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

	private String outStorageId;

	private String inStorageId;

}
