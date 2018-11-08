package com.lichkin.application.apis.api50500.P.n00;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class O {

	private String id;

	private String usingStatus;

	private String usingStatusDictCode;// for usingStatus

	private String insertTime;

	private String approvalStatus;

	private String approvalStatusDictCode;// for approvalStatus

	private String approvalTime;

	private String orderNo;

	private String billDate;

	private int stockCheckCount;

	/** 盘点仓库 */
	private String storageName;

}
