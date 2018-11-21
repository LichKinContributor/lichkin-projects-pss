LK.UI.datagrid($.extend((typeof LK.home == 'undefined' ? {
  title : 'title',
  icon : 'purchaseDetail',
} : {}), {
  i18nKey : 'purchaseDetail',
  $appendTo : true,
  cols : 5,
  url : '/SysPssPurchaseOrderProduct/P',
  columns : [
      {
        text : 'billDate',
        name : 'billDate',
        width : 100
      }, {
        text : 'orderNo',
        name : 'orderNo',
        width : 260
      }, {
        text : 'supplierName',
        name : 'supplierName',
        width : null
      }, {
        text : 'purchaserName',
        name : 'purchaserName',
        width : 120
      }, {
        text : 'productCode',
        name : 'productCode',
        width : 100
      }, {
        text : 'productName',
        name : 'productName',
        width : 100
      }, {
        text : 'barcode',
        name : 'barcode',
        width : 100
      }, {
        text : 'unit',
        name : 'unit',
        width : 80
      }, {
        text : 'quantity',
        name : 'quantity',
        width : 80
      }, {
        text : 'inventoryQuantity',
        name : 'inventoryQuantity',
        width : 80
      }, {
        text : 'unitPrice',
        name : 'unitPrice',
        width : 80
      }, {
        text : 'subTotalPrice',
        name : 'subTotalPrice',
        width : 80
      }
  ],
  searchForm : [
      {
        plugin : 'textbox',
        options : {
          name : 'orderNo',
          cls : 'fuzzy-left fuzzy-right'
        }
      }, {
        plugin : 'datepicker',
        options : {
          name : 'startDate'
        }
      }, {
        plugin : 'datepicker',
        options : {
          name : 'endDate'
        }
      }, {
        plugin : 'textbox',
        options : {
          name : 'productCode',
          cls : 'fuzzy-left fuzzy-right'
        }
      }, {
        plugin : 'textbox',
        options : {
          name : 'productName',
          cls : 'fuzzy-left fuzzy-right'
        }
      }, {
        plugin : 'textbox',
        options : {
          name : 'barcode'
        }
      }, {
        plugin : 'textbox',
        options : {
          name : 'supplierName',
          cls : 'fuzzy-left fuzzy-right'
        }
      }, {
        plugin : 'textbox',
        options : {
          name : 'purchaserName',
          cls : 'fuzzy-left fuzzy-right'
        }
      }
  ]
}));
