package com.lichkin.application.apis.api50001.P.n00;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import com.lichkin.application.utils.LKDictUtils;
import com.lichkin.application.utils.LKDictUtils4Pss;
import com.lichkin.framework.db.beans.Condition;
import com.lichkin.framework.db.beans.Order;
import com.lichkin.framework.db.beans.QuerySQL;
import com.lichkin.framework.db.beans.SysEmployeeR;
import com.lichkin.framework.db.beans.SysPssSupplierR;
import com.lichkin.framework.db.enums.LikeType;
import com.lichkin.springframework.entities.impl.SysEmployeeEntity;
import com.lichkin.springframework.entities.impl.SysPssSupplierEntity;
import com.lichkin.springframework.services.LKApiBusGetPageService;

@Service("SysPssSupplierP00Service")
public class S extends LKApiBusGetPageService<I, O, SysPssSupplierEntity> {

	@Override
	protected void initSQL(I sin, String locale, String compId, String loginId, QuerySQL sql) {
		// 主表
		sql.select(SysPssSupplierR.id);
		sql.select(SysPssSupplierR.insertTime);
		sql.select(SysPssSupplierR.supplierCode);
		sql.select(SysPssSupplierR.supplierName);
		sql.select(SysPssSupplierR.address);
		sql.select(SysPssSupplierR.linkmanName);
		sql.select(SysPssSupplierR.linkmanContactWay);
		sql.select(SysPssSupplierR.remarks);

		// 关联表
		sql.leftJoin(SysEmployeeEntity.class, new Condition(SysPssSupplierR.responsiblePerson, SysEmployeeR.id));
		sql.select(SysEmployeeR.userName, "responsiblePerson");
		sql.select(SysEmployeeR.cellphone);

		// 字典表
		int i = 0;
		LKDictUtils.usingStatus(sql, SysPssSupplierR.usingStatus, i++);
		LKDictUtils4Pss.supplierType(sql, SysPssSupplierR.supplierType, i++);

		// 筛选条件（必填项）
		// 公司ID
		addConditionCompId(false, sql, SysPssSupplierR.compId, compId, sin.getCompId());
		// 在用状态
		addConditionUsingStatus(sql, SysPssSupplierR.usingStatus, compId, sin.getUsingStatus());

		// 筛选条件（业务项）
		String supplierCode = sin.getSupplierCode();
		if (StringUtils.isNotBlank(supplierCode)) {
			sql.like(SysPssSupplierR.supplierCode, LikeType.ALL, supplierCode);
		}

		String supplierName = sin.getSupplierName();
		if (StringUtils.isNotBlank(supplierName)) {
			sql.like(SysPssSupplierR.supplierName, LikeType.ALL, supplierName);
		}

		String supplierType = sin.getSupplierType();
		if (StringUtils.isNotBlank(supplierType)) {
			sql.eq(SysPssSupplierR.supplierType, supplierType);
		}

		// 排序条件
		sql.addOrders(new Order(SysPssSupplierR.id, false));
	}

}
