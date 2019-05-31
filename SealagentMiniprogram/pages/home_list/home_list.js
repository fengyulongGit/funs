const network = require("../../static/utils/network.js")

var col1H = 0
var col2H = 0
const app = getApp()

Component({
  /**
   * 组件的属性列表
   */
  properties: {
    category: String
  },

  /**
   * 组件的初始数据
   */
  data: {
    host_static: app.globalData.host_static,
    templateinfolist: [],
    enableMore: true,
    imgWidth: 0,
    col1: [],
    col2: [],
    touch_down: 0, // 触摸时候的 Y 的位置
    loadingTop: -40
  },
  lifetimes: {
    // 生命周期函数，可以为函数，或一个在methods段中定义的方法名
    ready: function() {
      const systemInfo = app.globalData.systemInfo

      var ww = systemInfo.windowWidth;
      var wh = systemInfo.windowHeight;
      var imgWidth = ww * 0.5 - 24;

      this.setData({
        imgWidth: imgWidth
      });

      col1H = 0
      col2H = 0

      this.getlist(0)
    },
  },
  /**
   * 组件的方法列表
   */
  methods: {
    // start: 触摸开始
    start_fn(e) {
      let that = this;
      this.data.touch_down = e.touches[0].clientY;
    },
    move_fn(e) {
      // let current_y = e.changedTouches[0].clientY
      // let that = this
      // let {
      //   touch_down,
      //   loadingTop
      // } = this.data
      // loadingTop += (current_y - touch_down) / 2
      // if (loadingTop > 50) {
      //   loadingTop = 50;
      // } else if (loadingTop < -40) {
      //   loadingTop = -40;
      // }
      // console.log("move_fn " + (loadingTop))

      // // 获取 scroll-wrap 的高度和当前的 scrollTop 位置
      // wx.createSelectorQuery().in(this).select('#scroll-wrap').fields({
      //   scrollOffset: true,
      //   size: true
      // }, function(rect) {
      //   if (rect.scrollTop > 0) {
      //     loadingTop = -40
      //   }
      //   that.setData({
      //     loadingTop: loadingTop,
      //     touch_down: current_y
      //   })
      // }).exec();

    },
    // end: 触摸开始
    // start： 触摸结束
    end_fn(e) {
      let current_y = e.changedTouches[0].clientY;
      let that = this
      let {
        touch_down,
        loadingTop
      } = this.data;
      /**
       * 1、下拉刷新
       * 2、上拉加载
       */
      wx.createSelectorQuery().in(this).select('#scroll-wrap').fields({
        scrollOffset: true,
        size: true
      }, function(rect) {
        if (current_y >= touch_down && rect.scrollTop == 0) {
          // if (loadingTop > 5) {
            that.onPullDownRefresh()
          // } else {
            // that.setData({
            //   loadingTop: 50
            // })
          // }
        }
      }).exec();
    },
    // end: 触摸结束
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
      if (!this.data.enableMore) {
        return
      }
      this.getlist(this.data.templateinfolist.length)
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
      network.gettemplateinfolist({
        params: {
          "version": 1,
          "type": 1,
          "category": this.data.category,
          "offset": offset,
          "count": 20
        },
        success(data) {
          that.setData({
            enableMore: data.length >= 20,
            loadingTop: -40
          })
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
        },
        fail(res) {
          that.setData({
            loadingTop: -40
          })
        }
      })
    },
    phoster: function(e) {
      const id = e.currentTarget.id
      const schema = e.currentTarget.dataset.schema

      wx.navigateTo({
        url: '../phoster/phoster?template_id=' + id + '&schema=' + schema + '&category=' + this.properties.category,
      })
    }
  }
})