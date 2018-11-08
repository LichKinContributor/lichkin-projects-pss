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
 * 销售库存单表实体类
 * @author SuZhou LichKin Information Technology Co., Ltd.
 */
@Getter
@Setter
@Entity
@ClassGenerator(

		afterSaveMain = false, IUSubTables = { "SysPssSellStockOrderProductEntity" }

		, insertCheckType = InsertCheckType.UNCHECK

		, updateCheckType = UpdateCheckType.UNCHECK

		, pageResultColumns = {

				"String sellOrderNo 销售订单号 SysPssSellOrderR"

				, "String sellBillDate 销售订单日期 SysPssSellOrderR"

				, "String sellOrderAmount 销售订单金额 SysPssSellOrderR"

				, "String salesName 销售人姓名 SysEmployeeR"

		}

		, pageQueryConditions = {

				"String sellOrderNo 销售订单号 SysPssSellOrderR"

				, "String salesName 销售人姓名 SysEmployeeR"

				, "String startDate 开始日期 #entityR"

				, "String endDate 结束日期 #entityR"

		}

		, insertFields = {

				"String productList 产品列表"

		}

		, updateFields = {

				"String productList 产品列表"

		}

)
public class SysPssSellStockOrderEntity extends PssStockOrderEntity {

	/** serialVersionUID */
	private static final long serialVersionUID = 50202L;

	/** 销售单ID（SysPssSellOrderEntity.id） */
	@FieldGenerator(resultColumn = false, insertType = InsertType.COPY_COPY, updateable = false)
	@Column(length = 64, nullable = false)
	private String orderId;

}
