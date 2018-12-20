var purchaseReturnNotStockInOrderFormPlugins = [
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
        readonly : true,
        commitable : true,
      }
    }, {
      plugin : 'selector_employee',
      options : {
        key : 'returnedName',
        name : 'purchaserId',
        validator : true
      }
    }, {
      plugin : 'textbox',
      options : {
        key : 'purchaseOrderNo',
        name : 'orderId',
        readonly : true,
        commitable : true,
        cols : 4
      }
    }, {
      plugin : 'datagrid',
      options : {
        i18nKey : 'purchaseReturnNotStockInOrder.product-grid',
        key : 'title',
        name : 'productList',
        validator : 'datas',
        multiSelect : true,
        valueFieldName : 'purchaseOrderProductId',
        cols : 4,
        rows : 11,
        showSearchButton : false,
        pageable : false,
        withoutFieldKey : true,
        url : '/SysPssPurchaseReturnNotStockInOrderProduct/L',
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
              text : 'unit',
              width : 60,
              name : 'unit'
            }, {
              text : 'purchaseQty',
              width : 70,
              name : 'purchaseQty'
            }, {
              text : 'inventoryQuantity',
              width : 80,
              name : 'inventoryQuantity'
            }, {
              text : 'returnedQuantity',
              width : 80,
              name : 'returnedQuantity'
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
                    max : rowData.canStockInQty,
                    onChange : function($plugin, values, value, currentValue) {
                      if (currentValue > rowData.canStockInQty) {
                        currentValue = rowData.canStockInQty;
                      }
                      if (currentValue == 0) {
                        currentValue = 1;
                      }
                      var unitPrice = $plugin.LKGetSameNodePlugin('unitPrice').LKGetValue();
                      var quantity = currentValue;
                      $plugin.LKGetSameNodePlugin('subTotalPrice').LKSetValues((quantity * parseFloat(unitPrice)).toFixed(2));
                      purchaseReturnNotStockInOrderCalcTotal($plugin.parents('.lichkin-datagrid:first'));
                    }
                  }
                }
              }
            }, {
              text : 'unitPrice',
              width : 100,
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
                      purchaseReturnNotStockInOrderCalcTotal($plugin.parents('.lichkin-datagrid:first'));
                    }
                  }
                }
              }
            }, {
              text : 'subTotalPrice',
              width : 100,
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
        toolsRemoveData : {
          afterRemove : function($button, $datagrid, $selecteds, selectedDatas, value, i18nKey) {
            purchaseReturnNotStockInOrderCalcTotal($datagrid);
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

LK.UI.datagrid($.extend((typeof LK.home == 'undefined' ? {
  title : 'title',
  icon : 'purchaseReturnNotStockInOrder',
} : {}), {
  i18nKey : 'purchaseReturnNotStockInOrder',
  $appendTo : true,
  cols : 4,
  url : '/SysPssPurchaseReturnNotStockInOrder/P',
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
        width : 150,
        name : 'supplierName'
      }, {
        text : 'orderAmount',
        width : 100,
        name : 'orderAmount'
      }, {
        text : 'returnedName',
        width : 100,
        name : 'returnedName'
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
      }, {
        text : 'purchaseOrderNo',
        width : 240,
        name : 'purchaseOrderNo'
      }, {
        text : 'purchaserBillDate',
        width : 100,
        name : 'purchaserBillDate'
      }, {
        text : 'purchaseOrderAmount',
        width : 100,
        name : 'purchaseOrderAmount'
      }, {
        text : 'purchaserName',
        width : 100,
        name : 'purchaserName'
      }
  ],
  tools : [
    {
      sortIdx : -1,
      singleCheck : null,
      icon : 'add',
      text : 'purchaseReturnNotStockInOrder.grid.addFromPurchaseOrder',
      click : function($button, $plugin, $selecteds, selectedDatas, value, i18nKey) {
        LK.UI.openDialog($.extend({}, {
          size : {
            cols : 4,
            rows : 12
          }
        }, {
          title : i18nKey + 'selectPurchaseOrder',
          icon : 'add',
          mask : true,
          buttons : [
              {
                text : 'ok',
                icon : 'ok',
                cls : 'warning',
                click : function($button, $dialog, $contentBar) {
                  var $form = $contentBar.find('form');
                  if (!$form.LKValidate()) {
                    return;
                  }
                  var $purchaseOrderGrid = $form.find('[name=purchaseOrderList]').parents('.lichkin-plugin').first();
                  var $selectedRowData = $purchaseOrderGrid.LKGetDataContainer().find('tr.selected').data();

                  // 采购订单ID
                  var purchaseOrderId = $purchaseOrderGrid.LKGetValue();
                  $dialog.LKCloseDialog();

                  LK.UI.openDialog($.extend({}, {
                    size : {
                      cols : 4,
                      rows : 16
                    }
                  }, {
                    title : 'add',
                    icon : 'add',
                    mask : true,
                    buttons : [
                        {
                          text : 'save',
                          icon : 'save',
                          cls : 'warning',
                          click : function($button, $dialog, $contentBar) {
                            var $form = $contentBar.find('form');
                            var formData = $form.LKFormGetData();
                            var productList = $form.find('[name=productList]').parents('.lichkin-plugin').LKGetDataJsonString();
                            formData.productList = productList;
                            if ($form.LKValidate() && productList != '[]') {
                              LK.ajax({
                                url : '/SysPssPurchaseReturnNotStockInOrder/I',
                                data : formData,
                                showSuccess : true,
                                success : function() {
                                  $dialog.LKCloseDialog();
                                  $plugin.LKLoad();
                                },
                                error : function(errorCode, errorMessage) {
                                  LK.toast({
                                    timeout : 4000,
                                    msg : errorMessage
                                  });
                                }
                              });
                            }
                          }
                        }, {
                          text : 'cancel',
                          icon : 'cancel',
                          cls : 'danger',
                          click : function($button, $dialog, $contentBar) {
                            $dialog.LKCloseDialog();
                          }
                        }
                    ],
                    onAfterCreate : function($dialog, $contentBar) {
                      var formOptions = $.extend(true, {}, {
                        plugins : purchaseReturnNotStockInOrderFormPlugins
                      }, {
                        $appendTo : $contentBar
                      });
                      formOptions.i18nKey = i18nKey + 'columns.';
                      var $form = LK.UI.form(formOptions);

                      $form.find('[name=orderId]').parents('.lichkin-plugin').LKSetValues(purchaseOrderId, true);

                      LK.ajax({
                        url : '/SysPssPurchaseOrderProduct/L01',
                        data : {
                          orderId : purchaseOrderId
                        },
                        success : function(responseDatas) {
                          // 设置供应商
                          $form.find('[name=supplierId]').parents('.lichkin-plugin').LKInvokeSetValues($selectedRowData.supplierId, true);
                          // 设置产品数据
                          if (responseDatas && responseDatas.length != 0) {
                            var $productList = $form.find('[name=productList]').parents('.lichkin-plugin').first();
                            $productList.LKInvokeAddDatas(responseDatas);
                            purchaseReturnNotStockInOrderCalcTotal($productList);
                          } else {
                            LK.alert('purchaseReturnNotStockInOrder.grid.no stored products on current purchase order');
                          }
                        }
                      });

                    }
                  }));
                }
              }, {
                text : 'cancel',
                icon : 'cancel',
                cls : 'danger',
                click : function($button, $dialog, $contentBar) {
                  $dialog.LKCloseDialog();
                }
              }
          ],
          onAfterCreate : function($dialog, $contentBar) {
            var $formPlugins = {
              plugins : [
                {
                  plugin : 'datagrid',
                  options : {
                    i18nKey : 'purchaseReturnNotStockInOrder.purchaseOrder-grid',
                    validator : true,
                    key : 'title',
                    name : 'purchaseOrderList',
                    valueFieldName : 'id',
                    cols : 4,
                    rows : 12,
                    pageable : false,
                    url : '/SysPssPurchaseOrder/L',
                    $appendTo : $('body'),
                    columns : [
                        {
                          text : 'orderNo',
                          name : 'orderNo',
                          width : 260,
                          cssClass : 'monospacedFont'
                        }, {
                          text : 'billDate',
                          name : 'billDate',
                          width : 120
                        }, {
                          text : 'inventoryStatus',
                          name : 'inventoryStatus',
                          width : 150
                        }, {
                          text : 'supplierName',
                          name : 'supplierName',
                          width : 250
                        }, {
                          text : 'purchaserName',
                          name : 'purchaserName',
                          width : 150
                        }, {
                          text : 'orderAmount',
                          name : 'orderAmount',
                          width : 100
                        }
                    ],
                    searchForm : [
                        {
                          plugin : 'textbox',
                          options : {
                            key : 'orderNo',
                            name : 'orderNo',
                            cls : 'fuzzy-left fuzzy-right'
                          }
                        }, {
                          plugin : 'datepicker',
                          options : {
                            key : 'startDate',
                            name : 'startDate',
                            minDate : '1900-01-01',
                          }
                        }, {
                          plugin : 'datepicker',
                          options : {
                            key : 'endDate',
                            name : 'endDate',
                            minDate : '1900-01-01',
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
                            key : 'purchaserName',
                            name : 'purchaserName',
                            cls : 'fuzzy-left fuzzy-right'
                          }
                        }, {
                          plugin : 'droplist',
                          options : {
                            name : 'inventoryStatus',
                            param : {
                              categoryCode : 'PSS_INVENTORY_STATUS',
                              excludes : 'ALL'
                            }
                          }
                        }

                    ]
                  }
                }
              ]
            }
            var formOptions = $.extend({}, $formPlugins, {
              $appendTo : $contentBar
            });
            LK.UI.form(formOptions);
          }
        }));
      }
    }
  ],
  toolsEdit : {
    saveUrl : '/SysPssPurchaseReturnNotStockInOrder/U',
    form : {
      url : '/SysPssPurchaseReturnNotStockInOrder/O',
      plugins : purchaseReturnNotStockInOrderFormPlugins
    },
    dialog : {
      size : {
        cols : 4,
        rows : 16
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
      url : '/SysPssPurchaseReturnNotStockInOrder/O',
      plugins : purchaseReturnNotStockInOrderFormPlugins
    },
    dialog : {
      size : {
        cols : 4,
        rows : 16
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
    }
  },
  toolsRemove : {
    saveUrl : '/SysPssPurchaseReturnNotStockInOrder/D',
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
    processCode : 'PSS_PURCHASE_RETURN_NOT_STOCK_IN_ORDER',
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
      }, {
        plugin : 'textbox',
        options : {
          key : 'purchaseOrderNo',
          name : 'purchaseOrderNo',
          cls : 'fuzzy-left fuzzy-right'
        }
      }, {
        plugin : 'textbox',
        options : {
          key : 'purchaserName',
          name : 'purchaserName',
          cls : 'fuzzy-left fuzzy-right'
        }
      }

  ]
}));

var purchaseReturnNotStockInOrderCalcTotal = function($productList) {
  var orderAmount = 0;
  $productList.LKGetDataContainer().find('.lichkin-plugin').each(function() {
    if ($(this).LKis('subTotalPrice')) {
      orderAmount += parseFloat($(this).LKGetValue());
    }
  });
  $productList.LKGetSiblingPlugin('orderAmount').LKSetValues(orderAmount.toFixed(2));
};