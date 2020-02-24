layui.use(['form','layer','table','laytpl'],function(){
    var form = layui.form,
        layer = parent.layer === undefined ? layui.layer : top.layer,
        $ = layui.jquery,
        laytpl = layui.laytpl,
        table = layui.table;

    //委托管理
    var tableIns = table.render({
        elem: '#adminList',
        method:'post',
        url : '/entrustOrder/getOrderEntrustList',
        cellMinWidth : 95,
        page : true,
       // height : "full-125",
        limits : [10,15,20,25],
        limit : 12,
        id : "adminListTable",
        cols : [[
            {field: 'orderCode', title: '交易编号',width:180,align:"center"},
            {field: 'userName', title: '会员名(账号)', align:'center'},
            {field: 'stockName', title: '股票名称', align:'center'},
            {field: 'stockCode', title: '股票代码', align:'center'},
            {field: 'orderNumber', title: '委托数量', align:'center'},
            {field: 'orderBuyer',field:'orderType',field:'orderExitPrice', title: '委托单价', align:'center',templet:function (d) {
                    if(d.orderType == "1"){
                        return  !d.orderBuyer?"":"<span style='color:red'>"+d.orderBuyer.toFixed(2)+"</span>"
                    }else if(d.orderType == "2"){
                        return  !d.orderExitPrice?"":"<span style='color:red'>"+d.orderExitPrice.toFixed(2)+"</span>"
                    }
                }},
            {field: 'orderType', title: '买卖标志',  align:'center',sort: true,templet:function (d){
                if(d.orderType == "1"){
                    return "<span style='background-color:#5cb85c;padding:3px 10px;color:#fff;border-radius:1px'>"+"买入股票"+"</span>";
                }else if(d.orderType == "2"){
                    return "<span style='background-color:#d9534f;padding:3px 10px;color:#fff;border-radius:1px'>"+"卖出股票"+"</span>";
                }
            }},
            {field: 'entrustTime', title: '委托时间', align: 'center'},
            {title: '操作', minWidth:175, templet:'#moneyListBar',fixed:"right",align:"center"}
        ]]
    });

    //搜索【此功能需要后台配合，所以暂时没有动态效果演示】
    $(".search_btn").on("click",function(){
        var userName = $('.userName').val();
        var stockName = $('.stockName').val();
        var stockCode = $('.stockCode').val();
        var orderCode = $('.orderCode').val();
        table.reload("adminListTable",{
            page: {
                curr: 1 //重新从第 1 页开始
            },
            where: {
                userName: userName,
                stockName: stockName,
                stockCode: stockCode,
                orderCode: orderCode,
            }
        })
    });

    function revoke(data){
        $.post('/entrustOrder/revoke',{
            orderId : data.orderId
        },function(data){
            if(data.msg == 'success'){
                layer.msg('操作成功',{icon:6});
                tableIns.reload();//刷新表格
            }else{
                layer.msg(data.msg,{icon:5});
                return false;
            }
        },'json')
    }

    //列表操作
    table.on('tool(adminList)', function(obj){
        var layEvent = obj.event,
            data = obj.data;

        if(layEvent === 'revoke'){ //撤单
            layer.confirm('确认撤除该条委托订单？',{icon:3, title:'提示信息'},function(index){
                revoke(data);
            });
        }
    });

})
