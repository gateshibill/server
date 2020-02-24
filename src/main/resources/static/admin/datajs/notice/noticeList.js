layui.use(['form','layer','table','laytpl'],function(){
    var form = layui.form,
        layer = parent.layer === undefined ? layui.layer : top.layer,
        $ = layui.jquery,
        laytpl = layui.laytpl,
        table = layui.table;

    
    var tableIns = table.render({
        elem: '#noticeList',
        method:'post',
        url : '/notice/getnoticelist',
        cellMinWidth : 95,
        page : true,
       // height : "full-125",
        limits : [10,15,20,25],
        limit : 12,
        id : "noticeListTable",
        cols : [[
        	{field: 'noticeId', title: '编号', width:80, align:"center"},
            {field: 'noticeTitle', title: '标题', minWidth:100, align:"center"},
            {field: 'isEffect', title: '是否展示',width:150, align:"center",sort:true,templet:function(d){
                return d.isEffect == "1" ? "是" : "否";
            }},
            {field: 'createTime', title: '创建时间', width:200, align:"center",sort:true},
            {title: '操作', minWidth:175, templet:'#noticeListBar',fixed:"right",align:"center"}
        ]]
    });

    //搜索【此功能需要后台配合，所以暂时没有动态效果演示】
    $(".search_btn").on("click",function(){
    	var noticeTitle = $('.noticeTitle').val();
 
        table.reload("noticeListTable",{
                page: {
                    curr: 1 //重新从第 1 页开始
                },
                where: {
                	noticeTitle: noticeTitle,
            
                }
        })
    });

    
    function addNotice(){
        var index = layui.layer.open({
            title : '添加公告',
            type : 2,
            content : "/notice/add",
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
    
    function editNotice(data){
    	 var index = layui.layer.open({
             title : '编辑公告',
             type : 2,
             content : "/notice/update",
             success : function(layero, index){
                 var body = layui.layer.getChildFrame('body', index);
                 body.find(".noticeId").val(data.noticeId);
                 body.find(".noticeTitle").val(data.noticeTitle);
                 body.find(".noticeContent").val(data.noticeContent);
                 body.find(".isEffect option[value="+data.isEffect+"]").prop("selected","selected"); 
                 form.render();
                 setTimeout(function(){
                     layui.layer.tips('点击此处返回公告列表', '.layui-layer-setwin .layui-layer-close', {
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
    	addNotice();
    })
    //列表操作
    table.on('tool(noticeList)', function(obj){
        var layEvent = obj.event,
            data = obj.data;

        if(layEvent === 'edit'){ //编辑
        	editNotice(data);
        }else if(layEvent === 'del'){ //删除
            layer.confirm('确定删除此公告？',{icon:3, title:'提示信息'},function(index){
                 $.post("/notice/delnotice",{
                	 noticeId : data.noticeId  
                 },function(data){
                    tableIns.reload();
                    layer.close(index);
                 },'json')
            });
        }
    });

})
