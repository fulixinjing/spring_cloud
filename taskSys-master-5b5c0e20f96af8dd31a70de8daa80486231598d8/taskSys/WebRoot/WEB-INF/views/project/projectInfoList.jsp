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
<link rel="stylesheet" type="text/css" href="${ctx}/css/pop.css" />
<script type="text/javascript" src="${ctx}/js/jquery.min.js"></script>
<script type="text/javascript" src="${ctx}/js/alert/g_alert.js?1=1"></script>

<link href="${ctx}/css/defaultTheme1.css" rel="stylesheet" media="screen" />
<link href="${ctx}/css/myTheme1.css" rel="stylesheet" media="screen" />
<script type="text/javascript" src="${ctx}/js/My97DatePicker/WdatePicker.js" ></script>
<script src="${ctx}/js/jquery.fixedheadertable.js"></script>
<script src="${ctx}/js/demo.js"></script>
<script type="text/javascript" src="${ctx}/js/right_js.js"></script>
<script type="text/javascript" src="${ctx}/js/util/common.js"></script>
<script type="text/javascript" src="${ctx}/js/layer/skin/layer.css"></script> 
<script type="text/javascript" src="${ctx}/js/layer/skin/layer.ext.css"></script>
<script type="text/javascript" src="${ctx}/js/layer/layer.min.js"></script>
<script type="text/javascript" src="${ctx}/js/layer/extend/layer.ext.js"></script>
<!-- jquery-ui start -->
<link rel="stylesheet" href="${ctx}/js/jquery_ui/jquery-ui.css">
<script src="${ctx}/js/jquery_ui/jquery-1.11.3.js"></script>
<script src="${ctx}/js/jquery_ui/jquery-ui.js"></script>
<script type="text/javascript">
    var availableTags = new Array();
    var availableCodes = new Array();
	$(function() {
	    $( "#proName" ).autocomplete({
	      source: availableTags
	    });
	});
	function getProjectName(value){
		if(value!=null && value!=''){
			$.ajax({
				type : "POST", 
				url : '${ctx}/project/searchUtils.do',
				data : {"param":value},
				dataType : "json", 
				success : function(data){
					if(data.list!=null && data.list.length>0){
						availableTags = new Array();
						availableCodes = new Array();
						for ( var i = 0; i < data.list.length; i++) {
							availableTags[i]=data.list[i].proname;
							availableCodes[i]=data.list[i].code;
						}
						$( "#proName" ).autocomplete({
					    	source: availableTags
					    });
					}
				}
	    	}); 
		}
	}
	
	function changeF(value) {
		//alert(value);
		for ( var i = 0; i < availableTags.length; i++) {
 			if(value==availableTags[i]){
				$("#code").val(availableCodes[i]);
				break;
			}else{
				$("#code").val('');
			}
		}
	}
</script>
<!-- jquery-ui end -->
</head>
<style>
	html,body{
		height:100%;
		overflow:hidden;
	}
	#bodyTR  td{
		text-align:center;
	}
</style>
<body>
<c:if test="${resultFlag=='1' }">
	<tags:message content="${message}" type="success" />
</c:if>
<c:if test="${resultFlag=='0' }">
	<tags:message content="${message}" type="error" />
</c:if>
<form id ="f1" action="${ctx}/project/getProjectInfoList.do" method="POST">
<input type="hidden" id="page" name="page" value="1" />
<input type="hidden"  id="maxResult" name="maxResult" value="${pageView.maxresult}"/>
<input type="hidden" id="orgId" name="orgId"  value="${perforInfoReport.orgId}">
<input type="hidden" id="selectFlag" name="selectFlag" >
<div class="TAB_right">
<h1><b>位置：</b><a href="###">首页</a><span>&gt;</span><a href="###">项目管理</a></h1>
	<div class="retrieve_list">
			<table class="retrieve_table" width="100%" cellpadding="0" cellspacing="0">
					<tr>
						<td style="width:220px">&nbsp;&nbsp; <%-- <input style="width:90px" type="text" id="proName" name="proName" value= "${projectInfo.proName}"> --%>
							项目名称：<input class="inputC" id="proName" name="proName" value= "${projectInfo.proName}" placeholder="请输入项目名称关键字" style="border: 1px solid #94afc8;width:130px;height: 22px;line-height: 22px;color: #343333;text-indent: 8px;vertical-align: middle;border-radius: 3px;"
							 onkeydown="getProjectName(this.value)" onkeyup="getProjectName(this.value)" onblur="changeF(this.value);">
						</td>
						<td style="width:220px">&nbsp;&nbsp; 项目编号：<input style="width:130px" type="text" id="code" name="code" value= "${projectInfo.code}"></td>
						<td align="left" style="width:200px">PMO负责人：
							<input style="width:90px" type="text" id="pmo" name="pmo" value= "${projectInfo.pmo}">
							<%-- <select name="pmo" id="pmo" class="sel" style="width:100px">
                                <option value="">请选择</option>
			                   	<c:forEach items="${pmolist}" var="pmo" varStatus="status">
				                 	<option value="${pmo.userId}" <c:if test="${pmo.userId==projectInfo.pmo}">selected</c:if>>${pmo.userName }</option>
			                    </c:forEach>
                         	  </select> --%>
						</td>
						<td align="right">&nbsp;&nbsp;
							<input class="form_now marginr10" type="button" value="查询" onclick="selectSubmit()">&nbsp;&nbsp;&nbsp;&nbsp;
							<input class="form_now marginr10" type="button" value="重置" onclick="resetForm()">&nbsp;&nbsp;&nbsp;&nbsp;
							<shiro:hasPermission name="user:importProject">
								<input class="form_now marginr10" type="button" value="导入数据" onclick="importExcel()">&nbsp;&nbsp;&nbsp;&nbsp;
							</shiro:hasPermission>
							<shiro:hasPermission name="user:newProject">
								<input class="form_now marginr10" type="button" value="新增" onclick="newProject()">&nbsp;&nbsp;&nbsp;&nbsp;
							</shiro:hasPermission>
							<shiro:hasPermission name="user:exportProject">
								<input class="form_now marginr10" type="button" value="导出Excel" onclick="exportProject()">&nbsp;&nbsp;&nbsp;&nbsp;
							</shiro:hasPermission>
						</td>
					</tr>
					
			</table>
	</div>
	<div class="infor_base">
	<div  class="btn_operate">
		</div>
		   <div class="container_12" style="overflow-y:auto;">
        	<div class="grid_8" >
        		<table class="fancyTable" id="myTable02" width="100%" cellpadding="0" cellspacing="0" style="table-layout:fixed;">
        		    <thead>
        		        <tr>
        		        	<th style="width:35px;">序号</th>
        		        	<th style="width:120px;"><a href="#" style="color:black">项目编号</a></th>
        		        	<th style="width:150px;"><a href="#" style="color:black">项目名称</a></th>
        		            <th style="width:60px;"><a href="#"  style="color:black">项目类型</a></th>
        		            <th style="width:70px;"><a href="#"  style="color:black">项目类别</a></th>
        		            <th style="width:120px;"><a href="#"  style="color:black">实施部门</a></th>
        		            <th style="width:60px;"><a href="#"  style="color:black">项目经理</a></th>
        		            <th style="width:80px;" ><a href="#" style="color:black">PMO负责人</a></th>
        		            <th style="width:70px;" ><a href="#" style="color:black">时间进度情况</a></th>
        		            <th style="width:120px;" ><a href="#" style="color:black">导入时间</a></th>
        		            <th style="width:60px;" ><a href="#" style="color:black">是否可分配</a></th>
        		            <shiro:hasPermission name="user:caozuoProject">
        		            <th style="width:80px;" ><a href="#" style="color:black">操作</a></th>
        		            </shiro:hasPermission>
        		        </tr>
        		    </thead>
        		    <tbody id="bodyTR">
        		    <c:forEach items="${pageView.records}" var="projectInfo" varStatus="status">
        		        <tr>
        		        	<td>${status.index + 1}</td>
                            <td  style="word-wrap:break-word;text-align: center;" ><a href="#" onclick="findProjectInfoDetails('${projectInfo.id }');">${projectInfo.code }</a></td>
                            <td  style="word-wrap:break-word;text-align: center;" >${projectInfo.proName }</td>
                            <td  style="word-wrap:break-word;text-align: center;" >${projectInfo.proType }</td>
                            <td  style="word-wrap:break-word;text-align: center;" >${projectInfo.category }</td>
                            <td  style="word-wrap:break-word;text-align: center;" >${projectInfo.department }</td>
                            <td  style="word-wrap:break-word;text-align: center;" >${projectInfo.xmManager }</td>
                            <td  style="word-wrap:break-word;text-align: center;" >${projectInfo.pmo }</td>
                            <td  style="word-wrap:break-word;text-align: center;" ><font <c:if test="${fn:contains(projectInfo.schedule,'延期') || fn:contains(projectInfo.schedule,'暂停') }">color="red"</c:if>>${projectInfo.schedule }</font></td>
                            <td  style="word-wrap:break-word;text-align: center;" >${projectInfo.addTime }</td>
                            <td  style="word-wrap:break-word;text-align: center;" >
                            <c:if test="${projectInfo.isDistribution=='1' }">是</c:if>
                            <c:if test="${projectInfo.isDistribution=='0' }">否</c:if>
                            </td>
                            <shiro:hasPermission name="user:caozuoProject">
                            <td  style="word-wrap:break-word;text-align: center;" >
                            	<shiro:hasPermission name="user:modifyProject">
                            		<a href="#" style="color: #1478BB"  class="cblue4 popup-btn3"  onclick="modifyProject('${projectInfo.id}')">修改</a>&nbsp;&nbsp;
								</shiro:hasPermission>
								<shiro:hasPermission name="user:delProject">
									<a href="#" style="color: #1478BB"  class="cblue4 popup-btn3"  onclick="delProject('${projectInfo.id}')">删除</a>
								</shiro:hasPermission>
	                        </td>
	                        </shiro:hasPermission>
        		        </tr>
        		        </c:forEach>
        		    </tbody>
        		</table>
        	</div>
        	<div class="clear"></div>
        </div>	
     	<div class="pages">
        	<%@ include file="/common/pagination.jsp" %>
        </div>
	</div>
</div>
</form>
</body>

	<!-- 详情弹出框start -->
<link rel="stylesheet" type="text/css" href="${ctx}/components/colorbox/colorbox.css"/>
<script language="javascript" src="${ctx}/components/colorbox/jquery.colorbox.js"></script>
<script>
	
	function findProjectInfoDetails(id){
		var url = "${ctx}/project/findProjectInfoDetails.do?id="+id;
		$.colorbox({
			href:url,
			iframe:true,
			width:"700",
			height:"460"
		});
	}
	
</script>
<!-- 详情弹出框end -->
<script type="text/javascript">
	$(document).ready(function(){
		$(".fht-tbody").css("height", "85%");
		$(".grid_8").css("height","75%");
	})
	function selectSubmit(){
							
		$("#f1").attr("method","POST");
		$("#f1").attr("action","${ctx}/project/getProjectInfoList.do");
		$("#f1").submit();
	}	
	
	//到指定的分页页面
	function topage(page){
		$("#page").val(page);
		$("#listForm").attr("method","POST");
		$("#listForm").attr("action","${ctx}/project/getProjectInfoList.do");
		$("#listForm").submit();
	}	
	//到指定的分页页面
	function topagePagination(page,maxResult){
		$("#page").val(page);
		$("#maxResult").val(maxResult);
		$("#f1").attr("method","POST");
		$("#f1").attr("action","${ctx}/project/getProjectInfoList.do");
		$("#f1").submit();
	}	
	
	function resetForm(){
		$("#proName").val("");
		$("#code").val("");
		$("#pmo").val("");
	}
	function importExcel(){
		var url= "${ctx}/project/upfile.do";
	    $.colorbox({
	        href:url,
	        iframe:true,
	        width:"600",
	        height:"500"
	    }); 
	}
	
	function newProject(){
		var url= "${ctx}/project/addProject.do";
	    $.colorbox({
	        href:url,
	        iframe:true,
	        width:"800",
	        height:"500"
	    }); 
	}
	
	function modifyProject(id){
		var url= "${ctx}/project/modifyProject.do?id="+id;
	    $.colorbox({
	        href:url,
	        iframe:true,
	        width:"800",
	        height:"500"
	    }); 
	}
	
	function delProject(id){
		var isDelete = confirm("确定要删除选中数据吗？");
		if(isDelete){
			$("#f1").attr("method","POST");
			$("#f1").attr("action","${ctx}/project/delProject.do?id="+id);
	    	$("#f1").submit();
		}
	}
	
	function exportProject(){
		$("#f1").attr("method","POST");
		$("#f1").attr("action","${ctx}/project/exportProject.do");
    	$("#f1").submit();
	}
</script>
</html>