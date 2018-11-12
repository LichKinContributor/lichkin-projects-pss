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

	/** 采购 */
	PSS_PURCHASE,

	/** 采购退货 */
	PSS_PURCHASE_RETURN,

	/** 采购入库 */
	PSS_PURCHASE_STORAGE_IN,

	/** 采购出库（退货） */
	PSS_PURCHASE_STORAGE_OUT,

	/** 销售 */
	PSS_SELL,

	/** 销售退货 */
	PSS_SELL_RETURN,

	/** 销售出库 */
	PSS_SELL_STORAGE_OUT,

	/** 销售入库（退货） */
	PSS_SELL_STORAGE_IN,

	/** 其它入库 */
	PSS_OTHER_STORAGE_IN,

	/** 其它出库 */
	PSS_OTHER_STORAGE_OUT,

	/** 调拨单 */
	PSS_ALLOT,

	/** 盘点单 */
	PSS_CHECK;

}
