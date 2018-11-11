package com.lichkin.springframework.entities.suppers;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;

import com.lichkin.framework.defines.annotations.FieldGenerator;
import com.lichkin.framework.defines.annotations.InsertType;

import lombok.Getter;
import lombok.Setter;

/**
 * 订单表实体类
 * @author SuZhou LichKin Information Technology Co., Ltd.
 */
@Getter
@Setter
@MappedSuperclass
public abstract class PssOrderEntity extends ActivitiProcessEntity {

	/** serialVersionUID */
	private static final long serialVersionUID = 1L;

	/** 订单号（yyyyMMddHHmmssSSS+15位随即） */
	@FieldGenerator(resultColumn = true, queryCondition = true, insertType = InsertType.HANDLE_RETAIN, updateable = false)
	@Column(length = 32, nullable = false)
	private String orderNo;

	/** 订单日期（yyyy-MM-dd） */
	@FieldGenerator(resultColumn = true, insertType = InsertType.COPY_COPY)
	@Column(length = 10, nullable = false)
	private String billDate;

	/** 备注 */
	@FieldGenerator(insertType = InsertType.COPY_COPY)
	@Column(length = 512)
	private String remarks;

}
