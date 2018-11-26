package com.lichkin.application.apis.api50300.P.n00;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import com.lichkin.application.utils.LKDictUtils4Activiti;
import com.lichkin.application.utils.LKDictUtils4Pss;
import com.lichkin.framework.db.beans.Condition;
import com.lichkin.framework.db.beans.Order;
import com.lichkin.framework.db.beans.QuerySQL;
import com.lichkin.framework.db.beans.SysPssOtherStockOrderR;
import com.lichkin.framework.db.beans.SysPssStorageR;
import com.lichkin.framework.db.enums.LikeType;
import com.lichkin.framework.defines.enums.impl.ApprovalStatusEnum;
import com.lichkin.framework.defines.enums.impl.LKUsingStatusEnum;
import com.lichkin.springframework.controllers.ApiKeyValues;
import com.lichkin.springframework.entities.impl.SysPssOtherStockOrderEntity;
import com.lichkin.springframework.entities.impl.SysPssStorageEntity;
import com.lichkin.springframework.services.LKApiBusGetPageService;

@Service("SysPssOtherStockOrderP00Service")
public class S extends LKApiBusGetPageService<I, O, SysPssOtherStockOrderEntity> {

	@Override
	protected void initSQL(I sin, ApiKeyValues<I> params, QuerySQL sql) {
		// 主表
		sql.select(SysPssOtherStockOrderR.id);
		sql.select(SysPssOtherStockOrderR.insertTime);
		sql.select(SysPssOtherStockOrderR.approvalTime);
		sql.select(SysPssOtherStockOrderR.orderNo);
		sql.select(SysPssOtherStockOrderR.billDate);

		// 关联表
		sql.innerJoin(SysPssStorageEntity.class, new Condition(SysPssOtherStockOrderR.storageId, SysPssStorageR.id));
		sql.select(SysPssStorageR.storageName);

		Boolean orderType = sin.getOrderType();
		// 字典表
		int i = 0;
		if (orderType) {
			LKDictUtils4Pss.storageTypeIn(sql, params.getCompId(), SysPssOtherStockOrderR.storageType, i++);
		} else {
			LKDictUtils4Pss.storageTypeOut(sql, params.getCompId(), SysPssOtherStockOrderR.storageType, i++);
		}
		LKDictUtils4Activiti.approvalStatus(sql, SysPssOtherStockOrderR.approvalStatus, i++);

		// 筛选条件（必填项）
//		addConditionId(sql, SysPssOtherStockOrderR.id, params.getId());
//		addConditionLocale(sql, SysPssOtherStockOrderR.locale, params.getLocale());
		addConditionCompId(true, sql, SysPssOtherStockOrderR.compId, params.getCompId(), params.getBusCompId());
		addConditionUsingStatus(params.getCompId(), sql, SysPssOtherStockOrderR.usingStatus, params.getUsingStatus(), LKUsingStatusEnum.USING);

		if (orderType != null) {
			sql.eq(SysPssOtherStockOrderR.orderType, orderType);
		}

		// 筛选条件（业务项）
		ApprovalStatusEnum approvalStatus = sin.getApprovalStatus();
		if (approvalStatus != null) {
			sql.eq(SysPssOtherStockOrderR.approvalStatus, approvalStatus);
		}

		String orderNo = sin.getOrderNo();
		if (StringUtils.isNotBlank(orderNo)) {
			sql.like(SysPssOtherStockOrderR.orderNo, LikeType.ALL, orderNo);
		}

		String storageType = sin.getStorageType();
		if (StringUtils.isNotBlank(storageType)) {
			sql.eq(SysPssOtherStockOrderR.storageType, storageType);
		}

		String storageId = sin.getStorageId();
		if (StringUtils.isNotBlank(storageId)) {
			sql.eq(SysPssOtherStockOrderR.storageId, storageId);
		}

		String startDate = sin.getStartDate();
		if (StringUtils.isNotBlank(startDate)) {
			sql.gte(SysPssOtherStockOrderR.billDate, startDate);
		}

		String endDate = sin.getEndDate();
		if (StringUtils.isNotBlank(endDate)) {
			sql.lte(SysPssOtherStockOrderR.billDate, endDate);
		}

		// 排序条件
		sql.addOrders(new Order(SysPssOtherStockOrderR.id, false));
	}

}
