LK.UI.datagrid($.extend((typeof LK.home == 'undefined' ? {
  title : 'title',
  icon : 'stockInDetail',
} : {}), {
  i18nKey : 'stockInDetail',
  $appendTo : true,
  cols : 4,
  url : '/SysPssStock/P01',
  columns : [
      {
        text : 'orderNo',
        name : 'orderNo',
        width : 260
      }, {
        text : 'billDate',
        name : 'billDate',
        width : 100
      }, {
        text : 'storageType',
        name : 'storageType',
        width : 120
      }, {
        text : 'storageName',
        name : 'storageName',
        width : 150
      }, {
        text : 'productCode',
        name : 'productCode',
        width : 180
      }, {
        text : 'productName',
        name : 'productName',
        width : 120
      }, {
        text : 'barcode',
        name : 'barcode',
        width : 180
      }, {
        text : 'unit',
        name : 'unit',
        width : 80
      }, {
        text : 'quantity',
        name : 'quantity',
        width : 80
      }
  ],
  searchForm : [
      {
        plugin : 'hidden',
        options : {
          name : 'orderType',
          value : true
        }
      }, {
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
        plugin : 'droplist',
        options : {
          name : 'storageType',
          param : {
            categoryCode : 'PSS_STOCK_IN_TYPE'
          }
        }
      }, {
        plugin : 'droplist',
        options : {
          key : 'storageName',
          name : 'storageId',
          url : '/SysPssStorage/LD'
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
      }
  ]
}));
