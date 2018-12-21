const network = require("../../static/utils/network.js")

Page({

  /**
   * 页面的初始数据
   */
  data: {
    tel: ''
  },

  /**
   * 生命周期函数--监听页面加载
   */
  onLoad: function (options) {
    this.setData({
      tel: options.tel || ''
    })
  },
  input: function (e) {
    this.setData({
      tel: e.detail.value
    })
  },
  save: function (e) {
    const tel = this.data.tel

    if (!tel) {
      wx.showToast({
        title: '请输入电话',
        icon: 'none',
      })
      return
    }

    network.updateuserbusinesscard({
      params: {
        "tel": tel,
      },
      success(res) {

        wx.navigateBack({
          delta: 1
        })
      }
    })
  }
})