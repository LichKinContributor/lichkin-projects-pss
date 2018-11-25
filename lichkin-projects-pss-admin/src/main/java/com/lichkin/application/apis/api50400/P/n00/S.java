package com.lichkin.application.apis.api50400.P.n00;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import com.lichkin.application.utils.LKDictUtils;
import com.lichkin.application.utils.LKDictUtils4Activiti;
import com.lichkin.framework.db.beans.Condition;
import com.lichkin.framework.db.beans.Order;
import com.lichkin.framework.db.beans.QuerySQL;
import com.lichkin.framework.db.beans.SysPssAllotOrderR;
import com.lichkin.framework.db.beans.SysPssStorageR;
import com.lichkin.framework.db.enums.LikeType;
import com.lichkin.framework.defines.enums.impl.ApprovalStatusEnum;
import com.lichkin.springframework.controllers.ApiKeyValues;
import com.lichkin.springframework.entities.impl.SysPssAllotOrderEntity;
import com.lichkin.springframework.entities.impl.SysPssStorageEntity;
import com.lichkin.springframework.services.LKApiBusGetPageService;

@Service("SysPssAllotOrderP00Service")
public class S extends LKApiBusGetPageService<I, O, SysPssAllotOrderEntity> {

	@Override
	protected void initSQL(I sin, ApiKeyValues<I> params, QuerySQL sql) {
		// 主表
		sql.select(SysPssAllotOrderR.id);
		sql.select(SysPssAllotOrderR.insertTime);
		sql.select(SysPssAllotOrderR.approvalTime);
		sql.select(SysPssAllotOrderR.orderNo);
		sql.select(SysPssAllotOrderR.billDate);

		// 关联表
		sql.innerJoin(SysPssStorageEntity.class, new Condition(0, SysPssStorageR.id, SysPssAllotOrderR.outStorageId));
		sql.select(0, SysPssStorageR.storageName, "outStorageName");
		sql.innerJoin(SysPssStorageEntity.class, new Condition(1, SysPssStorageR.id, SysPssAllotOrderR.inStorageId));
		sql.select(1, SysPssStorageR.storageName, "inStorageName");

		// 字典表
		int i = 0;
		LKDictUtils.usingStatus(sql, SysPssAllotOrderR.usingStatus, i++);
		LKDictUtils4Activiti.approvalStatus(sql, SysPssAllotOrderR.approvalStatus, i++);

		// 筛选条件（必填项）
		// 公司ID
		params.addConditionCompId(false, sql, SysPssAllotOrderR.compId);
		// 在用状态
		params.addConditionUsingStatus(sql, SysPssAllotOrderR.usingStatus, sin.getUsingStatus());

		// 筛选条件（业务项）
		ApprovalStatusEnum approvalStatus = sin.getApprovalStatus();
		if (approvalStatus != null) {
			sql.eq(SysPssAllotOrderR.approvalStatus, approvalStatus);
		}

		String orderNo = sin.getOrderNo();
		if (StringUtils.isNotBlank(orderNo)) {
			sql.like(SysPssAllotOrderR.orderNo, LikeType.ALL, orderNo);
		}

		String outStorageId = sin.getOutStorageId();
		if (StringUtils.isNotBlank(outStorageId)) {
			sql.eq(SysPssAllotOrderR.outStorageId, outStorageId);
		}

		String inStorageId = sin.getInStorageId();
		if (StringUtils.isNotBlank(inStorageId)) {
			sql.eq(SysPssAllotOrderR.inStorageId, inStorageId);
		}

		String startDate = sin.getStartDate();
		if (StringUtils.isNotBlank(startDate)) {
			sql.gte(SysPssAllotOrderR.billDate, startDate);
		}

		String endDate = sin.getEndDate();
		if (StringUtils.isNotBlank(endDate)) {
			sql.lte(SysPssAllotOrderR.billDate, endDate);
		}

		// 排序条件
		sql.addOrders(new Order(SysPssAllotOrderR.id, false));
	}

}
