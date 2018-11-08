package com.lichkin.application.apis.api50500.U.n01;

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
import com.lichkin.framework.db.beans.SysPssStockCheckOrderR;
import com.lichkin.framework.db.beans.SysPssStorageR;
import com.lichkin.framework.defines.enums.LKCodeEnum;
import com.lichkin.framework.defines.enums.impl.ApprovalStatusEnum;
import com.lichkin.framework.json.LKJsonUtils;
import com.lichkin.springframework.entities.impl.SysPssAllotOrderProductEntity;
import com.lichkin.springframework.entities.impl.SysPssStockCheckOrderEntity;
import com.lichkin.springframework.entities.impl.SysPssStorageEntity;
import com.lichkin.springframework.entities.suppers.PssOrderProductEntity;
import com.lichkin.springframework.services.LKApiBusUpdateWithoutCheckerService;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Service("SysPssStockCheckOrderU01Service")
public class S extends LKApiBusUpdateWithoutCheckerService<I, SysPssStockCheckOrderEntity> {

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
	protected void beforeSaveMain(I sin, String locale, String compId, String loginId, SysPssStockCheckOrderEntity entity) {
		// TODO 校验处理

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
		activitiStartProcessService.startByAdmin(entity, compId, "PSS_ALLOT_ORDER", loginId, sin.getDatas().getUser().getUserName(), LKJsonUtils.toJson(datas));
	}


	private void setPurchaseOrderDatas(Map<String, Object> datas, String id, String compId) {
		QuerySQL sql = new QuerySQL(SysPssStockCheckOrderEntity.class);

		// P接口逻辑简化
		// 主表
		sql.select(SysPssStockCheckOrderR.id);

		// 关联表
		sql.innerJoin(SysPssStorageEntity.class, new Condition(SysPssStorageR.id, SysPssStockCheckOrderR.storageId));
		sql.select(SysPssStorageR.storageName, "storageName");

		sql.eq(SysPssStockCheckOrderR.id, id);

		com.lichkin.application.apis.api50500.P.n00.O out = dao.getOne(sql, com.lichkin.application.apis.api50500.P.n00.O.class);
		datas.put("storageName", out.getStorageName());
	}


	@Override
	protected void afterSaveMain(I sin, String locale, String compId, String loginId, SysPssStockCheckOrderEntity entity, String id) {
		if (ApprovalStatusEnum.APPROVED.equals(entity.getApprovalStatus())) {
			QuerySQL sql = new QuerySQL(false, SysPssAllotOrderProductEntity.class);
			sql.eq(SysPssAllotOrderProductR.orderId, id);
			List<PssOrderProductEntity> orderProductList = dao.getList(sql, PssOrderProductEntity.class);
			// TODO 处理库存
		}
	}

}
