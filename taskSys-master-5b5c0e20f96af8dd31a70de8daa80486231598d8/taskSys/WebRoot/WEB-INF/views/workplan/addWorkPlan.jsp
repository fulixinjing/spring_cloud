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
<link href="${ctx}/css/static/main.css?1=1" rel="stylesheet" type="text/css" />
<link href="${ctx}/css/static/base-global.css" rel="stylesheet" type="text/css" />
<link href="${ctx}/css/static/common.css?5=1" rel="stylesheet" type="text/css" />
<link href="${ctx}/css/static/main.css?1=1" rel="stylesheet" type="text/css" />
<link href="${ctx}/css/static/stylesheet.css" rel="stylesheet" type="text/css" />
<link rel="stylesheet" type="text/css" href="${ctx}/css/right.css" />
<link rel="stylesheet" type="text/css" href="${ctx}/css/pop.css" />
<link rel="stylesheet" type="text/css" href="${ctx}/css/index01.css">


<script type="text/javascript" src="${ctx}/js/My97DatePicker/WdatePicker.js" ></script>
<script type="text/javascript" src="${ctx}/js/jquery.min.js"></script>

<script type="text/javascript" src="${ctx}/js/jquery.validate.js"></script>
<script type="text/javascript" src="${ctx}/js/jquery.metadata.js"></script>
<script type="text/javascript" src="${ctx}/js/alert/g_alert.js?1=1"></script>
<script type="text/javascript" src="${ctx}/js/util/common.js?1=1"></script>

<link rel="stylesheet" href="${ctx}/js/jquery_ui/jquery-ui.css">
<script src="${ctx}/js/jquery_ui/jquery-ui.js"></script>
<style>
	.table00 tbody tr{height: 125px;}
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

    function addWorkPlan(){
    	var proName = $("#proName").val();
    	var importance = $("#importance").val();
    	var type = $("#type").val();
    	var month = $("#month").val();
    	
    	if(proName == "" || proName == null){
           // $("#checkProName").text("× 不可为空！");
            alert("项目名称不能为空");
            $("#proName").focus(); 
            return false;
        }
    	if(importance == "" || importance == null){
    		alert("请选择重要程度");
    		$("#importance").focus();
    		return false;
    	}
    	if(type == "" || type == null){
            alert("请选择项目类型");
            $("#type").focus();
            return false;
        }
    	if(month == "" || month == null){
            alert("请选择月份");
            $("#month").focus();
            return false;
        }
    	$("#saveForm").submit();
    }
  	//获取textarea的宽度
	//获取textarea的宽度
    $(function(){
        var w = $('.wen').width();
        var ww = parseInt(w);
        var tar = ww*1-132;
        var tarw = parseInt(tar);
        $('.textarea').css({'width':tarw+'px'});
    })


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
<body style='overflow:auto;'>
<form action="${ctx}/work_plan/saveWorkPlan.do" method="POST" id="saveForm" name="saveForm">
<input type="hidden" readonly="readonly" id="projectCode" name="projectCode" style="border:0px #dcdcdc solid;color:blue;">
<div class="all">
    <div class="r_top">
        <p class="r_tit">首页>>新增工作计划</p>
    </div>
    <div class="r_box" style="height: 450px;overflow: auto;">
        <div class="b_top">
            <div class="fl mr10">
                <label class="label"><span style="color: red;">*&nbsp;</span>项目名称：</label>
                <input class="input" id="proName" name="projectName" value="${workPlan.projectName}" onclick="javascript:selectSingleProject();" readonly="readonly" style="border: 1px solid #94afc8;width:150px;height: 22px;line-height: 22px;color: #343333;text-indent: 8px;vertical-align: middle;border-radius: 3px;">
                <!-- <input class="input" id="proName" name="projectName" placeholder="请输入项目关键字" style="border: 1px solid #94afc8;width:120px;height: 22px;line-height: 22px;color: #343333;text-indent: 8px;vertical-align: middle;border-radius: 3px;"
                             onkeydown="getProjectName(this.value)" onkeyup="getProjectName(this.value)" onblur="changeF(this.value);"> -->
                <span id="checkProName" style="color: red;"></span>
            </div>
            <div class="fl mr10">
                <label class="label"><span style="color: red;width: 120px;">*&nbsp;</span>重要程度：</label>
                <select name="importance" id="importance" class="select" style="width: 120px;">
                     <option value="" selected>---请选择---</option> 
                         <c:forEach items="${dictionaryLlist}" var="dic" varStatus="status">
                            <c:if test="${dic.type_code=='16'}">
                             <option value="${dic.code}">${dic.name }</option>
                            </c:if>
                         </c:forEach>
                 </select>
            </div>
            <div class="fl mr10">
                <label class="label">外部确认人：</label><input class="input" type="text" value="" name="verifier" id="verifier" style="width: 120px;">
            </div>
            <div class="fl mr10">
                <label class="label"><span style="color: red;">*&nbsp;</span>类型：</label>
                <select name="type" id="type" class="select" style="width:150px">
                    <option value="">请选择</option>
                    <c:forEach items="${dictionaryLlist}" var="dic" varStatus="status">
                        <c:if test="${dic.type_code=='17'}">
                            <option value="${dic.code}">${dic.name }</option>
                        </c:if>
                    </c:forEach>
                </select>
            </div>
            <div class="fl mr10">
				<label class="label"><span style="color: red;">*&nbsp;</span>月份：</label>
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
                        <td>
                            <input class="input" id="title1" name="title1" style="border: 1px solid #e6e6e6;" placeholder="请输入本周工作概述"/>
                            <span id="checkTitle1" style="color: red;"></span>
                        </td>
                        <td>
                            <textarea id="content1" name="content1"  cols="" style="width:278px;height:122px;resize: none;border: 1px solid #e6e6e6;" placeholder="请输入本周具体工作"></textarea>
                            <span id="checkContent1" style="color: red;"></span>
                        </td>
                        <td>
                            <input class="input" type="text" value="" name="teamMember1" id="teamMember1" style="border: 1px solid #e6e6e6;">
                        </td>
                        <td>
                            <textarea name="completeStatus1" id="completeStatus1" style="width:238px;height:122px;resize: none;border: 1px solid #e6e6e6;" placeholder="请输入本周工作完成度或结果"></textarea>
                        </td>
                    </tr>
                    <tr>
                        <td style="color: #1585b1;font-weight: bold;">第二周</td>
                        <td>
                            <input id="title2" name="title2" style="border: 1px solid #e6e6e6;" placeholder="请输入本周工作概述"/>
                            <span id="checkTitle2" style="color: red;"></span>
                        </td>
                        <td>
                            <textarea id="content2" name="content2"  cols="" style="width:278px;height:122px;resize: none;border: 1px solid #e6e6e6;" placeholder="请输入本周具体工作"></textarea>
                            <span id="checkContent2" style="color: red;"></span>
                        </td>
                        <td>
                            <input type="text" value="" name="teamMember2" id="teamMember2" style="border: 1px solid #e6e6e6;">
                        </td>
                        <td>
                            <!-- <input type="text" value="" name="completeStatus2" id="completeStatus2" style="border: 1px solid #e6e6e6;"> -->
                            <textarea name="completeStatus2" id="completeStatus2" style="width:238px;height:122px;resize: none;border: 1px solid #e6e6e6;" placeholder="请输入本周工作完成度或结果"></textarea>
                        </td>
                    </tr>
                    <tr>
                        <td style="color: #1585b1;font-weight: bold;">第三周</td>
                        <td>
                        <input id="title3" name="title3" style="border: 1px solid #e6e6e6;" placeholder="请输入本周工作概述"/>
                            <span id="checkTitle3" style="color: red;"></span>
                        </td>
                        <td>
                            <textarea id="content3" name="content3"  cols="" style="width:278px;height:122px;resize: none;border: 1px solid #e6e6e6;" placeholder="请输入本周具体工作"></textarea>
                            <span id="checkContent3" style="color: red;"></span>
                        </td>
                        <td>
                            <input type="text" value="" name="teamMember3" id="teamMember3" style="border: 1px solid #e6e6e6;">
                        </td>
                        <td>
                            <!-- <input type="text" value="" name="completeStatus3" id="completeStatus3" style="border: 1px solid #e6e6e6;"> -->
                            <textarea name="completeStatus3" id="completeStatus3" style="width:238px;height:122px;resize: none;border: 1px solid #e6e6e6;" placeholder="请输入本周工作完成度或结果"></textarea>
                        </td>
                    </tr>
                    <tr>
                        <td style="color: #1585b1;font-weight: bold;">第四周</td>
                        <td>
                            <input id="title4" name="title4" style="border: 1px solid #e6e6e6;" placeholder="请输入本周工作概述"/>
                            <span id="checkTitle4" style="color: red;"></span>
                        </td>
                        <td>
                            <textarea id="content4" name="content4"  cols="" style="width:278px;height:122px;resize: none;border: 1px solid #e6e6e6;" placeholder="请输入本周具体工作"></textarea>
                            <span id="checkContent4" style="color: red;"></span>
                        </td>
                        <td>
                            <input type="text" value="" name="teamMember4" id="teamMember4" style="border: 1px solid #e6e6e6;">
                        </td>
                        <td>
                            <!-- <input type="text" value="" name="completeStatus4" id="completeStatus4" style="border: 1px solid #e6e6e6;"> -->
                            <textarea name="completeStatus4" id="completeStatus4" style="width:238px;height:122px;resize: none;border: 1px solid #e6e6e6;" placeholder="请输入本周工作完成度或结果"></textarea>
                        </td>
                    </tr>
                    <tr>
                        <td style="color: #1585b1;font-weight: bold;">第五周</td>
                        <td><input id="title5" name="title5" style="border: 1px solid #e6e6e6;" placeholder="请输入本周工作概述"/></td>
                        <td>
                            <textarea id="content5" name="content5"  cols="" style="width:278px;height:122px;resize: none;border: 1px solid #e6e6e6;" placeholder="请输入本周具体工作"></textarea>
                        </td>
                        <td>
                            <input type="text" value="" name="teamMember5" id="teamMember5" style="border: 1px solid #e6e6e6;">
                        </td>
                        <td>
                            <!-- <input type="text" value="" name="completeStatus5" id="completeStatus5" style="border: 1px solid #e6e6e6;"> -->
                            <textarea name="completeStatus5" id="completeStatus5" style="width:238px;height:122px;resize: none;border: 1px solid #e6e6e6;" placeholder="请输入本周工作完成度或结果"></textarea>
                        </td>
                    </tr>
                </tbody>
            </table>
        </div>
        <div class="wen">
            <div class="w_yi">
                <label class="label01 fl">工作计划／目标：（本月部门）</label>
                <textarea class="textarea" id="departmentPlan" name="departmentPlan" style="resize: none;" placeholder="请输入本月工作计划"></textarea>
            </div>
            <div class="w_yi">
                <label class="label01 fl">遇到风险：</label>
                <textarea class="textarea" id="risk" name="risk" style="resize: none;"></textarea>
            </div>
            <div class="w_yi">
                <label class="label01 fl">采取措施：</label>
                <textarea class="textarea" id="measures" name="measures" style="resize: none;"></textarea>
            </div>
        </div>
    </div>
    <div class="div_btn" style="margin-bottom:20px;text-align: center;border-top: 1px solid #ccd4e1;padding-top: 15px">
        <input type="button" value="保存" onclick="addWorkPlan();"/>
        <input type="button" value="返回" onclick="javascript:history.back()"/>
    </div>
</div>

</form>
</body>
</html>