package com.lichkin.application.apis.api50300.U.n01;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lichkin.application.services.bus.impl.SysPssStockBusService;
import com.lichkin.application.services.impl.ActivitiStartProcessService;
import com.lichkin.application.utils.LKDictUtils4Pss;
import com.lichkin.framework.db.beans.Condition;
import com.lichkin.framework.db.beans.QuerySQL;
import com.lichkin.framework.db.beans.SysPssOtherStockOrderProductR;
import com.lichkin.framework.db.beans.SysPssOtherStockOrderR;
import com.lichkin.framework.db.beans.SysPssStorageR;
import com.lichkin.framework.defines.enums.LKCodeEnum;
import com.lichkin.framework.defines.enums.impl.ApprovalStatusEnum;
import com.lichkin.framework.defines.exceptions.LKRuntimeException;
import com.lichkin.framework.json.LKJsonUtils;
import com.lichkin.springframework.entities.impl.SysPssOtherStockOrderEntity;
import com.lichkin.springframework.entities.impl.SysPssOtherStockOrderProductEntity;
import com.lichkin.springframework.entities.impl.SysPssStorageEntity;
import com.lichkin.springframework.entities.suppers.PssOrderProductEntity;
import com.lichkin.springframework.services.LKApiBusUpdateWithoutCheckerService;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Service("SysPssOtherStockOrderU01Service")
public class S extends LKApiBusUpdateWithoutCheckerService<I, SysPssOtherStockOrderEntity> {

	@Autowired
	private ActivitiStartProcessService activitiStartProcessService;

	@Autowired
	private SysPssStockBusService stockBusService;


	@Getter
	@RequiredArgsConstructor
	enum ErrorCodes implements LKCodeEnum {

		PSS_PRODUCT_NOT_IN_STORAGE(60000),

		PSS_PRODUCT_STOCKOUT_QUANTITY_OUT_OF_STOCK_QUANTITY(60000),

		;

		private final Integer code;

	}


	@Override
	protected void beforeSaveMain(I sin, String locale, String compId, String loginId, SysPssOtherStockOrderEntity entity) {
		// 出库需做校验处理
		if (!entity.getOrderType()) {
			QuerySQL sql = new QuerySQL(false, SysPssOtherStockOrderProductEntity.class);
			sql.eq(SysPssOtherStockOrderProductR.orderId, entity.getId());
			List<PssOrderProductEntity> orderProductList = dao.getList(sql, PssOrderProductEntity.class);
			// 校验仓库中产品
			int errorCode = stockBusService.checkProductStockOut(entity.getStorageId(), orderProductList);
			switch (errorCode) {
				case 1:
					throw new LKRuntimeException(ErrorCodes.PSS_PRODUCT_NOT_IN_STORAGE);
				case 2:
					throw new LKRuntimeException(ErrorCodes.PSS_PRODUCT_STOCKOUT_QUANTITY_OUT_OF_STOCK_QUANTITY);
				default:
				break;
			}
		}

		Map<String, Object> datas = new HashMap<>();
		// 必填
		datas.put("id", entity.getId());
		// 订单统一参数
		datas.put("orderNo", entity.getOrderNo());
		datas.put("billDate", entity.getBillDate());
		datas.put("remarks", entity.getRemarks());
		// 关联表参数转换
		setPurchaseOrderDatas(datas, entity.getId(), compId);
		// 发起流程
		activitiStartProcessService.startByAdmin(entity, compId, entity.getOrderType() ? "PSS_OTHER_STOCK_ORDER_IN" : "PSS_OTHER_STOCK_ORDER_OUT", loginId, sin.getDatas().getUser().getUserName(), LKJsonUtils.toJson(datas));
	}


	private void setPurchaseOrderDatas(Map<String, Object> datas, String id, String compId) {
		QuerySQL sql = new QuerySQL(SysPssOtherStockOrderEntity.class);

		// P接口逻辑简化
		// 主表
		sql.select(SysPssOtherStockOrderR.id);

		// 关联表
		sql.innerJoin(SysPssStorageEntity.class, new Condition(SysPssOtherStockOrderR.storageId, SysPssStorageR.id));
		sql.select(SysPssStorageR.storageName);

		// 字典表
		int i = 0;
		LKDictUtils4Pss.storageTypeIn(sql, compId, SysPssOtherStockOrderR.storageType, i++);

		sql.eq(SysPssOtherStockOrderR.id, id);

		com.lichkin.application.apis.api50300.P.n00.O out = dao.getOne(sql, com.lichkin.application.apis.api50300.P.n00.O.class);
		datas.put("storageType", out.getStorageType());
		datas.put("storageName", out.getStorageName());
	}


	@Override
	protected void afterSaveMain(I sin, String locale, String compId, String loginId, SysPssOtherStockOrderEntity entity, String id) {
		if (ApprovalStatusEnum.APPROVED.equals(entity.getApprovalStatus())) {
			QuerySQL sql = new QuerySQL(false, SysPssOtherStockOrderProductEntity.class);
			sql.eq(SysPssOtherStockOrderProductR.orderId, id);
			List<PssOrderProductEntity> orderProductList = dao.getList(sql, PssOrderProductEntity.class);
			// 处理库存
			stockBusService.changeStockQuantity(entity, orderProductList);
		}
	}

}
