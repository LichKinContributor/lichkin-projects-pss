package com.lichkin.application.apis.api50006.P.n00;

import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import com.lichkin.application.mappers.impl.PssStockQtyMapper;
import com.lichkin.application.mappers.impl.in.PssStockOutQtyIn;
import com.lichkin.application.mappers.impl.out.PssStockOutQtyOut;
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

	@Autowired
	private PssStockQtyMapper pssStockOutQtyMapper;


	@Override
	protected void initSQL(I sin, String locale, String compId, String loginId, QuerySQL sql) {
		// 主表
		sql.select(SysPssStockR.id);
		sql.select(SysPssStockR.quantity);

		// 关联表
		sql.innerJoin(SysPssStorageEntity.class, new Condition(SysPssStockR.storageId, SysPssStorageR.id));
		sql.select(SysPssStorageR.id, "storageId");
		sql.select(SysPssStorageR.storageName);
		sql.select(SysPssStorageR.storageCode);
		sql.innerJoin(SysPssProductEntity.class, new Condition(SysPssStockR.productId, SysPssProductR.id));
		sql.select(SysPssProductR.id, "productId");
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


	@Override
	protected Page<O> afterQuery(I sin, String locale, String compId, String loginId, Page<O> page) {
		List<O> list = page.getContent();
		if (CollectionUtils.isNotEmpty(list)) {
			StringBuffer storageIds = new StringBuffer();
			StringBuffer prodIds = new StringBuffer();
			for (int i = 0; i < list.size(); i++) {
				O o = list.get(i);
				o.setCanOutQty(o.getQuantity());
				storageIds.append("'" + o.getStorageId() + "'");
				prodIds.append("'" + o.getProductId() + "'");
				if (i < (list.size() - 1)) {
					storageIds.append(",");
					prodIds.append(",");
				}
			}

			// 用mybatis 查询所有类型出库单的已填写的产品出库总和，计算可出库数量
			List<PssStockOutQtyOut> qtyList = pssStockOutQtyMapper.findStockOutQty(new PssStockOutQtyIn(storageIds.toString(), prodIds.toString(), null));
			if (CollectionUtils.isNotEmpty(qtyList)) {
				for (PssStockOutQtyOut stockOutQty : qtyList) {
					for (O o : list) {
						if (o.getStorageId().equals(stockOutQty.getStorageId()) && o.getProductId().equals(stockOutQty.getProductId())) {
							o.setCanOutQty(o.getQuantity() - stockOutQty.getQuantity());
							break;
						}
					}
				}
			}
		}

		return page;
	}

}
