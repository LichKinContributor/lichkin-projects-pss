package com.lichkin.application.apis.api50006.P.n00;

import com.lichkin.framework.beans.impl.LKRequestPageBean;
import com.lichkin.framework.defines.entities.I_CompId;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class I extends LKRequestPageBean implements I_CompId {

	/** 仓库名称 */
	private String storageName;

	/** 仓库编码 */
	private String storageCode;

	/** 产品名称 */
	private String productName;

	/** 产品编码 */
	private String productCode;

	/** 条形码 */
	private String barcode;

}
