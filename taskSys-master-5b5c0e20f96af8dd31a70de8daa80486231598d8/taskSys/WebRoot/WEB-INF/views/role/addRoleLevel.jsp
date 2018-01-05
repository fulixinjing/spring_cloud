<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/common/taglibs.jsp" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.0 Transitional//EN"> 
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<%@ include file="/common/meta.jsp" %>
<title>薪酬绩效管理系统</title>
<link rel="stylesheet" type="text/css" href="${ctx}/css/common.css" />
<link rel="stylesheet" type="text/css" href="${ctx}/css/right.css" />
<link rel="stylesheet" type="text/css" href="${ctx}/css/colorbox.css"/>
<link rel="stylesheet" type="text/css" href="${ctx}/css/pop.css" />
<script type="text/javascript" src="${ctx}/js/jquery.min.js"></script>
<script type="text/javascript" src="${ctx}/js/right_js.js"></script>
<script type="text/javascript" src="${ctx}/js/jquery.metadata.js"></script>
<script type="text/javascript" src="${ctx}/js/jquery.validate.js"></script>
<script language="javascript" src="${ctx}/components/colorbox/jquery.colorbox.js"></script>
</head>

<body>
<form action="${ctx}/roletree/addrole.do" method="POST" id="saveForm" name="saveForm">
<input type="hidden" id="roleId" name="roleId" >
<input type="hidden" id="jxSource" name="jxSource">
<input type="hidden" id="parentRoleName" name="parentRoleName">
<div class="TAB_right">
	<h1><b>位置：</b><a href="###">首页</a><span>&gt;</span><a href="###">新增角色</a></h1>
	<div class="TAB_right_content">
		<div class="jur_add">
		  <div class="btn_operate">
		      <ul>
		          <li><a id="btn_submit" href="#"><span>新增角色</span></a></li>		          
		      </ul>
		  </div>
		  <div class="ADD_tree">
		  <input type="hidden" name="id" />
		      <table class="Table_forms" cellpadding="0" cellspacing="0">
                <tr>
					<td><label><span>*</span>角色来源：</label> 
					<select id ="source" name="source" >
						<option value="0" >绩效</option>
						<option value="1" selected>汇金</option>
						<option value="2">财富</option>
					</select>
					</td>	                
                    <td>                        
	                    <label id="sourceRoleName"><span>*</span>角色：</label>	                    
	                    <textarea class="infor_base" id="roleName" name="roleName" readonly="readonly" ></textarea>
						 <input class="form_now" type="button"  onclick="javascript:showRoleDialog()" value="选择..">
					</td>  				              		
                </tr>          	
                <tr>
                	<td><label><span>*</span>上一级角色：</label>                	
                    <select id="parentRoleId" name="parentRoleId">
                    	<option value="" >请选择</option>
                    </select></td>  
					<td><label><span>*</span>类型：</label> 
					<select id ="type" name="type">
						<option value="" >请选择</option>
						</select>
					</td>					
				</tr>
				<tr>
					<td><label><span>*</span>编码：</label>
					  <input maxlength="30" type="text" value="" name="code">
					</td>   	
					<td><label><span>*</span>排序：</label>
					  <input type="text" value="" name="sort">
					</td>  								
                <tr>
                	<td>&nbsp;</td>
                	<td>&nbsp;</td>
                </tr>
                 <tr align="center">
                	<td colspan="2">
                		<input class="form_now" type="submit" value="保存">&nbsp;&nbsp;&nbsp;&nbsp;
                		<input class="form_now" type="button" onclick="javascript:history.back()" value="返回">&nbsp;&nbsp;&nbsp;&nbsp;
                	</td>
                </tr>
		     </table>
		  </div>
		</div>
	</div>
</div>
</form>
</body>
<script>
$(document).ready(function(){
        var dbSource ="${dbSource}";
		if (dbSource == "0"){
			$("#source").attr("value","0"); 	
		}else if(dbSource=="1"){
			$("#source").attr("value","1"); 	
			$("#source").attr("disabled","disabled");  
		}else if(dbSource == "2"){
			$("#source").attr("value","2"); 	
			$("#source").attr("disabled","disabled");  
		}
		$("#jxSource").val($("#source").val());
		url = "${ctx}/roletree/getParentRole.do";
		$.ajax({ 
			type : "POST",  
			url: url, 
			data :  {source:$("#source").val()},
			dataType : 'json',
			success: function(data,status){
				var cnm = data.parentRoleId;
				var ctm=cnm.split(",");
				$.each(ctm,function(index,b){
					var cnd = b.split("_");
					var str = "<option value='"+cnd[0]+"'>"+cnd[1]+"</option>";
    				$("#parentRoleId").append(str);
    				$("#parentRoleName").val($("#parentRoleId option:selected").text());
   				});
      		}
      	});
  
		$("#type").find("option").remove();
		url = "${ctx }/roletree/getType.do";
		$.ajax({ 
			type : "POST",  
			url: url, 
			data : "",
			dataType : 'json',
			success: function(data,status){
				var cnm = data.type;
				var ctm=cnm.split(",");
				$.each(ctm,function(index,b){
					var cnd = b.split("_");
					var str = "<option value='"+cnd[0]+"'>"+cnd[1]+"</option>";
    				$("#type").append(str);
   				});
      		}
      	});
      	      	
	$("#saveForm").validate({  
		rules:{
			roleName:{
				required:true
			},
			parentRoleId:{
				required:true
			},
			code:{
				required:true
			}
		}, 
		messages:{  
	 		roleName:{  
	 			required: "角色不能为空！"
	 		},
	 		parentRoleId:{  
	 			required: "上一级角色不能为空！"
	 		},
	 		code:{
	 			required:"编码不能为空！"
	 		}
		}
	});
});
function showRoleDialog(){
	    var url= "${ctx}/roletree/selectRoles.do?source="+$("#source").val();
	    $.colorbox({
	        href:url,
	        iframe:true,
	        width:"600",
	        height:"450"
	    });
}
function selectMutliRole_callback(ret){		
	var names = "";
	var codes = "";
	for (var i in ret) {
		var tmp = ret[i].split(",");
		names += tmp[1] + "\n";
		codes += tmp[0] + ",";
	}
	$("#roleName").text(names);
	$("#roleId").val(codes);
}
$("#parentRoleId").blur(function(){
	$("#parentRoleName").val($("#parentRoleId option:selected").text());
})
$("#source").change(function(){
	$("#roleName").empty();
	url = "${ctx}/roletree/getParentRole.do";
	$.ajax({ 
		type : "POST",  
		url: url, 
		data :  {source:$("#source").val()},
		dataType : 'json',
		success: function(data,status){
			var cnm = data.parentRoleId;
			var ctm=cnm.split(",");
			$("#parentRoleId").empty();
			$.each(ctm,function(index,b){
				var cnd = b.split("_");
				var str = "<option value='"+cnd[0]+"'>"+cnd[1]+"</option>";
    			$("#parentRoleId").append(str);
    			$("#parentRoleName").val($("#parentRoleId option:selected").text());
   			});
      		}
      	});
      	$("#jxSource").val($("#source").val());
})
</script>
</html>