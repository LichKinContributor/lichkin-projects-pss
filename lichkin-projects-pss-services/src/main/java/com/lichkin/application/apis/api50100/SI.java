package com.lichkin.application.apis.api50100;

import java.util.List;

import com.lichkin.springframework.entities.impl.SysPssPurchaseOrderProductEntity;

public interface SI {

	String getProductList();


	void setListProduct(List<SysPssPurchaseOrderProductEntity> listProduct);


	List<SysPssPurchaseOrderProductEntity> getListProduct();

}