const network = require("../../static/utils/network.js")
const app = getApp()

Page({
  data: {
    decryptminiprogram: {},
    mobile: '',
    captcha: '',
    countDown: 0,
    timer: null, //定时器名字
    countDownNum: '60', //倒计时初始值
  },
  onLoad: function(options) {
    this.setData({
      decryptminiprogram: JSON.parse(options.decryptminiprogram || "{}")
    })
  },
  inputMobile: function (e) {
    this.setData({
      mobile: e.detail.value
    })
  },
  inputCaptcha: function (e) {
    this.setData({
      captcha: e.detail.value
    })
  },
  sendCaptcha: function (e) {
    const mobile = this.data.mobile
    const that = this
    if (!mobile) {
      wx.showToast({
        title: '手机号不能为空',
        icon: 'none'
      })
      return
    }
    network.sendcaptcha({
      params: {
        "mobile": mobile,
        "template": "login"
      },
      success(res) {
        that.countDown()
      }
    })
  },
  countDown() {
    let that = this
    let countDown = that.data.countDownNum //获取倒计时初始值
    //如果将定时器设置在外面，那么用户就看不到countDown的数值动态变化，所以要把定时器存进data里面
    that.setData({
      timer: setInterval(function () { //这里把setInterval赋值给变量名为timer的变量
        //每隔一秒countDown就减一，实现同步
        countDown--;
        //然后把countDown存进data，好让用户知道时间在倒计着
        that.setData({
          countDown: countDown
        })
        //在倒计时还未到0时，这中间可以做其他的事情，按项目需求来
        if (countDown == 0) {
          //这里特别要注意，计时器是始终一直在走的，如果你的时间为0，那么就要关掉定时器！不然相当耗性能
          //因为timer是存在data里面的，所以在关掉时，也要在data里取出后再关闭
          clearInterval(that.data.timer);
          //关闭定时器之后，可作其他处理codes go here
        }
      }, 1000)
    })
  },
  login: function (e) {
    const mobile = this.data.mobile
    const captcha = this.data.captcha
    const decryptminiprogram = this.data.decryptminiprogram
    const that = this

    network.userlogin({
      params: {
        "mobile": mobile,
        "code": captcha,
      },
      success(res) {
        app.setMobileUser(res)
        network.wechatbind({
          params:{
            "open_id": decryptminiprogram.open_id,
            "union_id": decryptminiprogram.union_id
          },
          success(res){
            that.userdetail()
          },
          fail(res){
            if(res.code == -7){
              that.userdetail()
            }
          }
        })
      }
    })
  },
  userdetail:function(){
    const decryptminiprogram = this.data.decryptminiprogram
    network.getuserdetail({
      success(res){
        let nickname = ''
        let avatar = ''
        let gender = ''
        let country = ''
        let province = ''
        let city = ''
        if(!res){
          if (!res.nickname){
            nickname = decryptminiprogram.nick_name
          }
          if (!res.avatar){
            avatar = decryptminiprogram.avatar_url
          }
          if(decryptminiprogram.gender === "2" || decryptminiprogram.gender === "0"){
            gender = "0"
          }else if(decryptminiprogram.gender === "1"){
            gender = "1"
          }
          if (!res.country) {
            country = decryptminiprogram.country
          }
          if (!res.province) {
            province = decryptminiprogram.province
          }
          if (!res.city) {
            city = decryptminiprogram.city
          }
        }else{
          nickname = decryptminiprogram.nick_name
          avatar = decryptminiprogram.avatar_url
          if (decryptminiprogram.gender === "2" || decryptminiprogram.gender === "0") {
            gender = "0"
          } else if (decryptminiprogram.gender === "1") {
            gender = "1"
          }
          country = decryptminiprogram.country
          province = decryptminiprogram.province
          city = decryptminiprogram.city
        }

        network.updateuserdetail({
          params: {
            "nickname":nickname,
            "avatar":avatar,
            "gender":gender,
            "country":country,
            "province":province,
            "city":city
          },
          success(res) {
            wx.navigateBack({
              delta: 2
            })
          }
        })
      }
    })
  }
})