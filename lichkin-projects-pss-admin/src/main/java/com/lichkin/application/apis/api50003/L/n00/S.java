package com.lichkin.application.apis.api50003.L.n00;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import com.lichkin.application.utils.LKDictUtils;
import com.lichkin.application.utils.LKDictUtils4Pss;
import com.lichkin.framework.db.beans.Condition;
import com.lichkin.framework.db.beans.Order;
import com.lichkin.framework.db.beans.QuerySQL;
import com.lichkin.framework.db.beans.SysPssProductCategoryR;
import com.lichkin.framework.db.beans.SysPssProductR;
import com.lichkin.framework.db.enums.LikeType;
import com.lichkin.springframework.controllers.ApiKeyValues;
import com.lichkin.springframework.entities.impl.SysPssProductCategoryEntity;
import com.lichkin.springframework.entities.impl.SysPssProductEntity;
import com.lichkin.springframework.services.LKApiBusGetListService;

@Service("SysPssProductL00Service")
public class S extends LKApiBusGetListService<I, O, SysPssProductEntity> {

	@Override
	protected void initSQL(I sin, ApiKeyValues<I> params, QuerySQL sql) {
		// 主表
		sql.select(SysPssProductR.id);
		sql.select(SysPssProductR.insertTime);
		sql.select(SysPssProductR.productCode);
		sql.select(SysPssProductR.productName);
		sql.select(SysPssProductR.barcode);
		sql.select(SysPssProductR.purchasePrice);
		sql.select(SysPssProductR.referencePrice);
		sql.select(SysPssProductR.retailPrice);

		// 关联表
		sql.innerJoin(SysPssProductCategoryEntity.class, new Condition(SysPssProductR.productCategory, SysPssProductCategoryR.id));
		sql.select(SysPssProductCategoryR.categoryName, "productCategory");

		// 字典表
		int i = 0;
		LKDictUtils.usingStatus(sql, SysPssProductR.usingStatus, i++);
		LKDictUtils4Pss.pssProductUnit(sql, SysPssProductR.unit, i++);

		// 筛选条件（必填项）
		// 公司ID
		params.addConditionCompId(false, sql, SysPssProductR.compId);
		// 在用状态
		params.addConditionUsingStatus(sql, SysPssProductR.usingStatus, sin.getUsingStatus());

		// 筛选条件（业务项）
		String productCategory = sin.getProductCategory();
		if (StringUtils.isNotBlank(productCategory)) {
			sql.eq(SysPssProductR.productCategory, productCategory);
		}

		String productCode = sin.getProductCode();
		if (StringUtils.isNotBlank(productCode)) {
			sql.like(SysPssProductR.productCode, LikeType.ALL, productCode);
		}

		String productName = sin.getProductName();
		if (StringUtils.isNotBlank(productName)) {
			sql.like(SysPssProductR.productName, LikeType.ALL, productName);
		}

		String barcode = sin.getBarcode();
		if (StringUtils.isNotBlank(barcode)) {
			sql.eq(SysPssProductR.barcode, barcode);
		}

		// 排序条件
		sql.addOrders(new Order(SysPssProductR.id, false));
	}

}
