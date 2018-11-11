package com.lichkin.application.apis.api50102.U.n00;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lichkin.application.services.bus.impl.SysPssPurchaseStockOrderBusService;
import com.lichkin.framework.defines.enums.LKCodeEnum;
import com.lichkin.framework.defines.exceptions.LKRuntimeException;
import com.lichkin.springframework.entities.impl.SysPssPurchaseStockOrderEntity;
import com.lichkin.springframework.services.LKApiBusUpdateWithoutCheckerService;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Service("SysPssPurchaseStockOrderU00Service")
public class S extends LKApiBusUpdateWithoutCheckerService<I, SysPssPurchaseStockOrderEntity> {

	@Autowired
	private SysPssPurchaseStockOrderBusService busService;


	@Getter
	@RequiredArgsConstructor
	enum ErrorCodes implements LKCodeEnum {
		PSS_PURCHASE_STOCK_IN_MSG(60000),

		;

		private final Integer code;

	}


	@Override
	protected void clearSubs(I sin, String locale, String compId, String loginId, SysPssPurchaseStockOrderEntity entity, String id) {
		busService.clearPssPurchaseStockOrderProduct(id);
	}


	@Override
	protected void beforeSaveMain(I sin, String locale, String compId, String loginId, SysPssPurchaseStockOrderEntity entity) {
		// 采购入库
		if (entity.getOrderType()) {
			String errorMsg = busService.checkProductQty(entity.getId(), entity.getOrderId(), sin.getProductList());
			if (StringUtils.isNotBlank(errorMsg)) {
				throw new LKRuntimeException(ErrorCodes.PSS_PURCHASE_STOCK_IN_MSG).withParam("#prodName", errorMsg);
			}
		}
	}


	@Override
	protected void addSubs(I sin, String locale, String compId, String loginId, SysPssPurchaseStockOrderEntity entity, String id) {
		busService.addPssPurchaseStockOrderProduct(id, sin.getProductList());
	}

}
