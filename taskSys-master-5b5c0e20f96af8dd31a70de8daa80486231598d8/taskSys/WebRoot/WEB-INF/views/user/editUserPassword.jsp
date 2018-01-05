<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/common/taglibs.jsp" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.0 Transitional//EN"> 
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<%@ include file="/common/meta.jsp" %>
<title>修改用户</title>
<link rel="stylesheet" type="text/css" href="${ctx}/css/common.css" />
<link rel="stylesheet" type="text/css" href="${ctx}/css/right.css" />
<script type="text/javascript" src="${ctx}/js/jquery.min.js"></script>
<script type="text/javascript" src="${ctx}/js/right_js.js"></script>

<script type="text/javascript" src="${ctx}/js/jquery.metadata.js"></script>
<script type="text/javascript" src="${ctx}/js/jquery.validate.js"></script>
<script>
	$(document).ready(function(){
		$("#saveForm").validate({  
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
		    		remote: "您输入的原密码不正确！"
		 		},
				password:{
					equalTo: "两次输入的密码不一致！"
		 		},	 	 			
				cfmPassword:{
					equalTo: "两次输入的密码不一致！"
		 		}		 		
			}
		});
	});
</script>
</head>

<body>
<form id="saveForm" name="saveForm" action="${ctx}/user/upw.do" method="post">
<div class="TAB_right">
	<h1><b>位置：</b><a href="###">首页</a><span>&gt;</span><a href="###">修改用户密码</a></h1>
    <!--添加数据开始-->
    <div class="infor_base paddingbom10">
        <div class="btn_operate">
            <ul>
		          <li><a id="btn_submit" href="#">修改密码&nbsp;&nbsp;&nbsp;&nbsp;<span style="color: red;">${msg}</span></a></li>
		          
		      </ul>
        </div>
        <div class="ADD_tree">
        	
        	<input type="hidden" id="userId" name="userId" value="${userId}">
        	<table class="Table_forms" cellpadding="0" cellspacing="0">
                <tr>
                    <td><label><span>*</span>原密码：</label><input style="padding:4px 6px;" type="password" id="oldPassword" name="oldPassword" class="{required:true, minlength:6, messages:{required:'原密码不能为空！', minlength:'不能少于6位！'}}"><span></span></td>  
                </tr>
                <tr>
                    <td><label><span>*</span>新密码：</label><input style="padding:4px 6px;" type="password" id="password" name="password" class="{required:true, minlength:6, messages:{required:'新密码不能为空！', minlength:'不能少于6位！'}}"><span></span></td>
                </tr>
                <tr>
                    <td><label><span>*</span>确认密码：</label><input style="padding:4px 6px;" type="password" id="cfmPassword" name="cfmPassword" class="{required:true, minlength:6, messages:{required:'确认密码不能为空！', minlength:'不能少于6位！'}}"><span></span></td>
                </tr>
                <tr align="center">
                	<td colspan="2">
                		<input class="form_now" type="submit" value="保存">&nbsp;&nbsp;&nbsp;&nbsp;
                		<input class="form_now" type="button" onclick="javascript:history.back()" value="返回">
                	</td>
                </tr>
            </table>
        </div>
    </div>
    <!-- <div class="Unity_btn">
    	<ul>
        	<li><button type="submit">保存</button></li>
        </ul>
    </div> -->
</div>
</form>
</body>
</html>