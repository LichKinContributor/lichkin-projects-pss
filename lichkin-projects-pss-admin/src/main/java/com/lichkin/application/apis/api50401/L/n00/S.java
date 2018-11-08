package com.lichkin.application.apis.api50401.L.n00;

import org.springframework.stereotype.Service;

import com.lichkin.application.utils.LKDictUtils4Pss;
import com.lichkin.framework.db.beans.Condition;
import com.lichkin.framework.db.beans.Order;
import com.lichkin.framework.db.beans.QuerySQL;
import com.lichkin.framework.db.beans.SysPssAllotOrderProductR;
import com.lichkin.framework.db.beans.SysPssAllotOrderR;
import com.lichkin.framework.db.beans.SysPssProductR;
import com.lichkin.framework.db.beans.SysPssStockR;
import com.lichkin.springframework.entities.impl.SysPssAllotOrderEntity;
import com.lichkin.springframework.entities.impl.SysPssAllotOrderProductEntity;
import com.lichkin.springframework.entities.impl.SysPssProductEntity;
import com.lichkin.springframework.entities.impl.SysPssStockEntity;
import com.lichkin.springframework.services.LKApiBusGetListService;

@Service("SysPssAllotOrderProductL00Service")
public class S extends LKApiBusGetListService<I, O, SysPssAllotOrderProductEntity> {

	@Override
	protected void initSQL(I sin, String locale, String compId, String loginId, QuerySQL sql) {
		// 主表
		// sql.select(SysPssAllotOrderProductR.id);
		sql.select(SysPssAllotOrderProductR.quantity);

		// 关联表
		sql.innerJoin(SysPssProductEntity.class, new Condition(SysPssAllotOrderProductR.productId, SysPssProductR.id));
		sql.select(SysPssProductR.id);
		sql.select(SysPssProductR.productCode);
		sql.select(SysPssProductR.productName);
		sql.select(SysPssProductR.barcode);

		sql.innerJoin(SysPssAllotOrderEntity.class, new Condition(SysPssAllotOrderProductR.orderId, SysPssAllotOrderR.id));
		sql.innerJoin(SysPssStockEntity.class,

				new Condition(SysPssStockR.storageId, SysPssAllotOrderR.outStorageId),

				new Condition(SysPssStockR.productId, SysPssAllotOrderProductR.productId)

		);
		sql.select(SysPssStockR.quantity, "stockQuantity");

		// 字典表
		int i = 0;
		LKDictUtils4Pss.pssProductUnit(sql, SysPssProductR.unit, i++);

		// 筛选条件（必填项）
		sql.eq(SysPssAllotOrderProductR.orderId, sin.getOrderId());

		// 排序条件
		sql.addOrders(new Order(SysPssAllotOrderProductR.id, false));
	}

}
