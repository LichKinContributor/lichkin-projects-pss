package com.lichkin.application.apis.api50102.U.n00;

import com.lichkin.framework.beans.impl.LKRequestIDBean;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class I extends LKRequestIDBean {

	private String billDate;

	private String remarks;

	/** 产品列表 */
	private String productList;

}
