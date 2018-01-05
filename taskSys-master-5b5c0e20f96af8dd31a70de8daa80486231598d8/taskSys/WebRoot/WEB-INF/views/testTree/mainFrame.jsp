<%@ page contentType="text/html;charset=GBK"%>
<%@ include file="/common/taglibs.jsp" %>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=GBK">
<link rel="stylesheet" type="text/css" href="${ctx}/js/dhtml/dhtmlxLayout/dhtmlxlayout.css">
<link rel="stylesheet" type="text/css" href="${ctx}/js/dhtml/dhtmlxLayout/skins/dhtmlxlayout_dhx_skyblue.css">
<script language="javascript" src="${ctx}/js/dhtml/dhtmlxLayout/dhtmlxcommon.js"></script>
<script language="javascript" src="${ctx}/js/dhtml/dhtmlxLayout/dhtmlxlayout.js"></script>
<script language="javascript" src="${ctx}/js/dhtml/dhtmlxLayout/dhtmlxcontainer.js"></script>
<title>无标题文档</title>
<style>
html,body {
	width: 100%;
	height: 100%;
	margin: 0px;
	padding:0 -10px;
	overflow: hidden;
}
</style>
</head>

<body>
</body>
<script type="text/javascript">
	var nowDate = new Date();
	var conYear = nowDate.getFullYear();
	var conQuarter = '';
	var month = nowDate.getMonth()+1;
	if(month>=1 && month<=3){
		conQuarter = '1';
	}else if (month>=4 && month<=6) {
		conQuarter = '2';
	}else if (month>=7 && month<=9) {
		conQuarter = '3';
	}else if (month>=10 && month<=12) {
		conQuarter = '4';
	}
	var url="${ctx}/taskListQuery/treeJsp.do?conYear="+conYear+"&conQuarter="+conQuarter;	
	
	var dhxLayout;
	dhxLayout = new dhtmlXLayoutObject(document.body, "3T");
	dhxLayout.cells("a").hideHeader();
	dhxLayout.cells("b").hideHeader();
	dhxLayout.cells("c").hideHeader();
	dhxLayout.cells("b").setWidth(180);
	dhxLayout.cells("a").setHeight(-1);
	dhxLayout.progressOn();
	dhxLayout.cells("a").attachURL("${ctx}/taskListQuery/topTask.do");
	dhxLayout.cells("b").attachURL(url);
	dhxLayout.cells("c").attachURL("${ctx}/taskListQuery/listTask.do?flag1=1");
</script>
</html>
