package com.lichkin.application.apis.api50400.U.n00;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lichkin.application.services.bus.impl.SysPssAllotOrderBusService;
import com.lichkin.application.services.bus.impl.SysPssStockBusService;
import com.lichkin.framework.defines.enums.LKCodeEnum;
import com.lichkin.framework.defines.exceptions.LKRuntimeException;
import com.lichkin.springframework.controllers.ApiKeyValues;
import com.lichkin.springframework.entities.impl.SysPssAllotOrderEntity;
import com.lichkin.springframework.services.LKApiBusUpdateWithoutCheckerService;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Service("SysPssAllotOrderU00Service")
public class S extends LKApiBusUpdateWithoutCheckerService<I, SysPssAllotOrderEntity> {

	@Autowired
	private SysPssAllotOrderBusService busService;

	@Autowired
	private SysPssStockBusService stockBusService;


	@Getter
	@RequiredArgsConstructor
	enum ErrorCodes implements LKCodeEnum {

		PSS_STOCK_OUT_MSG(60000),

		;

		private final Integer code;

	}


	@Override
	protected void beforeSaveMain(I sin, ApiKeyValues<I> params, SysPssAllotOrderEntity entity) {
		// 出库需做校验处理
		String errorMsg = stockBusService.checkProductStockOut(entity.getOutStorageId(), sin.getProductList(), entity.getId());
		if (StringUtils.isNotBlank(errorMsg)) {
			throw new LKRuntimeException(ErrorCodes.PSS_STOCK_OUT_MSG).withParam("#prodName", errorMsg);
		}
	}


	@Override
	protected void clearSubs(I sin, ApiKeyValues<I> params, SysPssAllotOrderEntity entity, String id) {
		busService.clearPssAllotOrderProduct(id);
	}


	@Override
	protected void addSubs(I sin, ApiKeyValues<I> params, SysPssAllotOrderEntity entity, String id) {
		busService.addPssAllotOrderProduct(id, sin.getProductList());
	}

}
