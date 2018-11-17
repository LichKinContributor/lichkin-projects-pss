package com.lichkin.application.apis.activiti.PSS_PURCHASE_ORDER;

import java.util.Map;

import org.springframework.stereotype.Service;

import com.lichkin.defines.PssStatics;
import com.lichkin.framework.db.beans.QuerySQL;
import com.lichkin.framework.db.beans.SysEmployeeR;
import com.lichkin.framework.db.beans.SysPssSupplierR;
import com.lichkin.springframework.entities.impl.SysEmployeeEntity;
import com.lichkin.springframework.entities.impl.SysPssPurchaseOrderEntity;
import com.lichkin.springframework.entities.impl.SysPssSupplierEntity;
import com.lichkin.springframework.services.LKApiBusStartProcessService;

@Service("SysPssPurchaseOrderU01Service")
public class S extends LKApiBusStartProcessService<I, SysPssPurchaseOrderEntity> {

	@Override
	protected void initFormData(I sin, String locale, String compId, String loginId, SysPssPurchaseOrderEntity entity, Map<String, Object> datas) {
		// 订单统一参数
		datas.put("orderNo", entity.getOrderNo());
		datas.put("billDate", entity.getBillDate());
		datas.put("remarks", entity.getRemarks());
		// 采购订单特有
		datas.put("supplierId", entity.getSupplierId());
		datas.put("purchaserId", entity.getPurchaserId());
		datas.put("orderAmount", entity.getOrderAmount());
		// 关联表参数转换
		datas.put("supplierName", getSupplierName(entity.getSupplierId()));
		datas.put("purchaserName", getPurchaserName(entity.getPurchaserId()));
	}


	@Override
	protected String getProcessCode(I sin, String locale, String compId, String loginId, SysPssPurchaseOrderEntity entity) {
		return PssStatics.PSS_PURCHASE_ORDER;
	}


	private String getSupplierName(String id) {
		QuerySQL sql = new QuerySQL(SysPssSupplierEntity.class);
		sql.select(SysPssSupplierR.supplierName);
		sql.eq(SysPssSupplierR.id, id);
		return dao.getString(sql);
	}


	private String getPurchaserName(String id) {
		QuerySQL sql = new QuerySQL(SysEmployeeEntity.class);
		sql.select(SysEmployeeR.userName);
		sql.eq(SysEmployeeR.id, id);
		return dao.getString(sql);
	}

}
