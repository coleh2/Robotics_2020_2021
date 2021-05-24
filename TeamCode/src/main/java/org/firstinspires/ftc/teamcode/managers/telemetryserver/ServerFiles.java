package org.firstinspires.ftc.teamcode.managers.telemetryserver;

public class ServerFiles {
    public static final String indexDotHtml = "<!DOCTYPE html>\n" +
            "<html>\n" +
            "\n" +
            "<head>\n" +
            "    <title>Dashboard</title>\n" +
            "    <meta charset=\"utf-8\">\n" +
            "    <script>\n" +
            "        var mouseX = 0, mouseY = 0, gridSize = Math.max(window.innerHeight, window.innerWidth) / 16, widgetsParent = undefined;\n" +
            "\n" +
            "        var widgetsReg = {};\n" +
            "\n" +
            "        var fields = [];\n" +
            "\n" +
            "        var widgetTypes = {\n" +
            "            \"none\": {\n" +
            "                init: function (parent) { parent.innerHTML = \"\"; },\n" +
            "                ondata: function (data, parent) { }\n" +
            "            },\n" +
            "            \"meta.theme\": {\n" +
            "                init: function (parent) {\n" +
            "                    var select = document.createElement(\"select\");\n" +
            "                    var themes = [\"dark\", \"light\"];\n" +
            "                    var current = localStorage.getItem(\"theme\");\n" +
            "\n" +
            "                    for (var i = 0; i < themes.length; i++) {\n" +
            "                        var opt = document.createElement(\"option\");\n" +
            "                        if (themes[i] == current) opt.selected = true;\n" +
            "                        opt.textContent = themes[i];\n" +
            "                        select.appendChild(opt);\n" +
            "                    }\n" +
            "\n" +
            "                    select.addEventListener(\"change\", function () {\n" +
            "                        localStorage.setItem(\"theme\", themes[select.selectedIndex]);\n" +
            "                        document.body.classList.remove(\"dark-theme\");\n" +
            "                        document.body.classList.remove(\"light-theme\");\n" +
            "                        document.body.classList.add(themes[select.selectedIndex] + \"-theme\");\n" +
            "                    });\n" +
            "\n" +
            "                    parent.appendChild(select);\n" +
            "                },\n" +
            "                ondata: function (data, parent) { }\n" +
            "            },\n" +
            "            \"debug.alldata\": {\n" +
            "                init: function (parent, state) {\n" +
            "                    var pre = document.createElement(\"pre\");\n" +
            "                    state.pre = pre;\n" +
            "                    parent.appendChild(pre);\n" +
            "                },\n" +
            "                ondata: function (data, parent, state) {\n" +
            "                    var scrolledToBottom = parent.scrollHeight - Math.abs(parent.scrollTop) === parent.clientHeight;\n" +
            "\n" +
            "                    //override for when the scroll changes on hover\n" +
            "                    if (state.lastScrolledToBottom && state.lastScroll == parent.scrollTop) scrolledToBottom = true;\n" +
            "\n" +
            "                    state.pre.appendChild(document.createTextNode(JSON.stringify(data) + \"\\n\"));\n" +
            "\n" +
            "                    if (scrolledToBottom) parent.scrollTop = parent.scrollHeight - parent.clientHeight;\n" +
            "\n" +
            "                    state.lastScroll = parent.scrollTop;\n" +
            "                    state.lastScrolledToBottom = scrolledToBottom;\n" +
            "                }\n" +
            "            },\n" +
            "            \"chart.bar\": {\n" +
            "                init: function (parent) { },\n" +
            "                ondata: function (data, parent) { }\n" +
            "            },\n" +
            "            \"chart.line\": {\n" +
            "                init: function (parent, state, config, box) {\n" +
            "                    //use object.assign to avoid mutation of original box\n" +
            "                    state.box = {};\n" +
            "                    Object.assign(state.box, box);\n" +
            "\n" +
            "                    state.datapoints = [];\n" +
            "\n" +
            "                    var svg = document.createElementNS(\"http://www.w3.org/2000/svg\", \"svg\");\n" +
            "                    svg.classList.add(\"widget--line-chart\");\n" +
            "\n" +
            "                    svg.setAttribute(\"height\", box.height * gridSize);\n" +
            "                    svg.setAttribute(\"width\", box.width * gridSize);\n" +
            "\n" +
            "                    //scale up for a crisper view\n" +
            "                    state.box.height *= 2 * gridSize;\n" +
            "                    state.box.width *= 2 * gridSize;\n" +
            "\n" +
            "                    svg.setAttribute(\"viewBox\", `0 0 ${state.box.width} ${state.box.height}`);\n" +
            "                    svg.setAttribute(\"preserveAspectRatio\", \"xMidYMid meet\");\n" +
            "                    svg.setAttribute(\"xmlns\", \"http://www.w3.org/2000/svg\");\n" +
            "\n" +
            "                    var path = document.createElementNS(\"http://www.w3.org/2000/svg\", \"path\");\n" +
            "                    path.classList.add(\"widget--line-chart--stroke-line\");\n" +
            "\n" +
            "                    var fillPath = document.createElementNS(\"http://www.w3.org/2000/svg\", \"path\");\n" +
            "                    fillPath.classList.add(\"widget--line-chart--fill-line\");\n" +
            "\n" +
            "                    //caption lines\n" +
            "                    for (var i = 0; i <= 4; i++) {\n" +
            "                        var captionLine = document.createElementNS(\"http://www.w3.org/2000/svg\", \"path\");\n" +
            "                        captionLine.classList.add(\"widget--line-chart--caption-line\");\n" +
            "                        captionLine.setAttribute(\"d\", \"M0,\" + (state.box.height * i / 4) + \"H\" + state.box.width);\n" +
            "                        svg.appendChild(captionLine);\n" +
            "                    }\n" +
            "\n" +
            "                    state.captions = [];\n" +
            "                    //captions themselves. They go after so that they'll overlay the lines.\n" +
            "                    for (var i = 0; i <= 4; i++) {\n" +
            "                        //use IEFE so that `i` is preserved in each\n" +
            "                        (function (i) {\n" +
            "                            var caption = document.createElementNS(\"http://www.w3.org/2000/svg\", \"text\");\n" +
            "                            caption.classList.add(\"widget--line-chart--caption\");\n" +
            "                            caption.setAttribute(\"x\", 0);\n" +
            "                            caption.setAttribute(\"y\", (state.box.height * i / 4) - 6);\n" +
            "\n" +
            "\n" +
            "                            state.captions.push(function (max) {\n" +
            "                                caption.textContent = (max * (1 - i / 4)).toString().substring(0, 5);\n" +
            "                            });\n" +
            "\n" +
            "                            svg.appendChild(caption);\n" +
            "                        })(i);\n" +
            "                    }\n" +
            "\n" +
            "                    svg.appendChild(fillPath);\n" +
            "                    svg.appendChild(path);\n" +
            "\n" +
            "\n" +
            "                    state.path = path;\n" +
            "                    state.fill = fillPath;\n" +
            "\n" +
            "                    parent.appendChild(svg);\n" +
            "                    parent.classList.add(\"nopadding\");\n" +
            "                    parent.classList.add(\"nooverflow\");\n" +
            "                },\n" +
            "                ondata: function (data, parent, state, config) {\n" +
            "                    state.datapoints.push({\n" +
            "                        x: Date.now(),\n" +
            "                        y: data.fields[config.field]\n" +
            "                    });\n" +
            "\n" +
            "                    var line = drawLine(state.datapoints, state.box, config.xScaleSeconds * 1000);\n" +
            "\n" +
            "                    var max = state.datapoints[0].y;\n" +
            "                    for (var i = 0; i < state.datapoints.length; i++) max = Math.max(max, state.datapoints[i].y);\n" +
            "\n" +
            "                    max = Math.round(max * 1000) / 1000;\n" +
            "\n" +
            "                    for (var i = 0; i < state.captions.length; i++) {\n" +
            "                        state.captions[i](max);\n" +
            "                    }\n" +
            "\n" +
            "                    state.path.setAttribute(\"d\", line);\n" +
            "                    state.fill.setAttribute(\"d\", line + \"L\" + state.box.width + \",\" + state.box.height + \"L\" + (0) + \",\" + (state.box.height) + \"Z\");\n" +
            "                },\n" +
            "                config: [\n" +
            "                    {\n" +
            "                        name: \"field\",\n" +
            "                        type: \"select\",\n" +
            "                        value: \"field\"\n" +
            "                    },\n" +
            "                    {\n" +
            "                        name: \"xScaleSeconds\",\n" +
            "                        type: \"number\",\n" +
            "                        default: 5\n" +
            "                    }\n" +
            "                ]\n" +
            "            },\n" +
            "            \"chart.pie\": {\n" +
            "                init: function (parent) { },\n" +
            "                ondata: function (data, parent) { }\n" +
            "            },\n" +
            "            \"number.single\": {\n" +
            "                init: function (parent) { },\n" +
            "                ondata: function (data, parent) { }\n" +
            "            },\n" +
            "            \"number.change\": {\n" +
            "                init: function (parent) { },\n" +
            "                ondata: function (data, parent) { }\n" +
            "            },\n" +
            "            \"string.singleline\": {\n" +
            "                init: function (parent) { },\n" +
            "                ondata: function (data, parent) { }\n" +
            "            },\n" +
            "            \"string.long\": {\n" +
            "                init: function (parent) { },\n" +
            "                ondata: function (data, parent) { }\n" +
            "            },\n" +
            "            \"log.autoscroll\": {\n" +
            "                init: function (parent, state) {\n" +
            "                    var pre = document.createElement(\"pre\");\n" +
            "                    state.pre = pre;\n" +
            "                    parent.appendChild(pre);\n" +
            "                },\n" +
            "                ondata: function (data, parent, state) {\n" +
            "                    var scrolledToBottom = parent.scrollHeight - Math.abs(parent.scrollTop) === parent.clientHeight;\n" +
            "\n" +
            "                    //override for when the scroll changes on hover\n" +
            "                    if (state.lastScrolledToBottom && state.lastScroll == parent.scrollTop) scrolledToBottom = true;\n" +
            "\n" +
            "                    state.pre.appendChild(document.createTextNode(data.log + \"\\n\"));\n" +
            "\n" +
            "                    if (scrolledToBottom) parent.scrollTop = parent.scrollHeight - parent.clientHeight;\n" +
            "\n" +
            "                    state.lastScroll = parent.scrollTop;\n" +
            "                    state.lastScrolledToBottom = scrolledToBottom;\n" +
            "                }\n" +
            "            },\n" +
            "            \"log.noautoscroll\": {\n" +
            "                init: function (parent, state) {\n" +
            "                    var pre = document.createElement(\"pre\");\n" +
            "                    state.pre = pre;\n" +
            "                    parent.appendChild(pre);\n" +
            "                },\n" +
            "                ondata: function (data, parent, state) {\n" +
            "                    state.pre.appendChild(document.createTextNode(data.log + \"\\n\"));\n" +
            "                }\n" +
            "            },\n" +
            "            \"connection.serverlagtime\": {\n" +
            "                init: function (parent, state) {\n" +
            "                    state.num = document.createElement(\"h3\");\n" +
            "                    var deltaParent = document.createElement(\"span\");\n" +
            "\n" +
            "                    state.deltaIcon = document.createElement(\"span\");\n" +
            "                    state.delta = document.createElement(\"span\");\n" +
            "\n" +
            "                    deltaParent.appendChild(state.deltaIcon);\n" +
            "                    deltaParent.appendChild(state.delta);\n" +
            "\n" +
            "                    parent.appendChild(state.num);\n" +
            "                    parent.appendChild(deltaParent);\n" +
            "                },\n" +
            "                ondata: function (data, parent, state) {\n" +
            "                    var connectionLag = Date.now() - data.time;\n" +
            "\n" +
            "                    if (state.lastConnectionLag === undefined) state.lastConnectionLag = connectionLag;\n" +
            "\n" +
            "                    state.num.textContent = connectionLag + \"ms\";\n" +
            "\n" +
            "                    var deltaLag = ((connectionLag - state.lastConnectionLag) / state.lastConnectionLag) * 100;\n" +
            "                    if (deltaLag > 0) state.deltaIcon.textContent = \"+\";\n" +
            "                    else state.deltaIcon.textContent = \"\";\n" +
            "                    state.delta.textContent = Math.round(deltaLag) + \"%\";\n" +
            "\n" +
            "                    state.lastConnectionLag = connectionLag;\n" +
            "                }\n" +
            "            },\n" +
            "            \"robot.motors\": {\n" +
            "                init: function (parent) { },\n" +
            "                ondata: function (data, parent) { }\n" +
            "            },\n" +
            "            \"robot.motor\": {\n" +
            "                init: function (parent) { },\n" +
            "                ondata: function (data, parent) { }\n" +
            "            },\n" +
            "            \"sensor.color\": {\n" +
            "                init: function (parent) { },\n" +
            "                ondata: function (data, parent) { }\n" +
            "            },\n" +
            "            \"autoauto.stategraph\": {\n" +
            "                init: function (parent) { },\n" +
            "                ondata: function (data, parent) { }\n" +
            "            },\n" +
            "            \"autoauto.variables\": {\n" +
            "                init: function (parent) { },\n" +
            "                ondata: function (data, parent) { }\n" +
            "            },\n" +
            "            \"autoauto.currentstate\": {\n" +
            "                init: function (parent) { },\n" +
            "                ondata: function (data, parent) { }\n" +
            "            },\n" +
            "            \"input.gamepad1\": {\n" +
            "                init: function (parent) { },\n" +
            "                ondata: function (data, parent) { }\n" +
            "            },\n" +
            "            \"input.gamepad2\": {\n" +
            "                init: function (parent) { },\n" +
            "                ondata: function (data, parent) { }\n" +
            "            }\n" +
            "        }\n" +
            "\n" +
            "        window.addEventListener(\"load\", function () {\n" +
            "\n" +
            "            document.body.style.setProperty(\"--grid-size\", gridSize + \"px\");\n" +
            "\n" +
            "            var boxTotal = Math.ceil((window.innerWidth / gridSize) * (window.innerHeight / gridSize));\n" +
            "\n" +
            "            var draggedWidget = undefined;\n" +
            "\n" +
            "            var gridBackground = document.getElementById(\"grid-background\");\n" +
            "\n" +
            "            widgetsParent = document.getElementById(\"grid-widgets\");\n" +
            "\n" +
            "            for (var i = 0; i < boxTotal; i++) {\n" +
            "                gridBackground.appendChild(document.createElement(\"span\"))\n" +
            "            }\n" +
            "\n" +
            "            document.addEventListener(\"dragstart\", function (e) {\n" +
            "                e.preventDefault();\n" +
            "            });\n" +
            "\n" +
            "            var downX, downY, dragging, dragShadow;\n" +
            "            document.addEventListener(\"mousedown\", function (event) {\n" +
            "                if (draggedWidget == undefined) {\n" +
            "                    downX = mouseX, downY = mouseY;\n" +
            "                    dragging = true;\n" +
            "                }\n" +
            "            });\n" +
            "            document.addEventListener(\"mousemove\", function (event) {\n" +
            "                if (dragging) {\n" +
            "                    event.preventDefault();\n" +
            "                    if (!dragShadow) dragShadow = makeDragShadow();\n" +
            "\n" +
            "                    var x = Math.min(mouseX, downX) + 1;\n" +
            "                    var y = Math.min(mouseY, downY) + 1;\n" +
            "                    var width = Math.max(Math.abs(mouseX - downX) + 1, 0);\n" +
            "                    var height = Math.max(Math.abs(mouseY - downY) + 1, 0);\n" +
            "\n" +
            "                    dragShadow.style.gridColumn = (x) + \" / \" + (x + width);\n" +
            "                    dragShadow.style.gridRow = (y) + \" / \" + (y + height);\n" +
            "                }\n" +
            "            });\n" +
            "            document.addEventListener(\"mouseup\", function () {\n" +
            "                if (dragShadow && dragShadow.parentElement) {\n" +
            "                    dragShadow.parentElement.removeChild(dragShadow);\n" +
            "                    dragShadow = undefined;\n" +
            "                }\n" +
            "                if (dragging) {\n" +
            "                    var x = Math.min(mouseX, downX) + 1;\n" +
            "                    var y = Math.min(mouseY, downY) + 1;\n" +
            "                    var width = Math.max(Math.abs(mouseX - downX) + 1, 0);\n" +
            "                    var height = Math.max(Math.abs(mouseY - downY) + 1, 0);\n" +
            "\n" +
            "                    createWidget(x, y, width, height);\n" +
            "                }\n" +
            "                dragging = false;\n" +
            "                draggedWidget = undefined;\n" +
            "            });\n" +
            "\n" +
            "            startDataRequest();\n" +
            "        });\n" +
            "\n" +
            "        window.addEventListener(\"mousemove\", function (event) {\n" +
            "            mouseX = Math.floor((event.clientX) / gridSize);\n" +
            "            mouseY = Math.floor(event.clientY / gridSize);\n" +
            "        });\n" +
            "\n" +
            "        function createWidget(x, y, width, height) {\n" +
            "            var id = widgetsReg.__counter || 0;\n" +
            "            widgetsReg.__counter = id + 1;\n" +
            "\n" +
            "            var widget = document.createElement(\"div\");\n" +
            "            widget.classList.add(\"widget\");\n" +
            "\n" +
            "            widget.style.gridColumn = (x) + \" / \" + (x + width);\n" +
            "            widget.style.gridRow = (y) + \" / \" + (y + height);\n" +
            "\n" +
            "            var widgetInner = document.createElement(\"div\");\n" +
            "            widgetInner.classList.add(\"widget--inner\");\n" +
            "\n" +
            "            var selfWidget = {\n" +
            "                id: id,\n" +
            "                widget: widget,\n" +
            "                widgetInner: widgetInner,\n" +
            "                toolbar: createWidgetToolbar(id, widgetInner),\n" +
            "                content: createWidgetContent(id, widgetInner),\n" +
            "                type: \"none\",\n" +
            "                box: {\n" +
            "                    x: x,\n" +
            "                    y: y,\n" +
            "                    height: height,\n" +
            "                    width: width\n" +
            "                }\n" +
            "            };\n" +
            "\n" +
            "            widgetsReg[id] = selfWidget;\n" +
            "\n" +
            "            (function dragging() {\n" +
            "                var pointerEvents = [\"click\", \"dblclick\", \"mousedown\"];\n" +
            "\n" +
            "                for (var i = 0; i < pointerEvents.length; i++) {\n" +
            "                    widget.addEventListener(pointerEvents[i], function (e) {\n" +
            "                        e.stopPropagation();\n" +
            "                    });\n" +
            "                }\n" +
            "\n" +
            "                var downX, downY, dragging, dragShadow;\n" +
            "\n" +
            "                widget.addEventListener(\"contextmenu\", function (event) {\n" +
            "                    event.preventDefault();\n" +
            "                    openSidebar(id);\n" +
            "                });\n" +
            "\n" +
            "                widget.addEventListener(\"mousedown\", function (event) {\n" +
            "                    if (event.ctrlKey) {\n" +
            "                        event.preventDefault();\n" +
            "                        var widgetCopy = createWidget(x, y, width, height);\n" +
            "\n" +
            "                        widgetCopy.type = selfWidget.type;\n" +
            "                        widgetCopy.config = JSON.parse(JSON.stringify(selfWidget.config));\n" +
            "                        widgetCopy.state = {};\n" +
            "\n" +
            "                        widgetTypes[widgetCopy.type].init(widgetCopy.content, widgetCopy.state, widgetCopy.config, widgetCopy.box);\n" +
            "                    }\n" +
            "                    downX = mouseX, downY = mouseY;\n" +
            "                    dragging = true;\n" +
            "                    draggedWidget = widget;\n" +
            "                });\n" +
            "                document.addEventListener(\"mousemove\", function (event) {\n" +
            "                    if (dragging) {\n" +
            "                        event.preventDefault();\n" +
            "                        if (!dragShadow) dragShadow = makeDragShadow();\n" +
            "\n" +
            "                        var shadowX = x + (mouseX - downX);\n" +
            "                        var shadowY = y + (mouseY - downY);\n" +
            "\n" +
            "                        dragShadow.style.gridColumn = shadowX + \" / \" + (shadowX + width);\n" +
            "                        dragShadow.style.gridRow = shadowY + \" / \" + (shadowY + height);\n" +
            "                    }\n" +
            "                });\n" +
            "                function stopDrag(event) {\n" +
            "                    if (dragShadow && dragShadow.parentElement) {\n" +
            "                        dragShadow.parentElement.removeChild(dragShadow);\n" +
            "                        dragShadow = undefined;\n" +
            "                    }\n" +
            "\n" +
            "                    if (dragging && (mouseX - downX != 0 || mouseY - downY != 0)) {\n" +
            "                        x += (mouseX - downX);\n" +
            "                        y += (mouseY - downY);\n" +
            "\n" +
            "                        y = Math.max(y, 0);\n" +
            "                        x = Math.max(x, 0);\n" +
            "\n" +
            "                        widget.style.gridColumn = (x) + \" / \" + (x + width);\n" +
            "                        widget.style.gridRow = (y) + \" / \" + (y + height);\n" +
            "                    }\n" +
            "                    dragging = false;\n" +
            "                }\n" +
            "                document.addEventListener(\"mouseup\", stopDrag);\n" +
            "                widget.addEventListener(\"mouseup\", stopDrag);\n" +
            "            })();\n" +
            "\n" +
            "            widget.appendChild(widgetInner);\n" +
            "\n" +
            "            widgetsParent.appendChild(widget)\n" +
            "\n" +
            "            return selfWidget;\n" +
            "        }\n" +
            "        function createWidgetToolbar(id, parent) {\n" +
            "            var toolbar = document.createElement(\"nav\");\n" +
            "            toolbar.classList.add(\"widget--toolbar\");\n" +
            "\n" +
            "            var settingsButton = document.createElement(\"button\");\n" +
            "            settingsButton.setAttribute(\"aria-label\", \"Settings\");\n" +
            "            settingsButton.classList.add(\"widget--toolbar--settings-button\")\n" +
            "            settingsButton.addEventListener(\"click\", function () {\n" +
            "                openSidebar(id);\n" +
            "            });\n" +
            "            toolbar.appendChild(settingsButton);\n" +
            "\n" +
            "            var deleteButton = document.createElement(\"button\");\n" +
            "            deleteButton.setAttribute(\"aria-label\", \"Remove Widget\");\n" +
            "            deleteButton.classList.add(\"widget--toolbar--delete-button\")\n" +
            "            deleteButton.addEventListener(\"click\", function () {\n" +
            "                var widgetParent = widgetsReg[id].widget;\n" +
            "                if (widgetParent.parentElement) {\n" +
            "                    widgetParent.parentElement.removeChild(widgetParent);\n" +
            "                    delete widgetsReg[id];\n" +
            "                }\n" +
            "            });\n" +
            "\n" +
            "            toolbar.appendChild(deleteButton);\n" +
            "            parent.appendChild(toolbar);\n" +
            "\n" +
            "            return toolbar;\n" +
            "        }\n" +
            "        function createWidgetContent(id, parent) {\n" +
            "            var content = document.createElement(\"div\");\n" +
            "            content.classList.add(\"widget--content\");\n" +
            "            parent.appendChild(content);\n" +
            "            return content;\n" +
            "        }\n" +
            "        function makeDragShadow() {\n" +
            "            var shadow = document.createElement(\"div\");\n" +
            "            shadow.classList.add(\"drag-shadow\");\n" +
            "            document.getElementById(\"grid-ui\").appendChild(shadow);\n" +
            "            return shadow;\n" +
            "        }\n" +
            "\n" +
            "        var openSidebar, closeSidebar;\n" +
            "        (function sidebar() {\n" +
            "            var sidebarInited = false, sidebarFocusedWidgetId, sidebarOpen;\n" +
            "            openSidebar = function openSidebar(id) {\n" +
            "                if (!sidebarInited) initSidebar();\n" +
            "                if (sidebarOpen) closeSidebar();\n" +
            "\n" +
            "                sidebarFocusedWidgetId = id;\n" +
            "\n" +
            "                var sidebarParent = document.getElementById(\"config-sidebar-parent\");\n" +
            "                var sidebar = document.getElementById(\"config-sidebar\");\n" +
            "\n" +
            "                sidebar.innerHTML = \"\";\n" +
            "                populateSidebar(id, sidebar);\n" +
            "\n" +
            "                sidebarParent.style.display = \"block\";\n" +
            "                sidebar.setAttribute(\"open\", \"true\");\n" +
            "                sidebar.focus();\n" +
            "\n" +
            "                sidebarOpen = true;\n" +
            "            }\n" +
            "            closeSidebar = function closeSidebar() {\n" +
            "                if (!sidebarInited) initSidebar();\n" +
            "\n" +
            "                if (!sidebarOpen) return false;\n" +
            "\n" +
            "                var sidebarParent = document.getElementById(\"config-sidebar-parent\");\n" +
            "                var sidebar = document.getElementById(\"config-sidebar\");\n" +
            "                sidebarParent.style.display = \"none\";\n" +
            "                sidebar.removeAttribute(\"open\");\n" +
            "\n" +
            "                sidebarOpen = false;\n" +
            "\n" +
            "                if (sidebarFocusedWidgetId && widgetsReg[sidebarFocusedWidgetId]) widgetsReg[sidebarFocusedWidgetId].widget.focus();\n" +
            "                sidebarFocusedWidgetId = undefined;\n" +
            "            }\n" +
            "            function initSidebar() {\n" +
            "                var sidebarParent = document.getElementById(\"config-sidebar-parent\");\n" +
            "\n" +
            "                var pointerEvents = [\"click\", \"dblclick\", \"mousedown\"];\n" +
            "\n" +
            "                for (var i = 0; i < pointerEvents.length; i++) {\n" +
            "                    sidebarParent.addEventListener(pointerEvents[i], function (e) {\n" +
            "                        e.stopPropagation();\n" +
            "                    });\n" +
            "                }\n" +
            "\n" +
            "                sidebarParent.addEventListener(\"click\", function (e) {\n" +
            "                    if (e.target == sidebarParent) closeSidebar();\n" +
            "                });\n" +
            "\n" +
            "                document.addEventListener(\"keyup\", function escapeListener(e) {\n" +
            "                    if (e.which == 27 || e.key == \"Escape\" || e.code == \"Escape\") closeSidebar();\n" +
            "                });\n" +
            "\n" +
            "                sidebarInited = true;\n" +
            "\n" +
            "            }\n" +
            "\n" +
            "            function populateSidebar(id, sidebar) {\n" +
            "\n" +
            "                var typeSelect = document.createElement(\"select\");\n" +
            "                var settings = document.createElement(\"fieldset\");\n" +
            "                settings.classList.add(\"sidebar--config-fields\");\n" +
            "\n" +
            "                var keys = Object.keys(widgetTypes);\n" +
            "                for (var i = 0; i < keys.length; i++) {\n" +
            "                    var opt = document.createElement(\"option\");\n" +
            "                    if (keys[i] == widgetsReg[id].type) opt.selected = true;\n" +
            "                    opt.textContent = keys[i];\n" +
            "                    typeSelect.appendChild(opt);\n" +
            "                }\n" +
            "                typeSelect.addEventListener(\"change\", function () {\n" +
            "                    widgetsReg[id].type = keys[typeSelect.selectedIndex] || \"none\";\n" +
            "                    widgetsReg[id].state = {};\n" +
            "                    widgetsReg[id].config = {};\n" +
            "                    widgetsReg[id].content.innerHTML = \"\";\n" +
            "                    widgetsReg[id].content.className = \"widget--content\";\n" +
            "                    widgetTypes[widgetsReg[id].type].init(widgetsReg[id].content, widgetsReg[id].state, widgetsReg[id].config, widgetsReg[id].box);\n" +
            "\n" +
            "                    populateSidebarConfig(id, settings);\n" +
            "                });\n" +
            "                var label = document.createElement(\"label\");\n" +
            "                label.textContent = \"Widget Type: \";\n" +
            "                label.appendChild(typeSelect)\n" +
            "                sidebar.appendChild(label);\n" +
            "\n" +
            "                sidebar.appendChild(document.createElement(\"hr\"));\n" +
            "\n" +
            "\n" +
            "                populateSidebarConfig(id, settings);\n" +
            "                sidebar.appendChild(settings);\n" +
            "            }\n" +
            "\n" +
            "            function populateSidebarConfig(id, sidebar) {\n" +
            "                var type = widgetTypes[widgetsReg[id].type];\n" +
            "                var config = type.config || [];\n" +
            "\n" +
            "                if (config.length == 0) sidebar.classList.add(\"no-settings\");\n" +
            "                else sidebar.classList.remove(\"no-settings\");\n" +
            "\n" +
            "                sidebar.innerHTML = \"\";\n" +
            "\n" +
            "                for (var i = 0; i < config.length; i++) {\n" +
            "                    var configOption = config[i]\n" +
            "                    var lbl = document.createElement(\"label\");\n" +
            "                    lbl.textContent = configOption.name + \": \";\n" +
            "\n" +
            "                    if (configOption.type == \"select\") {\n" +
            "                        if (configOption.value == \"field\") {\n" +
            "                            lbl.appendChild(\n" +
            "                                createSelectControl(widgetsReg[id].config, configOption.name, fields)\n" +
            "                            );\n" +
            "                        }\n" +
            "                    } else if (configOption.type == \"number\") {\n" +
            "\n" +
            "                        lbl.appendChild(\n" +
            "                            createNumberControl(widgetsReg[id].config, configOption.name, configOption.default)\n" +
            "                        );\n" +
            "                    }\n" +
            "\n" +
            "                    sidebar.appendChild(lbl);\n" +
            "                }\n" +
            "            }\n" +
            "        })();\n" +
            "\n" +
            "        function createSelectControl(config, key, options) {\n" +
            "            var select = document.createElement(\"select\");\n" +
            "\n" +
            "            if (config[key] === undefined) config[key] = options[0];\n" +
            "\n" +
            "            for (var i = 0; i < options.length; i++) {\n" +
            "                var opt = document.createElement(\"option\");\n" +
            "                if (config[key] === options[i]) opt.setAttribute(\"selected\", \"true\");\n" +
            "                opt.textContent = options[i];\n" +
            "                select.appendChild(opt);\n" +
            "            }\n" +
            "            select.addEventListener(\"change\", function () {\n" +
            "                config[key] = options[select.selectedIndex];\n" +
            "            });\n" +
            "            return select;\n" +
            "        }\n" +
            "\n" +
            "        function createNumberControl(config, key, defaultValue) {\n" +
            "            if (config[key] === undefined) {\n" +
            "                config[key] = defaultValue || 0;\n" +
            "            }\n" +
            "            var input = document.createElement(\"input\");\n" +
            "            input.type = \"number\";\n" +
            "            input.value = config[key];\n" +
            "            input.addEventListener(\"change\", function () {\n" +
            "                config[key] = input.valueAsNumber;\n" +
            "            });\n" +
            "\n" +
            "            return input;\n" +
            "        }\n" +
            "\n" +
            "        function startDataRequest() {\n" +
            "            var xhr = new XMLHttpRequest();\n" +
            "\n" +
            "            xhr.open(\"GET\", \"/stream\");\n" +
            "\n" +
            "            var lastIndex = -1;\n" +
            "            xhr.addEventListener(\"progress\", function (event) {\n" +
            "                var currentIndex = xhr.responseText.length;\n" +
            "                if (lastIndex == currentIndex) return; // No new data\n" +
            "                var streamData = xhr.responseText.substring(lastIndex, currentIndex);\n" +
            "\n" +
            "                var streamChunks = streamData.split(\"\\n\");\n" +
            "                for (var i = 0; i < streamChunks.length; i++) {\n" +
            "                    if (streamChunks[i].startsWith(\"{\")) processData(streamChunks[i]);\n" +
            "                    else console.log(\"Control code: \" + streamChunks[i]);\n" +
            "                }\n" +
            "\n" +
            "                lastIndex = currentIndex;\n" +
            "            });\n" +
            "\n" +
            "            xhr.send();\n" +
            "\n" +
            "            //abort & restart after 30s\n" +
            "            setTimeout(function () {\n" +
            "                    //xhr.abort();\n" +
            "                    //startDataRequest();\n" +
            "            }, 30000);\n" +
            "        }\n" +
            "\n" +
            "        function processData(data) {\n" +
            "            var dataObject = {};\n" +
            "            try {\n" +
            "                dataObject = JSON.parse(data);\n" +
            "            } catch (e) {\n" +
            "                console.error(data);\n" +
            "                console.error(e);\n" +
            "            }\n" +
            "            if (dataObject.fields !== undefined) fields = Object.keys(dataObject.fields);\n" +
            "            var widgets = Object.values(widgetsReg);\n" +
            "            for (var i = 0; i < widgets.length; i++) {\n" +
            "                //skip `__continue`\n" +
            "                if (typeof widgets[i] == \"number\") continue;\n" +
            "\n" +
            "                try {\n" +
            "                    widgetTypes[widgets[i].type].ondata(dataObject, widgets[i].content, widgets[i].state, widgets[i].config);\n" +
            "                } catch (e) {\n" +
            "                    console.error(e);\n" +
            "                    console.error(widgets[i]);\n" +
            "                }\n" +
            "            }\n" +
            "        }\n" +
            "\n" +
            "        function drawLine(datapoints, box, xScale, isFilled) {\n" +
            "            var firstDisplayedDatapointIndex = 0;\n" +
            "            var xRightEdge = Date.now();\n" +
            "\n" +
            "            //find the first displayed datapoint. If there's not enough data to cover the graph, it'll select everything.\n" +
            "            for (var i = datapoints.length - 1; i >= 0; i--) {\n" +
            "                var pointAge = (xRightEdge - datapoints[i].x);\n" +
            "                if (pointAge > xScale) {\n" +
            "                    firstDisplayedDatapointIndex = i + 1;\n" +
            "                    break;\n" +
            "                }\n" +
            "            }\n" +
            "\n" +
            "            //slice out data that matters\n" +
            "            var displayedData = datapoints.slice(firstDisplayedDatapointIndex);\n" +
            "\n" +
            "            //find top point\n" +
            "            var max = displayedData[0].y;\n" +
            "            for (var i = 0; i < displayedData.length; i++) max = Math.max(max, displayedData[i].y);\n" +
            "\n" +
            "            var path = \"\";\n" +
            "\n" +
            "            for (var i = 0; i < displayedData.length; i++) {\n" +
            "                var point = displayedData[i];\n" +
            "\n" +
            "                var x = 1 - ((xRightEdge - point.x) / xScale);\n" +
            "                var y = (max - point.y) / max;\n" +
            "\n" +
            "                //initialization\n" +
            "                if (i == 0) path += \"M\" + (x * box.width) + \",\" + (y * box.height);\n" +
            "\n" +
            "                path += \"L\" + (x * box.width) + \",\" + (y * box.height);\n" +
            "            }\n" +
            "            return path;\n" +
            "        }\n" +
            "\n" +
            "    </script>\n" +
            "    <style>\n" +
            "        * {\n" +
            "            box-sizing: border-box;\n" +
            "        }\n" +
            "\n" +
            "        body {\n" +
            "            margin: 0;\n" +
            "            background: var(--background-color);\n" +
            "            font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif;\n" +
            "        }\n" +
            "\n" +
            "        body.dark-theme {\n" +
            "            --widget-text-color: #EFF9FB;\n" +
            "            --widget-background-color: #1B1D22;\n" +
            "            --background-color: #2A2D34;\n" +
            "            --grid-color: #EFF9FB;\n" +
            "            --widget-inner-background-color: #24272D;\n" +
            "            --close-widget-color: #C23838;\n" +
            "            --widget-settings-color: #0092CC;\n" +
            "            --sidebar-lightbox: #1B1D22cc;\n" +
            "            --data-green: #10BC8B;\n" +
            "            --graph-label: #3E4247;\n" +
            "        }\n" +
            "\n" +
            "        body.light-theme {\n" +
            "            --widget-text-color: #1e1f1f;\n" +
            "            --widget-background-color: #98A0AE;\n" +
            "            --background-color: #B0B4BF;\n" +
            "            --grid-color: #090A0B;\n" +
            "            --widget-inner-background-color: #d4d4d4;\n" +
            "            --close-widget-color: #B22E2E;\n" +
            "            --widget-settings-color: #0075A3;\n" +
            "            --sidebar-lightbox: #98A0AEcc;\n" +
            "            --data-green: #007a58;\n" +
            "            --graph-label: #a8a8a8;\n" +
            "        }\n" +
            "\n" +
            "        .grid {\n" +
            "            display: grid;\n" +
            "            grid-template-columns: repeat(auto-fill, var(--grid-size));\n" +
            "            grid-template-rows: repeat(auto-fill, var(--grid-size));\n" +
            "            grid-gap: 0;\n" +
            "            gap: 0;\n" +
            "\n" +
            "            position: fixed;\n" +
            "            top: 0;\n" +
            "            bottom: 0;\n" +
            "            left: 0;\n" +
            "            right: 0;\n" +
            "            width: 100vw;\n" +
            "            height: 100vh;\n" +
            "        }\n" +
            "\n" +
            "        #grid-background span {\n" +
            "            display: block;\n" +
            "            width: var(--grid-size);\n" +
            "            height: var(--grid-size);\n" +
            "            position: relative;\n" +
            "        }\n" +
            "\n" +
            "        #grid-background span::before {\n" +
            "            width: 2px;\n" +
            "            height: 2px;\n" +
            "            background: var(--grid-color);\n" +
            "            content: \"\";\n" +
            "            display: inline-block;\n" +
            "            position: absolute;\n" +
            "            top: -1px;\n" +
            "            left: -1px;\n" +
            "        }\n" +
            "\n" +
            "        #grid-background span::after {\n" +
            "            width: 2px;\n" +
            "            height: 2px;\n" +
            "            background: var(--grid-color);\n" +
            "            content: \"\";\n" +
            "            display: inline-block;\n" +
            "            position: absolute;\n" +
            "            bottom: -1px;\n" +
            "            right: -1px;\n" +
            "        }\n" +
            "\n" +
            "        .widget {\n" +
            "            background: var(--widget-background-color);\n" +
            "            box-shadow: 0 0 0.5px 1px var(--background-color);\n" +
            "            padding: 5px;\n" +
            "        }\n" +
            "\n" +
            "        .widget--inner {\n" +
            "            background: var(--widget-inner-background-color);\n" +
            "            width: 100%;\n" +
            "            height: 100%;\n" +
            "            display: flex;\n" +
            "            flex-direction: column;\n" +
            "        }\n" +
            "\n" +
            "        .drag-shadow {\n" +
            "            background: var(--widget-background-color);\n" +
            "            opacity: 0.85;\n" +
            "            border-radius: 50px;\n" +
            "        }\n" +
            "\n" +
            "        #grid-ui {\n" +
            "            pointer-events: none;\n" +
            "        }\n" +
            "\n" +
            "        .widget:hover .widget--toolbar {\n" +
            "            min-height: 2em;\n" +
            "            padding: 0.125em;\n" +
            "            background-color: var(--widget-background-color);\n" +
            "            opacity: 1;\n" +
            "\n" +
            "        }\n" +
            "\n" +
            "        .widget--toolbar {\n" +
            "            height: 0em;\n" +
            "            overflow: hidden;\n" +
            "            opacity: 0;\n" +
            "            transition: opacity 0.125s;\n" +
            "            gap: 0.2em;\n" +
            "            justify-content: flex-end;\n" +
            "            display: flex;\n" +
            "            flex-wrap: wrap;\n" +
            "            align-items: center;\n" +
            "            flex-shrink: 0;\n" +
            "        }\n" +
            "\n" +
            "        .widget--toolbar select {\n" +
            "            all: unset;\n" +
            "            color: var(--widget-text-color);\n" +
            "            background: inherit;\n" +
            "            border: 0;\n" +
            "            cursor: pointer;\n" +
            "            user-select: none;\n" +
            "        }\n" +
            "\n" +
            "        .widget--toolbar--delete-button {\n" +
            "            all: unset;\n" +
            "\n" +
            "            display: inline-block;\n" +
            "\n" +
            "            width: 1em;\n" +
            "            height: 1em;\n" +
            "\n" +
            "\n" +
            "            background: var(--close-widget-color);\n" +
            "            border-radius: 100%;\n" +
            "\n" +
            "            cursor: pointer;\n" +
            "            user-select: none;\n" +
            "        }\n" +
            "\n" +
            "        .widget--toolbar--settings-button {\n" +
            "            all: unset;\n" +
            "\n" +
            "            display: inline-block;\n" +
            "\n" +
            "            width: 1em;\n" +
            "            height: 1em;\n" +
            "\n" +
            "\n" +
            "            background: var(--widget-settings-color);\n" +
            "            border-radius: 100%;\n" +
            "\n" +
            "            cursor: pointer;\n" +
            "            user-select: none;\n" +
            "        }\n" +
            "\n" +
            "        .widget--content {\n" +
            "            flex-grow: 1;\n" +
            "            color: var(--widget-text-color);\n" +
            "            overflow: auto;\n" +
            "            padding: 0.5em;\n" +
            "        }\n" +
            "\n" +
            "        .widget--content.nopadding {\n" +
            "            padding: 0\n" +
            "        }\n" +
            "\n" +
            "        .widget--content.nooverflow {\n" +
            "            overflow: hidden\n" +
            "        }\n" +
            "\n" +
            "        .widget--content pre {\n" +
            "            margin: 0;\n" +
            "        }\n" +
            "\n" +
            "        #config-sidebar {\n" +
            "            color: var(--widget-text-color);\n" +
            "            position: absolute;\n" +
            "            top: 0;\n" +
            "            height: 100vh;\n" +
            "            width: calc(3 * var(--grid-size));\n" +
            "            background: var(--widget-inner-background-color);\n" +
            "            display: none;\n" +
            "            opacity: 0;\n" +
            "            transition: opacity 0.25s;\n" +
            "            padding: 1em;\n" +
            "            border-right: 0.25em solid var(--widget-background-color);\n" +
            "\n" +
            "            flex-direction: column;\n" +
            "        }\n" +
            "\n" +
            "        #config-sidebar hr {\n" +
            "            border: 0;\n" +
            "            border-top: 1px solid var(--widget-text-color);\n" +
            "        }\n" +
            "\n" +
            "        #config-sidebar[open] {\n" +
            "            display: flex;\n" +
            "            opacity: 1;\n" +
            "        }\n" +
            "\n" +
            "        #config-sidebar[data-side=left] {\n" +
            "            left: 0;\n" +
            "        }\n" +
            "\n" +
            "        #config-sidebar[data-side=right] {\n" +
            "            left: 0;\n" +
            "        }\n" +
            "\n" +
            "        #config-sidebar-parent {\n" +
            "            position: fixed;\n" +
            "            top: 0;\n" +
            "            right: 0;\n" +
            "            left: 0;\n" +
            "            bottom: 0;\n" +
            "            width: 100vw;\n" +
            "            height: 100vh;\n" +
            "\n" +
            "            display: none;\n" +
            "\n" +
            "            background: var(--sidebar-lightbox);\n" +
            "        }\n" +
            "\n" +
            "        .sidebar--config-fields {\n" +
            "            border: 2px solid var(--widget-background-color);\n" +
            "            flex-grow: 1;\n" +
            "        }\n" +
            "\n" +
            "        .sidebar--config-fields.no-settings {\n" +
            "            background: var(--widget-background-color);\n" +
            "        }\n" +
            "\n" +
            "        .widget--line-chart {\n" +
            "            width: 100%;\n" +
            "            height: 100%;\n" +
            "        }\n" +
            "\n" +
            "        .widget--line-chart--fill-line {\n" +
            "            fill: url(#line-graph-fill-gradient);\n" +
            "        }\n" +
            "\n" +
            "        .widget--line-chart--stroke-line {\n" +
            "            stroke: var(--data-green);\n" +
            "            stroke-width: 0.125em;\n" +
            "            stroke-linejoin: round;\n" +
            "            fill: transparent;\n" +
            "            stroke-linecap: round;\n" +
            "        }\n" +
            "\n" +
            "        .widget--line-chart--caption-line {\n" +
            "            stroke: var(--graph-label);\n" +
            "            stroke-width: 0.25em;\n" +
            "            stroke-linecap: round;\n" +
            "        }\n" +
            "\n" +
            "        .widget--line-chart--caption {\n" +
            "            fill: var(--graph-label);\n" +
            "            font: inherit;\n" +
            "            font-size: 1.85em;\n" +
            "        }\n" +
            "\n" +
            "        #line-graph-fill-gradient-color {\n" +
            "            stop-color: var(--data-green);\n" +
            "        }\n" +
            "\n" +
            "    </style>\n" +
            "</head>\n" +
            "\n" +
            "<body>\n" +
            "    <svg style=\"width:0;height:0;position:absolute;\" aria-hidden=\"true\" focusable=\"false\">\n" +
            "        <linearGradient id=\"line-graph-fill-gradient\" x2=\"0\" y2=\"1\">\n" +
            "            <stop offset=\"0%\" stop-color=\"#447799\" id=\"line-graph-fill-gradient-color\" />\n" +
            "            <stop offset=\"100%\" stop-color=\"#0000\" id=\"line-graph-fill-bg-color\" />\n" +
            "        </linearGradient>\n" +
            "    </svg>\n" +
            "\n" +
            "    <div aria-hidden=\"true\" class=\"grid\" id=\"grid-background\"></div>\n" +
            "    <div class=\"grid\" id=\"grid-widgets\"></div>\n" +
            "    <div aria-hidden=\"true\" class=\"grid\" id=\"grid-ui\"></div>\n" +
            "    <div id=\"config-sidebar-parent\">\n" +
            "        <sidebar id=\"config-sidebar\" data-side=\"left\"></sidebar>\n" +
            "    </div>\n" +
            "    <script>\n" +
            "        if (location.search == \"?paul\") localStorage.setItem(\"theme\", \"light\");\n" +
            "        else if (location.search == \"?unpaul\") localStorage.setItem(\"theme\", \"dark\")\n" +
            "\n" +
            "        document.body.classList.add((localStorage.getItem(\"theme\") || \"dark\") + \"-theme\");\n" +
            "    </script>\n" +
            "</body>\n" +
            "\n" +
            "</html>";
}
