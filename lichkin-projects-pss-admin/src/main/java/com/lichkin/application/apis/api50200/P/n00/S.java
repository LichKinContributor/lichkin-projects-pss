package com.lichkin.application.apis.api50200.P.n00;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import com.lichkin.application.utils.LKDictUtils;
import com.lichkin.application.utils.LKDictUtils4Activiti;
import com.lichkin.application.utils.LKDictUtils4Pss;
import com.lichkin.framework.db.beans.Condition;
import com.lichkin.framework.db.beans.Order;
import com.lichkin.framework.db.beans.QuerySQL;
import com.lichkin.framework.db.beans.SysEmployeeR;
import com.lichkin.framework.db.beans.SysPssSellOrderR;
import com.lichkin.framework.db.enums.LikeType;
import com.lichkin.framework.defines.enums.impl.ApprovalStatusEnum;
import com.lichkin.framework.defines.enums.impl.InventoryStatusEnum;
import com.lichkin.framework.defines.enums.impl.LKUsingStatusEnum;
import com.lichkin.springframework.controllers.ApiKeyValues;
import com.lichkin.springframework.entities.impl.SysEmployeeEntity;
import com.lichkin.springframework.entities.impl.SysPssSellOrderEntity;
import com.lichkin.springframework.services.LKApiBusGetPageService;

@Service("SysPssSellOrderP00Service")
public class S extends LKApiBusGetPageService<I, O, SysPssSellOrderEntity> {

	@Override
	protected void initSQL(I sin, ApiKeyValues<I> params, QuerySQL sql) {
		// 主表
		sql.select(SysPssSellOrderR.id);
		sql.select(SysPssSellOrderR.insertTime);
		sql.select(SysPssSellOrderR.approvalTime);
		sql.select(SysPssSellOrderR.orderNo);
		sql.select(SysPssSellOrderR.billDate);
		sql.select(SysPssSellOrderR.orderAmount);

		// 关联表
		sql.innerJoin(SysEmployeeEntity.class, new Condition(SysPssSellOrderR.salesId, SysEmployeeR.id));
		sql.select(SysEmployeeR.userName, "salesName");

		// 字典表
		int i = 0;
		LKDictUtils.usingStatus(sql, SysPssSellOrderR.usingStatus, i++);
		LKDictUtils4Activiti.approvalStatus(sql, SysPssSellOrderR.approvalStatus, i++);
		LKDictUtils4Pss.inventoryOutStatus(sql, SysPssSellOrderR.inventoryStatus, i++);

		// 筛选条件（必填项）
//		addConditionId(sql, SysPssSellOrderR.id, params.getId());
//		addConditionLocale(sql, SysPssSellOrderR.locale, params.getLocale());
		addConditionCompId(true, sql, SysPssSellOrderR.compId, params.getCompId(), params.getBusCompId());
		addConditionUsingStatus(true,params.getCompId(), sql, SysPssSellOrderR.usingStatus, params.getUsingStatus(), LKUsingStatusEnum.USING);

		// 筛选条件（业务项）
		ApprovalStatusEnum approvalStatus = sin.getApprovalStatus();
		if (approvalStatus != null) {
			sql.eq(SysPssSellOrderR.approvalStatus, approvalStatus);
		}

		String orderNo = sin.getOrderNo();
		if (StringUtils.isNotBlank(orderNo)) {
			sql.like(SysPssSellOrderR.orderNo, LikeType.ALL, orderNo);
		}

		InventoryStatusEnum inventoryStatus = sin.getInventoryStatus();
		if (inventoryStatus != null) {
			sql.eq(SysPssSellOrderR.inventoryStatus, inventoryStatus);
		}

		String salesName = sin.getSalesName();
		if (StringUtils.isNotBlank(salesName)) {
			sql.like(SysEmployeeR.userName, LikeType.ALL, salesName);
		}

		String startDate = sin.getStartDate();
		if (StringUtils.isNotBlank(startDate)) {
			sql.gte(SysPssSellOrderR.billDate, startDate);
		}

		String endDate = sin.getEndDate();
		if (StringUtils.isNotBlank(endDate)) {
			sql.lte(SysPssSellOrderR.billDate, endDate);
		}

		// 排序条件
		sql.addOrders(new Order(SysPssSellOrderR.id, false));
	}

}
