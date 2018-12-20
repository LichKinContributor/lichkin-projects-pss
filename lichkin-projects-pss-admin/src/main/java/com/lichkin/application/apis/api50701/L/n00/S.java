package com.lichkin.application.apis.api50701.L.n00;

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
import com.lichkin.framework.db.beans.SysPssProductR;
import com.lichkin.framework.db.beans.SysPssPurchaseReturnStockInOrderProductR;
import com.lichkin.framework.db.beans.SysPssPurchaseReturnStockInOrderR;
import com.lichkin.framework.db.beans.SysPssStockR;
import com.lichkin.springframework.controllers.ApiKeyValues;
import com.lichkin.springframework.entities.impl.SysPssProductEntity;
import com.lichkin.springframework.entities.impl.SysPssPurchaseReturnStockInOrderEntity;
import com.lichkin.springframework.entities.impl.SysPssPurchaseReturnStockInOrderProductEntity;
import com.lichkin.springframework.entities.impl.SysPssStockEntity;
import com.lichkin.springframework.services.LKApiBusGetListService;

@Service("SysPssPurchaseReturnStockInOrderProductL00Service")
public class S extends LKApiBusGetListService<I, O, SysPssPurchaseReturnStockInOrderProductEntity> {

	@Autowired
	private PssStockQtyMapper pssStockOutQtyMapper;


	@Override
	protected void initSQL(I sin, ApiKeyValues<I> params, QuerySQL sql) {

		// 主表
		// sql.select(SysPssPurchaseReturnStockInOrderProductR.id);
		sql.select(SysPssPurchaseReturnStockInOrderProductR.quantity);
		sql.select(SysPssPurchaseReturnStockInOrderProductR.unitPrice);
		sql.select(SysPssPurchaseReturnStockInOrderProductR.subTotalPrice);

		// 关联表
		sql.innerJoin(SysPssProductEntity.class, new Condition(SysPssPurchaseReturnStockInOrderProductR.productId, SysPssProductR.id));
		sql.select(SysPssProductR.id);
		sql.select(SysPssProductR.productCode);
		sql.select(SysPssProductR.productName);
		sql.select(SysPssProductR.barcode);

		if (sin.getIsView().equals(Boolean.TRUE)) {
			sql.select(SysPssPurchaseReturnStockInOrderProductR.stockQuantity);
			sql.select(SysPssPurchaseReturnStockInOrderProductR.canOutQuantity);
		} else {
			sql.innerJoin(SysPssPurchaseReturnStockInOrderEntity.class, new Condition(SysPssPurchaseReturnStockInOrderProductR.orderId, SysPssPurchaseReturnStockInOrderR.id));
			sql.innerJoin(SysPssStockEntity.class,

					new Condition(SysPssStockR.storageId, SysPssPurchaseReturnStockInOrderR.storageId),

					new Condition(SysPssStockR.productId, SysPssPurchaseReturnStockInOrderProductR.productId)

			);
			sql.select(SysPssStockR.storageId);
			sql.select(SysPssStockR.quantity, "stockQuantity");
		}

		// 字典表
		int i = 0;
		LKDictUtils4Pss.pssProductUnit(sql, SysPssProductR.unit, i++);

		// 筛选条件（业务项）
		sql.eq(SysPssPurchaseReturnStockInOrderProductR.orderId, sin.getOrderId());

		// 排序条件
		sql.addOrders(new Order(SysPssPurchaseReturnStockInOrderProductR.id, false));

	}


	@Override
	protected List<O> afterQuery(I sin, ApiKeyValues<I> params, List<O> list) {

		// 只是查看 不需要计算可出库数量
		if (sin.getIsView().equals(Boolean.TRUE)) {
			return list;
		}
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
		return list;

	}

}
