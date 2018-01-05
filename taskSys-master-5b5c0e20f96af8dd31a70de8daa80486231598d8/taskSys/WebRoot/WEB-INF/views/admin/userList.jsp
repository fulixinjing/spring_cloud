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
		var department = $("#department").val();
		var positionId = $("#positionId").val();
		//var team = $("#team").val();
		$.ajax({
			type : "POST", 
			url : "${ctx}/admin/ajaxUserList.do",
			data : {"flag":flag,"username":username,"department":department,"positionId":positionId},
			dataType : "json", 
			success : function(data){
				var str = "";
				if(data.userList!=null && data.userList.length>0){
					for ( var i = 0; i < data.userList.length; i++) {
						if(!(data.userId==(data.userList[i]).userId && data.userRole=='boss')){ //高管自己不能给自己分配 
							if(!((data.userList[i]).userId=='888999' || (data.userList[i]).userName=='超级管理员')){//过滤掉超级管理和id=888999的用户
								var deparmentName = (data.userList[i]).department_Name==null ? "" : (data.userList[i]).department_Name;
								var teamName = (data.userList[i]).team_Name==null ? "" : (data.userList[i]).team_Name;
								var appStr1="<tr><td><input type=\"radio\" name=\"userId\" value=\""+(data.userList[i]).userId+","+(data.userList[i]).userName+","+(data.userList[i]).department_id+"\"></td>";
								var appStr2="<td>"+(data.userList[i]).userName+"</td>";
								var appStr3="<td>"+deparmentName+"</td>";
								var appStr4="<td>"+teamName+"</td></tr>";
								str=str+appStr1+appStr2+appStr3+appStr4;
							}
						}
					}
				}
				$("#tableID2").html(str);
			}
    	}); 
	}
	/* $(document).ready(function(){
		$("#username").focus();
	}); */
	
	//根据所选部门获取其下的团队列表
    function toChnage(id){
		$("#team option:not(:first)").remove();
		url = "${ctx}/user/change/team.do";
		$.ajax({ 
			type : "POST",  
			url: url, 
			data : "code="+id,
			dataType : 'json',
			success: function(data){
				for(var i=0;i<data.list.length;i++){
					$('#team').append('<option value="'+data.list[i].code+'">'+data.list[i].name+'</option>');
					
				}
			}
      		
      	});
      	queryUser();
	}
</script>
</head>

<body>
<div class="infor_base">
  	<div class="role_side" style="height:280px;overflow: auto;">
      	<form id="queryFormId" action="${ctx}/admin/userList.do" method="post">
	      	<fieldset>
			  <legend>用户查询</legend>
			  <label for="username">姓名:</label>
			  <input type="hidden" id="flag" name="flag" value="${flag }"/>
			  <input type="text" name="username" id="username" size="14" value="${username }" placeholder="请输入姓名" style="height:21px;" onkeyup="queryUser()" onblur="queryUser()"/>
			  &nbsp;&nbsp;&nbsp;&nbsp;
			     所属部门：<select name="department" id="department" class="sel" style="width:170px" onchange="queryUser()">
                        <c:if test="${isFenpei=='1' }"><!-- 分配任务 -->
                        <c:choose>
                        	<c:when test="${userRole=='boss' || userRole=='PMO001'  || userRole=='PMUPDATE' }">
                        		<option value="">请选择</option>
				                <c:forEach items="${dictionaryLlist}" var="dic" varStatus="status">
				                  	<c:if test="${dic.type_code=='01' }">
				                  		<option value="${dic.code}" <c:if test="${dic.code==department}">selected</c:if>>${dic.name }</option>
				                  	</c:if>
			                  	</c:forEach>
                        	</c:when>
                        	<c:otherwise>
                        		<c:forEach items="${dictionaryLlist}" var="dic" varStatus="status">
				                  	<c:if test="${dic.type_code=='01' && dic.code==department }">
				                  		<option value="${dic.code}" <c:if test="${dic.code==department}">selected</c:if>>${dic.name }</option>
				                  	</c:if>
			                  	</c:forEach>
                        	</c:otherwise>
                        </c:choose>
                        </c:if>
                        <c:if test="${isFenpei=='0' }"><!-- 转交任务 -->
                        	<c:if test="${userRole=='boss' || userRole=='PMO001' || userRole=='PMUPDATE' }">
                        		<option value="">请选择</option>
                        	</c:if>
			                <c:forEach items="${dictionaryLlist}" var="dic" varStatus="status">
			                  	<c:if test="${dic.type_code=='01' }">
			                  		<option value="${dic.code}" <c:if test="${dic.code==department}">selected</c:if>>${dic.name }</option>
			                  	</c:if>
		                  	</c:forEach>
                        </c:if>
                   	</select>
                   	  &nbsp;&nbsp;&nbsp;&nbsp;
                                                职位级别：<select name="positionId" id="positionId" style="width: 100px;" class="sel" onchange="queryUser()">
                       		<option value="">请选择</option>
                       		<c:forEach items="${dictionaryLlist}" var="dic" varStatus="status">
			                  	<c:if test="${dic.type_code=='03' }">
			                  		<option value="${dic.code}" <c:if test="${dic.code==positionId}">selected</c:if>>${dic.name }</option>
			                  	</c:if>
			                </c:forEach>
                       </select>
			     <%-- 所属团队：<select name="team" id="team" style="width: 100px;" class="sel" onchange="queryUser()">
                             <c:choose>
                            	<c:when test="${userRole=='staff' || positionId=='10036' }">
                            		<c:forEach items="${dictionaryLlist}" var="dic" varStatus="status">
		                    	<c:if test="${dic.type_code=='02' && dic.code==team }">
		                    		<option value="${dic.code}" selected>${dic.name }</option>
		                    	</c:if>
		                    </c:forEach>
                            	</c:when>
                            	<c:otherwise>
                               <option value="">请选择</option>
                               <c:if test="${department!=null && department!='' }">
								<c:forEach items="${childDictionaryList}" var="dic" varStatus="status">
			                    	<option value="${dic.code}" <c:if test="${dic.code==team}">selected</c:if>>${dic.name }</option>
			                    </c:forEach>
		                    </c:if>
                            	</c:otherwise>
                            </c:choose>
                     		</select> --%>
			  <!-- <input class="form_now" type="button" onclick="queryUser()" value="查询"> -->
	      	</fieldset>
      	</form>
      	<table id="tableID" width="100%" cellpadding="0" cellspacing="0">
          	<thead>
	          	<tr>
	              	<th width="30px"></th>
	              	<th>姓名</th>
	              	<th>部门名称</th>
	              	<th>团队名称</th>
	          	</tr>
          	</thead>
          	<tbody id="tableID2">
          		<c:forEach items="${userList}" var="user" varStatus="status">
			     	<c:if test="${!(userId==user.userId && userRole=='boss') }"><!-- 高管自己不能给自己分配 -->
			     	<c:if test="${!(user.userId=='888999' || user.userName=='超级管理员') }"><!-- 过滤掉超级管理和id=888999的用户 -->
			     	<tr>
			     		<td><input type="radio" name="userId" value="${user.userId},${user.userName},${user.department_id}"></td>
			     		<td>${user.userName}</td>
			     		<td><c:if test="${user.department_Name!=null}">${user.department_Name}</c:if></td>
			     		<td><c:if test="${user.team_Name!=null}">${user.team_Name}</c:if></td>
			     	</tr>
			     	</c:if>
			     	</c:if>
				 </c:forEach>
          	</tbody>
          	<%-- <tr>
	          	<td id="td1" colspan="4">
	          		<table id="tableID2" width="100%" cellpadding="0" cellspacing="0">
	          			<c:forEach items="${userList}" var="user" varStatus="status">
				     	<c:if test="${!(userId==user.userId && userRole=='boss') }"><!-- 高管自己不能给自己分配 -->
				     	<c:if test="${!(user.userId=='888999' || user.userName=='超级管理员') }"><!-- 过滤掉超级管理和id=888999的用户 -->
				     	<tr>
				     		<td width="30px"><input type="radio" name="userId" value="${user.userId},${user.userName}"></td>
				     		<td width="106px">${user.userName}</td>
				     		<td width="215px">${user.department_Name}</td>
				     		<td>${user.team_Name}</td>
				     	</tr>
				     	</c:if>
				     	</c:if>
				    	</c:forEach>
	          		</table>
	          	</td>
          	</tr> --%>
			
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