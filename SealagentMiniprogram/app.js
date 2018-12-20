App({
  onLaunch: function () {
    console.log('App Launch')

    //获取设备信息
    const that = this
    wx.getSystemInfo({
      success: function(res) {
        console.log(res)
        that.globalData.statusBarHeight = res.statusBarHeight;
      },
    })

    // 登录
    wx.login({
      success: res => {
        console.log(res)
        // 发送 res.code 到后台换取 openId, sessionKey, unionId
      }
    })
    // 获取用户信息
    wx.getSetting({
      success: res => {
        console.log(res)

        if (res.authSetting['scope.userInfo']) {
          // 已经授权，可以直接调用 getUserInfo 获取头像昵称，不会弹框
          wx.getUserInfo({
            success: res => {
              console.log(res)
              // 可以将 res 发送给后台解码出 unionId
              this.globalData.wechatInfo = res

              // 由于 getUserInfo 是网络请求，可能会在 Page.onLoad 之后才返回
              // 所以此处加入 callback 以防止这种情况
              if (this.wechatInfoReadyCallback) {
                this.wechatInfoReadyCallback(res)
              }
            }
          })
        }
      }
    })
    
    //初始化登录状态
    var token = wx.getStorageSync('token') || {
      isLogined: false,
      mobileUser: {},
      wechatUser: {}
    }

    this.globalData.token = token
  },
  onShow: function () {
    console.log('App Show')
  },
  onHide: function () {
    console.log('App Hide')
  },
  onError: function () {
    console.log('App Error')
  },
  onPageNotFound: function(){
    console.log('App PageNotFound')
  },
  globalData: {
    statusBarHeight:0,
    host: "http://api.sealagent.com:8080/",
    host_static:"http://api.sealagent.com:8080/static/",
    wechatInfo:null,
    token:{
      isLogined:false,
      mobileUser:{},
      wechatUser:{}
    }
  },
  isLogined:function(){
    return this.globalData.token.isLogined
  },
  getToken:function(){
    const token = this.globalData.token

    if (token.mobileUser.token){
      return token.mobileUser.token
    }

    if (token.wechatUser.token){
      return token.wechatUser.token
    }

    return ''
  },
  getUser_id: function () {
    const token = this.globalData.token

    if (token.mobileUser.id) {
      return token.mobileUser.id
    }

    if (token.wechatUser.user_id) {
      return token.wechatUser.user_id
    }

    return ''
  },
  setMobileUser:function(mobileUser){
    const token = {
      isLogined: true,
      mobileUser: mobileUser,
      wechatUser: {}
    }
    this.globalData.token = token
    wx.setStorageSync('token', token)
  },
  setWechatUser: function (wechatUser) {
    const token = {
      isLogined: true,
      mobileUser: {},
      wechatUser: wechatUser
    }
    this.globalData.token = token
    wx.setStorageSync('token', token)
  },
  logout:function(){
    const token = {
      isLogined: false,
      mobileUser: {},
      wechatUser: {}
    }
    this.globalData.token = token
    wx.setStorageSync('token', token)
  },
});