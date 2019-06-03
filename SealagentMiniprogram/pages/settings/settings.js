// pages/settings/settings.js
const app = getApp()
Page({

  /**
   * 页面的初始数据
   */
  data: {
    isLogined:false
  },
  onLoad:function(){
    this.setData({
      isLogined: app.isLogined()
    })
  },
  service: function(e) {
    wx.navigateTo({
      url: '../borwser/borwser?type=service',
    })
  },
  privacy: function(e) {
    wx.navigateTo({
      url: '../borwser/borwser?type=privacy',
    })
  },
  logout: function(e) {
    wx.showModal({
      content: '您确定要离开海豹特工吗？',
      success(res) {
        if (res.confirm) {

          app.logout()
          wx.switchTab({
            url: '../home/home',
          })
        }
      }
    })
    
  }
})