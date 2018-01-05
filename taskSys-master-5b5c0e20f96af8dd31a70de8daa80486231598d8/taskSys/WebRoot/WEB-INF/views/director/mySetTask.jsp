<%@ page contentType="text/html;charset=UTF-8" %>
<%@ page import="java.util.*" %>
<%@ include file="/common/taglibs.jsp" %>
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

	$(function() {
	    $( "#proName1" ).autocomplete({
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
						$( "#proName1" ).autocomplete({
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
				$("#projectCode1").val(availableCodes[i]);
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
		var flagS = $("#flagS").val();
			if(flagS == "switch"){
				parent.$(".cur").removeClass('cur');
				parent.$("#leftMenu2").addClass('cur')
			}
			//	alert(flagS);
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
				for(var i=1;i<=3;i++){
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
        
        for(var i=1;i<=3;i++){
        g('tb3_'+i).className='fl';
        g('tbc3_0'+i).className='hide';
      }
      g('tbc3_0'+flag).className='';
    g('tb3_'+flag).className='fl cur';
    
    location.href="${ctx}/director/mySetTask.do?flag="+flag;
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
			$("#executedevtasksys").val(tmp[0]);
			$("#executedevtasksysName").val(tmp[1]);
			$("#fzrID").val(tmp[0]);
			$("#fzrName").val(tmp[1]);
		}
	}
	
	//主管--我创建的任务--进行中--任务详情
	function taskParticulars(id){
		var url = "${ctx}/director/taskParticulars.do?id="+id
		$.colorbox({
			href:url,
			iframe:true,
			width:"1100",
			height:"550"
		});
	}
	
	//主管--我创建的任务--已完成--任务详情
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
		<div class="title"><h2>我创建的任务</h2></div>
		<div class="form-box fn-clear mg-t20">
			<div class="fl wd918">
				<div class="tab-box fn-clear">
					<shiro:hasPermission name="myCreateTask:underway">
						<a href="#"><span class="fl cur" id="tb3_1" onclick="x:HoverLi3(1,1);">进行中</span></a>
					</shiro:hasPermission>
					<shiro:hasPermission name="myCreateTask:completed">
						<a href="#"><span class="fl" id="tb3_2" onclick="x:HoverLi3(2,2);">已完成</span></a>
					</shiro:hasPermission>
					<shiro:hasPermission name="myCreateTask:completed">
						<a href="#"><span class="fl" id="tb3_3" onclick="x:HoverLi3(2,3);">已转交</span></a>
					</shiro:hasPermission>
					
				</div>
	<!--  ------------------------------------------进行中--------------------------------------------------------  -->
				<input type="hidden" id="projectFalg" name="projectFalg" />
				<input type="hidden" id="flagS" name="flagS" value="${flagS}"/>
				<input type="hidden" id="flag" name="flag" value="${flag}"/>
				<input type="hidden" id="nowDate" name="nowDate" value="${nowDate}"/>
				<div class="gzt-hz">
					<div id="tbc3_01" style="position:relative;">
						<!-- <em class="hint3 cred3 mg-t5">注：延期的任务红色标注！</em> -->
						<div style="height: 350px;overflow: auto;">
    					<ul>
    					<c:forEach items="${pclist.datas}" var="myCreateTask" varStatus="status">
    						<li class="fn-clear line">
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
    							<div id="ystep4${status.index+1}" class="ystep4${status.index+1}">
    								<tr id="tr${status.index+1}">
	    								<input type="hidden" id="create_name${status.index+1}" value="${myCreateTask.create_name}"/><!-- 创建人 -->
	    								<input type="hidden" id="exec_name${status.index+1}" value="${myCreateTask.exec_name}"/><!-- 执行人 -->
	    								<input type="hidden" id="createTime${status.index+1}" value="${myCreateTask.createTime}"/><!-- 创建时间 -->
	    								<input type="hidden" id="expectEndTime${status.index+1}" value="${myCreateTask.expectEndTime}"/><!-- 计划完成时间 -->
	    								<input type="hidden" id="actualEndTime${status.index+1}" value="${myCreateTask.actualEndTime}"/><!-- 实际完成时间 -->
	    								<input type="hidden" id="taskstatus${status.index+1}" value="${myCreateTask.taskstatus}"/><!-- 任务状态    -->
    									
    									<input type="hidden" id="yfpDate${status.index+1}" value="${myCreateTask.yfpDate}"/><!-- 已分配时间 -->
	    								<input type="hidden" id="jxzDate${status.index+1}" value="${myCreateTask.jxzDate}"/><!-- 进行中时间（已接收时间） -->
	    								<input type="hidden" id="subDate${status.index+1}" value="${myCreateTask.subDate}"/><!-- 提交时间 -->
	    								<input type="hidden" id="endDate${status.index+1}" value="${myCreateTask.endDate}"/><!-- 完成时间    -->
    								</tr>
    							</div>
	    							<div class="fr mg-r15" style="margin-top:-20px;">
	    								<shiro:hasPermission name="myCreateTask:updateTask">
		    								<c:if test="${myCreateTask.taskstatus=='3' || myCreateTask.taskstatus=='4'}"> 
			    							 	<c:if test="${myCreateTask.tstatus == '0' }">
			    									<a href="javascript:(void);" class="btn-small1 fl popup-btn1" onclick="javascript:toUpdatePage('${myCreateTask.id}','${myCreateTask.taskContent }','${fn:substring(myCreateTask.expectEndTime, 0, 10)}','${myCreateTask.exec_name}',2,'${myCreateTask.taskstatus}','${myCreateTask.executedevtasksys}','${myCreateTask.create_name}','${myCreateTask.taskWorkTime}','${myCreateTask.projectName}','${myCreateTask.projectCode}','${myCreateTask.projectStage}')">修改</a>
			    								</c:if>
			    								<c:if test="${myCreateTask.tstatus == '1' }">
			    									<span href="" class="btn-small12 fl popup-btn3">修改</span> 
			    								</c:if>
			    							</c:if>
			    							<c:if test="${myCreateTask.taskstatus=='1' || myCreateTask.taskstatus=='2'||myCreateTask.taskstatus=='5'||myCreateTask.taskstatus=='6'||myCreateTask.taskstatus=='7'||myCreateTask.taskstatus=='8'}">
			    								<span href="" class="btn-small12 fl popup-btn3">修改</span> 
											</c:if>
										</shiro:hasPermission>
	    								<c:if test="${myCreateTask.tstatus=='0' || myCreateTask.tstatus == null}">
    										<a class="btn-small21 fl popup-btn1 mg-l10" title="暂停" onclick="javascript:start_suspendTask('${myCreateTask.id}', '1');">暂停</a>
    									</c:if>
    									<c:if test="${myCreateTask.tstatus=='1'}">
    										<a class="btn-small22 fl popup-btn1 mg-l10" title="继续" onclick="javascript:start_suspendTask('${myCreateTask.id}','0','${myCreateTask.taskContent }','${fn:substring(myCreateTask.expectEndTime, 0, 10)}','${myCreateTask.exec_name}','${myCreateTask.taskstatus}','${myCreateTask.create_name}','${myCreateTask.start_suspend_time}','${myCreateTask.taskWorkTime}','${myCreateTask.projectName}','${myCreateTask.projectCode}','${myCreateTask.projectStage}');">继续</a>
    									</c:if>
    									<a class="btn-small3 fl popup-btn1 mg-l10" title="删除任务" onclick="javascript:deleteTask('${myCreateTask.id}');">删除</a>
	    							</div>
    								
    								
    							
    						</li>
    						</c:forEach>
    					</ul>
    					</div>
    					<div style="float: right;">
					    	<div class="pagesDiv">
					    	 共<fmt:parseNumber value="${(pclist.total%ps==0 ? pclist.total/ps : (pclist.total/ps)+1)}" integerOnly="true" />页&nbsp;|&nbsp;共${pclist.total}条记录&nbsp;
						    	<pg:pager url="${ctx}/director/mySetTask.do" items="${pclist.total}" maxPageItems="${ps}" export="page,currentPageNumber=pageNumber">
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
<!--  ------------------------------------------已完成--------------------------------------------------------  -->
    				<div id="tbc3_02" class="hide">
    				<div style="height: 350px;overflow: auto;">
    					<ul>
    						<c:forEach items="${pclist.datas}" var="fulfilTask">
    							<li class="fn-clear line">
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
    								<div class="fl wd250">
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
	    							
	    							<div class="fr mg-t12 mg-l120">
    									<p>任务打分：${fulfilTask.score}</p>
    								</div>
	    							
    								<%-- <c:if test="${fulfilTask.delivered eq '1'}">
	    								<div class="fr mg-t12 mg-r10">
	    									<p style="color: blue;font: bold;">已转交</p>
	    								</div>
	    							</c:if>
    								<c:if test="${fulfilTask.delivered ne '1'}">
	    								<div class="fr mg-t12 mg-r10">
	    									<p style="color: red;font: bold;">未转交</p>
	    								</div>
	    							</c:if> --%>
    								<div class="fr mg-t12 ">
	    								<shiro:hasPermission name="myResponsibleTask:submitTask">
	    									<c:choose>
	    										<c:when test="${fulfilTask.delivered eq '1'}">
			    									<a href="javascript:deliverTask('${fulfilTask.id}','${fulfilTask.taskContent}','${fulfilTask.projectName}','${fulfilTask.projectCode}','${fulfilTask.projectStage}');" class="btn-small112 fl popup-btn2">再次转交</a>
	    										</c:when>
	    										<c:otherwise>
			    									<a href="javascript:deliverTask('${fulfilTask.id}','${fulfilTask.taskContent}','${fulfilTask.projectName}','${fulfilTask.projectCode}','${fulfilTask.projectStage}');" class="btn-small111 fl popup-btn2">转&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;交</a>
	    										</c:otherwise>
	    									</c:choose>
		    							</shiro:hasPermission>
	    							</div>
    							</li>
    						</c:forEach>
    					</ul>
    					</div>
    					<div style="float: right;">
					    	<div class="pagesDiv">
					    	 共<fmt:parseNumber value="${(pclist.total%ps==0 ? pclist.total/ps : (pclist.total/ps)+1)}" integerOnly="true" />页&nbsp;|&nbsp;共${pclist.total}条记录&nbsp;
						    	<pg:pager url="${ctx}/director/mySetTask.do" items="${pclist.total}" maxPageItems="${ps}" export="page,currentPageNumber=pageNumber">
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
   <!--  ------------------------------------------已转交--------------------------------------------------------  --> 				
    				<div id="tbc3_03" class="hide">
    				<div style="height: 350px;overflow: auto;">
    					<ul>
    						<c:forEach items="${pclist.datas}" var="deliverTask">
    							<li class="fn-clear line">
    								<div class="fl wd250 mg-t12">
    									<p title="${deliverTask.taskContent}">${fn:substring(deliverTask.taskContent, 0, 15)}
    									<c:if test="${fn:length(deliverTask.taskContent) > 15}">...</c:if></p>
    								</div>
    								<div class="fl wd250 mg-t12 mg-l75">
    									<p>转交时间：${fn:substring(deliverTask.deliver_time, 0, 10)}</p>
    								</div>
    								<div class="fl wd250 mg-t12 mg-l75">
    									<p>转交负责人：${deliverTask.exec_name}</p>
    								</div>
    							</li>
    						</c:forEach>
    					</ul>
    					</div>
    					<div style="float: right;">
					    	<div class="pagesDiv">
					    	 共<fmt:parseNumber value="${(pclist.total%ps==0 ? pclist.total/ps : (pclist.total/ps)+1)}" integerOnly="true" />页&nbsp;|&nbsp;共${pclist.total}条记录&nbsp;
						    	<pg:pager url="${ctx}/director/mySetTask.do" items="${pclist.total}" maxPageItems="${ps}" export="page,currentPageNumber=pageNumber">
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
    				

				</div>
			</div>

			
		</div>
	</div>
	
	<!-- 转交任务 -->
	<form  id="zjTask" action="${ctx}/director/deliverTask.do" method="POST">
    <div class="popup popup-box2" style="margin-left:-195px;">
    	<div class="tc-box">
    		<div class="title fn-clear">
    			<span>转交任务</span>
    			<a href="javascript:(void);" class="tc-close fr"></a>
    		</div>
    					
    		<ul class="pd-t5 mg-t10">
    			
    			<li class="">
    				<label class="txt-r mg-r5">&nbsp;</label>
    				<textarea id="rwnr" name="taskContent" readonly="readonly" maxlength="300" cols="" rows="" class="wbqy" style="width:310px;height:60px;resize: none;" onblur="validateXml2(this)"></textarea>
    			</li>
    		
				<c:if test="${preFalg eq 2}">
				<div id="zproject">
					<li class="fn-clear" >
	                 	<label class="fl txt-r mg-r10" >项目名称</label>
	                 		<input class="inputC" readonly="readonly" id="proName1" name="projectName" style="border: 1px solid #94afc8;width:120px;height: 22px;line-height: 22px;color:blue;text-indent: 8px;vertical-align: middle;border-radius: 3px;"
								 onkeydown="getProjectName1(this.value)" onkeyup="getProjectName1(this.value)" onblur="changeF1(this.value);">
					 </li>
					  <li class="fn-clear" >
	                 	<label class="fl txt-r mg-r10" >项目编号</label>
							<input type="text" readonly="readonly" id="projectCode1" name="projectCode" style="border:0px #dcdcdc solid;color:blue;">
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
    				<label class="fl txt-r mg-r10" style="width: 80px;"><span style="color: red;">*&nbsp;&nbsp;</span>转交负责人:</label>
    				<input style="height:20px;width:120px;" class="srk fl" readonly="readonly" type="text" name="executedevtasksysName" id="fzrName">
    				
                    <input type="hidden" name="executedevtasksys" id="fzrID">
                    <input type="hidden" name="deliver_taskid" id="deliver_taskid">
                    <input class="form_now1" type="button" onclick="selectSingleUser(0)" value="选择..">
                    <a class="discribtion">转交说明</a>
                    <!-- <a onclick="popBox7()">转交说明</a> -->
    			</li>
    			<li class="fn-clear" ><span id="discrib" style="display:none;"><p><b>转交:</b>指转交时（任务到负责人的部门经理“工作台”里由部门经理点击同意后） 
						此任务就流转到对应的负责人那里（由负责人接收后定义“计划完成时间”） </p></span></li>
    		</ul>
    		<div class="txt-c pd-t15">
    			<input class="form_now" type="button" onclick="deliverToTask()" value="确&nbsp;&nbsp;认"/>&nbsp;&nbsp;&nbsp;&nbsp;
    			<input class="form_now" type="button" onclick="javascript:goBack()" value="取&nbsp;&nbsp;消"/>
    		</div>
    	</div>
    </div>
	</form>
    <!-- 弹窗 -->
    <form  id="updateTask" action="${ctx}/director/updateTask.do" method="POST">
    <!-- 修改标识 -->	
    <%
		List list=new ArrayList();
		list.add("zhuGuan");
		session.setAttribute("list",list);
	%>
    <div class="popup popup-box1" style="margin-left:-195px;margin-top:-45px;">
    	<div class="tc-box">
    		<div class="title fn-clear">
    			<span id="regit" >修改任务</span>
    			<a href="javascript:(void);" class="tc-close fr"></a>
    		</div>
				<input type="hidden" id="status" name="taskstatus"/>
				<input type="hidden" id="id" name="id"/>
				<input type="hidden" id="falred" name="falred" value="1"/>
				<input type="hidden" id="create_name" name="create_name" />
				<input type="hidden" id="tstatus" name="tstatus" />
				<input type="hidden" id="start_suspend_time" name="start_suspend_time" />
    		<ul class="pd-t5">
    			<li class="mg-t10"><textarea name="taskContent" id="taskContent" cols="" rows="" maxlength=300; placeholder="最多输入300个中文字符" class="wbqy" style="width:90%;margin-left:12px;resize: none;"></textarea></li>
    			<li class="fn-clear">
    				<label class="fl txt-r mg-r10">完成时间</label>
    				<input readonly="readonly" type="text" id="finishTime" name="expectEndTime" onclick="WdatePicker()" onfocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd'})" class="Wdate">
    			</li>
    			<li class="fn-clear">
    				<label class="fl txt-r mg-r5">工作量：</label>
    				<input style="height: 21px;width:130px;" type="text" name="taskWorkTime" id="taskWorkTime" oninput="workCheck(this.value)">&nbsp;人时&nbsp;&nbsp;
    				<a class="discribtion1">工作量单位说明</a>
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
                    <label class="fl txt-r mg-r10">负责人</label>
                    <input class="srk1 fl" readonly="readonly" type="text" name="executedevtasksysName" id="executedevtasksysName">
                    <input type="hidden" name="executedevtasksys" id="executedevtasksys">
                    <input id="xuanze" class="form_now1" type="button" onclick="selectSingleUser(1)" value="选择..">
                </li>
                <li class="fn-clear" ><span id="discrib1" style="display:none;"><p><b>工作量人时:</b>指1个人在没有任何干扰的情况下，完成该项任务所需花费的小时数，也称为理想人时。</p></span></li>
    		</ul>
    		<div class="txt-c pd-t15">
    			<input class="form_now" type="button" onclick="updateTask()" value="确&nbsp;&nbsp;认"/>&nbsp;&nbsp;&nbsp;&nbsp;
    			<input class="form_now" type="button" onclick="javascript:goBack()" value="取&nbsp;&nbsp;消"/>
    		</div>
    	</div>
    </div>
    </form>
    <div id="covered"></div>
    </body>
    <script type="text/javascript">
      $(".discribtion").hover(function(){
                       $('#discrib').show()
                      },function(){
                          $('#discrib').hide()
                         }) 
       $(".discribtion1").hover(function(){
                       $('#discrib1').show()
                      },function(){
                          $('#discrib1').hide()
                         }) 
                         
                         
    
    function popBox7(){
    	$('#covered').show();
        $('.popup-box7').show();
    }
     function goBack1(){
        $('.popup-box7').hide();
    }
    $('tc-close fr1').click(function(){
    	$('.popup-box7').hide();
    })
    
    
    $('.popup-btn2').click(function(){
        $('#covered').show()
        $('.popup-box2').show()
      })
      
    function deliverTask(id,content,projectName,projectCode,projectStage){
    	$("#rwnr").val(content);
    	$("#deliver_taskid").val(id);
    	if(projectName==null||projectName==''){
			$("#zproject").hide();
			$("#projectFalg").val(projectName);
		}else{
			$("#zproject").show();
			$("#projectFalg").val(projectName);
			$("#proName1").val(projectName);
			$("#projectCode1").val(projectCode);
			$("#zprojectStage").val(projectStage);
		}
    }  
    
    
    function deliverToTask(){
    	var fzrID = $("#fzrID").val();
    	var deliver_taskid = $("#deliver_taskid").val();
    	var	projectName = $("#proName1").val();
	    var	projectCode = $("#projectCode1").val();
	    var	projectStage = $("#zprojectStage").val();
    	var projectFalg = $("#projectFalg").val();
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
  		 }
	  	if(fzrID == "" || fzrID == null){
    		g_alert("warn","转交负责人不能为空！","${ctx}");
	    	return;
    	}else{
    		url = "${ctx}/director/checkDeliver.do";
	    	var re = "";
	    	$.ajax({ 
				type : "POST",  
				url: url, 
				data : {"fzrID":fzrID,"deliver_taskid":deliver_taskid},
				dataType : 'text',
				async: false, 
				success: function(data){
					if(data > 0){
						g_alert("warn","该任务已转交给所选负责人！","${ctx}");
						re = 1;
					 }
					}
	      	});
	    	if(re == 1){
        		return;
        	}else{
        		
        		$("#zjTask").submit();
        		messageIgnore(deliver_taskid,2);
        	}
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
       if(createTime != "" && createTime != null){
    	   createTime=createTime.substring(0,16);
       }
       if(yfpDate != "" && yfpDate != null){
    	   yfpDate=yfpDate.substring(0,16);
       }
       if(jxzDate != "" && jxzDate != null){
    	   jxzDate=jxzDate.substring(0,16);
       }
       if(subDate != "" && subDate != null){
    	   subDate=subDate.substring(0,16);
       }
       if(endDate != "" && endDate != null){
    	   endDate=endDate.substring(0,16);
       }
       
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
	   
    });
  
   
   function toUpdatePage(id, content,expectEndTime,exec_name, status,taskstatus,executedevtasksys,create_name,taskWorkTime,projectName,projectCode,projectStage){
	  	//alert(taskstatus);
		$("#id").val(id);
		$("#status").val(taskstatus);
		$("#finishTime").val(expectEndTime);
		$("#taskContent").val(content);
		$("#executedevtasksysName").val(exec_name);
		$("#create_name").val(create_name);
		$("#taskWorkTime").val(taskWorkTime);
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
		//$("#executedevtasksys").val(executedevtasksys);
		if(taskstatus==5 || taskstatus==6 || taskstatus==7 || taskstatus==8){
			//g_alert('warn','任务已提交,不能修改！'); 
			alert("任务已提交,不能修改！");
		}else{
			$("#regit").html("修改任务");
			 $('#covered').show()
		     $('.popup-box1').show()
		}
	}
   

   function updateTask(){
	   var nowDate = $("#nowDate").val();
	   var id = $("#id").val();
	   var expectEndTime = $("#finishTime").val();
	   //alert(expectEndTime);
	   var taskContent = $("#taskContent").val();
	   var taskWorkTime = $("#taskWorkTime").val();
	   var executedevtasksys = $("#executedevtasksys").val();
	   var projectFalg = $("#projectFalg").val();
	   var	projectName = $("#proName").val();
	     	var	projectCode = $("#projectCode").val();
	     	var	projectStage = $("#projectStage").val();
	   //alert(projectName);
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
	    $('#fzrID').val("")
	    $('#fzrName').val("")
	    $("#taskWorkTime").val("");
	    $("#proName").val("");
		$("#projectCode").val("");
			$("#projectStage").val("");
			$("#proName1").val("");
		$("#projectCode1").val("");
		$("#zprojectStage").val("");
   }
    
    $(document).ready(function(){

      $('.tc-close').click(function(){
          $('.popup-box1').hide()
          $('.popup-box2').hide()
          $('#covered').hide()
          $('#fzrID').val("")
	      $('#fzrName').val("")
	      $("#taskWorkTime").val("");
	      $("#proName").val("");
		$("#projectCode").val("");
			$("#projectStage").val("");
			$("#proName").val("");
		$("#projectCode1").val("");
		$("#zprojectStage").val("");
        })

      $('.tc-btn1').click(function(){
          $('.popup-box1').hide()
          $('#covered').hide()
        })

      $('.tc-btn2').click(function(){
          $('.popup-box1').hide()
          $('.popup-box7').hide()
          $('#covered').hide()
        })
    });
    
    
    function deleteTask(id,tstatus){
    	//debugger
    	g_alert("confirm","确定要删除该任务吗?","${ctx}/director/deleteTask.do?id="+id,"${ctx}"); 
    }
    
    function start_suspendTask(id,tstatus,content,expectEndTime,exec_name,taskstatus,create_name,start_suspend_time,taskWorkTime,proName,projectCode,projectStage){
    	if(tstatus == 0){
    		var url = "${ctx}/director/ckTask.do";
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
			    			$("#id").val(id);
			    			$("#status").val(taskstatus);
			    			$("#finishTime").val(expectEndTime);
			    			$("#taskContent").val(content);
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
			   		     	$('.popup-box1').show();
			    		}
			    		//g_alert("confirm","确定要启动该任务吗?","${ctx}/director/startOrTerminatedTask.do?id="+id+"&tstatus="+tstatus+"&start_suspend_time="+start_suspend_time+"&expectEndTime="+expectEndTime,"${ctx}");
					}
				}
	      	});
    		
    	}else{
    		g_alert("confirm","确定要暂停该任务吗?","${ctx}/director/startOrTerminatedTask.do?id="+id+"&tstatus="+tstatus,"${ctx}");
    	}
    }
    
  </script>

</html>
