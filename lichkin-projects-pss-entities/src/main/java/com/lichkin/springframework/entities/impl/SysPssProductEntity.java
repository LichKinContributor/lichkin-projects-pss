package com.lichkin.springframework.entities.impl;

import javax.persistence.Column;
import javax.persistence.Entity;

import com.lichkin.framework.defines.annotations.ClassGenerator;
import com.lichkin.framework.defines.annotations.DefaultStringValue;
import com.lichkin.framework.defines.annotations.FieldGenerator;
import com.lichkin.framework.defines.annotations.InsertCheckType;
import com.lichkin.framework.defines.annotations.InsertType;
import com.lichkin.framework.defines.annotations.UpdateCheckType;
import com.lichkin.springframework.entities.suppers.BaseCompEntity;

import lombok.Getter;
import lombok.Setter;

/**
 * 产品表实体类
 * @author SuZhou LichKin Information Technology Co., Ltd.
 */
@Getter
@Setter
@Entity
@ClassGenerator(

		afterSaveMain = false, IUSubTables = {}

		, insertCheckType = InsertCheckType.CHECK_RESTORE

		, updateCheckType = UpdateCheckType.CHECK

		, pageResultColumns = { "String productCategory 产品类别 SysPssProductCategoryR" }

)
public class SysPssProductEntity extends BaseCompEntity {

	/** serialVersionUID */
	private static final long serialVersionUID = 50003L;

	/** 产品类别（SysPssProductCategoryEntity.id） */
	@FieldGenerator(insertType = InsertType.COPY_RETAIN, updateable = false, queryCondition = true, queryConditionLike = false, resultColumn = false)
	@Column(length = 64, nullable = false)
	private String productCategory;

	/** 产品编码 */
	@FieldGenerator(check = true, insertType = InsertType.COPY_COPY, resultColumn = true, queryCondition = true)
	@Column(length = 64)
	@DefaultStringValue
	private String productCode;

	/** 产品名称 */
	@FieldGenerator(check = true, resultColumn = true, queryCondition = true)
	@Column(length = 32, nullable = false)
	private String productName;

	/** 条形码 */
	@FieldGenerator(check = true, queryCondition = true, queryConditionLike = false, resultColumn = true)
	@Column(length = 64)
	@DefaultStringValue
	private String barcode;

	/** 单位（字典） */
	@FieldGenerator(resultColumn = true, dictionary = true)
	@Column(length = 64)
	private String unit;

	/** 下级产品ID（SysPssProductEntity.id） */
	@FieldGenerator()
	@Column(length = 64)
	private String subProduct;

	/** 下级产品个数 */
	@FieldGenerator()
	@Column
	private Short subProductCount;

	/** 参考进价 */
	@FieldGenerator(insertType = InsertType.CHANGE_HANDLE, resultColumn = true)
	@Column(length = 10)
	private String purchasePrice;

	/** 参考售价 */
	@FieldGenerator(insertType = InsertType.CHANGE_HANDLE, resultColumn = true)
	@Column(length = 10)
	private String referencePrice;

	/** 零售价 */
	@FieldGenerator(insertType = InsertType.CHANGE_HANDLE, resultColumn = true)
	@Column(length = 10, nullable = false)
	private String retailPrice;

	/** 备注 */
	@FieldGenerator()
	@Column(length = 512)
	private String remarks;

	/** 图片（URL） */
	@FieldGenerator(insertType = InsertType.CHANGE_HANDLE)
	@Column(length = 512)
	private String imageUrl1;

	/** 图片（URL） */
	@FieldGenerator(insertType = InsertType.CHANGE_HANDLE)
	@Column(length = 512)
	private String imageUrl2;

	/** 图片（URL） */
	@FieldGenerator(insertType = InsertType.CHANGE_HANDLE)
	@Column(length = 512)
	private String imageUrl3;

	/** 图片（URL） */
	@FieldGenerator(insertType = InsertType.CHANGE_HANDLE)
	@Column(length = 512)
	private String imageUrl4;

}
