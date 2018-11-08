package com.lichkin.application.apis.api50102.US.n00;

import org.springframework.stereotype.Service;

import com.lichkin.framework.db.beans.SysPssPurchaseStockOrderR;
import com.lichkin.springframework.entities.impl.SysPssPurchaseStockOrderEntity;
import com.lichkin.springframework.services.LKApiBusUpdateUsingStatusService;

@Service("SysPssPurchaseStockOrderUS00Service")
public class S extends LKApiBusUpdateUsingStatusService<I, SysPssPurchaseStockOrderEntity> {

	@Override
	protected int getIdColumnResId() {
		return SysPssPurchaseStockOrderR.id;
	}

}
