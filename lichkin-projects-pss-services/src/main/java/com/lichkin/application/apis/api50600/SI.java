package com.lichkin.application.apis.api50600;

import java.util.List;

import com.lichkin.springframework.entities.impl.SysPssPurchaseReturnNotStockInOrderProductEntity;

public interface SI {

	String getProductList();


	void setListProduct(List<SysPssPurchaseReturnNotStockInOrderProductEntity> listProduct);


	List<SysPssPurchaseReturnNotStockInOrderProductEntity> getListProduct();

}