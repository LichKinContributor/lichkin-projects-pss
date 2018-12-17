package com.lichkin.application.apis.api50400.I.n00;

import com.lichkin.framework.beans.impl.LKRequestBean;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class I extends LKRequestBean {

	private String billDate;

	private String remarks;

	private String outStorageId;

	private String inStorageId;

	/** 产品列表 */
	private String productList;

}
