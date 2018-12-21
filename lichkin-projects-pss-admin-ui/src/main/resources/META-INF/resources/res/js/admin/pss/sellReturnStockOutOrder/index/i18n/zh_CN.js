$.LKExtendI18N({
  'sellReturnStockOutOrder' : {
    'title' : '销售已出库退货单',

    'grid' : {
      'title' : '销售已出库退货单',

      'columns' : {
        'orderNo' : '订单号',
        'billDate' : '订单日期',
        'returnedName' : '退货员',
        'storageName' : '入库仓库',
        'orderAmount' : '订单金额',
        'approvalStatus' : '审批状态',
        'approvalTime' : '审批时间',
        'startDate' : '订单开始日期',
        'endDate' : '订单结束日期',
        'scanCode' : '条形码',
        'remarks' : '备注',
      },

      'add' : '录入订单',
      'edit' : '编辑订单',
      'remove' : '删除订单',
      'submit' : '提交订单',
      'view' : '查看订单',

      'confirm' : {
        'remove' : '数据删除后将不能恢复，确认删除么？',
        'submit' : '数据提交后将不能修改并修改库存，确认提交么？',
      },

      'product not exists' : '产品不存在，请先录入产品。',
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
        'quantity' : '数量',
        'unitPrice' : '单价(元)',
        'subTotalPrice' : '小计(元)',
        'product' : '选择产品',
      },

      'add' : '录入产品',
      'remove' : '删除产品',
    },

  }
});
