layui.use(['form', 'layer', 'table', 'laytpl'], function () {
    var form = layui.form,
        layer = parent.layer === undefined ? layui.layer : top.layer,
        $ = layui.jquery,
        laytpl = layui.laytpl,
        table = layui.table;

    //风险控制
    var tableIns = table.render({
        elem: '#adminList',
        method: 'post',
        url: '/memberRiskControl/getunwindlist',
        cellMinWidth: 95,
        page: true,
        //height: "full-125",
        limits: [10, 15, 20, 25],
        limit: 12,
        id: "adminListTable",
        totalRow: true,
        cols: [[
            {field: 'userName', title: '会员名(账号)', align: "center",totalRowText: '合计'},
            {field: 'cashDeposit', title: '保证金', align: 'center'},
            {
                field: 'totalAssets1', title: '总资产', align: 'center',totalRow: true, templet: function (d) {
                    return  !d.totalAssets1?"":"<span style='color:red'>"+d.totalAssets1.toFixed(2)+"</span>"
                }
            },
            {field: 'stockValue1', title: '股票市值', align: 'center',totalRow: true,},
            {field: 'expendableFund', title: '可用资金', align: 'center'},
           /* {
                field: 'principalSum', title: '累计已配本金总金额', align: 'center', sort: true, templet: function (d) {
                    return  !d.principalSum?"":"<span>"+d.principalSum.toFixed(2)+"</span>"
                }
            },
            {
                field: 'marginSum', title: '累计已配保证金总金额', align: 'center', sort: true, templet: function (d) {
                    return  !d.marginSum?"":"<span>"+d.marginSum.toFixed(2)+"</span>"
                }
            },
            {
                field: 'principalXin', title: '本次本金金额', align: 'center', sort: true, templet: function (d) {
                    return  !d.principalXin?"":"<span>"+d.principalXin.toFixed(2)+"</span>"
                }
            },
            {
                field: 'marginXin', title: '本次保证金金额', align: 'center', sort: true, templet: function (d) {
                    return  !d.marginXin?"":"<span>"+d.marginXin.toFixed(2)+"</span>"
                }
            },*/
            {field: 'positionRatio', title: '持仓比例(%)', align: 'center',templet:function (d) {
                return  "<span>"+d.positionRatio.toFixed(2)+"%</span>"
            }},
            {field: 'totalVariableProfitLoss', title: '总浮动盈亏', align: 'center',totalRow: true,templet:function (d) {
                    if(d.totalVariableProfitLoss >= 0){
                        return "<span style='color:red'>"+d.totalVariableProfitLoss.toFixed(2)+"</span>"
                    }else if(d.totalVariableProfitLoss < 0){
                        return "<span style='color:green'>"+d.totalVariableProfitLoss.toFixed(2)+"</span>"
                    }
                }},
            {field: 'profitLossRatio', title: '盈亏比例(%)', align: 'center',templet:function (d) {
                    if(d.profitLossRatio >= 0){
                        return "<span style='color:red'>"+d.profitLossRatio.toFixed(2) +"%</span>"
                    }else if(d.profitLossRatio < 0){
                        return "<span style='color:green'>"+d.profitLossRatio.toFixed(2)  +"%</span>"
                    }
                }},
            {
                field: 'redLine1', title: '亏损警戒线', align: 'center', sort: true, templet: function (d) {
                    return  !d.redLine1?"":"<span style='color:red'>"+d.redLine1.toFixed(2)+"</span>"
                }
            },
            {
                field: 'redLineRatio', title: '亏损警戒线(%)', align: 'center', sort: true, templet: function (d) {
                    return  !d.redLineRatio?"":"<span style='color:red'>"+d.redLineRatio.toFixed(2)+"%</span>"
                }
            },
            {
                field: 'lossLine1', title: '亏损平仓线', align: 'center', sort: true, templet: function (d) {
                    return  !d.lossLine1?"":"<span style='color:red'>"+d.lossLine1.toFixed(2)+"</span>"
                }
            },
            {
                field: 'lossLineRatio', title: '亏损平仓线(%)', align: 'center', sort: true, templet: function (d) {
                    return  !d.lossLineRatio?"":"<span style='color:red'>"+d.lossLineRatio.toFixed(2)+"%</span>"
                }
            },
            {title: '操作', minWidth: 50, templet: '#adminListBar',  align: "center"}
        ]]
    });

    //搜索【此功能需要后台配合，所以暂时没有动态效果演示】
    $(".search_btn").on("click", function () {
        var userName = $('.userName').val();
        table.reload("adminListTable", {
            page: {
                curr: 1 //重新从第 1 页开始
            },
            where: {
                userName: userName,

            }
        })
    });
    //持仓
    //列表操作
    table.on('tool(adminList)', function (obj) {
        var layEvent = obj.event,
            data = obj.data;

        if (layEvent === 'chicang') { //编辑
            var index = layui.layer.open({
                title: '会员：' + data.userName + "的持仓",
                type: 2,
                content: "/positionOrder/userIndex?userId=" + data.userId,
                success: function (layero, index) {
                    setTimeout(function () {
                        layui.layer.tips('点击此处返回预警线风控列表', '.layui-layer-setwin .layui-layer-close', {
                            tips: 3
                        });
                    }, 500)
                }
            })
            layui.layer.full(index);
            window.sessionStorage.setItem("index", index);
            //改变窗口大小时，重置弹窗的宽高，防止超出可视区域（如F12调出debug的操作）
            $(window).on("resize", function () {
                layui.layer.full(window.sessionStorage.getItem("index"));
            })
        }
    });
})
