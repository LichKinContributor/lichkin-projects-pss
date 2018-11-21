$.LKExtendI18N({
  'checkOrder' : {
    'title' : '盘点单',

    'grid' : {
      'title' : '盘点单',

      'columns' : {
        'orderNo' : '订单编号',
        'billDate' : '盘点日期',
        'storageName' : '盘点仓库',
        'stockCheckCount' : '盘点产品数',
        'usingStatus' : '盘点状态',
        'approvalStatus' : '审批状态',
        'approvalTime' : '审批时间',
        'startDate' : '订单开始日期',
        'endDate' : '订单结束日期',
        'scanCode' : '扫码',
        'remarks' : '备注',
      },

      'add' : '录入订单',
      'edit' : '编辑订单',
      'hold' : '盘点结束',
      'remove' : '删除订单',
      'submit' : '提交订单',
      'view' : '查看订单',

      'confirm' : {
        'remove' : '数据删除后将不能恢复，确认删除么？',
        'hold' : '数据暂存后将不能修改，确认暂存么？',
        'submit' : '数据提交后将会根据盘点数据修正库存，确认提交么？',
      },

      'please choose the storage' : '请选择盘点仓库。',
      'product already exists' : '产品已存在。',
      'this product does not exist in the current storage' : '当前仓库不存在此产品。',
      'only STAND_BY status can be edit' : '只有盘点中状态才可编辑。',
      'only STAND_BY status can be hold' : '只有盘点中状态才可盘点结束。',
      'only STAND_BY status can be remove' : '只有盘点中状态才可删除。',
      'only USING status can be submit' : '只有盘点结束状态才可提交。',
      'only PENDING status can be submit' : '只有草稿状态才可提交。',
      'only today order can be submit' : '只有当日盘点单才可提交。',
    },

    'product-grid' : {
      'title' : '产品列表',

      'columns' : {
        'productCode' : '产品编码',
        'productName' : '产品名称',
        'barcode' : '条形码',
        'unit' : '单位',
        'quantity' : '盘点数量',
        'stockQuantity' : '系统数量',
        'differenceQuantity' : '盘盈盘亏',
        'product' : '选择产品',
      },

      'add' : '录入产品',
      'remove' : '删除产品',
    },

  }
});
