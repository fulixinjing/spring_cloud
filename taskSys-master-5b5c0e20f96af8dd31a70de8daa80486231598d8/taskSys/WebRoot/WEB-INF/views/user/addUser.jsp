<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/common/taglibs.jsp" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.0 Transitional//EN"> 
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<%@ include file="/common/meta.jsp" %>
<title>新增用户</title>
<link rel="stylesheet" type="text/css" href="${ctx}/css/common.css" />
<link rel="stylesheet" type="text/css" href="${ctx}/css/right.css" />
<script type="text/javascript" src="${ctx}/js/jquery.min.js"></script>
<script type="text/javascript" src="${ctx}/js/right_js.js"></script>

<script type="text/javascript" src="${ctx}/js/jquery.metadata.js"></script>
<script type="text/javascript" src="${ctx}/js/jquery.validate.js"></script>

<link rel="stylesheet" type="text/css" href="${ctx}/components/colorbox/colorbox.css"/>
<script language="javascript" src="${ctx}/components/colorbox/jquery.colorbox.js"></script>
<script type="text/javascript" src="${ctx}/js/My97DatePicker/WdatePicker.js" ></script>
<script>
	// 角色弹出选择
	function selectMutliRole(){
	    var url= "${ctx}/sltwin/role/multi.do";
	    $.colorbox({
	        href:url,
	        iframe:true,
	        width:"600",
	        height:"450"
	    });
	}
	function selectMutliRole_callback(ret){
		$("input[name^='roles[']").remove();
		
		var names = "";
		for (var i in ret) {
			var tmp = ret[i].split(",");
			$("#saveForm").append("<input type=\"hidden\" name=\"roles["+i+"].id\" value=\""+tmp[0]+"\">");
			names += tmp[1] + "\n";
		}
		$("#roleArea").text(names);
	}
	
	// 机构弹出选择
	function selectSingleOrg(){
	    var url= "${ctx}/sltwin/org/single.do";
	    $.colorbox({
	        href:url,
	        iframe:true,
	        width:"300",
	        height:"500"
	    });
	}
	function selectSingleOrg_callback(ret){
		var tmp = ret.split(",");
		$("#orgId").val(tmp[0]);
		$("#orgName").val(tmp[1]);
		getUserTypeByOrgId(tmp[0]);
	}
	
	function getUserTypeByOrgId(orgId){
		$("#usertype").find("option").remove();
		url = "${ctx }/sltwin/getUserType.do";
		$.ajax({ 
			type : "POST",  
			url: url, 
			data : "orgId="+orgId,
			dataType : 'json',
			success: function(data,status){
				var cnm = data.userType;
				var ctm=cnm.split(",");
				$.each(ctm,function(index,b){
					var cnd = b.split("_");
					var str = "<option value='"+cnd[0]+"'>"+cnd[1]+"</option>";
    				$("#usertype").append(str);
   				});
      		}
      	});
	}
	
	// 员工弹出选择
	function selectSingleEmployee(){
	    var url= "${ctx}/sltwin/employee/single.do";
	    $.colorbox({
	        href:url,
	        iframe:true,
	        width:"800",
	        height:"500"
	    });
	}
	function selectSingleEmployee_callback(ret){
		var tmp = ret.split(",");
		$("#empCode").val(tmp[0]);
		$("#empName").val(tmp[1]);
	}
	
	
	$(document).ready(function(){
		$("#saveForm").validate({  
			rules:{  
				loginName:{  
		     		required: true,
		     		minlength: 6,
		     		maxlength:20,
		    		remote: {
		       			url: "${ctx}/user/check/loginName.do",
		             	type: "post",
		             	data: {  
		             		username:function() {
		                 		return $("#loginName").val();
		              		}  
		          		}
		       		}  
				},
				userName:{
					required: true,
					maxlength:20,
				},
			},  
			messages:{  
		 		loginName:{  
		 			required: "用户名不能为空！",
		 			minlength: "不能少于6位！",
		 			maxlength: "不能大于20位！",
		    		remote: "该用户名已被使用，请重新输入！"
		 		},
				userName:{
					required: "用户类型不能为空！",
					maxlength: "不能大于20位！",
				}, 
			}
		});
	});
	
	
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
					$('#team_id').append('<option value="'+data.list[i].CODE+'">'+data.list[i].NAME+'</option>');
					
				}
				
				
				}
      		
      	});
	}
	
	
</script>
</head>

<body>
<form id="saveForm" name="saveForm" action="${ctx}/user/save.do" method="post">
<div class="TAB_right">
	<h1><b>位置：</b><a href="###">新增用户</a></h1>
    <!--添加数据开始-->
    <div class="TAB_right_content">
    <div class="infor_base paddingbom10">
        
        <div class="ADD_tree">
        	<table class="Table_forms" cellpadding="0" cellspacing="0">
                <tr>
                    <td><label><span>*</span>用户名：</label><input type="text" name="loginName" value=""><span></span></td>
                </tr>
                <tr>
                    <td><label><span>*</span>姓名：</label><input type="text" name="userName" value="" 
                    class="{required:true, minlength:2, messages:{required:'真实姓名不能为空！', minlength:'不能少于两个字符！'}}"><span></span></td>
                </tr>
                <!-- <tr>
                	<td><label><span>*</span>密码：</label><input type="password" name="password" value="123456" readonly="readonly"><span></span></td>
                </tr> -->
                <tr>
                    <td>
	                    <label><span></span>所属部门：</label>
	                    	<select name="department_id" id="department_id"  style="width: 180px;" onchange="toChnage(this.value)">
	                    		<option value="">======请 选 择======</option>
	                    		<c:forEach  items="${list}" var="list">
	                    			<c:if test="${list.TYPE_CODE eq '01'}">
	                    				<option value="${list.CODE}">${list.NAME}</option>
	                    			</c:if>
	                    		</c:forEach>
	                    	</select>
                    </td>  
                </tr>
                <tr>
                    <td>
	                    <label><span></span>所属团队：</label>
	                    	<select name="team_id" id="team_id"  style="width: 180px;">
	                    		<option value="">======请 选 择======</option>
                    </td>  
                </tr>
                <tr>
                    <td>
	                    <label><span></span>职位级别：</label>
	                    	<select name="position_id" id="position_id"  style="width: 180px;">
	                    		<option value="">======请 选 择======</option>
	                    		<c:forEach  items="${list}" var="list">
	                    			<c:if test="${list.TYPE_CODE eq '03'}"><option value="${list.CODE }">${list.NAME}</option></c:if>
	                    		</c:forEach>
	                    	</select>
                    </td>  
                </tr>
                <tr>
                    <td>
	                    <label><span></span>岗位类型：</label>
	                    	<select name="jobId" id="jobId"  style="width: 180px;">
	                    		<option value="">======请 选 择======</option>
	                    		<c:forEach  items="${list}" var="list">
	                    			<c:if test="${list.TYPE_CODE eq '04'}"><option value="${list.CODE }">${list.NAME}</option></c:if>
	                    		</c:forEach>
	                    	</select>
                    </td>  
                </tr>
                <tr>
                	<td>
                		<label><span>*</span>角色：</label>
                		<textarea id="roleArea" name="roleArea" rows="3" cols="8" readonly="readonly" class="{required:true, messages:{required:'请选择角色！'}}"></textarea>
                		<input class="form_now" type="button" onclick="selectMutliRole()" value="选择..">
                	</td>
                </tr>
                <tr>
                    <td>
	                    <label><span>*</span>入职时间：</label>
	                    <input style=""  type="text" id="employed_date" name="employed_date" onclick="WdatePicker()" onfocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd'})" class="Wdate" "> 
                    </td>
                </tr>
                <tr>
                    <td height="25"><label><span>*</span>邮箱：</label>
	                    <input type="email" id="email" name="email" />
                    </td>
                </tr>
                <tr align="center">
                	<td colspan="2">
                		<input class="form_now" type="submit" value="保存">&nbsp;&nbsp;&nbsp;&nbsp;
                		<input class="form_now" type="button" onclick="javascript:history.back()" value="返回">&nbsp;&nbsp;&nbsp;&nbsp;
                	</td>
                </tr>
            </table>
             </div>
        </div>
    </div>
</div>
</form>
</body>
</html>