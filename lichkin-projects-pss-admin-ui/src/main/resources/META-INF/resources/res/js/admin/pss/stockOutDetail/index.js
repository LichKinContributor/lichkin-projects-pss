LK.UI.datagrid($.extend((typeof LK.home == 'undefined' ? {
  title : 'title',
  icon : 'stockOutDetail',
} : {}), {
  i18nKey : 'stockOutDetail',
  $appendTo : true,
  cols : 4,
  url : '',
  columns : [],
  searchForm : []
}));
