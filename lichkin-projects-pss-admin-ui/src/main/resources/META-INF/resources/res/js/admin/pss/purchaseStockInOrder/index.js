var purchaseStockInOrderFormPlugins = [
    {
      plugin : 'hidden',
      options : {
        name : 'orderType',
        value : true
      }
    }, {
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
        key : 'storageName',
        name : 'storageId',
        url : '/SysPssStorage/LD',
        validator : true
      }
    }, {
      plugin : 'textbox',
      options : {
        key : 'purchaseOrderNo',
        name : 'orderId',
        readonly : true,
        commitable : true,
        cols : 3
      }
    }, {
      plugin : 'datagrid',
      options : {
        i18nKey : 'purchaseStockInOrder.product-grid',
        key : 'title',
        name : 'productList',
        validator : 'datas',
        multiSelect : true,
        valueFieldName : 'purchaseOrderProductId',
        cols : 3,
        rows : 11,
        showSearchButton : false,
        pageable : false,
        withoutFieldKey : true,
        url : '/SysPssPurchaseStockOrderProduct/L',
        lazy : true,
        $appendTo : $body,
        columns : [
            {
              text : 'productCode',
              width : 160,
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
              width : 70,
              formatter : function(rowData) {
                return {
                  plugin : 'numberspinner',
                  options : {
                    name : 'quantity',
                    value : (typeof rowData.quantity != 'undefined') ? rowData.quantity : 1,
                    min : 1,
                    max : rowData.canStockInQty
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
  icon : 'purchaseStockInOrder',
} : {}), {
  i18nKey : 'purchaseStockInOrder',
  $appendTo : true,
  cols : 4,
  url : '/SysPssPurchaseStockOrder/P',
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
        text : 'supplierName',
        width : 100,
        name : 'supplierName'
      }, {
        text : 'purchaserName',
        width : 120,
        name : 'purchaserName'
      }
  ],
  tools : [
    {
      sortIdx : -1,
      singleCheck : null,
      icon : 'add',
      text : 'purchaseStockInOrder.grid.addFromPurchaseOrder',
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
                  // 采购订单ID
                  var purchaseOrderId = $purchaseOrderGrid.LKGetValue();
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
                                url : '/SysPssPurchaseStockOrder/I',
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
                        plugins : purchaseStockInOrderFormPlugins
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
                          if (responseDatas && responseDatas.length != 0) {
                            var $productList = $form.find('[name=productList]').parents('.lichkin-plugin').first();
                            $productList.LKInvokeAddDatas(responseDatas);
                          } else {
                            LK.alert('purchaseStockInOrder.grid.no stored products on current purchase order');
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
                    i18nKey : 'purchaseStockInOrder.purchaseOrder-grid',
                    validator : true,
                    key : 'title',
                    name : 'purchaseOrderList',
                    valueFieldName : 'id',
                    cols : 4,
                    rows : 12,
                    pageable : false,
                    url : '/SysPssPurchaseOrder/L',
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
    saveUrl : '/SysPssPurchaseStockOrder/U',
    form : {
      url : '/SysPssPurchaseStockOrder/O',
      plugins : purchaseStockInOrderFormPlugins
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
          orderType : true
        }
      });
    }
  },
  toolsView : {
    form : {
      url : '/SysPssPurchaseStockOrder/O',
      plugins : purchaseStockInOrderFormPlugins
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
          orderType : true,
          isView : true
        },
        tools : []
      });
    }
  },
  toolsRemove : {
    saveUrl : '/SysPssPurchaseStockOrder/D',
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
    processCode : 'PSS_PURCHASE_STOCK_ORDER',
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
          value : true
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
      }

  ]
}));
