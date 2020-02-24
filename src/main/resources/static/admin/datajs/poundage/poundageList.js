layui.use(['form', 'layer', 'table', 'laytpl', 'laydate'], function () {
    var form = layui.form,
        layer = parent.layer === undefined ? layui.layer : top.layer,
        $ = layui.jquery,
        laytpl = layui.laytpl,
        laydate = layui.laydate,
        table = layui.table;
    
    var tableIns = table.render({
        elem: '#adminList',
        method: 'post',
        url: '/poundage/getpoundagelist',
        cellMinWidth: 95,
        page: true,
       // height: "full-125",
        limits: [10, 15, 20, 25],
        limit: 12,
        id: "adminListTable",
        totalRow: true,
        cols: [[
            {field: 'orderCode', title: '编号',width:180, align: "center",totalRowText: '合计'},
            {field: 'userName', title: '会员名', align: 'center'},
            {field: 'stockName', title: '股票名称', align: 'center'},
            {field: 'stockCode', title: '股票代码', align: 'center'},
            {field: 'turnover', title: '成交数量', align: 'center'},
            {
                field: 'sellPrice', title: '成交单价', align: 'center', templet: function (d) {
                    return  "<span style='color:red'>"+d.sellPrice.toFixed(2)+"</span>"
                }
            },
            {
                field: 'money', title: '成交金额', align: 'center',totalRow: true,templet: function (d) {
                    return  "<span style='color:red'>"+d.money.toFixed(2)+"</span>"
                }
            },
//            {
//                field: 'transfer', title: '过户税', align: 'center',totalRow: true,templet: function (d) {
//                    return  "<span style='color:red'>"+d.transfer.toFixed(2)+"</span>"
//                }
//            },
//            {
//                field: 'printing', title: '印花税', align: 'center',totalRow: true,templet: function (d) {
//                    return  "<span style='color:red'>"+d.printing.toFixed(2)+"</span>"
//                }
//            },
//            {
//                field: 'commission', title: '佣金', align: 'center',totalRow: true,templet: function (d) {
//                    return  "<span style='color:red'>"+d.commission.toFixed(2)+"</span>"
//                }
//            },
            {
                field: 'poundagePrice', title: '手续费', align: 'center',totalRow: true,templet: function (d) {
                    return  !d.poundagePrice?"":"<span style='color:red'>"+d.poundagePrice.toFixed(2)+"</span>"
                }
            },
            {field: 'adminName', title: '所属代理商-%', align: 'center',templet:function(d){
            	return d.adminName+'-'+(d.agentHandInto?d.agentHandInto+'%':0);
            }},
            {field: 'adminChargePrice', title: '手续费(代)', align: 'center',totalRow: true,},
            {
                field: 'dealFlag', title: '买卖标志', align: 'center', sort: true, templet: function (d) {
                    if (d.dealFlag == "1") {
                        return "<span style='background-color:#5cb85c;padding:3px 10px;color:#fff;border-radius:1px'>" + "买" + "</span>";
                    } else if (d.dealFlag == "2") {
                        return "<span style='background-color:#d9534f;padding:3px 10px;color:#fff;border-radius:1px'>" + "卖" + "</span>";
                    }
                }
            },
            {field: 'dealTime', title: '交易时间',width:200, align: 'center', sort: true},
        ]]
    });

    //搜索【此功能需要后台配合，所以暂时没有动态效果演示】
    $(".search_btn").on("click", function () {
        var userName = $('.userName').val();
        var adminName = $('.agent').val();
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
