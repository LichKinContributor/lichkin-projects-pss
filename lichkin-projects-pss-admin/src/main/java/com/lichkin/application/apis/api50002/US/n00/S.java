package com.lichkin.application.apis.api50002.US.n00;

import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.stereotype.Service;

import com.lichkin.framework.beans.impl.LKRequestIDsBean;
import com.lichkin.framework.db.beans.QuerySQL;
import com.lichkin.framework.db.beans.SysPssProductCategoryR;
import com.lichkin.framework.db.beans.SysPssProductR;
import com.lichkin.framework.defines.enums.LKCodeEnum;
import com.lichkin.framework.defines.enums.impl.LKUsingStatusEnum;
import com.lichkin.framework.defines.exceptions.LKRuntimeException;
import com.lichkin.springframework.controllers.ApiKeyValues;
import com.lichkin.springframework.entities.impl.SysPssProductCategoryEntity;
import com.lichkin.springframework.entities.impl.SysPssProductEntity;
import com.lichkin.springframework.services.LKApiBusUpdateUsingStatusService;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Service("SysPssProductCategoryUS00Service")
public class S extends LKApiBusUpdateUsingStatusService<LKRequestIDsBean, SysPssProductCategoryEntity> {

	@Override
	protected int getIdColumnResId() {
		return SysPssProductCategoryR.id;
	}


	@Getter
	@RequiredArgsConstructor
	enum ErrorCodes implements LKCodeEnum {

		SysPssProductCategory_can_not_delete_category_when_has_product(60000),

		;

		private final Integer code;

	}


	@Override
	protected void beforeSaveMain(LKRequestIDsBean cin, ApiKeyValues<LKRequestIDsBean> params, SysPssProductCategoryEntity entity, String id) {
		QuerySQL sql = new QuerySQL(SysPssProductEntity.class);
		sql.eq(SysPssProductR.productCategory, entity.getCategoryCode());
		sql.neq(SysPssProductR.usingStatus, LKUsingStatusEnum.DEPRECATED);
		List<SysPssProductEntity> list = dao.getList(sql, SysPssProductEntity.class);
		// 部门中存在员工，不可删除
		if (CollectionUtils.isNotEmpty(list)) {
			throw new LKRuntimeException(ErrorCodes.SysPssProductCategory_can_not_delete_category_when_has_product);
		}
	}

}
