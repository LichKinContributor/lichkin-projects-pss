var checkOrderFormPlugins = [
    {
      plugin : 'datepicker',
      options : {
        name : 'billDate',
        validator : true,
        value : today()
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
            LK.alert('checkOrder.grid.please choose the storage');
            return;
          }

          LK.ajax({
            url : '/SysPssStock/L',
            data : {
              storageId : storageId,
              barcode : val
            },
            success : function(responseDatas) {
              if (responseDatas && responseDatas.length == 1) {
                var $productList = $plugin.LKGetSiblingPlugin('productList');
                $productList.LKInvokeAddDatas(responseDatas);
              } else {
                LK.alert('checkOrder.grid.this product does not exist in the current storage');
              }
            }
          });

        }
      }
    }, {
      plugin : 'datagrid',
      options : {
        i18nKey : 'checkOrder.product-grid',
        key : 'checkOrder.product-grid.title',
        name : 'productList',
        validator : 'datas',
        multiSelect : true,
        valueFieldName : 'random',
        cols : 3,
        rows : 10,
        showSearchButton : false,
        pageable : false,
        withoutFieldKey : true,
        url : '/SysPssStockCheckOrderProduct/L',
        lazy : true,
        $appendTo : $('body'),
        columns : [
            {
              text : 'productCode',
              width : 180,
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
              text : 'stockQuantity',
              width : 80,
              name : 'stockQuantity'
            }, {
              text : 'quantity',
              width : 80,
              formatter : function(rowData) {
                return {
                  plugin : 'numberspinner',
                  options : {
                    name : 'quantity',
                    value : (typeof rowData.quantity != 'undefined') ? rowData.quantity : 0,
                    min : 0,
                    onChange : function($plugin, values, value, val) { // 产品数量值改变
                      // 系统数量
                      var stockQuantity = rowData.stockQuantity;
                      if (stockQuantity) {
                        var currentCount = parseInt(val) - parseInt(stockQuantity);
                        $plugin.LKGetSiblingPlugin('differenceQuantity').LKSetValues(currentCount, true);
                      } else {
                        $plugin.LKGetSiblingPlugin('differenceQuantity').LKSetValues(0, true);
                      }
                    }
                  }
                }
              }
            }, {
              text : 'differenceQuantity',
              width : 80,
              formatter : function(rowData) {
                return {
                  plugin : 'textbox',
                  options : {
                    name : 'differenceQuantity',
                    value : (typeof rowData.differenceQuantity != 'undefined') ? rowData.differenceQuantity : 0,
                    readonly : true
                  }
                }
              }
            }
        ],
        toolsAddData : {
          beforeClick : function($button, $datagrid, $selecteds, selectedDatas, value, i18nKey) {
            var storageId = $datagrid.LKGetSiblingPlugin('storageId').LKGetValue();
            if (!storageId) {
              LK.alert('checkOrder.grid.please choose the storage');
              return false;
            }
            return true;
          },
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
            var storageId = $datagrid.LKGetSiblingPlugin('storageId').LKGetValue();
            var productDatas = $form.LKGetSubPlugin('product').LKGetValueDatas();
            var notExist = false;
            for (var i = 0; i < productDatas.length; i++) {
              var prod = productDatas[i];
              LK.ajax({
                url : '/SysPssStock/L',
                async : false,
                data : {
                  storageId : storageId,
                  productId : prod.id
                },
                success : function(responseDatas) {
                  if (responseDatas && responseDatas.length == 1) {
                    productDatas[i].stockQuantity = responseDatas[0].stockQuantity;
                  } else {
                    notExist = true;
                  }
                }
              });
              if (notExist) {
                LK.alert('checkOrder.grid.this product does not exist in the current storage');
                return [];
              }
            }
            return productDatas;
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

var $checkOrderDatagrid = LK.UI.datagrid($.extend((typeof LK.home == 'undefined' ? {
  title : 'title',
  icon : 'checkOrder',
} : {}), {
  i18nKey : 'checkOrder',
  $appendTo : true,
  cols : 4,
  url : '/SysPssStockCheckOrder/P',
  columns : [
      {
        text : 'orderNo',
        width : 260,
        name : 'orderNo',
        cssClass : 'monospacedFont'
      }, {
        text : 'billDate',
        width : 120,
        name : 'billDate'
      }, {
        text : 'storageName',
        name : 'storageName',
        width : 200
      }, {
        text : 'stockCheckCount',
        name : 'stockCheckCount',
        width : 100
      }, {
        text : 'usingStatus',
        width : 120,
        name : 'usingStatus'
      }, {
        text : 'approvalStatus',
        width : 120,
        name : 'approvalStatus'
      }, {
        text : 'approvalTime',
        width : null,
        formatter : function(rowData) {
          return showStandardTime(rowData.approvalTime);
        }
      }
  ],
  toolsAdd : {
    saveUrl : '/SysPssStockCheckOrder/I',
    form : {
      plugins : checkOrderFormPlugins
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
    saveUrl : '/SysPssStockCheckOrder/U',
    form : {
      url : '/SysPssStockCheckOrder/O',
      plugins : checkOrderFormPlugins
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
      if (selectedDatas.usingStatusDictCode != 'STAND_BY') {
        LK.alert(i18nKey + 'only STAND_BY status can be edit');
        return false;
      }
      return true;
    },
    readonlyPlugins : function(editJson, formOptions, $datagrid, $selecteds, selectedDatas, value) {
      return [
          'billDate', 'storageId'
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
      url : '/SysPssStockCheckOrder/O',
      plugins : checkOrderFormPlugins
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
    saveUrl : '/SysPssStockCheckOrder/D',
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
    saveUrl : '/SysPssStockCheckOrder/U01',
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
  toolsUS : {
    icon : 'save',
    text : 'hold',
    saveUrl : '/SysPssStockCheckOrder/US',
    beforeClick : function($button, $datagrid, $selecteds, selectedDatas, value, i18nKey) {
      for (var i = 0; i < selectedDatas.length; i++) {
        if (selectedDatas[i].usingStatusDictCode != 'STAND_BY') {
          LK.alert(i18nKey + 'only STAND_BY status can be hold');
          return false;
        }
      }
      return true;
    },
    usingStatus : 'USING'
  },
  searchForm : [
      {
        plugin : 'textbox',
        options : {
          name : 'orderNo',
          cls : 'fuzzy-left fuzzy-right'
        }
      }, {
        plugin : 'droplist',
        options : {
          key : 'storageName',
          name : 'storageId',
          url : '/SysPssStorage/LD',
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
          name : 'usingStatus',
          data : [
              {
                value : 'STAND_BY',
                text : $.LKGetI18N('checkOrder.grid.columns.USING_STATUS.STAND_BY')
              }, {
                value : 'USING',
                text : $.LKGetI18N('checkOrder.grid.columns.USING_STATUS.USING')
              }
          ]
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
