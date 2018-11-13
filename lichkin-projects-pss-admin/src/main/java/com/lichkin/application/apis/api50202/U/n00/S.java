package com.lichkin.application.apis.api50202.U.n00;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lichkin.application.services.bus.impl.SysPssSellStockOrderBusService;
import com.lichkin.framework.defines.enums.LKCodeEnum;
import com.lichkin.framework.defines.exceptions.LKRuntimeException;
import com.lichkin.springframework.entities.impl.SysPssSellStockOrderEntity;
import com.lichkin.springframework.services.LKApiBusUpdateWithoutCheckerService;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Service("SysPssSellStockOrderU00Service")
public class S extends LKApiBusUpdateWithoutCheckerService<I, SysPssSellStockOrderEntity> {

	@Autowired
	private SysPssSellStockOrderBusService busService;


	@Getter
	@RequiredArgsConstructor
	enum ErrorCodes implements LKCodeEnum {
		PSS_STOCK_OUT_MSG(60000),

		;

		private final Integer code;

	}


	@Override
	protected void beforeSaveMain(I sin, String locale, String compId, String loginId, SysPssSellStockOrderEntity entity) {
		// 销售出库 （进行出库数量校验）
		if (entity.getOrderType().equals(Boolean.FALSE)) {
			String errorMsg = busService.checkProductQty(entity.getId(), entity.getStorageId(), entity.getOrderId(), sin.getProductList());
			if (StringUtils.isNotBlank(errorMsg)) {
				throw new LKRuntimeException(ErrorCodes.PSS_STOCK_OUT_MSG).withParam("#prodName", errorMsg);
			}
		}
	}


	@Override
	protected void clearSubs(I sin, String locale, String compId, String loginId, SysPssSellStockOrderEntity entity, String id) {
		busService.clearPssSellStockOrderProduct(id);
	}


	@Override
	protected void addSubs(I sin, String locale, String compId, String loginId, SysPssSellStockOrderEntity entity, String id) {
		busService.addPssSellStockOrderProduct(id, sin.getProductList());
	}

}
