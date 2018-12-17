package com.lichkin.application.apis.api50003.US.n00;

import org.springframework.stereotype.Service;

import com.lichkin.framework.beans.impl.LKRequestIDsBean;
import com.lichkin.framework.db.beans.SysPssProductR;
import com.lichkin.springframework.entities.impl.SysPssProductEntity;
import com.lichkin.springframework.services.LKApiBusUpdateUsingStatusService;

@Service("SysPssProductUS00Service")
public class S extends LKApiBusUpdateUsingStatusService<LKRequestIDsBean, SysPssProductEntity> {

	@Override
	protected int getIdColumnResId() {
		return SysPssProductR.id;
	}

}
