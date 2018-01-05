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
<script type="text/javascript" src="${ctx}/js/alert/g_alert.js?1=1"></script>

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
<form id ="f1" action="${ctx}/admin/listScore.do" method="POST">
<input type="hidden" id="page" name="page" value="1" />
<input type="hidden"  id="maxResult" name="maxResult" value="${pageView.maxresult}"/>
<input type="hidden" id="orgId" name="orgId"  value="${perforInfoReport.orgId}">
<input type="hidden" id="selectFlag" name="selectFlag" >
<div class="TAB_right">
<h1><b>位置：</b><a href="###">首页</a><span>&gt;</span><a href="###">得分查询</a></h1>
	<div class="retrieve_list">
			<table class="retrieve_table" width="100%" cellpadding="0" cellspacing="0">
					<tr>
						<td>&nbsp;&nbsp; 统计时间：<input style="width: 90px;" readonly="readonly"  type="text" id="gMonth" name="gMonth" value= "${userScore.gMonth}"  onclick="WdatePicker({dateFmt:'yyyy-MM',isShowClear:true});"/>
						<td>&nbsp;&nbsp; 员工编号：<input style="width: 80px;" type="text" id="login_name" name="login_name" value= "${userScore.login_name}"/></td>
						<td>&nbsp;&nbsp; 姓名：<input style="width: 80px;"  type="text" id="user_name" name="user_name" value= "${userScore.user_name}"/></td>
						
						<td> 所属部门：
							<select name="department" id="department" class="sel" style="width:150px">
			                    <c:choose>
									<c:when test="${userRole=='BMJL'}">
	                              		<c:forEach items="${dictionaryLlist}" var="dic" varStatus="status">
					                    	<c:if test="${dic.type_code=='01' && dic.code==userScore.department }">
					                    		<option value="${dic.code}" selected>${dic.name }</option>
					                    	</c:if>
					                    </c:forEach>
	                              	</c:when>
                              		<c:otherwise>
		                                <option value="">请选择</option>
					                   	<c:forEach items="${dictionaryLlist}" var="dic" varStatus="status">
						                   	<c:if test="${dic.type_code=='01'}">
						                    	<option value="${dic.code}" <c:if test="${dic.code==userScore.department}">selected</c:if>>${dic.name }</option>
						                    </c:if>
					                    </c:forEach>
                              		</c:otherwise>
                              	</c:choose>
                         	</select>
						</td>
						<td>是否有任务：
							<select name="g_remark" id="g_remark" class="sel" style="width:105px">
                                <option value="">请选择</option>
				                <option value="正常情况" <c:if test="${'正常情况'==userScore.g_remark}">selected</c:if>>正常情况</option>
				                <option value="没有已完成的任务" <c:if test="${'没有已完成的任务'==userScore.g_remark}">selected</c:if>>没有完成任务</option>
				                <option value="暂且没有任务" <c:if test="${'暂且没有任务'==userScore.g_remark}">selected</c:if>>暂且没有任务</option>
                         	</select>
						</td>
						<td align="right">&nbsp;&nbsp;
							<shiro:hasPermission name="user_score:queryScore">
								<input class="form_now marginr10" type="button" value="查询" onclick="selectSubmit()">&nbsp;&nbsp;
							</shiro:hasPermission>
							<%-- <input class="form_now marginr10" type="button" value="重置" onclick="resetForm('${userRole}','${positionId }')">&nbsp;&nbsp;&nbsp;&nbsp; --%>
							<shiro:hasPermission name="user_score:exportScore">
								<input class="form_now marginr10" type="button" value="导出" onclick="exportUserScore()">&nbsp;&nbsp;
							</shiro:hasPermission>
							<shiro:hasPermission name="user_score:execute_task">
								<input class="form_now marginr10" type="button" value="执行" onclick="executeTask()">&nbsp;&nbsp;
							</shiro:hasPermission>
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
        		<input type="hidden" name="orderByPro" id="orderByPro" value="${userScore.orderByPro }">
        		<input type="hidden" name="orderByFlag" id="orderByFlag" value="${userScore.orderByFlag }">
        		<table class="fancyTable" id="myTable02" cellpadding="0" cellspacing="0" style="table-layout:fixed;">
        		    <thead>
        		        <tr>
        		        	<th style="width:35px;">序号</th>
        		        	<th style="width:100px;"><a href="#" style="color:black">员工编号</a></th>
        		        	<th style="width:80px;"><a href="#" style="color:black">姓名</a></th>
        		            <th style="width:130px;"><a href="#"  style="color:black">所属部门</a></th>
        		            <th style="width:120px;"><a href="#"  style="color:black">所属团队</a></th>
        		            <th style="width:100px;"><a href="#"  style="color:black">职位</a></th>
        		            <th style="width:100px;"><a href="#"  style="color:black">职位类别</a></th>
        		            <th style="width:120px;" onclick="queryByOrder('g_scores')"><a href="#" style="color:black">总得分</a></th>
        		            <th style="width:80px;"><a href="#" style="color:black">任务打分</a></th>
        		            <th style="width:80px;"><a href="#" style="color:black">任务状态分</a></th>
        		            <th style="width:120px;"><a href="#" style="color:black">饱和度值</a></th>
        		            <th style="width:80px;"><a href="#" style="color:black">月份</a></th>
        		            <th style="width:120px;"><a href="#" style="color:black">备注</a></th>
        		        </tr>
        		    </thead>
        		    <tbody id="bodyTR">
        		    <c:forEach items="${pageView.records}" var="grades" varStatus="status">
        		        <tr>
        		        	<td>${status.index + 1}</td>
                            <td  style="word-wrap:break-word;text-align: center;" >${grades.login_name }</td>
                            <td  style="word-wrap:break-word;text-align: center;" >${grades.user_name }</td>
                            <td  style="word-wrap:break-word;text-align: center;" >${grades.department_Name }</td>
                            <td  style="word-wrap:break-word;text-align: center;" >${grades.team_Name }</td>
                            <td  style="word-wrap:break-word;text-align: center;" >${grades.position_Name }</td>
                            <td  style="word-wrap:break-word;text-align: center;" >${grades.post_Name }</td>
                            <td  style="word-wrap:break-word;text-align: center;" >
                            	<c:choose>
	                            	<c:when test="${grades.g_scores=='N/A' }">未获取到考勤信息</c:when>
	                            	<c:otherwise>${grades.g_scores }</c:otherwise>
                            	</c:choose>
                            </td>
                            <td  style="word-wrap:break-word;text-align: center;" >${grades.g_task_score }</td>
                            <td  style="word-wrap:break-word;text-align: center;" >${grades.g_task_status_score }</td>
                            <td  style="word-wrap:break-word;text-align: center;" >${grades.g_saturation_score }</td>
                            <td  style="word-wrap:break-word;text-align: center;" >${grades.g_month }</td>
                            <td  style="word-wrap:break-word;text-align: center;" >${grades.g_remark }</td>
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
		$(".grid_8").css("height","75%");
	})
	function selectSubmit(){
							
		/* var DATE_DELIMITER="-";       //日期分隔符
		
  		var strValue1=$("#startTime").val() .split(DATE_DELIMITER);
        var date1Temp=new Date(strValue1[0],parseInt(strValue1[1],10)-1,parseInt(strValue1[2],10));
        var strValue2=$("#endTime").val() .split(DATE_DELIMITER);
        var date2Temp=new Date(strValue2[0],parseInt(strValue2[1],10)-1,parseInt(strValue2[2],10));
        if(date1Temp.getTime()>date2Temp.getTime()){
        	alert("选择开始时间不能大于结束时间！");
        	return false;
        }		
       var lowScore = $("#lowScore").val();
		var highScore = $("#highScore").val();
		if(lowScore!=null && lowScore!='' && highScore!=null && highScore!=''){
			if(parseInt(lowScore)>parseInt(highScore)){
				alert("选择最低分不能大于最高分！");
				return;
			}
		} */
		$("#f1").attr("method","POST");
		$("#f1").attr("action","${ctx}/admin/listScore.do");
		$("#f1").submit();
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
	//到指定的分页页面
	function topage(page){
		$("#page").val(page);
		$("#listForm").attr("method","POST");
		$("#listForm").attr("action","${ctx}/admin/listScore.do");
		$("#listForm").submit();
	}	
	//到指定的分页页面
	function topagePagination(page,maxResult){
		$("#page").val(page);
		$("#maxResult").val(maxResult);
		$("#f1").attr("method","POST");
		$("#f1").attr("action","${ctx}/admin/listScore.do");
		$("#f1").submit();
	}	
	//导出数据
	function exportUserScore(){
	
		/* var startTime = $("#startTime").val();
		var endTime = $("#endTime").val();
		if(startTime!=null && startTime!='' && endTime!=null && endTime!=''){
			var date1 = new Date(startTime);
			var date2 = new Date(endTime);
			if(Date.parse(date1)>Date.parse(date2)){
				alert("选择开始时间不能大于结束时间！");
				return;
			}
		} */
		$("#f1").attr("method","POST");
		$("#f1").attr("action","${ctx}/admin/exportScore.do");
    	$("#f1").submit();
	
	}
	//执行定时任务
	function executeTask(){
    	var isExecute = confirm("确认要执行吗？");
    	if(isExecute){
	    	$.ajax({
				type : "POST", 
				url : "${ctx}/admin/executeScoreTaskEr.do",
				dataType : "json", 
				success : function(data){
					if(data.res=="ok"){
						g_alert("success","执行成功！",null,"${ctx}"); 
					}else{
						g_alert("error","执行失败！",null,"${ctx}"); 
					}
				}
	    	}); 
    	}
	}
	/*input 属性readonly="readonly"时，禁用backspace事件*/
    function disableBackspace(){
    	var isReadOnly = $("#exec_name").prop("readonly");
	    if (isReadOnly && event.keyCode == 8) {
			event.preventDefault();
		}
    }
    
    //排序
	function queryByOrder(orderBy){
		var obj = $("#bodyTR tr td").html();
		if(obj==null){
			return;
		}
		$("#orderByPro").val(orderBy);
		var orderByFlag = $("#orderByFlag").val();
		if(orderByFlag==null || orderByFlag=='' || orderByFlag==undefined || orderByFlag=='desc'){
			$("#orderByFlag").val("asc");
		}else{
			$("#orderByFlag").val("desc");
		}
		
		$("#f1").attr("method","POST");
		$("#f1").attr("action","${ctx}/admin/listScore.do");
		$("#f1").submit(); 
	}
</script>
</html>