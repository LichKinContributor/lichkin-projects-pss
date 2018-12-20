package com.lichkin.defines;

/**
 * 常量定义
 * @author SuZhou LichKin Information Technology Co., Ltd.
 */
public interface PssStatics {

	/** 采购单 */
	public static final String PSS_PURCHASE_ORDER = "PSS_PURCHASE_ORDER";

	/** 采购入库单（需修改库存） */
	public static final String PSS_PURCHASE_STOCK_IN_ORDER = "PSS_PURCHASE_STOCK_IN_ORDER";

	/** 采购未入库退货单（需修改采购单） */
	public static final String PSS_PURCHASE_RETURN_NOT_STOCK_IN_ORDER = "PSS_PURCHASE_RETURN_NOT_STOCK_IN_ORDER";

	/** 采购已入库退货单（需修改库存） */
	public static final String PSS_PURCHASE_RETURN_STOCK_IN_ORDER = "PSS_PURCHASE_RETURN_STOCK_IN_ORDER";

	/** 销售单 */
	public static final String PSS_SELL_ORDER = "PSS_SELL_ORDER";

	/** 销售出库单（需修改库存） */
	public static final String PSS_SELL_STOCK_OUT_ORDER = "PSS_SELL_STOCK_OUT_ORDER";

	/** 销售未出库退货单（需修改销售单） */
	public static final String PSS_SELL_RETURN_NOT_STOCK_OUT_ORDER = "PSS_SELL_RETURN_NOT_STOCK_OUT_ORDER";

	/** 销售已出库退货单（需修改库存） */
	public static final String PSS_SELL_RETURN_STOCK_OUT_ORDER = "PSS_SELL_RETURN_STOCK_OUT_ORDER";

	/** 其它入库单（需修改库存） */
	public static final String PSS_OTHER_STOCK_IN_ORDER = "PSS_OTHER_STOCK_IN_ORDER";

	/** 其它出库单（需修改库存） */
	public static final String PSS_OTHER_STOCK_OUT_ORDER = "PSS_OTHER_STOCK_OUT_ORDER";

	/** 调拨单（需修改库存） */
	public static final String PSS_ALLOT_ORDER = "PSS_ALLOT_ORDER";

	/** 盘点单（需修改库存） */
	public static final String PSS_STOCK_CHECK_ORDER = "PSS_STOCK_CHECK_ORDER";

	// 以下不是实际订单类型
	/** 采购库存单（通用定义使用） */
	public static final String PSS_PURCHASE_STOCK_ORDER = "PSS_PURCHASE_STOCK_ORDER";

	/** 销售库存单（通用定义使用） */
	public static final String PSS_SELL_STOCK_ORDER = "PSS_SELL_STOCK_ORDER";

	/** 其它库存单（通用定义使用） */
	public static final String PSS_OTHER_STOCK_ORDER = "PSS_OTHER_STOCK_ORDER";

}
