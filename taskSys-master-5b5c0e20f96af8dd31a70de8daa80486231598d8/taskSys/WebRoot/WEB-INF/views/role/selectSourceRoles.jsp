<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/common/taglibs.jsp" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.0 Transitional//EN"> 
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<%@ include file="/common/meta.jsp" %>
<title>角色选择列表</title>
<link rel="stylesheet" type="text/css" href="${ctx}/css/common.css" />
<link rel="stylesheet" type="text/css" href="${ctx}/css/right.css" />
<script type="text/javascript" src="${ctx}/js/jquery.min.js"></script>
<script type="text/javascript" src="${ctx}/js/right_js.js"></script>

<script>
	$(function() { 
		$("#checkAll").click(function() {
			if (this.checked) {
				$("input[name='roleCode']").each(function() {
					$(this).attr("checked", true);
				});
			} else {
				$("input[name='roleCode']").each(function() {
					$(this).attr("checked", false);
				});
			}
		});
	});
	
	//确定按钮，返回
	function okBtnClick(){
		var chk_value =[];  
	  	$('input[name="roleCode"]:checked').each(function(){  
	   		chk_value.push($(this).val());  
	  	});  
		  
	    if (chk_value.length==0) {
	    	alert('你还没有选择角色！');
	    	return false;
	    }
	    //谁调用我，我就调用谁的callback方法
	    parent.selectMutliRole_callback(chk_value);
	    parent.$.colorbox.close();
	}
</script>
</head>

<body>
<div class="infor_base">
  	<div class="role_side">
      	<table width="100%" cellpadding="0" cellspacing="0">
          	<tr>
              	<th width="30"><input type="checkbox" id="checkAll"></th>
              	<th>角色编码</th>
              	<th>角色名称</th>
              	<th>角色描述</th>
          	</tr>
			<c:forEach items="${roles}" var="role" varStatus="status">
	     	<tr>
	     		<td><input type="checkbox" name="roleCode" value="${role.id},${role.roleName}" <c:if test="${role.checked == true}">checked</c:if> ></td>
	     		<td>${role.roleCode}</td>
	     		<td>${role.roleName}</td>
	     		<td>${role.description}</td>
	     	</tr>
	    	</c:forEach>
	    	<tr align="center">
               	<td colspan="10"><input class="form_now" type="button" onclick="okBtnClick()" value="选择带回"></td>
            </tr>
      	</table>
	</div>	
</div>
</body>
</html>