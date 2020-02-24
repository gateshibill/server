layui.use(['form', 'layer', 'table', 'laydate'], function () {
    var form = layui.form,
        layer = parent.layer === undefined ? layui.layer : top.layer,
        $ = layui.jquery,
        laytpl = layui.laytpl,
        laydate = layui.laydate,
        table = layui.table;

    //风险控制
    var tableIns = table.render({
        elem: '#adminList',
        method: 'post',
        url: '/serve/getservelist',
        cellMinWidth: 95,
        page: true,
        //height: "auto",
        limits: [10, 15, 20, 25],
        limit: 12,
        id: "adminListTable",
        totalRow: true,
        cols: [[
            {field: 'orderCode', title: '编号', align: 'center',totalRowText: '合计'},
            {field: 'userName', title: '会员名(账号)', align: 'center'},
            {field: 'actualAmount', title: '发生金额', align: 'center',totalRow: true},
            // {
            //     field: 'actualAmount', title: '发生金额', align: 'center', templet: function (d) {
            //  //   return "<span style='color:red'>" + d.actualAmount.toFixed(2) + "</span>";
            //         return actualAmount;
            // }
            // },
            {field: 'agentServiceInto', title: '所属代理商-%', align: 'center',templet:function(d){
            	 return d.adminName+'-'+(d.agentServiceInto?d.agentServiceInto+'%':0);
            }},
            {field: 'agentServiceFee', title: '服务费(代)', align: 'center', totalRow: true},
            {
                field: 'userMoney', title: '账户余额', align: 'center', templet: function (d) {
                return "<span style='color:red'>" + d.userMoney.toFixed(2) + "</span>";
            }
            },
            {field: 'serveTime', title: '时间', align: 'center', sort: true},
        ]]
    });

    //搜索【此功能需要后台配合，所以暂时没有动态效果演示】
    $(".search_btn").on("click", function () {
        var userName = $('.userName').val(); //用户名
        var adminName = $('.agent').val(); //代理
        var sTime = $('#startTime').val();
        var eTime = $('#endTime').val();
        var orderCode=$('.orderCode').val();
        table.reload("adminListTable", {
            page: {
                curr: 1 //重新从第 1 页开始
            },
            where: {
                userName: userName,
                adminName: adminName,
                sTime: sTime,
                eTime: eTime,
                orderCode:orderCode,
            }
        })
    });


})
