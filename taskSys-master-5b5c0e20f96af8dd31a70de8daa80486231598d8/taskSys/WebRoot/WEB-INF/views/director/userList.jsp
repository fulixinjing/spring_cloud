<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/common/taglibs.jsp" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.0 Transitional//EN"> 
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<%@ include file="/common/meta.jsp" %>
<title>用户选择列表</title>
<link rel="stylesheet" type="text/css" href="${ctx}/css/common.css" />
<link rel="stylesheet" type="text/css" href="${ctx}/css/right.css" />
<script type="text/javascript" src="${ctx}/js/jquery.min.js"></script>
<script type="text/javascript" src="${ctx}/js/right_js.js"></script>
<style type="text/css">
	fieldset {
		padding:10px;
		margin:10px;
		
		color:#333; 
		border:#06c dashed 1px;
	} 
	legend {
		color:#06c;
		font-weight:800; 
		background:#fff;
	} 
</style>
<script>
	/* $(function() { 
		$("#userId").click(function() {
			if (this.checked) {
				$("input[name='userId']").each(function() {
					$(this).attr("checked", true);
				});
			} else {
				$("input[name='userId']").each(function() {
					$(this).attr("checked", false);
				});
			}
		});
	}); */
	
	//确定按钮，返回
	function okBtnClick(flag){
		var chk_value =[];  
	  	$('input[name="userId"]:checked').each(function(){  
	   		chk_value.push($(this).val());  
	  	});  
		  
	    if (chk_value.length>1) {
	    	alert('只能选择一位用户！');
	    	return false;
	    }
	    //alert(chk_value);
	    //谁调用我，我就调用谁的callback方法
	    parent.selectSingleUser_callback(chk_value,flag);
	    parent.$.colorbox.close();
	}
	function queryUser(){
		var flag = $("#flag").val();
		var username = $("#username").val();
		$.ajax({
			type : "POST", 
			url : "${ctx}/director/ajaxUserList.do",
			data : {"flag":flag,"username":username},
			dataType : "json", 
			success : function(data){
				var str = "";
				if(data.userList!=null && data.userList.length>0){
					for ( var i = 0; i < data.userList.length; i++) {
						if(!(data.userId==(data.userList[i]).userId && data.userRole=='boss')){ //高管自己不能给自己分配 
							if(!(data.userId==(data.userList[i]).userId && data.userRole=='director')){//主管向下分配自己不能给自己分配
								if(!(data.userId=='888999' || (data.userList[i]).userName=='超级管理员')){//过滤掉超级管理和id=888999的用户
									var appStr1="<tr><td><input type=\"radio\" name=\"userId\" value=\""+(data.userList[i]).userId+","+(data.userList[i]).userName+"\"></td>";
									var appStr2="<td>"+(data.userList[i]).userName+"</td>";
									var appStr3="<td>"+(data.userList[i]).department_Name+"</td>";
									var appStr4="<td>"+(data.userList[i]).team_Name+"</td></tr>";
									str=str+appStr1+appStr2+appStr3+appStr4;
								}
							}
						}
					}
				}
				$("#tableID2").html(str);
			}
    	}); 
	}
	$(document).ready(function(){
		$("#username").focus();
	});
</script>
</head>

<body>
<div class="infor_base">
  	<div class="role_side" style="height:280px;overflow: auto;">
      	<form id="queryFormId" action="${ctx}/director/userList.do" method="post">
	      	<fieldset>
				  <legend>用户查询</legend>
				  <label for="username">姓名:</label>
				  <input type="hidden" id="flag" name="flag" value="${flag }"/>
				  <input type="text" name="username" id="username" value="${username }" placeholder="请输入姓名" style="height:21px;" onkeyup="queryUser()"/>
				  <%--<input class="form_now" type="button" onclick="queryUser()" value="查询">--%>
			  </fieldset>
      	</form>
      	<table id="tableID" width="100%" cellpadding="0" cellspacing="0">
      		<thead>
	          	<tr>
	              	<th width="30"></th>
	              	<th>姓名</th>
	              	<th>部门名称</th>
	              	<th>团队名称</th>
	          	</tr>
          	</thead>
          	<tbody id="tableID2">
				<c:forEach items="${userList}" var="user" varStatus="status">
		     	<c:if test="${!(userId==user.userId && userRole=='boss') }"><!-- 高管自己不能给自己分配 -->
		     	<c:if test="${!(userId==user.userId && userRole=='director') }"><!-- 主管向下分配自己不能给自己分配 -->
		     	<c:if test="${!(user.userId=='888999' || user.userName=='超级管理员') }"><!-- 过滤掉超级管理和id=888999的用户 -->
		     	<tr>
		     		<td><input type="radio" name="userId" value="${user.userId},${user.userName}"></td>
		     		<td>${user.userName}</td>
		     		<td>${user.department_Name}</td>
		     		<td>${user.team_Name}</td>
		     	</tr>
		     	</c:if>
		     	</c:if>
		     	</c:if>
		    	</c:forEach>
	    	</tbody>
	    	<!-- <tr align="center">
               	<td colspan="10"><input class="form_now" type="button" onclick="okBtnClick()" value="选择带回"></td>
            </tr> -->
      	</table>
	</div>
	<table width="100%" cellpadding="0" cellspacing="0">
		<tr align="center">
            <td colspan="10"><input class="form_now" type="button" onclick="okBtnClick('${flag}')" value="确定"></td>
         </tr>
   	</table>	
</div>
</body>
</html>