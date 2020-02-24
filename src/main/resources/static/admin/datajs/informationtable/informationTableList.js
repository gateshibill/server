layui.use(['form','layer','table','laytpl'],function(){
    var form = layui.form,
        layer = parent.layer === undefined ? layui.layer : top.layer,
        $ = layui.jquery,
        laytpl = layui.laytpl,
        table = layui.table;

    
    var tableIns = table.render({
        elem: '#InformationList',
        method:'post',
        url : '/informationtable/getinformationlist',
        cellMinWidth : 95,
        page : true,
       // height : "full-125",
        limits : [10,15,20,25],
        limit : 12,
        id : "informationListTable",
        cols : [[
        	{field: 'informationId', title: '编号', width:80, align:"center"},
            {field: 'informationTitle', title: '标题', minwidth:600, align:"center"},
            {field: 'informationIsIndex', title: '是否首页展示',width:200, align:"center",templet:function(d){
                return d.informationIsIndex == "2" ? "是" : "否";
            }},
            {field: 'informationAuthor', title: '作者', width:100, align:"center"},
            {field: 'createTime', title: '发布时间', minwidth:100, align:"center",sort:true},
            {title: '操作', minwidth:100, templet:'#informationListBar',fixed:"right",align:"center"}
        ]]
    });

    //搜索【此功能需要后台配合，所以暂时没有动态效果演示】
    $(".search_btn").on("click",function(){
    	var informationTitle = $('.informationTitle').val();
    	var informationAuthor = $('.informationAuthor').val();
    	var informationType = $('.informationType').val();
        table.reload("informationListTable",{
                page: {
                    curr: 1 //重新从第 1 页开始
                },
                where: {
                	informationTitle: informationTitle,
                	informationAuthor:informationAuthor,
                	informationType:informationType,
                }
        })
    });

    
    function addInformation(){
        var index = layui.layer.open({
            title : '添加资讯',
            type : 2,
            content : "/informationtable/addinfomation",
            success : function(layero, index){
                setTimeout(function(){
                    layui.layer.tips('点击此处返回资讯列表', '.layui-layer-setwin .layui-layer-close', {
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
    
    function editInformation(data){
    	 var index = layui.layer.open({
             title : '编辑资讯',
             type : 2,
             content : "/informationtable/updateinformation",
             success : function(layero, index){
                 var body = layui.layer.getChildFrame('body', index);
                 body.find(".informationId").val(data.informationId);
                 body.find(".informationTitle").val(data.informationTitle);
                 body.find(".informationAuthor").val(data.informationAuthor);
                 body.find(".informationType option[value="+data.informationType+"]").prop("selected","selected"); 
                 body.find(".informationIsIndex option[value="+data.informationIsIndex+"]").prop("selected","selected"); 
                 body.find(".informationContent").val(data.informationContent);
                 form.render();
                 setTimeout(function(){
                     layui.layer.tips('点击此处返回资讯列表', '.layui-layer-setwin .layui-layer-close', {
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
    	addInformation();
    })
    //列表操作
    table.on('tool(InformationList)', function(obj){
        var layEvent = obj.event,
            data = obj.data;

        if(layEvent === 'edit'){ //编辑
        	editInformation(data);
        }else if(layEvent === 'del'){ //删除
            layer.confirm('确定删除此资讯？',{icon:3, title:'提示信息'},function(index){
                 $.post("/informationtable/delinfomation",{
                	 informationId : data.informationId  
                 },function(data){
                    tableIns.reload();
                    layer.close(index);
                 },'json')
            });
        }
    });

})
