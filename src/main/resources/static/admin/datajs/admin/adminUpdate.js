layui.use(['form','layer'],function(){
    var form = layui.form
        layer = parent.layer === undefined ? layui.layer : top.layer,
        $ = layui.jquery;
    /**
     * 编辑管理员
     */
    form.on("submit(updateAdmin)",function(data){
        //弹出loading
        var index = top.layer.msg('数据提交中，请稍候',{icon: 16,time:false,shade:0.8});
        //更新代理
         $.post("/admin/doupdateadmin",{
             adminName : data.field.adminName,  //真实姓名
             adminAccount : data.field.adminAccount,  //登录账号
             adminCoding : data.field.adminCoding,  //登录编号
             phone : data.field.phone,  //联系方式
             serviceInto : data.field.serviceInto,//代理服务费分成
             handInto : data.field.handInto,//代理手续费分成
             isEffect : data.field.isEffect,    //用户状态
             adminId : data.field.adminId,//管理员id
         },function(res){
        	 if(res.code == 0){
        		  setTimeout(function(){
        	            top.layer.close(index);
        	            top.layer.msg("代理编辑成功！");
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
    /**
     * 编辑角色
     */	
    form.on("submit(updateRole)",function(data){
        //弹出loading
        var index = top.layer.msg('数据提交中，请稍候',{icon: 16,time:false,shade:0.8});
        //添加代理
         $.post("/admin/dosetrole",{
             roleId : data.field.roleId,    //角色id
             adminId : data.field.adminId,//管理员id
         },function(res){
        	 if(res.code == 0){
        		  setTimeout(function(){
        	            top.layer.close(index);
        	            top.layer.msg("更新角色成功！");
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