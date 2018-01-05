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
		<div class="title"><h1><b>&nbsp;&nbsp;任务详细</b><c:if test="${index<=0}"> —— <span style="color: red;">该任务没有子任务！</span></c:if></h1></div>
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
    								<div class="fl wd188 ">
    									<p style="color: red;">计划完成时间：${fn:substring(taskInfo.expectEndTime, 0, 10)}</p>
    								</div>
    								<div class="fl wd108">
    									<p style="color: red;">工作量：${taskInfo.taskWorkTime}</p>
    								</div>
    								<div class="fl wd108">
    									<p style="color: red;">负责人：${taskInfo.exec_name}</p>
    								</div>
    								<div class="fl wd108">
    									<p style="color: red;">任务已延期</p>
    								</div>
    							</c:if>
    						
    							<c:if test="${taskInfo.falred ne '0' }">
    								<div class="fl wd218">
    									<p  title="${taskInfo.taskContent}">${fn:substring(taskInfo.taskContent, 0, 15)}
    									<c:if test="${fn:length(taskInfo.taskContent) > 15}">...</c:if></p>
    								</div>
    								<div class="fl wd218 ">
    									<p>计划完成时间：${fn:substring(taskInfo.expectEndTime, 0, 10)}</p>
    								</div>
    								<div class="fl wd108">
    									<p>工作量：${taskInfo.taskWorkTime}</p>
    								</div>
    								<div class="fl wd108">
    									<p>负责人：${taskInfo.exec_name}</p>
    								</div>
    							</c:if></b>
    					 </li>
    				 </ul>
 <!-- -----------------------------------------------------分任务------------------------------------------------------------------------------------ -->   			 
    					
    					<c:forEach items="${pclist.datas}" var="subtask" varStatus="status">
    					<ul>
    						<li class="fn-clear line">
    						<div class="fl wd108 mg-t12">子任务&nbsp;&nbsp;${status.index+1}&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<c:if test="${subtask.ids > 0}"><img id="imgs${status.index+1}" src="../images/bigjia.png" onclick="showDiv('${status.index+1}','${subtask.id}')"></c:if></div>
    						<c:if test="${subtask.falred eq '0'}">
    							<div class="fl wd218">
    								<p style="color: red;" title="${subtask.taskContent}">${fn:substring(subtask.taskContent, 0, 15)}
    								<c:if test="${fn:length(subtask.taskContent) > 15}">...</c:if></p>
    								<p style="color: red;">计划完成时间：${fn:substring(subtask.expectEndTime, 0, 10)}</p>
    							</div>
    							<div class="fl wd218 ">
    								<p style="color: red;">负责人：${subtask.exec_name}</p>
    								<p style="color: red;">任务已延期</p>
    							</div>
    							<div class="fl mg-t12">
    									<p>工作量：${subtask.taskWorkTime}</p>
    								</div>
    						</c:if>
    						<c:if test="${subtask.falred ne '0'}">	
    							<div class="fl wd218" onclick="showDiv(${status.index+1})">
    								<p title="${subtask.taskContent}">${fn:substring(subtask.taskContent, 0, 15)}
    								<c:if test="${fn:length(subtask.taskContent) > 15}">...</c:if></p>
    								<p>计划完成时间：${fn:substring(subtask.expectEndTime, 0, 10)}</p>
    							</div>
    							<div class="fl wd218 ">
    								<p>负责人：${subtask.exec_name}</p>
    								<c:if test="${subtask.falred eq '0'}">
    									<p>任务已延期</p>
    								</c:if>
    							</div>
    							<div class="fl mg-t12">
    									<p>工作量：${subtask.taskWorkTime}</p>
    								</div>
    						</c:if>
    							<input type="hidden" id="index" value="${index}"/>
    							<div style="position: relative;float: left;"></div>
    							<div style="position: relative;float: left;left: 50px;" id="ystep4${status.index+1}" class="ystep4${status.index+1}">
    								<tr id="tr${status.index+1}">
	    								<input type="hidden" id="create_name${status.index+1}" value="${subtask.create_name}"/><!-- 创建人 -->
	    								<input type="hidden" id="exec_name${status.index+1}" value="${subtask.exec_name}"/><!-- 执行人 -->
	    								<input type="hidden" id="createTime${status.index+1}" value="${subtask.createTime}"/><!-- 创建时间 -->
	    								<input type="hidden" id="expectEndTime${status.index+1}" value="${subtask.expectEndTime}"/><!-- 计划完成时间 -->
	    								<input type="hidden" id="actualEndTime${status.index+1}" value="${subtask.actualEndTime}"/><!-- 实际完成时间 -->
	    								<input type="hidden" id="taskstatus${status.index+1}" value="${subtask.taskstatus}"/><!-- 任务状态    -->
    									
    									<input type="hidden" id="yfpDate${status.index+1}" value="${subtask.yfpDate}"/><!-- 已分配时间 -->
	    								<input type="hidden" id="jxzDate${status.index+1}" value="${subtask.jxzDate}"/><!-- 进行中时间（已接收时间） -->
	    								<input type="hidden" id="subDate${status.index+1}" value="${subtask.subDate}"/><!-- 提交时间 -->
	    								<input type="hidden" id="endDate${status.index+1}" value="${subtask.endDate}"/><!-- 完成时间    -->
    								</tr>
    							</div>
    							</li>
    							</ul>
    							<div id="ddv${status.index+1}"  style="display: none;">
	    								<ul>
	    								<c:set value="0" var="index1" scope="session" />
	    								
					    					<c:forEach items="${tList}" var="sontask" varStatus="sta">
					    					<c:if test="${sontask.ctaskpid == subtask.id}">
					    					<c:set value="${index1+1}" var="index1" scope="session" /> 
					    						<li class="fn-clear line">
					    						<div class="fl wd108 mg-t12">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;${status.index+1}.${index1}：&nbsp;&nbsp;<c:if test="${sontask.cids > 0}"><img id="imgs${status.index+1}.${index1}" src="../images/jia.png" onclick="showDiv2('${status.index+1}.${index1}')"></c:if></div>
					    						<c:if test="${sontask.cfalred eq '0'}">
					    							<div class="fl wd250">
					    								<p style="color: red;" title="${sontask.ctaskcontent}">${fn:substring(sontask.ctaskcontent, 0, 15)}
					    								<c:if test="${fn:length(sontask.ctaskcontent) > 15}">...</c:if></p>
					    								<p style="color: red;">计划完成时间：${fn:substring(sontask.cexpectendtime, 0, 10)}</p>
					    							</div>
					    							<div class="fl wd108 mg-l30">
					    								<p style="color: red;">负责人：${sontask.cexec_name}</p>
					    								<p style="color: red;">任务已延期</p>
					    							</div>
					    							<div class="fl mg-t12">
				    									<p>工作量：${sontask.taskworktime}</p>
				    								</div>
					    						</c:if>
					    						<c:if test="${sontask.cfalred ne '0'}">	
					    							<div class="fl wd250" onclick="showDiv(${sta.index+1})">
					    								<p title="${sontask.ctaskcontent}">${fn:substring(sontask.ctaskcontent, 0, 15)}
					    								<c:if test="${fn:length(sontask.ctaskcontent) > 15}">...</c:if></p>
					    								<p>计划完成时间：${fn:substring(sontask.cexpectendtime, 0, 10)}</p>
					    							</div>
					    							<div class="fl wd108 mg-l30">
					    								<p>负责人：${sontask.cexec_name}</p>
					    								<c:if test="${sontask.cfalred eq '0'}">
					    									<p>任务已延期</p>
					    								</c:if>
					    							</div>
					    							<div class="fl mg-t12">
				    									<p>工作量：${sontask.taskworktime}</p>
				    								</div>
					    						</c:if>
					    							<input type="hidden" id="tindex" value="${tindex}"/>
					    							<div style="position: relative;float: left;"></div>
					    							<div style="position: relative;float: left;left: 100px;" id="ystepc${sta.index+1}" class="ystepc${sta.index+1}">
					    								<tr id="tr${sta.index+1}">
						    								<input type="hidden" id="ccreate_name${sta.index+1}" value="${sontask.ccreate_name}"/><!-- 创建人 -->
						    								<input type="hidden" id="cexec_name${sta.index+1}" value="${sontask.cexec_name}"/><!-- 执行人 -->
						    								<input type="hidden" id="ccreateTime${sta.index+1}" value="${sontask.ccreatetime}"/><!-- 创建时间 -->
						    								<input type="hidden" id="cexpectEndTime${sta.index+1}" value="${sontask.cexpectendtime}"/><!-- 计划完成时间 -->
						    								<input type="hidden" id="cactualEndTime${sta.index+1}" value="${sontask.cactualendtime}"/><!-- 实际完成时间 -->
						    								<input type="hidden" id="ctaskstatus${sta.index+1}" value="${sontask.ctaskstatus}"/><!-- 任务状态    -->
					    									
					    									<input type="hidden" id="cyfpDate${sta.index+1}" value="${sontask.cyfpdate}"/><!-- 已分配时间 -->
						    								<input type="hidden" id="cjxzDate${sta.index+1}" value="${sontask.cjxzdate}"/><!-- 进行中时间（已接收时间） -->
						    								<input type="hidden" id="csubDate${sta.index+1}" value="${sontask.csubdate}"/><!-- 提交时间 -->
						    								<input type="hidden" id="cendDate${sta.index+1}" value="${sontask.cenddate}"/><!-- 完成时间    -->
					    								</tr>
					    							</div>
					    						</li>
					    					</c:if>
					    					<div id="ddv${status.index+1}.${index1}"  style="display: none;"></div>
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
								    	<pg:pager url="${ctx}/director/taskParticulars.do" items="${pclist.total}" maxPageItems="${ps}" export="page,currentPageNumber=pageNumber">
								             	<pg:param name="pqFlag" value="true"/>
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

function showDiv(index,id){
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

$(document).ready(function(){
	
	   jQuery.each($("tr#tr input"), function(k, v){
		   var create_name = $("input#create_name").val(); 
			//alert(k);
		});   	
	   
	   var index = $("#index").val();
	   //alert(index);
		  
	   
	   
	  for(i=1;i<index+1;i++){
		  var title1 = "";
		  var title2 = "";
		  var title3 = "";
		  var title4 = "";
		  var title5 = "";
		  var content1 = "";
		  var content2 = "";
		  var content3 = "";
		  var content4 = "";
		  var content5 = "";
		  
	   var create_name = $("#create_name"+i).val();    //任务创建人
	   var exec_name = $("#exec_name"+i).val();		//任务执行人
	   var createTime = $("#createTime"+i).val();			//任务创建时间
	   var taskstatus = $("#taskstatus"+i).val();			//任务状态  1待分配，2已分配，3进行中，4延期未提交，5延期提交，6按时提交，7提前提交，8已完成	
     var 	yfpDate = $("#yfpDate"+i).val();				//已分配时间
    var 	jxzDate = $("#jxzDate"+i).val();					//进行中时间（已接收时间）
    var 	subDate = $("#subDate"+i).val();					//提交时间
    var 	endDate = $("#endDate"+i).val();					//完成时间
    
    if(1<=taskstatus){
   	   title1 ="创建";
   	   content1 = create_name+"于"+createTime+"创建任务";
      }
      if(2<=taskstatus){
   	   title2 ="分配";
   	   content2 = "任务于"+yfpDate+"分配给:"+exec_name;
      }
      if(3<=taskstatus){
   	   title3 ="接受";
   	   content3 = exec_name+"于"+jxzDate+"接受任务";
      }
      if(5<=taskstatus){
   	   title4 ="提交";
   	   content4 = exec_name+"于"+subDate+"提交任务";
      }
      if(taskstatus==8){
   	   title5 ="完成";
   	   content5 = "任务已完成";
      }
	   
	   $(".ystep4"+i).loadStep({
 	  size: "small",
 	  color: "blue",
 	  steps: [{
 		title: title1,
 		content: content1
 	  },{
 		title: title2,
 		content: content2
 	  },{
 		title: title3,
 		content:content3
 	  },{
 		title: title4,
 		content: content4
 	  },{
 		title: title5,
 		content: content5
 	  }]
 	});
	   
	
	   if(taskstatus == 1){
		   $(".ystep4"+i).setStep(1);
	   }else if(taskstatus == 2){
		   $(".ystep4"+i).setStep(2);
	   }else if(taskstatus == 3 || taskstatus == 4){
		   $(".ystep4"+i).setStep(3);
	   }else if(taskstatus == 5 || taskstatus == 6 || taskstatus == 7){
		   $(".ystep4"+i).setStep(4);
	   }else if(taskstatus == 8){
		   $(".ystep4"+i).setStep(5);
	   }
	  }
	  
	  
	  var tindex = $("#tindex").val();
	  for(i=1;i<tindex+1;i++){
		  var title1 = "";
		  var title2 = "";
		  var title3 = "";
		  var title4 = "";
		  var title5 = "";
		  var content1 = "";
		  var content2 = "";
		  var content3 = "";
		  var content4 = "";
		  var content5 = "";
		  
	   var create_name = $("#ccreate_name"+i).val();    //任务创建人
	   var exec_name = $("#cexec_name"+i).val();		//任务执行人
	   var createTime = $("#ccreateTime"+i).val();			//任务创建时间
	   var taskstatus = $("#ctaskstatus"+i).val();			//任务状态  1待分配，2已分配，3进行中，4延期未提交，5延期提交，6按时提交，7提前提交，8已完成	
     var 	yfpDate = $("#cyfpDate"+i).val();				//已分配时间
    var 	jxzDate = $("#cjxzDate"+i).val();					//进行中时间（已接收时间）
    var 	subDate = $("#csubDate"+i).val();					//提交时间
    var 	endDate = $("#cendDate"+i).val();					//完成时间
    
    if(1<=taskstatus){
  	   title1 ="创建";
  	   content1 = create_name+"于"+createTime+"创建任务";
     }
     if(2<=taskstatus){
  	   title2 ="分配";
  	   content2 = "任务于"+yfpDate+"分配给:"+exec_name;
     }
     if(3<=taskstatus){
  	   title3 ="接受";
  	   content3 = exec_name+"于"+jxzDate+"接受任务";
     }
     if(5<=taskstatus){
  	   title4 ="提交";
  	   content4 = exec_name+"于"+subDate+"提交任务";
     }
     if(taskstatus==8){
  	   title5 ="完成";
  	   content5 = "任务已完成";
     }
	   
	   $(".ystepc"+i).loadStep({
 	  size: "small",
 	  color: "green",
 	  steps: [{
 		title: title1,
 		content: content1
 	  },{
 		title: title2,
 		content: content2
 	  },{
 		title: title3,
 		content:content3
 	  },{
 		title: title4,
 		content: content4
 	  },{
 		title: title5,
 		content: content5
 	  }]
 	});
	   
	   	
	
	   if(taskstatus == 1){
		   $(".ystepc"+i).setStep(1);
	   }else if(taskstatus == 2){
		   $(".ystepc"+i).setStep(2);
	   }else if(taskstatus == 3 || taskstatus == 4){
		   $(".ystepc"+i).setStep(3);
	   }else if(taskstatus == 5 || taskstatus == 6 || taskstatus == 7){
		   $(".ystepc"+i).setStep(4);
	   }else if(taskstatus == 8){
		   $(".ystepc"+i).setStep(5);
	   }
	  }
	  
	  
	   
 });
 

 	
</script>
</html>