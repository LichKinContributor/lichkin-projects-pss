package com.lichkin.application.apis.api50206;

import java.util.List;

import com.lichkin.springframework.entities.impl.SysPssSellReturnStockOutOrderProductEntity;

public interface SI {

	String getProductList();


	void setListProduct(List<SysPssSellReturnStockOutOrderProductEntity> listProduct);


	List<SysPssSellReturnStockOutOrderProductEntity> getListProduct();

}