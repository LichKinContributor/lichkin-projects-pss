package com.lichkin.application.services.bus.impl;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import com.lichkin.framework.db.beans.Condition;
import com.lichkin.framework.db.beans.QuerySQL;
import com.lichkin.framework.db.beans.SysPssSupplierR;
import com.lichkin.framework.db.beans.eq;
import com.lichkin.springframework.entities.impl.SysPssSupplierEntity;
import com.lichkin.springframework.services.LKDBService;

@Service
public class SysPssSupplierBusService extends LKDBService {

	public List<SysPssSupplierEntity> findExist(String id, String compId, String busCompId, String supplierCode, String supplierName) {
		QuerySQL sql = new QuerySQL(false, SysPssSupplierEntity.class);

		if (StringUtils.isNotBlank(id)) {
			sql.neq(SysPssSupplierR.id, id);
		}

		addConditionCompId(true, sql, SysPssSupplierR.compId, compId, busCompId);

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
