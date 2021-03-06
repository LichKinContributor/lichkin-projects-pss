var checkOrderFormPlugins = [
    {
      plugin : 'datepicker',
      options : {
        name : 'billDate',
        validator : true,
        value : today(),
        readonly : true,
        commitable : true,
      }
    }, {
      plugin : 'droplist',
      options : {
        key : 'storageName',
        name : 'storageId',
        url : '/SysPssStorage/LD',
        validator : true,
        linkages : [
          'productList'
        ]
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
              orderType : 'PSS_CHECK',
              storageId : storageId,
              barcode : val
            },
            success : function(responseDatas) {
              if (responseDatas && responseDatas.length == 1) {
                var $productList = $plugin.LKGetSiblingPlugin('productList');
                responseDatas[0].differenceQuantity = 0 - responseDatas[0].stockQuantity;
                $productList.LKInvokeAddDatas(responseDatas);
                checkOrderQtyChange($productList, responseDatas[0], 0);
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
        $appendTo : $body,
        onLinkaged : function($plugin, linkage) {
          $plugin.LKClearDatas(false, true);
        },
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
              formatter : function(rowData, $datagrid) {
                return {
                  plugin : 'numberspinner',
                  options : {
                    name : 'quantity',
                    value : (typeof rowData.quantity != 'undefined') ? rowData.quantity : 0,
                    min : 0,
                    onChange : function($plugin, values, value, currentValue) { // 产品数量值改变
                      if (!currentValue) {
                        currentValue = 0;
                      }
                      checkOrderQtyChange($datagrid, rowData, currentValue);
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

            var returnDatas = [];
            var notExist = false;
            for (var i = 0; i < productDatas.length; i++) {
              var prod = productDatas[i];
              LK.ajax({
                url : '/SysPssStock/L',
                async : false,
                data : {
                  orderType : 'PSS_CHECK',
                  storageId : storageId,
                  productId : prod.id
                },
                success : function(responseDatas) {
                  if (responseDatas && responseDatas.length == 1) {
                    responseDatas[0].differenceQuantity = 0 - responseDatas[0].stockQuantity;
                    returnDatas.push(responseDatas[0]);
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
            return returnDatas;
          },
          afterAddData : function($button, $datagrid, $selecteds, selectedDatas, value, $dialogButton, $dialog, i18nKey, $form) {
            var storageId = $datagrid.LKGetSiblingPlugin('storageId').LKGetValue();
            var productDatas = $form.LKGetSubPlugin('product').LKGetValueDatas();
            for (var i = 0; i < productDatas.length; i++) {
              var prod = productDatas[i];
              LK.ajax({
                url : '/SysPssStock/L',
                async : false,
                data : {
                  orderType : 'PSS_CHECK',
                  storageId : storageId,
                  productId : prod.id
                },
                success : function(responseDatas) {
                  if (responseDatas && responseDatas.length == 1) {
                    responseDatas[0].differenceQuantity = 0 - responseDatas[0].stockQuantity;
                    checkOrderQtyChange($datagrid, responseDatas[0], 0);
                  }
                }
              });
            }
          }
        },
        toolsRemoveData : {
          afterRemove : function($button, $datagrid, $selecteds, selectedDatas, value, i18nKey) {
            for (var i = 0; i < selectedDatas.length; i++) {
              checkOrderQtyChange($datagrid, selectedDatas[i], 0);
            }
          }
        },
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

var checkProdExists = function($datagrid, addProd) {
  var exists = false;
  var gridDatas = $datagrid.LKGetDatas();
  // 判断grid中是否存在此产品
  $(gridDatas).each(function() {
    if (this.id == addProd.id) {
      exists = true;
      return false;
    }
  });
  return exists;
}

var checkOrderQtyChange = function($datagrid, rowData, currentValue) {
  // 系统数量
  var stockQuantity = rowData.stockQuantity;
  var $allRows = $datagrid.LKGetDataContainer().find('tr');
  var checkQty = parseInt(currentValue);
  $allRows.each(function() {
    var data = $(this).data();
    if (rowData.id == data.id && rowData.random != data.random) {
      checkQty += parseInt($(this).LKGetSubPlugin('quantity').LKGetValue());
    }
  });

  var differenceQuantity = checkQty - parseInt(stockQuantity);
  $allRows.each(function() {
    var data = $(this).data();
    if (rowData.id == data.id) {
      $(this).LKGetSubPlugin('differenceQuantity').LKSetValues(differenceQuantity, true);
    }
  });
}

LK.UI.datagrid($.extend((typeof LK.home == 'undefined' ? {
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
        width : '1/2'
      }, {
        text : 'stockCheckCount',
        name : 'stockCheckCount',
        width : 120
      }, {
        text : 'usingStatus',
        width : 120,
        name : 'usingStatus'
      }, {
        text : 'approvalTime',
        width : '1/2',
        formatter : function(rowData) {
          return showStandardTime(rowData.approvalTime);
        }
      }
  ],
  toolsAdd : {
    sortIdx : 1,
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
    sortIdx : 2,
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
        'storageId'
      ];
    },
    handleFormOptions : function(editJson, formOptions, $datagrid, $selecteds, selectedDatas, value) {
      LK.UI.formUtils.changeOptions(formOptions.plugins, 'productList', false, {
        lazy : false,
        param : {
          orderId : value
        }
      });
      LK.UI.formUtils.changeOptions(formOptions.plugins, 'storageId', false, {
        linkages : []
      }, true);
    }
  },
  toolsView : {
    sortIdx : 6,
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
      LK.UI.formUtils.changeOptions(formOptions.plugins, 'storageId', false, {
        linkages : []
      }, true);
    }
  },
  toolsRemove : {
    sortIdx : 3,
    saveUrl : '/SysPssStockCheckOrder/D',
    beforeClick : function($button, $datagrid, $selecteds, selectedDatas, value, i18nKey) {
      for (var i = 0; i < selectedDatas.length; i++) {
        if (selectedDatas[i].usingStatusDictCode != 'STAND_BY') {
          LK.alert(i18nKey + 'only STAND_BY status can be remove');
          return false;
        }
      }
      return true;
    }
  },
  toolsSubmit : {
    sortIdx : 5,
    processCode : 'PSS_STOCK_CHECK_ORDER',
    beforeClick : function($button, $datagrid, $selecteds, selectedDatas, value, i18nKey) {
      for (var i = 0; i < selectedDatas.length; i++) {
        if (selectedDatas[i].usingStatusDictCode != 'USING') {
          LK.alert(i18nKey + 'only USING status can be submit');
          return false;
        }
        if (selectedDatas[i].billDate != today()) {
          LK.alert(i18nKey + 'only today order can be submit');
          return false;
        }
      }
      return true;
    }
  },
  toolsUS : {
    sortIdx : 4,
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
          param : {
            categoryCode : 'PSS_CHECK_ORDER_STATUS'
          }
        }
      }
  ]
}));
