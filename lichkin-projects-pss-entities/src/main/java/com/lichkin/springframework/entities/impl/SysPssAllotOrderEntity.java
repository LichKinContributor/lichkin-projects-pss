package com.lichkin.springframework.entities.impl;

import javax.persistence.Column;
import javax.persistence.Entity;

import com.lichkin.framework.defines.annotations.ClassGenerator;
import com.lichkin.framework.defines.annotations.FieldGenerator;
import com.lichkin.framework.defines.annotations.InsertCheckType;
import com.lichkin.framework.defines.annotations.InsertType;
import com.lichkin.framework.defines.annotations.UpdateCheckType;
import com.lichkin.springframework.entities.suppers.PssOrderEntity;

import lombok.Getter;
import lombok.Setter;

/**
 * 调拨订单表实体类
 * @author SuZhou LichKin Information Technology Co., Ltd.
 */
@Getter
@Setter
@Entity
@ClassGenerator(

		afterSaveMain = false, IUSubTables = { "SysPssAllotOrderProductEntity" }

		, insertCheckType = InsertCheckType.UNCHECK

		, updateCheckType = UpdateCheckType.UNCHECK

		, pageResultColumns = {

				"String outStorageName 调出仓库 SysPssStorageR"

				, "String inStorageName 调入仓库 SysPssStorageR"

		}

		, pageQueryConditions = {

				"String startDate 开始日期 #entityR"

				, "String endDate 结束日期 #entityR"

		}

		, insertFields = {

				"String productList 产品列表"

				, "List<SysPssAllotOrderProductEntity> listProduct 产品列表"

		}

		, updateFields = {

				"String productList 产品列表"

				, "List<SysPssAllotOrderProductEntity> listProduct 产品列表"

		}

)
public class SysPssAllotOrderEntity extends PssOrderEntity {

	/** serialVersionUID */
	private static final long serialVersionUID = 50400L;

	/** 出库仓库ID（SysPssStorageEntity.id） */
	@FieldGenerator(resultColumn = false, queryCondition = true, queryConditionLike = false, insertType = InsertType.COPY_RETAIN, updateable = false)
	@Column(length = 64, nullable = false)
	private String outStorageId;

	/** 入库仓库ID（SysPssStorageEntity.id） */
	@FieldGenerator(resultColumn = false, queryCondition = true, queryConditionLike = false, insertType = InsertType.COPY_RETAIN, updateable = false)
	@Column(length = 64, nullable = false)
	private String inStorageId;

}
