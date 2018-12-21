const network = require("../../static/utils/network.js")

Page({

  /**
   * 页面的初始数据
   */
  data: {
    nickname: ''
  },

  /**
   * 生命周期函数--监听页面加载
   */
  onLoad: function(options) {
    this.setData({
      nickname: options.nickname || ''
    })
  },
  input: function(e) {
    this.setData({
      nickname: e.detail.value
    })
  },
  save: function(e) {
    const nickname = this.data.nickname

    if (!nickname) {
      wx.showToast({
        title: '请输入昵称',
        icon: 'none',
      })
      return
    }

    network.updateuserdetail({
      params: {
        "nickname": nickname,
      },
      success(res) {
        
        wx.navigateBack({
          delta: 1
        })
      }
    })
  }
})