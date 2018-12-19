package com.lichkin.application.apis.api50600.P.n00;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import com.lichkin.application.utils.LKDictUtils;
import com.lichkin.application.utils.LKDictUtils4Activiti;
import com.lichkin.framework.db.beans.Condition;
import com.lichkin.framework.db.beans.Order;
import com.lichkin.framework.db.beans.QuerySQL;
import com.lichkin.framework.db.beans.SysEmployeeR;
import com.lichkin.framework.db.beans.SysPssPurchaseOrderR;
import com.lichkin.framework.db.beans.SysPssPurchaseReturnOrderR;
import com.lichkin.framework.db.beans.SysPssSupplierR;
import com.lichkin.framework.db.beans.like;
import com.lichkin.framework.db.enums.LikeType;
import com.lichkin.framework.defines.enums.impl.ApprovalStatusEnum;
import com.lichkin.framework.defines.enums.impl.LKUsingStatusEnum;
import com.lichkin.springframework.controllers.ApiKeyValues;
import com.lichkin.springframework.entities.impl.SysEmployeeEntity;
import com.lichkin.springframework.entities.impl.SysPssPurchaseOrderEntity;
import com.lichkin.springframework.entities.impl.SysPssPurchaseReturnOrderEntity;
import com.lichkin.springframework.entities.impl.SysPssSupplierEntity;
import com.lichkin.springframework.services.LKApiBusGetPageService;

@Service("SysPssPurchaseReturnOrderP00Service")
public class S extends LKApiBusGetPageService<I, O, SysPssPurchaseReturnOrderEntity> {

	@Override
	protected void initSQL(I sin, ApiKeyValues<I> params, QuerySQL sql) {
		// 主表
		sql.select(SysPssPurchaseReturnOrderR.id);
		sql.select(SysPssPurchaseReturnOrderR.insertTime);
		sql.select(SysPssPurchaseReturnOrderR.approvalTime);
		sql.select(SysPssPurchaseReturnOrderR.orderNo);
		sql.select(SysPssPurchaseReturnOrderR.billDate);
		sql.select(SysPssPurchaseReturnOrderR.orderAmount);

		// 关联表
		sql.innerJoin(SysPssPurchaseOrderEntity.class, new Condition(SysPssPurchaseReturnOrderR.orderId, SysPssPurchaseOrderR.id));
		sql.select(SysPssPurchaseOrderR.orderNo, "purchaseOrderNo");
		sql.select(SysPssPurchaseOrderR.billDate, "purchaserBillDate");
		sql.select(SysPssPurchaseOrderR.orderAmount, "purchaseOrderAmount");
		sql.innerJoin(SysEmployeeEntity.class, new Condition(0, SysEmployeeR.id, SysPssPurchaseOrderR.purchaserId));
		sql.select(0, SysEmployeeR.userName, "purchaserName");

		sql.innerJoin(SysPssSupplierEntity.class, new Condition(SysPssPurchaseReturnOrderR.supplierId, SysPssSupplierR.id));
		sql.select(SysPssSupplierR.supplierName);
		sql.innerJoin(SysEmployeeEntity.class, new Condition(1, SysEmployeeR.id, SysPssPurchaseReturnOrderR.purchaserId));
		sql.select(1, SysEmployeeR.userName, "returnedName");

		// 字典表
		int i = 0;
		LKDictUtils.usingStatus(sql, SysPssPurchaseReturnOrderR.usingStatus, i++);
		LKDictUtils4Activiti.approvalStatus(sql, SysPssPurchaseReturnOrderR.approvalStatus, i++);

		// 筛选条件（必填项）
		// addConditionId(sql, SysPssPurchaseReturnOrderR.id, params.getId());
		// addConditionLocale(sql, SysPssPurchaseReturnOrderR.locale, params.getLocale());
		addConditionCompId(true, sql, SysPssPurchaseReturnOrderR.compId, params.getCompId(), params.getBusCompId());
		addConditionUsingStatus(true, params.getCompId(), sql, SysPssPurchaseReturnOrderR.usingStatus, params.getUsingStatus(), LKUsingStatusEnum.USING);

		// 筛选条件（业务项）
		Boolean orderType = sin.getOrderType();
		if (orderType != null) {
			sql.eq(SysPssPurchaseReturnOrderR.orderType, orderType);
		}

		ApprovalStatusEnum approvalStatus = sin.getApprovalStatus();
		if (approvalStatus != null) {
			sql.eq(SysPssPurchaseReturnOrderR.approvalStatus, approvalStatus);
		}

		String orderNo = sin.getOrderNo();
		if (StringUtils.isNotBlank(orderNo)) {
			sql.like(SysPssPurchaseReturnOrderR.orderNo, LikeType.ALL, orderNo);
		}

		String purchaseOrderNo = sin.getPurchaseOrderNo();
		if (StringUtils.isNotBlank(purchaseOrderNo)) {
			sql.like(SysPssPurchaseOrderR.orderNo, LikeType.ALL, purchaseOrderNo);
		}

		String supplierName = sin.getSupplierName();
		if (StringUtils.isNotBlank(supplierName)) {
			sql.like(SysPssSupplierR.supplierName, LikeType.ALL, supplierName);
		}

		String purchaserName = sin.getPurchaserName();
		if (StringUtils.isNotBlank(purchaserName)) {
			sql.where(new Condition(true, new like(0, SysEmployeeR.userName, LikeType.ALL, purchaserName)));
		}

		String returnedName = sin.getReturnedName();
		if (StringUtils.isNotBlank(returnedName)) {
			sql.where(new Condition(true, new like(1, SysEmployeeR.userName, LikeType.ALL, returnedName)));
		}

		String startDate = sin.getStartDate();
		if (StringUtils.isNotBlank(startDate)) {
			sql.gte(SysPssPurchaseReturnOrderR.billDate, startDate);
		}

		String endDate = sin.getEndDate();
		if (StringUtils.isNotBlank(endDate)) {
			sql.lte(SysPssPurchaseReturnOrderR.billDate, endDate);
		}

		// 排序条件
		sql.addOrders(new Order(SysPssPurchaseReturnOrderR.billDate, false), new Order(SysPssPurchaseReturnOrderR.id, false));
	}

}
