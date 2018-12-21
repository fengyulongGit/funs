var md5 = require("/md5.js")

const app = getApp()

var request = {
  request: function(options) {
    const url = options.url || ''
    const params = options.params || {}
    const message = options.message || ''
    const success = options.success || function(res) {}
    const fail = options.fail || function(res) {
      console.log(res)
    }

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
    wx.showLoading({
      title: message,
    })
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
        if (res.statusCode == 200) {
          if (res.data.code == 0) {
            success(res.data.data)
          } else if (-2 == res.data.code || -4 == res.data.code || -13 == res.data.code || -1000 == res.data.code || -3 == res.data.code) {
            wx.navigateTo({
              url: '../../pages/login/login',
            })
          } else {
            wx.showToast({
              title: res.data.desc,
              icon: 'none'
            })
            fail(res.data)
          }
        } else {
          wx.showToast({
            title: res.data,
            icon: 'none'
          })
          fail(res.data)
        }

      },
      fail: function(res) {
        console.log(res)
        wx.showToast({
          title: res,
          icon: 'none'
        })
        fail(res)
      },
      complete: function(res) {
        console.log(res)
        wx.hideNavigationBarLoading()
        wx.stopPullDownRefresh()
        wx.hideLoading()
      },
    })
  },
  isNotLoginAndJump2: function() {
    if (!app.isLogined()) {
      wx.navigateTo({
        url: '../../pages/login/login',
      })
      return true
    }

    return false
  },
  getImageInfo: function(options) {
    const src = options.src || ''
    const success = options.success || function(res) {}
    const fail = options.fail || function(res) {
      console.log(res)
    }
    if (!src) {
      fail({
        'errMsg': 'src is empty'
      })
      return
    }
    wx.showLoading({
      title: '',
    })
    wx.getImageInfo({
      src: src,
      success(res) {
        console.log(res)

        success(res)
      },
      fail(res) {
        fail(res)
      },
      complete(res) {
        console.log(res)
        wx.hideLoading()
      }
    })
  },
  gettemplateinfolist: function(options) {
    options = options || {}
    options.url = "v1/template/gettemplateinfolist"
    this.request(options)
  },
  gettemplatepicturelist: function(options) {
    options = options || {}
    options.url = "v1/template/gettemplatepicturelist"
    this.request(options)
  },
  savework: function(options) {
    options = options || {}

    options.url = "v1/work/savework"

    let params = options.params || {}
    params.user_id = app.getUser_id()
    params.token = app.getToken()
    options.params = params

    this.request(options)
  },
  sendcaptcha: function(options) {
    options = options || {}
    options.url = "v1/user/sendcaptcha"
    this.request(options)
  },
  userlogin: function(options) {
    options = options || {}
    options.url = "v1/user/userlogin"

    let params = options.params || {}
    params.device = 'wechat'
    params.partner = 'sealagent'
    params.channel = 'sealagent'
    params.spm = ''
    options.params = params

    this.request(options)
  },
  getuserbusinesscard: function(options) {
    options = options || {}
    options.url = "v1/user/getuserbusinesscard"

    let params = options.params || {}
    params.user_id = app.getUser_id()
    params.token = app.getToken()
    options.params = params

    this.request(options)
  },
  getuserdetail: function(options) {
    options = options || {}
    options.url = "v1/user/getuserdetail"

    let params = options.params || {}
    params.user_id = app.getUser_id()
    params.token = app.getToken()
    options.params = params

    this.request(options)
  },
  getworklist: function (options) {
    options = options || {}
    options.url = "v1/work/getworklist"

    let params = options.params || {}
    params.user_id = app.getUser_id()
    params.token = app.getToken()
    options.params = params

    this.request(options)
  }

}

module.exports = request