App({
  onLaunch: function () {
    console.log('App Launch')
    const that = this
    wx.getSystemInfo({
      success: function(res) {
        that.globalData.statusBarHeight = res.statusBarHeight;
      },
    })
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
    this.globalData.token = {
      isLogined: true,
      mobileUser: mobileUser,
      wechatUser: {}
    }
  },
  setWechatUser: function (wechatUser) {
    this.globalData.token = {
      isLogined: true,
      mobileUser: {},
      wechatUser: wechatUser
    }
  },
  logout:function(){
    this.globalData.token = {
      isLogined: false,
      mobileUser: {},
      wechatUser: {}
    }
  }
});