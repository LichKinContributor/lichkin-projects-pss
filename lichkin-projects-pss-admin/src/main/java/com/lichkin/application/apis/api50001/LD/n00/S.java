package com.lichkin.application.apis.api50001.LD.n00;

import java.util.List;

import org.springframework.stereotype.Service;

import com.lichkin.framework.db.beans.Order;
import com.lichkin.framework.db.beans.QuerySQL;
import com.lichkin.framework.db.beans.SysPssSupplierR;
import com.lichkin.framework.defines.beans.impl.LKDroplistBean;
import com.lichkin.framework.defines.enums.impl.LKUsingStatusEnum;
import com.lichkin.framework.defines.exceptions.LKException;
import com.lichkin.springframework.controllers.ApiKeyValues;
import com.lichkin.springframework.entities.impl.SysPssSupplierEntity;
import com.lichkin.springframework.services.LKApiBusGetDroplistService;

@Service("SysPssSupplierLD00Service")
public class S extends LKApiBusGetDroplistService<I> {

	@Override
	public List<LKDroplistBean> handle(I sin, ApiKeyValues<I> params) throws LKException {
		QuerySQL sql = new QuerySQL(SysPssSupplierEntity.class);

		sql.select(SysPssSupplierR.id, "value");
		sql.select(SysPssSupplierR.supplierName, "text");

		sql.eq(SysPssSupplierR.usingStatus, LKUsingStatusEnum.USING);
		sql.eq(SysPssSupplierR.compId, params.getCompId(false));

		sql.addOrders(new Order(SysPssSupplierR.supplierName));

		return dao.getList(sql, LKDroplistBean.class);
	}

}
