package com.lichkin.application.apis.api50000.I.n00;

import com.lichkin.framework.beans.impl.LKRequestBean;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class I extends LKRequestBean {

	private String storageCode;

	private String storageName;

	private String address;

	private String responsiblePerson;

	private String remarks;

}
