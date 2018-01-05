<%@ page language="java" pageEncoding="UTF-8"%>
<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.0 Transitional//EN">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<%@ include file="/common/meta.jsp"%>
<title>任务管理系统</title>
<link rel="stylesheet" type="text/css" href="${ctx}/css/common.css" />
<link rel="stylesheet" type="text/css" href="${ctx}/css/right1.css" />
<link rel="stylesheet" type="text/css" href="${ctx}/css/colorbox.css" />
<link rel="stylesheet" type="text/css" href="${ctx}/css/pop.css" />
<script type="text/javascript" src="${ctx}/js/jquery.min.js"></script>

<link href="${ctx}/css/defaultTheme1.css" rel="stylesheet" media="screen" />
<link href="${ctx}/css/myTheme1.css" rel="stylesheet" media="screen" />
<script type="text/javascript" src="${ctx}/js/My97DatePicker/WdatePicker.js" ></script>
<script src="${ctx}/js/jquery.fixedheadertable.js"></script>
<script src="${ctx}/js/demo.js"></script>
<script type="text/javascript" src="${ctx}/js/right_js.js"></script>
<script type="text/javascript" src="${ctx}/js/util/common.js"></script>
<script type="text/javascript" src="${ctx}/js/layer/skin/layer.css"></script> 
<script type="text/javascript" src="${ctx}/js/layer/skin/layer.ext.css"></script>
<script type="text/javascript" src="${ctx}/js/layer/layer.min.js"></script>
<script type="text/javascript" src="${ctx}/js/layer/extend/layer.ext.js"></script>

<!-- jquery-ui start -->
<link rel="stylesheet" href="${ctx}/js/jquery_ui/jquery-ui.css">
<script src="${ctx}/js/jquery_ui/jquery-ui.js"></script>
<script type="text/javascript">
    var availableTags = new Array();
    var availableCodes = new Array();
	$(function() {
	    $( "#projectName" ).autocomplete({
	      source: availableTags
	    });
	});
	function getProjectName(value){
		if(value!=null && value!=''){
			$.ajax({
				type : "POST", 
				url : '${ctx}/project/searchUtils.do',
				data : {"param":value},
				dataType : "json", 
				success : function(data){
					if(data.list!=null && data.list.length>0){
						availableTags = new Array();
						availableCodes = new Array();
						for ( var i = 0; i < data.list.length; i++) {
							availableTags[i]=data.list[i].proname;
							availableCodes[i]=data.list[i].code;
						}
						$( "#projectName" ).autocomplete({
					    	source: availableTags
					    });
					}
				}
	    	}); 
		}
	}
	
	function changeF(value) {
		//alert(value);
		for ( var i = 0; i < availableTags.length; i++) {
 			if(value==availableTags[i]){
				$("#code").val(availableCodes[i]);
				break;
			}else{
				$("#code").val('');
			}
		}
	}
</script>
<!-- jquery-ui end -->
</head>
<style>
	html,body{
		height:100%;
		overflow:hidden;
	}
	#bodyTR  td{
		text-align:center;
	}	
</style>
<body>
<c:if test="${resultFlag==1 }">
	<tags:message content="${message}" type="success" />
</c:if>
<c:if test="${resultFlag==0 }">
	<tags:message content="${message}" type="error" />
</c:if>
<form id ="f1" action="${ctx}/admin/listTask.do" method="POST">
<input type="hidden" id="page" name="page" value="1" />
<input type="hidden"  id="maxResult" name="maxResult" value="${pageView.maxresult}"/>
<input type="hidden" id="orgId" name="orgId"  value="${perforInfoReport.orgId}">
<input type="hidden" id="selectFlag" name="selectFlag" >
<div class="TAB_right">
<h1><b>位置：</b><a href="###">首页</a><span>&gt;</span><a href="###">任务查询</a></h1>
	<div class="retrieve_list">
			<table class="retrieve_table" width="100%" cellpadding="0" cellspacing="0">
					<tr>					
						<td>
							创建人：
							<input type="text" id="create_name" name="create_name"  style="width:80px" value="${create_name}" onblur="validateXml1(this)"></td>
						<td> 所属部门：
								<select name="department" id="department" class="sel" style="width:160px" onchange="toChnage(this.value)">
                              <c:choose>
                              	<c:when test="${userRole=='director' || userRole=='staff' || userRole=='BMJL' || userRole=='XMJL' || userRole=='TDJL' }">
                              		<c:forEach items="${dictionaryLlist}" var="dic" varStatus="status">
				                    	<c:if test="${dic.type_code=='01' && dic.code==taskInfo.department }">
				                    		<option value="${dic.code}" selected>${dic.name }</option>
				                    	</c:if>
				                    </c:forEach>
                              	</c:when>
                              	<c:otherwise>
	                                <option value="">请选择</option>
				                    <c:forEach items="${dictionaryLlist}" var="dic" varStatus="status">
				                    	<c:if test="${dic.type_code=='01' }">
				                    		<option value="${dic.code}" <c:if test="${dic.code==taskInfo.department}">selected</c:if>>${dic.name }</option>
				                    	</c:if>
				                    </c:forEach>
                              	</c:otherwise>
                              </c:choose>
                         	  </select>
						</td>
					    <td>
					    
					     所属团队：<select name="team" id="team" style="width: 100px;" class="sel">
                               <c:choose>
                              	<c:when test="${userRole=='staff' || userRole=='TDJL' && taskInfo.department!='10007' }">
                              		<c:forEach items="${dictionaryLlist}" var="dic" varStatus="status">
				                    	<c:if test="${dic.type_code=='02' && dic.code==taskInfo.team }">
				                    		<option value="${dic.code}" selected>${dic.name }</option>
				                    	</c:if>
				                    </c:forEach>
                              	</c:when>
                              	<c:when test="${userRole=='TDJL' && taskInfo.department=='10007' }">
                              		<c:forEach items="${dictionaryLlist}" var="dic" varStatus="status">
				                    	<c:if test="${dic.type_code=='02' && dic.parent_id=='1000006' }">
				                    		<option value="${dic.code}" <c:if test="${dic.code==taskInfo.team}">selected</c:if>>${dic.name }</option>
				                    	</c:if>
				                    </c:forEach>
                              	</c:when>
                              	<c:otherwise>
	                                <option value="">请选择</option>
	                                <c:if test="${taskInfo.department!=null && taskInfo.department!='' }">
										<c:forEach items="${childDictionaryList}" var="dic" varStatus="status">
					                    	<option value="${dic.code}" <c:if test="${dic.code==taskInfo.team}">selected</c:if>>${dic.name }</option>
					                    </c:forEach>
				                    </c:if>
                              	</c:otherwise>
                              </c:choose>
                       		  </select>
                       	</td>
                       	<td>	     
					    
					    打分范围：<select name="lowScore" id="lowScore" class="sel" style="width: 70px;">
	                                <option value="">请选择</option>
									<c:forEach items="${dictionaryLlist}" var="dic" varStatus="status">
				                    	<c:if test="${dic.type_code=='06' }">
				                    		<option value="${dic.name}" <c:if test="${dic.name==taskInfo.lowScore}">selected</c:if>>${dic.name }</option>
				                    	</c:if>
				                    </c:forEach>
                         		</select>&nbsp;&nbsp;至&nbsp;
                                <select name="highScore" id="highScore" class="sel" style="width: 70px;">
	                                <option value="">请选择</option>
									<c:forEach items="${dictionaryLlist}" var="dic" varStatus="status">
				                    	<c:if test="${dic.type_code=='06' }">
				                    		<option value="${dic.name}" <c:if test="${dic.name==taskInfo.highScore}">selected</c:if>>${dic.name }</option>
				                    	</c:if>
				                    </c:forEach>
                                </select>
                       										
						</td>
						<td> 创&nbsp;&nbsp;建&nbsp;&nbsp;时&nbsp;&nbsp;间：<input style="width: 90px;" readonly="readonly"  type="text" id="startTime" name="startTime" value= "${taskInfo.startTime}"  onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:true});"/>
						至&nbsp;&nbsp;<input style="width: 90px;" readonly="readonly"  type="text" id="endTime" name="endTime" value="${fn:substring(taskInfo.endTime, 0, 10)}"  onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:true});"/>						
						</td>				    															
					</tr>
					<tr>
					    <td>
					 		负责人：
					 			<input class="srk" type="text" style="width: 80px;" id="exec_name" name="exec_name" value="${exec_name }" onblur="validateXml1(this)" onkeydown="disableBackspace();" onkeyup="disableBackspace();">   
					   </td>
					   <td>
					 		任务内容：&nbsp;<input class="srk" type="text" style="width: 160px;" id="taskContent" name="taskContent" value="${taskInfo.taskContent }" onblur="validateXml1(this)" onkeydown="disableBackspace();" onkeyup="disableBackspace();">   
					   </td>
					   <td>
						任务状态：<select name="taskstatus" id="taskstatus" style="width: 100px;" class="sel2">
		                             <option value="">请选择</option>
					                 <c:forEach items="${dictionaryLlist}" var="dic" varStatus="status">
				                    	<c:if test="${dic.type_code=='05' }">
				                    		<option value="${dic.code}" <c:if test="${dic.code==taskInfo.taskstatus}">selected</c:if>>${dic.name }</option>
				                    	</c:if>
					                 </c:forEach>
		                       </select>										
						</td>		
						<td> 
							项目名称：<input class="srk" type="text" id="projectName" name="projectName" value= "${taskInfo.projectName}" placeholder="请输入项目名称关键字" style="width: 165px;"
							 onkeydown="getProjectName(this.value)" onkeyup="getProjectName(this.value)" onblur="changeF(this.value);">
						</td>
						<td> 计划完成时间：<input style="width: 90px;" readonly="readonly"  type="text" id="expectEndTime1" name="expectEndTime1" value= "${taskInfo.expectEndTime1}"  onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:true});"/>
						至&nbsp;&nbsp;<input style="width: 90px;" readonly="readonly"  type="text" id="expectEndTime2" name="expectEndTime2" value="${fn:substring(taskInfo.expectEndTime2, 0, 10)}"  onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:true});"/>						
						</td>						
					</tr>	
					<tr>
						<td colspan="5">&nbsp;&nbsp;
							<input class="form_now marginr10" type="button" value="查询" onclick="selectSubmit()">&nbsp;
							<input class="form_now marginr10" type="button" value="重置" onclick="resetForm('${userRole}','${positionId }')">&nbsp;
							<input class="form_now marginr10" type="button" value="导出数据" onclick="exportTask()">
							<c:if test="${userRole=='SUPER' }">&nbsp;
							<input class="form_now marginr10" type="button" value="删除" onclick="delTasks()">
							</c:if>
						</td>																	
					</tr>		
			</table>
	</div>
	<div class="infor_base">
	<div  class="btn_operate">
		</div>
		   <div class="container_12">
        	<div class="grid_8">
        	<form id="listForm" name="listForm" method="post">
        		<input type="hidden" name="orderByPro" id="orderByPro" value="${taskInfo.orderByPro }">
        		<input type="hidden" name="orderByFlag" id="orderByFlag" value="${taskInfo.orderByFlag }">
        		<input type="hidden" id="currentPage" name="currentPage" value='${taskInfo.page }' />
        		<table class="fancyTable" id="myTable02" cellpadding="0" cellspacing="0" style="table-layout:fixed;">
        		    <thead>
        		        <tr>
        		        	<c:if test="${userRole=='SUPER' }">
        		        	<th style="width:30px;">&nbsp;</th>
        		        	</c:if>
        		        	<th style="width:35px;">序号</th>
        		        	<th style="width:260px;" onclick="queryByOrder('taskcontent')"><a href="#" style="color:black">任务内容</a></th>
        		            <th style="width:100px;" onclick="queryByOrder('create_name')"><a href="#"  style="color:black">任务创建人</a></th>
        		            <th style="width:120px;" onclick="queryByOrder('createtime')"><a href="#"  style="color:black">创建时间</a></th>
        		            <th style="width:120px;" onclick="queryByOrder('jxzdate')"><a href="#"  style="color:black">接收时间（开始时间）</a></th>
        		            <th style="width:100px;" onclick="queryByOrder('exec_name')"><a href="#" style="color:black">任务负责人</a></th>
        		            <th style="width:120px;" onclick="queryByOrder('expectendtime')"><a href="#" style="color:black">计划完成时间</a></th>
        		            <th style="width:140px;" onclick="queryByOrder('department')"><a href="#" style="color:black">所属部门</a></th>
        		            <th style="width:100px;" onclick="queryByOrder('team')"><a href="#" style="color:black">所属团队</a></th>
                            <th style="width:120px;" onclick="queryByOrder('subdate')"><a href="#" style="color:black">提交时间</a></th>
                            <th style="width:95px;" onclick="queryByOrder('taskstatus')"><a href="#" style="color:black">任务状态</a></th>
                            <th style="width:95px;" onclick="queryByOrder('taskstatus')"><a href="#" style="color:black">任务完成状态</a></th>
        		            <th style="width:120px;" onclick="queryByOrder('enddate')"><a href="#" style="color:black">实际完成时间</a></th>
        		            <th style="width:95px;" onclick="queryByOrder('score')"><a href="#" style="color:black">任务打分</a></th>
        		            <th style="width:90px;" onclick="queryByOrder('taskworktime')"><a href="#" style="color:black">工作量</a></th>
        		            <th style="width:120px;" onclick="queryByOrder('projectname')"><a href="#" style="color:black">项目名称</a></th>
        		            <th style="width:120px;" onclick="queryByOrder('projectcode')"><a href="#" style="color:black">项目编号</a></th>
        		            <th style="width:120px;" onclick="queryByOrder('remark')"><a href="#" style="color:black">备注</a></th>
        		            
        		        </tr>
        		    </thead>
        		    <tbody id="bodyTR">
        		    <c:forEach items="${pageView.records}" var="task" varStatus="status">
        		        <tr>
        		        	<c:if test="${userRole=='SUPER' }">
        		        	<td><input type="checkbox" name="taskId" id="taskId" value="${task.id }"></td>
        		        	</c:if>
        		        	<td>${status.index + 1}</td>
                            <td  style="word-wrap:break-word;text-align: left;" >${task.taskcontent }</td>
                            <td >${task.create_name }</td>
                            <td >${fn:substring(task.createtime, 0, 10) }</td>
                            <td >${fn:substring(task.jxzdate, 0, 10) }</td>
                            <td >${task.exec_name }</td>
                            <td >${fn:substring(task.expectendtime, 0, 10) }</td>
                            <td >
                            	<c:forEach items="${dictionaryLlist}" var="dic" varStatus="status">
			                    	<c:if test="${dic.type_code=='01' }">
			                    		<c:if test="${dic.code==task.department}">${dic.name }</c:if>
			                    	</c:if>
			                    </c:forEach>
                            </td>
                            <td >
                            	<c:forEach items="${dictionaryLlist}" var="dic" varStatus="status">
			                    	<c:if test="${dic.type_code=='02' }">
			                    		<c:if test="${dic.code==task.team}">${dic.name }</c:if>
			                    	</c:if>
			                    </c:forEach>
                            </td>
                            <td>${fn:substring(task.subdate, 0, 16) }</td>
                            <td >
                            	<c:forEach items="${dictionaryLlist}" var="dic" varStatus="status">
			                    	<c:if test="${dic.type_code=='05' }">
			                    		<c:if test="${dic.code==task.taskstatus}">${dic.name }</c:if>
			                    	</c:if>
			                    </c:forEach>
                            </td>
                            <td>
                            	<c:choose>
                            		<c:when test="${task.tjtype=='5'}"><font color="red">延期完成</font></c:when>
                            		<c:when test="${task.tjtype=='6'}">按时完成</c:when>
                            		<c:when test="${task.tjtype=='7'}">提前完成</c:when>
                            		<c:otherwise>未完成</c:otherwise>
                            	</c:choose>
                            </td>
                            <td>${fn:substring(task.enddate, 0, 16) }</td>
                            <td >${task.score }</td>		
                            <td >${task.taskworktime }</td>		
                            <td style="word-wrap:break-word;text-align: left;">${task.projectname }</td>		
                            <td style="word-wrap:break-word;text-align: left;">${task.projectcode }</td>		
                            <td style="word-wrap:break-word;text-align: left;">${task.remark }</td>		
        		        </tr>
        		        </c:forEach>
        		    </tbody>
        		</table>
        		</form>
        	</div>
        	<div class="clear"></div>
        </div>	
     	<div class="pages">
        	<%@ include file="/common/pagination.jsp" %>
        </div>
	</div>

</div>
</form>
</body>

<script type="text/javascript">
	$(document).ready(function(){
		$(".fht-tbody").css("height", "85%");
		$(".grid_8").css("height","70%");
	})
	function selectSubmit(){
							
       var lowScore = $("#lowScore").val();
		var highScore = $("#highScore").val();
		if(lowScore!=null && lowScore!='' && highScore!=null && highScore!=''){
			if(parseInt(lowScore)>parseInt(highScore)){
				alert("选择最低分不能大于最高分！");
				return;
			}
		}
		
		var DATE_DELIMITER="-";       //日期分隔符
		
  		var strValue1=$("#startTime").val() .split(DATE_DELIMITER);
        var date1Temp=new Date(strValue1[0],parseInt(strValue1[1],10)-1,parseInt(strValue1[2],10));
        var strValue2=$("#endTime").val() .split(DATE_DELIMITER);
        var date2Temp=new Date(strValue2[0],parseInt(strValue2[1],10)-1,parseInt(strValue2[2],10));
        if(date1Temp.getTime()>date2Temp.getTime()){
        	alert("选择开始时间不能大于结束时间！");
        	return false;
        }	
		
  		var strValue11=$("#expectEndTime1").val() .split(DATE_DELIMITER);
        var date11Temp=new Date(strValue11[0],parseInt(strValue11[1],10)-1,parseInt(strValue11[2],10));
        var strValue22=$("#expectEndTime2").val() .split(DATE_DELIMITER);
        var date22Temp=new Date(strValue22[0],parseInt(strValue22[1],10)-1,parseInt(strValue22[2],10));
        if(date11Temp.getTime()>date22Temp.getTime()){
        	alert("选择开始时间不能大于结束时间！");
        	return false;
        }	
        
		$("#f1").attr("method","POST");
		$("#f1").attr("action","${ctx}/admin/listTask.do");
		$("#f1").submit();
		layer.load('加载中...',0);
	}	
	
	 //根据所选部门获取其下的团队列表
    function toChnage(id){
		$("#team option:not(:first)").remove();
		url = "${ctx}/user/change/team.do";
		$.ajax({ 
			type : "POST",  
			url: url, 
			data : "code="+id,
			dataType : 'json',
			success: function(data){
				for(var i=0;i<data.list.length;i++){
					$('#team').append('<option value="'+data.list[i].code+'">'+data.list[i].name+'</option>');
					
				}
			}
      		
      	});
	}
	
	//重置
	function resetForm(userRole,positionId){
		$("#create_name").val("");
		if (userRole!='staff') {
			$("#exec_name").val("");
		}
		$("#taskstatus").val("");
		$("#department").val("");
		$("#startTime").val("");
		$("#endTime").val("");
		if (userRole=='BMJL' || userRole=='XMJL') {
			$("#team").val("");
		}else{
			$("#team option").nextAll().remove();
		}
		$("#lowScore").val("");
		$("#highScore").val("");
		$("#taskContent").val("");
		$("#projectName").val("");
		$("#expectEndTime1").val("");
		$("#expectEndTime2").val("");
		
	}
	
	
	
	
	
	
	
	
	
	
	//到指定的分页页面
	function topage(page){
		$("#page").val(page);
		$("#listForm").attr("method","POST");
		$("#listForm").attr("action","${ctx}/admin/listTask.do");
		$("#listForm").submit();
	}	
	//到指定的分页页面
	function topagePagination(page,maxResult){
		$("#page").val(page);
		$("#maxResult").val(maxResult);
		$("#f1").attr("method","POST");
		$("#f1").attr("action","${ctx}/admin/listTask.do");
		$("#f1").submit();
	}	
	//导出数据
	function exportTask(){
	
		var lowScore = $("#lowScore").val();
		var highScore = $("#highScore").val();
		if(lowScore!=null && lowScore!='' && highScore!=null && highScore!=''){
			if(parseInt(lowScore)>parseInt(highScore)){
				alert("选择最低分不能大于最高分！");
				return;
			}
		}
		
		var startTime = $("#startTime").val();
		var endTime = $("#endTime").val();
		if(startTime!=null && startTime!='' && endTime!=null && endTime!=''){
			var date1 = new Date(startTime);
			var date2 = new Date(endTime);
			if(Date.parse(date1)>Date.parse(date2)){
				alert("选择开始时间不能大于结束时间！");
				return;
			}
		}
		
		var startTime1 = $("#expectEndTime1").val();
		var endTime1 = $("#expectEndTime2").val();
		if(startTime1!=null && startTime1!='' && endTime1!=null && endTime1!=''){
			var date11 = new Date(startTime1);
			var date22 = new Date(endTime1);
			if(Date.parse(date11)>Date.parse(date22)){
				alert("选择开始时间不能大于结束时间！");
				return;
			}
		}
		
		$("#f1").attr("method","POST");
		$("#f1").attr("action","${ctx}/admin/exportTask.do");
    	$("#f1").submit();
	
	}
	//删除任务
	function delTasks(){
		var ids = '';
		var checkboxs = $(":checkbox");
		for ( var i = 0; i < checkboxs.length; i++) {
			if(checkboxs[i].checked){
				ids = ids + checkboxs[i].value + ",";
			}
		}
		if(ids!=null && ids!=''){
			ids = ids.substring(0, ids.length-1);
		}else{
			alert("请选择要删除的行！");
			return;
		}
		var isDelete = confirm("确定要删除选中数据吗？");
		if(isDelete){
			$("#f1").attr("method","POST");
			$("#f1").attr("action","${ctx}/admin/delTasks.do?ids="+ids);
	    	$("#f1").submit();
		}
	}
	//排序
	function queryByOrder(orderBy){
		var obj = $("#bodyTR tr td").html();
		if(obj==null){
			return;
		}
		//var currentPage = $("#currentPage").val();
		//$("#page").val(currentPage);
		$("#orderByPro").val(orderBy);
		var orderByFlag = $("#orderByFlag").val();
		if(orderByFlag==null || orderByFlag=='' || orderByFlag==undefined || orderByFlag=='desc'){
			$("#orderByFlag").val("asc");
		}else{
			$("#orderByFlag").val("desc");
		}
		
       var lowScore = $("#lowScore").val();
		var highScore = $("#highScore").val();
		if(lowScore!=null && lowScore!='' && highScore!=null && highScore!=''){
			//if(lowScore>highScore){
			if(parseInt(lowScore)>parseInt(highScore)){
				alert("选择最低分不能大于最高分！");
				return;
			}
		}
		
		var DATE_DELIMITER="-";       //日期分隔符
		
  		var strValue1=$("#startTime").val() .split(DATE_DELIMITER);
        var date1Temp=new Date(strValue1[0],parseInt(strValue1[1],10)-1,parseInt(strValue1[2],10));
        var strValue2=$("#endTime").val() .split(DATE_DELIMITER);
        var date2Temp=new Date(strValue2[0],parseInt(strValue2[1],10)-1,parseInt(strValue2[2],10));
        if(date1Temp.getTime()>date2Temp.getTime()){
        	alert("选择开始时间不能大于结束时间！");
        	return false;
        }	
        
        var strValue11=$("#expectEndTime1").val() .split(DATE_DELIMITER);
        var date11Temp=new Date(strValue11[0],parseInt(strValue11[1],10)-1,parseInt(strValue11[2],10));
        var strValue22=$("#expectEndTime2").val() .split(DATE_DELIMITER);
        var date22Temp=new Date(strValue22[0],parseInt(strValue22[1],10)-1,parseInt(strValue22[2],10));
        if(date11Temp.getTime()>date22Temp.getTime()){
        	alert("选择开始时间不能大于结束时间！");
        	return false;
        }		
        
		$("#f1").attr("method","POST");
		$("#f1").attr("action","${ctx}/admin/listTask.do");
		$("#f1").submit(); 
	}
	
	/*input 属性readonly="readonly"时，禁用backspace事件*/
    function disableBackspace(){
    	var isReadOnly = $("#exec_name").prop("readonly");
	    if (isReadOnly && event.keyCode == 8) {
			event.preventDefault();
		}
    }
</script>
</html>