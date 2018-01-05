<%@ page language="java"  pageEncoding="UTF-8"%>
<%@include file="/common/taglibs.jsp"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.0 Transitional//EN"> 
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
    <title>任务管理系统</title>
    <link rel="stylesheet" type="text/css" href="${ctx}/css/common.css" />
    <link rel="stylesheet" href="${ctx}/css/ztree/zTreeStyle.css" type="text/css">
    <link href="${ctx}/css/static/base-global.css" rel="stylesheet" type="text/css" />
    <link href="${ctx}/css/static/common.css" rel="stylesheet" type="text/css" />
    <link href="${ctx}/css/static/main.css" rel="stylesheet" type="text/css" />
    
    <link href="${ctx}/css/static/main.css?1=1" rel="stylesheet" type="text/css" />
<link href="${ctx}/css/static/base-global.css" rel="stylesheet" type="text/css" />
<link href="${ctx}/css/static/common.css?5=1" rel="stylesheet" type="text/css" />
<link href="${ctx}/css/static/main.css?1=1" rel="stylesheet" type="text/css" />
<link href="${ctx}/css/static/stylesheet.css" rel="stylesheet" type="text/css" />

    <link rel="stylesheet" type="text/css" href="${ctx}/css/pop.css" />
    <link rel="stylesheet" type="text/css" href="${ctx}/css/right1.css" />
    <script type="text/javascript" src="${ctx}/js/jquery.min.js"></script>
    <script type="text/javascript" src="${ctx}/js/right_js.js"></script>
    <script type="text/javascript" src="${ctx}/js/ztree/jquery-1.4.4.min.js"></script>
    <script type="text/javascript" src="${ctx}/js/ztree/jquery.ztree.core-3.5.js"></script>
    <link rel="stylesheet" type="text/css" href="${ctx}/components/colorbox/colorbox.css"/>
<script language="javascript" src="${ctx}/components/colorbox/jquery.colorbox.js"></script>
<link rel="stylesheet" href="${ctx}/js/jquery_ui/jquery-ui.css">
<script src="${ctx}/js/jquery_ui/jquery-ui.js"></script>
</head>
<script type="text/javascript">
function onsub(code){
	alert(code);
	//$("#deptlist").colorbox.close();
	window.location = "${ctx }/trello/projectstage.do?code="+code;
	parent.$.colorbox.close();
	parent.refresh();
}
</script>
<body>
<div class="deptlist" id="deptlist" style="height:100%;overflow-y:hidden;">
        <table class="" width="100%" cellpadding="0" cellspacing="0" border="0" >
            <tr>
                 <c:forEach items="${departmentList}" var="dept">
                   <tr>
                       <td><a href="#" onclick="onsub('${dept.code }')">${dept.proName}</a></td>
                   </tr>
                   </c:forEach>
            </tr>
        </table>  
</div>
</body>
</html>