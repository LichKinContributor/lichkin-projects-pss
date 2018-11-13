LK.UI.datagrid($.extend((typeof LK.home == 'undefined' ? {
  title : 'title',
  icon : 'sellDetail',
} : {}), {
  i18nKey : 'sellDetail',
  $appendTo : true,
  cols : 5,
  url : '/SysPssSellOrderProduct/P',
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
        text : 'salesName',
        name : 'salesName',
        width : null
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
        width : 100
      }, {
        text : 'quantity',
        name : 'quantity',
        width : 100
      }, {
        text : 'inventoryQuantity',
        name : 'inventoryQuantity',
        width : 100
      }, {
        text : 'unitPrice',
        name : 'unitPrice',
        width : 100
      }, {
        text : 'subTotalPrice',
        name : 'subTotalPrice',
        width : 100
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
          name : 'barcode',
          cls : 'fuzzy-left fuzzy-right'
        }
      }, {
        plugin : 'textbox',
        options : {
          name : 'salesName',
          cls : 'fuzzy-left fuzzy-right'
        }
      }
  ]
}));
