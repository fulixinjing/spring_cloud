<%@ page language="java"  pageEncoding="UTF-8"%>
<%@include file="/common/taglibs.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>任务管理系统</title>
<link rel="stylesheet" type="text/css" href="${ctx}/css/static/base-global.css"  media="screen" />
<link rel="stylesheet" type="text/css" href="${ctx}/css/static/common.css"  media="screen" />
<link rel="stylesheet" type="text/css" href="${ctx}/css/static/main.css"  media="screen" />
<script type="text/javascript" src="${ctx}/js/static/jquery/jquery-1.7.2.min.js"></script>
<script type="text/javascript" src="${ctx}/js/static/jquery/jquery.cookie.js"></script>
<script type="text/javascript" src="${ctx}/js/util/common.js"></script>
<script type="text/javascript">
	if(window!=top){
 		window.parent.location.href="${ctx}/login.do";
	}
</script>
<script type="text/javascript">
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
    
</script>

</head>  

<body style="overflow-y: auto;">
<div class="header" style="background:#fff;">
    	<div class="box fn-clear" style="width:1440px;">
    		<span><img src="${ctx}/css/images/logo2.png" width="182" height="60"></span>
    		
    	</div>
    </div>

    <div class="login-box">
    	<div class="login-img">
        <div class="wd760">
          <div class="resr-box fr mg-t130">
          
            <h2>试用服务已经停止--请转到生产地址：</h2>
            <h2>↓↓↓</h2>
            <a href="http://10.150.80.218:8080/taskSys/"><font style="color:red">http://10.150.80.218:8080/taskSys/</font></a>
         	<h2>生产服务器上有些任务没有同步--可以重新(新建任务)</h2>
          </div>
        </div>
    		
    	</div>
    </div>
    <div class="txt-c cgray3" style="margin-top: 15px;" >Copyright&nbsp;©&nbsp;2014&nbsp;SR&nbsp;All&nbsp;Rights&nbsp;Reserved&nbsp;</div>
    
    <script>
    	
	  //选中“自动登录”的同时选中”记住密码“
		$("#ck_auto").click(function(){
		    if($(this).attr('checked')){
		         $("#ck_rmbUser2").attr('checked',true); 
		         $("#ck_rmbUser2").attr('disabled',true);
		    }else{
		    	 $("#ck_rmbUser2").attr('disabled',false);
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
    </script>
</body>
</html>
<script type="text/javascript">
		 $(document).ready(function () {
			 if($("#ck_auto").attr("checked", true)){
					$("#ck_rmbUser2").attr("checked", true);
					$("#ck_rmbUser2").attr("disabled", true);
				}
			 
			if ($.cookie("rmbUser") == "true") {
				$("#ck_rmbUser2").attr("checked", true);
				$("#username").val($.cookie("username"));
				$("#password").val($.cookie("password"));
			}
			if ($.cookie("auto") == "true") {
				$("#ck_auto").attr("checked", true);
			}
		});
	function formSubmit() {

		form1.submit();
	}
	function Save() {
	}
	function SaveAuto() {
	}
/*
	//记住用户名密码
	function Save() {
		if ($("#ck_rmbUser").attr("checked")) {
			var str_username = $("#username").val();
			var str_password = $("#password").val();
			$.cookie("rmbUser", "true", {
				expires : 7
			}); //存储一个带7天期限的cookie
			$.cookie("username", str_username, {
				expires : 7
			});
			$.cookie("password", str_password, {
				expires : 7
			});
		} else {
			$.cookie("rmbUser", "false", {
				expire : -1
			});
			$.cookie("username", "", {
				expires : -1
			});
			$.cookie("password", "", {
				expires : -1
			});
		}
	};
	function SaveAuto() {
		//判断自动登录是否选中
		if ($("#ck_auto").attr("checked")) {
			$.cookie("auto", "true", {
				expires : 7
			}); //存储一个带7天期限的cookie
		} else {
			$.cookie("auto", "true", {
				expires : -1
			});
		}
	} */
</script>
