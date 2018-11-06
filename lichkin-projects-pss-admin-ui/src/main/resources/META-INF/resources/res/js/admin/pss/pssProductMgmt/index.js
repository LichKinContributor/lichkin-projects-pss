var pssProductMgmtFormPlugins = [
    {
      plugin : 'textbox',
      options : {
        name : 'productName',
        validator : true,
        maxlength : 50
      }
    }, {
      plugin : 'textbox',
      options : {
        name : 'productCode',
        maxlength : 64
      }
    }, {
      plugin : 'textbox',
      options : {
        name : 'barcode',
        maxlength : 64
      }
    }, {
      plugin : 'droplist',
      options : {
        name : 'unit',
        param : {
          categoryCode : 'PSS_PRODUCT_UNIT'
        }
      }
    }, {
      plugin : 'textbox',
      options : {
        name : 'purchasePrice',
        validator : true,
        maxlength : 10,
        onChange : function($plugin, values, value, val) {
          $plugin.LKSetValues(value.replace(/[^0-9.]/g, ''), true);
        },
        value : 0
      }
    }, {
      plugin : 'textbox',
      options : {
        name : 'referencePrice',
        validator : true,
        maxlength : 10,
        onChange : function($plugin, values, value, val) {
          $plugin.LKSetValues(value.replace(/[^0-9.]/g, ''), true);
        },
        value : 0
      }
    }, {
      plugin : 'textbox',
      options : {
        name : 'retailPrice',
        validator : true,
        maxlength : 10,
        onChange : function($plugin, values, value, val) {
          $plugin.LKSetValues(value.replace(/[^0-9.]/g, ''), true);
        },
        value : 0
      }
    }, '-', {
      plugin : 'selector_product',
      options : {
        name : 'subProduct',
      }
    }, {
      plugin : 'numberspinner',
      options : {
        name : 'subProductCount',
        min : 1
      }
    }, '-', '-', {
      plugin : 'textbox',
      options : {
        name : 'remarks',
        cols : 4,
        rows : 3,
        maxlength : 500
      }
    }, {
      plugin : 'cropper',
      options : {
        name : 'imageUrl1',
        cols : 1,
        rows : 4
      }
    }, {
      plugin : 'cropper',
      options : {
        name : 'imageUrl2',
        cols : 1,
        rows : 4
      }
    }, {
      plugin : 'cropper',
      options : {
        name : 'imageUrl3',
        cols : 1,
        rows : 4
      }
    }, {
      plugin : 'cropper',
      options : {
        name : 'imageUrl4',
        cols : 1,
        rows : 4
      }
    }
];

var $tree = LK.UI.tree({
  $appendTo : $('#pssProductMgmt'),
  name : 'pssProductCategroyTree',
  title : 'pssProductMgmt.pssProductCategroyTreeTitle',
  cols : 1,
  checkbox : false,
  cancelable : false,
  i18nText : false,
  multiSelect : false,
  showSearchButton : true,
  style : {
    'border-top' : 'none'
  },
  url : '/SysPssProductCategory/S',
  linkages : [
    'pssProductGrid'
  ]
});

LK.UI.datagrid({
  i18nKey : 'pssProductMgmt',
  $appendTo : $('#pssProductMgmt'),
  name : 'pssProductGrid',
  title : 'title',
  cols : 3,
  onLinkaged : function($plugin, linkage) {
    switch (linkage.linkageName) {
      case 'pssProductCategroyTree':
        if (linkage.linkageValue == '') {
          $plugin.LKClearDatas();
        } else {
          $plugin.LKLoad({
            param : {
              productCategory : linkage.linkageValue
            }
          }, linkage);
        }
        break;
    }
  },
  reloadParam : function($plugin, param) {
    param.productCategory = $plugin.LKGetSiblingPlugin('pssProductCategroyTree').LKGetValue();
    return param;
  },
  pageable : false,
  url : '/SysPssProduct/L',
  title : 'title',
  columns : [
      {
        text : 'productName',
        width : '1/3',
        name : 'productName'
      }, {
        text : 'productCode',
        width : '1/3',
        name : 'productCode'
      }, {
        text : 'unit',
        width : 60,
        name : 'unit'
      }, {
        text : 'barcode',
        width : '1/3',
        name : 'barcode'
      }, {
        text : 'retailPrice',
        width : 80,
        name : 'retailPrice'
      }
  ],
  toolsAdd : {
    beforeClick : function($button, $datagrid, $selecteds, selectedDatas, value) {
      var productCategory = $datagrid.LKGetSiblingPlugin('pssProductCategroyTree').LKGetValue();
      if (productCategory == '') {
        LK.alert('pssProductMgmt.noCategorySelect');
        return false;
      }
      return true;
    },
    beforeSave : function($button, $datagrid, $selecteds, selectedDatas, value, $dialogButton, $dialog) {
      return {
        productCategory : $datagrid.LKGetSiblingPlugin('pssProductCategroyTree').LKGetValue()
      };
    },
    saveUrl : '/SysPssProduct/I',
    dialog : {
      size : {
        cols : 4,
        rows : 10
      }
    },
    form : {
      plugins : pssProductMgmtFormPlugins
    }
  },
  toolsEdit : {
    saveUrl : '/SysPssProduct/U',
    dialog : {
      size : {
        cols : 4,
        rows : 10
      }
    },
    form : {
      plugins : pssProductMgmtFormPlugins,
      url : '/SysPssProduct/O'
    }
  },
  toolsRemove : {
    saveUrl : '/SysPssProduct/US'
  },
  toolsView : {
    dialog : {
      size : {
        cols : 4,
        rows : 10
      }
    },
    form : {
      plugins : pssProductMgmtFormPlugins,
      url : '/SysPssProduct/O'
    }
  },
  searchForm : [
      {
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
});
