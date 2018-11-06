package com.lichkin.framework.defines.beans.impl;

import com.lichkin.framework.defines.enums.impl.PayTypeEnum;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class PayPrice {

	/** 支付类型 */
	private final PayTypeEnum payType;

	/** 支付金额 */
	private final String price;

}
