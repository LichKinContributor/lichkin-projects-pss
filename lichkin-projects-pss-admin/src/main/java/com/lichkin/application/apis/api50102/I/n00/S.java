package com.lichkin.application.apis.api50102.I.n00;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lichkin.application.services.bus.impl.SysPssPurchaseStockOrderBusService;
import com.lichkin.framework.defines.enums.LKCodeEnum;
import com.lichkin.springframework.entities.impl.SysPssPurchaseStockOrderEntity;
import com.lichkin.springframework.services.LKApiBusInsertWithoutCheckerService;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Service("SysPssPurchaseStockOrderI00Service")
public class S extends LKApiBusInsertWithoutCheckerService<I, SysPssPurchaseStockOrderEntity> {

	@Autowired
	private SysPssPurchaseStockOrderBusService busService;


	@Getter
	@RequiredArgsConstructor
	enum ErrorCodes implements LKCodeEnum {

		;

		private final Integer code;

	}


	@Override
	protected void beforeAddNew(I sin, String locale, String compId, String loginId, SysPssPurchaseStockOrderEntity entity) {
		entity.setCompId(getCompId(compId, sin.getCompId()));
		entity.setOrderNo(busService.analysisOrderNo());
	}


	@Override
	protected void addSubs(I sin, String locale, String compId, String loginId, SysPssPurchaseStockOrderEntity entity, String id) {
		busService.addPssPurchaseStockOrderProduct(id, sin.getProductList());
	}

}
