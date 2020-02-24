layui.use(['form','layer'],function(){
    var form = layui.form
        layer = parent.layer === undefined ? layui.layer : top.layer,
        $ = layui.jquery;

    form.on("submit(addAdmin)",function(data){
    	if(data.field.password != data.field.repassword){
    		layer.msg('两次密码不一致',{icon:5});
    		return false;
    	}
    	
        //弹出loading
        var index = top.layer.msg('数据提交中，请稍候',{icon: 16,time:false,shade:0.8});
        //添加代理
         $.post("/admin/doaddadmin",{
             adminName : data.field.adminName,  //真实姓名
             adminAccount : data.field.adminAccount,  //登录账号
             adminCoding : data.field.adminCoding,  //角色编号
             phone : data.field.phone,  //联系方式
             password : data.field.password,  //密码
             handInto : data.field.handInto,//代理手续费分成
             serviceInto : data.field.serviceInto,//代理利息分成:服务费
             isEffect : data.field.isEffect,    //用户状态
             roleId : data.field.roleId //角色id
         },function(res){
        	 if(res.code == 0){
        		  setTimeout(function(){
        	            top.layer.close(index);
        	            top.layer.msg("代理添加成功！");
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