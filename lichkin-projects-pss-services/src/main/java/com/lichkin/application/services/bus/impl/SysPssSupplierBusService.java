package com.lichkin.application.services.bus.impl;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import com.lichkin.framework.db.beans.Condition;
import com.lichkin.framework.db.beans.QuerySQL;
import com.lichkin.framework.db.beans.SysPssSupplierR;
import com.lichkin.framework.db.beans.eq;
import com.lichkin.springframework.controllers.ApiKeyValues;
import com.lichkin.springframework.entities.impl.SysPssSupplierEntity;
import com.lichkin.springframework.services.LKDBService;

@Service
public class SysPssSupplierBusService extends LKDBService {

	public List<SysPssSupplierEntity> findExist(ApiKeyValues<?> params, String supplierCode, String supplierName) {
		QuerySQL sql = new QuerySQL(false, SysPssSupplierEntity.class);

		addConditionId(sql, SysPssSupplierR.id, params.getId());
//		addConditionLocale(sql, SysPssSupplierR.locale, params.getLocale());
		addConditionCompId(true, sql, SysPssSupplierR.compId, params.getCompId(), params.getBusCompId());
//		addConditionUsingStatus(true, params.getCompId(), sql, SysPssSupplierR.usingStatus, params.getUsingStatus(), LKUsingStatusEnum.USING);

		if (StringUtils.isBlank(supplierCode)) {
			sql.eq(SysPssSupplierR.supplierName, supplierName);
		} else {
			sql.where(

					new Condition(true,

							new Condition(null, new eq(SysPssSupplierR.supplierCode, supplierCode)),

							new Condition(false, new eq(SysPssSupplierR.supplierName, supplierName))

					)

			);
		}

		return dao.getList(sql, SysPssSupplierEntity.class);
	}

}
