<%@ page language="java" pageEncoding="UTF-8"%>
<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.0 Transitional//EN">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<%@ include file="/common/meta.jsp"%>
<title>任务管理系统</title>
<link rel="stylesheet" type="text/css" href="${ctx}/css/common.css" />
<link rel="stylesheet" type="text/css" href="${ctx}/css/right1.css?1=1" />
<link rel="stylesheet" type="text/css" href="${ctx}/css/colorbox.css" />
<link rel="stylesheet" type="text/css" href="${ctx}/css/pop.css" />
<script type="text/javascript" src="${ctx}/js/jquery.min.js"></script>

<link href="${ctx}/css/960.css" rel="stylesheet" media="screen" />
<link href="${ctx}/css/defaultTheme1.css" rel="stylesheet" media="screen" />
<link href="${ctx}/css/myTheme1.css" rel="stylesheet" media="screen" />
<script src="${ctx}/js/jquery.fixedheadertable.js"></script>
<script src="${ctx}/js/demo.js"></script>
<script type="text/javascript" src="${ctx}/js/right_js.js"></script>
<style type="text/css">
	td {text-align:center}
</style>
</head>
<body>
<form action="${ctx}/role/allrole.do" method="POST">
<tags:message content="${message}" type="success" />
<div class="TAB_right">
	<h1><b>位置：</b><a href="###">首页</a><span>&gt;</span><a href="###">角色管理</a></h1>
	<div class="retrieve_list">
			<table class="retrieve_table" width="100%" cellpadding="0" cellspacing="0">				
					<tr>
						<td width='310px'><label>角色名称 ：</label><input type="text" name="roleName" value="${role.roleName}">
						<input type="hidden" id="source" name="source"  value="<%=request.getParameter("source")%>">
						</td>
							
						<td width='220px'><label>状态：</label><select class="province_choice" name="status">
								<option value="0"
									<c:if test="${role.status==0}">selected</c:if>>在用</option>
								<option value="1"
									<c:if test="${role.status==1}">selected</c:if>>历史</option>
								</select> 
						</td>
						<td ><input class="form_now marginr10" type="submit" value="查询"></td>
					</tr>
				</table>
	</div>
	<div class="infor_base">
		  <div class="btn_operate">
            <ul>
				<li><a class="btn_staff" href="${ctx}/role/add.do?source=<%=request.getParameter("source")%>"><span>新增角色</span></a></li>
			</ul>
          </div>
		   <div class="container_12">
        	<div class="grid_8">
        		<table class="fancyTable" id="myTable02" cellpadding="0" cellspacing="0" style="width:100%">
        		    <thead>
        		        <tr>
        		            <th>角色编码</th>
        		            <th>角色名称</th>
        		            <th>状态</th>
        		            <th>描述</th>
                            <th>创建人</th>
        		            <th>创建时间</th>
        		            <th>操作</th>
        		        </tr>
        		    </thead>
        		    <tbody>
        		    <c:forEach items="${pclist.datas}" var="autolist">
        		        <tr>
						   <td  width="15%">${autolist.roleCode}</td>
							<td  width="25%">${autolist.roleName}</td>
							<td  width="10%"><c:if test="${autolist.status==0}">在用</c:if> <c:if
												test="${autolist.status==1}">历史</c:if></td>
							<td  width="20%">${autolist.description}</td>
							<td  width="10%">${autolist.createBy}</td>
							<td  width="10%"><fmt:formatDate value="${autolist.createTime}"
												pattern="yyyy-MM-dd" /></td>
        		            <td class="numeric operate_td">
                            	<div class="relative">
                                    <a class="operate_btn" style="display: block;text-align: center;">操作</a>
                                    <div class="pop_layer hide">
                                        <div class="cbox_content">
                                            <span class="left"><a class="icon del_icon opat_a" 
										     href="javascript:if(confirm('你确定要删除这个角色吗？')) delrole('${autolist.roleCode}','${autolist.roleName}')" title="删除">删除</a></span>
                                            <span class="left"><a class="icon edit_icon opat_a" href="javascript:updaterole('${autolist.roleCode}');" title="修改">修改</a></span>
                                            <div class="clear"></div>
                                        </div>
                                    </div>
                                </div>
                            	
                            </td>
        		        </tr>
        		        </c:forEach>
        		    </tbody>
        		</table>
        	</div>
        	<div class="clear"></div>
        </div>	
	    <div class="pages">
	     共<fmt:parseNumber value="${(pclist.total%ps==0 ? pclist.total/ps : (pclist.total/ps)+1)}" integerOnly="true" />页&nbsp;|&nbsp;共${pclist.total}条记录&nbsp;
	    	<pg:pager url="${ctx}/role/allrole.do" items="${pclist.total}"
				maxPageItems="${ps}" export="page,currentPageNumber=pageNumber">
				<pg:param name="source" value='<%=request.getParameter("source") %>'/>
				<pg:param name="pqFlag" value="true" />
				<pg:first>
					<a href="${pageUrl}">[首页]</a>
				</pg:first>
				<pg:prev>
					<a href="${pageUrl}">[上一页]</a>
				</pg:prev>
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
				<pg:next>
					<a href="${pageUrl }">[下一页]</a>
				</pg:next>
				<pg:last>
					<a href="${pageUrl }">[尾页]</a>
				</pg:last>
			</pg:pager>
				
	  </div>
	</div>

</div>
</form>
</body>
<script type="text/javascript">
	function delrole(rolecode,roleName) {
		window.location.href = "${ctx}/role/delrole/" + rolecode + "/" + roleName + ".do?source="+'<%=request.getParameter("source")%>';
	};
	function updaterole(rolecode) {
		window.location.href = "${ctx}/role/updaterol/" + rolecode + ".do?source="+'<%=request.getParameter("source")%>';
	};
</script>
</html>