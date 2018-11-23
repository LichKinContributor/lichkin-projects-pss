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
 * 采购库存单产品表实体类
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
public class SysPssPurchaseStockOrderProductEntity extends PssOrderProductEntity {

	/** serialVersionUID */
	private static final long serialVersionUID = 50103L;

	/** 采购单产品ID（SysPssPurchaseOrderProductEntity.id） */
	@FieldGenerator()
	@Column(length = 64, nullable = false)
	private String purchaseOrderProductId;

}
