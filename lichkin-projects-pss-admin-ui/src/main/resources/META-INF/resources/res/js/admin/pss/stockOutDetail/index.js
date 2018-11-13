LK.UI.datagrid($.extend((typeof LK.home == 'undefined' ? {
  title : 'title',
  icon : 'stockOutDetail',
} : {}), {
  i18nKey : 'stockOutDetail',
  $appendTo : true,
  cols : 5,
  url : '/SysPssStock/P01',
  columns : [
      {
        text : 'billDate',
        name : 'billDate',
        width : 120
      }, {
        text : 'orderNo',
        name : 'orderNo',
        width : 260
      }, {
        text : 'storageType',
        name : 'storageType',
        width : 120
      }, {
        text : 'storageName',
        name : 'storageName',
        width : null
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
        width : 120
      }, {
        text : 'quantity',
        name : 'quantity',
        width : 120
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
          name : 'barcode',
          cls : 'fuzzy-left fuzzy-right'
        }
      }, {
        plugin : 'droplist',
        options : {
          key : 'storageName',
          name : 'storageId',
          url : '/SysPssStorage/LD'
        }
      }, {
        plugin : 'droplist',
        options : {
          name : 'storageType',
          param : {
            categoryCode : 'PSS_STORAGE_OUT_TYPE'
          }
        }
      }
  ]
}));
