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

<!-- colorbox -->
<link rel="stylesheet" type="text/css" href="${ctx}/components/colorbox/colorbox.css"/>
<script language="javascript" src="${ctx}/components/colorbox/jquery.colorbox.js"></script>

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
<c:if test="${resultFlag=='1' }">
	<tags:message content="${message}" type="success" />
</c:if>
<c:if test="${resultFlag=='0' }">
	<tags:message content="${message}" type="error" />
</c:if>
<form id ="f1" action="${ctx}/oa/getOAInfoList.do" method="POST">
<input type="hidden" id="page" name="page" value="1" />
<input type="hidden"  id="maxResult" name="maxResult" value="${pageView.maxresult}"/>
<div class="TAB_right">
<h1><b>位置：</b><a href="###">首页</a><span>&gt;</span><a href="###">OA数据导入</a></h1>
	<div class="retrieve_list">
			<table class="retrieve_table" width="100%" cellpadding="0" cellspacing="0">
					<tr>
						<td style="width:150px">&nbsp;&nbsp;
							类型：<select id="in_type" name="in_type"  style="width: 90px;height: 22px;" class="sel">
	                            <!-- <option value="">全部</option> -->
		                    	<option value="1" <c:if test="${in_type eq '1'}">selected</c:if> >请假</option>
		                    	<option value="2" <c:if test="${in_type eq '2'}">selected</c:if> >外出</option>
		                    	<option value="3" <c:if test="${in_type eq '3'}">selected</c:if> >出差</option>
                 			</select>
						</td>
						<td style="width:180px">&nbsp;&nbsp;申请人姓名：<input style="width:90px" type="text" id="userName" name="userName" value= "${userName}"></td>
						<td style="width:180px">&nbsp;&nbsp;员工编号：<input style="width:90px" type="text" id="emp_code" name="emp_code" value= "${emp_code}"></td>
						<td style="width:300px">&nbsp;&nbsp;申请日期：
							<input style="width: 90px;" readonly="readonly"  type="text" id="apply_time_start" name="apply_time_start" value= "${apply_time_start}"  onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:true});"/>
							至&nbsp;&nbsp;<input style="width: 90px;" readonly="readonly"  type="text" id="apply_time_end" name="apply_time_end" value="${apply_time_end}"  onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:true});"/>						
						
						<td align="right">&nbsp;&nbsp;
							<shiro:hasPermission name="user:queryOA">
							<input class="form_now marginr10" type="button" value="查询" onclick="selectSubmit()">&nbsp;&nbsp;&nbsp;&nbsp;
							</shiro:hasPermission>
							<!-- <input class="form_now marginr10" type="button" value="重置" onclick="resetForm()">&nbsp;&nbsp;&nbsp;&nbsp; -->
							<shiro:hasPermission name="user:importOA">
								<input class="form_now marginr10" type="button" value="导入数据" onclick="importExcel()">&nbsp;&nbsp;&nbsp;&nbsp;
							</shiro:hasPermission>
							<shiro:hasPermission name="user:executeOA">
								<input class="form_now marginr10" type="button" value="手动执行" onclick="executeOA()">&nbsp;&nbsp;&nbsp;&nbsp;
							</shiro:hasPermission>
						</td>
					</tr>
					
			</table>
	</div>
	<div class="infor_base">
	<div  class="btn_operate">
		</div>
		   <div class="container_12" style="overflow-y:auto;">
        	<div class="grid_8" >
        		<table class="fancyTable" id="myTable02" width="100%" cellpadding="0" cellspacing="0" style="table-layout:fixed;">
        		    <thead>
        		        <tr>
        		        	<c:choose>
        		        		<c:when test="${in_type eq '1' }">
        		        			<th style="width:35px;">序号</th>
		        		        	<th style="width:80px;"><a href="#" style="color:black">请假人</a></th>
		        		        	<th style="width:100px;"><a href="#" style="color:black">员工编号</a></th>
		        		            <th style="width:80px;"><a href="#"  style="color:black">经办人</a></th>
		        		            <th style="width:120px;"><a href="#"  style="color:black">所属分部</a></th>
		        		            <th style="width:130px;"><a href="#"  style="color:black">所属部门</a></th>
		        		            <th style="width:120px;"><a href="#"  style="color:black">所属岗位</a></th>
		        		            <th style="width:100px;" ><a href="#" style="color:black">申请日期</a></th>
		        		            <th style="width:80px;" ><a href="#" style="color:black">请假类型</a></th>
		        		            <th style="width:100px;" ><a href="#" style="color:black">入职日期</a></th>
		        		            <th style="width:100px;" ><a href="#" style="color:black">请假开始日期</a></th>
		        		            <th style="width:100px;" ><a href="#" style="color:black">请假结束日期</a></th>
		        		            <th style="width:100px;" ><a href="#" style="color:black">请假开始时间</a></th>
		        		            <th style="width:100px;" ><a href="#" style="color:black">请假结束时间</a></th>
		        		            <th style="width:60px;" ><a href="#" style="color:black">请假天数</a></th>
		        		            <th style="width:130px;" ><a href="#" style="color:black">导入时间</a></th>
        		        		</c:when>
        		        		<c:when test="${in_type eq '2' }">
        		        			<th style="width:35px;">序号</th>
		        		        	<th style="width:80px;"><a href="#" style="color:black">外出人</a></th>
		        		        	<th style="width:100px;"><a href="#" style="color:black">员工编号</a></th>
		        		            <th style="width:130px;"><a href="#"  style="color:black">所属分部</a></th>
		        		            <th style="width:120px;"><a href="#"  style="color:black">所属部门</a></th>
		        		            <th style="width:100px;" ><a href="#" style="color:black">申请日期</a></th>
		        		        	<th style="width:100px;"><a href="#" style="color:black">外出开始日期</a></th>
		        		        	<th style="width:100px;"><a href="#" style="color:black">外出结束日期</a></th>
		        		        	<th style="width:100px;"><a href="#" style="color:black">外出开始时间</a></th>
		        		        	<th style="width:100px;"><a href="#" style="color:black">外出结束时间</a></th>
		        		        	<th style="width:70px;"><a href="#" style="color:black">上午未打卡</a></th>
		        		        	<th style="width:70px;"><a href="#" style="color:black">下午未打卡</a></th>
		        		        	<th style="width:130px;"><a href="#" style="color:black">导入时间</a></th>
        		        		</c:when>
        		        		<c:when test="${in_type eq '3' }">
        		        			<th style="width:35px;">序号</th>
		        		        	<th style="width:80px;"><a href="#" style="color:black">出差人</a></th>
		        		        	<th style="width:100px;"><a href="#" style="color:black">员工编号</a></th>
		        		            <th style="width:120px;"><a href="#"  style="color:black">所属分部</a></th>
		        		            <th style="width:130px;"><a href="#"  style="color:black">所属部门</a></th>
		        		            <th style="width:100px;" ><a href="#" style="color:black">申请日期</a></th>
		        		            <th style="width:100px;"><a href="#"  style="color:black">出发地点</a></th>
		        		            <th style="width:100px;" ><a href="#" style="color:black">出差地点</a></th>
		        		            <th style="width:100px;"><a href="#"  style="color:black">出发日期</a></th>
		        		            <th style="width:100px;" ><a href="#" style="color:black">出发时间</a></th>
		        		            <th style="width:100px;"><a href="#"  style="color:black">返回日期</a></th>
		        		            <th style="width:100px;" ><a href="#" style="color:black">返回时间</a></th>
		        		            <th style="width:60px;"><a href="#"  style="color:black">预计天数</a></th>
		        		            <th style="width:70px;"><a href="#"  style="color:black">是否有同伴</a></th>
		        		            <th style="width:100px;" ><a href="#" style="color:black">同行人</a></th>
		        		            <th style="width:130px;" ><a href="#" style="color:black">导入时间</a></th>
        		        		</c:when>
        		        		<c:otherwise></c:otherwise>
        		        	</c:choose>
        		        </tr>
        		    </thead>
        		    <tbody id="bodyTR">
        		    <c:forEach items="${pageView.records}" var="oAInfo" varStatus="status">
        		        <tr>
        		        	<c:choose>
        		        		<c:when test="${in_type eq '1' }">
        		        			<td>${status.index + 1}</td>
		                            <td  style="word-wrap:break-word;text-align: center;" >${oAInfo.leave_person }</td>
		                            <td  style="word-wrap:break-word;text-align: center;" >${oAInfo.emp_code }</td>
		                            <td  style="word-wrap:break-word;text-align: center;" >${oAInfo.operator }</td>
		                            <td  style="word-wrap:break-word;text-align: center;" >${oAInfo.company }</td>
		                            <td  style="word-wrap:break-word;text-align: center;" >${oAInfo.department }</td>
		                            <td  style="word-wrap:break-word;text-align: center;" >${oAInfo.post }</td>
		                            <td  style="word-wrap:break-word;text-align: center;" >${oAInfo.apply_time }</td>
		                            <td  style="word-wrap:break-word;text-align: center;" >${oAInfo.type }</td>
		                            <td  style="word-wrap:break-word;text-align: center;" >${oAInfo.taking_work_time }</td>
		                            <td  style="word-wrap:break-word;text-align: center;" >${oAInfo.start_date }</td>
		                            <td  style="word-wrap:break-word;text-align: center;" >${oAInfo.end_date }</td>
		                            <td  style="word-wrap:break-word;text-align: center;" >${oAInfo.start_time }</td>
		                            <td  style="word-wrap:break-word;text-align: center;" >${oAInfo.end_time }</td>
		                            <td  style="word-wrap:break-word;text-align: center;" >${oAInfo.days }</td>
		                            <td  style="word-wrap:break-word;text-align: center;" >${oAInfo.add_time }</td>
        		        		</c:when>
        		        		<c:when test="${in_type eq '2' }">
        		        			<td>${status.index + 1}</td>
		                            <td  style="word-wrap:break-word;text-align: center;" >${oAInfo.out_person }</td>
		                            <td  style="word-wrap:break-word;text-align: center;" >${oAInfo.emp_code }</td>
		                            <td  style="word-wrap:break-word;text-align: center;" >${oAInfo.company }</td>
		                            <td  style="word-wrap:break-word;text-align: center;" >${oAInfo.department }</td>
		                            <td  style="word-wrap:break-word;text-align: center;" >${oAInfo.apply_time }</td>
		                            <td  style="word-wrap:break-word;text-align: center;" >${oAInfo.start_date }</td>
		                            <td  style="word-wrap:break-word;text-align: center;" >${oAInfo.end_date }</td>
		                            <td  style="word-wrap:break-word;text-align: center;" >${oAInfo.start_time }</td>
		                            <td  style="word-wrap:break-word;text-align: center;" >${oAInfo.end_time }</td>
		                            <td  style="word-wrap:break-word;text-align: center;" >${oAInfo.un_punch_moring }</td>
		                            <td  style="word-wrap:break-word;text-align: center;" >${oAInfo.un_punch_afternoon }</td>
		                            <td  style="word-wrap:break-word;text-align: center;" >${oAInfo.add_time }</td>
        		        		</c:when>
        		        		<c:when test="${in_type eq '3' }">
        		        			<td>${status.index + 1}</td>
		                            <td  style="word-wrap:break-word;text-align: center;" >${oAInfo.on_business_person }</td>
		                            <td  style="word-wrap:break-word;text-align: center;" >${oAInfo.emp_code }</td>
		                            <td  style="word-wrap:break-word;text-align: center;" >${oAInfo.company }</td>
		                            <td  style="word-wrap:break-word;text-align: center;" >${oAInfo.department }</td>
		                            <td  style="word-wrap:break-word;text-align: center;" >${oAInfo.apply_time }</td>
		                            <td  style="word-wrap:break-word;text-align: center;" >${oAInfo.start_site }</td>
		                            <td  style="word-wrap:break-word;text-align: center;" >${oAInfo.on_business_site }</td>
		                            <td  style="word-wrap:break-word;text-align: center;" >${oAInfo.start_date }</td>
		                            <td  style="word-wrap:break-word;text-align: center;" >${oAInfo.start_time }</td>
		                            <td  style="word-wrap:break-word;text-align: center;" >${oAInfo.back_date }</td>
		                            <td  style="word-wrap:break-word;text-align: center;" >${oAInfo.back_time }</td>
		                            <td  style="word-wrap:break-word;text-align: center;" >${oAInfo.pre_days }</td>
		                            <td  style="word-wrap:break-word;text-align: center;" >${oAInfo.is_companion }</td>
		                            <td  style="word-wrap:break-word;text-align: center;" >${oAInfo.companion }</td>
		                            <td  style="word-wrap:break-word;text-align: center;" >${oAInfo.add_time }</td>
        		        		</c:when>
        		        		<c:otherwise></c:otherwise>
        		        	</c:choose>
        		        </tr>
        		        </c:forEach>
        		    </tbody>
        		</table>
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
		var DATE_DELIMITER="-";       //日期分隔符
		
  		var strValue1=$("#apply_time_start").val() .split(DATE_DELIMITER);
        var date1Temp=new Date(strValue1[0],parseInt(strValue1[1],10)-1,parseInt(strValue1[2],10));
        var strValue2=$("#apply_time_end").val() .split(DATE_DELIMITER);
        var date2Temp=new Date(strValue2[0],parseInt(strValue2[1],10)-1,parseInt(strValue2[2],10));
        if(date1Temp.getTime()>date2Temp.getTime()){
        	alert("选择开始时间不能大于结束时间！");
        	return false;
        }	
        		
		$("#f1").attr("method","POST");
		$("#f1").attr("action","${ctx}/oa/getOAInfoList.do");
		$("#f1").submit();
	}	
	
	//到指定的分页页面
	function topage(page){
		$("#page").val(page);
		$("#listForm").attr("method","POST");
		$("#listForm").attr("action","${ctx}/oa/getOAInfoList.do");
		$("#listForm").submit();
	}	
	//到指定的分页页面
	function topagePagination(page,maxResult){
		$("#page").val(page);
		$("#maxResult").val(maxResult);
		$("#f1").attr("method","POST");
		$("#f1").attr("action","${ctx}/oa/getOAInfoList.do");
		$("#f1").submit();
	}	

	function importExcel(){
		var url= "${ctx}/oa/upfile.do";
	    $.colorbox({
	        href:url,
	        iframe:true,
	        width:"600",
	        height:"400"
	    }); 
	}
	
	function exportOAInfo(){
		$("#f1").attr("method","POST");
		$("#f1").attr("action","${ctx}/oa/exportOAInfo.do");
    	$("#f1").submit();
	}
	
	function executeOA(){
		var isExecute = confirm("确认要执行吗？");
    	if(isExecute){
			$.ajax({
				type : "POST",  
				url:"${ctx}/executeTaskOAByDay.do", 
				//data : "attenceDate="+attenceDate,
				dataType : 'json',
				success: function(data){
					alert("执行成功！");
					
				}
			});
		}
	}
</script>
</html>