function initSchemaView() {
    const default_dir = $("#default_dir").val()
    let htmlstr = ''

    htmlstr += '<div class="scale_origin_left_top" style="width: ' + template.size.width + 'px;height: ' + template.size.height + 'px;transform: scale(0.2,0.2);">'

    //背景
    const has_background = template.background && Object.keys(template.background).length > 0
    if (has_background) {
        htmlstr += '<img class="schema_item_absolute" src="file://' + default_dir + template.background.src + '" style="' + schema_filter.image(template.background) + '"/>'
        const has_background_border = template.background.border && Object.keys(template.background.border).length > 0
        if (has_background_border) {
            htmlstr += '<div class="schema_item_absolute" style="' + schema_filter.border(template.background.border) + '"></div>'
        }
    }
    <!-- childs -->
    const has_childs = template.childs && template.childs.length > 0
    if (has_childs) {
        for (let i in template.childs) {
            const child = template.childs[i]
            if (child.class == "Image") {
                htmlstr += '<img class="schema_item_absolute" src="file://' + default_dir + child.src + '" style="' + schema_filter.image(child) + '"/>'
                const has_childs_child_border = child.border && Object.keys(child.border).length > 0
                if (has_childs_child_border) {
                    htmlstr += '<div class="schema_item_absolute" style="' + schema_filter.border(child.border) + '"></div>'
                }
            } else if (child.class == 'Text') {
                const has_childs_child_background = child.background && Object.keys(child.background).length > 0
                if (has_childs_child_background) {
                    htmlstr += '<img class="schema_item_absolute" src="file://' + default_dir + child.background.src + '" style="' + schema_filter.image(child.background) + '"/>'
                    let has_childs_child_background_border = child.background.border && Object.keys(child.background.border).length > 0
                    if (has_childs_child_background_border) {
                        htmlstr += '<div class="schema_item_absolute" style="' + schema_filter.border(child.background.border) + '"></div>'
                    }
                }
                htmlstr += '<div class="schema_item_absolute" style="' + schema_filter.text(child) + '">' + child.text + '</div>'
            }
        }
    }

    const has_desc = template.desc && Object.keys(template.desc).length > 0
    if (has_desc) {
        const has_desc_background = template.desc.background && Object.keys(template.desc.background).length > 0
        if (has_desc_background) {
            htmlstr += '<img class="schema_item_absolute" src="file://' + default_dir + template.desc.background.src + '" style="' + schema_filter.image(template.desc.background) + '"/>'

            let has_desc_background_border = template.desc.background.border && Object.keys(template.desc.background.border).length > 0
            if (has_desc_background_border) {
                htmlstr += '<div class="schema_item_absolute" style="' + schema_filter.border(template.desc.background.border) + '"></div>'
            }
        }
        htmlstr += '<div class="schema_item_absolute" style="' + schema_filter.text(template.desc) + '">' + template.desc.text + '</div>'
    }

    const has_title = template.title && Object.keys(template.title).length > 0
    if (has_title) {
        const has_title_background = template.title.background && Object.keys(template.title.background).length > 0
        if (has_title_background) {
            htmlstr += '<img class="schema_item_absolute" src="file://' + default_dir + template.title.background.src + '" style="' + schema_filter.image(template.title.background) + '"/>'

            let has_title_background_border = template.title.background.border && Object.keys(template.title.background.border).length > 0
            if (has_title_background_border) {
                htmlstr += '<div class="schema_item_absolute" style="' + schema_filter.border(template.title.background.border) + '"></div>'
            }
        }
        htmlstr += '<div class="schema_item_absolute" style="' + schema_filter.text(template.title) + '">' + template.title.text + '</div>'
    }

    htmlstr += '</div>'

    // console.log(htmlstr)

    $("#div_schema").width(template.size.width * 0.2).height(template.size.height * 0.2)
    $("#div_schema").html(htmlstr)
}

let schema_filter = {
    image: function (image) {
        return 'width:' + image.size.width + 'px; ' +
            'height:' + image.size.height + 'px; ' +
            'left:' + image.position.x + 'px; ' +
            'top:' + image.position.y + 'px;'
    },
    text: function (text) {
        return 'width:' + (text.size.width + 10) + 'px;' +
            'height:' + text.size.height + 'px;' +
            'line-height:' + text.size.height + 'px;' +
            'left:' + text.position.x + 'px;' +
            'top:' + text.position.y + 'px;' +
            'font-size:' + text.font.size + 'px;' +
            'display: flex;' +
            schema_filter.getAlign(text.alignment) +
            'font-family:' + text.font.family + ";" +
            'white-space: nowrap;' +
            'color:#' + color_filter.rgbToHex(text.color.rgb) + ';' +
            'opacity:' + text.color.alpha / 255 + ';' +
            'overflow: hidden;'
    },
    border: function (border) {
        return 'width:' + (border.size.width - border.weight * 2) + 'px;' +
            'height:' + (border.size.height - border.weight * 2) + 'px;' +
            'left:' + border.position.x + 'px;' +
            'top:' + border.position.y + 'px;' +
            'border:' + border.weight + 'px solid #' + color_filter.rgbToHex(border.color.rgb) + ';' +
            'border-radius:' + border.radius + 'px;' +
            'opacity:' + border.color.alpha / 255 + ';'
    },
    getAlign: function (alignment) {
        let type = ''
        let justify = ''
        if (alignment == 0 || alignment == 3 || alignment == 6) {
            type = "left"
            justify = 'flex-start'
        } else if (alignment == 1 || alignment == 4 || alignment == 7) {
            type = "center"
            justify = "center"
        } else if (alignment == 2 || alignment == 5 || alignment == 8) {
            type = "right"
            justify = 'flex-end'
        }

        return 'text-align:' + type + ';' +
            'justify-content: ' + justify + ';'
    }
}

let color_filter = {
    rgbToHex: function (rgb) {
        if (rgb == 0) {
            return "000000"
        }
        var color = rgb.toString(16).toUpperCase() + ''
        while (color.length < 6) {
            color = '0' + color
        }
        return color
    }
}