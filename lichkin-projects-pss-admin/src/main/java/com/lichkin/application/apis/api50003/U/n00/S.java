package com.lichkin.application.apis.api50003.U.n00;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lichkin.application.services.bus.impl.SysPssProductBusService;
import com.lichkin.framework.defines.enums.LKCodeEnum;
import com.lichkin.framework.utils.LKPriceUtils;
import com.lichkin.springframework.entities.impl.SysPssProductEntity;
import com.lichkin.springframework.services.LKApiBusUpdateService;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Service("SysPssProductU00Service")
public class S extends LKApiBusUpdateService<I, SysPssProductEntity> {

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
	protected boolean needCheckExist(I sin, String locale, String compId, String loginId, SysPssProductEntity entity, String id) {
		String productCodeSaved = entity.getProductCode();
		String productCodeIn = sin.getProductCode();
		if (((productCodeSaved == null) && (productCodeIn != null)) || ((productCodeSaved != null) && ((productCodeIn == null) || !productCodeSaved.equals(productCodeIn)))) {
			return true;
		}
		if (!entity.getProductName().equals(sin.getProductName())) {
			return true;
		}
		String barcodeSaved = entity.getBarcode();
		String barcodeIn = sin.getBarcode();
		if (((barcodeSaved == null) && (barcodeIn != null)) || ((barcodeSaved != null) && ((barcodeIn == null) || !barcodeSaved.equals(barcodeIn)))) {
			return true;
		}
		return false;
	}


	@Override
	protected List<SysPssProductEntity> findExist(I sin, String locale, String compId, String loginId, SysPssProductEntity entity, String id) {
		return busService.findExist(id, compId, null, sin.getProductCode(), sin.getProductName(), sin.getBarcode());
	}


	@Override
	protected LKCodeEnum existErrorCode(I sin, String locale, String compId, String loginId) {
		return ErrorCodes.SysPssProduct_EXIST;
	}


	@Override
	protected void beforeSaveMain(I sin, String locale, String compId, String loginId, SysPssProductEntity entity) {
		entity.setPurchasePrice(LKPriceUtils.analysisPrice(sin.getPurchasePrice()));
		entity.setReferencePrice(LKPriceUtils.analysisPrice(sin.getReferencePrice()));
		entity.setRetailPrice(LKPriceUtils.analysisPrice(sin.getRetailPrice()));
		entity.setImageUrl1(busService.analysisImageUrl(sin.getImageUrl1()));
		entity.setImageUrl2(busService.analysisImageUrl(sin.getImageUrl2()));
		entity.setImageUrl3(busService.analysisImageUrl(sin.getImageUrl3()));
		entity.setImageUrl4(busService.analysisImageUrl(sin.getImageUrl4()));
	}

}
