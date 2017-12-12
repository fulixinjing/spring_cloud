<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/";
%>
<c:set var="ctx" value="${pageContext.request.contextPath}" scope="page" />
<base href="${ctx}/">

<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<script type="text/javascript" src="${ctx}/js/jquery-1.8.0.min.js"></script>
<title>Insert title here</title>
</head>
<body>
    <form id="subform" action="">
		<table align="center">
			<tr>
				<td>选择英雄：</td>
				<td>
					<select name="yx">
							<!-- <option>德玛西亚之力</option>
							<option>诺克萨斯之手</option>
							<option>暗夜猎手</option> -->
							<c:forEach items="${hero}" var="item">
								<option>${item.key}</option>
							</c:forEach>
					</select>
				</td>
				<td>召唤师技能：</td>
				<td>
					<select name="jn">
							<c:forEach items="${skill}" var="item">
								<option>${item.key}</option>
							</c:forEach>
					</select>
				</td>
			</tr>
			<tr>
				<td colspan="4" align="center"><input type="button" id="sbtn" value="确定"> </td>
			</tr>
		</table>
	</form>
</body>
<script type="text/javascript">

$(function(){
	$("#sbtn").click(function (){
		$("#subform").attr("action","${ctx}/lol/yxsx");
		$("#subform").submit();
	})
	
})
</script>
</html>