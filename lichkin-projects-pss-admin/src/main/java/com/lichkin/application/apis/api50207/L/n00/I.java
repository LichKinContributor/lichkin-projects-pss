package com.lichkin.application.apis.api50207.L.n00;

import com.lichkin.framework.beans.impl.LKRequestBean;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class I extends LKRequestBean {

	private String orderId;

	/** 订单类型。true:入库单;false:出库单; */
	private Boolean orderType;

	/** 是否查看 */
	private Boolean isView = false;

}
