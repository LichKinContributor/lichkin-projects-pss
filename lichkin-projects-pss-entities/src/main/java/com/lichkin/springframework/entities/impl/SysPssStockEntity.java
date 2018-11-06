package com.lichkin.springframework.entities.impl;

import javax.persistence.Column;
import javax.persistence.Entity;

import com.lichkin.framework.defines.annotations.ClassGenerator;
import com.lichkin.framework.defines.annotations.FieldGenerator;
import com.lichkin.framework.defines.annotations.InsertCheckType;
import com.lichkin.framework.defines.annotations.UpdateCheckType;
import com.lichkin.springframework.entities.suppers.CompIDEntity;

import lombok.Getter;
import lombok.Setter;

/**
 * 商品库存表实体类
 * @author SuZhou LichKin Information Technology Co., Ltd.
 */
@Getter
@Setter
@Entity
@ClassGenerator(

		afterSaveMain = false, IUSubTables = {}

		, insertCheckType = InsertCheckType.FORCE_CHECK

		, updateCheckType = UpdateCheckType.UNCHECK

		, pageResultColumns = {

				"String storageName 仓库名称 SysPssStorageR"

				, "String storageCode 仓库编码 SysPssStorageR"

				, "String productName 产品名称 SysPssProductR"

				, "String productCode 产品编码 SysPssProductR"

				, "String barcode 条形码 SysPssProductR"

		}

		, pageQueryConditions = {

				"String storageName 仓库名称 SysPssStorageR"

				, "String storageCode 仓库编码 SysPssStorageR"

				, "String productName 产品名称 SysPssProductR"

				, "String productCode 产品编码 SysPssProductR"

				, "String barcode 条形码 SysPssProductR"

		}

)
public class SysPssStockEntity extends CompIDEntity {

	/** serialVersionUID */
	private static final long serialVersionUID = 50006L;

	/** 仓库ID（SysPssStorageEntity.id） */
	@FieldGenerator(check = true, updateable = false)
	@Column(length = 64, nullable = false)
	private String storageId;

	/** 产品ID(SysPssProductEntity.ID) */
	@FieldGenerator(check = true, updateable = false)
	@Column(length = 64, nullable = false)
	private String productId;

	/** 产品数量 */
	@FieldGenerator(resultColumn = true)
	@Column(nullable = false)
	private int quantity;

}
