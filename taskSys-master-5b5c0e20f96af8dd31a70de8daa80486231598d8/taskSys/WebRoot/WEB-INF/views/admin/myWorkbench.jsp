<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/common/taglibs.jsp" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.0 Transitional//EN"> 
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<%@ include file="/common/meta.jsp" %>
<title>我的工作台</title>
<link href="${ctx}/css/static/base-global.css" rel="stylesheet" type="text/css" />
<link href="${ctx}/css/static/common.css" rel="stylesheet" type="text/css" />
<link href="${ctx}/css/static/main.css?1=1" rel="stylesheet" type="text/css" />
<link rel="stylesheet" type="text/css" href="${ctx}/css/pop.css" />
<script type="text/javascript" src="${ctx}/js/static/jquery/jquery-1.7.2.min.js"></script>
<script type="text/javascript" src="${ctx}/js/My97DatePicker/WdatePicker.js" ></script>
<script type="text/javascript" src="${ctx}/js/sysUtil.js"></script>
<script type="text/javascript" src="${ctx}/js/alert/g_alert.js?1=1"></script>

<script type="text/javascript" src="${ctx}/js/util/common.js?1=1">
</script><script type="text/javascript" src="${ctx}/js/jquery.validate.js"></script>

<!-- 饼状图 -->
<script type="text/javascript" src="${ctx}/js/static/piechart/jsapi.js"></script>
<script type="text/javascript" src="${ctx}/js/static/piechart/corechart.js"></script>
<script type="text/javascript" src="${ctx}/js/static/piechart/jquery.gvChart-1.0.1.min.js"></script>

<!-- jquery-ui start -->
<link rel="stylesheet" href="${ctx}/js/jquery_ui/jquery-ui.css">
<script src="${ctx}/js/jquery_ui/jquery-ui.js"></script>
<script type="text/javascript">
    var availableTags = new Array();
    var availableCodes = new Array();
	$(function() {
	    $( "#proName" ).autocomplete({
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
						var resultStr="";
						
						for ( var i = 0; i < data.list.length; i++) {
							availableTags[i]=data.list[i].proname;
							availableCodes[i]=data.list[i].code;
							//resultStr = resultStr + "<option value='" + data.list[i].code + "'>" + data.list[i].proname + "</option>";
						}
						$( "#proName" ).autocomplete({
					    	source: availableTags
					    });
						//$("#codeT").html(resultStr);
					}
				}
	    	}); 
		}
	}
	
	function changeF(value) {
		//alert(value);
		for ( var i = 0; i < availableTags.length; i++) {
 			if(value==availableTags[i]){
				$("#projectCode").val(availableCodes[i]);
			}
		}
	}
</script>
<!-- jquery-ui start -->


<script type="text/javascript">
    gvChartInit();
    $(document).ready(function () {
    	var myTable = $('#myTable1');
        if(myTable!=null && myTable!=''){
        	myTable.gvChart({
	            chartType: 'PieChart',
	            gvSettings: {
	                width: 300,
	                height: 200
	            }
	        });
        }
        
    });
</script>
<!-- 饼状图 end-->
<script type="text/javascript">
  // 输入框内容点击变色
  function focusMeByAll(which,defalutWord){
      var val = $(which).val();
      $(which).css("color",'#3c3c3c');
      if(val==defalutWord)
        $(which).val("");
    }

    function blurMeByAll(which,defalutWord){
      var val = $(which).val().replace(/(^\s*)|(\s*$)/g, "");
        if(val==""){
        $(which).val(defalutWord);
        $(which).css("color",'#d6d6d6');
        }
    }
    /*textarea 属性readonly="readonly"时，禁用backspace事件*/
    function disableBackspace(){
    	var isReadOnly = $("#taskContent").prop("readonly");
	    if (isReadOnly && event.keyCode == 8) {
			event.preventDefault();
		}
    }
</script>
<script type="text/javascript">
	$(document).ready(function(){
	      $('.msg-wake').click(function(){
	          $('.m-box').show()
	        })

	      $('.mm-close').click(function(){
	          $('.m-box').hide()
	        })

	      $('.hl-msg').click(function(){
	          $('.m-box').hide()
	        })

	      $('.gly-wake').click(function(){
	          $('.m-box').hide()
	        })

	      $('.gly-wake').click(function(){
	          $('.zhxx-box').show()
	        })

	      $('.mm-close').click(function(){
	          $('.zhxx-box').hide()
	        })

	      $('.hl-msg').click(function(){
	          $('.zhxx-box').hide()
	        })

	      $('.msg-wake').click(function(){
	          $('.zhxx-box').hide()
	        })
	});
</script>

    <script type="text/javascript">
    	//----------------------------------点击忽略消息提醒---------------------------------------
    function messageIgnore(id,messagestatus){
    	
    	url = "${ctx}/message/messageIgnore.do";
		  $.ajax({
			type : "POST", 
			url : url,
			data : {"taskInfoId":id,"messageStatus":messagestatus},
			dataType : "json", 
			success : function(data){
				if(data.mesgList!=null){
					if(data.mesgList.length>0){
							parent.$("#num-bg").html(data.mesgList.length);
							parent.$("#allIgnore").html("<a href=\"javascript:(void);\" class=\"cblue2 hl-msg\" onclick=\"upMessage()\">忽略全部</a>");
						}else{
							parent.$("#num-bg").html("");
							parent.$("#num-bg").removeClass("num-bg");
							parent.$("#allIgnore").html("<span><font color=\"gray\">忽略全部</font></span>");
							parent.$("#showMessageId").hide();
						}
						parent.$("#ulId li").remove();
						for ( var i = 0; i < data.mesgList.length; i++) {
							parent.$("#ulId").append("<li><a href=\"javascript:(void);\" onclick=\"goingPage("+(data.mesgList[i]).id+")\" >"+(data.mesgList[i]).title+"</a></li>");
						}
				}
			}
    	}); 
    }
    </script>
<script type="text/javascript">
/*滑动门*/
		function g(o){return document.getElementById(o);}
		function HoverLi3(n, taskStatus){
		    //如果有N个标签,就将i<=N;
		    for(var i=1;i<=4;i++){
		    	if(g('tb3_'+i)!=null){
		    		g('tb3_'+i).className='fl';
		    	}
		    	g('tbc3_0'+i).className='hide';
		    }
		    g('tbc3_0'+n).className='';
			g('tb3_'+n).className='fl cur';
		
			$("#queryForm").action="${ctx}/admin/myWorkbenchList.do";
			 $("#queryForm").attr("method","POST");
			$("#taskStatus").val(taskStatus);
			if(n==4){
				$("#flag").val(4);
			}else{
				$("#flag").val(taskStatus);
			}
			$("#queryForm").submit();
		}
	$(document).ready(function(){
	   // 左边随右边高度变化而变化
		function initSideBar() {
			var o1 = document.getElementById("appwrap");
			var o2 = document.getElementById("mainarea");
			/* var maxh1 = o1.offsetHeight;
			var maxh2 = o2.offsetHeight;
			if(maxh1>maxh2)
				o2.style.height = maxh1 + "px";
			else
				o1.style.height = maxh2 + "px";*/
			} 
			window.onload=initSideBar;  
			
			
			//tab标签
			var n = $("#flag").val();
			//alert(n);
			if(n!=null && n!=''){
				for(var i=1;i<=4;i++){
		    		if(g('tb3_'+i)!=null){
			    		g('tb3_'+i).className='fl';
			    	}
		    		g('tbc3_0'+i).className='hide';
			    }
			    g('tbc3_0'+n).className='';
				g('tb3_'+n).className='fl cur';
			}
	});
</script>
<script type="text/javascript">
	
	function changeStatusList(id,status){
		$("#queryForm").attr("action","${ctx}/admin/updateMyWorkbench.do");
		$("#queryForm").attr("method","POST"); 
		$("#id").val(id);
		$("#status").val(status);
		$("#queryForm").submit();
	}
	
	function initParam(id, taskname,content, expectEndTime, status,flagA, tstatus, is_deliver,taskWorkTime,projectName,projectCode,projectStage){
		$("#id").val(id);
		$("#status").val(status);
		$("#flagA").val(flagA);//1拒绝接收，2提交拒绝,3分配，4接收，5确认
		$("#taskname").val(taskname);
		$("#taskContent").val(content);
		$("#expectEndTime").val(expectEndTime);
		$("#taskWorkTime").val(taskWorkTime);
		//alert(projectName);
		if(projectName==null||projectName==''){
			$("#project").hide();
			$("#projectFalg").val(projectName);
		}else{
			$("#project").show();
			$("#projectFalg").val(projectName);
			$("#proName").val(projectName);
			$("#projectCode").val(projectCode);
			$("#projectStage").val(projectStage);
		}
		$("#executedevtasksys").val("");
		$("#executedevtasksysName").val("");
		if(tstatus!=null && tstatus!=''){
			$("#tstatus").val(tstatus);
		}
		$("#is_deliver").val(is_deliver);
		if(is_deliver=='1'){
			if(flagA=='4'){
				$("#expectEndTime2").val(expectEndTime);
				$("#taskWorkTime2").val(taskWorkTime);
			}else if (flagA=='3') {
				$("#expectEndTime3").val(expectEndTime);
				$("#taskWorkTime3").val(taskWorkTime);
				$("#taskContent2").val(content);
			}
		}
	}
	
	function initParam1(obj){
		$("#id").val(obj.id);
		$("#sp1").html(obj.expectEndTime.substring(0,10));
		$("#sp2").html(obj.yfpDate.substring(0,10));
		$("#sp3").html(obj.subDate.substring(0,10));
		if(obj.remark.length>15){
			$("#sp4").html(obj.remark.substring(0,15)+"...");
		}else{
			$("#sp4").html(obj.remark);
		}
		$("#sp4").attr("title",obj.remark);
		$("#sp5").html(obj.taskWorkTime);
		$("#status").val(8);
		$("#flagA").val(obj.flagA);//1拒绝接收，2提交拒绝,3分配，4接收，5确认
		
		//任务得分计算
		//getScoreByDate(obj.expectEndTime.substring(0,10),obj.yfpDate.substring(0,10),obj.subDate.substring(0,10));
	}
	
	//任务得分计算
	//计算规则（任务计划完成时间点 — 任务提交时间点）/（任务计划完成时间点 — 任务分配时间点）
	function getScoreByDate(expectEndTime,yfpDate,subDate){
		expectEndTime =  expectEndTime.replace(/-/g,"/");
		yfpDate =  yfpDate.replace(/-/g,"/");
		subDate =  subDate.replace(/-/g,"/");
		
		var expectEndTime1 = new Date(expectEndTime);
		var yfpDate1 = new Date(yfpDate);
		var subDate1 = new Date(subDate);
		var res = (expectEndTime1.getTime()-subDate1.getTime())/(expectEndTime1.getTime()-yfpDate1.getTime());
		if(res>0){
			$("#score").val(10);
		}else if (res==0) {
			$("#score").val(8);
		}else if (res>=-0.2 && res<0) {
			$("#score").val(6);
		}else if (res<-0.2) {
			$("#score").val(2);
		}
		
	}
	
	function initParam2(id, flagA){
		$("#id").val(id);
		$("#flagA").val(flagA);//1拒绝接收，2提交拒绝,3分配，4接收，5确认,6转交审核
	}
	
	   
	function rejectFun(id,status,rejectFlag){
		var isAccept = confirm("确认要拒绝吗？");
		if(isAccept){
		//rejectFlag==>1拒绝接收，2提交拒绝
			/* if(rejectFlag==2){
				$("#score").val("");
			} */
			$("#id").val(id);
			$("#status").val(status);
			$("#flagA").val(rejectFlag);//1拒绝接收，2提交拒绝,3分配，4接收，5确认
			$("#queryForm").attr("action","${ctx}/admin/updateMyWorkbench.do");
			$("#queryForm").attr("method","POST");
			$("#queryForm").submit();
			if(rejectFlag==1){
				messageIgnore(id,1);
			}
			if(rejectFlag==2){
				$("#score").val("");
				messageIgnore(id,3);
			}
		}else{
			return;
		}
		
	}
	
	function deleteTask(id,flag){
		//alert(id);
		var isDelete = confirm("确认要删除该任务吗？");
		if(isDelete){
			$("#id").val(id);
			$("#queryForm").attr("action","${ctx}/admin/delTask.do?flag="+flag);
			$("#queryForm").attr("method","POST");
			$("#queryForm").submit();
			
			messageIgnore(id,5);
			messageIgnore(id,7);
		}
	}
</script>

<!-- 分配负责人弹出框start -->
<link rel="stylesheet" type="text/css" href="${ctx}/components/colorbox/colorbox.css"/>
<script language="javascript" src="${ctx}/components/colorbox/jquery.colorbox.js"></script>
<script>
	// 角色弹出选择
	function selectSingleUser(){
	    var url= "${ctx}/admin/userList.do?isFenpei=1";
	    $.colorbox({
	        href:url,
	        iframe:true,
	        width:"700",
	        height:"350"
	    });
	}
	function selectSingleUser_callback(result){
	
		for (var i in result) {
			var tmp = result[i].split(",");
			$("#executedevtasksys").val(tmp[0]);
			$("#executedevtasksysName").val(tmp[1]);
			$("#executedevtasksysName2").val(tmp[1]);
		}
	}
</script>
<!-- 分配负责人弹出框end -->

</head>
  
  <body>
  	<c:if test="${resultFlag==1 }">
  		<tags:message content="${message}" type="success" />
  	</c:if>
  	<script type="text/javascript">
  		$(document).ready(function(){
  			$('.Sure_btn').focus();
  		});
  	</script>
  	<c:if test="${resultFlag==0 }">
  		<tags:message content="${message}" type="error" />
  	</c:if>
							
    <form id="queryForm" action="${ctx}/admin/myWorkbenchList.do" method="post">
  	<div class="content" id="mainarea">
        <div class="title"><h2>我的工作台</h2></div>
		<input type="hidden" name="taskStatus" id="taskStatus" value="1">
		<input type="hidden" name="flag" id="flag" value="${flag }"><!-- flag指定点击的tab标签  -->
		<input type="hidden" name="flagA" id="flagA" value="${flagA }"><!-- flagA指定更新状态的操作  -->
		<input type="hidden" name="id" id="id">
		<input type="hidden" name="status" id="status">
		<input type="hidden" name="tstatus" id="tstatus">
    	<input type="hidden" name="is_deliver" id="is_deliver">
    	<input type="hidden" id="projectFalg" name="projectFalg" />
		
        <div class="form-box fn-clear mg-t20" style="width: 1000px;">
            <div class="fl wd588">
                <div class="tab-box fn-clear">
                	<shiro:hasPermission name="myWorkbench:unallocation">
                		<div class="fl p-r">
                			<a href="#"><span class="fl cur" id="tb3_1" onclick="x:HoverLi3(1,1);">待分配</span></a>
                			<c:if test="${dfp > 0}">
                				<font class="num-g" style="top:-6px;right:10px;">${dfp}</font>
                			</c:if>
                		</div>
                	</shiro:hasPermission>
                	<shiro:hasPermission name="myWorkbench:unreceive">
                		<div class="fl p-r">
                    		<a href="#"><span class="fl" id="tb3_2" onclick="x:HoverLi3(2,2);">待接收</span></a>
                    		<c:if test="${djs > 0}">
                    			<font class="num-g" style="top:-6px;right:10px;">${djs}</font>
                    		</c:if>
                    	</div>
                    </shiro:hasPermission>
                    <shiro:hasPermission name="myWorkbench:unconfirm">
                    	<div class="fl p-r">
                    		<a href="#"><span class="fl" id="tb3_3" onclick="x:HoverLi3(3,3);">待确认</span></a>
                    		<c:if test="${dqr > 0}">
                    			<font class="num-g" style="top:-6px;right:10px;">${dqr}</font>
                    		</c:if>
                    	</div>
                    </shiro:hasPermission>
                    <shiro:hasPermission name="myWorkbench:uncheck">
                    	<div class="fl p-r">
                    		<a href="#"><span class="fl" id="tb3_4" onclick="x:HoverLi3(4,0);">待通过</span></a>
                    		<c:if test="${dtg > 0}">
                    			<font class="num-g" style="top:-6px;right:10px;">${dtg}</font>
                    		</c:if>
                    	</div>
                    </shiro:hasPermission>
                </div>
                <div class="gzt-hz" style="height:398px;">
	                <!------------------ 待分配 start ------------------>
                	<div id="tbc3_01">
                		<div style="height: 380px;overflow: auto;">
                        <ul>
                    	<c:forEach items="${pclist.datas}" var="taskInfo" varStatus="status">
                        <c:if test="${taskInfo.taskstatus==1 }">
                        <li class="fn-clear line">
                            <div class="fl wd250">
                                <p title="${taskInfo.taskContent }">${fn:substring(taskInfo.taskContent, 0, 15)}
                                <c:if test="${fn:length(taskInfo.taskContent) > 15}">...</c:if></p>
                                <p>创建时间：${fn:substring(taskInfo.createTime, 0, 10) }</p>
                            </div>
                            <div class="fl wd218 mg-l48">
                                <p>计划完成时间：${fn:substring(taskInfo.expectEndTime, 0, 10) }</p>
                                <p>工作量：${taskInfo.taskWorkTime}</p>
                            </div>
                            <div class="fr mg-t12">
                            	<%-- <c:if test="${preFalg eq 1}">
				            			<a href="javascript:(void);" class="btn-small1 fl popup-btn1" onmouseover="javascript:initParam('${taskInfo.id}', '${taskInfo.taskContent }', '${fn:substring(taskInfo.expectEndTime, 0, 10) }', 2, 3,'${taskInfo.tstatus }', '${taskInfo.is_deliver}','${taskInfo.taskWorkTime}','${taskInfo.projectName}','${taskInfo.projectCode}','${taskInfo.projectStage}','${taskInfo.projectStageCode}');">分配</a>
				            	</c:if>
				            	<c:if test="${preFalg eq 2}"> --%>
				            		<a href="javascript:(void);" class="btn-small1 fl popup-btn1" onmouseover="javascript:initParam('${taskInfo.id}','${taskInfo.taskname}', '${taskInfo.taskContent }', '${fn:substring(taskInfo.expectEndTime, 0, 10) }', 2, 3,'${taskInfo.tstatus }', '${taskInfo.is_deliver}','${taskInfo.taskWorkTime}','${taskInfo.projectName}','${taskInfo.projectCode}','${taskInfo.projectStage}');">分配</a>
								<a href="javascript:(void);" class="btn-small2 fl mg-l5" onclick="javascript:deleteTask('${taskInfo.id}',1);">删除</a>
                            </div>
                        </li>
                        </c:if>
                        </c:forEach>
                    </ul>
                    </div>
                        <div align="right" >
					    	<div class="pagesDiv">
					    	 共<fmt:parseNumber value="${(pclist.total%ps==0 ? pclist.total/ps : (pclist.total/ps)+1)}" integerOnly="true" />页&nbsp;|&nbsp;共${pclist.total}条记录&nbsp;
						    	<pg:pager url="${ctx}/admin/myWorkbenchList.do" items="${pclist.total}" maxPageItems="${ps}" export="page,currentPageNumber=pageNumber">
						             	<pg:param name="pqFlag" value="true"/>
						             	<input type="hidden" name="allTotal" id="allTotal" value="${pclist.total }"><!-- 删除任务 -->
									<pg:first><a href="${pageUrl}&taskstatus=${flag}">[首页]</a></pg:first>
									<pg:prev><a href="${pageUrl}&taskstatus=${flag}">[上一页]</a></pg:prev>
									<pg:pages>
										<c:choose>
											<c:when test="${currentPageNumber eq pageNumber }">
												<span class="current">${pageNumber}</span>
												<input type="hidden" name="currentPageNumber" id="currentPageNumber" value="${currentPageNumber }">
											</c:when>
											<c:otherwise>
												<a href="${pageUrl }&taskstatus=${flag}">${pageNumber }</a>
											</c:otherwise>
										</c:choose>
									</pg:pages>
									<pg:next><a href="${pageUrl }&taskstatus=${flag}">[下一页]</a></pg:next>
									<pg:last><a href="${pageUrl }&taskstatus=${flag}">[尾页]</a></pg:last>
								</pg:pager>
							</div>
			  			</div>
                    </div>
                    <!------------------ 待分配 end ------------------>
                    <!------------------ 待接收 start ------------------>
                    <div id="tbc3_02" class="hide">
                    <div style="height: 380px;overflow: auto;">
                        <ul>
                    	<c:forEach items="${pclist.datas}" var="taskInfo" varStatus="status">
                        <c:if test="${taskInfo.taskstatus==2 }">
                        <li class="fn-clear line">
                            <div class="fl wd210">
                                <p title="${taskInfo.taskContent }">${fn:substring(taskInfo.taskContent, 0, 15)}
                                <c:if test="${fn:length(taskInfo.taskContent) > 15}">...</c:if></p>
                                <p>分配时间：${fn:substring(taskInfo.yfpDate, 0, 10) }</p>
                            </div>
                            <div class="fl wd188">
                                <p>计划完成时间：${fn:substring(taskInfo.expectEndTime, 0, 10) }</p>
                                <p>创建人：${taskInfo.create_name }</p>
                            </div>
                            <div class="fl wd108">
                                <p>工作量：${taskInfo.taskWorkTime}</p>
                                <p>&nbsp;</p>
                            </div>
                            <div class="fr mg-t12">
                                <a href="javascript:(void);" class="btn-small1 fl popup-btn3" onmouseover="javascript:initParam('${taskInfo.id}',null,null, '${fn:substring(taskInfo.expectEndTime, 0, 10) }', 3, 4, null,'${taskInfo.is_deliver}'),'${taskInfo.taskWorkTime}';">接收</a>
                                <a href="javascript:(void);" class="btn-small2 fl mg-l5" onclick="javascript:refuse1('${taskInfo.id}');">拒绝</a>
                            </div>
                        </li>
                        </c:if>
                        </c:forEach>
                    </ul>
                    </div>
                        <div align="right">
					    	<div class="pagesDiv">
					    	 共<fmt:parseNumber value="${(pclist.total%ps==0 ? pclist.total/ps : (pclist.total/ps)+1)}" integerOnly="true" />页&nbsp;|&nbsp;共${pclist.total}条记录&nbsp;
						    	<pg:pager url="${ctx}/admin/myWorkbenchList.do" items="${pclist.total}" maxPageItems="${ps}" export="page,currentPageNumber=pageNumber">
						             	<pg:param name="pqFlag" value="true"/>
									<pg:first><a href="${pageUrl}&taskstatus=${flag}">[首页]</a></pg:first>
									<pg:prev><a href="${pageUrl}&taskstatus=${flag}">[上一页]</a></pg:prev>
									<pg:pages>
										<c:choose>
											<c:when test="${currentPageNumber eq pageNumber }">
												<span class="current">${pageNumber}</span>
											</c:when>
											<c:otherwise>
												<a href="${pageUrl }&taskstatus=${flag}">${pageNumber }</a>
											</c:otherwise>
										</c:choose>
									</pg:pages>
									<pg:next><a href="${pageUrl }&taskstatus=${flag}">[下一页]</a></pg:next>
									<pg:last><a href="${pageUrl }&taskstatus=${flag}">[尾页]</a></pg:last>
								</pg:pager>
							</div>
			  			</div>
                    </div>
                    <!------------------ 待接收 end ------------------>
                    <!------------------ 待确认 start ------------------>
                    <div id="tbc3_03" class="hide">
                    <div style="height: 380px;overflow: auto;">
                        <ul>
                    	<c:forEach items="${pclist.datas}" var="taskInfo" varStatus="status">
                        <c:if test="${taskInfo.taskstatus==5 || taskInfo.taskstatus==6 || taskInfo.taskstatus==7 }">
                        <li class="fn-clear line">
                            <div class="fl wd210">
                                <p title="${taskInfo.taskContent }">${fn:substring(taskInfo.taskContent, 0, 15)}
                                <c:if test="${fn:length(taskInfo.taskContent) > 15}">...</c:if></p>
                                <p>提交时间：${fn:substring(taskInfo.subDate, 0, 10) }</p>
                            </div>
                            <div class="fl wd188">
                                <p>计划完成时间：${fn:substring(taskInfo.expectEndTime, 0, 10) }</p>
                                <p>负责人：${taskInfo.exec_name }</p>
                            </div>
                            <div class="fl wd108">
                                <p>工作量：${taskInfo.taskWorkTime}</p>
                                <p>&nbsp;</p>
                            </div>
                            <div class="fr mg-t12">
                            	<a href="javascript:(void);" class="btn-small1 fl popup-btn2" onmouseover="javascript:initParam1({'id':'${taskInfo.id}','expectEndTime':'${taskInfo.expectEndTime}','jxzDate':'${taskInfo.jxzDate}','subDate':'${taskInfo.subDate}','yfpDate':'${taskInfo.yfpDate}','flagA':'5','remark':'${taskInfo.remark}','taskWorkTime':'${taskInfo.taskWorkTime}'});">确认</a>
<%--                                 <a href="javascript:(void);" class="btn-small2 fl mg-l5" onclick="javascript:rejectFun('${taskInfo.id}', 3, 2);">拒绝</a> --%>
                                <a href="javascript:(void);" class="btn-small2 fl mg-l5" onclick="javascript:refuse2('${taskInfo.id}');">拒绝</a>
                            </div>
                            
                        </li>
                        </c:if>
                        </c:forEach>
                    </ul>
                    </div>
                        <div align="right">
					    	<div class="pagesDiv">
					    	 共<fmt:parseNumber value="${(pclist.total%ps==0 ? pclist.total/ps : (pclist.total/ps)+1)}" integerOnly="true" />页&nbsp;|&nbsp;共${pclist.total}条记录&nbsp;
						    	<pg:pager url="${ctx}/admin/myWorkbenchList.do" items="${pclist.total}" maxPageItems="${ps}" export="page,currentPageNumber=pageNumber">
						             	<pg:param name="pqFlag" value="true"/>
									<pg:first><a href="${pageUrl}&taskstatus=${flag}">[首页]</a></pg:first>
									<pg:prev><a href="${pageUrl}&taskstatus=${flag}">[上一页]</a></pg:prev>
									<pg:pages>
										<c:choose>
											<c:when test="${currentPageNumber eq pageNumber }">
												<span class="current">${pageNumber}</span>
											</c:when>
											<c:otherwise>
												<a href="${pageUrl }&taskstatus=${flag}">${pageNumber }</a>
											</c:otherwise>
										</c:choose>
									</pg:pages>
									<pg:next><a href="${pageUrl }&taskstatus=${flag}">[下一页]</a></pg:next>
									<pg:last><a href="${pageUrl }&taskstatus=${flag}">[尾页]</a></pg:last>
								</pg:pager>
							</div>
			  			</div>
                    </div>
                	<!------------------ 待确认 end ------------------>
                    <!------------------ 待通过 start ------------------>
                    <div id="tbc3_04" class="hide">
                    <div style="height: 380px;overflow: auto;">
                        <ul>
                    	<c:forEach items="${pclist.datas}" var="taskInfo" varStatus="status">
                        <c:if test="${taskInfo.taskstatus=='0' && taskInfo.is_pass=='0' }">
                        <li class="fn-clear line">
                            <div class="fl wd250">
                                <p title="${taskInfo.taskContent }">${fn:substring(taskInfo.taskContent, 0, 15)}
                                <c:if test="${fn:length(taskInfo.taskContent) > 15}">...</c:if></p>
                                <p>转交时间：${fn:substring(taskInfo.deliver_time, 0, 10) }</p>
                            </div>
                            <div class="fl wd218 mg-l48">
                                <p>转交人：${taskInfo.deliverPersonZh }</p>
                                <p>转交负责人：${taskInfo.exec_name }</p>
                            </div>
                            <div class="fr mg-t12">
                            	<a href="javascript:(void);" class="btn-small1 fl popup-btn4" onmouseover="javascript:initParam2('${taskInfo.id}', 6);">通过</a>
                                <a href="javascript:(void);" class="btn-small2 fl mg-l5" onclick="javascript:deleteTask('${taskInfo.id}',4);">删除</a>
                            </div>
                        </li>
                        </c:if>
                        </c:forEach>
                    </ul>
                    </div>
                        <div align="right">
					    	<div class="pagesDiv">
					    	 共<fmt:parseNumber value="${(pclist.total%ps==0 ? pclist.total/ps : (pclist.total/ps)+1)}" integerOnly="true" />页&nbsp;|&nbsp;共${pclist.total}条记录&nbsp;
						    	<pg:pager url="${ctx}/admin/myWorkbenchList.do" items="${pclist.total}" maxPageItems="${ps}" export="page,currentPageNumber=pageNumber">
						             	<pg:param name="pqFlag" value="true"/>
									<pg:first><a href="${pageUrl}&taskstatus=0&flag=4">[首页]</a></pg:first>
									<pg:prev><a href="${pageUrl}&taskstatus=0&flag=4">[上一页]</a></pg:prev>
									<pg:pages>
										<c:choose>
											<c:when test="${currentPageNumber eq pageNumber }">
												<span class="current">${pageNumber}</span>
											</c:when>
											<c:otherwise>
												<a href="${pageUrl }&taskstatus=0&flag=4">${pageNumber }</a>
											</c:otherwise>
										</c:choose>
									</pg:pages>
									<pg:next><a href="${pageUrl }&taskstatus=0&flag=4">[下一页]</a></pg:next>
									<pg:last><a href="${pageUrl }&taskstatus=0&flag=4">[尾页]</a></pg:last>
								</pg:pager>
							</div>
			  			</div>
                    </div>
                	<!------------------ 待通过 end ------------------>
                </div>
            </div>

            <div class="wd308 fl bd-qh mg-l10">
                <div class="pdbm">
                    <h2 class="fz16" id="helloId">${username }</h2>
                    <script type="text/javascript">
                    
                    Date.prototype.format = function(format){ 
						  var o = { 
						  "M+" : this.getMonth()+1, //month 
						  "d+" : this.getDate(), //day 
						  "h+" : this.getHours(), //hour 
						  "m+" : this.getMinutes(), //minute 
						  "s+" : this.getSeconds(), //second 
						  "q+" : Math.floor((this.getMonth()+3)/3), //quarter 
						  "S" : this.getMilliseconds() //millisecond 
						  }
				
						  if(/(y+)/.test(format)) { 
						  format = format.replace(RegExp.$1, (this.getFullYear()+"").substr(4 - RegExp.$1.length)); 
						  }
				
						  for(var k in o) { 
						  if(new RegExp("("+ k +")").test(format)) { 
						  format = format.replace(RegExp.$1, RegExp.$1.length==1 ? o[k] : ("00"+ o[k]).substr((""+ o[k]).length)); 
						  } 
						  } 
						  return format; 
						  }
                    
						$(document).ready(
						
							function(){
								var nowDate = new Date();
								var year = nowDate.getFullYear();
								var month = nowDate.getMonth()+1;
								var date = nowDate.getDate();
								var day = nowDate.getDay();
								var hours = nowDate.getHours();
								if(hours<8){
									var helloStr = $("#helloId").text()+"，早上好！";
									$("#helloId").text(helloStr);
								}else if(hours>=8 && hours<13){
									var helloStr = $("#helloId").text()+"，上午好！";
									$("#helloId").text(helloStr);
								}else if (hours>=13 && hours<19) {
									var helloStr = $("#helloId").text()+"，下午好！";
									$("#helloId").text(helloStr);
								}else if (hours>=19) {
									var helloStr = $("#helloId").text()+"，晚上好！";
									$("#helloId").text(helloStr);
								}
								var dayStr;
								switch(day){
								case 0:
								  dayStr="星期日";
								  break;
								case 1:
								  dayStr="星期一";
								  break;
								case 2:
								  dayStr="星期二";
								  break;
								case 3:
								  dayStr="星期三";
								  break;
								case 4:
								  dayStr="星期四";
								  break;
								case 5:
								  dayStr="星期五";
								  break;
								case 6:
								  dayStr="星期六";
								  break;
								default:
								}
					
								dayStr = "今天是"+dayStr+"，"+year+"年"+month+"月"+date+"日";
								var dayStr1 = nowDate.format("yyyy-MM");
								$("#timeId").html(dayStr);
								$("#imTime").val(dayStr1);
							}
							//$("#gvChartDiv1").html("");
						);
					function timeSelect(value){
						url = "${ctx}/admin/countScore.do";
						$.ajax({ 
							type : "POST",  
							url: url, 
							data : {"imTime":value},
							dataType : 'json',
							success: function(data){
								$(".totleScore").html("<span id='totleScore' style='font-size: 14px;'>"+data.totleScore+"</span>")
								$(".score1").html("<span>"+data.score1+"</span>")
								$(".score2").html("<span>"+data.score2+"</span>")
								$(".score3").html("<span>"+data.score3+"</span>")
								
								if(data.taskScore!=null && data.taskScore!=''){
									$("#taskScoreId").html(data.taskScore)
								}
								if(data.taskStatusScore!=null && data.taskStatusScore!=''){
									$("#taskStatusScoreId").html(data.taskStatusScore)
								}
								if(data.saturationScore!=null && data.saturationScore!=''){
									$("#saturationScoreId").html(data.saturationScore)
								}
								/* if(data.saturation!=null && data.saturation!=''){
									$("#saturationSpan").html(data.saturation)
								}else{
									$("#saturationSpan").html("无")
								}
								if(data.remark!=null && data.remark!=''){
									var remarkStr="<label style='font-size:11px;'>&nbsp;&nbsp;&nbsp;"+data.remark+"</label>";
									$(".totleScore").append(remarkStr);
								} */
							}
						});
					}
					
					</script>
                    <p class="mg-t10" id="timeId"></p>
                   
                    <shiro:hasPermission name="myWorkbench:finishTask">
                     <hr class="xustyle" style="color:#778899;height:1px;border:0px;border-top:1px dashed #778899;">
		                    <div class="scores-box mg-5 fn-clear">
		                    	<div class="fl" style="width:280px;">
		                    		<shiro:hasPermission name="myWorkbench:TDYG">
		                    		<table style="cellspacing:0px;">
		                    			<tr>
		                    				<td style="width:220px;"><font style="color:red;"><h2 class="fz16" id="helloId"><label class="fl txt-r mg-r5" >实时分数：</label><p class="totleScore"><span id="totleScore" style="font-size: 14px;">${totleScore }</span></p></h2></font></td>
		                    				<td align="left" style="font-size: 12px;"><a class="fr tc-link" href="javascript:(void);" style="float: left;">分数详情</a></td>
		                    			</tr>
		                    			<tr>
		                    				<td colspan="2" style="width:183px;"><input readonly="readonly" type="text" id="imTime" name="imTime"  onfocus="WdatePicker({onpicking:function(dp){ timeSelect(dp.cal.getNewDateStr()) },skin:'whyGreen',dateFmt:'yyyy-MM',maxDate:'%y-%M'})" class="Wdate" style="width:120px;border:0px;"></td>
		                    			</tr>
		                    		</table>
		                   			</shiro:hasPermission>
		                    		<%-- <font style="color:red;"><h2 class="fz16" id="helloId"><label class="fl txt-r mg-r5" >实时分数：</label><p class="totleScore"><span id="totleScore">${totleScore }</span></p></h2></font> --%>
			                    	
			                   			<%-- <p class="score1"><span id="score1">${score1 }</span></p>
			                    		<p class="score2"><span id="score2">${score2 }</span></p>
			                    		<p class="score3"><span id="score3">${score3 }</span></p> --%>
		                   		</div>
		                   		<shiro:hasPermission name="myWorkbench:BMJL">
		                   			<input readonly="readonly" type="text" id="imTime" name="imTime"  onfocus="WdatePicker({onpicking:function(dp){ timeSelect(dp.cal.getNewDateStr()) },skin:'whyGreen',dateFmt:'yyyy-MM',maxDate:'%y-%M'})" class="Wdate" style="width:120px;border:0px;">
		                   			<a class="fr tc-link2" href="javascript:(void);" >计算规则</a>
		                   			<table class="fl"  style="text-align:center;width:280px;font-size:13px;">
			                   			<tr >
				                   			<th style="width:48px;border:1px #dcdcdc solid" >总分</th>
				                   			<th style="width:88px;border:1px #dcdcdc solid">个人得分</th>
				                   			<th style="width:88px;border:1px #dcdcdc solid">部门得分</th>
				                   			<th style="width:106px;border:1px #dcdcdc solid">饱和度得分</th></tr>
			                   			<tr >
			                   				<td class="totleScore" style="border:1px #dcdcdc solid;">${totleScore }</td>
				                   			<td class="score1" style="border:1px #dcdcdc solid;">${score1 }</td>
				                   			<td class="score2" style="border:1px #dcdcdc solid;">${score3 }</td>
				                   			<td class="score3" style="border:1px #dcdcdc solid;">${score2 }</td>
			                   			</tr>
			                   		</table>
		                   		</shiro:hasPermission>
		                   </div>
	                   		<p style="height:5px;"></p>
	                   <hr class="xustyle" style="color:#778899;height:1px;border:0px;border-top:1px dashed #778899;">
                    	<p class="mg-t5">您最近一个月完成${finishCount }个任务：</p>
                    </shiro:hasPermission>
                    <shiro:hasPermission name="myWorkbench:showBingTu">
                    <div class="mg-t10">
                        <div style="width:200px;margin-left:-10px;">
                                <%-- <c:choose >
	                                <c:when test="${userRole=='staff' || userRole=='director' || userRole=='SUPER' }"> --%>
                           		<table id='myTable1'>
                                	<thead>
	                                    <tr>
	                                        <th></th>
	                                        <th>延期完成</th>
	                                        <th>按时完成</th>
	                                        <th>提前完成</th>
	                                    </tr>
	                                </thead>
	                                <tbody>
	                                    <c:choose>
	                                    	<c:when test="${count3==0 && count4==0 && count5==0 }">
	                                    		<tr>
			                                        <td>100</td>
			                                        <td>100</td>
			                                        <td>100</td>
			                                    </tr>
	                                    	</c:when>
		                                    <c:otherwise>
			                                    <tr>
			                                        <td>${count3 }</td>
			                                        <td>${count4 }</td>
			                                        <td>${count5 }</td>
			                                    </tr>
		                                    </c:otherwise>
	                                    </c:choose>
	                                </tbody>
	                            </table>
	                            <%--   </c:when>
                                </c:choose> --%>
                                
                            </table>
                            
                        </div>                
                    </div></shiro:hasPermission>
                    <shiro:hasPermission name="myWorkbench:noBingTu">
                    <div  class="mg-t10">
                    	<div style="width:200px;margin-left:-10px;">
                        	<table id='myTable1'>
                               	<thead>
                                    <tr>
                                        <th></th>
                                        <th>延期完成</th>
                                        <th>按时完成</th>
                                        <th>提前完成</th>
                                    </tr>
                                </thead>
                                <tbody>
                                    <tr>
                                        <td>0</td>
                                        <td>0</td>
                                        <td>0</td>
                                    </tr>
                                </tbody>
                            </table>
                        </div>    		
                    </div>
                    <script>
                    $(document).ready(
						function(){
                    	var gvChartDiv1 = $("#gvChartDiv1");
                    	if(gvChartDiv1!=null){
                    		gvChartDiv1.html("");
                    		gvChartDiv1.css("height","30px");
                    	}
                    });
                    </script>
                   </shiro:hasPermission>
                </div>
                <div class="rwjc mg-t5" style="height:40px;">
                    <ul class="fn-clear">
                        <shiro:hasPermission name="myWorkbench:staffModel">
                        <li class="fl txt-c wd50b bd-r">
                            <p class="green">${count1 }</p>
                            <p class="fz14">待接收</p>
                        </li>
                        <li class="fl txt-c wd50b" style="margin-left:-1px;">
                            <p class="red">${count2 }</p>
                            <p class="fz14">进行中</p>
                        </li>
                        </shiro:hasPermission>
                        <shiro:hasPermission name="myWorkbench:directorModel">
                        <li class="fl txt-c wd50b bd-r" style="margin-left:-1px;">
                            <p class="green">${count1 }</p>
                            <p class="fz14">待接收</p>
                        </li>
                        <li class="fl txt-c wd50b" style="margin-left:-1px;">
                            <p class="red">${count6 }</p>
                            <p class="fz14">待确认</p>
                        </li>
                        </shiro:hasPermission>
                        <shiro:hasPermission name="myWorkbench:bossModel">
                        <li class="fl txt-c wd50b bd-r" style="margin-left:-1px;">
                            <p class="green">${count2 }</p>
                            <p class="fz14">进行中</p>
                        </li>
                        <li class="fl txt-c wd50b" style="margin-left:-1px;">
                            <p class="red">${count6 }</p>
                            <p class="fz14">待确认</p>
                        </li>
                        </shiro:hasPermission>
                    </ul>
                </div>
                <shiro:hasPermission name="myWorkbench:createCount">
                <c:if test="${userRole=='director' || userRole=='SUPER' || userRole=='BMJL'  || userRole=='XMJL'  || userRole=='TDJL'  || userRole=='PMO001'  || userRole=='PMUPDATE'  }">
                <div class="rwjc">
                    <p class="fz14 pd-l20">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;我共创建了${creatCount }个任务</p>
                    
                </div>
                </c:if>
                <c:if test="${userRole=='boss' }">
                <div class="rwjc">
                    <p class="fz14 pd-l20" style="height:50px;vertical-align: middle;padding-buttom:10px">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;我共创建了${creatCount }个任务</p>
                </div>
                </c:if>
                </shiro:hasPermission>
                <c:if test="${userRole=='staff'}">
                <div class="rwjc">
                    <p class="fz14 pd-l20">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</p>
                </div>
                </c:if>
                <c:if test="${userRole=='boss' }">
                <div class="rwjc" style="height:145px;"></div>
                </c:if>
            </div>
        </div>
    </div>
   
    <div class="popup score-box" style="margin-left:-434px;top: 15%;">
    	<div class="tc-box" style="width:480px;">
    		<div class="title fn-clear">
         			<span>分数详情</span>
         			<a href="javascript:(void);" class="tc-close fr"></a>
         	</div>
         	<div style="height:360px;overflow-y:auto;">
	         	<table>
	         		<tr>
	         			<td width="120px"><strong>任务打分：</strong><label id="taskScoreId" style="font-size: 14px;">${taskScore }</label></td>
	         			<td width="135px"><strong>任务状态分：</strong><label id="taskStatusScoreId" style="font-size: 14px;">${taskStatusScore }</label></td>
	         			<td width="200px"><strong>饱和度值：</strong><label id="saturationScoreId" style="font-size: 14px;">${saturationScore }</label></td>
	         		</tr>
	         	</table>
	         	<p><font color="red">KPI值=任务打分（均值）+任务状态分+饱和度值</font></p>
	         	<p>KPI取值范围（75≤KPI≤110）</p>
	         	<p>不合格分数（75≤KPI＜95）合格分数（95≤KPI≤105）优秀分（105＜KPI≤110）</p>
	         	<p><font color="red">1)任务打分：</font>任务提交成功后任务发起人对任务完成情况所打的分数。</p>
	         	<p>任务打分均值=任务打分总数值/任务数  </p>
	         	<p>任务打分的取值范围为（10≤任务打分≤20）</p>
	         	<p>分值配比：根据任务完成进度与质量情况进行打分。</p>
	         	<p>差：10≤任务打分＜15；    一般：任务打分=15；   良 好：15＜任务打分≤20 </p>
	         	<p><font color="red">2)任务状态分：</font>任务状态分是根据任务完成情况确定的分值。</p>
	         	<p>任务完成状态分为三种，即延期完成、按时完成、提前完成。</p>
	         	<p>分值配比为：延期完成=40分；     按时完成=45分；     提前完成=50分</p>
	         	<p>任务状态分=（40*延期完成项目数+45*按时完成项目数+50*提前完成项目数）/（延期 完成项目数+按时完成项目数+提前完成项目数）</p>
	         	<p><font color="red">3)饱和度值：</font>饱和度值的目的是所完成的工作量是否达到公司规定的基本标准，即每天的工作量是否达到公司规定的8小时工作时间。</p>
	         	<p>工作总工时=∑任务计划完成工作量。</p>
	         	<p>饱和度值得取值范围（25≤工作量对比分≤40）；</p>
	         	<p>饱和度值={工作总工时/（实际出勤天数*8）}*35；</p>
	         	<p>当分值小于25时，取数值25作为饱和度值；</p>
	         	<p>当分值大于40时，取数值40作为饱和度值。</p>
	         	<p>4)分数计算周期为一个月，从每月1号开始到月末为一个计算周期。</p>
	       		<!-- <p>1)<font color="blue">总得分</font>=【基础分（60分）+任务得分率（10分）+任务完成状态分（15分）+工作效率分（10分）】*饱和度;</p>
	       		<p>2)<font color="blue">任务得分率</font>=【已完成任务的总得分/（已完成任务数量*8）】*10;</p>
	       		<p>3)<font color="blue">任务完成状态分</font>=（提前完成任务数*1.5+按时完成任务数*1+延期完成任务数*0.5）/完成任务总数量*15;</p>
	       		<p>4)<font color="blue">工作效率分</font>=10;</p>
	       		<p>5)<font color="blue">工作量饱和度值</font>=该员工所有任务工作量总和 /（该员工实际出勤天数× 8）*100%；（饱和度值计算至当前时间的前一天）。</p>
	       		<p>6)初始分数为0分（当本月从一号开始后到第一个任务完成前，默认得分为初始分）;</p>
	       		<p>7)分数计算周期为一个月，从每月1号开始到月末为一个计算周期。下一个周期分数从标准分数开始重新计算。</p>
	       		<p>8)未调整分数=完成任务所得分，没有乘以饱和度。</p> -->
	         	<div class="txt-c pd-t15">
	   				<a href="javascript:(void);" class="tc-btn2 mg-l15">关&nbsp;&nbsp;闭</a>
	    		</div>
         	</div>
    	</div>
    </div>
       <div class="popup score-box2" style="margin-left:-195px;">
    	<div class="tc-box">
    		<div class="title fn-clear">
         			<span>得分计算</span>
         			<a href="javascript:(void);" class="tc-close fr"></a>
         	</div>
         		<p>1)部门经理本人负责任务的整体打分=基础分（60分）+任务得分率（10分）+任务完成状态分（15分）+工作效率分（10分）;</p>
         		<p>2)任务得分率=【已完成任务的总得分/（已完成任务数量*8）】*10;</p>
         		<p>3)任务完成状态分=（提前完成任务数*1.5+按时完成任务数*1+延期完成任务数*0.5）/完成任务总数量*15;</p>
         		<p>4)工作效率分=10;</p>
         		<p>5)初始分数为0分（当本月从一号开始后到第一个任务完成前，默认得分为初始分）;</p>
         		<p>6)部门经理考核打分=部门经理本人负责任务的整体打分*30%+整个部门工作量饱和度百分比*100*30%+（部门所有任务中按时提交百分比*1+部门所有任务中提前提交百分比*1+部门所有任务中延迟提交百分比*0）*100*40%;</p>
         		<p>7)分数计算周期为一个月，从每月1号开始到月末为一个计算周期。下一个周期分数从标准分数开始重新计算。</p>
         	<div class="txt-c pd-t15">
   				<a href="javascript:(void);" class="tc-btn2 mg-l15">关&nbsp;&nbsp;闭</a>
    		</div>
    	</div>
    </div>

    <!-- 弹窗 -->
    <!-- 接收任务，不用选择期望时间 -->
    <div class="popup popup-box3" style="margin-left:-195px;">
        <div class="tc-box">
            <div class="title fn-clear">
                <span>提示</span>
                <a href="javascript:(void);" class="tc-close fr"></a>
            </div>

            <div class="txt-c pd-t25 pd-b25 fz16">确定接收任务？</div>
            <!-- <div class="txt-c pd-t10">计划完成时间：<input type="text"></div> -->
            <div class="txt-c pd-t20">
                <a href="javascript:(void);" class="tc-btn1">确&nbsp;&nbsp;认</a>
                <a href="javascript:(void);" class="tc-btn2 mg-l15">取&nbsp;&nbsp;消</a>
            </div>
        </div>
    </div>

    <!-- 任务提交后（项目经理）拒绝时添加拒绝原因 -->
    <div class="popup popup-box8" style="margin-left:-195px;">
        <div class="tc-box">
            <div class="title fn-clear">
                <span>拒绝原因</span>
                <a href="javascript:(void);" class="tc-close fr"></a>
            </div>
            <input type="hidden" id="id1" name="id1">
            <textarea name="refuseCause" id="refuseCause"  maxlength=300;  style="width:365px;margin-left:8px;resize: none;"></textarea>
            <div class="txt-c pd-t15">
                <a href="javascript:(void);" class="tc-btn8">确&nbsp;&nbsp;认</a>
                <a href="javascript:(void);" class="tc-btn2 mg-l15">取&nbsp;&nbsp;消</a>
            </div>
        </div>
    </div>
    
    <!-- 任务接收时（项目经理、员工及以下）拒绝时添加拒绝原因 -->
    <div class="popup popup-box9" style="margin-left:-195px;">
        <div class="tc-box">
            <div class="title fn-clear">
                <span>拒绝原因</span>
                <a href="javascript:(void);" class="tc-close fr"></a>
            </div>
            <input type="hidden" id="id2" name="id2">
            <textarea name="refuseCause1" id="refuseCause1"  maxlength=300;  style="width:365px;margin-left:8px;resize: none;"></textarea>
            <div class="txt-c pd-t15">
                <a href="javascript:(void);" class="tc-btn9">确&nbsp;&nbsp;认</a>
                <a href="javascript:(void);" class="tc-btn2 mg-l15">取&nbsp;&nbsp;消</a>
            </div>
        </div>
    </div>
    
    
    <!-- 接收转交任务，需要选择期望完成时间 -->
   <div class="popup popup-box5" style="margin-left:-195px;">
        <div class="tc-box">
            <div class="title fn-clear">
                <span>确定接收任务？</span>
                <a href="javascript:(void);" class="tc-close fr"></a>
            </div>
			<ul class="pd-t5">
			<li class="mg-t10"></li>
                <li class="fn-clear">
                    <label class="fl txt-r mg-r10"><font color="red">*</font>完成时间</label><!-- <p id="expectEndTime"></p> -->
                    <input readonly="readonly" type="text" id="expectEndTime2" name="expectEndTime2" onclick="WdatePicker({dateFmt:'yyyy-MM-dd',minDate:'%y-%M-%d'})" onfocus="WdatePicker({dateFmt:'yyyy-MM-dd',minDate:'%y-%M-%d'})" class="Wdate">
                </li>
                <li class="fn-clear">
                    <label class="fl txt-r mg-r10"><font color="red">*</font>工作量</label>
                    <input type="text" id="taskWorkTime2" name="taskWorkTime2" oninput="workCheck(this.value)">&nbsp;人时&nbsp;&nbsp;
                    <a class="discribtion2">工作量单位说明</a>
                </li>
                <li class="fn-clear" ><span id="discrib2" style="display:none;"><p><b>工作量人时:</b>指1个人在没有任何干扰的情况下，完成该项任务所需花费的小时数，也称为理想人时。</p></span></li>
            </ul>
            <div class="txt-c pd-t20">
                <a href="javascript:(void);" class="tc-btn3">确&nbsp;&nbsp;认</a>
                <a href="javascript:(void);" class="tc-btn2 mg-l15">取&nbsp;&nbsp;消</a>
            </div>
        </div>
    </div>

	<!-- 分配任务 -->
	<!-- 正常分配 -->
    <div class="popup popup-box1" style="margin-left:-195px;margin-top:-65px;">
        <div class="tc-box">
            <div class="title fn-clear">
                <span>分配任务</span>
                <a href="javascript:(void);" class="tc-close fr"></a>
            </div>

            <ul class="pd-t5">
                <li class="fn-clear" >
                    <label class="fl txt-r mg-r10" >任务标题</label>
                    <input style="width: 120px;height: 21px;border:1px #dcdcdc solid;" type="text" id="taskname" name="taskname" value="${taskname }"/>
                    <span id="tasknameText" style="display:none;"><font color="red">任务标题不能为空</font></span>
                </li>
                <li class="mg-t10">
                    <label class="fl txt-r mg-r10" >任务内容</label>
                    <textarea class="wbqy" style="width:230px;resize: none;" 
                        maxlength=300; placeholder="最多输入300个中文字符" cols="" id="taskContent" name="taskContent" value="${taskContent}" onblur="validateXml2(this)" onkeydown="disableBackspace();" onkeyup="disableBackspace();"></textarea>
                </li>
                <li class="fn-clear">
                    <label class="fl txt-r mg-r10"><font color="red">*</font>完成时间</label><!-- <p id="expectEndTime"></p> -->
                    <input style="width:120px;" readonly="readonly" type="text" id="expectEndTime" name="expectEndTime" onclick="WdatePicker({dateFmt:'yyyy-MM-dd',minDate:'%y-%M-%d'})" onfocus="WdatePicker({dateFmt:'yyyy-MM-dd',minDate:'%y-%M-%d'})" class="Wdate">
                    <!-- <input name="" type="text" class="srk fl"> -->
                </li>
                <li class="fn-clear">
                    <label class="fl txt-r mg-r10"><font color="red">*</font>工作量</label>
                    <input type="text" id="taskWorkTime" name="taskWorkTime" oninput="workCheck(this.value)">&nbsp;人时&nbsp;&nbsp;
                    <a class="discribtion">工作量单位说明</a>
                </li>
				<c:if test="${preFalg eq 2}">
				<div id="project">
	                 <li class="fn-clear" >
	                 	<label class="fl txt-r mg-r10" >项目名称</label>
	                 		<input class="inputC" readonly="readonly" id="proName" name="projectName" style="border: 1px solid #94afc8;width:120px;height: 22px;line-height: 22px;color:blue;text-indent: 8px;vertical-align: middle;border-radius: 3px;"
								 onkeydown="getProjectName(this.value)" onkeyup="getProjectName(this.value)" onblur="changeF(this.value);">
					 </li>
					  <li class="fn-clear" >
	                 	<label class="fl txt-r mg-r10" >项目编号</label>
							<input type="text" readonly="readonly" id="projectCode" name="projectCode" style="border:0px #dcdcdc solid;color:blue;">
	                 </li>
	                 <li class="fn-clear" >
	                        <label class="fl txt-r mg-r10" >项目阶段</label>
	                    	<select name="projectStage" id="projectStage" class="sel1">
	                    	<option value="" selected>---请选择---</option>
	                    		<c:forEach items="${peojectState}" var="dic" varStatus="status">
			                    		<option value="${dic.code}">${dic.name }</option>
			                    </c:forEach>
	                    	</select>
	                 </li>
                 </div>
				</c:if>
                
                <li class="fn-clear">
                    <label class="fl txt-r mg-r10"><font color="red">*</font>负责人</label>
                    <input class="srk1 fl" readonly="readonly" type="text" name="executedevtasksysName" id="executedevtasksysName" placeholder="请选择负责人">
                    <input type="hidden" name="executedevtasksys" id="executedevtasksys">
                    <input class="form_now1" type="button" onclick="selectSingleUser()" value="选择..">
                </li>
                <li class="fn-clear" ><span id="discrib" style="display:none;"><p><b>工作量人时:</b>指1个人在没有任何干扰的情况下，完成该项任务所需花费的小时数，也称为理想人时。</p></span></li>
            </ul>
            <div class="txt-c pd-t15">
                <a href="javascript:(void);" class="tc-btn1">确&nbsp;&nbsp;认</a>
                <a href="javascript:(void);" class="tc-btn2 mg-l15">取&nbsp;&nbsp;消</a>
            </div>
        </div>
    </div>
    <!-- 转交任务通过后拒绝接收，再次分配 -->
    <div class="popup popup-box6" style="margin-left:-195px;">
        <div class="tc-box">
            <div class="title fn-clear">
                <span>分配任务</span>
                <a href="javascript:(void);" class="tc-close fr"></a>
            </div>
            <ul class="pd-t5">
                <li class="mg-t10"><textarea name="taskContent3" id="taskContent3"  maxlength=300; onblur="validateXml2(this)"  class="wbqy" style="width:90%;margin-left:12px;resize: none;" onkeydown="disableBackspace();" onkeyup="disableBackspace();"></textarea></li>
                <li class="fn-clear">
                    <label class="fl txt-r mg-r10"><font color="red">*</font>完成时间</label>
                    <input readonly="readonly" type="text" id="expectEndTime3" name="expectEndTime3" onclick="WdatePicker({dateFmt:'yyyy-MM-dd',minDate:'%y-%M-%d'})" onfocus="WdatePicker({dateFmt:'yyyy-MM-dd',minDate:'%y-%M-%d'})" class="Wdate">
                    <!-- <input name="" type="text" class="srk fl"> -->
                </li>
                <li class="fn-clear">
                    <label class="fl txt-r mg-r10"><font color="red">*</font>工作量</label>
                    <input type="text" id="taskWorkTime3" name="taskWorkTime3" oninput="workCheck(this.value)">&nbsp;人时&nbsp;&nbsp;
                    <a class="discribtion3">工作量单位说明</a>
                
                <li class="fn-clear">
                    <label class="fl txt-r mg-r10"><font color="red">*</font>负责人</label>
                    <input class="srk1 fl"readonly="readonly" type="text" name="executedevtasksysName2" id="executedevtasksysName2" placeholder="请选择负责人">
                    <!-- <input type="hidden" name="executedevtasksys" id="executedevtasksys"> -->
                    <input class="form_now1" type="button" onclick="selectSingleUser()" value="选择..">
                </li>
                <li class="fn-clear" ><span id="discrib3" style="display:none;"><p><b>工作量人时:</b>指1个人在没有任何干扰的情况下，完成该项任务所需花费的小时数，也称为理想人时。</p></span></li>
            </ul>
            <div class="txt-c pd-t15">
                <a href="javascript:(void);" class="tc-btn4">确&nbsp;&nbsp;认</a>
                <a href="javascript:(void);" class="tc-btn2 mg-l15">取&nbsp;&nbsp;消</a>
            </div>
        </div>
    </div>
    
    <!-- 确认打分 -->
    <input type="hidden" name="expectEndTimeT" id="expectEndTimeT" value="${taskInfo.expectEndTime}">
    <input type="hidden" name="jxzDateT" id="jxzDateT" value="${taskInfo.jxzDate}">
    <input type="hidden" name="subDateT" id="subDateT" value="${taskInfo.subDate}">
    <input type="hidden" name="remark" id="remark" value="${taskInfo.remark}">
    <input type="hidden" name="taskWorkTimeX" id="taskWorkTimeX" value="${taskInfo.taskWorkTime}">
    <div class="popup popup-box2" style="margin-left:-195px;top: 10%;">
        <div class="tc-box">
            <div class="title fn-clear">
                <span>确认打分</span>
                <a href="javascript:(void);" class="tc-close fr"></a>
            </div>

            <ul class="pd-t5">
                <li class="fn-clear">
                    <label class="fl txt-r mg-r10">计划完成时间：</label>
                    <span id="sp1"></span>
                </li>
                <li class="fn-clear">
                    <label class="fl txt-r mg-r10">任务分配时间：</label>
                    <span id="sp2"></span>
                </li>
                <li class="fn-clear">
                    <label class="fl txt-r mg-r10">提交时间：</label>
                    <span id="sp3"></span>
                </li>
                <li class="fn-clear">
                    <label class="fl txt-r mg-r10">提交备注：</label>
                    <span class="sp4" id="sp4"></span>
                </li>
                <li class="fn-clear">
                    <label class="fl txt-r mg-r10">工作量：</label>
                    <span class="sp5" id="sp5"></span>
                </li>
                <li class="fn-clear pd-t15" style="border-top:1px #dcdcdc solid;">
                    <label class="fl txt-r mg-r10"><font color="red">*</font>任务打分：</label>
                    <!-- <input name="score" id="score" type="text" class="srk fl" style="width:130px;" onblur="validateScore(this.value);">（10分制）<a onclick="popBox7()">计算规则</a><br> -->
                    <input name="score" id="score" type="text" class="srk fl" style="width:130px;">（10~20分）<br>
                    <p style="margin-left: 35px;">打分标准：根据任务完成进度与质量情况进行打分</p>
					<p style="margin-left: 95px;">差&nbsp;&nbsp;:&nbsp;&nbsp; 10≤任务打分＜15</p>
					<p style="margin-left: 95px;">一般&nbsp;&nbsp;: 任务打分=15</p>
					<p style="margin-left: 95px;">良好&nbsp;&nbsp;: 15＜任务打分≤20</p>
                </li>
            </ul>
            <div class="txt-c pd-t15">
                <a href="javascript:(void);" class="tc-btn1">确&nbsp;&nbsp;认</a>
                <a href="javascript:(void);" class="tc-btn2 mg-l15">取&nbsp;&nbsp;消</a>
            </div>
        </div>
    </div>
    
    <!-- 打分计算规则 -->
    <div class="popup popup-box7" style="margin-left:-195px;">
        <div class="tc-box">
            <div class="title fn-clear">
                <span>打分计算规则</span>
                <a href="javascript:(void);" class="cancel-close fr"></a>
            </div>
            <p>1、如果（任务计划完成时间点—任务提交时间点）/（任务计划完成时间点—任务分配时间点）> 0，则说明任务提前完成，进度指标的建议分数为10分；</p>
            <p>2、如果（任务计划完成时间点—任务提交时间点）/（任务计划完成时间点—任务分配时间点）= 0，则说明任务按时完成，进度指标的建议分数为8分；</p>
            <p>3、如果 －20%≤（任务计划完成时间点—任务提交时间点）/（任务计划完成时间点—任务分配时间点）< 0，则说明任务完成有一定延期，进度指标的建议分数为6分；</p>
            <p>4、如果（任务计划完成时间点—任务提交时间点）/（任务计划完成时间点—任务分配时间点）＜ －20%，则说明任务完成严重延期，进度指标的建议分数为2分； </p>
            <div class="txt-c pd-t15">
                <!-- <a href="javascript:(void);" class="tc-btn4">确&nbsp;&nbsp;认</a> -->
                <a href="javascript:(void);" class="cancel-btn2 mg-l15">关&nbsp;&nbsp;闭</a>
            </div>
        </div>
    </div>
    
    <!-- 审核 -->
    <div class="popup popup-box4" style="margin-left:-195px;">
        <div class="tc-box">
            <div class="title fn-clear">
                <span>提示</span>
                <a href="javascript:(void);" class="tc-close fr"></a>
            </div>

            <div class="txt-c pd-t25 pd-b25 fz16">确定通过任务？</div>
            <div class="txt-c pd-t20">
                <a href="javascript:(void);" class="tc-btn1">确&nbsp;&nbsp;认</a>
                <a href="javascript:(void);" class="tc-btn2 mg-l15">取&nbsp;&nbsp;消</a>
            </div>
        </div>
    </div>
	</form>

    <div id="covered"></div>
    <script type="text/javascript">
	   $(".discribtion").hover(function(){
                       $('#discrib').show()
                      },function(){
                          $('#discrib').hide()
                         }) 
   	   $(".discribtion2").hover(function(){
                   $('#discrib2').show()
                  },function(){
                      $('#discrib2').hide()
                     }) 
   	   $(".discribtion3").hover(function(){
                  $('#discrib3').show()
                 },function(){
                     $('#discrib3').hide()
                    })                               
	   function workCheck(taskWorkTime){
	 			var strD = isNumericMath($.trim(taskWorkTime))
		    	if(!strD && $.trim(taskWorkTime) != "" && $.trim(taskWorkTime) != null){
		    		alert("请输入不大于40的工作量（可以保留一位小数）！");
		    		$("#taskWorkTime").val("");
		    		$("#taskWorkTime2").val("");
		    		$("#taskWorkTime3").val("");
		    		return false;
		}
	 }
        
        
    $('.tc-link').click(function(){
          $('.score-box').show()
    })
    $('.tc-link2').click(function(){
          $('.score-box2').show()
    })
    
    $(document).ready(function(){
	      //分配
	      $('.popup-btn1').click(function(){
	          var status = $("#status").val();
	          var tstatus = $("#tstatus").val();
	          if(status==2){
	         	//if(flagA==3){
	         	var expectEndTime = $("#expectEndTime").val();
	         	expectEndTime = expectEndTime.replace(/-/g,"/");
	            var taskWorkTime = $("#taskWorkTime").val();
				var nowDate = new Date();
				var year = nowDate.getFullYear();
				var month = nowDate.getMonth()+1;
				var date = nowDate.getDate();
				var nowDate2 = new Date(year+"/"+month+"/"+date).getTime();
				var expectDate = new Date(expectEndTime).getTime();
				if(nowDate2>expectDate){
					alert("该任务计划完成时间已过，不能进行分配，请新建项目！");
					return false;
				}
				if(tstatus=='1'){
					g_alert("warn","该任务已暂停，不能进行分配！",null,"${ctx}"); 
					return ;
				}else if (tstatus=='2') {
					g_alert("warn","该任务已终止，不能进行分配！",null,"${ctx}"); 
					return ;
				}
			  }
			
	        $('#covered').show();
	        var is_deliver = $("#is_deliver").val();
	        if(is_deliver!=null && is_deliver !='' && is_deliver=='1'){
	         	$('.popup-box1').hide();
	         	$('.popup-box6').show();
	        }else{
		        $('.popup-box6').hide();
		        $('.popup-box1').show();
	        }
	     })
       //确认
       $('.popup-btn2').click(function(){
         $('#covered').show()
         $('.popup-box2').show()
         $('.tc-btn1').focus();
       })
       //接收
       $('.popup-btn3').click(function(){
         $('#covered').show()
         var expectEndTime2 = $("#expectEndTime2").val();
         var taskWorkTime2 = $("#taskWorkTime2").val();
         var is_deliver = $("#is_deliver").val();
         if(is_deliver!=null && is_deliver !='' && is_deliver=='1'){
         	if(expectEndTime2==null || expectEndTime2=='' || expectEndTime2==undefined && taskWorkTime2==null || taskWorkTime2==''){
	         	$('.popup-box3').hide()
	         	$('.popup-box5').show()
         	}else{
         		$('.popup-box5').hide()
	         	$('.popup-box3').show()
         	}
         }else{
	         $('.popup-box5').hide()
	         $('.popup-box3').show()
         }
       })
       
     

        //审核
       $('.popup-btn4').click(function(){
         $('#covered').show()
         $('.popup-box4').show()
         $('.tc-btn1').focus();
       })
       
      $('.tc-close').click(function(){
          $('.popup-box1').hide()
          $('.popup-box2').hide()
          $('.popup-box3').hide()
          $('.popup-box4').hide()
          $('.popup-box5').hide()
          $('.popup-box6').hide()
          $('.popup-box7').hide()
          $('.popup-box8').hide()
          $('.popup-box9').hide()
          $('.score-box').hide()
          $('.score-box2').hide()
          $('#covered').hide()
          
          $("#proName").val("");
		  $("#projectCode").val("");
		  $("#projectStage").val("");
		  $("#score").val("");
        })
        

      $('.tc-btn1').click(function(){
          
         //alert($("#status").val());
         var status = $("#status").val();
          
      	if(status==2){
      		var taskContent = $("#taskContent").val();
         	var expectEndTime = $("#expectEndTime").val();
         	var taskWorkTime = $("#taskWorkTime").val();
         	//var projectName = $("#projectName").val();
         	var projectFalg = $("#projectFalg").val();
         	var	projectName = $("#proName").val();
	     	var	projectCode = $("#projectCode").val();
	     	var	projectStage = $("#projectStage").val();
         	
         	if(taskContent==null || taskContent==''){
         		g_alert("warn","任务内容不可以为空！",null,"${ctx}");
         		return;
         	}
         	if(expectEndTime==null || expectEndTime==''){
         		g_alert("warn","请选择期望完成时间！",null,"${ctx}");
         		//$('.popup-box1').show();
         		return;
         	}
         	if(taskWorkTime==null || taskWorkTime==''){
	         	g_alert("warn","工作量不为空！",null,"${ctx}"); 
				return ;
         	}
         	if(projectFalg!=null&&projectFalg!=""){
         		if(projectName == "" || projectName == null){
		  				g_alert("warn","项目名称不能为空！","${ctx}");
			    		return;
		     		}
		     		if(projectCode == "" || projectCode == null){
						g_alert("warn","没有此项目！！！","${ctx}");
			    		return;
					}
					if(projectStage == "" || projectStage == null){
						g_alert("warn","项目阶段不能为空！","${ctx}");
			    		return;
					}
         	}
         	/* if(projectName == "" || projectName == null){
	         	g_alert("warn","项目名称不可以为空！",null,"${ctx}")
	     		return ;
			  }
			if(projectStage == "" || projectStage == null){
		  		g_alert("warn","项目阶段不可以为空！",null,"${ctx}")
	     		return ;
		 	 } */
         	var id = $("#id").val();
         	var executedevtasksys = $("#executedevtasksys").val();
         	if(executedevtasksys==null || executedevtasksys==''){
         		g_alert("warn","请选择项目负责人！",null,"${ctx}")
         		$('.popup-box6').hide();
         		$('.popup-box1').show();
         		return;
         	}
         }else if (status==8) {
         	var id = $("#id").val();
			var score = $("#score").val();
         	if(score==null || score==''){
         		alert("请给该任务打分！");
         		$("#score").focus();
         		return;
         	}
         	if(score<10 || score>20){
         		alert("格式不符，打分在10-20之间的整数");
         		$("#score").focus();
         		return;
         	}
         	var reg = /^\d{1,2}$/;
			var result = reg.test(score);
			if(!result){
				alert("格式不符，打分在10-20之间的整数");
         		$("#score").focus();
				return;
			}
         	//return ;
		}else if(status==3){
         	var id = $("#id").val();
        }
    	 var flag = $("#flag").val();
    	 if(flag==4){
    	 	 $("#queryForm").attr("action","${ctx}/admin/checkTask.do");
    	 }else{
	         $("#queryForm").attr("action","${ctx}/admin/updateMyWorkbench.do");
    	 }
		 $("#queryForm").attr("method","POST");
		 $("#queryForm").submit();
		 var flagA = $("#flagA").val();
		 if(flagA==6){
		 	var id = $("#id").val();
		 	messageIgnore(id,7);
		 }
		 if(status==3){
		 	messageIgnore(id,1);
		 }else if(status==2){
		 	messageIgnore(id,5);
		 }else if(status==8){
		 	messageIgnore(id,3);
		 }
		 
		 $('.popup-box1').hide()
         $('.popup-box2').hide()
         $('.popup-box3').hide()
         $('.popup-box4').hide()
         $('.popup-box5').hide()
         $('.popup-box6').hide()
         $('#covered').hide()
      })

      $('.tc-btn2').click(function(){
          $('.popup-box1').hide()
          $('.popup-box2').hide()
          $('.popup-box3').hide()
          $('.popup-box4').hide()
          $('.popup-box5').hide()
          $('.popup-box6').hide()
          $('.popup-box7').hide()
          $('.popup-box8').hide()
          $('.popup-box9').hide()
          $('.score-box').hide()
          $('.score-box2').hide()
          $('#covered').hide()
          
          $("#proName").val("");
          $("#projectCode").val("");
          $("#projectStage").val("");
          $("#score").val("");
       })
        
       $('.tc-btn3').click(function(){
         var expectEndTime = $("#expectEndTime2").val();
         var taskWorkTime = $("#taskWorkTime2").val();
         if(expectEndTime==null || expectEndTime==''){
         	g_alert("warn","请选择期望完成时间！",null,"${ctx}"); 
			return ;
         }else if(taskWorkTime==null || taskWorkTime==''){
         	g_alert("warn","工作量不为空！",null,"${ctx}"); 
			return ;
         }else{
		 $("#queryForm").attr("action","${ctx}/admin/updateMyWorkbench.do?status=3&expectEndTime="+expectEndTime+ "&taskWorkTime="+taskWorkTime);
		 $("#queryForm").attr("method","POST");
		 $("#queryForm").submit();
         $('.popup-box1').hide()
         $('.popup-box2').hide()
         $('.popup-box3').hide()
         $('.popup-box4').hide()
         $('.popup-box5').hide()
         $('.popup-box6').hide()
         $('.score-box').hide()
         $('.score-box2').hide()
         $('#covered').hide()
         }
       })
        
       $('.tc-btn4').click(function(){
       var taskContent = $("#taskContent3").val();
         var expectEndTime = $("#expectEndTime3").val();
          var taskWorkTime = $("#taskWorkTime3").val();
         if(taskContent==null || taskContent==''){
         	g_alert("warn","任务内容不可以为空！",null,"${ctx}"); 
			return ;
         }
         if(expectEndTime==null || expectEndTime==''){
         	g_alert("warn","请选择期望完成时间！",null,"${ctx}"); 
			return ;
         }
         if(taskWorkTime==null || taskWorkTime==''){
         	g_alert("warn","工作量不可为空！",null,"${ctx}"); 
			return ;
         }
        var id = $("#id").val();
       	var executedevtasksys = $("#executedevtasksys").val();
       	if(executedevtasksys==null || executedevtasksys==''){
       		alert("请选择项目负责人！");
       		return;
       	}
		 $("#queryForm").attr("action","${ctx}/admin/updateMyWorkbench.do?expectEndTime="+expectEndTime+"&taskWorkTime="+taskWorkTime+"&taskContent="+taskContent);
		 $("#queryForm").attr("method","POST");
		 $("#queryForm").submit();
		 messageIgnore(id,5);
         $('.popup-box1').hide()
         $('.popup-box2').hide()
         $('.popup-box3').hide()
         $('.popup-box4').hide()
         $('.popup-box5').hide()
         $('.popup-box6').hide()
         $('.score-box').hide()
         $('.score-box2').hide()
         $('#covered').hide()
       })
       
    });
    
    function validateScore(value){
    	var reg = /^\d{1,2}$/;
		var result = reg.test(value);
		if(!result || value<10 || value>20){
			alert("格式不符，打分在10-20之间的整数");
         	$("#score").focus();
			return;
		}
    	
    }
    
  //任务提交后（项目经理）拒绝时添加拒绝原因任务提交后（项目经理）拒绝时添加拒绝原因
    function refuse1(id){
       $("#id1").val(id);
       $('#covered').show()
       $('.popup-box8').show()
       $('.tc-btn1').focus();
     }
  
    $('.tc-btn8').click(function(){
        var refuseCause = $("#refuseCause").val();//拒绝原因
        var id = $("#id1").val();
        $("#queryForm").attr("action","${ctx}/admin/updateMyWorkbench.do?id="+id+"&status=1&flagA=1&refuseCause=" +refuseCause);
        $("#queryForm").attr("method","POST");
        $("#queryForm").submit();
        $('.popup-box1').hide()
        $('.popup-box2').hide()
        $('.popup-box3').hide()
        $('.popup-box4').hide()
        $('.popup-box5').hide()
        $('.popup-box6').hide()
        $('.popup-box8').hide()
        $('.score-box').hide()
        $('.score-box2').hide()
        $('#covered').hide()
        
      })
      
      //任务接收时（项目经理、员工及以下）拒绝时添加拒绝原因
      function refuse2(id){
        $("#id2").val(id);
        $('#covered').show()
        $('.popup-box9').show()
        $('.tc-btn1').focus();
      }
   
     $('.tc-btn9').click(function(){
         var refuseCause = $("#refuseCause1").val();//拒绝原因
         var id = $("#id2").val();
         $("#queryForm").attr("action","${ctx}/admin/updateMyWorkbench.do?id="+id+"&status=3&flagA=2&refuseCause=" +refuseCause);
         $("#queryForm").attr("method","POST");
         $("#queryForm").submit();
         $('.popup-box1').hide()
         $('.popup-box2').hide()
         $('.popup-box3').hide()
         $('.popup-box4').hide()
         $('.popup-box5').hide()
         $('.popup-box6').hide()
         $('.popup-box8').hide()
         $('.score-box').hide()
         $('.score-box2').hide()
         $('#covered').hide()
         
       })
    
    function popBox7(){
    	$('#covered').show();
        $('.popup-box7').show();
    }
    $('.cancel-btn2').click(function(){
      $('.popup-box7').hide();
     /*  $('#covered').hide(); */
    })
    $('.cancel-close').click(function(){
      $('.popup-box7').hide();
     /*  $('#covered').hide(); */
    })
  </script>
  </body>
</html>
