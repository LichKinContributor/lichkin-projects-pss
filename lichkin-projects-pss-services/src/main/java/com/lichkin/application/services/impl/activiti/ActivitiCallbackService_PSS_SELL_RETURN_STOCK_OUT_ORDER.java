package com.lichkin.application.services.impl.activiti;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lichkin.application.services.ActivitiCallbackService;
import com.lichkin.application.services.WithoutActivitiCallbackService;
import com.lichkin.application.services.bus.impl.SysPssStockBusService;
import com.lichkin.defines.PssStatics;
import com.lichkin.framework.db.beans.QuerySQL;
import com.lichkin.framework.db.beans.SysPssSellReturnStockOutOrderProductR;
import com.lichkin.framework.defines.enums.impl.ApprovalStatusEnum;
import com.lichkin.framework.utils.LKDateTimeUtils;
import com.lichkin.springframework.entities.impl.SysActivitiFormDataEntity;
import com.lichkin.springframework.entities.impl.SysPssSellReturnStockOutOrderEntity;
import com.lichkin.springframework.entities.impl.SysPssSellReturnStockOutOrderProductEntity;
import com.lichkin.springframework.entities.suppers.PssOrderProductEntity;
import com.lichkin.springframework.services.LKDBService;

@Service(PssStatics.PSS_SELL_RETURN_STOCK_OUT_ORDER)
public class ActivitiCallbackService_PSS_SELL_RETURN_STOCK_OUT_ORDER extends LKDBService implements ActivitiCallbackService, WithoutActivitiCallbackService<SysPssSellReturnStockOutOrderEntity> {

	@Autowired
	private SysPssStockBusService stockBusService;


	@Override
	public void directFinish(SysPssSellReturnStockOutOrderEntity processEntity, String compId, String loginId) {
		QuerySQL sql = new QuerySQL(false, SysPssSellReturnStockOutOrderProductEntity.class);
		sql.eq(SysPssSellReturnStockOutOrderProductR.orderId, processEntity.getId());
		List<PssOrderProductEntity> orderProductList = dao.getList(sql, PssOrderProductEntity.class);
		stockBusService.changeStockQuantity(processEntity, orderProductList);
	}


	@Override
	public void approve(SysActivitiFormDataEntity formDataEntity, byte step) {
	}


	@Override
	public void finish(SysActivitiFormDataEntity formDataEntity) {
		SysPssSellReturnStockOutOrderEntity processEntity = dao.findOneById(SysPssSellReturnStockOutOrderEntity.class, formDataEntity.getField1());
		processEntity.setApprovalStatus(ApprovalStatusEnum.APPROVED);
		processEntity.setApprovalTime(LKDateTimeUtils.now());
		dao.mergeOne(processEntity);

		directFinish(processEntity, formDataEntity.getCompId(), formDataEntity.getApproverLoginId());
	}


	@Override
	public void reject(SysActivitiFormDataEntity formDataEntity) {
		SysPssSellReturnStockOutOrderEntity processEntity = dao.findOneById(SysPssSellReturnStockOutOrderEntity.class, formDataEntity.getField1());
		processEntity.setApprovalStatus(ApprovalStatusEnum.REJECT);
		processEntity.setApprovalTime(LKDateTimeUtils.now());
		dao.mergeOne(processEntity);
	}

}
