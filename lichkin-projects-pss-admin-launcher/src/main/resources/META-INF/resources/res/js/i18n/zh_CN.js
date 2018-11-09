$.LKExtendI18N({
  'copyright' : '版权所有',
  'loginBtnName' : '登录',
  'NoLoginName' : '请输入登录名',
  'NoPassword' : '请输入密码',
  'COPYRIGTH' : '苏州鑫宏利业信息科技有限公司',
  'systemName' : '管理平台'
});
$.LKExtendI18N({
  'starter' : '打开',
  'starter-closed' : '关闭',
  'starter-back' : '回退',
  'menu-next' : '下一页',

  'photo' : '头像',
  'modifyPhoto' : '修改头像',
  'photoModifiedSuccess' : '头像修改成功',

  'userInfo' : '个人信息',
  'userName' : '姓名',
  'email' : '邮箱',
  'gender' : '性别',

  'changePwd' : '修改密码',
  'pwdOld' : '原密码',
  'pwdOldError' : '原密码错误',
  'pwdNew' : '新密码',
  'surePwdNew' : '确认密码',
  'pwdInconsistency' : '新密码两次输入不一致',
  'pwdModifiedSuccess' : '密码修改成功',

  'changeTheme' : '更换主题',
  'help' : '使用帮助',
  'toggleFullScreen' : '切换全屏',
  'exit' : '退出登录',

  'helpInfo' : '请将问题发送至customer-service@lichkin.com',

  'GENDER' : {
    'FEMALE' : '女',
    'MALE' : '男',
    'ALIEN' : '外星人',
    'UNKNOWN' : '未知',
    'SECRECY' : '保密',
  },

  // 菜单目录
  'sysMgmt' : '系统管理',
  'orgMgmt' : '组织架构管理',
});
$.LKExtendI18N({
  'menuMgmt' : {
    'title' : '菜单管理',

    'grid' : {
      'title' : '菜单列表',

      'columns' : {
        'menuName' : '菜单名称',
        'url' : '链接地址',
        'icon' : '图标',
        'assignable' : '可分配',
        'rootOnly' : 'ROOT权限',
        'onLine' : '上线'
      },

      'add' : '新增菜单',
      'edit' : '编辑菜单',
    },
  },
  
  'up' : '向上移动',
  'down' : '向下移动',

  'ROOT_ONLY' : {
    'true' : 'ROOT权限可用',
    'false' : '非ROOT权限可用',
    'null' : '通用'
  },

  'ASSIGNABLE' : {
    'true' : '可分配',
    'false' : '不可分配'
  },

  'ON_LINE' : {
    'true' : '已上线',
    'false' : '未上线'
  },
});
$.LKExtendI18N({
  'categoryMgmt' : {
    'title' : '类目管理',

    'grid' : {
      'title' : '类目列表',

      'columns' : {
        'locale' : '国际化',
        'categoryCode' : '类目编码',
        'categoryName' : '类目名称',
        'sortId' : '排序号',
        'authType' : '权限类型',
      },

      'add' : '新增类目',
      'edit' : '编辑类目'
    },
  },
});
$.LKExtendI18N({
  'dictMgmt' : {
    'title' : '字典管理',

    'grid-category' : {
      'title' : '类目列表',

      'columns' : {
        'categoryName' : '类目类型',
      },
    },

    'grid-dictionary' : {
      'title' : '字典列表',

      'columns' : {
        'dictCode' : '编码',
        'dictName' : '名称',
        'sortId' : '排序号',
      },

      'add' : '新增字典',
      'edit' : '编辑字典',
      'remove' : '删除字典',

      'confirm' : {
        'remove' : '数据删除后将不能恢复，确认删除么？',
      },
    },
  },

  'noCategorySelect' : '请先选择类目',
});
$.LKExtendI18N({
  'compDictMgmt' : {
    'title' : '字典管理（R2C）',

    'grid' : {
      'title' : '字典列表',

      'columns' : {
        'categoryCode' : '类目编码',
        'categoryName' : '类目名称',
        'dictName' : '字典名称',
        'dictCode' : '字典编码'
      },
    },

    'comp-grid' : {
      'title' : '公司列表',
      'columns' : {
        'compName' : '公司名称'
      },

      'remove' : '删除',

      'confirm' : {
        'remove' : '数据删除后将不能恢复，确认删除么？',
      }
    }
  }
});
$.LKExtendI18N({
  'roleMgmt' : {
    'title' : '角色管理',

    'grid' : {
      'title' : '角色列表',

      'columns' : {
        'roleName' : '角色名称',
        'description' : '描述',
        'menuName' : '菜单',
      },

      'add' : '新增角色',
      'edit' : '编辑角色',
      'remove' : '删除角色',
      'view' : '查看角色',

      'confirm' : {
        'remove' : '数据删除后将不能恢复，确认删除么？',
      },
    },
  },
});
$.LKExtendI18N({
  'adminMgmt' : {
    'title' : '账号管理',

    'grid' : {
      'title' : '账号列表',

      'columns' : {
        'email' : '账号',
        'userName' : '姓名',
        'roleName' : '角色',
        'photo' : '头像',
        'level' : '等级',
        'usingStatus' : '在用状态',
        'gender' : '性别',
      },

      'add' : '新增账号',
      'edit' : '编辑账号',
      'remove' : '删除账号',
      'resetPwd' : '重置密码',
      'unlock' : '解锁账号',
      'lock' : '锁定账号',
      'view' : '查看账号',

      'confirm' : {
        'remove' : '数据删除后将不能恢复，确认删除么？',
        'resetPwd' : '确认重置密码么？',
        'unlock' : '账号解锁后可正常使用，确认解锁么？',
        'lock' : '账号锁定后将不能使用，确认锁定么？',
      },

      'lock can only be performed when it is USING!' : '只有在用状态才可以进行锁定操作！',
      'unlock can only be performed when it is LOCKED!' : '只有锁定状态才可以进行解锁操作！',
    }
  }
});
$.LKExtendI18N({
  'userMgmt' : {
    'title' : '用户管理',

    'grid' : {
      'title' : '用户列表',

      'columns' : {
        'loginName' : '账户名',
        'cellphone' : '手机号码',
        'email' : '邮箱',
        'userName' : '姓名',
        'birthday' : '出生日期',
        'level' : '等级',
        'insertTime' : '注册时间',
        'startDate' : '起始注册日期',
        'endDate' : '截止注册日期',
        'gender' : '性别',
      }
    }
  }
});
$.LKExtendI18N({
  'loginLog' : {
    'title' : '登录日志查看',

    'grid' : {
      'title' : '登录日志列表',

      'columns' : {
        'userName' : '姓名',
        'email' : '邮箱',
        'requestId' : '请求ID',
        'requestIp' : '登录IP地址',
        'requestTime' : '登录时间',
        'startDate' : '起始日期',
        'endDate' : '截止日期'
      }
    }
  }
});
$.LKExtendI18N({
  'operLog-Admin' : {
    'title' : '操作日志-管理员',

    'grid' : {
      'title' : '操作日志列表-管理员',

      'columns' : {
        'userName' : '姓名',
        'email' : '邮箱',
        'requestId' : '请求ID',
        'requestIp' : '请求IP',
        'requestUrl' : '请求URL',
        'operType' : '操作类型',
        'busType' : '业务操作类型',
        'requestTime' : '操作时间',
        'startDate' : '起始日期',
        'endDate' : '截止日期'
      }
    }
  }
});
$.LKExtendI18N({
  'operLog-User' : {
    'title' : '操作日志-用户',

    'grid' : {
      'title' : '操作日志列表-用户',

      'columns' : {
        'loginName' : '登录名',
        'cellphone' : '手机号码',
        'requestId' : '请求ID',
        'requestIp' : '请求IP',
        'requestUrl' : '请求URL',
        'operType' : '操作类型',
        'busType' : '业务操作类型',
        'requestTime' : '操作时间',
        'startDate' : '起始日期',
        'endDate' : '截止日期'
      }
    }
  }
});
$.LKExtendI18N({
  'operLog-Employee' : {
    'title' : '操作日志-员工',

    'grid' : {
      'title' : '操作日志列表-员工',

      'columns' : {
        'userName' : '姓名',
        'cellphone' : '手机号码',
        'requestId' : '请求ID',
        'requestIp' : '请求IP',
        'requestUrl' : '请求URL',
        'operType' : '操作类型',
        'busType' : '业务操作类型',
        'requestTime' : '操作时间',
        'startDate' : '起始日期',
        'endDate' : '截止日期'
      }
    }
  }
});
$.LKExtendI18N({
  'errorLog' : {
    'title' : '错误日志',
  }
});
$.LKExtendI18N({
  'compMgmt' : {
    'title' : '公司管理',

    'grid' : {
      'title' : '公司列表',

      'columns' : {
        'compName' : '公司名称',
        'compKey' : '公司标识',
        'linkman' : '联系人',
        'telephone' : '联系电话',
        'linkmanCellphone' : '手机号码',
        'email' : '邮箱',
        'address' : '地址',
        'compKey' : '公司标识',
        'website' : '网站地址',
        'brief' : '简介',
        'compLogo' : '公司Logo图片'
      },

      'add' : '新增公司',
      'edit' : '编辑公司',
      'remove' : '删除公司',
      'view' : '查看公司',

      'confirm' : {
        'remove' : '数据删除后将不能恢复，确认删除么？',
      },
    }
  }
});
$.LKExtendI18N({
  'compAdminMgmt' : {
    'title' : '公司账号管理',

    'grid' : {
      'title' : '公司账号列表',

      'columns' : {
        'compName' : '公司名称',
        'email' : '邮箱',
        'userName' : '姓名',
        'roleName' : '角色',
        'photo' : '头像',
        'level' : '等级',
        'usingStatus' : '在用状态',
      },

      'add' : '新增账号',
      'edit' : '编辑账号',
      'remove' : '删除账号',
      'resetPwd' : '重置密码',
      'unlock' : '解锁账号',
      'lock' : '锁定账号',
      'view' : '查看账号',

      'confirm' : {
        'remove' : '数据删除后将不能恢复，确认删除么？',
        'resetPwd' : '确认重置密码么？',
        'unlock' : '账号解锁后可正常使用，确认解锁么？',
        'lock' : '账号锁定后将不能使用，确认锁定么？',
      },

      'lock can only be performed when it is USING!' : '只有在用状态才可以进行锁定操作！',
      'unlock can only be performed when it is LOCKED!' : '只有锁定状态才可以进行解锁操作！',
    }
  }
});
$.LKExtendI18N({
  'deptMgmt' : {
    'title' : '部门管理',

    'grid' : {
      'title' : '部门列表',

      'columns' : {
        'deptName' : '部门名称',
        'description' : '描述',
      },

      'add' : '新增部门',
      'edit' : '编辑部门',
      'remove' : '删除部门',
      'view' : '查看部门',

      'confirm' : {
        'remove' : '数据删除后将不能恢复，确认删除么？',
      },

      'this department has sub department' : '当前部门有下级部门，不能直接删除！'
    }
  }
});
$.LKExtendI18N({
  'employeeMgmt' : {
    'title' : '员工管理',

    'grid' : {
      'title' : '员工列表',

      'columns' : {
        'usingStatus' : '在职状态',
        'jobNumber' : '工号',
        'jobTitle' : '职位',
        'entryDate' : '入职日期',
        'userName' : '姓名',
        'cellphone' : '手机号码',
        'email' : '邮箱',
        'userCard' : '身份证号码',
        'education' : '学历',
        'degree' : '学位',
        'maritalStatus' : '婚姻状态',
        'nation' : '民族',
        'birthplace' : '出生地',
        'deptName' : '部门',
        'gender' : '性别',
      },

      'add' : '录入员工',
      'edit' : '修改员工信息',
      'remove' : '取消录入员工',
      'view' : '查看信息',

      'confirm' : {
        'remove' : '数据删除后将不能恢复，确认删除么？',
      },

      'deprecated employee can not be edit' : '离职员工不能修改',
    }
  }
});
$.LKExtendI18N({
  'activitiProcessConfigMgmt' : {
    'title' : '流程配置管理',

    'grid' : {
      'title' : '工作流配置列表',

      'columns' : {
        'processCode' : '流程编码',
        'processKey' : '流程标识',
        'processType' : '流程类型',
        'processName' : '流程名称',
        'stepCount' : '流程步骤数',
        'compName' : '公司名称',
        'deptName' : '部门',
        'usingStatus' : '配置状态',
      },

      'add' : '新增流程',
      'edit' : '编辑流程',
      'remove' : '删除流程',
      'view' : '查看流程',
      'release' : '发布流程',

      'confirm' : {
        'remove' : '数据删除后将不能恢复，确认删除么？',
        'release' : '发布后将不可编辑和配置表单',
      },

      'all departments' : '所有部门',
      'config form' : '配置表单',
      'reconfig form' : '重新配置表单',
      'step N' : '第N步',

      'must config form before' : '未配置表单的数据不可发布',
      'only locked status can be edit' : '只有待配置表单状态可以编辑',
      'using status can not be config' : '已发布状态不可以配置表单',
      'not using status can not be reconfig' : '非已发布状态不可以重新配置表单',
      'using status can not be remove' : '已发布状态不可以删除',
      'already using' : '已经是发布状态'
    }
  }
});
$.LKExtendI18N({
  'activitiProcessTaskConfigMgmt' : {
    'title' : '审批人配置',

    'grid' : {
      'title' : '工作流列表',

      'columns' : {
        'processName' : '流程名称',
        'deptName' : '部门名称',
      },

      'deptNameAll' : '所有部门',
      'taskStepName' : '第N步',
      'originator' : '发起人',
      'approver' : '审批人',
      'noConfiguration' : '不用配置'
    }
  }
});
$.LKExtendI18N({
  'pssProductCategoryMgmt' : {
    'title' : '产品类别管理',

    'grid' : {
      'title' : '产品类别列表',

      'columns' : {
        'categoryName' : '类别名称'
      },

      'add' : '新增产品类别',
      'edit' : '编辑产品类别',
      'remove' : '删除产品类别',

      'confirm' : {
        'remove' : '数据删除后将不能恢复，确认删除么？',
      },

      'this category has sub category' : '当前产品类别有下级产品类别'
    }
  }
});
$.LKExtendI18N({
  'pssProductMgmt' : {
    'title' : '产品管理',

    'grid' : {
      'title' : '产品列表',

      'columns' : {
        'productCode' : '产品编码',
        'productName' : '产品名称',
        'productCategory' : '产品类型',
        'barcode' : '条形码',
        'unit' : '单位',
        'subProduct' : '下级产品',
        'subProductCount' : '下级产品个数',
        'purchasePrice' : '参考进价',
        'referencePrice' : '参考售价',
        'retailPrice' : '零售价',
        'remarks' : '备注',
        'imageUrl1' : '图片1',
        'imageUrl2' : '图片2',
        'imageUrl3' : '图片3',
        'imageUrl4' : '图片4'
      },

      'add' : '新增产品',
      'edit' : '编辑产品',
      'remove' : '删除产品',
      'view' : '查看产品',

      'confirm' : {
        'remove' : '数据删除后将不能恢复，确认删除么？',
      },
    },

    'pssProductCategroyTreeTitle' : '产品类别',
    'noCategorySelect' : '请选择产品类别'
  }
});
$.LKExtendI18N({
  'pssSupplierMgmt' : {
    'title' : '供应商管理',

    'grid' : {
      'title' : '供应商列表',

      'columns' : {
        'supplierCode' : '供应商编码',
        'supplierName' : '供应商名称',
        'supplierType' : '供应商类型',
        'address' : '地址',
        'linkmanName' : '联系人',
        'linkmanContactWay' : '联系方式',
        'responsiblePerson' : '负责人',
        'cellphone' : '负责人手机号码',
        'remarks' : '备注'
      },

      'add' : '新增供应商',
      'edit' : '编辑供应商',
      'remove' : '删除供应商',

      'confirm' : {
        'remove' : '数据删除后将不能恢复，确认删除么？',
      },
    }
  }
});
$.LKExtendI18N({
  'pssStorageMgmt' : {
    'title' : '仓库管理',

    'grid' : {
      'title' : '仓库列表',

      'columns' : {
        'storageCode' : '仓库编码',
        'storageName' : '仓库名称',
        'address' : '地址',
        'responsiblePerson' : '负责人',
        'cellphone' : '手机号码',
        'remarks' : '备注'
      },

      'add' : '新增仓库',
      'edit' : '编辑仓库',
      'remove' : '删除仓库',

      'confirm' : {
        'remove' : '数据删除后将不能恢复，确认删除么？',
      },
    }
  }
});
$.LKExtendI18N({
  'pssStoreMgmt' : {
    'title' : '门店管理',

    'grid' : {
      'title' : '门店列表',

      'columns' : {
        'storeCode' : '门店编码',
        'storeName' : '门店名称',
        'storageName' : '所属仓库',
        'deptName' : '所属部门',
        'address' : '地址',
        'responsiblePerson' : '负责人',
        'cellphone' : '手机号码',
        'remarks' : '备注'
      },

      'add' : '新增门店',
      'edit' : '编辑门店',
      'remove' : '删除门店',

      'confirm' : {
        'remove' : '数据删除后将不能恢复，确认删除么？',
      },
    }
  }
});
$.LKExtendI18N({
  'pssStoreCashierMgmt' : {
    'title' : '收银员管理',

    'grid' : {
      'title' : '收银员收银员列表',

      'columns' : {
        'storeName' : '门店',
        'cashier' : '收银员',
      },

      'add' : '新增收银员',
      'remove' : '删除收银员',
      'view' : '查看收银员',

      'confirm' : {
        'remove' : '数据删除后将不能恢复，确认删除么？',
      },
    }
  }
});
$.LKExtendI18N({
  'purchaseOrder' : {
    'title' : '采购订单',

    'grid' : {
      'title' : '采购订单',

      'columns' : {
        'orderNo' : '订单号',
        'billDate' : '订单日期',
        'supplierName' : '供应商',
        'purchaserName' : '采购员',
        'orderAmount' : '订单金额',
        'inventoryStatus' : '入库状态',
        'approvalStatus' : '审批状态',
        'approvalTime' : '审批时间',
        'startDate' : '订单开始日期',
        'endDate' : '订单结束日期',
        'scanCode' : '条形码',
        'remarks' : '备注',
      },

      'add' : '录入订单',
      'edit' : '编辑订单',
      'remove' : '删除订单',
      'submit' : '提交订单',
      'view' : '查看订单',

      'confirm' : {
        'remove' : '数据删除后将不能恢复，确认删除么？',
        'submit' : '数据提交后将不能修改，确认提交么？',
      },

      'product not exists' : '产品不存在，请先录入产品。',
      'only PENDING status can be edit' : '只有草稿状态才可编辑。',
      'only PENDING status can be remove' : '只有草稿状态才可删除。',
      'only PENDING status can be submit' : '只有草稿状态才可提交。',
    },

    'product-grid' : {
      'title' : '产品列表',

      'columns' : {
        'productCode' : '产品编码',
        'productName' : '产品名称',
        'barcode' : '条形码',
        'unit' : '单位',
        'quantity' : '数量',
        'unitPrice' : '单价',
        'subTotalPrice' : '小计',
        'product' : '选择产品',
      },

      'add' : '录入产品',
      'remove' : '删除产品',
    },

  }
});
$.LKExtendI18N({
  'purchaseStockInOrder' : {
    'title' : '采购入库单',

    'grid' : {
      'title' : '采购入库单',

      'columns' : {},
    }
  }
});
$.LKExtendI18N({
  'purchaseStockOutOrder' : {
    'title' : '采购退货（出库）单',

    'grid' : {
      'title' : '采购退货（出库）单',

      'columns' : {},
    }
  }
});
$.LKExtendI18N({
  'sellOrder' : {
    'title' : '销售订单',

    'grid' : {
      'title' : '销售订单',

      'columns' : {},
    }
  }
});
$.LKExtendI18N({
  'sellStockInOrder' : {
    'title' : '销售退货（入库）单',

    'grid' : {
      'title' : '销售退货（入库）单',

      'columns' : {},
    }
  }
});
$.LKExtendI18N({
  'sellStockOutOrder' : {
    'title' : '销售出库单',

    'grid' : {
      'title' : '销售出库单',

      'columns' : {},
    }
  }
});
$.LKExtendI18N({
  'allotOrder' : {
    'title' : '调拨单',

    'grid' : {
      'title' : '调拨单',

      'columns' : {
        'orderNo' : '订单编号',
        'billDate' : '调拨日期',
        'outStorageName' : '调出仓库',
        'inStorageName' : '调入仓库',
        'approvalStatus' : '审批状态',
        'approvalTime' : '审批时间',
        'startDate' : '订单开始日期',
        'endDate' : '订单结束日期',
        'scanCode' : '扫码',
        'remarks' : '备注',
      },

      'add' : '录入订单',
      'edit' : '编辑订单',
      'remove' : '删除订单',
      'submit' : '提交订单',
      'view' : '查看订单',

      'confirm' : {
        'remove' : '数据删除后将不能恢复，确认删除么？',
        'submit' : '数据提交后将不能修改，确认提交么？',
      },

      'please choose the storage' : '请选择调出仓库。',
      'product not exists' : '产品不存在，请先录入产品。',
      'only PENDING status can be edit' : '只有草稿状态才可编辑。',
      'only PENDING status can be remove' : '只有草稿状态才可删除。',
      'only PENDING status can be submit' : '只有草稿状态才可提交。',
    },

    'product-grid' : {
      'title' : '产品列表',

      'columns' : {
        'productCode' : '产品编码',
        'productName' : '产品名称',
        'barcode' : '条形码',
        'unit' : '单位',
        'stockQuantity' : '库存数量',
        'quantity' : '调拨数量',
        'product' : '选择产品',
      },

      'add' : '录入产品',
      'remove' : '删除产品',
    },

  }
});
$.LKExtendI18N({
  'checkOrder' : {
    'title' : '盘点单',

    'grid' : {
      'title' : '盘点单',

      'columns' : {
        'orderNo' : '订单编号',
        'billDate' : '盘点日期',
        'storageName' : '仓库',
        'scanCode' : '扫码',
        'stockCheckCount' : '盘点产品数',
        'approvalStatus' : '盘点状态',
        'approvalTime' : '审批时间',
        'startDate' : '订单开始日期',
        'endDate' : '订单结束日期',
        'scanCode' : '扫码',
        'remarks' : '备注',
      },

      'add' : '录入订单',
      'edit' : '编辑订单',
      'remove' : '删除订单',
      'submit' : '提交订单',
      'view' : '查看订单',

      'confirm' : {
        'remove' : '数据删除后将不能恢复，确认删除么？',
        'submit' : '数据提交后将不能修改，确认提交么？',
      },

      'please choose the storage' : '请选择盘点仓库。',
      'product not exists' : '产品不存在，请先录入产品。',
      'only PENDING status can be edit' : '只有草稿状态才可编辑。',
      'only PENDING status can be remove' : '只有草稿状态才可删除。',
      'only PENDING status can be submit' : '只有草稿状态才可提交。',
    },

    'product-grid' : {
      'title' : '产品列表',

      'columns' : {
        'productCode' : '产品编码',
        'productName' : '产品名称',
        'barcode' : '条形码',
        'unit' : '单位',
        'quantity' : '盘点数量',
        'stockQuantity' : '系统数量',
        'differenceQuantity' : '盘盈盘亏',
        'product' : '选择产品',
      },

      'add' : '录入产品',
      'remove' : '删除产品',
    },
    
    
  }
});
$.LKExtendI18N({
  'otherStockInOrder' : {
    'title' : '其他入库单',

    'grid' : {
      'title' : '其他入库单',

      'columns' : {
        'orderNo' : '订单号',
        'storageType' : '入库类型',
        'storageName' : '仓库',
        'billDate' : '订单日期',
        'approvalStatus' : '审批状态',
        'approvalTime' : '审批时间',
        'startDate' : '订单开始日期',
        'endDate' : '订单结束日期',
        'scanCode' : '扫码',
        'remarks' : '备注',
      },

      'add' : '录入订单',
      'edit' : '编辑订单',
      'remove' : '删除订单',
      'submit' : '提交订单',
      'view' : '查看订单',

      'confirm' : {
        'remove' : '数据删除后将不能恢复，确认删除么？',
        'submit' : '数据提交后将不能修改，确认提交么？',
      },

      'product not exists' : '产品不存在，请先录入产品。',
      'only PENDING status can be edit' : '只有草稿状态才可编辑。',
      'only PENDING status can be remove' : '只有草稿状态才可删除。',
      'only PENDING status can be submit' : '只有草稿状态才可提交。',
    },

    'product-grid' : {
      'title' : '产品列表',

      'columns' : {
        'productCode' : '产品编码',
        'productName' : '产品名称',
        'barcode' : '条形码',
        'unit' : '单位',
        'quantity' : '数量',
        'product' : '选择产品',
      }
    },

  }
});
$.LKExtendI18N({
  'otherStockOutOrder' : {
    'title' : '其他出库单',

    'grid' : {
      'title' : '其他出库单',

      'columns' : {
        'orderNo' : '订单号',
        'storageType' : '出库类型',
        'storageName' : '仓库',
        'billDate' : '订单日期',
        'approvalStatus' : '审批状态',
        'approvalTime' : '审批时间',
        'startDate' : '订单开始日期',
        'endDate' : '订单结束日期',
        'scanCode' : '扫码',
        'remarks' : '备注',
      },

      'add' : '录入订单',
      'edit' : '编辑订单',
      'remove' : '删除订单',
      'submit' : '提交订单',
      'view' : '查看订单',

      'confirm' : {
        'remove' : '数据删除后将不能恢复，确认删除么？',
        'submit' : '数据提交后将不能修改，确认提交么？',
      },

      'please select the storage first' : '请先选择仓库。',
      'product not exists' : '产品不存在，请先录入产品。',
      'only PENDING status can be edit' : '只有草稿状态才可编辑。',
      'only PENDING status can be remove' : '只有草稿状态才可删除。',
      'only PENDING status can be submit' : '只有草稿状态才可提交。',
    },

    'product-grid' : {
      'title' : '产品列表',

      'columns' : {
        'productCode' : '产品编码',
        'productName' : '产品名称',
        'barcode' : '条形码',
        'unit' : '单位',
        'stockQuantity' : '库存数量',
        'quantity' : '数量',
        'product' : '选择产品',
      }
    },

  }
});
$.LKExtendI18N({
  'stockDetail' : {
    'title' : '库存清单',

    'grid' : {
      'title' : '库存清单',

      'columns' : {
        'storageName' : '仓库名称',
        'storageCode' : '仓库编码',
        'productName' : '产品名称',
        'productCode' : '产品编码',
        'barcode' : '条形码',
        'quantity' : '产品数量'
      },
    }
  }
});
$.LKExtendI18N({
  'stockDetail' : {
    'title' : '采购单明细',

    'grid' : {
      'title' : '采购单明细',

      'columns' : {},
    }
  }
});
$.LKExtendI18N({
  'sellDetail' : {
    'title' : '销售单明细',

    'grid' : {
      'title' : '销售单明细',

      'columns' : {},
    }
  }
});
$.LKExtendI18N({
  'stockInDetail' : {
    'title' : '入库单明细',

    'grid' : {
      'title' : '入库单明细',

      'columns' : {},
    }
  }
});
$.LKExtendI18N({
  'stockOutDetail' : {
    'title' : '出库单明细',

    'grid' : {
      'title' : '出库单明细',

      'columns' : {},
    }
  }
});
