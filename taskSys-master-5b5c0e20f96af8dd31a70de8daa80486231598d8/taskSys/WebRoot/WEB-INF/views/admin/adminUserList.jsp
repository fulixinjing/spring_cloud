<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/common/taglibs.jsp" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.0 Transitional//EN"> 
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<%@ include file="/common/meta.jsp" %>
<title>用户列表</title>
<link href="${ctx}/css/static/base-global.css" rel="stylesheet" type="text/css" />
<link href="${ctx}/css/static/common.css" rel="stylesheet" type="text/css" />
<link href="${ctx}/css/static/main.css?1=1" rel="stylesheet" type="text/css" />
<link href="${ctx}/css/static/stylesheet.css" rel="stylesheet" type="text/css" />
<link rel="stylesheet" type="text/css" href="${ctx}/css/right.css" />
<link rel="stylesheet" type="text/css" href="${ctx}/css/pop.css" />


<script type="text/javascript" src="${ctx}/js/static/jquery/jquery-1.7.2.min.js"></script>
<script type="text/javascript" src="${ctx}/js/My97DatePicker/WdatePicker.js" ></script>
<link rel="stylesheet" type="text/css" href="${ctx}/components/colorbox/colorbox.css"/>
<script language="javascript" src="${ctx}/components/colorbox/jquery.colorbox.js"></script>

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

    function formSubmit(){
		form1.submit();
    }
 

    function plsc(){
		var ck = document.getElementsByName("ck");
		var ids ="";
		for(var x=0;x<ck.length;x++){
			if(ck[x].checked){
				ids+=ck[x].value+",";
			}
		}
		ids = ids.substring(0, ids.length-1);
		//alert(post_id);
		if(ids.length>0){
				location.href="${ctx}/user/delBatchUser/"+ids+".do";
		}else{
			confirm("请先勾选！")
		}
	}
	function editUser(userId){
		url = "${ctx}/user/edit.do";
		$.ajax({ 
			type : "POST",  
			url: url, 
			data : {"userId":userId},
			dataType : 'json',
			success: function(data){
					$("#editloginName").val(data.loginName);
				//	$("#editpassword").val(data.password);
					$("#edituserName").val(data.userName);
					$("#editemployed_date").val(data.employed_date);
					$("#editemail").val(data.email);
					$("#edituserId").val(data.userId);
					for(var i=0;i<data.list.length;i++){
						
						if(data.list[i].type_code=='01'){
							if(data.department_id == data.list[i].code){
								$('#editdepartment_id').append('<option value="'+data.list[i].code+'" selected="selected" >'+data.list[i].name+'</option>');
							}else{
								$('#editdepartment_id').append('<option value="'+data.list[i].code+'">'+data.list[i].name+'</option>');
							}
						}
					}
					for(var i=0;i<data.list.length;i++){
						 if(data.list[i].type_code=='02'){
							if(data.team_id == data.list[i].code){
								$('#editteam_id').append('<option value="'+data.list[i].code+'" selected="selected" >'+data.list[i].name+'</option>');
							}else{
								$('#editteam_id').append('<option value="'+data.list[i].code+'">'+data.list[i].name+'</option>');
							}
						}
					} 
					for(var i=0;i<data.list.length;i++){
						  if(data.list[i].type_code=='03'){
							if(data.position_id == data.list[i].code){
								$('#editposition_id').append('<option value="'+data.list[i].code+'" selected="selected" >'+data.list[i].name+'</option>');
							}else{
								$('#editposition_id').append('<option value="'+data.list[i].code+'">'+data.list[i].name+'</option>');
							}
						}
					} 
					for(var i=0;i<data.list.length;i++){
						 if(data.list[i].type_code=='04'){
							if(data.jobId == data.list[i].code){
								$('#editjobId').append('<option value="'+data.list[i].code+'" selected="selected" >'+data.list[i].name+'</option>');
							}else{
								$('#editjobId').append('<option value="'+data.list[i].code+'">'+data.list[i].name+'</option>');
							}
						}
					}
					debugger;
					//alert(data.map);
					var rname = "";
			for(var i=0;i<data.map.length;i++){
						//alert(data.map[i].roleName);
						rname += data.map[i].roleName+ "\n";
						$("#dd").append("<input type=\"hidden\"  name=\"roles["+i+"].id\" value=\""+data.map[i].id+"\">");
					//alert(rname);
			}
					
				$("#roleArea").val(rname);
				$("#changeRoles input").remove();
				$("#changeRoles").append("<input class=\"form_now\"  type=\"button\"  onclick=\"selectMutliRole("+data.userId+",2)\" value=\"选择\">");

			//document.getElementById("roleArea").innerText =sss;
				}
      		
      	});
	}
	
	function toChnage(id){
		$("#team_id option:not(:first)").remove();
		url = "${ctx}/user/change/team.do";
		$.ajax({ 
			type : "POST",  
			url: url, 
			data : "code="+id,
			dataType : 'json',
			success: function(data){
				for(var i=0;i<data.list.length;i++){
					$('#team_id').append('<option value="'+data.list[i].code+'">'+data.list[i].name+'</option>');
					
				}
				
				}
      		
      	});
	}
	
	function toChnage2(id){
		$("#editteam_id option:not(:first)").remove();
		url = "${ctx}/user/change/team.do";
		$.ajax({ 
			type : "POST",  
			url: url, 
			data : "code="+id,
			dataType : 'json',
			success: function(data){
				for(var i=0;i<data.list.length;i++){
					$('#editteam_id').append('<option value="'+data.list[i].code+'">'+data.list[i].name+'</option>');
					
				}
				
				}
      		
      	});
	}
	
	 function goBack(){
	    	location.reload();
	    }
	
	function lineColor(index){
		$("#line"+index).addClass( 'active' ).siblings().removeClass( 'active' );
		//var ck = document.getElementById("ckid"+index);
		//if(ck.checked){
		//	ck.checked=false;
		//}else{
		//	ck.checked=true;
		//}
	}
	function resetUserPassword(userId, userName) {
		if (confirm("您确认要重置[" + userName + "]用户的密码吗？")) {
			window.location = "${ctx}/user/rpw/" + userId + "/" + userName +".do";
		}
	}
</script>

<script type="text/javascript">
	
		 //----------------------------------新增用户     验证----------------------------------------------
	    
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
		  
		 
		    
		   function addUser(){
		    	var loginName = $("#loginName").val();
		    	var userName = $("#userName").val();
		    	var roleArea2 = $("#roleArea2").val();
		    	var employed_date = $("#employed_date").val();
		    	var email = $("#email").val();
		    	
		    	var reg = /^([a-zA-Z0-9_-])+@([a-zA-Z0-9_-])+((\.[a-zA-Z0-9_-]{2,3}){1,2})$/;
		    	if(loginName == "" || loginName == null){
					$("#checkLoginName").text("× 不可为空！");
					return false;
					$("#loginName").focus(); 
				}else if(!reg.test(loginName)){
					$("#checkLoginName").text("× 格式不正确!"); 
					return false;
		    		$("#loginName").focus(); 
				}else{
					$("#checkLoginName").text(" √"); 
				}
		    	
		    	if(userName == "" || userName == null){
		  		  $("#checkUserName").text("× 不可为空！");
		  		return false;
		  		  $("#userName").focus(); 
		  	  	}else{
		  		  $("#checkUserName").text("   √"); 
		  	  	}
		    	
		    	 if(roleArea2 == "" || roleArea2 == null){
		   		  $("#checkRole").text("× 请选择角色！");
		   		 // $("#userName").focus(); 
		   			return false;
		   	  	}else{
		   		  $("#rr").text("   √"); 
		   		$("#checkRole").text("");
		   	  	}
		    	 
		    	 var nowDate = $("#nowDate").val();
		   	  	alert(nowDate);
		   	  if(employed_date == "" || employed_date == null){
		   		  $("#checkEmployed_date").text("× 不可为空！");
		   		return false;
		   		  $("#employed_date").focus(); 
		   	  }else if(employed_date > nowDate){
		   		  $("#checkEmployed_date").text("× 超过当前时间！");
		   		return false;
		   		  $("#employed_date").focus(); 
		   	  }else{
		   		  $("#checkEmployed_date").text("   √"); 
		   	  }
		   	  
		   	if(email == "" || email == null){
				$("#checkEmail").text("× 不可为空！");
				return false;
				$("#email").focus(); 
			}else if(!reg.test(email)){
				$("#checkEmail").text("× 格式不正确!"); 
				return false;
				$("#email").focus(); 
			}else{
				$("#checkEmail").text(" √"); 
			}
		    	
				$("#saveForm").submit();
		 }
		   
		   
		 //---------------------------------编辑用户   验证------------------------------------------------------------//

			
		    function updateUser(){
		    	var loginName = $("#editloginName").val();
		    	var userName = $("#edituserName").val();
		    	var roleArea = $("#roleArea").val();
		    	var employed_date = $("#editemployed_date").val();
		    	var email = $("#editemail").val();
		    	var reg = /^([a-zA-Z0-9_-])+@([a-zA-Z0-9_-])+((\.[a-zA-Z0-9_-]{2,3}){1,2})$/;
				if(loginName == "" || loginName == null){
					$("#checkEditloginName").text("× 不可为空！");
					return false;
					$("#editloginName").focus(); 
				}else if(!reg.test(loginName)){
					$("#checkEditloginName").text("× 格式不正确!"); 
					return false;
		    		$("#editloginName").focus(); 
				}else{
					$("#checkEditloginName").text(" √"); 
				}
				
				 if(userName == "" || userName == null){
					  $("#checkEdituserName").text("× 不可为空！");
					  return false;
					  $("#edituserName").focus(); 
				  }else{
					  $("#checkEdituserName").text("   √"); 
				  }
				 
				 if(roleArea == "" || roleArea == null){
			   		  $("#checkEditRole").text("× 请选择角色！");
			   		 // $("#userName").focus(); 
			   			return false;
			   	  	}else{
			   		  $("#oo").text("   √"); 
			   		$("#checkEditRole").text("");
			   	  	}
				 
				 var nowDate = $("#nowDate").val();
				  //alert(nowStr);
				  if(employed_date == "" || employed_date == null){
					  $("#checkEditemployed_date").text("× 不可为空！");
					  return false;
					  $("#editemployed_date").focus(); 
				  }else if(employed_date > nowDate){
					  $("#checkEditemployed_date").text("× 不能超过当前时间！");
					  return false;
					  $("#editemployed_date").focus(); 
				  }else{
					  $("#checkEditemployed_date").text("   √"); 
				  }
				  
				  if(email == "" || email == null){
						$("#checkEditemail").text("× 不可为空！");
						 return false;
						$("#editemail").focus(); 
					}else if(!reg.test(email)){
						$("#checkEditemail").text("× 格式不正确!"); 
						 return false;
			  			$("#editemail").focus(); 
					}else{
						$("#checkEditemail").text(" √"); 
					}
		    	
				$("#editForm").submit();
		       }

</script>
<style>
		table tr.active{
			background: #70bae1;
		}
</style>
</head>
<tags:message content="${message}" type="success" />
<body>
 <div class="content" id="mainarea">
        <div class="title"><h2>用户管理</h2></div>
        <div class="form-box fn-clear mg-t20">
            <div class="gl-form">
                <div class="fn-clear">
	                <shiro:hasPermission name="user_manage:user_maintain:new">
	                    <a href="javascript:(void);" class="fl add-btn popup-btn1">新增用户</a>
	                </shiro:hasPermission>
	                <shiro:hasPermission name="user_manage:user_maintain:delete">
	                    <a href="javascript:(void);" class="fl del-btn mg-l10 popup-btn2">删除用户</a>
	                </shiro:hasPermission>
                </div>
                <form id="form1" action="${ctx}/user/list.do" method="post">
                <div class="fn-clear search-box mg-t15">
                     <span class="fl">
                         用户名：<input name="loginName" type="text" class="srk" value="${user.loginName}"/>
                     </span>
                     <span class="fl mg-l10">
                         姓名：<input name="userName" type="text" class="srk" value="${user.userName}"/>
                     </span>
                     <span class="fl mg-l10">
                         所属部门：<select name="department_id" class="sel">
                            <option value="">全部</option>
	                    		<c:forEach  items="${list}" var="list">
	                    			<c:if test="${list.type_code eq '01'}">
	                    				<option value="${list.code}" <c:if test="${user.department_id eq list.code}">selected</c:if> >${list.name}</option>
	                    			</c:if>
	                    		</c:forEach>
	                 </select>
                     </span>
                     <a href="javascript:formSubmit();" class="fl tc-btn1 mg-l10">查&nbsp;询</a>
                </div>
                </form>
                <table class="form mg-t20">
                    <tr>
                        <th nowrap="nowrap" width="2%">选择</th>
                        <th nowrap="nowrap" width="9%">用户名</th>
                        <!-- <th nowrap="nowrap" width="9%">密码</th>  -->
                        <th nowrap="nowrap" width="8%">姓名</th>
                        <th nowrap="nowrap" width="13%">所属部门</th>
                        <th nowrap="nowrap" width="8%">所属团队</th>
                        <th nowrap="nowrap" width="8%">职位级别</th>
                        <th nowrap="nowrap" width="8%">岗位类别</th>
                        <th nowrap="nowrap" width="8%">入职时间</th>
                        <th nowrap="nowrap" width="13%">邮箱</th>
                        <th nowrap="nowrap" width="9%">操作</th>
                    </tr>
                </table>
                <div style="height:300px;overflow:auto;">
                    <table class="form">
                    <c:forEach items="${pclist.datas}" var="user" varStatus="status">
                        <tr id="line${status.index}" onclick="lineColor('${status.index}');">
                            <td class="txt-c" width="3%"><input id="ckid${status.index}" type="checkbox" name="ck" value="${user.userId}"/><input type="hidden" name="userId" value="${user.userId}"/></td>
                            <td width="13%">${user.loginName }</td>
                            <!-- <td width="9%">${user.password }</td>  -->
                            <td width="8%">${user.userName }</td>
                            <td width="13%">${user.department_Name}</td>
                            <td width="8%">${user.team_Name}</td>
                            <td width="8%">${user.position_Name}</td>
                            <td width="8%">${user.postName}</td>
                            <td width="8%">${user.employed_date}</td>
                            <td width="13%">${user.email}</td>
                            <td class="txt-c" width="9%">
	                            <shiro:hasPermission name="user_manage:user_maintain:update_sava">
	                            	<a href="javascript:editUser('${user.userId }');" class="cblue4 popup-btn3">编辑</a>
	                            </shiro:hasPermission>
	                            <shiro:hasPermission name="user_manage:user_maintain:password_reset">
	                            	<a href="javascript:resetUserPassword('${user.userId }','${user.userName }');" class="cblue4">重置密码</a>
	                            </shiro:hasPermission>
                            </td>
                        </tr>
                    </c:forEach>
                    </table>
                </div>
                <div class="pages" align="right">
			    	<div class="lr15">
			    	 共<fmt:parseNumber value="${(pclist.total%ps==0 ? pclist.total/ps : (pclist.total/ps)+1)}" integerOnly="true" />页&nbsp;|&nbsp;共${pclist.total}条记录&nbsp;
			    	<pg:pager url="${ctx}/user/list.do" items="${pclist.total}" maxPageItems="${ps}" export="page,currentPageNumber=pageNumber">
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

    <!-- 弹窗 -->

    <!-- 新增用户 -->
    <form id="saveForm" name="saveForm" action="${ctx}/user/save.do" method="post">
    <div class="popup popup-box1" style="margin-left:-195px;top:00%;">
        <div class="tc-box">
            <div class="title fn-clear">
                <span>新增用户</span>
                <a href="#" class="tc-close fr" onclick="closeDIV()"></a>
            </div>

            <ul class="pd-t15">
            <div id="checkDiv"></div>
                
                <li class="fn-clear">
                    <label class="fl txt-r mg-r10"><span style="color: red;">*&nbsp;</span>用户名</label>
                    <input id="loginName" name="loginName" type="text" class="srk fl"  placeholder="请使用工作邮箱作为用户名"><span id="checkLoginName" style="color: red;"></span>
                </li>
                <%--<li class="fn-clear">
                    <label class="fl txt-r mg-r10">密码</label>
                    <input name="password" type="text" class="srk fl">
                </li>
                --%><li class="fn-clear">
                    <label class="fl txt-r mg-r10"><span style="color: red;">*&nbsp;</span>姓名</label>
                    <input id="userName" name="userName" type="text" class="srk fl"  placeholder="请输入真实姓名"><span id="checkUserName" style="color: red;"></span>
                </li>
                <li class="fn-clear">
                    <label class="fl txt-r mg-r10">所属部门</label>
                    <select id="department_id" name="department_id" class="sel fl" onchange="toChnage(this.value)">
                        <option value="">======请 选 择======</option>
	                    		<c:forEach  items="${mlist}" var="list">
	                    			<c:if test="${list.type_code eq '01'}">
	                    				<option value="${list.code}">${list.name}</option>
	                    			</c:if>
	                    		</c:forEach>
                    </select>
                </li>
                <li class="fn-clear">
                    <label class="fl txt-r mg-r10">所属团队</label>
                    <select name="team_id" id="team_id" class="sel fl">
                        <option value="">======请 选 择======</option>
                    </select>
                </li>
                <li class="fn-clear">
                    <label class="fl txt-r mg-r10">职位级别</label>
                    <select id="position_id" name="position_id" class="sel fl">
                        <option value="">======请 选 择======</option>
	                    		<c:forEach  items="${mlist}" var="list">
	                    			<c:if test="${list.type_code eq '03'}"><option value="${list.code }">${list.name}</option></c:if>
	                    		</c:forEach>
                    </select>
                </li>
                <li class="fn-clear">
                    <label class="fl txt-r mg-r10">岗位类型</label>
                    <select id="jobId" name="jobId" class="sel fl">
                        <option value="">======请 选 择======</option>
	                    		<c:forEach  items="${mlist}" var="list">
	                    			<c:if test="${list.type_code eq '04'}"><option value="${list.code }">${list.name}</option></c:if>
	                    		</c:forEach>
                    </select>
                </li>
                <li class="fn-clear">
                    <label class="fl txt-r mg-r10"><span style="color: red;">*&nbsp;</span>角色</label>
                    <textarea id="roleArea2" name="roleArea" rows="2" cols="28" readonly="readonly" placeholder="请选择角色"></textarea>
                	<span id="rr" style="color: red;"></span>
                	<input class="form_now" type="button" onclick="selectMutliRole('',1)" value="选择..">
                	<br>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                	<span id="checkRole" style="color: red;"></span>
                </li>
                <li class="fn-clear">
                    <label class="fl txt-r mg-r10"><span style="color: red;">*&nbsp;</span>入职时间</label>
                    <input type="text" id="employed_date" name="employed_date"  onclick="WdatePicker()" onfocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd'})" class="srk fl" " placeholder="入职时间不能大于当前时间">
                	<span id="checkEmployed_date" style="color: red;"></span>
                </li>
                <li class="fn-clear">
                    <label class="fl txt-r mg-r10"><span style="color: red;">*&nbsp;</span>邮箱</label>
                    <input id="email" name="email" type="text" class="srk fl" placeholder="请输入工作邮箱" ><span id="checkEmail" style="color: red;"></span>
                </li>
            </ul>
            <div id="addOfRole"></div>
            <div class="txt-c pd-t15">
                <input class="form_now" type="button" onclick="addUser()"  value="保存">&nbsp;&nbsp;&nbsp;&nbsp;
                 <input class="form_now" type="button" onclick="javascript:closeDIV()" value="取消">
            </div>
          
        </div>
    </div>
    </form>

    <!-- 删除用户 -->
    <div class="popup popup-box2" style="margin-left:-195px;">
        <div class="tc-box">
            <div class="title fn-clear">
                <span>提示</span>
            </div>

            <div class="txt-c pd-t25 pd-b25 fz16">是否确定删除该用户？</div>
            <div class="txt-c pd-t15">
                <a href="javascript:plsc();" class="tc-btn1">确&nbsp;&nbsp;认</a>
                <a href="javascript:(void);" class="tc-btn2 mg-l15">取&nbsp;&nbsp;消</a>
            </div>
        </div>
    </div>

    <!-- 编辑用户 -->
    <form id="editForm" name="editForm" action="${ctx}/user/save.do" method="post">
    <div id="div1" class="popup popup-box3" style="margin-left:-195px;top:0%;">
        <div class="tc-box">
            <div class="title fn-clear">
                <span>编辑用户</span>
                <a href="#" class="tc-close fr" onclick="closeDIV()"></a>
            </div>

            <ul class="pd-t15">
                <li class="fn-clear">
                    <label class="fl txt-r mg-r10"><span style="color: red;">*&nbsp;</span>用户名</label>
                    <input id="editloginName" readonly="readonly" name="loginName" type="text"  placeholder="请使用工作邮箱作为用户名"  class="srk fl">
                    <span id="checkEditloginName" style="color: red;"></span>
                    <input id="edituserId" name="userId" type="hidden">
                </li>
               <!--  
               <li class="fn-clear">
                    <label class="fl txt-r mg-r10">密码</label>
                    <input id="editpassword" name="password" type="text" value="123456" class="srk fl">
                </li>
                -->
                <li class="fn-clear">
                    <label class="fl txt-r mg-r10"><span style="color: red;">*&nbsp;</span>姓名</label>
                    <input id="edituserName" readonly="readonly" name="userName" type="text"  placeholder="请输入真实姓名" class="srk fl">
                    <span id="checkEdituserName" style="color: red;"></span>
                </li>
                <li class="fn-clear">
                    <label class="fl txt-r mg-r10">所属部门</label>
                    <select name="department_id" id="editdepartment_id" class="sel fl" onchange="toChnage2(this.value)">
                        <option value="">======请 选 择======</option>
                    </select>
                </li>
                <li class="fn-clear">
                    <label class="fl txt-r mg-r10">所属团队</label>
                    <select name="team_id" id="editteam_id" class="sel fl">
                        <option value="">======请 选 择======</option>
                    </select>
                </li>
                <li class="fn-clear">
                    <label class="fl txt-r mg-r10">职位级别</label>
                    <select name="position_id" id="editposition_id" class="sel fl">
                        <option value="">======请 选 择======</option>
                    </select>
                </li>
                <li class="fn-clear">
                    <label class="fl txt-r mg-r10">岗位类型</label>
                    <select name="jobId" id="editjobId" class="sel fl">
                        <option value="">======请 选 择======</option>
                    </select>
                </li>
                <li class="fn-clear">
                    <label class="fl txt-r mg-r10"><span style="color: red;">*&nbsp;</span>角色</label>
                    <textarea id="roleArea" rows="2" cols="28" style="resize: none;" readonly="readonly" placeholder="请选择角色"></textarea><span id="oo" style="color: red;"></span><span id="changeRoles"></span>
                	<br>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                	<span id="checkEditRole" style="color: red;"></span>
                </li>
                <li class="fn-clear">
                    <label class="fl txt-r mg-r10"><span style="color: red;">*&nbsp;</span>入职时间</label>
                    <input id="editemployed_date" name="employed_date" type="text"  placeholder="入职时间不能大于当前时间"  onclick="WdatePicker()" onfocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd'})" class="srk fl" ">
                	<span id="checkEditemployed_date" style="color: red;"></span>
                </li>
                <li class="fn-clear">
                    <label class="fl txt-r mg-r10"><span style="color: red;">*&nbsp;</span>邮箱</label>
                    <input name="email" id="editemail" readonly="readonly" type="text"  placeholder="请输入工作邮箱" class="srk fl">
                	<span id="checkEditemail" style="color: red;"></span>
                </li>
            </ul>
            <div class="txt-c pd-t15">
                <input class="form_now" type="button" onclick="updateUser()"  value="保存">&nbsp;&nbsp;&nbsp;&nbsp;
                 <input class="form_now" type="button" onclick="javascript:closeDIV()" value="取消">
            </div>
            <div id="editOfRole"></div>
            	
            
        </div>
    </div>
	</form>
    <div id="covered"></div>
    <script type="text/javascript">
  //角色弹出选择
	function selectMutliRole(userId,flag){
	  //alert(userId);
	  //alert(flag);  flag  标识符，1表示新增操作，2表示编辑操作
	    var url= "${ctx}/sltwin/role/multi.do?userId="+userId+"&flag="+flag;
	    $.colorbox({
	        href:url,
	        iframe:true,
	        width:"500",
	        height:"350"
	    });
	}
  
	function selectMutliRole_callback(ret,flag){
		if(flag == 2){
			//alert("22222");
			$("input[name^='roles[']").remove();
			var names = "";
			for (var i in ret) {
				var tmp = ret[i].split(",");
				$("#editOfRole").append("<input type=\"hidden\" name=\"roles["+i+"].id\" value=\""+tmp[0]+"\">");
				names += tmp[1] + "\n";
			}
			$("#roleArea").val(names);
			}else if(flag == 1){
				//alert("1111111");
				$("input[name^='roles[']").remove();
				var names = "";
				for (var i in ret) {
					var tmp = ret[i].split(",");
					$("#addOfRole").append("<input type=\"hidden\" name=\"roles["+i+"].id\" value=\""+tmp[0]+"\">");
					names += tmp[1] + "\n";
				}
				$("#roleArea2").val(names);
			}
		}
    
    $(document).ready(function(){
      $('.popup-btn1').click(function(){
          $('#covered').show()
          $('.popup-box1').show()
        })

      $('.popup-btn2').click(function(){
    	  
    	  var ck = document.getElementsByName("ck");
  		  var ids ="";
  		  for(var x=0;x<ck.length;x++){
  			 if(ck[x].checked){
  				ids+=ck[x].value+",";
  			  }
  		  }
  		  ids = ids.substring(0, ids.length-1);
  		//alert(post_id);
  		if(ids.length>0){
  			 $('#covered').show()
  	         $('.popup-box2').show()
  		}else{
  			confirm("请先勾选！")
  		}
    	  
        })

      $('.popup-btn3').click(function(){
          $('#covered').show()
          $('.popup-box3').show()
        })
      

     

      $('.tc-btn1').click(function(){
          $('.popup-box1').hide()
          $('.popup-box2').hide()
          $('.popup-box3').hide()
          $('#covered').hide()
        })

      $('.tc-btn2').click(function(){
          $('.popup-box1').hide()
          $('.popup-box2').hide()
          $('.popup-box3').hide()
          $('#covered').hide()
        })
    });
  </script>
  <script type="text/javascript">
  function closeDIV(){
      $('.popup-box1').hide()
      $('.popup-box2').hide()
      $('.popup-box3').hide()
      $('#covered').hide()
      $("#editloginName").val("");
      $("#edituserName").val("");
      $("#editdepartment_id").val("");
      $("#editteam_id").val("");
      $("#editposition_id").val("");
      $("#editjobId").val("");
      $("#roleArea").val("");
      $("#editemployed_date").val("");
      $("#editemail").val("");
      
      $("#checkEditloginName").text("");
      $("#checkEdituserName").text("");
      $("#checkEditRole").text("");
      $("#oo").text("");
      $("#checkEditemployed_date").text("");
      $("#checkEditemail").text("");
      
      $("#loginName").val("");
      $("#userName").val("");
      $("#department_id").val("");
      $("#team_id").val("");
      $("#position_id").val("");
      $("#jobId").val("");
      $("#roleArea2").val("");
      $("#employed_date").val("");
      $("#email").val("");
      
      $("#checkLoginName").text("");
      $("#checkUserName").text("");
      $("#checkRole").text("");
      $("#rr").text("");
      $("#checkEmployed_date").text("");
      $("#checkEmail").text("");
    }
  </script>
	<script>
	
	$( 'table tr' ).on( 'hover' , function () {
		$( this ).addClass( 'active' ).siblings().removeClass( 'active' );
	} )
</script>
</body>

</html>
