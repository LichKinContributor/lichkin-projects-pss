package com.lichkin.application.apis.api50201.L.n01;

import java.util.List;

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

@Service("SysPssSellOrderProductL01Service")
public class S extends LKApiBusGetListService<I, O, SysPssSellOrderProductEntity> {

	@Override
	protected void initSQL(I sin, ApiKeyValues<I> params, QuerySQL sql) {
		// 主表
		sql.select(SysPssSellOrderProductR.id, "sellOrderProductId");
		sql.select(SysPssSellOrderProductR.quantity, "salesQuantity");
		sql.select(SysPssSellOrderProductR.inventoryQuantity);
		sql.select(SysPssSellOrderProductR.returnedQuantity);
		sql.select(SysPssSellOrderProductR.unitPrice);

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
		// addConditionId(sql, SysPssSellOrderProductR.id, params.getId());
		// addConditionLocale(sql, SysPssSellOrderProductR.locale, params.getLocale());
		// addConditionCompId(true, sql, SysPssSellOrderProductR.compId, params.getCompId(), params.getBusCompId());
		// addConditionUsingStatus(true, params.getCompId(), sql, SysPssSellOrderProductR.usingStatus, params.getUsingStatus(), LKUsingStatusEnum.STAND_BY, LKUsingStatusEnum.USING);

		// 筛选条件（业务项）
		sql.lt_(SysPssSellOrderProductR.inventoryQuantity, SysPssSellOrderProductR.quantity);
		sql.lt_(SysPssSellOrderProductR.returnedQuantity, SysPssSellOrderProductR.quantity);

		String orderId = sin.getOrderId();
		if (StringUtils.isNotBlank(orderId)) {
			sql.eq(SysPssSellOrderProductR.orderId, orderId);
		}
		String barcode = sin.getBarcode();
		if (StringUtils.isNotBlank(barcode)) {
			sql.eq(SysPssProductR.barcode, barcode);
		}
		String productId = sin.getProductId();
		if (StringUtils.isNotBlank(productId)) {
			sql.eq(SysPssProductR.id, productId);
		}

		// 排序条件
		sql.addOrders(new Order(SysPssSellOrderProductR.sortId));
	}


	@Override
	protected List<O> afterQuery(I sin, ApiKeyValues<I> params, List<O> list) {
		for (int i = list.size() - 1; i >= 0; i--) {
			O o = list.get(i);
			int leftQty = o.getSalesQuantity() - o.getInventoryQuantity() - o.getReturnedQuantity();
			if (leftQty == 0) {
				list.remove(i);
				continue;
			}
			o.setCanStockOutQty(leftQty);
		}
		return list;
	}

}
