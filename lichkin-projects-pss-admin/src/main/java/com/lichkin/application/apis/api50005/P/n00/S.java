package com.lichkin.application.apis.api50005.P.n00;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import com.lichkin.framework.db.beans.Condition;
import com.lichkin.framework.db.beans.Order;
import com.lichkin.framework.db.beans.QuerySQL;
import com.lichkin.framework.db.beans.SysEmployeeR;
import com.lichkin.framework.db.beans.SysPssStoreCashierR;
import com.lichkin.framework.db.beans.SysPssStoreR;
import com.lichkin.framework.db.enums.LikeType;
import com.lichkin.springframework.controllers.ApiKeyValues;
import com.lichkin.springframework.entities.impl.SysEmployeeEntity;
import com.lichkin.springframework.entities.impl.SysPssStoreCashierEntity;
import com.lichkin.springframework.entities.impl.SysPssStoreEntity;
import com.lichkin.springframework.services.LKApiBusGetPageService;

@Service("SysPssStoreCashierP00Service")
public class S extends LKApiBusGetPageService<I, O, SysPssStoreCashierEntity> {

	@Override
	protected void initSQL(I sin, ApiKeyValues<I> params, QuerySQL sql) {
		// 主表
		sql.select(SysPssStoreCashierR.id);

		// 关联表
		sql.leftJoin(SysEmployeeEntity.class, new Condition(SysPssStoreCashierR.cashier, SysEmployeeR.id));
		sql.select(SysEmployeeR.userName, "cashierName");
		sql.leftJoin(SysPssStoreEntity.class, new Condition(SysPssStoreCashierR.storeId, SysPssStoreR.id));
		sql.select(SysPssStoreR.storeName);

		// 筛选条件（必填项）
		// 公司ID
		params.addConditionCompId(false, sql, SysPssStoreCashierR.compId);

		// 筛选条件（业务项）
		String cashierName = sin.getCashierName();
		if (StringUtils.isNotBlank(cashierName)) {
			sql.like(SysEmployeeR.userName, LikeType.ALL, cashierName);
		}

		String storeName = sin.getStoreName();
		if (StringUtils.isNotBlank(storeName)) {
			sql.like(SysPssStoreR.storeName, LikeType.ALL, storeName);
		}

		// 排序条件
		sql.addOrders(new Order(SysPssStoreCashierR.id, false));
	}

}
