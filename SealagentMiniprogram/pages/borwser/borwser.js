Page({

  /**
   * 页面的初始数据
   */
  data: {
    urls: {
      "registry":'https://www.sealagent.com/company/registry?show=app',
      "service":'https://www.sealagent.com/company/service?show=app',
      "privacy":'https://www.sealagent.com/company/privacy?show=app',
      "contact":'https://www.sealagent.com/company/contact?show=app'
    },
    url:'',
  },

  /**
   * 生命周期函数--监听页面加载
   */
  onLoad: function (options) {
    const url = decodeURIComponent(options.url || '')
    if(url){
      this.setData({
        url:url
      })
      return
    }
    const type = options.type
    if (type){
      const url = decodeURIComponent(this.data.urls[type] || '')
      if(url){
        this.setData({
          url: url
        })
        return
      }
    }
  },

  /**
   * 生命周期函数--监听页面初次渲染完成
   */
  onReady: function () {

  },

  /**
   * 生命周期函数--监听页面显示
   */
  onShow: function () {

  },

  /**
   * 生命周期函数--监听页面隐藏
   */
  onHide: function () {

  },

  /**
   * 生命周期函数--监听页面卸载
   */
  onUnload: function () {

  },

  /**
   * 页面相关事件处理函数--监听用户下拉动作
   */
  onPullDownRefresh: function () {

  },

  /**
   * 页面上拉触底事件的处理函数
   */
  onReachBottom: function () {

  },
})