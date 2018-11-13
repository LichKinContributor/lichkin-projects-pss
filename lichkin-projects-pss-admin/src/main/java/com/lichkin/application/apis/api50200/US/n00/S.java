package com.lichkin.application.apis.api50200.US.n00;

import org.springframework.stereotype.Service;

import com.lichkin.framework.db.beans.SysPssSellOrderR;
import com.lichkin.springframework.entities.impl.SysPssSellOrderEntity;
import com.lichkin.springframework.services.LKApiBusUpdateUsingStatusService;

@Service("SysPssSellOrderUS00Service")
public class S extends LKApiBusUpdateUsingStatusService<I, SysPssSellOrderEntity> {

	@Override
	protected int getIdColumnResId() {
		return SysPssSellOrderR.id;
	}

}
