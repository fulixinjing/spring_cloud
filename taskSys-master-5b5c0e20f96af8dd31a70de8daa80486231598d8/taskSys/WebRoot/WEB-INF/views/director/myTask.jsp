<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/common/taglibs.jsp" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.0 Transitional//EN"> 
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<%@ include file="/common/meta.jsp" %>
<title>用户列表</title>
<link href="${ctx}/css/static/base-global.css" rel="stylesheet" type="text/css" />
<link href="${ctx}/css/static/common.css" rel="stylesheet" type="text/css" />
<link href="${ctx}/css/static/main.css" rel="stylesheet" type="text/css" />
<link rel="stylesheet" type="text/css" href="${ctx}/css/pop.css" />
<link rel="stylesheet" type="text/css" href="${ctx}/css/common.css" />
<link rel="stylesheet" type="text/css" href="${ctx}/css/right.css" />

<script type="text/javascript" src="${ctx}/js/My97DatePicker/WdatePicker.js" ></script>
<script type="text/javascript" src="${ctx}/js/static/jquery/jquery-1.7.2.min.js"></script>
<script type="text/javascript" src="${ctx}/js/sysUtil.js"></script>
<script type="text/javascript" src="${ctx}/js/alert/g_alert.js?1=1"></script>
<script type="text/javascript" src="${ctx}/js/util/common.js?1=1"></script>

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
	
	
	
		$(function() {
	    $( "#zproName" ).autocomplete({
	      source: availableTags
	    });
	});
	function getProjectName1(value){
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
						$( "#zproName" ).autocomplete({
					    	source: availableTags
					    });
						//$("#codeT").html(resultStr);
					}
				}
	    	}); 
		}
	}
	
	function changeF1(value) {
		//alert(value);
		for ( var i = 0; i < availableTags.length; i++) {
 			if(value==availableTags[i]){
				$("#zprojectCode").val(availableCodes[i]);
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
			//alert(n);
			if(n!=null && n!=''){
				for(var i=1;i<=4;i++){
			        g('tb3_'+i).className='fl';
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
        
        for(var i=1;i<=4;i++){
        g('tb3_'+i).className='fl';
        g('tbc3_0'+i).className='hide';
      }
      g('tbc3_0'+flag).className='';
    g('tb3_'+flag).className='fl cur';
    location.href="${ctx}/director/myTask.do?flag="+flag;
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
    
    function selectSingleUser(isFenpei){
	    var url= "${ctx}/admin/userList.do?isFenpei="+isFenpei;
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
			$("#fzrID").val(tmp[0]);
			$("#executedevtasksysName").val(tmp[1]);
			$("#zjID").val(tmp[0]);
			$("#zjName").val(tmp[1]);
		}
	}
	
	//主管--我负责的任务-- 已完成-- 任务详情
	function taskParticulars(id){
		var url = "${ctx}/director/taskParticulars.do?id="+id
		$.colorbox({
			href:url,
			iframe:true,
			width:"1100",
			height:"550"
		});
	}
	
	//主管--我负责的任务--已完成--任务详情
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
		<div class="title"><h2>我负责的任务</h2></div>
		<div class="form-box fn-clear mg-t20">
			<div class="fl wd918">
				<div class="tab-box fn-clear">
					<shiro:hasPermission name="myResponsibleTask:allocation">
						<a href="#"><span class="fl cur" id="tb3_1" onclick="x:HoverLi3(1,1);">未接收</span></a>
					</shiro:hasPermission>
					<shiro:hasPermission name="myResponsibleTask:underway">
						<a href="#"><span class="fl" id="tb3_2" onclick="x:HoverLi3(2,2);">进行中</span></a>
					</shiro:hasPermission>
					<shiro:hasPermission name="myResponsibleTask:submitted">
						<a href="#"><span class="fl" id="tb3_3" onclick="x:HoverLi3(3,3);">已提交</span></a>
					</shiro:hasPermission>
					<shiro:hasPermission name="myResponsibleTask:completed">
						<a href="#"><span class="fl" id="tb3_4" onclick="x:HoverLi3(4,4);">已完成</span></a>
					</shiro:hasPermission>
				</div>
				<input type="hidden" id="projectFalg" name="projectFalg" />
				<input type="hidden" id="flag" name="flag" value="${flag}"/>
				<input type="hidden" id="focusMenu" name="focusMenu" value="${focusMenu}"/>
				<input type="hidden" id="nowDate" name="nowDate" value="${nowDate}"/>
				<div class="gzt-hz">


<!-- =============================  1  =已 分 配======================================= -->
					<div id="tbc3_01">
					<div style="height: 350px;overflow: auto;">
						<ul>
						<c:forEach items="${pclist.datas}" var="allocatingTask" varStatus="status">
    						<li class="fn-clear line">
    							<div class="fl wd250">
    								<p title="${allocatingTask.taskContent}">${fn:substring(allocatingTask.taskContent, 0, 15)}
    								<c:if test="${fn:length(allocatingTask.taskContent) > 15}">...</c:if></p>
    								<p>分配时间：${fn:substring(allocatingTask.yfpDate, 0, 10)}</p>
    							</div>
    							<div class="fl wd218 mg-l75">
    								<p>计划完成时间：${fn:substring(allocatingTask.expectEndTime, 0, 10)}</p>
    								<p>创建人：${allocatingTask.create_name}</p>
    							</div>
    							<div class="fl mg-t12">
    									<p>工作量：${allocatingTask.taskWorkTime}</p>
    								</div>
    						</li>
    					</c:forEach>
    					</ul>
    					</div>
    					<div style="float: right;">
					    	<div class="pagesDiv">
					    	 共<fmt:parseNumber value="${(pclist.total%ps==0 ? pclist.total/ps : (pclist.total/ps)+1)}" integerOnly="true" />页&nbsp;|&nbsp;共${pclist.total}条记录&nbsp;
						    	<pg:pager url="${ctx}/director/myTask.do" items="${pclist.total}" maxPageItems="${ps}" export="page,currentPageNumber=pageNumber">
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
    				
    				
    				
<!-- =============================== 2 =进 行 中=========================================== -->
    				<div id="tbc3_02" class="hide">
    				<div style="height: 350px;overflow: auto;">
    				
    					<input type="hidden" id="id" name="id"/>    													<!-- 任务id -->
    					<input type="hidden" id="allocationUser" name="allocationUser"/>		 			<!-- 任务创建者id -->
    					<input type="hidden" id="executedevtasksys" name="executedevtasksys"/>		<!-- 任务执行者id -->
    					<input type="hidden" id="taskContent" name="taskContent"/>							<!-- 任务内容 -->
    					<input type="hidden" id="expectEndTime" name="expectEndTime"/>				<!-- 计划完成时间 -->
    					<input type="hidden" id="exec_name" name="exec_name"/>
    					<input type="hidden" id="tLevel" name="tLevel"/>							<!-- 任务执行者名字 -->
    				
    					<ul>
    					<c:forEach items="${pclist.datas}" var="underwayTask" varStatus="status">
    						<li class="fn-clear  line">
    						<c:if test="${underwayTask.falred eq '0' }">
    							<div class="fl mg-t12 mg-r10">
    								<c:if test="${underwayTask.ids > 0}">
    									<a><img alt="" src="../images/you.jpg" title="点击查看子任务" onclick="taskParticulars('${underwayTask.id}')"></a>
    								</c:if>
    								<c:if test="${underwayTask.ids eq '0'}">
    									<img alt="" src="../images/mei.jpg" title="没有子任务！">
    								</c:if>
    							</div>
    							<div class="fl wd250">
    								<a onclick="taskParticulars('${underwayTask.id}')"><p style="color: red;" title="${underwayTask.taskContent}">${fn:substring(underwayTask.taskContent, 0,15)}
    								<c:if test="${fn:length(underwayTask.taskContent) > 15}">...</c:if></p></a>
    								<p style="color: red;">接收时间：${fn:substring(underwayTask.jxzDate, 0, 10)}</p>
    							</div>
    							<div class="fl wd218 mg-l75">
    								<p style="color: red;">计划完成时间：${fn:substring(underwayTask.expectEndTime, 0, 10)}</p>
    								<p style="color: red;">创建人：${underwayTask.create_name}</p>
    							</div>
    							<div class="fl mg-t12 wd108 mg-l75">
    								<span style="color: red;">任务已延期</span>
    							</div>
    							<div class="fl mg-t12">
    									<p>工作量：${underwayTask.taskWorkTime}</p>
    								</div>
    						</c:if>
    						
    						<c:if test="${underwayTask.falred ne '0' }">
    							<div class="fl mg-t12 mg-r10">
    								<c:if test="${underwayTask.ids > 0}">
    									<a><img alt="" src="../images/you.jpg" title="点击查看子任务" onclick="taskParticulars('${underwayTask.id}')"></a>
    								</c:if>
    								<c:if test="${underwayTask.ids eq '0'}">
    									<img alt="" src="../images/mei.jpg" title="没有子任务！">
    								</c:if>
    							</div>
    							<div class="fl wd250">
    								<a onclick="taskParticulars('${underwayTask.id}')"><p  title="${underwayTask.taskContent}">${fn:substring(underwayTask.taskContent, 0, 15)}
    								<c:if test="${fn:length(underwayTask.taskContent) > 15}">...</c:if></p></a>
    								<p >接收时间：${fn:substring(underwayTask.jxzDate, 0, 10)}</p>
    							</div>
    							<div class="fl wd218 mg-l75">
    								<p>计划完成时间：${fn:substring(underwayTask.expectEndTime, 0, 10)}</p>
    								<p>创建人：${underwayTask.create_name}</p>
    							</div>
    							<div class="fl mg-t12">
    									<p>工作量：${underwayTask.taskWorkTime}</p>
    								</div>
    						</c:if>
    							<div class="fr mg-t12 mg-r100">
    							<c:if test="${underwayTask.allocationUser != underwayTask.executedevtasksys}">
	    							<shiro:hasPermission name="myResponsibleTask:submitTask">
	    								<a href="javascript:submitTask2('${underwayTask.id}','${underwayTask.allocationUser}','${underwayTask.executedevtasksys}','${underwayTask.taskContent}','${fn:substring(underwayTask.expectEndTime, 0, 10)}','${underwayTask.tstatus}','${underwayTask.projectName}','${underwayTask.projectCode}','${underwayTask.projectStage}');" class="btn-small1 fl popup-btn1">提交</a>
	    							</shiro:hasPermission>
		    						<c:if test="${underwayTask.falred ne '0' }">
			    						<shiro:hasPermission name="myResponsibleTask:downAllocatingTask">	
			    							<a href="javascript:downAllocatingTask2('${underwayTask.id}','${underwayTask.taskContent}','${fn:substring(underwayTask.expectEndTime, 0, 10)}','${underwayTask.executedevtasksys}','${underwayTask.exec_name}','${underwayTask.tLevel}','${underwayTask.tstatus}','${underwayTask.projectName}','${underwayTask.projectCode}','${underwayTask.projectStage}');" class="btn-small2 fl mg-l5 popup-btn2">任务分解</a>
			    						</shiro:hasPermission>
		    						</c:if>
		    						<c:if test="${underwayTask.falred eq '0' }">
			    						<shiro:hasPermission name="myResponsibleTask:downAllocatingTask">	
			    							<a href="javascript:g_alert('warn','该任务已经延期，不能再分解！','${ctx}');" class="btn-small24 fl mg-l5">任务分解</a>
			    						</shiro:hasPermission>
	    							</c:if>
	    						</c:if>
	    						<c:if test="${underwayTask.allocationUser == underwayTask.executedevtasksys}">
	    							<shiro:hasPermission name="myResponsibleTask:submitTask">
	    								<a href="javascript:submitTask2('${underwayTask.id}','${underwayTask.allocationUser}','${underwayTask.executedevtasksys}','${underwayTask.taskContent}','${fn:substring(underwayTask.expectEndTime, 0, 10)}','${underwayTask.projectName}','${underwayTask.projectCode}','${underwayTask.projectStage}');" class="btn-small1 fl popup-btn1">提交</a>
	    							</shiro:hasPermission>
		    						<shiro:hasPermission name="myResponsibleTask:downAllocatingTask">	
		    							<a href="javascript:g_alert('warn','自己给自己的任务不能再分解！','${ctx}');" class="btn-small24 fl mg-l5">任务分解</a>
		    						</shiro:hasPermission>
	    						</c:if>
    							</div>
    						</li>
    					</c:forEach>
    					</ul>
    					</div>
    					<div style="float: right;">
					    	<div class="pagesDiv">
					    	 共<fmt:parseNumber value="${(pclist.total%ps==0 ? pclist.total/ps : (pclist.total/ps)+1)}" integerOnly="true" />页&nbsp;|&nbsp;共${pclist.total}条记录&nbsp;
						    	<pg:pager url="${ctx}/director/myTask.do" items="${pclist.total}" maxPageItems="${ps}" export="page,currentPageNumber=pageNumber">
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


<!-- ==========================  3  =已 提 交============================================ -->
    				<div id="tbc3_03" class="hide">
    				<div style="height: 350px;overflow: auto;"	>
    					<ul>
    					<c:forEach items="${pclist.datas}" var="submitTask" varStatus="status">
    						<li class="fn-clear  line">
    							<div class="fl wd250">
    								<a onclick="taskParticularsForCompleted('${submitTask.id}','2')"><p title="${submitTask.taskContent}">${fn:substring(submitTask.taskContent, 0, 15)}
    								<c:if test="${fn:length(submitTask.taskContent) > 15}">...</c:if></p></a>
    								<p>提交时间：${fn:substring(submitTask.subDate, 0, 10)}</p>
    							</div>
    							<div class="fl wd250 mg-l75">
    								<p>计划完成时间：${fn:substring(submitTask.expectEndTime, 0, 10)}</p>
    								<p>创建人：${submitTask.create_name}</p>
    							</div>
    							
    							<div class="fl mg-t12">
    								<p>工作量：${submitTask.taskWorkTime}</p>
    							</div>
    							
    							<c:if test="${submitTask.taskstatus eq '5'}">    <!-- 5延期提交，6按时提交，7提前提交， -->
    								<div class="fr mg-t12 mg-l120"><span style="color: red;blue;font: bold;">延期提交</span></div>
    							</c:if>
    							<c:if test="${submitTask.taskstatus eq '6'}">    <!-- 5延期提交，6按时提交，7提前提交， -->
    								<div class="fr mg-t12 mg-l120"><span style="color: blue;font: bold;">按时提交</span></div>
    							</c:if>
    							<c:if test="${submitTask.taskstatus eq '7'}">    <!-- 5延期提交，6按时提交，7提前提交， -->
    								<div class="fr mg-t12 mg-l120"><span style="color: green;blue;font: bold;">提前提交</span></div>
    							</c:if>
    						</li>
    					</c:forEach>
    					</ul>
    					</div>
    					<div style="float: right;">
					    	<div class="pagesDiv">
					    	 共<fmt:parseNumber value="${(pclist.total%ps==0 ? pclist.total/ps : (pclist.total/ps)+1)}" integerOnly="true" />页&nbsp;|&nbsp;共${pclist.total}条记录&nbsp;
						    	<pg:pager url="${ctx}/director/myTask.do" items="${pclist.total}" maxPageItems="${ps}" export="page,currentPageNumber=pageNumber">
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


<!-- =================================== 4 =已  完  成======================================= -->
    				<div id="tbc3_04" class="hide">
    				<div style="height: 350px;overflow: auto;">
						<ul>
						<c:forEach items="${pclist.datas}" var="fulfilTask" varStatus="status">
    						<li class="fn-clear  line">
    							<div class="fl mg-t12 mg-r10">
    								<c:if test="${fulfilTask.ids > 0}">
    									<a><img alt="" src="../images/you.jpg" title="点击查看子任务" onclick="taskParticulars('${underwayTask.id}')"></a>
    								</c:if>
    								<c:if test="${fulfilTask.ids eq '0'}">
    									<img alt="" src="../images/mei.jpg" title="没有子任务！">
    								</c:if>
    							</div>
    							<div class="fl wd250">
    									<a onclick="taskParticularsForCompleted('${fulfilTask.id}','1')"><p><p title="${fulfilTask.taskContent}">${fn:substring(fulfilTask.taskContent, 0, 15)}
    									<c:if test="${fn:length(fulfilTask.taskContent) > 15}">...</c:if></p></a>
    									<p>实际完成时间：${fn:substring(fulfilTask.endDate, 0, 10)}</p>
    								</div>
    								<div class="fl wd250 mg-l75">
    									<p>计划完成时间：${fn:substring(fulfilTask.expectEndTime, 0, 10)}</p>
    									<p>创建人：${fulfilTask.create_name}</p>
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
    					<div style="float: right; height: 35px">
					    	<div class="pagesDiv">
					    	 共<fmt:parseNumber value="${(pclist.total%ps==0 ? pclist.total/ps : (pclist.total/ps)+1)}" integerOnly="true" />页&nbsp;|&nbsp;共${pclist.total}条记录&nbsp;
						    	<pg:pager url="${ctx}/director/myTask.do" items="${pclist.total}" maxPageItems="${ps}" export="page,currentPageNumber=pageNumber">
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

				</div>
			</div>

			
		</div>
	</div>

	<!-- 弹窗 -->
    <div class="popup popup-box1" style="margin-left:-195px;">
    	<div class="tc-box">
    		<div class="title fn-clear">
    			<span>提交</span>
    			<a href="javascript:(void);" class="tc-close fr"></a>
    		</div>
    		<ul class="pd-t5 mg-t10" id="sp">
    			<li class="fn-clear">
    			<label >&nbsp;&nbsp;&nbsp;同步转交？ &nbsp;</label>
    				<span class="pd-t15"  style="font-size:15px;">
    					<input name="select" id="yes" type="radio" value="1" onclick="yesORno()" class="xk-t2 mg-l25 "/>&nbsp;是
    					<input style="height:24px;"  readonly="readonly" type="text" name="executedevtasksysName" id="zjName" placeholder="请选择转交负责人">
                    	<input type="hidden" name="executedevtasksys" id="zjID">
                    	<input class="form_now1" id="sel" type="button" onclick="selectSingleUser(0)" value="选择..">
    				</span>
    			</li>
    			
				<c:if test="${preFalg eq 2}">
				<div id="zproject" style="padding-left:40px;">
					<li class="fn-clear" >
                 	<label class="fl txt-r mg-r10" >项目名称</label>
                 		<input class="inputC" readonly="readonly" id="zproName" name="projectName" style="border: 1px solid #94afc8;width:120px;height: 22px;line-height: 22px;color:blue;text-indent: 8px;vertical-align: middle;border-radius: 3px;"
							 onkeydown="getProjectName1(this.value)" onkeyup="getProjectName1(this.value)" onblur="changeF1(this.value);">
				 </li>
				  <li class="fn-clear" >
                 	<label class="fl txt-r mg-r10" >项目编号</label>
						<input type="text" readonly="readonly" id="zprojectCode" name="projectCode" style="border:0px #dcdcdc solid;color:blue;">
                 </li>
	                 <li class="fn-clear" >
	                        <label class="fl txt-r mg-r10" >项目阶段</label>
	                    	<select name="projectStage" id="zprojectStage" class="sel1">
	                    		<option value="" selected>---请选择---</option>
	                    		<c:forEach items="${peojectState}" var="dic" varStatus="status">
			                    		<option value="${dic.code}">${dic.name }</option>
			                    </c:forEach>
	                    	</select>
	                 </li>
	                </div>
				</c:if>
    			<li class="fn-clear">
    				<span class="pd-t15" style="font-size:15px;">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
    				<input name="select" id="no"  type="radio" value="2" checked="checked" onclick="yesORno()" class="xk-t2 mg-l25"/>&nbsp;否</span>
    			</li>
    			 <li class="fn-clear">
                     <span style="padding-left:10px;height:60px;">备注</span><textarea class="wbqy" style="width:88%;resize: none;float:right;" 
	                    maxlength=100; placeholder="最多输入100个中文字符" rows="" cols="" id="remark" name="remark" onblur="validateXml2(this)"></textarea>
    			</li>
    		</ul>
    		<div class="txt-c pd-t15">
    			<a href="javascript:submitTask();" class="tc-btn11">确&nbsp;&nbsp;认</a>
    			<a href="javascript:(void);" class="tc-btn2 mg-l15">取&nbsp;&nbsp;消</a>
    		</div>
    	</div>
    </div>
    
        <div class="popup popup-box01" style="margin-left:-195px;display:">
    	<div class="tc-box">
    		<div class="title fn-clear">
    			<span>提示</span>
    			<a href="javascript:(void);" class="tc-close fr"></a>
    		</div>

    		<div class="txt-c pd-t15 pd-b25 fz16">该任务的子任务尚未完成，请确认子任务完成后再进行提交！</div>
    		<div class="txt-c pd-t10">
    			<a href="javascript:(void);" class="tc-btn1">确&nbsp;&nbsp;认</a>
    		</div>
    	</div>
    </div>
    
    <!-- 向下分配任务 -->
    <div class="popup popup-box2" style="margin-left:-195px;margin-top:-85px;">
    	<div class="tc-box1">
    		<div class="title fn-clear">
    			<span>任务分解</span>
    			<a href="javascript:(void);" class="tc-close fr"></a>
    		</div>
    					
    		<ul class="pd-t5 mg-t10">
    			<li class="fn-clear">
    				<!-- <span class="fl">使用原生任务信息：</span>
    				<span class="fl" id="sp">
    					<input name="select" id="yes" type="radio" value="1" onclick="yesORno()" class="xk-t2" checked="checked"/>&nbsp;是
    					<input name="select" id="no"  type="radio" value="2" onclick="yesORno()" class="xk-t2 mg-l15"/>&nbsp;否
    				</span> -->
    				<label class="fl txt-r mg-r5">主任务：</label><p title="" id="breakDown" name="breakDown" class="breakDown" runat="server" ></p>
    			</li>
    			<li class=""><label class="fl txt-r mg-r5">分解任务：</label><textarea id="rwnr" name="taskContent" maxlength=300; placeholder="最多输入300个中文字符" cols="" rows="" class="wbqy" style="width:310px;height:60px;resize: none;" onblur="validateXml2(this)"></textarea></li>
    			<li class="fn-clear">
    				<label class="fl txt-r mg-r5">完成时间：</label>
    				<input style="height:24px;" readonly="readonly" type="text" id="finishTime" name="expectEndTime" onclick="WdatePicker()" onfocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd'})" class="Wdate">
    			</li>
    			<li class="fn-clear">
    				<label class="fl txt-r mg-r5">工作量：</label>
    				<input style="height:24px;" class="srk fl" type="text" name="taskWorkTime" id="taskWorkTime" oninput="workCheck(this.value)">&nbsp;人时&nbsp;&nbsp;
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
	                    	<select name="projectStage" id="projectStage" class="sel1" style="width:160px;">
	                    		<option value="" selected>---请选择---</option>
	                    		<c:forEach items="${peojectState}" var="dic" varStatus="status">
			                    		<option value="${dic.code}">${dic.name }</option>
			                    </c:forEach>
	                    	</select>
	                 </li>
                 </div>
				</c:if>
    			
    			<li class="fn-clear">
    				<label class="fl txt-r mg-r5">负责人：</label>
    				<input style="height:24px;" class="srk fl" readonly="readonly" type="text" name="executedevtasksysName" id="executedevtasksysName">
                    <input type="hidden" name="executedevtasksys" id="fzrID">
                    <input class="form_now1" type="button" onclick="selectSingleUser(1)" value="选择..">
    			</li>
    			<li class="fn-clear" ><span id="discrib" style="display:none;"><p><b>工作量人时:</b>指1个人在没有任何干扰的情况下，完成该项任务所需花费的小时数，也称为理想人时。</p></span></li>
    		</ul>
    		<div class="txt-c pd-t15">
    			<input class="form_now" type="button" onclick="downAllocatingTask()" value="确&nbsp;&nbsp;认"/>&nbsp;&nbsp;&nbsp;&nbsp;
    			<input class="form_now" type="button" onclick="javascript:goBack()" value="取&nbsp;&nbsp;消"/>
    		</div>
    	</div>
    </div>

    <div id="covered"></div>
    
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
    
    $(".discribtion").hover(function(){
                       $('#discrib').show()
                      },function(){
                          $('#discrib').hide()
                         })
    
 //-------------------------------提交任务------------------------------   
    function submitTask2(id,allocationUser,executedevtasksys,taskContent,expectEndTime,tstatus,projectName,projectCode,projectStage){
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
	    	$("#zproName").val(projectName);
			$("#zprojectCode").val(projectCode);
			$("#zprojectStage").val(projectStage);
	 	}
    }
    
 function submitTask(){
	 	var taskstatus = "";
	 	var flag = "2";
    	var id = $("#id").val();
    	var allocationUser = $("#allocationUser").val();
    	var executedevtasksys = $("#executedevtasksys").val();
    	var taskContent = $("#taskContent").val();
    	var expectEndTime = $("#expectEndTime").val();
    	var remark = $("#remark").val();
    	var projectFalg = $("#projectFalg").val();
    	var	projectName = $("#zproName").val();
	    var	projectCode = $("#zprojectCode").val();
	    var	projectStage = $("#zprojectStage").val();
    	//alert(projectName+projectCode+projectStage);
    	var select = $("#sp input[name='select']:checked ").val();
	 	//alert(select)
		 	/* if(remark == "" || remark == null ){
			   alert("备注不可为空");
			   $("#remark").focus();
			}else{ */
		 	if(select == 1){
		 		var zjid = $("#zjID").val();
		 		if(zjid == "" || zjid == null ){
		 			g_alert("warn","转交负责人不能为空！","${ctx}");
		 			return;
		 		}
		 		if(projectFalg!=""&&projectFalg!=null){
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
		 		}/* else if(projectName == "" || projectName == null){
		    		g_alert("warn","项目名称不能为空！","${ctx}");
			    	return;
		    	}else if(projectStage == "" || projectStage == null){
		    		g_alert("warn","项目阶段不能为空！","${ctx}");
			    	return;
		    	} */
		 			  $('.popup-box1').hide()
			          $('#covered').hide()
		 			url = "${ctx}/director/checkSubmit.do";
		 	 		$.ajax({ 
		 				type : "POST",  
		 				url: url, 
		 				data : {"id":id},
		 				dataType : 'text',
		 				success: function(data){
		 					//alert(data);
		 					if(data == "ok"){
		 						var nowDate = $("#nowDate").val();
		 				 	   	//alert(nowStr);
		 				 	   	if(nowDate > expectEndTime){
		 				 	   	 	taskstatus = "5";
		 				 	   	}else if(nowDate < expectEndTime){
		 				 	   		taskstatus = "7";
		 				 	   	}else{
		 				 	   		taskstatus = "6";
		 				 	   	}
	
		 				 	   	location.href="${ctx}/director/submitTask.do?flag="+flag+"&id="+id+"&allocationUser="+allocationUser+"&executedevtasksys="+executedevtasksys+"&taskContent="+taskContent+"&taskstatus="+taskstatus+"&select="+select+"&zjid="+zjid+"&remark="+remark+"&projectName="+projectName+"&projectCode="+projectCode+"&projectStage="+projectStage;
		 					 }else{
		 						$('.popup-box01').show()
		 						$('#covered').show()
		 						//alert("该任务的子任务尚未完成，请确认子任务完成后再进行提交！");
		 					 }
		 					}
		 	   			});
		 			//消息忽略
		 			messageIgnore(id,5);
		 	}else{
			url = "${ctx}/director/checkSubmit.do";
	 		$.ajax({ 
				type : "POST",  
				url: url, 
				data : {"id":id},
				dataType : 'text',
				success: function(data){
					//alert(data);
					if(data == "ok"){
						var nowDate = $("#nowDate").val();
				 	   	//alert(nowStr);
				 	   	if(nowDate > expectEndTime){
				 	   	 	taskstatus = "5";
				 	   	}else if(nowDate < expectEndTime){
				 	   		taskstatus = "7";
				 	   	}else{
				 	   		taskstatus = "6";
				 	   	}
						
				 	   	location.href="${ctx}/director/submitTask.do?flag="+flag+"&id="+id+"&allocationUser="+allocationUser+"&executedevtasksys="+executedevtasksys+"&taskContent="+taskContent+"&taskstatus="+taskstatus+"&select"+select+"&remark="+remark;
						 	
					 }else{
						$('.popup-box01').show()
						$('#covered').show()
						//alert("该任务的子任务尚未完成，请确认子任务完成后再进行提交！");
					 }
					}
	   			});
	   			//消息忽略
		 			messageIgnore(id,5);
		 	}
	 	}
 //}
 //------------------- 任务分解  --------------------
 	function downAllocatingTask2(id,taskContent,expectEndTime,executedevtasksys,exec_name,tLevel,tstatus,projectName,projectCode,projectStage){
	 	if(tstatus == 1){
	 		g_alert("warn","该任务已暂停，不能分解！","${ctx}");
	 		 $('#covered').hide()
	         $('.popup-box2').hide()
	 	}else if(tstatus == 2){
	 		g_alert("warn","该任务已终止，不能分解！","${ctx}");
	 		$('#covered').hide()
	         $('.popup-box2').hide()
	 	}else{
	 		$("#id").val(id);
	    	$("#taskContent").val(taskContent);
	    	$("#expectEndTime").val(expectEndTime);
	    	$("#executedevtasksys").val(executedevtasksys);
	    	$("#exec_name").val(exec_name);
	    	$("#tLevel").val(tLevel);
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
	    	
	    	//$("#finishTime").val(expectEndTime);
	    	//var task = taskContent;
	    	//document.getElementById("breakDown").innerHTML = task.substring(0,26);
	    	$(".breakDown").html(taskContent);
	    	$(".breakDown").attr("title",taskContent);
	 	}
		
 	}
 
	function yesORno(){
		 var select = $("#sp input[name='select']:checked ").val();   //单选按钮   值1 使用原生任务内容； 值2 不使用原生任务内容
		 //alert(select)
		 if(select == 1){
			 $("#zjName").show();
			 $("#sel").show();
			var projectName = $("#zproName").val();
			 if(projectName==null||projectName==''){
			 	$("#zproject").hide();
			 	$("#projectFalg").val(projectName);
			 }else{
			 	$("#zproject").show();
			 	$("#projectFalg").val(projectName);
			 }
		 }else{
			 $("#zjName").hide();
			 $("#sel").hide();
			 $("#zproject").hide();
			 $("#zjName").val("");
			 $("#zjID").val("");
		 }
	 }
	 
	 function downAllocatingTask(){
			 	var nowDate = $("#nowDate").val();//系统当前时间
		 		var projectFalg = $("#projectFalg").val();
				var id = $("#id").val();  //原任务id   向下分配任务后 变成 taskpid
		    	var taskContent = $("#rwnr").val();   //新 任务内容
		    	var expectEndTime = $("#expectEndTime").val();//旧 任务计划完成时间
		    	var expectEndTime1 = $("#finishTime").val();//新 任务计划完成时间
		    	var executedevtasksys = $("#executedevtasksys").val();   //原任务执行人id  向下分配后变成新任务的 任务创建人id
		    	var exec_name = $("#exec_name").val();	//原任务执行人名字  向下分配后变成新任务的 任务创建人名字
		    	var fzrID = $("#fzrID").val();  //新 任务执行人 id
		    	var tLevel = $("#tLevel").val();//任务级别
		    	var taskWorkTime = $("#taskWorkTime").val();//工作量
		    	
		    	var	projectName = $("#proName").val();
	     	var	projectCode = $("#projectCode").val();
	     	var	projectStage = $("#projectStage").val();
		   /* 	url = "${ctx}/director/checkFZR.do";
		    	var re = "";
		    	$.ajax({ 
					type : "POST",  
					url: url, 
					data : {"executedevtasksys":fzrID,"taskpid":id},
					dataType : 'text',
					async: false, 
					success: function(data){
						if(data > 0){
							alert("同一个任务不能分给同一个负责人多次！请重新选择负责人！");
							re = 1;
						 }
						}
		      	});
		    	if(re == 1){
	        		return;
	        	}  **/
	    	
		    if(taskContent ==　"" || taskContent == null){
		    	alert("任务内容不可为空！");
		    	return false;
		    	$("#rwnr").focus();
		    }
		    if(expectEndTime == "" || expectEndTime == null){
		    	alert("完成时间不可为空！");
		    	return false;
		    	$("#finishTime").focus();
		    }
			if(expectEndTime1<nowDate){
		    	alert("亲！完成时间不得小于当前日期哦！！！");
		    	return false;
		    	$("#finishTime").focus();
		    }
		    if(expectEndTime < expectEndTime1){
		    	alert("亲！新任务完成时间不得大于"+expectEndTime);
		    	return false;
		    	$("#finishTime").focus();
	    	}
	    	if(taskWorkTime == "" || taskWorkTime == null){
	    		alert("工作量不可以为空");
	    		return false;
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
	    	 if(fzrID == "" || fzrID == null){
		    	alert("请选择任务负责人！");
		    	return false;
		    }
		    	location.href = "${ctx}/director/downAllocatingTask.do?taskpid="+id+"&taskContent="+taskContent+"&expectEndTime="+expectEndTime1+"&allocationUser="+executedevtasksys+"&create_name="+exec_name+"&executedevtasksys="+fzrID+"&tLevel="+tLevel+"&taskWorkTime="+taskWorkTime+"&projectName="+projectName+"&projectCode="+projectCode+"&projectStage="+projectStage;
		    	messageIgnore(id,5);
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
	     $('.popup-box2').hide()
	     $('#covered').hide()
	     $("#rwnr").val("");
	     $("#finishTime").val("");
	     $("#executedevtasksysName").val("");
	     $("#taskWorkTime").val("");
	     $("#selText").val("");
		$("#projectCode").val("");
			$("#projectStage").val("");
			$("#zproName").val("");
		$("#zprojectCode").val("");
		$("#zprojectStage").val("");
	  }
    
    $(document).ready(function(){
      $('.popup-btn1').click(function(){
          $('#covered').show()
          $('.popup-box1').show()
          $("#no").attr("checked",true)
          $("#zjName").hide();
		  $("#sel").hide();
		  	$("#zproject").hide();
        })

      $('.popup-btn2').click(function(){
          $('#covered').show()
          $('.popup-box2').show()
        })

      $('.tc-close').click(function(){
          $('.popup-box1').hide()
          $('.popup-box2').hide()
          $('.popup-box01').hide()
          $('#covered').hide()
          $("#rwnr").val("");
	      $("#finishTime").val("");
	      $("#executedevtasksysName").val("");
	      $("#taskWorkTime").val("");
	      $("#zjName").val("");
		  $("#zjID").val("");
		  $("#remark").val("");
		  $("#selText").val("");
		$("#projectCode").val("");
			$("#projectStage").val("");
			$("#zproName").val("");
		$("#zprojectCode").val("");
		$("#zprojectStage").val("");
        })

      $('.tc-btn1').click(function(){
          $('.popup-box1').hide()
          $('.popup-box2').hide()
          $('.popup-box01').hide()
          $('#covered').hide()
        })
   
      $('.tc-btn2').click(function(){
	      $('.popup-box1').hide()
	      $('.popup-box2').hide()
	      $('#covered').hide()
	      $("#zjName").val("");
		  $("#zjID").val("");
		  $("#remark").val("");
	    })
    });
  </script>
</body>

</html>
