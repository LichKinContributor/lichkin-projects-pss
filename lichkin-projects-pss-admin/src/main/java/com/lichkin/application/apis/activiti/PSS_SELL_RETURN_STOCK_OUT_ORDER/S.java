package com.lichkin.application.apis.activiti.PSS_SELL_RETURN_STOCK_OUT_ORDER;

import java.util.Map;

import org.springframework.stereotype.Service;

import com.lichkin.defines.PssStatics;
import com.lichkin.framework.db.beans.Condition;
import com.lichkin.framework.db.beans.QuerySQL;
import com.lichkin.framework.db.beans.SysEmployeeR;
import com.lichkin.framework.db.beans.SysPssSellReturnStockOutOrderR;
import com.lichkin.framework.db.beans.SysPssStorageR;
import com.lichkin.springframework.controllers.ApiKeyValues;
import com.lichkin.springframework.entities.impl.SysEmployeeEntity;
import com.lichkin.springframework.entities.impl.SysPssSellReturnStockOutOrderEntity;
import com.lichkin.springframework.entities.impl.SysPssStorageEntity;
import com.lichkin.springframework.services.LKApiBusStartProcessService;

@Service("SysPssSellReturnStockOutOrderU01Service")
public class S extends LKApiBusStartProcessService<I, SysPssSellReturnStockOutOrderEntity> {

	@Override
	protected void initFormData(I sin, ApiKeyValues<I> params, SysPssSellReturnStockOutOrderEntity entity, Map<String, Object> datas) {
		// 订单统一参数
		datas.put("orderNo", entity.getOrderNo());
		datas.put("billDate", entity.getBillDate());
		datas.put("remarks", entity.getRemarks());
		// 关联表参数转换
		setOrderDatas(datas, entity.getId());
	}


	@Override
	protected String getProcessCode(I sin, ApiKeyValues<I> params, SysPssSellReturnStockOutOrderEntity entity) {
		return PssStatics.PSS_SELL_RETURN_STOCK_OUT_ORDER;
	}


	private void setOrderDatas(Map<String, Object> datas, String id) {
		QuerySQL sql = new QuerySQL(SysPssSellReturnStockOutOrderEntity.class);

		// P接口逻辑简化
		// 主表
		sql.select(SysPssSellReturnStockOutOrderR.orderAmount);

		// 关联表
		sql.innerJoin(SysEmployeeEntity.class, new Condition(SysEmployeeR.id, SysPssSellReturnStockOutOrderR.salesId));
		sql.select(SysEmployeeR.userName, "returnedName");
		sql.innerJoin(SysPssStorageEntity.class, new Condition(SysPssSellReturnStockOutOrderR.storageId, SysPssStorageR.id));
		sql.select(SysPssStorageR.storageName);

		sql.eq(SysPssSellReturnStockOutOrderR.id, id);

		// 查询结果
		com.lichkin.application.apis.api50206.P.n00.O o = dao.getOne(sql, com.lichkin.application.apis.api50206.P.n00.O.class);

		// 设置值
		datas.put("returnedName", o.getReturnedName());
		datas.put("storageName", o.getStorageName());
		datas.put("orderAmount", o.getOrderAmount());
	}

}
