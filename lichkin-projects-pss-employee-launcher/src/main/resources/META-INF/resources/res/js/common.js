function getScrollTop() {
  var scrollTop = 0;
  if (document.documentElement && document.documentElement.scrollTop) {
    scrollTop = document.documentElement.scrollTop;
  } else if (document.body) {
    scrollTop = document.body.scrollTop;
  }
  return scrollTop;
}

function getClientHeight() {
  var clientHeight = 0;
  if (document.body.clientHeight && document.documentElement.clientHeight) {
    var clientHeight = (document.body.clientHeight < document.documentElement.clientHeight) ? document.body.clientHeight : document.documentElement.clientHeight;
  } else {
    var clientHeight = (document.body.clientHeight > document.documentElement.clientHeight) ? document.body.clientHeight : document.documentElement.clientHeight;
  }
  return clientHeight;
}

function getScrollHeight() {
  return Math.max(document.body.scrollHeight, document.documentElement.scrollHeight);
}

function isPageBottom() {
  if (getScrollTop() + getClientHeight() == getScrollHeight()) {
    return true;
  } else {
    return false;
  }
}

var addPageCommonContent = function(datas) {
  var $lkAppContent = $('<div class="lk-app-content"></div>').appendTo($('body'));
  var $lkAppTitle = $('<div class="lk-app-title"></div>').appendTo($lkAppContent);
  var $backBtn = $('<span class="lk-app-title-backBtn"><</div>').appendTo($lkAppTitle);
  $backBtn.click(function() {
    if (datas.backUrl) {
      var backUrl = _CTX + datas.backUrl + _MAPPING_PAGES;
      var paramsJson = datas.params;
      if (paramsJson) {
        var i = 0;
        for ( var key in paramsJson) {
          if (i == 0) {
            backUrl += '?' + key + '=' + paramsJson[key];
          } else {
            backUrl += '&' + key + '=' + paramsJson[key];
          }
          i++;
        }
      }
      window.location.href = backUrl;
    } else {
      window.history.back();
    }
  });

  if (datas.textKey) {
    $('<span class="lk-app-title-text">' + $.LKGetI18N(datas.textKey) + '</div>').appendTo($lkAppTitle);
  }

  return $lkAppContent;
}