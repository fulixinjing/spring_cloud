<%@ page language="java" pageEncoding="UTF-8"%>
<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.0 Transitional//EN">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<%@ include file="/common/meta.jsp"%>
<title>任务管理系统</title>

<link rel="stylesheet" type="text/css" href="${ctx}/css/common.css" />
<link rel="stylesheet" type="text/css" href="${ctx}/css/right1.css" />
<link rel="stylesheet" type="text/css" href="${ctx}/css/colorbox.css" />
<link rel="stylesheet" type="text/css" href="${ctx}/css/pop.css" />
<script type="text/javascript" src="${ctx}/js/jquery.min.js"></script>

<link href="${ctx}/css/defaultTheme1.css" rel="stylesheet" media="screen" />
<link href="${ctx}/css/myTheme1.css" rel="stylesheet" media="screen" />
<script type="text/javascript" src="${ctx}/js/My97DatePicker/WdatePicker.js" ></script>
<script src="${ctx}/js/jquery.fixedheadertable.js"></script>
<script src="${ctx}/js/demo.js"></script>
<script type="text/javascript" src="${ctx}/js/right_js.js"></script>
<script type="text/javascript" src="${ctx}/js/layer/skin/layer.css"></script> 
<script type="text/javascript" src="${ctx}/js/layer/skin/layer.ext.css"></script>
<script type="text/javascript" src="${ctx}/js/layer/layer.min.js"></script>
<script type="text/javascript" src="${ctx}/js/layer/extend/layer.ext.js"></script>

<link rel="stylesheet" type="text/css" href="${ctx}/js/dhtml/dhtmlxTree/css/dhtmlXTree.css">
<link rel="stylesheet" type="text/css" href="${ctx}/js/dhtml/dhtmlxLayout/dhtmlxlayout.css">
<link rel="stylesheet" type="text/css" href="${ctx}/js/dhtml/dhtmlxLayout/skins/dhtmlxlayout_dhx_skyblue.css">
<script language="javascript" src="${ctx}/js/dhtml/dhtmlxLayout/dhtmlxcommon.js"></script>
<script language="javascript" src="${ctx}/js/dhtml/dhtmlxLayout/dhtmlxlayout.js"></script>
<script language="javascript" src="${ctx}/js/dhtml/dhtmlxLayout/dhtmlxcontainer.js"></script>
<script language="javascript" src="${ctx}/js/dhtml/dhtmlxTree/js/dhtmlXTree.js"></script>

</head>
<style>
	html,body{
		height:100%;
		overflow:hidden;
	}
	#bodyTR  td{
		text-align:center;
	}	
</style>
<body>
<c:if test="${resultFlag==1 }">
	<tags:message content="${message}" type="success" />
</c:if>
<c:if test="${resultFlag==0 }">
	<tags:message content="${message}" type="error" />
</c:if>
<form id ="f1" action="${ctx}/taskListQuery/topTask.do" method="GET">
<div class="TAB_right">
	<h1><b>位置：</b><a href="###">首页</a><span>&gt;</span><a href="###">任务查询</a>
	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
	<span style="font-size: 12px;font-family: 微软雅黑;color: #343333">年份：</span>
	<input class="topinput1" type="text" id="conYear" name="conYear"  style="width:100px" value="">
	
	<span style="font-size: 12px;font-family: 微软雅黑;color: #343333">季度：</span>
	<select name="conQuarter" id="conQuarter" style="width: 100px;" class="sel">
		<!-- <option value="">请选择</option> -->
		<option value="1">第一季度</option>
		<option value="2">第二季度</option>
		<option value="3">第三季度</option>
		<option value="4">第四季度</option>
	</select>
	&nbsp;&nbsp;
	<input class="form_now marginr10" type="button" value="查询" onclick="queryByConQuarter()">
	</h1>
</div>
</form>
</body>

<script type="text/javascript">
	$(document).ready(function (){
		var nowDate = new Date();
		var year = nowDate.getFullYear();
		$("#conYear").val(year);
		var month = nowDate.getMonth()+1;
		if(month>=1 && month<=3){
			$("#conQuarter").val(1);
		}else if (month>=4 && month<=6) {
			$("#conQuarter").val(2);
		}else if (month>=7 && month<=9) {
			$("#conQuarter").val(3);
		}else if (month>=10 && month<=12) {
			$("#conQuarter").val(4);
		}
	});
	function queryByConQuarter(){
		var conYear = $("#conYear").val();
		var conQuarter = $("#conQuarter").val();
		var nowDate = new Date();
		var year = nowDate.getFullYear();
		if(conYear==null || conYear==''){
			conYear=year;
		}
		if(parent.dhxLayout){
			if(conQuarter!=''){
				parent.dhxLayout.cells("b").attachURL("${ctx}/taskListQuery/treeJsp.do?conYear="+conYear+"&conQuarter="+conQuarter);
			}else{
				parent.dhxLayout.cells("b").attachURL("${ctx}/taskListQuery/treeJsp.do?conYear="+conYear);
			}
		}
	}
</script>
</html>