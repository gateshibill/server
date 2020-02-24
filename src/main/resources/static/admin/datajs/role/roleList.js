layui.use(['form','layer','table','laytpl'],function(){
    var form = layui.form,
        layer = parent.layer === undefined ? layui.layer : top.layer,
        $ = layui.jquery,
        laytpl = layui.laytpl,
        table = layui.table;
    //代理商列表
    var tableIns = table.render({
        elem: '#roleList',
        method:'post',
        url : '/role/getrolelist',
        cellMinWidth : 95,
        page : true,
        //height : "full-125",
        limits : [10,15,20,25],
        limit : 12,
        id : "roleListTable",
        cols : [[
            {field: 'roleId', title: '编号', width:80, align:"center"},
            {field: 'roleName', title: '角色名称', minWidth:100, align:'center'},
            {field: 'isEffect', title: '状态',  align:'center',sort:true,templet:function(d){
                return d.isEffect == "1" ? "正常使用" : "限制使用";
            }},
            {field: 'createTime', title: '创建时间', align:'center',sort:true},
            {title: '操作', minWidth:175, templet:'#roleListBar',fixed:"right",align:"center"}
        ]],
        done: function(res, curr, count){
            //如果是异步请求数据方式，res即为你接口返回的信息。
            //如果是直接赋值的方式，res即为：{data: [], count: 99} data为当前页数据、count为数据总长度
            //console.log(res.data);

        	data = res.data;
        	
          }
    });
    
    //搜索【此功能需要后台配合，所以暂时没有动态效果演示】
    $(".search_btn").on("click",function(){
    	var roleName = $('.roleName').val();
        table.reload("roleListTable",{
                page: {
                    curr: 1 //重新从第 1 页开始
                },
                where: {
                    roleName: roleName,
                }
        })
    });

    //添加角色
    function addRole(){
        var index = layui.layer.open({
            title : '添加角色',
            type : 2,
            content : "/role/addrole",
            success : function(layero, index){
                setTimeout(function(){
                    layui.layer.tips('点击此处返回角色列表', '.layui-layer-setwin .layui-layer-close', {
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
     * 编辑角色
     */
    function editRole(data){
    	 var index = layui.layer.open({
             title : '编辑角色',
             type : 2,
             content : "/role/updaterole?roleId="+data.roleId,
             success : function(layero, index){
                 var body = layui.layer.getChildFrame('body', index);
                 body.find(".roleName").val(data.roleName); 
                 body.find(".isEffect option[value="+data.isEffect+"]").prop("selected","selected");  
                 body.find(".roleId").val(data.roleId);   
                 form.render();
                 setTimeout(function(){
                     layui.layer.tips('点击此处返回角色列表', '.layui-layer-setwin .layui-layer-close', {
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
    //添加角色
    $(".addNews_btn").click(function(){
    	addRole();
    })
    //列表操作
    table.on('tool(roleList)', function(obj){
        var layEvent = obj.event,
            data = obj.data;

        if(layEvent === 'edit'){ //编辑
        	editRole(data);
        }else if(layEvent === 'del'){ //删除
            layer.confirm('确定删除此角色？',{icon:3, title:'提示信息'},function(index){
                 $.post("/role/delrole",{
                     roleId : data.roleId  
                 },function(data){
                    tableIns.reload();
                    layer.close(index);
                 },'json')
            });
        }
    });

})
