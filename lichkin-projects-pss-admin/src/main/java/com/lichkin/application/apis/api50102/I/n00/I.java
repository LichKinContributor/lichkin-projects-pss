package com.lichkin.application.apis.api50102.I.n00;

import com.lichkin.framework.beans.impl.LKRequestBean;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class I extends LKRequestBean {

	private String compId;

	private String billDate;

	private String remarks;

	private String orderId;

	/** 产品列表 */
	private String productList;

}
