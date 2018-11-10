package com.lichkin.application.apis.api50400.U.n01;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lichkin.application.services.bus.impl.SysPssStockBusService;
import com.lichkin.application.services.impl.ActivitiStartProcessService;
import com.lichkin.framework.db.beans.Condition;
import com.lichkin.framework.db.beans.QuerySQL;
import com.lichkin.framework.db.beans.SysPssAllotOrderProductR;
import com.lichkin.framework.db.beans.SysPssAllotOrderR;
import com.lichkin.framework.db.beans.SysPssStorageR;
import com.lichkin.framework.defines.enums.LKCodeEnum;
import com.lichkin.framework.defines.enums.impl.ApprovalStatusEnum;
import com.lichkin.framework.defines.exceptions.LKRuntimeException;
import com.lichkin.framework.json.LKJsonUtils;
import com.lichkin.springframework.entities.impl.SysPssAllotOrderEntity;
import com.lichkin.springframework.entities.impl.SysPssAllotOrderProductEntity;
import com.lichkin.springframework.entities.impl.SysPssStorageEntity;
import com.lichkin.springframework.entities.suppers.PssOrderProductEntity;
import com.lichkin.springframework.services.LKApiBusUpdateWithoutCheckerService;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Service("SysPssAllotOrderU01Service")
public class S extends LKApiBusUpdateWithoutCheckerService<I, SysPssAllotOrderEntity> {

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
	protected void beforeSaveMain(I sin, String locale, String compId, String loginId, SysPssAllotOrderEntity entity) {
		// 校验处理
		QuerySQL sql = new QuerySQL(false, SysPssAllotOrderProductEntity.class);
		sql.eq(SysPssAllotOrderProductR.orderId, entity.getId());
		List<PssOrderProductEntity> orderProductList = dao.getList(sql, PssOrderProductEntity.class);
		// 校验仓库中产品
		int errorCode = stockBusService.checkProductStockOut(entity.getOutStorageId(), orderProductList, entity.getId());
		switch (errorCode) {
			case 1:
				throw new LKRuntimeException(ErrorCodes.PSS_PRODUCT_NOT_IN_STORAGE);
			case 2:
				throw new LKRuntimeException(ErrorCodes.PSS_PRODUCT_STOCKOUT_QUANTITY_OUT_OF_STOCK_QUANTITY);
			default:
			break;
		}

		Map<String, Object> datas = new HashMap<>();
		// 必填
		datas.put("id", entity.getId());
		// 订单统一参数
		datas.put("orderNo", entity.getOrderNo());
		datas.put("billDate", entity.getBillDate());
		datas.put("remarks", entity.getRemarks());
		// 关联表参数转换
		setOrderDatas(datas, entity.getId(), compId);
		// 发起流程
		activitiStartProcessService.startByAdmin(entity, compId, "PSS_ALLOT_ORDER", loginId, sin.getDatas().getUser().getUserName(), LKJsonUtils.toJson(datas));
	}


	private void setOrderDatas(Map<String, Object> datas, String id, String compId) {
		QuerySQL sql = new QuerySQL(SysPssAllotOrderEntity.class);

		// P接口逻辑简化
		// 主表
		sql.select(SysPssAllotOrderR.id);

		// 关联表
		sql.innerJoin(SysPssStorageEntity.class, new Condition(0, SysPssStorageR.id, SysPssAllotOrderR.outStorageId));
		sql.select(0, SysPssStorageR.storageName, "outStorageName");
		sql.innerJoin(SysPssStorageEntity.class, new Condition(1, SysPssStorageR.id, SysPssAllotOrderR.inStorageId));
		sql.select(1, SysPssStorageR.storageName, "inStorageName");

		sql.eq(SysPssAllotOrderR.id, id);

		com.lichkin.application.apis.api50400.P.n00.O out = dao.getOne(sql, com.lichkin.application.apis.api50400.P.n00.O.class);
		datas.put("outStorageName", out.getOutStorageName());
		datas.put("inStorageName", out.getInStorageName());
	}


	@Override
	protected void afterSaveMain(I sin, String locale, String compId, String loginId, SysPssAllotOrderEntity entity, String id) {
		if (ApprovalStatusEnum.APPROVED.equals(entity.getApprovalStatus())) {
			QuerySQL sql = new QuerySQL(false, SysPssAllotOrderProductEntity.class);
			sql.eq(SysPssAllotOrderProductR.orderId, id);
			List<PssOrderProductEntity> orderProductList = dao.getList(sql, PssOrderProductEntity.class);
			// 处理库存
			stockBusService.changeStockQuantityByAllotOrder(entity, orderProductList);
		}
	}

}
