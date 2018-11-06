package com.lichkin.framework.defines.enums.impl;

import com.lichkin.framework.defines.enums.LKEnum;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * 支付类型状态枚举
 * @author SuZhou LichKin Information Technology Co., Ltd.
 */
@Getter
@RequiredArgsConstructor
public enum PayTypeEnum implements LKEnum {

	/** 支付宝 */
	ALIPAY("支付宝"),

	/** 微信 */
	WECHAT("微信"),

	/** 现金 */
	CASH("现金"),

	;

	private final String name;

}
