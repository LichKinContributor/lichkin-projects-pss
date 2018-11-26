package com.lichkin.application.apis.api50301.L.n00;

import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lichkin.application.mappers.impl.PssStockQtyMapper;
import com.lichkin.application.mappers.impl.in.PssStockOutQtyIn;
import com.lichkin.application.mappers.impl.out.PssStockOutQtyOut;
import com.lichkin.application.utils.LKDictUtils4Pss;
import com.lichkin.framework.db.beans.Condition;
import com.lichkin.framework.db.beans.Order;
import com.lichkin.framework.db.beans.QuerySQL;
import com.lichkin.framework.db.beans.SysPssOtherStockOrderProductR;
import com.lichkin.framework.db.beans.SysPssOtherStockOrderR;
import com.lichkin.framework.db.beans.SysPssProductR;
import com.lichkin.framework.db.beans.SysPssStockR;
import com.lichkin.springframework.controllers.ApiKeyValues;
import com.lichkin.springframework.entities.impl.SysPssOtherStockOrderEntity;
import com.lichkin.springframework.entities.impl.SysPssOtherStockOrderProductEntity;
import com.lichkin.springframework.entities.impl.SysPssProductEntity;
import com.lichkin.springframework.entities.impl.SysPssStockEntity;
import com.lichkin.springframework.services.LKApiBusGetListService;

@Service("SysPssOtherStockOrderProductL00Service")
public class S extends LKApiBusGetListService<I, O, SysPssOtherStockOrderProductEntity> {

	@Autowired
	private PssStockQtyMapper pssStockOutQtyMapper;


	@Override
	protected void initSQL(I sin, ApiKeyValues<I> params, QuerySQL sql) {
		// 主表
		// sql.select(SysPssOtherStockOrderProductR.id);
		sql.select(SysPssOtherStockOrderProductR.quantity);

		// 关联表
		sql.innerJoin(SysPssProductEntity.class, new Condition(SysPssOtherStockOrderProductR.productId, SysPssProductR.id));
		sql.select(SysPssProductR.id);
		sql.select(SysPssProductR.productCode);
		sql.select(SysPssProductR.productName);
		sql.select(SysPssProductR.barcode);

		if (sin.getIsView().equals(Boolean.TRUE)) {
			sql.select(SysPssOtherStockOrderProductR.stockQuantity);
			sql.select(SysPssOtherStockOrderProductR.canOutQuantity);
		} else {
			// 其它出库单
			if (sin.getOrderType().equals(Boolean.FALSE)) {
				sql.innerJoin(SysPssOtherStockOrderEntity.class, new Condition(SysPssOtherStockOrderProductR.orderId, SysPssOtherStockOrderR.id));
				sql.innerJoin(SysPssStockEntity.class,

						new Condition(SysPssStockR.storageId, SysPssOtherStockOrderR.storageId),

						new Condition(SysPssStockR.productId, SysPssOtherStockOrderProductR.productId)

				);
				sql.select(SysPssStockR.storageId);
				sql.select(SysPssStockR.quantity, "stockQuantity");
			}
		}

		// 字典表
		int i = 0;
		LKDictUtils4Pss.pssProductUnit(sql, SysPssProductR.unit, i++);

		// 筛选条件（必填项）
//		addConditionId(sql, SysPssOtherStockOrderProductR.id, params.getId());
//		addConditionLocale(sql, SysPssOtherStockOrderProductR.locale, params.getLocale());
//		addConditionCompId(true, sql, SysPssOtherStockOrderProductR.compId, params.getCompId(), params.getBusCompId());
//		addConditionUsingStatus(params.getCompId(), sql, SysPssOtherStockOrderProductR.usingStatus, params.getUsingStatus(), LKUsingStatusEnum.STAND_BY, LKUsingStatusEnum.USING);

		// 筛选条件（业务项）
		sql.eq(SysPssOtherStockOrderProductR.orderId, sin.getOrderId());

		// 排序条件
		sql.addOrders(new Order(SysPssOtherStockOrderProductR.id, false));
	}


	@Override
	protected List<O> afterQuery(I sin, ApiKeyValues<I> params, List<O> list) {
		// 只是查看 不需要计算可出库数量
		if (sin.getIsView().equals(Boolean.TRUE)) {
			return list;
		}
		// 出库单
		if (sin.getOrderType().equals(Boolean.FALSE)) {
			if (CollectionUtils.isNotEmpty(list)) {
				String storageId = "";
				StringBuffer prodIds = new StringBuffer();
				for (int i = 0; i < list.size(); i++) {
					O o = list.get(i);
					o.setCanOutQuantity(o.getStockQuantity());
					storageId = o.getStorageId();
					prodIds.append("'" + o.getId() + "'");
					if (i < (list.size() - 1)) {
						prodIds.append(",");
					}
				}

				// 用mybatis 查询所有类型出库单的已填写的产品出库总和，计算可提交的出库数量
				List<PssStockOutQtyOut> qtyList = pssStockOutQtyMapper.findStockOutQty(new PssStockOutQtyIn("'" + storageId + "'", prodIds.toString(), sin.getOrderId()));
				if (CollectionUtils.isNotEmpty(qtyList)) {
					for (PssStockOutQtyOut stockOutQty : qtyList) {
						for (O o : list) {
							if (o.getId().equals(stockOutQty.getProductId())) {
								o.setCanOutQuantity(o.getStockQuantity() - stockOutQty.getQuantity());
								break;
							}
						}
					}
				}
			}
		}
		return list;
	}

}
