const network = require("../../static/utils/network.js")
const StringUtils = require("../../static/utils/StringUtils.js")
var col1H = 0;
var col2H = 0;
var col3H = 0;

const app = getApp()
Page({

  /**
   * 页面的初始数据
   */
  data: {
    host_static: app.globalData.host_static,
    worklist: [],
    enableMore: true,
    imgWidth: 0,
    col1: [],
    col2: [],
    col3: []
  },

  /**
   * 生命周期函数--监听页面加载
   */
  onLoad: function(options) {
    const systemInfo = app.globalData.systemInfo

    var ww = systemInfo.windowWidth;
    var wh = systemInfo.windowHeight;
    var imgWidth = ww / 3 - 24;

    this.setData({
      imgWidth: imgWidth
    });

    col1H = 0
    col2H = 0
    col3H = 0

    this.getlist(0)
  },
  /**
   * 页面相关事件处理函数--监听用户下拉动作
   */
  onPullDownRefresh: function() {
    col1H = 0
    col2H = 0
    col3H = 0
    this.getlist(0)
  },

  /**
   * 页面上拉触底事件的处理函数
   */
  onReachBottom: function() {
    if (!this.data.enableMore){
      return
    }
    this.getlist(this.data.worklist.length)
  },

  /**
   * 用户点击右上角分享
   */
  onShareAppMessage: function() {

  },
  waterFall(list) {
    for (var i = 0; i < list.length; i++) {
      var work = list[i]
      var oImgW = work.width; //图片原始宽度
      var oImgH = work.height; //图片原始高度
      var imgWidth = this.data.imgWidth; //图片设置的宽度
      var scale = imgWidth / oImgW; //比例计算
      var imgHeight = oImgH * scale; //自适应高度

      work.imgWidth = imgWidth;
      work.imgHeight = imgHeight;

      work.create_time = StringUtils.dateFtt('yyyy-MM-dd', new Date(work.create_time))

      if (col1H <= col2H && col1H <= col3H) {
        col1H += imgHeight;

        var length = this.data.col1.length
        if (!length) {
          length = 0
        }
        var itemlist = 'col1[' + length + ']'
        this.setData({
          [itemlist]: work
        })
      } else if (col2H <= col1H && col2H <= col3H) {
        col2H += imgHeight;

        var length = this.data.col2.length
        if (!length) {
          length = 0
        }
        var itemlist = 'col2[' + length + ']'
        this.setData({
          [itemlist]: work
        })
      }else{
        col3H += imgHeight;

        var length = this.data.col3.length
        if (!length) {
          length = 0
        }
        var itemlist = 'col3[' + length + ']'
        this.setData({
          [itemlist]: work
        })
      }
    }
  },
  getlist(offset) {
    var that = this
    network.getworklist({
      params: {
        "version": 1,
        "type": 1,
        "offset": offset,
        "count": 20
      },
      success(data) {
        console.log(data)
        that.setData({
          enableMore: data.length >= 20
        })
        if (offset == 0) {
          that.setData({
            col1: [],
            col2: [],
            col3: []
          })
          that.setData({
            worklist: data
          })
        } else {
          for (var i = 0; i < data.length; i++) {
            var itemlist = 'worklist[' + (offset + i) + ']'
            that.setData({
              [itemlist]: data[i]
            })
          }
          var itemlist = 'worklist[' + offset + ']'
          that.setData({
            [itemlist]: data
          })
        }
        that.waterFall(data)
      }
    })
  },
  changeChecked: function(e) {
    const id = e.currentTarget.id
    const schema = e.currentTarget.dataset.schema

    // wx.navigateTo({
    //   url: '../phoster/phoster?template_id=' + id + '&schema=' + schema,
    // })
  },
  start: function(e) {
    wx.switchTab({
      url: '../main/main',
    })
  }
})