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
    mobile: '',
    works: []
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
    const that = this

    if (app.isLogined()) {
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
      network.getworklist({
        params: {
          'version': 1,
          'type': 1,
          'offset': 0,
          'count': 5
        },
        success(data) {
          that.setData({
            works: data
          })
        }
      })
    } else {
      that.setData({
        userDetail: null,
        avatar: '',
        nickname: '',
        mobile: '',
        works: []
      })
    }
  },

  userdetail: function(e) {
    if (network.isNotLoginAndJump2()) {
      return
    }

    wx.navigateTo({
      url: '../userdetail/userdetail',
    })
  },
  businesscard: function(e) {
    if (network.isNotLoginAndJump2()) {
      return
    }

    wx.navigateTo({
      url: '../businesscard/businesscard',
    })
  },
  phosterresult:function(e){
    const work = e.currentTarget.dataset.work
    wx.navigateTo({
      url: '../phosterresult/phosterresult?editable=true&work=' + JSON.stringify(work),
    })
  },
  work: function(e) {
    if (network.isNotLoginAndJump2()) {
      return
    }

    wx.navigateTo({
      url: '../work/work',
    })
  },
  feedback: function(e) {
    // wx.showToast({
    //   title: '稍后开通，请使用联系我们',
    //   icon: 'none'
    // })
    const that = this
    wx.showModal({
      content: '稍后开通，是否使用电话联系我们？',
      success(res) {
        if (res.confirm) {
          that.callus()
        }
      }
    })
  },
  callus: function(e) {
    wx.makePhoneCall({
      phoneNumber: '186 1236 0431'
    })
  },
  settings: function(e) {
    wx.navigateTo({
      url: '../settings/settings',
    })
  }
})