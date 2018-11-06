var pssStorageMgmtFormPlugins = [
    {
      plugin : 'textbox',
      options : {
        name : 'storageName',
        validator : true,
        maxlength : 32
      }
    }, {
      plugin : 'textbox',
      options : {
        name : 'storageCode',
        maxlength : 64
      }
    }, {
      plugin : 'selector_employee',
      options : {
        name : 'responsiblePerson',
        maxlength : 64
      }
    }, {
      plugin : 'textbox',
      options : {
        name : 'address',
        cols : 3,
        maxlength : 50
      }
    }, {
      plugin : 'textbox',
      options : {
        name : 'remarks',
        cols : 3,
        rows : 2,
        maxlength : 512
      }
    }
];

LK.UI.datagrid($.extend((typeof LK.home == 'undefined' ? {
  title : 'title',
  icon : 'pssStorageMgmt',
} : {}), {
  i18nKey : 'pssStorageMgmt',
  $appendTo : true,
  cols : 4,
  url : '/SysPssStorage/P',
  columns : [
      {
        text : 'storageName',
        width : 200,
        name : 'storageName'
      }, {
        text : 'storageCode',
        width : 200,
        name : 'storageCode'
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
        text : 'remarks',
        width : 500,
        name : 'remarks',
        textAlign : 'left'
      }
  ],
  toolsAdd : {
    saveUrl : '/SysPssStorage/I',
    dialog : {
      size : {
        cols : 3,
        rows : 4
      }
    },
    form : {
      plugins : pssStorageMgmtFormPlugins
    }
  },
  toolsEdit : {
    saveUrl : '/SysPssStorage/U',
    dialog : {
      size : {
        cols : 3,
        rows : 4
      }
    },
    form : {
      plugins : pssStorageMgmtFormPlugins,
      url : '/SysPssStorage/O'
    }
  },
  toolsRemove : {
    saveUrl : '/SysPssStorage/US'
  },
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
      }
  ]
}));
