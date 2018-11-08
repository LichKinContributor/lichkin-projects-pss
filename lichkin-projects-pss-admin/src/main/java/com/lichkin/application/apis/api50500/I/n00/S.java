package com.lichkin.application.apis.api50500.I.n00;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lichkin.application.services.bus.impl.SysPssStockCheckOrderBusService;
import com.lichkin.framework.defines.enums.LKCodeEnum;
import com.lichkin.framework.defines.enums.impl.ApprovalStatusEnum;
import com.lichkin.framework.json.LKJsonUtils;
import com.lichkin.springframework.entities.impl.SysPssStockCheckOrderEntity;
import com.lichkin.springframework.entities.impl.SysPssStockCheckOrderProductEntity;
import com.lichkin.springframework.services.LKApiBusInsertWithoutCheckerService;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Service("SysPssStockCheckOrderI00Service")
public class S extends LKApiBusInsertWithoutCheckerService<I, SysPssStockCheckOrderEntity> {

	@Autowired
	private SysPssStockCheckOrderBusService busService;


	@Getter
	@RequiredArgsConstructor
	enum ErrorCodes implements LKCodeEnum {

		;

		private final Integer code;

	}


	@Override
	protected void beforeAddNew(I sin, String locale, String compId, String loginId, SysPssStockCheckOrderEntity entity) {
		entity.setCompId(getCompId(compId, sin.getCompId()));
		entity.setOrderNo(busService.analysisOrderNo());
	}


	@Override
	protected void beforeSaveMain(I sin, String locale, String compId, String loginId, SysPssStockCheckOrderEntity entity) {
		List<SysPssStockCheckOrderProductEntity> listProduct = LKJsonUtils.toList(sin.getProductList(), SysPssStockCheckOrderProductEntity.class);
		entity.setStockCheckCount(listProduct.size());
		entity.setApprovalStatus(ApprovalStatusEnum.PENDING);
	}


	@Override
	protected void addSubs(I sin, String locale, String compId, String loginId, SysPssStockCheckOrderEntity entity, String id) {
		busService.addPssStockCheckOrderProduct(id, sin.getProductList());
	}

}
