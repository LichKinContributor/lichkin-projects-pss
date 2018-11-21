package com.lichkin.application.services.bus.impl;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import com.lichkin.framework.db.beans.Condition;
import com.lichkin.framework.db.beans.DeleteSQL;
import com.lichkin.framework.db.beans.QuerySQL;
import com.lichkin.framework.db.beans.SysPssProductR;
import com.lichkin.framework.db.beans.SysPssStockCheckOrderProductR;
import com.lichkin.framework.db.beans.SysPssStockCheckOrderR;
import com.lichkin.framework.defines.enums.impl.LKUsingStatusEnum;
import com.lichkin.framework.json.LKJsonUtils;
import com.lichkin.framework.utils.LKDateTimeUtils;
import com.lichkin.framework.utils.LKRandomUtils;
import com.lichkin.springframework.entities.impl.SysPssProductEntity;
import com.lichkin.springframework.entities.impl.SysPssStockCheckOrderEntity;
import com.lichkin.springframework.entities.impl.SysPssStockCheckOrderProductEntity;
import com.lichkin.springframework.services.LKDBService;

@Service
public class SysPssStockCheckOrderBusService extends LKDBService {

	public String analysisOrderNo() {
		return LKDateTimeUtils.now() + LKRandomUtils.create(15);
	}


	public void addPssStockCheckOrderProduct(String id, String productList) {
		List<SysPssStockCheckOrderProductEntity> listProduct = LKJsonUtils.toList(productList, SysPssStockCheckOrderProductEntity.class);
		for (int i = 0; i < listProduct.size(); i++) {
			SysPssStockCheckOrderProductEntity product = listProduct.get(i);
			product.setProductId(product.getId());
			product.setId(null);
			product.setOrderId(id);
			product.setSortId(i);
		}
		dao.persistList(listProduct);
	}


	public void clearPssStockCheckOrderProduct(String id) {
		DeleteSQL sql = new DeleteSQL(SysPssStockCheckOrderProductEntity.class);
		sql.eq(SysPssStockCheckOrderProductR.orderId, id);
		dao.delete(sql);
	}


	/**
	 * 验证产品是否在同一天的盘点单中盘点过
	 * @param billDate 盘点日期
	 * @param currentListProduct 盘点产品列表
	 * @param orderId 盘点单ID
	 * @return 错误提示信息
	 */
	public String checkProdExist(String billDate, List<SysPssStockCheckOrderProductEntity> currentListProduct, String orderId) {
		QuerySQL sql = new QuerySQL(SysPssProductEntity.class);
		sql.selectTable(SysPssProductEntity.class);
		sql.innerJoin(SysPssStockCheckOrderProductEntity.class, new Condition(SysPssProductR.id, SysPssStockCheckOrderProductR.productId));
		sql.innerJoin(SysPssStockCheckOrderEntity.class, new Condition(SysPssStockCheckOrderProductR.orderId, SysPssStockCheckOrderR.id));
		sql.eq(SysPssStockCheckOrderR.billDate, billDate);
		sql.neq(SysPssStockCheckOrderR.usingStatus, LKUsingStatusEnum.DEPRECATED);
		if (StringUtils.isNotBlank(orderId)) {
			sql.neq(SysPssStockCheckOrderR.id, orderId);
		}
		List<SysPssProductEntity> checkedListProduct = dao.getList(sql, SysPssProductEntity.class);

		String errorMsg = "";
		for (SysPssProductEntity checkedProduct : checkedListProduct) {
			for (SysPssStockCheckOrderProductEntity currentProduct : currentListProduct) {
				if (checkedProduct.getId().equals(currentProduct.getId())) {
					errorMsg += checkedProduct.getProductName() + ";";
					break;
				}
			}
		}
		return errorMsg;
	}
}
