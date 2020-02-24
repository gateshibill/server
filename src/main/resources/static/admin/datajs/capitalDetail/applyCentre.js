layui.use(['form','layer','table','laytpl'],function(){
    var form = layui.form,
        layer = parent.layer === undefined ? layui.layer : top.layer,
        $ = layui.jquery,
        laytpl = layui.laytpl,
        table = layui.table;

    //风险控制
    var tableIns = table.render({
        elem: '#moneyList',
        method:'post',
        url : '/capitalDetail/selectapplycentre',
        cellMinWidth : 95,
        page : true,
        //height : "full-125",
        limits : [10,15,20,25],
        limit : 12,
        id : "moneyListTable",
        totalRow: true,
        cols : [[
            {field: 'userName', title: '会员名', align:'center',totalRowText: '合计'},
            {field: 'tradeMoney', title: '交易金额', align:'center',totalRow: true,templet:function (d) {
                    return "<span style='color:red'>"+d.tradeMoney.toFixed(2)+"</span>"
                }},
            {field: 'source', title: '充值方式', align:'center',sort:true,templet:function (d) {
                    return d.source == "1" ? "银行卡":"支付宝";
                }},
            {field: 'userCard', title: '银行卡号', align:'center',templet:function (d) {
                    return  !d.userCard?"":"<span style='color:red'>"+d.userCard+"</span>"

                }},
            {field: 'userPhone', title: '联系方式', align:'center',templet:function (d) {
                    return  !d.userPhone?"":"<span style='color:red'>"+d.userPhone+"</span>"
                }},
            {field: 'userIdentity', title: '身份证', align:'center',sort:true,templet:function (d) {
                    return  !d.userIdentity?"":"<span style='color:red'>"+d.userIdentity+"</span>"
                }},
            {field: 'throughReason', title: '申请理由', align:'center',sort:true,templet:function (d) {
                    return  !d.throughReason?"":"<span style='color:red'>"+d.throughReason+"</span>"
                }},
            {field: 'createTime', title: '创建时间', align:'center',sort:true},
            {title: '操作', minWidth:175, templet:'#moneyListBar',fixed:"right",align:"center"}
        ]]
    });

    //搜索【此功能需要后台配合，所以暂时没有动态效果演示】
    $(".search_btn").on("click",function(){
        var userName = $('.userName').val();
        var adminName = $('.adminName').val();
        var source = $('.source').val();
        var userCard = $('.userCard').val();
        var userPhone = $('.userPhone').val();
        var sTime = $('#startTime').val();
        var eTime = $('#endTime').val();
        table.reload("moneyListTable",{
            page: {
                curr: 1 //重新从第 1 页开始
            },
            where: {
                userName: userName,
                adminName:adminName,
                source: source,
                userCard: userCard,
                userPhone: userPhone,
                sTime: sTime,
                eTime: eTime,
            }
        })
    });

    //添加用户配资
    $(".addNews_btn").click(function(){
        toaddPZ();
    })

    //添加用户配资
    function toaddPZ(){
        var index = layui.layer.open({
            title : '添加用户配资',
            type : 2,
            content : "/capitalDetail/toaddPZ",
            success : function(layero, index){
                setTimeout(function(){
                    layui.layer.tips('点击此处返回充值列表', '.layui-layer-setwin .layui-layer-close', {
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

    function agree(data){
    	$.post('/capitalDetail/updateisdealpass',{
    		id : data.id
    	},function(data){
    		if(data.msg == 'success'){
    			layer.msg('操作成功',{icon:6});
    			tableIns.reload();//刷新表格
    		}else{
    			layer.msg(data.msg,{icon:5});
    			return false;
    		}
    	},'json')
    }
    
    function refuse(id,content){
    	$.post('/capitalDetail/failReason',{
    		id : id,
    		reason:content,
    	},function(data){
    		if(data.msg == 'success'){
    			layer.msg('操作成功',{icon:6});
    			tableIns.reload();//刷新表格
    		}else{
    			layer.msg(data.msg,{icon:5});
    			return false;
    		}
    	},'json')
    }

    /*function quota(id,quota){
        $.post('/capitalDetail/quota',{
            id : id,
            quota:quota,
        },function(data){
            if(data.msg == 'success'){
                layer.msg('操作成功',{icon:6});
                tableIns.reload();//刷新表格
            }else{
                layer.msg(data.msg,{icon:5});
                return false;
            }
        },'json')
    }*/
    //列表操作
    table.on('tool(moneyList)', function(obj){
        var layEvent = obj.event,
            data = obj.data;

        if(layEvent === 'agree'){ //同意
        	layer.confirm('确定同意此会员申请充值？',{icon:3, title:'提示信息'},function(index){
                agree(data);
            });
        }else if(layEvent === 'refuse'){ //拒绝
        	layer.open({
        		  type: 0, 
        		  content: '<div style="width:400px;height:200px;margin-left:0" class="layui-input-block"><textarea style="width:320px;height:200px" id="content" placeholder="请输入拒绝理由" class="layui-textarea"></textarea></div>', //这里content是一个普通的String
        		  btn: ['确定拒绝'],
	       	      yes: function(index, layero){
	       	    	var content = top.$("#content").val();
	        	     refuse(data.id,content);
	        	  }
        	});
        }/*else if(layEvent === 'quota'){
            layer.open({
                type: 0,
                content: '<div style="width:400px;height:200px;margin-left:0" class="layui-input-block"> ' +
                    '<input style="width:320px;height:40px" type="text" id="content" lay-verify="title" autocomplete="off" placeholder="请输入配资比例(3--10)" class="layui-input">' +
                    '</div>', //这里content是一个普通的String
                btn: ['配资'],
                yes: function(index, layero){
                    var content = top.$("#content").val();
                    quota(data.id,content);
                }
            });
        }*/else if(layEvent === 'quota'){
//            layer.open({
//                type: 2,
//                title: '新增配资',
//                shadeClose: true,
//                shade: false,
//                maxmin: true, //开启最大化最小化按钮
//                area: ['100%', '100%'],
//                content:'/capitalDetail/toadd'
//
//            });
        	var index = layui.layer.open({
                title : '配资',
                type : 2,
                area: ['100%', '100%'],
                content:'/capitalDetail/toadd',
                success : function(layero, index){
                	 var body = layui.layer.getChildFrame('body', index);
                     body.find(".id").val(data.id); 
                     form.render();
                    setTimeout(function(){
                        layui.layer.tips('点击此处返回充值申请列表', '.layui-layer-setwin .layui-layer-close', {
                            tips: 3
                        });
                    },500)
                }
            })
            
        }
    });

})
