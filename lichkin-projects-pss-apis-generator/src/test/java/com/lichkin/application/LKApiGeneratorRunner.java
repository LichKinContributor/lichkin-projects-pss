package com.lichkin.application;

import org.junit.Test;

import com.lichkin.springframework.generator.LKApiGenerator;
import com.lichkin.springframework.generator.LKApiGenerator.Type;

public class LKApiGeneratorRunner {

	String projectDir = Thread.currentThread().getContextClassLoader().getResource(".").getPath().replace("/target/test-classes/", "");

	String apiType = "WEB";

	String userType = "ADMIN";

	int index = 0;

	int errorCode = 60000;


	@Test
	public void generateInsert() {
		LKApiGenerator.generate(userType, apiType, projectDir, "SysPssStorageEntity", index, errorCode, Type.Insert, "新增数据接口");
		LKApiGenerator.generate(userType, apiType, projectDir, "SysPssSupplierEntity", index, errorCode, Type.Insert, "新增数据接口");
		LKApiGenerator.generate(userType, apiType, projectDir, "SysPssProductCategoryEntity", index, errorCode, Type.Insert, "新增数据接口");
		LKApiGenerator.generate(userType, apiType, projectDir, "SysPssProductEntity", index, errorCode, Type.Insert, "新增数据接口");
		LKApiGenerator.generate(userType, apiType, projectDir, "SysPssStoreEntity", index, errorCode, Type.Insert, "新增数据接口");
		LKApiGenerator.generate(userType, apiType, projectDir, "SysPssStoreCashierEntity", index, errorCode, Type.Insert, "新增数据接口");
		LKApiGenerator.generate(userType, apiType, projectDir, "SysPssPurchaseOrderEntity", index, errorCode, Type.Insert, "新增数据接口");
	}


	@Test
	public void generateUpdate() {
		LKApiGenerator.generate(userType, apiType, projectDir, "SysPssStorageEntity", index, errorCode, Type.Update, "编辑数据接口");
		LKApiGenerator.generate(userType, apiType, projectDir, "SysPssSupplierEntity", index, errorCode, Type.Update, "编辑数据接口");
		LKApiGenerator.generate(userType, apiType, projectDir, "SysPssProductCategoryEntity", index, errorCode, Type.Update, "编辑数据接口");
		LKApiGenerator.generate(userType, apiType, projectDir, "SysPssProductEntity", index, errorCode, Type.Update, "编辑数据接口");
		LKApiGenerator.generate(userType, apiType, projectDir, "SysPssStoreEntity", index, errorCode, Type.Update, "编辑数据接口");
		LKApiGenerator.generate(userType, apiType, projectDir, "SysPssPurchaseOrderEntity", index, errorCode, Type.Update, "编辑数据接口");
		LKApiGenerator.generate(userType, apiType, projectDir, "SysPssPurchaseOrderEntity", index + 1, errorCode, Type.Update, "提交数据接口");
	}


	@Test
	public void generateDelete() {
		LKApiGenerator.generate(userType, apiType, projectDir, "SysPssStoreCashierEntity", index, errorCode, Type.Delete, "删除数据接口");
		LKApiGenerator.generate(userType, apiType, projectDir, "SysPssPurchaseOrderEntity", index, errorCode, Type.Delete, "删除数据接口");
	}


	@Test
	public void generatePage() {
		LKApiGenerator.generate(userType, apiType, projectDir, "SysPssStorageEntity", index, errorCode, Type.GetPage, "获取分页数据接口");
		LKApiGenerator.generate(userType, apiType, projectDir, "SysPssSupplierEntity", index, errorCode, Type.GetPage, "获取分页数据接口");
		LKApiGenerator.generate(userType, apiType, projectDir, "SysPssStoreEntity", index, errorCode, Type.GetPage, "获取分页数据接口");
		LKApiGenerator.generate(userType, apiType, projectDir, "SysPssStoreCashierEntity", index, errorCode, Type.GetPage, "获取分页数据接口");
		LKApiGenerator.generate(userType, apiType, projectDir, "SysPssStockEntity", index, errorCode, Type.GetPage, "获取分页数据接口");
		LKApiGenerator.generate(userType, apiType, projectDir, "SysPssPurchaseOrderEntity", index, errorCode, Type.GetPage, "获取分页数据接口");
	}


	@Test
	public void generateList() {
		LKApiGenerator.generate(userType, apiType, projectDir, "SysPssProductEntity", index, errorCode, Type.GetList, "获取列表数据接口");
	}


	@Test
	public void generateOne() {
		LKApiGenerator.generate(userType, apiType, projectDir, "SysPssStorageEntity", index, errorCode, Type.GetOne, "获取单个数据接口");
		LKApiGenerator.generate(userType, apiType, projectDir, "SysPssSupplierEntity", index, errorCode, Type.GetOne, "获取单个数据接口");
		LKApiGenerator.generate(userType, apiType, projectDir, "SysPssProductCategoryEntity", index, errorCode, Type.GetOne, "获取单个数据接口");
		LKApiGenerator.generate(userType, apiType, projectDir, "SysPssProductEntity", index, errorCode, Type.GetOne, "获取单个数据接口");
		LKApiGenerator.generate(userType, apiType, projectDir, "SysPssStoreEntity", index, errorCode, Type.GetOne, "获取单个数据接口");
		LKApiGenerator.generate(userType, apiType, projectDir, "SysPssPurchaseOrderEntity", index, errorCode, Type.GetOne, "获取单个数据接口");
	}


	@Test
	public void generateUS() {
		LKApiGenerator.generate(userType, apiType, projectDir, "SysPssStorageEntity", index, errorCode, Type.UpdateUsingStatus, "修改状态接口");
		LKApiGenerator.generate(userType, apiType, projectDir, "SysPssSupplierEntity", index, errorCode, Type.UpdateUsingStatus, "修改状态接口");
		LKApiGenerator.generate(userType, apiType, projectDir, "SysPssProductCategoryEntity", index, errorCode, Type.UpdateUsingStatus, "修改状态接口");
		LKApiGenerator.generate(userType, apiType, projectDir, "SysPssProductEntity", index, errorCode, Type.UpdateUsingStatus, "修改状态接口");
		LKApiGenerator.generate(userType, apiType, projectDir, "SysPssStoreEntity", index, errorCode, Type.UpdateUsingStatus, "修改状态接口");
		LKApiGenerator.generate(userType, apiType, projectDir, "SysPssPurchaseOrderEntity", index, errorCode, Type.UpdateUsingStatus, "修改状态接口");
	}

}
