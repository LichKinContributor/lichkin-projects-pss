package com.lichkin.application.apis.api50202.P.n00;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import com.lichkin.application.utils.LKDictUtils;
import com.lichkin.application.utils.LKDictUtils4Activiti;
import com.lichkin.framework.db.beans.Condition;
import com.lichkin.framework.db.beans.Order;
import com.lichkin.framework.db.beans.QuerySQL;
import com.lichkin.framework.db.beans.SysEmployeeR;
import com.lichkin.framework.db.beans.SysPssSellOrderR;
import com.lichkin.framework.db.beans.SysPssSellStockOrderR;
import com.lichkin.framework.db.beans.SysPssStorageR;
import com.lichkin.framework.db.enums.LikeType;
import com.lichkin.framework.defines.enums.impl.ApprovalStatusEnum;
import com.lichkin.springframework.controllers.ApiKeyValues;
import com.lichkin.springframework.entities.impl.SysEmployeeEntity;
import com.lichkin.springframework.entities.impl.SysPssSellOrderEntity;
import com.lichkin.springframework.entities.impl.SysPssSellStockOrderEntity;
import com.lichkin.springframework.entities.impl.SysPssStorageEntity;
import com.lichkin.springframework.services.LKApiBusGetPageService;

@Service("SysPssSellStockOrderP00Service")
public class S extends LKApiBusGetPageService<I, O, SysPssSellStockOrderEntity> {

	@Override
	protected void initSQL(I sin, ApiKeyValues<I> params, QuerySQL sql) {
		// 主表
		sql.select(SysPssSellStockOrderR.id);
		sql.select(SysPssSellStockOrderR.insertTime);
		sql.select(SysPssSellStockOrderR.approvalTime);
		sql.select(SysPssSellStockOrderR.orderNo);
		sql.select(SysPssSellStockOrderR.billDate);

		// 关联表
		sql.innerJoin(SysPssStorageEntity.class, new Condition(SysPssSellStockOrderR.storageId, SysPssStorageR.id));
		sql.select(SysPssStorageR.storageName);
		sql.innerJoin(SysPssSellOrderEntity.class, new Condition(SysPssSellStockOrderR.orderId, SysPssSellOrderR.id));
		sql.select(SysPssSellOrderR.orderNo, "sellOrderNo");
		sql.select(SysPssSellOrderR.billDate, "sellBillDate");
		sql.select(SysPssSellOrderR.orderAmount, "sellOrderAmount");
		sql.innerJoin(SysEmployeeEntity.class, new Condition(SysPssSellOrderR.salesId, SysEmployeeR.id));
		sql.select(SysEmployeeR.userName, "salesName");

		// 字典表
		int i = 0;
		LKDictUtils.usingStatus(sql, SysPssSellStockOrderR.usingStatus, i++);
		LKDictUtils4Activiti.approvalStatus(sql, SysPssSellStockOrderR.approvalStatus, i++);

		// 筛选条件（必填项）
		// 公司ID
		params.addConditionCompId(false, sql, SysPssSellStockOrderR.compId);
		// 在用状态
		params.addConditionUsingStatus(sql, SysPssSellStockOrderR.usingStatus, sin.getUsingStatus());

		// 筛选条件（业务项）
		ApprovalStatusEnum approvalStatus = sin.getApprovalStatus();
		if (approvalStatus != null) {
			sql.eq(SysPssSellStockOrderR.approvalStatus, approvalStatus);
		}

		String orderNo = sin.getOrderNo();
		if (StringUtils.isNotBlank(orderNo)) {
			sql.like(SysPssSellStockOrderR.orderNo, LikeType.ALL, orderNo);
		}

		String storageId = sin.getStorageId();
		if (StringUtils.isNotBlank(storageId)) {
			sql.eq(SysPssSellStockOrderR.storageId, storageId);
		}

		Boolean orderType = sin.getOrderType();
		if (orderType != null) {
			sql.eq(SysPssSellStockOrderR.orderType, orderType);
		}

		String sellOrderNo = sin.getSellOrderNo();
		if (StringUtils.isNotBlank(sellOrderNo)) {
			sql.like(SysPssSellOrderR.orderNo, LikeType.ALL, sellOrderNo);
		}

		String salesName = sin.getSalesName();
		if (StringUtils.isNotBlank(salesName)) {
			sql.like(SysEmployeeR.userName, LikeType.ALL, salesName);
		}

		String startDate = sin.getStartDate();
		if (StringUtils.isNotBlank(startDate)) {
			sql.gte(SysPssSellStockOrderR.billDate, startDate);
		}

		String endDate = sin.getEndDate();
		if (StringUtils.isNotBlank(endDate)) {
			sql.lte(SysPssSellStockOrderR.billDate, endDate);
		}

		// 排序条件
		sql.addOrders(new Order(SysPssSellStockOrderR.id, false));
	}

}
