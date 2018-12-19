package com.lichkin.application.services.bus.impl;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import com.lichkin.framework.db.beans.Condition;
import com.lichkin.framework.db.beans.QuerySQL;
import com.lichkin.framework.db.beans.SysPssStorageR;
import com.lichkin.framework.db.beans.eq;
import com.lichkin.springframework.controllers.ApiKeyValues;
import com.lichkin.springframework.entities.impl.SysPssStorageEntity;
import com.lichkin.springframework.services.LKDBService;

@Service
public class SysPssStorageBusService extends LKDBService {

	public List<SysPssStorageEntity> findExist(ApiKeyValues<?> params, String storageCode, String storageName) {
		QuerySQL sql = new QuerySQL(false, SysPssStorageEntity.class);

		addConditionId(sql, SysPssStorageR.id, params.getId());
//		addConditionLocale(sql, SysPssStorageR.locale, params.getLocale());
		addConditionCompId(true, sql, SysPssStorageR.compId, params.getCompId(), params.getBusCompId());
//		addConditionUsingStatus(true,params.getCompId(), sql, SysPssStorageR.usingStatus, params.getUsingStatus(), LKUsingStatusEnum.USING);

		if (StringUtils.isBlank(storageCode)) {
			sql.eq(SysPssStorageR.storageName, storageName);
		} else {
			sql.where(

					new Condition(true,

							new Condition(null, new eq(SysPssStorageR.storageCode, storageCode)),

							new Condition(false, new eq(SysPssStorageR.storageName, storageName))

					)

			);
		}

		return dao.getList(sql, SysPssStorageEntity.class);
	}

}
