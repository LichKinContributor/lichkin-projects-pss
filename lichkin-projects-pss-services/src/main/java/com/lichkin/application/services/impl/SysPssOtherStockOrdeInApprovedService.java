package com.lichkin.application.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lichkin.application.services.ActivitiApprovedService;
import com.lichkin.springframework.entities.impl.SysActivitiFormDataEntity;
import com.lichkin.springframework.services.LKDBService;

@Service("PSS_OTHER_STOCK_ORDER_IN")
public class SysPssOtherStockOrdeInApprovedService extends LKDBService implements ActivitiApprovedService {

	@Autowired
	private SysPssOtherStockOrderService service;


	@Override
	public void handleBusiness(SysActivitiFormDataEntity formDataEntity) {
		service.handleByActiviti(formDataEntity);
	}

}
