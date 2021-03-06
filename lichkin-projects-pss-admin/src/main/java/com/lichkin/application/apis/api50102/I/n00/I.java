package com.lichkin.application.apis.api50102.I.n00;

import com.lichkin.framework.beans.impl.LKRequestBean;
import com.lichkin.framework.defines.entities.I_CompId;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class I extends LKRequestBean implements I_CompId {

	private String billDate;

	private String remarks;

	private Boolean orderType;

	private String storageId;

	private String orderId;

	/** 产品列表 */
	private String productList;

}
