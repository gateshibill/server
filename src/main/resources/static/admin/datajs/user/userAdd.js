layui.use(['form','layer'],function(){
    var form = layui.form
        layer = parent.layer === undefined ? layui.layer : top.layer,
        $ = layui.jquery;

    form.on("submit(adduser)",function(data){
    	if(!/^[\w]{6,12}$/.test(data.field.password)){
    		layer.msg('密码长度必须为6-12',{icon:5});
    		return false;
    	}
    	if(data.field.password != data.field.repassword){
    		layer.msg('两次密码不一致',{icon:5});
    		return false;
    	}
    	if(!/^[a-zA-Z0-9_-]+@[a-zA-Z0-9_-]+(\.[a-zA-Z0-9_-]+)+$/.test(data.field.userEmail)){
    		layer.msg('邮箱格式不正确',{icon:5});
    		return false;
    	}
    	if(!/^1(3|4|5|6|7|8|9)\d{9}$/.test(data.field.userPhone)){
    		layer.msg('手机号码格式不正确',{icon:5});
    		return false;
    	}
        //弹出loading
        var index = top.layer.msg('数据提交中，请稍候',{icon: 16,time:false,shade:0.8});
        //添加代理
         $.post("/user/adduser",{

             userName : data.field.userName,  //真实姓名
             userPassword : data.field.password,  //密码
             inviterId : data.field.inviterId,	//邀请人id
             userCoding : data.field.userCoding,	//用户编号
             userEmail : data.field.userEmail,	//用户邮箱
             userPhone : data.field.userPhone,	//用户电话
            // userCode : data.field.userCode,	//用户邀请码
         },function(res){
        	 if(res.code == 0){
        		  setTimeout(function(){
        	            top.layer.close(index);
        	            top.layer.msg("会员添加成功！");
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