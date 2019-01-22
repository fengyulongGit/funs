const template_str = '{"class": "Template","position": {"x": 0,"y": 0},"size": {"width": 1701,"height": 2550},"editable": false,"moveable": false,"version": 1,"background": {"class": "Image","position": {"x": 0,"y": 0},"size": {"width": 1701,"height": 2550},"editable": false,"moveable": false,"border": {"class": "Border","position": {"x": 0,"y": 0},"size": {"width": 1701,"height": 2550},"editable": false,"moveable": false,"style": 0,"weight": 0,"color": {"rgb": 0,"alpha": 255},"radius": 0},"src": ""},"title": {"class": "Text","position": {"x": 0,"y": 0},"size": {"width": 0,"height": 0},"editable": false,"moveable": false,"background": {"class": "Image","position": {"x": 0,"y": 0},"size": {"width": 0,"height": 0},"editable": false,"moveable": false,"border": {"class": "Border","position": {"x": 0,"y": 0},"size": {"width": 0,"height": 0},"editable": false,"moveable": false,"style": 0,"weight": 0,"color": {"rgb": 0,"alpha": 255},"radius": 0},"src": ""},"font": {"family": "","style": 0,"size": 0},"color": {"rgb": 0,"alpha": 255},"alignment": 0,"text": ""},"desc": {"class": "Text","position": {"x": 0,"y": 0},"size": {"width": 0,"height": 0},"editable": false,"moveable": false,"background": {"class": "Image","position": {"x": 0,"y": 0},"size": {"width": 0,"height": 0},"editable": false,"moveable": false,"border": {"class": "Border","position": {"x": 0,"y": 0},"size": {"width": 0,"height": 0},"editable": false,"moveable": false,"style": 0,"weight": 0,"color": {"rgb": 0,"alpha": 255},"radius": 0},"src": ""},"font": {"family": "","style": 0,"size": 0},"color": {"rgb": 0,"alpha": 255},"alignment": 0,"text": ""},"childs": [{"class": "Image","position": {"x": 0,"y": 0},"size": {"width": 1701,"height": 2550},"editable": false,"moveable": false,"border": {"class": "Border","position": {"x": 0,"y": 0},"size": {"width": 1701,"height": 2550},"editable": false,"moveable": false,"style": 0,"weight": 0,"color": {"rgb": 0,"alpha": 255},"radius": 0},"src": ""}, {"class": "Text","position": {"x": 0,"y": 0},"size": {"width": 0,"height": 0},"editable": false,"moveable": false,"background": {"class": "Image","position": {"x": 0,"y": 0},"size": {"width": 0,"height": 0},"editable": false,"moveable": false,"border": {"class": "Border","position": {"x": 0,"y": 0},"size": {"width": 0,"height": 0},"editable": false,"moveable": false,"style": 0,"weight": 0,"color": {"rgb": 0,"alpha": 255},"radius": 0},"src": ""},"font": {"family": "","style": 0,"size": 0},"color": {"rgb": 0,"alpha": 255},"alignment": 0,"text": ""}],"card": {"class": "BusinessCard","position": null,"size": null,"editable": false,"moveable": false,"background": {},"logo": {"class": "Image","position": {"x": 0,"y": 0},"size": {"width": 600,"height": 150},"editable": false,"moveable": false,"border": {},"src": ""},"name": {"class": "Text","position": {"x": 0,"y": 0},"size": {"width": 0,"height": 0},"editable": false,"moveable": false,"background": {},"font": {"family": "","style": 0,"size": 0},"color": {"rgb": 0,"alpha": 255},"alignment": 0,"text": ""},"tel": {"class": "Text","position": {"x": 0,"y": 0},"size": {"width": 0,"height": 0},"editable": false,"moveable": false,"background": {},"font": {"family": "","style": 0,"size": 0},"color": {"rgb": 0,"alpha": 255},"alignment": 0,"text": ""},"address": {"class": "Text","position": {"x": 0,"y": 0},"size": {"width": 0,"height": 0},"editable": false,"moveable": false,"background": {},"font": {"family": "","style": 0,"size": 0},"color": {"rgb": 0,"alpha": 255},"alignment": 0,"text": ""}}}'

const text_str = '{"class": "Text","position": {"x": 0,"y": 0},"size": {"width": 0,"height": 0},"editable": false,"moveable": false,"background": {"class": "Image","position": {"x": 0,"y": 0},"size": {"width": 0,"height": 0},"editable": false,"moveable": false,"border": {"class": "Border","position": {"x": 0,"y": 0},"size": {"width": 0,"height": 0},"editable": false,"moveable": false,"style": 0,"weight": 0,"color": {"rgb": 0,"alpha": 255},"radius": 0},"src": ""},"font": {"family": "","style": 0,"size": 0},"color": {"rgb": 0,"alpha": 255},"alignment": 0,"text": ""}'

const image_str = '{"class": "Image","position": {"x": 0,"y": 0},"size": {"width": 0,"height": 0},"editable": false,"moveable": false,"border": {"class": "Border","position": {"x": 0,"y": 0},"size": {"width": 0,"height": 0},"editable": false,"moveable": false,"style": 0,"weight": 0,"color": {"rgb": 0,"alpha": 255},"radius": 0},"src": ""}'

const border_str = '{"class": "Border","position": {"x": 0,"y": 0},"size": {"width": 0,"height": 0},"editable": false,"moveable": false,"style": 0,"weight": 0,"color": {"rgb": 0,"alpha": 255},"radius": 0}'

const childs_str = '[' + image_str + ',' + text_str + ']'

const logo_str = image_str.replace('"width": 0,"height": 0', '"width": 600,"height": 150')
const card_str = '{"class": "BusinessCard","position": null,"size": null,"editable": false,"moveable": false,"background": ' + image_str + ',"logo": ' + logo_str + ',"name": ' + text_str + ',"tel": ' + text_str + ',"address": ' + text_str + '}'

let template = JSON.parse(template_str)

function initTemplate() {

    let htmlstr = ''
    //Template
    htmlstr += '<tr><td class="td_width">template</td><td>'
    //position
    htmlstr += '<p>position:&emsp;X&emsp;-&emsp;' + genText('template.position.x', template.position.x) + '&emsp;Y&emsp;-&emsp;' + genText('template.position.y', template.position.y) + '</p>'
    //size
    htmlstr += '<p>size:&emsp;width&emsp;-&emsp;' + genText('template.size.width', template.size.width) + '&emsp;height&emsp;-&emsp;' + genText('template.size.height', template.size.height) + '</p>'
    //editable moveable
    htmlstr += '<p>editable:&emsp;' + genCheckBox('template.editable', template.editable) + '&emsp;moveable:&emsp;' + genCheckBox('template.moveable', template.moveable) + '</p>'
    //background
    htmlstr += '<p><table><tr><td class="td_width">background</td><td>'

    const has_background = template.background && Object.keys(template.background).length > 0
    if (has_background) {
        //position
        htmlstr += '<p>position:&emsp;X&emsp;-&emsp;' + genText('template.background.position.x', template.background.position.x) + '&emsp;Y&emsp;-&emsp;' + genText('template.background.position.y', template.background.position.y) + '</p>'
        //size
        htmlstr += '<p>size:&emsp;width&emsp;-&emsp;' + genText('template.background.size.width', template.background.size.width) + '&emsp;height&emsp;-&emsp;' + genText('template.background.size.height', template.background.size.height) + '</p>'
        //editable moveable
        htmlstr += '<p>editable:&emsp;' + genCheckBox('template.background.editable', template.background.editable) + '&emsp;moveable:&emsp;' + genCheckBox('template.background.moveable', template.background.moveable) + '</p>'

        //background.border
        htmlstr += '<p><table><tr><td class="td_width">border</td><td>'

        const has_background_border = template.background.border && Object.keys(template.background.border).length > 0
        if (has_background_border) {
            //position
            htmlstr += '<p>position:&emsp;X&emsp;-&emsp;' + genText('template.background.border.position.x', template.background.border.position.x) + '&emsp;Y&emsp;-&emsp;' + genText('template.background.border.position.y', template.background.border.position.y) + '</p>'
            //size
            htmlstr += '<p>size:&emsp;width&emsp;-&emsp;' + genText('template.background.border.size.width', template.background.border.size.width) + '&emsp;height&emsp;-&emsp;' + genText('template.background.border.size.height', template.background.border.size.height) + '</p>'
            //editable moveable
            htmlstr += '<p>editable:&emsp;' + genCheckBox('template.background.border.editable', template.background.border.editable) + '&emsp;moveable:&emsp;' + genCheckBox('template.background.border.moveable', template.background.border.moveable) + '</p>'
            //style
            htmlstr += '<p>style:&emsp;' + genText('template.background.border.style', template.background.border.style) + '</p>'
            //weight
            htmlstr += '<p>weight:&emsp;' + genText('template.background.border.weight', template.background.border.weight) + '</p>'
            //color
            htmlstr += '<p>color:&emsp;rgb&emsp;-&emsp;' + genText4Color('template.background.border.color.rgb', template.background.border.color.rgb) + '&emsp;alpha&emsp;-&emsp;' + genText4Color('template.background.border.color.alpha', template.background.border.color.alpha) + '</p>'
            //radius
            htmlstr += '<p>radius:&emsp;' + genText('template.background.border.radius', template.background.border.radius) + '</p>'
        }

        htmlstr += '</td><td class="td_width">' + genButton('template.background.border', has_background_border) + '</td></tr></table></p>'

        //src
        htmlstr += '<p>src:&emsp;' + genText('template.background.src', template.background.src) + '</p>'
    }

    htmlstr += '</td><td class="td_width">' + genButton('template.background', has_background) + '</td></tr></table></p>'
    //操作按钮
    htmlstr += '</td><td class="td_width"></td></tr>'

    //Title
    htmlstr += '<tr><td class="td_width">title</td><td>'

    const has_title = template.title && Object.keys(template.title).length > 0
    if (has_title) {
        //position
        htmlstr += '<p>position:&emsp;X&emsp;-&emsp;' + genText('template.title.position.x', template.title.position.x) + '&emsp;Y&emsp;-&emsp;' + genText('template.title.position.y', template.title.position.y) + '</p>'
        //size
        htmlstr += '<p>size:&emsp;width&emsp;-&emsp;' + genText('template.title.size.width', template.title.size.width) + '&emsp;height&emsp;-&emsp;' + genText('template.title.size.height', template.title.size.height) + '</p>'
        //editable moveable
        htmlstr += '<p>editable:&emsp;' + genCheckBox('template.title.editable', template.title.editable) + '&emsp;moveable:&emsp;' + genCheckBox('template.title.moveable', template.title.moveable) + '</p>'
        //background
        htmlstr += '<p><table><tr><td class="td_width">background</td><td>'

        const has_title_background = template.title.background && Object.keys(template.title.background).length > 0
        if (has_title_background) {
            //position
            htmlstr += '<p>position:&emsp;X&emsp;-&emsp;' + genText('template.title.background.position.x', template.title.background.position.x) + '&emsp;Y&emsp;-&emsp;' + genText('template.title.background.position.y', template.title.background.position.y) + '</p>'
            //size
            htmlstr += '<p>size:&emsp;width&emsp;-&emsp;' + genText('template.title.background.size.width', template.title.background.size.width) + '&emsp;height&emsp;-&emsp;' + genText('template.title.background.size.height', template.title.background.size.height) + '</p>'
            //editable moveable
            htmlstr += '<p>editable:&emsp;' + genCheckBox('template.title.background.editable', template.title.background.editable) + '&emsp;moveable:&emsp;' + genCheckBox('template.title.background.moveable', template.title.background.moveable) + '</p>'

            //background.border
            htmlstr += '<p><table><tr><td class="td_width">border</td><td>'

            let has_title_background_border = template.title.background.border && Object.keys(template.title.background.border).length > 0
            if (has_title_background_border) {
                //position
                htmlstr += '<p>position:&emsp;X&emsp;-&emsp;' + genText('template.title.background.border.position.x', template.title.background.border.position.x) + '&emsp;Y&emsp;-&emsp;' + genText('template.title.background.border.position.y', template.title.background.border.position.y) + '</p>'
                //size
                htmlstr += '<p>size:&emsp;width&emsp;-&emsp;' + genText('template.title.background.border.size.width', template.title.background.border.size.width) + '&emsp;height&emsp;-&emsp;' + genText('template.title.background.border.size.height', template.title.background.border.size.height) + '</p>'
                //editable moveable
                htmlstr += '<p>editable:&emsp;' + genCheckBox('template.title.background.border.editable', template.title.background.border.editable) + '&emsp;moveable:&emsp;' + genCheckBox('template.title.background.border.moveable', template.title.background.border.moveable) + '</p>'
                //style
                htmlstr += '<p>style:&emsp;' + genText('template.title.background.border.style', template.title.background.border.style) + '</p>'
                //weight
                htmlstr += '<p>weight:&emsp;' + genText('template.title.background.border.weight', template.title.background.border.weight) + '</p>'
                //color
                htmlstr += '<p>color:&emsp;rgb&emsp;-&emsp;' + genText4Color('template.title.background.border.color.rgb', template.title.background.border.color.rgb) + '&emsp;alpha&emsp;-&emsp;' + genText4Color('template.title.background.border.color.alpha', template.title.background.border.color.alpha) + '</p>'
                //radius
                htmlstr += '<p>radius:&emsp;' + genText('template.title.background.border.radius', template.title.background.border.radius) + '</p>'
            }

            htmlstr += '</td><td class="td_width">' + genButton('template.title.background.border', has_title_background_border) + '</td></tr></table></p>'
            //src
            htmlstr += '<p>src:&emsp;' + genText('template.title.background.src', template.title.background.src) + '</p>'
        }

        htmlstr += '</td><td class="td_width">' + genButton('template.title.background', has_title_background) + '</td></tr></table></p>'

        //font
        htmlstr += '<p>font:&emsp;family&emsp;-&emsp;' + genFamily('template.title.font.family', template.title.font.family) + '&emsp;style&emsp;-&emsp;' + genText('template.title.font.style', template.title.font.style) + '&emsp;size&emsp;-&emsp;' + genText('template.title.font.size', template.title.font.size) + '</p>'
        //color
        htmlstr += '<p>color:&emsp;rgb&emsp;-&emsp;' + genText4Color('template.title.color.rgb', template.title.color.rgb) + '&emsp;alpha&emsp;-&emsp;' + genText4Color('template.title.color.alpha', template.title.color.alpha) + '</p>'

        //alignment
        htmlstr += '<p>alignment:&emsp;' + genText('template.title.alignment', template.title.alignment) + '</p>'
        //text
        htmlstr += '<p>text:&emsp;' + genText('template.title.text', template.title.text) + '</p>'
    }
    //操作按钮
    htmlstr += '</td><td class="td_width">' + genButton('template.title', has_title) + '</td></tr>'

    //Desc
    htmlstr += '<tr><td class="td_width">desc</td><td>'

    const has_desc = template.desc && Object.keys(template.desc).length > 0
    if (has_desc) {
        //position
        htmlstr += '<p>position:&emsp;X&emsp;-&emsp;' + genText('template.desc.position.x', template.desc.position.x) + '&emsp;Y&emsp;-&emsp;' + genText('template.desc.position.y', template.desc.position.y) + '</p>'
        //size
        htmlstr += '<p>size:&emsp;width&emsp;-&emsp;' + genText('template.desc.size.width', template.desc.size.width) + '&emsp;height&emsp;-&emsp;' + genText('template.desc.size.height', template.desc.size.height) + '</p>'
        //editable moveable
        htmlstr += '<p>editable:&emsp;' + genCheckBox('template.desc.editable', template.desc.editable) + '&emsp;moveable:&emsp;' + genCheckBox('template.desc.moveable', template.desc.moveable) + '</p>'
        //background
        htmlstr += '<p><table><tr><td class="td_width">background</td><td>'

        const has_desc_background = template.desc.background && Object.keys(template.desc.background).length > 0
        if (has_desc_background) {
            //position
            htmlstr += '<p>position:&emsp;X&emsp;-&emsp;' + genText('template.desc.background.position.x', template.desc.background.position.x) + '&emsp;Y&emsp;-&emsp;' + genText('template.desc.background.position.y', template.desc.background.position.y) + '</p>'
            //size
            htmlstr += '<p>size:&emsp;width&emsp;-&emsp;' + genText('template.desc.background.size.width', template.desc.background.size.width) + '&emsp;height&emsp;-&emsp;' + genText('template.desc.background.size.height', template.desc.background.size.height) + '</p>'
            //editable moveable
            htmlstr += '<p>editable:&emsp;' + genCheckBox('template.desc.background.editable', template.desc.background.editable) + '&emsp;moveable:&emsp;' + genCheckBox('template.desc.background.moveable', template.desc.background.moveable) + '</p>'

            //background.border
            htmlstr += '<p><table><tr><td class="td_width">border</td><td>'

            let has_desc_background_border = template.desc.background.border && Object.keys(template.desc.background.border).length > 0
            if (has_desc_background_border) {
                //position
                htmlstr += '<p>position:&emsp;X&emsp;-&emsp;' + genText('template.desc.background.border.position.x', template.desc.background.border.position.x) + '&emsp;Y&emsp;-&emsp;' + genText('template.desc.background.border.position.y', template.desc.background.border.position.y) + '</p>'
                //size
                htmlstr += '<p>size:&emsp;width&emsp;-&emsp;' + genText('template.desc.background.border.size.width', template.desc.background.border.size.width) + '&emsp;height&emsp;-&emsp;' + genText('template.desc.background.border.size.height', template.desc.background.border.size.height) + '</p>'
                //editable moveable
                htmlstr += '<p>editable:&emsp;' + genCheckBox('template.desc.background.border.editable', template.desc.background.border.editable) + '&emsp;moveable:&emsp;' + genCheckBox('template.desc.background.border.moveable', template.desc.background.border.moveable) + '</p>'
                //style
                htmlstr += '<p>style:&emsp;' + genText('template.desc.background.border.style', template.desc.background.border.style) + '</p>'
                //weight
                htmlstr += '<p>weight:&emsp;' + genText('template.desc.background.border.weight', template.desc.background.border.weight) + '</p>'
                //color
                htmlstr += '<p>color:&emsp;rgb&emsp;-&emsp;' + genText4Color('template.desc.background.border.color.rgb', template.desc.background.border.color.rgb) + '&emsp;alpha&emsp;-&emsp;' + genText4Color('template.desc.background.border.color.alpha', template.desc.background.border.color.alpha) + '</p>'
                //radius
                htmlstr += '<p>radius:&emsp;' + genText('template.desc.background.border.radius', template.desc.background.border.radius) + '</p>'
            }

            htmlstr += '</td><td class="td_width">' + genButton('template.desc.background.border', has_desc_background_border) + '</td></tr></table></p>'

            //src
            htmlstr += '<p>src:&emsp;' + genText('template.desc.background.src', template.desc.background.src) + '</p>'
        }

        htmlstr += '</td><td class="td_width">' + genButton('template.desc.background', has_desc_background) + '</td></tr></table></p>'
        //font
        htmlstr += '<p>font:&emsp;family&emsp;-&emsp;' + genFamily('template.desc.font.family', template.desc.font.family) + '&emsp;style&emsp;-&emsp;' + genText('template.desc.font.style', template.desc.font.style) + '&emsp;size&emsp;-&emsp;' + genText('template.desc.font.size', template.desc.font.size) + '</p>'
        //color
        htmlstr += '<p>color:&emsp;rgb&emsp;-&emsp;' + genText4Color('template.desc.color.rgb', template.desc.color.rgb) + '&emsp;alpha&emsp;-&emsp;' + genText4Color('template.desc.color.alpha', template.desc.color.alpha) + '</p>'

        //alignment
        htmlstr += '<p>alignment:&emsp;' + genText('template.desc.alignment', template.desc.alignment) + '</p>'
        //text
        htmlstr += '<p>text:&emsp;' + genText('template.desc.text', template.desc.text) + '</p>'
    }
    //操作按钮
    htmlstr += '</td><td class="td_width">' + genButton('template.desc', has_desc) + '</td></tr>'

    //childs
    htmlstr += '<tr><td class="td_width">childs</td><td>'
    const has_childs = template.childs && template.childs.length > 0
    if (has_childs) {
        for (let i in template.childs) {
            const child = template.childs[i]
            htmlstr += '<p><table><tr><td class="td_width">' + i + '-' + child.class + '</td><td>'
            if (child.class == "Image") {
                //position
                htmlstr += '<p>position:&emsp;X&emsp;-&emsp;' + genText('template.childs.child.position.x', child.position.x, i) + '&emsp;Y&emsp;-&emsp;' + genText('template.childs.child.position.y', child.position.y, i) + '</p>'
                //size
                htmlstr += '<p>size:&emsp;width&emsp;-&emsp;' + genText('template.childs.child.size.width', child.size.width, i) + '&emsp;height&emsp;-&emsp;' + genText('template.childs.child.size.height', child.size.height, i) + '</p>'
                //editable moveable
                htmlstr += '<p>editable:&emsp;' + genCheckBox('template.childs.child.editable', child.editable, i) + '&emsp;moveable:&emsp;' + genCheckBox('template.childs.child.moveable', child.moveable, i) + '</p>'

                //background.border
                htmlstr += '<p><table><tr><td class="td_width">border</td><td>'

                const has_childs_child_border = child.border && Object.keys(child.border).length > 0
                if (has_childs_child_border) {
                    //position
                    htmlstr += '<p>position:&emsp;X&emsp;-&emsp;' + genText('template.childs.child.border.position.x', child.border.position.x, i) + '&emsp;Y&emsp;-&emsp;' + genText('template.childs.child.border.position.y', child.border.position.y, i) + '</p>'
                    //size
                    htmlstr += '<p>size:&emsp;width&emsp;-&emsp;' + genText('template.childs.child.border.size.width', child.border.size.width, i) + '&emsp;height&emsp;-&emsp;' + genText('template.childs.child.border.size.height', child.border.size.height, i) + '</p>'
                    //editable moveable
                    htmlstr += '<p>editable:&emsp;' + genCheckBox('template.childs.child.border.editable', child.border.editable, i) + '&emsp;moveable:&emsp;' + genCheckBox('template.childs.child.border.moveable', child.border.moveable, i) + '</p>'
                    //style
                    htmlstr += '<p>style:&emsp;' + genText('template.childs.child.border.style', child.border.style, i) + '</p>'
                    //weight
                    htmlstr += '<p>weight:&emsp;' + genText('template.childs.child.border.weight', child.border.weight, i) + '</p>'
                    //color
                    htmlstr += '<p>color:&emsp;rgb&emsp;-&emsp;' + genText4Color('template.childs.child.border.color.rgb', child.border.color.rgb, i) + '&emsp;alpha&emsp;-&emsp;' + genText4Color('template.childs.child.border.color.alpha', child.border.color.alpha, i) + '</p>'
                    //radius
                    htmlstr += '<p>radius:&emsp;' + genText('template.childs.child.border.radius', child.border.radius, i) + '</p>'
                }

                htmlstr += '</td><td class="td_width">' + genButton('template.childs.child.border', has_childs_child_border, i) + '</td></tr></table></p>'

                //src
                htmlstr += '<p>src:&emsp;' + genText('template.childs.child.src', child.src, i) + '</p>'
            } else if (child.class == 'Text') {
                //position
                htmlstr += '<p>position:&emsp;X&emsp;-&emsp;' + genText('template.childs.child.position.x', child.position.x, i) + '&emsp;Y&emsp;-&emsp;' + genText('template.childs.child.position.y', child.position.y, i) + '</p>'
                //size
                htmlstr += '<p>size:&emsp;width&emsp;-&emsp;' + genText('template.childs.child.size.width', child.size.width, i) + '&emsp;height&emsp;-&emsp;' + genText('template.childs.child.size.height', child.size.height, i) + '</p>'
                //editable moveable
                htmlstr += '<p>editable:&emsp;' + genCheckBox('template.childs.child.editable', child.editable, i) + '&emsp;moveable:&emsp;' + genCheckBox('template.childs.child.moveable', child.moveable, i) + '</p>'
                //background
                htmlstr += '<p><table><tr><td class="td_width">background</td><td>'

                const has_childs_child_background = child.background && Object.keys(child.background).length > 0
                if (has_childs_child_background) {
                    //position
                    htmlstr += '<p>position:&emsp;X&emsp;-&emsp;' + genText('template.childs.child.background.position.x', child.background.position.x, i) + '&emsp;Y&emsp;-&emsp;' + genText('template.childs.child.background.position.y', child.background.position.y, i) + '</p>'
                    //size
                    htmlstr += '<p>size:&emsp;width&emsp;-&emsp;' + genText('template.childs.child.background.size.width', child.background.size.width, i) + '&emsp;height&emsp;-&emsp;' + genText('template.childs.child.background.size.height', child.background.size.height, i) + '</p>'
                    //editable moveable
                    htmlstr += '<p>editable:&emsp;' + genCheckBox('template.childs.child.background.editable', child.background.editable, i) + '&emsp;moveable:&emsp;' + genCheckBox('template.childs.child.background.moveable', child.background.moveable, i) + '</p>'

                    //background.border
                    htmlstr += '<p><table><tr><td class="td_width">border</td><td>'

                    let has_childs_child_background_border = child.background.border && Object.keys(child.background.border).length > 0
                    if (has_childs_child_background_border) {
                        //position
                        htmlstr += '<p>position:&emsp;X&emsp;-&emsp;' + genText('template.childs.child.background.border.position.x', child.background.border.position.x, i) + '&emsp;Y&emsp;-&emsp;' + genText('template.childs.child.background.border.position.y', child.background.border.position.y, i) + '</p>'
                        //size
                        htmlstr += '<p>size:&emsp;width&emsp;-&emsp;' + genText('template.childs.child.background.border.size.width', child.background.border.size.width, i) + '&emsp;height&emsp;-&emsp;' + genText('template.childs.child.background.border.size.height', child.background.border.size.height, i) + '</p>'
                        //editable moveable
                        htmlstr += '<p>editable:&emsp;' + genCheckBox('template.childs.child.background.border.editable', child.background.border.editable, i) + '&emsp;moveable:&emsp;' + genCheckBox('template.childs.child.background.border.moveable', child.background.border.moveable, i) + '</p>'
                        //style
                        htmlstr += '<p>style:&emsp;' + genText('template.childs.child.background.border.style', child.background.border.style, i) + '</p>'
                        //weight
                        htmlstr += '<p>weight:&emsp;' + genText('template.childs.child.background.border.weight', child.background.border.weight, i) + '</p>'
                        //color
                        htmlstr += '<p>color:&emsp;rgb&emsp;-&emsp;' + genText4Color('template.childs.child.background.border.color.rgb', child.background.border.color.rgb, i) + '&emsp;alpha&emsp;-&emsp;' + genText4Color('template.childs.child.background.border.color.alpha', child.background.border.color.alpha, i) + '</p>'
                        //radius
                        htmlstr += '<p>radius:&emsp;' + genText('template.childs.child.background.border.radius', child.background.border.radius, i) + '</p>'
                    }

                    htmlstr += '</td><td class="td_width">' + genButton('template.childs.child.background.border', has_childs_child_background_border, i) + '</td></tr></table></p>'
                    //src
                    htmlstr += '<p>src:&emsp;' + genText('template.childs.child.background.src', child.background.src, i) + '</p>'
                }

                htmlstr += '</td><td class="td_width">' + genButton('template.childs.child.background', has_childs_child_background, i) + '</td></tr></table></p>'

                //font
                htmlstr += '<p>font:&emsp;family&emsp;-&emsp;' + genFamily('template.childs.child.font.family', child.font.family, i) + '&emsp;style&emsp;-&emsp;' + genText('template.childs.child.font.style', child.font.style, i) + '&emsp;size&emsp;-&emsp;' + genText('template.childs.child.font.size', child.font.size, i) + '</p>'
                //color
                htmlstr += '<p>color:&emsp;rgb&emsp;-&emsp;' + genText4Color('template.childs.child.color.rgb', child.color.rgb, i) + '&emsp;alpha&emsp;-&emsp;' + genText4Color('template.childs.child.color.alpha', child.color.alpha, i) + '</p>'

                //alignment
                htmlstr += '<p>alignment:&emsp;' + genText('template.childs.child.alignment', child.alignment, i) + '</p>'
                //text
                htmlstr += '<p>text:&emsp;' + genText('template.childs.child.text', child.text, i) + '</p>'
            }
            htmlstr += '</td><td class="td_width">' + genButton('template.childs.child', true, i) + '</td></tr></table></p>'
        }
    }
    //操作按钮
    htmlstr += '</td><td class="td_width"><p>' + genButton('template.childs', has_childs) + '</p><p>' + genButton4ChildsAdd('Image') + '</p><p>' + genButton4ChildsAdd('Text') + '</p></td></tr>'

    //card
    htmlstr += '<tr><td class="td_width">card</td><td>'
    const has_card = template.card && Object.keys(template.card).length > 0
    if (has_card) {
        //background
        htmlstr += '<p><table><tr><td class="td_width">background</td><td>'

        const has_background = template.card.background && Object.keys(template.card.background).length > 0
        if (has_background) {
            //position
            htmlstr += '<p>position:&emsp;X&emsp;-&emsp;' + genText('template.card.background.position.x', template.card.background.position.x) + '&emsp;Y&emsp;-&emsp;' + genText('template.card.background.position.y', template.card.background.position.y) + '</p>'
            //size
            htmlstr += '<p>size:&emsp;width&emsp;-&emsp;' + genText('template.card.background.size.width', template.card.background.size.width) + '&emsp;height&emsp;-&emsp;' + genText('template.card.background.size.height', template.card.background.size.height) + '</p>'
            //editable moveable
            htmlstr += '<p>editable:&emsp;' + genCheckBox('template.card.background.editable', template.card.background.editable) + '&emsp;moveable:&emsp;' + genCheckBox('template.card.background.moveable', template.card.background.moveable) + '</p>'

            //background.border
            htmlstr += '<p><table><tr><td class="td_width">border</td><td>'

            const has_background_border = template.card.background.border && Object.keys(template.card.background.border).length > 0
            if (has_background_border) {
                //position
                htmlstr += '<p>position:&emsp;X&emsp;-&emsp;' + genText('template.card.background.border.position.x', template.card.background.border.position.x) + '&emsp;Y&emsp;-&emsp;' + genText('template.card.background.border.position.y', template.card.background.border.position.y) + '</p>'
                //size
                htmlstr += '<p>size:&emsp;width&emsp;-&emsp;' + genText('template.card.background.border.size.width', template.card.background.border.size.width) + '&emsp;height&emsp;-&emsp;' + genText('template.card.background.border.size.height', template.card.background.border.size.height) + '</p>'
                //editable moveable
                htmlstr += '<p>editable:&emsp;' + genCheckBox('template.card.background.border.editable', template.card.background.border.editable) + '&emsp;moveable:&emsp;' + genCheckBox('template.card.background.border.moveable', template.card.background.border.moveable) + '</p>'
                //style
                htmlstr += '<p>style:&emsp;' + genText('template.card.background.border.style', template.card.background.border.style) + '</p>'
                //weight
                htmlstr += '<p>weight:&emsp;' + genText('template.card.background.border.weight', template.card.background.border.weight) + '</p>'
                //color
                htmlstr += '<p>color:&emsp;rgb&emsp;-&emsp;' + genText4Color('template.card.background.border.color.rgb', template.card.background.border.color.rgb) + '&emsp;alpha&emsp;-&emsp;' + genText4Color('template.card.background.border.color.alpha', template.card.background.border.color.alpha) + '</p>'
                //radius
                htmlstr += '<p>radius:&emsp;' + genText('template.card.background.border.radius', template.card.background.border.radius) + '</p>'
            }

            htmlstr += '</td><td class="td_width">' + genButton('template.card.background.border', has_background_border) + '</td></tr></table></p>'

            //src
            htmlstr += '<p>src:&emsp;' + genText('template.card.background.src', template.card.background.src) + '</p>'
        }

        htmlstr += '</td><td class="td_width">' + genButton('template.card.background', has_background) + '</td></tr></table></p>'
        //logo
        htmlstr += '<p><table><tr><td class="td_width">logo</td><td>'

        const has_logo = template.card.logo && Object.keys(template.card.logo).length > 0
        if (has_logo) {
            //position
            htmlstr += '<p>position:&emsp;X&emsp;-&emsp;' + genText('template.card.logo.position.x', template.card.logo.position.x) + '&emsp;Y&emsp;-&emsp;' + genText('template.card.logo.position.y', template.card.logo.position.y) + '</p>'
            //size
            htmlstr += '<p>size:&emsp;width&emsp;-&emsp;' + genText('template.card.logo.size.width', template.card.logo.size.width) + '&emsp;height&emsp;-&emsp;' + genText('template.card.logo.size.height', template.card.logo.size.height) + '</p>'
            //editable moveable
            htmlstr += '<p>editable:&emsp;' + genCheckBox('template.card.logo.editable', template.card.logo.editable) + '&emsp;moveable:&emsp;' + genCheckBox('template.card.logo.moveable', template.card.logo.moveable) + '</p>'

            //logo.border
            htmlstr += '<p><table><tr><td class="td_width">border</td><td>'

            const has_logo_border = template.card.logo.border && Object.keys(template.card.logo.border).length > 0
            if (has_logo_border) {
                //position
                htmlstr += '<p>position:&emsp;X&emsp;-&emsp;' + genText('template.card.logo.border.position.x', template.card.logo.border.position.x) + '&emsp;Y&emsp;-&emsp;' + genText('template.card.logo.border.position.y', template.card.logo.border.position.y) + '</p>'
                //size
                htmlstr += '<p>size:&emsp;width&emsp;-&emsp;' + genText('template.card.logo.border.size.width', template.card.logo.border.size.width) + '&emsp;height&emsp;-&emsp;' + genText('template.card.logo.border.size.height', template.card.logo.border.size.height) + '</p>'
                //editable moveable
                htmlstr += '<p>editable:&emsp;' + genCheckBox('template.card.logo.border.editable', template.card.logo.border.editable) + '&emsp;moveable:&emsp;' + genCheckBox('template.card.logo.border.moveable', template.card.logo.border.moveable) + '</p>'
                //style
                htmlstr += '<p>style:&emsp;' + genText('template.card.logo.border.style', template.card.logo.border.style) + '</p>'
                //weight
                htmlstr += '<p>weight:&emsp;' + genText('template.card.logo.border.weight', template.card.logo.border.weight) + '</p>'
                //color
                htmlstr += '<p>color:&emsp;rgb&emsp;-&emsp;' + genText4Color('template.card.logo.border.color.rgb', template.card.logo.border.color.rgb) + '&emsp;alpha&emsp;-&emsp;' + genText4Color('template.card.logo.border.color.alpha', template.card.logo.border.color.alpha) + '</p>'
                //radius
                htmlstr += '<p>radius:&emsp;' + genText('template.card.logo.border.radius', template.card.logo.border.radius) + '</p>'
            }

            htmlstr += '</td><td class="td_width">' + genButton('template.card.logo.border', has_logo_border) + '</td></tr></table></p>'

            //src
            htmlstr += '<p>src:&emsp;' + genText('template.card.logo.src', template.card.logo.src) + '</p>'
        }

        htmlstr += '</td><td class="td_width">' + genButton('template.card.logo', has_logo) + '</td></tr></table></p>'
        //name
        htmlstr += '<p><table><tr><td class="td_width">card-name</td><td>'

        const has_card_name = template.card.name && Object.keys(template.card.name).length > 0
        if (has_card_name) {
            //position
            htmlstr += '<p>position:&emsp;X&emsp;-&emsp;' + genText('template.card.name.position.x', template.card.name.position.x) + '&emsp;Y&emsp;-&emsp;' + genText('template.card.name.position.y', template.card.name.position.y) + '</p>'
            //size
            htmlstr += '<p>size:&emsp;width&emsp;-&emsp;' + genText('template.card.name.size.width', template.card.name.size.width) + '&emsp;height&emsp;-&emsp;' + genText('template.card.name.size.height', template.card.name.size.height) + '</p>'
            //editable moveable
            htmlstr += '<p>editable:&emsp;' + genCheckBox('template.card.name.editable', template.card.name.editable) + '&emsp;moveable:&emsp;' + genCheckBox('template.card.name.moveable', template.card.name.moveable) + '</p>'
            //background
            htmlstr += '<p><table><tr><td class="td_width">background</td><td>'

            const has_card_name_background = template.card.name.background && Object.keys(template.card.name.background).length > 0
            if (has_card_name_background) {
                //position
                htmlstr += '<p>position:&emsp;X&emsp;-&emsp;' + genText('template.card.name.background.position.x', template.card.name.background.position.x) + '&emsp;Y&emsp;-&emsp;' + genText('template.card.name.background.position.y', template.card.name.background.position.y) + '</p>'
                //size
                htmlstr += '<p>size:&emsp;width&emsp;-&emsp;' + genText('template.card.name.background.size.width', template.card.name.background.size.width) + '&emsp;height&emsp;-&emsp;' + genText('template.card.name.background.size.height', template.card.name.background.size.height) + '</p>'
                //editable moveable
                htmlstr += '<p>editable:&emsp;' + genCheckBox('template.card.name.background.editable', template.card.name.background.editable) + '&emsp;moveable:&emsp;' + genCheckBox('template.card.name.background.moveable', template.card.name.background.moveable) + '</p>'

                //background.border
                htmlstr += '<p><table><tr><td class="td_width">border</td><td>'

                let has_card_name_background_border = template.card.name.background.border && Object.keys(template.card.name.background.border).length > 0
                if (has_card_name_background_border) {
                    //position
                    htmlstr += '<p>position:&emsp;X&emsp;-&emsp;' + genText('template.card.name.background.border.position.x', template.card.name.background.border.position.x) + '&emsp;Y&emsp;-&emsp;' + genText('template.card.name.background.border.position.y', template.card.name.background.border.position.y) + '</p>'
                    //size
                    htmlstr += '<p>size:&emsp;width&emsp;-&emsp;' + genText('template.card.name.background.border.size.width', template.card.name.background.border.size.width) + '&emsp;height&emsp;-&emsp;' + genText('template.card.name.background.border.size.height', template.card.name.background.border.size.height) + '</p>'
                    //editable moveable
                    htmlstr += '<p>editable:&emsp;' + genCheckBox('template.card.name.background.border.editable', template.card.name.background.border.editable) + '&emsp;moveable:&emsp;' + genCheckBox('template.card.name.background.border.moveable', template.card.name.background.border.moveable) + '</p>'
                    //style
                    htmlstr += '<p>style:&emsp;' + genText('template.card.name.background.border.style', template.card.name.background.border.style) + '</p>'
                    //weight
                    htmlstr += '<p>weight:&emsp;' + genText('template.card.name.background.border.weight', template.card.name.background.border.weight) + '</p>'
                    //color
                    htmlstr += '<p>color:&emsp;rgb&emsp;-&emsp;' + genText4Color('template.card.name.background.border.color.rgb', template.card.name.background.border.color.rgb) + '&emsp;alpha&emsp;-&emsp;' + genText4Color('template.card.name.background.border.color.alpha', template.card.name.background.border.color.alpha) + '</p>'
                    //radius
                    htmlstr += '<p>radius:&emsp;' + genText('template.card.name.background.border.radius', template.card.name.background.border.radius) + '</p>'
                }

                htmlstr += '</td><td class="td_width">' + genButton('template.card.name.background.border', has_card_name_background_border) + '</td></tr></table></p>'
                //src
                htmlstr += '<p>src:&emsp;' + genText('template.card.name.background.src', template.card.name.background.src) + '</p>'
            }

            htmlstr += '</td><td class="td_width">' + genButton('template.card.name.background', has_card_name_background) + '</td></tr></table></p>'
            //font
            htmlstr += '<p>font:&emsp;family&emsp;-&emsp;' + genFamily('template.card.name.font.family', template.card.name.font.family) + '&emsp;style&emsp;-&emsp;' + genText('template.card.name.font.style', template.card.name.font.style) + '&emsp;size&emsp;-&emsp;' + genText('template.card.name.font.size', template.card.name.font.size) + '</p>'
            //color
            htmlstr += '<p>color:&emsp;rgb&emsp;-&emsp;' + genText4Color('template.card.name.color.rgb', template.card.name.color.rgb) + '&emsp;alpha&emsp;-&emsp;' + genText4Color('template.card.name.color.alpha', template.card.name.color.alpha) + '</p>'

            //alignment
            htmlstr += '<p>alignment:&emsp;' + genText('template.card.name.alignment', template.card.name.alignment) + '</p>'
            //text
            htmlstr += '<p>text:&emsp;' + genText('template.card.name.text', template.card.name.text) + '</p>'
        }
        //操作按钮
        htmlstr += '</td><td class="td_width">' + genButton('template.card.name', has_card_name) + '</td></tr></table></p>'

        //tel
        htmlstr += '<p><table><tr><td class="td_width">card-tel</td><td>'

        const has_card_tel = template.card.tel && Object.keys(template.card.tel).length > 0
        if (has_card_tel) {
            //position
            htmlstr += '<p>position:&emsp;X&emsp;-&emsp;' + genText('template.card.tel.position.x', template.card.tel.position.x) + '&emsp;Y&emsp;-&emsp;' + genText('template.card.tel.position.y', template.card.tel.position.y) + '</p>'
            //size
            htmlstr += '<p>size:&emsp;width&emsp;-&emsp;' + genText('template.card.tel.size.width', template.card.tel.size.width) + '&emsp;height&emsp;-&emsp;' + genText('template.card.tel.size.height', template.card.tel.size.height) + '</p>'
            //editable moveable
            htmlstr += '<p>editable:&emsp;' + genCheckBox('template.card.tel.editable', template.card.tel.editable) + '&emsp;moveable:&emsp;' + genCheckBox('template.card.tel.moveable', template.card.tel.moveable) + '</p>'
            //background
            htmlstr += '<p><table><tr><td class="td_width">background</td><td>'

            const has_card_tel_background = template.card.tel.background && Object.keys(template.card.tel.background).length > 0
            if (has_card_tel_background) {
                //position
                htmlstr += '<p>position:&emsp;X&emsp;-&emsp;' + genText('template.card.tel.background.position.x', template.card.tel.background.position.x) + '&emsp;Y&emsp;-&emsp;' + genText('template.card.tel.background.position.y', template.card.tel.background.position.y) + '</p>'
                //size
                htmlstr += '<p>size:&emsp;width&emsp;-&emsp;' + genText('template.card.tel.background.size.width', template.card.tel.background.size.width) + '&emsp;height&emsp;-&emsp;' + genText('template.card.tel.background.size.height', template.card.tel.background.size.height) + '</p>'
                //editable moveable
                htmlstr += '<p>editable:&emsp;' + genCheckBox('template.card.tel.background.editable', template.card.tel.background.editable) + '&emsp;moveable:&emsp;' + genCheckBox('template.card.tel.background.moveable', template.card.tel.background.moveable) + '</p>'

                //background.border
                htmlstr += '<p><table><tr><td class="td_width">border</td><td>'

                let has_card_tel_background_border = template.card.tel.background.border && Object.keys(template.card.tel.background.border).length > 0
                if (has_card_tel_background_border) {
                    //position
                    htmlstr += '<p>position:&emsp;X&emsp;-&emsp;' + genText('template.card.tel.background.border.position.x', template.card.tel.background.border.position.x) + '&emsp;Y&emsp;-&emsp;' + genText('template.card.tel.background.border.position.y', template.card.tel.background.border.position.y) + '</p>'
                    //size
                    htmlstr += '<p>size:&emsp;width&emsp;-&emsp;' + genText('template.card.tel.background.border.size.width', template.card.tel.background.border.size.width) + '&emsp;height&emsp;-&emsp;' + genText('template.card.tel.background.border.size.height', template.card.tel.background.border.size.height) + '</p>'
                    //editable moveable
                    htmlstr += '<p>editable:&emsp;' + genCheckBox('template.card.tel.background.border.editable', template.card.tel.background.border.editable) + '&emsp;moveable:&emsp;' + genCheckBox('template.card.tel.background.border.moveable', template.card.tel.background.border.moveable) + '</p>'
                    //style
                    htmlstr += '<p>style:&emsp;' + genText('template.card.tel.background.border.style', template.card.tel.background.border.style) + '</p>'
                    //weight
                    htmlstr += '<p>weight:&emsp;' + genText('template.card.tel.background.border.weight', template.card.tel.background.border.weight) + '</p>'
                    //color
                    htmlstr += '<p>color:&emsp;rgb&emsp;-&emsp;' + genText4Color('template.card.tel.background.border.color.rgb', template.card.tel.background.border.color.rgb) + '&emsp;alpha&emsp;-&emsp;' + genText4Color('template.card.tel.background.border.color.alpha', template.card.tel.background.border.color.alpha) + '</p>'
                    //radius
                    htmlstr += '<p>radius:&emsp;' + genText('template.card.tel.background.border.radius', template.card.tel.background.border.radius) + '</p>'
                }

                htmlstr += '</td><td class="td_width">' + genButton('template.card.tel.background.border', has_card_tel_background_border) + '</td></tr></table></p>'
                //src
                htmlstr += '<p>src:&emsp;' + genText('template.card.tel.background.src', template.card.tel.background.src) + '</p>'
            }

            htmlstr += '</td><td class="td_width">' + genButton('template.card.tel.background', has_card_tel_background) + '</td></tr></table></p>'
            //font
            htmlstr += '<p>font:&emsp;family&emsp;-&emsp;' + genFamily('template.card.tel.font.family', template.card.tel.font.family) + '&emsp;style&emsp;-&emsp;' + genText('template.card.tel.font.style', template.card.tel.font.style) + '&emsp;size&emsp;-&emsp;' + genText('template.card.tel.font.size', template.card.tel.font.size) + '</p>'
            //color
            htmlstr += '<p>color:&emsp;rgb&emsp;-&emsp;' + genText4Color('template.card.tel.color.rgb', template.card.tel.color.rgb) + '&emsp;alpha&emsp;-&emsp;' + genText4Color('template.card.tel.color.alpha', template.card.tel.color.alpha) + '</p>'

            //alignment
            htmlstr += '<p>alignment:&emsp;' + genText('template.card.tel.alignment', template.card.tel.alignment) + '</p>'
            //text
            htmlstr += '<p>text:&emsp;' + genText('template.card.tel.text', template.card.tel.text) + '</p>'
        }
        //操作按钮
        htmlstr += '</td><td class="td_width">' + genButton('template.card.tel', has_card_tel) + '</td></tr></table></p>'

        //address
        htmlstr += '<p><table><tr><td class="td_width">card-address</td><td>'

        const has_card_address = template.card.address && Object.keys(template.card.address).length > 0
        if (has_card_address) {
            //position
            htmlstr += '<p>position:&emsp;X&emsp;-&emsp;' + genText('template.card.address.position.x', template.card.address.position.x) + '&emsp;Y&emsp;-&emsp;' + genText('template.card.address.position.y', template.card.address.position.y) + '</p>'
            //size
            htmlstr += '<p>size:&emsp;width&emsp;-&emsp;' + genText('template.card.address.size.width', template.card.address.size.width) + '&emsp;height&emsp;-&emsp;' + genText('template.card.address.size.height', template.card.address.size.height) + '</p>'
            //editable moveable
            htmlstr += '<p>editable:&emsp;' + genCheckBox('template.card.address.editable', template.card.address.editable) + '&emsp;moveable:&emsp;' + genCheckBox('template.card.address.moveable', template.card.address.moveable) + '</p>'
            //background
            htmlstr += '<p><table><tr><td class="td_width">background</td><td>'

            const has_card_address_background = template.card.address.background && Object.keys(template.card.address.background).length > 0
            if (has_card_address_background) {
                //position
                htmlstr += '<p>position:&emsp;X&emsp;-&emsp;' + genText('template.card.address.background.position.x', template.card.address.background.position.x) + '&emsp;Y&emsp;-&emsp;' + genText('template.card.address.background.position.y', template.card.address.background.position.y) + '</p>'
                //size
                htmlstr += '<p>size:&emsp;width&emsp;-&emsp;' + genText('template.card.address.background.size.width', template.card.address.background.size.width) + '&emsp;height&emsp;-&emsp;' + genText('template.card.address.background.size.height', template.card.address.background.size.height) + '</p>'
                //editable moveable
                htmlstr += '<p>editable:&emsp;' + genCheckBox('template.card.address.background.editable', template.card.address.background.editable) + '&emsp;moveable:&emsp;' + genCheckBox('template.card.address.background.moveable', template.card.address.background.moveable) + '</p>'

                //background.border
                htmlstr += '<p><table><tr><td class="td_width">border</td><td>'

                let has_card_address_background_border = template.card.address.background.border && Object.keys(template.card.address.background.border).length > 0
                if (has_card_address_background_border) {
                    //position
                    htmlstr += '<p>position:&emsp;X&emsp;-&emsp;' + genText('template.card.address.background.border.position.x', template.card.address.background.border.position.x) + '&emsp;Y&emsp;-&emsp;' + genText('template.card.address.background.border.position.y', template.card.address.background.border.position.y) + '</p>'
                    //size
                    htmlstr += '<p>size:&emsp;width&emsp;-&emsp;' + genText('template.card.address.background.border.size.width', template.card.address.background.border.size.width) + '&emsp;height&emsp;-&emsp;' + genText('template.card.address.background.border.size.height', template.card.address.background.border.size.height) + '</p>'
                    //editable moveable
                    htmlstr += '<p>editable:&emsp;' + genCheckBox('template.card.address.background.border.editable', template.card.address.background.border.editable) + '&emsp;moveable:&emsp;' + genCheckBox('template.card.address.background.border.moveable', template.card.address.background.border.moveable) + '</p>'
                    //style
                    htmlstr += '<p>style:&emsp;' + genText('template.card.address.background.border.style', template.card.address.background.border.style) + '</p>'
                    //weight
                    htmlstr += '<p>weight:&emsp;' + genText('template.card.address.background.border.weight', template.card.address.background.border.weight) + '</p>'
                    //color
                    htmlstr += '<p>color:&emsp;rgb&emsp;-&emsp;' + genText4Color('template.card.address.background.border.color.rgb', template.card.address.background.border.color.rgb) + '&emsp;alpha&emsp;-&emsp;' + genText4Color('template.card.address.background.border.color.alpha', template.card.address.background.border.color.alpha) + '</p>'
                    //radius
                    htmlstr += '<p>radius:&emsp;' + genText('template.card.address.background.border.radius', template.card.address.background.border.radius) + '</p>'
                }

                htmlstr += '</td><td class="td_width">' + genButton('template.card.address.background.border', has_card_address_background_border) + '</td></tr></table></p>'
                //src
                htmlstr += '<p>src:&emsp;' + genText('template.card.address.background.src', template.card.address.background.src) + '</p>'
            }

            htmlstr += '</td><td class="td_width">' + genButton('template.card.address.background', has_card_address_background) + '</td></tr></table></p>'
            //font
            htmlstr += '<p>font:&emsp;family&emsp;-&emsp;' + genFamily('template.card.address.font.family', template.card.address.font.family) + '&emsp;style&emsp;-&emsp;' + genText('template.card.address.font.style', template.card.address.font.style) + '&emsp;size&emsp;-&emsp;' + genText('template.card.address.font.size', template.card.address.font.size) + '</p>'
            //color
            htmlstr += '<p>color:&emsp;rgb&emsp;-&emsp;' + genText4Color('template.card.address.color.rgb', template.card.address.color.rgb) + '&emsp;alpha&emsp;-&emsp;' + genText4Color('template.card.address.color.alpha', template.card.address.color.alpha) + '</p>'

            //alignment
            htmlstr += '<p>alignment:&emsp;' + genText('template.card.address.alignment', template.card.address.alignment) + '</p>'
            //text
            htmlstr += '<p>text:&emsp;' + genText('template.card.address.text', template.card.address.text) + '</p>'
        }
        //操作按钮
        htmlstr += '</td><td class="td_width">' + genButton('template.card.address', has_card_address) + '</td></tr></table></p>'
    }


    //操作按钮
    htmlstr += '</td><td class="td_width">' + genButton('template.card', has_card) + '</td></tr>'


    // console.log(htmlstr)
    $("#table_scheam").html(htmlstr)

    gen()
}

function genText(mode, value, index) {
    index = index || 0
    return '<input type="text" style="' + (mode.indexOf('text') >= 0 || mode.indexOf('src') >= 0 ? 'width:300px;' : '') + '" mode="' + mode + '" index="' + index + '" value="' + value + '" onchange="changeText(this)" />'
}

function genText4Color(mode, value, index) {
    if (mode.indexOf('rgb') >= 0) {
        value = value.toString(16).toUpperCase()
    } else if (mode.indexOf('alpha') >= 0) {
        value = (value / 255).toFixed(2)
    }
    index = index || 0
    return '<input type="text" mode="' + mode + '" index="' + index + '" value="' + value + '" onchange="changeText4Color(this)"/>'
}

function genCheckBox(mode, value, index) {
    index = index || 0
    return '<input type="checkbox" mode="' + mode + '" index="' + index + '" ' + (value ? 'checked="checked"' : '') + ' onchange="changeCheckBox(this)" />'
}

function genButton(mode, checked, index) {
    index = index || 0
    return '<input type="button" value="' + (checked ? '删除' : '添加') + '" mode="' + mode + '" index="' + index + '" onclick="' + (checked ? 'delSchema(this)' : 'addSchema(this)') + '"/>'
}

function genButton4ChildsAdd(flag) {
    return '<input type="button" value="添加' + flag + '" mode="template.childs.child" style="width:100%;" flag="' + flag + '" onclick="addSchema(this)"/>'
}

function genFamily(mode, value, index) {
    index = index || 0
    return '<select onchange="changeFamily(this)" mode="' + mode + '" index="' + index + '" value="' + value + '"><option value="" ' + (value == '' ? 'selected="selected"' : '') + '>--未选择--</option><option value="FangZheng-LanTingCuHei" ' + (value == 'FangZheng-LanTingCuHei' ? 'selected="selected"' : '') + '>FangZheng-LanTingCuHei</option><option value="FangZheng-ZhongYaSong" ' + (value == 'FangZheng-ZhongYaSong' ? 'selected="selected"' : '') + '>FangZheng-ZhongYaSong</option><option value="PingFang-Regular" ' + (value == 'PingFang-Regular' ? 'selected="selected"' : '') + '>PingFang-Regular</option><option value="PingFangSC-Medium" ' + (value == 'PingFangSC-Medium' ? 'selected="selected"' : '') + '>PingFangSC-Medium</option></select>'
}

function delSchema(e) {
    const dom = $(e)
    const mode = dom.attr("mode")
    const index = dom.attr("index")
    if (mode == "template.background") {
        template.background = {}
    } else if (mode == "template.background.border") {
        template.background.border = {}
    } else if (mode == 'template.title') {
        template.title = {}
    } else if (mode == "template.title.background") {
        template.title.background = {}
    } else if (mode == "template.title.background.border") {
        template.title.background.border = {}
    } else if (mode == 'template.desc') {
        template.desc = {}
    } else if (mode == "template.desc.background") {
        template.desc.background = {}
    } else if (mode == "template.desc.background.border") {
        template.desc.background.border = {}
    } else if (mode == 'template.childs') {
        template.childs = []
    } else if (mode == 'template.childs.child') {
        template.childs.splice(index, 1)
    } else if (mode == 'template.childs.child.border') {
        template.childs[index].border = {}
    } else if (mode == 'template.childs.child.background') {
        template.childs[index].background = {}
    } else if (mode == 'template.childs.child.background.border') {
        template.childs[index].background.border = {}
    } else if (mode == 'template.card') {
        template.card = {}
    } else if (mode == "template.card.background") {
        template.card.background = {}
    } else if (mode == "template.card.background.border") {
        template.card.background.border = {}
    } else if (mode == "template.card.logo") {
        template.card.logo = {}
    } else if (mode == "template.card.logo.border") {
        template.card.logo.border = {}
    } else if (mode == 'template.card.name') {
        template.card.name = {}
    } else if (mode == "template.card.name.background") {
        template.card.name.background = {}
    } else if (mode == "template.card.name.background.border") {
        template.card.name.background.border = {}
    } else if (mode == 'template.card.tel') {
        template.card.tel = {}
    } else if (mode == "template.card.tel.background") {
        template.card.tel.background = {}
    } else if (mode == "template.card.tel.background.border") {
        template.card.tel.background.border = {}
    } else if (mode == 'template.card.address') {
        template.card.address = {}
    } else if (mode == "template.card.address.background") {
        template.card.address.background = {}
    } else if (mode == "template.card.address.background.border") {
        template.card.address.background.border = {}
    }
    initTemplate()
}

function addSchema(e) {
    const dom = $(e)
    const mode = dom.attr("mode")
    const index = dom.attr("index")
    if (mode == "template.background") {
        template.background = JSON.parse(image_str)
    } else if (mode == "template.background.border") {
        template.background.border = JSON.parse(border_str)
    } else if (mode == 'template.title') {
        template.title = JSON.parse(text_str)
    } else if (mode == "template.title.background") {
        template.title.background = JSON.parse(image_str)
    } else if (mode == "template.title.background.border") {
        template.title.background.border = JSON.parse(border_str)
    } else if (mode == 'template.desc') {
        template.desc = JSON.parse(text_str)
    } else if (mode == "template.desc.background") {
        template.desc.background = JSON.parse(image_str)
    } else if (mode == "template.desc.background.border") {
        template.desc.background.border = JSON.parse(border_str)
    } else if (mode == 'template.childs') {
        template.childs = JSON.parse(childs_str)
    } else if (mode == 'template.childs.child') {
        const flag = dom.attr("flag")
        if (flag == 'Image') {
            template.childs.push(JSON.parse(image_str))
        } else if (flag == 'Text') {
            template.childs.push(JSON.parse(text_str))
        }
    } else if (mode == 'template.childs.child.border') {
        template.childs[index].border = JSON.parse(border_str)
    } else if (mode == 'template.childs.child.background') {
        template.childs[index].background = JSON.parse(image_str)
    } else if (mode == 'template.childs.child.background.border') {
        template.childs[index].background.border = JSON.parse(border_str)
    } else if (mode == 'template.card') {
        template.card = JSON.parse(card_str)
    } else if (mode == "template.card.background") {
        template.card.background = JSON.parse(image_str)
    } else if (mode == "template.card.background.border") {
        template.card.background.border = JSON.parse(border_str)
    } else if (mode == "template.card.logo") {
        template.card.logo = JSON.parse(image_str)
    } else if (mode == "template.card.logo.border") {
        template.card.logo.border = JSON.parse(border_str)
    } else if (mode == 'template.card.name') {
        template.card.name = JSON.parse(text_str)
    } else if (mode == "template.card.name.background") {
        template.card.name.background = JSON.parse(image_str)
    } else if (mode == "template.card.name.background.border") {
        template.card.name.background.border = JSON.parse(border_str)
    } else if (mode == 'template.card.tel') {
        template.card.tel = JSON.parse(text_str)
    } else if (mode == "template.card.tel.background") {
        template.card.tel.background = JSON.parse(image_str)
    } else if (mode == "template.card.tel.background.border") {
        template.card.tel.background.border = JSON.parse(border_str)
    } else if (mode == 'template.card.address') {
        template.card.address = JSON.parse(text_str)
    } else if (mode == "template.card.address.background") {
        template.card.address.background = JSON.parse(image_str)
    } else if (mode == "template.card.address.background.border") {
        template.card.address.background.border = JSON.parse(border_str)
    }
    initTemplate()
}

function changeText(e) {
    const dom = $(e)
    const mode = dom.attr("mode")
    const index = dom.attr("index")

    let value = dom.val()
    if (mode.indexOf('text') >= 0) {

    } else if (mode.indexOf('src') >= 0) {
        const default_dir = $("#default_dir").val()
        console.log(value)
        value = value.replace(default_dir, '')
    } else {
        value = Number(value)
    }

    if ('template.position.x' == mode) {
        template.position.x = value
    } else if ('template.position.y' == mode) {
        template.position.y = value
    } else if ('template.size.width' == mode) {
        template.size.width = value
    } else if ('template.size.height' == mode) {
        template.size.height = value
    } else if ('template.background.position.x' == mode) {
        template.background.position.x = value
    } else if ('template.background.position.y' == mode) {
        template.background.position.y = value
    } else if ('template.background.size.width' == mode) {
        template.background.size.width = value
    } else if ('template.background.size.height' == mode) {
        template.background.size.height = value
    } else if ('template.background.border.position.x' == mode) {
        template.background.border.position.x = value
    } else if ('template.background.border.position.y' == mode) {
        template.background.border.position.y = value
    } else if ('template.background.border.size.width' == mode) {
        template.background.border.size.width = value
    } else if ('template.background.border.size.height' == mode) {
        template.background.border.size.height = value
    } else if ('template.background.border.style' == mode) {
        template.background.border.style = value
    } else if ('template.background.border.weight' == mode) {
        template.background.border.weight = value
    } else if ('template.background.border.radius' == mode) {
        template.background.border.radius = value
    } else if ('template.background.src' == mode) {
        template.background.src = value
    } else if ('template.title.position.x' == mode) {
        template.title.position.x = value
    } else if ('template.title.position.y' == mode) {
        template.title.position.y = value
    } else if ('template.title.size.width' == mode) {
        template.title.size.width = value
    } else if ('template.title.size.height' == mode) {
        template.title.size.height = value
    } else if ('template.title.background.position.x' == mode) {
        template.title.background.position.x = value
    } else if ('template.title.background.position.y' == mode) {
        template.title.background.position.y = value
    } else if ('template.title.background.size.width' == mode) {
        template.title.background.size.width = value
    } else if ('template.title.background.size.height' == mode) {
        template.title.background.size.height = value
    } else if ('template.title.background.border.position.x' == mode) {
        template.title.background.border.position.x = value
    } else if ('template.title.background.border.position.y' == mode) {
        template.title.background.border.position.y = value
    } else if ('template.title.background.border.size.width' == mode) {
        template.title.background.border.size.width = value
    } else if ('template.title.background.border.size.height' == mode) {
        template.title.background.border.size.height = value
    } else if ('template.title.background.border.style' == mode) {
        template.title.background.border.style = value
    } else if ('template.title.background.border.weight' == mode) {
        template.title.background.border.weight = value
    } else if ('template.title.background.border.radius' == mode) {
        template.title.background.border.radius = value
    } else if ('template.title.background.src' == mode) {
        template.title.background.src = value
    } else if ('template.title.font.style' == mode) {
        template.title.font.style = value
    } else if ('template.title.font.size' == mode) {
        template.title.font.size = value
    } else if ('template.title.alignment' == mode) {
        template.title.alignment = value
    } else if ('template.title.text' == mode) {
        template.title.text = value
    } else if ('template.desc.position.x' == mode) {
        template.desc.position.x = value
    } else if ('template.desc.position.y' == mode) {
        template.desc.position.y = value
    } else if ('template.desc.size.width' == mode) {
        template.desc.size.width = value
    } else if ('template.desc.size.height' == mode) {
        template.desc.size.height = value
    } else if ('template.desc.background.position.x' == mode) {
        template.desc.background.position.x = value
    } else if ('template.desc.background.position.y' == mode) {
        template.desc.background.position.y = value
    } else if ('template.desc.background.size.width' == mode) {
        template.desc.background.size.width = value
    } else if ('template.desc.background.size.height' == mode) {
        template.desc.background.size.height = value
    } else if ('template.desc.background.border.position.x' == mode) {
        template.desc.background.border.position.x = value
    } else if ('template.desc.background.border.position.y' == mode) {
        template.desc.background.border.position.y = value
    } else if ('template.desc.background.border.size.width' == mode) {
        template.desc.background.border.size.width = value
    } else if ('template.desc.background.border.size.height' == mode) {
        template.desc.background.border.size.height = value
    } else if ('template.desc.background.border.style' == mode) {
        template.desc.background.border.style = value
    } else if ('template.desc.background.border.weight' == mode) {
        template.desc.background.border.weight = value
    } else if ('template.desc.background.border.radius' == mode) {
        template.desc.background.border.radius = value
    } else if ('template.desc.background.src' == mode) {
        template.desc.background.src = value
    } else if ('template.desc.font.style' == mode) {
        template.desc.font.style = value
    } else if ('template.desc.font.size' == mode) {
        template.desc.font.size = value
    } else if ('template.desc.alignment' == mode) {
        template.desc.alignment = value
    } else if ('template.desc.text' == mode) {
        template.desc.text = value
    } else if ('template.childs.child.position.x' == mode) {
        template.childs[index].position.x = value
    } else if ('template.childs.child.position.y' == mode) {
        template.childs[index].position.y = value
    } else if ('template.childs.child.size.width' == mode) {
        template.childs[index].size.width = value
    } else if ('template.childs.child.size.height' == mode) {
        template.childs[index].size.height = value
    } else if ('template.childs.child.border.position.x' == mode) {
        template.childs[index].border.position.x = value
    } else if ('template.childs.child.border.position.y' == mode) {
        template.childs[index].border.position.y = value
    } else if ('template.childs.child.border.size.width' == mode) {
        template.childs[index].border.size.width = value
    } else if ('template.childs.child.border.size.height' == mode) {
        template.childs[index].border.size.height = value
    } else if ('template.childs.child.border.style' == mode) {
        template.childs[index].border.style = value
    } else if ('template.childs.child.border.weight' == mode) {
        template.childs[index].border.weight = value
    } else if ('template.childs.child.border.radius' == mode) {
        template.childs[index].border.radius = value
    } else if ('template.childs.child.src' == mode) {
        template.childs[index].src = value
    } else if ('template.childs.child.position.x' == mode) {
        template.childs[index].position.x = value
    } else if ('template.childs.child.position.y' == mode) {
        template.childs[index].position.y = value
    } else if ('template.childs.child.size.width' == mode) {
        template.childs[index].size.width = value
    } else if ('template.childs.child.size.height' == mode) {
        template.childs[index].size.height = value
    } else if ('template.childs.child.background.position.x' == mode) {
        template.childs[index].background.position.x = value
    } else if ('template.childs.child.background.position.y' == mode) {
        template.childs[index].background.position.y = value
    } else if ('template.childs.child.background.size.width' == mode) {
        template.childs[index].background.size.width = value
    } else if ('template.childs.child.background.size.height' == mode) {
        template.childs[index].background.size.height = value
    } else if ('template.childs.child.background.border.position.x' == mode) {
        template.childs[index].background.border.position.x = value
    } else if ('template.childs.child.background.border.position.y' == mode) {
        template.childs[index].background.border.position.y = value
    } else if ('template.childs.child.background.border.size.width' == mode) {
        template.childs[index].background.border.size.width = value
    } else if ('template.childs.child.background.border.size.height' == mode) {
        template.childs[index].background.border.size.height = value
    } else if ('template.childs.child.background.border.style' == mode) {
        template.childs[index].background.border.style = value
    } else if ('template.childs.child.background.border.weight' == mode) {
        template.childs[index].background.border.weight = value
    } else if ('template.childs.child.background.border.radius' == mode) {
        template.childs[index].background.border.radius = value
    } else if ('template.childs.child.background.src' == mode) {
        template.childs[index].background.src = value
    } else if ('template.childs.child.font.style' == mode) {
        template.childs[index].font.style = value
    } else if ('template.childs.child.font.size' == mode) {
        template.childs[index].font.size = value
    } else if ('template.childs.child.alignment' == mode) {
        template.childs[index].alignment = value
    } else if ('template.childs.child.text' == mode) {
        template.childs[index].text = value
    } else if ('template.card.background.position.x' == mode) {
        template.card.background.position.x = value
    } else if ('template.card.background.position.y' == mode) {
        template.card.background.position.y = value
    } else if ('template.card.background.size.width' == mode) {
        template.card.background.size.width = value
    } else if ('template.card.background.size.height' == mode) {
        template.card.background.size.height = value
    } else if ('template.card.background.border.position.x' == mode) {
        template.card.background.border.position.x = value
    } else if ('template.card.background.border.position.y' == mode) {
        template.card.background.border.position.y = value
    } else if ('template.card.background.border.size.width' == mode) {
        template.card.background.border.size.width = value
    } else if ('template.card.background.border.size.height' == mode) {
        template.card.background.border.size.height = value
    } else if ('template.card.background.border.style' == mode) {
        template.card.background.border.style = value
    } else if ('template.card.background.border.weight' == mode) {
        template.card.background.border.weight = value
    } else if ('template.card.background.border.radius' == mode) {
        template.card.background.border.radius = value
    } else if ('template.card.background.src' == mode) {
        template.card.background.src = value
    } else if ('template.card.logo.position.x' == mode) {
        template.card.logo.position.x = value
    } else if ('template.card.logo.position.y' == mode) {
        template.card.logo.position.y = value
    } else if ('template.card.logo.size.width' == mode) {
        template.card.logo.size.width = value
    } else if ('template.card.logo.size.height' == mode) {
        template.card.logo.size.height = value
    } else if ('template.card.logo.border.position.x' == mode) {
        template.card.logo.border.position.x = value
    } else if ('template.card.logo.border.position.y' == mode) {
        template.card.logo.border.position.y = value
    } else if ('template.card.logo.border.size.width' == mode) {
        template.card.logo.border.size.width = value
    } else if ('template.card.logo.border.size.height' == mode) {
        template.card.logo.border.size.height = value
    } else if ('template.card.logo.border.style' == mode) {
        template.card.logo.border.style = value
    } else if ('template.card.logo.border.weight' == mode) {
        template.card.logo.border.weight = value
    } else if ('template.card.logo.border.radius' == mode) {
        template.card.logo.border.radius = value
    } else if ('template.card.logo.src' == mode) {
        template.card.logo.src = value
    } else if ('template.card.name.position.x' == mode) {
        template.card.name.position.x = value
    } else if ('template.card.name.position.y' == mode) {
        template.card.name.position.y = value
    } else if ('template.card.name.size.width' == mode) {
        template.card.name.size.width = value
    } else if ('template.card.name.size.height' == mode) {
        template.card.name.size.height = value
    } else if ('template.card.name.background.position.x' == mode) {
        template.card.name.background.position.x = value
    } else if ('template.card.name.background.position.y' == mode) {
        template.card.name.background.position.y = value
    } else if ('template.card.name.background.size.width' == mode) {
        template.card.name.background.size.width = value
    } else if ('template.card.name.background.size.height' == mode) {
        template.card.name.background.size.height = value
    } else if ('template.card.name.background.border.position.x' == mode) {
        template.card.name.background.border.position.x = value
    } else if ('template.card.name.background.border.position.y' == mode) {
        template.card.name.background.border.position.y = value
    } else if ('template.card.name.background.border.size.width' == mode) {
        template.card.name.background.border.size.width = value
    } else if ('template.card.name.background.border.size.height' == mode) {
        template.card.name.background.border.size.height = value
    } else if ('template.card.name.background.border.style' == mode) {
        template.card.name.background.border.style = value
    } else if ('template.card.name.background.border.weight' == mode) {
        template.card.name.background.border.weight = value
    } else if ('template.card.name.background.border.radius' == mode) {
        template.card.name.background.border.radius = value
    } else if ('template.card.name.background.src' == mode) {
        template.card.name.background.src = value
    } else if ('template.card.name.font.style' == mode) {
        template.card.name.font.style = value
    } else if ('template.card.name.font.size' == mode) {
        template.card.name.font.size = value
    } else if ('template.card.name.alignment' == mode) {
        template.card.name.alignment = value
    } else if ('template.card.name.text' == mode) {
        template.card.name.text = value
    } else if ('template.card.tel.position.x' == mode) {
        template.card.tel.position.x = value
    } else if ('template.card.tel.position.y' == mode) {
        template.card.tel.position.y = value
    } else if ('template.card.tel.size.width' == mode) {
        template.card.tel.size.width = value
    } else if ('template.card.tel.size.height' == mode) {
        template.card.tel.size.height = value
    } else if ('template.card.tel.background.position.x' == mode) {
        template.card.tel.background.position.x = value
    } else if ('template.card.tel.background.position.y' == mode) {
        template.card.tel.background.position.y = value
    } else if ('template.card.tel.background.size.width' == mode) {
        template.card.tel.background.size.width = value
    } else if ('template.card.tel.background.size.height' == mode) {
        template.card.tel.background.size.height = value
    } else if ('template.card.tel.background.border.position.x' == mode) {
        template.card.tel.background.border.position.x = value
    } else if ('template.card.tel.background.border.position.y' == mode) {
        template.card.tel.background.border.position.y = value
    } else if ('template.card.tel.background.border.size.width' == mode) {
        template.card.tel.background.border.size.width = value
    } else if ('template.card.tel.background.border.size.height' == mode) {
        template.card.tel.background.border.size.height = value
    } else if ('template.card.tel.background.border.style' == mode) {
        template.card.tel.background.border.style = value
    } else if ('template.card.tel.background.border.weight' == mode) {
        template.card.tel.background.border.weight = value
    } else if ('template.card.tel.background.border.radius' == mode) {
        template.card.tel.background.border.radius = value
    } else if ('template.card.tel.background.src' == mode) {
        template.card.tel.background.src = value
    } else if ('template.card.tel.font.style' == mode) {
        template.card.tel.font.style = value
    } else if ('template.card.tel.font.size' == mode) {
        template.card.tel.font.size = value
    } else if ('template.card.tel.alignment' == mode) {
        template.card.tel.alignment = value
    } else if ('template.card.tel.text' == mode) {
        template.card.tel.text = value
    } else if ('template.card.address.position.x' == mode) {
        template.card.address.position.x = value
    } else if ('template.card.address.position.y' == mode) {
        template.card.address.position.y = value
    } else if ('template.card.address.size.width' == mode) {
        template.card.address.size.width = value
    } else if ('template.card.address.size.height' == mode) {
        template.card.address.size.height = value
    } else if ('template.card.address.background.position.x' == mode) {
        template.card.address.background.position.x = value
    } else if ('template.card.address.background.position.y' == mode) {
        template.card.address.background.position.y = value
    } else if ('template.card.address.background.size.width' == mode) {
        template.card.address.background.size.width = value
    } else if ('template.card.address.background.size.height' == mode) {
        template.card.address.background.size.height = value
    } else if ('template.card.address.background.border.position.x' == mode) {
        template.card.address.background.border.position.x = value
    } else if ('template.card.address.background.border.position.y' == mode) {
        template.card.address.background.border.position.y = value
    } else if ('template.card.address.background.border.size.width' == mode) {
        template.card.address.background.border.size.width = value
    } else if ('template.card.address.background.border.size.height' == mode) {
        template.card.address.background.border.size.height = value
    } else if ('template.card.address.background.border.style' == mode) {
        template.card.address.background.border.style = value
    } else if ('template.card.address.background.border.weight' == mode) {
        template.card.address.background.border.weight = value
    } else if ('template.card.address.background.border.radius' == mode) {
        template.card.address.background.border.radius = value
    } else if ('template.card.address.background.src' == mode) {
        template.card.address.background.src = value
    } else if ('template.card.address.font.style' == mode) {
        template.card.address.font.style = value
    } else if ('template.card.address.font.size' == mode) {
        template.card.address.font.size = value
    } else if ('template.card.address.alignment' == mode) {
        template.card.address.alignment = value
    } else if ('template.card.address.text' == mode) {
        template.card.address.text = value
    }

    gen()
}

function changeText4Color(e) {
    const dom = $(e)
    const mode = dom.attr("mode")
    const index = dom.attr("index")
    let value = dom.val()
    if (mode.indexOf('rgb') >= 0) {
        value = parseInt(value.toUpperCase(), 16)
    } else if (mode.indexOf('alpha') >= 0) {
        value = Math.round(value * 255)
        if (value > 255) {
            value = 255
        } else if (value < 0) {
            value = 0
        }
    }

    if (mode == 'template.background.border.color.rgb') {
        template.background.border.color.rgb = value
    } else if (mode == 'template.background.border.color.alpha') {
        template.background.border.color.alpha = value
    } else if (mode == 'template.title.background.border.color.rgb') {
        template.title.background.border.color.rgb = value
    } else if (mode == 'template.title.background.border.color.alpha') {
        template.title.background.border.color.alpha = value
    } else if (mode == 'template.title.color.rgb') {
        template.title.color.rgb = value
    } else if (mode == 'template.title.color.alpha') {
        template.title.color.alpha = value
    } else if (mode == 'template.desc.background.border.color.rgb') {
        template.desc.background.border.color.rgb = value
    } else if (mode == 'template.desc.background.border.color.alpha') {
        template.desc.background.border.color.alpha = value
    } else if (mode == 'template.desc.color.rgb') {
        template.desc.color.rgb = value
    } else if (mode == 'template.desc.color.alpha') {
        template.desc.color.alpha = value
    } else if (mode == 'template.childs.child.border.color.rgb') {
        template.childs[index].border.color.rgb = value
    } else if (mode == 'template.childs.child.border.color.alpha') {
        template.childs[index].border.color.alpha = value
    } else if (mode == 'template.childs.child.background.border.color.rgb') {
        template.childs[index].background.border.color.rgb = value
    } else if (mode == 'template.childs.child.background.border.color.alpha') {
        template.childs[index].background.border.color.alpha = value
    } else if (mode == 'template.childs.child.color.rgb') {
        template.childs[index].color.rgb = value
    } else if (mode == 'template.childs.child.color.alpha') {
        template.childs[index].color.alpha = value
    } else if (mode == 'template.card.background.border.color.rgb') {
        template.card.background.border.color.rgb = value
    } else if (mode == 'template.card.background.border.color.alpha') {
        template.card.background.border.color.alpha = value
    } else if (mode == 'template.card.logo.border.color.rgb') {
        template.card.logo.border.color.rgb = value
    } else if (mode == 'template.card.logo.border.color.alpha') {
        template.card.logo.border.color.alpha = value
    } else if (mode == 'template.card.name.background.border.color.rgb') {
        template.card.name.background.border.color.rgb = value
    } else if (mode == 'template.card.name.background.border.color.alpha') {
        template.card.name.background.border.color.alpha = value
    } else if (mode == 'template.card.name.color.rgb') {
        template.card.name.color.rgb = value
    } else if (mode == 'template.card.name.color.alpha') {
        template.card.name.color.alpha = value
    } else if (mode == 'template.card.tel.background.border.color.rgb') {
        template.card.tel.background.border.color.rgb = value
    } else if (mode == 'template.card.tel.background.border.color.alpha') {
        template.card.tel.background.border.color.alpha = value
    } else if (mode == 'template.card.tel.color.rgb') {
        template.card.tel.color.rgb = value
    } else if (mode == 'template.card.tel.color.alpha') {
        template.card.tel.color.alpha = value
    } else if (mode == 'template.card.address.background.border.color.rgb') {
        template.card.address.background.border.color.rgb = value
    } else if (mode == 'template.card.address.background.border.color.alpha') {
        template.card.address.background.border.color.alpha = value
    } else if (mode == 'template.card.address.color.rgb') {
        template.card.address.color.rgb = value
    } else if (mode == 'template.card.address.color.alpha') {
        template.card.address.color.alpha = value
    }

    gen()
}

function changeCheckBox(e) {
    const dom = $(e)
    const mode = dom.attr("mode")
    const index = dom.attr("index")
    const checked = dom[0].checked
    if (mode == 'template.editable') {
        template.editable = checked
    } else if (mode == 'template.moveable') {
        template.moveable = checked
    } else if (mode == 'template.background.editable') {
        template.background.editable = checked
    } else if (mode == 'template.background.moveable') {
        template.background.moveable = checked
    } else if (mode == 'template.background.border.editable') {
        template.background.border.editable = checked
    } else if (mode == 'template.background.border.moveable') {
        template.background.border.moveable = checked
    } else if (mode == 'template.title.editable') {
        template.title.editable = checked
    } else if (mode == 'template.title.moveable') {
        template.title.moveable = checked
    } else if (mode == 'template.title.background.editable') {
        template.title.background.editable = checked
    } else if (mode == 'template.title.background.moveable') {
        template.title.background.moveable = checked
    } else if (mode == 'template.title.background.border.editable') {
        template.title.background.border.editable = checked
    } else if (mode == 'template.title.background.border.moveable') {
        template.title.background.border.moveable = checked
    } else if (mode == 'template.desc.editable') {
        template.desc.editable = checked
    } else if (mode == 'template.desc.moveable') {
        template.desc.moveable = checked
    } else if (mode == 'template.desc.background.editable') {
        template.desc.background.editable = checked
    } else if (mode == 'template.desc.background.moveable') {
        template.desc.background.moveable = checked
    } else if (mode == 'template.desc.background.border.editable') {
        template.desc.background.border.editable = checked
    } else if (mode == 'template.desc.background.border.moveable') {
        template.desc.background.border.moveable = checked
    } else if (mode == 'template.childs.child.editable') {
        template.childs[index].editable = checked
    } else if (mode == 'template.childs.child.moveable') {
        template.childs[index].moveable = checked
    } else if (mode == 'template.childs.child.border.editable') {
        template.childs[index].border.editable = checked
    } else if (mode == 'template.childs.child.border.moveable') {
        template.childs[index].border.moveable = checked
    } else if (mode == 'template.childs.child.background.editable') {
        template.childs[index].background.editable = checked
    } else if (mode == 'template.childs.child.background.moveable') {
        template.childs[index].background.moveable = checked
    } else if (mode == 'template.childs.child.background.border.editable') {
        template.childs[index].background.border.editable = checked
    } else if (mode == 'template.childs.child.background.border.moveable') {
        template.childs[index].background.border.moveable = checked
    } else if (mode == 'template.card.background.editable') {
        template.card.background.editable = checked
    } else if (mode == 'template.card.background.moveable') {
        template.card.background.moveable = checked
    } else if (mode == 'template.card.background.border.editable') {
        template.card.background.border.editable = checked
    } else if (mode == 'template.card.background.border.moveable') {
        template.card.background.border.moveable = checked
    } else if (mode == 'template.card.logo.editable') {
        template.card.logo.editable = checked
    } else if (mode == 'template.card.logo.moveable') {
        template.card.logo.moveable = checked
    } else if (mode == 'template.card.logo.border.editable') {
        template.card.logo.border.editable = checked
    } else if (mode == 'template.card.logo.border.moveable') {
        template.card.logo.border.moveable = checked
    } else if (mode == 'template.card.name.editable') {
        template.card.name.editable = checked
    } else if (mode == 'template.card.name.moveable') {
        template.card.name.moveable = checked
    } else if (mode == 'template.card.name.background.editable') {
        template.card.name.background.editable = checked
    } else if (mode == 'template.card.name.background.moveable') {
        template.card.name.background.moveable = checked
    } else if (mode == 'template.card.name.background.border.editable') {
        template.card.name.background.border.editable = checked
    } else if (mode == 'template.card.name.background.border.moveable') {
        template.card.name.background.border.moveable = checked
    } else if (mode == 'template.card.tel.editable') {
        template.card.tel.editable = checked
    } else if (mode == 'template.card.tel.moveable') {
        template.card.tel.moveable = checked
    } else if (mode == 'template.card.tel.background.editable') {
        template.card.tel.background.editable = checked
    } else if (mode == 'template.card.tel.background.moveable') {
        template.card.tel.background.moveable = checked
    } else if (mode == 'template.card.tel.background.border.editable') {
        template.card.tel.background.border.editable = checked
    } else if (mode == 'template.card.tel.background.border.moveable') {
        template.card.tel.background.border.moveable = checked
    } else if (mode == 'template.card.address.editable') {
        template.card.address.editable = checked
    } else if (mode == 'template.card.address.moveable') {
        template.card.address.moveable = checked
    } else if (mode == 'template.card.address.background.editable') {
        template.card.address.background.editable = checked
    } else if (mode == 'template.card.address.background.moveable') {
        template.card.address.background.moveable = checked
    } else if (mode == 'template.card.address.background.border.editable') {
        template.card.address.background.border.editable = checked
    } else if (mode == 'template.card.address.background.border.moveable') {
        template.card.address.background.border.moveable = checked
    }

    gen()
}

function changeFamily(e) {
    const dom = $(e)
    const mode = dom.attr("mode")
    const index = dom.attr("index")
    const family = dom.val()
    if (mode == 'template.title.font.family') {
        template.title.font.family = family
    } else if (mode == 'template.desc.font.family') {
        template.desc.font.family = family
    } else if (mode == 'template.childs.child.font.family') {
        template.childs[index].font.family = family
    } else if (mode == 'template.card.name.font.family') {
        template.card.name.font.family = family
    } else if (mode == 'template.card.tel.font.family') {
        template.card.tel.font.family = family
    } else if (mode == 'template.card.address.font.family') {
        template.card.address.font.family = family
    }

    gen()
}

function gen() {
    $("#template_result").val(JSON.stringify(template, null, "\t"))

    initSchemaView()
    // $("#template_result").val(arr)
}