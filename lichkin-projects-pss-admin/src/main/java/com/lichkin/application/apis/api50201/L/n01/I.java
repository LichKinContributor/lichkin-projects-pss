package com.lichkin.application.apis.api50201.L.n01;

import com.lichkin.framework.beans.impl.LKRequestBean;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class I extends LKRequestBean {

	/** 销售单ID */
	private String orderId;

	/** 产品条码 */
	private String barcode;

	/** 产品ID */
	private String productId;

}
