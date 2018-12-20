package com.lichkin.application.apis.api50701.L.n00;

import com.lichkin.framework.beans.impl.LKRequestBean;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class I extends LKRequestBean {

	private String orderId;

	/** 是否查看 */
	private Boolean isView = false;

}
