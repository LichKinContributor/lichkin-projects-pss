package com.lichkin.application.apis.api50500.US.n00;

import org.springframework.stereotype.Service;

import com.lichkin.framework.db.beans.SysPssStockCheckOrderR;
import com.lichkin.springframework.entities.impl.SysPssStockCheckOrderEntity;
import com.lichkin.springframework.services.LKApiBusUpdateUsingStatusService;

@Service("SysPssStockCheckOrderUS00Service")
public class S extends LKApiBusUpdateUsingStatusService<I, SysPssStockCheckOrderEntity> {

	@Override
	protected int getIdColumnResId() {
		return SysPssStockCheckOrderR.id;
	}

}
