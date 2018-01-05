<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/common/taglibs.jsp" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.0 Transitional//EN"> 
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<%@ include file="/common/meta.jsp" %>
<title>在线用户列表</title>

<link rel="stylesheet" type="text/css" href="${ctx}/js/dhtml/dhtmlxLayout/dhtmlxlayout.css">
<link rel="stylesheet" type="text/css" href="${ctx}/js/dhtml/dhtmlxLayout/skins/dhtmlxlayout_dhx_skyblue.css">
<script language="javascript" src="${ctx}/js/dhtml/dhtmlxLayout/dhtmlxcommon.js"></script>
<script language="javascript" src="${ctx}/js/dhtml/dhtmlxLayout/dhtmlxlayout.js"></script>
<script language="javascript" src="${ctx}/js/dhtml/dhtmlxLayout/dhtmlxcontainer.js"></script>
<link rel="stylesheet" type="text/css" href="${ctx}/css/common.css" />
<link rel="stylesheet" type="text/css" href="${ctx}/css/right.css" />
<link rel="stylesheet" type="text/css" href="${ctx}/css/pop.css" />

<script type="text/javascript" src="${ctx}/js/jquery.min.js"></script>
<script type="text/javascript" src="${ctx}/js/right_js.js"></script>
<link rel="stylesheet" type="text/css" href="${ctx}/components/colorbox/colorbox.css"/>
<script language="javascript" src="${ctx}/components/colorbox/jquery.colorbox.js"></script>

<link href="${ctx}/css/960.css" rel="stylesheet" media="screen" />
<link href="${ctx}/css/defaultTheme.css" rel="stylesheet" media="screen" />
<link href="${ctx}/css/myTheme.css" rel="stylesheet" media="screen" />

<script src="${ctx}/js/jquery.fixedheadertable.js"></script>
<script src="${ctx}/js/demo.js"></script>
<script type="text/javascript" src="${ctx}/js/right_js.js"></script>
<link rel="stylesheet" type="text/css" href="${ctx}/js/dhtml/dhtmlxWindows/dhtmlxwindows.css">
<link rel="stylesheet" type="text/css" href="${ctx}/js/dhtml/dhtmlxWindows/skins/dhtmlxwindows_dhx_skyblue.css">
<script src="${ctx}/js/dhtml/dhtmlxWindows/dhtmlxcommon.js"></script>
<script src="${ctx}/js/dhtml/dhtmlxWindows/dhtmlxwindows.js"></script>
<script src="${ctx}/js/dhtml/dhtmlxWindows/dhtmlxcontainer.js"></script>

<script>

	function clearUser(userId,userName) {
		if (confirm("您确认要踢掉[" + userName + "]用户吗？")) {
			window.location = "${ctx}/user/clearUser/" + userId + "/" + userName +".do";
		}
	}
</script>
</head>

<body>
<div class="TAB_right">
<tags:message content="${message}" type="success" />
	<h1><b>位置：</b><a href="###">首页</a><span>&gt;</span><a href="###">在线用户列表</a><span>&gt;</span><a href="###">用户查询</a></h1>
	
	<div class="infor_base">
	  	
	  	<div class="container_12">
        	<div class="grid_8">
        		<table class="fancyTable" id="myTable02" cellpadding="0" cellspacing="0">
        			<thead>
        		        <tr>
        		            <th>序号</th>
        		            <th>系统</th>
        		            <th>用户名</th>
        		            <th>姓名</th>
        		            <th>工号</th>
        		            <th>登录IP</th>
        		            <th>登录时间</th>
        		            <th>状态</th>
        		            <!-- <th>操作</th> -->
        		        </tr>
        		    </thead>
				<tbody>
				<c:forEach items="${returnList}" var="user" varStatus="status">
				<tr>
	              	<td class="numeric">${status.count}</td>
	              	<td><c:if test="${user.source == '0'}">绩效</c:if>
	              		<c:if test="${user.source == '1'}">汇金</c:if>
	              		<c:if test="${user.source == '2'}">财富</c:if>
	              	</td>
	              	<td  class="numeric">${user.loginName}</td>
	              	<td  class="numeric">${user.loginCaption}</td>
	              	<td  class="numeric">${user.empCode}</td>
	              	<td  class="numeric">${user.loginIP}</td>
	              	<td  class="numeric"><fmt:formatDate value="${user.loginDate}" type="both"/></td>
	              	<td  class="numeric">
	              		<c:if test="${user.loginType == '1'}">正常</c:if>
	              		<c:if test="${user.loginType == '0'}">异常</c:if>
	              	</td>
	              	<%-- <td  class="numeric operate_td" >
	              		<div class="relative">
                                    <a class="operate_btn">操作</a>
                                    <div class="pop_layer hide">
                                        <div class="cbox_content">
                                        	<shiro:hasPermission name="user_manage:user_maintain:update_sava">
                                            <a class="zxyh_tc" href="javascript:clearUser(${user.loginUUID},'${user.loginCaption}');" title="踢除">踢除</a></br>
                                            </shiro:hasPermission>
                                            <div class="clear"></div>
                                        </div>
                                    </div>
                                </div>
	              	</td> --%>
	          	</tr>
				</c:forEach>
				</tbody>
	      </table>
	      </div>
	      </div>
		</div>	
	</div>
</div>
</body>
<script type="text/javascript">
	var dhxWins;
	dhxWins = new dhtmlXWindows();
	dhxWins.enableAutoViewport(true);
		if(parent.dhxLayout){
		parent.dhxLayout.progressOff();
		}
</script>
</html>
