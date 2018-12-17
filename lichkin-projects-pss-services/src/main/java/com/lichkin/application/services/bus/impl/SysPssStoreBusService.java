package com.lichkin.application.services.bus.impl;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import com.lichkin.framework.db.beans.Condition;
import com.lichkin.framework.db.beans.QuerySQL;
import com.lichkin.framework.db.beans.SysPssStoreR;
import com.lichkin.framework.db.beans.eq;
import com.lichkin.springframework.controllers.ApiKeyValues;
import com.lichkin.springframework.entities.impl.SysPssStoreEntity;
import com.lichkin.springframework.services.LKDBService;

@Service
public class SysPssStoreBusService extends LKDBService {

	public List<SysPssStoreEntity> findExist(ApiKeyValues<?> params, String storeCode, String storeName) {
		QuerySQL sql = new QuerySQL(false, SysPssStoreEntity.class);

		addConditionId(sql, SysPssStoreR.id, params.getId());
//		addConditionLocale(sql, SysPssStoreR.locale, params.getLocale());
		addConditionCompId(true, sql, SysPssStoreR.compId, params.getCompId(), params.getBusCompId());
//		addConditionUsingStatus(true, params.getCompId(), sql, SysPssStoreR.usingStatus, params.getUsingStatus(), LKUsingStatusEnum.USING);

		if (StringUtils.isBlank(storeCode)) {
			sql.eq(SysPssStoreR.storeName, storeName);
		} else {
			sql.where(

					new Condition(true,

							new Condition(null, new eq(SysPssStoreR.storeCode, storeCode))

							, new Condition(false, new eq(SysPssStoreR.storeName, storeName))

					)

			);
		}

		return dao.getList(sql, SysPssStoreEntity.class);
	}

}
