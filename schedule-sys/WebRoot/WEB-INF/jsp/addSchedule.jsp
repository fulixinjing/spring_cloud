<%@ page language="java" pageEncoding="UTF-8"%>
<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.0 Transitional//EN">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<script type="text/javascript" src="${ctx}/js/My97DatePicker/WdatePicker.js" ></script>
<script type="text/javascript" src="${ctx}/js/jquery.min.js"></script>
<script src="${ctx}/js/artDialog4.1.7/artDialog.source.js?skin=default"></script>
<script src="${ctx}/js/artDialog4.1.7/iframeTools.source.js"></script>
<link rel="stylesheet" type="text/css" href="${ctx}/css/right.css" />
</head>
<style>
.input1{ 

    border: 1px solid #999 ; 
    width: 170px;

}
.textarea_refuse{width:400px; height:80px;border:1px solid #bcbcbc;border-radius: 3px;vertical-align:top}
.lab{width:125px;text-align:right;display:inline-block;height:26px;line-height:27px;}
.input-medium {
	width: 100px;
	max-width: 100%;
	border-radius: 3px;
}
</style>
<body>
 <form id="xjrw" action="" method="post">
  <table width="90%" height="70%" align="center">
        <tr>
        	<td colspan="2">任务名称:<input type="text" class="input1" id="name" name="name"></td>
        </tr>
        <tr>
        	<td colspan="2">开始时间:<input style="width: 170px;" readonly="readonly" type="text" id="expectEndTime" name="startDate" 
	                         onclick="WdatePicker()" onfocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd HH:mm:ss'})" class="Wdate" value="">
			</td>
        </tr>
        <tr>
        	<td colspan="2">结束时间:<input style="width: 170px;" readonly="readonly" type="text" id="expectEndTime" name="endDate" 
	                         onclick="WdatePicker()" onfocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd HH:mm:ss'})" class="Wdate" value="">
			</td>
        </tr>
        <tr>
        	<td>是否提醒： <input type="checkbox" value="1" name="remind">
        	</td>
        	<td>优先级：
        				<select name="priority" class="input-medium">
        	  				<option value='1'>高</option>
        	  				<option value='2' selected="selected" >中</option>
        	  				<option value='3'>低</option>
        			   </select>
            </td>
        </tr>
        <tr>
        	<td colspan="2">
        		备注：<textarea rows="" cols="" class="textarea_refuse" name="remarks"></textarea>
        	</td>
        </tr>
        <tr>
        	<td  colspan="2" align="center">
        		<input class="form_now" type="button"  id="ConfirmBtn" value="确定">&nbsp;&nbsp;&nbsp;&nbsp;
                    <input class="form_now" type="button" id="cancelBtn" value="取消">
        	</td>
        </tr>
  </table>
 </form>
        	
</body>

<script type="text/javascript">
	$(function(){
		$('#ConfirmBtn').click(function(){
			var name =$('#name').val();
			 if(name==''){
				 art.dialog.alert('任务名称不能为空！', '提示信息');
				 return false;
			 }
			 var win = art.dialog.open.origin;//来源页面
				$.ajax({
					type : 'post',
					url : "${ctx}/schedule/addSchedule",
					data :$('#xjrw').serialize(),
					cache : false,
					async : false,
					
					success : function(result) {
						if(result== 'true'){
							
				 			art.dialog.alert("保存成功！",function (){
					 		    art.dialog.close();  
					 		    win.location ="${ctx}/schedule/now";
				 			});
						}else{
							art.dialog.alert('保存失败！', '提示信息');
						}
					},
					error : function() {
						art.dialog.alert('请求异常！', '提示信息');
					}
				});
		})
		
	})
</script>
</html>