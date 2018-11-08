$.LKExtendI18N({
  'otherStockOutOrder' : {
    'title' : '其他出库单',

    'grid' : {
      'title' : '其他出库单',

      'columns' : {
        'orderNo' : '订单号',
        'storageType' : '出库类型',
        'storageName' : '仓库',
        'billDate' : '订单日期',
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

      'please select the storage first' : '请先选择仓库。',
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
        'stockQuantity' : '库存数量',
        'quantity' : '数量',
        'product' : '选择产品',
      }
    },

  }
});
