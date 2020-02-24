layui.use(['form','layer','table','laytpl'],function(){
    var form = layui.form,
        layer = parent.layer === undefined ? layui.layer : top.layer,
        $ = layui.jquery,
        laytpl = layui.laytpl,
        table = layui.table;

    //风险控制
    var tableIns = table.render({
        elem: '#adminList',
        method:'post',
        url : '/flowrecord/getCapitalStatisticsAll',
        cellMinWidth : 95,
        page : true,
        //height : "full-125",
        limits : [10,15,20,25],
        limit : 12,
        id : "adminListTable",
        cols : [[
            {field: 'userName', title: '用户名',  align:"center"},
            {field: 'statistical', title: '流水金额', align:'center'},
            {field: 'flowType', title: '收入/支出', align:'center',sort: true,templet:function (d) {
                    return d.flowType == "1" ? "收入" : "支出";
                }},
        ]]
    });

    //搜索【此功能需要后台配合，所以暂时没有动态效果演示】
    $(".search_btn").on("click",function(){
        var userName = $('.userName').val();
        table.reload("adminListTable",{
            page: {
                curr: 1 //重新从第 1 页开始
            },
            where: {
                userName: userName,

            }
        })
    });


})
