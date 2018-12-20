package com.lichkin.application.apis.activiti.PSS_SELL_STOCK_ORDER;

import java.util.Map;

import org.springframework.stereotype.Service;

import com.lichkin.defines.PssStatics;
import com.lichkin.framework.db.beans.Condition;
import com.lichkin.framework.db.beans.QuerySQL;
import com.lichkin.framework.db.beans.SysEmployeeR;
import com.lichkin.framework.db.beans.SysPssSellOrderR;
import com.lichkin.springframework.controllers.ApiKeyValues;
import com.lichkin.springframework.entities.impl.SysEmployeeEntity;
import com.lichkin.springframework.entities.impl.SysPssSellOrderEntity;
import com.lichkin.springframework.entities.impl.SysPssSellStockOrderEntity;
import com.lichkin.springframework.entities.impl.SysPssStorageEntity;
import com.lichkin.springframework.services.LKApiBusStartProcessService;

@Service("SysPssSellStockOrderU01Service")
public class S extends LKApiBusStartProcessService<I, SysPssSellStockOrderEntity> {

	@Override
	protected void initFormData(I sin, ApiKeyValues<I> params, SysPssSellStockOrderEntity entity, Map<String, Object> datas) {
		// 订单统一参数
		datas.put("orderNo", entity.getOrderNo());
		datas.put("billDate", entity.getBillDate());
		datas.put("remarks", entity.getRemarks());
		// 关联表参数转换
		setOrderDatas(datas, entity.getOrderId(), entity.getStorageId());
	}


	@Override
	protected String getProcessCode(I sin, ApiKeyValues<I> params, SysPssSellStockOrderEntity entity) {
		return PssStatics.PSS_SELL_STOCK_OUT_ORDER;
	}


	private void setOrderDatas(Map<String, Object> datas, String orderId, String storageId) {
		QuerySQL sql = new QuerySQL(SysPssSellOrderEntity.class);

		// P接口逻辑简化
		// 主表
		sql.select(SysPssSellOrderR.orderNo);
		sql.select(SysPssSellOrderR.billDate);
		sql.select(SysPssSellOrderR.orderAmount);

		// 关联表
		sql.innerJoin(SysEmployeeEntity.class, new Condition(SysPssSellOrderR.salesId, SysEmployeeR.id));
		sql.select(SysEmployeeR.userName, "salesName");

		sql.eq(SysPssSellOrderR.id, orderId);

		// 查询结果
		com.lichkin.application.apis.api50200.P.n00.O o = dao.getOne(sql, com.lichkin.application.apis.api50200.P.n00.O.class);

		// 设置值
		datas.put("sellOrderNo", o.getOrderNo());
		datas.put("sellBillDate", o.getBillDate());
		datas.put("sellOrderAmount", o.getOrderAmount());
		datas.put("salesName", o.getSalesName());

		SysPssStorageEntity storage = dao.findOneById(SysPssStorageEntity.class, storageId);
		datas.put("storageName", storage.getStorageName());
	}

}
