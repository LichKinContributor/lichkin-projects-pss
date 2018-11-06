package com.lichkin.application.apis.api50003.U.n00;

import com.lichkin.framework.beans.impl.LKRequestIDBean;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class I extends LKRequestIDBean {

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
