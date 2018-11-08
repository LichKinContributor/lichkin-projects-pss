package com.lichkin.springframework.entities.impl;

import javax.persistence.Column;
import javax.persistence.Entity;

import com.lichkin.springframework.entities.suppers.PssOrderEntity;

import lombok.Getter;
import lombok.Setter;

/**
 * 盘点订单表实体类
 * @author SuZhou LichKin Information Technology Co., Ltd.
 */
@Getter
@Setter
@Entity
public class SysPssStockCheckOrderEntity extends PssOrderEntity {

	/** serialVersionUID */
	private static final long serialVersionUID = 50015L;

	/** 仓库ID（SysPssStorageEntity.id） */
	@Column(length = 64, nullable = false)
	private String storageId;

	/** 盘点产品数 */
	@Column(nullable = false)
	private int stockCheckCount;

}
