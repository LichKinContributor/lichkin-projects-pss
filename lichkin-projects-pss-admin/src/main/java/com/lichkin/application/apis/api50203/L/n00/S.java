package com.lichkin.application.apis.api50203.L.n00;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import com.lichkin.application.utils.LKDictUtils4Pss;
import com.lichkin.framework.db.beans.Condition;
import com.lichkin.framework.db.beans.Order;
import com.lichkin.framework.db.beans.QuerySQL;
import com.lichkin.framework.db.beans.SysPssProductR;
import com.lichkin.framework.db.beans.SysPssSellOrderProductR;
import com.lichkin.framework.db.beans.SysPssSellStockOrderProductR;
import com.lichkin.springframework.controllers.ApiKeyValues;
import com.lichkin.springframework.entities.impl.SysPssProductEntity;
import com.lichkin.springframework.entities.impl.SysPssSellOrderProductEntity;
import com.lichkin.springframework.entities.impl.SysPssSellStockOrderProductEntity;
import com.lichkin.springframework.services.LKApiBusGetListService;

@Service("SysPssSellStockOrderProductL00Service")
public class S extends LKApiBusGetListService<I, O, SysPssSellStockOrderProductEntity> {

	@Override
	protected void initSQL(I sin, ApiKeyValues<I> params, QuerySQL sql) {
		// 主表
		// sql.select(SysPssSellStockOrderProductR.id);
		sql.select(SysPssSellStockOrderProductR.quantity);

		// 关联表
		sql.innerJoin(SysPssSellOrderProductEntity.class, new Condition(SysPssSellStockOrderProductR.sellOrderProductId, SysPssSellOrderProductR.id));
		sql.select(SysPssSellOrderProductR.id, "sellOrderProductId");
		sql.select(SysPssSellOrderProductR.quantity, "salesQuantity");
		sql.select(SysPssSellOrderProductR.inventoryQuantity);
		sql.select(SysPssSellOrderProductR.returnedQuantity);
		sql.select(SysPssSellOrderProductR.unitPrice);

		sql.innerJoin(SysPssProductEntity.class, new Condition(SysPssProductR.id, SysPssSellStockOrderProductR.productId));
		sql.select(SysPssProductR.id);
		sql.select(SysPssProductR.productCode);
		sql.select(SysPssProductR.productName);
		sql.select(SysPssProductR.barcode);

		// 字典表
		int i = 0;
		LKDictUtils4Pss.pssProductUnit(sql, SysPssProductR.unit, i++);

		// 筛选条件（必填项）
		// addConditionId(sql, SysPssSellStockOrderProductR.id, params.getId());
		// addConditionLocale(sql, SysPssSellStockOrderProductR.locale, params.getLocale());
		// addConditionCompId(true, sql, SysPssSellStockOrderProductR.compId, params.getCompId(), params.getBusCompId());
		// addConditionUsingStatus(true,params.getCompId(), sql, SysPssSellStockOrderProductR.usingStatus, params.getUsingStatus(), LKUsingStatusEnum.STAND_BY, LKUsingStatusEnum.USING);

		// 筛选条件（业务项）
		String orderId = sin.getOrderId();
		if (StringUtils.isNotBlank(orderId)) {
			sql.eq(SysPssSellStockOrderProductR.orderId, orderId);
		}

		// 排序条件
		sql.addOrders(new Order(SysPssSellStockOrderProductR.id, false));
	}


	@Override
	protected List<O> afterQuery(I sin, ApiKeyValues<I> params, List<O> list) {
		for (O o : list) {
			if (sin.getIsView()) {
				o.setCanStockOutQty(o.getSalesQuantity());
			} else {
				o.setCanStockOutQty(o.getSalesQuantity() - o.getInventoryQuantity() - o.getReturnedQuantity());
			}
		}
		return list;
	}

}
