package com.lichkin.application.apis.api50204.P.n00;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import com.lichkin.application.utils.LKDictUtils;
import com.lichkin.application.utils.LKDictUtils4Activiti;
import com.lichkin.framework.db.beans.Condition;
import com.lichkin.framework.db.beans.Order;
import com.lichkin.framework.db.beans.QuerySQL;
import com.lichkin.framework.db.beans.SysEmployeeR;
import com.lichkin.framework.db.beans.SysPssSellOrderR;
import com.lichkin.framework.db.beans.SysPssSellReturnNotStockOutOrderR;
import com.lichkin.framework.db.beans.like;
import com.lichkin.framework.db.enums.LikeType;
import com.lichkin.framework.defines.enums.impl.ApprovalStatusEnum;
import com.lichkin.framework.defines.enums.impl.LKUsingStatusEnum;
import com.lichkin.springframework.controllers.ApiKeyValues;
import com.lichkin.springframework.entities.impl.SysEmployeeEntity;
import com.lichkin.springframework.entities.impl.SysPssSellOrderEntity;
import com.lichkin.springframework.entities.impl.SysPssSellReturnNotStockOutOrderEntity;
import com.lichkin.springframework.services.LKApiBusGetPageService;

@Service("SysPssSellReturnNotStockOutOrderP00Service")
public class S extends LKApiBusGetPageService<I, O, SysPssSellReturnNotStockOutOrderEntity> {

	@Override
	protected void initSQL(I sin, ApiKeyValues<I> params, QuerySQL sql) {
		// 主表
		sql.select(SysPssSellReturnNotStockOutOrderR.id);
		sql.select(SysPssSellReturnNotStockOutOrderR.insertTime);
		sql.select(SysPssSellReturnNotStockOutOrderR.approvalTime);
		sql.select(SysPssSellReturnNotStockOutOrderR.orderNo);
		sql.select(SysPssSellReturnNotStockOutOrderR.billDate);
		sql.select(SysPssSellReturnNotStockOutOrderR.orderAmount);

		// 关联表
		sql.innerJoin(SysPssSellOrderEntity.class, new Condition(SysPssSellReturnNotStockOutOrderR.orderId, SysPssSellOrderR.id));
		sql.select(SysPssSellOrderR.orderNo, "sellOrderNo");
		sql.select(SysPssSellOrderR.billDate, "sellBillDate");
		sql.select(SysPssSellOrderR.orderAmount, "sellOrderAmount");
		sql.innerJoin(SysEmployeeEntity.class, new Condition(0, SysEmployeeR.id, SysPssSellOrderR.salesId));
		sql.select(SysEmployeeR.userName, "salesName");

		sql.innerJoin(SysEmployeeEntity.class, new Condition(1, SysEmployeeR.id, SysPssSellReturnNotStockOutOrderR.salesId));
		sql.select(1, SysEmployeeR.userName, "returnedName");

		// 字典表
		int i = 0;
		LKDictUtils.usingStatus(sql, SysPssSellReturnNotStockOutOrderR.usingStatus, i++);
		LKDictUtils4Activiti.approvalStatus(sql, SysPssSellReturnNotStockOutOrderR.approvalStatus, i++);

		// 筛选条件（必填项）
		// addConditionId(sql, SysPssSellReturnNotStockOutOrderR.id, params.getId());
		// addConditionLocale(sql, SysPssSellReturnNotStockOutOrderR.locale, params.getLocale());
		addConditionCompId(true, sql, SysPssSellReturnNotStockOutOrderR.compId, params.getCompId(), params.getBusCompId());
		addConditionUsingStatus(true, params.getCompId(), sql, SysPssSellReturnNotStockOutOrderR.usingStatus, params.getUsingStatus(), LKUsingStatusEnum.USING);

		// 筛选条件（业务项）
		ApprovalStatusEnum approvalStatus = sin.getApprovalStatus();
		if (approvalStatus != null) {
			sql.eq(SysPssSellReturnNotStockOutOrderR.approvalStatus, approvalStatus);
		}

		String orderNo = sin.getOrderNo();
		if (StringUtils.isNotBlank(orderNo)) {
			sql.like(SysPssSellReturnNotStockOutOrderR.orderNo, LikeType.ALL, orderNo);
		}

		String sellOrderNo = sin.getSellOrderNo();
		if (StringUtils.isNotBlank(sellOrderNo)) {
			sql.like(SysPssSellOrderR.orderNo, LikeType.ALL, sellOrderNo);
		}

		String salesName = sin.getSalesName();
		if (StringUtils.isNotBlank(salesName)) {
			sql.where(new Condition(true, new like(0, SysEmployeeR.userName, LikeType.ALL, salesName)));
		}

		String returnedName = sin.getReturnedName();
		if (StringUtils.isNotBlank(returnedName)) {
			sql.where(new Condition(true, new like(1, SysEmployeeR.userName, LikeType.ALL, returnedName)));
		}

		String startDate = sin.getStartDate();
		if (StringUtils.isNotBlank(startDate)) {
			sql.gte(SysPssSellReturnNotStockOutOrderR.billDate, startDate);
		}

		String endDate = sin.getEndDate();
		if (StringUtils.isNotBlank(endDate)) {
			sql.lte(SysPssSellReturnNotStockOutOrderR.billDate, endDate);
		}

		// 排序条件
		sql.addOrders(new Order(SysPssSellReturnNotStockOutOrderR.id, false));
	}

}
