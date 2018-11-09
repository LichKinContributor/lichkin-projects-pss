var otherStockOutOrderFormPlugins = [
    {
      plugin : 'hidden',
      options : {
        name : 'orderType',
        value : false
      }
    }, {
      plugin : 'datepicker',
      options : {
        name : 'billDate',
        validator : true,
        value : today()
      }
    }, {
      plugin : 'droplist',
      options : {
        name : 'storageType',
        param : {
          categoryCode : 'PSS_OTHER_STORAGE_OUT'
        },
        validator : true
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
        name : 'scanCode',
        cols : 3,
        enterKeyClick : function($plugin, values, value, val) {// 扫码录入
          $plugin.LKSetValues('', true);
          if (!val) {
            return;
          }
          var storageId = $plugin.LKGetSiblingPlugin('storageId').LKGetValue();
          if (!storageId) {
            LK.alert('otherStockOutOrder.grid.please select the storage first');
            return;
          }

          LK.ajax({
            url : '/SysPssStockProduct/L',
            data : {
              storageId : storageId,
              barcode : val
            },
            success : function(responseDatas) {
              if (responseDatas && responseDatas.length == 1) {
                var $productList = $plugin.LKGetSiblingPlugin('productList');
                $productList.LKInvokeAddDatas(responseDatas);
              } else {
                LK.alert('otherStockOutOrder.grid.product not exists');
              }
            }
          });
        }
      }
    }, {
      plugin : 'datagrid',
      options : {
        i18nKey : 'otherStockOutOrder.product-grid',
        key : 'otherStockOutOrder.product-grid.title',
        name : 'productList',
        validator : 'datas',
        multiSelect : true,
        valueFieldName : 'random',
        cols : 3,
        rows : 10,
        showSearchButton : false,
        pageable : false,
        withoutFieldKey : true,
        url : '/SysPssOtherStockOrderProduct/L',
        lazy : true,
        $appendTo : $('body'),
        columns : [
            {
              text : 'productCode',
              width : 200,
              name : 'productCode'
            }, {
              text : 'productName',
              width : null,
              name : 'productName'
            }, {
              text : 'barcode',
              width : 120,
              name : 'barcode'
            }, {
              text : 'unit',
              width : 60,
              name : 'unit'
            }, {
              text : 'quantity',
              width : 100,
              formatter : function(rowData) {
                return {
                  plugin : 'numberspinner',
                  options : {
                    name : 'quantity',
                    value : (typeof rowData.quantity != 'undefined') ? rowData.quantity : 1,
                    min : 1
                  }
                }
              }
            }
        ],
        toolsAddData : {
          form : {
            plugins : [
              {
                plugin : 'selector_product',
                options : {
                  name : 'product',
                }
              }
            ]
          },
          dialog : {
            size : {
              cols : 1,
              rows : 1
            }
          },
          handleAddData : function($button, $datagrid, $selecteds, selectedDatas, value, $dialogButton, $dialog, i18nKey, $form) {
            return $form.LKGetSubPlugin('product').LKGetValueDatas();
          },
        },
        toolsRemoveData : {},
      }
    }, {
      plugin : 'textbox',
      options : {
        name : 'remarks',
        maxlength : 512,
        cols : 3,
        rows : 2
      }
    }
];

LK.UI.datagrid($.extend((typeof LK.home == 'undefined' ? {
  title : 'title',
  icon : 'otherStockOutOrder',
} : {}), {
  i18nKey : 'otherStockOutOrder',
  $appendTo : true,
  cols : 3,
  url : '/SysPssOtherStockOrder/P',
  columns : [
      {
        text : 'orderNo',
        width : 260,
        name : 'orderNo',
        cssClass : 'monospacedFont'
      }, {
        text : 'storageType',
        width : 100,
        name : 'storageType'
      }, {
        text : 'storageName',
        width : 100,
        name : 'storageName'
      }, {
        text : 'billDate',
        width : null,
        name : 'billDate'
      }, {
        text : 'approvalStatus',
        width : 100,
        name : 'approvalStatus'
      }, {
        text : 'approvalTime',
        width : 160,
        formatter : function(rowData) {
          return showStandardTime(rowData.approvalTime);
        }
      }
  ],
  toolsAdd : {
    saveUrl : '/SysPssOtherStockOrder/I',
    form : {
      plugins : otherStockOutOrderFormPlugins
    },
    dialog : {
      size : {
        cols : 3,
        rows : 14
      }
    },
    beforeSave : function($button, $datagrid, $selecteds, selectedDatas, value, $dialogButton, $dialog, i18nKey) {
      return {
        productList : $dialog.LKGetSubPlugin('productList').LKGetDataJsonString()
      };
    }
  },
  toolsEdit : {
    saveUrl : '/SysPssOtherStockOrder/U',
    form : {
      url : '/SysPssOtherStockOrder/O',
      plugins : otherStockOutOrderFormPlugins
    },
    dialog : {
      size : {
        cols : 3,
        rows : 14
      }
    },
    beforeSave : function($button, $datagrid, $selecteds, selectedDatas, value, $dialogButton, $dialog, i18nKey) {
      return {
        productList : $dialog.LKGetSubPlugin('productList').LKGetDataJsonString()
      };
    },
    beforeClick : function($button, $datagrid, $selecteds, selectedDatas, value, i18nKey) {
      if (selectedDatas.approvalStatusDictCode != 'PENDING') {
        LK.alert(i18nKey + 'only PENDING status can be edit');
        return false;
      }
      return true;
    },
    readonlyPlugins : function(editJson, formOptions, $datagrid, $selecteds, selectedDatas, value) {
      return [
          'billDate', 'storageType', 'storageId'
      ];
    },
    handleFormOptions : function(editJson, formOptions, $datagrid, $selecteds, selectedDatas, value) {
      LK.UI.formUtils.changeOptions(formOptions.plugins, 'productList', false, {
        lazy : false,
        param : {
          orderId : value
        }
      });
    }
  },
  toolsView : {
    form : {
      url : '/SysPssOtherStockOrder/O',
      plugins : otherStockOutOrderFormPlugins
    },
    dialog : {
      size : {
        cols : 3,
        rows : 13
      }
    },
    handleFormOptions : function(viewJson, formOptions, $datagrid, $selecteds, selectedDatas, value) {
      LK.UI.formUtils.removePlugins(formOptions.plugins, 'scanCode');
      LK.UI.formUtils.changeOptions(formOptions.plugins, 'productList', false, {
        lazy : false,
        param : {
          orderId : value
        },
        tools : []
      });
    }
  },
  toolsRemove : {
    saveUrl : '/SysPssOtherStockOrder/D',
    beforeClick : function($button, $datagrid, $selecteds, selectedDatas, value, i18nKey) {
      for (var i = 0; i < selectedDatas.length; i++) {
        if (selectedDatas[i].approvalStatusDictCode != 'PENDING') {
          LK.alert(i18nKey + 'only PENDING status can be remove');
          return false;
        }
      }
      return true;
    }
  },
  toolsSubmit : {
    saveUrl : '/SysPssOtherStockOrder/U01',
    beforeClick : function($button, $datagrid, $selecteds, selectedDatas, value, i18nKey) {
      for (var i = 0; i < selectedDatas.length; i++) {
        if (selectedDatas[i].approvalStatusDictCode != 'PENDING') {
          LK.alert(i18nKey + 'only PENDING status can be submit');
          return false;
        }
      }
      return true;
    }
  },
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
        plugin : 'droplist',
        options : {
          name : 'storageType',
          param : {
            categoryCode : 'PSS_OTHER_STORAGE_OUT'
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
        plugin : 'droplist',
        options : {
          name : 'approvalStatus',
          param : {
            categoryCode : 'ACTIVITI_APPROVAL_STATUS'
          }
        }
      }

  ]
}));
