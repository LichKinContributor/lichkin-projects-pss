package com.lichkin.application.apis.api50200.L.n00;

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

	/** 销售人姓名 */
	private String salesName;

	/** 开始日期 */
	private String startDate;

	/** 结束日期 */
	private String endDate;

}
