layui.use(['form', 'layer', 'table', 'laytpl','laydate'], function () {
    var form = layui.form,
        layer = parent.layer === undefined ? layui.layer : top.layer,
        $ = layui.jquery,
        laytpl = layui.laytpl,
        laydate = layui.laydate,
        table = layui.table;

    //正在进行操盘
    var tableIns = table.render({
        elem: '#adminList',
        method: 'post',
        url: '/stockoperate/selectstockoperateone',
        cellMinWidth: 95,
        page: true,
        //height: "full-125",
        limits: [10, 15, 20, 25],
        limit: 12,
        id: "adminListTable",
        cols: [[
            {field: 'userName', title: '用户名',width:90, align: "center"},
            {field: 'userPhone', title: '手机号',width:180, align: "center",templet: function (d) {
                    return "<span style='color:red'>" + d.userPhone + "</span>"
                }},
            {field: 'totalMoney', title: '账户余额', align: "center",templet: function (d) {
                    return "<span style='color:red'>" + d.totalMoney + "</span>"
                }},
            {field: 'traderMoney', title: '股票余额', align: "center"},
            {field: 'bzjMoney', title: '保证金', align: "center",templet:function (d) {
                    return "<span style='color:red'>" + d.bzjMoney + "</span>";
                }},
            {field: 'pzMultiple', title: '倍数', align: "center"},
            {field: 'runningTime', title: '操盘时长(天)',width:120, align: "center"},
           // {field: 'operateProduce', title: '操盘产品', align: "center"},
            {
                field: 'applyTime', title: '申请时间',width:200, align: "center", templet: function (d) {
                    return "<span style='color:red'>" + d.applyTime + "</span>";
                }
            },
            {field: 'startTime', title: '开始时间',width:200, align: "center"},
            {field: 'endTime', title: '结束时间',width:200, align: "center"},
            {field: 'overdueTime', title: '剩余(天)', align: "center",templet: function (d) {
                    return "<span style='color:red'>" + d.overdueTime + "</span>"
                }},
            {field: 'moneyRatesTime', title: '收息时间',width:200, align: "center",templet: function (d) {
                    return "<span style='color:red'>" + d.moneyRatesTime + "</span>"
                }},
        ]]
    });

    //搜索【此功能需要后台配合，所以暂时没有动态效果演示】
    $(".search_btn").on("click", function () {
        var userName = $('.userName').val();
        var userPhone = $('.userPhone').val();
        var sTime = $('#startTime').val();
        var eTime = $('#endTime').val();
        table.reload("adminListTable", {
            page: {
                curr: 1 //重新从第 1 页开始
            },
            where: {
                userName: userName,
                userPhone: userPhone,
                sTime: sTime,
                eTime: eTime,
            }
        })
    });


})
