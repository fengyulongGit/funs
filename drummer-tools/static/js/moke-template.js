const template_str = '{"image":"resources/","description":{"knowledge":{"label":"10~15分钟","content":""},"skillExample":{"label":"10~20分钟","content":""},"skillExplain":{"label":"10~15分钟","content":""},"chapterExercise":{"label":"10~20分钟","content":""},"classOver":{"label":"10分钟","content":""}}}'

let template = JSON.parse(template_str)

function initTemplate() {

    let htmlstr = ''
    htmlstr += '<tr><td class="td_width">图片</td><td>' + genText('template.image', template.image, 80) + '</td></tr>'
    htmlstr += '<tr><td class="td_width">课程介绍</td><td><table>'
    htmlstr += '<tr><td class="td_width">知识篇</td><td><p>标签：' + genText('template.description.knowledge.label', template.description.knowledge.label, 80) + '</p>'
    htmlstr += '<p>内容:&emsp;<span style="color: #D12F19;">(支持回车换行)</span>' + genTextarea('template.description.knowledge.content', template.description.knowledge.content) + '</p>'
    htmlstr += '</td></tr>'
    htmlstr += '<tr><td class="td_width">技巧示范</td><td><p>标签：' + genText('template.description.skillExample.label', template.description.skillExample.label, 80) + '</p>'
    htmlstr += '<p>内容:&emsp;<span style="color: #D12F19;">(支持回车换行)</span>' + genTextarea('template.description.skillExample.content', template.description.skillExample.content) + '</p>'
    htmlstr += '</td></tr>'
    htmlstr += '<tr><td class="td_width">技巧讲解</td><td><p>标签：' + genText('template.description.skillExplain.label', template.description.skillExplain.label, 80) + '</p>'
    htmlstr += '<p>内容:&emsp;<span style="color: #D12F19;">(支持回车换行)</span>' + genTextarea('template.description.skillExplain.content', template.description.skillExplain.content) + '</p>'
    htmlstr += '</td></tr>'
    htmlstr += '<tr><td class="td_width">曲目练习</td><td><p>标签：' + genText('template.description.chapterExercise.label', template.description.chapterExercise.label, 80) + '</p>'
    htmlstr += '<p>内容:&emsp;<span style="color: #D12F19;">(支持回车换行)</span>' + genTextarea('template.description.chapterExercise.content', template.description.chapterExercise.content) + '</p>'
    htmlstr += '</td></tr>'
    htmlstr += '<tr><td class="td_width">下课</td><td><p>标签：' + genText('template.description.classOver.label', template.description.classOver.label, 80) + '</p>'
    htmlstr += '<p>内容:&emsp;<span style="color: #D12F19;">(支持回车换行)</span>' + genTextarea('template.description.classOver.content', template.description.classOver.content) + '</p>'
    htmlstr += '</td></tr>'
    htmlstr += '</table></td></tr>'

    $("#table_scheam").html(htmlstr)

    gen()
}

function genText(mode, value, width) {
    width = width || 0
    return '<input type="text" id="text_' + (mode).replace(/\./g, '_') + '" style="' + (width > 0 ? 'width:' + width + '%;' : '') + '" mode="' + mode + '" value="' + value + '" onchange="changeText(this)" />'
}

function genTextarea(mode, value) {
    return '<textarea style="width: 100%;height: 100px;" mode="' + mode + '" onchange="changeText(this)" >' + value + '</textarea>'
}

function changeText(e) {
    const dom = $(e)
    const mode = dom.attr("mode")

    let value = dom.val()
    if (mode.indexOf('image') >= 0) {
        const default_dir = $("#default_dir").val()
        value = value.replace(default_dir, '')
    }
    if ('template.image' == mode) {
        template.image = value
    } else if ('template.description.knowledge.label' == mode) {
        template.description.knowledge.label = value
    } else if ('template.description.knowledge.content' == mode) {
        template.description.knowledge.content = value
    } else if ('template.description.skillExample.label' == mode) {
        template.description.skillExample.label = value
    } else if ('template.description.skillExample.content' == mode) {
        template.description.skillExample.content = value
    } else if ('template.description.skillExplain.label' == mode) {
        template.description.skillExplain.label = value
    } else if ('template.description.skillExplain.content' == mode) {
        template.description.skillExplain.content = value
    } else if ('template.description.chapterExercise.label' == mode) {
        template.description.chapterExercise.label = value
    } else if ('template.description.chapterExercise.content' == mode) {
        template.description.chapterExercise.content = value
    } else if ('template.description.classOver.label' == mode) {
        template.description.classOver.label = value
    } else if ('template.description.classOver.content' == mode) {
        template.description.classOver.content = value
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