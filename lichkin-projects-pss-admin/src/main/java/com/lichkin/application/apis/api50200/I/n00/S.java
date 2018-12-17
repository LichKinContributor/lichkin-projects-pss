package com.lichkin.application.apis.api50200.I.n00;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lichkin.application.services.bus.impl.SysPssSellOrderBusService;
import com.lichkin.framework.defines.enums.LKCodeEnum;
import com.lichkin.framework.defines.enums.impl.ApprovalStatusEnum;
import com.lichkin.framework.defines.enums.impl.InventoryStatusEnum;
import com.lichkin.springframework.controllers.ApiKeyValues;
import com.lichkin.springframework.entities.impl.SysPssSellOrderEntity;
import com.lichkin.springframework.services.LKApiBusInsertWithoutCheckerService;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Service("SysPssSellOrderI00Service")
public class S extends LKApiBusInsertWithoutCheckerService<I, SysPssSellOrderEntity> {

	@Autowired
	private SysPssSellOrderBusService busService;


	@Getter
	@RequiredArgsConstructor
	enum ErrorCodes implements LKCodeEnum {

		;

		private final Integer code;

	}


	@Override
	protected void beforeAddNew(I sin, ApiKeyValues<I> params, SysPssSellOrderEntity entity) {
		entity.setOrderNo(busService.analysisOrderNo());
	}


	@Override
	protected void beforeSaveMain(I sin, ApiKeyValues<I> params, SysPssSellOrderEntity entity) {
		entity.setOrderAmount(busService.analysisOrderAmount(sin));
		entity.setApprovalStatus(ApprovalStatusEnum.PENDING);
		entity.setInventoryStatus(InventoryStatusEnum.NOT);
	}


	@Override
	protected void addSubs(I sin, ApiKeyValues<I> params, SysPssSellOrderEntity entity, String id) {
		busService.addPssSellOrderProduct(id, sin.getListProduct());
	}

}
