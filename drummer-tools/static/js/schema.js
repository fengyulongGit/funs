function initSchemaView() {
    const default_dir = $("#default_dir").val()
    let htmlstr = ''

    const scale = 1701 * 0.3 / template.size.width

    htmlstr += '<div class="scale_origin_left_top" style="width: ' + template.size.width + 'px;height: ' + template.size.height + 'px;transform: scale(' + scale + ',' + scale + ');">'

    // childs
    const has_childs = template.childs && template.childs.length > 0
    if (has_childs) {
        for (let i in template.childs) {
            const child = template.childs[i]
            if (child.class == "Image") {
                htmlstr += '<img class="schema_item_absolute" src="file://' + default_dir + child.src + '" style="' + schema_filter.image(child) + '"/>'
            } else if (child.class == 'Video') {
                htmlstr += '<div class="schema_item_absolute" style="' + schema_filter.image(child) + ';background-color: red;opacity: 0.2;" onclick="openMedia(\'' + child.src + '\')";>Video</div>'
            } else if (child.class == 'Audio') {
                htmlstr += '<div class="schema_item_absolute" style="' + schema_filter.image(child) + ';background-color: blue;opacity: 0.2;" onclick="openMedia(\'' + child.src + '\')";>Audio</div>'
            }
        }
    }

    htmlstr += '</div>'

    // console.log(htmlstr)

    $("#div_schema").width(template.size.width * scale).height(template.size.height * scale)
    $("#div_schema").html(htmlstr)
}

function openMedia(src) {
    var url = (src || '').trim()
    if (url) {
        const default_dir = $("#default_dir").val()
        window.open('file://' + default_dir + url, "_blank")
    }
}

let schema_filter = {
    image: function (image) {
        return 'width:' + image.size.width + 'px; ' +
            'height:' + image.size.height + 'px; ' +
            'left:' + image.position.x + 'px; ' +
            'top:' + image.position.y + 'px;'
    },
}