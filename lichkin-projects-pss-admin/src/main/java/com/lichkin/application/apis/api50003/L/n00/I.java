package com.lichkin.application.apis.api50003.L.n00;

import com.lichkin.framework.beans.impl.LKRequestBean;
import com.lichkin.framework.defines.enums.impl.LKUsingStatusEnum;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class I extends LKRequestBean {

	private LKUsingStatusEnum usingStatus;

	private String compId;

	private String productCategory;

	private String productCode;

	private String productName;

	private String barcode;

}
