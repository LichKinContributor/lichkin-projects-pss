package com.lichkin.springframework.entities.impl;

import javax.persistence.Column;
import javax.persistence.Entity;

import com.lichkin.framework.defines.annotations.FieldGenerator;
import com.lichkin.springframework.entities.suppers.PssOrderProductEntity;

import lombok.Getter;
import lombok.Setter;

/**
 * 采购未入库退货订单产品表实体类
 * @author SuZhou LichKin Information Technology Co., Ltd.
 */
@Getter
@Setter
@Entity
public class SysPssPurchaseReturnNotStockInOrderProductEntity extends PssOrderProductEntity {

	/** serialVersionUID */
	private static final long serialVersionUID = 50601L;

	/** 采购单产品ID（SysPssPurchaseOrderProductEntity.id） */
	@Column(length = 64, nullable = false)
	private String purchaseOrderProductId;

	/** 单价 */
	@FieldGenerator(resultColumn = true)
	@Column(length = 10, nullable = false)
	private String unitPrice;

	/** 小计（unitPrice*quantity） */
	@FieldGenerator(resultColumn = true)
	@Column(length = 10, nullable = false)
	private String subTotalPrice;

}
