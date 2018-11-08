package com.lichkin.springframework.entities.impl;

import javax.persistence.Column;
import javax.persistence.Entity;

import com.lichkin.framework.defines.annotations.ClassGenerator;
import com.lichkin.framework.defines.annotations.FieldGenerator;
import com.lichkin.framework.defines.annotations.InsertCheckType;
import com.lichkin.framework.defines.annotations.InsertType;
import com.lichkin.framework.defines.annotations.UpdateCheckType;
import com.lichkin.springframework.entities.suppers.PssStockOrderEntity;

import lombok.Getter;
import lombok.Setter;

/**
 * 采购库存单表实体类
 * @author SuZhou LichKin Information Technology Co., Ltd.
 */
@Getter
@Setter
@Entity
@ClassGenerator(

		afterSaveMain = false, IUSubTables = { "SysPssPurchaseStockOrderProductEntity" }

		, insertCheckType = InsertCheckType.UNCHECK

		, updateCheckType = UpdateCheckType.UNCHECK

		, pageResultColumns = {

				"String purchaseOrderNo 采购订单号 SysPssPurchaseOrderR"

				, "String purchaserBillDate 采购订单日期 SysPssPurchaseOrderR"

				, "String purchaseOrderAmount 采购订单金额 SysPssPurchaseOrderR"

				, "String supplierName 供应商名称 SysPssSupplierR"

				, "String purchaserName 采购人姓名 SysEmployeeR"

		}

		, pageQueryConditions = {

				"String purchaseOrderNo 采购订单号 SysPssPurchaseOrderR"

				, "String supplierName 供应商名称 SysPssSupplierR"

				, "String purchaserName 采购人姓名 SysEmployeeR"

				, "String startDate 开始日期 #entityR"

				, "String endDate 结束日期 #entityR"

		}

		, insertFields = {

				"String productList 产品列表"

		}

		, updateFields = {

				"String productList 产品列表"

		}

)
public class SysPssPurchaseStockOrderEntity extends PssStockOrderEntity {

	/** serialVersionUID */
	private static final long serialVersionUID = 50102L;

	/** 采购单ID（SysPssPurchaseOrderEntity.id） */
	@FieldGenerator(resultColumn = false, insertType = InsertType.COPY_COPY, updateable = false)
	@Column(length = 64, nullable = false)
	private String orderId;

}
