package com.lichkin.springframework.entities.impl;

import javax.persistence.Column;
import javax.persistence.Entity;

import com.lichkin.springframework.entities.suppers.PssOrderProductEntity;

import lombok.Getter;
import lombok.Setter;

/**
 * 盘点订单商品表实体类
 * @author SuZhou LichKin Information Technology Co., Ltd.
 */
@Getter
@Setter
@Entity
public class SysPssStockCheckOrderProductEntity extends PssOrderProductEntity {

	/** serialVersionUID */
	private static final long serialVersionUID = 50016L;

	/** 盘点数量 */
	@Column(nullable = false)
	private int checkQuantity;

	/** 盘盈盘亏数量（盘点数量 - 库存数量） */
	@Column(nullable = false)
	private int differenceQuantity;

}
