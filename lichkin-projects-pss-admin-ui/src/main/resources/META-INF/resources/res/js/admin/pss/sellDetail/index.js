LK.UI.datagrid($.extend((typeof LK.home == 'undefined' ? {
  title : 'title',
  icon : 'sellDetail',
} : {}), {
  i18nKey : 'sellDetail',
  $appendTo : true,
  cols : 4,
  url : '/SysPssSellOrderProduct/P',
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
        text : 'salesName',
        name : 'salesName',
        width : 150
      }, {
        text : 'productCode',
        name : 'productCode',
        width : 120
      }, {
        text : 'productName',
        name : 'productName',
        width : 120
      }, {
        text : 'barcode',
        name : 'barcode',
        width : 120
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
        text : 'returnedQuantity',
        name : 'returnedQuantity',
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
        plugin : 'textbox',
        options : {
          name : 'salesName',
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
