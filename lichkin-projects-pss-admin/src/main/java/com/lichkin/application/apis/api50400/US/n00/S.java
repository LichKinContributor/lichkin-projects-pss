package com.lichkin.application.apis.api50400.US.n00;

import org.springframework.stereotype.Service;

import com.lichkin.framework.db.beans.SysPssAllotOrderR;
import com.lichkin.springframework.entities.impl.SysPssAllotOrderEntity;
import com.lichkin.springframework.services.LKApiBusUpdateUsingStatusService;

@Service("SysPssAllotOrderUS00Service")
public class S extends LKApiBusUpdateUsingStatusService<I, SysPssAllotOrderEntity> {

	@Override
	protected int getIdColumnResId() {
		return SysPssAllotOrderR.id;
	}

}
