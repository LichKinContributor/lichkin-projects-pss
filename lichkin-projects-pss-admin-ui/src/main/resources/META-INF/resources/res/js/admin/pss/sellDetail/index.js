LK.UI.datagrid($.extend((typeof LK.home == 'undefined' ? {
  title : 'title',
  icon : 'sellDetail',
} : {}), {
  i18nKey : 'sellDetail',
  $appendTo : true,
  cols : 4,
  url : '',
  columns : [],
  searchForm : []
}));
