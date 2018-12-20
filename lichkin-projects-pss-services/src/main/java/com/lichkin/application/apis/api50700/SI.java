package com.lichkin.application.apis.api50700;

import java.util.List;

import com.lichkin.springframework.entities.impl.SysPssPurchaseReturnStockInOrderProductEntity;

public interface SI {

	String getProductList();


	void setListProduct(List<SysPssPurchaseReturnStockInOrderProductEntity> listProduct);


	List<SysPssPurchaseReturnStockInOrderProductEntity> getListProduct();

}