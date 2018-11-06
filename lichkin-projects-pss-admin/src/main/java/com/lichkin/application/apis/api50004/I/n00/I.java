package com.lichkin.application.apis.api50004.I.n00;

import com.lichkin.framework.beans.impl.LKRequestBean;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class I extends LKRequestBean {

	private String compId;

	private String storeCode;

	private String storeName;

	private String storageId;

	private String address;

	private String responsiblePerson;

	private String remarks;

}
