layui.use(['form','layer','table','laytpl'],function(){
    var form = layui.form,
        layer = parent.layer === undefined ? layui.layer : top.layer,
        $ = layui.jquery,
        laytpl = layui.laytpl,
        table = layui.table;
    var userId = $(".userId").val();
    //成交管理
    var tableIns = table.render({
        elem: '#adminList',
        method:'post',
        url : '/positionOrder/getPositionOrderList',
        where: {
            userId: userId,
        },
        cellMinWidth : 95,
        page : true,
        //height : "full-125",
        limits : [10,15,20,25],
        limit : 12,
        id : "adminListTable",
        totalRow: true,
        cols : [[
            {field: 'userName', title: '会员名', width:100,align:'center',totalRowText: '合计'},
            {field: 'stockName', title: '股票名称', align:'center'},
            {field: 'stockCode', title: '股票代码', align:'center'},
            {field: 'orderNumber', title: '证券数量', align:'center',totalRow: true},
            {field: 'allowSellNumber', title: '可用数量', align:'center',totalRow: true},
            {field: 'stockNewMoney', title: '现价', align:'center',totalRow: true},
            {field: 'orderBuyer', title: '成本单价', align:'center',totalRow: true,templet:function (d) {
                    return  !d.orderBuyer?"":"<span style='color:red'>"+d.orderBuyer.toFixed(2)+"</span>"
                }},
            {field: 'orderProfit', title: '浮动盈亏', align:'center',totalRow: true,templet:function (d) {
                    if(d.orderProfit >= 0){
                        return "<span style='color:red'>"+d.orderProfit.toFixed(2)+"</span>"
                    }else if(d.orderProfit < 0){
                        return "<span style='color:green'>"+d.orderProfit.toFixed(2)+"</span>"
                    }
                }},
            {field: 'orderYield', title: '盈亏比例', align:'center',totalRow: true,templet:function (d) {
                    if(d.orderYield >= 0){
                        return "<span style='color:red'>"+d.orderYield.toFixed(2)+"</span>"
                    }else if(d.orderYield < 0){
                        return "<span style='color:green'>"+d.orderYield.toFixed(2)+"</span>"
                    }
                }},
            {field: 'orderMarketValue', title: '股票市值', align:'center',totalRow: true,templet:function (d) {
                    return  !d.orderMarketValue?"":"<span style='color:red'>"+d.orderMarketValue.toFixed(2)+"</span>"
                }},

        ]]
    });

    //搜索【此功能需要后台配合，所以暂时没有动态效果演示】
    $(".search_btn").on("click",function(){
        var userName = $('.userName').val();
        var stockName = $('.stockName').val();
        var stockCode = $('.stockCode').val();
        table.reload("adminListTable",{
            page: {
                curr: 1 //重新从第 1 页开始
            },
            where: {
                userName: userName,
                userId : userId,
                stockName: stockName,
                stockCode: stockCode,

            }
        })
    });


})
