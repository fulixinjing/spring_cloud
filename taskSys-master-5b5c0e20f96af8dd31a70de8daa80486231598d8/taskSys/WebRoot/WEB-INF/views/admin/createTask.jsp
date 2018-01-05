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
<script type="text/javascript" src="${ctx}/js/My97DatePicker/WdatePicker.js" ></script>
<script type="text/javascript" src="${ctx}/js/jquery.min.js"></script>
<script type="text/javascript" src="${ctx}/js/right_js.js"></script>
<script type="text/javascript" src="${ctx}/js/jquery.metadata.js"></script>
<script type="text/javascript" src="${ctx}/js/jquery.validate.js"></script>
<script language="javascript" src="${ctx}/components/colorbox/jquery.colorbox.js"></script>
</head>

<body>
<form action="${ctx}/admin/saveTask.do" method="POST" id="saveForm" name="saveForm">
<input type="hidden" id="parentRoleName" name="parentRoleName">
<div class="TAB_right">
	<table>
		<tr>
			<td class="message">新建任务</td>
		</tr>
	</table>
	<hr/>
	<table>
		<tr>
			<td colspan="2"><textarea maxlength=500; cols="40" id="taskContent" name="taskContent" value="${taskContent}"></textarea>
			</td>
		</tr>
		<tr>
			<td>完成时间：</td>
			<td>
			   <input style="width: 90px;" readonly="readonly" type="text" id="expectEndTime" name="expectEndTime" onclick="WdatePicker()" onfocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd'})" class="Wdate" value="${expectEndTime}">
			</td>
		</tr>
		
		<c:forEach items="${mlist }" var="Message" >
		
			<tr>
				<td id="Message" name="Message" value="">${Message.content}</td>
			</tr>
			
		</c:forEach>	
        <tr align="center">
	       	<td colspan="2">
	       		<input class="form_now" type="submit" value="保存">&nbsp;&nbsp;&nbsp;&nbsp;	       	     		
	       		<input class="form_now" type="button" onclick="javascript:history.back()" value="取消">&nbsp;&nbsp;&nbsp;&nbsp;
	       	</td>
       	</tr>
	</table>	 
	
</div>
</form>
</body>
<script>

</script>
</html>