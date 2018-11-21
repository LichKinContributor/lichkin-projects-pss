package com.lichkin.application.apis.api50401.L.n00;

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
import com.lichkin.framework.db.beans.SysPssAllotOrderProductR;
import com.lichkin.framework.db.beans.SysPssAllotOrderR;
import com.lichkin.framework.db.beans.SysPssProductR;
import com.lichkin.framework.db.beans.SysPssStockR;
import com.lichkin.springframework.entities.impl.SysPssAllotOrderEntity;
import com.lichkin.springframework.entities.impl.SysPssAllotOrderProductEntity;
import com.lichkin.springframework.entities.impl.SysPssProductEntity;
import com.lichkin.springframework.entities.impl.SysPssStockEntity;
import com.lichkin.springframework.services.LKApiBusGetListService;

@Service("SysPssAllotOrderProductL00Service")
public class S extends LKApiBusGetListService<I, O, SysPssAllotOrderProductEntity> {

	@Autowired
	private PssStockQtyMapper pssStockOutQtyMapper;


	@Override
	protected void initSQL(I sin, String locale, String compId, String loginId, QuerySQL sql) {
		// 主表
		// sql.select(SysPssAllotOrderProductR.id);
		sql.select(SysPssAllotOrderProductR.quantity);

		// 关联表
		sql.innerJoin(SysPssProductEntity.class, new Condition(SysPssAllotOrderProductR.productId, SysPssProductR.id));
		sql.select(SysPssProductR.id);
		sql.select(SysPssProductR.productCode);
		sql.select(SysPssProductR.productName);
		sql.select(SysPssProductR.barcode);

		sql.innerJoin(SysPssAllotOrderEntity.class, new Condition(SysPssAllotOrderProductR.orderId, SysPssAllotOrderR.id));
		sql.innerJoin(SysPssStockEntity.class,

				new Condition(SysPssStockR.storageId, SysPssAllotOrderR.outStorageId),

				new Condition(SysPssStockR.productId, SysPssAllotOrderProductR.productId)

		);
		sql.select(SysPssStockR.storageId);
		sql.select(SysPssStockR.quantity, "stockQuantity");

		// 字典表
		int i = 0;
		LKDictUtils4Pss.pssProductUnit(sql, SysPssProductR.unit, i++);

		// 筛选条件（必填项）
		sql.eq(SysPssAllotOrderProductR.orderId, sin.getOrderId());

		// 排序条件
		sql.addOrders(new Order(SysPssAllotOrderProductR.id, false));
	}


	@Override
	protected List<O> afterQuery(I sin, String locale, String compId, String loginId, List<O> list) {
		if (CollectionUtils.isNotEmpty(list)) {
			String storageId = "";
			StringBuffer prodIds = new StringBuffer();
			for (int i = 0; i < list.size(); i++) {
				O o = list.get(i);
				o.setCanOutQty(o.getStockQuantity());
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
							o.setCanOutQty(o.getStockQuantity() - stockOutQty.getQuantity());
							break;
						}
					}
				}
			}
		}
		return list;
	}

}
