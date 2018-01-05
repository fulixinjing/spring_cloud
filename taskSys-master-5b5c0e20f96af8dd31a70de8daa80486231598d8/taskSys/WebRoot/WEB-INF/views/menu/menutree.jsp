<%@ page language="java"  pageEncoding="UTF-8"%>
<%@include file="/common/taglibs.jsp"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.0 Transitional//EN"> 
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
	<title>任务管理系统</title>
	<link rel="stylesheet" type="text/css" href="${ctx}/css/common.css" />
	<link rel="stylesheet" type="text/css" href="${ctx}/css/right1.css" />
	<link rel="stylesheet" href="${ctx}/css/ztree/zTreeStyle.css" type="text/css">
	<script type="text/javascript" src="${ctx}/js/jquery.min.js"></script>
	<script type="text/javascript" src="${ctx}/js/right_js.js"></script>
	<script type="text/javascript" src="${ctx}/js/ztree/jquery-1.4.4.min.js"></script>
	<script type="text/javascript" src="${ctx}/js/ztree/jquery.ztree.core-3.5.js"></script>

	<SCRIPT type="text/javascript">
		<!--
		var setting = {
			data: {
				simpleData: {
					enable: true
				}
			}
		};

		
		var zNodes =${pm};

		$(document).ready(function(){
			$.fn.zTree.init($("#menutree"), setting, zNodes);
		});
		
		
		//-->
	</SCRIPT>
</head>

<body>
<div class="TAB_right" style="height:100%;overflow-y:hidden;">
	<h1 ><b>位置：</b><a href="###">首页</a><span>&gt;</span><a href="###">菜单栏目管理</a></h1>
    <div class="margint10" id="inner_right">
    	<table class="doc_t" width="100%" cellpadding="0" cellspacing="0" border="0" >
        	<tr>
            	<td valign="top" width="265" style="background-color: #E7EDF8;">
                	<div class="jur_tree" id="z_tree_zone">
                	<!--文档树部分开始-->
						<ul id="menutree" class="ztree"></ul>
                    <!--文档数部分结束-->
                    </div>
                </td>
                <td valign="top"  class="menu_tree">
                	<iframe id="ky" name="ky" src="${ctx}/menu/menuinfo.do?menuid=0" width="100%" height="100%" frameborder="0"></iframe>
                </td>
            </tr>
        </table>
    </div>
</div>
</body>
</html>