package com.lichkin.application.apis.activiti.PSS_PURCHASE_STOCK_ORDER;

import java.util.Map;

import org.springframework.stereotype.Service;

import com.lichkin.defines.PssStatics;
import com.lichkin.framework.db.beans.Condition;
import com.lichkin.framework.db.beans.QuerySQL;
import com.lichkin.framework.db.beans.SysEmployeeR;
import com.lichkin.framework.db.beans.SysPssPurchaseOrderR;
import com.lichkin.framework.db.beans.SysPssSupplierR;
import com.lichkin.springframework.entities.impl.SysEmployeeEntity;
import com.lichkin.springframework.entities.impl.SysPssPurchaseOrderEntity;
import com.lichkin.springframework.entities.impl.SysPssPurchaseStockOrderEntity;
import com.lichkin.springframework.entities.impl.SysPssStorageEntity;
import com.lichkin.springframework.entities.impl.SysPssSupplierEntity;
import com.lichkin.springframework.services.LKApiBusStartProcessService;

@Service("SysPssPurchaseStockOrderU01Service")
public class S extends LKApiBusStartProcessService<I, SysPssPurchaseStockOrderEntity> {

	@Override
	protected void initFormData(I sin, String locale, String compId, String loginId, SysPssPurchaseStockOrderEntity entity, Map<String, Object> datas) {
		// 订单统一参数
		datas.put("orderNo", entity.getOrderNo());
		datas.put("billDate", entity.getBillDate());
		datas.put("remarks", entity.getRemarks());
		// 关联表参数转换
		setOrderDatas(datas, entity.getOrderId(), entity.getStorageId());
	}


	@Override
	protected String getProcessCode(I sin, String locale, String compId, String loginId, SysPssPurchaseStockOrderEntity entity) {
		return entity.getOrderType() ? PssStatics.PSS_PURCHASE_STOCK_IN_ORDER : PssStatics.PSS_PURCHASE_STOCK_OUT_ORDER;
	}


	private void setOrderDatas(Map<String, Object> datas, String orderId, String storageId) {
		QuerySQL sql = new QuerySQL(SysPssPurchaseOrderEntity.class);

		// P接口逻辑简化
		// 主表
		sql.select(SysPssPurchaseOrderR.orderNo);
		sql.select(SysPssPurchaseOrderR.billDate);
		sql.select(SysPssPurchaseOrderR.orderAmount);

		// 关联表
		sql.innerJoin(SysPssSupplierEntity.class, new Condition(SysPssPurchaseOrderR.supplierId, SysPssSupplierR.id));
		sql.select(SysPssSupplierR.supplierName);
		sql.innerJoin(SysEmployeeEntity.class, new Condition(SysPssPurchaseOrderR.purchaserId, SysEmployeeR.id));
		sql.select(SysEmployeeR.userName, "purchaserName");

		sql.eq(SysPssPurchaseOrderR.id, orderId);

		// 查询结果
		com.lichkin.application.apis.api50100.P.n00.O o = dao.getOne(sql, com.lichkin.application.apis.api50100.P.n00.O.class);

		// 设置值
		datas.put("purchaseOrderNo", o.getOrderNo());
		datas.put("purchaseBillDate", o.getBillDate());
		datas.put("purchaseOrderAmount", o.getOrderAmount());
		datas.put("supplierName", o.getSupplierName());
		datas.put("purchaserName", o.getPurchaserName());

		SysPssStorageEntity storage = dao.findOneById(SysPssStorageEntity.class, storageId);
		datas.put("storageName", storage.getStorageName());
	}

}
