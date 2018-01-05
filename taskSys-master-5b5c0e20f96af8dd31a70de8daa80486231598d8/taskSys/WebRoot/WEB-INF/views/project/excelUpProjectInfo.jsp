<%@ page language="java"  pageEncoding="UTF-8"%>
<%@page import="java.util.*" %>
<%@include file="/common/taglibs.jsp"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.0 Transitional//EN"> 
<html xmlns="http://www.w3.org/1999/xhtml">
<head> 
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>任务管理系统</title>
<link rel="stylesheet" type="text/css" href="${ctx}/css/common.css" /> 
<link rel="stylesheet" type="text/css" href="${ctx}/css/right.css" /> 
<script type="text/javascript" src="${ctx}/js/jquery.min.js" ></script> 
<script type="text/javascript" src="${ctx}/js/alert/g_alert.js?1=1"></script>
<link href="${ctx}/css/960.css" rel="stylesheet" media="screen" /> 
<link href="${ctx}/css/defaultTheme.css" rel="stylesheet" media="screen" /> 
<link href="${ctx}/css/myTheme.css" rel="stylesheet" media="screen" /> 
<link rel="stylesheet" type="text/css" href="${ctx}/css/pop.css" /> 
<script type="text/javascript" src="${ctx}/js/right_js.js"></script> 
<link rel="stylesheet" type="text/css" href="${ctx}/static/uploadify/uploadify.css" />
<script type="text/javascript" src="${ctx}/static/uploadify/jquery.uploadify.min.js?ver=<%=(new Random()).nextInt() %>"></script> 
<fmt:formatNumber value="" />

<script type="text/javascript">
$(document).ready(function()
{
	 function windowClose(){
		parent.$.colorbox.close();
		parent.refusePage();
	}
	
    $("#file_upload").uploadify({
    	'buttonText' : '请选择Excel文件',
        'swf': '${ctx}/static/uploadify/uploadify.swf',
        'uploader': '${ctx}/project/uploadFile.do;jsessionid=<%=session.getId()%>',
        'cancelImg': '${ctx}/static/uploadify/cancel.png',
        'auto':false, 
        'fileSizeLimit' : '10240KB', 
        'fileTypeExts'  : '*.xls; *.xlsx',
        'uploadLimit' : 3,
        'fileObjName'   : 'file',
        'onUploadSuccess' : function(file, data, response) {
            alert( '文件【'+file.name+'】上传成功！');
           
            $.ajax({
        		url:"${ctx}/project/getExcelInfoList.do",
        		data:{
        			fileName:encodeURI(file.name)
        			},
        		error : function(e){
        			console.log(e)
        		},
        		success:function(msg){
	        		if(null!=msg){
						if('suc'== msg.message){
							g_alert('success','导入成功！','',"${ctx}");
							parent.window.location.reload();
						} else {
							alert(msg.message);
							return;
						}
	        		}
				}
            });
        }
    });
}); 
</script>
</head>

<body style="min-width:580px">
<form id="scoreinfo" action="" method="post" enctype="multipart/form-data" >
<tags:message content="${message}" type="success" />
<div class="TAB_right">
	<h1><b>位置：</b><a href="###">文件上传</a><span>&gt;</span><a href="###">项目信息上传</a></h1>
	<div class="retrieve_list">
			<table class="retrieve_table" width="100%" cellpadding="0" cellspacing="0">
		    	<tr>
		        	<td>
		        	<input type="file" name="fileName" id="file_upload" />  
    					<a href="javascript:$('#file_upload').uploadify('upload', '*')">上传文件</a> | 
    					<a href="javascript:$('#file_upload').uploadify('stop')">停止上传</a>		
		        	</td>
		        </tr>
		    </table>
	</div>

</div>
</form>
</body>
</html>