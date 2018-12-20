var sellReturnNotStockOutOrderFormPlugins = [
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
      plugin : 'textbox',
      options : {
        key : 'sellOrderNo',
        name : 'orderId',
        readonly : true,
        commitable : true,
        cols : 4
      }
    }, {
      plugin : 'datagrid',
      options : {
        i18nKey : 'sellReturnNotStockOutOrder.product-grid',
        key : 'title',
        name : 'productList',
        validator : 'datas',
        multiSelect : true,
        valueFieldName : 'sellOrderProductId',
        cols : 4,
        rows : 11,
        showSearchButton : false,
        pageable : false,
        withoutFieldKey : true,
        url : '/SysPssSellReturnNotStockOutOrderProduct/L',
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
              text : 'salesQuantity',
              width : 70,
              name : 'salesQuantity'
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
                    max : rowData.canStockOutQty,
                    onChange : function($plugin, values, value, currentValue) {
                      if (currentValue > rowData.canStockOutQty) {
                        currentValue = rowData.canStockOutQty;
                      }
                      if (currentValue == 0) {
                        currentValue = 1;
                      }
                      var quantity = currentValue;
                      var unitPrice = $plugin.LKGetSameNodePlugin('unitPrice').LKGetValue();
                      $plugin.LKGetSameNodePlugin('subTotalPrice').LKSetValues((quantity * parseFloat(unitPrice)).toFixed(2));
                      sellReturnNotStockOutOrderCalcTotal($plugin.parents('.lichkin-datagrid:first'));
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
                      sellReturnNotStockOutOrderCalcTotal($plugin.parents('.lichkin-datagrid:first'));
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
            sellReturnNotStockOutOrderCalcTotal($datagrid);
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
  icon : 'sellReturnNotStockOutOrder',
} : {}), {
  i18nKey : 'sellReturnNotStockOutOrder',
  $appendTo : true,
  cols : 4,
  url : '/SysPssSellReturnNotStockOutOrder/P',
  columns : [
      {
        text : 'orderNo',
        width : 240,
        name : 'orderNo',
        cssClass : 'monospacedFont'
      }, {
        text : 'billDate',
        width : 100,
        name : 'billDate'
      }, {
        text : 'returnedName',
        width : 100,
        name : 'returnedName'
      }, {
        text : 'orderAmount',
        width : 120,
        name : 'orderAmount'
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
        text : 'sellOrderNo',
        width : 240,
        name : 'sellOrderNo'
      }, {
        text : 'sellBillDate',
        width : 100,
        name : 'sellBillDate'
      }, {
        text : 'sellOrderAmount',
        width : 120,
        name : 'sellOrderAmount'
      }, {
        text : 'salesName',
        width : 120,
        name : 'salesName'
      }
  ],
  tools : [
    {
      sortIdx : -1,
      singleCheck : null,
      icon : 'add',
      text : 'sellReturnNotStockOutOrder.grid.addFromSellOrder',
      click : function($button, $plugin, $selecteds, selectedDatas, value, i18nKey) {
        LK.UI.openDialog($.extend({}, {
          size : {
            cols : 4,
            rows : 15
          }
        }, {
          title : i18nKey + 'selectSellOrder',
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
                  var $sellOrderGrid = $form.find('[name=sellOrderList]').parents('.lichkin-plugin').first();
                  // 销售订单ID
                  var sellOrderId = $sellOrderGrid.LKGetValue();
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
                                url : '/SysPssSellReturnNotStockOutOrder/I',
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
                        plugins : sellReturnNotStockOutOrderFormPlugins
                      }, {
                        $appendTo : $contentBar
                      });
                      formOptions.i18nKey = i18nKey + 'columns.';
                      var $form = LK.UI.form(formOptions);

                      $form.find('[name=orderId]').parents('.lichkin-plugin').LKSetValues(sellOrderId, true);

                      LK.ajax({
                        url : '/SysPssSellOrderProduct/L01',
                        data : {
                          orderId : sellOrderId
                        },
                        success : function(responseDatas) {
                          if (responseDatas && responseDatas.length != 0) {
                            var $productList = $form.find('[name=productList]').parents('.lichkin-plugin').first();
                            $productList.LKInvokeAddDatas(responseDatas);
                            sellReturnNotStockOutOrderCalcTotal($productList);
                          } else {
                            LK.alert('sellReturnNotStockOutOrder.grid.no stored products on current sell order');
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
                    i18nKey : 'sellReturnNotStockOutOrder.sellOrder-grid',
                    validator : true,
                    key : 'title',
                    name : 'sellOrderList',
                    valueFieldName : 'id',
                    cols : 4,
                    rows : 15,
                    pageable : false,
                    url : '/SysPssSellOrder/L',
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
                          width : 200
                        }, {
                          text : 'inventoryStatus',
                          name : 'inventoryStatus',
                          width : 200
                        }, {
                          text : 'salesName',
                          name : 'salesName',
                          width : 200
                        }, {
                          text : 'orderAmount',
                          name : 'orderAmount',
                          width : 180
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
                            key : 'salesName',
                            name : 'salesName',
                            cls : 'fuzzy-left fuzzy-right'
                          }
                        }, {
                          plugin : 'droplist',
                          options : {
                            name : 'inventoryStatus',
                            param : {
                              categoryCode : 'PSS_INVENTORY_OUT_STATUS',
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
    saveUrl : '/SysPssSellReturnNotStockOutOrder/U',
    form : {
      url : '/SysPssSellReturnNotStockOutOrder/O',
      plugins : sellReturnNotStockOutOrderFormPlugins
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
          'billDate', 'salesId'
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
      url : '/SysPssSellReturnNotStockOutOrder/O',
      plugins : sellReturnNotStockOutOrderFormPlugins
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
          orderId : value
        },
        tools : []
      });
    }
  },
  toolsRemove : {
    saveUrl : '/SysPssSellReturnNotStockOutOrder/D',
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
    processCode : 'PSS_SELL_RETURN_NOT_STOCK_OUT_ORDER',
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
          key : 'sellOrderNo',
          name : 'sellOrderNo',
          cls : 'fuzzy-left fuzzy-right'
        }
      }, {
        plugin : 'textbox',
        options : {
          key : 'salesName',
          name : 'salesName',
          cls : 'fuzzy-left fuzzy-right'
        }
      }
  ]
}));

var sellReturnNotStockOutOrderCalcTotal = function($productList) {
  var orderAmount = 0;
  $productList.LKGetDataContainer().find('.lichkin-plugin').each(function() {
    if ($(this).LKis('subTotalPrice')) {
      orderAmount += parseFloat($(this).LKGetValue());
    }
  });
  $productList.LKGetSiblingPlugin('orderAmount').LKSetValues(orderAmount.toFixed(2));
};