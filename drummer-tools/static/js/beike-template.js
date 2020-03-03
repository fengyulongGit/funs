const template_str = '[{"title":"破冰","data":[{"subtitle":"","linkFlow":"","accompany":null,"linkTalk":null,"teachingTalk":null,"desc":""}]},' +
    '{"title":"知识","data":[{"subtitle":"","linkFlow":"","accompany":null,"linkTalk":null,"teachingTalk":null,"desc":""}]},' +
    '{"title":"示范","data":[{"subtitle":"","linkFlow":"","accompany":null,"linkTalk":null,"teachingTalk":null,"desc":""}]},' +
    '{"title":"讲解","data":[{"subtitle":"","linkFlow":"","accompany":null,"linkTalk":null,"teachingTalk":null,"desc":""}]},' +
    '{"title":"曲目","data":[{"subtitle":"","linkFlow":"","accompany":null,"linkTalk":null,"teachingTalk":null,"desc":""}]},' +
    '{"title":"下课","data":[{"subtitle":"","linkFlow":"","accompany":null,"linkTalk":null,"teachingTalk":null,"desc":""}]}]'

const item_str = '{"title":"","data":[{"subtitle":"","linkFlow":"","accompany":null,"linkTalk":null,"teachingTalk":null,"desc":""}]}'

const data_item_str = '{"subtitle":"","linkFlow":"","accompany":null,"linkTalk":null,"teachingTalk":null,"desc":""}'

const video_str = '{"type":0,"src":"resources/"}'

const audio_str = '{"type":1,"src":"resources/","duration":0}'

let template = JSON.parse(template_str)

function initTemplate() {

    let htmlstr = ''

    for (let i in template) {
        const item = template[i]
        htmlstr += '<tr><td class="td_width">' + genText('template.title', item.title, i) + '</td><td>'
        for (let j in item.data) {
            const data = item.data[j]

            htmlstr += '<p><table><tr><td class="td_width">' + (Number(j) + 1) + '</td><td>'
            htmlstr += '<p>二级标题:&emsp;' + genText('template.data.subtitle', data.subtitle, i + '_' + j, true) + '</p>'
            htmlstr += '<p>环节流程:&emsp;' + genText('template.data.linkFlow', data.linkFlow, i + '_' + j, true) + '</p>'

            // 伴奏音频
            const has_accompany = data.accompany && Object.keys(data.accompany).length > 0
            htmlstr += '<p><table><tr><td class="td_width">伴奏音频</td><td>'
            if (has_accompany) {

                htmlstr += '<p>src:&emsp;' + genText('template.data.accompany.src', data.accompany.src, i + '_' + j, true) + '</p>'
                htmlstr += '<p>duration:&emsp;' + genText('template.data.accompany.duration', data.accompany.duration, i + '_' + j) + '&emsp;秒</p>'
            }
            htmlstr += '</td><td class="td_width">' + genButton('', 'template.data.accompany', has_accompany, i + '_' + j) + '</td></tr></table></p>'

            // 环节话术音频
            htmlstr += '<p><table><tr><td class="td_width">环节话术音频</td><td>'
            data.linkTalk = data.linkTalk || []
            for (let k in data.linkTalk) {
                const talk = data.linkTalk[k]
                htmlstr += '<p><table><tr><td class="td_width">' + (talk.type == 0 ? '视频' : '音频') + (Number(k) + 1) + '</td><td>'
                htmlstr += '<p>src:&emsp;' + genText('template.data.linkTalk.src', talk.src, i + '_' + j + '_' + k, true) + '</p>'
                if (talk.type == 1) {
                    htmlstr += '<p>时长:&emsp;' + genText('template.data.linkTalk.duration', talk.duration, i + '_' + j + '_' + k) + '&emsp;秒</p>'
                }
                htmlstr += '</td><td class="td_width">' + genButton('', 'template.data.linkTalk', true, i + '_' + j + '_' + k) + '</td></tr></table></p>'
            }
            htmlstr += '<p>' + genButton('视频', 'template.data.linkTalk.video', false, i + '_' + j) + '&emsp;&emsp;' + genButton('音频', 'template.data.linkTalk.audio', false, i + '_' + j) + '<p>'
            htmlstr += '</td></tr></table></p>'

            // 环节话术音频
            htmlstr += '<p><table><tr><td class="td_width">教学法话术音频</td><td>'
            data.teachingTalk = data.teachingTalk || []
            for (let k in data.teachingTalk) {
                const talk = data.teachingTalk[k]
                htmlstr += '<p><table><tr><td class="td_width">' + (talk.type == 0 ? '视频' : '音频') + (Number(k) + 1) + '</td><td>'
                htmlstr += '<p>src:&emsp;' + genText('template.data.teachingTalk.src', talk.src, i + '_' + j + '_' + k, true) + '</p>'
                if (talk.type == 1) {
                    htmlstr += '<p>时长:&emsp;' + genText('template.data.teachingTalk.duration', talk.duration, i + '_' + j + '_' + k) + '&emsp;秒</p>'
                }
                htmlstr += '</td><td class="td_width">' + genButton('', 'template.data.teachingTalk', true, i + '_' + j + '_' + k) + '</td></tr></table></p>'
            }
            htmlstr += '<p>' + genButton('视频', 'template.data.teachingTalk.video', false, i + '_' + j) + '&emsp;&emsp;' + genButton('音频', 'template.data.teachingTalk.audio', false, i + '_' + j) + '<p>'
            htmlstr += '</td></tr></table></p>'

            htmlstr += '<p>教学提示:&emsp;' + genText('template.data.desc', data.desc, i + '_' + j, true) + '</p>'

            htmlstr += '</td><td class="td_width"><p>' + genButton('', 'template.data', true, i + '_' + j) + '</p></td></tr>'
            htmlstr += '</table></p>'
        }

        htmlstr += '<p class="td_width">' + genButton('', 'template.data', false, i) + '</p>'
        //操作按钮
        htmlstr += '</td><td class="td_width"><p>' + genButton('', 'template', true, i) + '</p></td></tr>'
    }

    htmlstr += '<tr><td class="td_width">' + genButton('', 'template', false, 0) + '</td><td></td><td class="td_width"></td></tr>'

    $("#table_scheam").html(htmlstr)

    gen()
}

function genText(mode, value, index, big) {
    index = index || 0
    big = big || false
    return '<input type="text" style="' + (big ? 'width:80%;' : '') + '" mode="' + mode + '" index="' + index + '" value="' + value + '" onchange="changeText(this)" />'
}

function genButton(flag, mode, checked, index) {
    index = index || 0
    return '<input type="button" style="width:60px;' + (checked ? 'background-color: #999999;' : '') + '" value="' + (checked ? '删除' : '添加') + flag + '" mode="' + mode + '" index="' + index + '" onclick="' + (checked ? 'delSchema(this)' : 'addSchema(this)') + '"/>'
}

function delSchema(e) {
    const dom = $(e)
    const mode = dom.attr("mode")
    const index = dom.attr("index")
    if (mode == 'template') {
        template.splice(index, 1)
    } else if (mode == 'template.data') {
        const positions = index.split("_")
        template[positions[0]].data.splice(positions[1], 1)
    } else if (mode == 'template.data.accompany') {
        const positions = index.split("_")
        template[positions[0]].data[positions[1]].accompany = null
    } else if (mode == 'template.data.linkTalk') {
        const positions = index.split("_")
        template[positions[0]].data[positions[1]].linkTalk.splice(positions[2], 1)
    } else if (mode == 'template.data.teachingTalk') {
        const positions = index.split("_")
        template[positions[0]].data[positions[1]].teachingTalk.splice(positions[2], 1)
    }
    initTemplate()
}

function addSchema(e) {
    const dom = $(e)
    const mode = dom.attr("mode")
    const index = dom.attr("index")
    if (mode == 'template') {
        template.push(JSON.parse(item_str))
    } else if (mode == 'template.data') {
        const positions = index.split("_")
        template[positions[0]].data.push(JSON.parse(data_item_str))
    } else if (mode == 'template.data.accompany') {
        const positions = index.split("_")
        template[positions[0]].data[positions[1]].accompany = JSON.parse(audio_str)
    } else if (mode == 'template.data.linkTalk.audio') {
        const positions = index.split("_")
        template[positions[0]].data[positions[1]].linkTalk.push(JSON.parse(audio_str))
    } else if (mode == 'template.data.linkTalk.video') {
        const positions = index.split("_")
        template[positions[0]].data[positions[1]].linkTalk.push(JSON.parse(video_str))
    } else if (mode == 'template.data.teachingTalk.audio') {
        const positions = index.split("_")
        template[positions[0]].data[positions[1]].teachingTalk.push(JSON.parse(audio_str))
    } else if (mode == 'template.data.teachingTalk.video') {
        const positions = index.split("_")
        template[positions[0]].data[positions[1]].teachingTalk.push(JSON.parse(video_str))
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
    } else if ('template.data.subtitle' == mode) {
        const positions = index.split("_")
        template[positions[0]].data[positions[1]].subtitle = value
    } else if ('template.data.linkFlow' == mode) {
        const positions = index.split("_")
        template[positions[0]].data[positions[1]].linkFlow = value
    } else if ('template.data.accompany.src' == mode) {
        const positions = index.split("_")
        template[positions[0]].data[positions[1]].accompany.src = value
    } else if ('template.data.accompany.duration' == mode) {
        const positions = index.split("_")
        template[positions[0]].data[positions[1]].accompany.duration = Number(value)
    } else if ('template.data.linkTalk.src' == mode) {
        const positions = index.split("_")
        template[positions[0]].data[positions[1]].linkTalk[positions[2]].src = value
    } else if ('template.data.linkTalk.duration' == mode) {
        const positions = index.split("_")
        template[positions[0]].data[positions[1]].linkTalk[positions[2]].duration = Number(value)
    } else if ('template.data.teachingTalk.src' == mode) {
        const positions = index.split("_")
        template[positions[0]].data[positions[1]].teachingTalk[positions[2]].src = value
    } else if ('template.data.teachingTalk.duration' == mode) {
        const positions = index.split("_")
        template[positions[0]].data[positions[1]].teachingTalk[positions[2]].duration = Number(value)
    } else if ('template.data.desc' == mode) {
        const positions = index.split("_")
        template[positions[0]].data[positions[1]].desc = value
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