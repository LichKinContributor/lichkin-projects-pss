package com.lichkin.application.apis.api50700.U.n00;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lichkin.application.services.bus.impl.SysPssPurchaseReturnStockInOrderBusService;
import com.lichkin.application.services.bus.impl.SysPssStockBusService;
import com.lichkin.framework.defines.enums.LKCodeEnum;
import com.lichkin.framework.defines.exceptions.LKRuntimeException;
import com.lichkin.springframework.controllers.ApiKeyValues;
import com.lichkin.springframework.entities.impl.SysPssPurchaseReturnStockInOrderEntity;
import com.lichkin.springframework.services.LKApiBusUpdateWithoutCheckerService;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Service("SysPssPurchaseReturnStockInOrderU00Service")
public class S extends LKApiBusUpdateWithoutCheckerService<I, SysPssPurchaseReturnStockInOrderEntity> {

	@Autowired
	private SysPssPurchaseReturnStockInOrderBusService busService;

	@Autowired
	private SysPssStockBusService pssStockBusService;


	@Getter
	@RequiredArgsConstructor
	enum ErrorCodes implements LKCodeEnum {
		PSS_STOCK_OUT_MSG(60000),

		;

		private final Integer code;

	}


	@Override
	protected void clearSubs(I sin, ApiKeyValues<I> params, SysPssPurchaseReturnStockInOrderEntity entity, String id) {
		busService.clearPurchaseReturnOrderProduct(id);
	}


	@Override
	protected void beforeSaveMain(I sin, ApiKeyValues<I> params, SysPssPurchaseReturnStockInOrderEntity entity) {
		entity.setOrderAmount(busService.analysisOrderAmount(sin));
		String errorMsg = pssStockBusService.checkProductStockOut(entity.getStorageId(), sin.getProductList(), entity.getId());
		if (StringUtils.isNotBlank(errorMsg)) {
			throw new LKRuntimeException(ErrorCodes.PSS_STOCK_OUT_MSG).withParam("#prodName", errorMsg);
		}
	}


	@Override
	protected void addSubs(I sin, ApiKeyValues<I> params, SysPssPurchaseReturnStockInOrderEntity entity, String id) {
		busService.addPurchaseReturnOrderProduct(id, sin.getListProduct());
	}

}
