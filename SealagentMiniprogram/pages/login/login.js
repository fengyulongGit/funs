const network = require("../../static/utils/network.js")
const app = getApp()
Page({

  /**
   * 页面的初始数据
   */
  data: {
    wechatInfo: {},
    hasWechatInfo: false,
    canIUse: wx.canIUse('button.open-type.getUserInfo'),
    mobile: '',
    captcha: '',
    countDown: 0,
    timer: null, //定时器名字
    countDownNum: '60', //倒计时初始值
  },

  /*
   * 生命周期函数--监听页面加载
   */
  onLoad: function(options) {
    if (app.globalData.wechatInfo) {
      this.setData({
        wechatInfo: app.globalData.wechatInfo,
        hasWechatInfo: true
      })
    } else if (this.data.canIUse) {
      // 由于 getUserInfo 是网络请求，可能会在 Page.onLoad 之后才返回
      // 所以此处加入 callback 以防止这种情况
      app.wechatInfoReadyCallback = res => {
        this.setData({
          wechatInfo: res,
          hasWechatInfo: true
        })
      }
    } else {
      // 在没有 open-type=getUserInfo 版本的兼容处理
      wx.getUserInfo({
        success: res => {
          app.globalData.wechatInfo = res
          this.setData({
            wechatInfo: res,
            hasWechatInfo: true
          })
        }
      })
    }
  },
  getUserInfo: function(e) {
    const wechatInfo = e.detail

    if (wechatInfo && wechatInfo.userInfo) {
      app.globalData.wechatInfo = wechatInfo
      this.setData({
        wechatInfo: wechatInfo,
        hasWechatInfo: true
      })
      this.wechatlogin()
    }
  },
  inputMobile: function(e) {
    this.setData({
      mobile: e.detail.value
    })
  },
  inputCaptcha: function(e) {
    this.setData({
      captcha: e.detail.value
    })
  },
  sendCaptcha: function(e) {
    const mobile = this.data.mobile
    const that = this
    if (!mobile) {
      wx.showToast({
        title: '手机号不能为空',
        icon: 'none'
      })
      return
    }
    network.sendcaptcha({
      params: {
        "mobile": mobile,
        "template": "login"
      },
      success(res) {
        that.countDown()
      }
    })
  },
  countDown() {
    let that = this
    let countDown = that.data.countDownNum //获取倒计时初始值
    //如果将定时器设置在外面，那么用户就看不到countDown的数值动态变化，所以要把定时器存进data里面
    that.setData({
      timer: setInterval(function() { //这里把setInterval赋值给变量名为timer的变量
        //每隔一秒countDown就减一，实现同步
        countDown--;
        //然后把countDown存进data，好让用户知道时间在倒计着
        that.setData({
          countDown: countDown
        })
        //在倒计时还未到0时，这中间可以做其他的事情，按项目需求来
        if (countDown == 0) {
          //这里特别要注意，计时器是始终一直在走的，如果你的时间为0，那么就要关掉定时器！不然相当耗性能
          //因为timer是存在data里面的，所以在关掉时，也要在data里取出后再关闭
          clearInterval(that.data.timer);
          //关闭定时器之后，可作其他处理codes go here
        }
      }, 1000)
    })
  },
  login: function(e) {
    const mobile = this.data.mobile
    const captcha = this.data.captcha

    network.userlogin({
      params: {
        "mobile": mobile,
        "code": captcha,
      },
      success(res) {
        app.setMobileUser(res)
        network.adduserdevice({
          params: {
            "device_id": app.globalData.uuid,
          },
          success(res) {
            wx.navigateBack({
              delta: 1
            })
          }
        })
      }
    })
  },
  protocol: function(e) {
    wx.navigateTo({
      url: '../protocol/protocol?type=registry',
    })
  },
  wechatlogin: function(e) {
    const wechatInfo = this.data.wechatInfo

    if (!wechatInfo || wechatInfo.length) {
      return
    }

    wx.login({
      success(login) {
        wx.getUserInfo({
          success(userinfo) {
            network.decryptminiprogram({
              params: {
                "js_code": login.code,
                "encrypted_data": userinfo.encryptedData,
                "iv": userinfo.iv
              },
              success(decryptminiprogram) {
                network.wechatlogin({
                  params: {
                    "open_id": decryptminiprogram.open_id,
                    "token": ""
                  },
                  success(wechatuser) {
                    if (!wechatuser) {
                      wx.navigateTo({
                        url: '../login_bind_mobile/login_bind_mobile?decryptminiprogram=' + JSON.stringify(decryptminiprogram),
                      })
                    } else {
                      app.setWechatUser(wechatuser)
                      network.adduserdevice({
                        params: {
                          "device_id": app.globalData.uuid,
                        },
                        success(res) {
                          wx.navigateBack({
                            delta: 1
                          })
                        }
                      })
                    }
                  }
                })
              }
            })
          }
        })
      }
    })
  }
})