const network = require("../../static/utils/network.js")
const StringUtils = require("../../static/utils/StringUtils.js")
var col1H = 0;
var col2H = 0;
var col3H = 0;

const app = getApp()
Page({
  startTime: 0,
  endTime: 0,
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
    col3: [],
    isDelete:false,
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

  },
  onShow:function(){

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
  },
  checked:function(e){
    if(this.data.isDelete){
      this.setCheckStatus(e)
      return
    }
    const work = e.currentTarget.dataset.work
    wx.navigateTo({
      url: '../phosterresult/phosterresult?editable=true&work=' + JSON.stringify(work),
    })
  },
  openDelete:function(e){
    this.setData({
      isDelete:true
    })
    this.setCheckStatus(e)
  },
  setCheckStatus(e){
    let col = e.currentTarget.dataset.col
    let index = e.currentTarget.dataset.index || 0
    let work
    if(col == 1){
      work = this.data.col1[index]
    } else if (col == 2) {
      work = this.data.col2[index]
    } else if (col == 3) {
      work = this.data.col3[index]
    }
    
    let isChecked = work.checked || false

    work.checked = !isChecked

    let itemlist = 'col' + col+'[' + index + ']'
    this.setData({
      [itemlist]: work
    })
  },
  deleteWorks:function(e){
    let ids = []
    const col1 = this.data.col1
    const col2 = this.data.col2
    const col3 = this.data.col3
    for (let i in col1){
      const work = col1[i]
      let isChecked = work.checked || false
      if (isChecked){
        ids.push(String(work.id))
      }
    }
    for (let i in col2) {
      const work = col2[i]
      let isChecked = work.checked || false
      if (isChecked) {
        ids.push(String(work.id))
      }
    }
    for (let i in col3) {
      const work = col3[i]
      let isChecked = work.checked || false
      if (isChecked) {
        ids.push(String(work.id))
      }
    }
    console.log(ids)

    var that = this
    wx.showModal({
      content: '是否删除选择的作品?',
      success(res) {
        if (res.confirm) {
          network.deletework({
            params:{
              work_id_list: JSON.stringify(ids)
            },
            success(data){
              that.setData({
                isDelete:false
              })
              
              that.onShow()
            }
          })
        }
      }
    })
  }
})