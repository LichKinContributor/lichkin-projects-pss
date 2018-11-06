package com.lichkin.application.apis.api50006.P.n00;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import com.lichkin.framework.db.beans.Condition;
import com.lichkin.framework.db.beans.Order;
import com.lichkin.framework.db.beans.QuerySQL;
import com.lichkin.framework.db.beans.SysPssProductR;
import com.lichkin.framework.db.beans.SysPssStockR;
import com.lichkin.framework.db.beans.SysPssStorageR;
import com.lichkin.framework.db.enums.LikeType;
import com.lichkin.springframework.entities.impl.SysPssProductEntity;
import com.lichkin.springframework.entities.impl.SysPssStockEntity;
import com.lichkin.springframework.entities.impl.SysPssStorageEntity;
import com.lichkin.springframework.services.LKApiBusGetPageService;

@Service("SysPssStockP00Service")
public class S extends LKApiBusGetPageService<I, O, SysPssStockEntity> {

	@Override
	protected void initSQL(I sin, String locale, String compId, String loginId, QuerySQL sql) {
		// 主表
		sql.select(SysPssStockR.id);
		sql.select(SysPssStockR.quantity);

		// 关联表
		sql.innerJoin(SysPssStorageEntity.class, new Condition(SysPssStockR.storageId, SysPssStorageR.id));
		sql.select(SysPssStorageR.storageName);
		sql.select(SysPssStorageR.storageCode);
		sql.innerJoin(SysPssProductEntity.class, new Condition(SysPssStockR.productId, SysPssProductR.id));
		sql.select(SysPssProductR.productName);
		sql.select(SysPssProductR.productCode);
		sql.select(SysPssProductR.barcode);

		// 筛选条件（必填项）
		// 公司ID
		addConditionCompId(false, sql, SysPssStockR.compId, compId, sin.getCompId());

		// 筛选条件（业务项）
		String storageName = sin.getStorageName();
		if (StringUtils.isNotBlank(storageName)) {
			sql.like(SysPssStorageR.storageName, LikeType.ALL, storageName);
		}

		String storageCode = sin.getStorageCode();
		if (StringUtils.isNotBlank(storageCode)) {
			sql.like(SysPssStorageR.storageCode, LikeType.ALL, storageCode);
		}

		String productName = sin.getProductName();
		if (StringUtils.isNotBlank(productName)) {
			sql.like(SysPssProductR.productName, LikeType.ALL, productName);
		}

		String productCode = sin.getProductCode();
		if (StringUtils.isNotBlank(productCode)) {
			sql.like(SysPssProductR.productCode, LikeType.ALL, productCode);
		}

		String barcode = sin.getBarcode();
		if (StringUtils.isNotBlank(barcode)) {
			sql.eq(SysPssProductR.barcode, barcode);
		}

		sql.neq(SysPssStockR.quantity, 0);// 排除库存为0的项

		// 排序条件
		sql.addOrders(new Order(SysPssStockR.id, false));
	}

}
