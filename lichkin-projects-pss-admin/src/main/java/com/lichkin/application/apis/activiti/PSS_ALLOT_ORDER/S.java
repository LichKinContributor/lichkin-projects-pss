package com.lichkin.application.apis.activiti.PSS_ALLOT_ORDER;

import java.util.Map;

import org.springframework.stereotype.Service;

import com.lichkin.defines.PssStatics;
import com.lichkin.framework.db.beans.Condition;
import com.lichkin.framework.db.beans.QuerySQL;
import com.lichkin.framework.db.beans.SysPssAllotOrderR;
import com.lichkin.framework.db.beans.SysPssStorageR;
import com.lichkin.springframework.entities.impl.SysPssAllotOrderEntity;
import com.lichkin.springframework.entities.impl.SysPssStorageEntity;
import com.lichkin.springframework.services.LKApiBusStartProcessService;

@Service("SysPssAllotOrderU01Service")
public class S extends LKApiBusStartProcessService<I, SysPssAllotOrderEntity> {

	@Override
	protected void initFormData(I sin, String locale, String compId, String loginId, SysPssAllotOrderEntity entity, Map<String, Object> datas) {
		// 订单统一参数
		datas.put("orderNo", entity.getOrderNo());
		datas.put("billDate", entity.getBillDate());
		datas.put("remarks", entity.getRemarks());
		// 关联表参数转换
		setOrderDatas(datas, entity.getId(), compId);
	}


	@Override
	protected String getProcessCode(I sin, String locale, String compId, String loginId, SysPssAllotOrderEntity entity) {
		return PssStatics.PSS_ALLOT_ORDER;
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

}
