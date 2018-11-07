LK.UI.datagrid($.extend((typeof LK.home == 'undefined' ? {
  title : 'title',
  icon : 'stockInDetail',
} : {}), {
  i18nKey : 'stockInDetail',
  $appendTo : true,
  cols : 4,
  url : '',
  columns : [],
  searchForm : []
}));
