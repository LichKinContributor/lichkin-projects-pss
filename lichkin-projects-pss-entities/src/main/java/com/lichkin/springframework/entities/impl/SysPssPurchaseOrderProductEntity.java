package com.lichkin.springframework.entities.impl;

import javax.persistence.Column;
import javax.persistence.Entity;

import com.lichkin.framework.defines.annotations.ClassGenerator;
import com.lichkin.framework.defines.annotations.FieldGenerator;
import com.lichkin.framework.defines.annotations.InsertCheckType;
import com.lichkin.framework.defines.annotations.UpdateCheckType;
import com.lichkin.springframework.entities.suppers.PssOrderProductEntity;

import lombok.Getter;
import lombok.Setter;

/**
 * 采购订单产品表实体类
 * @author SuZhou LichKin Information Technology Co., Ltd.
 */
@Getter
@Setter
@Entity
@ClassGenerator(

		afterSaveMain = false

		, insertCheckType = InsertCheckType.UNCHECK

		, updateCheckType = UpdateCheckType.UNCHECK

		, pageResultColumns = {

				"String id 产品ID SysPssProductR"

				, "String productCode 产品编码 SysPssProductR"

				, "String productName 产品名称 SysPssProductR"

				, "String barcode 条形码 SysPssProductR"

				, "String unit 单位 SysPssProductR"

		}

)
public class SysPssPurchaseOrderProductEntity extends PssOrderProductEntity {

	/** serialVersionUID */
	private static final long serialVersionUID = 50101L;

	/** 已入库数量 */
	@FieldGenerator(resultColumn = true)
	@Column(nullable = false)
	private int inventoryQuantity;

	/** 已退货数量 */
	@FieldGenerator(resultColumn = true)
	@Column(nullable = false)
	private int returnedQuantity;

	/** 单价 */
	@FieldGenerator(resultColumn = true)
	@Column(length = 10, nullable = false)
	private String unitPrice;

	/** 小计（unitPrice*quantity） */
	@FieldGenerator(resultColumn = true)
	@Column(length = 10, nullable = false)
	private String subTotalPrice;

}
