const network = require("../../static/utils/network.js")
const StringUtils = require("../../static/utils/StringUtils.js")
const app = getApp()
Page({

  /**
   * 页面的初始数据
   */
  data: {
    canIUse: wx.canIUse('button.open-type.getUserInfo'),
    host_static: app.globalData.host_static,
    userDetail: null,
    avatar: '',
    nickname: '',
    mobile: '',
    isShowWechat: false,
    wechats: [],
  },
  onLoad: function(options) {
    
  },
  /**
   * 生命周期函数--监听页面显示
   */
  onShow: function() {
    this.getuserdetail()
    this.getuserwechatlist()
    const that = this
    if (app.globalData.imagecropper_result) {
      network.uploadFile({
        category: "avatar",
        filePath: app.globalData.imagecropper_result,
        success(data) {
          network.updateuserdetail({
            params: {
              "avatar": data,
            },
            success(res) {
              that.getuserdetail()
            }
          })
        },
        complete(res) {
          app.globalData.imagecropper_result = ''
        },
      })
    }
  },
  getuserwechatlist:function(){
    const that = this
    network.getuserwechatlist({
      success(data) {
        that.setData({
          isShowWechat: app.isLoginByMobile(),
          wechats: data,
        })
      },
    })
  },
  getuserdetail: function() {
    const that = this

    network.getuserdetail({
      success(data) {
        let avatar = data.avatar
        if (avatar.indexOf("http") < 0) {
          avatar = that.data.host_static + avatar
        }
        that.setData({
          userDetail: data,
          avatar: avatar,
          nickname: data.nickname,
          mobile: StringUtils.securityMobile(data.mobile)
        })
      }
    })
  },
  chooseImage: function() {
    wx.chooseImage({
      count: 1, // 默认9
      sizeType: ['original'], // 可以指定是原图还是压缩图，默认二者都有
      sourceType: ['album', 'camera'], // 可以指定来源是相册还是相机，默认二者都有
      success(res) {
        wx.navigateTo({
          url: '../imagecropper/imagecropper?width=' + 400 + '&height=' + 400 + "&crop_origin=" + res.tempFilePaths[0],
        })
      }
    })
  },
  nickname: function(e) {
    const nickname = this.data.nickname
    wx.navigateTo({
      url: '../userdetail_nickname/userdetail_nickname?nickname=' + nickname,
    })
  },
  getUserInfo: function (e) {
    const wechatInfo = e.detail

    if (!wechatInfo || wechatInfo.length) {
      return
    }

    const that = this
    wx.login({
      success(login) {
        wx.getUserInfo({
          success(userinfo) {
            network.decryptminiprogram({
              params: {
                "js_code": login.code,
                "encrypted_data": userinfo.encryptedData,
                "iv": userinfo.iv
              },
              success(decryptminiprogram) {
                network.wechatbind({
                  params: {
                    "open_id": decryptminiprogram.open_id,
                    "union_id": decryptminiprogram.union_id
                  },
                  success(res) {
                    that.getuserwechatlist()
                  },
                  fail(res) {
                    if (res.code == -7) {
                      that.getuserwechatlist()
                    }
                  }
                })
              }
            })
          }
        })
      }
    })
  },
})