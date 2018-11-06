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
 * 门店收银员表实体类
 * <pre>
 *  收银员只能在一家门店任职，本表只作为员工关联门店逻辑用，业务处理直接使用cashier（即员工ID）。
 * </pre>
 * @author SuZhou LichKin Information Technology Co., Ltd.
 */
@Getter
@Setter
@Entity
@ClassGenerator(

		afterSaveMain = false, IUSubTables = {}

		, insertCheckType = InsertCheckType.FORCE_CHECK

		, updateCheckType = UpdateCheckType.CHECK

		, pageResultColumns = {

				"String cashierName 收银员姓名 SysEmployeeR"

				, "String storeName 门店名称 SysPssStoreR"

		}

		, pageQueryConditions = {

				"String cashierName 收银员姓名 SysEmployeeR"

				, "String storeName 门店名称 SysPssStoreR"

		}

)
public class SysPssStoreCashierEntity extends CompIDEntity {

	/** serialVersionUID */
	private static final long serialVersionUID = 50005L;

	/** 收银员登录ID（SysEmployeeEntity.id） */
	@FieldGenerator(check = true, updateable = false)
	@Column(length = 64, nullable = false)
	private String cashier;

	/** 门店ID（SysPssStoreCashierEntity.id） */
	@FieldGenerator(updateable = false)
	@Column(length = 64, nullable = false)
	private String storeId;

}
