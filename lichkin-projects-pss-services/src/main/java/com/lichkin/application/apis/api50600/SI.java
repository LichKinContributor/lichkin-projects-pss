package com.lichkin.application.apis.api50600;

import java.util.List;

import com.lichkin.springframework.entities.impl.SysPssPurchaseReturnOrderProductEntity;

public interface SI {

	String getProductList();


	void setListProduct(List<SysPssPurchaseReturnOrderProductEntity> listProduct);


	List<SysPssPurchaseReturnOrderProductEntity> getListProduct();

}