package com.lichkin.application.apis.api50601.L.n00;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import com.lichkin.application.utils.LKDictUtils4Pss;
import com.lichkin.framework.db.beans.Condition;
import com.lichkin.framework.db.beans.Order;
import com.lichkin.framework.db.beans.QuerySQL;
import com.lichkin.framework.db.beans.SysPssProductR;
import com.lichkin.framework.db.beans.SysPssPurchaseOrderProductR;
import com.lichkin.framework.db.beans.SysPssPurchaseReturnNotStockInOrderProductR;
import com.lichkin.springframework.controllers.ApiKeyValues;
import com.lichkin.springframework.entities.impl.SysPssProductEntity;
import com.lichkin.springframework.entities.impl.SysPssPurchaseOrderProductEntity;
import com.lichkin.springframework.entities.impl.SysPssPurchaseReturnNotStockInOrderProductEntity;
import com.lichkin.springframework.services.LKApiBusGetListService;

@Service("SysPssPurchaseReturnNotStockInOrderProductL00Service")
public class S extends LKApiBusGetListService<I, O, SysPssPurchaseReturnNotStockInOrderProductEntity> {

	@Override
	protected void initSQL(I sin, ApiKeyValues<I> params, QuerySQL sql) {
		// 主表
		// sql.select(SysPssPurchaseReturnNotStockInOrderProductR.id);
		sql.select(SysPssPurchaseReturnNotStockInOrderProductR.quantity);

		// 关联表
		sql.innerJoin(SysPssPurchaseOrderProductEntity.class, new Condition(SysPssPurchaseReturnNotStockInOrderProductR.purchaseOrderProductId, SysPssPurchaseOrderProductR.id));
		sql.select(SysPssPurchaseOrderProductR.id, "purchaseOrderProductId");
		sql.select(SysPssPurchaseOrderProductR.quantity, "purchaseQty");
		sql.select(SysPssPurchaseOrderProductR.inventoryQuantity);
		sql.select(SysPssPurchaseOrderProductR.returnedQuantity);
		sql.select(SysPssPurchaseOrderProductR.unitPrice);

		sql.innerJoin(SysPssProductEntity.class, new Condition(SysPssProductR.id, SysPssPurchaseReturnNotStockInOrderProductR.productId));
		sql.select(SysPssProductR.id);
		sql.select(SysPssProductR.productCode);
		sql.select(SysPssProductR.productName);
		sql.select(SysPssProductR.barcode);

		// 字典表
		int i = 0;
		LKDictUtils4Pss.pssProductUnit(sql, SysPssProductR.unit, i++);

		// 筛选条件（必填项）
		// addConditionId(sql, SysPssPurchaseReturnNotStockInOrderProductR.id, params.getId());
		// addConditionLocale(sql, SysPssPurchaseReturnNotStockInOrderProductR.locale, params.getLocale());
		// addConditionCompId(true, sql, SysPssPurchaseReturnNotStockInOrderProductR.compId, params.getCompId(), params.getBusCompId());
		// addConditionUsingStatus(true,params.getCompId(), sql, SysPssPurchaseReturnNotStockInOrderProductR.usingStatus, params.getUsingStatus(), LKUsingStatusEnum.STAND_BY, LKUsingStatusEnum.USING);

		// 筛选条件（业务项）
		String orderId = sin.getOrderId();
		if (StringUtils.isNotBlank(orderId)) {
			sql.eq(SysPssPurchaseReturnNotStockInOrderProductR.orderId, orderId);
		}

		// 排序条件
		sql.addOrders(new Order(SysPssPurchaseReturnNotStockInOrderProductR.sortId));
	}


	@Override
	protected List<O> afterQuery(I sin, ApiKeyValues<I> params, List<O> list) {
		for (O o : list) {
			if (sin.getIsView().equals(Boolean.TRUE)) {
				o.setCanStockInQty(o.getPurchaseQty());
			} else {
				o.setCanStockInQty(o.getPurchaseQty() - o.getInventoryQuantity() - o.getReturnedQuantity());
			}
		}
		return list;
	}

}
