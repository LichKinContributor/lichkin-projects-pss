package com.lichkin.application.apis.api50200.U.n01;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lichkin.application.services.impl.ActivitiStartProcessService;
import com.lichkin.framework.db.beans.QuerySQL;
import com.lichkin.framework.db.beans.SysEmployeeR;
import com.lichkin.framework.json.LKJsonUtils;
import com.lichkin.springframework.entities.impl.SysEmployeeEntity;
import com.lichkin.springframework.entities.impl.SysPssSellOrderEntity;
import com.lichkin.springframework.services.LKApiBusUpdateWithoutCheckerService;

@Service("SysPssSellOrderU01Service")
public class S extends LKApiBusUpdateWithoutCheckerService<I, SysPssSellOrderEntity> {

	@Autowired
	private ActivitiStartProcessService activitiStartProcessService;


	@Override
	protected void beforeSaveMain(I sin, String locale, String compId, String loginId, SysPssSellOrderEntity entity) {
		Map<String, Object> datas = new HashMap<>();
		// 必填
		datas.put("id", entity.getId());
		// 订单统一参数
		datas.put("orderNo", entity.getOrderNo());
		datas.put("billDate", entity.getBillDate());
		datas.put("remarks", entity.getRemarks());
		// 销售订单特有
		datas.put("salesId", entity.getSalesId());
		datas.put("orderAmount", entity.getOrderAmount());
		// 关联表参数转换
		datas.put("salesName", getSalesName(entity.getSalesId()));
		// 发起流程
		activitiStartProcessService.startByAdmin(entity, compId, "PSS_SELL_ORDER", loginId, sin.getDatas().getUser().getUserName(), LKJsonUtils.toJson(datas));
	}


	private String getSalesName(String id) {
		QuerySQL sql = new QuerySQL(SysEmployeeEntity.class);
		sql.select(SysEmployeeR.userName);
		sql.eq(SysEmployeeR.id, id);
		return dao.getString(sql);
	}

}
