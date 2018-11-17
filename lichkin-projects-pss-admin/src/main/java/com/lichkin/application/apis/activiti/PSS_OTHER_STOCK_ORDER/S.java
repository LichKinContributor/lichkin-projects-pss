package com.lichkin.application.apis.activiti.PSS_OTHER_STOCK_ORDER;

import java.util.Map;

import org.springframework.stereotype.Service;

import com.lichkin.application.utils.LKDictUtils4Pss;
import com.lichkin.defines.PssStatics;
import com.lichkin.framework.db.beans.Condition;
import com.lichkin.framework.db.beans.QuerySQL;
import com.lichkin.framework.db.beans.SysPssOtherStockOrderR;
import com.lichkin.framework.db.beans.SysPssStorageR;
import com.lichkin.springframework.entities.impl.SysPssOtherStockOrderEntity;
import com.lichkin.springframework.entities.impl.SysPssStorageEntity;
import com.lichkin.springframework.services.LKApiBusStartProcessService;

@Service("SysPssOtherStockOrderU01Service")
public class S extends LKApiBusStartProcessService<I, SysPssOtherStockOrderEntity> {

	@Override
	protected void initFormData(I sin, String locale, String compId, String loginId, SysPssOtherStockOrderEntity entity, Map<String, Object> datas) {
		// 订单统一参数
		datas.put("orderNo", entity.getOrderNo());
		datas.put("billDate", entity.getBillDate());
		datas.put("remarks", entity.getRemarks());
		// 关联表参数转换
		setOrderDatas(datas, entity.getId(), compId);
	}


	@Override
	protected String getProcessCode(I sin, String locale, String compId, String loginId, SysPssOtherStockOrderEntity entity) {
		return entity.getOrderType() ? PssStatics.PSS_OTHER_STOCK_IN_ORDER : PssStatics.PSS_OTHER_STOCK_OUT_ORDER;
	}


	private void setOrderDatas(Map<String, Object> datas, String id, String compId) {
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

}
