package com.lichkin.application.apis.api50103.P.n00;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import com.lichkin.framework.db.beans.Order;
import com.lichkin.framework.db.beans.QuerySQL;
import com.lichkin.framework.db.beans.SysPssProductR;
import com.lichkin.framework.db.beans.SysPssPurchaseStockOrderProductR;
import com.lichkin.springframework.entities.impl.SysPssPurchaseStockOrderProductEntity;
import com.lichkin.springframework.services.LKApiBusGetPageService;

@Service("SysPssPurchaseStockOrderProductP00Service")
public class S extends LKApiBusGetPageService<I, O, SysPssPurchaseStockOrderProductEntity> {

	@Override
	protected void initSQL(I sin, String locale, String compId, String loginId, QuerySQL sql) {
		// 主表
		sql.select(SysPssPurchaseStockOrderProductR.id);
		sql.select(SysPssPurchaseStockOrderProductR.quantity);

		// 关联表
		sql.select(SysPssProductR.productCode);
		sql.select(SysPssProductR.productName);
		sql.select(SysPssProductR.barcode);
		sql.select(SysPssProductR.unit);

		// 字典表
		int i = 0;

		// 筛选条件（必填项）

		// 筛选条件（业务项）
		String orderId = sin.getOrderId();
		if (StringUtils.isNotBlank(orderId)) {
			sql.eq(SysPssPurchaseStockOrderProductR.orderId, orderId);
		}

		// 排序条件
		sql.addOrders(new Order(SysPssPurchaseStockOrderProductR.id, false));
	}

}
