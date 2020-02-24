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
        url : '/flowrecord/selectUserSpending',
        cellMinWidth : 95,
        page : true,
        //height : "full-125",
        limits : [10,15,20,25],
        limit : 12,
        id : "adminListTable",
        totalRow: true,
        cols : [[
            {field: 'userName', title: '用户名',  align:"center",totalRowText: '合计'},
            {field: 'statistical', title: '支出金额', align:'center',totalRow: true,templet:function (d) {
                        //d.statistical.toFixed(2)
                        return "<span style='color:red'>" + d.statistical.toFixed(2) + "</span>";
                }},

        ]]
    });

    //搜索【此功能需要后台配合，所以暂时没有动态效果演示】
    $(".search_btn").on("click",function(){
        var userName = $('.userName').val();
        var sTime = $('#startTime').val();
        var eTime = $('#endTime').val();
        table.reload("adminListTable",{
            page: {
                curr: 1 //重新从第 1 页开始
            },
            where: {
                userName: userName,
                sTime: sTime,
                eTime: eTime,
            }
        })
    });


})
