package com.lichkin.application.apis.api50500.U.n00;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lichkin.application.services.bus.impl.SysPssStockCheckOrderBusService;
import com.lichkin.framework.json.LKJsonUtils;
import com.lichkin.springframework.entities.impl.SysPssStockCheckOrderEntity;
import com.lichkin.springframework.entities.impl.SysPssStockCheckOrderProductEntity;
import com.lichkin.springframework.services.LKApiBusUpdateWithoutCheckerService;

@Service("SysPssStockCheckOrderU00Service")
public class S extends LKApiBusUpdateWithoutCheckerService<I, SysPssStockCheckOrderEntity> {

	@Autowired
	private SysPssStockCheckOrderBusService busService;


	@Override
	protected void clearSubs(I sin, String locale, String compId, String loginId, SysPssStockCheckOrderEntity entity, String id) {
		busService.clearPssStockCheckOrderProduct(id);
	}


	@Override
	protected void beforeSaveMain(I sin, String locale, String compId, String loginId, SysPssStockCheckOrderEntity entity) {
		List<SysPssStockCheckOrderProductEntity> listProduct = LKJsonUtils.toList(sin.getProductList(), SysPssStockCheckOrderProductEntity.class);
		entity.setStockCheckCount(listProduct.size());
	}


	@Override
	protected void addSubs(I sin, String locale, String compId, String loginId, SysPssStockCheckOrderEntity entity, String id) {
		busService.addPssStockCheckOrderProduct(id, sin.getProductList());
	}

}
