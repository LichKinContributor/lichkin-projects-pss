package com.lichkin.springframework.entities.suppers;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;

import com.lichkin.framework.defines.annotations.FieldGenerator;
import com.lichkin.framework.defines.annotations.InsertType;

import lombok.Getter;
import lombok.Setter;

/**
 * 库存单表实体类
 * @author SuZhou LichKin Information Technology Co., Ltd.
 */
@Getter
@Setter
@MappedSuperclass
public abstract class PssStockOrderEntity extends PssOrderEntity {

	/** serialVersionUID */
	private static final long serialVersionUID = 1L;

	/** 仓库ID（SysPssStorageEntity.id） */
	@FieldGenerator(resultColumn = false, queryCondition = true, queryConditionLike = false, insertType = InsertType.COPY_COPY, updateable = false)
	@Column(length = 64, nullable = false)
	private String storageId;

	/** 订单类型（true:入库;false:出库;） */
	@FieldGenerator(resultColumn = false, insertType = InsertType.COPY_RETAIN, updateable = false, queryCondition = true, queryConditionLike = false)
	@Column(nullable = false)
	private Boolean orderType;

}
