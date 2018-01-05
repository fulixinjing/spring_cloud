<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/common/taglibs.jsp" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.0 Transitional//EN"> 
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<%@ include file="/common/meta.jsp" %>
<title>Colorbox Simple Demo</title>
<link rel="stylesheet" type="text/css" href="${ctx}/css/common.css" />
<link rel="stylesheet" type="text/css" href="${ctx}/css/right.css" />
<script type="text/javascript" src="${ctx}/js/jquery.min.js"></script>
<script type="text/javascript" src="${ctx}/js/right_js.js"></script>

<link rel="stylesheet" type="text/css" href="${ctx}/components/colorbox/colorbox.css"/>
<script language="javascript" src="${ctx}/components/colorbox/jquery.colorbox.js"></script>
<script>
function showModalDialog(){
    var url= "${ctx}/components/colorbox/demo/example-child.jsp";
    $.colorbox({
        href:url,
        iframe:true,
        width:"600",
        height:"630"
    });
}
function showModalDialog_callback(ret){
	$(".infor_base").text(ret);
}
</script>
</head>

<body>
<div class="retrieve_list">
<input class="retrieve_btn" type="button" onclick="javascript:showModalDialog()" value="弹弹弹...">
</div>
<div class="infor_base">
</div>
</body>
</html>