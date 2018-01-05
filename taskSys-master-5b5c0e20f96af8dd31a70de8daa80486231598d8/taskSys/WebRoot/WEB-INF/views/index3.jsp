<%@ page language="java"  pageEncoding="UTF-8"%>
<%@ page import="cn.taskSys.entity.User" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml"> 
<head>
<%@include file="/common/taglibs.jsp"%> 
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<script type="text/javascript" src="${ctx}/js/jquery.min.js"></script>
<link rel='icon' href="${ctx}/images/ico.png" type=‘image/x-ico’ />
<title>任务管理系统</title>
<link href="${ctx}/css/static/main.css?1=1" rel="stylesheet" type="text/css" />
<link href="${ctx}/css/static/base-global.css" rel="stylesheet" type="text/css" />
<link href="${ctx}/css/static/common.css?5=1" rel="stylesheet" type="text/css" />
<link href="${ctx}/css/static/main.css?1=1" rel="stylesheet" type="text/css" />
<link href="${ctx}/css/static/stylesheet.css" rel="stylesheet" type="text/css" />
<link rel="stylesheet" type="text/css" href="${ctx}/css/right.css" />
<link rel="stylesheet" type="text/css" href="${ctx}/css/pop.css" />

<script type="text/javascript" src="${ctx}/js/jquery.min.js"></script>

<script type="text/javascript" src="${ctx}/js/jquery.validate.js"></script>
<script type="text/javascript" src="${ctx}/js/jquery.metadata.js"></script>
<script type="text/javascript" src="${ctx}/js/alert/g_alert.js?1=1"></script>
<script type="text/javascript" src="${ctx}/js/util/common.js?1=1"></script>
<script type="text/javascript" src="${ctx}/js/My97DatePicker/WdatePicker.js" ></script>

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
    function leftColor(id){
        $(".cur").removeClass('cur');
       
		$("#leftMenu"+id).addClass('cur');
    }
</script>
<script type="text/javascript">

 
$(document).ready(function(){
	$("#leftMenu0").addClass('cur');
	      $('.msg-wake').click(function(){
	          $('.m-box').show()
	          $('#covered').show()
	          
	        })
	        
	      $('.mm-close').click(function(){
	          $('.m-box').hide()
	           $('#covered').hide()
	          
	        })

	      $('.hl-msg').click(function(){
	          $('.m-box').hide()
	        })

	      $('.gly-wake').click(function(){
	          $('.m-box').hide()
	        })

	      $('.gly-wake').click(function(){
	          $('.zhxx-box').show()
	          $('#covered').show()
	        })

	      $('.mm-close').click(function(){
	          $('.zhxx-box').hide()
	           $('#covered').hide()
	        })

	      $('.hl-msg').click(function(){
	          $('.zhxx-box').hide()
	        })

	      $('.msg-wake').click(function(){
	          $('.zhxx-box').hide()
	        })
	     
	        $("#saveForm2").validate({  
				rules:{
					oldPassword:{  
			    		remote: {
			       			url: "${ctx}/user/check/password.do",
			             	type: "post",
			             	data: {  
			             		oldPassword: function() {
			                 		return $("#oldPassword").val();
			              		},
			              		userId: function() {
			                 		return $("#userId").val();
			              		}
			          		}
			       		}  
					},
					password:{  
						equalTo: "#cfmPassword"
					},						
					cfmPassword:{  
						equalTo: "#password"
					}			
				},  
				messages:{
					oldPassword:{  
			    		remote: "原密码不正确！"
			 		},
					password:{
						equalTo: "两次新密码不一致！"
			 		},	 	 			
					cfmPassword:{
						equalTo: "两次新密码不一致！"
			 		}		 		
				}
			});
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
    
    function goBack(){
    	 $('.xgmm-box').hide()
    	 $('.xgtx-box').hide()
         $('#covered').hide()
         $("#oldPassword").val("");
         $("#password").val("");
         $("#cfmPassword").val("");
         $("#head_portrait").val("");
         
         $("#oldPW").text("");
         $("#newPW1").text("");
 		  $("#newPW2").text("");
    }
  
</script>
</head>
 <tags:message content="${message}" type="success" />
 <input type="hidden" id="upPass" name="upPass" value="${upPass }"/>
 <input type="hidden" id="nowDate" name="nowDate" value="${nowDate}"/>
<body>


<div class="header">
        <div class="box fn-clear">
            <span><img src="${ctx}/css/images/logo.png" width="230" height="60"></span>
           
            <div class="fr lh60 hd-txt">
              <shiro:hasPermission name="create_tsak">
            	<c:if test="${preFalg eq 1}">
            		<a href="javascript:(void);" class="xjrw-btn cblue1 mg-r35">+新建任务</a>
            	</c:if>
            	<c:if test="${preFalg eq 2}">
            		<a href="javascript:(void);" class="xjrw-btn1 cblue1 mg-r35">+新建任务</a>
            	</c:if>
              </shiro:hasPermission>
                <div class="message">
                <shiro:hasPermission name="message_tsak">
                    <a href="#" class="cblue1 msg-wake">消息提醒&nbsp; <img src="${ctx}/css/images/hd-arrow.png">
	                    	<div class="upMessageTask">
	                    	<c:if test="${msgCount>0}">
		                    		<span class="num-bg" id="num-bg">${msgCount}</span>
		                    	
		                    </c:if>
		                     <c:if test="${msgCount<=0}">
		                    	<span class=""></span>
		                    </c:if>
		                    </div>
                    </a>
                  </shiro:hasPermission>
                    <div class="m-box hide" id="showMessageId">
                        <div class="xx-arrow"></div>
                        <div class="tit fn-clear">
                            <span class="fl">消息提醒</span>
                            <a href="javascript:(void);" class="mm-close fr"></a>
                        </div>
	                        <ul id="ulId">
	                            <c:forEach items="${mlist}" var="message" >					
									<li><a href="javascript:(void);" onclick="goingPage(${message.id})" >${message.title}</a></li>
								</c:forEach>
	                        </ul>
	                     <c:if test="${msgCount>0}">
		                    	<div class="txt-r pd-t10 pd-b10" id="allIgnore"><a href="javascript:(void);" class="cblue2 hl-msg" onclick="upMessage()">忽略全部</a></div>
		                    </c:if>
		                     <c:if test="${msgCount<=0}">
		                     	<div class="txt-r pd-t10 pd-b10"  id="allIgnore"><span><font color="gray">忽略全部</font></span></div>
		                    </c:if>
                        
                    </div>
                </div>
               
                <div class="mg-l35 gly">
                    <a href="javascript:(void);" class="cblue1 gly-wake"><img style="width: 50px;height: 50px;border-radius: 50%;" src="${ctx}/<%=((User)session.getAttribute("JX_USERINFO")).getHead_portrait()==null?"images/top2.png":((User)session.getAttribute("JX_USERINFO")).getHead_portrait() %>"><%=session.getAttribute("JX_USERINFO")==null?"":((User)session.getAttribute("JX_USERINFO")).getUserName() %>&nbsp;<img src="${ctx}/css/images/hd-arrow.png"></a>
                    <div class="zhxx-box hide">
                        <div class="xx-arrow"></div>
                        <div class="tit fn-clear">
                            <span class="fl">账号管理</span>
                            <a href="javascript:(void);" class="mm-close fr"></a>
                        </div>
                        <ul>
                            <li>
                                <p><%=session.getAttribute("JX_USERINFO")==null?"":((User)session.getAttribute("JX_USERINFO")).getUserName() %></p>
                                <p style="width:228px;word-break:break-all; word-wrap:break-word ;"><%=session.getAttribute("JX_USERINFO")==null?"":((User)session.getAttribute("JX_USERINFO")).getEmail() %></p>
                            </li>
                        </ul>
                        <div class="txt-c pd-t10 pd-b10">
                            <a href="javascript:(void);" class="cblue2 xgmm-btn">修改密码</a>
                            <a href="${ctx}/logout.do" class="tc-close hl-msg mg-l35">退出</a>
                        </div>
                    </div>
                </div>
            </div>
        </div>
        <!-- 修改密码 -->
      <form id="saveForm" name="saveForm" action="${ctx}/user/upw.do" method="post">
      <input type="hidden" id="userId" name="userId" value="<%=session.getAttribute("JX_USERINFO")==null?"":((User)session.getAttribute("JX_USERINFO")).getUserId() %>">
      <input type="hidden" id="pw" name="pw" value="<%=session.getAttribute("JX_USERINFO")==null?"":((User)session.getAttribute("JX_USERINFO")).getPassword() %>">
        <div class="popup xgmm-box" style="margin-left:-195px;">
            <div class="tc-box">
                <div class="title fn-clear">
                    <span>修改密码</span>
                    <a href="javascript:(void);" class="tc-close fr"></a>
                </div>

                <ul class="pd-t5">
                    <li class="fn-clear pd-t15">
                        <label class="fl txt-r mg-r10"><span style="color: red;">*&nbsp;</span>原密码</label>
                        <input  type="password" id="oldPassword" name="oldPassword"  >
                    	<span id="oldPW" style="color: red;"></span>
                    </li>
                    <li class="fn-clear">
                        <label class="fl txt-r mg-r10"><span style="color: red;">*&nbsp;</span>新密码</label>
                        <input type="password" id="password" name="password" >
                    	<span id="newPW1" style="color: red;"></span>
                    </li>
                    <li class="fn-clear">
                        <label class="fl txt-r mg-r10"><span style="color: red;">*&nbsp;</span>确定新密码</label>
                        <input  type="password" id="cfmPassword" name="cfmPassword" >
                    	<span id="newPW2" style="color: red;"></span>
                    </li>
                </ul>
                <div class="txt-c pd-t15">
                    <input class="form_now" type="button" onclick="updatePW()" value="确定">&nbsp;&nbsp;&nbsp;&nbsp;
                    <input class="form_now" type="button" onclick="javascript:goBack()" value="取消">
                </div>
            </div>
        </div>
        </form>
                <!-- 修改密码 -->
      <form id="saveForm1" name="saveForm1" action="${ctx}/user/upw.do" method="post">
     <input type="hidden" id="userId1" name="userId" value="<%=session.getAttribute("JX_USERINFO")==null?"":((User)session.getAttribute("JX_USERINFO")).getUserId() %>">
      <input type="hidden" id="pw1" name="pw" value="<%=session.getAttribute("JX_USERINFO")==null?"":((User)session.getAttribute("JX_USERINFO")).getPassword() %>">
        <div class="popup xgmm-box1" style="margin-left:-195px;">
            <div class="tc-box">
                <div class="title fn-clear">
                    <span>修改密码</span>
                </div>
					<h2>&nbsp;&nbsp;&nbsp;&nbsp; 
						<b style="color: red;">*</b><span>&nbsp;&nbsp;登录成功！由于登录密码过于简单，请修改后再登录！</span>
					</h2>
					<h2>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;  
						(提示：<span style="color: red;">密码由8至16位字母和数字组成,必须以字母开头</span>) 
					</h2>
                <ul class="pd-t5">
                    <li class="fn-clear pd-t15" >
                        <label class="fl txt-r mg-r10"><span style="color: red;">*&nbsp;</span>原密码</label>
                        <input  style="width: 155px;" type="password" id="oldPassword1" name="oldPassword"  value="${oldPassword }" >
                    	<span id="oldPW1" style="color: red;"></span>
                    </li>
                    <li class="fn-clear">
                        <label class="fl txt-r mg-r10"><span style="color: red;">*&nbsp;</span>新密码</label>
                        <input style="width: 155px;" type="password" id="password1" name="password" placeholder="8到16位字母开头和数字组成">
                    	<span id="newPW11" style="color: red;"></span>
                    </li>
                    <li class="fn-clear">
                        <label class="fl txt-r mg-r10"><span style="color: red;">*&nbsp;</span>确定新密码</label>
                        <input  style="width: 155px;" type="password" id="cfmPassword1" name="cfmPassword" placeholder="8到16位字母开头和数字组成">
                    	<span id="newPW22" style="color: red;"></span>
                    </li>
                </ul>
                <div class="txt-c pd-t15">
                    <input class="form_now" type="button" onclick="updatePW1()" value="确定">&nbsp;&nbsp;&nbsp;&nbsp;
                     <input class="form_now" type="button" onclick="javascript:goBack1()" value="返回">
                </div>
            </div>
        </div>
        </form>
            <!-- 修改上传头像 -->
        <form id="saveForm3" name="saveForm3" action="${ctx}/user/upheadportrait.do" method="post" enctype="multipart/form-data">
        <input type="hidden" id="userId2" name="userId" value="<%=session.getAttribute("JX_USERINFO")==null?"":((User)session.getAttribute("JX_USERINFO")).getUserId() %>">
        <div class="popup xgtx-box" style="margin-left:-195px;">
            <div class="tc-box">
                <div class="title fn-clear">
                    <span>上传头像</span>
                    <a href="javascript:(void);" class="tc-close fr"></a>
               </div>
	            <span class="f_title">头像logo：</span>
	            <input type="file" id="file" name="file"/>    
                <div class="txt-c pd-t15">
                    <input class="form_now" type="button" onclick="updatePortrait()" value="确定">&nbsp;&nbsp;&nbsp;&nbsp;
                     <input class="form_now" type="button" onclick="javascript:goBack()" value="返回">
                </div>
            </div>
        </div>
        </form>
        <!-- 修改密码 -->
        <script type="text/javascript">
        	function updatePW1(){
        	var oldPassword = $("#oldPassword1").val();
        	var password = $("#password1").val();
        	var cfmPassword = $("#cfmPassword1").val();
        	var userId = $("#userId1").val();
        	
        	var reg = /^[a-zA-Z][a-zA-Z0-9]{7,15}$/
        	var url = "${ctx}/user/check/password.do";
        	
        	if(oldPassword == null || oldPassword == ""){
        		$("#oldPW1").text("× 原密码不可为空！");
        		return false;
        		$("#oldPassword1").focus();
        	}else{
        		$("#oldPW1").text(" √");
        	}
        	
        	var re = "";
        	$.ajax({ 
				type : "POST",  
				url: url, 
				data : {"userId":userId,"oldPassword":oldPassword},
				dataType : 'text',
				async: false, 
				success: function(data){
						if(data == "false"){
							$("#oldPW1").text("× 原密码不正确！");
							re = 1;
			        		$("#oldPassword1").focus();
						}else{
							$("#oldPW1").text(" √");
						}					
					}
	      	});
        	if(re == 1){
        		return;
        	}
        	
        	if(password == null || password == ""){
        		$("#newPW11").text("× 新密码不可为空！");
        		return false;
        		$("#password").focus();
        	}else if(!reg.test(password)){
        		$("#newPW11").text("× 格式错误,8-16个数字和字符组成并且以字母开头！");
        		return false;
        		$("#password1").val("");
        		$("#password1").focus();
        	}else{
        		$("#newPW11").text(" √");
        	}
        	if(cfmPassword == null || cfmPassword == ""){
        		$("#newPW22").text("× 新密码不可为空！");
        		return false;
        		$("#cfmPassword1").focus();
        	}else if(!reg.test(cfmPassword)){
        		$("#newPW22").text("× 格式错误,8-16个数字和字符组成并且以字母开头！");
        		return false;
        		$("#cfmPassword1").focus();
        		$("#cfmPassword1").val("");
        	}else{
        		$("#newPW2").text(" √");
        	}
        	if(cfmPassword != password){
        		$("#newPW11").text("× 两次输入不一致！");
        		$("#newPW22").text("× 两次输入不一致！");
        		return false;
        		$("#password1").val("");
        		$("#cfmPassword1").val("");
        		$("#password1").focus();
        	}else{
        		$("#newPW11").text(" √");
        		$("#newPW22").text(" √");
        	}
        	updatePassword1();
        }
        
       
        
	        function updatePassword1(){
        	var password = $("#password1").val();
        	var userId = $("#userId1").val();
        	var url = "${ctx}/user/upw.do";
        	
        	$.ajax({ 
				type : "POST",  
				url: url, 
				data : {"userId":userId,"password":password},
				dataType : 'text',
				success: function(data){
						var flag = data;
					if(flag == 1){
						 //$('.xgmm-box').hide();
		                 //$('#covered').hide();
						setTimeout("window.location.href='${ctx}/logout.do'",5000); 
						g_alert('success','密码修改成功请重新登录!','${ctx}/logout.do',"${ctx}"); 
						
					}else if(flag == 2){
						$('.xgmm-box1').hide();
		                 $('#covered').hide();
						g_alert('error','操作失败',"${ctx}");
					}
								
					}
	      	});
        }
	      
	      //用户修改或者上传头像
	        function updatePortrait(){
	    	  var file = $("#file").val();
	    	  if(file == null || file == ""){
	    		  alert("请上传头像");
	    	  }else{
	    		  var formData = new FormData($( "#saveForm3" )[0]);
	                //formData.append("file", document.getElementById("file").files[0]);   
	                
	                $.ajax({ 
	                    type : "POST",  
	                    url: "${ctx}/user/upheadportrait.do",
	                    data: formData,  
	                    async: false,  
	                    cache: false,  
	                    contentType: false,  
	                    processData: false,
	                    success: function(data){
	                        alert(data.msg); 
	                        $('.xgtx-box').hide();
	                        $('#covered').hide();
	                                    
	                        }
	                }); 
	    	  }
	        	
	        }
	        
        function check(){
            var obj=document.getElementById("head_portrait");
            var f=document.getElementById("head_portrait").value;
            if(f!=""&&f!=null){ 
                if(!/\.(gif|jpg|jpeg|png|GIF|JPG|PNG)$/.test(f)){  
                    alert("仅支持gif,jpeg,jpg,png格式！");
                    obj.outerHTML=obj.outerHTML;
                    return false; 
                }else if(f.length>25){
                    alert("您的图片名过长，请缩减！");
                    obj.outerHTML=obj.outerHTML;
                    return false; 
                }else if(/.*[\u4e00-\u9fa5]+.*$/.test(f)){
                    alert("您的图片名含有中文，请更改！");
                    obj.outerHTML=obj.outerHTML;
                    return false; 
                }
            }
        }
        function goBack1(){
		        setTimeout("window.location.href='${ctx}/logout.do'");
		    }
        
        function updatePW(){
        	var oldPassword = $("#oldPassword").val();
        	var password = $("#password").val();
        	var cfmPassword = $("#cfmPassword").val();
        	var userId = $("#userId").val();
        	
        	var reg = /^[a-zA-Z0-9_]{6,12}$/
        		var url = "${ctx}/user/check/password.do";
        	
        	if(oldPassword == null || oldPassword == ""){
        		$("#oldPW").text("× 原密码不可为空！");
        		return false;
        		$("#oldPassword").focus();
        	}else{
        		$("#oldPW").text(" √");
        	}
        	
        	var re = "";
        	$.ajax({ 
				type : "POST",  
				url: url, 
				data : {"userId":userId,"oldPassword":oldPassword},
				dataType : 'text',
				async: false, 
				success: function(data){
						if(data == "false"){
							$("#oldPW").text("× 原密码不正确！");
							re = 1;
			        		$("#oldPassword").focus();
						}else{
							$("#oldPW").text(" √");
						}					
					}
	      	});
        	if(re == 1){
        		return;
        	}
        	
        	if(password == null || password == ""){
        		$("#newPW1").text("× 新密码不可为空！");
        		return false;
        		$("#password").focus();
        	}else if(!reg.test(password)){
        		$("#newPW1").text("× 格式错误,6-12个数字或字符！");
        		return false;
        		$("#password").val("");
        		$("#password").focus();
        	}else{
        		$("#newPW1").text(" √");
        	}
        	if(cfmPassword == null || cfmPassword == ""){
        		$("#newPW2").text("× 新密码不可为空！");
        		return false;
        		$("#cfmPassword").focus();
        	}else if(!reg.test(cfmPassword)){
        		$("#newPW2").text("× 格式错误,6-12个数字或字符！");
        		return false;
        		$("#cfmPassword").focus();
        		$("#cfmPassword").val("");
        	}else{
        		$("#newPW2").text(" √");
        	}
        	if(cfmPassword != password){
        		$("#newPW1").text("× 两次输入不一致！");
        		$("#newPW2").text("× 两次输入不一致！");
        		return false;
        		$("#password").val("");
        		$("#cfmPassword").val("");
        		$("#password").focus();
        	}else{
        		$("#newPW1").text(" √");
        		$("#newPW2").text(" √");
        	}
        	
        	updatePassword();
        }
        
        function updatePassword(){
        	var password = $("#password").val();
        	var userId = $("#userId").val();
        	var url = "${ctx}/user/upw.do";
        	
        	$.ajax({ 
				type : "POST",  
				url: url, 
				data : {"userId":userId,"password":password},
				dataType : 'text',
				success: function(data){
						var flag = data;
					if(flag == 1){
						 //$('.xgmm-box').hide();
		                 //$('#covered').hide();
						setTimeout("window.location.href='${ctx}/logout.do'",5000); 
						g_alert('success','密码修改成功请重新登录!','${ctx}/logout.do',"${ctx}"); 
						
					}else if(flag == 2){
						$('.xgmm-box').hide();
		                 $('#covered').hide();
						g_alert('error','操作失败',"${ctx}");
					}
								
					}
	      	});
        }
        
       $(document).ready(function(){
              $('.xgmm-btn').click(function(){
            	  $("#oldPassword").val("");
                  $('#covered').show()
                  $('.xgmm-box').show()
                  $('.zhxx-box').hide()
                  
                })
              
                $('.xgtx-btn').click(function(){
                	alert(1);
                  $("#head_portrait").val("");
                  $('#covered').show()
                  $('.xgtx-box').show()
                  $('.zhxx-box').hide()
                  
                })

              $('.tc-close').click(function(){
                  $('.xgmm-box').hide()
                  $('.xgtx-box').hide()
                  $('#covered').hide()
                  $("#oldPassword").val("");
                  $("#password").val("");
                  $("#cfmPassword").val("");
                  $("#head_portrait").val("");
                  
                  $("#oldPW").text("");
                  $("#newPW1").text("");
          		  $("#newPW2").text("");
                })
                
              $('.tc-btn1').click(function(){
                  $('.xgmm-box').hide()
                  $('#covered').hide()
                })
               $('.tc-btn11').click(function(){
                  $('.xgmm-box').hide()
                  $('#covered').hide()
                })

              $('.tc-btn2').click(function(){
                  $('.xgmm-box').hide()
                  $('#covered').hide()
                })
            }); 
            
        </script>
    </div>

    <div class="wrap fn-clear" style="height: 550px;" >
    	<div class="menu-list fl" id="appwrap" style="height: 100%;overflow-y:auto; overflow-x:hidden;" >
    		<ul>
    			<li class="title">菜单栏</li>
    			<c:forEach var="smenu" items="${smenulist}" varStatus="status" >
    				<shiro:hasPermission name="${smenu.privileges_code}"> 
		    			<li id="leftMenu${status.index}" name="leftMenu" ><a target="mima" onclick="leftColor('${status.index}');" href="${ctx}${smenu.menu_url}" >${smenu.menu_name}</a></li>
	               	</shiro:hasPermission> 
                </c:forEach>
    		</ul>
    	</div>

    	<div class="content1" id="mainarea" style="height: 100%;" >
    		<iframe id="mima" name="mima" width="100%" style="height:100%;overflow: hidden;" frameborder="0" src="${ctx}/welcome.do"></iframe>
    	</div>
    </div>
	
	<!-- 新建任务 -->
        <div class="popup xjrw-box" style="margin-left:-195px;">
            <div class="tc-box">
                <div class="title fn-clear">
                    <span>新建任务</span>
                    <a href="javascript:(void);" class="tc-close fr"></a>
                </div>

                <ul class="pd-t5">
                    <li class="fn-clear" >
                        <label class="fl txt-r mg-r10" >任务标题</label>
                        <input style="width: 120px;height: 21px;border:1px #dcdcdc solid;" type="text" id="taskname" name="taskname"/>
                        <span id="tasknameText" style="display:none;"><font color="red">任务标题不能为空</font></span>
                    </li>
                    
                    <li class="mg-t10">
                    <label class="fl txt-r mg-r10" >任务内容</label>
                    <textarea class="wbqy" style="width:230px;resize: none;" 
	                    maxlength=300; placeholder="最多输入300个中文字符" cols="" id="taskContent" name="taskContent" value="${taskContent}" onblur="validateXml2(this)"></textarea>
                    </li>
                    <li class="fn-clear" >
                        <label class="fl txt-r mg-r10" style="width: 80px;">完成时间</label>
                        <input style="width: 120px;" readonly="readonly" type="text" id="expectEndTime" name="expectEndTime" 
	                         onclick="WdatePicker()" onfocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd'})" class="Wdate" value="${expectEndTime}">
	                         <span id="expectTime" style="display:none;"><font color="red">时间不能为空</font></span>
	                         <span id="content" style="display:none;align:right"><font color="red">内容不能为空</font></span>
                    </li>
                    <li class="fn-clear" >
                        <label class="fl txt-r mg-r10" style="width: 80px;">工作量</label>
                    	<input style="width: 120px;height: 21px;border:solid gray 1px;" type="text" id="taskWorkTime" name="taskWorkTime" onblur="validateWorkTime(this.value);" />&nbsp;人时&nbsp;&nbsp;
                    	<a class="discribtion">工作量单位说明</a>
                    	<span id="taskWorkTimePrompt" style="display:none;"><font color="red">工作量不能为空</font></span>
                    </li>
                    <li class="fn-clear" ><span id="discrib" style="display:none;"><p><b>工作量人时:</b>指1个人在没有任何干扰的情况下，完成该项任务所需花费的小时数，也称为理想人时。</p></span></li>
                </ul>
                <div class="txt-c pd-t15">
                    <a href="javascript:(void);" id="subcommit" class="tc-btn1" onclick="saveTask()" >保&nbsp;&nbsp;存</a>
                    <a href="javascript:(void);" class="tc-btn2 mg-l15" onclick="cancelTask()">取&nbsp;&nbsp;消</a>
                </div>
            </div>
        </div>
        
        
        	<!-- 开发、需求新建任务 -->
        <div class="popup xjrw-box1" style="margin-left:-195px;">
            <div class="tc-box">
                <div class="title fn-clear">
                    <span>新建任务</span>
                    <a href="javascript:(void);" class="tc-close fr"></a>
                </div>
				<input type="hidden" name="projectStageCode" id="projectStageCode"/>
                <ul class="pd-t5">
                    <li class="fn-clear" >
                        <label class="fl txt-r mg-r10" >任务标题</label>
                        <input style="width: 120px;height: 21px;border:1px #dcdcdc solid;" type="text" id="taskname1" name="taskname1" value="${taskname}"/>
                        <span id="tasknameText" style="display:none;"><font color="red">任务标题不能为空</font></span>
                    </li>
                    
                    <li class="mg-t10">
                    <label class="fl txt-r mg-r10" >任务内容</label>
                    <textarea class="wbqy" style="width:230px;resize: none;"  
	                    maxlength=300; placeholder="最多输入300个中文字符" cols="" id="taskContent1" name="taskContent1" value="${taskContent}" onblur="validateXml2(this)"></textarea>
                    </li>
                    <li class="fn-clear" >
                        <label class="fl txt-r mg-r10" >完成时间</label>
                        <input style="width: 120px;" readonly="readonly" type="text" id="expectEndTime1" name="expectEndTime1" 
	                         onclick="WdatePicker()" onfocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd'})" class="Wdate" value="${expectEndTime}">
	                    <span id="content1" style="display:none;align:right"><font color="red">内容不能为空</font></span>
	                    <span id="expectTime1" style="display:none;"><font color="red">时间不能为空</font></span>
                    </li>
                    <li class="fn-clear" >
                        <label class="fl txt-r mg-r10" >工作量</label>
                    	<input style="width: 120px;height: 21px;border:1px #dcdcdc solid;" type="text" id="taskWorkTime1" name="taskWorkTime1" oninput="validateWorkTime(this.value);" />&nbsp;人时&nbsp;&nbsp;
                    	<a class="discribtion1">工作量单位说明</a>
                    	<span id="taskWorkTimePrompt1" style="display:none;"><font color="red">工作量不能为空</font></span>
                    </li>
                    <li class="fn-clear" ><span id="discrib1" style="display:none;"><p><b>工作量人时:</b>指1个人在没有任何干扰的情况下，完成该项任务所需花费的小时数，也称为理想人时。</p></span></li>
                <li class="fn-clear" >
                 	<label class="fl txt-r mg-r10" >项目名称</label>
                 		<input class="inputC" id="proName" name="projectName" placeholder="请输入项目关键字" style="border: 1px solid #94afc8;width:120px;height: 22px;line-height: 22px;color: #343333;text-indent: 8px;vertical-align: middle;border-radius: 3px;"
							 onkeydown="getProjectName(this.value)" onkeyup="getProjectName(this.value)" onblur="changeF(this.value);">
							 <span id="selText1" style="display:none;"><font color="red">项目名称不能为空</font></span>
							 <span id="projectCode1" style="display:none;"><font color="red">没有此项目！！！</font></span>
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
                    	<span id="projectStage1" style="display:none;"><font color="red">项目阶段不能为空</font></span>
                 </li>
                 
                </ul>
                <div class="txt-c pd-t15">
                    <a href="javascript:(void);" id="subcommit1" class="tc-btn11" onclick="saveTask1()" >保&nbsp;&nbsp;存</a>
                    <a href="javascript:(void);" class="tc-btn2 mg-l15" onclick="cancelTask()">取&nbsp;&nbsp;消</a>
                </div>
            </div>
        </div>
    

    <div id="covered" onclick="closeDiv()"></div>
    <div id="covered2"></div>
    <div id="covered3"></div>
    <div id="covered4"></div>
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

$('.popup-btn1').click(function(){
          $('#covered').show()
          $('.popup-box1').show()
        })

      $('.popup-btn2').click(function(){
          $('#covered').show()
          $('.popup-box2').show()
        })
    $(document).ready(function(){
      

      $('.popup-btn3').click(function(){
          $('#covered').show()
          $('.popup-box3').show()
        })
        
       $('.xjrw-btn').click(function(){
                  $('#covered').show()
                  $('.xjrw-box').show()
                  $('.zhxx-box').hide()
                  $('.xgmm-box').hide()
                  $('.m-box').hide()
                  
                })
       
       $('.xjrw-btn1').click(function(){
                  $('#covered').show()
                  $('.xjrw-box1').show()
                })
       
       $('.tc-close').click(function(){
                  $('.xgmm-box').hide()
                  $('.xjrw-box').hide()
                  $('.xjrw-box1').hide()
                  $('#covered').hide()
                  $("#content").hide();
     			  $("#expectTime").hide();
     			  $("#taskWorkTimePrompt").hide();
     			  $("#taskname").val('');
                  $("#taskContent").val('');
				  $("#expectEndTime").val('');
				  $("#taskWorkTime").val('');
				  $("#taskname1").val('');
				  $("#taskContent1").val('');
				  $("#expectEndTime1").val('');
				  $("#taskWorkTime1").val('');
				  $("#projectStage").val('');
				  $("#projectCode").val('');
				  $("#proName").val('');
                })

            

              $('.tc-btn2').click(function(){
                  $('.xgmm-box').hide()
                  $('.xjrw-box').hide()
                  $('.xjrw-box1').hide()
                  $('#covered').hide()
                })

      $('.tc-close').click(function(){
          $('.popup-box1').hide()
          $('.popup-box2').hide()
          $('.popup-box3').hide()
          $('#covered').hide()
        })

      $('.tc-btn1').click(function(){
          $('.popup-box1').hide()
          $('.popup-box2').hide()
          $('.popup-box3').hide()
          $('#covered').show()
        })
       $('.tc-btn11').click(function(){
          $('.popup-box1').hide()
          $('.popup-box2').hide()
          $('.popup-box3').hide()
          $('#covered').show()
        })
       

      $('.tc-btn2').click(function(){
          $('.popup-box1').hide()
          $('.popup-box2').hide()
          $('.popup-box3').hide()
          $('#covered').hide()
        })
    }); 
        
     function cancelTask(){
    	    $("#tasknameText").hide();
     		$("#content").hide();
     		$("#expectTime").hide();
     		$("#taskWorkTimePrompt").hide();
     		$("#taskname").val('');
     		$("#taskContent").val('');
			$("#expectEndTime").val('');
			$("#taskWorkTime").val('');
			$("#projectStage").val('');
			$("#projectCode").val('');
			$("#proName").val('');
			$("#taskname1").val('');
			$("#taskContent1").val('');
			$("#expectEndTime1").val('');
			$("#taskWorkTime1").val('');
     }

     function saveTask(){
    	var taskname = $("#taskname").val();
     	var	taskContent = $("#taskContent").val();
     	var	expectEndTime = $("#expectEndTime").val();
     	var	taskWorkTime = $("#taskWorkTime").val();
     	var nowDate = $("#nowDate").val();
     	//alert(nowDate);
     	if(taskname == "" || taskname == null){
            $("#tasknameText").show();
            return false;
        }else{
            $("#tasknameText").hide();
        }
     	if(taskContent == "" || taskContent == null){
     		$("#content").show();
	     	return false;
     	}else{
     		$("#content").hide();
     		}
     		
     	if(expectEndTime == "" || expectEndTime == null){
     		$("#expectTime").show();
     		return false;
     		}else{
     			$("#expectTime").hide();
     		} 
     	 if(expectEndTime<nowDate){
			alert("亲！完成时间需要大于当前日期哦！！！");
			return false;
		} 
		
		/* if($.trim(taskWorkTime)!=null && $.trim(taskWorkTime)!=''){
			var result = isInteger(taskWorkTime);
			if(!result){
				alert("工作量输入格式不正确，请输入整数！");
				return false;
			}
		} */
		
		if(taskWorkTime == "" || taskWorkTime == null){
     		$("#taskWorkTimePrompt").show();
	     	return false;
     	}else{
     		$("#taskWorkTimePrompt").hide();
     	}
		
		url = "${ctx}/admin/saveTask.do";
			  $.ajax({
				type : "POST", 
				url : url, 
				data : {"taskname":taskname,"taskContent":taskContent,"expectEndTime":expectEndTime,"taskWorkTime":taskWorkTime},
				success : function(data){
					g_alert('success','操作成功','${ctx}/index.do?menu=10000000000001',"${ctx}");
					$("#taskname").val('');
					$("#taskContent").val('');
					$("#expectEndTime").val('');
					$("#taskWorkTime").val('');
				}
			});
			
		  $('.tc-btn1').click(function(){
                 $('.xgmm-box').hide()
                 $('.xjrw-box').hide()
                 $('.xjrw-box1').hide()
                 $('#covered').hide()
               });
		} 
		
		
		function saveTask1(){
		var taskname = $("#taskname1").val();
     	var	taskContent = $("#taskContent1").val();
     	var	expectEndTime = $("#expectEndTime1").val();
     	var	taskWorkTime = $("#taskWorkTime1").val();
     	var	projectName = $("#proName").val();
     	var	projectCode = $("#projectCode").val();
     	var	projectStage = $("#projectStage").val();
     	//var	projectStageCode = $("#projectStageCode").val();
     	var nowDate = $("#nowDate").val();
     	//alert(projectStageCode);
     	if(taskname == "" || taskname == null){
            $("#tasknameText").show();
            return false;
        }else{
            $("#tasknameText").hide();
        }
     	
     	if(taskContent == "" || taskContent == null){
     		$("#content1").show();
	     	return false;
     	}else{
     		$("#content1").hide();
     		}
     		
     	if(expectEndTime == "" || expectEndTime == null){
     		$("#expectTime1").show();
     		return false;
     		}else{
     			$("#expectTime1").hide();
     		} 
     	 if(expectEndTime<nowDate){
			alert("亲！完成时间需要大于当前日期哦！！！");
			return false;
		} 
		
		if(taskWorkTime == "" || taskWorkTime == null){
     		$("#taskWorkTimePrompt1").show();
	     	return false;
     	}else{
     		$("#taskWorkTimePrompt1").hide();
     	}
		
		if(projectName == "" || projectName == null){
			$("#selText1").show();
			$("#projectCode1").hide();
     		return false;
     		}else{
     			$("#selText1").hide();
     			if(projectCode == "" || projectCode == null){
				$("#projectCode1").show();
	     		return false;
	     		}else{
	     			$("#projectCode1").hide();
				}
		}
		
		if(projectStage == "" || projectStage == null){
			$("#projectStage1").show();
     		return false;
     		}else{
     			$("#projectStage1").hide();
		}
		
			
		url = "${ctx}/admin/saveTask.do";
			  $.ajax({
				type : "POST", 
				url : url, 
				data : {"taskname":taskname,"taskContent":taskContent,"expectEndTime":expectEndTime,"taskWorkTime":taskWorkTime,"projectName":projectName,"projectCode":projectCode,"projectStage":projectStage},
				success : function(data){
					g_alert('success','操作成功','${ctx}/index.do?menu=10000000000001',"${ctx}");
					$("#taskname1").val('');
					$("#projectStage").val('');
					$("#projectCode").val('');
					$("#proName").val('');
					$("#taskContent1").val('');
					$("#expectEndTime1").val('');
					$("#taskWorkTime1").val('');
				}
			});
			
          $('.tc-btn11').click(function(){
                 $('.xgmm-box').hide()
                 $('.xjrw-box').hide()
                 $('.xjrw-box1').hide()
                 $('#covered').hide()
               });
		} 
		
		
    function upMessage(){
		url = "${ctx}/upMessage.do";
			  $.ajax({
				type : "POST", 
				url : url,
				data : {},
				success : function(){
					$("#num-bg").html("");
					$("#num-bg").removeClass("num-bg");
					$("#allIgnore").html("<span><font color=\"gray\">忽略全部</font></span>");
					$("#ulId li").remove();
					$('#covered').hide();
					$('.m-box').hide()
				}
			});
		} 
	function goingPage(id){
	
		  url = "${ctx}/message/messageHandle.do";
		  $.ajax({
			type : "GET", 
			url : url,
			data : "id="+id, 
			dataType : "json", 
			success : function(data){
				if(data.list!=null){
					if(data.list.length>0){
						$("#num-bg").html(data.list.length);
						$("#allIgnore").html("<a href=\"javascript:(void);\" class=\"cblue2 hl-msg\" onclick=\"upMessage()\">忽略全部</a>");
					}else{
						$("#num-bg").html("");
						$("#num-bg").removeClass("num-bg");
						$("#allIgnore").html("<span><font color=\"gray\">忽略全部</font></span>");
						$("#showMessageId").hide();
					}
					$("#ulId li").remove();
					for ( var i = 0; i < data.list.length; i++) {
						$("#ulId").append("<li><a href=\"javascript:(void);\" onclick=\"goingPage("+(data.list[i]).id+")\" >"+(data.list[i]).title+"</a></li>");
					}
					//--------------- 跳转到工作台-------
					if(data.focusMenu=="11"){
						 $(".cur").removeClass('cur');
						 $("#leftMenu0").addClass('cur');
					}
					if(data.focusMenu=="21"){
						 $(".cur").removeClass('cur');
						 $("#leftMenu0").addClass('cur');
					}
					if(data.focusMenu=="31"){
						 $(".cur").removeClass('cur');
						 $("#leftMenu0").addClass('cur');
					}
					//--------------- 跳转到我的任务-------
					if(data.focusMenu=="12"){
						 $(".cur").removeClass('cur');
						 $("#leftMenu1").addClass('cur');
					}
					if(data.focusMenu=="32"){
						 $(".cur").removeClass('cur');
						 $("#leftMenu1").addClass('cur');
					}
					//--------------- 跳转到我创建的任务-------
					if(data.focusMenu=="22"){
						 $(".cur").removeClass('cur');
						 $("#leftMenu2").addClass('cur');
					}
					//--------------- 跳转到我负责的任务-------
					if(data.focusMenu=="23"){
						 $(".cur").removeClass('cur');
						 $("#leftMenu3").addClass('cur');
					}
				}
				var newUrl="${ctx}"+data.result+"?flag="+data.flag+"&taskInfoId="+data.taskInfoId+"&taskStatus="+data.taskStatus;
				$("#mima").attr("src",newUrl);
				$('#covered').hide();
				$('.m-box').hide()
			}
		});
		
	}	
	  
	  $(document).ready(function(){
		var upPass = $("#upPass").val();
	 	if(upPass==2){
	                  $('#covered2').show()
	                  $('.xgmm-box1').show()
		}else{
			 $('#covered2').hide()
	         $('.xgmm-box1').hide()
	         }
        
      }); 
  </script>
  <script type="text/javascript">
	function closeDiv(){  
	    $("#covered").hide();
	    $('.m-box').hide()
	    $('.zhxx-box').hide()
	    $('.xgmm-box').hide()
        $("#oldPassword").val("");
        $("#password").val("");
        $("#cfmPassword").val("");
        $("#oldPW").text("");
        $("#newPW1").text("");
		$("#newPW2").text("");
		$('.xjrw-box').hide()
		$('.xjrw-box1').hide()
		$('.popup-box1').hide()
		$('.popup-box2').hide()
		$('.popup-box3').hide()

	};  
	
	function validateWorkTime(value){
		if($.trim(value)!=null && $.trim(value)!=''){
			var result = isNumericMath(value);
			if(!result){
				alert("请输入不大于40的工作量（可以保留一位小数）！");
				$("#taskWorkTime").val("");
				$("#taskWorkTime1").val("");
				return false;
			}
		}
	}
</script>


<link rel="stylesheet" href="${ctx}/js/jquery_ui/jquery-ui.css">
<script src="${ctx}/js/jquery_ui/jquery-ui.js"></script>
<script type="text/javascript">
    var availableTags = new Array();
    var availableCodes = new Array();
    var availableManagers = new Array();
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
						availableTags = new Array();
						availableCodes = new Array();
						for ( var i = 0; i < data.list.length; i++) {
							availableTags[i]=data.list[i].proname;
							availableCodes[i]=data.list[i].code;
							availableManagers[i]=data.list[i].isdistribution;
						}
						$( "#proName" ).autocomplete({
					    	source: availableTags
					    });
					}
				}
	    	}); 
		}
	}
	
	function changeF(value) {
		//alert(value);
		for ( var i = 0; i < availableTags.length; i++) {
			//alert(availableTags[i]);
 			if(value==availableTags[i]){
 				if(availableManagers[i]==1){
					$("#projectCode").val(availableCodes[i]);
					break;
				}else{
					$("#projectCode").val(availableCodes[i]);
					alert("该项目不允许分配任务！");
					$( "#proName" ).val("");
					$("#projectCode").val("");
				}
			}else{
				$("#projectCode").val('');
			}
		}
	}
</script>
<!-- jquery-ui end -->
</body>
</html>
 