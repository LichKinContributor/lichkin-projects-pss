package com.lichkin.application.apis.api50200.U.n01;

import java.util.List;

import com.lichkin.framework.beans.impl.LKRequestIDBean;
import com.lichkin.springframework.entities.impl.SysPssSellOrderProductEntity;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class I extends LKRequestIDBean {

	// private String billDate;

	// private String remarks;

	// private String orderAmount;

	/** 产品列表 */
	private String productList;

	/** 产品列表 */
	private List<SysPssSellOrderProductEntity> listProduct;

}
