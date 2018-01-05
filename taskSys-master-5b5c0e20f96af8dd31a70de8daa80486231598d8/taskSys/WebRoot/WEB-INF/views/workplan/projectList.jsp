<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/common/taglibs.jsp" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.0 Transitional//EN"> 
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<%@ include file="/common/meta.jsp" %>
<title>项目选择列表</title>
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
	
	//确定按钮，返回
	function okBtnClick(){
		var chk_value =[];  
	  	$('input[name="proId"]:checked').each(function(){  
	   		chk_value.push($(this).val());  
	  	});  
		  
	    if (chk_value.length>1) {
	    	alert('只能选择一个项目！');
	    	return false;
	    }
	    //alert(chk_value);
	    //谁调用我，我就调用谁的callback方法
	    parent.selectSingleProject_callback(chk_value);
	    parent.$.colorbox.close();
	}
	function queryProject(){
		var proName = $("#proName").val();
		var proCode = $("#proCode").val();
		if(proName=='' && proCode==''){
			$("#tableID2").html('');
			return;
		}
		$.ajax({
			type : "POST", 
			url : "${ctx}/work_plan/ajaxProjectList.do",
			data : {"proName":proName,"proCode":proCode},
			dataType : "json", 
			success : function(data){
				var str = "";
				if(data!=null && data.list!=null && data.list.length>0){
					for ( var i = 0; i < data.list.length; i++) {
						var proName = (data.list[i]).proname==null ? "" : (data.list[i]).proname;
						var proCode = (data.list[i]).code==null ? "" : (data.list[i]).code;
						var appStr1="<tr><td><input type=\"radio\" name=\"proId\" value=\""+(data.list[i]).id+","+proName+","+proCode+"\"></td>";
						var appStr2="<td>"+proName+"</td>";
						var appStr3="<td>"+proCode+"</td>";
						str=str+appStr1+appStr2+appStr3;
					}
				}
				$("#tableID2").html(str);
			}
    	}); 
	}
	
</script>
</head>

<body>
<div class="infor_base">
  	<div class="role_side" style="height:280px;overflow: auto;">
      	<form id="queryFormId" action="" method="post">
	      	<fieldset>
			  <legend>项目查询</legend>
			  <label>项目名称:</label>
			  <input type="text" name="proName" id="proName" size="14" value="${proName }" style="height:21px;" onkeyup="queryProject()" onblur="queryProject()"/>
			  &nbsp;&nbsp;&nbsp;&nbsp;
			  <label>项目编号:</label>
			  <input type="text" name="proCode" id="proCode" size="14" value="${proCode }" style="height:21px;" onkeyup="queryProject()" onblur="queryProject()"/>
	      	</fieldset>
      	</form>
      	<table id="tableID" width="100%" cellpadding="0" cellspacing="0">
          	<thead>
	          	<tr>
	              	<th width="30px"></th>
	              	<th>项目名称</th>
	              	<th>项目编号</th>
	          	</tr>
          	</thead>
          	<tbody id="tableID2">
          		<c:forEach items="${projectList}" var="project" varStatus="status">
			     	<tr>
			     		<td><input type="radio" name=proId" value="${project.id},${project.proName},${project.code}"></td>
			     		<td>${project.proName}</td>
			     		<td>${project.code}</td>
			     	</tr>
				 </c:forEach>
          	</tbody>
      	</table>
	</div>
	<table width="100%" cellpadding="0" cellspacing="0">
		<tr align="center">
            <td colspan="10"><input class="form_now" type="button" onclick="okBtnClick()" value="确定"></td>
         </tr>
   	</table>	
</div>
</body>
</html>