<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/common/taglibs.jsp" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.0 Transitional//EN"> 
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<%@ include file="/common/meta.jsp" %>
<title>任务管理系统</title>
<link rel="stylesheet" type="text/css" href="${ctx}/css/common.css" />
<link rel="stylesheet" type="text/css" href="${ctx}/css/right.css" />
<link rel="stylesheet" type="text/css" href="${ctx}/css/colorbox.css"/>
<link rel="stylesheet" type="text/css" href="${ctx}/css/pop.css" />
<script type="text/javascript" src="${ctx}/js/jquery.min.js"></script>
<script type="text/javascript" src="${ctx}/js/right_js.js"></script>
<script type="text/javascript" src="${ctx}/js/jquery.metadata.js"></script>
<script type="text/javascript" src="${ctx}/js/jquery.validate.js"></script>
<script language="javascript" src="${ctx}/components/colorbox/jquery.colorbox.js"></script>
<script type="text/javascript">
	/* function onclickHandler(value){
		if(value=='01'){
			$(".trTwo").hide();
			$(".trOne").show();
		}else{
			$(".trOne").hide();
			$(".trTwo").show();
		}
	} */
	function submitForm(){
		var type_code = document.getElementById("type_code").value;
		var departmentName = $("#departmentName").val();
		var parent_id = $("#parent_id").val();
		var teamName = $("#teamName").val();
		var companyName = $("#companyName").val();
		
		if(type_code=='01'){
			$("#teamName").val("");
			$("#companyName").val("");
			if(departmentName==null || departmentName==''){
				alert("请输入部门名称！");
				return;
			}
		}else if(type_code=='02'){
			$("#departmentName").val("");
			$("#companyName").val("");
			if(parent_id==null || parent_id==''){
				alert("请选择团队所在部门！");
				return;
			}
			if(teamName==null || teamName==''){
				alert("请输入团队名称！");
				return;
			}
		}else if(type_code=='00'){
			$("#departmentName").val("");
			$("#teamName").val("");
			if(companyName==null || companyName==''){
				alert("请输入公司名称！");
				return;
			}
		}
		
		$("#saveForm").attr("action","${ctx}/utils/saveDictionary.do");
		$("#saveForm").attr("method","POST");
		$("#saveForm").submit();
	}
	
	function historyBack(){
		location.href="${ctx}/utils/listDictionary.do";
	}
</script>
</head>

<body>
	<c:if test="${resultFlag==1 }">
  		<tags:message content="${message}" type="success" />
  	</c:if>
  	<script type="text/javascript">
  		$(document).ready(function(){
  			$('.Sure_btn').focus();
  		});
  	</script>
  	<c:if test="${resultFlag==0 }">
  		<tags:message content="${message}" type="error" />
  	</c:if>
<form action="${ctx}/utils/saveDictionary.do" method="POST" id="saveForm" name="saveForm">
<div class="TAB_right">
	<h1><b>位置：</b><a href="###">首页</a><span>&gt;</span><a href="###">修改字典表</a></h1>
	<div class="TAB_right_content">
		<div class="jur_add">
		  <!-- <div class="btn_operate">&nbsp;&nbsp;&nbsp;&nbsp;
		     <input type="radio" name="type_code" value="01" onclick="onclickHandler(this.value)" checked="true">&nbsp;&nbsp;<font size="3">部门</font><br>&nbsp;&nbsp;&nbsp;&nbsp;
		     <input type="radio" name="type_code" value="02" onclick="onclickHandler(this.value)">&nbsp;&nbsp;<font size="3">团队</font>
		  </div> -->
		  <input type="hidden" id="type_code" name="type_code" value="${type_code }">
		  <input type="hidden" id="flag" name="flag" value="modify">
		  <input type="hidden" id="id" name="id" value="${dictionaryT.id }">
		  <div class="ADD_tree">
		      <table class="Table_forms" cellpadding="0" cellspacing="0">
		        <c:if test="${type_code=='00' }">
		        <tr class="trThree">
		        </c:if>
		        <c:if test="${type_code=='02' || type_code=='01' }">
		        <tr class="trThree"  style="display:none;">
		        </c:if>
                    <td><label><span>*</span>公司名称：</label><input type="text" name="companyName" id="companyName" value="${dictionaryT.name }"></td>
                    <td></td>
                </tr>
		        <c:if test="${type_code=='01' }">
		        <tr class="trOne">
		        </c:if>
		        <c:if test="${type_code=='02' || type_code=='00' }">
		        <tr class="trOne"  style="display:none;">
		        </c:if>
                    <td>
                    	<label>已有部门：</label>
	                    <select  class="sel" style="width:190px">
	                    	<!-- <option value="">请选择</option> -->
		                    <c:forEach items="${dictionaryLlist}" var="dic" varStatus="status">
		                    	<c:if test="${dic.type_code=='01' }">
		                    		<option value="${dic.code}" >${dic.name }</option>
		                    	</c:if>
		                    </c:forEach>
	                    </select>
                    </td>
                    <td><label><span>*</span>部门名称：</label><input type="text" name="departmentName" id="departmentName" value="${dictionaryT.name }"></td>
                </tr>
                <c:if test="${type_code=='01' || type_code=='00' }">
                <tr class="trTwo" style="display:none;">
                </c:if>
                <c:if test="${type_code=='02' }">
                <tr class="trTwo">
                </c:if>
                    <td><label><span>*</span>所属部门：</label>
	                    <select name="parent_id" id="parent_id" class="sel" style="width:190px" disabled="disabled">
	                    	<option value="">请选择</option>
		                    <c:forEach items="${dictionaryLlist}" var="dic" varStatus="status">
		                    	<c:if test="${dic.type_code=='01' }">
		                    		<option value="${dic.id}" <c:if test="${parentDictionary.code==dic.code}">selected</c:if>>${dic.name }</option>
		                    	</c:if>
		                    </c:forEach>
	                    </select>
	                    <label style="width: 80px">已有团队：</label>
	                    <select id="team" class="sel" style="width:150px">
	                    	<option value="">请选择</option>
		                    <c:forEach items="${teamlist}" var="dic" varStatus="status">
		                    		<option value="${dic.id}" >${dic.name }</option>
		                    </c:forEach>
	                    </select>
                    </td>
                    <td><label><span>*</span>团队名称：</label><input maxlength="15" type="text" value="${dictionaryT.name }" name="teamName" id="teamName"></td>
                </tr>
                 <tr align="center">
                	<td colspan="2">
                		<input class="form_now" type="button" value="保存" onclick="submitForm()">&nbsp;&nbsp;&nbsp;&nbsp;
                		<input class="form_now" type="button" onclick="javascript:historyBack()" value="返回">&nbsp;&nbsp;&nbsp;&nbsp;
                	</td>
                </tr>
		      </table>
		  </div>
		</div>
	</div>
</div>
</form>
</body>
</html>