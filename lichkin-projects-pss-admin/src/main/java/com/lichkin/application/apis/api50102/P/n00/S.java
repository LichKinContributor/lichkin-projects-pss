package com.lichkin.application.apis.api50102.P.n00;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import com.lichkin.application.utils.LKDictUtils;
import com.lichkin.application.utils.LKDictUtils4Activiti;
import com.lichkin.framework.db.beans.Condition;
import com.lichkin.framework.db.beans.Order;
import com.lichkin.framework.db.beans.QuerySQL;
import com.lichkin.framework.db.beans.SysEmployeeR;
import com.lichkin.framework.db.beans.SysPssPurchaseOrderR;
import com.lichkin.framework.db.beans.SysPssPurchaseStockOrderR;
import com.lichkin.framework.db.beans.SysPssSupplierR;
import com.lichkin.framework.db.enums.LikeType;
import com.lichkin.framework.defines.enums.impl.ApprovalStatusEnum;
import com.lichkin.springframework.entities.impl.SysEmployeeEntity;
import com.lichkin.springframework.entities.impl.SysPssPurchaseOrderEntity;
import com.lichkin.springframework.entities.impl.SysPssPurchaseStockOrderEntity;
import com.lichkin.springframework.entities.impl.SysPssSupplierEntity;
import com.lichkin.springframework.services.LKApiBusGetPageService;

@Service("SysPssPurchaseStockOrderP00Service")
public class S extends LKApiBusGetPageService<I, O, SysPssPurchaseStockOrderEntity> {

	@Override
	protected void initSQL(I sin, String locale, String compId, String loginId, QuerySQL sql) {
		// 主表
		sql.select(SysPssPurchaseStockOrderR.id);
		sql.select(SysPssPurchaseStockOrderR.insertTime);
		sql.select(SysPssPurchaseStockOrderR.approvalTime);
		sql.select(SysPssPurchaseStockOrderR.orderNo);
		sql.select(SysPssPurchaseStockOrderR.billDate);
		sql.select(SysPssPurchaseStockOrderR.storageId);

		// 关联表
		sql.innerJoin(SysPssPurchaseOrderEntity.class, new Condition(SysPssPurchaseStockOrderR.orderId, SysPssPurchaseOrderR.id));
		sql.select(SysPssPurchaseOrderR.orderNo, "purchaseOrderNo");
		sql.select(SysPssPurchaseOrderR.billDate, "purchaserBillDate");
		sql.select(SysPssPurchaseOrderR.orderAmount, "purchaseOrderAmount");
		sql.innerJoin(SysPssSupplierEntity.class, new Condition(SysPssPurchaseOrderR.supplierId, SysPssSupplierR.id));
		sql.select(SysPssSupplierR.supplierName);
		sql.innerJoin(SysEmployeeEntity.class, new Condition(SysPssPurchaseOrderR.purchaserId, SysEmployeeR.id));
		sql.select(SysEmployeeR.userName, "purchaserName");

		// 字典表
		int i = 0;
		LKDictUtils.usingStatus(sql, SysPssPurchaseStockOrderR.usingStatus, i++);
		LKDictUtils4Activiti.approvalStatus(sql, SysPssPurchaseStockOrderR.approvalStatus, i++);

		// 筛选条件（必填项）
		// 公司ID
		addConditionCompId(false, sql, SysPssPurchaseStockOrderR.compId, compId, sin.getCompId());
		// 在用状态
		addConditionUsingStatus(sql, SysPssPurchaseStockOrderR.usingStatus, compId, sin.getUsingStatus());

		// 筛选条件（业务项）
		ApprovalStatusEnum approvalStatus = sin.getApprovalStatus();
		if (approvalStatus != null) {
			sql.eq(SysPssPurchaseStockOrderR.approvalStatus, approvalStatus);
		}

		String orderNo = sin.getOrderNo();
		if (StringUtils.isNotBlank(orderNo)) {
			sql.like(SysPssPurchaseStockOrderR.orderNo, LikeType.ALL, orderNo);
		}

		String storageId = sin.getStorageId();
		if (StringUtils.isNotBlank(storageId)) {
			sql.eq(SysPssPurchaseStockOrderR.storageId, storageId);
		}

		Boolean orderType = sin.getOrderType();
		if (orderType != null) {
			sql.eq(SysPssPurchaseStockOrderR.orderType, orderType);
		}

		String purchaseOrderNo = sin.getPurchaseOrderNo();
		if (StringUtils.isNotBlank(purchaseOrderNo)) {
			sql.eq(SysPssPurchaseOrderR.orderNo, purchaseOrderNo);
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
			sql.gte(SysPssPurchaseStockOrderR.insertTime, startDate);
		}

		String endDate = sin.getEndDate();
		if (StringUtils.isNotBlank(endDate)) {
			sql.lte(SysPssPurchaseStockOrderR.insertTime, endDate);
		}

		// 排序条件
		sql.addOrders(new Order(SysPssPurchaseStockOrderR.billDate, false), new Order(SysPssPurchaseStockOrderR.id, false));
	}

}
