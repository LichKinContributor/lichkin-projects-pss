var purchaseReturnStockInOrderFormPlugins = [
    {
      plugin : 'datepicker',
      options : {
        name : 'billDate',
        minDate : '1900-01-01',
        validator : true,
        value : today()
      }
    }, {
      plugin : 'droplist',
      options : {
        key : 'supplierName',
        name : 'supplierId',
        url : '/SysPssSupplier/LD',
        validator : true
      }
    }, {
      plugin : 'selector_employee',
      options : {
        key : 'returnedName',
        name : 'purchaserId',
        validator : true
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
        cols : 4,
        enterKeyClick : function($plugin, values, value, val) {// 扫码录入
          $plugin.LKSetValues('', true);
          if (!val) {
            return;
          }
          var storageId = $plugin.LKGetSiblingPlugin('storageId').LKGetValue();
          if (!storageId) {
            LK.alert('purchaseReturnStockInOrder.grid.please select the storage first');
            return;
          }

          var orderId = $plugin.parents('form').find('input[name=id]').val();
          if (!orderId) {
            orderId = '';
          }

          LK.ajax({
            url : '/SysPssStock/L',
            data : {
              storageId : storageId,
              barcode : val,
              orderId : orderId
            },
            success : function(responseDatas) {
              if (responseDatas && responseDatas.length == 1) {
                if (responseDatas[0].canOutQuantity == 0) {
                  LK.alert('purchaseReturnStockInOrder.grid.the number of products currently available is zero');
                  return;
                }
                var $productList = $plugin.LKGetSiblingPlugin('productList');
                $productList.LKInvokeAddDatas(responseDatas);
                purchaseReturnStockInOrderCalcTotal($productList);
              } else {
                LK.alert('purchaseReturnStockInOrder.grid.this product does not exist in the current storage');
              }
            }
          });
        }
      }
    }, {
      plugin : 'datagrid',
      options : {
        i18nKey : 'purchaseReturnStockInOrder.product-grid',
        key : 'purchaseReturnStockInOrder.product-grid.title',
        name : 'productList',
        validator : 'datas',
        multiSelect : true,
        valueFieldName : 'random',
        cols : 4,
        rows : 10,
        showSearchButton : false,
        pageable : false,
        withoutFieldKey : true,
        url : '/SysPssPurchaseReturnStockInOrderProduct/L',
        lazy : true,
        $appendTo : $('body'),
        onLinkaged : function($plugin, linkage) {
          $plugin.LKClearDatas(false, true);
        },
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
              text : 'stockQuantity',
              width : 80,
              name : 'stockQuantity'
            }, {
              text : 'canOutQuantity',
              width : 90,
              name : 'canOutQuantity'
            }, {
              text : 'quantity',
              width : 80,
              formatter : function(rowData) {
                return {
                  plugin : 'numberspinner',
                  options : {
                    name : 'quantity',
                    value : (typeof rowData.quantity != 'undefined') ? rowData.quantity : 1,
                    min : 1,
                    max : rowData.canOutQuantity,
                    onChange : function($plugin, values, value, currentValue) {
                      if (currentValue > rowData.canOutQuantity) {
                        currentValue = rowData.canOutQuantity;
                      }
                      var unitPrice = $plugin.LKGetSameNodePlugin('unitPrice').LKGetValue();
                      var quantity = currentValue;
                      $plugin.LKGetSameNodePlugin('subTotalPrice').LKSetValues((quantity * parseFloat(unitPrice)).toFixed(2));
                      purchaseReturnStockInOrderCalcTotal($plugin.parents('.lichkin-datagrid:first'));
                    }
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
                    value : rowData.unitPrice,
                    onChange : function($plugin, values, value, currentValue) {
                      var quantity = $plugin.LKGetSameNodePlugin('quantity').LKGetValue();
                      var unitPrice = currentValue;
                      $plugin.LKGetSameNodePlugin('subTotalPrice').LKSetValues((quantity * parseFloat(unitPrice)).toFixed(2));
                      purchaseReturnStockInOrderCalcTotal($plugin.parents('.lichkin-datagrid:first'));
                    }
                  }
                }
              }
            }, {
              text : 'subTotalPrice',
              width : 80,
              formatter : function(rowData) {
                var quantity = (typeof rowData.quantity != 'undefined') ? rowData.quantity : 1;
                return {
                  plugin : 'textbox',
                  options : {
                    name : 'subTotalPrice',
                    value : (typeof rowData.subTotalPrice != 'undefined') ? rowData.subTotalPrice : (quantity * parseFloat(rowData.unitPrice)).toFixed(2),
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
              LK.alert('purchaseReturnStockInOrder.grid.please select the storage first');
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
            var orderId = $datagrid.parents('form').find('input[name=id]').val();
            if (!orderId) {
              orderId = '';
            }

            var storageId = $datagrid.LKGetSiblingPlugin('storageId').LKGetValue();
            var productDatas = $form.LKGetSubPlugin('product').LKGetValueDatas();
            var notExist = false;
            var qtyIsZero = false;

            var returnDatas = [];
            for (var i = 0; i < productDatas.length; i++) {
              var prod = productDatas[i];
              LK.ajax({
                url : '/SysPssStock/L',
                async : false,
                data : {
                  storageId : storageId,
                  productId : prod.id,
                  orderId : orderId
                },
                success : function(responseDatas) {
                  if (responseDatas && responseDatas.length == 1) {
                    if (responseDatas[0].canOutQuantity == 0) {
                      qtyIsZero = true;
                      return;
                    }
                    returnDatas.push(responseDatas[0]);
                  } else {
                    notExist = true;
                  }
                }
              });
              if (qtyIsZero) {
                LK.alert('purchaseReturnStockInOrder.grid.the number of products currently available is zero');
                return [];
              }
              if (notExist) {
                LK.alert('purchaseReturnStockInOrder.grid.this product does not exist in the current storage');
                return [];
              }
            }
            return returnDatas;
          },
          afterAddData : function($button, $datagrid, $selecteds, selectedDatas, value, $dialogButton, $dialog, i18nKey, $form) {
            purchaseReturnStockInOrderCalcTotal($datagrid);
          }
        },
        toolsRemoveData : {
          afterRemove : function($button, $datagrid, $selecteds, selectedDatas, value, i18nKey) {
            purchaseReturnStockInOrderCalcTotal($datagrid);
          },
        },
      }
    }, {
      plugin : 'textbox',
      options : {
        name : 'remarks',
        maxlength : 512,
        cols : 4,
        rows : 2
      }
    }, '-', '-', '-', {
      plugin : 'textbox',
      options : {
        name : 'orderAmount',
        value : 0,
        maxlength : 50,
        readonly : true
      }
    }
];

var $purchaseReturnStockInOrderDatagrid = LK.UI.datagrid($.extend((typeof LK.home == 'undefined' ? {
  title : 'title',
  icon : 'purchaseReturnStockInOrder',
} : {}), {
  i18nKey : 'purchaseReturnStockInOrder',
  $appendTo : true,
  cols : 4,
  url : '/SysPssPurchaseReturnStockInOrder/P',
  columns : [
      {
        text : 'orderNo',
        width : 260,
        name : 'orderNo',
        cssClass : 'monospacedFont'
      }, {
        text : 'billDate',
        width : 100,
        name : 'billDate'
      }, {
        text : 'supplierName',
        width : '1/2',
        name : 'supplierName'
      }, {
        text : 'returnedName',
        width : 100,
        name : 'returnedName'
      }, {
        text : 'storageName',
        width : '1/2',
        name : 'storageName'
      }, {
        text : 'orderAmount',
        width : 100,
        name : 'orderAmount'
      }, {
        text : 'approvalStatus',
        width : 80,
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
    saveUrl : '/SysPssPurchaseReturnStockInOrder/I',
    form : {
      plugins : purchaseReturnStockInOrderFormPlugins
    },
    dialog : {
      size : {
        cols : 4,
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
    saveUrl : '/SysPssPurchaseReturnStockInOrder/U',
    form : {
      url : '/SysPssPurchaseReturnStockInOrder/O',
      plugins : purchaseReturnStockInOrderFormPlugins
    },
    dialog : {
      size : {
        cols : 4,
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
          'billDate', 'supplierId', 'purchaserId', 'storageId'
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
    form : {
      url : '/SysPssPurchaseReturnStockInOrder/O',
      plugins : purchaseReturnStockInOrderFormPlugins
    },
    dialog : {
      size : {
        cols : 4,
        rows : 14
      }
    },
    handleFormOptions : function(viewJson, formOptions, $datagrid, $selecteds, selectedDatas, value) {
      LK.UI.formUtils.removePlugins(formOptions.plugins, 'scanCode');
      LK.UI.formUtils.changeOptions(formOptions.plugins, 'productList', false, {
        lazy : false,
        param : {
          orderId : value,
          orderType : false,
          isView : true
        },
        tools : []
      });
      LK.UI.formUtils.changeOptions(formOptions.plugins, 'storageId', false, {
        linkages : []
      }, true);
    }
  },
  toolsRemove : {
    saveUrl : '/SysPssPurchaseReturnStockInOrder/D',
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
    processCode : 'PSS_PURCHASE_RETURN_STOCK_IN_ORDER',
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
          key : 'supplierName',
          name : 'supplierName',
          cls : 'fuzzy-left fuzzy-right'
        }
      }, {
        plugin : 'textbox',
        options : {
          key : 'returnedName',
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

var purchaseReturnStockInOrderCalcTotal = function($productList) {
  var orderAmount = 0;
  $productList.LKGetDataContainer().find('.lichkin-plugin').each(function() {
    if ($(this).LKis('subTotalPrice')) {
      orderAmount += parseFloat($(this).LKGetValue());
    }
  });
  $productList.LKGetSiblingPlugin('orderAmount').LKSetValues(orderAmount.toFixed(2));
};