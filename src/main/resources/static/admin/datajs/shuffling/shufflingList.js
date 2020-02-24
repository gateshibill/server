layui.use(['form','layer','table','laytpl'],function(){
    var form = layui.form,
        layer = parent.layer === undefined ? layui.layer : top.layer,
        $ = layui.jquery,
        laytpl = layui.laytpl,
        table = layui.table;

    
    var tableIns = table.render({
        elem: '#shufflingList',
        method:'post',
        url : '/shuffling/getfigurefist',
        cellMinWidth : 95,
        page : true,
        //height : "full-125",
        limits : [10,15,20,25],
        limit :12,
        id : "shufflingListTable",
        cols : [[
        	{field: 'shufflingId', title: '编号', width:80, align:"center"},
            {field: 'shufflingImg', title: '图片路径', minWidth:100, align:"center"},
            {field: 'shufflingIndex', title: '图片顺序',width:150, align:"center",sort:true},
            {field: 'shufflingHref', title: '图片链接',width:150, align:"center",sort:true},
            {field: 'createTime', title: '创建时间', width:200, align:"center",sort:true},
            {title: '操作', minWidth:175, templet:'#shufflingListBar',fixed:"right",align:"center"}
        ]]
    });
  
    function addShuffling(){
        var index = layui.layer.open({
            title : '添加轮播图',
            type : 2,
            content : "/shuffling/addfigure",
            success : function(layero, index){
                setTimeout(function(){
                    layui.layer.tips('点击此处返回广告列表', '.layui-layer-setwin .layui-layer-close', {
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
    
    function editShuffling(data){
    	 var index = layui.layer.open({
             title : '编辑轮播图',
             type : 2,
             content : "/shuffling/updatefigure",
             success : function(layero, index){
                 var body = layui.layer.getChildFrame('body', index);
                 body.find(".shufflingId").val(data.shufflingId);
                 body.find(".shufflingHref").val(data.shufflingHref);
                 body.find(".shufflingIndex").val(data.shufflingIndex);
                 body.find(".uploadImg").attr('src',data.shufflingImg); 
                 form.render();
                 setTimeout(function(){
                     layui.layer.tips('点击此处返回轮播图列表', '.layui-layer-setwin .layui-layer-close', {
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
    	addShuffling();
    })
    //列表操作
    table.on('tool(shufflingList)', function(obj){
        var layEvent = obj.event,
            data = obj.data;

        if(layEvent === 'edit'){ //编辑
        	editShuffling(data);
        }else if(layEvent === 'del'){ //删除
            layer.confirm('确定删除此轮播图？',{icon:3, title:'提示信息'},function(index){
                 $.post("/shuffling/delfigure",{
                	 figureId : data.shufflingId  
                 },function(data){
                    tableIns.reload();
                    layer.close(index);
                 },'json')
            });
        }
    });

})
