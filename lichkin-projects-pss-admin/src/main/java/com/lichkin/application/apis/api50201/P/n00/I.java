package com.lichkin.application.apis.api50201.P.n00;

import com.lichkin.framework.beans.impl.LKRequestPageBean;
import com.lichkin.framework.defines.entities.I_CompId;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class I extends LKRequestPageBean implements I_CompId {

	private String compId;

	private String orderNo;

	private String salesName;

	private String startDate;

	private String endDate;

	private String productCode;

	private String productName;

	private String barcode;

}
