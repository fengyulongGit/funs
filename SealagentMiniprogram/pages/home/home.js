const network = require("../../static/utils/network.js")
const app = getApp()
Page({

  /**
   * 页面的初始数据
   */
  data: {
    classifies: [],
    currentTab: 0, //预设当前项的值
    scrollLeft: 0, //tab标题的滚动条位置
  },

  /**
   * 生命周期函数--监听页面加载
   */
  onLoad: function(options) {
    const that = this

    network.getconfiglist({
      success(res) {
        for (var i = 0; i < res.length; i++) {
          if (res[i].key == 'classify') {
            let classifies = JSON.parse(res[i].value)
            for (let j in classifies) {
              classifies[j].icon = JSON.parse(classifies[j].icon)
            }
            that.setData({
              classifies: classifies
            })
            break
          }
        }
      }
    })

    network.adddeviceinfo({
      params: {
        "device_id": app.globalData.uuid,
        "context": JSON.stringify(app.globalData.systemInfo),
      }
    })
  },
  //滑动切换
  swiperTab: function(e) {
    this.setData({
      currentTab: e.detail.current
    });
    this.checkCor()
  },
  //点击切换
  clickTab: function(e) {
    let cur = e.target.dataset.current
    if (this.data.currentTab === cur) {
      return false;
    } else {
      this.setData({
        currentTab: cur
      })
    }
    // this.checkCor()
  },
  //判断当前滚动超过一屏时，设置tab标题滚动条。
  checkCor: function() {
    const that = this
    const currentTab = this.data.currentTab

    wx.createSelectorQuery().select('#tab_' + currentTab).boundingClientRect(function(rect) {
      var itemleft = rect.left,
        itemwidth = rect.width;

      wx.createSelectorQuery().select('.swiper-tab').fields({
        scrollOffset: true,
        size: true
      }, function(rect) {

        var scrollwidth = rect.width,
          scrollLeft = rect.scrollLeft;

        //左边
        if (itemleft < 0) {
          if (currentTab == 0) {
            scrollLeft = 0
          } else {
            scrollLeft = -itemleft
          }
        } else if (itemleft + itemwidth > scrollLeft + scrollwidth) {
          scrollLeft = itemleft + itemwidth - scrollwidth
        }

        that.setData({
          scrollLeft: scrollLeft
        })
      }).exec();
    }).exec()
  },
})