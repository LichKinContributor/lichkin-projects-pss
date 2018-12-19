package com.lichkin.application.apis.api50002.S.n00;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import com.lichkin.framework.db.beans.Order;
import com.lichkin.framework.db.beans.QuerySQL;
import com.lichkin.framework.db.beans.SysPssProductCategoryR;
import com.lichkin.framework.db.enums.LikeType;
import com.lichkin.framework.defines.enums.impl.LKUsingStatusEnum;
import com.lichkin.framework.utils.LKCodeUtils;
import com.lichkin.springframework.controllers.ApiKeyValues;
import com.lichkin.springframework.entities.impl.SysPssProductCategoryEntity;
import com.lichkin.springframework.services.LKApiBusGetListService;

@Service("SysPssProductCategoryS00Service")
public class S extends LKApiBusGetListService<I, SysPssProductCategoryEntity, SysPssProductCategoryEntity> {

	@Override
	protected void initSQL(I sin, ApiKeyValues<I> params, QuerySQL sql) {
		// 主表

		// 关联表

		// 字典表
//		int i = 0;

		// 筛选条件（必填项）
//		addConditionId(sql, SysPssProductCategoryR.id, params.getId());
//		addConditionLocale(sql, SysPssProductCategoryR.locale, params.getLocale());
		addConditionCompId(true, sql, SysPssProductCategoryR.compId, params.getCompId(), params.getBusCompId());
		addConditionUsingStatus(true,params.getCompId(), sql, SysPssProductCategoryR.usingStatus, params.getUsingStatus(), LKUsingStatusEnum.USING);

		// 筛选条件（业务项）
		if (StringUtils.isNotBlank(sin.getCategoryName())) {
			sql.like(SysPssProductCategoryR.categoryName, LikeType.ALL, sin.getCategoryName());
		}

		// 排序条件
		sql.addOrders(new Order(SysPssProductCategoryR.categoryCode));
	}


	@Override
	protected List<SysPssProductCategoryEntity> afterQuery(I sin, ApiKeyValues<I> params, List<SysPssProductCategoryEntity> list) {
		if (StringUtils.isNotBlank(sin.getCategoryName())) {
			List<String> codeList = new ArrayList<>();
			for (int i = 0; i < list.size(); i++) {
				codeList.add(list.get(i).getCategoryCode());
			}

			codeList.addAll(LKCodeUtils.parentsCode(codeList, false));

			QuerySQL sql = new QuerySQL(false, SysPssProductCategoryEntity.class, true);
			// 主表

			// 关联表

			// 字典表
//			int i = 0;

			// 筛选条件（必填项）
//			addConditionId(sql, SysPssProductCategoryR.id, params.getId());
//			addConditionLocale(sql, SysPssProductCategoryR.locale, params.getLocale());
			addConditionCompId(true, sql, SysPssProductCategoryR.compId, params.getCompId(), params.getBusCompId());
			addConditionUsingStatus(true,params.getCompId(), sql, SysPssProductCategoryR.usingStatus, params.getUsingStatus(), LKUsingStatusEnum.USING);

			// 筛选条件（业务项）
			sql.in(SysPssProductCategoryR.categoryCode, codeList);

			// 排序条件
			sql.addOrders(new Order(SysPssProductCategoryR.categoryCode));

			return dao.getList(sql, SysPssProductCategoryEntity.class);
		}
		return list;
	}

}
