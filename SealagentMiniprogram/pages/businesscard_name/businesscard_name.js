const network = require("../../static/utils/network.js")

Page({

  /**
   * 页面的初始数据
   */
  data: {
    name: ''
  },

  /**
   * 生命周期函数--监听页面加载
   */
  onLoad: function (options) {
    this.setData({
      name: options.name || ''
    })
  },
  input: function (e) {
    this.setData({
      name: e.detail.value
    })
  },
  save: function (e) {
    const name = this.data.name

    if (!name) {
      wx.showToast({
        title: '请输入店名',
        icon: 'none',
      })
      return
    }

    network.updateuserbusinesscard({
      params: {
        "name": name,
      },
      success(res) {

        wx.navigateBack({
          delta: 1
        })
      }
    })
  }
})