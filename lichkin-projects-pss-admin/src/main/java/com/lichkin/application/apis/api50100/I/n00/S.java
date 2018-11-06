package com.lichkin.application.apis.api50100.I.n00;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lichkin.application.services.bus.impl.SysPssPurchaseOrderBusService;
import com.lichkin.framework.defines.enums.LKCodeEnum;
import com.lichkin.framework.defines.enums.impl.ApprovalStatusEnum;
import com.lichkin.framework.defines.enums.impl.InventoryStatusEnum;
import com.lichkin.springframework.entities.impl.SysPssPurchaseOrderEntity;
import com.lichkin.springframework.services.LKApiBusInsertWithoutCheckerService;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Service("SysPssPurchaseOrderI00Service")
public class S extends LKApiBusInsertWithoutCheckerService<I, SysPssPurchaseOrderEntity> {

	@Autowired
	private SysPssPurchaseOrderBusService busService;


	@Getter
	@RequiredArgsConstructor
	enum ErrorCodes implements LKCodeEnum {

		;

		private final Integer code;

	}


	@Override
	protected void beforeAddNew(I sin, String locale, String compId, String loginId, SysPssPurchaseOrderEntity entity) {
		entity.setCompId(getCompId(compId, sin.getCompId()));
		entity.setOrderNo(busService.analysisOrderNo());
	}


	@Override
	protected void beforeSaveMain(I sin, String locale, String compId, String loginId, SysPssPurchaseOrderEntity entity) {
		entity.setOrderAmount(busService.analysisOrderAmount(sin));
		entity.setApprovalStatus(ApprovalStatusEnum.PENDING);
		entity.setInventoryStatus(InventoryStatusEnum.NOT);
	}


	@Override
	protected void addSubs(I sin, String locale, String compId, String loginId, SysPssPurchaseOrderEntity entity, String id) {
		busService.addPssPurchaseOrderProduct(id, sin.getListProduct());
	}

}
