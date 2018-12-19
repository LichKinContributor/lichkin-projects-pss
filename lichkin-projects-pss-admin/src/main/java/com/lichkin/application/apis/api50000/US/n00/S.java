package com.lichkin.application.apis.api50000.US.n00;

import org.springframework.stereotype.Service;

import com.lichkin.framework.beans.impl.LKRequestIDsBean;
import com.lichkin.framework.db.beans.SysPssStorageR;
import com.lichkin.springframework.entities.impl.SysPssStorageEntity;
import com.lichkin.springframework.services.LKApiBusUpdateUsingStatusService;

@Service("SysPssStorageUS00Service")
public class S extends LKApiBusUpdateUsingStatusService<LKRequestIDsBean, SysPssStorageEntity> {

	@Override
	protected int getIdColumnResId() {
		return SysPssStorageR.id;
	}

}
