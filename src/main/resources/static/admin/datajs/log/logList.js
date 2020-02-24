var logType;
layui.use(['form', 'layer', 'table', 'laytpl', 'laydate'], function () {
    var form = layui.form,
        layer = parent.layer === undefined ? layui.layer : top.layer,
        $ = layui.jquery,
        laytpl = layui.laytpl,
        laydate = layui.laydate,
        table = layui.table;
    logType = $(".logType").val();
    //日志列表
    var tableIns = table.render({
        elem: '#logList',
        method: 'post',
        url: '/operatelog/getoperatelog',
        where: {
            logType: logType,
        },
        cellMinWidth: 95,
        page: true,
       // height: "full-125",
        limits: [10, 15, 20, 25],
        limit: 12,
        id: "logListTable",
        cols: [[
            {type: "checkbox", fixed: "left", width: 50},
            {field: 'logId', title: '编号', width: 80, align: "center"},
            {field: 'logIp', title: 'ip地址', align: "center"},
            {field: 'logContent', title: '内容', align: "center"},
            {field: 'logAdminId', title: '操作者id', align: "center"},
            {field: 'logModules', title: '模块', align: "center"},
            {field: 'createTime', title: '创建时间', align: "center", sort: true},
            {title: '操作', minWidth: 100, templet: '#logListBar', fixed: "right", align: "center"}
        ]]
    });

    //搜索【此功能需要后台配合，所以暂时没有动态效果演示】
    $(".search_btn").on("click", function () {
        var logContent = $('.logContent').val();
        var sTime = $('#startTime').val();
        var eTime = $('#endTime').val();
        table.reload("logListTable", {
            page: {
                curr: 1 //重新从第 1 页开始
            },
            where: {
                logContent: logContent,
                logType: logType,
                sTime: sTime,
                eTime: eTime,
            }
        })
    });

    //批量删除
    $(".delAll_btn").click(function () {
        var checkStatus = table.checkStatus('logListTable'),
            data = checkStatus.data,
            logsId = [];
        if (data.length > 0) {
            for (var i in data) {
                logsId.push(data[i].logId);
            }
            var ids = "";
            if (logsId.length > 0) {
                var idsStr = JSON.stringify(logsId);
                ids = idsStr.substr(1, idsStr.length - 2);
            }
            layer.confirm('确定删除选中的日志？', {icon: 3, title: '提示信息'}, function (index) {
                $.post("/operatelog/deleteselectoperatelog", {
                    logIds: ids
                }, function (data) {
                    if (data.code == 0) {
                        layer.msg('删除成功', {icon: 6});
                    } else {
                        layer.msg(data.msg, {icon: 5});
                        return false;
                    }
                    tableIns.reload();
                    layer.close(index);
                }, 'json')
            })
        } else {
            layer.msg("请选择需要删除的日志");
        }
    })

    //列表操作
    table.on('tool(logList)', function (obj) {
        var layEvent = obj.event,
            data = obj.data;

        if (layEvent === 'del') { //删除
            layer.confirm('确定删除此日志？', {icon: 3, title: '提示信息'}, function (index) {
                $.post("/operatelog/deloperatelog", {
                    logId: data.logId
                }, function (data) {
                    if (data.code == 0) {
                        layer.msg('删除日志成功', {icon: 6});
                        tableIns.reload();
                        layer.close(index);
                    } else {
                        layer.msg(data.msg, {icon: 5});
                        return false;
                    }
                }, 'json')
            });
        }
    });

})
