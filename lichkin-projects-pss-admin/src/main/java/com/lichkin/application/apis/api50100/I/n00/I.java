package com.lichkin.application.apis.api50100.I.n00;

import java.util.List;

import com.lichkin.application.apis.api50100.SI;
import com.lichkin.framework.beans.impl.LKRequestBean;
import com.lichkin.springframework.entities.impl.SysPssPurchaseOrderProductEntity;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class I extends LKRequestBean implements SI {

	private String compId;

	private String billDate;

	private String remarks;

	private String supplierId;

	private String purchaserId;

	/** 产品列表 */
	private String productList;

	/** 产品列表 */
	private List<SysPssPurchaseOrderProductEntity> listProduct;

}
