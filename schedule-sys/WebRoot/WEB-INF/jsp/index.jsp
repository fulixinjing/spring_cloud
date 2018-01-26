<%@ page language="java"  pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml"> 
<head>
<%@include file="/common/taglibs.jsp"%> 
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<script type="text/javascript" src="${ctx}/js/jquery.min.js"></script>
<link rel='icon' href="${ctx}/images/ico.png" type=‘image/x-ico’ />
<title>日程安排系统</title>
<link href="${ctx}/css/static/main.css?1=1" rel="stylesheet" type="text/css" />
<link href="${ctx}/css/static/base-global.css" rel="stylesheet" type="text/css" />
<link href="${ctx}/css/static/common.css?5=1" rel="stylesheet" type="text/css" />
<link href="${ctx}/css/static/stylesheet.css" rel="stylesheet" type="text/css" />
<link rel="stylesheet" type="text/css" href="${ctx}/css/right.css" />
<link rel="stylesheet" type="text/css" href="${ctx}/css/pop.css" />

<script type="text/javascript" src="${ctx}/js/jquery.validate.js"></script>
<script type="text/javascript" src="${ctx}/js/jquery.metadata.js"></script>
<script type="text/javascript" src="${ctx}/js/alert/g_alert.js?1=1"></script>
<script type="text/javascript" src="${ctx}/js/util/common.js?1=1"></script>
<script type="text/javascript" src="${ctx}/js/My97DatePicker/WdatePicker.js" ></script>


<script type="text/javascript">

 
$(document).ready(function(){
	$("#leftMenu0").addClass('cur');
			//消息提醒
	      $('.msg-wake').click(function(){
	          $('.m-box').show()
	          $('#covered').show()
	           $('.zhxx-box').hide()
	        })
	        //修改密码
	        $('.xgmm-btn').click(function(){
	        	$('.xgmm-box').show();
	        	$('.zhxx-box').hide();
	        	
	        })
	        //消息提醒关闭
	      $('.mm-close').click(function(){
	          $('.m-box').hide()
	          $('.zhxx-box').hide()
	           $('#covered').hide()
	          
	        })	
			//忽略全部
	      $('.hl-msg').click(function(){
	          $('.m-box').hide()
	          $('.zhxx-box').hide()
	          $('#covered').hide()
	        })
			//用户名事件
	      $('.gly-wake').click(function(){
	          $('.m-box').hide()
	          $('.zhxx-box').show()
	          $('#covered').show()
	        })
	        //窗口关闭
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
	}); 
	//修改密码 提交
	function updatePW(){
		var oldPassword = $("#oldPassword").val();
		var password = $("#password").val();
		var cfmPassword = $("#cfmPassword").val();
		$("#oldPW").text("");
		$("#newPW1").text("");
		$("#newPW2").text("");
		if(oldPassword == ''){
			$("#oldPW").text("× 原密码不可为空！");
    		return false;
		}else if(password == ''){
			$("#newPW1").text("× 新密码不可为空！");
    		return false;
		}else if(password != cfmPassword){
			$("#newPW2").text("× 两次输入密码不一致！");
    		return false;
		}else{
			$.ajax({ 
				type : "POST",  
				url: "${ctx}/user/checkPassword", 
				data : {"password":oldPassword,"newPassword":password},
				dataType : 'text',
				async: false, 
				success: function(data){
						if(data == "false"){
							$("#oldPW").text("× 原密码不正确！");
			        		$("#oldPassword").focus();
			        		return false;
						}else{
							$("#oldPW").text(" √");
							g_alert('success','密码修改成功请重新登录!','${ctx}/login',"${ctx}"); 
						}					
					}
	      	});
		}
		
	}
	var t2 = window.setInterval("hello()",3000);
	var x=1;
	function hello(){
		
		$.ajax({ 
			type : "POST",  
			url: "${ctx}/schedule/toRemind", 
			data : {},
			dataType : 'json',
			async: false, 
			success: function(data){
				$("#ulId").text("");
				if(data!=null){
					$("#num-bg").show();
					for(var i = 0; i<data.length;i++){
						var txt="<li><a href='javascript:(void);' onclick='goingPage(${message.id})' >任务【"+data[i].name+"】就要开始啦！</a></li>";               // 以 HTML 创建新元素
						$("#ulId").append(txt); 
						$('#num-bg').text(data.length);
					}
				}else{
					$("#num-bg").hide();
				}
			},
			error:function(data){
				alert(111);
			}
      	});
		//$('#ulId').text('aaaaaaaaa');
	}
</script>


</head>
<body>


<div class="header">
        <div class="box fn-clear">
            <span><img src="${ctx}/css/images/logo11.png" width="230" height="60"></span>
           
            <div class="fr lh60 hd-txt">
                <div class="message">
                    <a href="#" class="cblue1 msg-wake">消息提醒&nbsp; <img src="${ctx}/css/images/hd-arrow.png">
	                    	<div class="upMessageTask">
		                    	<span class="num-bg" id="num-bg" style="display: none"></span>
		                    </div>
                    </a>
                    <div class="m-box hide" id="showMessageId">
                        <div class="xx-arrow"></div>
                        <div class="tit fn-clear">
                            <span class="fl">消息提醒</span>
                            <a href="javascript:(void);" class="mm-close fr"></a>
                        </div>
	                        <ul id="ulId">
									<li><a href="javascript:(void);" onclick="goingPage(${message.id})" >你好你好</a></li>
	                        </ul>
		                    <div class="txt-r pd-t10 pd-b10" id="allIgnore"><a href="javascript:(void);" class="cblue2 hl-msg" onclick="upMessage()">忽略全部</a></div>
                        
                    </div>
                </div>
                <div class="mg-l35 gly">
                    <a href="javascript:(void);" class="cblue1 gly-wake">
                    <img style="width: 50px;height: 50px;border-radius: 50%;" src="${ctx}/images/tou_xiang.png">
                    	常慧娟
                    		<img src="${ctx}/css/images/hd-arrow.png"></a>
                    <div class="zhxx-box hide">
                        <div class="xx-arrow"></div>
                        <div class="tit fn-clear">
                            <span class="fl">账号管理</span>
                            <a href="javascript:(void);" class="mm-close fr"></a>
                        </div>
                        <ul>
                            <li>
                                <p>XXXX</p>
                                <p style="width:228px;word-break:break-all; word-wrap:break-word ;">XXXXXXXX</p>
                            </li>
                        </ul>
                        <div class="txt-c pd-t10 pd-b10">
                            <a href="javascript:(void);" class="cblue2 xgmm-btn">修改密码</a>
                            <a href="${ctx}/login/quit" class="tc-close hl-msg mg-l35">退出</a>
                        </div>
                    </div>
                </div>
            </div>
        </div>
        <!-- 修改密码 -->
      <form id="saveForm" name="saveForm" action="${ctx}/user/upw.do" method="post">
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
      
    </div>

    <div class="wrap fn-clear" style="height: 550px;" >
    	<div class="menu-list fl" id="appwrap" style="height: 100%;overflow-y:auto; overflow-x:hidden;" >
    		<ul>
    			<li class="title">菜单栏</li>
		    	<li id="" name="leftMenu" ><a target="mima" onclick="leftColor('1');" href="${ctx}/schedule/now" >今日安排</a></li>
		    	<li id="" name="leftMenu" ><a target="mima" onclick="leftColor('1');" href="${ctx}/schedule/kalendar" >日历</a></li>
		    	<li id="" name="leftMenu" ><a target="mima" onclick="leftColor('1');" href="${ctx}/user/personSys" >个人中心</a></li>
    		</ul>
    	</div>

    	<div class="content1" id="mainarea" style="height: 100%;" >
    		<iframe id="mima" name="mima" width="100%" style="height:100%;overflow: hidden;" frameborder="0" src="${ctx}/schedule/now"></iframe>
    	</div>
    </div>
    <div id="covered" onclick="closeDiv()"></div>
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
  </script>

</body>
</html>
 