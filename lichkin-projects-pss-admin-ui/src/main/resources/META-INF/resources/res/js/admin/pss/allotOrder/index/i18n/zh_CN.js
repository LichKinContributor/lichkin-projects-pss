$.LKExtendI18N({
  'allotOrder' : {
    'title' : '调拨单',

    'grid' : {
      'title' : '调拨单',

      'columns' : {
        'orderNo' : '订单编号',
        'billDate' : '调拨日期',
        'outStorageName' : '调出仓库',
        'inStorageName' : '调入仓库',
        'approvalStatus' : '审批状态',
        'approvalTime' : '审批时间',
        'startDate' : '订单开始日期',
        'endDate' : '订单结束日期',
        'scanCode' : '扫码',
        'remarks' : '备注',
      },

      'add' : '录入订单',
      'edit' : '编辑订单',
      'remove' : '删除订单',
      'submit' : '提交订单',
      'view' : '查看订单',

      'confirm' : {
        'remove' : '数据删除后将不能恢复，确认删除么？',
        'submit' : '数据提交后将不能修改，确认提交么？',
      },

      'please choose the storage' : '请选择调出仓库。',
      'the number of products currently available is zero' : '当前产品可出库的数量为0。',
      'this product does not exist in the storage' : '调出仓库中不存在此产品。',
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
        'stockQuantity' : '库存数量',
        'canOutQuantity' : '可用库存数量',
        'quantity' : '调拨数量',
        'product' : '选择产品',
      },

      'add' : '录入产品',
      'remove' : '删除产品',
    },

  }
});
