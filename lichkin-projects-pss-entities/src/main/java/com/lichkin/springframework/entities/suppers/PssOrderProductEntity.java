package com.lichkin.springframework.entities.suppers;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;

import com.lichkin.framework.defines.annotations.DefaultByteValue;
import com.lichkin.framework.defines.annotations.FieldGenerator;

import lombok.Getter;
import lombok.Setter;

/**
 * 订单表实体类
 * @author SuZhou LichKin Information Technology Co., Ltd.
 */
@Getter
@Setter
@MappedSuperclass
public abstract class PssOrderProductEntity extends IDEntity {

	/** serialVersionUID */
	private static final long serialVersionUID = 1L;

	/** 订单ID（对应订单实体类.id） */
	@FieldGenerator(queryCondition = true, queryConditionLike = false)
	@Column(length = 64, nullable = false)
	private String orderId;

	/** 产品ID（SysPssProductEntity.id） */
	@FieldGenerator()
	@Column(length = 64, nullable = false)
	private String productId;

	/** 产品数量 */
	@FieldGenerator(resultColumn = true)
	@Column(nullable = false)
	private int quantity;

	/** 排序号 */
	@DefaultByteValue
	@FieldGenerator()
	@Column(nullable = false)
	private Integer sortId;

}
