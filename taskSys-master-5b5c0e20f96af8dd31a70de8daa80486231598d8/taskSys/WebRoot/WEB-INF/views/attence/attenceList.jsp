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
<form id ="f1" action="${ctx}/attence/getAttenceList.do" method="POST">
<input type="hidden" id="page" name="page" value="1" />
<input type="hidden"  id="maxResult" name="maxResult" value="${pageView.maxresult}"/>
<input type="hidden" id="orgId" name="orgId"  value="${perforInfoReport.orgId}">
<input type="hidden" id="selectFlag" name="selectFlag" >
<div class="TAB_right">
<h1><b>位置：</b><a href="###">首页</a><span>&gt;</span><a href="###">考勤导入</a></h1>
	<div class="retrieve_list">
			<table class="retrieve_table" width="100%" cellpadding="0" cellspacing="0">
					<tr>
						<td style="width:150px">&nbsp;&nbsp; 姓名：<input style="width:90px" type="text" id="username" name="username" value= "${attence.username}"></td>
						<td style="width:180px">&nbsp;&nbsp; 员工编号：<input style="width:90px" type="text" id="empCode" name="empCode" value= "${attence.empCode}"></td>
						<td align="left" style="width:160px"> 状态：
							<select name="state" id="state" class="sel" style="width:90px">
                                <option value="">请选择</option>
                               	<c:forEach items="${dictionaryLlist}" var="dic" varStatus="status">
                               		<c:if test="${dic.type_code=='08'}">
			                    		<option value="${dic.code}" <c:if test="${dic.code==attence.state}">selected</c:if>>${dic.name }</option>
			                    	</c:if>
			                    </c:forEach>
                         	</select>
						</td>
						<td align="left" style="width:225px">所属部门：
							<select name="department" id="department" class="sel" style="width:150px">
								<c:choose>
									<c:when test="${userRole=='BMJL'}">
	                              		<c:forEach items="${dictionaryLlist}" var="dic" varStatus="status">
					                    	<c:if test="${dic.type_code=='01' && dic.code==attence.department }">
					                    		<option value="${dic.code}" selected>${dic.name }</option>
					                    	</c:if>
					                    </c:forEach>
	                              	</c:when>
                              		<c:otherwise>
		                                <option value="">请选择</option>
					                   	<c:forEach items="${dictionaryLlist}" var="dic" varStatus="status">
						                   	<c:if test="${dic.type_code=='01'}">
						                    	<option value="${dic.code}" <c:if test="${dic.code==attence.department}">selected</c:if>>${dic.name }</option>
						                    </c:if>
					                    </c:forEach>
                              		</c:otherwise>
                              	</c:choose>
                         	  </select>
						</td>
						<td width="180px">&nbsp;&nbsp; 考勤月份：<input style="width: 90px;" readonly="readonly"  type="text" id="months" name="months" value= "${attence.months}"  onclick="WdatePicker({dateFmt:'yyyy-MM',isShowClear:true});"/></td>
						<td align="right">&nbsp;&nbsp;
							<shiro:hasPermission name="user_attence:queryAttence">
								<input class="form_now marginr10" type="button" value="查询" onclick="selectSubmit()">&nbsp;&nbsp;&nbsp;&nbsp;
							</shiro:hasPermission>
							<%-- <shiro:hasPermission name="user_attence:exportAttence">
								<input class="form_now marginr10" type="button" value="导出" onclick="queryAttenceDetails()">&nbsp;&nbsp;&nbsp;&nbsp;
							</shiro:hasPermission> --%>
						</td>
					</tr>
					<tr><td colspan="6">&nbsp;&nbsp;
						<shiro:hasPermission name="user_attence:executeAttence">
							 考勤时间：<input style="width: 90px;" readonly="readonly"  type="text" id="attenceDate" name="attenceDate" value= "${attenceDate}"  onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:true,maxDate:'%y-%M-%d'});"/>
								&nbsp;&nbsp;<input class="form_now marginr10 but1" type="button" value="导入考勤" onclick="executeAttence()">&nbsp;&nbsp;&nbsp;&nbsp;
						</shiro:hasPermission>
						<shiro:hasPermission name="user_attence:executePersonAttence">
							<%-- 考勤时间：<input style="width: 90px;" readonly="readonly"  type="text" id="attenceDate" name="attenceDate" value= "${attenceDate}"  onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:true,maxDate:'%y-%M-%d'});"/> --%>
								&nbsp;&nbsp;<input class="form_now marginr10 but2" type="button" value="个人考勤" onclick="preAttence()"/>&nbsp;&nbsp;&nbsp;&nbsp;
						</shiro:hasPermission>
						<shiro:hasPermission name="user_attence:updatePersonAttence">
							&nbsp;&nbsp;<input class="form_now marginr10 but3" type="button" value="个人备注" onclick="preRemarkAttence()"/>&nbsp;&nbsp;&nbsp;&nbsp;
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
        		<input type="hidden" name="orderByPro" id="orderByPro" value="${workTime.orderByPro }">
        		<input type="hidden" name="orderByFlag" id="orderByFlag" value="${workTime.orderByFlag }">
        		<table class="fancyTable" id="myTable02" cellpadding="0" cellspacing="0" style="table-layout:fixed;">
        		    <thead>
        		        <tr>
        		        	<th style="width:35px;">序号</th>
        		        	<th style="width:100px;"><a href="#" style="color:black">姓名</a></th>
        		        	<th style="width:100px;"><a href="#" style="color:black">员工编号</a></th>
        		            <th style="width:120px;"><a href="#"  style="color:black">所属部门</a></th>
        		            <th style="width:80px;"><a href="#"  style="color:black">考勤月份</a></th>
        		            <th style="width:120px;"><a href="#"  style="color:black">导入时间</a></th>
        		            <th style="width:80px;"><a href="#"  style="color:black">状态</a></th>
        		            <th style="width:80px;" ><a href="#" style="color:black">操作</a></th>
        		        </tr>
        		    </thead>
        		    <tbody id="bodyTR">
        		    <c:forEach items="${pageView.records}" var="attence" varStatus="status">
        		        <tr>
        		        	<td>${status.index + 1}</td>
                            <td  style="word-wrap:break-word;text-align: center;" >${attence.username }</td>
                            <td  style="word-wrap:break-word;text-align: center;" >${attence.empCode }</td>
                            <td  style="word-wrap:break-word;text-align: center;" >${attence.departmentName }</td>
                            <td  style="word-wrap:break-word;text-align: center;" >${attence.months }</td>
                            <td  style="word-wrap:break-word;text-align: center;" >${attence.importTime }</td>
                            <td  style="word-wrap:break-word;text-align: center;" ><font <c:if test="${attence.state=='0' }">color="red"</c:if>>${attence.stateName }</font></td>
                            <td  style="word-wrap:break-word;text-align: center;" ><a href="#" onclick="findAttenceDetails('${attence.empCode }','${attence.months }')">查看</a></td>
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

	<!-- 详情弹出框start -->
<link rel="stylesheet" type="text/css" href="${ctx}/components/colorbox/colorbox.css"/>
<script language="javascript" src="${ctx}/components/colorbox/jquery.colorbox.js"></script>
<script>
	
	function findAttenceDetails(empCode, months){
		var url = "${ctx}/attence/findAttenceDetails.do?empCode="+empCode+"&months="+months;
		$.colorbox({
			href:url,
			iframe:true,
			width:"1000",
			height:"500"
		});
	}
	
</script>
<!-- 详情弹出框end -->
<script type="text/javascript">
	$(document).ready(function(){
		$(".fht-tbody").css("height", "85%");
		$(".grid_8").css("height","75%");
	})
	function selectSubmit(){
		$("#f1").attr("method","POST");
		$("#f1").attr("action","${ctx}/attence/getAttenceList.do");
		$("#f1").submit();
	}
	
	//到指定的分页页面
	function topage(page){
		$("#page").val(page);
		$("#listForm").attr("method","POST");
		$("#listForm").attr("action","${ctx}/attence/getAttenceList.do");
		$("#listForm").submit();
	}	
	//到指定的分页页面
	function topagePagination(page,maxResult){
		$("#page").val(page);
		$("#maxResult").val(maxResult);
		$("#f1").attr("method","POST");
		$("#f1").attr("action","${ctx}/attence/getAttenceList.do");
		$("#f1").submit();
	}	

	//手动执行考勤导入定时任务
	function executeAttence(){
		var attenceDate = $("#attenceDate").val();
		if(attenceDate==null || attenceDate==''){
			alert("请选择时间！");
			return;
		}
		$.ajax({
			type : "POST",  
			url:"${ctx}/attence/executeAttenceByDayTemp.do", 
			data : "attenceDate="+attenceDate,
			dataType : 'json',
			success: function(data){
				if(data.result=='ok'){
					alert("导入考勤成功！");
				}else{
					alert("导入考勤失败！");
				}
			}
		});
	}
	
	//手动执行个人考勤
	function preAttence(){
		var attenceDate = $("#attenceDate").val();
		$.ajax({
			type : "POST",  
			url: "${ctx}/attence/savePerAttence.do", 
			data : "attenceDate="+attenceDate,
			dataType : 'json',
			success: function(data){
				if(data.result=='ok'){
					alert("执行成功！");
				}else{
					alert("执行失败！");
				}
			}
		});
	}
	
	
		//手动备注个人考勤
	function preRemarkAttence(){
		var attenceDate = $("#attenceDate").val();
		$.ajax({
			type : "POST",  
			url: "${ctx}/attence/modifyPerAttence.do", 
			data : "attenceDate="+attenceDate,
			dataType : 'json',
			success: function(data){
				if(data.result=='ok'){
					alert("执行成功！");
				}else{
					alert("执行失败！");
				}
			}
		});
	}
	
	
</script>
</html>