package com.lichkin.springframework.entities.impl;

import javax.persistence.Entity;

import com.lichkin.framework.defines.annotations.ClassGenerator;
import com.lichkin.framework.defines.annotations.InsertCheckType;
import com.lichkin.framework.defines.annotations.UpdateCheckType;
import com.lichkin.springframework.entities.suppers.PssOrderProductEntity;

import lombok.Getter;
import lombok.Setter;

/**
 * 其他入库订单商品表实体类
 * @author SuZhou LichKin Information Technology Co., Ltd.
 */
@Getter
@Setter
@Entity
@ClassGenerator(

		afterSaveMain = false, IUSubTables = {}

		, insertCheckType = InsertCheckType.UNCHECK

		, updateCheckType = UpdateCheckType.UNCHECK,

		pageResultColumns = {

				"String productCode 产品编码 SysPssProductR"

				, "String productName 产品名称 SysPssProductR"

				, "String barcode 条形码 SysPssProductR"

				, "String unit 单位 SysPssProductR"

		})
public class SysPssOtherStockOrderProductEntity extends PssOrderProductEntity {

	/** serialVersionUID */
	private static final long serialVersionUID = 50301L;

}
