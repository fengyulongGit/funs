var StringUtils = {
  securityMobile: function(text) {
    if (!text) {
      return ''
    }
    console.log(text)

    let res = ''
    for (let i in text) {
      if (i >= 3 && text.length - i > 4) {
        res += '*'
      } else {
        res += text.charAt(i)
      }
    }
    console.log(res)
    return res
  }
}

module.exports = StringUtils