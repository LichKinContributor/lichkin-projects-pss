package com.lichkin.application.apis.api50202.I.n00;

import com.lichkin.framework.beans.impl.LKRequestBean;
import com.lichkin.framework.defines.entities.I_CompId;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class I extends LKRequestBean implements I_CompId {

	private String compId;

	private String billDate;

	private String remarks;

	private String storageId;

	private Boolean orderType;

	private String orderId;

	/** 产品列表 */
	private String productList;

}
