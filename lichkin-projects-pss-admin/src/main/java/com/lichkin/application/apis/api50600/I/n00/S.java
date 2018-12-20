package com.lichkin.application.apis.api50600.I.n00;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lichkin.application.services.bus.impl.SysPssPurchaseReturnNotStockInOrderBusService;
import com.lichkin.framework.defines.enums.LKCodeEnum;
import com.lichkin.framework.defines.enums.impl.ApprovalStatusEnum;
import com.lichkin.framework.defines.exceptions.LKRuntimeException;
import com.lichkin.springframework.controllers.ApiKeyValues;
import com.lichkin.springframework.entities.impl.SysPssPurchaseReturnNotStockInOrderEntity;
import com.lichkin.springframework.services.LKApiBusInsertWithoutCheckerService;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Service("SysPssPurchaseReturnNotStockInOrderI00Service")
public class S extends LKApiBusInsertWithoutCheckerService<I, SysPssPurchaseReturnNotStockInOrderEntity> {

	@Autowired
	private SysPssPurchaseReturnNotStockInOrderBusService busService;


	@Getter
	@RequiredArgsConstructor
	enum ErrorCodes implements LKCodeEnum {
		PSS_RETURN_ORDER_MSG(60000),

		;

		private final Integer code;

	}


	@Override
	protected void beforeAddNew(I sin, ApiKeyValues<I> params, SysPssPurchaseReturnNotStockInOrderEntity entity) {
		entity.setOrderNo(busService.analysisOrderNo());
	}


	@Override
	protected void beforeSaveMain(I sin, ApiKeyValues<I> params, SysPssPurchaseReturnNotStockInOrderEntity entity) {
		entity.setOrderAmount(busService.analysisOrderAmount(sin));
		String errorMsg = busService.checkProductQty(entity.getId(), sin.getOrderId(), sin.getProductList());
		if (StringUtils.isNotBlank(errorMsg)) {
			throw new LKRuntimeException(ErrorCodes.PSS_RETURN_ORDER_MSG).withParam("#prodName", errorMsg);
		}
		entity.setApprovalStatus(ApprovalStatusEnum.PENDING);
	}


	@Override
	protected void addSubs(I sin, ApiKeyValues<I> params, SysPssPurchaseReturnNotStockInOrderEntity entity, String id) {
		busService.addPurchaseReturnOrderProduct(id, sin.getListProduct());
	}

}
