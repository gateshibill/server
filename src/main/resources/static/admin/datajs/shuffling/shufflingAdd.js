layui.use(['form','layer','upload'],function(){
    var form = layui.form,
    	upload = layui.upload,
        layer = parent.layer === undefined ? layui.layer : top.layer,
        $ = layui.jquery;
    
    //上传轮播图
    upload.render({
        elem: '.imgPic',
        url: '/uploadfile/uploadimg',
        method : "post",  //此处是为了演示之用，实际使用中请将此删除，默认用post方式提交
        done: function(res, index, upload){
            $('#uploadImg').attr('src',res.object);
        }
    });
    
    /**
     * 新增轮播图
     */
    form.on("submit(addshuffling)",function(data){
    	if(!/(http|https):\/\/([\w.]+\/?)\S*/.test(data.field.shufflingHref)){
    		layer.msg('图片跳转链接格式不正确',{icon:5});
    		return false;
    	}
    	var shufflingImg = $('#uploadImg').attr('src');
    	if(!shufflingImg){
    		layer.msg('请上传图片',{icon:5});
    		return false;
    	}
        //弹出loading
        var index = top.layer.msg('数据提交中，请稍候',{icon: 16,time:false,shade:0.8});
        //添加代理
         $.post("/shuffling/addfigure",{
        	 shufflingHref : data.field.shufflingHref,  
        	 shufflingIndex : data.field.shufflingIndex,
        	 shufflingImg : shufflingImg,
         },function(res){
        	 if(res.code == 0){
        		  setTimeout(function(){
        	            top.layer.close(index);
        	            top.layer.msg("添加轮播图成功！");
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