const app = getApp()
Page({

  /**
   * 页面的初始数据
   */
  data: {
    host_static: app.globalData.host_static,
    work: {},
    editable: false,
    protocol: true,
    writePhotosAlbum:false,
  },

  /**
   * 生命周期函数--监听页面加载
   */
  onLoad: function(options) {
    console.log(options)
    // options.work = '{\"id\":42,\"user_id\":5,\"template_id\":2,\"thumbnail\":\"user/772a12a3721c69f0abc363824e6cf1b6/work/thumbnail/5c778e6d-e8c8-4cf2-8230-6a26fd29e6e9.png\",\"src\":\"user/772a12a3721c69f0abc363824e6cf1b6/work/src/5c778e6d-e8c8-4cf2-8230-6a26fd29e6e9.png\",\"schema\":\"{\\"class\\":\\"Template\\",\\"position\\":{\\"x\\":0,\\"y\\":0},\\"size\\":{\\"width\\":1701,\\"height\\":2550},\\"editable\\":true,\\"moveable\\":false,\\"version\\":1,\\"background\\":{\\"class\\":\\"Image\\",\\"position\\":{\\"x\\":0,\\"y\\":0},\\"size\\":{\\"width\\":1701,\\"height\\":2550},\\"editable\\":true,\\"moveable\\":false,\\"border\\":{\\"class\\":\\"Border\\",\\"position\\":{\\"x\\":0,\\"y\\":0},\\"size\\":{\\"width\\":1701,\\"height\\":2550},\\"editable\\":false,\\"moveable\\":false,\\"style\\":0,\\"weight\\":50,\\"color\\":{\\"rgb\\":8760576,\\"alpha\\":255},\\"radius\\":0},\\"src\\":\\"template/image/b2a0a353-802f-4f88-88f6-b926c3648883.png\\"},\\"title\\":{\\"class\\":\\"Text\\",\\"position\\":{\\"x\\":213,\\"y\\":323},\\"size\\":{\\"width\\":1275,\\"height\\":467},\\"editable\\":true,\\"moveable\\":false,\\"background\\":null,\\"font\\":{\\"family\\":\\"FangZheng-LanTingCuHei\\",\\"style\\":0,\\"size\\":425},\\"color\\":{\\"rgb\\":10228994,\\"alpha\\":255},\\"alignment\\":4,\\"text\\":\\"私房菜\\"},\\"desc\\":{\\"class\\":\\"Text\\",\\"position\\":{\\"x\\":0,\\"y\\":843},\\"size\\":{\\"width\\":1701,\\"height\\":112},\\"editable\\":true,\\"moveable\\":false,\\"background\\":{\\"class\\":\\"Image\\",\\"position\\":{\\"x\\":161,\\"y\\":780},\\"size\\":{\\"width\\":1380,\\"height\\":222},\\"editable\\":false,\\"moveable\\":false,\\"border\\":null,\\"src\\":\\"template/image/9c2c778d-8b74-4099-9a48-ff491f1ec039.png\\"},\\"font\\":{\\"family\\":\\"PingFangSC-Medium\\",\\"style\\":0,\\"size\\":102},\\"color\\":{\\"rgb\\":0,\\"alpha\\":255},\\"alignment\\":4,\\"text\\":\\"你应该享受更好的美食体验\\"},\\"childs\\":[{\\"class\\":\\"Image\\",\\"position\\":{\\"x\\":859,\\"y\\":1434},\\"size\\":{\\"width\\":715,\\"height\\":715},\\"editable\\":false,\\"moveable\\":false,\\"border\\":null,\\"src\\":\\"template/image/63e7e948-7960-490b-9088-57e0ef1af68a.png\\"},{\\"class\\":\\"Text\\",\\"position\\":{\\"x\\":919,\\"y\\":1666},\\"size\\":{\\"width\\":596,\\"height\\":132},\\"editable\\":true,\\"moveable\\":false,\\"background\\":null,\\"font\\":{\\"family\\":\\"FangZheng-LanTingCuHei\\",\\"style\\":0,\\"size\\":120},\\"color\\":{\\"rgb\\":16777215,\\"alpha\\":255},\\"alignment\\":4,\\"text\\":\\"消费满200\\"},{\\"class\\":\\"Text\\",\\"position\\":{\\"x\\":916,\\"y\\":1800},\\"size\\":{\\"width\\":600,\\"height\\":132},\\"editable\\":true,\\"moveable\\":false,\\"background\\":null,\\"font\\":{\\"family\\":\\"FangZheng-LanTingCuHei\\",\\"style\\":0,\\"size\\":120},\\"color\\":{\\"rgb\\":16777215,\\"alpha\\":255},\\"alignment\\":4,\\"text\\":\\"送饮品一杯\\"}],\\"card\\":{\\"class\\":\\"BusinessCard\\",\\"position\\":null,\\"size\\":null,\\"editable\\":false,\\"moveable\\":false,\\"background\\":{\\"class\\":\\"Image\\",\\"position\\":{\\"x\\":0,\\"y\\":2300},\\"size\\":{\\"width\\":1700,\\"height\\":204},\\"editable\\":false,\\"moveable\\":false,\\"border\\":null,\\"src\\":\\"template/image/4179fea3-e982-4f0c-bae7-4c4ee253306f.png\\"},\\"logo\\":{\\"class\\":\\"Image\\",\\"position\\":{\\"x\\":550,\\"y\\":149},\\"size\\":{\\"width\\":600,\\"height\\":150},\\"editable\\":false,\\"moveable\\":false,\\"border\\":null,\\"src\\":\\"\\"},\\"name\\":{\\"class\\":\\"Text\\",\\"position\\":{\\"x\\":0,\\"y\\":180},\\"size\\":{\\"width\\":1701,\\"height\\":121},\\"editable\\":false,\\"moveable\\":false,\\"background\\":null,\\"font\\":{\\"family\\":\\"PingFangSC-Medium\\",\\"style\\":0,\\"size\\":110},\\"color\\":{\\"rgb\\":10228994,\\"alpha\\":255},\\"alignment\\":4,\\"text\\":\\"\\"},\\"tel\\":{\\"class\\":\\"Text\\",\\"position\\":{\\"x\\":0,\\"y\\":2340},\\"size\\":{\\"width\\":1701,\\"height\\":52},\\"editable\\":false,\\"moveable\\":false,\\"background\\":null,\\"font\\":{\\"family\\":\\"PingFangSC-Medium\\",\\"style\\":0,\\"size\\":48},\\"color\\":{\\"rgb\\":0,\\"alpha\\":255},\\"alignment\\":4,\\"text\\":\\"\\"},\\"address\\":{\\"class\\":\\"Text\\",\\"position\\":{\\"x\\":0,\\"y\\":2408},\\"size\\":{\\"width\\":1701,\\"height\\":52},\\"editable\\":false,\\"moveable\\":false,\\"background\\":null,\\"font\\":{\\"family\\":\\"PingFangSC-Medium\\",\\"style\\":0,\\"size\\":48},\\"color\\":{\\"rgb\\":0,\\"alpha\\":255},\\"alignment\\":4,\\"text\\":\\"\\"}}}\",\"count\":0,\"width\":1701,\"height\":2550,\"quality\":72,\"version\":1,\"category\":1,\"type\":1,\"create_time\":null,\"update_time\":null}'
    this.setData({
      work: JSON.parse(options.work),
      editable: options.editable || false
    })

    this.authorizePhotosAlbum()
  },
  onShareAppMessage:function(){

  },
  changeProtocol: function(e) {
    const protocol = this.data.protocol
    this.setData({
      protocol: !protocol
    })
  },
  protocol: function(e) {
    wx.navigateTo({
      url: '../protocol/protocol?type=service',
    })
  },
  preview: function(e) {

  },
  edit: function(e) {
    const template_id = this.data.work.template_id
    const schema = this.data.work.schema
    const work_id = tihs.data.work.id
    wx.navigateTo({
      url: '../phoster/phoster?work_id=' + work_id + '&template_id=' + template_id + '&schema=' + schema,
    })
  },
  download: function(e) {
    const work = this.data.work
    const host_static = this.data.host_static
    const that = this
    wx.showActionSheet({
      itemList: ['下载图片', '下载原图', '分享图片', '复制图片链接'],
      success: function(res) {
        if (!res.cancel) {
          const tapIndex = res.tapIndex
          console.log(tapIndex)

          if (tapIndex == 0) {
            that.downloadImage(host_static+work.thumbnail,res)
          } else if (tapIndex == 1) {
            that.downloadImage(host_static + work.src, res)
          } else if (tapIndex == 2) {

          } else if (tapIndex == 3) {
            wx.setClipboardData({
              data: host_static + work.src,
              success(res){
                wx.showToast({
                  title: '已经复制到剪切板.',
                  icon: 'none'
                })
              }
            })
          }
        }
      }
    })
  },
  authorizePhotosAlbum() {
    const that = this
    //获取相册授权
    wx.getSetting({
      success(res) {
        console.log(res)
        if (!res.authSetting['scope.writePhotosAlbum']) {
          wx.authorize({
            scope: 'scope.writePhotosAlbum',
            success() {
              console.log('授权成功')
              
              that.setData({
                writePhotosAlbum:true
              })
            },
            fail(err) {
              console.log(err)
              that.setData({
                writePhotosAlbum: false
              })
            },
          })
        }else{
          that.setData({
            writePhotosAlbum: true
          })
        }
      }
    })
  },
  downloadImage(url,e) {
    if (!this.data.writePhotosAlbum){
      this.openSetting(e)
      return
    }

    wx.downloadFile({
      url: url,
      success(res) {
        console.log(res)
        //图片保存到本地
        wx.saveImageToPhotosAlbum({
          filePath: res.tempFilePath,
          success(data) {
            wx.showToast({
              title: '保存成功',
              icon: 'success'
            })
          },
          fail(err) {
            console.log(err);
            if (err.errMsg === "saveImageToPhotosAlbum:fail auth deny") {
              console.log("当初用户拒绝，再次发起授权")
              
            }
          }
        })
      }
    })
  },
  openSetting(e){
    const that = this
    
    wx.openSetting({
      success(settingdata) {
        console.log(settingdata)
        if (settingdata.authSetting['scope.writePhotosAlbum']) {
          console.log('获取权限成功，给出再次点击图片保存到相册的提示。')
          wx.showToast({
            title: '再次下载可保存到相册',
            icon: 'none'
          })
          that.setData({
            writePhotosAlbum: true
          })
        } else {
          console.log('获取权限失败，给出不给权限就无法正常使用的提示')
          wx.showToast({
            title: '无权限无法保存到相册',
            icon: 'none'
          })
          that.setData({
            writePhotosAlbum: false
          })
        }
      },
      fail(err) {
        console.log(err);
        that.setData({
          writePhotosAlbum: false
        })
      }
    })
  }
})