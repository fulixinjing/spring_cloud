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

<script>
//确定按钮，返回
function okBtnClick(){
	
	var chk_value =[];  
  	$('input[name="test"]:checked').each(function(){  
   		chk_value.push($(this).val());  
  	});  
	  
    if (chk_value.length==0) {
    	alert('你还没有选择任何内容！');
    	return false;
    }
    
    //谁调用我，我就调用谁的callback方法
    parent.showModalDialog_callback(chk_value);
    parent.$.colorbox.close();
}
</script>
</head>

<body>
<div class="retrieve_list">
	<input type="checkbox" name="test" value="0" />0&nbsp;&nbsp;  
	<input type="checkbox" name="test" value="1" />1&nbsp;&nbsp;  
	<input type="checkbox" name="test" value="2" />2&nbsp;&nbsp;  
	<input type="checkbox" name="test" value="3" />3&nbsp;&nbsp;  
	<input type="checkbox" name="test" value="4" />4&nbsp;&nbsp;  
	<input type="checkbox" name="test" value="5" />5&nbsp;&nbsp;  
	<input type="checkbox" name="test" value="6" />6&nbsp;&nbsp;  
	<input type="checkbox" name="test" value="7" />7&nbsp;&nbsp;  
	<input type="button" onclick="javascript:okBtnClick();" value="提  交" />  
</div>
</body>
</html>