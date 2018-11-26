package com.lichkin.application.apis.api50003.L.n00;

import com.lichkin.framework.beans.impl.LKRequestBean;
import com.lichkin.framework.defines.entities.I_CompId;
import com.lichkin.framework.defines.enums.impl.LKUsingStatusEnum;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class I extends LKRequestBean implements I_CompId {

	private LKUsingStatusEnum usingStatus;

	private String compId;

	private String productCategory;

	private String productCode;

	private String productName;

	private String barcode;

}
