package com.lichkin.application.apis.api50400.U.n00;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lichkin.application.services.bus.impl.SysPssAllotOrderBusService;
import com.lichkin.springframework.entities.impl.SysPssAllotOrderEntity;
import com.lichkin.springframework.services.LKApiBusUpdateWithoutCheckerService;

@Service("SysPssAllotOrderU00Service")
public class S extends LKApiBusUpdateWithoutCheckerService<I, SysPssAllotOrderEntity> {

	@Autowired
	private SysPssAllotOrderBusService busService;


	@Override
	protected void clearSubs(I sin, String locale, String compId, String loginId, SysPssAllotOrderEntity entity, String id) {
		busService.clearPssAllotOrderProduct(id);
	}


	@Override
	protected void addSubs(I sin, String locale, String compId, String loginId, SysPssAllotOrderEntity entity, String id) {
		busService.addPssAllotOrderProduct(id, sin.getProductList());
	}

}
