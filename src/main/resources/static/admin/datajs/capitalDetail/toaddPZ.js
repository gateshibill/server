layui.use(['form','layer','laydate'],function(){
    var form = layui.form
    layer = parent.layer === undefined ? layui.layer : top.layer,
    $ = layui.jquery,
    laydate = layui.laydate;
    
    var count=0;
    
  //日期范围
    laydate.render({
        elem: '#startTime',
        type: 'datetime',
        format: 'yyyy-MM-dd',
        done: function (value, date, endDate) {
        	var endTime = new Date($('#endTime').val()).getTime();
        	var startDate = new Date(value).getTime();
        	if (endTime < startDate) {
        		layer.msg('结束时间不能小于开始时间',{icon:5});
        		//$('endTime').val($('#startTime').val());
        		//return false;
        		$("#startTime").val('');
        		return count=1;
        	}else{
    			return count=0;
    		}
        }
    });
    //日期范围
    laydate.render({
        /*elem: '#endTime'
        ,type: 'datetime'*/
    	elem: '#endTime',
    	type: 'datetime',
    	format: 'yyyy-MM-dd',
    	done: function (value, date, endDate) {
//    		var endTime = new Date(value).getTime();
//    		var startDate = new Date($('#endTime').val()).getTime();
//    		if (endTime < startDate) {
//    			layer.msg('结束时间不能小于开始时间');
//    			$('#startTime').val($('#endTime').val());
//    		}
    		var endTime = new Date(value).getTime();
    		var startDate = new Date($('#startTime').val()).getTime();
    		if (endTime < startDate) {
    			layer.msg('结束时间不能小于开始时间',{icon:5});
    			//$('#qBeginTime').val($('#qEndTime').val());
    			//return false;
    			$("#endTime").val('');
    			return count=1;
    		}else{
    			return count=0;
    		}
    	}
    });
    
    $("#ripei").show();
    form.on('radio(peizhi)', function(data){
    	  if(data.value == 2){
    		  $("#yuepei").show();
    		  $("#ripei").hide();
    	  }else{
    		  $("#yuepei").hide();
    		  $("#ripei").show();
    	  }
    });  
    form.on("submit(addDetails)",function(data){
    	if(!/^1(3|4|5|6|7|8|9)\d{9}$/.test(data.field.phone)){
    		layer.msg('手机号码格式不正确',{icon:5});
    		return false;
    	}
    	if(count>0){
    		layer.msg('结束时间不能小于开始时间',{icon:5});
    		return false;
    	}
        //弹出loading
        var index = top.layer.msg('数据提交中，请稍候',{icon: 16,time:false,shade:0.8});
        //添加
        $.post("/capitalDetail/quotaPZ",{
        	id : data.field.id,//id
            phone : data.field.phone, //手机号
            money : data.field.money, //充值金额
            quota1 : data.field.quota1,  //日配配资
            quota2 : data.field.quota2,  //月配配资
            // bzjMoney : data.field.bzjMoney, //配资金额
        	type : data.field.type,  //操盘类型
            startTime : data.field.startTime,	//开始时间
            endTime : data.field.endTime,	//结束时间
        },function(res){
            if(res.code == "0"){
                setTimeout(function(){
                    top.layer.close(index);
                    top.layer.msg("添加成功！");
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