var StringUtils = {
  securityMobile: function(text) {
    if (!text) {
      return ''
    }

    let res = ''
    for (let i in text) {
      if (i >= 3 && text.length - i > 4) {
        res += '*'
      } else {
        res += text.charAt(i)
      }
    }
    return res
  },
  dateFtt: function(fmt, date) { //author: meizz   
    var o = {
      "M+": date.getMonth() + 1, //月份   
      "d+": date.getDate(), //日   
      "h+": date.getHours(), //小时   
      "m+": date.getMinutes(), //分   
      "s+": date.getSeconds(), //秒   
      "q+": Math.floor((date.getMonth() + 3) / 3), //季度   
      "S": date.getMilliseconds() //毫秒   
    };
    if (/(y+)/.test(fmt))
      fmt = fmt.replace(RegExp.$1, (date.getFullYear() + "").substr(4 - RegExp.$1.length));
    for (var k in o)
      if (new RegExp("(" + k + ")").test(fmt))
        fmt = fmt.replace(RegExp.$1, (RegExp.$1.length == 1) ? (o[k]) : (("00" + o[k]).substr(("" + o[k]).length)));
    return fmt;
  }
}

module.exports = StringUtils