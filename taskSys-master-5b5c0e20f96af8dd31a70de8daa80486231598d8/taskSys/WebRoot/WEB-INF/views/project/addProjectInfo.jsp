<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/common/taglibs.jsp" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.0 Transitional//EN"> 
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<%@ include file="/common/meta.jsp" %>
<title>新增项目</title>
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
	<h1><b>位置：</b><a href="###">新增项目</a></h1>
    <!--添加数据开始-->
    <div class="TAB_right_content">
    <div class="infor_base paddingbom10">
        
        <div class="ADD_tree">
        	<table class="Table_forms" cellpadding="0" cellspacing="0">
                <tr>
                    <td ><label><span>*</span>项目名称：</label></td><td align="left"><input type="text" id="proName" name="proName" value=""></td>
                    <td><label><span>*</span>项目编码：</label></td><td align="left"><input type="text" id="code" name="code" value=""></td>
                </tr>
                <tr>
                    <td><label><span></span>项目类型：</label></td>
                    <td><input type="text" id="proType" name="proType">
                    	<%-- <select name="proType" id="proType"  style="width: 182px;">
                    		<option value="">======请 选 择======</option>
                    		<c:forEach  items="${dictionaryLlist}" var="list">
                    			<c:if test="${list.type_code eq '13'}">
                    				<option value="${list.code}">${list.name}</option>
                    			</c:if>
                    		</c:forEach>
                    	</select> --%>
                    </td>  
                    <td><label><span></span>项目类别：</label></td>
                    <td><input type="text" id="category" name="category">
                    	<%-- <select name="category" id="category"  style="width: 182px;">
                    		<option value="">======请 选 择======</option>
                    		<c:forEach  items="${dictionaryLlist}" var="list">
                    			<c:if test="${list.type_code eq '14'}">
                    				<option value="${list.code}">${list.name}</option>
                    			</c:if>
                    		</c:forEach>
                    	</select> --%>
                    </td>  
                </tr>
                <tr>
                   <td><label><span></span>实施部门：</label></td>
                   <td><input type="text" id="department" name="department">
                    	<%-- <select name="department" id="department"  style="width: 182px;" onchange="changeValue(this.value)">
                    		<option value="">======请 选 择======</option>
                    		<c:forEach  items="${dictionaryLlist}" var="list">
                    			<c:if test="${list.type_code eq '01'}">
                    				<option value="${list.code}">${list.name}</option>
                    			</c:if>
                    		</c:forEach>
                    	</select> --%>
                    </td> 
                    <td><label><span></span>项目经理：</label></td>
                    <td>
                    	<input type="text" name="xmManager" id="xmManager">
                    	<!-- <select name="xmManager" id="xmManager"  style="width: 182px;">
                    		<option value="">======请 选 择======</option>
                    	</select> -->
                    </td>  
                </tr>
                <tr>
                    <td><label><span></span>PMO负责人：</label></td>
                    <td><input type="text" name="pmo" id="pmo">
                    	<%-- <select name="pmo" id="pmo"  style="width: 182px;">
                    		<option value="">======请 选 择======</option>
                    		<c:forEach  items="${pmolist}" var="list">
                    			<option value="${list.loginName}">${list.userName}</option>
                    		</c:forEach>
                    	</select> --%>
                    </td> 
                    <td><label><span></span>当前阶段：</label></td>
                    <td><input type="text" name="stage" id="stage">
                    	<%-- <select name="stage" id="stage"  style="width: 182px;" >
                    		<option value="">======请 选 择======</option>
                    		<c:forEach  items="${dictionaryLlist}" var="list">
                    			<c:if test="${list.type_code eq '12'}">
                    				<option value="${list.code}">${list.name}</option>
                    			</c:if>
                    		</c:forEach>
                    	</select> --%>
                    </td>  
                </tr>
                <tr>
                    <td><label><span></span>项目立项时间：</label></td>
                    <td>
                    	<input style=""  type="text" id="approvalTime" name="approvalTime" onclick="WdatePicker()" onfocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd'})" class="Wdate" "> 
                    </td> 
                    <td><label><span></span>计划上线时间：</label></td>
	                <td>
                    	<input style=""  type="text" id="expectOnlineTime" name="expectOnlineTime" onclick="WdatePicker()" onfocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd'})" class="Wdate" "> 
                    </td>  
                </tr>
                <tr>
                    <td><label><span></span>时间进度情况：</label></td><td><input type="text" name="schedule" id="schedule" value=""></td> 
                    <td><label><span></span>项目研发类型：</label></td>
	                <td><input type="text" name="developType" id="developType">
                    	<%-- <select name="developType" id="developType"  style="width: 182px;" >
                    		<option value="">======请 选 择======</option>
                    		<c:forEach  items="${dictionaryLlist}" var="list">
                    			<c:if test="${list.type_code eq '15'}">
                    				<option value="${list.code}">${list.name}</option>
                    			</c:if>
                    		</c:forEach>
                    	</select> --%>
                    </td>  
                </tr>
                <tr>
                    <td><label><span></span>需求控制：</label></td><td><input type="text" name="requirementControl" id="requirementControl" value=""></td> 
                    <td><label><span></span>风险情况：</label></td><td><input type="text" name="riskCondition" id="riskCondition" value=""></td>  
                </tr>
                <tr>
                    <td><label><span></span>备注：</label></td>
	                <td><input type="text" name="remark" id="remark" value=""></td> 
                    <td><label><span></span>是否可分配：</label></td>
	                <td>
	                	<select name="isDistribution" id="isDistribution"  style="width: 182px;" >
                    		<!-- <option value="">请 选 择</option> -->
                    		<option value="1">是</option>
                    		<option value="0">否</option>
                    	</select>
	                </td> 
                </tr>
                <tr align="center">
                	<td colspan="4">
                		<input class="form_now" type="button" value="保存" onclick="javascript:saveFormInfo();">&nbsp;&nbsp;&nbsp;&nbsp;
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
		var id = $("#id").val();
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

       	var proType = $("#proType").val();
       	var category = $("#category").val();
       	var department = $("#department").val();
       	var xmManager = $("#xmManager").val();
       	var pmo = $("#pmo").val();
       	var stage = $("#stage").val();
       	var approvalTime = $("#approvalTime").val();
       	var expectOnlineTime = $("#expectOnlineTime").val();
       	var schedule = $("#schedule").val();		//时间进度情况
       	var developType = $("#developType").val();
       	var requirementControl = $("#requirementControl").val();
       	var riskCondition = $("#riskCondition").val();
       	var remark = $("#remark").val();
       	var isDistribution = $("#isDistribution").val();
       	$.ajax({ 
			type : "POST",  
			url: "${ctx}/project/saveProjectInfo.do", 
			data : {"proName":proName,"code":code,"proType":proType,"category":category,
					"department":department,"xmManager":xmManager,"pmo":pmo,"stage":stage,"approvalTime":approvalTime,
					"expectOnlineTime":expectOnlineTime,"schedule":schedule,"developType":developType,
					"requirementControl":requirementControl,"riskCondition":riskCondition,"remark":remark,"isDistribution":isDistribution
					
			},
			dataType : 'json',
			success: function(data){
				if(data.res=='ok'){
					alert("保存成功！");
					window.parent.location="${ctx}/project/getProjectInfoList.do";
				}else if(data.res=='double'){
					alert("项目编号重复，保存失败！");
				}else{
					alert("保存失败！");
				}
			}
		});
		/* $("#saveForm").attr("action","${ctx}/project/saveProjectInfo.do?flag=modify&id="+id);
		$("#saveForm").attr("method","POST");
		$("#saveForm").submit(); */
		//window.parent.$.colorbox.close();
		
	}
	/* function saveFormInfo(){
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
	} */
	
	function changeValue(value){
		$("#xmManager option:not(:first)").remove();
		url = "${ctx}/project/xmManager.do";
		$.ajax({ 
			type : "POST",  
			url: url, 
			data : "department="+value,
			dataType : 'json',
			success: function(data){
				for(var i=0;i<data.list.length;i++){
					$('#xmManager').append('<option value="'+data.list[i].login_name+'">'+data.list[i].user_name+'</option>');
				}
			}
      		
      	});
	}
</script>
</body>
</html>