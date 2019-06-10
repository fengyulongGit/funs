var md5 = require("/md5.js")

const app = getApp()

var request = {
  _genSignParams: function(params) {
    params = params || {}
    params.ts = Date.parse(new Date()) / 1000

    var arr = "";
    var signRaws = ""
    for (var key in params) {
      if (arr.length > 0) {
        arr += ","
      }

      arr += key;
    }

    arr = arr.split(",").sort()

    for (var i in arr) {
      signRaws += arr[i] + "=" + params[arr[i]] + app.globalData.delimiter
    }

    signRaws += "key=" + app.globalData.key
    console.log(signRaws)

    params.sign = md5.md5(signRaws)

    console.log(params)
    return params
  },
  request: function(options) {
    const url = options.url || ''
    const params = options.params || {}
    const message = options.message || ''
    const success = options.success || function(res) {}
    const fail = options.fail || function(res) {
      console.log(res)
    }
    const complete = options.complete || function(res) {}

    // wx.showNavigationBarLoading()
    wx.showLoading({
      title: message,
    })
    wx.request({
      url: getApp().globalData.host + url,
      data: this._genSignParams(params),
      header: {
        //'Content-Type': 'application/json'
        'content-type': 'application/x-www-form-urlencoded'
      },
      method: 'post',
      success: function(res) {
        res.data = typeof(res.data) == "string" ? JSON.parse(res.data) : res.data
        console.log(res.data)
        if (res.statusCode == 200) {
          if (res.data.code == 0) {
            success(res.data.data)
          } else if (-1000 == res.data.code) {
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

        complete(res)
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
  uploadFile: function(options) {
    const category = options.category || ''
    const filePath = options.filePath || ''
    const params = options.params || {}
    const message = options.message || ''
    const success = options.success || function(res) {}
    const fail = options.fail || function(res) {
      console.log(res)
    }
    const complete = options.complete || function(res) {}

    params.user_id = app.getUser_id()
    params.token = app.getToken()

    wx.showLoading({
      title: message,
    })

    wx.uploadFile({
      url: getApp().globalData.host + "v1/file/" + category + "/upload",
      filePath: filePath,
      name: 'file',
      formData: this._genSignParams(params),
      success: function(res) {
        res.data = typeof(res.data) == "string" ? JSON.parse(res.data) : res.data
        console.log(res.data)
        if (res.statusCode == 200) {
          if (res.data.code == 0) {
            success(res.data.data)
          } else if (-1000 == res.data.code) {
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

        complete(res)
      },
    })
  },
  getconfiglist: function(options) {
    options = options || {}
    options.url = "v1/app/getconfiglist"

    let params = options.params || {}
    params.name = 'sealagent'
    params.os = 'miniprogram'
    options.params = params

    this.request(options)
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
    params.device = 'miniprogram'
    params.source = 'miniprogram'
    params.partner = 'sealagent'
    params.channel = 'sealagent'
    params.spm = ''
    options.params = params

    this.request(options)
  },
  decryptminiprogram: function(options) {
    options = options || {}
    options.url = "v1/user/decryptminiprogram"

    let params = options.params || {}
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
  wechatlogin: function(options) {
    options = options || {}
    options.url = "v1/user/wechatlogin"

    let params = options.params || {}
    params.device = 'miniprogram'
    params.source = 'miniprogram'
    params.partner = 'sealagent'
    params.channel = 'sealagent'
    params.spm = ''
    options.params = params

    this.request(options)
  },
  getuserwechatlist: function (options) {
    options = options || {}
    options.url = "v1/user/getuserwechatlist"

    let params = options.params || {}
    params.user_id = app.getUser_id()
    params.token = app.getToken()
    options.params = params

    this.request(options)
  },
  wechatbind: function(options) {
    options = options || {}
    options.url = "v1/user/wechatbind"

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
  getworklist: function(options) {
    options = options || {}
    options.url = "v1/work/getworklist"

    let params = options.params || {}
    params.user_id = app.getUser_id()
    params.token = app.getToken()
    options.params = params

    this.request(options)
  },
  deletework: function(options) {
    options = options || {}
    options.url = "v1/work/deletework"

    let params = options.params || {}
    params.user_id = app.getUser_id()
    params.token = app.getToken()
    options.params = params

    this.request(options)
  },
  sharework: function (options) {
    options = options || {}
    options.url = "v1/work/sharework"

    let params = options.params || {}
    params.user_id = app.getUser_id()
    params.token = app.getToken()
    options.params = params

    this.request(options)
  },
  updateuserdetail: function(options) {
    options = options || {}
    options.url = "v1/user/updateuserdetail"

    let params = options.params || {}
    params.user_id = app.getUser_id()
    params.token = app.getToken()
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
  updateuserbusinesscard: function(options) {
    options = options || {}
    options.url = "v1/user/updateuserbusinesscard"

    let params = options.params || {}
    params.user_id = app.getUser_id()
    params.token = app.getToken()
    options.params = params

    this.request(options)
  },
  adddeviceinfo: function(options) {
    options = options || {}
    options.url = "v1/device/adddeviceinfo"

    let params = options.params || {}
    params.os = 'miniprogram'
    options.params = params

    this.request(options)
  },
  adduserdevice: function (options) {
    options = options || {}
    options.url = "v1/user/adduserdevice"

    let params = options.params || {}
    params.user_id = app.getUser_id()
    params.token = app.getToken()
    params.os = 'miniprogram'
    options.params = params

    this.request(options)
  },
  adduserhistory: function (options) {
    options = options || {}
    options.url = "v1/user/adduserhistory"

    let params = options.params || {}
    params.user_id = app.getUser_id()
    params.token = app.getToken()
    options.params = params

    this.request(options)
  },
}

module.exports = request