package com.lichkin.application.apis.api50500.D.n00;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lichkin.application.services.bus.impl.SysPssStockCheckOrderBusService;
import com.lichkin.framework.db.beans.SysPssStockCheckOrderR;
import com.lichkin.framework.defines.enums.impl.LKErrorCodesEnum;
import com.lichkin.framework.defines.enums.impl.LKUsingStatusEnum;
import com.lichkin.framework.defines.exceptions.LKRuntimeException;
import com.lichkin.springframework.entities.impl.SysPssStockCheckOrderEntity;
import com.lichkin.springframework.services.LKApiBusDeleteService;

@Service("SysPssStockCheckOrderD00Service")
public class S extends LKApiBusDeleteService<I, SysPssStockCheckOrderEntity> {

	@Autowired
	private SysPssStockCheckOrderBusService busService;


	@Override
	protected int getIdColumnResId() {
		return SysPssStockCheckOrderR.id;
	}


	@Override
	protected boolean realDelete(I sin, String locale, String compId, String loginId) {
		return true;
	}


	@Override
	protected void beforeRealDelete(I sin, String locale, String compId, String loginId, SysPssStockCheckOrderEntity entity, String id) {
		LKUsingStatusEnum usingStatus = entity.getUsingStatus();
		switch (usingStatus) {
			case STAND_BY:
				busService.clearPssStockCheckOrderProduct(id);
			break;
			default:
				throw new LKRuntimeException(LKErrorCodesEnum.PARAM_ERROR);
		}
	}

}
