const network = require("../../static/utils/network.js")

const app = getApp()
Page({
  /**
   * 页面的初始数据
   */
  data: {
    host_static: app.globalData.host_static,
    userBusinessCard: {},
    template_id: 0,
    work_id: 0,
    content: {
      width: 0,
      height: 0,
      left: 0,
      top: 0,
      scale: 0.2
    },
    schema: {},
    image: {
      type: '',
      index: 0,
      schema: {}
    },
    text: {
      type: '',
      index: 0,
      schema: {}
    },
    templateinfolist: [],
    picturelist: [],
    textlist: [],
    isShowInputDialog: false
  },

  /**
   * 生命周期函数--监听页面加载
   */
  onLoad: function(options) {
    console.log(options)

    this.setData({
      work_id: options.work_id || 0,
      template_id: options.template_id || 0,
      schema: JSON.parse(options.schema || "{}")
    })

    this.initSchema()
  },

  /**
   * 生命周期函数--监听页面初次渲染完成
   */
  onReady: function() {
    const query = wx.createSelectorQuery()
    const that = this

    let content = this.data.content
    query.select('.content').boundingClientRect(function(rect) {
      content.width = rect.width
      content.height = rect.height
      that.setData({
        content: content
      })
      that.initScale()
    }).exec()
  },
  /**
   * 生命周期函数--监听页面显示
   */
  onShow: function() {
    const that = this
    if (app.isLogined()) {
      //获取名片信息
      network.getuserbusinesscard({
        success(userBusinessCard) {
          that.setData({
            userBusinessCard: userBusinessCard
          })

          that.initSchema()
        }
      })
    } else {
      that.setData({
        userBusinessCard: {}
      })
      that.initSchema()
    }
  },
  onShareAppMessage: function() {

  },
  initSchema() {
    const userBusinessCard = this.data.userBusinessCard
    if (!userBusinessCard) {
      return
    }

    let schema = this.data.schema
    if (!schema || !schema.card) {
      return
    }

    const display = userBusinessCard.display || 0
    let logo = userBusinessCard.logo || ''
    let name = userBusinessCard.name || ''
    let tel = userBusinessCard.tel || ''
    let address = userBusinessCard.address || ''

    if (display == 1) {
      if (tel) {
        tel = "电话：" + tel
      }
      if (address) {
        address = "地址：" + address
      }
      if (logo) {
        name = ''
      }
    } else {
      logo = ''
      name = ''
      tel = ''
      address = ''
    }

    if (schema.card.logo) {
      schema.card.logo.src = logo
    }
    if (schema.card.name) {
      schema.card.name.text = name
    }
    if (schema.card.tel) {
      schema.card.tel.text = tel
    }
    if (schema.card.address) {
      schema.card.address.text = address
    }

    this.setData({
      schema: schema
    })

  },
  replaceTemplate: function(e) {
    this.loadTemplate(function() {

    })
  },
  replacePicture: function(e) {
    console.log(e)

    let image = this.data.image
    let type = e.currentTarget.dataset.type || image.type
    let index = e.currentTarget.dataset.index != undefined ? e.currentTarget.dataset.index : image.index
    if (!type) {
      let schema = this.data.schema
      if (schema.background && schema.background.editable) {
        type = 'background'
      } else if (schema.title && schema.title.background && schema.title.background.editable) {
        type = 'title_background'
      } else if (schema.desc && schema.desc.background && schema.desc.background.editable) {
        type = 'desc_background'
      } else {
        for (let i in schema.childs) {
          let child = schema.childs[i]
          if (child.class == 'Image' && child.editable) {
            type = "childs"
            index = i
            break
          } else if (child.class == 'Text' && child.background && child.background.editable) {
            type = "childs_background"
            index = i
            break
          }
        }
      }
    }

    let current = this.currentImage(type, index)

    this.setData({
      image: {
        type: type,
        index: index,
        schema: current
      }
    })

    this.loadPicture(function() {

    })
  },
  replaceText: function(e) {
    console.log(e)
    //显示底部文字
    let textlist = []
    let schema = this.data.schema
    if (schema.title && schema.title.editable) {
      textlist.push({
        type: 'title',
        index: 0,
        schema: schema.title
      })
    }
    if (schema.desc && schema.desc.editable) {
      textlist.push({
        type: 'desc',
        index: 0,
        schema: schema.desc
      })
    }
    for (let i in schema.childs) {
      let child = schema.childs[i]
      if (child.class == 'Text' && child.editable) {
        textlist.push({
          type: 'childs',
          index: i,
          schema: child
        })
      }
    }

    this.setData({
      textlist: textlist
    })

    console.log(textlist)
  },
  hideTemplateinfolist: function(e) {
    this.setData({
      templateinfolist: {}
    })
  },
  selectTemplate: function(e) {
    const template_id = e.currentTarget.dataset.template_id
    const schema = e.currentTarget.dataset.schema
    this.setData({
      template_id: template_id,
      schema: JSON.parse(schema),
      image: {
        type: '',
        index: '',
        schema: {}
      },
      text: {
        type: '',
        index: '',
        schema: {}
      }
    })

    this.initScale()

    this.initSchema()
  },
  hidePicturelist: function(e) {
    this.setData({
      picturelist: {}
    })
  },
  selectPicture: function(e) {
    const src = e.currentTarget.dataset.src

    this.setSrc(src)
  },
  hideTextInput: function(e) {
    this.setData({
      isShowInputDialog: false
    })
  },
  selectText: function(e) {
    let type = e.currentTarget.dataset.type
    let index = e.currentTarget.dataset.index || 0

    //显示输入框
    let current = this.currentText(type, index)

    this.setData({
      text: {
        type: type,
        index: index,
        schema: current
      },
      isShowInputDialog: true
    })
  },
  onTextInput: function(e) {
    console.log(e)
    const value = e.detail.value
    this.setText(value)
  },
  hideTextlist: function(e) {
    this.setData({
      textlist: {}
    })
  },
  initScale() {
    if (this.data.schema.size.width && this.data.content.width) {
      const scaleWidth = this.data.content.width / this.data.schema.size.width;
      const scaleHeight = this.data.content.height / this.data.schema.size.height;

      const scale = scaleWidth > scaleHeight ? scaleHeight : scaleWidth;

      const left = (this.data.content.width - this.data.schema.size.width * scale) / 2
      const top = (this.data.content.height - this.data.schema.size.height * scale) / 2

      let content = this.data.content
      content.scale = scale
      content.left = left
      content.top = top

      this.setData({
        content: content
      })
    }
  },
  currentImage(type, index) {
    let current

    let schema = this.data.schema
    if (type == 'background') {
      current = schema.background
    } else if (type == 'childs') {
      current = schema.childs[index]
    } else if (type == 'childs_background') {
      current = schema.childs[index].background
    } else if (type == 'desc_background') {
      current = schema.desc.background
    } else if (type == 'title_background') {
      current = schema.title.background
    }

    return current
  },
  setSrc(src) {
    const type = this.data.image.type
    const index = this.data.image.index

    let schema = this.data.schema
    if (type == 'background') {
      schema.background.src = src
    } else if (type == 'childs') {
      schema.childs[index].src = src
    } else if (type == 'childs_background') {
      schema.childs[index].background.src = src
    } else if (type == 'desc_background') {
      schema.desc.background.src = src
    } else if (type == 'title_background') {
      schema.title.background.src = src
    }

    let image = this.data.image
    image.schema.src = src
    this.setData({
      schema: schema,
      image: image
    })
  },
  currentText: function(type, index) {
    let current

    let schema = this.data.schema
    if (type == 'title') {
      current = schema.title
    } else if (type == 'desc') {
      current = schema.desc
    } else if (type == 'childs') {
      current = schema.childs[index]
    }

    return current
  },
  setText: function(str) {
    const type = this.data.text.type
    const index = this.data.text.index

    let schema = this.data.schema
    if (type == 'title') {
      schema.title.text = str
    } else if (type == 'desc') {
      schema.desc.text = str
    } else if (type == 'childs') {
      schema.childs[index].text = str
    }

    let text = this.data.text
    text.schema.text = str

    let textlist = this.data.textlist
    for (let i in textlist) {
      if (type == textlist[i].type && index == textlist[i].index) {
        textlist[i].schema.text = str
      }
    }

    this.setData({
      schema: schema,
      text: text,
      textlist: textlist
    })
  },
  loadTemplate(success) {
    var that = this
    network.gettemplateinfolist({
      params: {
        "version": 1,
        "type": 1,
        "offset": 0,
        "count": 50
      },
      success(data) {
        console.log(data)

        that.setData({
          templateinfolist: data
        })

        success()
      }
    })
  },
  loadPicture(success) {
    var that = this

    console.log(that.data.image)

    network.gettemplatepicturelist({
      params: {
        "template_id": that.data.template_id,
        "width": that.data.image.schema.size.width,
        "height": that.data.image.schema.size.height,
        "version": 1,
        "type": 1,
        "offset": 0,
        "count": 50
      },
      success(data) {
        console.log(data)

        that.setData({
          picturelist: data
        })

        success()
      }
    })
  },
  done: function(e) {
    const template_id = this.data.template_id
    const schema = JSON.stringify(this.data.schema)
    console.log(schema)

    network.savework({
      params: {
        "template_id": template_id,
        "schema": schema
      },
      success(e) {
        console.log(e)
        wx.navigateTo({
          url: '../phosterresult/phosterresult?work=' + JSON.stringify(e),
        })
      }
    })
  }
})