package com.lichkin.application.apis.api50003.I.n00;

import com.lichkin.framework.beans.impl.LKRequestBean;
import com.lichkin.framework.defines.entities.I_CompId;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class I extends LKRequestBean implements I_CompId {

	private String compId;

	private String productCategory;

	private String productCode;

	private String productName;

	private String barcode;

	private String unit;

	private String subProduct;

	private Short subProductCount;

	private String purchasePrice;

	private String referencePrice;

	private String retailPrice;

	private String remarks;

	private String imageUrl1;

	private String imageUrl2;

	private String imageUrl3;

	private String imageUrl4;

}
