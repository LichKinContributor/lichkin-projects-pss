package com.lichkin.application.apis.api50300.I.n00;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lichkin.application.services.bus.impl.SysPssOtherStockOrderBusService;
import com.lichkin.application.services.bus.impl.SysPssStockBusService;
import com.lichkin.framework.defines.enums.LKCodeEnum;
import com.lichkin.framework.defines.enums.impl.ApprovalStatusEnum;
import com.lichkin.framework.defines.exceptions.LKRuntimeException;
import com.lichkin.springframework.entities.impl.SysPssOtherStockOrderEntity;
import com.lichkin.springframework.services.LKApiBusInsertWithoutCheckerService;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Service("SysPssOtherStockOrderI00Service")
public class S extends LKApiBusInsertWithoutCheckerService<I, SysPssOtherStockOrderEntity> {

	@Autowired
	private SysPssOtherStockOrderBusService busService;

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
	protected void beforeAddNew(I sin, String locale, String compId, String loginId, SysPssOtherStockOrderEntity entity) {
		entity.setCompId(getCompId(compId, sin.getCompId()));
		entity.setOrderNo(busService.analysisOrderNo());
	}


	@Override
	protected void beforeSaveMain(I sin, String locale, String compId, String loginId, SysPssOtherStockOrderEntity entity) {
		// 出库需做校验处理
		if (entity.getOrderType().equals(Boolean.FALSE)) {
			String errorMsg = stockBusService.checkProductStockOut(entity.getStorageId(), sin.getProductList(), entity.getId());
			if (StringUtils.isNotBlank(errorMsg)) {
				throw new LKRuntimeException(ErrorCodes.PSS_STOCK_OUT_MSG).withParam("#prodName", errorMsg);
			}
		}
		entity.setApprovalStatus(ApprovalStatusEnum.PENDING);
	}


	@Override
	protected void addSubs(I sin, String locale, String compId, String loginId, SysPssOtherStockOrderEntity entity, String id) {
		busService.addPssOtherStockOrderProduct(id, sin.getProductList());
	}

}
