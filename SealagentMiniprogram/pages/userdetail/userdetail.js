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
  /**
   * 生命周期函数--监听页面显示
   */
  onShow: function() {
    const that = this

    network.getuserdetail({
      success(data) {
        that.setData({
          userDetail: data,
          avatar: data.avatar,
          nickname: data.nickname,
          mobile: StringUtils.securityMobile(data.mobile)
        })
      }
    })
  },
  avatar: function(e) {
    
  },
  nickname: function(e) {
    const nickname = this.data.nickname
    wx.navigateTo({
      url: '../userdetail_nickname/userdetail_nickname?nickname=' + nickname,
    })
  }
})