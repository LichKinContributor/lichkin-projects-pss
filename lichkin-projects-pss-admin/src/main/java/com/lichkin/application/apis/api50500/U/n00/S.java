package com.lichkin.application.apis.api50500.U.n00;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lichkin.application.services.bus.impl.SysPssStockCheckOrderBusService;
import com.lichkin.framework.defines.enums.LKCodeEnum;
import com.lichkin.framework.defines.enums.impl.LKDateTimeTypeEnum;
import com.lichkin.framework.defines.exceptions.LKRuntimeException;
import com.lichkin.framework.json.LKJsonUtils;
import com.lichkin.framework.utils.LKDateTimeUtils;
import com.lichkin.springframework.controllers.ApiKeyValues;
import com.lichkin.springframework.entities.impl.SysPssStockCheckOrderEntity;
import com.lichkin.springframework.entities.impl.SysPssStockCheckOrderProductEntity;
import com.lichkin.springframework.services.LKApiBusUpdateWithoutCheckerService;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Service("SysPssStockCheckOrderU00Service")
public class S extends LKApiBusUpdateWithoutCheckerService<I, SysPssStockCheckOrderEntity> {

	@Autowired
	private SysPssStockCheckOrderBusService busService;


	@Getter
	@RequiredArgsConstructor
	enum ErrorCodes implements LKCodeEnum {
		PSS_STOCK_CHECK_MSG(60000),

		;

		private final Integer code;

	}


	@Override
	protected void clearSubs(I sin, ApiKeyValues<I> params, SysPssStockCheckOrderEntity entity, String id) {
		busService.clearPssStockCheckOrderProduct(id);
	}


	@Override
	protected void beforeSaveMain(I sin, ApiKeyValues<I> params, SysPssStockCheckOrderEntity entity) {
		entity.setBillDate(LKDateTimeUtils.now(LKDateTimeTypeEnum.DATE_ONLY));
		List<SysPssStockCheckOrderProductEntity> listProduct = LKJsonUtils.toList(sin.getProductList(), SysPssStockCheckOrderProductEntity.class);
		// 校验处理
		String errorMsg = busService.checkProdExist(entity, listProduct);
		if (StringUtils.isNotBlank(errorMsg)) {
			throw new LKRuntimeException(ErrorCodes.PSS_STOCK_CHECK_MSG).withParam("#prodName", errorMsg);
		}
		entity.setStockCheckCount(listProduct.size());
	}


	@Override
	protected void addSubs(I sin, ApiKeyValues<I> params, SysPssStockCheckOrderEntity entity, String id) {
		busService.addPssStockCheckOrderProduct(id, sin.getProductList());
	}

}
