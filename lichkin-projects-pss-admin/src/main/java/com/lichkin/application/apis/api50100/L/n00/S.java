package com.lichkin.application.apis.api50100.L.n00;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

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
import com.lichkin.springframework.services.LKApiBusGetListService;

@Service("SysPssPurchaseOrderL00Service")
public class S extends LKApiBusGetListService<I, O, SysPssPurchaseOrderEntity> {

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
		LKDictUtils4Pss.inventoryStatus(sql, SysPssPurchaseOrderR.inventoryStatus, i++);

		// 筛选条件（必填项）
		// 公司ID
		params.addConditionCompId(false, sql, SysPssPurchaseOrderR.compId);
		// 在用状态
		params.addConditionUsingStatus(sql, SysPssPurchaseOrderR.usingStatus, null, LKUsingStatusEnum.USING);
		// 审核状态
		sql.eq(SysPssPurchaseOrderR.approvalStatus, ApprovalStatusEnum.APPROVED);

		// 入库状态
		InventoryStatusEnum inventoryStatus = sin.getInventoryStatus();
		if (inventoryStatus != null) {
			sql.eq(SysPssPurchaseOrderR.inventoryStatus, inventoryStatus);
		} else {
			sql.neq(SysPssPurchaseOrderR.inventoryStatus, InventoryStatusEnum.ALL);
		}

		// 筛选条件（业务项）
		String orderNo = sin.getOrderNo();
		if (StringUtils.isNotBlank(orderNo)) {
			sql.like(SysPssPurchaseOrderR.orderNo, LikeType.ALL, orderNo);
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
