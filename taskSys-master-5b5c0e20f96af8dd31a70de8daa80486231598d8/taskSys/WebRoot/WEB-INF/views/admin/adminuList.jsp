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
<script type="text/javascript" src="${ctx}/js/alert/g_alert.js?1=1"></script>
<script type="text/javascript" src="${ctx}/js/util/common.js"></script>
<script type="text/javascript" src="${ctx}/js/jquery.validate.js"></script>
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

function exportUsers(){
	var loginName = $("#lname").val()
	var userName = $("#uname").val()
	var department_id = $("#dtid").val()
	var company_id = $("#companyid").val()
	var speciality = $("#speciality").val()
	var status = $("#status").val()
	location.href = "${ctx}/user/exportUser.do?loginName="+loginName+"&userName="+userName+"&department_id="+department_id+"&company_id="+company_id+"&speciality="+speciality+"&status="+status;
	//layer.load('加载中...',0);
}


//到指定的分页页面
function topagePagination(page,maxResult){
    $("#page").val(page);
    $("#maxResult").val(maxResult);
    $("#f1").attr("method","POST");
    $("#f1").attr("action","${ctx}/user/list.do");
    $("#f1").submit();
}

</script>

<body>
<form id ="f1" action="${ctx}/user/list.do" method="POST">
<input type="hidden" id="page" name="page" value="1" />
<input type="hidden"  id="maxResult" name="maxResult" value="${pageView.maxresult}"/>
<input type="hidden" id="selectFlag" name="selectFlag" >
<input type="hidden" id="nowDate" name="nowDate" value="${nowDate}"/>
<input type="hidden" name="acgz" id="acgz" value="${acgz }">


<tags:message content="${message}" type="success" />
<div class="TAB_right">
<h1><b>位置：</b><a href="###">首页</a><span>&gt;</span><a href="###">用户管理</a></h1>
	<div class="retrieve_list">
			<table class="retrieve_table" width="100%" cellpadding="0" cellspacing="0">
					<td>
						<table>
							<tr>					
							   <td width="170">
		                        	 用户名：<input id="lname" name="loginName" type="text" class="srk" style="width: 120px" value="${user.loginName}"/>
							   </td>
								<td width="170">
									 姓名：<input id="uname" name="userName" type="text" class="srk"  style="width: 120px" value="${user.userName}"/>				
								</td>
							    <td width="190">
							           所属部门：<select id="dtid" name="department_id"  style="width: 65%;height: 22px;" class="sel">
				                            <option value="">全部</option>
					                    		<c:forEach  items="${list}" var="list">
					                    			<c:if test="${list.type_code eq '01'}">
					                    				<option value="${list.code}" <c:if test="${user.department_id eq list.code}">selected</c:if> >${list.name}</option>
					                    			</c:if>
					                    		</c:forEach>
			                 			</select>
										
								</td>
								<td width="190">
								所属公司：<select id="companyid" name="company_id"  style="width: 65%;height: 22px;" class="sel">
				                            <option value="">全部</option>
					                    		<c:forEach  items="${list}" var="list">
					                    			<c:if test="${list.type_code eq '00'}">
					                    				<option value="${list.code}" <c:if test="${user.company_id eq list.code}">selected</c:if> >${list.name}</option>
					                    			</c:if>
					                    		</c:forEach>
			                 			</select>
								</td>		
							</tr>
							<tr>
								<td width="275" colspan="2">
		                        	 特&nbsp;&nbsp;&nbsp;&nbsp;长：<input id="speciality" name="speciality" type="text" class="srk" style="width: 275px" value="${user.speciality}"/>
							    </td>
							    <td width="190">
		                        	用户状态：<select id="status" name="status" style="width: 65%;height: 22px;" class="sel">
				                            <option value="">全部</option>
				                            <option value="0" <c:if test="${user.status eq '0'}">selected</c:if>>在职</option>
				                            <option value="1" <c:if test="${user.status eq '1'}">selected</c:if>>离职</option>
			                 			</select>
							    </td>
								<td align="right" style="padding-right: 10px;">
									<input class="form_now marginr10" type="button" value="查询" onclick="selectSubmit()">
								</td>
							</tr>
						</table>
					</td>
					<td>
						<table>
							<tr>
								<td  >	
									<shiro:hasPermission name="user_manage:user_maintain:new">
			                    		<a href="javascript:(void);" style="color: #3A3A3A" onclick="addShow()" class="fl add-btn popup-btn1">新增用户</a>
			               		 	</shiro:hasPermission>
								</td  >
								<td  >
									<shiro:hasPermission name="user_manage:user_maintain:delete">
			                    		<a href="javascript:(void);" style="color: #3A3A3A" class="fl del-btn mg-l10 popup-btn2">删除用户</a>
			                		</shiro:hasPermission>
		                		</td  >
								<td  >
			                		<shiro:hasPermission name="user_manage:user_maintain:stop">
			                    		<a href="javascript:(void);" style="color: #3A3A3A" class="fl del-btn mg-l10 popup-btn5">停用用户</a>
			                		</shiro:hasPermission>
			                	</td>
			                </tr>
			                <tr>
			                	<td >
			                		<shiro:hasPermission name="user_manage:user_maintain:inport">
			                    		<a href="javascript:(void);" style="color: #3A3A3A" onclick="importExcel()" class="fl inp-btn  popup-btn">导入Excel</a>
			               		 	</shiro:hasPermission>
			               		</td  >
								<td  >
			               		 	<shiro:hasPermission name="user_manage:user_maintain:export">
			                    		<a href="${ctx}/static/用户信息模板.xlsx"  style="color: #3A3A3A"  class="fl inp-btn mg-l10 popup-btn">导出模板</a>
			               		 	</shiro:hasPermission>
		               		 	</td  >
								<td  >
			               		 	<shiro:hasPermission name="user_manage:user_maintain:exportUser">
			                    		<a href="#" onclick="exportUsers()"  style="color: #3A3A3A"  class="fl inp-btn mg-l10 popup-btn">导出数据</a>
			               		 	</shiro:hasPermission>
								</td>		    															
							</tr>
						</table>
					</td>
			</table>
	</div>
	<div class="infor_base">
	<div  class="btn_operate">
		</div>
		   <div class="container_12">
        	<div class="grid_8">
        	<form id="listForm" name="listForm" method="post">
        		<table class="fancyTable" id="myTable02" cellpadding="0" cellspacing="0" style="table-layout:fixed;">
        		    <thead>
        		        <tr>
        		        	<th style="width:35px;">选择</th>
        		        	<th style="width:70px;">用户名</th>
        		            <th style="width:80px;">姓名</th>
        		            <th style="width:130px;" >所属公司</th>
        		            <th style="width:130px;" >所属部门</th>
        		            <th style="width:80px;">所属团队</th>
        		            <th style="width:90px;">职位级别</th>
        		            <th style="width:80px;">岗位类别</th>
        		            <th style="width:90px;">角色</th>
        		            <th style="width:80px;">入职时间</th>
                            <th style="width:200px;">邮箱</th>
                            <th style="width:50px;">状态</th>
                            <th style="width:200px;">特长</th>
        		            <th style="width:200px;">操作</th>
        		        </tr>
        		    </thead>
        		    <tbody id="bodyTR">
        		          <c:forEach items="${pageView.records}" var="user" varStatus="status">
                        <tr id="line${status.index}" onclick="lineColor('${status.index}');">
                            <td><input id="ckid${status.index}" type="checkbox" name="ck" value="${user.userId}"/><input type="hidden" name="userId" value="${user.userId}"/></td>
                            <td  style="word-wrap:break-word;" >${user.loginName }</td>
                            <td  style="word-wrap:break-word;" >${user.userName }</td>
                            <td >${user.company_Name}</td>
                            <td >${user.department_Name}</td>
                            <td >${user.team_Name}</td>
                            <td>${user.position_Name}</td>
                            <td >${user.postName}</td>
                            <td >${user.roleName}</td>
                            <td >${user.employed_date}</td>
                            <td  style="word-wrap:break-word;" >${user.email}</td>
                            <td >
                            	<c:if test="${user.status == 0}">在职</c:if>
                            	<c:if test="${user.status == 1}">离职</c:if>
                            </td>
                            <td title="${user.speciality}">
	                            ${fn:substring(user.speciality, 0, 15)}<c:if test="${fn:length(user.speciality) > 15}">...</c:if>
                            </td>
                            <td >
	                            <shiro:hasPermission name="user_manage:user_maintain:update_sava">
	                            	<a href="javascript:editUser('${user.userId }');" style="color: #1478BB"  class="cblue4 popup-btn3">编辑</a>
	                            </shiro:hasPermission>
	                            <shiro:hasPermission name="user_manage:user_maintain:password_reset">
	                            	<a href="javascript:resetUserPassword('${user.userId }','${user.userName }');" style="color: #1478BB" class="cblue4">重置密码</a>
	                            </shiro:hasPermission>
	                            <shiro:hasPermission name="user_manage:user_maintain:nurse">
	                            	<a href="javascript:resetUserNurse('${user.loginName }');" style="color: #1478BB" class="cblue4 popup-btn10">考勤规则设置</a>
	                            </shiro:hasPermission>
                            </td>
                           </tr>
                    </c:forEach>
        		    </tbody>
        		</table>
        		</form>
        	</div>
        	<div class="clear"></div>
        </div>	
     	<div class="pages">
            <%@ include file="/common/pagination.jsp" %>
        </div>
	</div>

</div>
  <!-- 弹窗 -->

    <!-- 新增用户 -->
    <form id="saveForm" name="saveForm" action="${ctx}/user/save.do" autocomplete="off" method="post">
    <div class="popup popup-box1" style="margin-left:-380px;top:0%">
    
    <input type="hidden" id="param1" name="param1"  />
    <input type="hidden" id="param2" name="param2"  />
    <input type="hidden" id="param3" name="param3"  />
    <input type="hidden" id="param4" name="param4"  />
    
        <div class="tc-box" style="width:760px;">
            <div class="titdiv">
                <span>新增用户</span>
                <a href="#" class="tc-close fr" onclick="closeDIV()"></a>
            </div>

            <ul class="ptop15">
            <div id="checkDiv"></div>
                
                <li class="fn-clear">
                	<div style="width:380px;float:left;">
	                    <label class="fl txt-r mg-r10"><span style="color: red;">*&nbsp;</span>用户名</label>
	                    <input id="loginName" name="loginName" type="text" class="srk fl"   placeholder="请使用工号做为用户名" style="width: 180px;"><span id="checkLoginName" style="color: red;width: 100px;"></span>
                    </div>
                    <div style="width:380px;float:left;">
	                    <label class="fl txt-r mg-r10"><span style="color: red;">*&nbsp;</span>姓&nbsp;&nbsp;&nbsp;&nbsp;名</label>
	                    <input id="userName" name="userName" type="text" class="srk fl"  maxlength="20"  placeholder="请输入真实姓名" style="width: 180px;"><span id="checkUserName" style="color: red;width: 100px;""></span>
                    </div>
                </li>
                
                <li class="fn-clear">
                	<div style="width:380px;float:left;">
	                    <label class="fl txt-r mg-r10">所属公司</label>
	                    <select id="comp_id" name="company_id" class="sel fl" style="width: 180px;">
	                        <option value="">======请 选 择======</option>
		                    		<c:forEach  items="${mlist}" var="list">
		                    			<c:if test="${list.type_code eq '00'}">
		                    				<option value="${list.code}">${list.name}</option>
		                    			</c:if>
		                    		</c:forEach>
	                    </select>
                    </div>
               		<div style="width:380px;float:left;">
	                    <label class="fl txt-r mg-r10">职位级别</label>
	                    <select id="position_id" name="position_id" class="sel fl" style="width: 180px;">
	                        <option value="">======请 选 择======</option>
		                    		<c:forEach  items="${mlist}" var="list">
		                    			<c:if test="${list.type_code eq '03'}"><option value="${list.code }">${list.name}</option></c:if>
		                    		</c:forEach>
	                    </select>
	                </div>
                </li>
                <li class="fn-clear">
                	<div style="width:380px;float:left;">
	                    <label class="fl txt-r mg-r10">所属部门</label>
	                    <select id="department_id" name="department_id" class="sel fl" onchange="toChnage(this.value)" style="width: 180px;">
	                        <option value="">======请 选 择======</option>
		                    		<c:forEach  items="${mlist}" var="list">
		                    			<c:if test="${list.type_code eq '01'}">
		                    				<option value="${list.code}">${list.name}</option>
		                    			</c:if>
		                    		</c:forEach>
	                    </select>
                    </div>
	                <div style="width:380px;float:left;">
	                    <label class="fl txt-r mg-r10">所属团队</label>
	                    <select name="team_id" id="team_id" class="sel fl" style="width: 180px;">
	                        <option value="">======请 选 择======</option>
	                    </select>
	                </div>
                </li>
                <li class="fn-clear">
	               	<div style="width:380px;float:left;">
	                    <label class="fl txt-r mg-r10"><span style="color: red;">*&nbsp;</span>入职时间</label>
	                    <input type="text" id="employed_date"  name="employed_date"  onclick="WdatePicker()" onfocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd'})" class="srk fl" " placeholder="入职时间不能大于当前时间" style="width: 180px;">
	                	<span id="checkEmployed_date" style="color: red;"></span>
	                </div>
	               	<div style="width:380px;float:left;">
	                    <label class="fl txt-r mg-r10"><span style="color: red;">*&nbsp;</span>邮&nbsp;&nbsp;&nbsp;&nbsp;箱</label>
	                    <input id="email" name="email" onblur="value=value.replace(/\s/g, '')" type="text"  class="srk fl" maxlength="42" placeholder="请输入工作邮箱" style="width: 180px;" ><span id="checkEmail" style="color: red;"></span>
	                </div>
                </li>
                <li class="fn-clear">
	               	<div style="width:380px;float:left;">
	                    <label class="fl txt-r mg-r10"><span style="color: red;">*&nbsp;</span>角&nbsp;&nbsp;&nbsp;&nbsp;色</label>
	                    <textarea id="roleArea2" name="roleArea" rows="2" cols="25" style="resize:none;" readonly="readonly" placeholder="请选择角色"></textarea>
	                	<span id="rr" style="color: red;"></span>
	                	<input class="form_now" type="button" onclick="selectMutliRole('',1)" value="选择..">
	                	<br>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
	                	<span id="checkRole" style="color: red;"></span>
	                </div>
	                <div style="width:380px;float:left;">
	                    <label class="fl txt-r mg-r10">岗位类型</label>
	                    <select id="jobId" name="jobId" class="sel fl" style="width: 180px;">
	                        <option value="">======请 选 择======</option>
		                    		<c:forEach  items="${mlist}" var="list">
		                    			<c:if test="${list.type_code eq '04'}"><option value="${list.code }">${list.name}</option></c:if>
		                    		</c:forEach>
	                    </select>
	                </div>
                </li>
                <li class="fn-clear">
                    <label class="fl txt-r mg-r10">特&nbsp;&nbsp;&nbsp;&nbsp;长</label>
                    <textarea id="speciality" name="speciality" rows="8" cols="105"  maxlength=500; placeholder="请输入500字以内" onblur="validateXml2(this)"></textarea>
                </li>
            </ul>
            <div id="addOfRole"></div>
            <div class="txt-c pd-t15">
                <input class="form_now" type="button" id="but" onclick="addUser()"  value="保存">&nbsp;&nbsp;&nbsp;&nbsp;
                <input class="form_now" type="button" onclick="javascript:closeDIV()" value="取消">
            </div>
          
        </div>
    </div>
    </form>
    <!-- 删除用户 -->
    <div class="popup popup-box2" style="margin-left:-195px;">
        <div class="tc-box">
            <div class="title fn-clear">
                <span>提示</span>
            </div>

            <div  class="txtDelete">是否确定删除该用户？</div>
            <div class="txtTiti">
                <a href="javascript:plsc();" class="tc-btn1">确&nbsp;&nbsp;认</a>
                <a href="javascript:(void);" class="tc-btn2 mg-l15">取&nbsp;&nbsp;消</a>
            </div>
        </div>
    </div>
    
     <!-- 停用用户 -->
    <div class="popup popup-box5" style="margin-left:-195px;">
        <div class="tc-box">
            <div class="title fn-clear">
                <span>提示</span>
            </div>

            <div  class="txtDelete">是否确定停用该用户？</div>
            <div class="txtTiti">
                <a href="javascript:plty();" class="tc-btn1">确&nbsp;&nbsp;认</a>
                <a href="javascript:(void);" class="tc-btn2 mg-l15">取&nbsp;&nbsp;消</a>
            </div>
        </div>
    </div>

    <!-- 编辑用户 -->
    <form id="editForm" name="editForm" action="${ctx}/user/save.do" method="post">
    <div id="div1" class="popup popup-box3" style="margin-left:-380px;top:0%">
	    
        <div class="tc-box" style="width:760px;">
            <div class="title fn-clear">
                <span>编辑用户</span>
                <a href="#" class="tc-close fr" onclick="closeDIV()"></a>
            </div>
			<input type="hidden" id="did" name="did" value="${user.department_id}"/>
					 <input type="hidden" id="param11" name="param1"  />
				    <input type="hidden" id="param21" name="param2"  />
				    <input type="hidden" id="param31" name="param3"  />
				    <input type="hidden" id="param41" name="param4"  />
            <ul class="pd-t15">
                <li class="fn-clear">
	                <div style="width:380px;float:left;">
	                    <label class="fl txt-r mg-r10"><span style="color: red;">*&nbsp;</span>用户名</label>
	                    <input id="editloginName"  name="loginName" type="text"  placeholder="请使用工号做为用户名"  class="srk fl" style="width: 180px;">
	                    <span id="checkEditloginName" style="color: red;"></span>
	                    <input id="edituserId" name="userId" type="hidden">
	                </div>
	                <div style="width:380px;float:left;">
	                    <label class="fl txt-r mg-r10"><span style="color: red;">*&nbsp;</span>姓&nbsp;&nbsp;&nbsp;&nbsp;名</label>
	                    <input id="edituserName"  name="userName" type="text" maxlength="20" placeholder="请输入真实姓名" class="srk fl"  style="width: 180px;">
	                    <span id="checkEdituserName" style="color: red;"></span>
	                </div>
                </li>
                
                <li class="fn-clear">
		            <div style="width:380px;float:left;">
	                    <label class="fl txt-r mg-r10">所属公司</label>
	                    <select name="company_id" id="editcompany_id" class="sel fl" style="width: 180px;">
	                        <option value="">======请 选 择======</option>
	                    </select>
	                </div>
		            <div style="width:380px;float:left;">
	                    <label class="fl txt-r mg-r10">职位级别</label>
	                    <select name="position_id" id="editposition_id" class="sel fl" style="width: 180px;">
	                        <option value="">======请 选 择======</option>
	                    </select>
	                </div>
                </li>
                <li class="fn-clear">
	                <div style="width:380px;float:left;">
	                    <label class="fl txt-r mg-r10">所属部门</label>
	                    <select name="department_id" id="editdepartment_id" class="sel fl" onchange="toChnage2(this.value)" style="width: 180px;">
	                        <option value="">======请 选 择======</option>
	                    </select>
	                </div>
		            <div style="width:380px;float:left;">
	                    <label class="fl txt-r mg-r10">所属团队</label>
	                    <select name="team_id" id="editteam_id" class="sel fl" style="width: 180px;">
	                        <option value="">======请 选 择======</option>
	                    </select>
	                </div>
                </li>
                <li class="fn-clear">
		            <div style="width:380px;float:left;">
	                    <label class="fl txt-r mg-r10"><span style="color: red;">*&nbsp;</span>角&nbsp;&nbsp;&nbsp;&nbsp;色</label>
	                    <textarea id="roleArea" rows="2" cols="28" style="resize: none;" readonly="readonly" placeholder="请选择角色"></textarea><span id="oo" style="color: red;"></span><span id="changeRoles"></span>
	                	<br>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
	                	<span id="checkEditRole" style="color: red;"></span>
	                </div>
	                <div style="width:380px;float:left;">
	                    <label class="fl txt-r mg-r10">岗位类型</label>
	                    <select name="jobId" id="editjobId" class="sel fl" style="width: 180px;">
	                        <option value="">======请 选 择======</option>
	                    </select>
	                </div>
                </li>
                <li class="fn-clear">
		            <div style="width:380px;float:left;">
	                    <label class="fl txt-r mg-r10"><span style="color: red;">*&nbsp;</span>入职时间</label>
	                    <input id="editemployed_date" name="employed_date" type="text"  placeholder="入职时间不能大于当前时间"  onclick="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd'})" class="srk fl"  style="width: 180px;">
	                	<span id="checkEditemployed_date" style="color: red;"></span>
	                </div>
		            <div style="width:380px;float:left;">
	                    <label class="fl txt-r mg-r10"><span style="color: red;">*&nbsp;</span>邮&nbsp;&nbsp;&nbsp;&nbsp;箱</label>
	                    <input name="email" id="editemail" onblur="value=value.replace(/\s/g, '')"  type="text" maxlength="42"  placeholder="请输入工作邮箱" class="srk fl" style="width: 180px;">
	                	<span id="checkEditemail" style="color: red;"></span>
	                </div>
                </li>
                <li class="fn-clear">
                    <label class="fl txt-r mg-r10">特&nbsp;&nbsp;&nbsp;&nbsp;长</label>
                    <textarea id="edit_speciality" name="speciality" rows="8" cols="105" maxlength=500;  placeholder="请输入500字以内" onblur="validateXml2(this)"></textarea>
                </li>
            </ul>
            <div class="txt-c pd-t15">
                <input class="form_now" type="button" onclick="updateUser()"  value="保存">&nbsp;&nbsp;&nbsp;&nbsp;
                 <input class="form_now" type="button" onclick="javascript:closeDIV()" value="取消">
            </div>
            <div id="editOfRole"></div>
            	
            
        </div>
    </div>
	</form>
	
	<!-- 编辑用户 -->
    <form id="editForm" name="editForm" action="${ctx}/user/save.do" method="post">
    	<div class="popup popup-box10" style="margin-left:-195px;">
    	 <input type="hidden" id="acgzId" name="acgzId"  />
    	<div class="tc-box">
    		<div class="title fn-clear">
                    <span>设置考勤规则</span>
                    <a href="javascript:(void);" class="tc-close fr"></a>
                </div>
                <ul class="pd-t25" style="padding-left:35%;" >
	                <li class="fn-clear">
	                	<input name="selTime"  id="sel1" type="radio" value="1" onclick="getRadio()">&nbsp;&nbsp;08:30---17:30
	    			</li>
	    			 <li class="fn-clear">
	                	<input name="selTime"  id="sel2" type="radio" value="2" onclick="getRadio()">&nbsp;&nbsp;09:00---18:00
	    			</li>
	    			 <li class="fn-clear">
	                	<input name="selTime"  id="sel3" type="radio" value="3" onclick="getRadio()">&nbsp;&nbsp;09:30---18:30
	    			</li>
	    			 
    			</ul>
    			<li class="fn-clear" style="padding-left:22%;">
	                	<div style="float:left;"> 哺乳期：<input name="selTime"  id="sel4" type="radio" value="4" onclick="getRadio()">&nbsp;&nbsp;</div>
	                	<div id="stime" style="display:none;">
	                	<input style="width: 100px;" readonly="readonly" type="text" id="setTime" name="setTime" placeholder="开始时间"
		                         onclick="WdatePicker()" onfocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd'})" class="Wdate">
		                    &nbsp;~&nbsp;<input style="width: 100px;" readonly="readonly" type="text" id="endTime" name="endTime" placeholder="结束时间"
		                         onclick="WdatePicker()" onfocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd'})" class="Wdate">
	    				</div>
	    			</li>
    			<ul class="title fn-clear1"></ul>
    		<div class="txt-c pd-t15">
    			<a href="javascript:(void);" class="tc-btn11">选&nbsp;&nbsp;择</a>
    			<a href="javascript:(void);" class="tc-btn2 mg-l15">取&nbsp;&nbsp;消</a>
    		</div>
    	</div>
    </div>
     </form>
    <div id="covered" onclick="closeDIV()"></div>
    <input id="pid" name="pid" value="" type="hidden"/>
</form>
</body>
<script type="text/javascript">
function loadInput(){				
	var xinheSuggest = new InputSuggest({
		width: 190,
		input: document.getElementById('email'),
		data: ['creditharmony.cn']
	});
}
</script>
<script type="text/javascript">
  //角色弹出选择
	function selectMutliRole(userId,flag){
	  //alert(userId);
	  //alert(flag);  flag  标识符，1表示新增操作，2表示编辑操作
	    var url= "${ctx}/sltwin/role/multi.do?userId="+userId+"&flag="+flag;
	    $.colorbox({
	        href:url,
	        iframe:true,
	        width:"500",
	        height:"350"
	    });
	}
  
  
		  function resetUserNurse(loginName){
		  		$("#acgzId").val(loginName);
		  		url = "${ctx}/user/check/listSys.do";
				$.ajax({	 
					type : "POST",  
					url: url, 
					data : {"loginName":loginName},
					dataType : 'json',
					success: function(data){
						if(data.acgz==1){
			 				$("#sel1").attr("checked",true)
				 		}
				 		if(data.acgz==2){
				 			$("#sel2").attr("checked",true)
				 		}
				 		if(data.acgz==3){
				 			$("#sel3").attr("checked",true)
				 		}
				 		if(data.acgz==4){
				 			$("#stime").show();
				 			$("#sel4").attr("checked",true)
				 			var setTime = $("#setTime").val(data.starttime);
         					var endTime = $("#endTime").val(data.endtime);
				 		}else{
				 			$("#stime").hide();
				 			var setTime = $("#setTime").val("");
         					var endTime = $("#endTime").val("");
				 		}
					}
				});
		  }
         
         function getRadio(){
         	var selTime = $("input[name='selTime']:checked ").val();
         	if(selTime==4){
         		$("#stime").show();
         	}else{
         		$("#stime").hide();
         		var setTime = $("#setTime").val("");
         		var endTime = $("#endTime").val("");
         	}
         }
         
          $('.tc-close').click(function(){
          		$(".popup-box10").hide();
          		$("#stime").hide();
         		$("#covered").hide()
	            parent.$("#covered3").hide()
	            parent.$("#covered4").hide()
          })
         
         $('.tc-btn11').click(function(){
         	var selTime = $("input[name='selTime']:checked ").val();
         	var setTime = $("#setTime").val();
         	var endTime = $("#endTime").val();
         	var loginName = $("#acgzId").val();
         	if(selTime==undefined){
         		alert("请选择规则日期！");
         		return false;
         	}
			if(selTime==4){
         			if(setTime==null||setTime==""){
         				alert("请选择开始时间！");
         				return false;
         			}
         			if(endTime==null||endTime==""){
         				alert("请选择结束时间！");
         				return false;
         			}
         			if(endTime<=setTime){
         				alert("结束时间必须大于开始时间！");
	         			return false;
         			}
         	}
	         	url = "${ctx}/attence/attenceSys.do";
				$.ajax({	 
					type : "POST",  
					url: url, 
					data : {"selTime":selTime,"setTime":setTime,"endTime":endTime,"loginName":loginName},
					dataType : 'json',
					success: function(data){
						//$("input[name='selTime'][value=@selTime]").attr("checked":true);
						alert("设置成功");
						$('.popup-box10').hide()
						$("#covered").hide()
			            parent.$("#covered3").hide()
			            parent.$("#covered4").hide()
					}
	          })
   })
  
	function selectMutliRole_callback(ret,flag){
		if(flag == 2){
			//alert("22222");
			$("input[name^='roles[']").remove();
			var names = "";
			for (var i in ret) {
				var tmp = ret[i].split(",");
				$("#editOfRole").append("<input type=\"hidden\" name=\"roles["+i+"].id\" value=\""+tmp[0]+"\">");
				names += tmp[1] + "\n";
			}
			$("#roleArea").val(names);
			}else if(flag == 1){
				//alert("1111111");
				$("input[name^='roles[']").remove();
				var names = "";
				for (var i in ret) {
					var tmp = ret[i].split(",");
					$("#addOfRole").append("<input type=\"hidden\" name=\"roles["+i+"].id\" value=\""+tmp[0]+"\">");
					names += tmp[1] + "\n";
				}
				$("#roleArea2").val(names);
			}
		}
    
    $(document).ready(function(){
     $(".popup-btn1").click(function(){
           $("#covered").show();
           parent.$("#covered3").show();
           parent.$("#covered4").show();
          $(".popup-box1").show();
          loadInput();            //用户名邮箱后缀自动带出
        })
      $(".popup-btn2").click(function(){
    	  
    	  var ck = document.getElementsByName("ck");
  		  var ids ="";
  		  for(var x=0;x<ck.length;x++){
  			 if(ck[x].checked){
  				ids+=ck[x].value+",";
  			  }
  		  }
  		  ids = ids.substring(0, ids.length-1);
  		//alert(post_id);
  		if(ids.length>0){
  			 $("#covered").show()
  	         $(".popup-box2").show()
  		}else{
  			confirm("请先勾选！")
  		}
    	  
        })
        
        $(".popup-btn5").click(function(){
    	  
    	  var ck = document.getElementsByName("ck");
  		  var ids ="";
  		  for(var x=0;x<ck.length;x++){
  			 if(ck[x].checked){
  				ids+=ck[x].value+",";
  			  }
  		  }
  		  ids = ids.substring(0, ids.length-1);
  		//alert(post_id);
  		if(ids.length>0){
  			 $("#covered").show()
  	         $(".popup-box5").show()
  		}else{
  			confirm("请先勾选！")
  		}
    	  
        })
	 $(".popup-btn10").click(function(){
          $("#covered").show()
          $(".popup-box10").show()
          parent.$("#covered3").show()
          parent.$("#covered4").show()
     })
      $(".popup-btn3").click(function(){
          $("#covered").show()
          $(".popup-box3").show()
          parent.$("#covered3").show()
          parent.$("#covered4").show()
        })
      $(".tc-btn1").click(function(){
          $(".popup-box1").hide()
          $(".popup-box2").hide()
          $(".popup-box3").hide()
          $(".popup-box10").hide()
          $("#covered").hide()
        })

      $(".tc-btn2").click(function(){
      		$("#stime").hide();
          $(".popup-box1").hide()
          $(".popup-box2").hide()
          $(".popup-box3").hide()
          parent.$("#covered3").hide()
          parent.$("#covered4").hide()
          $(".popup-box10").hide()
          $(".popup-box5").hide()
          $("#covered").hide()
        })
    });
  </script>
<script type="text/javascript">
	$(document).ready(function(){
		$(".fht-tbody").css("height", "85%");
		$(".grid_8").css("height","75%");
	})
	
	 //按enter自动登录
   // document.onkeydown=function(event){ 
	  // e = event ? event :(window.event ? window.event : null); 
	   // if(e.keyCode==13){ 
	    //	selectSubmit();
	   // } 
  //  } 
	
	function selectSubmit(){
		$("#f1").attr("method","POST"); 
		$("#f1").submit();
		layer.load('加载中...',0);
	}	
    function plsc(){
    	var param1 = $("#lname").val();
    	var param2 = $("#uname").val();
    	var param3 = $("#dtid").val();
    	var param4 = $("#companyid").val();
    	
		var ck = document.getElementsByName("ck");
		var ids ="";
		for(var x=0;x<ck.length;x++){
			if(ck[x].checked){
				ids+=ck[x].value+",";
			}
		}
		ids = ids.substring(0, ids.length-1);
		//alert(post_id);
		if(ids.length>0){
				location.href="${ctx}/user/delBatchUser/"+ids+".do?param1="+param1+"&param2="+param2+"&param3="+param3+"&param4="+param4+"&param5="+1;
		}else{
			confirm("请先勾选！")
		}
	}
    function plty(){
    	var param1 = $("#lname").val();
    	var param2 = $("#uname").val();
    	var param3 = $("#dtid").val();
    	var param4 = $("#companyid").val();
    
		var ck = document.getElementsByName("ck");
		var ids ="";
		for(var x=0;x<ck.length;x++){
			if(ck[x].checked){
				ids+=ck[x].value+",";
			}
		}
		ids = ids.substring(0, ids.length-1);
		//alert(post_id);
		if(ids.length>0){
				location.href="${ctx}/user/stopBatchUser/"+ids+".do?param1="+param1+"&param2="+param2+"&param3="+param3+"&param4="+param4+"&param5="+1;
		}else{
			confirm("请先勾选！")
		}
	}
	function editUser(userId){
		url = "${ctx}/user/edit.do";
		$.ajax({ 
			type : "POST",  
			url: url, 
			data : {"userId":userId},
			dataType : 'json',
			success: function(data){
					$("#editloginName").val(data.loginName);
				//	$("#editpassword").val(data.password);
					$("#edituserName").val(data.userName);
					$("#editemployed_date").val(data.employed_date);
					$("#editemail").val(data.email);
					$("#edituserId").val(data.userId);
					$("#edit_speciality").val(data.speciality);
					//debugger;
					//每次加载之前现清空
					$('#editcompany_id').empty();
					$('#editcompany_id').append('<option value="">======请 选 择======</option>');
					$('#editdepartment_id').empty();
					$('#editdepartment_id').append('<option value="">======请 选 择======</option>');
					$('#editteam_id').empty();
					$('#editteam_id').append('<option value="">======请 选 择======</option>');
					$('#editposition_id').empty();
					$('#editposition_id').append('<option value="">======请 选 择======</option>');
					$('#editjobId').empty();
					$('#editjobId').append('<option value="">======请 选 择======</option>');
					
					for(var i=0;i<data.list.length;i++){
						
						if(data.list[i].type_code=='00'){
							if(data.company_id == data.list[i].code){
								//$('#pid').val(data.list[i].id);
								$('#editcompany_id').append('<option value="'+data.list[i].code+'" selected="selected" >'+data.list[i].name+'</option>');
							}else{
								$('#editcompany_id').append('<option value="'+data.list[i].code+'">'+data.list[i].name+'</option>');
							}
						}
					}
					for(var i=0;i<data.list.length;i++){
						
						if(data.list[i].type_code=='01'){
							if(data.department_id == data.list[i].code){
								$('#pid').val(data.list[i].id);
								$('#editdepartment_id').append('<option value="'+data.list[i].code+'" selected="selected" >'+data.list[i].name+'</option>');
							}else{
								$('#editdepartment_id').append('<option value="'+data.list[i].code+'">'+data.list[i].name+'</option>');
							}
						}
					}
					for(var i=0;i<data.list.length;i++){
						 if(data.list[i].type_code=='02'){
							if(data.team_id == data.list[i].code){
								$('#editteam_id').append('<option value="'+data.list[i].code+'" selected="selected" >'+data.list[i].name+'</option>');
							}else{
								var pid=$('#pid').val();
								if(pid==data.list[i].parent_id ){
									$('#editteam_id').append('<option value="'+data.list[i].code+'">'+data.list[i].name+'</option>');
									}
								}
						}
					} 
					for(var i=0;i<data.list.length;i++){
						  if(data.list[i].type_code=='03'){
							if(data.position_id == data.list[i].code){
								$('#editposition_id').append('<option value="'+data.list[i].code+'" selected="selected" >'+data.list[i].name+'</option>');
							}else{
								$('#editposition_id').append('<option value="'+data.list[i].code+'">'+data.list[i].name+'</option>');
							}
						}
					} 
					for(var i=0;i<data.list.length;i++){
						 if(data.list[i].type_code=='04'){
							if(data.jobId == data.list[i].code){
								$('#editjobId').append('<option value="'+data.list[i].code+'" selected="selected" >'+data.list[i].name+'</option>');
							}else{
								$('#editjobId').append('<option value="'+data.list[i].code+'">'+data.list[i].name+'</option>');
							}
						}
					}
					//debugger;
					//alert(data.map);
					var rname = "";
			for(var i=0;i<data.map.length;i++){
						//alert(data.map[i].roleName);
						rname += data.map[i].roleName+ "\n";
						$("#dd").append("<input type=\"hidden\"  name=\"roles["+i+"].id\" value=\""+data.map[i].id+"\">");
					//alert(rname);
			}
					
				$("#roleArea").val(rname);
				$("#changeRoles input").remove();
				$("#changeRoles").append("<input class=\"form_now\"  type=\"button\"  onclick=\"selectMutliRole("+data.userId+",2)\" value=\"选择\">");
			//document.getElementById("roleArea").innerText =sss;
				}
      		
      	});
	}
	
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
					$('#team_id').append('<option value="'+data.list[i].code+'">'+data.list[i].name+'</option>');
					
				}
				
				}
      		
      	});
	}
	
	function toChnage2(id){
		$("#editteam_id option:not(:first)").remove();
		url = "${ctx}/user/change/team.do";
		$.ajax({ 
			type : "POST",  
			url: url, 
			data : "code="+id,
			dataType : 'json',
			success: function(data){
				for(var i=0;i<data.list.length;i++){
					$('#editteam_id').append('<option value="'+data.list[i].code+'">'+data.list[i].name+'</option>');
					
				}
				
				}
      		
      	});
	}
	
	 function goBack(){
	    	location.reload();
	    }
	
	function lineColor(index){
		$("#line"+index).addClass( 'active' ).siblings().removeClass( 'active' );
		//var ck = document.getElementById("ckid"+index);
		//if(ck.checked){
		//	ck.checked=false;
		//}else{
		//	ck.checked=true;
		//}
	}
	function resetUserPassword(userId, userName) {
		if (confirm("您确认要重置[" + userName + "]用户的密码吗？")) {
			window.location = "${ctx}/user/rpw/" + userId + "/" + userName +".do";
		}
	}
	
</script>

<script type="text/javascript">

	
		 //----------------------------------新增用户     验证----------------------------------------------
	    
		  Date.prototype.format = function(format){ 
			  var o = { 
			  "M+" : this.getMonth()+1, //month 
			  "d+" : this.getDate(), //day 
			  "h+" : this.getHours(), //hour 
			  "m+" : this.getMinutes(), //minute 
			  "s+" : this.getSeconds(), //second 
			  "q+" : Math.floor((this.getMonth()+3)/3), //quarter 
			  "S" : this.getMilliseconds() //millisecond 
			  }

			  if(/(y+)/.test(format)) { 
			  format = format.replace(RegExp.$1, (this.getFullYear()+"").substr(4 - RegExp.$1.length)); 
			  }

			  for(var k in o) { 
			  if(new RegExp("("+ k +")").test(format)) { 
			  format = format.replace(RegExp.$1, RegExp.$1.length==1 ? o[k] : ("00"+ o[k]).substr((""+ o[k]).length)); 
			  } 
			  } 
			  return format; 
			  }
		  
		  
		    
		   function addUser(){
			   jinyong();
		    	var loginName = $("#loginName").val();
		    	var userName = $("#userName").val();
		    	
		    	var roleArea2 = $("#roleArea2").val();
		    	var employed_date = $("#employed_date").val();
		    	var email = $("#email").val();
		    	url = "${ctx}/user/checkLoginName.do";
				
		    	var reg2 = /^[a-zA-Z0-9]{6,16}$/;
		    	var reg = /^[a-z0-9]([a-z0-9]*[-_\.]?[a-z0-9]+)*@([a-z0-9]*[-_]?[a-z0-9]+)+[\.][a-z]{2,3}([\.][a-z]{2})?$/;
		    	if(loginName == "" || loginName == null){
					$("#checkLoginName").text("× 不可为空！");
					$("#loginName").focus(); 
					qiyong();
					return;
				}else if(!reg2.test(loginName)){
					$("#checkLoginName").text("× 格式不正确!"); 
					//$("#loginName").val("");
		    		$("#loginName").focus();
		    		qiyong();
		    		return;
				}else{
					$("#checkLoginName").text(" √"); 
				}
		    	
		    	var re = "";
		    	$.ajax({ 
					type : "POST",  
					url: url, 
					data : "loginName="+loginName,
					dataType : 'text',
					async: false, 
					success: function(data){
						if(data > 0){
							$("#checkLoginName").text("× 用户已存在");
							re = 1;
							$("#loginName").focus();
							qiyong();
						}else{
							$("#checkLoginName").text(" √");
						}
						
						}
		      	});
		    	if(re == 1){
		    		qiyong();
	        		return;
	        	}
		    	
		    	var regUserName = /^[\u4e00-\u9fa5a-zA-Z]{2,20}$/;
		    	if(userName == "" || userName == null){
		  		  $("#checkUserName").text("× 不可为空！");
		  		  $("#userName").focus(); 
		  			qiyong();
		  			return;
		  	  	}else if(validateXml(userName)==1){
		  	  		 $("#checkUserName").text("× 有非法字符！");
		  	  		 $("#userName").focus(); 
		  	  		 qiyong();
		  	  		 return;
		  	  	}else if(!regUserName.test(userName)){
		  	  		 $("#checkUserName").text("× 请输入真实姓名！");
	  	  		 	 $("#userName").focus(); 
	  	  		 	 qiyong();
	  	  		 	 return;
		  	  	}else{
		  	  		$("#checkUserName").text("   √"); 
		  	  	}
		    
		      var nowDate = $("#nowDate").val();
		   	  if(employed_date == "" || employed_date == null){
		   		  $("#checkEmployed_date").text("× 不可为空！");
		   		  //$("#employed_date").focus(); 
		   		  qiyong();
		   		return;
		   	  }else if(employed_date > nowDate){
		   		  $("#checkEmployed_date").text("× 超过当前时间！");
		   		  //$("#employed_date").focus(); 
		   		  qiyong();
		   		return;
		   	  }else{
		   		  $("#checkEmployed_date").text("   √"); 
		   	  }
		   	  
		   	if(email == "" || email == null){
				$("#checkEmail").text("× 不可为空！");
				$("#email").focus(); 
				qiyong();
				return;
			}else if(!reg.test(email)){
				$("#checkEmail").text("× 格式不正确!"); 
				//$("#email").val("");
				$("#email").focus(); 
				qiyong();
				return;
			}else{
				$("#checkEmail").text(" √"); 
			}
			
			if(roleArea2 == "" || roleArea2 == null){
	   		  $("#checkRole").text("× 请选择角色！");
	   		 // $("#userName").focus(); 
	   		 	qiyong();
	   			return;
	   	  	}else{
	   		  $("#rr").text("   √"); 
	   		$("#checkRole").text("");
	   	  	}
		   	  	
	    	parent.$("#covered3").hide()
	      	parent.$("#covered4").hide()
	      	//搜索条件
	    	var lname = $("#lname").val();
	    	var uname = $("#uname").val();
	    	var dtid = $("#dtid").val();
	    	var companyid = $("#companyid").val();
	      	
	      	$("#param1").val(lname);
	      	$("#param2").val(uname);
	      	$("#param3").val(dtid);
	      	$("#param4").val(companyid);
	      	
			$("#saveForm").submit();
		 }
		   
		   function jinyong(){
			   $("#but").val("正在保存...");
			   $("#but").attr("disabled", "disabled");
		   }
		   
		   function qiyong(){
			   $("#but").val("保存");
			   $("#but").attr("disabled", false);
		   }
		   
		   
		 //---------------------------------编辑用户   验证------------------------------------------------------------//

			
		    function updateUser(){
		    	var loginName = $("#editloginName").val();
		    	var userName = $("#edituserName").val();
		    	
		    	var roleArea = $("#roleArea").val();
		    	var employed_date = $("#editemployed_date").val();
		    	var email = $("#editemail").val();
		    	var reg2 = /^[a-zA-Z0-9]{6,16}$/;
		    	var reg = /^[a-z0-9]([a-z0-9]*[-_\.]?[a-z0-9]+)*@([a-z0-9]*[-_]?[a-z0-9]+)+[\.][a-z]{2,3}([\.][a-z]{2})?$/;
				if(loginName == "" || loginName == null){
					$("#checkEditloginName").text("× 不可为空！");
					$("#editloginName").focus(); 
					return false;
				}else if(!reg2.test(loginName)){
					$("#checkEditloginName").text("× 格式不正确!"); 
					$("#editloginName").val(""); 
		    		$("#editloginName").focus(); 
		    		return false;
				}else{
					$("#checkEditloginName").text(" √"); 
				}
				
				var regUserName = /^[\u4e00-\u9fa5a-zA-Z]{2,20}$/;
				 if(userName == "" || userName == null){
					 	$("#checkEdituserName").text("× 不可为空！");
					    $("#edituserName").focus(); 
			  			return false;
			  	  	}else if(validateXml(userName)==1){
			  	  		 $("#checkEdituserName").text("× 有非法字符！");
			  	  		 $("#edituserName").focus(); 
			  	  		 return false;
			  	  	}else if(!regUserName.test(userName)){
			  	  		$("#checkEdituserName").text("× 请输入真实姓名!");
				    	$("#edituserName").focus(); 
		  				return false;
			  	  	}else{
			  	  	 	$("#checkEdituserName").text("   √"); 
			  	  	}
				 	
				 var nowDate = $("#nowDate").val();
				  //alert(nowStr);
				  if(employed_date == "" || employed_date == null){
					  $("#checkEditemployed_date").text("× 不可为空！");
					  $("#editemployed_date").focus(); 
					  return false;
				  }else if(employed_date > nowDate){
					  $("#checkEditemployed_date").text("× 不能超过当前时间！");
					  $("#editemployed_date").focus(); 
					  return false;
				  }else{
					  $("#checkEditemployed_date").text("   √"); 
				  }
				  
				  if(email == "" || email == null){
						$("#checkEditemail").text("× 不可为空！");
						$("#editemail").focus(); 
						return false;
					}else if(!reg.test(email)){
						$("#checkEditemail").text("× 格式不正确!"); 
						$("#editemail").val(""); 
			  			$("#editemail").focus(); 
			  			return false;
					}else{
						$("#checkEditemail").text(" √"); 
					}
					
					if(roleArea == "" || roleArea == null){
			   		  $("#checkEditRole").text("× 请选择角色！");
			   		 // $("#userName").focus(); 
			   			return false;
			   	  	}else{
			   		  $("#oo").text("   √"); 
			   		  $("#checkEditRole").text("");
			   	 	}
				  	parent.$("#covered3").hide()
			      	parent.$("#covered4").hide()
			      	
			      	//搜索条件
		    	var lname = $("#lname").val();
		    	var uname = $("#uname").val();
		    	var dtid = $("#dtid").val();
		    	var companyid = $("#companyid").val();
		      	
		      	$("#param12").val(lname);
		      	$("#param21").val(uname);
		      	$("#param31").val(dtid);
		      	$("#param41").val(companyid);
		      	
				$("#editForm").submit();
				  	
		       }

</script>
 
  <script type="text/javascript">
  function closeDIV(){
      $(".popup-box1").hide()
      $(".popup-box2").hide()
      $(".popup-box5").hide()
      $(".popup-box3").hide()
      $(".popup-box10").hide()
      $("#covered").hide()
      parent.$("#covered3").hide()
      parent.$("#covered4").hide()
      $("#editloginName").val("");
      $("#edituserName").val("");
      $("#editdepartment_id").val("");
      $("#editcompany_id").val("");
      $("#editteam_id").val("");
      $("#editposition_id").val("");
      $("#editjobId").val("");
      $("#roleArea").val("");
      $("#editemployed_date").val("");
      $("#editemail").val("");
      
      $("#checkEditloginName").text("");
      $("#checkEdituserName").text("");
      $("#checkEditRole").text("");
      $("#oo").text("");
      $("#checkEditemployed_date").text("");
      $("#checkEditemail").text("");
      
      $("#loginName").val("");
      $("#userName").val("");
      $("#department_id").val("");
      $("#company_id").val("");
      $("#team_id").val("");
      $("#position_id").val("");
      $("#jobId").val("");
      $("#roleArea2").val("");
      $("#employed_date").val("");
      $("#email").val("");
      
      $("#checkLoginName").text("");
      $("#checkUserName").text("");
      $("#checkRole").text("");
      $("#rr").text("");
      $("#checkEmployed_date").text("");
      $("#checkEmail").text("");
    }
  </script>
</html>