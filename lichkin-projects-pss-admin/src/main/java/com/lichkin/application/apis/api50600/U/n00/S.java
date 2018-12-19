package com.lichkin.application.apis.api50600.U.n00;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lichkin.application.services.bus.impl.SysPssPurchaseReturnNotStockInOrderBusService;
import com.lichkin.framework.defines.enums.LKCodeEnum;
import com.lichkin.framework.defines.exceptions.LKRuntimeException;
import com.lichkin.springframework.controllers.ApiKeyValues;
import com.lichkin.springframework.entities.impl.SysPssPurchaseReturnNotStockInOrderEntity;
import com.lichkin.springframework.services.LKApiBusUpdateWithoutCheckerService;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Service("SysPssPurchaseReturnNotStockInOrderU00Service")
public class S extends LKApiBusUpdateWithoutCheckerService<I, SysPssPurchaseReturnNotStockInOrderEntity> {

	@Autowired
	private SysPssPurchaseReturnNotStockInOrderBusService busService;


	@Getter
	@RequiredArgsConstructor
	enum ErrorCodes implements LKCodeEnum {
		PSS_PURCHASE_RETURN_NOT_STOCK_IN_ORDER_MSG(60000),

		;

		private final Integer code;

	}


	@Override
	protected void clearSubs(I sin, ApiKeyValues<I> params, SysPssPurchaseReturnNotStockInOrderEntity entity, String id) {
		busService.clearPurchaseReturnOrderProduct(id);
	}


	@Override
	protected void beforeSaveMain(I sin, ApiKeyValues<I> params, SysPssPurchaseReturnNotStockInOrderEntity entity) {
		entity.setOrderAmount(busService.analysisOrderAmount(sin));
		String errorMsg = busService.checkProductQty(entity.getId(), entity.getOrderId(), sin.getProductList());
		if (StringUtils.isNotBlank(errorMsg)) {
			throw new LKRuntimeException(ErrorCodes.PSS_PURCHASE_RETURN_NOT_STOCK_IN_ORDER_MSG).withParam("#prodName", errorMsg);
		}
	}


	@Override
	protected void addSubs(I sin, ApiKeyValues<I> params, SysPssPurchaseReturnNotStockInOrderEntity entity, String id) {
		busService.addPurchaseReturnOrderProduct(id, sin.getListProduct());
	}

}
