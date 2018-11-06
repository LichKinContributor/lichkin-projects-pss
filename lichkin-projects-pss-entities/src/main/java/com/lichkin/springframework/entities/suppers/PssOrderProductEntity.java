package com.lichkin.springframework.entities.suppers;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;

import com.lichkin.framework.defines.annotations.DefaultByteValue;

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

	/** 订单ID（SysPssAllotOrderEntity.id） */
	@Column(length = 64, nullable = false)
	private String orderId;

	/** 产品ID（SysPssProductEntity.id） */
	@Column(length = 64, nullable = false)
	private String productId;

	/** 产品数量 */
	@Column(nullable = false)
	private int quantity;

	/** 排序号 */
	@DefaultByteValue
	@Column(nullable = false)
	private Integer sortId;

}
