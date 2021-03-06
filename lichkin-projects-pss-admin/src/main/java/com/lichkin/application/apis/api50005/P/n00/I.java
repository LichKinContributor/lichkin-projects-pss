package com.lichkin.application.apis.api50005.P.n00;

import com.lichkin.framework.beans.impl.LKRequestPageBean;
import com.lichkin.framework.defines.entities.I_CompId;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class I extends LKRequestPageBean implements I_CompId {

	/** 收银员姓名 */
	private String cashierName;

	/** 门店名称 */
	private String storeName;

}
