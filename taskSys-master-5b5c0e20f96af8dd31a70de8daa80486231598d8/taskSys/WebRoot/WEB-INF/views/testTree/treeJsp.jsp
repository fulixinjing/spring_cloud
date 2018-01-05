<%@ page contentType="text/html;charset=GBK"%>
<%@ include file="/common/taglibs.jsp" %>
<html>
<head>
<%-- <link rel="stylesheet" type="text/css" href="${ctx}/js/dhtml/dhtmlxLayout/dhtmlxlayout.css">
<link rel="stylesheet" type="text/css" href="${ctx}/js/dhtml/dhtmlxLayout/skins/dhtmlxlayout_dhx_skyblue.css">
<script language="javascript" src="${ctx}/js/dhtml/dhtmlxLayout/dhtmlxcommon.js"></script>
<script language="javascript" src="${ctx}/js/dhtml/dhtmlxLayout/dhtmlxlayout.js"></script>
<script language="javascript" src="${ctx}/js/dhtml/dhtmlxLayout/dhtmlxcontainer.js"></script> --%>

<link rel="stylesheet" type="text/css" href="${ctx}/js/dhtml/dhtmlxTree/css/dhtmlXTree.css">
<script language="javascript" src="${ctx}/js/dhtml/dhtmlxTree/js/dhtmlXCommon.js"></script>
<script language="javascript" src="${ctx}/js/dhtml/dhtmlxTree/js/dhtmlXTree.js"></script>
<script language="javascript" src="${ctx}/js/dhtml/dhtmlxLayout/dhtmlxcontainer.js"></script>
</head>
<body scroll="no">
<div id="treeBox" style="width:100%;height:expression(body.offsetHeight-15); overflow:auto"></div>
<script>
	var tree=new dhtmlXTreeObject('treeBox',"100%","100%",0);
	tree.setImagePath("${ctx}/js/dhtml/dhtmlxTree/imgs/csh_vista/");
	tree.setOnClickHandler(onNodeClick);
	tree.attachEvent("onXLE",parent.dhxLayout.progressOff());
	tree.loadXML("${ctx}/taskListQuery/resTerr.do");
	function onNodeClick(nodeId){
		if(parent.dhxLayout){
			parent.dhxLayout.cells("c").attachURL("${ctx}/taskListQuery/listTask.do?nodeId="+nodeId);
			parent.dhxLayout.progressOn();
		}
		
	}
	
</script>

</body>
</html>