package com.lichkin.application.apis.api50102.U.n01;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lichkin.application.services.bus.impl.SysPssPurchaseOrderBusService;
import com.lichkin.application.services.bus.impl.SysPssStockBusService;
import com.lichkin.application.services.impl.ActivitiStartProcessService;
import com.lichkin.framework.db.beans.Condition;
import com.lichkin.framework.db.beans.QuerySQL;
import com.lichkin.framework.db.beans.SysEmployeeR;
import com.lichkin.framework.db.beans.SysPssPurchaseOrderR;
import com.lichkin.framework.db.beans.SysPssPurchaseStockOrderProductR;
import com.lichkin.framework.db.beans.SysPssSupplierR;
import com.lichkin.framework.defines.enums.impl.ApprovalStatusEnum;
import com.lichkin.framework.json.LKJsonUtils;
import com.lichkin.springframework.entities.impl.SysEmployeeEntity;
import com.lichkin.springframework.entities.impl.SysPssPurchaseOrderEntity;
import com.lichkin.springframework.entities.impl.SysPssPurchaseStockOrderEntity;
import com.lichkin.springframework.entities.impl.SysPssPurchaseStockOrderProductEntity;
import com.lichkin.springframework.entities.impl.SysPssSupplierEntity;
import com.lichkin.springframework.entities.suppers.PssOrderProductEntity;
import com.lichkin.springframework.services.LKApiBusUpdateWithoutCheckerService;

@Service("SysPssPurchaseStockOrderU01Service")
public class S extends LKApiBusUpdateWithoutCheckerService<I, SysPssPurchaseStockOrderEntity> {

	@Autowired
	private ActivitiStartProcessService activitiStartProcessService;

	@Autowired
	private SysPssStockBusService stockBusService;

	@Autowired
	private SysPssPurchaseOrderBusService purchaseOrderBusService;


	@Override
	protected void beforeSaveMain(I sin, String locale, String compId, String loginId, SysPssPurchaseStockOrderEntity entity) {
		Map<String, Object> datas = new HashMap<>();
		// 必填
		datas.put("id", entity.getId());
		// 订单统一参数
		datas.put("orderNo", entity.getOrderNo());
		datas.put("billDate", entity.getBillDate());
		datas.put("remarks", entity.getRemarks());
		// 关联表参数转换
		setPurchaseOrderDatas(datas, entity.getOrderId());
		// 发起流程
		activitiStartProcessService.startByAdmin(entity, compId, entity.getOrderType() ? "PSS_PURCHASE_STOCK_IN_ORDER" : "PSS_PURCHASE_STOCK_OUT_ORDER", loginId, sin.getDatas().getUser().getUserName(), LKJsonUtils.toJson(datas));
	}


	private void setPurchaseOrderDatas(Map<String, Object> datas, String orderId) {
		QuerySQL sql = new QuerySQL(SysPssPurchaseOrderEntity.class);

		// P接口逻辑简化
		// 主表
		sql.select(SysPssPurchaseOrderR.orderNo);
		sql.select(SysPssPurchaseOrderR.billDate);
		sql.select(SysPssPurchaseOrderR.orderAmount);

		// 关联表
		sql.innerJoin(SysPssSupplierEntity.class, new Condition(SysPssPurchaseOrderR.supplierId, SysPssSupplierR.id));
		sql.select(SysPssSupplierR.supplierName);
		sql.innerJoin(SysEmployeeEntity.class, new Condition(SysPssPurchaseOrderR.purchaserId, SysEmployeeR.id));
		sql.select(SysEmployeeR.userName, "purchaserName");

		sql.eq(SysPssPurchaseOrderR.id, orderId);

		// 查询结果
		com.lichkin.application.apis.api50100.P.n00.O o = dao.getOne(sql, com.lichkin.application.apis.api50100.P.n00.O.class);

		// 设置值
		datas.put("purchaseOrderNo", o.getOrderNo());
		datas.put("purchaseBillDate", o.getBillDate());
		datas.put("purchaseOrderAmount", o.getOrderAmount());
		datas.put("supplierName", o.getSupplierName());
		datas.put("purchaserName", o.getPurchaserName());
	}


	@Override
	protected void afterSaveMain(I sin, String locale, String compId, String loginId, SysPssPurchaseStockOrderEntity entity, String id) {
		if (ApprovalStatusEnum.APPROVED.equals(entity.getApprovalStatus())) {
			// 改库存
			QuerySQL sql = new QuerySQL(false, SysPssPurchaseStockOrderProductEntity.class);
			sql.eq(SysPssPurchaseStockOrderProductR.orderId, id);
			List<PssOrderProductEntity> orderProductList = dao.getList(sql, PssOrderProductEntity.class);
			stockBusService.changeStockQuantity(entity, orderProductList);
			if (entity.getOrderType().equals(Boolean.TRUE)) {
				// 修改采购单入库数量及状态
				purchaseOrderBusService.changePurchaseOrderProductInventoryQuantity(entity.getOrderId(), orderProductList);
			}

		}
	}

}
