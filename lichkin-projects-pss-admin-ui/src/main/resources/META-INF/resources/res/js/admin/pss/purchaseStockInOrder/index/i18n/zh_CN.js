$.LKExtendI18N({
  'purchaseStockInOrder' : {
    'title' : '采购入库单',

    'grid' : {
      'title' : '采购入库单',

      'columns' : {
        'orderNo' : '订单号',
        'storageName' : '入库仓库',
        'billDate' : '订单日期',
        'approvalStatus' : '审批状态',
        'approvalTime' : '审批时间',
        'purchaseOrderNo' : '采购订单',
        'purchaserBillDate' : '采购时间',
        'purchaseOrderAmount' : '采购金额(元)',
        'supplierName' : '供应商',
        'purchaserName' : '采购人',
        'startDate' : '订单开始日期',
        'endDate' : '订单结束日期',
        'scanCode' : '扫码',
        'remarks' : '备注',
      },

      'addFromPurchaseOrder' : '从采购单录入',
      'selectPurchaseOrder' : '选择采购单',
      'add' : '录入订单',
      'edit' : '编辑订单',
      'remove' : '删除订单',
      'submit' : '提交订单',
      'view' : '查看订单',

      'confirm' : {
        'remove' : '数据删除后将不能恢复，确认删除么？',
        'submit' : '数据提交后将不能修改，确认提交么？',
      },

      'no stored products on current purchase order' : '当前采购单无可入库的产品。',
      'product not exists' : '此产品无可入库的数量',
      'only PENDING status can be edit' : '只有草稿状态才可编辑。',
      'only PENDING status can be remove' : '只有草稿状态才可删除。',
      'only PENDING status can be submit' : '只有草稿状态才可提交。',
    },

    'product-grid' : {
      'title' : '产品列表',

      'columns' : {
        'productCode' : '产品编码',
        'productName' : '产品名称',
        'barcode' : '条形码',
        'unit' : '单位',
        'unitPrice' : '单价',
        'purchaseQty' : '采购数量',
        'inventoryQuantity' : '已入库数量',
        'returnedQuantity' : '已退货数量',
        'quantity' : '数量',
        'product' : '选择产品',
      },

      'add' : '录入产品',
      'remove' : '删除产品',
    },

    'purchaseOrder-grid' : {
      'title' : '采购订单列表',

      'columns' : {
        'orderNo' : '订单号',
        'billDate' : '订单日期',
        'inventoryStatus' : '入库状态',
        'INVENTORYSTATUS' : {
          'NOT' : '未入库',
          'PART' : '部分入库',
          'ALL' : '全部入库'
        },
        'supplierName' : '供应商',
        'purchaserName' : '采购人',
        'orderAmount' : '订单金额',
        'startDate' : '订单开始日期',
        'endDate' : '订单结束日期',
      },

    },

  }
});
