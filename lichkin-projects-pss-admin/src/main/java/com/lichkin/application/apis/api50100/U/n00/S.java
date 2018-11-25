package com.lichkin.application.apis.api50100.U.n00;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lichkin.application.services.bus.impl.SysPssPurchaseOrderBusService;
import com.lichkin.springframework.controllers.ApiKeyValues;
import com.lichkin.springframework.entities.impl.SysPssPurchaseOrderEntity;
import com.lichkin.springframework.services.LKApiBusUpdateWithoutCheckerService;

@Service("SysPssPurchaseOrderU00Service")
public class S extends LKApiBusUpdateWithoutCheckerService<I, SysPssPurchaseOrderEntity> {

	@Autowired
	private SysPssPurchaseOrderBusService busService;


	@Override
	protected void beforeSaveMain(I sin, ApiKeyValues<I> params, SysPssPurchaseOrderEntity entity) {
		entity.setOrderAmount(busService.analysisOrderAmount(sin));
	}


	@Override
	protected void clearSubs(I sin, ApiKeyValues<I> params, SysPssPurchaseOrderEntity entity, String id) {
		busService.clearPssPurchaseOrderProduct(id);
	}


	@Override
	protected void addSubs(I sin, ApiKeyValues<I> params, SysPssPurchaseOrderEntity entity, String id) {
		busService.addPssPurchaseOrderProduct(id, sin.getListProduct());
	}

}
