package com.lichkin.application.apis.api50204.U.n00;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lichkin.application.services.bus.impl.SysPssSellReturnNotStockOutOrderBusService;
import com.lichkin.framework.defines.enums.LKCodeEnum;
import com.lichkin.framework.defines.exceptions.LKRuntimeException;
import com.lichkin.springframework.controllers.ApiKeyValues;
import com.lichkin.springframework.entities.impl.SysPssSellReturnNotStockOutOrderEntity;
import com.lichkin.springframework.services.LKApiBusUpdateWithoutCheckerService;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Service("SysPssSellReturnNotStockOutOrderU00Service")
public class S extends LKApiBusUpdateWithoutCheckerService<I, SysPssSellReturnNotStockOutOrderEntity> {

	@Autowired
	private SysPssSellReturnNotStockOutOrderBusService busService;


	@Getter
	@RequiredArgsConstructor
	enum ErrorCodes implements LKCodeEnum {
		PSS_RETURN_ORDER_MSG(60000),

		;

		private final Integer code;

	}


	@Override
	protected void beforeSaveMain(I sin, ApiKeyValues<I> params, SysPssSellReturnNotStockOutOrderEntity entity) {
		entity.setOrderAmount(busService.analysisOrderAmount(sin));
		String errorMsg = busService.checkProductQty(entity.getId(), entity.getOrderId(), sin.getProductList());
		if (StringUtils.isNotBlank(errorMsg)) {
			throw new LKRuntimeException(ErrorCodes.PSS_RETURN_ORDER_MSG).withParam("#prodName", errorMsg);
		}
	}


	@Override
	protected void clearSubs(I sin, ApiKeyValues<I> params, SysPssSellReturnNotStockOutOrderEntity entity, String id) {
		busService.clearOrderProduct(id);
	}


	@Override
	protected void addSubs(I sin, ApiKeyValues<I> params, SysPssSellReturnNotStockOutOrderEntity entity, String id) {
		busService.addOrderProduct(id, sin.getListProduct());
	}

}
