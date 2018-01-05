<%@ page language="java" pageEncoding="UTF-8"%>
<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.0 Transitional//EN">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<%@ include file="/common/meta.jsp"%>
<title>任务管理系统</title>

</head>
<style>
	html,body{
		height:100%;
		overflow:hidden;
	}
	
	.exportTable td{
		border: 1px solid #b9b6b6;
		text-align: center;
	}
	.exportTable tr{
		height: 35px;
	}
</style>
<body>
<div>
<!-- <h1><b>位置：</b><a href="###">首页</a><span>&gt;</span><a href="###">工作月报</a></h1> -->
	<%  
        String exportToExcel =  (String)request.getAttribute("exportToExcel");  
        String nowTime =  (String)request.getAttribute("nowTime");  
        if (exportToExcel != null  
                && exportToExcel.toString().equalsIgnoreCase("YES")) {  
            response.setContentType("application/vnd.ms-excel"); 
            response.setHeader("Content-Disposition", "attachment; filename="  
                    +new String(("工作月报-"+nowTime).getBytes("gbk"),"iso8859-1")+".xls");  
   
        }  
    %> 
	<div class="infor_base">
       	<div class="" style="overflow: auto;width: 1100px;height: 500px;">
       		<table class="exportTable" id="exportTable" cellpadding="0" cellspacing="0" style="table-layout:fixed;overflow:auto;width:3000px;border: 1px solid #b9b6b6;">
      		        <tr><td colspan="10" style="text-align: center;border: 1px solid #b9b6b6;font-size: 22px;">工作月报</td><td colspan="14"></td></tr>
      		        <tr>
						<td colspan="15" style="width:100px;text-align: left;border: 1px solid #b9b6b6;font-weight: bold;">部门名称：<span id="department">${workPlanList1[0].departmentName}</span></td>
						<td colspan="9" style="width:100px;text-align: left;border: 1px solid #b9b6b6;font-weight: bold;">提交日期：<span id="addTime"></span></td>
					</tr>
					<tr><td colspan="24" style="text-align: left;border: 1px solid #b9b6b6;font-weight: bold;">一、新项目建设类工作</td></tr>
					<tr>
						<td rowspan="2" style="width:35px;border: 1px solid #b9b6b6;">序号</td>
						<td rowspan="2" style="width:120px;border: 1px solid #b9b6b6;">项目编号</td>
						<td rowspan="2" style="width:120px;border: 1px solid #b9b6b6;">项目名称</td>
						<td rowspan="2" style="width:240px;border: 1px solid #b9b6b6;">本月部门工作计划/目标</td>
						<td rowspan="2" style="width:120px;border: 1px solid #b9b6b6;">重要程度</td>
						<td rowspan="2" style="width:100px;border: 1px solid #b9b6b6;">负责人</td>
						<td rowspan="2" style="width:150px;border: 1px solid #b9b6b6;">外部确认人</td>
			
						<td colspan="3" style="border: 1px solid #b9b6b6;text-align: center;">第一周</td>
						<td colspan="3" style="border: 1px solid #b9b6b6;text-align: center;">第二周</td>
						<td colspan="3" style="border: 1px solid #b9b6b6;text-align: center;">第三周</td>
						<td colspan="3" style="border: 1px solid #b9b6b6;text-align: center;">第四周</td>
						<td colspan="3" style="border: 1px solid #b9b6b6;text-align: center;">第五周</td>
						
						<td rowspan="2" style="width:500px;border: 1px solid #b9b6b6;text-align: center;">遇到风险</td>
						<td rowspan="2" style="width:500px;border: 1px solid #b9b6b6;text-align: center;">采取措施</td>
					</tr>
					<tr>
						<td style="width:200px;border: 1px solid #b9b6b6;text-align: center;">工作内容</td>
						<td style="width:150px;border: 1px solid #b9b6b6;text-align: center;">团队成员</td>
						<td style="width:120px;border: 1px solid #b9b6b6;text-align: center;">完成情况</td>
			
						<td style="width:200px;border: 1px solid #b9b6b6;text-align: center;">工作内容</td>
						<td style="width:150px;border: 1px solid #b9b6b6;text-align: center;">团队成员</td>
						<td style="width:120px;border: 1px solid #b9b6b6;text-align: center;">完成情况</td>
			
						<td style="width:200px;border: 1px solid #b9b6b6;text-align: center;">工作内容</td>
						<td style="width:150px;border: 1px solid #b9b6b6;text-align: center;">团队成员</td>
						<td style="width:120px;border: 1px solid #b9b6b6;text-align: center;">完成情况</td>
			
						<td style="width:200px;border: 1px solid #b9b6b6;text-align: center;">工作内容</td>
						<td style="width:150px;border: 1px solid #b9b6b6;text-align: center;">团队成员</td>
						<td style="width:120px;border: 1px solid #b9b6b6;text-align: center;">完成情况</td>
			
						<td style="width:200px;border: 1px solid #b9b6b6;text-align: center;">工作内容</td>
						<td style="width:150px;border: 1px solid #b9b6b6;text-align: center;">团队成员</td>
						<td style="width:120px;border: 1px solid #b9b6b6;text-align: center;">完成情况</td>
					</tr>
      		    	<c:forEach items="${workPlanList1}" var="work" varStatus="status">
      		        <tr>
      		        	<td style="border: 1px solid #b9b6b6;">${status.index + 1}</td>
                        <td style="word-wrap:break-word;border: 1px solid #b9b6b6;" >${work.projectCode }</td>
                        <td style="word-wrap:break-word;border: 1px solid #b9b6b6;" >${work.projectName }</td>
                        <td style="border: 1px solid #b9b6b6;">${work.departmentPlan }</td>
                       	<c:choose>
                       		<c:when test="${work.importance==1 }"><td  style="word-wrap:break-word;background-color: red;border: 1px solid #b9b6b6;" >${work.importanceName}</td></c:when>
                       		<c:when test="${work.importance==2 }"><td  style="word-wrap:break-word;background-color:#F4B084;border: 1px solid #b9b6b6;">${work.importanceName}</td></c:when>
                       		<c:when test="${work.importance==3 }"><td  style="word-wrap:break-word;background-color: #B4C6E7;border: 1px solid #b9b6b6;">${work.importanceName}</td></c:when>
                       		<c:when test="${work.importance==4 }"><td  style="word-wrap:break-word;background-color: #92D050;border: 1px solid #b9b6b6;">${work.importanceName}</td></c:when>
                       		<c:otherwise><td></td></c:otherwise>
                       	</c:choose>
                        <td style="word-wrap:break-word;border: 1px solid #b9b6b6;">${work.principalName }</td>
                        <td style="word-wrap:break-word;border: 1px solid #b9b6b6;">${work.verifier }</td>
                        
                        <td style="border: 1px solid #b9b6b6;">${work.content1 }</td>
						<td style="word-wrap:break-word;border: 1px solid #b9b6b6;">${work.teamMember1 }</td>
						<td style="word-wrap:break-word;border: 1px solid #b9b6b6;">${work.completeStatus1 }</td>
						
						<td style="border: 1px solid #b9b6b6;">${work.content2 }</td>
						<td style="word-wrap:break-word;border: 1px solid #b9b6b6;">${work.teamMember2 }</td>
						<td style="word-wrap:break-word;border: 1px solid #b9b6b6;">${work.completeStatus2 }</td>
						
						<td style="border: 1px solid #b9b6b6;">${work.content3 }</td>
						<td style="word-wrap:break-word;border: 1px solid #b9b6b6;">${work.teamMember3 }</td>
						<td style="word-wrap:break-word;border: 1px solid #b9b6b6;">${work.completeStatus3 }</td>
						
						<td style="border: 1px solid #b9b6b6;">${work.content4 }</td>
						<td style="word-wrap:break-word;border: 1px solid #b9b6b6;">${work.teamMember4 }</td>
						<td style="word-wrap:break-word;border: 1px solid #b9b6b6;">${work.completeStatus4 }</td>
						
						<td style="border: 1px solid #b9b6b6;">${work.content5 }</td>
						<td style="word-wrap:break-word;border: 1px solid #b9b6b6;">${work.teamMember5 }</td>
						<td style="word-wrap:break-word;border: 1px solid #b9b6b6;">${work.completeStatus5 }</td>
						
						<td style="word-wrap:break-word;border: 1px solid #b9b6b6;">${work.risk }</td>
						<td style="word-wrap:break-word;border: 1px solid #b9b6b6;">${work.measures }</td>
      		        </tr>
      		        </c:forEach>
      		        
      		        <tr><td colspan="24" style="text-align: left;border: 1px solid #b9b6b6;font-weight: bold;">二、现有项目支持类工作</td></tr>
					<tr>
						<td rowspan="2" style="width:35px;border: 1px solid #b9b6b6;">序号</td>
						<td rowspan="2" style="width:120px;border: 1px solid #b9b6b6;">项目编号</td>
						<td rowspan="2" style="width:120px;border: 1px solid #b9b6b6;">项目名称</td>
						<td rowspan="2" style="width:200px;border: 1px solid #b9b6b6;">本月部门工作计划/目标</td>
						<td rowspan="2" style="width:120px;border: 1px solid #b9b6b6;">重要程度</td>
						<td rowspan="2" style="width:100px;border: 1px solid #b9b6b6;">负责人</td>
						<td rowspan="2" style="width:150px;border: 1px solid #b9b6b6;">外部确认人</td>
			
						<td colspan="3" style="width:420px;border: 1px solid #b9b6b6;text-align: center;">第一周</td>
						<td colspan="3" style="width:420px;border: 1px solid #b9b6b6;text-align: center;">第二周</td>
						<td colspan="3" style="width:420px;border: 1px solid #b9b6b6;text-align: center;">第三周</td>
						<td colspan="3" style="width:420px;border: 1px solid #b9b6b6;text-align: center;">第四周</td>
						<td colspan="3" style="width:420px;border: 1px solid #b9b6b6;text-align: center;">第五周</td>
						
						<td rowspan="2" style="width:500px;border: 1px solid #b9b6b6;text-align: center;">遇到风险</td>
						<td rowspan="2" style="width:500px;border: 1px solid #b9b6b6;text-align: center;">采取措施</td>
					</tr>
					<tr>
						<td style="width:150px;border: 1px solid #b9b6b6;text-align: center;">工作内容</td>
						<td style="width:150px;border: 1px solid #b9b6b6;text-align: center;">团队成员</td>
						<td style="width:120px;border: 1px solid #b9b6b6;text-align: center;">完成情况</td>
			
						<td style="width:150px;border: 1px solid #b9b6b6;text-align: center;">工作内容</td>
						<td style="width:150px;border: 1px solid #b9b6b6;text-align: center;">团队成员</td>
						<td style="width:120px;border: 1px solid #b9b6b6;text-align: center;">完成情况</td>
			
						<td style="width:150px;border: 1px solid #b9b6b6;text-align: center;">工作内容</td>
						<td style="width:150px;border: 1px solid #b9b6b6;text-align: center;">团队成员</td>
						<td style="width:120px;border: 1px solid #b9b6b6;text-align: center;">完成情况</td>
			
						<td style="width:150px;border: 1px solid #b9b6b6;text-align: center;">工作内容</td>
						<td style="width:150px;border: 1px solid #b9b6b6;text-align: center;">团队成员</td>
						<td style="width:120px;border: 1px solid #b9b6b6;text-align: center;">完成情况</td>
			
						<td style="width:150px;border: 1px solid #b9b6b6;text-align: center;">工作内容</td>
						<td style="width:150px;border: 1px solid #b9b6b6;text-align: center;">团队成员</td>
						<td style="width:120px;border: 1px solid #b9b6b6;text-align: center;">完成情况</td>
					</tr>
      		    	<c:forEach items="${workPlanList2}" var="work" varStatus="status">
      		        <tr>
      		        	<td style="border: 1px solid #b9b6b6;">${status.index + 1}</td>
                        <td style="word-wrap:break-word;border: 1px solid #b9b6b6;" >${work.projectCode }</td>
                        <td style="word-wrap:break-word;border: 1px solid #b9b6b6;" >${work.projectName }</td>
                        <td style="border: 1px solid #b9b6b6;">${work.departmentPlan }</td>
                       	<c:choose>
                       		<c:when test="${work.importance==1 }"><td  style="word-wrap:break-word;background-color: red;border: 1px solid #b9b6b6;" >${work.importanceName}</td></c:when>
                       		<c:when test="${work.importance==2 }"><td  style="word-wrap:break-word;background-color:#F4B084;border: 1px solid #b9b6b6;">${work.importanceName}</td></c:when>
                       		<c:when test="${work.importance==3 }"><td  style="word-wrap:break-word;background-color: #B4C6E7;border: 1px solid #b9b6b6;">${work.importanceName}</td></c:when>
                       		<c:when test="${work.importance==4 }"><td  style="word-wrap:break-word;background-color: #92D050;border: 1px solid #b9b6b6;">${work.importanceName}</td></c:when>
                       		<c:otherwise><td></td></c:otherwise>
                       	</c:choose>
                        <td style="word-wrap:break-word;border: 1px solid #b9b6b6;">${work.principalName }</td>
                        <td style="word-wrap:break-word;border: 1px solid #b9b6b6;">${work.verifier }</td>
                        
                        <td style="border: 1px solid #b9b6b6;">${work.content1 }</td>
						<td style="word-wrap:break-word;border: 1px solid #b9b6b6;">${work.teamMember1 }</td>
						<td style="word-wrap:break-word;border: 1px solid #b9b6b6;">${work.completeStatus1 }</td>
						
						<td style="border: 1px solid #b9b6b6;">${work.content2 }</td>
						<td style="word-wrap:break-word;border: 1px solid #b9b6b6;">${work.teamMember2 }</td>
						<td style="word-wrap:break-word;border: 1px solid #b9b6b6;">${work.completeStatus2 }</td>
						
						<td style="border: 1px solid #b9b6b6;">${work.content3 }</td>
						<td style="word-wrap:break-word;border: 1px solid #b9b6b6;">${work.teamMember3 }</td>
						<td style="word-wrap:break-word;border: 1px solid #b9b6b6;">${work.completeStatus3 }</td>
						
						<td style="border: 1px solid #b9b6b6;">${work.content4 }</td>
						<td style="word-wrap:break-word;border: 1px solid #b9b6b6;">${work.teamMember4 }</td>
						<td style="word-wrap:break-word;border: 1px solid #b9b6b6;">${work.completeStatus4 }</td>
						
						<td style="border: 1px solid #b9b6b6;">${work.content5 }</td>
						<td style="word-wrap:break-word;border: 1px solid #b9b6b6;">${work.teamMember5 }</td>
						<td style="word-wrap:break-word;border: 1px solid #b9b6b6;">${work.completeStatus5 }</td>
						
						<td style="word-wrap:break-word;border: 1px solid #b9b6b6;">${work.risk }</td>
						<td style="word-wrap:break-word;border: 1px solid #b9b6b6;">${work.measures }</td>
      		        </tr>
      		        </c:forEach>
       		</table>
       	</div>
	</div>
</div>
</body>
</html>