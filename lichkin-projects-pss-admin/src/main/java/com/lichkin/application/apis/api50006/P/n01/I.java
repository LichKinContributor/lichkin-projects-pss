package com.lichkin.application.apis.api50006.P.n01;

import com.lichkin.framework.beans.impl.LKRequestPageBean;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class I extends LKRequestPageBean {

	private Boolean orderType;

	private String orderNo;

	private String productCode;

	private String productName;

	private String barcode;

	private String storageId;

	private String storageType;

}
