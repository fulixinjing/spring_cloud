<%@ page language="java" pageEncoding="UTF-8"%>
<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.0 Transitional//EN">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<%@ include file="/common/meta.jsp"%>
<title>任务管理系统</title>
<link href="${ctx}/css/static/base-global.css" rel="stylesheet" type="text/css">
<link href="${ctx}/css/static/common.css" rel="stylesheet" type="text/css">
<link href="${ctx}/css/static/main.css" rel="stylesheet" type="text/css">
<link href="${ctx}/css/ystep.css?1=1" rel="stylesheet" type="text/css">
<link rel="stylesheet" type="text/css" href="${ctx}/css/common.css">
<link rel="stylesheet" type="text/css" href="${ctx}/css/right.css">
<link rel="stylesheet" type="text/css" href="${ctx}/css/pop.css">
<link rel="stylesheet" type="text/css" href="${ctx}/components/colorbox/colorbox.css"/>

<script type="text/javascript" src="${ctx}/js/static/jquery/jquery-1.7.2.min.js"></script>
<script type="text/javascript" src="${ctx}/js/My97DatePicker/WdatePicker.js" ></script>
<script type="text/javascript" src="${ctx}/js/sysUtil.js"></script>
<script type="text/javascript" src="${ctx}/js/alert/g_alert.js?1=1"></script>
<script language="javascript" src="${ctx}/components/colorbox/jquery.colorbox.js"></script>
</head>

<body>
 	<div class="content" id="mainarea">
		<div class="title"><h2>个人中心</h2></div>
		<div class="form-box fn-clear mg-t10">
			<div class="fl wd918">
				<div class="pd-b30 mg-l15" style="border-bottom:1px #e5e5e5 solid;">
					<h2 class="fz16">出勤信息&nbsp;&nbsp;&nbsp;
						<a class="mg-l20 lh30 fz14 blue popup-btn2">考勤规则手册</a>
					</h2>
					<div class="fn-clear search-box mg-t15 fz14">
	                     <span class="fl mg-l10">
	                        	 考勤月份：<input readonly="readonly" type="text" id="imTime" name="imTime" value="${nowDate }" onclick="WdatePicker({onpicking:function(dp){ timeSelect(dp.cal.getNewDateStr()) },skin:'whyGreen',dateFmt:'yyyy-MM',maxDate:'%y-%M'})" onfocus="WdatePicker({onpicking:function(dp){ timeSelect(dp.cal.getNewDateStr()) },skin:'whyGreen',dateFmt:'yyyy-MM',maxDate:'%y-%M'})" class="Wdate" style="width:120px;border:0px;">
	                     </span>
	                     <a class="fl mg-l20 lh30 fz14 blue" onclick="findAttenceDetails()">查看详情</a>
	                </div>
					
	                <div class="fn-clear pd-t15 pd-b15 mg-t15">
	                	<div class="fl mg-l15">
	                		<span class="grzx-bg1">${pAttenceDay }<font class="fz12 mg-l2">天</font></span>
	                		<p class="fz14 mg-t5 txt-c">出勤天数</p>
	                	</div>
	                	<div class="fl mg-l20">
	                		<span class="grzx-bg2">${pAttenceTime }<font class="fz12 mg-l2">时</font></span>
	                		<p class="fz14 mg-t5 txt-c">勤外工时</p>
	                	</div>
	                	<div class="fl mg-l20">
	                		<span class="grzx-bg3">${pMealPay }<font class="fz12 mg-l2">元</font></span>
	                		<p class="fz14 mg-t5 txt-c">餐费报销</p>
	                	</div>
	                	<div class="fl mg-l20" >
	                		<span class="grzx-bg5">${pTrafficPay }<font class="fz12 mg-l2">元</font></span>
	                		<p class="fz14 mg-t5 txt-c">交通报销</p>
	                	</div>
	                	<div class="fl mg-l20">
	                		<span class="grzx-bg4">${pAttenceUnpunctualTime }<font class="fz12 mg-l2">次</font></span>
	                		<p class="fz14 mg-t5 txt-c">迟到早退次数</p>
	                	</div>
	                </div>
	               <!-- <div class="mg-l20 lh30 fz14 blue"><font>说明：新员工显示的考勤信息和实际会有偏差，下个月考勤数据会符合实际情况。</font></div> -->
	                
				</div>
				<div class="pd-b30 mg-l15 pd-t30">
					<h2 class="fz16">个人设置</h2>
					<div class="fn-clear search-box mg-t15 fz14">
	               <shiro:hasPermission name="user_attence:setAttence">      
	                     <a class="fl lh30 fz14 blue tc-btn" >考勤规则设置</a>
	               </shiro:hasPermission>     
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
                    <span>设置考勤规则</span>
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
    
    <div class="popup popup-box2" style="margin-left:-499px;position:absolute;">
    	<a href="javascript:void(0);" class="rule-close"></a>
    	<div class="rule-box">
    		<h2 class="fz16 txt-c"><b>系统考勤规则说明</b></h2>
    		<div class="pd-t30 lh26">
    			<p class="fz14">一、出勤规则：</p>
    			<p class="pd-l10">1、出勤天数：根据每月导入的考勤记录统计月度员工的实际出勤天数；</p>
    		</div>
    		<div class="pd-t10 lh26">
    			<p class="fz14">二、 加班规则：</p>
    			<p class="pd-l10">1、累计加班工时：根据每月导入的考勤记录统计月度员工的累计加班时间；</p>
    			<p class="pd-l10">2、累计加班工时=工作日加班+非工作日加班；</p>
    			<p class="pd-l10">3、工作日加班以每日18：30后开始计算。精确到0.5小时。超过30分钟少于60分钟按0.5小时计算。少于30分钟按0小时计算；</p>
    			<p class="pd-l10">4、非工作日加班，以打卡时间计算。二次打卡时间减去首次打卡时间，满4小时算加班半天，满8小时算加班1天；</p>
    		</div>
    		<div class="pd-t10 lh26">
    			<p class="fz14">三、报销规则：</p>
    			<p class="pd-l10">1、非工作日加班，满8小时报1餐，20点以后报2餐（餐费25元），晚上9点后报销打车费；</p>
    			<p class="pd-l10">2、根据每月导入的考勤记录统计月度员工的加班情况，并计算相对应的饭补和车补金额，</p>
    			<p class="pd-l29">饭补金额=20点后打卡次数*25【包含21点后打卡次数】</p>
    			<p class="pd-l29">车补金额=21点后打卡次数*25</p>
    			<p class="pd-l29">加班报销=饭补+车补；</p>
    		</div>
    		<div class="pd-t10 lh26">
    			<p class="fz14">四、迟到早退规则：</p>
    			<p class="pd-l10">1、晚于9:30打卡，算迟到；若早上9:00之前打卡，下班早于18:00打卡算早退；若早上9:30之前打卡，下班早于18:30打卡算早 退；</p>
    			<p class="pd-l10">2、若上班打卡早于9:30之前且下班打卡晚于12:00，记为工作半天；由于迟到30分钟按事假处理，若10:00-12:00之间打卡，按考勤少0.5天处理；若13:30-18:30之间打卡，按考勤少0.5天处理；</p>
    			<p class="pd-l29">考勤时间点跨度图如下所示：</p>
    			<p class="pd-l29 cred"><img src="../css/images/rule.png"></p>
    			<p class="pd-l29 cred">备注：数字代表出勤天数，例如   1+早退   代表出勤1天  + 早退1次；</p>
    		</div>
			<div class="pd-t10 lh26">
    			<p class="fz14">五、调休请假规则：</p>
    			<p class="pd-l10"> 1、调休，请假最小单位为半天；</p>
    			<p class="pd-l10">2、根据每月导入的考勤记录统计月度员工的可倒休天数，倒休天数依据员工非工作日的加班天数而定。精确度为0.5天。  【非工作日加班工时少于8小时的计半天，</p>
    			<p class="pd-l29">少于4小时的不计倒休，超过8小时的按一天计算】；</p>
    			<p class="pd-l10">3、调休：本月加班工时，需在本月和次月休完，如有剩余加班工时的情况，则做过期处理；</p>
    		</div>
			<div class="pd-t10 lh26">
    			<p class="fz14">六、异常打卡说明：</p>
    			<p class="pd-l10">若员工漏打卡（外出，请假等），需由任务管理系统的管理员定期按照oa的实际情况做更新调整。</p>
    		</div>
    		<div class="pd-t10 lh26">
    			<p class="fz14 blue">七、新员工显示的考勤信息和实际会有偏差，下个月考勤数据会符合实际情况。</p>
    		</div>
    	</div>
    </div>
    
</html>
<script type="text/javascript">

	function check(){
	    var obj=document.getElementById("head_portrait");
	    var f=document.getElementById("head_portrait").value;
	    if(f!=""&&f!=null){ 
	        if(!/\.(gif|jpg|jpeg|png|GIF|JPG|PNG)$/.test(f)){ 
	            alert("仅支持gif,jpeg,jpg,png格式！");
	            obj.outerHTML=obj.outerHTML;
	            return false; 
	        }else if(f.length>25){
	            alert("您的图片名过长，请缩减！");
	            obj.outerHTML=obj.outerHTML;
	            return false; 
	        }else if(/.*[\u4e00-\u9fa5]+.*$/.test(f)){
	            alert("您的图片名含有中文，请更改！");
	            obj.outerHTML=obj.outerHTML;
	            return false; 
	        }
	    }
	}


	function timeSelect(value){
		url = "${ctx}/attence/personSysList.do";
		$.ajax({	 
			type : "POST",  
			url: url, 
			data : {"imTime":value},
			dataType : 'json',
			success: function(data){
				if(data==null){
					$(".grzx-bg1").html("<span class=\"grzx-bg1\">"+""+"<font class=\"fz12 mg-l2\">天</font></span>");
					$(".grzx-bg2").html("<span class=\"grzx-bg2\">"+""+"<font class=\"fz12 mg-l2\">时</font></span>");
					$(".grzx-bg3").html("<span class=\"grzx-bg3\">"+""+"<font class=\"fz12 mg-l2\">元</font></span>");
					$(".grzx-bg4").html("<span class=\"grzx-bg4\">"+""+"<font class=\"fz12 mg-l2\">次</font></span>");
					$(".grzx-bg5").html("<span class=\"grzx-bg5\">"+""+"<font class=\"fz12 mg-l2\">元</font></span>");
				}else{
					$(".grzx-bg1").html("<span class=\"grzx-bg1\">"+data.pAttenceDay+"<font class=\"fz12 mg-l2\">天</font></span>");
					$(".grzx-bg2").html("<span class=\"grzx-bg2\">"+data.pAttenceTime+"<font class=\"fz12 mg-l2\">时</font></span>");
					$(".grzx-bg3").html("<span class=\"grzx-bg3\">"+data.pMealPay+"<font class=\"fz12 mg-l2\">元</font></span>");
					$(".grzx-bg4").html("<span class=\"grzx-bg4\">"+data.pAttenceUnpunctualTime+"<font class=\"fz12 mg-l2\">次</font></span>");
					$(".grzx-bg5").html("<span class=\"grzx-bg5\">"+data.pTrafficPay+"<font class=\"fz12 mg-l2\">元</font></span>");
				}
				
			}
		});
	}
	
	
	function findAttenceDetails(){
		var month = $("#imTime").val();
		var url = "${ctx}/attence/findAttenceDetails.do?months="+month+"&flag="+"88888";
		$.colorbox({
			href:url,
			iframe:true,
			width:"1000",
			height:"500"
		});
	}
			
		 $(document).ready(function(){
		 	
		 		var acgz =  $("#acgz").val();
		 		if(acgz==1){
		 			$("#sel1").attr("checked",true)
		 		}
		 		if(acgz==2){
		 			$("#sel2").attr("checked",true)
		 		}
		 		if(acgz==3){
		 			$("#sel3").attr("checked",true)
		 		}	
		 		
			$('.tc-btn').click(function(){		
				$('.popup-box1').show();
			});
			
			
			$('.popup-btn2').click(function(){		
				$('.popup-box2').show();
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
      
       $('.tc-btn1').click(function(){
         	var selTime = $("input[name='selTime']:checked ").val();
         	if(selTime==undefined){
         		alert("请选择规则日期！");
         	}else{
	         	url = "${ctx}/attence/attenceSys.do";
				$.ajax({	 
					type : "POST",  
					url: url, 
					data : {"selTime":selTime},
					dataType : 'json',
					success: function(data){
						//$("input[name='selTime'][value=@selTime]").attr("checked":true);
						alert("设置成功");
						$('.popup-box1').hide()
					}
	          })
	        }
        })
       
          
</script>

