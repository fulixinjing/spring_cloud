<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<!DOCTYPE html>
<html>
 <head>
 <%@include file="/common/taglibs.jsp"%> 
  <script type="text/javascript" src="${ctx}/js/My97DatePicker/WdatePicker.js" ></script>
  </head>
  <body class="">
计划完成时间：<input style="width: 90px;" readonly="readonly"  type="text" id="expectEndTime1" name="expectEndTime1" value= "${taskInfo.expectEndTime1}"  onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false});"/>

</body>
</html>