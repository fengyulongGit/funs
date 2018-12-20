var md5 = require("/md5.js")

const app = getApp()

function request(url, params, success, fail) {
  this.requestLoading(url, params, "", success, fail)
}
// 展示进度条的网络请求
// url:网络请求的url
// params:请求参数
// message:进度条的提示信息
// success:成功的回调函数
// fail：失败的回调
function requestLoading(url, params, message, success, fail) {

  params.ts = Date.parse(new Date()) / 1000

  var arr = "";
  var singRaws = ""
  for (var key in params) {
    if (arr.length > 0) {
      arr += ","
    }

    arr += key;
  }

  arr = arr.split(",").sort()

  for (var i in arr) {
    singRaws += arr[i] + "=" + params[arr[i]] + "@@@"
  }

  singRaws += "key=325622db5b5158ce8267038eb8b22372"
  console.log(singRaws)

  params.sign = md5.md5(singRaws)

  console.log(params)

  // wx.showNavigationBarLoading()
  if (message != "") {
    wx.showLoading({
      title: message,
    })
  }
  wx.request({
    url: getApp().globalData.host + url,
    data: params,
    header: {
      //'Content-Type': 'application/json'
      'content-type': 'application/x-www-form-urlencoded'
    },
    method: 'post',
    success: function(res) {
      console.log(res.data)
      // wx.hideNavigationBarLoading()
      if (message != "") {
        wx.hideLoading()
      }
      if (res.statusCode == 200) {
        if (res.data.code == 0) {
          success(res.data.data)
        } else {
          fail(res.data)
        }
      } else {
        fail(res.data)
      }

    },
    fail: function(res) {
      wx.hideNavigationBarLoading()
      if (message != "") {
        wx.hideLoading()
      }
      fail(res)
    },
    complete: function(res) {

    },
  })
}

function gettemplateinfolist(offset, count, success) {
  this.requestLoading(
    "v1/template/gettemplateinfolist", {
      "version": 1,
      "type": 1,
      "offset": offset,
      "count": count
    },
    "加载中",
    success,
    function(e) {
      console.log(e)
    }
  )
}

function gettemplatepicturelist(template_id, width, height, offset, count, success) {
  this.requestLoading(
    "v1/template/gettemplatepicturelist", {
      "template_id": template_id,
      "width": width,
      "height": height,
      "version": 1,
      "type": 1,
      "offset": offset,
      "count": count
    },
    "加载中",
    success,
    function(e) {
      console.log(e)
    }
  )
}

function savework(template_id, schema,success) {
  this.requestLoading(
    "v1/work/savework", {
      "user_id": app.getUser_id(),
      "token": app.getToken(),
      "template_id": template_id,
      "schema": schema
    },
    "加载中",
    success,
    function(e) {
      console.log(e)
    }
  )
}

module.exports = {
  request: request,
  requestLoading: requestLoading,
  gettemplateinfolist: gettemplateinfolist,
  gettemplatepicturelist: gettemplatepicturelist,
  savework: savework
}