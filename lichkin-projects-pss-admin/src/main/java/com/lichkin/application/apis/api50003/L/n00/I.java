package com.lichkin.application.apis.api50003.L.n00;

import com.lichkin.framework.beans.impl.LKRequestBean;
import com.lichkin.framework.defines.entities.I_CompId;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class I extends LKRequestBean implements I_CompId {

	private String productCategory;

	private String productCode;

	private String productName;

	private String barcode;

}
