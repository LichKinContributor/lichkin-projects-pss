package com.lichkin.application.apis.api50500.I.n00;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lichkin.application.services.bus.impl.SysPssStockCheckOrderBusService;
import com.lichkin.framework.defines.enums.LKCodeEnum;
import com.lichkin.framework.defines.enums.impl.ApprovalStatusEnum;
import com.lichkin.framework.defines.enums.impl.LKDateTimeTypeEnum;
import com.lichkin.framework.defines.enums.impl.LKUsingStatusEnum;
import com.lichkin.framework.defines.exceptions.LKRuntimeException;
import com.lichkin.framework.json.LKJsonUtils;
import com.lichkin.framework.utils.LKDateTimeUtils;
import com.lichkin.springframework.entities.impl.SysPssStockCheckOrderEntity;
import com.lichkin.springframework.entities.impl.SysPssStockCheckOrderProductEntity;
import com.lichkin.springframework.services.LKApiBusInsertWithoutCheckerService;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Service("SysPssStockCheckOrderI00Service")
public class S extends LKApiBusInsertWithoutCheckerService<I, SysPssStockCheckOrderEntity> {

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
	protected void beforeAddNew(I sin, String locale, String compId, String loginId, SysPssStockCheckOrderEntity entity) {
		entity.setCompId(getCompId(compId, sin.getCompId()));
		entity.setOrderNo(busService.analysisOrderNo());
	}


	@Override
	protected void beforeSaveMain(I sin, String locale, String compId, String loginId, SysPssStockCheckOrderEntity entity) {
		entity.setBillDate(LKDateTimeUtils.now(LKDateTimeTypeEnum.DATE_ONLY));
		List<SysPssStockCheckOrderProductEntity> listProduct = LKJsonUtils.toList(sin.getProductList(), SysPssStockCheckOrderProductEntity.class);
		// 校验处理
		String errorMsg = busService.checkProdExist(entity, listProduct);
		if (StringUtils.isNotBlank(errorMsg)) {
			throw new LKRuntimeException(ErrorCodes.PSS_STOCK_CHECK_MSG).withParam("#prodName", errorMsg);
		}
		entity.setStockCheckCount(listProduct.size());
		entity.setUsingStatus(LKUsingStatusEnum.STAND_BY);
		entity.setApprovalStatus(ApprovalStatusEnum.PENDING);
	}


	@Override
	protected void addSubs(I sin, String locale, String compId, String loginId, SysPssStockCheckOrderEntity entity, String id) {
		busService.addPssStockCheckOrderProduct(id, sin.getProductList());
	}

}
