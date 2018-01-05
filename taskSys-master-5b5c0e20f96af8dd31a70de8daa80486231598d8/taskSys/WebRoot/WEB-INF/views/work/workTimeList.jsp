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
<form id ="f1" action="${ctx}/work/getWorkTimeList.do" method="POST">
<input type="hidden" id="page" name="page" value="1" />
<input type="hidden"  id="maxResult" name="maxResult" value="${pageView.maxresult}"/>
<input type="hidden" id="orgId" name="orgId"  value="${perforInfoReport.orgId}">
<input type="hidden" id="selectFlag" name="selectFlag" >
<div class="TAB_right">
<h1><b>位置：</b><a href="###">首页</a><span>&gt;</span><a href="###">工作量统计</a></h1>
	<div class="retrieve_list">
			<table class="retrieve_table" width="100%" cellpadding="0" cellspacing="0">
					<tr>
						<td width="180px">&nbsp;&nbsp; 统计时间：<input style="width: 90px;" readonly="readonly"  type="text" id="statisticsTime" name="statisticsTime" value= "${workTime.statisticsTime}"  onclick="WdatePicker({dateFmt:'yyyy-MM',isShowClear:true});"/></td>
						<td width="160px">&nbsp;&nbsp; 员工编号：<input style="width: 80px;" type="text" id="empCode" name="empCode" value= "${workTime.empCode}"/></td>
						<td width="150px">&nbsp;&nbsp; 姓名：<input style="width: 80px;"  type="text" id="userName" name="userName" value= "${workTime.userName}"/></td>
						<td align="left" width="260px"> 所属部门：
							<select name="department" id="department" class="sel" style="width:170px">
								<c:choose>
									<c:when test="${userRole=='BMJL'}">
	                              		<c:forEach items="${dictionaryLlist}" var="dic" varStatus="status">
					                    	<c:if test="${dic.type_code=='01' && dic.code==workTime.department }">
					                    		<option value="${dic.code}" selected>${dic.name }</option>
					                    	</c:if>
					                    </c:forEach>
	                              	</c:when>
                              		<c:otherwise>
                              			<option value="">请选择</option>
					                    <c:forEach items="${dictionaryLlist}" var="dic" varStatus="status">
					                    	<option value="${dic.code}" <c:if test="${dic.code==workTime.department}">selected</c:if>>${dic.name }</option>
					                    </c:forEach>
                              		</c:otherwise>
								</c:choose>
                                
                         	  </select>
						</td>
						<td align="left" width="110px"> 类型：
							<select name="type" id="type" class="sel" onchange="javascript:displayDeclare(this.value);">
                                <!-- <option value="">请选择</option> -->
                                <option value="1" <c:if test="${workTime.type=='1'}">selected</c:if>>员工</option>
                                <option value="3" <c:if test="${workTime.type=='3'}">selected</c:if>>部门</option>
                         	</select>
						</td>
						<td align="right">&nbsp;&nbsp;
							<shiro:hasPermission name="user_work:queryWork">
								<input class="form_now marginr10" type="button" value="查询" onclick="selectSubmit()">&nbsp;&nbsp;&nbsp;&nbsp;
							</shiro:hasPermission>
							<%-- <input class="form_now marginr10" type="button" value="重置" onclick="resetForm('${userRole}','${positionId }')">&nbsp;&nbsp;&nbsp;&nbsp; --%>
							<shiro:hasPermission name="user_work:exportWork">
								<input class="form_now marginr10" type="button" value="导出" onclick="exportWorkTime()">&nbsp;&nbsp;&nbsp;&nbsp;
							</shiro:hasPermission>
							<%-- <shiro:hasPermission name="user_score:execute_task">
								<input class="form_now marginr10" type="button" value="执行" onclick="executeTask()">&nbsp;&nbsp;&nbsp;&nbsp;
							</shiro:hasPermission> --%>
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
        		<input type="hidden" name="orderByPro" id="orderByPro" value="${workTime.orderByPro }">
        		<input type="hidden" name="orderByFlag" id="orderByFlag" value="${workTime.orderByFlag }">
        		<table class="fancyTable" id="myTable02" cellpadding="0" cellspacing="0" style="table-layout:fixed;">
        		    <thead>
        		        <tr>
        		        	<th style="width:35px;">序号</th>
        		        	<c:if test="${workTime.type!='3'}">
        		        	<th style="width:100px;"><a href="#" style="color:black">员工编号</a></th>
        		        	<th style="width:80px;"><a href="#" style="color:black">姓名</a></th>
        		        	</c:if>
        		            <th style="width:120px;"><a href="#"  style="color:black">所属部门</a></th>
        		            <c:if test="${workTime.type!='3'}">
        		            <th style="width:120px;"><a href="#"  style="color:black">所属团队</a></th>
        		            <th style="width:100px;"><a href="#"  style="color:black">职位</a></th>
        		            <th style="width:100px;"><a href="#"  style="color:black">职位类别</a></th>
        		            </c:if>
        		            <th style="width:80px;" ><a href="#" style="color:black">总工作量</a></th>
        		            <c:if test="${workTime.type!='3'}">
        		            <th style="width:80px;"><a href="#" style="color:black">出勤天数</a></th>
        		            </c:if>
        		            <th style="width:120px;" ><a href="#" style="color:black">饱和度</a></th>
        		            <th style="width:80px;"><a href="#" style="color:black">月份</a></th>
        		        </tr>
        		    </thead>
        		    <tbody id="bodyTR">
        		    <c:forEach items="${pageView.records}" var="work" varStatus="status">
        		        <tr>
        		        	<td>${status.index + 1}</td>
        		        	<c:if test="${workTime.type!='3'}">
                            <td  style="word-wrap:break-word;text-align: center;" >${work.loginName }</td>
                            <td  style="word-wrap:break-word;text-align: center;" >${work.userName }</td>
                            </c:if>
                            <td  style="word-wrap:break-word;text-align: center;" >${work.departmentName }</td>
                            <c:if test="${workTime.type!='3'}">
                            <td  style="word-wrap:break-word;text-align: center;" >${work.teamName }</td>
                            <td  style="word-wrap:break-word;text-align: center;" >${work.positionName }</td>
                            <td  style="word-wrap:break-word;text-align: center;" >${work.postName }</td>
                            </c:if>
                            <td  style="word-wrap:break-word;text-align: center;" >${work.allWorkTime }</td>
                            <c:if test="${workTime.type!='3'}">
                            <td  style="word-wrap:break-word;text-align: center;" >${work.attenceDay }</td>
                            </c:if>
                            <td  style="word-wrap:break-word;text-align: center;" >
	                            <c:choose>
	                            	<c:when test="${work.saturation!='N/A'}">
	                            		<font <c:if test="${work.saturation!='N/A' && work.saturation<0.85}">color="red"</c:if>>
	                            		<fmt:formatNumber value="${work.saturation*100 }" pattern="#0.00"/>%</font>
	                            	</c:when>
	                            	<c:when test="${work.saturation=='N/A'}">未获取到考勤信息</c:when>
	                            	<c:otherwise>
	                            		${work.saturation }
	                            	</c:otherwise>
	                            </c:choose>
                            </td>
                            <td  style="word-wrap:break-word;text-align: center;" >${work.statisticsTime }</td>
        		        </tr>
        		        </c:forEach>
        		    </tbody>
        		</table>
        		</form>
        	</div>
	    	<div id="saturationHelpInfo">
	    		<h2><b>饱和度计算说明：</b></h2>
	    		<div id="displayDeclare">
	    			<p>（1）员工工作量饱和度值=该员工所有任务工作量总和 /（该员工实际出勤天数× 8）*100%；</p>
	    			<p>（2）员工实际出勤天数以科技公司考勤管理系统中数据为准，嘉盛职场和海淀黄庄职场的考勤数据尚未同步到考勤管理系统，因此获取到的出勤数据与实际出勤情况可能会有差异。</p>
	    		</div>
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
		$(".grid_8").css("height","65%");
	})
	function selectSubmit(){
							
		$("#f1").attr("method","POST");
		$("#f1").attr("action","${ctx}/work/getWorkTimeList.do");
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
		$("#listForm").attr("action","${ctx}/work/getWorkTimeList.do");
		$("#listForm").submit();
	}	
	//到指定的分页页面
	function topagePagination(page,maxResult){
		$("#page").val(page);
		$("#maxResult").val(maxResult);
		$("#f1").attr("method","POST");
		$("#f1").attr("action","${ctx}/work/getWorkTimeList.do");
		$("#f1").submit();
	}	
	//导出数据
	function exportWorkTime(){
	
		$("#f1").attr("method","POST");
		$("#f1").attr("action","${ctx}/work/exportWorkTimeList.do");
    	$("#f1").submit();
	
	}
	//执行定时任务
	/* function executeTask(){
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
	} */
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
		$("#f1").attr("action","${ctx}/work/getWorkTimeList.do");
		$("#f1").submit(); 
	}
	
	function displayDeclare(value){
		if(value=='1'){
			$("#displayDeclare").html("<p>（1）员工工作量饱和度值=该员工所有任务工作量总和 / （该员工实际出勤天数× 8）*100%；</p><p>（2）员工实际出勤天数以科技公司考勤管理系统中数据为准，嘉盛职场和海淀黄庄职场的考勤数据尚未同步到考勤管理系统，因此获取到的出勤数据与实际出勤情况可能会有差异。</p>");
		}else if(value=='3'){
			$("#displayDeclare").html("<p>（1）部门工作量饱和度值=本月部门所有任务总工作量/(部门内所有员工出勤天数和*8)*100%；</p>");
		}
	}
</script>
</html>