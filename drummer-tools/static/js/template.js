const template_str = '{"class":"Template","position":{"x":0,"y":0},"size":{"width":1920,"height":1080},"background":null,"age":[1,2,3],"childs":[{"class":"Image","position":{"x":0,"y":0},"size":{"width":1920,"height":1080},"src":"resources/"}]}'

const image_str = '{"class":"Image","position":{"x":0,"y":0},"size":{"width":1920,"height":1080},"src":"resources/"}'

const video_str = '{"class":"Video","position":{"x":0,"y":0},"size":{"width":0,"height":0},"src":"resources/"}'

const audio_str = '{"class":"Audio","position":{"x":0,"y":0},"size":{"width":0,"height":0},"src":"resources/"}'

const childs_str = '[' + image_str + ',' + video_str + ',' + audio_str + ']'

let template = JSON.parse(template_str)

function initTemplate() {

    let htmlstr = ''
    //Template
    htmlstr += '<tr><td class="td_width">template</td><td>'
    //position
    htmlstr += '<p>position:&emsp;X&emsp;-&emsp;' + genText('template.position.x', template.position.x) + '&emsp;Y&emsp;-&emsp;' + genText('template.position.y', template.position.y) + '</p>'
    //size
    htmlstr += '<p>size:&emsp;width&emsp;-&emsp;' + genText('template.size.width', template.size.width) + '&emsp;height&emsp;-&emsp;' + genText('template.size.height', template.size.height) + '</p>'
    //操作按钮
    htmlstr += '</td><td class="td_width"></td></tr>'
    //background
    htmlstr += '<tr><td class="td_width">background</td><td>'

    const has_background = template.background && Object.keys(template.background).length > 0
    if (has_background) {
        //src
        htmlstr += '<p>src:&emsp;' + genText('template.background.src', template.background.src) + '</p>'
    }

    //操作按钮
    htmlstr += '</td><td class="td_width">' + genButton('template.background', has_background) + '</td></tr>'

    //Age
    htmlstr += '<tr><td class="td_width">age</td><td>'
    template.age = template.age || [1, 2, 3]
    if (Object.keys(template.age).length === 0) {
        template.age = [1, 2, 3]
    }
    htmlstr += genAge('template.age', template.age)
    //操作按钮
    htmlstr += '</td><td class="td_width"></td></tr>'

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

                //src
                htmlstr += '<p>src:&emsp;' + genText('template.childs.child.src', child.src, i) + '</p>'
            } else if (child.class == 'Video') {
                //position
                htmlstr += '<p>position:&emsp;X&emsp;-&emsp;' + genText('template.childs.child.position.x', child.position.x, i) + '&emsp;Y&emsp;-&emsp;' + genText('template.childs.child.position.y', child.position.y, i) + '</p>'
                //size
                htmlstr += '<p>size:&emsp;width&emsp;-&emsp;' + genText('template.childs.child.size.width', child.size.width, i) + '&emsp;height&emsp;-&emsp;' + genText('template.childs.child.size.height', child.size.height, i) + '</p>'

                //text
                htmlstr += '<p>src:&emsp;' + genText('template.childs.child.src', child.src, i) + '</p>'
            } else if (child.class == 'Audio') {
                //position
                htmlstr += '<p>position:&emsp;X&emsp;-&emsp;' + genText('template.childs.child.position.x', child.position.x, i) + '&emsp;Y&emsp;-&emsp;' + genText('template.childs.child.position.y', child.position.y, i) + '</p>'
                //size
                htmlstr += '<p>size:&emsp;width&emsp;-&emsp;' + genText('template.childs.child.size.width', child.size.width, i) + '&emsp;height&emsp;-&emsp;' + genText('template.childs.child.size.height', child.size.height, i) + '</p>'

                //text
                htmlstr += '<p>src:&emsp;' + genText('template.childs.child.src', child.src, i) + '</p>'
            }
            htmlstr += '</td><td class="td_width">' + genButton('template.childs.child', true, i) + '</td></tr></table></p>'
        }
    }
    //操作按钮
    htmlstr += '</td><td class="td_width"><p>' + genButton('template.childs', has_childs) + '</p><p>' + genButton4ChildsAdd('Image') + '</p><p>' + genButton4ChildsAdd('Video') + '</p><p>' + genButton4ChildsAdd('Audio') + '</p></td></tr>'

    $("#table_scheam").html(htmlstr)

    gen()
}

function genText(mode, value, index) {
    index = index || 0
    return '<input type="text" style="' + (mode.indexOf('text') >= 0 || mode.indexOf('src') >= 0 ? 'width:300px;' : '') + '" mode="' + mode + '" index="' + index + '" value="' + value + '" onchange="changeText(this)" />'
}

function genButton(mode, checked, index) {
    index = index || 0
    return '<input type="button" value="' + (checked ? '删除' : '添加') + '" mode="' + mode + '" index="' + index + '" onclick="' + (checked ? 'delSchema(this)' : 'addSchema(this)') + '"/>'
}

function genButton4ChildsAdd(flag) {
    return '<input type="button" value="添加' + flag + '" mode="template.childs.child" style="width:100%;" flag="' + flag + '" onclick="addSchema(this)"/>'
}

function genAge(mode, value, index) {
    index = index || 0
    return '4-6岁：<input id="template.age_1" type="checkbox" onchange="changeAge(this)" mode="' + mode + '" index="' + index + '" value="1" ' + (value && value.includes(1) ? 'checked' : '') + ' />'
        + '&emsp;&emsp;6-8岁：<input id="template.age_2" type="checkbox" onchange="changeAge(this)" mode="' + mode + '" index="' + index + '" value="2" ' + (value && value.includes(2) ? 'checked' : '') + ' />'
        + '&emsp;&emsp;8岁以上：<input id="template.age_3" type="checkbox" onchange="changeAge(this)" mode="' + mode + '" index="' + index + '" value="3" ' + (value && value.includes(3) ? 'checked' : '') + ' />'
}

function delSchema(e) {
    const dom = $(e)
    const mode = dom.attr("mode")
    const index = dom.attr("index")
    if (mode == "template.background") {
        template.background = {}
    } else if (mode == 'template.childs') {
        template.childs = []
    } else if (mode == 'template.childs.child') {
        template.childs.splice(index, 1)
    }
    initTemplate()
}

function addSchema(e) {
    const dom = $(e)
    const mode = dom.attr("mode")
    const index = dom.attr("index")
    if (mode == "template.background") {
        template.background = JSON.parse(image_str)
    } else if (mode == 'template.childs') {
        template.childs = JSON.parse(childs_str)
    } else if (mode == 'template.childs.child') {
        const flag = dom.attr("flag")
        if (flag == 'Image') {
            template.childs.push(JSON.parse(image_str))
        } else if (flag == 'Video') {
            template.childs.push(JSON.parse(video_str))
        } else if (flag == 'Audio') {
            template.childs.push(JSON.parse(audio_str))
        }
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
    } else if ('template.background.src' == mode) {
        template.background.src = value
    } else if ('template.childs.child.position.x' == mode) {
        template.childs[index].position.x = value
    } else if ('template.childs.child.position.y' == mode) {
        template.childs[index].position.y = value
    } else if ('template.childs.child.size.width' == mode) {
        template.childs[index].size.width = value
    } else if ('template.childs.child.size.height' == mode) {
        template.childs[index].size.height = value
    } else if ('template.childs.child.src' == mode) {
        template.childs[index].src = value
    }

    gen()
}

function changeAge(e) {
    const dom = $(e)
    const mode = dom.attr("mode")
    const index = dom.attr("index")
    const age = dom.val()
    const checked = dom.prop("checked")
    if (mode == 'template.age') {
        template.age = template.age || []
        if (checked) {
            template.age.push(Number(age))
        } else {
            remove(template.age, Number(age))
        }

        template.age = template.age || []
        if (Object.keys(template.age).length === 0) {
            template.age = [1, 2, 3]

            $("input:checkbox[id^='template.age_']").prop("checked", true)
        }
    }

    gen()
}

function gen() {
    let template_str = JSON.stringify(template)
    template_str = template_str.replace(/{}/g, 'null')
    template_str = template_str.replace(/\[\]/g, 'null')

    $("#template_result").val(JSON.stringify(JSON.parse(template_str), null, "\t"))

    playBackgroundAudio()
    initSchemaView()
    // $("#template_result").val(arr)
}

var audioPlayer;

function playBackgroundAudio() {
    const has_background = template.background && Object.keys(template.background).length > 0

    if (!has_background) {
        if (audioPlayer) {
            audioPlayer.pause()
        }
        return
    }
    let src = (template.background.src || '').trim()
    const default_dir = $("#default_dir").val()
    if (src) {
        audioPlayer = new Audio('file://' + default_dir + src)
        $(audioPlayer).bind("ended", function () {
            audioPlayer.play();
        });
        audioPlayer.play();
    } else {
        if (audioPlayer) {
            audioPlayer.pause()
        }
    }
}

function remove(array, val) {
    const index = array.indexOf(val);
    if (index > -1) {
        array.splice(index, 1);
    }
};