package com.lichkin.application.apis.api50200;

import java.util.List;

import com.lichkin.springframework.entities.impl.SysPssSellOrderProductEntity;

public interface SI {

	String getProductList();


	void setListProduct(List<SysPssSellOrderProductEntity> listProduct);


	List<SysPssSellOrderProductEntity> getListProduct();

}