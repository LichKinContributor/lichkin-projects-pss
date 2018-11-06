package com.lichkin.application.apis.api50100.U.n01;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lichkin.application.services.impl.ActivitiStartProcessService;
import com.lichkin.framework.db.beans.QuerySQL;
import com.lichkin.framework.db.beans.SysEmployeeR;
import com.lichkin.framework.db.beans.SysPssSupplierR;
import com.lichkin.framework.json.LKJsonUtils;
import com.lichkin.springframework.entities.impl.SysEmployeeEntity;
import com.lichkin.springframework.entities.impl.SysPssPurchaseOrderEntity;
import com.lichkin.springframework.entities.impl.SysPssSupplierEntity;
import com.lichkin.springframework.services.LKApiBusUpdateWithoutCheckerService;

@Service("SysPssPurchaseOrderU01Service")
public class S extends LKApiBusUpdateWithoutCheckerService<I, SysPssPurchaseOrderEntity> {

	@Autowired
	private ActivitiStartProcessService activitiStartProcessService;


	@Override
	protected void beforeSaveMain(I sin, String locale, String compId, String loginId, SysPssPurchaseOrderEntity entity) {
		Map<String, Object> datas = new HashMap<>();
		// 必填
		datas.put("id", entity.getId());
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
		// 发起流程
		activitiStartProcessService.startByAdmin(entity, compId, "PSS_PURCHASE_ORDER", loginId, sin.getDatas().getUser().getUserName(), LKJsonUtils.toJson(datas));
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
