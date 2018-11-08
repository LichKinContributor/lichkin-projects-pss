package com.lichkin.application.apis.api50500.P.n00;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import com.lichkin.application.utils.LKDictUtils;
import com.lichkin.application.utils.LKDictUtils4Activiti;
import com.lichkin.framework.db.beans.Condition;
import com.lichkin.framework.db.beans.Order;
import com.lichkin.framework.db.beans.QuerySQL;
import com.lichkin.framework.db.beans.SysPssStockCheckOrderR;
import com.lichkin.framework.db.beans.SysPssStorageR;
import com.lichkin.framework.db.enums.LikeType;
import com.lichkin.framework.defines.enums.impl.ApprovalStatusEnum;
import com.lichkin.springframework.entities.impl.SysPssStockCheckOrderEntity;
import com.lichkin.springframework.entities.impl.SysPssStorageEntity;
import com.lichkin.springframework.services.LKApiBusGetPageService;

@Service("SysPssStockCheckOrderP00Service")
public class S extends LKApiBusGetPageService<I, O, SysPssStockCheckOrderEntity> {

	@Override
	protected void initSQL(I sin, String locale, String compId, String loginId, QuerySQL sql) {
		// 主表
		sql.select(SysPssStockCheckOrderR.id);
		sql.select(SysPssStockCheckOrderR.insertTime);
		sql.select(SysPssStockCheckOrderR.approvalTime);
		sql.select(SysPssStockCheckOrderR.orderNo);
		sql.select(SysPssStockCheckOrderR.billDate);
		sql.select(SysPssStockCheckOrderR.stockCheckCount);

		// 关联表
		sql.innerJoin(SysPssStorageEntity.class, new Condition(SysPssStockCheckOrderR.storageId, SysPssStorageR.id));
		sql.select(SysPssStorageR.storageName);

		// 字典表
		int i = 0;
		LKDictUtils.usingStatus(sql, SysPssStockCheckOrderR.usingStatus, i++);
		LKDictUtils4Activiti.approvalStatus(sql, SysPssStockCheckOrderR.approvalStatus, i++);

		// 筛选条件（必填项）
		// 公司ID
		addConditionCompId(false, sql, SysPssStockCheckOrderR.compId, compId, sin.getCompId());
		// 在用状态
		addConditionUsingStatus(sql, SysPssStockCheckOrderR.usingStatus, compId, sin.getUsingStatus());

		// 筛选条件（业务项）
		ApprovalStatusEnum approvalStatus = sin.getApprovalStatus();
		if (approvalStatus != null) {
			sql.eq(SysPssStockCheckOrderR.approvalStatus, approvalStatus);
		}

		String orderNo = sin.getOrderNo();
		if (StringUtils.isNotBlank(orderNo)) {
			sql.like(SysPssStockCheckOrderR.orderNo, LikeType.ALL, orderNo);
		}

		String storageId = sin.getStorageId();
		if (StringUtils.isNotBlank(storageId)) {
			sql.eq(SysPssStockCheckOrderR.storageId, storageId);
		}

		String startDate = sin.getStartDate();
		if (StringUtils.isNotBlank(startDate)) {
			sql.gte(SysPssStockCheckOrderR.billDate, startDate);
		}

		String endDate = sin.getEndDate();
		if (StringUtils.isNotBlank(endDate)) {
			sql.lte(SysPssStockCheckOrderR.billDate, endDate);
		}

		// 排序条件
		sql.addOrders(new Order(SysPssStockCheckOrderR.id, false));
	}

}
