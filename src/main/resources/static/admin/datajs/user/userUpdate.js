layui.use(['form','layer'],function(){
    var form = layui.form
        layer = parent.layer === undefined ? layui.layer : top.layer,
        $ = layui.jquery;

    form.on("submit(updateuser)",function(data){
        //弹出loading
        var index = top.layer.msg('数据提交中，请稍候',{icon: 16,time:false,shade:0.8});
        //添加代理
         $.post("/user/updateuser",{
        	 userId : data.field.userId,  //会员Id
        	 userName : data.field.userName,  //真实姓名
        	 userPassword : data.field.userPassword,  //用户密码
        	 inviterId : data.field.inviterId,	//邀请人id
			 userCoding : data.field.userCoding,	//用户编号
        	 userEmail : data.field.userEmail,	//用户邮箱
        	 userPhone : data.field.userPhone,	//手机号码
        	 memberState : data.field.memberState,	//会员状态
        	 userTransationCode : data.field.userTransationCode,	//管用户交易密码
        	 isCertification : data.field.isCertification,	//是否实名
        	// userCode : data.field.userCode,	//邀请码
        	// codeImg : data.field.codeImg,	//邀请二维码
        	 userType : data.field.userType,	//类型
         },function(res){
        	 if(res.code == 0){
        		  setTimeout(function(){
        	            top.layer.close(index);
        	            top.layer.msg("会员编辑成功！");
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