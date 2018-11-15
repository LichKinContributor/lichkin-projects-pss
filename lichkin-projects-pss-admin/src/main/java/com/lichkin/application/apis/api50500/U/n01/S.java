package com.lichkin.application.apis.api50500.U.n01;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lichkin.application.services.impl.ActivitiStartProcessService;
import com.lichkin.application.services.impl.SysPssStockCheckOrderApprovedService;
import com.lichkin.framework.db.beans.Condition;
import com.lichkin.framework.db.beans.QuerySQL;
import com.lichkin.framework.db.beans.SysPssStockCheckOrderR;
import com.lichkin.framework.db.beans.SysPssStorageR;
import com.lichkin.framework.defines.enums.impl.ApprovalStatusEnum;
import com.lichkin.framework.json.LKJsonUtils;
import com.lichkin.springframework.entities.impl.SysPssStockCheckOrderEntity;
import com.lichkin.springframework.entities.impl.SysPssStorageEntity;
import com.lichkin.springframework.services.LKApiBusUpdateWithoutCheckerService;

@Service("SysPssStockCheckOrderU01Service")
public class S extends LKApiBusUpdateWithoutCheckerService<I, SysPssStockCheckOrderEntity> {

	@Autowired
	private ActivitiStartProcessService activitiStartProcessService;

	@Autowired
	private SysPssStockCheckOrderApprovedService approvedService;


	@Override
	protected void beforeSaveMain(I sin, String locale, String compId, String loginId, SysPssStockCheckOrderEntity entity) {
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
		activitiStartProcessService.startByAdmin(entity, compId, "PSS_STOCK_CHECK_ORDER", loginId, sin.getDatas().getUser().getUserName(), LKJsonUtils.toJson(datas));
	}


	private void setOrderDatas(Map<String, Object> datas, String id, String compId) {
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
			approvedService.changeStockQty(entity);
		}
	}

}
