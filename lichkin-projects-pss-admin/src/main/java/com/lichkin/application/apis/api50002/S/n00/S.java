package com.lichkin.application.apis.api50002.S.n00;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import com.lichkin.framework.db.beans.Order;
import com.lichkin.framework.db.beans.QuerySQL;
import com.lichkin.framework.db.beans.SysPssProductCategoryR;
import com.lichkin.framework.db.enums.LikeType;
import com.lichkin.framework.utils.LKCodeUtils;
import com.lichkin.springframework.entities.impl.SysPssProductCategoryEntity;
import com.lichkin.springframework.services.LKApiBusGetListService;

@Service("SysPssProductCategoryS00Service")
public class S extends LKApiBusGetListService<I, SysPssProductCategoryEntity, SysPssProductCategoryEntity> {

	@Override
	protected void initSQL(I sin, String locale, String compId, String loginId, QuerySQL sql) {
		addConditionCompId(true, sql, SysPssProductCategoryR.compId, compId, sin.getCompId());
		addConditionUsingStatus(sql, SysPssProductCategoryR.usingStatus, compId, sin.getUsingStatus());

		if (StringUtils.isNotBlank(sin.getCategoryName())) {
			sql.like(SysPssProductCategoryR.categoryName, LikeType.ALL, sin.getCategoryName());
		}

		sql.addOrders(new Order(SysPssProductCategoryR.categoryCode));
	}


	@Override
	protected List<SysPssProductCategoryEntity> afterQuery(I sin, String locale, String compId, String loginId, List<SysPssProductCategoryEntity> list) {
		if (StringUtils.isNotBlank(sin.getCategoryName())) {
			List<String> codeList = new ArrayList<>();
			for (int i = 0; i < list.size(); i++) {
				codeList.add(list.get(i).getCategoryCode());
			}

			codeList.addAll(LKCodeUtils.parentsCode(codeList, false));

			QuerySQL sql = new QuerySQL(false, SysPssProductCategoryEntity.class, true);

			sql.in(SysPssProductCategoryR.categoryCode, codeList);
			sql.addOrders(new Order(SysPssProductCategoryR.categoryCode));

			return dao.getList(sql, SysPssProductCategoryEntity.class);
		}
		return list;
	}

}
