<%@ page language="java" pageEncoding="UTF-8"%>
<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.0 Transitional//EN">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<link href="${ctx}/css/static/base-global.css" rel="stylesheet" type="text/css">
<link href="${ctx}/css/static/common.css" rel="stylesheet" type="text/css">
<link href="${ctx}/css/static/main.css" rel="stylesheet" type="text/css">
<link href="${ctx}/css/ystep.css?1=1" rel="stylesheet" type="text/css">
<link rel="stylesheet" type="text/css" href="${ctx}/css/common.css">
<link rel="stylesheet" type="text/css" href="${ctx}/css/right.css">
<link rel="stylesheet" type="text/css" href="${ctx}/css/pop.css">

<script type="text/javascript" src="${ctx}/js/static/jquery/jquery-1.7.2.min.js"></script>
<script type="text/javascript" src="${ctx}/js/My97DatePicker/WdatePicker.js" ></script>
<script type="text/javascript" src="${ctx}/js/alert/g_alert.js?1=1"></script>
<script src="${ctx}/js/artDialog4.1.7/artDialog.source.js?skin=aero"></script>
<script src="${ctx}/js/artDialog4.1.7/iframeTools.source.js"></script>
</head>

<body>
 	<div class="content" id="mainarea">
		<div class="title"><h2>个人中心</h2></div>
		<div class="form-box fn-clear mg-t10">
			<div class="fl wd918">
				<div class="pd-b30 mg-l15" style="border-bottom:1px #e5e5e5 solid;">
					<h2 class="fz16">日程安排&nbsp;&nbsp;&nbsp;
					</h2>
					<div class="fn-clear search-box mg-t15 fz14">
	                     <span class="fl mg-l10">
	                        	 日程月份：<input readonly="readonly" type="text" id="imTime" name="imTime" value="${personSys.month }" onclick="WdatePicker({onpicking:function(dp){ timeSelect(dp.cal.getNewDateStr()) },skin:'whyGreen',dateFmt:'yyyy-MM',maxDate:'%y-%M'})" onfocus="WdatePicker({onpicking:function(dp){ timeSelect(dp.cal.getNewDateStr()) },skin:'whyGreen',dateFmt:'yyyy-MM',maxDate:'%y-%M'})" class="Wdate" style="width:120px;border:0px;">
	                     </span>
	                     <a class="fl mg-l20 lh30 fz14 blue" onclick="findAttenceDetails()">查看详情</a>
	                </div>
					
	                <div class="fn-clear pd-t15 pd-b15 mg-t15">
	                	<div class="fl mg-l15">
	                		<span class="grzx-bg1" style="cursor:pointer" onclick ="onTask('1')">${personSys.normalFinish }<font class="fz12 mg-l2">次</font></span>
	                		<p class="fz14 mg-t5 txt-c">已完成</p>
	                	</div>
	                	<div class="fl mg-l20" >
	                		<span class="grzx-bg2" style="cursor:pointer" onclick ="onTask('2')">${personSys.haveInHand }<font class="fz12 mg-l2">次</font></span>
	                		<p class="fz14 mg-t5 txt-c">进行中</p>
	                	</div>
	                	<div class="fl mg-l20">
	                		<span class="grzx-bg4" style="cursor:pointer" onclick ="onTask('3')">${personSys.beforeStart }<font class="fz12 mg-l2">次</font></span>
	                		<p class="fz14 mg-t5 txt-c">未开始</p>
	                	</div>
	                </div>
	                
				</div>
				<div class="pd-b30 mg-l15 pd-t30">
					<h2 class="fz16">个人设置</h2>
					<div class="fn-clear search-box mg-t15 fz14">
	                     <a class="fl lh30 fz14 blue tc-btn" >提醒规则设置</a>
	                     <a class="fl mg-l30 lh30 fz14 blue xgmm-btn">修改密码</a>
	                     <a class="fl mg-l30 lh30 fz14 blue xgtx-btn">上传头像</a>
	                </div>
	            </div>
			</div>
		</div>
		<input type="hidden" name="acgz" id="acgz" value="${acgz }">
	</div>
    </body>
    <!-- ==============================弹窗================================== -->
    <div class="popup popup-box1" style="margin-left:-195px;">
    	<div class="tc-box">
    		<div class="title fn-clear">
                    <span>设置提醒规则</span>
                    <a href="javascript:(void);" class="tc-close fr"></a>
                </div>
                <ul class="pd-t25" style="padding-left:35%;" >
	                <li class="fn-clear">
	                	<input name="selTime"  id="sel1" type="radio" value="1" >&nbsp;&nbsp;08:30---17:30
	    			</li>
	    			 <li class="fn-clear">
	                	<input name="selTime"  id="sel2" type="radio" value="2" >&nbsp;&nbsp;09:00---18:00
	    			</li>
	    			 <li class="fn-clear">
	                	<input name="selTime"  id="sel3" type="radio" value="3" >&nbsp;&nbsp;09:30---18:30
	    			</li>
    			</ul>
    			<ul class="title fn-clear1"></ul>
    		<div class="txt-c pd-t15">
    			<a href="javascript:submitTask();" class="tc-btn1">选&nbsp;&nbsp;择</a>
    			<a href="javascript:(void);" class="tc-btn2 mg-l15">取&nbsp;&nbsp;消</a>
    		</div>
    	</div>
    </div>
    
    
</html>
<script type="text/javascript">

	function timeSelect(value){
		if(value !=null  && value !=''){
			var win = art.dialog.open.origin;//来源页面
			win.location ="${ctx}/user/personSys?month="+value;
		}
	}
	
	
	function findAttenceDetails(){
		var month = $("#imTime").val();
		 art.dialog.open("${ctx}/schedule/now?month="+month, {
	         id: 'flag',
	         title: '日程安排',
	         lock:true,
	         width:800,
	     	 height:400
	     },  
	     false);
	}
			
		 $(document).ready(function(){
		 	
		 		
			$('.tc-btn').click(function(){		
				$('.popup-box1').show();
			});
			
		
			
			$('.xgmm-btn').click(function(){
            	  parent.$("#oldPassword").val("");
                  parent.$('#covered').show()
                  parent.$('.xgmm-box').show()
                });
			
			$('.xgtx-btn').click(function(){
				parent.$("#head_portrait").val("");
                parent.$('#covered').show()
                parent.$('.xgtx-box').show()
              });
			})
		$('.tc-close').click(function(){
          $('.popup-box1').hide()
          })
        $('.tc-btn2').click(function(){
          $('.popup-box1').hide()
          }) 
        $('.rule-close').click(function(){
          $('.popup-box2').hide()
          }) 
       function onTask(type){
			 var month = $("#imTime").val();
			 art.dialog.open("${ctx}/schedule/now?month="+month+"&type="+type, {
		         id: 'flag',
		         title: '日程安排',
		         lock:true,
		         width:800,
		     	 height:400
		     },  
		     false);
		 }
          
</script>

