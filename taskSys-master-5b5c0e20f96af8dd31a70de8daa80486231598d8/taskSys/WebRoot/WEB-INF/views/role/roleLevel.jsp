<%@ page language="java" pageEncoding="UTF-8"%>
<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.0 Transitional//EN">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<%@ include file="/common/meta.jsp"%>
<title>薪酬绩效管理系统</title>
<link rel="stylesheet" type="text/css" href="${ctx}/css/common.css" />
<link rel="stylesheet" type="text/css" href="${ctx}/css/right.css" />
<link rel="stylesheet" type="text/css" href="${ctx}/css/colorbox.css" />
<link rel="stylesheet" type="text/css" href="${ctx}/css/pop.css" />
<script type="text/javascript" src="${ctx}/js/jquery.min.js"></script>
<script type="text/javascript" src="${ctx}/js/right_js.js"></script>

<link href="${ctx}/css/960.css" rel="stylesheet" media="screen" />
<link href="${ctx}/css/defaultTheme.css" rel="stylesheet" media="screen" />
<link href="${ctx}/css/myTheme.css" rel="stylesheet" media="screen" />

<script src="${ctx}/js/jquery.fixedheadertable.js"></script>
<script src="${ctx}/js/demo.js"></script>
<script type="text/javascript" src="${ctx}/js/right_js.js"></script>

</head>
<body>
<form action="${ctx}/roletree/list.do" method="POST">
<tags:message content="${message}" type="success" />
<div class="TAB_right">
	<h1><b>位置：</b><a href="###">首页</a><span>&gt;</span><a href="###">角色层级</a></h1>
	<div class="retrieve_list">
			<table class="retrieve_table" width="100%" cellpadding="0" cellspacing="0">
					<tr>
						<td width='310px'><label>角色名称 ：</label><input type="text" name="roleName" value="${roleTree.roleName}"></td>
							
						<td width='220px'><label>来源：</label><select class="province_choice" name="source">
								<option value="0"
									<c:if test="${roleTree.source==0}">selected</c:if>>绩效</option>
								<option value="1"
									<c:if test="${roleTree.source==1}">selected</c:if>>汇金</option>
								<option value="2"
									<c:if test="${roleTree.source==2}">selected</c:if>>财富</option>									
								</select> 
						</td>
						<td width='280px'><label>类型：</label><select class="province_choice"  id="type" name="type">
						<option value="" >请选择</option>
                		<c:forEach  items="${type}" var="cd" varStatus="st">
                			<option value="${cd.code}" <c:if test="${roleTree.type eq cd.code}">selected</c:if>>${cd.name}</option>
                		</c:forEach>	
                		</select> 					
						</td>						
						<td ><input class="form_now marginr10" type="submit" value="查询"></td>
					</tr>
				</table>
	</div>
	<div class="infor_base">
		  <div class="btn_operate">
            <ul>
				<li><a class="btn_staff" href="${ctx}/roletree/add.do"><span>新增角色</span></a></li>
			</ul>
          </div>
		   <div class="container_12">
        	<div class="grid_8">
        		<table class="fancyTable" id="myTable02" cellpadding="0" cellspacing="0">
        		    <thead>
        		        <tr>
        		            <th>角色ID</th>
        		            <th>角色名称</th>
        		            <th>上一级角色ID</th>
        		            <th>上一级角色名称</th>
                            <th>来源</th>
        		            <th>类型</th>
        		            <th>编码</th>
        		            <th>操作</th>
        		        </tr>
        		    </thead>
        		    <tbody>
        		    <c:forEach items="${pclist.datas}" var="autolist">
        		        <tr>
						    <td  width="15%">${autolist.roleId}</td>
							<td  width="15%">${autolist.roleName}</td>
							<td  width="15%">${autolist.parentRoleId}</td>
							<td  width="15%">${autolist.parentRoleName}</td>
							<td  width="10%"><c:if test="${autolist.source==0}">绩效</c:if>
							 <c:if test="${autolist.source==1}">汇金</c:if>
							 <c:if test="${autolist.source==2}">财富</c:if></td>
							 <td  width="16%">
							 <c:forEach  items="${type}" var="cd" varStatus="st">
							 <c:if test="${cd.code eq autolist.type}">${cd.name} </c:if>
							 </c:forEach>
							 </td>
							<td  width="10%">${autolist.code}</td>			
        		            <td class="numeric operate_td">
                            	<div class="relative">
                                    <a class="operate_btn">操作</a>
                                    <div class="pop_layer hide">
                                        <div class="cbox_content">
                                            <span class="left"><a class="icon del_icon opat_a" 
										     href="javascript:if(confirm('你确定要删除这个角色吗？')) delrole('${autolist.roleId}','${autolist.roleName}','${autolist.source}','${autolist.type}')" title="删除">删除</a></span>
                                            <!-- <span class="left" ><a class="icon edit_icon opat_a" href="javascript:updaterole('${autolist.roleId}','${autolist.source}','${autolist.type}');" title="修改">修改</a></span> -->
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
	    	<pg:pager url="${ctx}/roletree/list.do" items="${pclist.total}"
				maxPageItems="${ps}" export="page,currentPageNumber=pageNumber">
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
	$(document).ready(function(){

	})
	function delrole(roleId,roleName,source,type) {
		window.location.href = "${ctx}/roletree/delrole/" + roleId + "/" + roleName + ".do?source="+source+"&type="+type;
	};
</script>
</html>