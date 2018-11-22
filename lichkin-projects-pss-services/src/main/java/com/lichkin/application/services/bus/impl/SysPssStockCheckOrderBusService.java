package com.lichkin.application.services.bus.impl;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.joda.time.DateTime;
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
	 * 验证产品是否盘点过
	 * @param checkOrder 盘点单
	 * @param currentListProduct 盘点产品列表
	 * @return 错误提示信息
	 */
	public String checkProdExist(SysPssStockCheckOrderEntity checkOrder, List<SysPssStockCheckOrderProductEntity> currentListProduct) {
		// 查询是否在当天的盘点单盘点过相同的产品
		QuerySQL sql = new QuerySQL(SysPssProductEntity.class);
		sql.selectTable(SysPssProductEntity.class);
		sql.innerJoin(SysPssStockCheckOrderProductEntity.class, new Condition(SysPssProductR.id, SysPssStockCheckOrderProductR.productId));
		sql.innerJoin(SysPssStockCheckOrderEntity.class, new Condition(SysPssStockCheckOrderProductR.orderId, SysPssStockCheckOrderR.id));
		sql.eq(SysPssStockCheckOrderR.storageId, checkOrder.getStorageId());
		sql.gte(SysPssStockCheckOrderR.insertTime, LKDateTimeUtils.toString(DateTime.now().minusHours(12)));
		sql.neq(SysPssStockCheckOrderR.usingStatus, LKUsingStatusEnum.DEPRECATED);
		if (StringUtils.isNotBlank(checkOrder.getId())) {
			sql.neq(SysPssStockCheckOrderR.id, checkOrder.getId());
		}
		List<SysPssProductEntity> checkedListProduct = dao.getList(sql, SysPssProductEntity.class);

		String errorMsg = "";
		for (SysPssStockCheckOrderProductEntity currentProduct : currentListProduct) {
			for (SysPssProductEntity checkedProduct : checkedListProduct) {
				if (checkedProduct.getId().equals(currentProduct.getId())) {
					errorMsg += checkedProduct.getProductName() + ";";
					break;
				}
			}
		}
		if (StringUtils.isNotBlank(errorMsg)) {
			return errorMsg + "已盘点过";
		}
		return errorMsg;
	}
}
