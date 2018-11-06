package com.lichkin.framework.defines.beans.impl;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class OrderProduct {

	/** 产品序号 */
	private final String no;

	/** 产品名称 */
	private final String name;

	/** 产品数量 */
	private final String count;

	/** 单价 */
	private final String price;

	/** 优惠金额 */
	private final String reducePrice;

}