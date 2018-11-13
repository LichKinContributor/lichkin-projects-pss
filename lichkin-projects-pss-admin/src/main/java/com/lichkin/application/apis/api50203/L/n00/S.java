package com.lichkin.application.apis.api50203.L.n00;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import com.lichkin.application.utils.LKDictUtils4Pss;
import com.lichkin.framework.db.beans.Condition;
import com.lichkin.framework.db.beans.Order;
import com.lichkin.framework.db.beans.QuerySQL;
import com.lichkin.framework.db.beans.SysPssProductR;
import com.lichkin.framework.db.beans.SysPssSellStockOrderProductR;
import com.lichkin.springframework.entities.impl.SysPssProductEntity;
import com.lichkin.springframework.entities.impl.SysPssSellStockOrderProductEntity;
import com.lichkin.springframework.services.LKApiBusGetListService;

@Service("SysPssSellStockOrderProductL00Service")
public class S extends LKApiBusGetListService<I, O, SysPssSellStockOrderProductEntity> {

	@Override
	protected void initSQL(I sin, String locale, String compId, String loginId, QuerySQL sql) {
		// 主表
		// sql.select(SysPssSellStockOrderProductR.id);
		sql.select(SysPssSellStockOrderProductR.quantity);

		// 关联表
		sql.innerJoin(SysPssProductEntity.class, new Condition(SysPssProductR.id, SysPssSellStockOrderProductR.productId));
		sql.select(SysPssProductR.id);
		sql.select(SysPssProductR.productCode);
		sql.select(SysPssProductR.productName);
		sql.select(SysPssProductR.barcode);

		// 字典表
		int i = 0;
		LKDictUtils4Pss.pssProductUnit(sql, SysPssProductR.unit, i++);
		// 筛选条件（必填项）

		// 筛选条件（业务项）
		String orderId = sin.getOrderId();
		if (StringUtils.isNotBlank(orderId)) {
			sql.eq(SysPssSellStockOrderProductR.orderId, orderId);
		}

		// 排序条件
		sql.addOrders(new Order(SysPssSellStockOrderProductR.id, false));
	}

}
