layui.use(['form','layer','table','laytpl'],function(){
    var form = layui.form,
        layer = parent.layer === undefined ? layui.layer : top.layer,
        $ = layui.jquery,
        laytpl = layui.laytpl,
        table = layui.table;

    var tableIns = table.render({
        elem: '#FundingList',
        method:'post',
        url : '/funding/getfundingList',
        cellMinWidth : 95,
        page : true,
       // height : "full-125",
        limits : [10,15,20,25],
        id : "FundingTable",
        limit : 12,
        cols : [[
            {field: 'id', title: '编号', width:100, align:"center"},
            {field: 'multiple', title: '配额', minWidth:100, align:'center'},
            {field: 'profits', title: '利润比', minWidth:100, align:'center'},
            {field: 'operateType', title: '配资类型', minWidth:100, align:'center'},
            {title: '操作', minWidth:175, templet:'#fundingListBar',fixed:"right",align:"center"}
        ]]
    });
    
    $(".addNews_btn").click(function(){
    	addFunding();
    })
    
    function addFunding(){
        var index = layui.layer.open({
            title : '添加配额',
            type : 2,
            content : "/funding/addfunding",
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
    

    function editFunding(data){
    	 var index = layui.layer.open({
             title : '编辑配额',
             type : 2,
             content : "/funding/updateFunding?id="+data.id,
             success : function(layero, index){
                 var body = layui.layer.getChildFrame('body', index);
                 body.find(".multiple option[value="+data.multiple+"]").prop("selected","selected"); 
                 body.find(".profits").val(data.profits);  
                 body.find(".operateType").val(data.operateType);   
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
    //列表操作
    table.on('tool(FundingList)', function(obj){
        var layEvent = obj.event,
            data = obj.data;

        if(layEvent === 'edit'){ //编辑
        	editFunding(data);
        }else if(layEvent === 'del'){ //删除
            layer.confirm('确定删除此角色？',{icon:3, title:'提示信息'},function(index){
                 $.post("/funding/delFunding",{
                	 id : data.id  
                 },function(data){
                    tableIns.reload();
                    layer.close(index);
                 },'json')
            });
        }
    });

})
