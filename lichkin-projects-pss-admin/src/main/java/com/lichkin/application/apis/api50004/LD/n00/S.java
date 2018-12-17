package com.lichkin.application.apis.api50004.LD.n00;

import java.util.List;

import org.springframework.stereotype.Service;

import com.lichkin.framework.db.beans.Order;
import com.lichkin.framework.db.beans.QuerySQL;
import com.lichkin.framework.db.beans.SysPssStoreR;
import com.lichkin.framework.defines.beans.impl.LKDroplistBean;
import com.lichkin.framework.defines.enums.impl.LKUsingStatusEnum;
import com.lichkin.framework.defines.exceptions.LKException;
import com.lichkin.springframework.controllers.ApiKeyValues;
import com.lichkin.springframework.entities.impl.SysPssStoreEntity;
import com.lichkin.springframework.services.LKApiBusGetDroplistService;

@Service("SysPssStoreLD00Service")
public class S extends LKApiBusGetDroplistService<I> {

	@Override
	public List<LKDroplistBean> handle(I sin, ApiKeyValues<I> params) throws LKException {
		QuerySQL sql = new QuerySQL(SysPssStoreEntity.class);

		sql.select(SysPssStoreR.id, "value");
		sql.select(SysPssStoreR.storeName, "text");

		sql.eq(SysPssStoreR.usingStatus, LKUsingStatusEnum.USING);
		sql.eq(SysPssStoreR.compId, params.getCompId());

		sql.addOrders(new Order(SysPssStoreR.storeName));

		return dao.getList(sql, LKDroplistBean.class);
	}

}
