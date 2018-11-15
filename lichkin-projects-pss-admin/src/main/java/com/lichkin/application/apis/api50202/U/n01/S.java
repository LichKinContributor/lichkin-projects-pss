package com.lichkin.application.apis.api50202.U.n01;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lichkin.application.services.impl.ActivitiStartProcessService;
import com.lichkin.application.services.impl.SysPssSellStockOrderApprovedService;
import com.lichkin.framework.db.beans.Condition;
import com.lichkin.framework.db.beans.QuerySQL;
import com.lichkin.framework.db.beans.SysEmployeeR;
import com.lichkin.framework.db.beans.SysPssSellOrderR;
import com.lichkin.framework.defines.enums.impl.ApprovalStatusEnum;
import com.lichkin.framework.json.LKJsonUtils;
import com.lichkin.springframework.entities.impl.SysEmployeeEntity;
import com.lichkin.springframework.entities.impl.SysPssSellOrderEntity;
import com.lichkin.springframework.entities.impl.SysPssSellStockOrderEntity;
import com.lichkin.springframework.services.LKApiBusUpdateWithoutCheckerService;

@Service("SysPssSellStockOrderU01Service")
public class S extends LKApiBusUpdateWithoutCheckerService<I, SysPssSellStockOrderEntity> {

	@Autowired
	private ActivitiStartProcessService activitiStartProcessService;

	@Autowired
	private SysPssSellStockOrderApprovedService approvedService;


	@Override
	protected void beforeSaveMain(I sin, String locale, String compId, String loginId, SysPssSellStockOrderEntity entity) {
		Map<String, Object> datas = new HashMap<>();
		// 必填
		datas.put("id", entity.getId());
		// 订单统一参数
		datas.put("orderNo", entity.getOrderNo());
		datas.put("billDate", entity.getBillDate());
		datas.put("remarks", entity.getRemarks());
		// 关联表参数转换
		setOrderDatas(datas, entity.getOrderId());
		// 发起流程
		activitiStartProcessService.startByAdmin(entity, compId, entity.getOrderType() ? "PSS_SELL_STOCK_IN_ORDER" : "PSS_SELL_STOCK_OUT_ORDER", loginId, sin.getDatas().getUser().getUserName(), LKJsonUtils.toJson(datas));
	}


	private void setOrderDatas(Map<String, Object> datas, String orderId) {
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
		com.lichkin.application.apis.api50202.P.n00.O o = dao.getOne(sql, com.lichkin.application.apis.api50202.P.n00.O.class);

		// 设置值
		datas.put("sellOrderNo", o.getSellOrderNo());
		datas.put("sellBillDate", o.getSellBillDate());
		datas.put("sellOrderAmount", o.getSellOrderAmount());
		datas.put("salesName", o.getSalesName());
	}


	@Override
	protected void afterSaveMain(I sin, String locale, String compId, String loginId, SysPssSellStockOrderEntity entity, String id) {
		if (ApprovalStatusEnum.APPROVED.equals(entity.getApprovalStatus())) {
			approvedService.changeStockQty(entity);
		}
	}

}
