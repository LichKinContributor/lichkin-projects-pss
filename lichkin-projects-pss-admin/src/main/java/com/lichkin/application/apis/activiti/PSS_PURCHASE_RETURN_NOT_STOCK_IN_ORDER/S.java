package com.lichkin.application.apis.activiti.PSS_PURCHASE_RETURN_NOT_STOCK_IN_ORDER;

import java.util.Map;

import org.springframework.stereotype.Service;

import com.lichkin.defines.PssStatics;
import com.lichkin.framework.db.beans.Condition;
import com.lichkin.framework.db.beans.QuerySQL;
import com.lichkin.framework.db.beans.SysEmployeeR;
import com.lichkin.framework.db.beans.SysPssPurchaseOrderR;
import com.lichkin.framework.db.beans.SysPssPurchaseReturnNotStockInOrderR;
import com.lichkin.framework.db.beans.SysPssSupplierR;
import com.lichkin.springframework.controllers.ApiKeyValues;
import com.lichkin.springframework.entities.impl.SysEmployeeEntity;
import com.lichkin.springframework.entities.impl.SysPssPurchaseOrderEntity;
import com.lichkin.springframework.entities.impl.SysPssPurchaseReturnNotStockInOrderEntity;
import com.lichkin.springframework.entities.impl.SysPssSupplierEntity;
import com.lichkin.springframework.services.LKApiBusStartProcessService;

@Service("SysPssPurchaseReturnNotStockInOrderU01Service")
public class S extends LKApiBusStartProcessService<I, SysPssPurchaseReturnNotStockInOrderEntity> {

	@Override
	protected void initFormData(I sin, ApiKeyValues<I> params, SysPssPurchaseReturnNotStockInOrderEntity entity, Map<String, Object> datas) {
		// 订单统一参数
		datas.put("orderNo", entity.getOrderNo());
		datas.put("billDate", entity.getBillDate());
		datas.put("remarks", entity.getRemarks());

		// 关联表参数转换
		setOrderDatas(datas, entity.getId());
	}


	@Override
	protected String getProcessCode(I sin, ApiKeyValues<I> params, SysPssPurchaseReturnNotStockInOrderEntity entity) {
		return PssStatics.PSS_PURCHASE_RETURN_NOT_STOCK_IN_ORDER;
	}


	private void setOrderDatas(Map<String, Object> datas, String orderId) {
		QuerySQL sql = new QuerySQL(SysPssPurchaseReturnNotStockInOrderEntity.class);

		// P接口逻辑简化
		// 主表
		sql.select(SysPssPurchaseReturnNotStockInOrderR.orderAmount);

		// 关联表
		sql.innerJoin(SysPssPurchaseOrderEntity.class, new Condition(SysPssPurchaseReturnNotStockInOrderR.orderId, SysPssPurchaseOrderR.id));
		sql.select(SysPssPurchaseOrderR.orderNo, "purchaseOrderNo");
		sql.select(SysPssPurchaseOrderR.billDate, "purchaserBillDate");
		sql.select(SysPssPurchaseOrderR.orderAmount, "purchaseOrderAmount");
		sql.innerJoin(SysEmployeeEntity.class, new Condition(0, SysEmployeeR.id, SysPssPurchaseOrderR.purchaserId));
		sql.select(SysEmployeeR.userName, "purchaserName");

		sql.innerJoin(SysPssSupplierEntity.class, new Condition(SysPssPurchaseReturnNotStockInOrderR.supplierId, SysPssSupplierR.id));
		sql.select(SysPssSupplierR.supplierName);
		sql.innerJoin(SysEmployeeEntity.class, new Condition(1, SysEmployeeR.id, SysPssPurchaseReturnNotStockInOrderR.purchaserId));
		sql.select(SysEmployeeR.userName, "returnedName");

		sql.eq(SysPssPurchaseReturnNotStockInOrderR.id, orderId);

		// 查询结果
		com.lichkin.application.apis.api50600.P.n00.O o = dao.getOne(sql, com.lichkin.application.apis.api50600.P.n00.O.class);

		// 设置值
		datas.put("supplierName", o.getSupplierName());
		datas.put("returnedName", o.getReturnedName());
		datas.put("orderAmount", o.getOrderAmount());

		datas.put("purchaseOrderNo", o.getOrderNo());
		datas.put("purchaseBillDate", o.getBillDate());
		datas.put("purchaseOrderAmount", o.getPurchaseOrderAmount());
		datas.put("purchaserName", o.getPurchaserName());
	}

}
