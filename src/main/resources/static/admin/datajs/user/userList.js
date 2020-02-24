layui.use(['form','layer','table','laytpl'],function(){
    var form = layui.form,
        layer = parent.layer === undefined ? layui.layer : top.layer,
        laytpl = layui.laytpl,
        table = layui.table,
        $ = layui.jquery;

    //代理商列表
    var tableIns = table.render({
        elem: '#userList',
        method:'post',
        url : '/user/getuserlist',
        cellMinWidth : 95,
        page : true,
        //height : "full-125",
        limits : [10,15,20,25],
        limit : 12,
        id : "userListTable",
        totalRow: true,
        cols : [[
            {field: 'userId', title: 'ID', width:80, align:"center",totalRowText: '合计'},
            {field: 'userCoding', title: '用户编号', minWidth:100, align:"center"},
            {field: 'userName', title: '姓名', minWidth:100, align:"center"},
            {field: 'userPhone', title: '联系方式', minWidth:100, align:"center"},
            {field: 'adminName', title: '所属代理', minWidth:100, align:"center"},
            {field: 'userMoney', title: '保证金', minWidth:100, sort:true,align:"center",totalRow: true},
            {field: 'userNetAsset', title: '总资产', minWidth:100, align:"center",totalRow: true},
            {field: 'userExpendableFund', title: '可用资产', minWidth:100, sort:true,align:"center",totalRow: true},
            {field: 'createTime', title: '创建时间',minWidth:100,sort:true,align:'center',sort:true},
            {title: '操作', minWidth:100, templet:'#userListBar',align:"center"}
        ]],
        done: function(res, curr, count){
            //如果是异步请求数据方式，res即为你接口返回的信息。
            //如果是直接赋值的方式，res即为：{data: [], count: 99} data为当前页数据、count为数据总长度
            //console.log(res.data);
            $("userList").append('<tr data-index="5" class=""><td data-field="userId" align="center"><div class="layui-table-cell laytable-cell-1-userId">5</div></td><td data-field="userName" align="center" data-minwidth="100"><div class="layui-table-cell laytable-cell-1-userName">xiexieni</div></td><td data-field="userPhone" align="center" data-minwidth="100"><div class="layui-table-cell laytable-cell-1-userPhone">18212345678</div></td><td data-field="adminName" align="center" data-minwidth="100"><div class="layui-table-cell laytable-cell-1-adminName">two</div></td><td data-field="userMoney" align="center" data-minwidth="100"><div class="layui-table-cell laytable-cell-1-userMoney">10000</div></td><td data-field="userNetAsset" align="center" data-minwidth="100"><div class="layui-table-cell laytable-cell-1-userNetAsset">110000</div></td><td data-field="userExpendableFund" align="center" data-minwidth="100"><div class="layui-table-cell laytable-cell-1-userExpendableFund">110000</div></td><td data-field="createTime" align="center"><div class="layui-table-cell laytable-cell-1-createTime">2019-06-20 16:48:33</div></td><td data-field="8" align="center" data-content="" data-minwidth="175"><div class="layui-table-cell laytable-cell-1-8"> <a class="layui-btn layui-btn-xs" lay-event="edit">编辑</a> <a class="layui-btn layui-btn-xs layui-btn-danger" lay-event="del">删除</a> </div></td></tr>');

            //在这里对res.data循环取userMoney字段的值相加
          }
    });
    //搜索【此功能需要后台配合，所以暂时没有动态效果演示】
    $(".search_btn").on("click",function(){
    	var userName = $('.userName').val();
    	var userPhone = $('.userPhone').val();
    	var inviterId = $('.inviterId').val();
        var sTime = $('#startTime').val();
        var eTime = $('#endTime').val();
        table.reload("userListTable",{
            page: {
                curr: 1 //重新从第 1 页开始
            },
            where: {
                userName: userName,
                userPhone:userPhone,
                inviterId:inviterId,
                sTime: sTime,
                eTime: eTime,
            }
        })
    });

    //添加代理
    function addUser(){
        var index = layui.layer.open({
            title : '添加会员',
            type : 2,
            content : "/user/adduser",
            success : function(layero, index){
                setTimeout(function(){
                    layui.layer.tips('点击此处返回会员列表', '.layui-layer-setwin .layui-layer-close', {
                        tips: 3
                    });
                },500)
            }
        })
        layui.layer.full(index);
        window.sessionStorage.setItem("index",index);
        //改变窗口大小时，重置弹窗的宽高，防止超出可视区域（如F12调出debug的操作）
        $(window).on("resize",function(){
            layui.layer.full(window.sessionStorage.getItem("index"));
        })
    }
    /*
            * 编辑代理
     */
    function editUser(data){
    	 var index = layui.layer.open({
             title : '编辑会员',
             type : 2,
             content : "/user/updateuser",
             success : function(layero, index){
                 var body = layui.layer.getChildFrame('body', index);
                 body.find(".userId").val(data.userId); 
                 body.find(".userName").val(data.userName); 
                 body.find(".userPassword").val(data.userPassword);  
                 body.find(".inviterId").val(data.inviterId);
                 body.find(".userCoding").val(data.userCoding);
                 body.find(".userEmail").val(data.userEmail);  
                 body.find(".userPhone").val(data.userPhone);  
                 body.find(".memberState").val(data.memberState); 
                 body.find(".userTransationCode").val(data.userTransationCode); 
                 body.find(".isCertification").val(data.isCertification);   
                 body.find(".userCode").val(data.userCode); 
                 body.find(".codeImg").val(data.codeImg); 
                 body.find(".userType").val(data.userType); 
                 form.render();
                 setTimeout(function(){
                     layui.layer.tips('点击此处返回会员列表', '.layui-layer-setwin .layui-layer-close', {
                         tips: 3
                     });
                 },500)
             }
         })
         layui.layer.full(index);
         window.sessionStorage.setItem("index",index);
         //改变窗口大小时，重置弹窗的宽高，防止超出可视区域（如F12调出debug的操作）
         $(window).on("resize",function(){
             layui.layer.full(window.sessionStorage.getItem("index"));
         })
    }
    //添加代理
    $(".addNews_btn").click(function(){
    	addUser();
    })
    //列表操作
    table.on('tool(userList)', function(obj){
        var layEvent = obj.event,
            data = obj.data;

        if(layEvent === 'edit'){ //编辑
        	editUser(data);
        }else if(layEvent === 'del'){ //删除
            layer.confirm('确定删除此会员？',{icon:3, title:'提示信息'},function(index){
                 $.post("/user/deluser",{
                	 userId : data.userId  
                 },function(data){
                    tableIns.reload();
                    layer.close(index);
                 },'json')
            });
        }
    });

})
