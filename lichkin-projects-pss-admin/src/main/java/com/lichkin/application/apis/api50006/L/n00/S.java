package com.lichkin.application.apis.api50006.L.n00;

import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lichkin.application.mappers.impl.PssStockQtyMapper;
import com.lichkin.application.mappers.impl.in.PssStockOutQtyIn;
import com.lichkin.application.mappers.impl.out.PssStockOutQtyOut;
import com.lichkin.application.utils.LKDictUtils4Pss;
import com.lichkin.framework.db.beans.Condition;
import com.lichkin.framework.db.beans.QuerySQL;
import com.lichkin.framework.db.beans.SysPssProductR;
import com.lichkin.framework.db.beans.SysPssStockR;
import com.lichkin.framework.defines.enums.impl.PssOrderTypeEnum;
import com.lichkin.springframework.controllers.ApiKeyValues;
import com.lichkin.springframework.entities.impl.SysPssProductEntity;
import com.lichkin.springframework.entities.impl.SysPssStockEntity;
import com.lichkin.springframework.services.LKApiBusGetListService;

@Service("SysPssStockL00Service")
public class S extends LKApiBusGetListService<I, O, SysPssStockEntity> {

	@Autowired
	private PssStockQtyMapper pssStockOutQtyMapper;


	@Override
	protected void initSQL(I sin, ApiKeyValues<I> params, QuerySQL sql) {
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
		params.addConditionCompId(false, sql, SysPssStockR.compId);
		// 仓库ID
		sql.eq(SysPssStockR.storageId, sin.getStorageId());

		// 条形码
		String barcode = sin.getBarcode();
		if (StringUtils.isNotBlank(barcode)) {
			sql.eq(SysPssProductR.barcode, barcode);
		}

		// 产品ID
		String productId = sin.getProductId();
		if (StringUtils.isNotBlank(productId)) {
			sql.eq(SysPssProductR.id, productId);
		}
	}


	@Override
	protected List<O> afterQuery(I sin, ApiKeyValues<I> params, List<O> list) {
		// 盘点单显示实际库存量
		if (PssOrderTypeEnum.PSS_CHECK.equals(sin.getOrderType())) {
			return list;
		}

		if (CollectionUtils.isNotEmpty(list)) {
			O O = list.get(0);
			int stockQty = O.getStockQuantity();
			// 用mybatis 查询所有类型出库单的已填写的产品出库总和，计算可提交的出库数量
			List<PssStockOutQtyOut> qtyList = pssStockOutQtyMapper.findStockOutQty(new PssStockOutQtyIn("'" + sin.getStorageId() + "'", "'" + O.getId() + "'", sin.getOrderId()));
			if (CollectionUtils.isNotEmpty(qtyList)) {
				PssStockOutQtyOut out = qtyList.get(0);
				stockQty = stockQty - out.getQuantity();
			}
			O.setCanOutQuantity(stockQty < 0 ? 0 : stockQty);
		}
		return list;
	}

}
