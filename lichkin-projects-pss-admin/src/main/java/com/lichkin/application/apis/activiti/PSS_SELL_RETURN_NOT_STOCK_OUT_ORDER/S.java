package com.lichkin.application.apis.activiti.PSS_SELL_RETURN_NOT_STOCK_OUT_ORDER;

import java.util.Map;

import org.springframework.stereotype.Service;

import com.lichkin.defines.PssStatics;
import com.lichkin.framework.db.beans.Condition;
import com.lichkin.framework.db.beans.QuerySQL;
import com.lichkin.framework.db.beans.SysEmployeeR;
import com.lichkin.framework.db.beans.SysPssSellOrderR;
import com.lichkin.framework.db.beans.SysPssSellReturnNotStockOutOrderR;
import com.lichkin.springframework.controllers.ApiKeyValues;
import com.lichkin.springframework.entities.impl.SysEmployeeEntity;
import com.lichkin.springframework.entities.impl.SysPssSellOrderEntity;
import com.lichkin.springframework.entities.impl.SysPssSellReturnNotStockOutOrderEntity;
import com.lichkin.springframework.services.LKApiBusStartProcessService;

@Service("SysPssSellReturnNotStockOutOrderU01Service")
public class S extends LKApiBusStartProcessService<I, SysPssSellReturnNotStockOutOrderEntity> {

	@Override
	protected void initFormData(I sin, ApiKeyValues<I> params, SysPssSellReturnNotStockOutOrderEntity entity, Map<String, Object> datas) {
		// 订单统一参数
		datas.put("orderNo", entity.getOrderNo());
		datas.put("billDate", entity.getBillDate());
		datas.put("remarks", entity.getRemarks());
		// 关联表参数转换
		setOrderDatas(datas, entity.getId());
	}


	@Override
	protected String getProcessCode(I sin, ApiKeyValues<I> params, SysPssSellReturnNotStockOutOrderEntity entity) {
		return PssStatics.PSS_SELL_RETURN_NOT_STOCK_OUT_ORDER;
	}


	private void setOrderDatas(Map<String, Object> datas, String id) {
		QuerySQL sql = new QuerySQL(SysPssSellReturnNotStockOutOrderEntity.class);

		// P接口逻辑简化
		// 主表
		sql.select(SysPssSellReturnNotStockOutOrderR.orderAmount);

		// 关联表
		sql.innerJoin(SysPssSellOrderEntity.class, new Condition(SysPssSellReturnNotStockOutOrderR.orderId, SysPssSellOrderR.id));
		sql.select(SysPssSellOrderR.orderNo, "sellOrderNo");
		sql.select(SysPssSellOrderR.billDate, "sellBillDate");
		sql.select(SysPssSellOrderR.orderAmount, "sellOrderAmount");
		sql.innerJoin(SysEmployeeEntity.class, new Condition(0, SysEmployeeR.id, SysPssSellOrderR.salesId));
		sql.select(SysEmployeeR.userName, "salesName");

		sql.innerJoin(SysEmployeeEntity.class, new Condition(1, SysEmployeeR.id, SysPssSellReturnNotStockOutOrderR.salesId));
		sql.select(1, SysEmployeeR.userName, "returnedName");

		sql.eq(SysPssSellReturnNotStockOutOrderR.id, id);

		// 查询结果
		com.lichkin.application.apis.api50204.P.n00.O o = dao.getOne(sql, com.lichkin.application.apis.api50204.P.n00.O.class);

		// 设置值
		datas.put("returnedName", o.getReturnedName());
		datas.put("orderAmount", o.getOrderAmount());

		datas.put("sellOrderNo", o.getSellOrderNo());
		datas.put("sellBillDate", o.getSellBillDate());
		datas.put("sellOrderAmount", o.getSellOrderAmount());
		datas.put("salesName", o.getSalesName());
	}

}
