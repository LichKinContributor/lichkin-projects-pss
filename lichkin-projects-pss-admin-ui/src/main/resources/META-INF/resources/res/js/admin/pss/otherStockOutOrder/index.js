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
            LK.alert('otherStockOutOrder.grid.please select the storage first');
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
                if (responseDatas[0].stockQuantity == 0) {
                  LK.alert('otherStockOutOrder.grid.the number of products currently available is zero');
                  return;
                }

                var $productList = $plugin.LKGetSiblingPlugin('productList');
                if (mergeProdOutnumber($productList, responseDatas[0])) {
                  return;
                }

                $productList.LKInvokeAddDatas(responseDatas);
              } else {
                LK.alert('otherStockOutOrder.grid.this product does not exist in the current storage');
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
              text : 'quantity',
              width : 100,
              formatter : function(rowData) {
                return {
                  plugin : 'numberspinner',
                  options : {
                    name : 'quantity',
                    value : (typeof rowData.quantity != 'undefined') ? rowData.quantity : 1,
                    min : 1,
                    max : rowData.stockQuantity
                  }
                }
              }
            }
        ],
        toolsAddData : {
          beforeClick : function($button, $datagrid, $selecteds, selectedDatas, value, i18nKey) {
            var storageId = $datagrid.LKGetSiblingPlugin('storageId').LKGetValue();
            if (!storageId) {
              LK.alert('otherStockOutOrder.grid.please select the storage first');
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
                    if (responseDatas[0].stockQuantity == 0) {
                      qtyIsZero = true;
                      return;
                    }
                    if (mergeProdOutnumber($datagrid, responseDatas[0])) {
                      return;
                    }
                    returnDatas.push(responseDatas[0]);
                  } else {
                    notExist = true;
                  }
                }
              });
              if (qtyIsZero) {
                LK.alert('otherStockOutOrder.grid.the number of products currently available is zero');
                return [];
              }
              if (notExist) {
                LK.alert('otherStockOutOrder.grid.this product does not exist in the current storage');
                return [];
              }
            }
            return returnDatas;
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

var mergeProdOutnumber = function($datagrid, addProd) {
  var outnumber = false;
  var $allRows = $datagrid.LKGetDataContainer().find('tr');
  $allRows.each(function() {
    var rowData = $(this).data();
    if (rowData.id == addProd.id) {
      outnumber = true;
      var qty = parseInt($(this).LKGetSubPlugin('quantity').LKGetValue()) + 1;
      if (qty <= addProd.stockQuantity) {
        $(this).LKGetSubPlugin('quantity').LKSetValues(qty, true);
      }
      return false;
    }
  });
  return outnumber;
}

var $otherStockOutOrderDatagrid = LK.UI.datagrid($.extend((typeof LK.home == 'undefined' ? {
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
          orderId : value,
          orderType : false
        }
      });
      LK.UI.formUtils.changeOptions(formOptions.plugins, 'storageId', false, {
        linkages : []
      }, true);
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
          orderId : value,
          orderType : false
        },
        tools : []
      });
      LK.UI.formUtils.changeOptions(formOptions.plugins, 'storageId', false, {
        linkages : []
      }, true);
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
    processCode : 'PSS_OTHER_STOCK_ORDER',
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
