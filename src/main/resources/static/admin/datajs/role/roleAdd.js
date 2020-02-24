// 需要查看相关DEMO的时候，搜索相关函数即可，比如：全选则搜索 checkAll，列表转树搜索 listConvert
layui.config({
  base: '/static/admin/layui_exts/',
}).extend({
  authtree: 'authtree',
});
layui.use(['form','authtree','layer'],function(){
    var form = layui.form
        layer = parent.layer === undefined ? layui.layer : top.layer,
        authtree = layui.authtree,
        $ = layui.jquery;
    
    // 初始化
    $.ajax({
      url: '/power/getpowerlist',
      type:'post',
      data:{},
      dataType: 'json',
      success: function(data){
        // 渲染时传入渲染目标ID，树形结构数据（具体结构看样例，checked表示默认选中），以及input表单的名字
        authtree.render('#LAY-auth-tree-index', data.data, {
          inputname: 'ids[]'
          ,layfilter: 'lay-check-auth'
          // ,dblshow: true
          // ,dbltimeout: 180
          // ,autoclose: false
          // ,autochecked: false
          // ,openchecked: false
          // ,openall: true   //初始化展示全部
          // ,hidechoose: true
          // ,checkType: 'radio'
          // ,checkSkin: 'primary'
          //,'theme': 'auth-skin-default'
          //,'themePath': '/static/admin/layui_exts/tree_themes/'
          ,autowidth: true
        });
        authtree.on('change(lay-check-auth)', function(data) {
            console.log('监听 authtree 触发事件数据', data);
            // 获取所有节点
            var all = authtree.getAll('#LAY-auth-tree-index');
            // 获取所有已选中节点
            var checked = authtree.getChecked('#LAY-auth-tree-index');
            // 获取所有未选中节点
            var notchecked = authtree.getNotChecked('#LAY-auth-tree-index');
            // 获取选中的叶子节点
            var leaf = authtree.getLeaf('#LAY-auth-tree-index');
            // 获取最新选中
            var lastChecked = authtree.getLastChecked('#LAY-auth-tree-index');
            // 获取最新取消
            var lastNotChecked = authtree.getLastNotChecked('#LAY-auth-tree-index');
//            console.log(
//              'all', all,"\n",
//              'checked', checked,"\n",
//              'notchecked', notchecked,"\n",
//              'leaf', leaf,"\n",
//              'lastChecked', lastChecked,"\n",
//              'lastNotChecked', lastNotChecked,"\n"
//            );
    });
        authtree.on('deptChange(lay-check-auth)', function(data) {
            //console.log('监听到显示层数改变',data);
        });
        authtree.on('dblclick(lay-check-auth)', function(data) {
            //console.log('监听到双击事件',data);
          });
        },
        error: function(xml, errstr, err) {
          layer.alert(errstr+'，获取样例数据失败，请检查是否部署在本地服务器中！');
        }
      });
    form.on("submit(addRole)",function(data){
    	var authids = authtree.getChecked('#LAY-auth-tree-index'); //权限,一维数组
    	var rolePower = "";
    	if(authids){
    		rolePower = JSON.stringify(authids).replace(/\"/g, "");
    		rolePower = rolePower.substring(1,rolePower.length-1);
    	}   	
        //弹出loading
        var index = top.layer.msg('数据提交中，请稍候',{icon: 16,time:false,shade:0.8});
        //添加角色
         $.post("/role/doaddrole",{
             roleName : data.field.roleName,  //角色名称
             isEffect : data.field.isEffect,    //状态
             rolePower : rolePower, // 角色所选择权限
         },function(res){
        	 if(res.code == 0){
        		  setTimeout(function(){
        	            top.layer.close(index);
        	            top.layer.msg("添加角色成功！");
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