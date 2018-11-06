package com.lichkin.application.services.bus.impl;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lichkin.framework.db.beans.QuerySQL;
import com.lichkin.framework.db.beans.SysPssProductCategoryR;
import com.lichkin.framework.defines.enums.LKCodeEnum;
import com.lichkin.springframework.entities.impl.SysPssProductCategoryEntity;
import com.lichkin.springframework.services.LKCodeService;
import com.lichkin.springframework.services.LKDBService;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Service
public class SysPssProductCategoryBusService extends LKDBService {

	@Getter
	@RequiredArgsConstructor
	enum ErrorCodes implements LKCodeEnum {

		SysPssProductCategory_LEVEL_OUT(60000),

		;

		private final Integer code;

	}


	public List<SysPssProductCategoryEntity> findExist(String id, String compId, String busCompId, String parentCode, String categoryName) {
		QuerySQL sql = new QuerySQL(false, SysPssProductCategoryEntity.class);

		if (StringUtils.isNotBlank(id)) {
			sql.neq(SysPssProductCategoryR.id, id);
		}

		addConditionCompId(true, sql, SysPssProductCategoryR.compId, compId, busCompId);

		sql.eq(SysPssProductCategoryR.parentCode, parentCode);
		sql.eq(SysPssProductCategoryR.categoryName, categoryName);

		return dao.getList(sql, SysPssProductCategoryEntity.class);
	}


	@Autowired
	private LKCodeService codeService;


	public String analysisCategoryCode(String compId, String parentCode) {
		return codeService.analysisCode(SysPssProductCategoryEntity.class, compId, parentCode, "categoryCode", SysPssProductCategoryR.compId, SysPssProductCategoryR.parentCode, SysPssProductCategoryR.categoryCode, ErrorCodes.SysPssProductCategory_LEVEL_OUT);
	}

}
