<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/common/taglibs.jsp" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.0 Transitional//EN"> 
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<%@ include file="/common/meta.jsp" %>
<title> 薪酬绩效管理系统</title>
<link rel="stylesheet" type="text/css" href="${ctx}/css/common.css" />
<link rel="stylesheet" type="text/css" href="${ctx}/css/right.css" />
<link rel="stylesheet" type="text/css" href="${ctx}/css/colorbox.css"/>
<script type="text/javascript" src="${ctx}/js/jquery.min.js"></script>
<script type="text/javascript" src="${ctx}/js/right_js.js"></script>
<script type="text/javascript" src="${ctx}/js/jquery.metadata.js"></script>
<script type="text/javascript" src="${ctx}/js/jquery.validate.js"></script>
<script language="javascript" src="${ctx}/components/colorbox/jquery.colorbox.js"></script>
<script type="text/javascript" src="${ctx}/js/ztree/jquery.ztree.core-3.5.js"></script>
<script type="text/javascript" src="${ctx}/js/ztree/jquery.ztree.excheck-3.5.js"></script>
</head>

<body>
<div class="TAB_right">
	<h1><b>位置：</b><a href="###">首页</a><span>&gt;</span><a href="###">修改角色</a></h1>
    <!--添加数据开始-->
    <div class="TAB_right_content">
    <div class="jur_add">
        <div class="btn_operate">
            <ul>
		          <li><a id="btn_submit" href="#"><span>修改角色</span></a></li>
		          
		      </ul>
        </div>
        <div class="ADD_tree">
        <form action="${ctx}/role/updaterole.do" method="POST" id="updateForm">
         <table class="Table_forms" cellpadding="0" cellspacing="0">
        	 <input type="hidden" id="rightCodes" name="rightCodes" value="${rightlistcode}">
        	 <input type="hidden" id="source" name="source" >
        	 <input type="hidden" id="parentRoleName" name="parentRoleName">
        	 <input type="hidden" id="sourceCodes" name="sourceCodes" >
        	<%--<c:forEach items="${role.right}" var="role" varStatus="status">
	        	<input type="hidden" name="rights[${status.index}].id" value="${right.id}">
        	</c:forEach> --%>
                <tr>
                    <td><label><span>*</span>角色编码：</label><input type="text" value="${role.roleCode}" name="roleCode" readonly="readonly"><span></span></td>
                    <td><label><span>*</span>角色名称：</label><input type="text" value="${role.roleName}" name="roleName"><span></span></td>
                </tr>
                <tr>
                	<td><label><span>*</span>角色描述：</label><input type="text" value="${role.description}" name="description"><span></span></td>
                	<td><label><span>*</span>状       态：</label><select name="status">
                    		<option value="0" ${role.status == 0 ? "selected='selected'" : ""}>在用</option>
                  			<option value="1" ${role.status == 1 ? "selected='selected'" : ""}>历史</option></select><span></span></td>
                </tr>
                <%--<c:if test="${source!=0}">
                <tr>
                	<td><label><span>*</span>上一级角色：</label>                	
                    <select id="parentRoleCode" name="parentRoleCode">
                    	<option value="" >请选择</option>
                    </select></td>
                    <td>                        
	                    <label id="sourceRoleName"><span>*</span>
	                    <c:if test="${source == 0}">绩效角色：</c:if>
	                    <c:if test="${source == 1}">汇金事业部角色：</c:if>
	                    <c:if test="${source == 2}">财富事业部角色：</c:if>
	                    </label>	                    
	                    <textarea class="infor_base" id="sourceRole" name="sourceRole" readonly="readonly" ></textarea>
						 <input class="form_now" type="button"  onclick="javascript:showSourceRoleDialog('')" value="选择..">
					</td>                  		
                </tr>  
                </c:if>                  
                --%><tr>
                    <td>
	                    <label><span>*</span>拥有权限：</label>
	                    <textarea class="infor_base" id="quanxi" name="quanxi"  readonly="readonly"></textarea>
							<input class="form_now" type="button" onclick="javascript:showModalDialog('${role.roleCode}')" value="查询权限">
					</td>
					<%--<td><label><span>*</span>角色来源：</label> 
								<select id ="jxSource" name="jxSource" disabled="disabled">
										<option value="0" selected="selected">绩效</option>
										<option value="1">汇金</option>
										<option value="2">财富</option>
								</select>
					</td>					
                --%></tr>
                <tr>
                	<td>&nbsp;</td>
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
    </form>
</div>
</body>
<script type="text/javascript">
var zNodes =${menuList};
var zRoleNodes = ${roleRelationList};
$(function () {
    var chk_value =[];
    for(var i=0;i<zNodes.length;i++){
    	chk_value.push(zNodes[i].id + "," + zNodes[i].name);
    }
    
    var chk_Rolevalue =[];
    for(var i=0;i<zRoleNodes.length;i++){
    	chk_Rolevalue.push(zRoleNodes[i].id + "," + zRoleNodes[i].name);
    }		   
    //谁调用我，我就调用谁的callback方法
    showModalDialog_callback(chk_value);
    selectMutliRole_callback(chk_Rolevalue);
});

function showModalDialog(rolecode){
    var url= "${ctx}/role/allright/"+rolecode+".do";
    $.colorbox({
        href:url,
        iframe:true,
        width:"300",
        height:"500",
        opacity:"0.3"
    });
}
function showModalDialog_callback(ret){
	$("input[name^='rights[']").remove();
	var codes = "";
	var names = "";
	for (var i in ret) {
		var tmp = ret[i].split(",");
		codes +="'"+ tmp[0] + "',";
		names += tmp[1] + "\n";
	}
	$("#quanxi").text(names);
	$("#rightCodes").val(codes);
}
$(document).ready(function(){
       
	$("#updateForm").validate({  
		rules:{  
			quanxi:{  
	     		required: true
			}
		},  
		messages:{  
	 		quanxi:{  
	 			required: "权限不能为空！",
	 		}
		}
	});
});
function showSourceRoleDialog(){
	    var url= "${ctx}/role/source.do?source="+${source};
	    $.colorbox({
	        href:url,
	        iframe:true,
	        width:"600",
	        height:"450"
	    });
}
function selectMutliRole_callback(ret){		
	var names = "";
	var codes = "";
	for (var i in ret) {
		var tmp = ret[i].split(",");
		names += tmp[1] + "\n";
		codes += tmp[0] + ",";
	}
	$("#sourceRole").text(names);
	$("#sourceCodes").val(codes);
}
$("#parentRoleCode").blur(function(){
	$("#parentRoleName").val($("#parentRoleCode option:selected").text());
})
</script>
</html>