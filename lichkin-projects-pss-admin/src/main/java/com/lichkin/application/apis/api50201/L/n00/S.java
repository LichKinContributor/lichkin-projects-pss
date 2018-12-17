package com.lichkin.application.apis.api50201.L.n00;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import com.lichkin.application.utils.LKDictUtils4Pss;
import com.lichkin.framework.db.beans.Condition;
import com.lichkin.framework.db.beans.Order;
import com.lichkin.framework.db.beans.QuerySQL;
import com.lichkin.framework.db.beans.SysPssProductR;
import com.lichkin.framework.db.beans.SysPssSellOrderProductR;
import com.lichkin.springframework.controllers.ApiKeyValues;
import com.lichkin.springframework.entities.impl.SysPssProductEntity;
import com.lichkin.springframework.entities.impl.SysPssSellOrderProductEntity;
import com.lichkin.springframework.services.LKApiBusGetListService;

@Service("SysPssSellOrderProductL00Service")
public class S extends LKApiBusGetListService<I, O, SysPssSellOrderProductEntity> {

	@Override
	protected void initSQL(I sin, ApiKeyValues<I> params, QuerySQL sql) {
		// 主表
		// sql.select(SysPssSellOrderProductR.id);
		sql.select(SysPssSellOrderProductR.quantity);
		sql.select(SysPssSellOrderProductR.inventoryQuantity);
		sql.select(SysPssSellOrderProductR.unitPrice);
		sql.select(SysPssSellOrderProductR.subTotalPrice);

		// 关联表
		sql.innerJoin(SysPssProductEntity.class, new Condition(SysPssProductR.id, SysPssSellOrderProductR.productId));
		sql.select(SysPssProductR.id);
		sql.select(SysPssProductR.productCode);
		sql.select(SysPssProductR.productName);
		sql.select(SysPssProductR.barcode);

		// 字典表
		int i = 0;
		LKDictUtils4Pss.pssProductUnit(sql, SysPssProductR.unit, i++);

		// 筛选条件（必填项）
//		addConditionId(sql, SysPssSellOrderProductR.id, params.getId());
//		addConditionLocale(sql, SysPssSellOrderProductR.locale, params.getLocale());
//		addConditionCompId(true, sql, SysPssSellOrderProductR.compId, params.getCompId(), params.getBusCompId());
//		addConditionUsingStatus(true, params.getCompId(), sql, SysPssSellOrderProductR.usingStatus, params.getUsingStatus(), LKUsingStatusEnum.STAND_BY, LKUsingStatusEnum.USING);

		// 筛选条件（业务项）
		String orderId = sin.getOrderId();
		if (StringUtils.isNotBlank(orderId)) {
			sql.eq(SysPssSellOrderProductR.orderId, orderId);
		}

		// 排序条件
		sql.addOrders(new Order(SysPssSellOrderProductR.id, false));
	}

}
