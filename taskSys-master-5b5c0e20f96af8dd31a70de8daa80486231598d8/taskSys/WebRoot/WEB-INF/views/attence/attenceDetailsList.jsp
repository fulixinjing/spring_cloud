<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/common/taglibs.jsp" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.0 Transitional//EN"> 
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<%@ include file="/common/meta.jsp" %>
<title>考勤明细列表</title>
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

<style type="text/css">
	fieldset {
		padding:10px;
		margin:10px;
		
		color:#333; 
		border:#06c dashed 1px;
	} 
	legend {
		color:#06c;
		font-weight:800; 
		background:#fff;
	} 

	#tableID2  td{
		text-align:center;
	}	
</style>
</head>
<body>
<form id ="f1" action="${ctx}/attence/queryAttenceDetails.do" method="POST">
<input type="hidden" id="page" name="page" value="1" />
<input type="hidden"  id="maxResult" name="maxResult" value="${pageView.maxresult}"/>
<div class="TAB_right">
<h1><b>位置：</b><a href="###">首页</a><span>&gt;</span><a href="###">考勤详情列表</a></h1>
	<div class="retrieve_list">
			<table class="retrieve_table" width="100%" cellpadding="0" cellspacing="0">
					<tr>
						<td style="width:150px">&nbsp;&nbsp; 姓名：<input style="width:90px" type="text" id="username" name="username" value= "${attenceDetails.username}"></td>
						<td style="width:180px">&nbsp;&nbsp; 员工编号：<input style="width:90px" type="text" id="empCode" name="empCode" value= "${attenceDetails.empCode}"></td>
						<td align="left" style="width:225px">所属部门：
							<select name="department" id="department" class="sel" style="width:150px">
								<c:choose>
									<c:when test="${userRole=='BMJL'}">
	                              		<c:forEach items="${dictionaryLlist}" var="dic" varStatus="status">
					                    	<c:if test="${dic.type_code=='01' && dic.code==attenceDetails.department }">
					                    		<option value="${dic.code}" selected>${dic.name }</option>
					                    	</c:if>
					                    </c:forEach>
	                              	</c:when>
                              		<c:otherwise>
		                                <option value="">请选择</option>
					                   	<c:forEach items="${dictionaryLlist}" var="dic" varStatus="status">
						                   	<c:if test="${dic.type_code=='01'}">
						                    	<option value="${dic.code}" <c:if test="${dic.code==attenceDetails.department}">selected</c:if>>${dic.name }</option>
						                    </c:if>
					                    </c:forEach>
                              		</c:otherwise>
                              	</c:choose>
                         	  </select>
						</td>
						<td width="180px">&nbsp;&nbsp; 考勤月份：<input style="width: 90px;" readonly="readonly"  type="text" id="attenceDate" name="attenceDate" value= "${attenceDetails.attenceDate}"  onclick="WdatePicker({dateFmt:'yyyy-MM',isShowClear:true});"/></td>
						<td align="right">&nbsp;&nbsp;
							<shiro:hasPermission name="user_attence_details_query">
								<input class="form_now marginr10" type="button" value="查询" onclick="selectSubmit()">&nbsp;&nbsp;&nbsp;&nbsp;
							</shiro:hasPermission>
							<shiro:hasPermission name="user_attence:exportAttence">
								<input class="form_now marginr10" type="button" value="导出" onclick="exportAttence()">&nbsp;&nbsp;&nbsp;&nbsp;
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
        		<table class="fancyTable" id="myTable02" cellpadding="0" cellspacing="0" style="table-layout:fixed;">
        		    <thead>
        		        <tr>
        		        	<th style="width:35px;">序号</th>
        		        	<th style="width:100px;"><a href="#" style="color:black">姓名</a></th>
        		        	<th style="width:100px;"><a href="#" style="color:black">员工编号</a></th>
        		            <th style="width:140px;"><a href="#"  style="color:black">所属部门</a></th>
        		            <th style="width:100px;"><a href="#"  style="color:black">考勤日期</a></th>
			              	<th style="width:100px;"><a href="#"  style="color:black">星期</a></th>
			              	<th style="width:100px;"><a href="#"  style="color:black">日期类型</a></th>
			              	<th style="width:100px;"><a href="#"  style="color:black">上午考勤</a></th>
			              	<th style="width:100px;"><a href="#"  style="color:black">下午考勤</a></th>
			              	<th style="width:80px;"><a href="#"  style="color:black">备注</a></th>
        		        </tr>
        		    </thead>
        		    <tbody id="bodyTR">
        		    <c:forEach items="${pageView.records}" var="attenceDetails" varStatus="status">
        		        <tr>
        		        	<td>${status.index + 1}</td>
                            <td  style="word-wrap:break-word;text-align: center;" >${attenceDetails.username }</td>
                            <td  style="word-wrap:break-word;text-align: center;" >${attenceDetails.empCode }</td>
                            <td  style="word-wrap:break-word;text-align: center;" >${attenceDetails.departmentName }</td>
                            <td  style="word-wrap:break-word;text-align: center;" >${attenceDetails.attenceDate }</td>
                            <td  style="word-wrap:break-word;text-align: center;" >${attenceDetails.weekName }</td>
                            <td  style="word-wrap:break-word;text-align: center;" >${attenceDetails.dateTypeName }</td>
                            <td  style="word-wrap:break-word;text-align: center;" >${attenceDetails.morningAttence }</td>
                            <td  style="word-wrap:break-word;text-align: center;" >${attenceDetails.afternoonAttence }</td>
                            <td  style="word-wrap:break-word;text-align: center;" >
                            	<c:choose>
                            		<c:when test="${attenceDetails.remark=='1' || attenceDetails.remark=='1all'  }">外出（一天）</c:when>
                            		<c:when test="${attenceDetails.remark=='1moring'  }">外出（上午）</c:when>
                            		<c:when test="${attenceDetails.remark=='1afternoon'  }">外出（下午）</c:when>
                            		<c:when test="${attenceDetails.remark=='2' || attenceDetails.remark=='2all'  }">请假（一天）</c:when>
                            		<c:when test="${attenceDetails.remark=='2moring'  }">请假（上午）</c:when>
                            		<c:when test="${attenceDetails.remark=='2afternoon'  }">请假（下午）</c:when>
                            		<c:when test="${attenceDetails.remark=='3'  }">出差</c:when>
                            		
                            		<c:when test="${attenceDetails.remark=='4' || attenceDetails.remark=='4all'  }">病假（一天）</c:when>
                            		<c:when test="${attenceDetails.remark=='4moring'  }">病假（上午）</c:when>
                            		<c:when test="${attenceDetails.remark=='4afternoon'  }">病假（下午）</c:when>
                            		<c:when test="${attenceDetails.remark=='5' || attenceDetails.remark=='5all'  }">事假（一天）</c:when>
                            		<c:when test="${attenceDetails.remark=='5moring'  }">事假（上午）</c:when>
                            		<c:when test="${attenceDetails.remark=='5afternoon'  }">事假（下午）</c:when>
                            		<c:when test="${attenceDetails.remark=='6' || attenceDetails.remark=='6all'  }">婚假（一天）</c:when>
                            		<c:when test="${attenceDetails.remark=='6moring'  }">婚假（上午）</c:when>
                            		<c:when test="${attenceDetails.remark=='6afternoon'  }">婚假（下午）</c:when>
                            		
                            		<c:when test="${attenceDetails.remark=='7' || attenceDetails.remark=='7all'  }">丧假（一天）</c:when>
                            		<c:when test="${attenceDetails.remark=='7moring'  }">丧假（上午）</c:when>
                            		<c:when test="${attenceDetails.remark=='7afternoon'  }">丧假（下午）</c:when>
                            		<c:when test="${attenceDetails.remark=='8' || attenceDetails.remark=='8all'  }">产假（一天）</c:when>
                            		<c:when test="${attenceDetails.remark=='8moring'  }">产假（上午）</c:when>
                            		<c:when test="${attenceDetails.remark=='8afternoon'  }">产假（下午）</c:when>
                            		<c:when test="${attenceDetails.remark=='9' || attenceDetails.remark=='9all'  }">年假（一天）</c:when>
                            		<c:when test="${attenceDetails.remark=='9moring'  }">年假（上午）</c:when>
                            		<c:when test="${attenceDetails.remark=='9afternoon'  }">年假（下午）</c:when>
                            		<c:otherwise>--</c:otherwise>
                            	</c:choose>
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
<script type="text/javascript">
	$(document).ready(function(){
		$(".fht-tbody").css("height", "85%");
		$(".grid_8").css("height","75%");
	})
	
	//到指定的分页页面
	function topage(page){
		$("#page").val(page);
		$("#listForm").attr("method","POST");
		$("#listForm").attr("action","${ctx}/attence/queryAttenceDetails.do");
		$("#listForm").submit();
	}	
	//到指定的分页页面
	function topagePagination(page,maxResult){
		$("#page").val(page);
		$("#maxResult").val(maxResult);
		$("#f1").attr("method","POST");
		$("#f1").attr("action","${ctx}/attence/queryAttenceDetails.do");
		$("#f1").submit();
	}	
	
	function selectSubmit(){
		$("#f1").attr("method","POST");
		$("#f1").attr("action","${ctx}/attence/queryAttenceDetails.do");
		$("#f1").submit();
	}
	
	function exportAttence(){
		$("#f1").attr("method","POST");
		$("#f1").attr("action","${ctx}/attence/exportAttence.do");
		$("#f1").submit();
	}
</script>
</body>
</html>