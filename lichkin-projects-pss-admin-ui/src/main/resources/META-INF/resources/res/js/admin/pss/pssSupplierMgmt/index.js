var pssSupplierMgmtFormPlugins = [
    {
      plugin : 'droplist',
      options : {
        name : 'supplierType',
        validator : true,
        param : {
          categoryCode : 'PSS_SUPPLIER_TYPE'
        }
      }
    }, {
      plugin : 'textbox',
      options : {
        name : 'supplierName',
        validator : true,
        maxlength : 32
      }
    }, {
      plugin : 'textbox',
      options : {
        name : 'supplierCode',
        maxlength : 64
      }
    }, {
      plugin : 'textbox',
      options : {
        name : 'linkmanName',
        maxlength : 32
      }
    }, {
      plugin : 'textbox',
      options : {
        name : 'linkmanContactWay',
        maxlength : 100
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
  icon : 'pssSupplierMgmt',
} : {}), {
  i18nKey : 'pssSupplierMgmt',
  $appendTo : true,
  cols : 4,
  url : '/SysPssSupplier/P',
  columns : [
      {
        text : 'supplierType',
        name : 'supplierType',
        width : 80
      }, {
        text : 'supplierName',
        name : 'supplierName',
        width : 200
      }, {
        text : 'supplierCode',
        name : 'supplierCode',
        width : 200
      }, {
        text : 'linkmanName',
        name : 'linkmanName',
        width : 120
      }, {
        text : 'linkmanContactWay',
        name : 'linkmanContactWay',
        width : 200
      }, {
        text : 'responsiblePerson',
        name : 'responsiblePerson',
        width : 120
      }, {
        text : 'cellphone',
        name : 'cellphone',
        width : 100
      }, {
        text : 'address',
        name : 'address',
        width : 400,
        textAlign : 'left'
      }, {
        text : 'remarks',
        name : 'remarks',
        width : 500,
        textAlign : 'left'
      }
  ],
  toolsAdd : {
    saveUrl : '/SysPssSupplier/I',
    dialog : {
      size : {
        cols : 3,
        rows : 5
      }
    },
    form : {
      plugins : pssSupplierMgmtFormPlugins
    }
  },
  toolsEdit : {
    saveUrl : '/SysPssSupplier/U',
    dialog : {
      size : {
        cols : 3,
        rows : 5
      }
    },
    form : {
      plugins : pssSupplierMgmtFormPlugins,
      url : '/SysPssSupplier/O'
    }
  },
  toolsRemove : {
    saveUrl : '/SysPssSupplier/US'
  },
  searchForm : [
      {
        plugin : 'droplist',
        options : {
          name : 'supplierType',
          param : {
            categoryCode : 'PSS_SUPPLIER_TYPE'
          }
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
          name : 'supplierCode',
          cls : 'fuzzy-left fuzzy-right'
        }
      }
  ]
}));
