<%@ page language="java"  pageEncoding="UTF-8"%>
<%@include file="/common/taglibs.jsp"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.0 Transitional//EN"> 
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>机构单选树</title>
<link rel="stylesheet" type="text/css" href="${ctx}/css/common.css" />
<link rel="stylesheet" type="text/css" href="${ctx}/css/right.css" />
<link rel="stylesheet" href="${ctx}/css/ztree/zTreeStyle.css" type="text/css">
<script type="text/javascript" src="${ctx}/js/jquery.min.js"></script>
<script type="text/javascript" src="${ctx}/js/right_js.js"></script>
<script type="text/javascript" src="${ctx}/js/ztree/jquery.ztree.core-3.5.js"></script>
<script type="text/javascript" src="${ctx}/js/ztree/jquery.ztree.excheck-3.5.js"></script>
<link rel="stylesheet" type="text/css" href="${ctx}/css/pop.css" />
<script type="text/javascript">
	$(function(){
		$(".X_tablefom tr:even").css("background-color","#e6edf3");
		$(".X_tablefom tr:odd").css("background-color","#ffffff");
	})
</script>
<SCRIPT type="text/javascript">
	var zNodes =${pm};
	var setting = {
		check: {
			enable: true,
			chkStyle: "radio",
			radioType: "all"
		},
		data: {
			simpleData: {
				enable: true
			}
		}
	};
	
	//确定按钮，返回
	function okBtnClick(){
		var treeObj = $.fn.zTree.getZTreeObj("orgTree");
        var nodes = treeObj.getCheckedNodes(true);
        if (nodes.length == 0) {
        	alert("请您选择一个机构！");
        }
        
		var chk_value = nodes[0].id + "," + nodes[0].name;
	    
	    //谁调用我，我就调用谁的callback方法
	    parent.selectSingleOrg_callback(chk_value);
	    parent.$.colorbox.close();
	}
	
	$(document).ready(function(){
		$.fn.zTree.init($("#orgTree"), setting, zNodes);
	});
</SCRIPT>
</head>

<body>
<div class="X_layer">
	<div class="X_layer_content"><span>标题</span></div>
	 <div class="X_input">
    <div class="X_btn1"><input class="btn_submit" type="button" onclick="okBtnClick()" value="选择带回" /></div>
    	<div class="X_box">
        	<div class="jur_tree">
             		<!-- 文档树部分开始 -->
			<ul id="orgTree" class="ztree"></ul>
                 	<!-- 文档数部分结束 -->
            </div>
           
        </div>
    </div>
</div>
</body>
</html>