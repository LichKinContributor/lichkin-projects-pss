package com.lichkin.application.apis.api50001.U.n00;

import com.lichkin.framework.beans.impl.LKRequestIDBean;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class I extends LKRequestIDBean {

	private String supplierCode;

	private String supplierName;

	private String supplierType;

	private String address;

	private String linkmanName;

	private String linkmanContactWay;

	private String responsiblePerson;

	private String remarks;

}
