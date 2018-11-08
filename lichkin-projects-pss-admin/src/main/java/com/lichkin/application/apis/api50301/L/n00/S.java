package com.lichkin.application.apis.api50301.L.n00;

import org.springframework.stereotype.Service;

import com.lichkin.application.utils.LKDictUtils4Pss;
import com.lichkin.framework.db.beans.Condition;
import com.lichkin.framework.db.beans.Order;
import com.lichkin.framework.db.beans.QuerySQL;
import com.lichkin.framework.db.beans.SysPssOtherStockOrderProductR;
import com.lichkin.framework.db.beans.SysPssProductR;
import com.lichkin.springframework.entities.impl.SysPssOtherStockOrderProductEntity;
import com.lichkin.springframework.entities.impl.SysPssProductEntity;
import com.lichkin.springframework.services.LKApiBusGetListService;

@Service("SysPssOtherStockOrderProductL00Service")
public class S extends LKApiBusGetListService<I, O, SysPssOtherStockOrderProductEntity> {

	@Override
	protected void initSQL(I sin, String locale, String compId, String loginId, QuerySQL sql) {
		// 主表
		// sql.select(SysPssOtherStockOrderProductR.id);
		sql.select(SysPssOtherStockOrderProductR.quantity);

		// 关联表
		sql.innerJoin(SysPssProductEntity.class, new Condition(SysPssOtherStockOrderProductR.productId, SysPssProductR.id));
		sql.select(SysPssProductR.id);
		sql.select(SysPssProductR.productCode);
		sql.select(SysPssProductR.productName);
		sql.select(SysPssProductR.barcode);

		// 字典表
		int i = 0;
		LKDictUtils4Pss.pssProductUnit(sql, SysPssProductR.unit, i++);

		// 筛选条件（必填项）
		sql.eq(SysPssOtherStockOrderProductR.orderId, sin.getOrderId());

		// 排序条件
		sql.addOrders(new Order(SysPssOtherStockOrderProductR.id, false));
	}

}
