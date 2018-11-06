package com.lichkin.application.apis.api50100.US.n00;

import org.springframework.stereotype.Service;

import com.lichkin.framework.db.beans.SysPssPurchaseOrderR;
import com.lichkin.springframework.entities.impl.SysPssPurchaseOrderEntity;
import com.lichkin.springframework.services.LKApiBusUpdateUsingStatusService;

@Service("SysPssPurchaseOrderUS00Service")
public class S extends LKApiBusUpdateUsingStatusService<I, SysPssPurchaseOrderEntity> {

	@Override
	protected int getIdColumnResId() {
		return SysPssPurchaseOrderR.id;
	}

}
