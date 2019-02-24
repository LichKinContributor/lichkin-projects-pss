package com.lichkin.application.apis.api50100.P.n00;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import com.lichkin.application.utils.LKDictUtils;
import com.lichkin.application.utils.LKDictUtils4Activiti;
import com.lichkin.application.utils.LKDictUtils4Pss;
import com.lichkin.framework.db.beans.Condition;
import com.lichkin.framework.db.beans.Order;
import com.lichkin.framework.db.beans.QuerySQL;
import com.lichkin.framework.db.beans.SysEmployeeR;
import com.lichkin.framework.db.beans.SysPssPurchaseOrderR;
import com.lichkin.framework.db.beans.SysPssSupplierR;
import com.lichkin.framework.db.enums.LikeType;
import com.lichkin.framework.defines.enums.impl.ApprovalStatusEnum;
import com.lichkin.framework.defines.enums.impl.InventoryStatusEnum;
import com.lichkin.framework.defines.enums.impl.LKUsingStatusEnum;
import com.lichkin.springframework.controllers.ApiKeyValues;
import com.lichkin.springframework.entities.impl.SysEmployeeEntity;
import com.lichkin.springframework.entities.impl.SysPssPurchaseOrderEntity;
import com.lichkin.springframework.entities.impl.SysPssSupplierEntity;
import com.lichkin.springframework.services.LKApiBusGetPageService;

@Service("SysPssPurchaseOrderP00Service")
public class S extends LKApiBusGetPageService<I, O, SysPssPurchaseOrderEntity> {

	@Override
	protected void initSQL(I sin, ApiKeyValues<I> params, QuerySQL sql) {
		// 主表
		sql.select(SysPssPurchaseOrderR.id);
		sql.select(SysPssPurchaseOrderR.insertTime);
		sql.select(SysPssPurchaseOrderR.approvalTime);
		sql.select(SysPssPurchaseOrderR.orderNo);
		sql.select(SysPssPurchaseOrderR.billDate);
		sql.select(SysPssPurchaseOrderR.orderAmount);

		// 关联表
		sql.innerJoin(SysPssSupplierEntity.class, new Condition(SysPssPurchaseOrderR.supplierId, SysPssSupplierR.id));
		sql.select(SysPssSupplierR.supplierName);
		sql.innerJoin(SysEmployeeEntity.class, new Condition(SysPssPurchaseOrderR.purchaserId, SysEmployeeR.id));
		sql.select(SysEmployeeR.userName, "purchaserName");

		// 字典表
		int i = 0;
		LKDictUtils.usingStatus(sql, SysPssPurchaseOrderR.usingStatus, i++);
		LKDictUtils4Activiti.approvalStatus(sql, SysPssPurchaseOrderR.approvalStatus, i++);
		LKDictUtils4Pss.inventoryStatus(sql, SysPssPurchaseOrderR.inventoryStatus, i++);

		// 筛选条件（必填项）
		// addConditionId(sql, SysPssPurchaseOrderR.id, params.getId());
		// addConditionLocale(sql, SysPssPurchaseOrderR.locale, params.getLocale());
		addConditionCompId(true, sql, SysPssPurchaseOrderR.compId, params.getCompId(), params.getBusCompId());
		addConditionUsingStatus(true, params.getCompId(), sql, SysPssPurchaseOrderR.usingStatus, params.getUsingStatus(), LKUsingStatusEnum.USING);

		// 筛选条件（业务项）
		ApprovalStatusEnum approvalStatus = sin.getApprovalStatus();
		if (approvalStatus != null) {
			sql.eq(SysPssPurchaseOrderR.approvalStatus, approvalStatus);
		}

		String orderNo = sin.getOrderNo();
		if (StringUtils.isNotBlank(orderNo)) {
			sql.like(SysPssPurchaseOrderR.orderNo, LikeType.ALL, orderNo);
		}

		InventoryStatusEnum inventoryStatus = sin.getInventoryStatus();
		if (inventoryStatus != null) {
			sql.eq(SysPssPurchaseOrderR.inventoryStatus, inventoryStatus);
		}

		String supplierName = sin.getSupplierName();
		if (StringUtils.isNotBlank(supplierName)) {
			sql.like(SysPssSupplierR.supplierName, LikeType.ALL, supplierName);
		}

		String purchaserName = sin.getPurchaserName();
		if (StringUtils.isNotBlank(purchaserName)) {
			sql.like(SysEmployeeR.userName, LikeType.ALL, purchaserName);
		}

		String startDate = sin.getStartDate();
		if (StringUtils.isNotBlank(startDate)) {
			sql.gte(SysPssPurchaseOrderR.billDate, startDate);
		}

		String endDate = sin.getEndDate();
		if (StringUtils.isNotBlank(endDate)) {
			sql.lte(SysPssPurchaseOrderR.billDate, endDate);
		}

		// 排序条件
		sql.addOrders(new Order(SysPssPurchaseOrderR.id, false));
	}

}
