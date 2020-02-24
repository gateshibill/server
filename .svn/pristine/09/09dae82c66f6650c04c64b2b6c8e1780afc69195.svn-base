layui.use(['form','layer','table','laytpl'],function(){
    var form = layui.form,
        layer = parent.layer === undefined ? layui.layer : top.layer,
        $ = layui.jquery,
        laytpl = layui.laytpl,
        table = layui.table;

    //代理商列表
    var tableIns = table.render({
        elem: '#adminList',
        method:'post',
        url : '/admin/getadminlist',
        cellMinWidth : 95,
        page : true,
        //height : "full-125",
        limits : [10,15,20,25],
        limit : 12,
        id : "adminListTable",
        cols : [[
            {field: 'adminId', title: 'ID', width:80, align:"center"},
            {field: 'adminCoding', title: '编号',align:'center'},
            {field: 'adminAccount', title: '账号',align:'center'},
            {field: 'adminName', title: '真实姓名',align:'center'},
            {field: 'phone', title: '联系方式', align:'center'},
            {field: 'handInto', title: '手续费', align:'center',sort:true,templet:function(d){
            	return d.handInto?d.handInto:0;
            }},
            {field: 'serviceInto', title: '服务费', align:'center',sort:true,templet:function(d){
            	return d.serviceInto?d.serviceInto:0;
            }},
            {field: 'isEffect', title: '状态',  align:'center',sort:true,templet:function(d){
                return d.isEffect == "1" ? "<span style='background-color:#5cb85c;padding:3px 10px;color:#fff;border-radius:1px'>正常</span>" : "<span style='background-color:#d9534f;padding:3px 10px;color:#fff;border-radius:1px'>禁用</span>";
            }},
            {field: 'createTime', title: '创建时间', align:'center',sort:true},
            {title: '操作', width:200, templet:'#adminListBar',fixed:"right",align:"center"}
        ]]
    });

    //搜索【此功能需要后台配合，所以暂时没有动态效果演示】
    $(".search_btn").on("click",function(){
    	var adminName = $('.adminName').val();
    	var adminAccount = $('.adminAccount').val();
    	var phone = $('.phone').val();
        table.reload("adminListTable",{
                page: {
                    curr: 1 //重新从第 1 页开始
                },
                where: {
                    adminAccount: adminAccount,
                    adminName:adminName,
                    phone:phone,
                }
        })
    });

    //添加代理
    function addAdmin(){
        var index = layui.layer.open({
            title : '添加代理',
            type : 2,
            content : "/admin/addadmin",
            success : function(layero, index){
                setTimeout(function(){
                    layui.layer.tips('点击此处返回代理列表', '.layui-layer-setwin .layui-layer-close', {
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
    function editAdmin(data){
    	 var index = layui.layer.open({
             title : '编辑代理',
             type : 2,
             content : "/admin/updateadmin",
             success : function(layero, index){
                 var body = layui.layer.getChildFrame('body', index);
                 body.find(".adminName").val(data.adminName); 
                 body.find(".adminAccount").val(data.adminAccount);
                 body.find(".adminCoding").val(data.adminCoding);
                 body.find(".isEffect option[value="+data.isEffect+"]").prop("selected","selected");  
                 body.find(".phone").val(data.phone);  
                 body.find(".serviceInto").val(data.serviceInto);  
                 body.find(".handInto").val(data.handInto);  
                 body.find(".adminId").val(data.adminId);   
                 form.render();
                 setTimeout(function(){
                     layui.layer.tips('点击此处返回代理列表', '.layui-layer-setwin .layui-layer-close', {
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
    /**
     * 设置角色
     */
    function setRole(data){
    	 var index = layui.layer.open({
             title : '设置角色',
             type : 2,
             content : "/admin/setrole?roleId="+data.roleId+"&adminId="+data.adminId,
             success : function(layero, index){

                 setTimeout(function(){
                     layui.layer.tips('点击此处返回代理列表', '.layui-layer-setwin .layui-layer-close', {
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
    	addAdmin();
    })
    //列表操作
    table.on('tool(adminList)', function(obj){
        var layEvent = obj.event,
            data = obj.data;

        if(layEvent === 'edit'){ //编辑
        	editAdmin(data);
        }else if(layEvent === 'setrole'){ //设置角色
        	setRole(data)
        }else if(layEvent === 'del'){ //删除
            layer.confirm('确定删除此代理？',{icon:3, title:'提示信息'},function(index){
                 $.post("/admin/deladmin",{
                     adminId : data.adminId  
                 },function(data){
                    tableIns.reload();
                    layer.close(index);
                 },'json')
            });
        }
    });

})
