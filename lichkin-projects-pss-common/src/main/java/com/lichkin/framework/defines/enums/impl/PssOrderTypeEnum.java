package com.lichkin.framework.defines.enums.impl;

import com.lichkin.framework.defines.enums.LKEnum;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * 订单类型枚举
 * @author SuZhou LichKin Information Technology Co., Ltd.
 */
@Getter
@RequiredArgsConstructor
public enum PssOrderTypeEnum implements LKEnum {

	/** 正常采购入库 */
	PSS_PURCHASE_STORAGE_IN("CGRKD"),

	/** 其它入库 */
	PSS_OTHER_STORAGE_IN("QTRKD"),

	/** 正常销售出库 */
	PSS_SELL_STORAGE_OUT("XSCKD"),

	/** 其它出库 */
	PSS_OTHER_STORAGE_OUT("QTCKD"),

	/** 退货 */
	PSS_RETURN_STORAGE_OUT("THD");

	private final String prefix;

}
