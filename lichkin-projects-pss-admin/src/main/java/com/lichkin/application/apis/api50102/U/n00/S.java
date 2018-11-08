package com.lichkin.application.apis.api50102.U.n00;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lichkin.application.services.bus.impl.SysPssPurchaseStockOrderBusService;
import com.lichkin.springframework.entities.impl.SysPssPurchaseStockOrderEntity;
import com.lichkin.springframework.services.LKApiBusUpdateWithoutCheckerService;

@Service("SysPssPurchaseStockOrderU00Service")
public class S extends LKApiBusUpdateWithoutCheckerService<I, SysPssPurchaseStockOrderEntity> {

	@Autowired
	private SysPssPurchaseStockOrderBusService busService;


	@Override
	protected void clearSubs(I sin, String locale, String compId, String loginId, SysPssPurchaseStockOrderEntity entity, String id) {
		busService.clearPssPurchaseStockOrderProduct(id);
	}


	@Override
	protected void addSubs(I sin, String locale, String compId, String loginId, SysPssPurchaseStockOrderEntity entity, String id) {
		busService.addPssPurchaseStockOrderProduct(id, sin.getProductList());
	}

}
