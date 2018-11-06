package com.lichkin.application.services.bus.impl;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import com.lichkin.framework.db.beans.QuerySQL;
import com.lichkin.framework.db.beans.SysPssStoreCashierR;
import com.lichkin.springframework.entities.impl.SysPssStoreCashierEntity;
import com.lichkin.springframework.services.LKDBService;

@Service
public class SysPssStoreCashierBusService extends LKDBService {

	public List<SysPssStoreCashierEntity> findExist(String id, String compId, String busCompId, String cashier) {
		QuerySQL sql = new QuerySQL(false, SysPssStoreCashierEntity.class);

		if (StringUtils.isNotBlank(id)) {
			sql.neq(SysPssStoreCashierR.id, id);
		}

		addConditionCompId(true, sql, SysPssStoreCashierR.compId, compId, busCompId);

		sql.eq(SysPssStoreCashierR.cashier, cashier);

		return dao.getList(sql, SysPssStoreCashierEntity.class);
	}

}
