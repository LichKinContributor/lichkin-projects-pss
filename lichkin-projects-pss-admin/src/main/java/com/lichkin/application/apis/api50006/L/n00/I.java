package com.lichkin.application.apis.api50006.L.n00;

import com.lichkin.framework.beans.impl.LKRequestBean;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class I extends LKRequestBean {

	/** 仓库Id */
	private String storageId;

	/** 条形码 */
	private String barcode;

	/** 产品Id */
	private String productId;

	/** 当前订单ID */
	private String orderId;

}
