package com.lichkin.application.apis.api50206.U.n00;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lichkin.application.services.bus.impl.SysPssSellReturnStockOutOrderBusService;
import com.lichkin.springframework.controllers.ApiKeyValues;
import com.lichkin.springframework.entities.impl.SysPssSellReturnStockOutOrderEntity;
import com.lichkin.springframework.services.LKApiBusUpdateWithoutCheckerService;

@Service("SysPssSellReturnStockOutOrderU00Service")
public class S extends LKApiBusUpdateWithoutCheckerService<I, SysPssSellReturnStockOutOrderEntity> {

	@Autowired
	private SysPssSellReturnStockOutOrderBusService busService;


	@Override
	protected void beforeSaveMain(I sin, ApiKeyValues<I> params, SysPssSellReturnStockOutOrderEntity entity) {
		entity.setOrderAmount(busService.analysisOrderAmount(sin));

	}


	@Override
	protected void clearSubs(I sin, ApiKeyValues<I> params, SysPssSellReturnStockOutOrderEntity entity, String id) {
		busService.clearOrderProduct(id);
	}


	@Override
	protected void addSubs(I sin, ApiKeyValues<I> params, SysPssSellReturnStockOutOrderEntity entity, String id) {
		busService.addOrderProduct(id, sin.getListProduct());
	}

}
