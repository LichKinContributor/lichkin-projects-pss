LK.UI.datagrid($.extend((typeof LK.home == 'undefined' ? {
  title : 'title',
  icon : 'sellOrder',
} : {}), {
  i18nKey : 'sellOrder',
  $appendTo : true,
  cols : 4,
  url : '',
  columns : [],
  searchForm : []
}));
