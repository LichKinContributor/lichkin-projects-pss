LK.UI.datagrid($.extend((typeof LK.home == 'undefined' ? {
  title : 'title',
  icon : 'stockDetail',
} : {}), {
  i18nKey : 'stockDetail',
  $appendTo : true,
  cols : 4,
  url : '/SysPssStock/P',
  columns : [
      {
        text : 'storageName',
        width : '1/5',
        name : 'storageName'
      }, {
        text : 'storageCode',
        width : '1/5',
        name : 'storageCode'
      }, {
        text : 'productName',
        width : '1/5',
        name : 'productName'
      }, {
        text : 'productCode',
        width : '1/5',
        name : 'productCode'
      }, {
        text : 'barcode',
        width : '1/5',
        name : 'barcode'
      }, {
        text : 'quantity',
        width : 80,
        name : 'quantity'
      }, {
        text : 'canOutQty',
        width : 90,
        name : 'canOutQty'
      }
  ],
  searchForm : [
      {
        plugin : 'textbox',
        options : {
          name : 'storageName',
          cls : 'fuzzy-left fuzzy-right'
        }
      }, {
        plugin : 'textbox',
        options : {
          name : 'storageCode',
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
          name : 'productCode',
          cls : 'fuzzy-left fuzzy-right'
        }
      }, {
        plugin : 'textbox',
        options : {
          name : 'barcode',
        }
      }
  ]
}));
