package com.lichkin.application.apis.api50000.P.n00;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import com.lichkin.application.utils.LKDictUtils;
import com.lichkin.framework.db.beans.Condition;
import com.lichkin.framework.db.beans.Order;
import com.lichkin.framework.db.beans.QuerySQL;
import com.lichkin.framework.db.beans.SysEmployeeR;
import com.lichkin.framework.db.beans.SysPssStorageR;
import com.lichkin.framework.db.enums.LikeType;
import com.lichkin.framework.defines.enums.impl.LKUsingStatusEnum;
import com.lichkin.springframework.controllers.ApiKeyValues;
import com.lichkin.springframework.entities.impl.SysEmployeeEntity;
import com.lichkin.springframework.entities.impl.SysPssStorageEntity;
import com.lichkin.springframework.services.LKApiBusGetPageService;

@Service("SysPssStorageP00Service")
public class S extends LKApiBusGetPageService<I, O, SysPssStorageEntity> {

	@Override
	protected void initSQL(I sin, ApiKeyValues<I> params, QuerySQL sql) {
		// 主表
		sql.select(SysPssStorageR.id);
		sql.select(SysPssStorageR.insertTime);
		sql.select(SysPssStorageR.storageCode);
		sql.select(SysPssStorageR.storageName);
		sql.select(SysPssStorageR.address);
		sql.select(SysPssStorageR.remarks);

		// 关联表
		sql.leftJoin(SysEmployeeEntity.class, new Condition(SysPssStorageR.responsiblePerson, SysEmployeeR.id));
		sql.select(SysEmployeeR.userName, "responsiblePerson");
		sql.select(SysEmployeeR.cellphone);

		// 字典表
		int i = 0;
		LKDictUtils.usingStatus(sql, SysPssStorageR.usingStatus, i++);

		// 筛选条件（必填项）
//		addConditionId(sql, SysPssStorageR.id, params.getId());
//		addConditionLocale(sql, SysPssStorageR.locale, params.getLocale());
		addConditionCompId(true, sql, SysPssStorageR.compId, params.getCompId(), params.getBusCompId());
		addConditionUsingStatus(true,params.getCompId(), sql, SysPssStorageR.usingStatus, params.getUsingStatus(), LKUsingStatusEnum.USING);

		// 筛选条件（业务项）
		String storageCode = sin.getStorageCode();
		if (StringUtils.isNotBlank(storageCode)) {
			sql.like(SysPssStorageR.storageCode, LikeType.ALL, storageCode);
		}

		String storageName = sin.getStorageName();
		if (StringUtils.isNotBlank(storageName)) {
			sql.like(SysPssStorageR.storageName, LikeType.ALL, storageName);
		}

		// 排序条件
		sql.addOrders(new Order(SysPssStorageR.id, false));
	}

}
