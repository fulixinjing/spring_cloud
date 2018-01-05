<%@ page language="java" pageEncoding="UTF-8"%>
<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.0 Transitional//EN">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<%@ include file="/common/meta.jsp"%>
<title>任务管理系统</title>
<link rel="stylesheet" type="text/css" href="${ctx}/css/common.css" />
<link rel="stylesheet" type="text/css" href="${ctx}/css/right1.css" />
<link rel="stylesheet" type="text/css" href="${ctx}/css/colorbox.css" />
<link rel="stylesheet" type="text/css" href="${ctx}/css/pop.css" />
<script type="text/javascript" src="${ctx}/js/jquery.min.js"></script>

<link href="${ctx}/css/static/main.css" rel="stylesheet" type="text/css" />

<link rel="stylesheet" type="text/css" href="${ctx}/components/colorbox/colorbox.css"/>
<script language="javascript" src="${ctx}/components/colorbox/jquery.colorbox.js"></script>

<link href="${ctx}/css/defaultTheme1.css" rel="stylesheet" media="screen" />
<link href="${ctx}/css/myTheme1.css" rel="stylesheet" media="screen" />
<script type="text/javascript" src="${ctx}/js/My97DatePicker/WdatePicker.js" ></script>
<script src="${ctx}/js/jquery.fixedheadertable.js"></script>
<script src="${ctx}/js/demo.js"></script>
<script type="text/javascript" src="${ctx}/js/right_js.js"></script>
<script type="text/javascript" src="${ctx}/js/layer/skin/layer.css"></script> 
<script type="text/javascript" src="${ctx}/js/layer/skin/layer.ext.css"></script>
<script type="text/javascript" src="${ctx}/js/layer/layer.min.js"></script>
<script type="text/javascript" src="${ctx}/js/layer/extend/layer.ext.js"></script>
<script type="text/javascript" src="${ctx}/js/email/inputSuggest_0.1.js"></script>
<link href="${ctx}/css/static/base-global1.css" rel="stylesheet" type="text/css" />
</head>
<style>
	html,body{
		height:100%;
		overflow:hidden;
	}
	#bodyTR  td{
		text-align:center;
	}	
	
	.suggest-container{border:1px solid #C1C1C1;background:#e0e0e0;visibility:hidden;}
	.suggest-item{padding:3px 2px;}
	.suggest-active {background:#178e8e;color:white;padding:3px 2px;}			
</style>

<script type="text/javascript">
//导入excel
function importExcel(){
	var url= "${ctx}/user/upfile.do";
    $.colorbox({
        href:url,
        iframe:true,
        width:"600",
        height:"500"
    }); 	
}
</script>

<body>
<form id ="f1" action="${ctx}/user/list.do" method="POST">
<tags:message content="${message}" type="success" />
<div class="TAB_right">
<h1><b>位置：</b><a href="###">首页</a><span>&gt;</span><a href="###">在线用户维护</a></h1>
	<div class="infor_base">
	<div  class="btn_operate">
		</div>
		   <div class="container_12">
        	<div class="grid_8">
        	<form id="listForm" name="listForm" method="post">
        		<table class="fancyTable" id="myTable02" cellpadding="0" cellspacing="0" style="table-layout:fixed;">
        		    <thead>
        		        <tr>
        		        	<th style="width:150px;">用户名</th>
        		            <th style="width:80px;">姓名</th>
        		            <th style="width:120px;" >所属部门</th>
        		            <th style="width:80px;">所属团队</th>
        		            <th style="width:90px;">职位级别</th>
        		            <th style="width:80px;">岗位类别</th>
        		            <th style="width:100px;">登录IP</th>
        		            <!-- <th style="width:80px;">登录时间</th> -->
                            <th style="width:150px;">邮箱</th>
        		            
        		        </tr>
        		    </thead>
        		    <tbody id="bodyTR">
        		          <c:forEach items="${onLineUserList}" var="user" varStatus="status">
                        <tr id="line${status.index}" onclick="lineColor('${status.index}');">
                            <td  style="word-wrap:break-word;" >${user.loginName }</td>
                            <td  style="word-wrap:break-word;" >${user.userName }</td>
                            <td >${user.department_Name}</td>
                            <td >${user.team_Name}</td>
                            <td>${user.position_Name}</td>
                            <td >${user.postName}</td>
                            <td >${user.loginIp}</td>
                            <%-- <td >${user.lastLoginTime}</td> --%>
                            <td  style="word-wrap:break-word;" >${user.email}</td>
                           </tr>
                    </c:forEach>
        		    </tbody>
        		</table>
        		</form>
        	</div>
        	<div class="clear"></div>
        </div>	
     	<%-- <div class="pages">
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
        </div> --%>
	</div>

</div>
    <div id="covered"></div>
</form>
</body>
</html>