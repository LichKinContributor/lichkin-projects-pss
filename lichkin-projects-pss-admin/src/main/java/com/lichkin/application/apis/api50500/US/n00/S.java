package com.lichkin.application.apis.api50500.US.n00;

import org.joda.time.DateTime;
import org.springframework.stereotype.Service;

import com.lichkin.framework.beans.impl.LKRequestIDsBean;
import com.lichkin.framework.db.beans.SysPssStockCheckOrderR;
import com.lichkin.framework.defines.enums.LKCodeEnum;
import com.lichkin.framework.defines.enums.impl.LKDateTimeTypeEnum;
import com.lichkin.framework.defines.exceptions.LKRuntimeException;
import com.lichkin.framework.utils.LKDateTimeUtils;
import com.lichkin.springframework.controllers.ApiKeyValues;
import com.lichkin.springframework.entities.impl.SysPssStockCheckOrderEntity;
import com.lichkin.springframework.services.LKApiBusUpdateUsingStatusService;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Service("SysPssStockCheckOrderUS00Service")
public class S extends LKApiBusUpdateUsingStatusService<LKRequestIDsBean, SysPssStockCheckOrderEntity> {

	@Getter
	@RequiredArgsConstructor
	enum ErrorCodes implements LKCodeEnum {
		PSS_ONLY_THE_CHECK_ORDER_OF_THE_DAY_CAN_BE_COMPLETED(60000),

		;

		private final Integer code;

	}


	@Override
	protected int getIdColumnResId() {
		return SysPssStockCheckOrderR.id;
	}


	@Override
	protected void beforeSaveMain(LKRequestIDsBean sin, ApiKeyValues<LKRequestIDsBean> params, SysPssStockCheckOrderEntity entity, String id) {
		if (LKDateTimeUtils.toDateTime(entity.getInsertTime()).isBefore(DateTime.now().minusHours(12))) {
			throw new LKRuntimeException(ErrorCodes.PSS_ONLY_THE_CHECK_ORDER_OF_THE_DAY_CAN_BE_COMPLETED);
		}
		entity.setBillDate(LKDateTimeUtils.now(LKDateTimeTypeEnum.DATE_ONLY));
	}
}
