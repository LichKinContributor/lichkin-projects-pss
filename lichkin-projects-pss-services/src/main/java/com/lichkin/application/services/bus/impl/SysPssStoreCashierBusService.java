package com.lichkin.application.services.bus.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.lichkin.framework.db.beans.QuerySQL;
import com.lichkin.framework.db.beans.SysPssStoreCashierR;
import com.lichkin.springframework.controllers.ApiKeyValues;
import com.lichkin.springframework.entities.impl.SysPssStoreCashierEntity;
import com.lichkin.springframework.services.LKDBService;

@Service
public class SysPssStoreCashierBusService extends LKDBService {

	public List<SysPssStoreCashierEntity> findExist(ApiKeyValues<?> params, String cashier) {
		QuerySQL sql = new QuerySQL(false, SysPssStoreCashierEntity.class);

		addConditionId(sql, SysPssStoreCashierR.id, params.getId());
//		addConditionLocale(sql, SysPssStoreCashierR.locale, params.getLocale());
		addConditionCompId(true, sql, SysPssStoreCashierR.compId, params.getCompId(), params.getBusCompId());
//		addConditionUsingStatus(true, params.getCompId(), sql, SysPssStoreCashierR.usingStatus, params.getUsingStatus(), LKUsingStatusEnum.USING);

		sql.eq(SysPssStoreCashierR.cashier, cashier);

		return dao.getList(sql, SysPssStoreCashierEntity.class);
	}

}
