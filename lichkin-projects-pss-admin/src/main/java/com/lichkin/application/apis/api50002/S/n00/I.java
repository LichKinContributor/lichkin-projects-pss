package com.lichkin.application.apis.api50002.S.n00;

import com.lichkin.framework.beans.impl.LKRequestBean;
import com.lichkin.framework.defines.entities.I_CompId;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class I extends LKRequestBean implements I_CompId {

	/** 类别名称 */
	private String categoryName;

}
