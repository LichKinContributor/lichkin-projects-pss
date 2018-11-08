package com.lichkin.application.services.bus.impl;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import com.lichkin.framework.db.beans.QuerySQL;
import com.lichkin.framework.db.beans.SysPssStockR;
import com.lichkin.springframework.entities.impl.SysPssAllotOrderEntity;
import com.lichkin.springframework.entities.impl.SysPssProductEntity;
import com.lichkin.springframework.entities.impl.SysPssStockEntity;
import com.lichkin.springframework.entities.suppers.PssOrderProductEntity;
import com.lichkin.springframework.entities.suppers.PssStockOrderEntity;
import com.lichkin.springframework.services.LKDBService;

@Service
public class SysPssStockBusService extends LKDBService {

	public void changeStockQuantity(PssStockOrderEntity stockOrder, List<PssOrderProductEntity> orderProductList) {
		if (stockOrder.getOrderType()) {// 入库单业务处理
			for (PssOrderProductEntity productEntity : orderProductList) {
				increaseStock(stockOrder.getCompId(), stockOrder.getStorageId(), productEntity);
			}
		} else {// 出库单业务处理
			for (PssOrderProductEntity productEntity : orderProductList) {
				reduceStock(stockOrder.getStorageId(), productEntity);
			}
		}
	}


	public int checkProductStockOut(String storageId, List<PssOrderProductEntity> orderProductList) {
		int errorCode = 0;
		for (PssOrderProductEntity entity : orderProductList) {
			QuerySQL sql = new QuerySQL(SysPssStockEntity.class);
			sql.eq(SysPssStockR.storageId, storageId);
			sql.eq(SysPssStockR.productId, entity.getProductId());
			SysPssStockEntity stockEntity = dao.getOne(sql, SysPssStockEntity.class);
			if (stockEntity == null) {
				errorCode = 1;
				break;
			} else {
				// TODO 用mybatis 查询所有审核中的出库单的产品总和，计算可提交的出库数量

				if (entity.getQuantity() > stockEntity.getQuantity()) {
					errorCode = 2;
					break;
				}
			}
		}
		return errorCode;
	}


	public void changeStockQuantityByAllotOrder(SysPssAllotOrderEntity allotOrder, List<PssOrderProductEntity> orderProductList) {
		for (PssOrderProductEntity productEntity : orderProductList) {
			reduceStock(allotOrder.getOutStorageId(), productEntity);
			increaseStock(allotOrder.getCompId(), allotOrder.getInStorageId(), productEntity);
		}
	}


	// 增加库存
	private void increaseStock(String compId, String storageId, PssOrderProductEntity productEntity) {
		// 查询库存中是否存在此商品
		QuerySQL sql = new QuerySQL(SysPssStockEntity.class);
		sql.eq(SysPssStockR.storageId, storageId);
		sql.eq(SysPssStockR.productId, productEntity.getProductId());
		SysPssStockEntity stockEntity = dao.getOne(sql, SysPssStockEntity.class);

		if (stockEntity == null) {// 库存中不存在此商品
			stockEntity = new SysPssStockEntity();
			stockEntity.setCompId(compId);
			stockEntity.setStorageId(storageId);
			stockEntity.setProductId(productEntity.getProductId());
			stockEntity.setQuantity(productEntity.getQuantity());
			dao.persistOne(stockEntity);

			// 查询商品是否有下级商品
			SysPssProductEntity product = dao.findOneById(SysPssProductEntity.class, productEntity.getProductId());
			if (StringUtils.isNotBlank(product.getSubProduct())) {// 商品有下级商品
				sql = new QuerySQL(SysPssStockEntity.class);
				sql.eq(SysPssStockR.storageId, storageId);
				sql.eq(SysPssStockR.productId, product.getSubProduct());
				stockEntity = dao.getOne(sql, SysPssStockEntity.class);

				// 默认插入一条下级商品
				if (stockEntity == null) {
					SysPssStockEntity subProductStock = new SysPssStockEntity();
					subProductStock.setCompId(compId);
					subProductStock.setStorageId(storageId);
					subProductStock.setProductId(product.getSubProduct());
					subProductStock.setQuantity(0);
					dao.persistOne(subProductStock);
				}
			}
		} else {
			stockEntity.setQuantity(stockEntity.getQuantity() + productEntity.getQuantity());
			dao.mergeOne(stockEntity);
		}
	}


	// 减少库存
	private void reduceStock(String storageId, PssOrderProductEntity productEntity) {
		QuerySQL sql = new QuerySQL(SysPssStockEntity.class);
		sql.eq(SysPssStockR.storageId, storageId);
		sql.eq(SysPssStockR.productId, productEntity.getProductId());
		SysPssStockEntity stockEntity = dao.getOne(sql, SysPssStockEntity.class);
		stockEntity.setQuantity(stockEntity.getQuantity() - productEntity.getQuantity());
		dao.mergeOne(stockEntity);
	}
}
