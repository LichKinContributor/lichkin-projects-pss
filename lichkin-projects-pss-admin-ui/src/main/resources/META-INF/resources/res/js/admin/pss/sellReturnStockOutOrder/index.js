var sellReturnStockOutOrderFormPlugins = [
    {
      plugin : 'datepicker',
      options : {
        name : 'billDate',
        minDate : '1900-01-01',
        validator : true,
        value : today()
      }
    }, {
      plugin : 'selector_employee',
      options : {
        key : 'returnedName',
        name : 'salesId',
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
          LK.ajax({
            url : '/SysPssProduct/L',
            data : {
              barcode : val
            },
            success : function(responseDatas) {
              if (responseDatas && responseDatas.length == 1) {
                var $productList = $plugin.LKGetSiblingPlugin('productList');
                $productList.LKInvokeAddDatas(responseDatas);
                sellReturnStockOutOrderCalcTotal($productList);
              } else {
                LK.alert('sellReturnStockOutOrder.grid.product not exists');
              }
            }
          });
        }
      }
    }, {
      plugin : 'datagrid',
      options : {
        i18nKey : 'sellReturnStockOutOrder.product-grid',
        key : 'sellReturnStockOutOrder.product-grid.title',
        name : 'productList',
        validator : 'datas',
        multiSelect : true,
        valueFieldName : 'random',
        cols : 3,
        rows : 10,
        showSearchButton : false,
        pageable : false,
        withoutFieldKey : true,
        url : '/SysPssSellReturnStockOutOrderProduct/L',
        lazy : true,
        $appendTo : $('body'),
        columns : [
            {
              text : 'productCode',
              width : 120,
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
              width : 80,
              formatter : function(rowData) {
                return {
                  plugin : 'numberspinner',
                  options : {
                    name : 'quantity',
                    value : (typeof rowData.quantity != 'undefined') ? rowData.quantity : 1,
                    onChange : function($plugin, values, value, currentValue) {
                      var quantity = currentValue;
                      var unitPrice = $plugin.LKGetSameNodePlugin('unitPrice').LKGetValue();
                      $plugin.LKGetSameNodePlugin('subTotalPrice').LKSetValues((quantity * parseFloat(unitPrice)).toFixed(2));
                      sellReturnStockOutOrderCalcTotal($plugin.parents('.lichkin-datagrid:first'));
                    },
                    min : 1
                  }
                }
              }
            }, {
              text : 'unitPrice',
              width : 80,
              formatter : function(rowData) {
                return {
                  plugin : 'textbox',
                  options : {
                    name : 'unitPrice',
                    value : (typeof rowData.unitPrice != 'undefined') ? rowData.unitPrice : rowData.referencePrice,
                    onChange : function($plugin, values, value, currentValue) {
                      var quantity = $plugin.LKGetSameNodePlugin('quantity').LKGetValue();
                      var unitPrice = currentValue;
                      $plugin.LKGetSameNodePlugin('subTotalPrice').LKSetValues((quantity * parseFloat(unitPrice)).toFixed(2));
                      sellReturnStockOutOrderCalcTotal($plugin.parents('.lichkin-datagrid:first'));
                    }
                  }
                }
              }
            }, {
              text : 'subTotalPrice',
              width : 80,
              formatter : function(rowData) {
                return {
                  plugin : 'textbox',
                  options : {
                    name : 'subTotalPrice',
                    value : (typeof rowData.subTotalPrice != 'undefined') ? rowData.subTotalPrice : rowData.referencePrice,
                    readonly : true
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
          afterAddData : function($button, $datagrid, $selecteds, selectedDatas, value, $dialogButton, $dialog, i18nKey, $form) {
            sellReturnStockOutOrderCalcTotal($datagrid);
          }
        },
        toolsRemoveData : {
          afterRemove : function($button, $datagrid, $selecteds, selectedDatas, value, i18nKey) {
            sellReturnStockOutOrderCalcTotal($datagrid);
          },
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
    }, '-', '-', {
      plugin : 'textbox',
      options : {
        name : 'orderAmount',
        value : 0,
        maxlength : 50,
        readonly : true
      }
    }
];

LK.UI.datagrid($.extend((typeof LK.home == 'undefined' ? {
  title : 'title',
  icon : 'sellReturnStockOutOrder',
} : {}), {
  i18nKey : 'sellReturnStockOutOrder',
  $appendTo : true,
  cols : 4,
  url : '/SysPssSellReturnStockOutOrder/P',
  columns : [
      {
        text : 'orderNo',
        width : 260,
        name : 'orderNo',
        cssClass : 'monospacedFont'
      }, {
        text : 'billDate',
        width : 180,
        name : 'billDate'
      }, {
        text : 'returnedName',
        width : 120,
        name : 'returnedName'
      }, {
        text : 'storageName',
        width : null,
        name : 'storageName'
      }, {
        text : 'orderAmount',
        width : 120,
        name : 'orderAmount'
      }, {
        text : 'approvalStatus',
        width : 120,
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
    saveUrl : '/SysPssSellReturnStockOutOrder/I',
    form : {
      plugins : sellReturnStockOutOrderFormPlugins
    },
    dialog : {
      size : {
        cols : 3,
        rows : 15
      }
    },
    beforeSave : function($button, $datagrid, $selecteds, selectedDatas, value, $dialogButton, $dialog, i18nKey) {
      return {
        productList : $dialog.LKGetSubPlugin('productList').LKGetDataJsonString()
      };
    }
  },
  toolsEdit : {
    saveUrl : '/SysPssSellReturnStockOutOrder/U',
    form : {
      url : '/SysPssSellReturnStockOutOrder/O',
      plugins : sellReturnStockOutOrderFormPlugins
    },
    dialog : {
      size : {
        cols : 3,
        rows : 15
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
          'billDate', 'salesId', 'storageId'
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
      url : '/SysPssSellReturnStockOutOrder/O',
      plugins : sellReturnStockOutOrderFormPlugins
    },
    dialog : {
      size : {
        cols : 3,
        rows : 15
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
    saveUrl : '/SysPssSellReturnStockOutOrder/D',
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
    processCode : 'PSS_SELL_RETURN_STOCK_OUT_ORDER',
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
        plugin : 'textbox',
        options : {
          name : 'orderNo',
          cls : 'fuzzy-left fuzzy-right'
        }
      }, {
        plugin : 'textbox',
        options : {
          name : 'returnedName',
          cls : 'fuzzy-left fuzzy-right'
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

var sellReturnStockOutOrderCalcTotal = function($productList) {
  var orderAmount = 0;
  $productList.LKGetDataContainer().find('.lichkin-plugin').each(function() {
    if ($(this).LKis('subTotalPrice')) {
      orderAmount += parseFloat($(this).LKGetValue());
    }
  });
  $productList.LKGetSiblingPlugin('orderAmount').LKSetValues(orderAmount.toFixed(2));
};
