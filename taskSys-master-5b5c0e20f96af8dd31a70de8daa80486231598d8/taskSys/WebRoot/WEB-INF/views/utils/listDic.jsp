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
<c:if test="${resultFlag==1 }">
	<tags:message content="${message}" type="success" />
</c:if>
<c:if test="${resultFlag==0 }">
	<tags:message content="${message}" type="error" />
</c:if>
<form id ="f1" action="${ctx}/utils/listDictionary.do" method="POST">
<input type="hidden" id="page" name="page" value="1" />
<input type="hidden"  id="maxResult" name="maxResult" value="${pageView.maxresult}"/>
<input type="hidden" id="orgId" name="orgId"  value="${perforInfoReport.orgId}">
<input type="hidden" id="selectFlag" name="selectFlag" >
<div class="TAB_right">
<h1><b>位置：</b><a href="###">首页</a><span>&gt;</span><a href="###">字典表管理</a></h1>
	<div class="retrieve_list">
			<table class="retrieve_table" width="100%" cellpadding="0" cellspacing="0">
					<tr>
						<td> 类型：
							<select name="type_code" id="type_code" class="sel" style="width:100px">
                                <option value="">请选择</option>
			                    <option value="00" <c:if test="${dictionary.type_code=='00'}">selected</c:if>>公司</option>
			                    <option value="01" <c:if test="${dictionary.type_code=='01'}">selected</c:if>>部门</option>
			                    <option value="02" <c:if test="${dictionary.type_code=='02'}">selected</c:if>>团队</option>
                         	  </select>
                         	 &nbsp;&nbsp;&nbsp;&nbsp;
                         	  名称：<input type="text" name="name" id="name" style="width: 100px" value="${dictionary.name}" >
						</td>
						<td align="right">&nbsp;&nbsp;
							<input class="form_now marginr10" type="button" value="查询" onclick="selectSubmit()">&nbsp;&nbsp;&nbsp;&nbsp;
							<input class="form_now marginr10" type="button" value="新增" onclick="add()">&nbsp;&nbsp;&nbsp;&nbsp;
							<input class="form_now marginr10" type="button" value="删除" onclick="del()">&nbsp;&nbsp;&nbsp;&nbsp;
							<%-- <input class="form_now marginr10" type="button" value="重置" onclick="resetForm('${userRole}','${positionId }')">&nbsp;&nbsp;&nbsp;&nbsp; --%>
							<%-- <input class="form_now marginr10" type="button" value="导出" onclick="exportUserScore()">&nbsp;&nbsp;&nbsp;&nbsp;
							--%>
						</td>
					</tr>	
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
        		        	<th style="width:35px;">&nbsp;</th>
        		            <th style="width:120px;"><a href="#"  style="color:black">名称</a></th>
        		            <th style="width:120px;"><a href="#"  style="color:black">编码</a></th>
        		        	<th style="width:100px;"><a href="#" style="color:black">类型名称</a></th>
        		        	<th style="width:80px;"><a href="#" style="color:black">类型编码</a></th>
        		        	<th style="width:80px;"><a href="#" style="color:black">父级</a></th>
        		        	<th style="width:80px;"><a href="#" style="color:black">操作</a></th>
        		        </tr>
        		    </thead>
        		    <tbody id="bodyTR">
        		    <c:forEach items="${dictionaryList}" var="dictionary" varStatus="status">
        		        <tr>
        		        	<td><input type="checkbox" name="dictionaryId" id="dictionaryId" value="${dictionary.id }"></td>
                            <td  style="word-wrap:break-word;text-align: center;" >${dictionary.name }</td>
                            <td  style="word-wrap:break-word;text-align: center;" >${dictionary.code }</td>
                            <td  style="word-wrap:break-word;text-align: center;" >${dictionary.type_name }</td>
                            <td  style="word-wrap:break-word;text-align: center;" >${dictionary.type_code }</td>
                            <td  style="word-wrap:break-word;text-align: center;" >${dictionary.parent_name }</td>
                            <td  style="word-wrap:break-word;text-align: center;" >
                            		<a href="#" style="color: #1478BB"  class="cblue4 popup-btn3"  onclick="modifyDic('${dictionary.id }','${dictionary.type_code }')">修改</a>&nbsp;&nbsp;
                            </td>
        		        </tr>
        		        </c:forEach>
        		    </tbody>
        		</table>
        		</form>
        	</div>
        	<div class="clear"></div>
        </div>
	</div>

</div>
</form>
</body>

<script type="text/javascript">
	$(document).ready(function(){
		$(".fht-tbody").css("height", "85%");
		$(".grid_8").css("height","75%");
	})
	function selectSubmit(){
							
		$("#f1").attr("method","POST");
		$("#f1").attr("action","${ctx}/utils/listDictionary.do");
		$("#f1").submit();
	}	
	function add(){
							
		$("#f1").attr("method","POST");
		$("#f1").attr("action","${ctx}/utils/addDictionary.do");
		$("#f1").submit();
	}	
	//删除任务
	function del(){
		var ids = '';
		var checkboxs = $(":checkbox");
		for ( var i = 0; i < checkboxs.length; i++) {
			if(checkboxs[i].checked){
				ids = ids + checkboxs[i].value + ",";
			}
		}
		if(ids!=null && ids!=''){
			ids = ids.substring(0, ids.length-1);
		}else{
			alert("请选择要删除的行！");
			return;
		}
		var isDelete = confirm("确定要删除选中数据吗？");
		if(isDelete){
			$("#f1").attr("method","POST");
			$("#f1").attr("action","${ctx}/utils/delDictionary.do?ids="+ids);
	    	$("#f1").submit();
		}
	}
	function modifyDic(id,type_code){
							
		$("#f1").attr("method","POST");
		$("#f1").attr("action","${ctx}/utils/modifyDictionary.do?id="+id+"&type_code="+type_code);
		$("#f1").submit();
	}
	/*input 属性readonly="readonly"时，禁用backspace事件*/
    function disableBackspace(){
    	var isReadOnly = $("#exec_name").prop("readonly");
	    if (isReadOnly && event.keyCode == 8) {
			event.preventDefault();
		}
    }
</script>
</html>