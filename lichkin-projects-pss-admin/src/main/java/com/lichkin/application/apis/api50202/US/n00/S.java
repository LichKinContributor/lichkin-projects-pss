package com.lichkin.application.apis.api50202.US.n00;

import org.springframework.stereotype.Service;

import com.lichkin.framework.db.beans.SysPssSellStockOrderR;
import com.lichkin.springframework.entities.impl.SysPssSellStockOrderEntity;
import com.lichkin.springframework.services.LKApiBusUpdateUsingStatusService;

@Service("SysPssSellStockOrderUS00Service")
public class S extends LKApiBusUpdateUsingStatusService<I, SysPssSellStockOrderEntity> {

	@Override
	protected int getIdColumnResId() {
		return SysPssSellStockOrderR.id;
	}

}
