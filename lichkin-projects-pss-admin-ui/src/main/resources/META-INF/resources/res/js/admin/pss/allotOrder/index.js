var allotOrderFormPlugins = [
    {
      plugin : 'datepicker',
      options : {
        name : 'billDate',
        minDate : '1900-01-01',
        validator : true,
        value : today(),
        minDate : lastMonthDay()
      }
    }, {
      plugin : 'droplist',
      options : {
        key : 'outStorageName',
        name : 'outStorageId',
        url : '/SysPssStorage/LD',
        validator : true,
        onChange : function($plugin, values, value, currentValue) {
          $plugin.LKGetSiblingPlugin('inStorageId').LKLoad({
            param : {
              excludeIds : currentValue
            }
          });
        },
        linkages : [
          'productList'
        ]
      }
    }, {
      plugin : 'droplist',
      options : {
        key : 'inStorageName',
        name : 'inStorageId',
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

          var storageId = $plugin.LKGetSiblingPlugin('outStorageId').LKGetValue();
          if (!storageId) {
            LK.alert('allotOrder.grid.please choose the storage');
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
                  LK.alert('allotOrder.grid.the number of products currently available is zero');
                  return;
                }

                var $productList = $plugin.LKGetSiblingPlugin('productList');
                if (allotOrderMergeProd($productList, responseDatas[0])) {
                  return;
                }

                $productList.LKInvokeAddDatas(responseDatas);
              } else {
                LK.alert('allotOrder.grid.this product does not exist in the storage');
              }
            }
          });

        }
      }
    }, {
      plugin : 'datagrid',
      options : {
        i18nKey : 'allotOrder.product-grid',
        key : 'allotOrder.product-grid.title',
        name : 'productList',
        validator : 'datas',
        multiSelect : true,
        valueFieldName : 'random',
        cols : 3,
        rows : 10,
        showSearchButton : false,
        pageable : false,
        withoutFieldKey : true,
        url : '/SysPssAllotOrderProduct/L',
        lazy : true,
        $appendTo : $body,
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
                    min : 0,
                    max : rowData.canOutQuantity
                  }
                }
              }
            }
        ],
        toolsAddData : {
          beforeClick : function($button, $datagrid, $selecteds, selectedDatas, value, i18nKey) {
            var storageId = $datagrid.LKGetSiblingPlugin('outStorageId').LKGetValue();
            if (!storageId) {
              LK.alert('allotOrder.grid.please choose the storage');
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

            var storageId = $datagrid.LKGetSiblingPlugin('outStorageId').LKGetValue();
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
                    if (allotOrderMergeProd($datagrid, responseDatas[0])) {
                      return;
                    }
                    returnDatas.push(responseDatas[0]);
                  } else {
                    notExist = true;
                  }
                }
              });
              if (qtyIsZero) {
                LK.alert('allotOrder.grid.the number of products currently available is zero');
                return [];
              }
              if (notExist) {
                LK.alert('allotOrder.grid.this product does not exist in the storage');
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

var allotOrderMergeProd = function($datagrid, addProd) {
  var outnumber = false;
  var $allRows = $datagrid.LKGetDataContainer().find('tr');
  $allRows.each(function() {
    var rowData = $(this).data();
    if (rowData.id == addProd.id) {
      outnumber = true;
      var qty = parseInt($(this).LKGetSubPlugin('quantity').LKGetValue()) + 1;
      if (qty <= addProd.canOutQuantity) {
        $(this).LKGetSubPlugin('quantity').LKSetValues(qty, true);
      }
      return false;
    }
  });
  return outnumber;
}

var $allotOrderDatagrid = LK.UI.datagrid($.extend((typeof LK.home == 'undefined' ? {
  title : 'title',
  icon : 'allotOrder',
} : {}), {
  i18nKey : 'allotOrder',
  $appendTo : true,
  cols : 4,
  url : '/SysPssAllotOrder/P',
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
        text : 'outStorageName',
        width : '1/2',
        name : 'outStorageName'
      }, {
        text : 'inStorageName',
        width : '1/2',
        name : 'inStorageName'
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
    saveUrl : '/SysPssAllotOrder/I',
    form : {
      plugins : allotOrderFormPlugins
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
    saveUrl : '/SysPssAllotOrder/U',
    form : {
      url : '/SysPssAllotOrder/O',
      plugins : allotOrderFormPlugins
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
          'billDate', 'outStorageId', 'inStorageId'
      ];
    },
    handleFormOptions : function(editJson, formOptions, $datagrid, $selecteds, selectedDatas, value) {
      LK.UI.formUtils.changeOptions(formOptions.plugins, 'productList', false, {
        lazy : false,
        param : {
          orderId : value
        }
      });
      LK.UI.formUtils.changeOptions(formOptions.plugins, 'outStorageId', false, {
        linkages : []
      }, true);
    }
  },
  toolsView : {
    form : {
      url : '/SysPssAllotOrder/O',
      plugins : allotOrderFormPlugins
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
          isView : true
        },
        tools : []
      });
      LK.UI.formUtils.changeOptions(formOptions.plugins, 'outStorageId', false, {
        linkages : []
      }, true);
    }
  },
  toolsRemove : {
    saveUrl : '/SysPssAllotOrder/D',
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
    processCode : 'PSS_ALLOT_ORDER',
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
          key : 'outStorageName',
          name : 'outStorageId',
          url : '/SysPssStorage/LD'
        }
      }, {
        plugin : 'droplist',
        options : {
          key : 'inStorageName',
          name : 'inStorageId',
          url : '/SysPssStorage/LD'
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
