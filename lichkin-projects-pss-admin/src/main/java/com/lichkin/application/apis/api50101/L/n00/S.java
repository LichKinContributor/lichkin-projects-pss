package com.lichkin.application.apis.api50101.L.n00;

import org.springframework.stereotype.Service;

import com.lichkin.application.utils.LKDictUtils4Pss;
import com.lichkin.framework.db.beans.Condition;
import com.lichkin.framework.db.beans.Order;
import com.lichkin.framework.db.beans.QuerySQL;
import com.lichkin.framework.db.beans.SysPssProductR;
import com.lichkin.framework.db.beans.SysPssPurchaseOrderProductR;
import com.lichkin.springframework.entities.impl.SysPssProductEntity;
import com.lichkin.springframework.entities.impl.SysPssPurchaseOrderProductEntity;
import com.lichkin.springframework.services.LKApiBusGetListService;

@Service("SysPssPurchaseOrderProductL00Service")
public class S extends LKApiBusGetListService<I, O, SysPssPurchaseOrderProductEntity> {

	@Override
	protected void initSQL(I sin, String locale, String compId, String loginId, QuerySQL sql) {
		// 主表
//		sql.select(SysPssPurchaseOrderProductR.id);
		sql.select(SysPssPurchaseOrderProductR.quantity);
		sql.select(SysPssPurchaseOrderProductR.productId, "id");
//		sql.select(SysPssPurchaseOrderProductR.orderId);
		sql.select(SysPssPurchaseOrderProductR.subTotalPrice);
		sql.select(SysPssPurchaseOrderProductR.unitPrice);
		sql.select(SysPssPurchaseOrderProductR.inventoryQuantity);

		// 关联表
		sql.innerJoin(SysPssProductEntity.class, new Condition(SysPssProductR.id, SysPssPurchaseOrderProductR.productId));
		sql.select(SysPssProductR.productCode);
		sql.select(SysPssProductR.productName);
		sql.select(SysPssProductR.barcode);

		// 字典表
		int i = 0;
		LKDictUtils4Pss.pssProductUnit(sql, SysPssProductR.unit, i++);

		// 筛选条件（必填项）
//		sql.eq(SysPssPurchaseOrderProductR.compId, compId);
//		sql.eq(SysPssPurchaseOrderProductR.usingStatus, LKUsingStatusEnum.USING);

		// 筛选条件（业务项）
		sql.eq(SysPssPurchaseOrderProductR.orderId, sin.getOrderId());

		// 排序条件
		sql.addOrders(new Order(SysPssPurchaseOrderProductR.sortId));
	}

}
