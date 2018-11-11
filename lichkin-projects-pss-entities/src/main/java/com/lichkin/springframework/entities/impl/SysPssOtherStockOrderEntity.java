package com.lichkin.springframework.entities.impl;

import javax.persistence.Column;
import javax.persistence.Entity;

import com.lichkin.framework.defines.annotations.ClassGenerator;
import com.lichkin.framework.defines.annotations.FieldGenerator;
import com.lichkin.framework.defines.annotations.InsertCheckType;
import com.lichkin.framework.defines.annotations.InsertType;
import com.lichkin.framework.defines.annotations.UpdateCheckType;
import com.lichkin.springframework.entities.suppers.PssStockOrderEntity;

import lombok.Getter;
import lombok.Setter;

/**
 * 其它库存单表实体类
 * @author SuZhou LichKin Information Technology Co., Ltd.
 */
@Getter
@Setter
@Entity
@ClassGenerator(

		afterSaveMain = false, IUSubTables = { "SysPssOtherStockOrderProductEntity" }

		, insertCheckType = InsertCheckType.UNCHECK

		, updateCheckType = UpdateCheckType.UNCHECK

		, pageResultColumns = {

				"String storageName 仓库 SysPssStorageR"

		}

		, pageQueryConditions = {

				"String startDate 开始日期 #entityR"

				, "String endDate 结束日期 #entityR"

		}

		, insertFields = {

				"String productList 产品列表"

				, "List<SysPssOtherStockOrderProductEntity> listProduct 产品列表"

		}

		, updateFields = {

				"String productList 产品列表"

				, "List<SysPssOtherStockOrderProductEntity> listProduct 产品列表"

		}

)
public class SysPssOtherStockOrderEntity extends PssStockOrderEntity {

	/** serialVersionUID */
	private static final long serialVersionUID = 50300L;

	/** 出入库类型（字典） */
	@FieldGenerator(resultColumn = true, dictionary = true, queryCondition = true, queryConditionLike = false, insertType = InsertType.COPY_RETAIN, updateable = false)
	@Column(length = 64, nullable = false)
	private String storageType;

}
