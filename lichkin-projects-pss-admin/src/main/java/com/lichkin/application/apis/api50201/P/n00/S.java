package com.lichkin.application.apis.api50201.P.n00;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import com.lichkin.application.utils.LKDictUtils4Pss;
import com.lichkin.framework.db.beans.Condition;
import com.lichkin.framework.db.beans.Order;
import com.lichkin.framework.db.beans.QuerySQL;
import com.lichkin.framework.db.beans.SysEmployeeR;
import com.lichkin.framework.db.beans.SysPssProductR;
import com.lichkin.framework.db.beans.SysPssSellOrderProductR;
import com.lichkin.framework.db.beans.SysPssSellOrderR;
import com.lichkin.framework.db.enums.LikeType;
import com.lichkin.framework.defines.enums.impl.ApprovalStatusEnum;
import com.lichkin.framework.defines.enums.impl.LKUsingStatusEnum;
import com.lichkin.springframework.controllers.ApiKeyValues;
import com.lichkin.springframework.entities.impl.SysEmployeeEntity;
import com.lichkin.springframework.entities.impl.SysPssProductEntity;
import com.lichkin.springframework.entities.impl.SysPssSellOrderEntity;
import com.lichkin.springframework.entities.impl.SysPssSellOrderProductEntity;
import com.lichkin.springframework.services.LKApiBusGetPageService;

@Service("SysPssSellOrderProductP00Service")
public class S extends LKApiBusGetPageService<I, O, SysPssSellOrderProductEntity> {

	@Override
	protected void initSQL(I sin, ApiKeyValues<I> params, QuerySQL sql) {
		// 主表
		sql.select(SysPssSellOrderProductR.id);
		sql.select(SysPssSellOrderProductR.quantity);
		sql.select(SysPssSellOrderProductR.inventoryQuantity);
		sql.select(SysPssSellOrderProductR.returnedQuantity);
		sql.select(SysPssSellOrderProductR.unitPrice);
		sql.select(SysPssSellOrderProductR.subTotalPrice);

		// 关联表
		sql.innerJoin(SysPssProductEntity.class, new Condition(SysPssProductR.id, SysPssSellOrderProductR.productId));
		sql.select(SysPssProductR.productCode);
		sql.select(SysPssProductR.productName);
		sql.select(SysPssProductR.barcode);

		sql.innerJoin(SysPssSellOrderEntity.class, new Condition(SysPssSellOrderProductR.orderId, SysPssSellOrderR.id));
		sql.select(SysPssSellOrderR.billDate);
		sql.select(SysPssSellOrderR.orderNo);

		sql.innerJoin(SysEmployeeEntity.class, new Condition(SysPssSellOrderR.salesId, SysEmployeeR.id));
		sql.select(SysEmployeeR.userName, "salesName");

		// 字典表
		int i = 0;
		LKDictUtils4Pss.pssProductUnit(sql, SysPssProductR.unit, i++);

		// 筛选条件（必填项）
		// addConditionId(sql, SysPssSellOrderR.id, params.getId());
		// addConditionLocale(sql, SysPssSellOrderR.locale, params.getLocale());
		addConditionCompId(true, sql, SysPssSellOrderR.compId, params.getCompId(), params.getBusCompId());
		addConditionUsingStatus(true, params.getCompId(), sql, SysPssSellOrderR.usingStatus, params.getUsingStatus(), LKUsingStatusEnum.USING);
		// 审核状态
		sql.eq(SysPssSellOrderR.approvalStatus, ApprovalStatusEnum.APPROVED);

		// 筛选条件（业务项）
		String orderNo = sin.getOrderNo();
		if (StringUtils.isNotBlank(orderNo)) {
			sql.like(SysPssSellOrderR.orderNo, LikeType.ALL, orderNo);
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
		sql.addOrders(new Order(SysPssSellOrderR.id, false), new Order(SysPssSellOrderProductR.id, false));
	}

}
