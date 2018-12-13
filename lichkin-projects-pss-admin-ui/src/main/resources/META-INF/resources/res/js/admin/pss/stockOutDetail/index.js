LK.UI.datagrid($.extend((typeof LK.home == 'undefined' ? {
  title : 'title',
  icon : 'stockOutDetail',
} : {}), {
  i18nKey : 'stockOutDetail',
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
        width : 80,
        formatter : function(rowData) {
          if (rowData.quantity < 0) {
            return Math.abs(rowData.quantity) + '';
          }
          return rowData.quantity + '';
        }
      }
  ],
  searchForm : [
      {
        plugin : 'hidden',
        options : {
          name : 'orderType',
          value : false
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
          name : 'startDate',
          minDate : '1900-01-01',
        }
      }, {
        plugin : 'datepicker',
        options : {
          name : 'endDate',
          minDate : '1900-01-01',
        }
      }, {
        plugin : 'droplist',
        options : {
          name : 'storageType',
          param : {
            categoryCode : 'PSS_STOCK_OUT_TYPE'
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
