package com.lichkin.application.apis.api50200.U.n00;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lichkin.application.services.bus.impl.SysPssSellOrderBusService;
import com.lichkin.springframework.entities.impl.SysPssSellOrderEntity;
import com.lichkin.springframework.services.LKApiBusUpdateWithoutCheckerService;

@Service("SysPssSellOrderU00Service")
public class S extends LKApiBusUpdateWithoutCheckerService<I, SysPssSellOrderEntity> {

	@Autowired
	private SysPssSellOrderBusService busService;


	@Override
	protected void beforeSaveMain(I sin, String locale, String compId, String loginId, SysPssSellOrderEntity entity) {
		entity.setOrderAmount(busService.analysisOrderAmount(sin));
	}


	@Override
	protected void clearSubs(I sin, String locale, String compId, String loginId, SysPssSellOrderEntity entity, String id) {
		busService.clearPssSellOrderProduct(id);
	}


	@Override
	protected void addSubs(I sin, String locale, String compId, String loginId, SysPssSellOrderEntity entity, String id) {
		busService.addPssSellOrderProduct(id, sin.getListProduct());
	}

}
