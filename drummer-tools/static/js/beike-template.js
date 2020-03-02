const template_str = '[{"title":"","subtitle":"","image":null,"video":null,"audio":null}]'

const item_str = '{"title":"","subtitle":"","image":null,"video":null,"audio":null}'

const image_str = '{"width":1920,"height":1080,"src":"resources/"}'

const video_str = '{"thumb":{"width":1920,"height":1080,"src":"resources/"},"src":"resources/","duration":0}'

const audio_str = '{"src":"resources/","duration":0}'

let template = JSON.parse(template_str)

function initTemplate() {

    let htmlstr = ''

    for (let i in template) {
        const item = template[i]
        htmlstr += '<tr><td class="td_width">' + (Number(i) + 1) + '</td><td>'
        htmlstr += '<p>title:&emsp;' + genText('template.title', item.title, i) + '</p>'
        htmlstr += '<p>subtitle:&emsp;' + genText('template.subtitle', item.subtitle, i) + '</p>'

        let buttons = ''
        const has_image = item.image && Object.keys(item.image).length > 0
        if (has_image) {
            htmlstr += '<p><table><tr><td class="td_width">Image:</td><td>'
            htmlstr += '<p>width:&emsp;' + genText('template.image.width', item.image.width, i)
            htmlstr += '&emsp;&emsp;height:&emsp;' + genText('template.image.height', item.image.height, i) + '</p>'
            htmlstr += '<p>src:&emsp;' + genText('template.image.src', item.image.src, i) + '</p>'
            htmlstr += '</td><td>' + genButton('Image', 'template.image', has_image, i) + '</td></tr></table></p>'
        } else {
            buttons += '<p>' + genButton('Image', 'template.image', has_image, i) + '</p>'
        }

        const has_video = item.video && Object.keys(item.video).length > 0
        if (has_video) {
            htmlstr += '<p><table><tr><td class="td_width">Video:</td><td>'
            htmlstr += '<p><table><tr></tr><td>Thumb:</td><td>'
            htmlstr += '<p>width:&emsp;' + genText('template.video.thumb.width', item.video.thumb.width, i)
            htmlstr += '&emsp;&emsp;height:&emsp;' + genText('template.video.thumb.height', item.video.thumb.height, i) + '</p>'
            htmlstr += '<p>src:&emsp;' + genText('template.video.thumb.src', item.video.thumb.src, i) + '</p>'
            htmlstr += '</td></tr></table></p>'
            htmlstr += '<p>src:&emsp;' + genText('template.video.src', item.video.src, i) + '</p>'
            htmlstr += '<p>duration:&emsp;' + genText('template.video.duration', item.video.duration, i) + '&emsp;秒</p>'
            htmlstr += '</td><td>' + genButton('Video', 'template.video', has_video, i) + '</td></tr></table></p>'
        } else {
            buttons += '<p>' + genButton('Video', 'template.video', has_video, i) + '</p>'
        }

        const has_audio = item.audio && Object.keys(item.audio).length > 0
        if (has_audio) {
            htmlstr += '<p><table><tr><td class="td_width">Audio:</td><td>'
            htmlstr += '<p>src:&emsp;' + genText('template.audio.src', item.audio.src, i) + '</p>'
            htmlstr += '<p>duration:&emsp;' + genText('template.audio.duration', item.audio.duration, i) + '&emsp;秒</p>'
            htmlstr += '</td><td>' + genButton('Audio', 'template.audio', has_audio, i) + '</td></tr></table></p>'
        } else {
            buttons += '<p>' + genButton('Audio', 'template.audio', has_audio, i) + '</p>'
        }
        //操作按钮
        htmlstr += '</td><td class="td_width"><p>' + genButton('', 'template.item', true, i) + '</p>' + buttons + '</td></tr>'
    }

    htmlstr += '<tr><td class="td_width">' + genButton('', 'template.item', false, 0) + '</td><td></td><td class="td_width"></td></tr>'

    $("#table_scheam").html(htmlstr)

    gen()
}

function genText(mode, value, index) {
    index = index || 0
    return '<input type="text" style="' + (mode.indexOf('title') >= 0 || mode.indexOf('subtitle') >= 0 || mode.indexOf('src') >= 0 ? 'width:300px;' : '') + '" mode="' + mode + '" index="' + index + '" value="' + value + '" onchange="changeText(this)" />'
}

function genButton(flag, mode, checked, index) {
    index = index || 0
    return '<input type="button" style="width:100%;" value="' + (checked ? '删除' : '添加') + flag + '" mode="' + mode + '" index="' + index + '" onclick="' + (checked ? 'delSchema(this)' : 'addSchema(this)') + '"/>'
}

function delSchema(e) {
    const dom = $(e)
    const mode = dom.attr("mode")
    const index = dom.attr("index")
    if (mode == 'template.image') {
        template[index].image = null
    } else if (mode == 'template.video') {
        template[index].video = null
    } else if (mode == 'template.audio') {
        template[index].audio = null
    } else if (mode == 'template.item') {
        template.splice(index, 1)
    }
    initTemplate()
}

function addSchema(e) {
    const dom = $(e)
    const mode = dom.attr("mode")
    const index = dom.attr("index")
    if (mode == 'template.image') {
        template[index].image = JSON.parse(image_str)
    } else if (mode == 'template.video') {
        template[index].video = JSON.parse(video_str)
    } else if (mode == 'template.audio') {
        template[index].audio = JSON.parse(audio_str)
    } else if (mode == 'template.item') {
        template.push(JSON.parse(item_str))
    }
    initTemplate()
}

function changeText(e) {
    const dom = $(e)
    const mode = dom.attr("mode")
    const index = dom.attr("index")

    let value = dom.val()
    if (mode.indexOf('src') >= 0) {
        const default_dir = $("#default_dir").val()
        value = value.replace(default_dir, '')
    }

    if ('template.title' == mode) {
        template[index].title = value
    } else if ('template.subtitle' == mode) {
        template[index].subtitle = value
    } else if ('template.image.width' == mode) {
        template[index].image.width = Number(value)
    } else if ('template.image.height' == mode) {
        template[index].image.height = Number(value)
    } else if ('template.image.src' == mode) {
        template[index].image.src = value
    } else if ('template.video.thumb.width' == mode) {
        template[index].video.thumb.width = Number(value)
    } else if ('template.video.thumb.height' == mode) {
        template[index].video.thumb.height = Number(value)
    } else if ('template.video.thumb.src' == mode) {
        template[index].video.thumb.src = value
    } else if ('template.video.src' == mode) {
        template[index].video.src = value
    } else if ('template.video.duration' == mode) {
        template[index].video.duration = Number(value)
    } else if ('template.audio.src' == mode) {
        template[index].audio.src = value
    } else if ('template.audio.duration' == mode) {
        template[index].audio.duration = Number(value)
    }

    gen()
}

function gen() {
    let template_str = JSON.stringify(template)
    template_str = template_str.replace(/{}/g, 'null')
    template_str = template_str.replace(/\[\]/g, 'null')

    $("#template_result").val(JSON.stringify(JSON.parse(template_str), null, "\t"))

    initSchemaView()
    // $("#template_result").val(arr)
}