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
 * 门店表实体类
 * @author SuZhou LichKin Information Technology Co., Ltd.
 */
@Getter
@Setter
@Entity
@ClassGenerator(

		afterSaveMain = false, IUSubTables = {}

		, insertCheckType = InsertCheckType.CHECK_RESTORE

		, updateCheckType = UpdateCheckType.CHECK

		, pageResultColumns = {

				"String responsiblePerson 负责人姓名 SysEmployeeR"

				, "String cellphone 负责人联系电话 SysEmployeeR"

				, "String storageName 仓库名称 SysPssStorageR"

		}

)
public class SysPssStoreEntity extends BaseCompEntity {

	/** serialVersionUID */
	private static final long serialVersionUID = 50004L;

	/** 门店编码 */
	@FieldGenerator(check = true, insertType = InsertType.COPY_COPY, queryCondition = true, resultColumn = true)
	@Column(length = 64)
	private String storeCode;

	/** 门店名称 */
	@FieldGenerator(check = true, queryCondition = true, resultColumn = true)
	@Column(length = 32, nullable = false)
	private String storeName;

	/** 仓库ID（SysPssStorageEntity.id） */
	@FieldGenerator()
	@Column(length = 64, nullable = false)
	private String storageId;

	/** 门店地址 */
	@FieldGenerator(insertType = InsertType.COPY_COPY, resultColumn = true)
	@Column(length = 50)
	private String address;

	/** 负责人登录ID（SysEmployeeEntity.id） */
	@FieldGenerator(insertType = InsertType.COPY_COPY)
	@Column(length = 64)
	private String responsiblePerson;

	/** 备注 */
	@FieldGenerator(insertType = InsertType.COPY_COPY, resultColumn = true)
	@Column(length = 512)
	private String remarks;

}
