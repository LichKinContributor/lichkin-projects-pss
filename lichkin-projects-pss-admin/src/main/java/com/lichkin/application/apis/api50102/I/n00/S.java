package com.lichkin.application.apis.api50102.I.n00;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lichkin.application.services.bus.impl.SysPssPurchaseStockOrderBusService;
import com.lichkin.framework.defines.enums.LKCodeEnum;
import com.lichkin.framework.defines.enums.impl.ApprovalStatusEnum;
import com.lichkin.framework.defines.exceptions.LKRuntimeException;
import com.lichkin.springframework.controllers.ApiKeyValues;
import com.lichkin.springframework.entities.impl.SysPssPurchaseStockOrderEntity;
import com.lichkin.springframework.services.LKApiBusInsertWithoutCheckerService;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Service("SysPssPurchaseStockOrderI00Service")
public class S extends LKApiBusInsertWithoutCheckerService<I, SysPssPurchaseStockOrderEntity> {

	@Autowired
	private SysPssPurchaseStockOrderBusService busService;


	@Getter
	@RequiredArgsConstructor
	enum ErrorCodes implements LKCodeEnum {
		PSS_STOCK_IN_MSG(60000),

		;

		private final Integer code;

	}


	@Override
	protected void beforeAddNew(I sin, ApiKeyValues<I> params, SysPssPurchaseStockOrderEntity entity) {
		entity.setCompId(params.getCompId(true));
		entity.setOrderNo(busService.analysisOrderNo());
	}


	@Override
	protected void beforeSaveMain(I sin, ApiKeyValues<I> params, SysPssPurchaseStockOrderEntity entity) {
		// 采购入库
		if (sin.getOrderType()) {
			String errorMsg = busService.checkProductQty(entity.getId(), sin.getOrderId(), sin.getProductList());
			if (StringUtils.isNotBlank(errorMsg)) {
				throw new LKRuntimeException(ErrorCodes.PSS_STOCK_IN_MSG).withParam("#prodName", errorMsg);
			}
		}
		entity.setApprovalStatus(ApprovalStatusEnum.PENDING);
	}


	@Override
	protected void addSubs(I sin, ApiKeyValues<I> params, SysPssPurchaseStockOrderEntity entity, String id) {
		busService.addPssPurchaseStockOrderProduct(id, sin.getProductList());
	}

}
