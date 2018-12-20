package com.lichkin.application.apis.api50206.I.n00;

import java.util.List;

import com.lichkin.application.apis.api50206.SI;
import com.lichkin.framework.beans.impl.LKRequestBean;
import com.lichkin.framework.defines.entities.I_CompId;
import com.lichkin.springframework.entities.impl.SysPssSellReturnStockOutOrderProductEntity;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class I extends LKRequestBean implements SI, I_CompId {

	private String compId;

	private String billDate;

	private String remarks;

	private String salesId;

	private String storageId;

	/** 产品列表 */
	private String productList;

	/** 产品列表 */
	private List<SysPssSellReturnStockOutOrderProductEntity> listProduct;

}
