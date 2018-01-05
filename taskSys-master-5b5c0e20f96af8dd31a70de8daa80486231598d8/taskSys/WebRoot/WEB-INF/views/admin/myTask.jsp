<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/common/taglibs.jsp" %>
<%@ page import="java.util.*" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.0 Transitional//EN"> 
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<%@ include file="/common/meta.jsp" %>

<title>用户列表</title>
<link href="${ctx}/css/static/base-global.css" rel="stylesheet" type="text/css" />
<link href="${ctx}/css/static/common.css" rel="stylesheet" type="text/css" />
<link href="${ctx}/css/static/main.css" rel="stylesheet" type="text/css" />
<link href="${ctx}/css/ystep.css?1=1" rel="stylesheet" type="text/css" />
<link rel="stylesheet" type="text/css" href="${ctx}/css/common.css" />
<link rel="stylesheet" type="text/css" href="${ctx}/css/right.css" />
<link rel="stylesheet" type="text/css" href="${ctx}/css/pop.css" />
<script type="text/javascript" src="${ctx}/js/util/common.js?1=1"></script>

<script type="text/javascript" src="${ctx}/js/My97DatePicker/WdatePicker.js" ></script>
<script type="text/javascript" src="${ctx}/js/static/jquery/jquery.min.js"></script>
<script type="text/javascript" src="${ctx}/js/ystep.js?1=1"></script>
<script type="text/javascript" src="${ctx}/js/alert/g_alert.js?1=1"></script>
<script type="text/javascript" src="${ctx}/js/sysUtil.js"></script>

<link rel="stylesheet" type="text/css" href="${ctx}/components/colorbox/colorbox.css"/>
<script language="javascript" src="${ctx}/components/colorbox/jquery.colorbox.js"></script>

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
	$(document).ready(function(){
	   // 左边随右边高度变化而变化
		function initSideBar() {
			var o1 = document.getElementById("appwrap");
			var o2 = document.getElementById("mainarea");
			var maxh1 = o1.offsetHeight;
			var maxh2 = o2.offsetHeight;
			if(maxh1>maxh2)
				o2.style.height = maxh1 + "px";
			else
				o1.style.height = maxh2 + "px";
			}
			window.onload=initSideBar;
			
			//tab标签
			var n = $("#flag").val();
			if(n!=null && n!=''){
				for(var i=1;i<=n;i++){
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
	$(document).ready(function(){
	   //鼠标移入移出效果
		$('.jc1').mouseenter(function(){
			$(this).find('.cjts').show()
		}).mouseleave(function(){
			$(this).find('.cjts').hide()
		})
	});
</script>
<script type="text/javascript">
  
    /*滑动门*/
    function g(o){return document.getElementById(o);}
	  function HoverLi3(n,flag){
	  
	        //如果有N个标签,就将i<=N;
	        for(var i=1;i<=flag;i++){
	        	if(g('tb3_'+i)!=null){
		    		g('tb3_'+i).className='fl';
		    		}
		    	g('tbc3_0'+i).className='hide';
		    	}
	      		g('tbc3_0'+n).className='';
			    g('tb3_'+n).className='fl cur';
			    $("#queryForm").action="${ctx}/director/myTaskBoss.do";
			   //location.href="${ctx}/director/myTaskBoss.do?flag="+flag;
			   	$("#flag").val(flag);
				$("#queryForm").submit();
	    	
	    	}
	
    /*点击展开.隐藏*/
    function changeMc(id){
      var content = document.getElementById(id);
      if(content != null) {
        if(content.style.display == "none") {
          content.style.display = "block";
        }
        else if(content.style.display == "block")
          content.style.display = "none";
      }
    }
    
     function selectSingleUser(){
	    var url= "${ctx}/admin/userList.do?isFenpei=1";
	    $.colorbox({
	        href:url,
	        iframe:true,
	        width:"680",
	        height:"350"
	    });
	}
	function selectSingleUser_callback(result){
	
		for (var i in result) {
			var tmp = result[i].split(",");
			$("#upexecutedevtasksys").val(tmp[0]);
			$("#executedevtasksysName").val(tmp[1]);
		}
	}
	
	//高管进行中任务详情
	function taskParticulars(id){
		var url = "${ctx}/director/taskParticulars.do?id="+id
		$.colorbox({
			href:url,
			iframe:true,
			width:"1100",
			height:"550"
		});
	}
	
	//高管 已完成 任务详情
	function taskParticularsForCompleted(id,flag){
		var url = "${ctx}/director/taskParticularsForCompleted.do?id="+id+"&flag="+flag;
		$.colorbox({
			href:url,
			iframe:true,
			width:"1100",
			height:"550"
		});
	}

  
</script>
</head>

<tags:message content="${message}" type="success" />
<body>
 	<div class="content" id="mainarea">
		<div class="title"><h2>我的任务</h2></div>
		<form id="queryForm" action="${ctx}/director/myTaskBoss.do" method="POST">
	<!-- 状态标识 -->
		<input type="hidden" id="projectFalg" name="projectFalg" />
		<input type="hidden" id="flag" name="flag" value="${flag}"/>
		<input type="hidden" id="nowDate" name="nowDate" value="${nowDate}"/>
		<div class="form-box fn-clear mg-t20">
			<div class="fl wd918">
				<div class="tab-box fn-clear">
					<shiro:hasPermission name="myEmpSTask:distribution">
						<a href="#"><span class="fl cur" id="tb3_1" onclick="x:HoverLi3(1,1);">未接收</span></a>
					</shiro:hasPermission>
					<shiro:hasPermission name="myEmpSTask:doing">
						<a href="#"><span class="fl" id="tb3_2" onclick="x:HoverLi3(2,2);">进行中</span></a>
					</shiro:hasPermission>
					<shiro:hasPermission name="myEmpSTask:commited">	
						<a href="#"><span class="fl" id="tb3_3" onclick="x:HoverLi3(3,3);">已提交</span></a>
					</shiro:hasPermission>
					<shiro:hasPermission name="myEmpSTask:complete">
						<a href="#"><span class="fl" id="tb3_4" onclick="x:HoverLi3(4,4);">已完成</span></a>
					</shiro:hasPermission>
					<shiro:hasPermission name="gaoGuanTask:myCreateTask">
						<a href="#"><span class="fl" id="tb3_5" onclick="x:HoverLi3(5,5);">进行中</span></a>
					</shiro:hasPermission>
					<shiro:hasPermission name="gaoGuanTask:fulfilTask">
						<a href="#"><span class="fl" id="tb3_6" onclick="x:HoverLi3(6,6);">已完成</span></a>
					</shiro:hasPermission>
				</div>
				
				
				<div class="gzt-hz">
				<!-- 我的任务	已分配 -->
					<div id="tbc3_01">
					<div style="height: 350px;overflow: auto;">
						<ul>
    						<c:forEach items="${pclist.datas}" var="distribution" varStatus="status">
	    						<li class="fn-clear  line">
	    							<div class="fl wd250">
	    							<p title="${distribution.taskContent }">${fn:substring(distribution.taskContent, 0, 15)}
	    								<c:if test="${fn:length(distribution.taskContent) > 15}">...</c:if></p>
	    								<p>分配时间：${fn:substring(distribution.yfpDate, 0, 10)}</p>
	    							</div>
	    							<div class="fl wd218 mg-l75">
	    								<p>计划完成时间：${fn:substring(distribution.expectEndTime, 0, 10)}</p>
	    								<p>创建人：${distribution.create_name }</p>
	    							</div>
	    							<div class="fl mg-t12">
    									<p >工作量：${distribution.taskWorkTime}</p>
    								</div>
	    						</li>
    						</c:forEach>
    					</ul>
    					</div>
    					<div  style="float: right" >
					    	<div class="pagesDiv">
					    	 共<fmt:parseNumber value="${(pclist.total%ps==0 ? pclist.total/ps : (pclist.total/ps)+1)}" integerOnly="true" />页&nbsp;|&nbsp;共${pclist.total}条记录&nbsp;
						    	<pg:pager url="${ctx}/director/myTaskBoss.do" items="${pclist.total}" maxPageItems="${ps}" export="page,currentPageNumber=pageNumber">
						             	<pg:param name="pqFlag" value="true"/>
						             	<pg:param name="flag" value="1"/>
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
					<!-- 我的任务	进行中 -->
    				<div id="tbc3_02" class="hide">
    				<div style="height: 350px;overflow: auto;">
    					<ul>
    						<c:forEach items="${pclist.datas}" var="doing" varStatus="status">	
		    						<li class="fn-clear  line">
		    						<c:if test="${doing.falred eq '0' }">
		    							<div class="fl wd250 cred3">
		    								<p title="${doing.taskContent}">${fn:substring(doing.taskContent, 0, 15)}
		    								<c:if test="${fn:length(doing.taskContent) > 15}">...</c:if></p>
		    								<p>接收时间：${fn:substring(doing.jxzDate, 0, 10)}</p>
		    							</div>
		    							<div class="fl wd218 mg-l75 cred3">
		    								<p>计划完成时间：${fn:substring(doing.expectEndTime, 0, 10)}</p>
		    								<p>创建人：${doing.create_name }</p>
		    							</div>
		    							<div class="fl mg-t12 wd108 mg-l75">
											<span style="color: red;">任务已延期</span>
										</div>
										<div class="fl mg-t12">
    									<p style="color: red;">工作量：${doing.taskWorkTime}</p>
    								</div>
	    						</c:if>
	    						<c:if test="${doing.falred ne '0' }">
		    							<div class="fl wd250">
		    								<p title="${doing.taskContent}">${fn:substring(doing.taskContent, 0, 15)}
		    								<c:if test="${fn:length(doing.taskContent) > 15}">...</c:if></p>
		    								<p>接收时间：${fn:substring(doing.jxzDate, 0, 10)}</p>
		    							</div>
		    							<div class="fl wd218 mg-l75">
		    								<p>计划完成时间：${fn:substring(doing.expectEndTime, 0, 10)}</p>
		    								<p>创建人：${doing.create_name }</p>
		    							</div>
		    							<div class="fl mg-t12">
    									<p>工作量：${doing.taskWorkTime}</p>
    								</div>
	    						</c:if>
	    						<div class="fr mg-t12 mg-r188">
		    						<shiro:hasPermission name="myEmpSTask:submitTask">
		    							<input type="hidden" id="id" name="id"/>    													<!-- 任务id -->
										<input type="hidden" id="allocationUser" name="allocationUser"/>		 			<!-- 任务创建者id -->
										<input type="hidden" id="executedevtasksys" name="executedevtasksys"/>		<!-- 任务执行者id -->
										<input type="hidden" id="taskContent" name="taskContent"/>							<!-- 任务内容 -->
										<input type="hidden" id="expectEndTime" name="expectEndTime"/>				<!-- 计划完成时间 -->
										<input type="hidden" id="exec_name" name="exec_name"/>							<!-- 任务执行者名字 -->
	    								<a href="javascript:submitTask2('${doing.id}','${doing.allocationUser}','${doing.executedevtasksys}','${doing.taskContent}','${fn:substring(doing.expectEndTime, 0, 10)}','${doing.tstatus}');" class="btn-small1 fl popup-btn1">提交</a>
	    							</shiro:hasPermission>
		    					</div>
		    					</li>
    					</c:forEach>
    					</ul>
    					</div>
    					<div  style="float: right" >
					    	<div class="pagesDiv">
					    	 共<fmt:parseNumber value="${(pclist.total%ps==0 ? pclist.total/ps : (pclist.total/ps)+1)}" integerOnly="true" />页&nbsp;|&nbsp;共${pclist.total}条记录&nbsp;
						    	<pg:pager url="${ctx}/director/myTaskBoss.do" items="${pclist.total}" maxPageItems="${ps}" export="page,currentPageNumber=pageNumber">
						             	<pg:param name="pqFlag" value="true"/>
						             	<pg:param name="flag" value="2"/>
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
					<!-- 我的任务	已提交 -->
    				<div id="tbc3_03" class="hide">
    				<div style="height: 350px;overflow: auto;">
    					<ul>
    						<c:forEach items="${pclist.datas}" var="commited" varStatus="status">
    						<li class="fn-clear  line" >
    							<div class="fl wd250">
    								<p title="${commited.taskContent}">${fn:substring(commited.taskContent, 0, 15)}
    								<c:if test="${fn:length(commited.taskContent) > 15}">...</c:if></p>
    								<p>提交时间：${fn:substring(commited.subDate, 0, 10)}</p>
    							</div>
    							<div class="fl wd250 mg-l75">
    								<p>计划完成时间：${fn:substring(commited.expectEndTime, 0, 10)}</p>
    								<p>创建人：${commited.create_name }</p>
    							</div>
    							
    							<div class="fl mg-t12 ">
    									<p>工作量：${commited.taskWorkTime}</p>
    								</div>
    							
    							<c:if test="${commited.taskstatus eq '5'}">    <!-- 5延期提交，6按时提交，7提前提交， -->
    								<div class="fr mg-t12 mg-r245"><span style="color: red;">延期提交</span></div>
    							</c:if>
    							<c:if test="${commited.taskstatus eq '6'}">    <!-- 5延期提交，6按时提交，7提前提交， -->
    								<div class="fr mg-t12 mg-r245"><span style="color: blue;">按时提交</span></div>
    							</c:if>
    							<c:if test="${commited.taskstatus eq '7'}">    <!-- 5延期提交，6按时提交，7提前提交， -->
    								<div class="fr mg-t12 mg-r245"><span style="color: green;">提前提交</span></div>
    							</c:if>
    						</li>
    						</c:forEach>
    					</ul>
    					</div>
    					<div  style="float: right" >
					    	<div class="pagesDiv">
					    	 共<fmt:parseNumber value="${(pclist.total%ps==0 ? pclist.total/ps : (pclist.total/ps)+1)}" integerOnly="true" />页&nbsp;|&nbsp;共${pclist.total}条记录&nbsp;
						    	<pg:pager url="${ctx}/director/myTaskBoss.do" items="${pclist.total}" maxPageItems="${ps}" export="page,currentPageNumber=pageNumber">
						             	<pg:param name="pqFlag" value="true"/>
						             	<pg:param name="flag" value="3"/>
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
					<!-- 我的任务	已完成 -->
    				<div id="tbc3_04" class="hide">
    				<div style="height: 350px;overflow: auto;">
						<ul>
    						<c:forEach items="${pclist.datas}" var="complete" varStatus="status">
								<li class="fn-clear  line" >
	    							<div class="fl wd250">
	    								<p title="${complete.taskContent}">${fn:substring(complete.taskContent, 0, 15)}
	    								<c:if test="${fn:length(complete.taskContent) > 15}">...</c:if></p>
	    								<p>实际完成时间：${fn:substring(complete.endDate, 0, 10)}</p>
	    							</div>
	    							<div class="fl wd250 mg-l75">
	    								<p>计划完成时间：${fn:substring(complete.expectEndTime, 0, 10)}</p>
	    								<p>创建人：${complete.create_name }</p>
	    							</div>
	    							
	    							<div class="fl mg-t12">
    									<p>工作量：${complete.taskWorkTime}</p>
    								</div>
	    							
	    							<c:if test="${complete.tjtype == 5}">    <!-- 5延期提交，6按时提交，7提前提交， -->
    									<div class="fr mg-t12 mg-l120"><span style="color: red;blue;font: bold;">延期完成</span></div>
	    							</c:if>
	    							<c:if test="${complete.tjtype == 7}">    <!-- 5延期提交，6按时提交，7提前提交， -->
	    								<div class="fr mg-t12 mg-l120"><span style="color: green;font: bold;">提前完成</span></div>
	    							</c:if>
	    							<c:if test="${complete.tjtype == 6}">    <!-- 5延期提交，6按时提交，7提前提交， -->
	    								<div class="fr mg-t12 mg-l120"><span style="color: blue;blue;font: bold;">按时完成</span></div>
	    							</c:if>
	    							
	    							<div class="fr mg-t12 mg-r245">
	    									<p>任务打分：${complete.score}</p>
	    							</div>
	    						</li>
							</c:forEach>
    					</ul>
    					</div>
    					<div  style="float: right" >
					    	<div class="pagesDiv">
					    	 共<fmt:parseNumber value="${(pclist.total%ps==0 ? pclist.total/ps : (pclist.total/ps)+1)}" integerOnly="true" />页&nbsp;|&nbsp;共${pclist.total}条记录&nbsp;
						    	<pg:pager url="${ctx}/director/myTaskBoss.do" items="${pclist.total}" maxPageItems="${ps}" export="page,currentPageNumber=pageNumber">
						             	<pg:param name="pqFlag" value="true"/>
						             	<pg:param name="flag" value="4"/>
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
    				<!-- 高管  进行中 -->
    					<div id="tbc3_05" style="position:relative;" class="hide" >
						<!-- <em class="hint3 cred3 mg-t5">注：延期的任务红色标注！</em> -->
						<div style="height: 350px;overflow: auto;">
    					<ul>
    						<c:forEach items="${pclist2.datas}" var="myCreateTask" varStatus="status">
    						<li class="fn-clear line" >
    						<c:if test="${myCreateTask.falred eq '0'}">
    							<div class="fl mg-t12 mg-r10">
    								<c:if test="${myCreateTask.ids > 0}">
    									<a><img alt="" src="../images/you.jpg" title="点击查看子任务" onclick="taskParticulars('${myCreateTask.id}')"></a>
    								</c:if>
    								<c:if test="${myCreateTask.ids eq '0'}">
    									<img alt="" src="../images/mei.jpg" title="没有子任务!">
    								</c:if>
    							</div>
    							<div class="fl wd218">
    								<a onclick="taskParticulars('${myCreateTask.id}')"><p style="color: red;" title="${myCreateTask.taskContent}">${fn:substring(myCreateTask.taskContent, 0, 15)}
    								<c:if test="${fn:length(myCreateTask.taskContent) > 15}">...</c:if></p></a>
    								<p style="color: red;">计划完成时间：${fn:substring(myCreateTask.expectEndTime, 0, 10)}</p>
    							</div>
    							<div class="fl wd188">
	    								<p style="color: red;">负责人：${myCreateTask.exec_name}</p>
	    								<p style="color: red;">任务已延期</p>
    							</div>
    							<div class="fl mg-t12 mg-r25">
    									<p>工作量：${myCreateTask.taskWorkTime}</p>
    								</div>
    						</c:if>
    						<c:if test="${myCreateTask.falred ne '0'}">	
    							<div class="fl mg-t12 mg-r10">
    								<c:if test="${myCreateTask.ids > 0}">
    									<a><img alt="" src="../images/you.jpg" title="点击查看子任务" onclick="taskParticulars('${myCreateTask.id}')"></a>
    								</c:if>
    								<c:if test="${myCreateTask.ids eq '0'}">
    									<img alt="" src="../images/mei.jpg" title="没有子任务!">
    								</c:if>
    							</div>
    							<div class="fl wd218">
    								<a onclick="taskParticulars('${myCreateTask.id}')"><p title="${myCreateTask.taskContent}">${fn:substring(myCreateTask.taskContent, 0, 15)}
    								<c:if test="${fn:length(myCreateTask.taskContent) > 15}">...</c:if></p></a>
    								<p>计划完成时间：${fn:substring(myCreateTask.expectEndTime, 0, 10)}</p>
    							</div>
    							<div class="fl wd188">
    									<p>负责人：${myCreateTask.exec_name}</p>
    								<c:if test="${myCreateTask.falred eq '0'}">
    									<p>任务已延期</p>
    								</c:if>
    							</div>
    							<div class="fl mg-t12 mg-r25">
    									<p>工作量：${myCreateTask.taskWorkTime}</p>
    								</div>
    						</c:if>
    							<input type="hidden" id="index" value="${index}"/>
    							<div style="" id="ystep4${status.index+1}" class="ystep4${status.index+1}">
    								<tr id="tr${status.index+1}">
    								<input type="hidden" id="create_name${status.index+1}" value="${myCreateTask.create_name}"/><!-- 创建人 -->
    								<input type="hidden" id="exec_name${status.index+1}" value="${myCreateTask.exec_name}"/><!-- 执行人 -->
    								<input type="hidden" id="createTime${status.index+1}" value="${fn:substring(myCreateTask.createTime, 0, 16)}"/><!-- 创建时间 -->
    								<input type="hidden" id="expectEndTime${status.index+1}" value="${myCreateTask.expectEndTime}"/><!-- 计划完成时间 -->
    								<input type="hidden" id="actualEndTime${status.index+1}" value="${myCreateTask.actualEndTime}"/><!-- 实际完成时间 -->
    								<input type="hidden" id="taskstatus${status.index+1}" value="${myCreateTask.taskstatus}"/><!-- 任务状态    -->
    								
   									<input type="hidden" id="yfpDate${status.index+1}" value="${fn:substring(myCreateTask.yfpDate, 0, 16)}"/><!-- 已分配时间 -->
    								<input type="hidden" id="jxzDate${status.index+1}" value="${fn:substring(myCreateTask.jxzDate, 0, 16)}"/><!-- 进行中时间（已接收时间） -->
    								<input type="hidden" id="subDate${status.index+1}" value="${fn:substring(myCreateTask.subDate, 0, 16)}"/><!-- 提交时间 -->
    								<input type="hidden" id="endDate${status.index+1}" value="${fn:substring(myCreateTask.endDate, 0, 16)}"/><!-- 完成时间    -->
    								</tr>
    							</div>
	    							
		    							<div class="fr mg-r15" style="margin-top:-20px;">
		    								<shiro:hasPermission name="gaoGuanTask:updateTask">
		    									<c:if test="${myCreateTask.taskstatus=='3' || myCreateTask.taskstatus=='4'}">
		    										<c:if test="${myCreateTask.tstatus == '0'}">
		    											<a href="javascript:(void);" class="btn-small1 fl popup-btn1" onclick="javascript:toUpdatePage('${myCreateTask.id}','${myCreateTask.taskContent }','${fn:substring(myCreateTask.expectEndTime, 0, 10)}','${myCreateTask.exec_name}',2,'${myCreateTask.taskstatus}','${myCreateTask.executedevtasksys}','${myCreateTask.create_name}','${myCreateTask.taskWorkTime}','${myCreateTask.projectName}','${myCreateTask.projectCode}','${myCreateTask.projectStage}')">修改</a>
		    										</c:if>
		    										<c:if test="${myCreateTask.tstatus == '1'}">
		    											<span href="" class="btn-small12 fl popup-btn3">修改</span> 
		    										</c:if>
		    									</c:if>
		    									<c:if test="${myCreateTask.taskstatus=='1' || myCreateTask.taskstatus=='2'||myCreateTask.taskstatus=='5'||myCreateTask.taskstatus=='6'||myCreateTask.taskstatus=='7'||myCreateTask.taskstatus=='8'}">
		    										<span href="" class="btn-small12 fl popup-btn3">修改</span>
		    									</c:if>
		    								</shiro:hasPermission>
		    								<c:if test="${myCreateTask.tstatus=='0' || myCreateTask.tstatus == null}">
    											<a class="btn-small21 fl popup-btn11 mg-l10" title="暂停" onclick="javascript:start_suspendTask('${myCreateTask.id}', '1');">暂停</a>
    										</c:if>
    										<c:if test="${myCreateTask.tstatus=='1'}">
    											<a class="btn-small22 fl popup-btn11 mg-l10" title="继续" onclick="javascript:start_suspendTask('${myCreateTask.id}','0','${myCreateTask.taskContent }','${fn:substring(myCreateTask.expectEndTime, 0, 10)}','${myCreateTask.exec_name}','${myCreateTask.taskstatus}','${myCreateTask.create_name}','${myCreateTask.start_suspend_time}','${myCreateTask.taskWorkTime}','${myCreateTask.projectName}','${myCreateTask.projectCode}','${myCreateTask.projectStage}');">继续</a>
    										</c:if>
    										<a class="btn-small3 fl popup-btn11 mg-l10" title="删除任务" onclick="javascript:deleteTask('${myCreateTask.id}');">删除</a>
		    							</div>
    								</li>
    							</c:forEach>
    						</ul>
    					</div>
    					<div  style="float: right" >
					    	<div class="pagesDiv">
					    	 共<fmt:parseNumber value="${(pclist2.total%ps==0 ? pclist2.total/ps : (pclist2.total/ps)+1)}" integerOnly="true" />页&nbsp;|&nbsp;共${pclist2.total}条记录&nbsp;
						    	<pg:pager url="${ctx}/director/myTaskBoss.do" items="${pclist2.total}" maxPageItems="${ps}" export="page,currentPageNumber=pageNumber">
						             	<pg:param name="pqFlag" value="true"/>
						             	<pg:param name="flag" value="5"/>
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
    				<!-- 高管 	已完成 -->
    				<div id="tbc3_06" class="hide">
    				<div style="height: 350px;overflow: auto;">
    					<ul>
    						<c:forEach items="${pclist2.datas}" var="fulfilTask">
    							<li class="fn-clear line" >
    							<div class="fl mg-t12 mg-r10">
    									<c:if test="${fulfilTask.ids > 0}">
    										<a><img alt="" src="../images/you.jpg" title="点击查看子任务" onclick="taskParticulars('${fulfilTask.id}')"></a>
    									</c:if>
    									<c:if test="${fulfilTask.ids eq '0'}">
    										<img alt="" src="../images/mei.jpg" title="没有子任务!">
    									</c:if>
    								</div>
    								<div class="fl wd250">
    									<a onclick="taskParticularsForCompleted('${fulfilTask.id}','0')"><p title="${fulfilTask.taskContent}">${fn:substring(fulfilTask.taskContent, 0, 15)}
    									<c:if test="${fn:length(fulfilTask.taskContent) > 15}">...</c:if></p></a>
    									<p>实际完成时间：${fn:substring(fulfilTask.endDate, 0, 10)}</p>
    								</div>
    								<div class="fl wd250 mg-l75">
    									<p>计划完成时间：${fn:substring(fulfilTask.expectEndTime, 0, 10)}</p>
    									<p>负责人：${fulfilTask.exec_name}</p>
    								</div>
    								
    								<div class="fl mg-t12">
    									<p>工作量：${fulfilTask.taskWorkTime}</p>
    								</div>
    								
    								<c:if test="${fulfilTask.tjtype == 5}">    <!-- 5延期提交，6按时提交，7提前提交， -->
    									<div class="fr mg-t12 mg-l120"><span style="color: red;font: bold;">延期完成</span></div>
	    							</c:if>
	    							<c:if test="${fulfilTask.tjtype == 7}">    <!-- 5延期提交，6按时提交，7提前提交， -->
	    								<div class="fr mg-t12 mg-l120"><span style="color: green;font: bold;">提前完成</span></div>
	    							</c:if>
	    							<c:if test="${fulfilTask.tjtype == 6}">    <!-- 5延期提交，6按时提交，7提前提交， -->
	    								<div class="fr mg-t12 mg-l120"><span style="color: blue;font: bold;">按时完成</span></div>
	    							</c:if>
    								
    								<div class="fr mg-t12 mg-r245">
    									<p>任务打分：${fulfilTask.score}</p>
    								</div>
    							</li>
    						</c:forEach>
    					</ul>
    					</div>
    					<div  style="float: right" >
					    	<div class="pagesDiv">
					    	 共<fmt:parseNumber value="${(pclist2.total%ps==0 ? pclist2.total/ps : (pclist2.total/ps)+1)}" integerOnly="true" />页&nbsp;|&nbsp;共${pclist2.total}条记录&nbsp;
						    	<pg:pager url="${ctx}/director/myTaskBoss.do" items="${pclist2.total}" maxPageItems="${ps}" export="page,currentPageNumber=pageNumber">
						             	<pg:param name="pqFlag" value="true"/>
						             	<pg:param name="flag" value="6"/>
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
		</form>
	</div>
    <!-- 弹窗 -->
    <div class="popup popup-box1" style="margin-left:-195px;">
    	<div class="tc-box">
    		<div class="title1 fn-clear">
                    <span>提交</span>
                    <a href="javascript:(void);" class="tc-close fr"></a>
                </div>
                <ul class="pd-t25">
                <li class="fn-clear">
                     <span style="padding-left:10px;height:60px;">备注</span><textarea class="wbqy" style="width:88%;resize: none;float:right;" 
	                    maxlength=100; placeholder="最多输入100个中文字符" rows="" cols="" id="remark" name="remark" onblur="validateXml2(this)"></textarea>
    			</li>
    			</ul>
    		<div class="txt-c pd-t15">
    			<a href="javascript:submitTask();" class="tc-btn1">确&nbsp;&nbsp;认</a>
    			<a href="javascript:(void);" class="tc-btn2 mg-l15">取&nbsp;&nbsp;消</a>
    		</div>
    	</div>
    </div>

	<!-- 弹窗 -->
    <form  id="updateTask" action="${ctx}/director/updateTask.do" method="POST">
        <%
		List list=new ArrayList();
		list.add("gaoGuan");
		session.setAttribute("list",list);
		%>
    <div class="popup popup-box2" style="margin-left:-195px;margin-top:-65px;">
    	<div class="tc-box">
    		<div class="title fn-clear">
    			<span id="regit">修改任务</span>
    			<a href="javascript:(void);" class="tc-close fr"></a>
    		</div>
				<input type="hidden" id="status" name="taskstatus"/>
				<input type="hidden" id="upid" name="id"/>
				<input type="hidden" id="falred" name="falred" value="1"/>
				<input type="hidden" id="create_name" name="create_name" />
				<input type="hidden" id="tstatus" name="tstatus" />
				<input type="hidden" id="start_suspend_time" name="start_suspend_time" />
    		<ul class="pd-t5">
    			<li class="mg-t10"><textarea maxlength=300; placeholder="最多输入300个中文字符" name="taskContent" id="uptaskContent" cols="" rows="" class="wbqy" style="width:90%;margin-left:12px;resize: none;"></textarea></li>
    			<li class="fn-clear">
    				<label class="fl txt-r mg-r10">完成时间</label>
    				<input readonly="readonly" type="text" id="finishTime" name="expectEndTime" 
    				onclick="WdatePicker()" onfocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd'})" class="Wdate" >
    			</li>
    			<li class="fn-clear">
    				<label class="fl txt-r mg-r10">工作量：</label>
    				<input style="width: 130px;height: 21px;" type="text" name="taskWorkTime" id="taskWorkTime" oninput="workCheck(this.value)">&nbsp;人时&nbsp;&nbsp;
    				<a class="discribtion">工作量单位说明</a>
    			</li>
    			
				<c:if test="${preFalg eq 2}">
				<div id="project">
					<li class="fn-clear" >
	                 	<label class="fl txt-r mg-r10" >项目名称</label>
	                 		<input class="inputC"  readonly="readonly" id="proName" name="projectName" style="border: 1px solid #94afc8;width:120px;height: 22px;line-height: 22px;color:blue;text-indent: 8px;vertical-align: middle;border-radius: 3px;"
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
                    <label class="fl txt-r mg-r10">负责人</label>
                    <input  readonly="readonly" type="text" name="executedevtasksysName" id="executedevtasksysName">
                    <input type="hidden" name="executedevtasksys" id="upexecutedevtasksys">
                    <input id="xuanze" class="form_now1" type="button" onclick="selectSingleUser()" value="选择..">
                </li>
                <li class="fn-clear" ><span id="discrib" style="display:none;"><p><b>工作量人时:</b>指1个人在没有任何干扰的情况下，完成该项任务所需花费的小时数，也称为理想人时。</p></span></li>
    		</ul>
    		<div class="txt-c pd-t15">
    			<input class="form_now" type="button" onclick="updateTask()" value="确&nbsp;&nbsp;认"/>&nbsp;&nbsp;&nbsp;&nbsp;
    			<input class="form_now" type="button" onclick="javascript:goBack()" value="取&nbsp;&nbsp;消"/>
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
    
     $(document).ready(function(){
		   jQuery.each($("tr#tr input"), function(k, v){
			   var create_name = $("input#create_name").val(); 
				
			});   	
		   
		   var index = $("#index").val();
		   
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
	       
	       if(taskstatus >= 1){
	       			title1 ="创建";
	    	   		content1 = create_name+"于"+createTime+"创建任务";
	    	   		/* if(create_name==""||create_name==null){
	    	   			title1 ="";
	    	   			content1 = "";
	    	   		} */
	       }
	       if(taskstatus >= 2){
		    	   title2 ="分配";
		    	   content2 = create_name+"于"+yfpDate+"分配任务";
		    	   //content2 = "任务于"+yfpDate+"分配给："+exec_name;
		    	  /*  if(yfpDate==""||yfpDate==null){
		    	   		title2 ="";
		    	   		content2 = "";
		    	   } */
	       }
	       if(taskstatus >= 3){
		    	    title3 ="接受";
	    	   		content3 = exec_name+"于"+jxzDate+"接受任务";
	    	   		/* if(jxzDate==""||jxzDate==null){
			       		title3 ="";
			    	   	content3 ="";
	       			} */
	       }
	       if(taskstatus >=5){
		    	title4 ="提交";
	    	   content4 = exec_name+"于"+subDate+"提交任务";
	    	   /* if(subDate==""||subDate==null){
	    	   	title4 ="";
	    	   content4 ="";
	    	   } */
	       }
	       if(taskstatus == 8){
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
	   }else if(taskstatus <= 2){
		   $(".ystep4"+i).setStep(2);
	   }else if(taskstatus <= 4){
		   $(".ystep4"+i).setStep(3);
	   }else if(taskstatus <= 7){
		   $(".ystep4"+i).setStep(4);
	   }else if(taskstatus == 8){
		   $(".ystep4"+i).setStep(5);
	   }
	  }
	   
    });
	    
    
    //-------------------------------提交任务------------------------------   
    function submitTask2(id,allocationUser,executedevtasksys,taskContent,expectEndTime,tstatus){
    	if(tstatus == 1){
	 		g_alert("warn","该任务已暂停，不能提交！","${ctx}");
	 		 $('#covered').hide()
	         $('.popup-box1').hide()
	 	}else if(tstatus == 2){
	 		g_alert("warn","该任务已终止，不能提交！","${ctx}");
	 		 $('#covered').hide()
	         $('.popup-box1').hide()
	 	}else{
	    	$("#id").val(id);
	    	$("#allocationUser").val(allocationUser);
	    	$("#executedevtasksys").val(executedevtasksys);
	    	$("#taskContent").val(taskContent);
	    	$("#expectEndTime").val(expectEndTime);
	 	}
    	
    	/* $("#id").val(id);
    	$("#allocationUser").val(allocationUser);
    	$("#executedevtasksys").val(executedevtasksys);
    	$("#taskContent").val(taskContent);
    	$("#expectEndTime").val(expectEndTime); */
    	
    }
    
 function submitTask(){
 	var nowDate = $("#nowDate").val();
	 var taskstatus = "";
	 var flag = "2";
    	var id = $("#id").val();
    	var allocationUser = $("#allocationUser").val();
    	var executedevtasksys = $("#executedevtasksys").val();
    	var taskContent = $("#taskContent").val();
    	var expectEndTime = $("#expectEndTime").val();
    	var remark = $("#remark").val();
    	
		
 	   	if(nowDate > expectEndTime){
 	   	 	taskstatus = "5";
 	   	}else if(nowDate < expectEndTime){
 	   		taskstatus = "7";
 	   	}else{
 	   		taskstatus = "6";
 	   	}
 	   	/* if(remark == "" || remark == null ){
		   alert("备注不可为空");
		   $("#remark").focus();
		}else{ */
		/* 
		if(expectEndTime==null||expectEndTime==""){
			taskstatus = "6";
		}else{
	 	   	if(nowStr > expectEndTime){
	 	   	 	taskstatus = "5";
	 	   	}else if(nowStr < expectEndTime){
	 	   		taskstatus = "7";
	 	   	}else{
	 	   		taskstatus = "6";
	 	   	}
 	   	} */
 	   location.href="${ctx}/director/submitTask2.do?flag="+flag+"&id="+id+"&allocationUser="+allocationUser+"&executedevtasksys="+executedevtasksys+"&taskContent="+taskContent+"&taskstatus="+taskstatus+"&remark="+remark;		 
 		 if(taskstatus == "5"){
 		 	messageIgnore(id,6);
 		 }else{
 		 	messageIgnore(id,5);
 		 }
 		 $('.tc-btn1').click(function(){
                 $('.popup-box1').hide()
                 $('#covered').hide()
               });
 		}
 //}	
 
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
 //-------------------------------------任务终止--------------------------------------------------
 	function deleteTask(id){
    	//debugger
    	g_alert("confirm","确定要删除该任务吗?","${ctx}/director/deleteTaskBoss.do?id="+id,"${ctx}"); 
    }
    
    function start_suspendTask(id,tstatus,content,expectEndTime,exec_name,taskstatus,create_name,start_suspend_time,taskWorkTime,proName,projectCode,projectStage){
    	if(tstatus == 0){
    		/* var url = "${ctx}/director/ckTask.do";
    		$.ajax({ 
				type : "POST",  
				url: url, 
				data : "id="+id,
				dataType : 'text',
				async: false, 
				success: function(data){
					if(data > 4){
						g_alert("warn","主任务已提交,不能启动！","","${ctx}"); 
					}else{
						if(confirm("确定要启动该任务吗?")){
			    			$("#upid").val(id);
			    			$("#status").val(taskstatus);
			    			$("#finishTime").val(expectEndTime);
			    			$("#uptaskContent").val(content);
			    			$("#executedevtasksysName").val(exec_name);
			    			$("#create_name").val(create_name);
			    			$("#tstatus").val(tstatus);
			    			$("#start_suspend_time").val(start_suspend_time);
			    			$("#xuanze").attr('hidden',true)
			    			$('#covered').show();
			   		     	$('.popup-box2').show();
			    		}
			    		//g_alert("confirm","确定要启动该任务吗?","${ctx}/director/startOrTerminatedTask.do?id="+id+"&tstatus="+tstatus+"&start_suspend_time="+start_suspend_time+"&expectEndTime="+expectEndTime,"${ctx}");
					}
				}
	      	}); */
	      	if(confirm("确定要启动该任务吗?")){
			    			$("#upid").val(id);
			    			$("#status").val(taskstatus);
			    			$("#finishTime").val(expectEndTime);
			    			$("#uptaskContent").val(content);
			    			$("#executedevtasksysName").val(exec_name);
			    			$("#create_name").val(create_name);
			    			$("#tstatus").val(tstatus);
			    			$("#start_suspend_time").val(start_suspend_time);
			    			$("#taskWorkTime").val(taskWorkTime);
			    			$("#proName").val(proName);
			    			$("#projectCode").val(projectCode);
			    			$("#projectStage").val(projectStage);
			    			$("#xuanze").attr('hidden',true)
			    			$('#covered').show();
			    			$("#regit").html("启动任务");
			   		     	$('.popup-box2').show();
			   		     	
			    		}
    	}else{
    		g_alert("confirm","确定要暂停该任务吗?","${ctx}/director/startOrTerminatedTaskBoss.do?id="+id+"&tstatus="+tstatus,"${ctx}");
    	}
    }
 
 //-------------------------------------任务更新--------------------------------------------------
 
 		function toUpdatePage(id,content,expectEndTime,exec_name, status,taskstatus,executedevtasksys,create_name,taskWorkTime,projectName,projectCode,projectStage){

		/* $("#upid").val(id);
		$("#status").val(status);
		$("#finishTime").val(expectEndTime);
		$("#uptaskContent").val(content); */
		$("#upid").val(id);
		$("#status").val(status);
		$("#finishTime").val(expectEndTime);
		$("#uptaskContent").val(content);
		$("#executedevtasksysName").val(exec_name);
		//$("#upexecutedevtasksys").val(executedevtasksys);
		$("#create_name").val(create_name);
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
		
		if(taskstatus==5 || taskstatus==6 || taskstatus==7 || taskstatus==8){
			//g_alert('warn','任务已提交,不能修改！'); 
			alert("任务已提交,不能修改！");
		}else{
			 $("#regit").html("修改任务");
			 $('#covered').show()
		     $('.popup-box2').show()
		}
	}
	
	function updateTask(){
	  var nowDate = $("#nowDate").val();
	   //var status = $("#status").val();
	   var expectEndTime = $("#finishTime").val();
	   var taskContent = $("#uptaskContent").val();
	   
	   var taskWorkTime = $("#taskWorkTime").val();
	   var projectFalg = $("#projectFalg").val();
	   var projectName = $("#proName").val();
       var projectCode = $("#projectCode").val();
       var projectStage = $("#projectStage").val();
	   //alert(projectCode);
	   var id = $("#upid").val();
	   var executedevtasksys = $("#upexecutedevtasksys").val();
	   if(taskContent == "" || taskContent == null){
		   alert("任务内容不可为空");
		   return false;
		   $("#taskContent").focus();
	  
	   }
	   if(expectEndTime == "" || expectEndTime == null){
		   alert("完成时间不可为空");
		   return false;
		   $("#finishTime").focus(); 
	   }else if(expectEndTime < nowDate){
		   alert("任务完成时间不得小于当前时间！");
		   return false;
		   $("#finishTime").focus();
	   }
	   if(taskWorkTime == "" || taskWorkTime == null){
	    		alert("工作量不可以为空");
	    		return false;
	    		$("#taskWorkTime").focus();
	  	}
	  	if(projectFalg!=""&&projectFalg!=null){
	    	 		if(projectName == "" || projectName == null){
		  				alert("项目名称不可以为空");	
			     		return false;
			     		$("#proName").focus();
		     		}
		     		if(projectCode == "" || projectCode == null){
						alert("没有此项目！！！");
			     		return false;
			     		$("#projectCode").focus();
					}
					if(projectStage == "" || projectStage == null){
						alert("项目阶段不可以为空");
			     		return false;
			     		$("#projectStage").focus();
					}
	    	 }
	    	 	 		 
		   $("#updateTask").submit();
		   //alert("修改成功！");
		    messageIgnore(id,2);
   }
 
   function workCheck(taskWorkTime){
	 			var strD = isNumericMath($.trim(taskWorkTime))
		    	if(!strD && $.trim(taskWorkTime) != "" && $.trim(taskWorkTime) != null){
		    		alert("请输入不大于40的工作量（可以保留一位小数）！");
		    		$("#taskWorkTime").val("");
		    		return false;
		}
	 }
 
    function goBack(){
    	 $('.popup-box1').hide()
	     $('.popup-box2').hide()
	     $('#covered').hide()
	     $("#rwnr").val("");
	      $("#finishTime").val("");
	      $("#executedevtasksysName").val("");
	      $("#taskWorkTime").val("");
	      $("#proName").val("");
		$("#projectCode").val("");
			$("#projectStage").val("");
	  }
    
    $(document).ready(function(){
      $('.popup-btn1').click(function(){
          $('#covered').show()
          $('.popup-box1').show()
        })

      $('.popup-btn2').click(function(){
          $('#covered').show()
          $('.popup-box2').show()
        })

      $('.tc-close').click(function(){
          $('.popup-box1').hide()
          $('.popup-box2').hide()
          $('#covered').hide()
          $("#rwnr").val("");
          $("#remark").val("");
	      $("#finishTime").val("");
	      $("#executedevtasksysName").val("");
	      $("#taskWorkTime").val("");
	      $("#proName").val("");
		$("#projectCode").val("");
			$("#projectStage").val("");
        })

      /* $('.tc-btn1').click(function(){
          $('.popup-box2').hide()
          $('#covered').hide()
        }) */
   
      $('.tc-btn2').click(function(){
	      $('.popup-box1').hide()
	      $('.popup-box2').hide()
	      $("#remark").val("");
	      $('#covered').hide()
	    })
    });
  </script>
</body>

</html>
