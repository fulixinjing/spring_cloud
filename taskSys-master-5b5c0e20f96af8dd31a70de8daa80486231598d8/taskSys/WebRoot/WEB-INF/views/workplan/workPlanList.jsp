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
<link rel="stylesheet" type="text/css" href="${ctx}/css/index01.css" />
<link href="${ctx}/css/static/base-global.css" rel="stylesheet" type="text/css" />
<link href="${ctx}/css/static/main.css?1=1" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="${ctx}/js/jquery.min.js"></script>
<script type="text/javascript" src="${ctx}/js/alert/g_alert.js?1=1"></script>

<link href="${ctx}/css/defaultTheme1.css" rel="stylesheet" media="screen" />
<link href="${ctx}/css/myTheme1.css" rel="stylesheet" media="screen" />
<script type="text/javascript" src="${ctx}/js/My97DatePicker/WdatePicker.js" ></script>
<script src="${ctx}/js/jquery.fixedheadertable.js"></script>
<script src="${ctx}/js/demo.js"></script>
<script type="text/javascript" src="${ctx}/js/right_js.js"></script>
<script type="text/javascript" src="${ctx}/js/util/common.js"></script>
<%-- <script type="text/javascript" src="${ctx}/js/layer/skin/layer.css"></script> 
<script type="text/javascript" src="${ctx}/js/layer/skin/layer.ext.css"></script> --%>
<script type="text/javascript" src="${ctx}/js/layer/layer.min.js"></script>
<script type="text/javascript" src="${ctx}/js/layer/extend/layer.ext.js"></script>

<script type="text/javascript" src="${ctx}/js/sysUtil.js"></script>

</head>
<style>
	html,body{
		height:100%;
		overflow:hidden;
	}
	#bodyTR  td{
		text-align:center;
	}
	
	.table00 td{
		line-height: 20px;
	}
	.table00 tr{
		height: 55px;
	}
</style>
<body>
<c:if test="${resultFlag==1 }">
	<tags:message content="${message}" type="success" />
</c:if>
<c:if test="${resultFlag==0 }">
	<tags:message content="${message}" type="error" />
</c:if>
<form id ="f1" action="${ctx}/work_plan/getWorkPlanList.do" method="POST">
<input type="hidden" id="page" name="page" value="1" />
<input type="hidden"  id="maxResult" name="maxResult" value="${pageView.maxresult}"/>
<input type="hidden" id="orgId" name="orgId"  value="${perforInfoReport.orgId}">
<input type="hidden" id="selectFlag" name="selectFlag" >
<div class="TAB_right">
<h1><b>位置：</b><a href="###">首页</a><span>&gt;</span><a href="###">工作月报</a></h1>
	<div class="retrieve_list">
			<table class="retrieve_table" width="100%" cellpadding="0" cellspacing="0">
					<tr>
						<td align="left" width="240px">&nbsp;&nbsp;所属部门：
							<select name="department" id="department" class="sel" style="width:150px">
                            	<option value="">请选择</option>
			                    <c:forEach items="${dictionaryLlist}" var="dic" varStatus="status">
			                    	<c:if test="${dic.type_code=='01'}">
			                    		<option value="${dic.code}" <c:if test="${dic.code==workPlan.department}">selected</c:if>>${dic.name }</option>
			                   		</c:if>
			                    </c:forEach>
                         	  </select>
						</td>
						<td width="180px">&nbsp;&nbsp; 项目名称：<input style="width: 100px;"  type="text" id="projectName" name="projectName" value= "${workPlan.projectName}"/></td>
						<td align="left" width="240px">&nbsp;&nbsp;类型：
							<select name="type" id="type" class="sel" style="width:150px">
                            	<option value="">请选择</option>
                            	<c:forEach items="${dictionaryLlist}" var="dic" varStatus="status">
	                            	<c:if test="${dic.type_code=='17'}">
			                    		<option value="${dic.code}" <c:if test="${dic.code==workPlan.type}">selected</c:if>>${dic.name }</option>
			                   		</c:if>
		                   		</c:forEach>
                         	</select>
						</td>
						<td style="width: 180px;">负责人:&nbsp;&nbsp;<input style="width: 100px;"  type="text" id="principalName" name="principalName" value= "${workPlan.principalName}"/></td>
						<td>
							月份：
							<input style="width: 90px;" readonly="readonly"  type="text" id="month" name="month" value= "${workPlan.month}"  onclick="WdatePicker({dateFmt:'yyyy-MM',isShowClear:true});"/>
						</td>
					</tr>
					<tr>
						<td align="left" colspan="5">&nbsp;&nbsp;
							<%-- <shiro:hasPermission name="user_work:queryWorkPlan"> --%>
							<input class="form_now marginr10" type="button" value="查询" onclick="selectSubmit()">&nbsp;&nbsp;&nbsp;&nbsp;
							<%-- </shiro:hasPermission>
							<shiro:hasPermission name="user_work:exportWorkPlan"> --%>
							<input class="form_now marginr10" type="button" value="导出" onclick="exportWorkPlan()">&nbsp;&nbsp;&nbsp;&nbsp;
							<%-- </shiro:hasPermission> --%>
							<input class="form_now marginr10" style=" width: 90px;background: url(../images/btn.png) top; " type="button" value="新增工作任务" onclick="addwp()">&nbsp;&nbsp;&nbsp;&nbsp;
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
        		<table class="fancyTable" id="myTable02" cellpadding="0" cellspacing="0" style="table-layout:fixed;">
        		    <thead>
        		        <tr>
        		        	<th style="width:35px;text-align: center;">序 号</th>
        		        	<th style="width:120px;text-align: center;"><a href="#" style="color:black">类 型</a></th>
        		        	<th style="width:130px;text-align: center;"><a href="#" style="color:black">部 门</a></th>
        		        	<th style="width:105px;text-align: center;"><a href="#" style="color:black">项目编号</a></th>
        		        	<th style="width:150px;text-align: center;"><a href="#" style="color:black">项目名称</a></th>
        		            <!-- <th style="width:120px;text-align: center;"><a href="#"  style="color:black">本月部门工作计划/目标</a></th> -->
        		            <th style="width:90px;text-align: center;"><a href="#"  style="color:black">重要程度</a></th>
        		            <th style="width:70px;text-align: center;"><a href="#"  style="color:black">负责人</a></th>
        		            <th style="width:100px;text-align: center;"><a href="#"  style="color:black">外部确认人</a></th>
        		            <th style="width:80px;text-align: center;"><a href="#"  style="color:black">月 份</a></th>
        		            <th style="width:105px;text-align: center;">操 作</th>
        		        </tr>
        		    </thead>
        		    <tbody id="bodyTR">
        		    <c:forEach items="${pageView.records}" var="work" varStatus="status">
        		        <tr>
        		        	<td>${status.index + 1}</td>
                            <td  style="word-wrap:break-word;text-align: center;" >${work.typeName }</td>
                            <td  style="word-wrap:break-word;text-align: center;" >${work.departmentName }</td>
                            <td  style="word-wrap:break-word;text-align: center;" >${work.projectCode }</td>
                            <td  style="word-wrap:break-word;text-align: center;" >${work.projectName }</td>
                            <%-- <td  style="word-wrap:break-word;text-align: center;" >${work.departmentPlan }</td> --%>
                           	<c:choose>
                           		<c:when test="${work.importance==1 }"><td  style="word-wrap:break-word;text-align: center;background-color: red;" >${work.importanceName}</td></c:when>
                           		<c:when test="${work.importance==2 }"><td  style="word-wrap:break-word;text-align: center;background-color:#F4B084;">${work.importanceName}</td></c:when>
                           		<c:when test="${work.importance==3 }"><td  style="word-wrap:break-word;text-align: center;background-color: #B4C6E7;">${work.importanceName}</td></c:when>
                           		<c:when test="${work.importance==4 }"><td  style="word-wrap:break-word;text-align: center;background-color: #92D050;">${work.importanceName}</td></c:when>
                           		<c:otherwise><td></td></c:otherwise>
                           	</c:choose>
                            <td  style="word-wrap:break-word;text-align: center;" >${work.principalName }</td>
                            <td  style="word-wrap:break-word;text-align: center;" >${work.verifier }</td>
                            <td  style="word-wrap:break-word;text-align: center;" >${work.month }</td>
                            <td  style="word-wrap:break-word;text-align: center;" >
                                <a href="#" id="showContent" onclick="showContent1('${work.id}')" style="color:blue;">查看任务</a>&nbsp;&nbsp;
                                <shiro:hasPermission name="work_plan:update">
                                <a href="#" onclick="goUpdate('${work.id}')" style="color:blue;">修改</a>
                                </shiro:hasPermission>
                                <shiro:hasPermission name="work_plan:delete">
                                <a href="#" onclick="deletePlan('${work.id}')" style="color:blue;">删除</a>
                                </shiro:hasPermission>
                            </td>
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
<div id="covered"></div>
<div class="popup score-box" style="margin-left:-434px;top: 0%;">
    	<div class="tc-box" style="width:800px;height: 480px">
    		<div class="title fn-clear">
         			<span>月报详情</span>
         			<a href="javascript:(void);" class="tc-close fr"></a>
         	</div>
         	<div id="showCon">
         		<div style="height: 420px;overflow: auto;">
         			<div class="list">
						<table width="100%" cellpadding="0" cellspacing="0" border="0" class="table00">
							<thead>
								<tr>
									<th style="width: 80px;"></th>
									<th style="width: 150px;">工作标题</th>
									<th style="width: 240px;">工作内容</th>
									<th style="width: 120px;">团队成员</th>
									<th>完成情况</th>
								</tr>
							</thead>
							<tbody>
								<tr>
									<td style="color: #1585b1;font-weight: bold;">第一周</td>
									<td id="title1"></td>
									<td id="contentTd1"></td>
									<td id="teamMemberTd1"></td>
									<td id="completeStatusTd1"></td>
								</tr>
								<tr>
									<td style="color: #1585b1;font-weight: bold;">第二周</td>
									<td id="title2"></td>
									<td id="contentTd2"></td>
									<td id="teamMemberTd2"></td>
									<td id="completeStatusTd2"></td>
								</tr>
								<tr>
									<td style="color: #1585b1;font-weight: bold;">第三周</td>
									<td id="title3"></td>
									<td id="contentTd3"></td>
									<td id="teamMemberTd3"></td>
									<td id="completeStatusTd3"></td>
								</tr>
								<tr>
									<td style="color: #1585b1;font-weight: bold;">第四周</td>
									<td id="title4"></td>
									<td id="contentTd4"></td>
									<td id="teamMemberTd4"></td>
									<td id="completeStatusTd4"></td>
								</tr>
								<tr>
									<td style="color: #1585b1;font-weight: bold;">第五周</td>
									<td id="title5"></td>
									<td id="contentTd5"></td>
									<td id="teamMemberTd5"></td>
									<td id="completeStatusTd5"></td>
								</tr>
							</tbody>
						</table>
					</div>
					<div class="wen">
						<div class="w_yi" style="width: 700px;float: left;">
							<label class="label01 fl">工作计划／目标：（本月部门）</label>
							<span id="departmentPlan"></span>
						</div>
						<div class="w_yi" style="width: 700px;float: left;">
							<label class="label01 fl">遇到风险：</label>
							<span id="risk"></span>
						</div>
						<div class="w_yi" style="width: 700px;float: left;">
							<label class="label01 fl">采取措施：</label>
							<span id="measures"></span>
						</div>
					</div>
					
					<!-- <table class="showTable" style="border: 1px solid;width: 100%;height: 88%;">
						<tr>
							<td rowspan="3" class="firstTd" style="border-bottom-color: #b3abab;">第一周</td>
							<td  class="titleTd" style="width: 150px;">内&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;容:</td>
							<td id="contentTd1"></td>
						</tr>
						<tr>
							<td class="titleTd">成&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;员:</td>
							<td id="teamMemberTd1"></td>
						</tr>
						<tr>
							<td class="titleTd" style="border-bottom-color: #b3abab;">完成情况:</td>
							<td id="completeStatusTd1" style="border-bottom-color: #b3abab;"></td>
						</tr>
						<tr>
							<td rowspan="3" class="firstTd" style="border-bottom-color: #b3abab;">第二周</td>
							<td class="titleTd">内&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;容:</td>
							<td id="contentTd2"></td>
						</tr>
						<tr>
							<td class="titleTd">成&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;员:</td>
							<td id="teamMemberTd2"></td>
						</tr>
						<tr>
							<td class="titleTd" style="border-bottom-color: #b3abab;">完成情况:</td>
							<td id="completeStatusTd3" style="border-bottom-color: #b3abab;"></td>
						</tr>
						<tr>
							<td rowspan="3" class="firstTd" style="border-bottom-color: #b3abab;">第三周</td>
							<td class="titleTd">内&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;容:</td>
							<td id="contentTd3"></td>
						</tr>
						<tr>
							<td class="titleTd">成&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;员:</td>
							<td id="teamMemberTd3"></td>
						</tr>
						<tr>
							<td class="titleTd" style="border-bottom-color: #b3abab;">完成情况:</td>
							<td id="completeStatusTd3" style="border-bottom-color: #b3abab;"></td>
						</tr>
						<tr>
							<td rowspan="3" class="firstTd" style="border-bottom-color: #b3abab;">第四周</td>
							<td class="titleTd">内&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;容:</td>
							<td id="contentTd4"></td>
						</tr>
						<tr>
							<td class="titleTd">成&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;员:</td>
							<td id="teamMemberTd4"></td>
						</tr>
						<tr>
							<td class="titleTd" style="border-bottom-color: #b3abab;">完成情况:</td>
							<td id="completeStatusTd4" style="border-bottom-color: #b3abab;"></td>
						</tr>
						<tr>
							<td rowspan="3" class="firstTd" style="border-bottom-color: #b3abab;">第五周</td>
							<td class="titleTd">内&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;容:</td>
							<td id="contentTd5"></td>
						</tr>
						<tr>
							<td class="titleTd">成&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;员:</td>
							<td id="teamMemberTd5"></td>
						</tr>
						<tr>
							<td class="titleTd" style="border-bottom-color: #b3abab;">完成情况:</td>
							<td id="completeStatusTd5" style="border-bottom-color: #b3abab;"></td>
						</tr>
					</table> -->
				</div>
				<!-- <div class="div_btn" style="border-top: 1px solid #dcdcdc;">
					<a href="javascript:(void);" >关&nbsp;&nbsp;闭</a>
				</div> -->
				<div class="txt-c pd-t15" style="border-top: 1px solid #dcdcdc;">
	   				<a href="javascript:(void);" class="tc-btn2 mg-l15">关&nbsp;&nbsp;闭</a>
	    		</div>
			</div>
    	</div>
    </div>

</body>

<script type="text/javascript">
	$(document).ready(function(){
		$(".fht-tbody").css("height", "85%");
		$(".grid_8").css("height","65%");
	})
	function selectSubmit(){
							
		$("#f1").attr("method","POST");
		$("#f1").attr("action","${ctx}/work_plan/getWorkPlanList.do");
		$("#f1").submit();
	}	
	function addwp(){
        $("#f1").attr("method","POST");
        $("#f1").attr("action","${ctx}/work_plan/addWorkPlan.do");
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
		$("#listForm").attr("action","${ctx}/work_plan/getWorkPlanList.do");
		$("#listForm").submit();
	}	
	//到指定的分页页面
	function topagePagination(page,maxResult){
		$("#page").val(page);
		$("#maxResult").val(maxResult);
		$("#f1").attr("method","POST");
		$("#f1").attr("action","${ctx}/work_plan/getWorkPlanList.do");
		$("#f1").submit();
	}	
	//导出数据
	function exportWorkPlan(){
		var department = $("#department").val();
		if(department==null || department==''){
			alert("请选择部门");
			return;
		}
		$("#f1").attr("method","POST");
		$("#f1").attr("action","${ctx}/work_plan/exportWorkPlanList.do");
    	$("#f1").submit();
	}
	
	/*input 属性readonly="readonly"时，禁用backspace事件*/
    function disableBackspace(){
    	var isReadOnly = $("#exec_name").prop("readonly");
	    if (isReadOnly && event.keyCode == 8) {
			event.preventDefault();
		}
    }
   
   function showContent1(id) {
   		url = "${ctx}/work_plan/findWorkPlanById.do";
		$.ajax({ 
			type : "POST",  
			url: url, 
			data : "id="+id,
			dataType : 'json',
			success: function(data){
				if(data!=null){
					$("#title1").html(data.map.title1);
					$("#contentTd1").html(data.map.content1);
					$("#teamMemberTd1").html(data.map.teamMember1);
					$("#completeStatusTd1").html(data.map.completeStatus1);
					
					$("#title2").html(data.map.title2);
					$("#contentTd2").html(data.map.content2);
					$("#teamMemberTd2").html(data.map.teamMember2);
					$("#completeStatusTd2").html(data.map.completeStatus2);
					
					$("#title3").html(data.map.title3);
					$("#contentTd3").html(data.map.content3);
					$("#teamMemberTd3").html(data.map.teamMember3);
					$("#completeStatusTd3").html(data.map.completeStatus3);
					
					$("#title4").html(data.map.title4);
					$("#contentTd4").html(data.map.content4);
					$("#teamMemberTd4").html(data.map.teamMember4);
					$("#completeStatusTd4").html(data.map.completeStatus4);
					
					$("#title5").html(data.map.title5);
					$("#contentTd5").html(data.map.content5);
					$("#teamMemberTd5").html(data.map.teamMember5);
					$("#completeStatusTd5").html(data.map.completeStatus5);
					
					$("#departmentPlan").html(data.map.departmentPlan);
					$("#risk").html(data.map.risk);
					$("#measures").html(data.map.measures);
					
					$('#covered').show();
					$(".score-box").show();
				}else{
				}
			}
      		
      	});
   }
   function goUpdate(id){
	   $("#f1").attr("method","POST");
       $("#f1").attr("action","${ctx}/work_plan/goUpdateWorkPlan.do?id="+id);
       $("#f1").submit();
   }
   $('.tc-close').click(function(){
     $('.score-box').hide();
     $('#covered').hide();
   });
   $('.tc-btn2').click(function(){
     $('.score-box').hide();
     $('#covered').hide();
   });
   
   function deletePlan(id){
   		if(id!=null && id!=''){
   			var isConfirm = confirm("确认删除该条记录！");
   			if(isConfirm){
   				$("#f1").attr("method","POST");
		    	$("#f1").attr("action","${ctx}/work_plan/deleteWorkPlan.do?id="+id);
		    	$("#f1").submit();
   			}
   		}
   }
</script>
</html>