package com.lichkin.application.apis.api50000.U.n00;

import com.lichkin.framework.beans.impl.LKRequestIDBean;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class I extends LKRequestIDBean {

	private String storageCode;

	private String storageName;

	private String address;

	private String responsiblePerson;

	private String remarks;

}
