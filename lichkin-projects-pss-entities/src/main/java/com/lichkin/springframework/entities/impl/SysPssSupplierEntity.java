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
 * 供应商表实体类
 * @author SuZhou LichKin Information Technology Co., Ltd.
 */
@Getter
@Setter
@Entity
@ClassGenerator(

		afterSaveMain = false

		, insertCheckType = InsertCheckType.CHECK_RESTORE

		, updateCheckType = UpdateCheckType.CHECK

		, pageResultColumns = { "String responsiblePerson 负责人姓名 SysEmployeeR", "String cellphone 负责人联系电话 SysEmployeeR" }

)
public class SysPssSupplierEntity extends BaseCompEntity {

	/** serialVersionUID */
	private static final long serialVersionUID = 50001L;

	/** 供应商编码 */
	@FieldGenerator(check = true, insertType = InsertType.COPY_COPY, queryCondition = true, resultColumn = true)
	@Column(length = 64)
	private String supplierCode;

	/** 供应商名称 */
	@FieldGenerator(check = true, insertType = InsertType.COPY_COPY, queryCondition = true, resultColumn = true)
	@Column(length = 32, nullable = false)
	private String supplierName;

	/** 供应商类型（字典） */
	@FieldGenerator(insertType = InsertType.COPY_COPY, queryCondition = true, resultColumn = true, dictionary = true, queryConditionLike = false)
	@Column(length = 32)
	private String supplierType;

	/** 供应商地址 */
	@FieldGenerator(insertType = InsertType.COPY_COPY, resultColumn = true)
	@Column(length = 50)
	private String address;

	/** 联系人姓名 */
	@FieldGenerator(insertType = InsertType.COPY_COPY, resultColumn = true)
	@Column(length = 32)
	private String linkmanName;

	/** 联系人联系方式 */
	@FieldGenerator(insertType = InsertType.COPY_COPY, resultColumn = true)
	@Column(length = 100)
	private String linkmanContactWay;

	/** 负责人登录ID（SysEmployeeEntity.id） */
	@FieldGenerator(insertType = InsertType.COPY_COPY)
	@Column(length = 64)
	private String responsiblePerson;

	/** 备注 */
	@FieldGenerator(insertType = InsertType.COPY_COPY, resultColumn = true)
	@Column(length = 512)
	private String remarks;

}
