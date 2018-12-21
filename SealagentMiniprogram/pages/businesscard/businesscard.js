const network = require("../../static/utils/network.js")
const app = getApp()
Page({

  /**
   * 页面的初始数据
   */
  data: {
    host_static: app.globalData.host_static,
    userBusinessCard: null
  },

  /**
   * 生命周期函数--监听页面加载
   */
  onLoad: function(options) {

  },
  /**
   * 生命周期函数--监听页面显示
   */
  onShow: function() {
    this.getuserbusinesscard()
  },
  getuserbusinesscard(e) {
    const that = this
    network.getuserbusinesscard({
      success(data) {
        that.setData({
          userBusinessCard: data
        })
      }
    })
  },
  changeDisplay: function(e) {
    const that = this
    let display = this.data.userBusinessCard.display
    if (display == 1) {
      display = 0
    } else {
      display = 1
    }
    network.updateuserbusinesscard({
      params: {
        display: display
      },
      success(data) {
        that.setData({
          userBusinessCard: data
        })
      }
    })
  },
  name: function(e) {
    const name = this.data.userBusinessCard.name
    wx.navigateTo({
      url: '../businesscard_name/businesscard_name?name=' + name,
    })
  },
  logo: function(e) {

  },
  dellogo: function(e) {
    const that = this
    network.updateuserbusinesscard({
      params: {
        logo: '-'
      },
      success(data) {
        that.setData({
          userBusinessCard: data
        })
      }
    })
  },
  tel: function(e) {
    const tel = this.data.userBusinessCard.tel
    wx.navigateTo({
      url: '../businesscard_tel/businesscard_tel?tel=' + tel,
    })
  },
  address: function(e) {
    const address = this.data.userBusinessCard.address
    wx.navigateTo({
      url: '../businesscard_address/businesscard_address?address=' + address,
    })
  }
})