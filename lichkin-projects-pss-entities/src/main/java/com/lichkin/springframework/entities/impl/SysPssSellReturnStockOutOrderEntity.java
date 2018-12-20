package com.lichkin.springframework.entities.impl;

import javax.persistence.Column;
import javax.persistence.Entity;

import com.lichkin.framework.defines.annotations.FieldGenerator;
import com.lichkin.framework.defines.annotations.InsertType;
import com.lichkin.springframework.entities.suppers.PssStockOrderEntity;

import lombok.Getter;
import lombok.Setter;

/**
 * 销售已出库退货单表实体类
 * @author SuZhou LichKin Information Technology Co., Ltd.
 */
@Getter
@Setter
@Entity
public class SysPssSellReturnStockOutOrderEntity extends PssStockOrderEntity {

	/** serialVersionUID */
	private static final long serialVersionUID = 50206L;

	/** 退货人ID（SysEmployeeEntity.id） */
	@FieldGenerator(resultColumn = false, insertType = InsertType.COPY_COPY, updateable = false)
	@Column(length = 64, nullable = false)
	private String salesId;

	/** 订单金额 */
	@FieldGenerator(resultColumn = true, insertType = InsertType.HANDLE_HANDLE)
	@Column(length = 10, nullable = false)
	private String orderAmount;

}
