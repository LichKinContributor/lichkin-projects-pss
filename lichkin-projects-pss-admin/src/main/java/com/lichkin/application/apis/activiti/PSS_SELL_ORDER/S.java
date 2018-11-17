package com.lichkin.application.apis.activiti.PSS_SELL_ORDER;

import java.util.Map;

import org.springframework.stereotype.Service;

import com.lichkin.defines.PssStatics;
import com.lichkin.framework.db.beans.QuerySQL;
import com.lichkin.framework.db.beans.SysEmployeeR;
import com.lichkin.springframework.entities.impl.SysEmployeeEntity;
import com.lichkin.springframework.entities.impl.SysPssSellOrderEntity;
import com.lichkin.springframework.services.LKApiBusStartProcessService;

@Service("SysPssSellOrderU01Service")
public class S extends LKApiBusStartProcessService<I, SysPssSellOrderEntity> {

	@Override
	protected void initFormData(I sin, String locale, String compId, String loginId, SysPssSellOrderEntity entity, Map<String, Object> datas) {
		// 订单统一参数
		datas.put("orderNo", entity.getOrderNo());
		datas.put("billDate", entity.getBillDate());
		datas.put("remarks", entity.getRemarks());
		// 销售订单特有
		datas.put("salesId", entity.getSalesId());
		datas.put("orderAmount", entity.getOrderAmount());
		// 关联表参数转换
		datas.put("salesName", getSalesName(entity.getSalesId()));
	}


	@Override
	protected String getProcessCode(I sin, String locale, String compId, String loginId, SysPssSellOrderEntity entity) {
		return PssStatics.PSS_SELL_ORDER;
	}


	private String getSalesName(String id) {
		QuerySQL sql = new QuerySQL(SysEmployeeEntity.class);
		sql.select(SysEmployeeR.userName);
		sql.eq(SysEmployeeR.id, id);
		return dao.getString(sql);
	}

}
