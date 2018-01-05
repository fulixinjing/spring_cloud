<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/common/taglibs.jsp" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.0 Transitional//EN"> 
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<%@ include file="/common/meta.jsp" %>
<title>用户列表</title>

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
	function editUser(userId) {
		window.location = "${ctx}/user/edit/" + userId + ".do";
	}
	function deleteUser(userId, userName) {
		if (confirm("您确认要删除[" + userName + "]用户吗？")) {
			window.location = "${ctx}/user/delete/" + userId + "/" + userName +".do";
		}
	}
	function disableUser(userId, userName) {
		if (confirm("您确认要停用[" + userName + "]用户吗？")) {
			window.location = "${ctx}/user/disable/" + userId + ".do";
		}
	}
	function enableUser(userId, userName) {
		if (confirm("您确认要启用[" + userName + "]用户吗？")) {
			window.location = "${ctx}/user/enable/" + userId + ".do";
		}
	}
	function resetUserPassword(userId, userName) {
		if (confirm("您确认要重置[" + userName + "]用户的密码吗？")) {
			window.location = "${ctx}/user/rpw/" + userId + "/" + userName +".do";
		}
	}
	function updateUserPassword(userId) {
		window.location = "${ctx}/user/epw/" + userId + ".do";
	}
	
	
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
	}
	
	
	function toAddPage(){
	    var url= "${ctx}/user/add.do";
	    $.colorbox({
	        href:url,
	        iframe:true,
	        width:"550",
	        height:"500"
	    });
	}
	
	function plsc(){
		var ck = document.getElementsByName("ck");
		var ids ="";
		for(var x=0;x<ck.length;x++){
			if(ck[x].checked){
				ids+=ck[x].value+",";
			}
		}
		ids = ids.substring(0, ids.length-1);
		alert(ids);
		//alert(post_id);
		if(ids.length>0){
			if(confirm("确定要删除选中项吗？")){
				location.href="${ctx}/user/delBatchUser/"+ids+".do";
			}
		}else{
			confirm("请先勾选！")
		}
	}
	
</script>
	<style type="text/css">
		
		td {text-align:center}
	</style>
</head>

<body>
<div class="TAB_right">
<tags:message content="${message}" type="success" />
	<h1><b>位置：</b><a href="###">首页</a><span>&gt;</span><a href="###">用户列表</a><span>&gt;</span><a href="###">用户查询</a></h1>
	<div class="retrieve_list">
		<form action="${ctx}/user/list.do" method="post">
		<table class="retrieve_table" width="100%" cellpadding="0" cellspacing="0">
	    	<tr>
	        	<td>
		        	<label>用户名：</label>
		        	<input type="text" name="loginName" value="${user.loginName}">
	        	</td>
	            <td>
		            <label>姓名：</label>
		            <input type="text" name="userName" value="${user.userName}">
	            </td>
	            
	        	<td>
		        	<label>所属部门：</label>
		        			<select name="department_id" id="department_id"  style="width: 180px;height: 25px;">
	                    		<option value="">======请 选 择======</option>
	                    		<c:forEach  items="${list}" var="list">
	                    			<c:if test="${list.TYPE_CODE eq '01'}">
	                    				<option value="${list.CODE}" <c:if test="${user.department_id eq list.CODE}">selected</c:if> >${list.NAME}</option>
	                    			</c:if>
	                    		</c:forEach>
	                    	</select>
	        	</td>
	        </tr>
	        <tr align="right">
	        	<td colspan="3"><input class="form_now marginr10" type="submit" value="查询"></td>
	        </tr>
	    </table>
	    </form>
	</div>
	<div class="infor_base">
	  	<div class="btn_operate">
	  		<shiro:hasPermission name="user_manage:user_maintain:new">
	      	<ul>
	          	<li><a class="btn_staff" id="asd" onclick="toAddPage()"><span>新增人员</span></a></li>
	      	</ul>
	      	</shiro:hasPermission>
	      		<ul>
					<li><a class="btn_close"  onclick="plsc()"><span>批量删除</span></a></li>
				</ul>
	  	</div>
	  	<div class="container_12">
        	<div class="grid_8">
        		<table class="fancyTable" id="myTable02" cellpadding="0" cellspacing="0">
        			<thead>
        		        <tr>
        		        	<th style="width:30px;">选择</th>
        		            <th style="width:30px;">序号</th>
        		            <th style="width:80px;">用户名</th>
        		            <th style="width:80px;">姓名</th>
        		            <th style="width:100px;">所属部门</th>
        		            <th style="width:100px;">所属团队</th>
        		            <th style="width:80px;">职位级别</th>
        		            <th style="width:80px;">岗位类别</th>
        		            <th style="width:80px;">入职时间</th>
        		            <th style="width:100px;">邮箱</th>
        		            <th style="width:50px;">操作</th>
        		        </tr>
        		    </thead>
				<tbody>
				<c:forEach items="${pclist.datas}" var="user" varStatus="status">
				<tr>
					<td  class="numeric"><input type="checkbox" name="ck" value="${user.userId}"/><input type="hidden" name="userId" value="${user.userId}"/></td>
	              	<td class="numeric">${status.count}</td>
	              	<td  class="numeric">${user.loginName}</td>
	              	<td  class="numeric">${user.userName}</td>
	              	<td  class="numeric">${user.department_Name}</td>
	              	<td  class="numeric">${user.team_Name}</td>
	              	<td  class="numeric">${user.position_Name}</td>
	              	<td  class="numeric">${user.postName}</td>
	              	<td  class="numeric">${user.employed_date}</td>
	              	<td  class="numeric">${user.email}</td>
	              	<td  class="numeric operate_td" >
	              		<div class="relative">
                                    <a class="operate_btn">操作</a>
                                    <div class="pop_layer hide">
                                        <div class="cbox_content">
                                        	<shiro:hasPermission name="user_manage:user_maintain:update_sava">
                                            <a class="icon edit_icon opat_a" href="javascript:editUser(${user.userId});" title="编辑">编辑</a></br>
                                            </shiro:hasPermission>
                                            <%--
                                            <c:if test="${user.status == '0'}">
                                            	<shiro:hasPermission name="user_manage:user_maintain:stap">
		              							<a class="icon stop_icon opat_a" href="javascript:disableUser(${user.userId},'${user.userName}');" title="停用">停用</a></br>
		              							 </shiro:hasPermission>
		              						</c:if>
                                            <c:if test="${user.status == '1'}">
		              							<shiro:hasPermission name="user_manage:user_maintain:start">
		              							<a class="icon start_icon opat_a" href="javascript:enableUser(${user.userId},'${user.userName}');" title="启用">启用</a></br>
	              								</shiro:hasPermission>
	              							</c:if>
	              							<shiro:hasPermission name="user_manage:user_maintain:delete">
                                            <a class="icon del_icon opat_a" href="javascript:deleteUser(${user.userId},'${user.userName}');" title="删除">删除</a></br>
                                            </shiro:hasPermission>
                                            <shiro:hasPermission name="user_manage:user_maintain:password_reset">
                                            <a class="icon del_icon opat_a" href="javascript:resetUserPassword(${user.userId},'${user.userName}');" title="密码重置">密码重置</a></br>
                                            </shiro:hasPermission>
                                            --%>
                                            <shiro:hasPermission name="user_manage:user_maintain:password_save">
                                            <a class="icon del_icon opat_a" href="javascript:updateUserPassword(${user.userId});" title="密码修改">密码修改</a></br>
                                            </shiro:hasPermission>
                                            <div class="clear"></div>
                                        </div>
                                    </div>
                                </div>
		              	<%-- <a class="icon edit_icon" href="javascript:editUser(${user.id});" title="编辑"><img src="${ctx}/images/pencil.png"></a>
		              	<c:if test="${user.status == '0'}">
		              	<a class="icon stop_icon" href="javascript:disableUser(${user.id},'${user.userName}');" title="停用"><img src="${ctx}/images/hammer_screwdriver.png"></a>
		              	</c:if>
	              		<c:if test="${user.status == '1'}">
		              	<a class="icon start_icon" href="javascript:enableUser(${user.id},'${user.userName}');" title="启用"><img src="${ctx}/images/arrow_refresh.png"></a>
	              		</c:if>
		              	<a class="icon del_icon" href="javascript:deleteUser(${user.id},'${user.userName}');" title="删除"><img src="${ctx}/images/cross.png"></a>
		              	|
		              	<a class="icon del_icon" href="javascript:resetUserPassword(${user.id},'${user.userName}');" title="密码重置"><img src="${ctx}/images/pencil.png"></a>
		              	<a class="icon del_icon" href="javascript:updateUserPassword(${user.id});" title="密码修改"><img src="${ctx}/images/pencil.png"></a> --%>
	              	</td>
	          	</tr>
				</c:forEach>
				</tbody>
	      </table>
	      </div>
	      </div>
		</div>	
	    <div class="pages">
	    	<div class="lr15">
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
			</div>
	  	</div>
	</div>
</div>
</body>
<script type="text/javascript">
//parent.dhxLayout.progressOff();
	var dhxWins;
	dhxWins = new dhtmlXWindows();
	dhxWins.enableAutoViewport(true);
		if(parent.dhxLayout){
		parent.dhxLayout.progressOff();
		}
	//parent.dhxLayout.progressOff();
</script>
</html>
