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
<link rel="stylesheet" type="text/css" href="${ctx}/js/dhtml/dhtmlxLayout/dhtmlxlayout.css">
<link rel="stylesheet" type="text/css" href="${ctx}/js/dhtml/dhtmlxLayout/skins/dhtmlxlayout_dhx_skyblue.css">
<script language="javascript" src="${ctx}/js/dhtml/dhtmlxLayout/dhtmlxcommon.js"></script>
<script language="javascript" src="${ctx}/js/dhtml/dhtmlxLayout/dhtmlxlayout.js"></script>
<script language="javascript" src="${ctx}/js/dhtml/dhtmlxLayout/dhtmlxcontainer.js"></script>
<script language="javascript" src="${ctx}/js/dhtml/dhtmlxTree/js/dhtmlXTree.js"></script>
<script type="text/javascript" src="${ctx}/js/jquery.min.js"></script>
</head>
<body scroll="no">
<div id="treeBox" style="width:100%;height:expression(body.offsetHeight-15); overflow:auto"></div>
<script>
	var tree=new dhtmlXTreeObject('treeBox',"100%","100%",0);
	tree.setImagePath("${ctx}/js/dhtml/dhtmlxTree/imgs/csh_vista/");
	tree.setOnClickHandler(onNodeClick);
	tree.setOnDblClickHandler(onNodeDbClick);
	//tree.attachEvent("onXLE",parent.dhxLayout.progressOff());
	/****timestamp作用：在IE11下tree.loadXML只加载一次（每次请求的URL相同），加上时间戳每次请求的URL不同，解决了每次数据不能及时刷新的问题******/
	var timestamp=new Date().getTime();
	
	var url = '';
	if('${conQuarter}'!='' && '${conQuarter}'!=null){
		url = "${ctx}/taskListQuery/resTerr.do?conYear="+'${conYear}'+"&conQuarter="+'${conQuarter}'+"&timestamp="+timestamp;
	}else{
		url = "${ctx}/taskListQuery/resTerr.do?conYear="+'${conYear}'+"&timestamp="+timestamp;
	}
	
	tree.loadXML(url);
	function onNodeClick(nodeId){
			/* var result = $.ajax({
				url: "${ctx}/taskListQuery/subTimeTree.do?nodeId="+nodeId,
				type: "POST",
				data: {
					timenow: new Date().getTime(),
					orgId:nodeId
				},
				async: false//同步
			}).responseText;
			//alert(result);
			var json = eval("(" + result + ")");
			$(json).each(function(i){
				 if(tree.getIndexById(json[i].nodeId)==null){
					tree.insertNewChild(nodeId,json[i].nodeId,json[i].nodeName,'achive.gif','folderClosed.gif','folderClosed.gif','folderClosed.gif');
				 }
			});	 */
		
		
	   if(parent.dhxLayout){
			parent.dhxLayout.cells("c").attachURL("${ctx}/taskListQuery/listTask.do?nodeId="+nodeId);
			parent.dhxLayout.progressOn();
		}
		
	}
	function onNodeDbClick(nodeId){
	
	}
	tree.attachEvent("onXLE",deleverSel);
	function deleverSel(){
		//dhxLayout.progressOff();
	}
	
	
</script>
<input type="hidden" id="conYeaar" name="conYeaar" value="${conYear }" >
</body>
</html>