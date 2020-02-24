layui.use(['form', 'layer', 'table', 'laytpl','laydate'], function () {
    var form = layui.form,
        layer = parent.layer === undefined ? layui.layer : top.layer,
        $ = layui.jquery,
        laytpl = layui.laytpl,
        laydate = layui.laydate,
        table = layui.table;
    //成交管理
    var tableIns = table.render({
        elem: '#adminList',
        method: 'post',
        url: '/userOrder/getUserOrderList',
        cellMinWidth: 95,
        page: true,
        //height: "full-125",
        limits: [10, 15, 20, 25],
        limit: 12,
        id: "adminListTable",
        totalRow: true,
        cols: [[
            {field: 'orderCode', title: '交易编号', width:180,align: "center",totalRowText: '合计'},
            {field: 'userName', title: '会员名(账号)', align: 'center'},
            {field: 'stockName', title: '股票名称', align: 'center'},
            {field: 'stockCode', title: '股票代码', align: 'center'},
            {
                field: 'orderNumber', title: '成交数量', align: 'center',totalRow: true, templet: function (d) {
                    if(d.orderType == "1"){
                        return  "<span style='color:red'>" +d.orderNumber.toFixed(2)+ "</span>"
                    }else if(d.orderType == "2"){
                        return "<span style='color:green'>"+d.orderNumber.toFixed(2)+ "</span>"
                    }
                 }
            },
            {
                field: 'orderBuyer', field:'orderExitPrice', title: '成交单价', align: 'center',templet: function (d) {
                    if(d.orderType == "1"){
                        return    d.orderBuyer.toFixed(2)
                    }else if(d.orderType == "2"){
                        return    !d.orderExitPrice?"": d.orderExitPrice.toFixed(2)
                    }
                 }
            },
            {
                field: 'turnover',  title: '成交金额', align: 'center',totalRow: true, templet: function (d) {
                    if(d.orderType == "1"){
                        return    !d.turnover?"":d.turnover.toFixed(2)
                    }else if(d.orderType == "2"){
                        return    !d.turnover?"": d.turnover.toFixed(2)
                    }
                }
            },
            {
                field: 'orderType', title: '买卖标志', align: 'center', sort: true, templet: function (d) {
                if (d.orderType == "1") {
                    return "<span style='background-color:#5cb85c;padding:3px 10px;color:#fff;border-radius:1px'>" + "买入股票" + "</span>";
                } else if (d.orderType == "2") {
                    return "<span style='background-color:#d9534f;padding:3px 10px;color:#fff;border-radius:1px'>" + "卖出股票" + "</span>";
                }
            }
            },
            {field: 'tradeTime', title: '成交时间', align: 'center'},
            //{field: 'createTime', title: '交易时间', align: 'center'}
        ]]
    });

    //搜索【此功能需要后台配合，所以暂时没有动态效果演示】
    $(".search_btn").on("click", function () {
        var userName = $('.userName').val();
        var sTime = $('#startTime').val();
        var eTime = $('#endTime').val();
        var orderCode = $('.orderCode').val();
        table.reload("adminListTable", {
            page: {
                curr: 1 //重新从第 1 页开始
            },
            where: {
                userName: userName,
                sTime: sTime,
                eTime: eTime,
                orderCode: orderCode,
            }
        })
    });


})
