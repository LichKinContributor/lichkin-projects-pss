package com.lichkin.application.services.bus.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lichkin.application.mappers.impl.PssStockQtyMapper;
import com.lichkin.application.mappers.impl.in.PssStockOutQtyIn;
import com.lichkin.application.mappers.impl.out.PssStockOutQtyOut;
import com.lichkin.framework.db.beans.QuerySQL;
import com.lichkin.framework.db.beans.SysPssStockR;
import com.lichkin.framework.json.LKJsonUtils;
import com.lichkin.springframework.entities.impl.SysPssAllotOrderEntity;
import com.lichkin.springframework.entities.impl.SysPssProductEntity;
import com.lichkin.springframework.entities.impl.SysPssStockCheckOrderEntity;
import com.lichkin.springframework.entities.impl.SysPssStockCheckOrderProductEntity;
import com.lichkin.springframework.entities.impl.SysPssStockEntity;
import com.lichkin.springframework.entities.suppers.PssOrderProductEntity;
import com.lichkin.springframework.entities.suppers.PssStockOrderEntity;
import com.lichkin.springframework.services.LKDBService;

@Service
public class SysPssStockBusService extends LKDBService {

	@Autowired
	private PssStockQtyMapper pssStockOutQtyMapper;


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


	public String checkProductStockOut(String storageId, String productList, String orderId) {
		String errorMsg = "";

		// 转为产品对象
		List<SysPssProductEntity> listProd = LKJsonUtils.toList(productList, SysPssProductEntity.class);
		List<String> prodIdList = new ArrayList<>();
		Map<String, String> prodInfoMap = new HashMap<>();
		for (int i = 0; i < listProd.size(); i++) {
			SysPssProductEntity prod = listProd.get(i);
			String prodId = prod.getId();
			String productName = prod.getProductName();

			prodIdList.add(prodId);
			prodInfoMap.put(prodId, productName);
		}

		String prodIds = "'" + StringUtils.join(prodIdList.toArray(), "','") + "'";

		// 查询仓库中产品库存信息
		QuerySQL sql = new QuerySQL(SysPssStockEntity.class);
		sql.eq(SysPssStockR.storageId, storageId);
		sql.in(SysPssStockR.productId, prodIdList);
		List<SysPssStockEntity> listStockProd = dao.getList(sql, SysPssStockEntity.class);
		Map<String, Integer> prodStockQtyMap = listStockProd.stream().collect(Collectors.groupingBy(o -> o.getProductId(), Collectors.summingInt(o -> o.getQuantity())));

		// 判断出库产品是否在仓库中
		for (int i = 0; i < listProd.size(); i++) {
			SysPssProductEntity prod = listProd.get(i);
			String prodId = prod.getId();
			String productName = prod.getProductName();

			// 产品不存在
			if (prodStockQtyMap.get(prodId) == null) {
				errorMsg += productName + ";";
			}
		}
		if (StringUtils.isNotBlank(errorMsg)) {
			return errorMsg + "不在当前仓库中。";
		}

		// 合并产品出库数量
		List<SysPssStockEntity> orderProductList = LKJsonUtils.toList(productList, SysPssStockEntity.class);
		Map<String, Integer> prodQtyMap = orderProductList.stream().collect(Collectors.groupingBy(o -> o.getId(), Collectors.summingInt(o -> o.getQuantity())));

		// 查询所有类型出库单的已填写的产品出库总和
		List<PssStockOutQtyOut> listStockOutQty = pssStockOutQtyMapper.findStockOutQty(new PssStockOutQtyIn("'" + storageId + "'", prodIds, orderId));
		Map<String, Integer> prodStockOutQtyMap = listStockOutQty.stream().collect(Collectors.groupingBy(o -> o.getProductId(), Collectors.summingInt(o -> o.getQuantity())));

		// 判断产品出库数量是否足够
		for (Map.Entry<String, Integer> entry : prodQtyMap.entrySet()) {
			String prodId = entry.getKey();
			// 填写数量
			Integer productQty = entry.getValue();
			// 库存数量
			Integer prodStockQty = prodStockQtyMap.get(prodId);
			// 所有类型出库单的数量
			Integer prodStockOutQty = prodStockOutQtyMap.get(prodId);
			if (prodStockOutQty == null) {
				prodStockOutQty = 0;
			}

			if (productQty > (prodStockQty - prodStockOutQty)) {
				errorMsg += prodInfoMap.get(prodId) + ";";
			}
		}
		if (StringUtils.isNotBlank(errorMsg)) {
			return errorMsg + "出库总量超过库存数量。";
		}

		return "";
	}


	public int checkProductStockOut3(String storageId, List<PssOrderProductEntity> orderProductList, String orderId) {
		// 将相同产品合并计算数量
		Map<String, Integer> prodQtyMap = orderProductList.stream().collect(Collectors.groupingBy(o -> o.getProductId(), Collectors.summingInt(o -> o.getQuantity())));

		int errorCode = 0;
		for (Map.Entry<String, Integer> entry : prodQtyMap.entrySet()) {
			QuerySQL sql = new QuerySQL(SysPssStockEntity.class);
			sql.eq(SysPssStockR.storageId, storageId);
			sql.eq(SysPssStockR.productId, entry.getKey());
			SysPssStockEntity stockEntity = dao.getOne(sql, SysPssStockEntity.class);
			if (stockEntity == null) {
				errorCode = 1;
				break;
			} else {
				// 用mybatis 查询所有类型出库单的已填写的产品出库总和，计算可提交的出库数量
				List<PssStockOutQtyOut> list = pssStockOutQtyMapper.findStockOutQty(new PssStockOutQtyIn("'" + storageId + "'", "'" + entry.getKey() + "'", orderId));
				int stockQty = stockEntity.getQuantity();
				if (CollectionUtils.isNotEmpty(list)) {
					PssStockOutQtyOut out = list.get(0);
					stockQty = stockQty - out.getQuantity();
				}

				if (entry.getValue() > stockQty) {
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


	public void changeStockQuantityByCheckOrder(SysPssStockCheckOrderEntity checkOrder, List<SysPssStockCheckOrderProductEntity> orderProductList) {
		if (CollectionUtils.isNotEmpty(orderProductList)) {
			// 将相同产品合并计算数量
			Map<String, Integer> prodQtyMap = orderProductList.stream().collect(Collectors.groupingBy(o -> o.getProductId(), Collectors.summingInt(o -> o.getQuantity())));

			List<String> prodIdList = new ArrayList<>();
			for (SysPssStockCheckOrderProductEntity prod : orderProductList) {
				prodIdList.add(prod.getProductId());
			}
			// 查询库存中商品
			QuerySQL sql = new QuerySQL(SysPssStockEntity.class);
			sql.eq(SysPssStockR.storageId, checkOrder.getStorageId());
			sql.in(SysPssStockR.productId, prodIdList);
			List<SysPssStockEntity> stockList = dao.getList(sql, SysPssStockEntity.class);

			for (SysPssStockEntity stockEntity : stockList) {
				stockEntity.setQuantity(prodQtyMap.get(stockEntity.getProductId()));
			}
			dao.mergeList(stockList);
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
