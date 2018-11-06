var pssStoreMgmtFormPlugins = [
    {
      plugin : 'textbox',
      options : {
        name : 'storeName',
        validator : true,
        maxlength : 32
      }
    }, {
      plugin : 'droplist',
      options : {
        key : 'storageName',
        name : 'storageId',
        url : '/SysPssStorage/LD',
        validator : true
      }
    }, {
      plugin : 'textbox',
      options : {
        name : 'storeCode',
        maxlength : 64
      }
    }, {
      plugin : 'selector_employee',
      options : {
        name : 'responsiblePerson',
      }
    }, {
      plugin : 'textbox',
      options : {
        name : 'address',
        maxlength : 50,
        cols : 2
      }
    }, {
      plugin : 'textbox',
      options : {
        name : 'remarks',
        cols : 2,
        rows : 2,
        maxlength : 512
      }
    }
];

LK.UI.datagrid($.extend((typeof LK.home == 'undefined' ? {
  title : 'title',
  icon : 'pssStoreMgmt',
} : {}), {
  i18nKey : 'pssStoreMgmt',
  $appendTo : true,
  cols : 4,
  url : '/SysPssStore/P',
  columns : [
      {
        text : 'storeName',
        width : 200,
        name : 'storeName'
      }, {
        text : 'storeCode',
        width : 200,
        name : 'storeCode'
      }, {
        text : 'responsiblePerson',
        width : 120,
        name : 'responsiblePerson'
      }, {
        text : 'cellphone',
        width : 100,
        name : 'cellphone'
      }, {
        text : 'address',
        width : 400,
        name : 'address',
        textAlign : 'left'
      }, {
        text : 'storageName',
        width : 150,
        name : 'storageName'
      }, {
        text : 'remarks',
        width : 500,
        name : 'remarks',
        textAlign : 'left'
      }
  ],
  toolsAdd : {
    saveUrl : '/SysPssStore/I',
    dialog : {
      size : {
        cols : 2,
        rows : 5
      }
    },
    form : {
      plugins : pssStoreMgmtFormPlugins
    }
  },
  toolsEdit : {
    saveUrl : '/SysPssStore/U',
    dialog : {
      size : {
        cols : 2,
        rows : 5
      }
    },
    form : {
      plugins : pssStoreMgmtFormPlugins,
      url : '/SysPssStore/O'
    }
  },
  toolsRemove : {
    saveUrl : '/SysPssStore/US'
  },
  searchForm : [
      {
        plugin : 'textbox',
        options : {
          name : 'storeName',
          cls : 'fuzzy-left fuzzy-right'
        }
      }, {
        plugin : 'textbox',
        options : {
          name : 'storeCode',
          cls : 'fuzzy-left fuzzy-right'
        }
      }
  ]
}));
