package com.lichkin.application.apis.api50003.I.n00;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lichkin.application.services.bus.impl.SysPssProductBusService;
import com.lichkin.framework.defines.enums.LKCodeEnum;
import com.lichkin.framework.utils.LKPriceUtils;
import com.lichkin.springframework.controllers.ApiKeyValues;
import com.lichkin.springframework.entities.impl.SysPssProductEntity;
import com.lichkin.springframework.services.LKApiBusInsertService;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Service("SysPssProductI00Service")
public class S extends LKApiBusInsertService<I, SysPssProductEntity> {

	@Autowired
	private SysPssProductBusService busService;


	@Getter
	@RequiredArgsConstructor
	enum ErrorCodes implements LKCodeEnum {

		SysPssProduct_EXIST(60000),

		;

		private final Integer code;

	}


	@Override
	protected List<SysPssProductEntity> findExist(I sin, ApiKeyValues<I> params) {
		return busService.findExist(params, sin.getProductCode(), sin.getProductName(), sin.getBarcode());
	}


	@Override
	protected boolean forceCheck(I sin, ApiKeyValues<I> params) {
		return false;
	}


	@Override
	protected LKCodeEnum existErrorCode(I sin, ApiKeyValues<I> params) {
		return ErrorCodes.SysPssProduct_EXIST;
	}


	@Override
	protected void beforeRestore(I sin, ApiKeyValues<I> params, SysPssProductEntity entity, SysPssProductEntity exist) {
		entity.setProductCategory(exist.getProductCategory());
	}


	@Override
	protected void beforeSaveMain(I sin, ApiKeyValues<I> params, SysPssProductEntity entity) {
		entity.setPurchasePrice(LKPriceUtils.analysisPrice(sin.getPurchasePrice()));
		entity.setReferencePrice(LKPriceUtils.analysisPrice(sin.getReferencePrice()));
		entity.setRetailPrice(LKPriceUtils.analysisPrice(sin.getRetailPrice()));
		entity.setImageUrl1(busService.analysisImageUrl(sin.getImageUrl1()));
		entity.setImageUrl2(busService.analysisImageUrl(sin.getImageUrl2()));
		entity.setImageUrl3(busService.analysisImageUrl(sin.getImageUrl3()));
		entity.setImageUrl4(busService.analysisImageUrl(sin.getImageUrl4()));
	}

}
