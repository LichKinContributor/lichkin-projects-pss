package com.lichkin.application.apis.api50700.P.n00;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import com.lichkin.application.utils.LKDictUtils;
import com.lichkin.application.utils.LKDictUtils4Activiti;
import com.lichkin.framework.db.beans.Condition;
import com.lichkin.framework.db.beans.Order;
import com.lichkin.framework.db.beans.QuerySQL;
import com.lichkin.framework.db.beans.SysEmployeeR;
import com.lichkin.framework.db.beans.SysPssPurchaseReturnStockInOrderR;
import com.lichkin.framework.db.beans.SysPssStorageR;
import com.lichkin.framework.db.beans.SysPssSupplierR;
import com.lichkin.framework.db.enums.LikeType;
import com.lichkin.framework.defines.enums.impl.ApprovalStatusEnum;
import com.lichkin.framework.defines.enums.impl.LKUsingStatusEnum;
import com.lichkin.springframework.controllers.ApiKeyValues;
import com.lichkin.springframework.entities.impl.SysEmployeeEntity;
import com.lichkin.springframework.entities.impl.SysPssPurchaseReturnStockInOrderEntity;
import com.lichkin.springframework.entities.impl.SysPssStorageEntity;
import com.lichkin.springframework.entities.impl.SysPssSupplierEntity;
import com.lichkin.springframework.services.LKApiBusGetPageService;

@Service("SysPssPurchaseReturnStockInOrderP00Service")
public class S extends LKApiBusGetPageService<I, O, SysPssPurchaseReturnStockInOrderEntity> {

	@Override
	protected void initSQL(I sin, ApiKeyValues<I> params, QuerySQL sql) {
		// 主表
		sql.select(SysPssPurchaseReturnStockInOrderR.id);
		sql.select(SysPssPurchaseReturnStockInOrderR.insertTime);
		sql.select(SysPssPurchaseReturnStockInOrderR.approvalTime);
		sql.select(SysPssPurchaseReturnStockInOrderR.orderNo);
		sql.select(SysPssPurchaseReturnStockInOrderR.billDate);
		sql.select(SysPssPurchaseReturnStockInOrderR.orderAmount);

		// 关联表
		sql.innerJoin(SysPssSupplierEntity.class, new Condition(SysPssPurchaseReturnStockInOrderR.supplierId, SysPssSupplierR.id));
		sql.select(SysPssSupplierR.supplierName);
		sql.innerJoin(SysEmployeeEntity.class, new Condition(SysEmployeeR.id, SysPssPurchaseReturnStockInOrderR.purchaserId));
		sql.select(SysEmployeeR.userName, "returnedName");
		sql.innerJoin(SysPssStorageEntity.class, new Condition(SysPssPurchaseReturnStockInOrderR.storageId, SysPssStorageR.id));
		sql.select(SysPssStorageR.storageName);

		// 字典表
		int i = 0;
		LKDictUtils.usingStatus(sql, SysPssPurchaseReturnStockInOrderR.usingStatus, i++);
		LKDictUtils4Activiti.approvalStatus(sql, SysPssPurchaseReturnStockInOrderR.approvalStatus, i++);

		// 筛选条件（必填项）
		// addConditionId(sql, SysPssPurchaseReturnStockInOrderR.id, params.getId());
		// addConditionLocale(sql, SysPssPurchaseReturnStockInOrderR.locale, params.getLocale());
		addConditionCompId(true, sql, SysPssPurchaseReturnStockInOrderR.compId, params.getCompId(), params.getBusCompId());
		addConditionUsingStatus(true, params.getCompId(), sql, SysPssPurchaseReturnStockInOrderR.usingStatus, params.getUsingStatus(), LKUsingStatusEnum.USING);

		// 筛选条件（业务项）
		ApprovalStatusEnum approvalStatus = sin.getApprovalStatus();
		if (approvalStatus != null) {
			sql.eq(SysPssPurchaseReturnStockInOrderR.approvalStatus, approvalStatus);
		}

		String orderNo = sin.getOrderNo();
		if (StringUtils.isNotBlank(orderNo)) {
			sql.like(SysPssPurchaseReturnStockInOrderR.orderNo, LikeType.ALL, orderNo);
		}

		String supplierName = sin.getSupplierName();
		if (StringUtils.isNotBlank(supplierName)) {
			sql.like(SysPssSupplierR.supplierName, LikeType.ALL, supplierName);
		}

		String returnedName = sin.getReturnedName();
		if (StringUtils.isNotBlank(returnedName)) {
			sql.like(SysEmployeeR.userName, LikeType.ALL, returnedName);
		}

		String storageId = sin.getStorageId();
		if (StringUtils.isNotBlank(storageId)) {
			sql.eq(SysPssStorageR.id, storageId);
		}

		String startDate = sin.getStartDate();
		if (StringUtils.isNotBlank(startDate)) {
			sql.gte(SysPssPurchaseReturnStockInOrderR.billDate, startDate);
		}

		String endDate = sin.getEndDate();
		if (StringUtils.isNotBlank(endDate)) {
			sql.lte(SysPssPurchaseReturnStockInOrderR.billDate, endDate);
		}

		// 排序条件
		sql.addOrders(new Order(SysPssPurchaseReturnStockInOrderR.billDate, false), new Order(SysPssPurchaseReturnStockInOrderR.id, false));
	}

}
