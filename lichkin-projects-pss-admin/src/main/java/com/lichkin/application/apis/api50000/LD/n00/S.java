package com.lichkin.application.apis.api50000.LD.n00;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import com.lichkin.framework.db.beans.Order;
import com.lichkin.framework.db.beans.QuerySQL;
import com.lichkin.framework.db.beans.SysPssStorageR;
import com.lichkin.framework.defines.beans.impl.LKDroplistBean;
import com.lichkin.framework.defines.enums.impl.LKUsingStatusEnum;
import com.lichkin.framework.defines.exceptions.LKException;
import com.lichkin.springframework.controllers.ApiKeyValues;
import com.lichkin.springframework.entities.impl.SysPssStorageEntity;
import com.lichkin.springframework.services.LKApiBusGetDroplistService;

@Service("SysPssStorageLD00Service")
public class S extends LKApiBusGetDroplistService<I> {

	@Override
	public List<LKDroplistBean> handle(I sin, ApiKeyValues<I> params) throws LKException {
		QuerySQL sql = new QuerySQL(SysPssStorageEntity.class);

		sql.select(SysPssStorageR.id, "value");
		sql.select(SysPssStorageR.storageName, "text");

		sql.eq(SysPssStorageR.usingStatus, LKUsingStatusEnum.USING);
		sql.eq(SysPssStorageR.compId, params.getCompId(false));

		String excludeIds = sin.getExcludeIds();
		if (StringUtils.isNotBlank(excludeIds)) {
			sql.notIn(SysPssStorageR.id, excludeIds);
		}

		sql.addOrders(new Order(SysPssStorageR.storageName));

		return dao.getList(sql, LKDroplistBean.class);
	}

}
