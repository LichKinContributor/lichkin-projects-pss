package com.lichkin.application.apis.api50300.US.n00;

import org.springframework.stereotype.Service;

import com.lichkin.framework.db.beans.SysPssOtherStockOrderR;
import com.lichkin.springframework.entities.impl.SysPssOtherStockOrderEntity;
import com.lichkin.springframework.services.LKApiBusUpdateUsingStatusService;

@Service("SysPssOtherStockOrderUS00Service")
public class S extends LKApiBusUpdateUsingStatusService<I, SysPssOtherStockOrderEntity> {

	@Override
	protected int getIdColumnResId() {
		return SysPssOtherStockOrderR.id;
	}

}
