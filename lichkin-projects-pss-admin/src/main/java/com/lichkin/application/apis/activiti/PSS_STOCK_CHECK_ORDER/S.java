package com.lichkin.application.apis.activiti.PSS_STOCK_CHECK_ORDER;

import java.util.Map;

import org.springframework.stereotype.Service;

import com.lichkin.defines.PssStatics;
import com.lichkin.framework.db.beans.Condition;
import com.lichkin.framework.db.beans.QuerySQL;
import com.lichkin.framework.db.beans.SysPssStockCheckOrderR;
import com.lichkin.framework.db.beans.SysPssStorageR;
import com.lichkin.springframework.entities.impl.SysPssStockCheckOrderEntity;
import com.lichkin.springframework.entities.impl.SysPssStorageEntity;
import com.lichkin.springframework.services.LKApiBusStartProcessService;

@Service("SysPssStockCheckOrderU01Service")
public class S extends LKApiBusStartProcessService<I, SysPssStockCheckOrderEntity> {

	@Override
	protected void initFormData(I sin, String locale, String compId, String loginId, SysPssStockCheckOrderEntity entity, Map<String, Object> datas) {
		// 订单统一参数
		datas.put("orderNo", entity.getOrderNo());
		datas.put("billDate", entity.getBillDate());
		datas.put("remarks", entity.getRemarks());
		// 关联表参数转换
		setOrderDatas(datas, entity.getId(), compId);
	}


	@Override
	protected String getProcessCode(I sin, String locale, String compId, String loginId, SysPssStockCheckOrderEntity entity) {
		return PssStatics.PSS_STOCK_CHECK_ORDER;
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

}
