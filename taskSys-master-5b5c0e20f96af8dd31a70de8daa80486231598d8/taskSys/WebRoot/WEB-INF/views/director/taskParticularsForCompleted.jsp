<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/common/taglibs.jsp" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.0 Transitional//EN"> 
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<%@ include file="/common/meta.jsp" %>
<title>任务详情</title>
<link href="${ctx}/css/static/base-global.css" rel="stylesheet" type="text/css" />
<link href="${ctx}/css/static/common.css" rel="stylesheet" type="text/css" />
<link href="${ctx}/css/static/main.css" rel="stylesheet" type="text/css" />
<link href="${ctx}/css/ystep.css?" rel="stylesheet" type="text/css" />
<link rel="stylesheet" type="text/css" href="${ctx}/css/pop.css" />
<link rel="stylesheet" type="text/css" href="${ctx}/css/common.css" />
<link rel="stylesheet" type="text/css" href="${ctx}/css/right.css" />


<script type="text/javascript" src="${ctx}/js/My97DatePicker/WdatePicker.js" ></script>
<script type="text/javascript" src="${ctx}/js/static/jquery/jquery.min.js"></script>
<script type="text/javascript" src="${ctx}/js/ystep.js"></script>

<link rel="stylesheet" type="text/css" href="${ctx}/components/colorbox/colorbox.css"/>
<script language="javascript" src="${ctx}/components/colorbox/jquery.colorbox.js"></script>
</head>

<body scroll="no">
	<div class="content" id="mainarea">
		<div class="title"><h1><b>&nbsp;&nbsp;任务详细</b><c:if test="${n<=0}"> —— <span style="color: red;">该任务没有子任务！</span></c:if></h1></div>
			<div class="form-box fn-clear mg-t20">
				<div class="fl wd800">
				<div class="gzt-hz">
					<div id="tbc3_01" style="position:relative;">
<!-- -------------------------------------------------------主任务------------------------------------------------------------------------------- -->
					<div style="height: 350px;overflow: auto;">
						<ul>
    						<li class="fn-clear line">
    						<div class="fl wd108"><span style="font-size: large;">主任务：</span></div><b>
    							<c:if test="${taskInfo.falred eq '0' }">
    								<div class="fl wd218">
    									<p style="color: red;" title="${taskInfo.taskContent}">${fn:substring(taskInfo.taskContent, 0, 15)}
    									<c:if test="${fn:length(taskInfo.taskContent) > 15}">...</c:if></p>
    								</div>
    								<div class="fl wd188">
    									<p style="color: red;">计划完成时间：${fn:substring(taskInfo.expectEndTime, 0, 10)}</p>
    								</div>
    								<div class="fl wd188">
    									<p style="color: red;">实际完成时间：${fn:substring(taskInfo.endDate, 0, 10)}</p>
    								</div>
    								<div class="fl wd108">
    									<p style="color: red;">工作量：${taskInfo.taskWorkTime}</p>
    								</div>
    								<div class="fl wd108">
    									<c:if test="${flag == 0 }"><p style="color: red;">负责人：${taskInfo.exec_name}</p></c:if>
    									<c:if test="${flag == 1 }"><p style="color: red;">创建人：${taskInfo.create_name}</p></c:if>
    								</div>
    							</c:if>
    						
    							<c:if test="${taskInfo.falred ne '0' }">
    								<div class="fl wd218">
    									<p  title="${taskInfo.taskContent}">${fn:substring(taskInfo.taskContent, 0, 15)}
    									<c:if test="${fn:length(taskInfo.taskContent) > 15}">...</c:if></p>
    								</div>
    								<div class="fl wd188">
    									<p>计划完成时间：${fn:substring(taskInfo.expectEndTime, 0, 10)}</p>
    								</div>
    								<div class="fl wd188">
    									<p>实际完成时间：${fn:substring(taskInfo.endDate, 0, 10)}</p>
    								</div>
    								<div class="fl wd108">
    									<p>工作量：${taskInfo.taskWorkTime}</p>
    								</div>
    								<div class="fl wd108">
    									<c:if test="${flag == 0 }"><p>负责人：${taskInfo.exec_name}</p></c:if>
    									<c:if test="${flag == 1 }"><p>创建人：${taskInfo.create_name}</p></c:if>
    								</div>
    							</c:if></b>
    					 </li>
    				 </ul>
 <!-- -----------------------------------------------------分任务------------------------------------------------------------------------------------ -->   			 
    					<c:forEach items="${pclist.datas}" var="sontask" varStatus="status">
    					<ul>
    						<li class="fn-clear line">
    						<div class="fl wd108 mg-t12">子任务&nbsp;&nbsp;${status.index+1}&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<c:if test="${sontask.ids > 0}"><img id="imgs${status.index+1}" src="../images/bigjia.png" onclick="showDiv('${status.index+1}')"></c:if></div>
    								<div class="fl wd250">
    									<p title="${sontask.taskcontent}">${fn:substring(sontask.taskcontent, 0, 15)}
    									<c:if test="${fn:length(sontask.taskcontent) > 15}">...</c:if></p>
    									<p>计划完成时间：${fn:substring(sontask.expectendtime, 0, 10)}</p>
    								</div>
    								<div class="fl wd250 ">
    									<p>实际完成时间：${fn:substring(sontask.enddate, 0, 10)}</p>
    									<p>负责人：${sontask.exec_name}</p>
    								</div>
    								
    								<div class="fl mg-t12">
    									<p>工作量：${sontask.taskworktime}</p>
    								</div>
    								
    								<c:if test="${sontask.tjtype == 5}">    <!-- 5延期提交，6按时提交，7提前提交， -->
    									<div class="fr mg-t12 mg-l120"><span style="color: red;blue;font: bold;">延期完成</span></div>
	    							</c:if>
	    							<c:if test="${sontask.tjtype == 7}">    <!-- 5延期提交，6按时提交，7提前提交， -->
	    								<div class="fr mg-t12 mg-l120"><span style="color: green;font: bold;">提前完成</span></div>
	    							</c:if>
	    							<c:if test="${sontask.tjtype == 6}">    <!-- 5延期提交，6按时提交，7提前提交， -->
	    								<div class="fr mg-t12 mg-l120"><span style="color: blue;blue;font: bold;">按时完成</span></div>
	    							</c:if>
    								
    								<div class="fr mg-t12 mg-r245">
    									<p>任务打分：${sontask.score}</p>
    								</div>
    							</li>
    							</ul>
    							<div id="ddv${status.index+1}"  style="display: none;">
	    								<ul>
	    								<c:set value="0" var="index1" scope="session" />
	    								
					    					<c:forEach items="${grandsonTaskList}" var="grandsontask" varStatus="sta">
					    					<c:if test="${grandsontask.taskpid == sontask.id}">
					    					<c:set value="${index1+1}" var="index1" scope="session" /> 
					    						<li class="fn-clear line">
					    						<div class="fl wd108 mg-t12">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;${status.index+1}.${index1}：&nbsp;&nbsp;<c:if test="${grandsontask.ids > 0}"><img id="imgs${status.index+1}.${index1}" src="../images/jia.png" onclick="showDiv2('${status.index+1}.${index1}')"></c:if></div>
					    							<div class="fl wd250">
				    									<p title="${grandsontask.taskContent}">${fn:substring(grandsontask.taskcontent, 0, 15)}
				    									<c:if test="${fn:length(grandsontask.taskcontent) > 15}">...</c:if></p>
				    									<p>计划完成时间：${fn:substring(grandsontask.expectendtime, 0, 10)}</p>
				    								</div>
				    								<div class="fl wd250 ">
				    									<p>实际完成时间：${fn:substring(grandsontask.enddate, 0, 10)}</p>
				    									<p>负责人：${grandsontask.exec_name}</p>
				    								</div>
				    								
				    								<div class="fl mg-t12">
				    									<p>工作量：${grandsontask.taskworktime}</p>
				    								</div>
				    								
				    								<c:if test="${grandsontask.tjtype == 5}">    <!-- 5延期提交，6按时提交，7提前提交， -->
				    									<div class="fr mg-t12 mg-l120"><span style="color: red;blue;font: bold;">延期完成</span></div>
					    							</c:if>
					    							<c:if test="${grandsontask.tjtype == 7}">    <!-- 5延期提交，6按时提交，7提前提交， -->
					    								<div class="fr mg-t12 mg-l120"><span style="color: green;font: bold;">提前完成</span></div>
					    							</c:if>
					    							<c:if test="${grandsontask.tjtype == 6}">    <!-- 5延期提交，6按时提交，7提前提交， -->
					    								<div class="fr mg-t12 mg-l120"><span style="color: blue;blue;font: bold;">按时完成</span></div>
					    							</c:if>
				    								
				    								<div class="fr mg-t12 mg-r245">
				    									<p>任务打分：${grandsontask.score}</p>
				    								</div>
					    						</li>
					    						</c:if>
					    						</c:forEach>
					    						</ul>
					    				</div>
					    			</c:forEach>
		    					</div>
		    					
		    			</div>
		    			
    			</div>
    			<div style="float: right;">
							    	<div class="pagesDiv">
							    	 共<fmt:parseNumber value="${(pclist.total%ps==0 ? pclist.total/ps : (pclist.total/ps)+1)}" integerOnly="true" />页&nbsp;|&nbsp;共${pclist.total}条记录&nbsp;
								    	<pg:pager url="${ctx}/director/taskParticularsForCompleted.do" items="${pclist.total}" maxPageItems="${ps}" export="page,currentPageNumber=pageNumber">
								             	<pg:param name="pqFlag" value="true"/>
								             	<pg:param name="flag" value="${flag}"/>
								             	<pg:param name="id" value="${id}"/>
											<pg:first><a href="${pageUrl}">[首页]</a></pg:first>
											<pg:prev><a href="${pageUrl}">[上一页]</a></pg:prev>
											<pg:pages>
												<c:choose>
													<c:when test="${currentPageNumber eq pageNumber }">
														<span class="current">${pageNumber}</span>
													</c:when>
													<c:otherwise>
														<a href="${pageUrl }">${pageNumber }</a>
													</c:otherwise>
												</c:choose>
											</pg:pages>
											<pg:next><a href="${pageUrl }">[下一页]</a></pg:next>
											<pg:last><a href="${pageUrl }">[尾页]</a></pg:last>
										</pg:pager>
									</div>
			  			</div>
    		</div>
    	</div>
	</div>
</body>

<script type="text/javascript">

function showDiv(index){
	//alert(index);
	//alert(id);
	if(document.getElementById("ddv"+index).style.display=="none"){
		   document.getElementById("ddv"+index).style.display="";
		   document.getElementById("imgs"+index).setAttribute('src','../images/bigjian.png');
	}else{
		   		document.getElementById("ddv"+index).style.display="none";
		   		document.getElementById("imgs"+index).setAttribute('src','../images/bigjia.png');
		    }
	}

function showDiv2(index){
	//alert(index);
	if(document.getElementById("ddv"+index).style.display=="none"){
		   document.getElementById("ddv"+index).style.display="";
		   document.getElementById("imgs"+index).setAttribute('src','../images/jian.png');
	}else{
		   		document.getElementById("ddv"+index).style.display="none";
		   		document.getElementById("imgs"+index).setAttribute('src','../images/jia.png');
		    }
	}


</script>
</html>