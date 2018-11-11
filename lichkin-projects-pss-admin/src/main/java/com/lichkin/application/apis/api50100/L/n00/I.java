package com.lichkin.application.apis.api50100.L.n00;

import com.lichkin.framework.beans.impl.LKRequestBean;
import com.lichkin.framework.defines.enums.impl.InventoryStatusEnum;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class I extends LKRequestBean {

	private String compId;

	private String orderNo;

	private InventoryStatusEnum inventoryStatus;

	/** 供应商名称 */
	private String supplierName;

	/** 采购人姓名 */
	private String purchaserName;

	/** 开始日期 */
	private String startDate;

	/** 结束日期 */
	private String endDate;

}
