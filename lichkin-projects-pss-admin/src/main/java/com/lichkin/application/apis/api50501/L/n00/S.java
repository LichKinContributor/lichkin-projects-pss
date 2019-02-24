package com.lichkin.application.apis.api50501.L.n00;

import org.springframework.stereotype.Service;

import com.lichkin.application.utils.LKDictUtils4Pss;
import com.lichkin.framework.db.beans.Condition;
import com.lichkin.framework.db.beans.Order;
import com.lichkin.framework.db.beans.QuerySQL;
import com.lichkin.framework.db.beans.SysPssProductR;
import com.lichkin.framework.db.beans.SysPssStockCheckOrderProductR;
import com.lichkin.springframework.controllers.ApiKeyValues;
import com.lichkin.springframework.entities.impl.SysPssProductEntity;
import com.lichkin.springframework.entities.impl.SysPssStockCheckOrderProductEntity;
import com.lichkin.springframework.services.LKApiBusGetListService;

@Service("SysPssStockCheckOrderProductL00Service")
public class S extends LKApiBusGetListService<I, O, SysPssStockCheckOrderProductEntity> {

	@Override
	protected void initSQL(I sin, ApiKeyValues<I> params, QuerySQL sql) {
		// 主表
		// sql.select(SysPssStockCheckOrderProductR.id);
		sql.select(SysPssStockCheckOrderProductR.quantity);
		sql.select(SysPssStockCheckOrderProductR.stockQuantity);
		sql.select(SysPssStockCheckOrderProductR.differenceQuantity);

		// 关联表
		sql.innerJoin(SysPssProductEntity.class, new Condition(SysPssStockCheckOrderProductR.productId, SysPssProductR.id));
		sql.select(SysPssProductR.id);
		sql.select(SysPssProductR.productCode);
		sql.select(SysPssProductR.productName);
		sql.select(SysPssProductR.barcode);

		// 字典表
		int i = 0;
		LKDictUtils4Pss.pssProductUnit(sql, SysPssProductR.unit, i++);

		// 筛选条件（必填项）
		// addConditionId(sql, SysPssStockCheckOrderProductR.id, params.getId());
		// addConditionLocale(sql, SysPssStockCheckOrderProductR.locale, params.getLocale());
		// addConditionCompId(true, sql, SysPssStockCheckOrderProductR.compId, params.getCompId(), params.getBusCompId());
		// addConditionUsingStatus(true, params.getCompId(), sql, SysPssStockCheckOrderProductR.usingStatus, params.getUsingStatus(), LKUsingStatusEnum.STAND_BY, LKUsingStatusEnum.USING);

		// 筛选条件（业务项）
		sql.eq(SysPssStockCheckOrderProductR.orderId, sin.getOrderId());

		// 排序条件
		sql.addOrders(new Order(SysPssStockCheckOrderProductR.id, false));
	}

}
