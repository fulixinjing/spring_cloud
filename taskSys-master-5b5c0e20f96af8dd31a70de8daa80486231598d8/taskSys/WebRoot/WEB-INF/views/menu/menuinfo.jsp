<%@ page language="java"  pageEncoding="UTF-8"%>
<%@include file="/common/taglibs.jsp"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.0 Transitional//EN"> 
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>任务管理系统</title>
<link rel="stylesheet" type="text/css" href="${ctx}/css/common.css" />
<link rel="stylesheet" type="text/css" href="${ctx}/css/right1.css" />
<link rel="stylesheet" type="text/css" href="${ctx}/css/colorbox.css"/>
<script type="text/javascript" src="${ctx}/js/jquery.min.js"></script>
<script type="text/javascript" src="${ctx}/js/jquery.validate.js"></script>
<script type="text/javascript" src="${ctx}/js/right_js.js"></script>
<script language="javascript" src="${ctx}/components/colorbox/jquery.colorbox.js"></script>
<script type="text/javascript">
	
function validateForm() {  
	   //validate方法参数可选  
	   return $("#menuinfo").validate({ 
		   rules: {  
			  menu_name:{
		          required : true,
		          minlength: 2,
		          maxlength: 25
	         },
	         menu_pid:{
	                required : true
	         },
	         menu_type:{
	                required : true
	         },
	         menu_status:{
	                required : true
	         },
	         menu_url:{
		          required : true,
		          minlength: 1,
		          maxlength: 80
	         },
	         privilegescode:{
		          required : true
	         },
	         menu_sort:{
		          required : true
	         },
	         description:{
	                maxlength: 50
	         }
	       },   
	       messages:{ 
	    	   menu_name:{
	                required: "请输入菜单名称",
	                minlength: "菜单名称请勿过短",
	                maxlength: "菜单名称请勿过长"
	         	},
	         	menu_pid:{
	                required : "请选择上级菜单"
	            },
	            menu_level:{
	            	required: "请选择菜单级别",
	            },
	            menu_status:{
	                required : "请选择使用状态"
	            },
	            menu_url:{
	                required : "请输入URL地址",
	                minlength: "URL地址请勿过短",
	                maxlength: "URL地址请勿过长"
	            },
	            privileges_code:{
	                required : "请输入权限"
	            },
	            menu_sort:{
	                required : "请输入排序"
	            },
	            description:{
	                maxlength:"备注信息过长"
	         	}
	       }
	   }).form();
	} 
	
	function menuup(){
		if(validateForm()){
			$.ajax({ 
				type : "POST",  
				url: "${ctx}/menu/menuup.do", 
				data : $("#menuinfo").serialize(),
				dataType : 'json',
				success: function(data){
	        		alert("菜单修改成功！");
	      		},error : function(data) {  
	            	alert("菜单修改失败！");
	          	} 
	      	}); 
		}
	}
	
	function menudel(){
		$.ajax({ 
			type : "POST",  
			url: "${ctx}/menu/menudel.do", 
			data : $("#menuinfo").serialize(),
			dataType : 'json',
			success: function(data){
        		alert("菜单删除成功！");
        		parent.location.replace(parent.location.href);
      		},error : function(data) {  
            	alert("菜单删除失败！");
        		parent.location.replace(parent.location.href);
          	} 
      	});
	
	}
	
	// 机构弹出选择
	function selectSingleOrg(){
	    var url= "${ctx}/menu/allRight.do";
	    $.colorbox({
	        href:url,
	        iframe:true,
	        width:"300",
	        height:"500",
	        opacity:"0.3"
	    });
	}
	
	function selectSingleOrg_callback(ret){
		var tmp = ret.split(",");
		$("#menu_pid").val(tmp[0]);
		$("#name").val(tmp[1]);
	}

</script>
</head>

<body style="background-color: #E7EDF8;">
<form id="menuinfo" name="menuinfo" action="${ctx}/menu/menuup.do" method="post">
<div class="infor_base  menu_tree">
  <div class="btn_operate">
      <ul>
          <li><a class="btn_submit" href="javascript:menuup();"><span>保存</span></a></li>
          <li><a class="btn_organ" href="${ctx}/menu/newmenu.do?menuid=${menuinfo.id}"><span>新增菜单</span></a></li>
      </ul>
  </div>
   <div class="ADD_tree">
  <input type="hidden" name="id"  value="${menuinfo.id}"/>
      <table class="Table_forms" cellpadding="0" cellspacing="0">
      
                <tr>
                    <td height="25"><label><span>*</span>菜单名称：</label><input type="text" name="menu_name" value="${menuinfo.menu_name}"></td>
                  
                  	 <%-- <td height="25"><label><span>*</span>上级菜单：</label>
						<select name="menu_pid" >  
				            <c:forEach var="par" items="${parentlist}">  
						                <option value="${par.id}"  <c:if test="${menuinfo.menu_pid==par.id}"> selected</c:if>>  
						                    ${par.menu_name}
						                </option>       
				                  
				            </c:forEach>  
				        </select>
					</td> --%>
					<td>
	                    <label><span>*</span>上级菜单：</label>
	                    <input type="text" value="${menu_name}" id="name" readonly="readonly" class="{required:true, messages:{required:'请选择所属机构！'}}"><span></span>
	                    <input type="hidden" id="menu_pid" name="menu_pid" value="${menuinfo.menu_pid}"><span></span>
	                    <input class="form_now" type="button" onclick="selectSingleOrg()" value="选择..">
                    </td>
                </tr>
                <tr> 
                    <td height="25"><label><span>*</span>菜单级别：</label>
	                    <select name="menu_level">
	                    	<option value="1" <c:if test="${menuinfo.menu_level=='1'}"> selected</c:if>>一级</option>
	                    	<option value="2" <c:if test="${menuinfo.menu_level=='2'}"> selected</c:if>>二级</option>
	                    	<option value="3" <c:if test="${menuinfo.menu_level=='3'}"> selected</c:if>>三级</option>
	                    	<option value="4" <c:if test="${menuinfo.menu_level=='4'}"> selected</c:if>>四级</option>
                    	</select>
                    </td>
               
                    <td height="25"><label><span>*</span>URL地址：</label>
						<input type="text" name="menu_url" value="${menuinfo.menu_url}" >
					</td>
                </tr>
               
                 <tr>
                    <td height="25"><label><span>*</span>使用状态：</label>
                    <select name="menu_status">
                    	<option value="0" <c:if test="${menuinfo.menu_status=='0'}"> selected</c:if>>是</option>
                    	<option value="1" <c:if test="${menuinfo.menu_status=='1'}"> selected</c:if>>否</option>
                    </select>
                    </td>
                     <td height="25"><label>图标：</label>
                    	<input type="text" name="menu_icon" value="${menuinfo.menu_icon}" >
                    </td>
                </tr>
                <tr>
                	<td height="25"><label>描述：</label><textarea name="description"  cols="40" >${menuinfo.description}</textarea> </td>
                	<td height="25"><label><span>*</span>权限：</label><input type="text" name="privileges_code" value="${menuinfo.privileges_code}" > </td>
                </tr>
                <tr>
                	<td height="25"><label><span>*</span>排序：</label><input type="text" name="menu_sort" value="${menuinfo.menu_sort}" > </td>
                </tr>
            </table>
</div>	
</div>
</form>
</body>
</html>