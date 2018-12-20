$.LKExtendI18N({
  'sellReturnNotStockOutOrder' : {
    'title' : '销售未出库退货单',

    'grid' : {
      'title' : '销售未出库退货单',

      'columns' : {
        'orderNo' : '订单号',
        'returnedName' : '退货人',
        'billDate' : '订单日期',
        'orderAmount' : '订单金额',
        'approvalStatus' : '审批状态',
        'approvalTime' : '审批时间',
        'sellOrderNo' : '销售订单',
        'sellBillDate' : '销售时间',
        'sellOrderAmount' : '销售金额(元)',
        'salesName' : '销售员',
        'startDate' : '订单开始日期',
        'endDate' : '订单结束日期',
        'scanCode' : '扫码',
        'remarks' : '备注',
      },

      'addFromSellOrder' : '从销售单录入',
      'selectSellOrder' : '选择销售单',
      'add' : '录入订单',
      'edit' : '编辑订单',
      'remove' : '删除订单',
      'submit' : '提交订单',
      'view' : '查看订单',

      'confirm' : {
        'remove' : '数据删除后将不能恢复，确认删除么？',
        'submit' : '数据提交后将不能修改，确认提交么？',
      },

      'no stored products on current sell order' : '当前销售单无可出库的产品。',
      'product not exists' : '此产品无可出库的数量',
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
        'subTotalPrice' : '小计(元)',
        'salesQuantity' : '销售数量',
        'inventoryQuantity' : '已出库数量',
        'returnedQuantity' : '已退货数量',
        'quantity' : '数量',
        'product' : '选择产品',
      },

      'add' : '录入产品',
      'remove' : '删除产品',
    },

    'sellOrder-grid' : {
      'title' : '销售订单列表',

      'columns' : {
        'orderNo' : '订单号',
        'billDate' : '订单日期',
        'inventoryStatus' : '出库状态',
        'salesName' : '销售员',
        'orderAmount' : '订单金额',
        'startDate' : '订单开始日期',
        'endDate' : '订单结束日期',
      },
    },

  }
});
