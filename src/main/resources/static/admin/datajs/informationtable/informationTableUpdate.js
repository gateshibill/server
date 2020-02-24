layui.use(['form','layer'],function(){
    var form = layui.form
        layer = parent.layer === undefined ? layui.layer : top.layer,
        $ = layui.jquery;

    form.on("submit(updateinformation)",function(data){
        //弹出loading
        var index = top.layer.msg('数据提交中，请稍候',{icon: 16,time:false,shade:0.8});
        //添加代理
         $.post("/informationtable/updateinformation",{
        	 informationId : data.field.informationId,  
        	 informationTitle : data.field.informationTitle,  
        	 informationAuthor : data.field.informationAuthor,  
        	 informationType : data.field.informationType,	
        	 informationIsIndex : data.field.informationIsIndex,
        	 informationContent : data.field.informationContent,	
         },function(res){
        	 if(res.code == 0){
        		  setTimeout(function(){
        	            top.layer.close(index);
        	            top.layer.msg("资讯编辑成功！");
        	            layer.closeAll("iframe");
        	            //刷新父页面
        	            parent.location.reload();
        	        },500);
        	 }else{
        		 layer.msg(res.msg,{icon:5});
        	 }
         },'json')
        return false;
    })

})