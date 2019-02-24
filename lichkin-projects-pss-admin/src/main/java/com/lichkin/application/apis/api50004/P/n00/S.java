package com.lichkin.application.apis.api50004.P.n00;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import com.lichkin.application.utils.LKDictUtils;
import com.lichkin.framework.db.beans.Condition;
import com.lichkin.framework.db.beans.Order;
import com.lichkin.framework.db.beans.QuerySQL;
import com.lichkin.framework.db.beans.SysEmployeeR;
import com.lichkin.framework.db.beans.SysPssStorageR;
import com.lichkin.framework.db.beans.SysPssStoreR;
import com.lichkin.framework.db.enums.LikeType;
import com.lichkin.framework.defines.enums.impl.LKUsingStatusEnum;
import com.lichkin.springframework.controllers.ApiKeyValues;
import com.lichkin.springframework.entities.impl.SysEmployeeEntity;
import com.lichkin.springframework.entities.impl.SysPssStorageEntity;
import com.lichkin.springframework.entities.impl.SysPssStoreEntity;
import com.lichkin.springframework.services.LKApiBusGetPageService;

@Service("SysPssStoreP00Service")
public class S extends LKApiBusGetPageService<I, O, SysPssStoreEntity> {

	@Override
	protected void initSQL(I sin, ApiKeyValues<I> params, QuerySQL sql) {
		// 主表
		sql.select(SysPssStoreR.id);
		sql.select(SysPssStoreR.insertTime);
		sql.select(SysPssStoreR.storeCode);
		sql.select(SysPssStoreR.storeName);
		sql.select(SysPssStoreR.address);
		sql.select(SysPssStoreR.remarks);

		// 关联表
		sql.leftJoin(SysEmployeeEntity.class, new Condition(SysPssStoreR.responsiblePerson, SysEmployeeR.id));
		sql.select(SysEmployeeR.userName, "responsiblePerson");
		sql.select(SysEmployeeR.cellphone);
		sql.innerJoin(SysPssStorageEntity.class, new Condition(SysPssStoreR.storageId, SysPssStorageR.id));
		sql.select(SysPssStorageR.storageName);

		// 字典表
		int i = 0;
		LKDictUtils.usingStatus(sql, SysPssStoreR.usingStatus, i++);

		// 筛选条件（必填项）
		// addConditionId(sql, SysPssStoreR.id, params.getId());
		// addConditionLocale(sql, SysPssStoreR.locale, params.getLocale());
		addConditionCompId(true, sql, SysPssStoreR.compId, params.getCompId(), params.getBusCompId());
		addConditionUsingStatus(true, params.getCompId(), sql, SysPssStoreR.usingStatus, params.getUsingStatus(), LKUsingStatusEnum.USING);

		// 筛选条件（业务项）
		String storeCode = sin.getStoreCode();
		if (StringUtils.isNotBlank(storeCode)) {
			sql.like(SysPssStoreR.storeCode, LikeType.ALL, storeCode);
		}

		String storeName = sin.getStoreName();
		if (StringUtils.isNotBlank(storeName)) {
			sql.like(SysPssStoreR.storeName, LikeType.ALL, storeName);
		}

		// 排序条件
		sql.addOrders(new Order(SysPssStoreR.id, false));
	}

}
