package com.lichkin.application.apis.api50206.P.n00;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import com.lichkin.application.utils.LKDictUtils;
import com.lichkin.application.utils.LKDictUtils4Activiti;
import com.lichkin.framework.db.beans.Condition;
import com.lichkin.framework.db.beans.Order;
import com.lichkin.framework.db.beans.QuerySQL;
import com.lichkin.framework.db.beans.SysEmployeeR;
import com.lichkin.framework.db.beans.SysPssSellReturnStockOutOrderR;
import com.lichkin.framework.db.beans.SysPssStorageR;
import com.lichkin.framework.db.enums.LikeType;
import com.lichkin.framework.defines.enums.impl.ApprovalStatusEnum;
import com.lichkin.framework.defines.enums.impl.LKUsingStatusEnum;
import com.lichkin.springframework.controllers.ApiKeyValues;
import com.lichkin.springframework.entities.impl.SysEmployeeEntity;
import com.lichkin.springframework.entities.impl.SysPssSellReturnStockOutOrderEntity;
import com.lichkin.springframework.entities.impl.SysPssStorageEntity;
import com.lichkin.springframework.services.LKApiBusGetPageService;

@Service("SysPssSellReturnStockOutOrderP00Service")
public class S extends LKApiBusGetPageService<I, O, SysPssSellReturnStockOutOrderEntity> {

	@Override
	protected void initSQL(I sin, ApiKeyValues<I> params, QuerySQL sql) {
		// 主表
		sql.select(SysPssSellReturnStockOutOrderR.id);
		sql.select(SysPssSellReturnStockOutOrderR.insertTime);
		sql.select(SysPssSellReturnStockOutOrderR.approvalTime);
		sql.select(SysPssSellReturnStockOutOrderR.orderNo);
		sql.select(SysPssSellReturnStockOutOrderR.billDate);
		sql.select(SysPssSellReturnStockOutOrderR.orderAmount);

		// 关联表
		sql.innerJoin(SysEmployeeEntity.class, new Condition(SysEmployeeR.id, SysPssSellReturnStockOutOrderR.salesId));
		sql.select(SysEmployeeR.userName, "returnedName");
		sql.innerJoin(SysPssStorageEntity.class, new Condition(SysPssSellReturnStockOutOrderR.storageId, SysPssStorageR.id));
		sql.select(SysPssStorageR.storageName);

		// 字典表
		int i = 0;
		LKDictUtils.usingStatus(sql, SysPssSellReturnStockOutOrderR.usingStatus, i++);
		LKDictUtils4Activiti.approvalStatus(sql, SysPssSellReturnStockOutOrderR.approvalStatus, i++);

		// 筛选条件（必填项）
		// addConditionId(sql, SysPssSellReturnStockOutOrderR.id, params.getId());
		// addConditionLocale(sql, SysPssSellReturnStockOutOrderR.locale, params.getLocale());
		addConditionCompId(true, sql, SysPssSellReturnStockOutOrderR.compId, params.getCompId(), params.getBusCompId());
		addConditionUsingStatus(true, params.getCompId(), sql, SysPssSellReturnStockOutOrderR.usingStatus, params.getUsingStatus(), LKUsingStatusEnum.USING);

		// 筛选条件（业务项）
		ApprovalStatusEnum approvalStatus = sin.getApprovalStatus();
		if (approvalStatus != null) {
			sql.eq(SysPssSellReturnStockOutOrderR.approvalStatus, approvalStatus);
		}

		String orderNo = sin.getOrderNo();
		if (StringUtils.isNotBlank(orderNo)) {
			sql.like(SysPssSellReturnStockOutOrderR.orderNo, LikeType.ALL, orderNo);
		}

		String returnedName = sin.getReturnedName();
		if (StringUtils.isNotBlank(returnedName)) {
			sql.like(SysEmployeeR.userName, LikeType.ALL, returnedName);
		}

		String storageId = sin.getStorageId();
		if (StringUtils.isNotBlank(storageId)) {
			sql.eq(SysPssSellReturnStockOutOrderR.storageId, storageId);
		}

		String startDate = sin.getStartDate();
		if (StringUtils.isNotBlank(startDate)) {
			sql.gte(SysPssSellReturnStockOutOrderR.billDate, startDate);
		}

		String endDate = sin.getEndDate();
		if (StringUtils.isNotBlank(endDate)) {
			sql.lte(SysPssSellReturnStockOutOrderR.billDate, endDate);
		}

		// 排序条件
		sql.addOrders(new Order(SysPssSellReturnStockOutOrderR.id, false));
	}

}
