package com.lichkin.application.apis.api50002.S.n00;

import com.lichkin.framework.beans.impl.LKRequestBean;
import com.lichkin.framework.defines.enums.impl.LKUsingStatusEnum;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class I extends LKRequestBean {

	/** 公司ID */
	private String compId;

	/** 在用状态 */
	private LKUsingStatusEnum usingStatus;

	/** 类别名称 */
	private String categoryName;

}
