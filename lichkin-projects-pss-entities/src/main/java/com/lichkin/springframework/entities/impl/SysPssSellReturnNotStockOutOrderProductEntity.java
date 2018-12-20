package com.lichkin.springframework.entities.impl;

import javax.persistence.Column;
import javax.persistence.Entity;

import com.lichkin.framework.defines.annotations.FieldGenerator;
import com.lichkin.springframework.entities.suppers.PssOrderProductEntity;

import lombok.Getter;
import lombok.Setter;

/**
 * 销售未出库退货单产品表实体类
 * @author SuZhou LichKin Information Technology Co., Ltd.
 */
@Getter
@Setter
@Entity
public class SysPssSellReturnNotStockOutOrderProductEntity extends PssOrderProductEntity {

	/** serialVersionUID */
	private static final long serialVersionUID = 50205L;

	/** 销售单产品ID（SysPssSellOrderProductEntity.id） */
	@FieldGenerator()
	@Column(length = 64, nullable = false)
	private String sellOrderProductId;

	/** 单价 */
	@FieldGenerator(resultColumn = true)
	@Column(length = 10, nullable = false)
	private String unitPrice;

	/** 小计（unitPrice*quantity） */
	@FieldGenerator(resultColumn = true)
	@Column(length = 10, nullable = false)
	private String subTotalPrice;

}
