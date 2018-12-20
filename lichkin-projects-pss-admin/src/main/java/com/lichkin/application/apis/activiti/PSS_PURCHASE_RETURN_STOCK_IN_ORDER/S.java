package com.lichkin.application.apis.activiti.PSS_PURCHASE_RETURN_STOCK_IN_ORDER;

import java.util.Map;

import org.springframework.stereotype.Service;

import com.lichkin.defines.PssStatics;
import com.lichkin.framework.db.beans.Condition;
import com.lichkin.framework.db.beans.QuerySQL;
import com.lichkin.framework.db.beans.SysEmployeeR;
import com.lichkin.framework.db.beans.SysPssPurchaseReturnStockInOrderR;
import com.lichkin.framework.db.beans.SysPssStorageR;
import com.lichkin.framework.db.beans.SysPssSupplierR;
import com.lichkin.springframework.controllers.ApiKeyValues;
import com.lichkin.springframework.entities.impl.SysEmployeeEntity;
import com.lichkin.springframework.entities.impl.SysPssPurchaseReturnStockInOrderEntity;
import com.lichkin.springframework.entities.impl.SysPssStorageEntity;
import com.lichkin.springframework.entities.impl.SysPssSupplierEntity;
import com.lichkin.springframework.services.LKApiBusStartProcessService;

@Service("SysPssPurchaseReturnStockInOrderU01Service")
public class S extends LKApiBusStartProcessService<I, SysPssPurchaseReturnStockInOrderEntity> {

	@Override
	protected void initFormData(I sin, ApiKeyValues<I> params, SysPssPurchaseReturnStockInOrderEntity entity, Map<String, Object> datas) {
		// 订单统一参数
		datas.put("orderNo", entity.getOrderNo());
		datas.put("billDate", entity.getBillDate());
		datas.put("remarks", entity.getRemarks());
		// 关联表参数转换
		setOrderDatas(datas, entity.getId());
	}


	@Override
	protected String getProcessCode(I sin, ApiKeyValues<I> params, SysPssPurchaseReturnStockInOrderEntity entity) {
		return PssStatics.PSS_PURCHASE_RETURN_STOCK_IN_ORDER;
	}


	private void setOrderDatas(Map<String, Object> datas, String orderId) {
		QuerySQL sql = new QuerySQL(SysPssPurchaseReturnStockInOrderEntity.class);

		// P接口逻辑简化
		// 主表
		sql.select(SysPssPurchaseReturnStockInOrderR.orderAmount);

		// 关联表
		sql.innerJoin(SysPssSupplierEntity.class, new Condition(SysPssPurchaseReturnStockInOrderR.supplierId, SysPssSupplierR.id));
		sql.select(SysPssSupplierR.supplierName);
		sql.innerJoin(SysEmployeeEntity.class, new Condition(SysEmployeeR.id, SysPssPurchaseReturnStockInOrderR.purchaserId));
		sql.select(SysEmployeeR.userName, "returnedName");
		sql.innerJoin(SysPssStorageEntity.class, new Condition(SysPssPurchaseReturnStockInOrderR.storageId, SysPssStorageR.id));
		sql.select(SysPssStorageR.storageName);

		sql.eq(SysPssPurchaseReturnStockInOrderR.id, orderId);

		// 查询结果
		com.lichkin.application.apis.api50700.P.n00.O o = dao.getOne(sql, com.lichkin.application.apis.api50700.P.n00.O.class);

		// 设置值
		datas.put("supplierName", o.getSupplierName());
		datas.put("returnedName", o.getReturnedName());
		datas.put("storageName", o.getStorageName());
		datas.put("orderAmount", o.getOrderAmount());
	}

}
