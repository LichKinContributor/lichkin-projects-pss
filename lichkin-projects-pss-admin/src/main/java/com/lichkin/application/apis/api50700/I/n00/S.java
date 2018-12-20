package com.lichkin.application.apis.api50700.I.n00;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lichkin.application.services.bus.impl.SysPssPurchaseReturnStockInOrderBusService;
import com.lichkin.application.services.bus.impl.SysPssStockBusService;
import com.lichkin.framework.defines.enums.LKCodeEnum;
import com.lichkin.framework.defines.enums.impl.ApprovalStatusEnum;
import com.lichkin.framework.defines.exceptions.LKRuntimeException;
import com.lichkin.springframework.controllers.ApiKeyValues;
import com.lichkin.springframework.entities.impl.SysPssPurchaseReturnStockInOrderEntity;
import com.lichkin.springframework.services.LKApiBusInsertWithoutCheckerService;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Service("SysPssPurchaseReturnStockInOrderI00Service")
public class S extends LKApiBusInsertWithoutCheckerService<I, SysPssPurchaseReturnStockInOrderEntity> {

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
	protected void beforeAddNew(I sin, ApiKeyValues<I> params, SysPssPurchaseReturnStockInOrderEntity entity) {
		entity.setOrderNo(busService.analysisOrderNo());
	}


	@Override
	protected void beforeSaveMain(I sin, ApiKeyValues<I> params, SysPssPurchaseReturnStockInOrderEntity entity) {
		entity.setOrderType(false);// 采购退货出库
		entity.setOrderAmount(busService.analysisOrderAmount(sin));
		String errorMsg = pssStockBusService.checkProductStockOut(entity.getStorageId(), sin.getProductList(), entity.getId());
		if (StringUtils.isNotBlank(errorMsg)) {
			throw new LKRuntimeException(ErrorCodes.PSS_STOCK_OUT_MSG).withParam("#prodName", errorMsg);
		}
		entity.setApprovalStatus(ApprovalStatusEnum.PENDING);
	}


	@Override
	protected void addSubs(I sin, ApiKeyValues<I> params, SysPssPurchaseReturnStockInOrderEntity entity, String id) {
		busService.addPurchaseReturnOrderProduct(id, sin.getListProduct());
	}

}
