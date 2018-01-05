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

<SCRIPT type="text/javascript">
	var zNodes =${listright};
	var setting = {
		check: {
			enable: true,
			chkStyle: "checkbox",
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
       
        var chk_value =[];
        for(var i=0;i<nodes.length;i++){
        	chk_value.push(nodes[i].id + "," + nodes[i].name);
        }
        
	    //谁调用我，我就调用谁的callback方法
	    parent.showModalDialog_callback(chk_value);
	    parent.$.colorbox.close();
	}
	
	$(document).ready(function(){
		$.fn.zTree.init($("#orgTree"), setting, zNodes);
	});
</SCRIPT>
</head>

<body style="OVERFLOW-X:HIDDEN">
    <div class="margint10">
    	<table class="doc_t" width="100%" cellpadding="0" cellspacing="0" border="0">
    		<tr>
            	<td>
                	<input class="form_now" type="button" onclick="okBtnClick()" value="选择带回">
                </td>
            </tr>
        	<tr>
            	<td valign="top" width="180">
                	<div class="jur_tree">
                		<!--文档树部分开始-->
						<ul id="orgTree" class="ztree"></ul>
                    	<!--文档数部分结束-->
                    </div>
                </td>
            </tr>
        	
        </table>
    </div>
</body>
</html>