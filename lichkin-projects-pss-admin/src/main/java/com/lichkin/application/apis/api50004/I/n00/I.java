package com.lichkin.application.apis.api50004.I.n00;

import com.lichkin.framework.beans.impl.LKRequestBean;
import com.lichkin.framework.defines.entities.I_CompId;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class I extends LKRequestBean implements I_CompId {

	private String storeCode;

	private String storeName;

	private String storageId;

	private String address;

	private String responsiblePerson;

	private String remarks;

}
