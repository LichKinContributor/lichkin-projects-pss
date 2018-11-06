package com.lichkin.application.apis.api50000.P.n00;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class O {

	private String id;

	private String usingStatus;

	private String usingStatusDictCode;// for usingStatus

	private String insertTime;

	private String storageCode;

	private String storageName;

	private String address;

	private String remarks;

	/** 负责人姓名 */
	private String responsiblePerson;

	/** 负责人联系电话 */
	private String cellphone;

}
