LK.UI.datagrid($.extend((typeof LK.home == 'undefined' ? {
  title : 'title',
  icon : 'pssProductCategoryMgmt',
} : {}), {
  i18nKey : 'pssProductCategoryMgmt',
  $appendTo : true,
  url : '/SysPssProductCategory/S',
  cols : 2,
  pageable : false,
  treeFieldName : 'categoryName',
  columns : [
    {
      text : 'categoryName',
      width : null,
      name : 'categoryName'
    }
  ],
  toolsAdd : {
    saveUrl : '/SysPssProductCategory/I',
    dialog : {
      size : {
        cols : 1,
        rows : 1
      }
    },
    form : {
      plugins : [
        {
          plugin : 'textbox',
          options : {
            name : 'categoryName',
            validator : true,
            maxlength : 64
          }
        }
      ]
    },
    beforeSave : function($button, $datagrid, $selecteds, selectedDatas, value, $dialogButton, $dialog) {
      if (selectedDatas.length == 0) {
        return {
          parentCode : 'ROOT'
        };
      } else {
        return {
          parentCode : selectedDatas[0].code
        };
      }
    }
  },
  toolsEdit : {
    saveUrl : '/SysPssProductCategory/U',
    dialog : {
      size : {
        cols : 1,
        rows : 1
      }
    },
    form : {
      url : '/SysPssProductCategory/O',
      plugins : [
        {
          plugin : 'textbox',
          options : {
            name : 'categoryName',
            validator : true,
            maxlength : 64
          }
        }
      ]
    }
  },
  toolsRemove : {
    saveUrl : '/SysPssProductCategory/US',
    beforeClick : function($button, $datagrid, $selecteds, selectedDatas, value, i18nKey) {
      if (selectedDatas[0].children.length != 0) {
        LK.alert(i18nKey + 'this category has sub category');
        return false;
      }
      return true;
    }
  },
  searchForm : [
    {
      plugin : 'textbox',
      options : {
        name : 'categoryName',
        cls : 'fuzzy-left fuzzy-right'
      }
    }
  ]
}));