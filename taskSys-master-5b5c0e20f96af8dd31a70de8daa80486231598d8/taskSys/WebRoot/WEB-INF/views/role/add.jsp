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
<form action="${ctx}/role/addrole.do" method="POST" id="saveForm" name="saveForm">
<input type="hidden" id="rightCodes" name="rightCodes" value="${rightlistcode}">
<input type="hidden" id="sourceCodes" name="sourceCodes" >
<input type="hidden" id="source" name="source" >
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
                    <td><label><span>*</span>角色编码：</label><input maxlength="15" type="text" value="" name="roleCode"><span></span></td>
                    <td><label><span>*</span>角色名称：</label><input maxlength="15" type="text" value="" name="roleName" 
                    class="{required:true, minlength:3, maxlength:15,messages:{required:'角色名称不能为空！', minlength:'不能少于三个字符！',maxlength:'不能大于15个字符！'}}"><span></span></td>
                </tr>
                <tr>
                	<td><label><span>*</span>角色描述：</label><input type="text" value="" name="description"
                		class="{required:true, minlength:3, maxlength:15,messages:{required:'描述不能为空！', minlength:'不能少于三个字符！',maxlength:'不能大于15个字符！'}}"><span></span></td>
                	<td><label><span>*</span>状       态：</label><select name="status"  >
                    	<option value="0">在用</option>
                  		<option value="1">历史</option></select></td>
                </tr>
                <c:if test="${source!=0}">
                <%--<tr>
                	<td><label><span>*</span>上一级角色：</label>                	
                    <select id="parentRoleCode" name="parentRoleCode">
                    	<option value="" >请选择</option>
                    </select></td>
                    <td>                        
	                    <label id="sourceRoleName"><span>*</span>
	                    <c:if test="${source == 0}">绩效角色：</c:if>
	                    <c:if test="${source == 1}">汇金事业部角色：</c:if>
	                    <c:if test="${source == 2}">财富事业部角色：</c:if>                    
	                    </label>	                    
	                    <textarea class="infor_base" id="sourceRole" name="sourceRole" readonly="readonly" ></textarea>
						 <input class="form_now" type="button"  onclick="javascript:showSourceRoleDialog('')" value="选择..">
					</td>                  		
                </tr>    
                --%></c:if>            	
                 <tr>
                    <td>
	                    <label><span>*</span>拥有权限：</label>
	                    <textarea class="infor_base"  id ="quanxi" name="quanxi" readonly="readonly" ></textarea>
						 <input class="form_now" type="button" onclick="javascript:showModalDialog('')" value="查询权限">
					</td>
					<%--<td><label><span>*</span>角色来源：</label> 
								<select id ="jxSource" name="jxSource" disabled="disabled">
										<option value="0" selected="selected">绩效</option>
										<option value="1">汇金</option>
										<option value="2">财富</option>
								</select>
					</td>
				</tr>--%>
                <tr>
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
function showModalDialog(rolecode){
    var url= "${ctx}/role/allright/"+rolecode+".do";
    $.colorbox({
        href:url,
        iframe:true,
        width:"300",
        height:"630",
        opacity:"0.3"
    });
}
function showModalDialog_callback(ret){
	var codes = "";
	var names = "";
	for (var i in ret) {
		var tmp = ret[i].split(",");
		//codes += tmp[0] + ",";
		codes +="'"+ tmp[0] + "',";
		names += tmp[1] + "\n";
	}
	$("#quanxi").text(names);
	$("#rightCodes").val(codes);
}

$(document).ready(function(){
        var jxSource ="${source}";
		if (jxSource == "0"){
			$("#jxSource").attr("value","0"); 	
		}else if(jxSource=="1"){
			$("#jxSource").attr("value","1"); 	
		}else if(jxSource == "2"){
			$("#jxSource").attr("value","2"); 	
		}
		$("#source").val($("#jxSource").val());
		url = "${ctx}/role/getParentRole.do";
		$.ajax({ 
			type : "POST",  
			url: url, 
			data : {jxSource:$("#jxSource").val()},
			dataType : 'json',
			success: function(data,status){
				var cnm = data.parentRoleCode;
				var ctm=cnm.split(",");
				$.each(ctm,function(index,b){
					var cnd = b.split("_");
					var str = "<option value='"+cnd[0]+"'>"+cnd[1]+"</option>";
    				$("#parentRoleCode").append(str);
   				});
      		}
      	});
      	
	jQuery.validator.addMethod("stringCheck", function(value, element) {       
	    return this.optional(element) ||  /^[u4E00-u9FA5]+$/.test(value);       
	 }, "只能输入包括英文字母、数字和下划线");
	
	
	$("#saveForm").validate({  
		rules:{  
			roleCode:{  
	     		required: true,
	     		minlength: 4,
	     		maxlength: 15,
	    		remote: {
	       			url: "${ctx}/role/check/roleCode.do",
	             	type: "post",
	             	data: {  
	             		rolecode:function() {
	                 		return $("#roleCode").val();
	              		}  
	          		}
	       		},
	       		stringCheck: true
			},
			quanxi:{  
	     		required: true
			},
			parentRoleCode:{
				required: true
			},
	 		sourceRole:{
	 			required: true
	 		}			
		},  
		messages:{  
	 		roleCode:{  
	 			required: "角色编码不能为空！",
	 			minlength: "不能少于4位！",
	 			maxlength: "不能多于15位！",
	    		remote: "角色编码已存在，请更换角色编码！",
	    		stringCheck: "只能输入包括英文字母、数字和下划线"
	 		},
	 		quanxi:{  
	 			required: "权限不能为空！"
	 		},
	 		parentRoleCode:{
	 			required: "上一级角色不能为空！"
	 		},	 		
	 		sourceRole:{
	 			required: "来源角色不能为空！"
	 		}
		}
	});
});
function showSourceRoleDialog(){
	    var url= "${ctx}/role/source.do?source="+${source};
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
	$("#sourceRole").text(names);
	$("#sourceCodes").val(codes);
}
$("#parentRoleCode").blur(function(){
	$("#parentRoleName").val($("#parentRoleCode option:selected").text());
})
</script>
</html>