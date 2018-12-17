var sellStockOutOrderFormPlugins = [
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
        value : today(),
        minDate : lastMonthDay()
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
        key : 'sellOrderNo',
        name : 'orderId',
        readonly : true,
        commitable : true,
        cols : 3
      }
    }, {
      plugin : 'datagrid',
      options : {
        i18nKey : 'sellStockOutOrder.product-grid',
        key : 'title',
        name : 'productList',
        validator : 'datas',
        multiSelect : true,
        valueFieldName : 'sellOrderProductId',
        cols : 3,
        rows : 11,
        showSearchButton : false,
        pageable : false,
        withoutFieldKey : true,
        url : '/SysPssSellStockOrderProduct/L',
        lazy : true,
        $appendTo : $body,
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
              text : 'unitPrice',
              width : 70,
              name : 'unitPrice'
            }, {
              text : 'salesQuantity',
              width : 70,
              name : 'salesQuantity'
            }, {
              text : 'inventoryQuantity',
              width : 80,
              name : 'inventoryQuantity'
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
                    max : rowData.canStockOutQty
                  }
                }
              }
            }
        ],
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
  icon : 'sellStockOutOrder',
} : {}), {
  i18nKey : 'sellStockOutOrder',
  $appendTo : true,
  cols : 4,
  url : '/SysPssSellStockOrder/P',
  columns : [
      {
        text : 'orderNo',
        width : 260,
        name : 'orderNo',
        cssClass : 'monospacedFont'
      }, {
        text : 'storageName',
        width : 150,
        name : 'storageName'
      }, {
        text : 'billDate',
        width : 100,
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
      }, {
        text : 'sellOrderNo',
        width : 260,
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
      singleCheck : null,
      icon : 'add',
      text : 'sellStockOutOrder.grid.addFromSellOrder',
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
                      cols : 3,
                      rows : 15
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
                                url : '/SysPssSellStockOrder/I',
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
                        plugins : sellStockOutOrderFormPlugins
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
                          } else {
                            LK.alert('sellStockOutOrder.grid.no stored products on current sell order');
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
                    i18nKey : 'sellStockOutOrder.sellOrder-grid',
                    validator : true,
                    key : 'title',
                    name : 'sellOrderList',
                    valueFieldName : 'id',
                    cols : 4,
                    rows : 15,
                    pageable : false,
                    url : '/SysPssSellOrder/L',
                    $appendTo : $body,
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
                            name : 'startDate'
                          }
                        }, {
                          plugin : 'datepicker',
                          options : {
                            key : 'endDate',
                            name : 'endDate'
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
    saveUrl : '/SysPssSellStockOrder/U',
    form : {
      url : '/SysPssSellStockOrder/O',
      plugins : sellStockOutOrderFormPlugins
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
          'billDate', 'storageId'
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
    }
  },
  toolsView : {
    form : {
      url : '/SysPssSellStockOrder/O',
      plugins : sellStockOutOrderFormPlugins
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
          orderId : value,
          orderType : false
        },
        tools : []
      });
    }
  },
  toolsRemove : {
    saveUrl : '/SysPssSellStockOrder/D',
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
    processCode : 'PSS_SELL_STOCK_ORDER',
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
