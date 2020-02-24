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
        url : '/capitalDetail/selectwithdrawalthrough',
        cellMinWidth : 95,
        page : true,
       // height : "full-125",
        limits : [10,15,20,25],
        limit : 12,
        id : "adminListTable",
        totalRow: true,
        cols : [[
            {field: 'userName', title: '会员名', align:'center',totalRowText: '合计'},
            {field: 'tradeMoney', title: '交易金额', align:'center',totalRow: true,templet:function (d) {
                    return "<span style='color:red'>"+d.tradeMoney.toFixed(2)+"</span>"
                }},
            {field: 'source', title: '提现方式', align:'center',sort:true,templet:function (d) {
                    return d.source == "1" ? "银行卡":"支付宝";
                }},
            {field: 'adminName', title: '操作人', align:'center',sort:true},
            {field: 'userCard', title: '银行卡号', align:'center',templet:function (d) {
                    return  !d.userCard?"":"<span style='color:red'>"+d.userCard+"</span>"
                }},
            {field: 'userPhone', title: '联系方式', align:'center',templet:function (d) {
                    return  !d.userPhone?"":"<span style='color:red'>"+d.userPhone+"</span>"
                }},
            {field: 'userIdentity', title: '身份证号', align:'center',sort:true,templet:function (d) {
                    return  !d.userIdentity?"":"<span style='color:red'>"+d.userIdentity+"</span>"
                }},
            {field: 'createTime', title: '创建时间', align:'center',sort:true},
        ]]
    });

    //搜索【此功能需要后台配合，所以暂时没有动态效果演示】
    $(".search_btn").on("click",function(){
        var userName = $('.userName').val();
        var adminName = $('.adminName').val();
        var source = $('.source').val();
        var userCard = $('.userCard').val();
        var userPhone = $('.userPhone').val();
        var sTime = $('#startTime').val();
        var eTime = $('#endTime').val();
        table.reload("adminListTable",{
            page: {
                curr: 1 //重新从第 1 页开始
            },
            where: {
                userName: userName,
                adminName:adminName,
                source: source,
                userCard: userCard,
                userPhone: userPhone,
                sTime: sTime,
                eTime: eTime,
            }
        })
    });


})
