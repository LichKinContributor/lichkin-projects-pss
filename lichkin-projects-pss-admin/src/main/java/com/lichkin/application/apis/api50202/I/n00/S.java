package com.lichkin.application.apis.api50202.I.n00;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lichkin.application.services.bus.impl.SysPssSellStockOrderBusService;
import com.lichkin.framework.defines.enums.LKCodeEnum;
import com.lichkin.framework.defines.enums.impl.ApprovalStatusEnum;
import com.lichkin.framework.defines.exceptions.LKRuntimeException;
import com.lichkin.springframework.controllers.ApiKeyValues;
import com.lichkin.springframework.entities.impl.SysPssSellStockOrderEntity;
import com.lichkin.springframework.services.LKApiBusInsertWithoutCheckerService;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Service("SysPssSellStockOrderI00Service")
public class S extends LKApiBusInsertWithoutCheckerService<I, SysPssSellStockOrderEntity> {

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
	protected void beforeAddNew(I sin, ApiKeyValues<I> params, SysPssSellStockOrderEntity entity) {
		entity.setCompId(params.getCompId(true));
		entity.setOrderNo(busService.analysisOrderNo());
	}


	@Override
	protected void beforeSaveMain(I sin, ApiKeyValues<I> params, SysPssSellStockOrderEntity entity) {
		// 销售出库 （进行出库数量校验）
		if (sin.getOrderType().equals(Boolean.FALSE)) {
			String errorMsg = busService.checkProductQty(entity.getId(), entity.getStorageId(), entity.getOrderId(), sin.getProductList());
			if (StringUtils.isNotBlank(errorMsg)) {
				throw new LKRuntimeException(ErrorCodes.PSS_STOCK_OUT_MSG).withParam("#prodName", errorMsg);
			}
		}
		entity.setApprovalStatus(ApprovalStatusEnum.PENDING);
	}


	@Override
	protected void addSubs(I sin, ApiKeyValues<I> params, SysPssSellStockOrderEntity entity, String id) {
		busService.addPssSellStockOrderProduct(id, sin.getProductList());
	}

}
