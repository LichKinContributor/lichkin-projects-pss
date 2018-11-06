LK.UI.datagrid($.extend((typeof LK.home == 'undefined' ? {
  title : 'title',
  icon : 'pssStoreCashierMgmt',
} : {}), {
  i18nKey : 'pssStoreCashierMgmt',
  $appendTo : true,
  cols : 2.5,
  url : '/SysPssStoreCashier/P',
  columns : [
      {
        text : 'storeName',
        width : '1/2',
        name : 'storeName'
      }, {
        text : 'cashier',
        width : '1/2',
        name : 'cashierName'
      }
  ],
  toolsAdd : {
    saveUrl : '/SysPssStoreCashier/I',
    dialog : {
      size : {
        cols : 1,
        rows : 2
      }
    },
    form : {
      plugins : [
          {
            plugin : 'droplist',
            options : {
              key : 'storeName',
              name : 'storeId',
              url : '/SysPssStore/LD',
              validator : true
            }
          }, {
            plugin : 'selector_employee',
            options : {
              name : 'cashier',
              validator : true,
            }
          }
      ]
    }
  },
  toolsRemove : {
    saveUrl : '/SysPssStoreCashier/D'
  },
  searchForm : [
      {
        plugin : 'textbox',
        options : {
          key : 'storeName',
          name : 'storeName',
          cls : 'fuzzy-left fuzzy-right'
        }
      }, {
        plugin : 'textbox',
        options : {
          key : 'cashier',
          name : 'cashierName',
          cls : 'fuzzy-left fuzzy-right'
        }
      }
  ]
}));
