package com.lichkin.application.apis.api50206.I.n00;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lichkin.application.services.bus.impl.SysPssSellReturnStockOutOrderBusService;
import com.lichkin.framework.defines.enums.impl.ApprovalStatusEnum;
import com.lichkin.springframework.controllers.ApiKeyValues;
import com.lichkin.springframework.entities.impl.SysPssSellReturnStockOutOrderEntity;
import com.lichkin.springframework.services.LKApiBusInsertWithoutCheckerService;

@Service("SysPssSellReturnStockOutOrderI00Service")
public class S extends LKApiBusInsertWithoutCheckerService<I, SysPssSellReturnStockOutOrderEntity> {

	@Autowired
	private SysPssSellReturnStockOutOrderBusService busService;


	@Override
	protected void beforeAddNew(I sin, ApiKeyValues<I> params, SysPssSellReturnStockOutOrderEntity entity) {
		entity.setOrderNo(busService.analysisOrderNo());
	}


	@Override
	protected void beforeSaveMain(I sin, ApiKeyValues<I> params, SysPssSellReturnStockOutOrderEntity entity) {
		entity.setOrderType(true);// 销售退货入库
		entity.setOrderAmount(busService.analysisOrderAmount(sin));
		entity.setApprovalStatus(ApprovalStatusEnum.PENDING);
	}


	@Override
	protected void addSubs(I sin, ApiKeyValues<I> params, SysPssSellReturnStockOutOrderEntity entity, String id) {
		busService.addOrderProduct(id, sin.getListProduct());
	}

}
