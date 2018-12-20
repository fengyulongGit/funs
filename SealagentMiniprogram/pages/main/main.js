// Page/main/main.js

const network = require("../../utils/network.js")

var col1H = 0;
var col2H = 0;

const app = getApp()
Page({

  /**
   * 页面的初始数据
   */
  data: {
    host_static: app.globalData.host_static,
    templateinfolist: [],
    imgWidth: 0,
    col1: [],
    col2: []
  },

  /**
   * 生命周期函数--监听页面加载
   */
  onLoad: function(options) {
    wx.getSystemInfo({
      success: (res) => {
        var ww = res.windowWidth;
        var wh = res.windowHeight;
        var imgWidth = ww * 0.5 - 24;

        this.setData({
          imgWidth: imgWidth
        });

        col1H = 0
        col2H = 0

        this.getlist(0)
      }
    })
  },
  /**
   * 页面相关事件处理函数--监听用户下拉动作
   */
  onPullDownRefresh: function() {
    col1H = 0
    col2H = 0
    this.getlist(0)
  },

  /**
   * 页面上拉触底事件的处理函数
   */
  onReachBottom: function() {
    this.getlist(this.data.templateinfolist.length)
  },

  /**
   * 用户点击右上角分享
   */
  onShareAppMessage: function() {

  },
  waterFall(list) {
    for (var i = 0; i < list.length; i++) {
      var templateinfo = list[i]
      var oImgW = templateinfo.width; //图片原始宽度
      var oImgH = templateinfo.height; //图片原始高度
      var imgWidth = this.data.imgWidth; //图片设置的宽度
      var scale = imgWidth / oImgW; //比例计算
      var imgHeight = oImgH * scale; //自适应高度

      templateinfo.imgWidth = imgWidth;
      templateinfo.imgHeight = imgHeight;

      if (col1H <= col2H) {
        col1H += imgHeight;

        var length = this.data.col1.length
        if (!length) {
          length = 0
        }
        var itemlist = 'col1[' + length + ']'
        this.setData({
          [itemlist]: templateinfo
        })
      } else {
        col2H += imgHeight;

        var length = this.data.col2.length
        if (!length) {
          length = 0
        }
        var itemlist = 'col2[' + length + ']'
        this.setData({
          [itemlist]: templateinfo
        })
      }
    }
  },
  getlist(offset) {
    var that = this
    network.gettemplateinfolist(
      offset,
      20,
      function(data) {
        console.log(data)
        if (offset == 0) {
          that.setData({
            col1: [],
            col2: []
          })
          that.setData({
            templateinfolist: data
          })
        } else {
          for (var i = 0; i < data.length; i++) {
            var itemlist = 'templateinfolist[' + (offset + i) + ']'
            that.setData({
              [itemlist]: data[i]
            })
          }
          var itemlist = 'templateinfolist[' + offset + ']'
          that.setData({
            [itemlist]: data
          })
        }
        that.waterFall(data)

        // console.log(that.data.col1)
        // console.log(that.data.col2)
      }
    )
  },
  phoster: function(event) {
    const id = event.currentTarget.id
    const schema = event.currentTarget.dataset.schema

    wx.navigateTo({
      url: '../phoster/phoster?template_id=' + id + '&schema=' + schema,
    })
  }
})