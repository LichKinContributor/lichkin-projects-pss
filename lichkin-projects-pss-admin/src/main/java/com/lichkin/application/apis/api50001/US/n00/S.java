package com.lichkin.application.apis.api50001.US.n00;

import org.springframework.stereotype.Service;

import com.lichkin.framework.db.beans.SysPssSupplierR;
import com.lichkin.springframework.entities.impl.SysPssSupplierEntity;
import com.lichkin.springframework.services.LKApiBusUpdateUsingStatusService;

@Service("SysPssSupplierUS00Service")
public class S extends LKApiBusUpdateUsingStatusService<I, SysPssSupplierEntity> {

	@Override
	protected int getIdColumnResId() {
		return SysPssSupplierR.id;
	}

}
