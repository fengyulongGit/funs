const network = require("../../static/utils/network.js")
Page({

  /**
   * 页面的初始数据
   */
  data: {
    classifies:[],
    currentTab: 0
  },

  /**
   * 生命周期函数--监听页面加载
   */
  onLoad: function(options) {
    const that = this
    network.getconfiglist({
      success(res){
        for (var i = 0; i < res.length; i++) {
          if (res[i].key == 'classify'){
            let classifies = JSON.parse(res[i].value)
            for(let j in classifies){
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
  },
  //滑动切换
  swiperTab: function (e) {
    var that = this;
    that.setData({
      currentTab: e.detail.current
    });
  },
  //点击切换
  clickTab: function (e) {
    var that = this;
    if (this.data.currentTab === e.target.dataset.current) {
      return false;
    } else {
      that.setData({
        currentTab: e.target.dataset.current
      })
    }
  }
})