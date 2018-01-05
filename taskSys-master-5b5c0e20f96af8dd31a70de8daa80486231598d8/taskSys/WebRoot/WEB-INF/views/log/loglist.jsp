<%@ page language="java"  pageEncoding="UTF-8"%>
<%@include file="/common/taglibs.jsp"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.0 Transitional//EN"> 
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>无标题文档</title>
<link rel="stylesheet" type="text/css" href="${ctx}/css/common.css" />
<link rel="stylesheet" type="text/css" href="${ctx}/css/right1.css" />
<link rel="stylesheet" type="text/css" href="${ctx}/css/easyui.css">
<link rel="stylesheet" type="text/css" href="${ctx}/css/pop.css" />
<link href="${ctx}/css/960.css" rel="stylesheet" media="screen" />
<link href="${ctx}/css/defaultTheme1.css" rel="stylesheet" media="screen" />
<link href="${ctx}/css/myTheme1.css" rel="stylesheet" media="screen" />
<script type="text/javascript" src="${ctx}/js/jquery.min.js" ></script>
<script type="text/javascript" src="${ctx}/js/jquery.fixedheadertable.js"></script>
<script type="text/javascript" src="${ctx}/js/demo.js"></script>
<script type="text/javascript" src="${ctx}/js/right_js.js"></script>
<script type="text/javascript" src="${ctx}/js/jquery.easyui.min.js"></script>
<link rel="stylesheet" type="text/css" href="${ctx}/css/colorbox.css" />
<script type="text/javascript" src="${ctx}/js/jquery.colorbox.js"></script>
<script type="text/javascript" src="${ctx}/js/My97DatePicker/WdatePicker.js" ></script>
</head>

<body >
<form id="creditorInfo" action="${ctx}/log/getLoglistp.do" method="post">
<tags:message content="${message}" type="success" />
<div class="TAB_right" >
	<h1><b>位置：</b><a href="###">首页</a><span>&gt;<a href="###">日志管理</a><span>&gt;</span></span><a href="###">日志查询</a></h1>
	<div class="retrieve_list">
		<table class="retrieve_table" width="100%" cellpadding="0" cellspacing="0">
		    	<tr>
		        	<td><label>操作用户姓名：</label><input type="text" name="logusername" value="${log.logusername}"></td>
		        	<td><label>开始操作时间：</label><input  type="text" name="logdate" onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:true});" value="<fmt:formatDate value="${log.logdate}" pattern="yyyy-MM-dd"/>"  ></td>
		        	<td><label>结束操作时间：</label><input  type="text" name="lastlogdate" onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:true});" value="<fmt:formatDate value="${log.lastlogdate}" pattern="yyyy-MM-dd"/>"  ></td>
		        </tr>
		        <tr>
		        	<td><label>操作用户登录名：</label><input type="text" name="loguserlogin" value="${log.loguserlogin}"></td>
		        	<td ><label>日志类型：</label>
		        		<select id="logtype" name="logtype" >  
								<option value="" >--请选择--</option>
				                <option value="Info" <c:if test="${log.logtype=='Info'}"> selected</c:if>>  
				                   		 信息日志
				                </option>  
				                <option value="Debug" <c:if test="${log.logtype=='Debug'}"> selected</c:if>>  
				                    	调试日志
				                </option>  
				                <option value="Error" <c:if test="${log.logtype=='Error'}"> selected</c:if>>  
				                    	错误日志
				                </option>  
					        </select>
					</td>
					<td><label>操作用户IP：</label><input type="text" name="logip" value="${log.logip}"></td>
		        </tr>
		        <tr align="right">
		        	<td colspan="3"><input id="retrieve_btn" class="form_now marginr10" type="submit" value="查询"></td>
		        </tr>
		    </table>
	</div>
	<div class="infor_base" style="height: 339px;">
		 <div class="btn_operate">
          </div>
		   <div class="container_12">
        	<div class="grid_8">
        		<table class="fancyTable" id="myTable02" cellpadding="0" cellspacing="0">
        		    <thead>
        		        <tr>
        		            <th width="10%">日志类型</th>
        		            <th width="10%">操作用户姓名</th>
        		            <th  width="12%">操作时间</th>
        		            <th width="40%">操作内容</th>
        		            <th width="18%">操作用户登录名</th>
        		            <th width="10%">操作用户IP</th>
        		        </tr>
        		    </thead>
        		    <tbody>
        		    		<c:forEach items="${pclist.datas}" var="item">
			               	<tr >
			                   <td >
			                   <c:if test="${item.logtype=='Info'}">
			                   				信息日志
			                   </c:if>
			                   <c:if test="${item.logtype=='Debug'}">
			                   				调试日志
			                   </c:if>
			                   <c:if test="${item.logtype=='Error'}">
			                   				错误日志
			                   </c:if>
			                   </td>
			                   <td >${item.logusername}</td>
			                   <td ><fmt:formatDate value="${item.logdate}" type="both"/></td>
			                   <td >${item.loginfo}</td>
			                   <td >${item.loguserlogin}</td>
			                   <td >${item.logip}</td>
			                </tr>
			             </c:forEach> 
        		    </tbody>
        		</table>
        	</div>
        	<div class="clear"></div>
        </div>	
	</div>
	<div class="pages">
	    	共<fmt:parseNumber value="${(pclist.total%ps==0 ? pclist.total/ps : (pclist.total/ps)+1)}" integerOnly="true" />页&nbsp;|&nbsp;共${pclist.total}条记录&nbsp;
	       <pg:pager url="${ctx}/log/getLoglistg.do" items="${pclist.total}"  maxPageItems="${ps}"  export="page,currentPageNumber=pageNumber" scope="request">
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
</form>
</body>
</html>