package com.lichkin.application.services.impl.activiti;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lichkin.application.services.ActivitiCallbackService;
import com.lichkin.application.services.WithoutActivitiCallbackService;
import com.lichkin.application.services.bus.impl.SysPssPurchaseOrderBusService;
import com.lichkin.defines.PssStatics;
import com.lichkin.framework.db.beans.QuerySQL;
import com.lichkin.framework.db.beans.SysPssPurchaseReturnNotStockInOrderProductR;
import com.lichkin.framework.defines.enums.impl.ApprovalStatusEnum;
import com.lichkin.framework.utils.LKDateTimeUtils;
import com.lichkin.springframework.entities.impl.SysActivitiFormDataEntity;
import com.lichkin.springframework.entities.impl.SysPssPurchaseReturnNotStockInOrderEntity;
import com.lichkin.springframework.entities.impl.SysPssPurchaseReturnNotStockInOrderProductEntity;
import com.lichkin.springframework.services.LKDBService;

@Service(PssStatics.PSS_PURCHASE_RETURN_NOT_STOCK_IN_ORDER)
public class ActivitiCallbackService_PSS_PURCHASE_RETURN_NOT_STOCK_IN_ORDER extends LKDBService implements ActivitiCallbackService, WithoutActivitiCallbackService<SysPssPurchaseReturnNotStockInOrderEntity> {

	@Autowired
	private SysPssPurchaseOrderBusService purchaseOrderBusService;


	@Override
	public void directFinish(SysPssPurchaseReturnNotStockInOrderEntity processEntity, String compId, String loginId) {
		QuerySQL sql = new QuerySQL(false, SysPssPurchaseReturnNotStockInOrderProductEntity.class);
		sql.eq(SysPssPurchaseReturnNotStockInOrderProductR.orderId, processEntity.getId());
		List<SysPssPurchaseReturnNotStockInOrderProductEntity> stockOrderProductList = dao.getList(sql, SysPssPurchaseReturnNotStockInOrderProductEntity.class);
		purchaseOrderBusService.changePurchaseOrderProductReturnedQuantity(processEntity, stockOrderProductList);
	}


	@Override
	public void approve(SysActivitiFormDataEntity formDataEntity, byte step) {
	}


	@Override
	public void finish(SysActivitiFormDataEntity formDataEntity) {
		SysPssPurchaseReturnNotStockInOrderEntity processEntity = dao.findOneById(SysPssPurchaseReturnNotStockInOrderEntity.class, formDataEntity.getField1());
		processEntity.setApprovalStatus(ApprovalStatusEnum.APPROVED);
		processEntity.setApprovalTime(LKDateTimeUtils.now());
		dao.mergeOne(processEntity);

		directFinish(processEntity, formDataEntity.getCompId(), formDataEntity.getApproverLoginId());
	}


	@Override
	public void reject(SysActivitiFormDataEntity formDataEntity) {
		SysPssPurchaseReturnNotStockInOrderEntity processEntity = dao.findOneById(SysPssPurchaseReturnNotStockInOrderEntity.class, formDataEntity.getField1());
		processEntity.setApprovalStatus(ApprovalStatusEnum.REJECT);
		processEntity.setApprovalTime(LKDateTimeUtils.now());
		dao.mergeOne(processEntity);
	}

}
