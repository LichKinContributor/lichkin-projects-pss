package com.lichkin.application.mappers.impl.in;

import com.lichkin.framework.defines.beans.impl.LKPageBean;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PssStorageDetailIn extends LKPageBean {

	private String compId;

	private String orderNo;

	private Boolean orderType;

	private String startDate;

	private String endDate;

	private String productCode;

	private String productName;

	private String barcode;

	private String storageId;

	private String storageType;

	private Integer pageStartLine;


	public Integer getPageStartLine() {
		if (getPageNumber() == null) {
			return 0;
		}
		return getPageNumber() * getPageSize();
	}

}
