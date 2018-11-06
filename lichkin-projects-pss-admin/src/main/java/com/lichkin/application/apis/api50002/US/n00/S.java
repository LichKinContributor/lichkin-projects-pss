package com.lichkin.application.apis.api50002.US.n00;

import org.springframework.stereotype.Service;

import com.lichkin.framework.db.beans.SysPssProductCategoryR;
import com.lichkin.springframework.entities.impl.SysPssProductCategoryEntity;
import com.lichkin.springframework.services.LKApiBusUpdateUsingStatusService;

@Service("SysPssProductCategoryUS00Service")
public class S extends LKApiBusUpdateUsingStatusService<I, SysPssProductCategoryEntity> {

	@Override
	protected int getIdColumnResId() {
		return SysPssProductCategoryR.id;
	}

}
