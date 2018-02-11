<%@ page language="java"  pageEncoding="UTF-8"%>
<%@include file="/common/taglibs.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<link rel='icon' href="${ctx}/images/ico.png" type=‘image/x-ico’ />
<title>日程安排系统</title>
<link rel="stylesheet" type="text/css" href="${ctx}/css/static/base-global.css"  media="screen" />
<link rel="stylesheet" type="text/css" href="${ctx}/css/static/common.css"  media="screen" />
<link rel="stylesheet" type="text/css" href="${ctx}/css/static/main.css"  media="screen" />
<script type="text/javascript" src="${ctx}/js/static/jquery/jquery-1.7.2.min.js"></script>
<script type="text/javascript" src="${ctx}/js/static/jquery/jquery.cookie.js"></script>
<script type="text/javascript" src="${ctx}/js/util/common.js"></script>
<script src="${ctx}/js/artDialog4.1.7/artDialog.source.js?skin=aero"></script>
<script src="${ctx}/js/artDialog4.1.7/iframeTools.source.js"></script>
<script type="text/javascript">
	if(window!=top){
 		window.parent.location.href="${ctx}/login";
	}
</script>
</head>  
<style>
.zc-box {
	width:318px;
	height:400px;
}
</style>
<body style="overflow-y: auto;">

<div class="header" style="background:#fff;">
    	<div class="box fn-clear" style="width:1440px;">
    		<span><img src="${ctx}/css/images/logo_21.png" width="199" height="56"></span>
    		
    	</div>
    </div>

    <div class="login-box" id="login">
    	<div class="login-img">
        <div class="wd760">
          <div class="sr-box fr mg-t65">
            <h2>用户登录：</h2>
           <span style="color: red;">${message}</span> 
            <form action="${ctx}/login" method="post" id="form1">
            <ul class="mg-l29">
              <li class="fn-clear mg-t35">
                <input id="username" name="username" type="text"  placeholder="用户名" value="${login.username}" onclick="focusMeByAll(this,'');" onblur="blurMeByAll(this,''),validateXml1(this);" maxlength="35" class="fl srk">
              </li>
              <li class="fn-clear mg-t35">
               <input id="password" name="password" type="password" placeholder="密   码" value="" onclick="focusMeByAll(this,'');" onblur="blurMeByAll(this,''),validateXml1(this);" maxlength="35" class="fl srk1">
              </li>
              <li class="fn-clear mg-t35" style="border:none;">
                <span class="fl cgray1"><input name="ck_auto" id="ck_auto" type="checkbox" value="1" onclick="SaveAuto()"  class="vt-t">&nbsp;自动登录</span>
                <span class="fl cgray1 mg-l20"><input name="ck_rmbUser2" id="ck_rmbUser2" onclick="Save()"  type="checkbox" value="" class="vt-t">&nbsp;记住密码</span>
               	<span class="fl cgray1" style="float: right;"> <a href="javascript:void(0)" onclick="goRegister()">>>去注册</a></span>
                <input name="ck_rmbUser" id="ck_rmbUser"  value=""  type="hidden" class="vt-t">
              </li>
            </ul>
            <div class="txt-c mg-t15">
             <input class="login-button" type="submit" value="登&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;录" />
             
            </div>
            </form>
          </div>
        </div>
    		
    	</div>
    </div>
    <div class="login-box" id="register" style="display: none;">
    	<div class="login-img">
        <div class="wd760">
          <div class="zc-box sr-box fr mg-t65">
            <h2>用户注册：</h2>
           <span style="color: red;height: 100px" id="msg" ></span> 
            <form action="${ctx}/login/register" method="post" id="form2">
            <ul class="mg-l29">
              <li class="fn-clear mg-t35">
                	<input id="" name="name" type="text" placeholder="姓名" onblur="validate(this);" maxlength="35" class="fl srk"/>
              </li>
              <li class="fn-clear mg-t35">
                <input id="" name="email" type="text" placeholder="邮箱" value=""  onblur="validate(this);" maxlength="35" class="fl srk">
              </li>
              <li class="fn-clear mg-t35">
                <input id="" name="username" type="text" placeholder="登陆名" value=""  onblur="validate(this);" maxlength="35" class="fl srk">
              </li>
              <li class="fn-clear mg-t35">
               <input id="password1" name="password" type="password" placeholder="密码" value=""  onblur="validate(this);" maxlength="35" class="fl srk1">
              </li>
              <li class="fn-clear mg-t35">
               <input id="password2" name="qrpassword" type="password" placeholder="确认密码" value=""  onblur="validate(this);" maxlength="35" class="fl srk1">
              </li>
            </ul>
            <div class="txt-c mg-t15">
             <input type = "button" class="login-button" id="registerBtn" value="注&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;册" />
             
            </div>
            </form>
          </div>
        </div>
    		
    	</div>
    </div>
    <div class="txt-c cgray3" style="margin-top: 15px;">Copyright&nbsp;©&nbsp;2018&nbsp;SR&nbsp;All&nbsp;Rights&nbsp;Reserved&nbsp;</div>

</body>
</html>

<script  type="text/javascript">

		//选中“自动登录”的同时选中”记住密码“
		$("#ck_auto").click(function(){
		    if($(this).attr('checked')){
		         $("#ck_rmbUser2").attr('checked',true);
		         $("#ck_rmbUser2").attr('disabled',true);
		         $("#ck_rmbUser").val("1");
		         
		    }else{
		    	//$("#ck_rmbUser").attr('checked',false);
		    	$("#ck_rmbUser2").attr('disabled',false);
		    	$("#ck_rmbUser").val("1");
		   }
		});
		$("#ck_rmbUser2").click(function(){
		  if($(this).attr('checked')){
		         $("#ck_rmbUser").val("1");
		    }else{
		    	$("#ck_rmbUser").val("");
		   }
		});
    //重设垂直居中 
    window.onload = resize()
    function resize(){  
      window.onload = fVericalAlignBody()
       if(!window.onresize){  
       window.onresize =  fVericalAlignBody;  
       }else{  
        var old = window.onresize  
        onresize = function (){  
         fVericalAlignBody();  
         old();  
       }  
     }  
    }
    
    function  fVericalAlignBody(){  
      var nBodyHeight = 710;
      var nClientHeight = document.documentElement.clientHeight;
      if(nClientHeight >= nBodyHeight){
        var nDis = (nClientHeight - nBodyHeight)/2;
        document.body.style.paddingTop = nDis + 'px';
      }else{
        document.body.style.paddingTop = '0px';
      }
    }   
    
  // 输入框内容点击变色
  function focusMeByAll(which,defalutWord){
      var val = $(which).val();
      $(which).css("color",'#666');
      if(val==defalutWord)
        $(which).val("");
    }

    function blurMeByAll(which,defalutWord){
      var val = $(which).val().replace(/(^\s*)|(\s*$)/g, "");
        if(val==""){
        $(which).val(defalutWord);
        $(which).css("color",'#ccc');
        }
    }
    
 	$(document).ready(function () {
			
	});

	function formSubmit() {

		form1.submit();
	}
	function Save() {
	}
	function SaveAuto() {
	}
	function goRegister(){
		$('#login').hide();
		$('#register').show();
		$('.zc-box').height(450);
		$(":input[placeholder]").each(function(){
			$(this).val("");
		});
	}
	function validate(type){
		$("#msg").text("");
		if($(type).val() == ""){
			$("#msg").text($(type).attr('placeholder')+"不能为空！");
			return false;
		}else if($("#password1").val()!="" && $("#password2").val()!="" && $("#password1").val() != $("#password2").val()){
			$("#msg").text("两次密码不一样，请重新输入！");
			return false;
		}
		return true; 
	}
	$('#registerBtn').click(function(){
		//遍历 id为form2 下 所有包含placeholder属性的input
		$('#form2 :input[placeholder]').each(function(){
			if(validate(this)==false){
				return false;
			}
		});
		var win = art.dialog.open.origin;//来源页面
		if($("#msg").text() ==""){
			$.ajax({
				type:"POST",
				url:"${ctx}/login/register",
				data: $('#form2').serialize(),
				dataType:"text",
				async:false, //false 同步 
				success:function(data){
					if(data =='true'){
						art.dialog.alert("注册成功！",function (){
			 				win.location ="${ctx}/schedule/now";
			 			});
					}else if(data =='1'){
						$("#msg").text("登陆名已存在！");
					}
				},
				error:function(e){
					alert("系统异常");
				}
				
			});
		}
	})
	
</script>