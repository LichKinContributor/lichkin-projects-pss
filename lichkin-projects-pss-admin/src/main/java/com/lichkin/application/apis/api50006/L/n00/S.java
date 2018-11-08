package com.lichkin.application.apis.api50006.L.n00;

import org.springframework.stereotype.Service;

import com.lichkin.application.utils.LKDictUtils4Pss;
import com.lichkin.framework.db.beans.Condition;
import com.lichkin.framework.db.beans.QuerySQL;
import com.lichkin.framework.db.beans.SysPssProductR;
import com.lichkin.framework.db.beans.SysPssStockR;
import com.lichkin.springframework.entities.impl.SysPssProductEntity;
import com.lichkin.springframework.entities.impl.SysPssStockEntity;
import com.lichkin.springframework.services.LKApiBusGetListService;

@Service("SysPssStockProductL00Service")
public class S extends LKApiBusGetListService<I, O, SysPssStockEntity> {

	@Override
	protected void initSQL(I sin, String locale, String compId, String loginId, QuerySQL sql) {
		// 主表
		// sql.select(SysPssStockR.id);
		sql.select(SysPssStockR.quantity, "stockQuantity");

		// 关联表
		sql.innerJoin(SysPssProductEntity.class, new Condition(SysPssStockR.productId, SysPssProductR.id));
		sql.select(SysPssProductR.id);
		sql.select(SysPssProductR.productCode);
		sql.select(SysPssProductR.productName);
		sql.select(SysPssProductR.barcode);

		// 字典表
		int i = 0;
		LKDictUtils4Pss.pssProductUnit(sql, SysPssProductR.unit, i++);

		// 筛选条件（必填项）
		// 公司ID
		addConditionCompId(false, sql, SysPssStockR.compId, compId, null);
		// 仓库ID
		sql.eq(SysPssStockR.storageId, sin.getStorageId());
		// 条形码
		sql.eq(SysPssProductR.barcode, sin.getBarcode());
	}

}
