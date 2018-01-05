<%@ page language="java"  pageEncoding="UTF-8"%>
<%@ page import="cn.taskSys.entity.User" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml"> 
<head>
<%@include file="/common/taglibs.jsp"%> 
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<script type="text/javascript" src="${ctx}/js/jquery.min.js"></script>
<link rel='icon' href="${ctx}/images/ico.png" type=‘image/x-ico’ />
<title>任务管理系统</title>
<%-- <link href="${ctx}/css/static/main.css?1=1" rel="stylesheet" type="text/css" />
<link href="${ctx}/css/static/base-global.css" rel="stylesheet" type="text/css" />
<link href="${ctx}/css/static/common.css?5=1" rel="stylesheet" type="text/css" />
<link href="${ctx}/css/static/stylesheet.css" rel="stylesheet" type="text/css" /> --%>
<link rel="stylesheet" type="text/css" href="${ctx}/css/right.css" />
<link rel="stylesheet" type="text/css" href="${ctx}/css/index01.css" />

<script type="text/javascript" src="${ctx}/js/jquery.min.js"></script>

<script type="text/javascript" src="${ctx}/js/jquery.validate.js"></script>
<script type="text/javascript" src="${ctx}/js/jquery.metadata.js"></script>
<script type="text/javascript" src="${ctx}/js/alert/g_alert.js?1=1"></script>
<script type="text/javascript" src="${ctx}/js/util/common.js?1=1"></script>

<script type="text/javascript" src="${ctx}/js/My97DatePicker/WdatePicker.js" ></script>

<link rel="stylesheet" href="${ctx}/js/jquery_ui/jquery-ui.css">
<script src="${ctx}/js/jquery_ui/jquery-ui.js"></script>
<style>
	.table00 tbody tr{height: 120px;}
	.table00 tbody td input{width: 200px;height: 30px;}
</style>
<script type="text/javascript">
    var availableTags = new Array();
    var availableCodes = new Array();
    var availableManagers = new Array();
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
                	if(data!=null){
	                    if(data.list!=null && data.list.length>0){
	                        availableTags = new Array();
	                        availableCodes = new Array();
	                        for ( var i = 0; i < data.list.length; i++) {
	                            availableTags[i]=data.list[i].proname;
	                            availableCodes[i]=data.list[i].code;
	                            availableManagers[i]=data.list[i].isdistribution;
	                        }
	                        $( "#proName" ).autocomplete({
	                            source: availableTags
	                        });
	                    }
                	}
                }
            }); 
        }
    }
    
    function changeF(value) {
        for ( var i = 0; i < availableTags.length; i++) {
            if(value==availableTags[i]){
                if(availableManagers[i]==1){
                    $("#projectCode").val(availableCodes[i]);
                    break;
                }
//                 else{
//                     $("#projectCode").val(availableCodes[i]);
//                     //alert("该项目不允许分配任务！");
//                     $( "#proName" ).val("");
//                     $("#projectCode").val("");
//                 }
            }else{
                $("#projectCode").val('');
            }
        }
    }
    
    /* $(document).ready(function(){
        $("#saveForm").validate({
            rules:{  
                projectName:{
                    required: true
                },
                importance:{
                    required:true
                },
                content1:{
                    required:true,
                    maxlength: 300
                },
                teamMember1:{
                    required:true
                },
                content2:{
                    required:true,
                    maxlength: 300
                },
                teamMember2:{
                    required:true
                },
                content3:{
                    required:true,
                    maxlength: 300
                },
                teamMember3:{
                    required:true
                },
                content4:{
                    required:true,
                    maxlength: 300
                },
                content5:{
                    maxlength: 300
                },
                teamMember4:{
                    required:true
                },
                departmentPlan:{
                    required:true,
                    maxlength: 300
                },
               type:{
                	required:true
                },
                month:{
                	required:true
                }
            },  
            messages:{
                projectName:{
                    required: "<span style='color:red'>项目名称不能为空！</span>"
                },
                importance:{
                    required: "<span style='color:red'>重要程度不能为空！</span>"
                },
                content1:{
                    required: "<span style='color:red'>第一周工作内容不能为空！</span>",
                    maxlength: jQuery.format("<span style='color:red'>第一周工作内容不能大于{0}个字符！</span>")
                },
                teamMember1:{
                    required: "<span style='color:red'>第一周团队成员不能为空！</span>"
                },
                content2:{
                    required: "<span style='color:red'>第二周工作内容不能为空！</span>",
                    maxlength: jQuery.format("<span style='color:red'>第二周工作内容不能大于{0}个字符！</span>")
                },
                teamMember2:{
                    required: "<span style='color:red'>第二周团队成员不能为空！</span>"
                },
                content3:{
                    required: "<span style='color:red'>第三周工作内容不能为空！</span>",
                    maxlength: jQuery.format("<span style='color:red'>第三周工作内容不能大于{0}个字符！</span>")
                },
                teamMember3:{
                    required: "<span style='color:red'>第三周团队成员不能为空！</span>"
                },
                content4:{
                    required: "<span style='color:red'>第四周工作内容不能为空！</span>",
                    maxlength: jQuery.format("<span style='color:red'>第四周工作内容不能大于{0}个字符！</span>")
                },
                content5:{
                    maxlength: jQuery.format("<span style='color:red'>第五周工作内容不能大于{0}个字符！</span>")
                },
                teamMember4:{
                    required: "<span style='color:red'>第四周团队成员不能为空！</span>"
                },
                departmentPlan:{
                    required: "<span style='color:red'>本月部门工作计划/目标不能为空！</span>",
                    maxlength: jQuery.format("<span style='color:red'>本月部门工作计划/目标内容不能大于{0}个字符！</span>")
                },
                type:{
                	required: "<span style='color:red'>请选择类型！</span>"
                },
                month:{
                	required: "<span style='color:red'>请选择月份！</span>"
                }
            }
        });
    }); */
</script>
<!-- 项目名称弹出框start -->
<link rel="stylesheet" type="text/css" href="${ctx}/components/colorbox/colorbox.css"/>
<script language="javascript" src="${ctx}/components/colorbox/jquery.colorbox.js"></script>
<script>
	// 角色弹出选择
	function selectSingleProject(){
	    var url= "${ctx}/work_plan/projectList.do";
	    $.colorbox({
	        href:url,
	        iframe:true,
	        width:"500",
	        height:"350"
	    });
	}
	function selectSingleProject_callback(result){
	
		for (var i in result) {
			var tmp = result[i].split(",");
			//$("").val(tmp[0]);
			$("#proName").val(tmp[1]);
			$("#projectCode").val(tmp[2]);
		}
	}
</script>
<!-- 项目名称弹出框end -->
</head>
<body>
<form action="${ctx}/work_plan/updateWorkPlan.do" method="POST" id="saveForm" name="saveForm">
<input type="hidden" id="id" name="id" value="${workPlan.id}" />
<input type="hidden" readonly="readonly" id="projectCode" name="projectCode" style="border:0px #dcdcdc solid;color:blue;">

<div class="all">
    <!-- <h1><b>位置：</b><a href="###">首页</a><span>&gt;</span><a href="###">新增工作计划</a></h1> -->
    <div class="r_top">
		<p class="r_tit">首页>>修改工作计划</p>
	</div>
    <div class="r_box" style="height: 450px;overflow: auto;">
		<div class="b_top">
			<div class="fl mr10">
				<label class="label"><span style="color: red;">*</span>项目名称：</label>
				<input class="input" id="proName" name="projectName" value="${workPlan.projectName}" onclick="javascript:selectSingleProject();" readonly="readonly" style="border: 1px solid #94afc8;width:150px;height: 22px;line-height: 22px;color: #343333;text-indent: 8px;vertical-align: middle;border-radius: 3px;">
				<%-- <input class="input" id="proName" name="projectName" value="${workPlan.projectName}" placeholder="请输入项目关键字" style="border: 1px solid #94afc8;width:150px;height: 22px;line-height: 22px;color: #343333;text-indent: 8px;vertical-align: middle;border-radius: 3px;"
                             onkeydown="getProjectName(this.value)" onkeyup="getProjectName(this.value)" onblur="changeF(this.value);"> --%>
			</div>
			<div class="fl mr10">
				<label class="label"><span style="color: red;">*</span>重要程度：</label>
                <select name="importance" id="importance" class="select" style="width: 120px;">
                    <option value="" selected>---请选择---</option>
                        <c:forEach items="${dictionaryLlist}" var="dic" varStatus="status">
                        	<c:if test="${dic.type_code=='16'}">
                           		<option value="${dic.code}" <c:if test="${dic.code eq workPlan.importance}" >selected</c:if>>${dic.name }</option>
                           </c:if>
                        </c:forEach>
                </select>
			</div>
			<div class="fl mr10">
				<label class="label">外部确认人：</label><input class="input" type="text"  value="${workPlan.verifier }" name="verifier" id="verifier"  style="width: 120px;">
			</div>
			<div class="fl mr10">
				<label class="label"><span style="color: red;">*</span>类型：</label>
				<select name="type" id="type" class="select" style="width:150px">
                   	<option value="">请选择</option>
                   	<c:forEach items="${dictionaryLlist}" var="dic" varStatus="status">
                    	<c:if test="${dic.type_code=='17'}">
	              			<option value="${dic.code}" <c:if test="${dic.code==workPlan.type}">selected</c:if>>${dic.name }</option>
	             		</c:if>
            		</c:forEach>
                </select>
			</div>
			<div class="fl mr10">
				<label class="label"><span style="color: red;">*</span>月份：</label>
				<input class="input" style="width: 90px;" readonly="readonly"  type="text" id="month" name="month" value= "${workPlan.month}"  onclick="WdatePicker({dateFmt:'yyyy-MM',isShowClear:true});"/>
			</div>
		</div>
		<div class="list">
			<table width="100%" cellpadding="0" cellspacing="0" border="0" class="table00">
				<thead>
					<tr>
						<th></th>
						<th style="width: 240px">工作标题</th>
						<th style="width: 280px">工作内容</th>
						<th style="width: 240px">团队成员</th>
						<th style="width: 240px">完成情况</th>
					</tr>
				</thead>
				<tbody>
					<tr>
						<td style="color: #1585b1;font-weight: bold;">第一周</td>
						<td><input type="text" id="title1" name="title1"  value="${workPlan.title1}"  placeholder="请输入本周工作概述"/></td>
						<td><textarea id="content1" name="content1" rows="5" style="width:278px;height:122px;resize: none;" placeholder="请输入本周具体工作">${workPlan.content1 }</textarea></td>
						<td><input type="text" value="${workPlan.teamMember1}" name="teamMember1" id="teamMember1"></td>
						<td><%-- <input type="text" value="${workPlan.completeStatus1}" name="completeStatus1" id="completeStatus1"> --%>
							<textarea name="completeStatus1" id="completeStatus1" style="width:238px;height:122px;resize: none;border: 1px solid #e6e6e6;" placeholder="请输入本周工作完成度或结果">${workPlan.completeStatus1}</textarea>
						</td>
					</tr>
					<tr>
						<td style="color: #1585b1;font-weight: bold;">第二周</td>
						<td><input type="text" id="title2" name="title2" value="${workPlan.title2}"  placeholder="请输入本周工作概述"/></td>
						<td><textarea id="content2" name="content2" rows="5" style="width:278px;height:122px;resize: none;" placeholder="请输入本周具体工作">${workPlan.content2 }</textarea></td>
						<td><input type="text" value="${workPlan.teamMember2 }" name="teamMember2" id="teamMember2"></td>
						<td><%-- <input type="text" value="${workPlan.completeStatus2 }" name="completeStatus2" id="completeStatus2"> --%>
							<textarea name="completeStatus2" id="completeStatus2" style="width:238px;height:122px;resize: none;border: 1px solid #e6e6e6;" placeholder="请输入本周工作完成度或结果">${workPlan.completeStatus2}</textarea>
						</td>
					</tr>
					<tr>
						<td style="color: #1585b1;font-weight: bold;">第三周</td>
						<td><input type="text" id="title3" name="title3" value="${workPlan.title3}"  placeholder="请输入本周工作概述"/></td>
						<td><textarea id="content3" name="content3" rows="5" cols="" style="width:278px;height:122px;resize: none;" placeholder="请输入本周具体工作">${workPlan.content3 }</textarea></td>
						<td><input type="text" value="${workPlan.teamMember3 }" name="teamMember3" id="teamMember3"></td>
                    	<td><%-- <input type="text" value="${workPlan.completeStatus3 }" name="completeStatus3" id="completeStatus3"> --%>
							<textarea name="completeStatus3" id="completeStatus3" style="width:238px;height:122px;resize: none;border: 1px solid #e6e6e6;" placeholder="请输入本周工作完成度或结果">${workPlan.completeStatus3}</textarea>
						</td>
					</tr>
					<tr>
						<td style="color: #1585b1;font-weight: bold;">第四周</td>
						<td><input type="text" id="title4" name="title4" value="${workPlan.title4}"  placeholder="请输入本周工作概述"/></td>
						<td><textarea id="content4" name="content4" rows="5" cols="" style="width:278px;height:122px;resize: none;" placeholder="请输入本周具体工作">${workPlan.content4}</textarea></td>
	                    <td><input type="text" value="${workPlan.teamMember4 }" name="teamMember4" id="teamMember4"></td>
	                    <td><%-- <input type="text" value="${workPlan.completeStatus4 }" name="completeStatus4" id="completeStatus4"> --%>
							<textarea name="completeStatus4" id="completeStatus4" style="width:238px;height:122px;resize: none;border: 1px solid #e6e6e6;" placeholder="请输入本周工作完成度或结果">${workPlan.completeStatus4}</textarea>
						</td>
					</tr>
					<tr>
						<td style="color: #1585b1;font-weight: bold;">第五周</td>
						<td><input type="text" id="title5" name="title5" value="${workPlan.title5}"  placeholder="请输入本周工作概述"/></td>
						<td><textarea id="content5" name="content5" rows="5" cols="" style="width:278px;height:122px;resize: none;" placeholder="请输入本周具体工作">${workPlan.content5 }</textarea></td>
	                    <td><input type="text" value="${workPlan.teamMember5 }" name="teamMember5" id="teamMember5"></td>
	                    <td><%-- <input type="text" value="${workPlan.completeStatus5 }" name="completeStatus5" id="completeStatus5"> --%>
							<textarea name="completeStatus5" id="completeStatus5" style="width:238px;height:122px;resize: none;border: 1px solid #e6e6e6;" placeholder="请输入本周工作完成度或结果">${workPlan.completeStatus5}</textarea>
						</td>
					</tr>
				</tbody>
			</table>
		</div>
		<div class="wen">
			<div class="w_yi">
				<label class="label01 fl">工作计划／目标：（本月部门）</label>
				<textarea id="departmentPlan" name="departmentPlan"  class="textarea" placeholder="请输入本月工作计划">${workPlan.departmentPlan }</textarea>
			</div>
			<div class="w_yi">
				<label class="label01 fl">遇到风险：</label>
				<textarea id="risk" name="risk"  class="textarea">${workPlan.risk }</textarea>
			</div>
			<div class="w_yi">
				<label class="label01 fl">采取措施：</label>
				<textarea id="measures" name="measures"  class="textarea">${workPlan.measures }</textarea>
			</div>
		</div>
	</div>
	<div class="div_btn" style="margin-bottom:20px;text-align: center;border-top: 1px solid #ccd4e1;padding-top: 15px">
		<input type="button" value="保存" onclick="javascript:updateWorkPlan();"/>
		<input type="button" value="返回"  onclick="javascript:history.back()"/>
	</div>
</div>
</form>
</body>
<script type="text/javascript">
//获取textarea的宽度
	$(function(){
		var w = $('.wen').width();
		var ww = parseInt(w);
		var tar = ww*1-132;
		var tarw = parseInt(tar);
		$('.textarea').css({'width':tarw+'px'});
	})
	
	function updateWorkPlan(){
    	var proName = $("#proName").val();
    	var importance = $("#importance").val();
    	var type = $("#type").val();
    	var month = $("#month").val();
    	
    	if(proName == "" || proName == null){
           // $("#checkProName").text("× 不可为空！");
            alert("项目名称不能为空");
            $("#proName").focus(); 
            return;
        }
    	if(importance == "" || importance == null){
    		alert("请选择重要程度");
    		$("#importance").focus();
    		return;
    	}
    	if(type == "" || type == null){
            alert("请选择项目类型");
            $("#type").focus();
            return;
        }
    	if(month == "" || month == null){
            alert("请选择月份");
            $("#month").focus();
            return;
        }
    	$("#saveForm").submit();
    }
</script>
</html>