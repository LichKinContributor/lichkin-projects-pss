package com.lichkin.application.apis.api50101.L.n01;

import com.lichkin.framework.beans.impl.LKRequestBean;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class I extends LKRequestBean {

	/** 采购单ID */
	private String orderId;

	/** 产品条码 */
	private String barcode;

	/** 产品ID */
	private String productId;

}
