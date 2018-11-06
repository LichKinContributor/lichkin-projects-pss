LK.SUB_URL = '/Employee';

/**
 * 跳转页面
 * @param url 跳转地址
 * @param params 参数
 */
LK.Go = function(url, params) {
  url = _CTX + url + _MAPPING_PAGES + '?__=' + new Date().getTime();
  if (isJSON(params)) {
    for ( var key in params) {
      url += '&' + key + '=' + params[key];
    }
  }
  window.location.href = url;
};

/**
 * 初始化标题
 * @param title I18N标题
 * @param backUrl 返回按钮点击跳转地址
 * @param params 参数
 */
LK.initTitle = function(title, backUrl, params) {
  if (isString(backUrl)) {
    $('.title-back').click(function() {
      LK.Go(backUrl, params);
    });
  }
  if (Array.isArray(title)) {
    var titleText = '';
    for (var i = 0; i < title.length; i++) {
      titleText += $.LKGetI18N(title[i]);
    }
    $('.title .title-text').html(titleText);
  } else {
    $('.title .title-text').html($.LKGetI18N(title));
  }
};

/**
 * 初始化列表项
 * @param itemId 列表项ID
 * @param title 列表项I18N标题
 * @param 列表项点击跳转地址
 */
LK.initListItem = function(itemId, title, linkUrl) {
  var $item = $('#' + itemId);
  $item.children('.list-item-text').html($.LKGetI18N(title));
  $item.click(function() {
    LK.Go(linkUrl);
  });
};

/**
 * 滑动加载数据
 * @param key 全局变量名
 * @param url 数据请求地址
 * @param data 数据请求参数
 * @param addData 新增数据回调方法。回传当前行数据。
 */
LK.scrollDatas = function(key, url, data, addData, apiSubUrl) {
  window[key] = {
    pageNumber : 0,
    isLastPage : false,
    scrollDatas : function() {
      if (window[key].isLastPage) {
        LK.toast($.LKGetI18N('No more datas'));
        return;
      }
      LK.ajax({
        url : url,
        apiSubUrl : apiSubUrl,
        data : $.extend({
          pageNumber : window[key].pageNumber
        }, data),
        success : function(responseDatas) {
          if (responseDatas.content) {
            window[key].isLastPage = responseDatas.last;
            window[key].pageNumber = responseDatas.number + 1;
            if (responseDatas.content.length != 0) {
              $('body').attr('style', "");
              for (var i = 0; i < responseDatas.content.length; i++) {
                addData(responseDatas.content[i]);
              }
            }
          } else {
            window[key].isLastPage = responseDatas.length == 0;
            window[key].pageNumber++;
            if (responseDatas.length != 0) {
              $('body').attr('style', "");
              for (var i = 0; i < responseDatas.length; i++) {
                addData(responseDatas[i]);
              }
            }
          }
        }
      });
    }
  };

  window[key].scrollDatas();

  $(window).scroll(function() {
    if ($(this).scrollTop() + $(this).height() >= $(document).height()) {
      window[key].scrollDatas();
    }
  });
}