package com.lichkin.application.apis.api50101.L.n01;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
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

@Service("SysPssPurchaseOrderProductL01Service")
public class S extends LKApiBusGetListService<I, O, SysPssPurchaseOrderProductEntity> {

	@Override
	protected void initSQL(I sin, String locale, String compId, String loginId, QuerySQL sql) {
		// 主表
		sql.select(SysPssPurchaseOrderProductR.id, "purchaseOrderProductId");
		sql.select(SysPssPurchaseOrderProductR.quantity, "purchaseQty");
		sql.select(SysPssPurchaseOrderProductR.inventoryQuantity);
		sql.select(SysPssPurchaseOrderProductR.unitPrice);

		// 关联表
		sql.innerJoin(SysPssProductEntity.class, new Condition(SysPssProductR.id, SysPssPurchaseOrderProductR.productId));
		sql.select(SysPssProductR.id);
		sql.select(SysPssProductR.productCode);
		sql.select(SysPssProductR.productName);
		sql.select(SysPssProductR.barcode);

		// 字典表
		int i = 0;
		LKDictUtils4Pss.pssProductUnit(sql, SysPssProductR.unit, i++);

		// 筛选条件（必填项）过滤已经全部入库的产品
		sql.lt_(SysPssPurchaseOrderProductR.inventoryQuantity, SysPssPurchaseOrderProductR.quantity);

		// 筛选条件（业务项）
		String orderId = sin.getOrderId();
		if (StringUtils.isNotBlank(orderId)) {
			sql.eq(SysPssPurchaseOrderProductR.orderId, orderId);
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
		sql.addOrders(new Order(SysPssPurchaseOrderProductR.sortId));
	}


	@Override
	protected List<O> afterQuery(I sin, String locale, String compId, String loginId, List<O> list) {
		for (O o : list) {
			o.setCanStockInQty(o.getPurchaseQty() - o.getInventoryQuantity());
		}
		return list;
	}

}
