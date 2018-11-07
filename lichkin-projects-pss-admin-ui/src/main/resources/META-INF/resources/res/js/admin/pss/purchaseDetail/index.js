LK.UI.datagrid($.extend((typeof LK.home == 'undefined' ? {
  title : 'title',
  icon : 'purchaseDetail',
} : {}), {
  i18nKey : 'purchaseDetail',
  $appendTo : true,
  cols : 4,
  url : '',
  columns : [],
  searchForm : []
}));
