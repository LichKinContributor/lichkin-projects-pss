package com.lichkin.application.apis.api50101.P.n00;

import com.lichkin.framework.beans.impl.LKRequestPageBean;
import com.lichkin.framework.defines.entities.I_CompId;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class I extends LKRequestPageBean implements I_CompId {

	private String orderNo;

	private String supplierName;

	private String purchaserName;

	private String productCode;

	private String productName;

	private String barcode;

}
