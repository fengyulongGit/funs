const network = require("../../static/utils/network.js")

Page({

  /**
   * 页面的初始数据
   */
  data: {
    address: ''
  },

  /**
   * 生命周期函数--监听页面加载
   */
  onLoad: function (options) {
    this.setData({
      address: options.address || ''
    })
  },
  input: function (e) {
    this.setData({
      address: e.detail.value
    })
  },
  save: function (e) {
    const address = this.data.address

    if (!address) {
      wx.showToast({
        title: '请输入地址',
        icon: 'none',
      })
      return
    }

    network.updateuserbusinesscard({
      params: {
        "address": address,
      },
      success(res) {

        wx.navigateBack({
          delta: 1
        })
      }
    })
  }
})