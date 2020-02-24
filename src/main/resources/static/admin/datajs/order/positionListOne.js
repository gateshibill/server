layui.use(['form', 'layer', 'table', 'laytpl'], function () {
    var form = layui.form,
        layer = parent.layer === undefined ? layui.layer : top.layer,
        $ = layui.jquery,
        laytpl = layui.laytpl,
        table = layui.table;
    var userId = $(".userId").val();
    //风险控制
    var tableIns = table.render({
        elem: '#adminList',
        method: 'post',
        url : '/positionOrder/getUserPositionOrderList',
        where: {
            userId: userId,
        },
        cellMinWidth: 95,
        page: true,
        //height: "full-125",
        limits: [10, 15, 20, 25],
        limit: 12,
        id: "adminListTable",
        cols : [[
            {field: 'stockName', title: '股票名称', width:212},
            {field: 'stockCode', title: '股票代码', width:212},
            {field: 'stockNumber', title: '证券数量', width:212},
            {field: 'lastest', title: '现价',width:212},
            {field: 'ykMoney', title: '盈亏', width:212,templet:function (d) {
                    if(d.ykMoney >= 0){
                        return "<span style='color:red'>"+d.ykMoney.toFixed(2)+"</span>"
                    }else if(d.ykMoney < 0){
                        return "<span style='color:green'>"+d.ykMoney.toFixed(2)+"</span>"
                    }
                }},
            {field: 'ykRatio', title: '盈亏比例', width:212,templet:function (d) {
                    if(d.ykRatio >= 0){
                        return "<span style='color:red'>"+d.ykRatio +"%</span>"
                    }else if(d.ykRatio < 0){
                        return "<span style='color:green'>"+d.ykRatio +"%</span>"
                    }
                }},
            {field: 'marketValue', title: '股票市值', width:212,templet:function (d) {
                    return  !d.marketValue?"":"<span style='color:red'>"+d.marketValue.toFixed(2)+"</span>"
                }},
            {field: 'costPrice', title: '成本价',width:212,templet:function (d) {
                    return  !d.costPrice?"":"<span style='color:red'>"+d.costPrice.toFixed(2)+"</span>"
                }},
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
})
