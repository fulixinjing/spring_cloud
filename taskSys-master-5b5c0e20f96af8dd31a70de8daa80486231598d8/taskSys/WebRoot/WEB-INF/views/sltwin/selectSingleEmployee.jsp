<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/common/taglibs.jsp" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.0 Transitional//EN"> 
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<%@ include file="/common/meta.jsp" %>
<title>员工列表</title>
<link rel="stylesheet" type="text/css" href="${ctx}/css/common.css" />
<link rel="stylesheet" type="text/css" href="${ctx}/css/right.css" />
<script type="text/javascript" src="${ctx}/js/jquery.min.js"></script>
<script type="text/javascript" src="${ctx}/js/right_js.js"></script>
<script>
	//确定按钮，返回
	function okBtnClick(employeeCode, employeeName){
		var chk_value = employeeCode + "," + employeeName;
	    
	    //谁调用我，我就调用谁的callback方法
	    parent.selectSingleEmployee_callback(chk_value);
	    parent.$.colorbox.close();
	}
</script>

<link rel="stylesheet" type="text/css" href="${ctx}/css/pop.css" />
<script type="text/javascript">
	$(function(){
		$(".X_tablefom tr:even").css("background-color","#e6edf3");
		$(".X_tablefom tr:odd").css("background-color","#ffffff");
	})
</script>
</head>

<body>
<div class="X_layer">
	<div class="X_layer_content"><span>标题</span></div>
    <div class="X_input">
    	<div class="X_box">
    	<form action="${ctx}/sltwin/employee/single/search.do" method="post">
        	<table class="X_Tab X_inputfom" width="100%" cellpadding="0" cellspacing="0">
            	<tr>
                	<td><label>编号</label><input type="text" name="employeeCode" value="${employee.employeeCode}"></td>
                    <td><label>姓名</label><input type="text" name="employeeName" value="${employee.employeeName}"></td>
                </tr>
                <tr>
                	<td><label>机构</label><input type="text" name="orgId" value="${employee.orgId}"></td>
                    <td><label>状态</label><select name="status" >
                    <option value="">全部</option>
                    <option value="0" <c:if test="${employee.status == '0'}">selected</c:if>>在职</option>
                    <option value="1" <c:if test="${employee.status == '1'}">selected</c:if>>离职</option>
            	</select></td>
                </tr>
            </table>
            <div class="X_btn"><input class="btn_submit" type="submit" value="检索" /></div>
            </form>
            <div class="X_table">
                <table class="X_Tab X_tablefom" width="100%" cellpadding="0" cellspacing="0">
                    <tr>
		              	<th width="30">序号</th>
		              	<th>编号</th>
		              	<th>姓名</th>
		              	<th>机构</th>
		              	<th>状态</th>
		              	<th>性别</th>
		              	<th>手机</th>
		              	<th>邮箱</th>
		              	<th>操作</th>
		          	</tr>
					
					<c:forEach items="${employees}" var="employee" varStatus="status">
					<tr>
		              	<td>${status.count}</td>
		              	<td>${employee.employeeCode}</td>
		              	<td>${employee.employeeName}</td>
		              	<td>${employee.orgId}</td>
		              	<td>
		              		<c:if test="${employee.status == '0'}">启用</c:if>
		              		<c:if test="${employee.status == '1'}">停用</c:if>
		              	</td>
		              	<td>${employee.sex}</td>
		              	<td>${employee.mobilePhone}</td>
		              	<td>${employee.email}</td>
		              	<td>
			              	<a class="icon edit_icon" href="javascript:okBtnClick('${employee.employeeCode}','${employee.employeeName}');" title="选择带回"><img src="${ctx}/images/pencil.png"></a>
		              	</td>
		          	</tr>
					</c:forEach>
                </table>
            </div>
            <div class="X_pages">
            	<a href="###">首页</a>
                <a class="X_mt" href="###"><img src="${ctx}/images/X_pageleft.png" /></a>
                <a href="###">1</a>
                <a href="###">2</a>
                <a href="###">3</a>
                <a href="###">4</a>
                <a href="###">...</a>
                <a  class="X_mt" href="###"><img src="${ctx}/images/X_pageright.png" /></a>
                <a href="###">尾页</a>
            </div>
        </div>
    </div>
</div>
</body>
</html>