<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/common/taglibs.jsp" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.0 Transitional//EN"> 
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<%@ include file="/common/meta.jsp" %>
<title>项目详情</title>
<link rel="stylesheet" type="text/css" href="${ctx}/css/common.css" />
<link rel="stylesheet" type="text/css" href="${ctx}/css/right.css" />
<script type="text/javascript" src="${ctx}/js/jquery.min.js"></script>
<script type="text/javascript" src="${ctx}/js/right_js.js"></script>

<script type="text/javascript" src="${ctx}/js/jquery.metadata.js"></script>
<script type="text/javascript" src="${ctx}/js/jquery.validate.js"></script>

<link rel="stylesheet" type="text/css" href="${ctx}/components/colorbox/colorbox.css"/>
<script language="javascript" src="${ctx}/components/colorbox/jquery.colorbox.js"></script>
<script type="text/javascript" src="${ctx}/js/My97DatePicker/WdatePicker.js" ></script>
<script type="text/javascript" src="${ctx}/js/alert/g_alert.js?1=1"></script>
</head>

<body>
<form id="saveForm" name="saveForm" action="${ctx}/project/saveProjectInfo.do" method="post">
<div class="TAB_right">
	<h1><b>位置：</b><a href="###">项目详情</a></h1>
    <div class="TAB_right_content">
    <div class="infor_base paddingbom10">
        
        <div class="ADD_tree">
        	<table class="Table_forms" cellpadding="0" cellspacing="0">
                <tr>
                    <td width="130px"><label>项目名称：</label></td><td align="left" width="180px">${projectInfo.proName }</td>
                    <td width="150px"><label>项目编码：</label></td><td align="left">${projectInfo.code }</td>
                </tr>
                <tr>
                    <td><label><span></span>项目类型：</label></td><td>${projectInfo.proType }</td>  
                    <td><label><span></span>项目类别：</label></td><td>${projectInfo.category }</td>
                </tr>
                <tr>
                   <td><label><span></span>实施部门：</label></td></td><td>${projectInfo.department }</td>
                   <td><label><span></span>项目经理：</label></td><td>${projectInfo.xmManager }</td>
                </tr>
                <tr>
                    <td><label><span></span>PMO负责人：</label></td><td>${projectInfo.pmo }</td>
                    <td><label><span></span>当前阶段：</label></td><td>${projectInfo.stage }</td>
                </tr>
                <tr>
                    <td><label><span></span>项目立项时间：</label></td><td>${projectInfo.approvalTime }</td>
                    <td><label><span></span>计划上线时间：</label></td><td>${projectInfo.expectOnlineTime }</td>
                </tr>
                <tr>
                    <td><label><span></span>时间进度情况：</label></td><td>${projectInfo.schedule }</td> 
                    <td><label><span></span>项目研发类型：</label></td><td>${projectInfo.developType }</td> 
                </tr>
                <tr>
                    <td><label><span></span>需求控制：</label></td><td>${projectInfo.requirementControl }</td>
                    <td><label><span></span>风险情况：</label></td><td>${projectInfo.riskCondition }</td>  
                </tr>
                <tr>
                    <td><label><span></span>备注：</label></td><td>${projectInfo.remark }</td>  
                    <td><label><span></span>导入时间：</label></td><td>${projectInfo.addTime }</td>  
                </tr>
                <tr>
                    <td><label><span></span>是否可分配：</label></td>
                    <td>
                    	<c:if test="${projectInfo.isDistribution=='1' }">是</c:if>
                    	<c:if test="${projectInfo.isDistribution=='0' }">否</c:if>
                    </td>  
                    <td colspan="2"></td>  
                </tr>
                <tr align="center">
                	<td colspan="4">
                		<input class="form_now" type="button" onclick="javascript:window.parent.$.colorbox.close();" value="返回">&nbsp;&nbsp;&nbsp;&nbsp;
                	</td>
                </tr>
            </table>
             </div>
        </div>
    </div>
</div>
</form>
<script type="text/javascript">
	function saveFormInfo(){
		var proName = $("#proName").val();
		var code = $("#code").val();
		if(proName==null || proName==''){
			alert("项目名称不能为空！"); 
			return;
		}
		if(code==null || code==''){
			alert("项目编号不能为空！"); 
			return;
		}
		$("#saveForm").attr("action","${ctx}/project/saveProjectInfo.do");
		$("#saveForm").attr("method","POST"); 
		$("#saveForm").submit();
		window.parent.$.colorbox.close();
	}
</script>
</body>
</html>