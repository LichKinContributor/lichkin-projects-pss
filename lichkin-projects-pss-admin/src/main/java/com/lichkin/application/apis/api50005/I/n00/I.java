package com.lichkin.application.apis.api50005.I.n00;

import com.lichkin.framework.beans.impl.LKRequestBean;
import com.lichkin.framework.defines.entities.I_CompId;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class I extends LKRequestBean implements I_CompId {

	private String cashier;

	private String storeId;

}
