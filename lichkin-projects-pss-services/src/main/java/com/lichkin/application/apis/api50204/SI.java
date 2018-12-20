package com.lichkin.application.apis.api50204;

import java.util.List;

import com.lichkin.springframework.entities.impl.SysPssSellReturnNotStockOutOrderProductEntity;

public interface SI {

	String getProductList();


	void setListProduct(List<SysPssSellReturnNotStockOutOrderProductEntity> listProduct);


	List<SysPssSellReturnNotStockOutOrderProductEntity> getListProduct();

}