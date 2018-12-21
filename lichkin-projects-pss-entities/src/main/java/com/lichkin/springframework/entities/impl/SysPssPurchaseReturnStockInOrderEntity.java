package com.lichkin.springframework.entities.impl;

import javax.persistence.Column;
import javax.persistence.Entity;

import com.lichkin.framework.defines.annotations.FieldGenerator;
import com.lichkin.framework.defines.annotations.InsertType;
import com.lichkin.springframework.entities.suppers.PssStockOrderEntity;

import lombok.Getter;
import lombok.Setter;

/**
 * 采购已入库退货订单表实体类
 * @author SuZhou LichKin Information Technology Co., Ltd.
 */
@Getter
@Setter
@Entity
public class SysPssPurchaseReturnStockInOrderEntity extends PssStockOrderEntity {

	/** serialVersionUID */
	private static final long serialVersionUID = 50700L;

	/** 供应商ID（SysPssSupplierEntity.id） */
	@FieldGenerator(resultColumn = false, insertType = InsertType.COPY_COPY, updateable = false)
	@Column(length = 64, nullable = false)
	private String supplierId;

	/** 退货员ID（SysEmployeeEntity.id） */
	@FieldGenerator(resultColumn = false, insertType = InsertType.COPY_COPY, updateable = false)
	@Column(length = 64)
	private String purchaserId;

	/** 订单金额 */
	@FieldGenerator(resultColumn = true, insertType = InsertType.HANDLE_HANDLE)
	@Column(length = 10, nullable = false)
	private String orderAmount;

}
