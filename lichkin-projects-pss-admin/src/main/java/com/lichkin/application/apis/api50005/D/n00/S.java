package com.lichkin.application.apis.api50005.D.n00;

import org.springframework.stereotype.Service;

import com.lichkin.framework.db.beans.SysPssStoreCashierR;
import com.lichkin.springframework.entities.impl.SysPssStoreCashierEntity;
import com.lichkin.springframework.services.LKApiBusDeleteService;

@Service("SysPssStoreCashierD00Service")
public class S extends LKApiBusDeleteService<I, SysPssStoreCashierEntity> {

	@Override
	protected int getIdColumnResId() {
		return SysPssStoreCashierR.id;
	}

}
