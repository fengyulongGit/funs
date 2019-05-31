const network = require("../../static/utils/network.js")
const StringUtils = require("../../static/utils/StringUtils.js")
const app = getApp()
Page({

  /**
   * 页面的初始数据
   */
  data: {
    host_static: app.globalData.host_static,
    userDetail: null,
    avatar: '',
    nickname: '',
    mobile: ''
  },
  onLoad: function(options) {
   
  },
  /**
   * 生命周期函数--监听页面显示
   */
  onShow: function() {
    const that = this

    network.getuserdetail({
      success(data) {
        let avatar = data.avatar
        if (avatar.indexOf("http") < 0) {
          avatar = this.data.host_static + avatar
        }
        that.setData({
          userDetail: data,
          avatar: avatar,
          nickname: data.nickname,
          mobile: StringUtils.securityMobile(data.mobile)
        })
      }
    })
  },
  avatar: function() {
    wx.navigateTo({
      url: '../imagecropper/imagecropper?width=' + 400 + '&height=' + 400,
    })
  },
  nickname: function(e) {
    const nickname = this.data.nicknam
    wx.navigateTo({
      url: '../userdetail_nickname/userdetail_nickname?nickname=' + nickname,
    })
  }
})