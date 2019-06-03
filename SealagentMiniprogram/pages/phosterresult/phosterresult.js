const network = require("../../static/utils/network.js")

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
    writePhotosAlbum: false,
    workshare: {},
  },

  /**
   * 生命周期函数--监听页面加载
   */
  onLoad: function(options) {
    this.setData({
      work: JSON.parse(options.work),
      editable: options.editable || false
    })

    let that = this
    network.sharework({
      params: {
        "work_id": this.data.work.id,
      },
      success(data) {
        that.setData({
          workshare: data,
        })
      },
    })
  },
  onShow: function() {
    this.authorizePhotosAlbum()
  },
  changeProtocol: function(e) {
    const protocol = this.data.protocol
    this.setData({
      protocol: !protocol
    })
  },
  protocol: function(e) {
    wx.navigateTo({
      url: '../borwser/borwser?type=service',
    })
  },
  preview: function(e) {
    const work = this.data.work
    const host_static = this.data.host_static

    wx.previewImage({
      urls: [host_static + work.src],
    })
  },
  edit: function(e) {
    const template_id = this.data.work.template_id
    const schema = this.data.work.schema
    const work_id = this.data.work.id
    wx.navigateTo({
      url: '../phoster/phoster?work_id=' + work_id + '&template_id=' + template_id + '&schema=' + schema,
      //  + '&category=' + this.data.work.category
    })
  },
  download: function(e) {
    const work = this.data.work
    const host_static = this.data.host_static
    const that = this
    wx.showActionSheet({
      itemList: ['下载作品', '下载原图', '复制作品链接'],
      success: function(res) {
        if (!res.cancel) {
          const tapIndex = res.tapIndex
          if (tapIndex == 0) {
            that.downloadImage(host_static + work.thumbnail, res)
          } else if (tapIndex == 1) {
            that.downloadImage(host_static + work.src, res)
          } else if (tapIndex == 2) {
            wx.setClipboardData({
              data: host_static + work.src,
              success(res) {
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
        if (!res.authSetting['scope.writePhotosAlbum']) {
          wx.authorize({
            scope: 'scope.writePhotosAlbum',
            success() {
              console.log('授权成功')

              that.setData({
                writePhotosAlbum: true
              })
            },
            fail(err) {
              console.log(err)
              that.setData({
                writePhotosAlbum: false
              })
            },
          })
        } else {
          that.setData({
            writePhotosAlbum: true
          })
        }
      }
    })
  },
  downloadImage(url, e) {
    if (!this.data.writePhotosAlbum) {
      this.openSetting(e)
      return
    }

    network.getImageInfo({
      src: url,
      success(res) {
        //图片保存到本地
        wx.saveImageToPhotosAlbum({
          filePath: res.path,
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
  openSetting(e) {
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
  },
  // onShareAppMessage: function(options) {
  //   const host_static = this.data.host_static
  //   const workshare = this.data.workshare
  //   let share = {
  //     title: workshare.title,
  //     imageUrl: host_static + workshare.thumbnail,
  //     path: "/pages/borwser/borwser?url=" + encodeURIComponent(workshare.url.replace("http://", "https://") + "&scenario=1"),
  //   }
  //   console.log(share)
  //   return share
  // },
})