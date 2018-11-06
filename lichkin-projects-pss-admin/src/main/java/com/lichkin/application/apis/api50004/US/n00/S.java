package com.lichkin.application.apis.api50004.US.n00;

import org.springframework.stereotype.Service;

import com.lichkin.framework.db.beans.SysPssStoreR;
import com.lichkin.springframework.entities.impl.SysPssStoreEntity;
import com.lichkin.springframework.services.LKApiBusUpdateUsingStatusService;

@Service("SysPssStoreUS00Service")
public class S extends LKApiBusUpdateUsingStatusService<I, SysPssStoreEntity> {

	@Override
	protected int getIdColumnResId() {
		return SysPssStoreR.id;
	}

}
