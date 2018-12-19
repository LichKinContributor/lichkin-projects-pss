package com.lichkin.springframework.entities.impl;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;

import com.lichkin.framework.defines.annotations.ClassGenerator;
import com.lichkin.framework.defines.annotations.FieldGenerator;
import com.lichkin.framework.defines.annotations.InsertCheckType;
import com.lichkin.framework.defines.annotations.InsertType;
import com.lichkin.framework.defines.annotations.UpdateCheckType;
import com.lichkin.framework.defines.enums.impl.InventoryStatusEnum;
import com.lichkin.springframework.entities.suppers.PssOrderEntity;

import lombok.Getter;
import lombok.Setter;

/**
 * 采购退货订单表实体类
 * @author SuZhou LichKin Information Technology Co., Ltd.
 */
@Getter
@Setter
@Entity
@ClassGenerator(

		afterSaveMain = false, IUSubTables = { "SysPssPurchaseReturnOrderProductEntity" }

		, insertCheckType = InsertCheckType.UNCHECK

		, updateCheckType = UpdateCheckType.UNCHECK

		, pageResultColumns = {

				"String supplierName 供应商名称 SysPssSupplierR"

				, "String purchaserName 采购人姓名 SysEmployeeR"

		}

		, pageQueryConditions = {

				"String supplierName 供应商名称 SysPssSupplierR"

				, "String purchaserName 采购人姓名 SysEmployeeR"

				, "String startDate 开始日期 #entityR"

				, "String endDate 结束日期 #entityR"

		}

		, insertFields = {

				"String productList 产品列表"

				, "List<SysPssPurchaseReturnOrderProductEntity> listProduct 产品列表"

		}

		, updateFields = {

				"String productList 产品列表"

				, "List<SysPssPurchaseReturnOrderProductEntity> listProduct 产品列表"

		}

)
public class SysPssPurchaseReturnOrderEntity extends PssOrderEntity {

	/** serialVersionUID */
	private static final long serialVersionUID = 50600L;

	/** 供应商ID（SysPssSupplierEntity.id） */
	@FieldGenerator(resultColumn = false, insertType = InsertType.COPY_COPY, updateable = false)
	@Column(length = 64, nullable = false)
	private String supplierId;

	/** 退货人ID（SysEmployeeEntity.id） */
	@FieldGenerator(resultColumn = false, insertType = InsertType.COPY_COPY, updateable = false)
	@Column(length = 64)
	private String purchaserId;

	/** 订单金额 */
	@FieldGenerator(resultColumn = true, insertType = InsertType.HANDLE_HANDLE)
	@Column(length = 10, nullable = false)
	private String orderAmount;

	/** 订单类型（true:已入库退货;false:未入库退货;） */
	@FieldGenerator(resultColumn = false, insertType = InsertType.COPY_RETAIN, updateable = false, queryCondition = true, queryConditionLike = false)
	@Column(nullable = false)
	private Boolean orderType;

	/** 采购单ID（SysPssPurchaseOrderEntity.id 未入库退货时才有值） */
	@FieldGenerator(resultColumn = false, insertType = InsertType.COPY_COPY, updateable = false)
	@Column(length = 64)
	private String orderId;

	/** 出库状态（枚举 已入库退货时才有值） */
	@Enumerated(EnumType.STRING)
	@FieldGenerator(resultColumn = true, insertType = InsertType.DEFAULT_DEFAULT, updateable = false, queryCondition = true, queryConditionLike = false)
	@Column(length = 4)
	private InventoryStatusEnum inventoryStatus;

}
