package com.lichkin.application.apis.api50101.P.n00;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import com.lichkin.application.utils.LKDictUtils4Pss;
import com.lichkin.framework.db.beans.Condition;
import com.lichkin.framework.db.beans.Order;
import com.lichkin.framework.db.beans.QuerySQL;
import com.lichkin.framework.db.beans.SysEmployeeR;
import com.lichkin.framework.db.beans.SysPssProductR;
import com.lichkin.framework.db.beans.SysPssPurchaseOrderProductR;
import com.lichkin.framework.db.beans.SysPssPurchaseOrderR;
import com.lichkin.framework.db.beans.SysPssSupplierR;
import com.lichkin.framework.db.enums.LikeType;
import com.lichkin.framework.defines.enums.impl.ApprovalStatusEnum;
import com.lichkin.framework.defines.enums.impl.LKUsingStatusEnum;
import com.lichkin.springframework.entities.impl.SysEmployeeEntity;
import com.lichkin.springframework.entities.impl.SysPssProductEntity;
import com.lichkin.springframework.entities.impl.SysPssPurchaseOrderEntity;
import com.lichkin.springframework.entities.impl.SysPssPurchaseOrderProductEntity;
import com.lichkin.springframework.entities.impl.SysPssSupplierEntity;
import com.lichkin.springframework.services.LKApiBusGetPageService;

@Service("SysPssPurchaseOrderProductP00Service")
public class S extends LKApiBusGetPageService<I, O, SysPssPurchaseOrderProductEntity> {

	@Override
	protected void initSQL(I sin, String locale, String compId, String loginId, QuerySQL sql) {

		// 主表
		sql.select(SysPssPurchaseOrderProductR.id);
		sql.select(SysPssPurchaseOrderProductR.quantity);
		sql.select(SysPssPurchaseOrderProductR.inventoryQuantity);
		sql.select(SysPssPurchaseOrderProductR.unitPrice);
		sql.select(SysPssPurchaseOrderProductR.subTotalPrice);

		// 关联表
		sql.innerJoin(SysPssProductEntity.class, new Condition(SysPssProductR.id, SysPssPurchaseOrderProductR.productId));
		sql.select(SysPssProductR.productCode);
		sql.select(SysPssProductR.productName);
		sql.select(SysPssProductR.barcode);

		sql.innerJoin(SysPssPurchaseOrderEntity.class, new Condition(SysPssPurchaseOrderProductR.orderId, SysPssPurchaseOrderR.id));
		sql.select(SysPssPurchaseOrderR.billDate);
		sql.select(SysPssPurchaseOrderR.orderNo);

		sql.innerJoin(SysPssSupplierEntity.class, new Condition(SysPssPurchaseOrderR.supplierId, SysPssSupplierR.id));
		sql.select(SysPssSupplierR.supplierName);
		sql.innerJoin(SysEmployeeEntity.class, new Condition(SysPssPurchaseOrderR.purchaserId, SysEmployeeR.id));
		sql.select(SysEmployeeR.userName, "purchaserName");

		// 字典表
		int i = 0;
		LKDictUtils4Pss.pssProductUnit(sql, SysPssProductR.unit, i++);

		// 筛选条件（必填项）
		// 公司ID
		addConditionCompId(false, sql, SysPssPurchaseOrderR.compId, compId, sin.getCompId());
		// 在用状态
		addConditionUsingStatus(sql, SysPssPurchaseOrderR.usingStatus, compId, LKUsingStatusEnum.USING);
		// 审核状态
		sql.eq(SysPssPurchaseOrderR.approvalStatus, ApprovalStatusEnum.APPROVED);

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

		String productCode = sin.getProductCode();
		if (StringUtils.isNotBlank(productCode)) {
			sql.like(SysPssProductR.productCode, LikeType.ALL, productCode);
		}

		String productName = sin.getProductName();
		if (StringUtils.isNotBlank(productName)) {
			sql.like(SysPssProductR.productName, LikeType.ALL, productName);
		}

		String barcode = sin.getBarcode();
		if (StringUtils.isNotBlank(barcode)) {
			sql.eq(SysPssProductR.barcode, barcode);
		}
		// 排序条件
		sql.addOrders(new Order(SysPssPurchaseOrderR.id, false), new Order(SysPssPurchaseOrderProductR.id, false));
	}

}
