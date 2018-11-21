package com.lichkin.application.utils;

import com.lichkin.framework.db.beans.QuerySQL;
import com.lichkin.framework.defines.LKFrameworkStatics;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

/**
 * 字典工具类
 * @author SuZhou LichKin Information Technology Co., Ltd.
 */
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class LKDictUtils4Pss extends LKDictUtils {

	/**
	 * 连接字典表（供应商类型）
	 * @param sql SQL语句对象
	 * @param columnResId 列资源ID
	 * @param tableIdx 字典表序号（从0开始）
	 */
	public static void supplierType(QuerySQL sql, int columnResId, int tableIdx) {
		leftJoinDictionary(sql, "supplierType", LKFrameworkStatics.LichKin, "PSS_SUPPLIER_TYPE", columnResId, tableIdx);
	}


	/**
	 * 连接字典表（产品单位）
	 * @param sql SQL语句对象
	 * @param columnResId 列资源ID
	 * @param tableIdx 字典表序号（从0开始）
	 */
	public static void pssProductUnit(QuerySQL sql, int columnResId, int tableIdx) {
		leftJoinDictionary(sql, "unit", LKFrameworkStatics.LichKin, "PSS_PRODUCT_UNIT", columnResId, tableIdx);
	}


	/**
	 * 连接字典表（入库状态）
	 * @param sql SQL语句对象
	 * @param columnResId 列资源ID
	 * @param tableIdx 字典表序号（从0开始）
	 */
	public static void inventoryStatus(QuerySQL sql, int columnResId, int tableIdx) {
		leftJoinDictionary(sql, "inventoryStatus", LKFrameworkStatics.LichKin, "PSS_INVENTORY_STATUS", columnResId, tableIdx);
	}


	/**
	 * 连接字典表（其它入库单类型）
	 * @param sql SQL语句对象
	 * @param compId 公司ID
	 * @param columnResId 列资源ID
	 * @param tableIdx 字典表序号（从0开始）
	 */
	public static void storageTypeIn(QuerySQL sql, String compId, int columnResId, int tableIdx) {
		leftJoinDictionary(sql, "storageType", compId, "PSS_OTHER_STOCK_IN", columnResId, tableIdx);
	}


	/**
	 * 连接字典表（其它出库单类型）
	 * @param sql SQL语句对象
	 * @param compId 公司ID
	 * @param columnResId 列资源ID
	 * @param tableIdx 字典表序号（从0开始）
	 */
	public static void storageTypeOut(QuerySQL sql, String compId, int columnResId, int tableIdx) {
		leftJoinDictionary(sql, "storageType", compId, "PSS_OTHER_STOCK_OUT", columnResId, tableIdx);
	}


	/**
	 * 连接字典表（盘点状态）
	 * @param sql SQL语句对象
	 * @param columnResId 列资源ID
	 * @param tableIdx 字典表序号（从0开始）
	 */
	public static void checkOrderStatus(QuerySQL sql, int columnResId, int tableIdx) {
		leftJoinDictionary(sql, "usingStatus", LKFrameworkStatics.LichKin, "PSS_CHECK_ORDER_STATUS", columnResId, tableIdx);
	}


	/**
	 * 连接字典表（出库状态）
	 * @param sql SQL语句对象
	 * @param columnResId 列资源ID
	 * @param tableIdx 字典表序号（从0开始）
	 */
	public static void inventoryOutStatus(QuerySQL sql, int columnResId, int tableIdx) {
		leftJoinDictionary(sql, "inventoryStatus", LKFrameworkStatics.LichKin, "PSS_INVENTORY_OUT_STATUS", columnResId, tableIdx);
	}

}
