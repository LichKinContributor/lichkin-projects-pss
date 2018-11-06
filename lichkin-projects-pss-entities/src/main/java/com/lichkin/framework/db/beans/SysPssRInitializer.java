package com.lichkin.framework.db.beans;

/**
 * 数据库资源初始化类
 * @author SuZhou LichKin Information Technology Co., Ltd.
 */
class SysPssRInitializer implements LKRInitializer {

	/**
	 * 初始化数据库资源
	 */
	public static void init() {
		LKDBResource.addTable("com.lichkin.springframework.entities.impl.SysPssStorageEntity", "T_SYS_PSS_STORAGE", "SysPssStorageEntity");
		LKDBResource.addColumn("50000000", "SysPssStorageEntity", "id");
		LKDBResource.addColumn("50000001", "SysPssStorageEntity", "usingStatus");
		LKDBResource.addColumn("50000002", "SysPssStorageEntity", "insertTime");
		LKDBResource.addColumn("50000003", "SysPssStorageEntity", "compId");
		LKDBResource.addColumn("50000004", "SysPssStorageEntity", "storageCode");
		LKDBResource.addColumn("50000005", "SysPssStorageEntity", "storageName");
		LKDBResource.addColumn("50000006", "SysPssStorageEntity", "address");
		LKDBResource.addColumn("50000007", "SysPssStorageEntity", "responsiblePerson");
		LKDBResource.addColumn("50000008", "SysPssStorageEntity", "remarks");
		LKDBResource.addTable("com.lichkin.springframework.entities.impl.SysPssSupplierEntity", "T_SYS_PSS_SUPPLIER", "SysPssSupplierEntity");
		LKDBResource.addColumn("50001000", "SysPssSupplierEntity", "id");
		LKDBResource.addColumn("50001001", "SysPssSupplierEntity", "usingStatus");
		LKDBResource.addColumn("50001002", "SysPssSupplierEntity", "insertTime");
		LKDBResource.addColumn("50001003", "SysPssSupplierEntity", "compId");
		LKDBResource.addColumn("50001004", "SysPssSupplierEntity", "supplierCode");
		LKDBResource.addColumn("50001005", "SysPssSupplierEntity", "supplierName");
		LKDBResource.addColumn("50001006", "SysPssSupplierEntity", "supplierType");
		LKDBResource.addColumn("50001007", "SysPssSupplierEntity", "address");
		LKDBResource.addColumn("50001008", "SysPssSupplierEntity", "linkmanName");
		LKDBResource.addColumn("50001009", "SysPssSupplierEntity", "linkmanContactWay");
		LKDBResource.addColumn("50001010", "SysPssSupplierEntity", "responsiblePerson");
		LKDBResource.addColumn("50001011", "SysPssSupplierEntity", "remarks");
		LKDBResource.addTable("com.lichkin.springframework.entities.impl.SysPssProductCategoryEntity", "T_SYS_PSS_PRODUCT_CATEGORY", "SysPssProductCategoryEntity");
		LKDBResource.addColumn("50002000", "SysPssProductCategoryEntity", "id");
		LKDBResource.addColumn("50002001", "SysPssProductCategoryEntity", "usingStatus");
		LKDBResource.addColumn("50002002", "SysPssProductCategoryEntity", "insertTime");
		LKDBResource.addColumn("50002003", "SysPssProductCategoryEntity", "compId");
		LKDBResource.addColumn("50002004", "SysPssProductCategoryEntity", "parentCode");
		LKDBResource.addColumn("50002005", "SysPssProductCategoryEntity", "categoryCode");
		LKDBResource.addColumn("50002006", "SysPssProductCategoryEntity", "categoryName");
		LKDBResource.addTable("com.lichkin.springframework.entities.impl.SysPssProductEntity", "T_SYS_PSS_PRODUCT", "SysPssProductEntity");
		LKDBResource.addColumn("50003000", "SysPssProductEntity", "id");
		LKDBResource.addColumn("50003001", "SysPssProductEntity", "usingStatus");
		LKDBResource.addColumn("50003002", "SysPssProductEntity", "insertTime");
		LKDBResource.addColumn("50003003", "SysPssProductEntity", "compId");
		LKDBResource.addColumn("50003004", "SysPssProductEntity", "productCategory");
		LKDBResource.addColumn("50003005", "SysPssProductEntity", "productCode");
		LKDBResource.addColumn("50003006", "SysPssProductEntity", "productName");
		LKDBResource.addColumn("50003007", "SysPssProductEntity", "barcode");
		LKDBResource.addColumn("50003008", "SysPssProductEntity", "unit");
		LKDBResource.addColumn("50003009", "SysPssProductEntity", "subProduct");
		LKDBResource.addColumn("50003010", "SysPssProductEntity", "subProductCount");
		LKDBResource.addColumn("50003011", "SysPssProductEntity", "purchasePrice");
		LKDBResource.addColumn("50003012", "SysPssProductEntity", "referencePrice");
		LKDBResource.addColumn("50003013", "SysPssProductEntity", "retailPrice");
		LKDBResource.addColumn("50003014", "SysPssProductEntity", "remarks");
		LKDBResource.addColumn("50003015", "SysPssProductEntity", "imageUrl1");
		LKDBResource.addColumn("50003016", "SysPssProductEntity", "imageUrl2");
		LKDBResource.addColumn("50003017", "SysPssProductEntity", "imageUrl3");
		LKDBResource.addColumn("50003018", "SysPssProductEntity", "imageUrl4");
		LKDBResource.addTable("com.lichkin.springframework.entities.impl.SysPssStoreEntity", "T_SYS_PSS_STORE", "SysPssStoreEntity");
		LKDBResource.addColumn("50004000", "SysPssStoreEntity", "id");
		LKDBResource.addColumn("50004001", "SysPssStoreEntity", "usingStatus");
		LKDBResource.addColumn("50004002", "SysPssStoreEntity", "insertTime");
		LKDBResource.addColumn("50004003", "SysPssStoreEntity", "compId");
		LKDBResource.addColumn("50004004", "SysPssStoreEntity", "storeCode");
		LKDBResource.addColumn("50004005", "SysPssStoreEntity", "storeName");
		LKDBResource.addColumn("50004006", "SysPssStoreEntity", "storageId");
		LKDBResource.addColumn("50004007", "SysPssStoreEntity", "address");
		LKDBResource.addColumn("50004008", "SysPssStoreEntity", "responsiblePerson");
		LKDBResource.addColumn("50004009", "SysPssStoreEntity", "remarks");
		LKDBResource.addTable("com.lichkin.springframework.entities.impl.SysPssStoreCashierEntity", "T_SYS_PSS_STORE_CASHIER", "SysPssStoreCashierEntity");
		LKDBResource.addColumn("50005000", "SysPssStoreCashierEntity", "id");
		LKDBResource.addColumn("50005001", "SysPssStoreCashierEntity", "compId");
		LKDBResource.addColumn("50005002", "SysPssStoreCashierEntity", "cashier");
		LKDBResource.addColumn("50005003", "SysPssStoreCashierEntity", "storeId");
		LKDBResource.addTable("com.lichkin.springframework.entities.impl.SysPssStockEntity", "T_SYS_PSS_STOCK", "SysPssStockEntity");
		LKDBResource.addColumn("50006000", "SysPssStockEntity", "id");
		LKDBResource.addColumn("50006001", "SysPssStockEntity", "compId");
		LKDBResource.addColumn("50006002", "SysPssStockEntity", "storageId");
		LKDBResource.addColumn("50006003", "SysPssStockEntity", "productId");
		LKDBResource.addColumn("50006004", "SysPssStockEntity", "quantity");
		LKDBResource.addTable("com.lichkin.springframework.entities.impl.SysPssPurchaseOrderEntity", "T_SYS_PSS_PURCHASE_ORDER", "SysPssPurchaseOrderEntity");
		LKDBResource.addColumn("50100000", "SysPssPurchaseOrderEntity", "id");
		LKDBResource.addColumn("50100001", "SysPssPurchaseOrderEntity", "usingStatus");
		LKDBResource.addColumn("50100002", "SysPssPurchaseOrderEntity", "insertTime");
		LKDBResource.addColumn("50100003", "SysPssPurchaseOrderEntity", "compId");
		LKDBResource.addColumn("50100004", "SysPssPurchaseOrderEntity", "approvalStatus");
		LKDBResource.addColumn("50100005", "SysPssPurchaseOrderEntity", "approvalTime");
		LKDBResource.addColumn("50100006", "SysPssPurchaseOrderEntity", "orderNo");
		LKDBResource.addColumn("50100007", "SysPssPurchaseOrderEntity", "billDate");
		LKDBResource.addColumn("50100008", "SysPssPurchaseOrderEntity", "remarks");
		LKDBResource.addColumn("50100009", "SysPssPurchaseOrderEntity", "supplierId");
		LKDBResource.addColumn("50100010", "SysPssPurchaseOrderEntity", "purchaserId");
		LKDBResource.addColumn("50100011", "SysPssPurchaseOrderEntity", "orderAmount");
		LKDBResource.addColumn("50100012", "SysPssPurchaseOrderEntity", "inventoryStatus");
		LKDBResource.addTable("com.lichkin.springframework.entities.impl.SysPssPurchaseOrderProductEntity", "T_SYS_PSS_PURCHASE_ORDER_PRODUCT", "SysPssPurchaseOrderProductEntity");
		LKDBResource.addColumn("50101000", "SysPssPurchaseOrderProductEntity", "id");
		LKDBResource.addColumn("50101001", "SysPssPurchaseOrderProductEntity", "orderId");
		LKDBResource.addColumn("50101002", "SysPssPurchaseOrderProductEntity", "productId");
		LKDBResource.addColumn("50101003", "SysPssPurchaseOrderProductEntity", "quantity");
		LKDBResource.addColumn("50101004", "SysPssPurchaseOrderProductEntity", "sortId");
		LKDBResource.addColumn("50101005", "SysPssPurchaseOrderProductEntity", "inventoryQuantity");
		LKDBResource.addColumn("50101006", "SysPssPurchaseOrderProductEntity", "unitPrice");
		LKDBResource.addColumn("50101007", "SysPssPurchaseOrderProductEntity", "subTotalPrice");
	}

}