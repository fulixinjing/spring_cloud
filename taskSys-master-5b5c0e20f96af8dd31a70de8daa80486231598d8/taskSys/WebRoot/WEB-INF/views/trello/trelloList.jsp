<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">    
<%@include file="/common/taglibs.jsp"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.0 Transitional//EN"> 
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
    <title>任务管理系统</title>
    <link rel="stylesheet" type="text/css" href="${ctx}/css/common.css" />
    <link rel="stylesheet" href="${ctx}/css/ztree/zTreeStyle.css" type="text/css">
    <link href="${ctx}/css/static/base-global.css" rel="stylesheet" type="text/css" />
    <link href="${ctx}/css/static/common.css" rel="stylesheet" type="text/css" />
    <link href="${ctx}/css/static/main.css" rel="stylesheet" type="text/css" />
    
    <link rel="stylesheet" type="text/css" href="${ctx}/css/pop.css" />
    <link rel="stylesheet" type="text/css" href="${ctx}/css/right1.css" />
    <script type="text/javascript" src="${ctx}/js/jquery.min.js"></script>
    <script type="text/javascript" src="${ctx}/js/right_js.js"></script>
    <script type="text/javascript" src="${ctx}/js/ztree/jquery-1.4.4.min.js"></script>
    <script type="text/javascript" src="${ctx}/js/ztree/jquery.ztree.core-3.5.js"></script>
    <link rel="stylesheet" type="text/css" href="${ctx}/components/colorbox/colorbox.css"/>
    <script language="javascript" src="${ctx}/components/colorbox/jquery.colorbox.js"></script>
    <script type="text/javascript" src="${ctx}/js/layer/skin/layer.css"></script> 
<script type="text/javascript" src="${ctx}/js/layer/skin/layer.ext.css"></script>
<link rel="stylesheet" href="${ctx}/js/jquery_ui/jquery-ui.css">
<style type="text/css">
.deptlist{
width: 300px;
padding: 20px;
padding-bottom: 30px;
border: 5px #acacac solid;
background: #fff;
border-radius: 9px;
display:none;
position:fixed;
height: 260px;
overflow: auto;
}
.deptdiv{position:relative;}
.sss{position:absolute;}
</style>
<script src="${ctx}/js/jquery_ui/jquery-ui.js"></script>
   <script type="text/javascript">
   var setting = {
           data: {
               simpleData: {
                   enable: true
               }
           }
       };

       var zNodes =${pm};

       $(document).ready(function(){
           $.fn.zTree.init($("#menutree"), setting, zNodes);
           
           $('.tc-close').click(function(){
               $('.popup-box1').hide()
               $('#covered').hide()
             })
          $('.tc-btn2').click(function(){
	          $('.popup-box1').hide()
	          $('#covered').hide()
	        })
       });
       function info(taskname,taskcontent,create_name,exec_name,createtime,jxzDate,expectendtime,taskworktime){
    	   alert(1);
    	   $("#taskname").html(taskname);
    	   $("#taskcontent").val(taskcontent);
    	   $("#create_name").val(create_name);
           $("#exec_name").val(exec_name);
           $("#createtime").val(createtime);
           $("#jxzDate").val(jxzDate);
    	   $("#expectendtime").val(expectendtime);
    	   $("#taskworktime").val(taskworktime);
    	   $('#covered').show();
    	   $('.popup-box1').show();
    	} 
       
    	function taskselect(){
    		$('.popup-box1').hide();
    		$('#covered').hide();
    		var exec_name = $("#exec_name").val();
            var url = "${ctx}/admin/listTask.do?exec_name="+encodeURI(exec_name);
            $.colorbox({
                href:url,
                iframe:true,
                width:"1100",
                height:"500"
            });
    	}
       $(document).ready(function() {
    	   //$('#deptlist').hide();
           //鼠标移入移出效果
           $(".sss").click(function(){
        	   $("#deptlist").show();
        	   $(".ddd").show();
           });
           
          
                
           $("#deptlist").mouseleave(function (){  
               $(".ddd").hide();
               $("#deptlist").hide();
               
           });  
       });
       
</script>
</head>

<body>
<div class="ddd" style="position: fixed;display:none;top: 0px;left: 0px;width: 100%;height: 100%;background-color: #000;opacity:0.3"></div>
<div class="deptlist" id="deptlist">
        <table class="" width="100%" cellpadding="0" cellspacing="0" border="0" >
            <tr>
                 <c:forEach items="${departmentList}" var="dept">
                   <tr>
                       <td><a href="${ctx }/trello/projectstage.do?code=${dept.code }">${dept.proName}</a></td>
                   </tr>
                   </c:forEach>
            </tr>
        </table>  
</div>

<div class="l-num1" id = "deptdiv">
	<input type="button" name="button" value="测试" class="sss"/>
</div>

<div class="content" id="mainarea" style="height:100%;overflow-y:hidden;">
<!-- <h1><b>位置：</b><a href="###">首页</a><span>&gt;</span><a href="###">OA数据导入</a></h1> -->
    <div class="margint10" id="inner_right">
        <table class="" width="100%" cellpadding="0" cellspacing="0" border="0" >
            <tr>
                <td valign="top" width="265" style="background-color: #E7EDF8;">
                    <div class="jur_tree" id="z_tree_zone">
                    <!--文档树部分开始-->
                        <ul id="menutree" class="ztree"></ul>
                    <!--文档数部分结束-->
                    </div>
                </td>
                <td valign="top"  class="">
                    <iframe id="ky" name="ky" src="${ctx}/trello/projectstage.do" width="100%" height="500px" frameborder="0"></iframe>
                </td>
            </tr>
        </table>
    </div>
    
</div>
<div id="covered"></div>
<div class="popup popup-box1" style="margin-left:-195px;z-index: 10000;"  >
       <div class="tc-box">
            <div class="title fn-clear">
                任务名称：<span id="taskname" id="taskname"></span>
                <a href="javascript:(void);" class="tc-close fr"></a>
            </div>
            <ul class="pd-t5">
                <li class="mg-t10"><textarea name="taskcontent" id="taskcontent"  class="wbqy" style="width:90%;margin-left:12px;resize: none;" ></textarea></li>
                <li class="fn-clear">
                    <label class="fl txt-r mg-r10">创建人：</label>
                    <input readonly="readonly" type="text" id="create_name" name="create_name"  class="Wdate">
                </li>
                <li class="fn-clear">
                    <label class="fl txt-r mg-r10">负责人：</label>
                    <input readonly="readonly" onclick="taskselect();" type="text" id="exec_name" name="exec_name"  class="Wdate">
                </li>
                <li class="fn-clear">
                    <label class="fl txt-r mg-r10">创建时间：</label>
                    <input readonly="readonly" type="text" id="createtime" name="createtime"  class="Wdate">
                </li>
                <li class="fn-clear">
                    <label class="fl txt-r mg-r10">接受时间：</label>
                    <input readonly="readonly" type="text" id="jxzDate" name="jxzDate"  class="Wdate">
                </li>
                <li class="fn-clear">
                    <label class="fl txt-r mg-r10">完成时间：</label>
                    <input readonly="readonly" type="text" id="expectendtime" name="expectendtime"  class="Wdate">
                </li>
                <li class="fn-clear">
                    <label class="fl txt-r mg-r10">工作量：</label>
                    <input type="text" readonly="readonly" id="taskworktime" name="taskworktime" >&nbsp;人时&nbsp;&nbsp;
                </li>
            </ul>
            <div class="txt-c pd-t15">
                <a href="javascript:(void);" class="tc-btn2 mg-l15">取&nbsp;&nbsp;消</a>
            </div>
        </div>
    </div> 
</body>
</html>