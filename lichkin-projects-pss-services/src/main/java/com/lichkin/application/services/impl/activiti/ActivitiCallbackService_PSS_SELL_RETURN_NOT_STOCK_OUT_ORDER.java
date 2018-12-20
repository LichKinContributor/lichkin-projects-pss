package com.lichkin.application.services.impl.activiti;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lichkin.application.services.ActivitiCallbackService;
import com.lichkin.application.services.WithoutActivitiCallbackService;
import com.lichkin.application.services.bus.impl.SysPssSellOrderBusService;
import com.lichkin.defines.PssStatics;
import com.lichkin.framework.db.beans.QuerySQL;
import com.lichkin.framework.db.beans.SysPssSellReturnNotStockOutOrderProductR;
import com.lichkin.framework.defines.enums.impl.ApprovalStatusEnum;
import com.lichkin.framework.utils.LKDateTimeUtils;
import com.lichkin.springframework.entities.impl.SysActivitiFormDataEntity;
import com.lichkin.springframework.entities.impl.SysPssSellReturnNotStockOutOrderEntity;
import com.lichkin.springframework.entities.impl.SysPssSellReturnNotStockOutOrderProductEntity;
import com.lichkin.springframework.services.LKDBService;

@Service(PssStatics.PSS_SELL_RETURN_NOT_STOCK_OUT_ORDER)
public class ActivitiCallbackService_PSS_SELL_RETURN_NOT_STOCK_OUT_ORDER extends LKDBService implements ActivitiCallbackService, WithoutActivitiCallbackService<SysPssSellReturnNotStockOutOrderEntity> {

	@Autowired
	private SysPssSellOrderBusService sellOrderBusService;


	@Override
	public void directFinish(SysPssSellReturnNotStockOutOrderEntity processEntity, String compId, String loginId) {
		QuerySQL sql = new QuerySQL(false, SysPssSellReturnNotStockOutOrderProductEntity.class);
		sql.eq(SysPssSellReturnNotStockOutOrderProductR.orderId, processEntity.getId());
		List<SysPssSellReturnNotStockOutOrderProductEntity> orderProductList = dao.getList(sql, SysPssSellReturnNotStockOutOrderProductEntity.class);
		sellOrderBusService.changeSellOrderProductReturnedQuantity(processEntity, orderProductList);
	}


	@Override
	public void approve(SysActivitiFormDataEntity formDataEntity, byte step) {
	}


	@Override
	public void finish(SysActivitiFormDataEntity formDataEntity) {
		SysPssSellReturnNotStockOutOrderEntity processEntity = dao.findOneById(SysPssSellReturnNotStockOutOrderEntity.class, formDataEntity.getField1());
		processEntity.setApprovalStatus(ApprovalStatusEnum.APPROVED);
		processEntity.setApprovalTime(LKDateTimeUtils.now());
		dao.mergeOne(processEntity);

		directFinish(processEntity, formDataEntity.getCompId(), formDataEntity.getApproverLoginId());
	}


	@Override
	public void reject(SysActivitiFormDataEntity formDataEntity) {
		SysPssSellReturnNotStockOutOrderEntity processEntity = dao.findOneById(SysPssSellReturnNotStockOutOrderEntity.class, formDataEntity.getField1());
		processEntity.setApprovalStatus(ApprovalStatusEnum.REJECT);
		processEntity.setApprovalTime(LKDateTimeUtils.now());
		dao.mergeOne(processEntity);
	}

}
