const network = require("../../static/utils/network.js")
const app = getApp()
Page({
  data: {
    name: '',
    tel: '',
    address: '',
  },
  onLoad: function(options) {

  },
  inputName: function(e) {
    this.setData({
      name: e.detail.value
    })
  },
  inputTel: function(e) {
    this.setData({
      tel: e.detail.value
    })
  },
  inputAddress: function(e) {
    this.setData({
      address: e.detail.value
    })
  },
  done: function() {
    const {
      name,
      tel,
      address
    } = this.data
    if (!name) {
      wx.showToast({
        title: '请输入店名',
        icon: 'none',
      })
      return
    }
    if (!tel) {
      wx.showToast({
        title: '请输入电话',
        icon: 'none',
      })
      return
    }
    if (!address) {
      wx.showToast({
        title: '请输入地址',
        icon: 'none',
      })
      return
    }

    network.updateuserbusinesscard({
      params: {
        "name": name,
        "tel": tel,
        "address": address,
      },
      success(res) {
        wx.navigateBack({
          delta: 2
        })
      },
    })
  },
})