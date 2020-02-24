layui.use(['form','layer','jquery','upload'],function(){
	var form = layui.form,
		layer = parent.layer === undefined ? layui.layer : top.layer,
		upload = layui.upload,
		$ = layui.jquery;
	 //上传支付宝二维码
    upload.render({
        elem: '#alipayPic',
        url: '/uploadfile/uploadimg',
        method : "post",  //此处是为了演示之用，实际使用中请将此删除，默认用post方式提交
        done: function(res, index, upload){
            $('#uploadAlipay').attr('src',res.object).css('display','block');
        }
    });
    //上传轮播图
    upload.render({
        elem: '#wechatPic',
        url: '/uploadfile/uploadimg',
        method : "post",  //此处是为了演示之用，实际使用中请将此删除，默认用post方式提交
        done: function(res, index, upload){
            $('#uploadWechat').attr('src',res.object).css('display','block');;
        }
    });
 	form.on("submit(systemParameter)",function(data){
 		var wechat = $('#uploadWechat').attr('src');
 		var alipay = $('#uploadAlipay').attr('src');
 		//弹出loading
 		var index = top.layer.msg('数据提交中，请稍候',{icon: 16,time:false,shade:0.8});
 		$.post('/sysconfig/updatesysconfig',{
 			sysId:data.field.sysId,
 			hyfkWind:data.field.hyfkWind,
 			yjxWind:data.field.yjxWind,
 			pcxWind:data.field.pcxWind,
 			bzjSet:data.field.bzjSet,
 			//oneAgent:data.field.oneAgent,
 			//twoAgent:data.field.twoAgent,
 			//threeAgent : data.field.threeAgent,
 			rechargeRatio : data.field.rechargeRatio,
 			deferredCharges : data.field.deferredCharges,
			servicePhone : data.field.servicePhone,
			alipay:alipay,
			wechat:wechat,
			bank:data.field.bank,
			account:data.field.account,
			bankName:data.field.bankName,
 		},function(data){
 			if(data.code == 0){
 				setTimeout(function(){
 					layer.msg("设置成功！",{icon:6});
 					layer.close(index);
 		        },500);
 			}else{
 				layer.msg(data.msg,{icon:5});
 			}
 		},'json');
 		return false;
 	})
})
