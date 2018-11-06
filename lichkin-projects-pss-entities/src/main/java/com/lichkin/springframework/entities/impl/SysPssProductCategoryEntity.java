package com.lichkin.springframework.entities.impl;

import javax.persistence.Column;
import javax.persistence.Entity;

import com.lichkin.framework.defines.annotations.ClassGenerator;
import com.lichkin.framework.defines.annotations.FieldGenerator;
import com.lichkin.framework.defines.annotations.InsertCheckType;
import com.lichkin.framework.defines.annotations.InsertType;
import com.lichkin.framework.defines.annotations.UpdateCheckType;
import com.lichkin.springframework.entities.suppers.BaseCompEntity;

import lombok.Getter;
import lombok.Setter;

/**
 * 产品类别表实体类
 * @author SuZhou LichKin Information Technology Co., Ltd.
 */
@Getter
@Setter
@Entity
@ClassGenerator(

		afterSaveMain = false, IUSubTables = {}

		, insertCheckType = InsertCheckType.CHECK_RESTORE

		, updateCheckType = UpdateCheckType.CHECK

)
public class SysPssProductCategoryEntity extends BaseCompEntity {

	/** serialVersionUID */
	private static final long serialVersionUID = 50002L;

	/** 上级类别编码（工具类规则） */
	@FieldGenerator(check = true, insertType = InsertType.COPY_ERROR, updateable = false)
	@Column(length = 64, nullable = false)
	private String parentCode;

	/** 类别编码（工具类规则） */
	@FieldGenerator(insertType = InsertType.HANDLE_RETAIN, updateable = false)
	@Column(length = 64, nullable = false)
	private String categoryCode;

	/** 类别名称 */
	@FieldGenerator(check = true)
	@Column(length = 64, nullable = false)
	private String categoryName;

}
