<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/common/taglibs.jsp" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.0 Transitional//EN"> 
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<%@ include file="/common/meta.jsp" %>
<title>任务列表</title>
<link href="${ctx}/css/static/base-global.css" rel="stylesheet" type="text/css" />
<link href="${ctx}/css/static/common.css" rel="stylesheet" type="text/css" />
<link href="${ctx}/css/static/main.css?1112=1" rel="stylesheet" type="text/css" />
<link href="${ctx}/css/static/stylesheet.css" rel="stylesheet" type="text/css" />

<script type="text/javascript" src="${ctx}/js/alert/g_alert.js?1=1"></script>

<script type="text/javascript" src="${ctx}/js/My97DatePicker/WdatePicker.js" ></script>
<script type="text/javascript" src="${ctx}/js/static/jquery/jquery-1.7.2.min.js"></script>
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
	});
</script>
<script type="text/javascript">
  
    /*滑动门*/
    function g(o){return document.getElementById(o);}
  function HoverLi3(n){
        //如果有N个标签,就将i<=N;
        
        for(var i=1;i<=2;i++){
        g('tb3_'+i).className='fl';
        g('tbc3_0'+i).className='hide';
      }
      g('tbc3_0'+n).className='';
    g('tb3_'+n).className='fl cur';
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
  
</script>
<script type="text/javascript">
    $(document).ready(function(){
        //展开收缩
        $('.zk-btn').toggle(
            function(){
                $('.yccxnr').show();
                $(this).attr('class','ss-btn');
                $(this).html('收起')
            },
            function(){
                $('.yccxnr').hide();
                $(this).attr('class','zk-btn');
                $(this).html('展开')
            });
    });
    function formSubmit(){
    	var startTime = $("#startTime").val();
		var endTime = $("#endTime").val();
		if(startTime!=null && startTime!='' && endTime!=null && endTime!=''){
			var date1 = new Date(startTime);
			var date2 = new Date(endTime);
			if(Date.parse(date1)>Date.parse(date2)){
				alert("选择开始时间不能大于结束时间！");
				return;
			}
		}
		
		var lowScore = $("#lowScore").val();
		var highScore = $("#highScore").val();
		if(lowScore!=null && lowScore!='' && highScore!=null && highScore!=''){
			if(lowScore>highScore){
				alert("选择最低分不能大于最高分！");
				return;
			}
		}
		$("#queryForm").attr("method","POST"); 
		queryForm.action="${ctx}/admin/listTask.do";
    	queryForm.submit();
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
	
	//重置
	function resetForm(){
		$("#create_name").val("");
		$("#exec_name").val("");
		$("#taskstatus").val("");
		$("#department").val("");
		$("#startTime").val("");
		$("#endTime").val("");
		$("#team").val("");
		$("#lowScore").val("");
		$("#highScore").val("");
		
	}
	
	//导出excel
	function exportSubmit(){
		var startTime = $("#startTime").val();
		var endTime = $("#endTime").val();
		if(startTime!=null && startTime!='' && endTime!=null && endTime!=''){
			var date1 = new Date(startTime);
			var date2 = new Date(endTime);
			if(Date.parse(date1)>Date.parse(date2)){
				alert("选择开始时间不能大于结束时间！");
				return;
			}
		}
		
		var lowScore = $("#lowScore").val();
		var highScore = $("#highScore").val();
		if(lowScore!=null && lowScore!='' && highScore!=null && highScore!=''){
			if(lowScore>highScore){
				alert("选择最低分不能大于最高分！");
				return;
			}
		}
		$("#queryForm").attr("method","POST");
		queryForm.action="${ctx}/admin/exportTask.do";
    	queryForm.submit();
	}
</script>
<style>
		table tr.active{
			background: #70bae1;
		}
</style>
</head>
  <body>
   <div class="content" id="mainarea">
		<div class="title"><h2>任务查询</h2></div>
		<div class="form-box fn-clear mg-t20">
            <div class="gl-form">
            <form id="queryForm" action="${ctx}/admin/listTask.do" method=post">
                <div class="fn-clear search-box" style="width:1110px;overflow:auto;">
                    <div class="fl wd710">
                        <div class="fn-clear" style="margin-left:-10px;">
                            <span class="fl mg-l10 mg-b10">
                          		创建人：<input class="srk" type="text" id="create_name" name="create_name" value="${create_name }">
                            </span>
                            <span class="fl mg-l10 mg-b10">
                                                                                          所属部门：<select name="department" id="department" class="sel" onchange="toChnage(this.value)">
                              <c:choose>
                              	<c:when test="${userRole=='director' || userRole=='staff' }">
                              		<c:forEach items="${dictionaryLlist}" var="dic" varStatus="status">
				                    	<c:if test="${dic.type_code=='01' && dic.code==taskInfo.department }">
				                    		<option value="${dic.code}" selected>${dic.name }</option>
				                    	</c:if>
				                    </c:forEach>
                              	</c:when>
                              	<c:otherwise>
	                                <option value="">请选择</option>
				                    <c:forEach items="${dictionaryLlist}" var="dic" varStatus="status">
				                    	<c:if test="${dic.type_code=='01' }">
				                    		<option value="${dic.code}" <c:if test="${dic.code==taskInfo.department}">selected</c:if>>${dic.name }</option>
				                    	</c:if>
				                    </c:forEach>
                              	</c:otherwise>
                              </c:choose>
                         	  </select>
                            </span>
                            <span class="fl mg-l10 mg-b10">
                            	打分范围：<select name="lowScore" id="lowScore" class="sel" style="width: 80px;">
	                                <option value="">请选择</option>
									<c:forEach items="${dictionaryLlist}" var="dic" varStatus="status">
				                    	<c:if test="${dic.type_code=='06' }">
				                    		<option value="${dic.name}" <c:if test="${dic.name==taskInfo.lowScore}">selected</c:if>>${dic.name }</option>
				                    	</c:if>
				                    </c:forEach>
                         		</select>&nbsp;&nbsp;至&nbsp;
                                <select name="highScore" id="highScore" class="sel" style="width: 80px;">
	                                <option value="">请选择</option>
									<c:forEach items="${dictionaryLlist}" var="dic" varStatus="status">
				                    	<c:if test="${dic.type_code=='06' }">
				                    		<option value="${dic.name}" <c:if test="${dic.name==taskInfo.highScore}">selected</c:if>>${dic.name }</option>
				                    	</c:if>
				                    </c:forEach>
                                </select>
                            </span>
                            <span class="fl mg-l10 mg-b10">
                            	任务状态：<select name="taskstatus" id="taskstatus" class="sel2">
		                             <option value="">请选择</option>
					                 <c:forEach items="${dictionaryLlist}" var="dic" varStatus="status">
				                    	<c:if test="${dic.type_code=='05' }">
				                    		<option value="${dic.code}" <c:if test="${dic.code==taskInfo.taskstatus}">selected</c:if>>${dic.name }</option>
				                    	</c:if>
					                 </c:forEach>
		                       </select>
                            </span>
                            <span class="fl mg-l10 mg-b10">
                            	负责人：<input class="srk" type="text" id="exec_name" name="exec_name" value="${exec_name }">
                            </span>
                            <span class="fl mg-l10 mg-b10">
                                                                                               所属团队：<select name="team" id="team" class="sel">
                               <c:choose>
                              	<c:when test="${userRole=='staff' }">
                              		<c:forEach items="${dictionaryLlist}" var="dic" varStatus="status">
				                    	<c:if test="${dic.type_code=='02' && dic.code==taskInfo.team }">
				                    		<option value="${dic.code}" selected>${dic.name }</option>
				                    	</c:if>
				                    </c:forEach>
                              	</c:when>
                              	<c:otherwise>
	                                <option value="">请选择</option>
	                                <c:if test="${taskInfo.department!=null && taskInfo.department!='' }">
										<c:forEach items="${childDictionaryList}" var="dic" varStatus="status">
					                    	<option value="${dic.code}" <c:if test="${dic.code==taskInfo.team}">selected</c:if>>${dic.name }</option>
					                    </c:forEach>
				                    </c:if>
                              	</c:otherwise>
                              </c:choose>
                       		  </select>
                            </span>
                            <span class="fl mg-l10 mg-b10">
                            	创建时间：<input class="srk2" size="15" readonly="readonly" type="text" id="startTime" name="startTime" onclick="WdatePicker()" onfocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd'})" class="Wdate" value="${taskInfo.startTime}">
								&nbsp;至&nbsp;
								<input class="srk2" readonly="readonly" type="text" id="endTime" name="endTime" onclick="WdatePicker()" onfocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd'})" class="Wdate" value="${fn:substring(taskInfo.endTime, 0, 10)}">
                            </span>
                        </div>
                        <!-- <div class="yccxnr hide">
                            <div class="fn-clear mg-t10">
                                
                                
                            </div>
                            <div class="fn-clear mg-t10">
                                
                                
                            </div>
                        </div> -->
                        
                    </div>
                     
                    <div class="fl wd210">
                        <a href="javascript:formSubmit();" class="fl tc-btn1 mg-l10">查&nbsp;询</a>
                        <a href="javascript:resetForm();" class="fl tc-btn2 mg-l10">重&nbsp;置</a><br><br>
                        <a href="javascript:exportSubmit();" class="fl tc-btn1 mg-l10">导出列表</a>
                    </div>
                     
                </div>
                </form>
    			<table class="form mg-t10">
                    <tr>
                        <th nowrap="nowrap" style="width:6%">序号</th>
                        <th nowrap="nowrap" style="width:18%">任务内容</th>
                        <th nowrap="nowrap" style="width:8%">任务创建人</th>
                        <th nowrap="nowrap" style="width:10%">创建时间</th>
                        <th nowrap="nowrap" style="width:8%">任务负责人</th>
                        <th nowrap="nowrap" style="width:10%">计划完成时间</th>
                        <th nowrap="nowrap" style="width:10%">所属部门</th>
                        <th nowrap="nowrap" style="width:10%">所属团队</th>
                        <th nowrap="nowrap" style="width:10%">任务状态</th>
                        <th nowrap="nowrap" style="width:10%">实际完成时间</th>
                        <th nowrap="nowrap" style="width:8%">任务打分</th>
                    </tr>
                </table>
                <div style="height:300px;overflow:auto;">
                    <table class="form">
                    <c:forEach items="${pclist.datas}" var="task" varStatus="status">
                        <tr>
                            <td style="width:6%">${status.index + 1}</td>
                            <td style="width:18%">${task.taskContent }</td>
                            <td nowrap="nowrap" style="width:8%">${task.create_name }</td>
                            <td nowrap="nowrap" style="width:10%">${fn:substring(task.createTime, 0, 19) }</td>
                            <td nowrap="nowrap" style="width:8%">${task.exec_name }</td>
                            <td nowrap="nowrap" style="width:10%">${fn:substring(task.expectEndTime, 0, 10) }</td>
                            <td nowrap="nowrap" style="width:10%">
                            	<c:forEach items="${dictionaryLlist}" var="dic" varStatus="status">
			                    	<c:if test="${dic.type_code=='01' }">
			                    		<c:if test="${dic.code==task.department}">${dic.name }</c:if>
			                    	</c:if>
			                    </c:forEach>
                            </td>
                            <td nowrap="nowrap" style="width:10%">
                            	<c:forEach items="${dictionaryLlist}" var="dic" varStatus="status">
			                    	<c:if test="${dic.type_code=='02' }">
			                    		<c:if test="${dic.code==task.team}">${dic.name }</c:if>
			                    	</c:if>
			                    </c:forEach>
                            </td>
                            <td nowrap="nowrap" style="width:10%">
                            	<c:forEach items="${dictionaryLlist}" var="dic" varStatus="status">
			                    	<c:if test="${dic.type_code=='05' }">
			                    		<c:if test="${dic.code==task.taskstatus}">${dic.name }</c:if>
			                    	</c:if>
			                    </c:forEach>
                            </td>
                            <td nowrap="nowrap" style="width:10%">${fn:substring(task.subdate, 0, 16) }</td>
                            <td nowrap="nowrap" style="width:8%">${task.score }</td>
                        </tr>
                    </c:forEach>
                    </table>
                </div>
                <div class="pages" align="right">
			    	<div class="lr15">
			    	 共<fmt:parseNumber value="${(pclist.total%ps==0 ? pclist.total/ps : (pclist.total/ps)+1)}" integerOnly="true" />页&nbsp;|&nbsp;共${pclist.total}条记录&nbsp;
			    	<pg:pager url="${ctx}/admin/listTask.do" items="${pclist.total}" maxPageItems="${ps}" export="page,currentPageNumber=pageNumber">
		              	<pg:param name="pqFlag" value="true"/>
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

    <div id="covered"></div>
    <!-- 表格行变色 -->
    <script>
	$( 'table tr' ).on( 'hover' , function () {
		$( this ).addClass( 'active' ).siblings().removeClass( 'active' );
	} )
</script>
  </body>
</html>
