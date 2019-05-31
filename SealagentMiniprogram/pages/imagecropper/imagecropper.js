const app = getApp()
Page({

  /**
   * 页面的初始数据
   */
  data: {
    crop_origin: '',
    crop_width: 0, //宽度
    crop_height: 0, //高度
    export_scale: 1,
  },

  onLoad: function(options) {
    const systemInfo = app.globalData.systemInfo

    const {
      width,
      height,
      crop_origin
    } = options

    if (!width || !height || !crop_origin){
      wx.showToast({
        title: '参数错误',
        icon: 'none'
      })
      return
    }

    let windowWidth = systemInfo.windowWidth * 0.8
    let windowHeight = systemInfo.windowHeight * 0.8

    let scaleWidth = width / windowWidth;
    let scaleHeight = height / windowHeight;
    let crop_width, crop_height, export_scale
    if (scaleWidth > scaleHeight) {
      crop_width = windowWidth
      crop_height = windowWidth / width * height
      export_scale = scaleWidth
    } else {
      crop_width = windowHeight / height * width
      crop_height = windowHeight
      export_scale = scaleWidth
    }

    this.setData({
      crop_origin: crop_origin,
      crop_width: crop_width,
      crop_height: crop_height,
      export_scale: export_scale,
    })
  },
  cropperload(e) {
    console.log("cropper初始化完成");
    this.cropper = this.selectComponent("#image-cropper");
    // this.cropper.upload();//上传图片
  },
  loadimage(e) {
    console.log("图片加载完成", e.detail);
    wx.hideLoading();
    //重置图片角度、缩放、位置
    this.cropper.imgReset();
  },
  clickcut(e) {
    //点击裁剪框阅览图片
    wx.previewImage({
      current: e.detail.url, // 当前显示图片的http链接
      urls: [e.detail.url] // 需要预览的图片http链接列表
    })
  },
  cropcancel() {
    wx.navigateBack({
      delta: -1
    })
  },
  cropok() {
    this.cropper.getImg((obj) => {
      app.globalData.imagecropper_result = obj.url;
      wx.navigateBack({
        delta: -1
      })
    });
  },
})