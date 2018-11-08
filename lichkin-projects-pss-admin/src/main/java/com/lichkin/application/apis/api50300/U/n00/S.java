package com.lichkin.application.apis.api50300.U.n00;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lichkin.application.services.bus.impl.SysPssOtherStockOrderBusService;
import com.lichkin.springframework.entities.impl.SysPssOtherStockOrderEntity;
import com.lichkin.springframework.services.LKApiBusUpdateWithoutCheckerService;

@Service("SysPssOtherStockOrderU00Service")
public class S extends LKApiBusUpdateWithoutCheckerService<I, SysPssOtherStockOrderEntity> {

	@Autowired
	private SysPssOtherStockOrderBusService busService;


	@Override
	protected void clearSubs(I sin, String locale, String compId, String loginId, SysPssOtherStockOrderEntity entity, String id) {
		busService.clearPssOtherStockOrderProduct(id);
	}


	@Override
	protected void addSubs(I sin, String locale, String compId, String loginId, SysPssOtherStockOrderEntity entity, String id) {
		busService.addPssOtherStockOrderProduct(id, sin.getProductList());
	}

}
