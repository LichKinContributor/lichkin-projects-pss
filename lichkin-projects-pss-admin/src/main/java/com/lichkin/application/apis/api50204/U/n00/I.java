package com.lichkin.application.apis.api50204.U.n00;

import java.util.List;

import com.lichkin.application.apis.api50204.SI;
import com.lichkin.framework.beans.impl.LKRequestIDBean;
import com.lichkin.springframework.entities.impl.SysPssSellReturnNotStockOutOrderProductEntity;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class I extends LKRequestIDBean implements SI {

	// private String billDate;

	private String remarks;

	/** 产品列表 */
	private String productList;

	/** 产品列表 */
	private List<SysPssSellReturnNotStockOutOrderProductEntity> listProduct;

}
