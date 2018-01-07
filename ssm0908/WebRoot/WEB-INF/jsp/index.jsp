<%@ page language="java"  pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml"> 
<head>
<%@include file="/common/taglibs.jsp"%> 
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<script type="text/javascript" src="${ctx}/js/jquery.min.js"></script>
<link rel='icon' href="${ctx}/images/ico.png" type=‘image/x-ico’ />
<title>日志安排系统</title>
<link href="${ctx}/css/static/main.css?1=1" rel="stylesheet" type="text/css" />
<link href="${ctx}/css/static/base-global.css" rel="stylesheet" type="text/css" />
<link href="${ctx}/css/static/common.css?5=1" rel="stylesheet" type="text/css" />
<link href="${ctx}/css/static/main.css?1=1" rel="stylesheet" type="text/css" />
<link href="${ctx}/css/static/stylesheet.css" rel="stylesheet" type="text/css" />
<link rel="stylesheet" type="text/css" href="${ctx}/css/right.css" />
<link rel="stylesheet" type="text/css" href="${ctx}/css/pop.css" />

<script type="text/javascript" src="${ctx}/js/jquery.validate.js"></script>
<script type="text/javascript" src="${ctx}/js/jquery.metadata.js"></script>
<script type="text/javascript" src="${ctx}/js/alert/g_alert.js?1=1"></script>
<script type="text/javascript" src="${ctx}/js/util/common.js?1=1"></script>
<script type="text/javascript" src="${ctx}/js/My97DatePicker/WdatePicker.js" ></script>


<script type="text/javascript">

 
$(document).ready(function(){
	$("#leftMenu0").addClass('cur');
	      $('.msg-wake').click(function(){
	          $('.m-box').show()
	          $('#covered').show()
	          
	        })
	        
	      $('.mm-close').click(function(){
	          $('.m-box').hide()
	           $('#covered').hide()
	          
	        })

	      $('.hl-msg').click(function(){
	          $('.m-box').hide()
	        })

	      $('.gly-wake').click(function(){
	          $('.m-box').hide()
	        })

	      $('.gly-wake').click(function(){
	          $('.zhxx-box').show()
	          $('#covered').show()
	        })

	      $('.mm-close').click(function(){
	          $('.zhxx-box').hide()
	           $('#covered').hide()
	        })

	      $('.hl-msg').click(function(){
	          $('.zhxx-box').hide()
	        })

	      $('.msg-wake').click(function(){
	          $('.zhxx-box').hide()
	        })
	     
	        $("#saveForm2").validate({  
				rules:{
					oldPassword:{  
			    		remote: {
			       			url: "${ctx}/user/check/password.do",
			             	type: "post",
			             	data: {  
			             		oldPassword: function() {
			                 		return $("#oldPassword").val();
			              		},
			              		userId: function() {
			                 		return $("#userId").val();
			              		}
			          		}
			       		}  
					},
					password:{  
						equalTo: "#cfmPassword"
					},						
					cfmPassword:{  
						equalTo: "#password"
					}			
				},  
				messages:{
					oldPassword:{  
			    		remote: "原密码不正确！"
			 		},
					password:{
						equalTo: "两次新密码不一致！"
			 		},	 	 			
					cfmPassword:{
						equalTo: "两次新密码不一致！"
			 		}		 		
				}
			});
	}); 
</script>


</head>
<body>


<div class="header">
        <div class="box fn-clear">
            <span><img src="${ctx}/css/images/logo11.png" width="230" height="60"></span>
           
            <div class="fr lh60 hd-txt">
                <div class="message">
                    <div class="m-box hide" id="showMessageId">
                        <div class="xx-arrow"></div>
                        <div class="tit fn-clear">
                            <span class="fl">消息提醒</span>
                            <a href="javascript:(void);" class="mm-close fr"></a>
                        </div>
	                        <ul id="ulId">
	                            <c:forEach items="${mlist}" var="message" >					
									<li><a href="javascript:(void);" onclick="goingPage(${message.id})" >${message.title}</a></li>
								</c:forEach>
	                        </ul>
	                     <c:if test="${msgCount>0}">
		                    	<div class="txt-r pd-t10 pd-b10" id="allIgnore"><a href="javascript:(void);" class="cblue2 hl-msg" onclick="upMessage()">忽略全部</a></div>
		                    </c:if>
		                     <c:if test="${msgCount<=0}">
		                     	<div class="txt-r pd-t10 pd-b10"  id="allIgnore"><span><font color="gray">忽略全部</font></span></div>
		                    </c:if>
                        
                    </div>
                </div>
            </div>
        </div>
        <!-- 修改密码 -->
      <form id="saveForm" name="saveForm" action="${ctx}/user/upw.do" method="post">
        <div class="popup xgmm-box" style="margin-left:-195px;">
            <div class="tc-box">
                <div class="title fn-clear">
                    <span>修改密码</span>
                    <a href="javascript:(void);" class="tc-close fr"></a>
                </div>

                <ul class="pd-t5">
                    <li class="fn-clear pd-t15">
                        <label class="fl txt-r mg-r10"><span style="color: red;">*&nbsp;</span>原密码</label>
                        <input  type="password" id="oldPassword" name="oldPassword"  >
                    	<span id="oldPW" style="color: red;"></span>
                    </li>
                    <li class="fn-clear">
                        <label class="fl txt-r mg-r10"><span style="color: red;">*&nbsp;</span>新密码</label>
                        <input type="password" id="password" name="password" >
                    	<span id="newPW1" style="color: red;"></span>
                    </li>
                    <li class="fn-clear">
                        <label class="fl txt-r mg-r10"><span style="color: red;">*&nbsp;</span>确定新密码</label>
                        <input  type="password" id="cfmPassword" name="cfmPassword" >
                    	<span id="newPW2" style="color: red;"></span>
                    </li>
                </ul>
                <div class="txt-c pd-t15">
                    <input class="form_now" type="button" onclick="updatePW()" value="确定">&nbsp;&nbsp;&nbsp;&nbsp;
                    <input class="form_now" type="button" onclick="javascript:goBack()" value="取消">
                </div>
            </div>
        </div>
        </form>
      
    </div>

    <div class="wrap fn-clear" style="height: 550px;" >
    	<div class="menu-list fl" id="appwrap" style="height: 100%;overflow-y:auto; overflow-x:hidden;" >
    		<ul>
    			<li class="title">菜单栏</li>
		    	<li id="" name="leftMenu" ><a target="mima" onclick="leftColor('1');" href="#" >22222222</a></li>
		    	<li id="" name="leftMenu" ><a target="mima" onclick="leftColor('1');" href="#" >444444444</a></li>
    		</ul>
    	</div>

    	<div class="content1" id="mainarea" style="height: 100%;" >
    		<iframe id="mima" name="mima" width="100%" style="height:100%;overflow: hidden;" frameborder="0" src="${ctx}/welcome.do"></iframe>
    	</div>
    </div>
    
    <script type="text/javascript">
   
  $(".discribtion").hover(function(){
                       $('#discrib').show()
                      },function(){
                          $('#discrib').hide()
                         }) 
  $(".discribtion1").hover(function(){
                       $('#discrib1').show()
                      },function(){
                          $('#discrib1').hide()
                         })                     

$('.popup-btn1').click(function(){
          $('#covered').show()
          $('.popup-box1').show()
        })

      $('.popup-btn2').click(function(){
          $('#covered').show()
          $('.popup-box2').show()
        })
    $(document).ready(function(){
      

      $('.popup-btn3').click(function(){
          $('#covered').show()
          $('.popup-box3').show()
        })
        
       $('.xjrw-btn').click(function(){
                  $('#covered').show()
                  $('.xjrw-box').show()
                  $('.zhxx-box').hide()
                  $('.xgmm-box').hide()
                  $('.m-box').hide()
                  
                })
       
       $('.xjrw-btn1').click(function(){
                  $('#covered').show()
                  $('.xjrw-box1').show()
                })
       
       $('.tc-close').click(function(){
                  $('.xgmm-box').hide()
                  $('.xjrw-box').hide()
                  $('.xjrw-box1').hide()
                  $('#covered').hide()
                  $("#content").hide();
     			  $("#expectTime").hide();
     			  $("#taskWorkTimePrompt").hide();
     			  $("#taskname").val('');
                  $("#taskContent").val('');
				  $("#expectEndTime").val('');
				  $("#taskWorkTime").val('');
				  $("#taskname1").val('');
				  $("#taskContent1").val('');
				  $("#expectEndTime1").val('');
				  $("#taskWorkTime1").val('');
				  $("#projectStage").val('');
				  $("#projectCode").val('');
				  $("#proName").val('');
                })

            

              $('.tc-btn2').click(function(){
                  $('.xgmm-box').hide()
                  $('.xjrw-box').hide()
                  $('.xjrw-box1').hide()
                  $('#covered').hide()
                })

      $('.tc-close').click(function(){
          $('.popup-box1').hide()
          $('.popup-box2').hide()
          $('.popup-box3').hide()
          $('#covered').hide()
        })

      $('.tc-btn1').click(function(){
          $('.popup-box1').hide()
          $('.popup-box2').hide()
          $('.popup-box3').hide()
          $('#covered').show()
        })
       $('.tc-btn11').click(function(){
          $('.popup-box1').hide()
          $('.popup-box2').hide()
          $('.popup-box3').hide()
          $('#covered').show()
        })
       

      $('.tc-btn2').click(function(){
          $('.popup-box1').hide()
          $('.popup-box2').hide()
          $('.popup-box3').hide()
          $('#covered').hide()
        })
    }); 
        
     function cancelTask(){
    	    $("#tasknameText").hide();
     		$("#content").hide();
     		$("#expectTime").hide();
     		$("#taskWorkTimePrompt").hide();
     		$("#taskname").val('');
     		$("#taskContent").val('');
			$("#expectEndTime").val('');
			$("#taskWorkTime").val('');
			$("#projectStage").val('');
			$("#projectCode").val('');
			$("#proName").val('');
			$("#taskname1").val('');
			$("#taskContent1").val('');
			$("#expectEndTime1").val('');
			$("#taskWorkTime1").val('');
     }

     function saveTask(){
    	var taskname = $("#taskname").val();
     	var	taskContent = $("#taskContent").val();
     	var	expectEndTime = $("#expectEndTime").val();
     	var	taskWorkTime = $("#taskWorkTime").val();
     	var nowDate = $("#nowDate").val();
     	//alert(nowDate);
     	if(taskname == "" || taskname == null){
            $("#tasknameText").show();
            return false;
        }else{
            $("#tasknameText").hide();
        }
     	if(taskContent == "" || taskContent == null){
     		$("#content").show();
	     	return false;
     	}else{
     		$("#content").hide();
     		}
     		
     	if(expectEndTime == "" || expectEndTime == null){
     		$("#expectTime").show();
     		return false;
     		}else{
     			$("#expectTime").hide();
     		} 
     	 if(expectEndTime<nowDate){
			alert("亲！完成时间需要大于当前日期哦！！！");
			return false;
		} 
		
		/* if($.trim(taskWorkTime)!=null && $.trim(taskWorkTime)!=''){
			var result = isInteger(taskWorkTime);
			if(!result){
				alert("工作量输入格式不正确，请输入整数！");
				return false;
			}
		} */
		
		if(taskWorkTime == "" || taskWorkTime == null){
     		$("#taskWorkTimePrompt").show();
	     	return false;
     	}else{
     		$("#taskWorkTimePrompt").hide();
     	}
		
		url = "${ctx}/admin/saveTask.do";
			  $.ajax({
				type : "POST", 
				url : url, 
				data : {"taskname":taskname,"taskContent":taskContent,"expectEndTime":expectEndTime,"taskWorkTime":taskWorkTime},
				success : function(data){
					g_alert('success','操作成功','${ctx}/index.do?menu=10000000000001',"${ctx}");
					$("#taskname").val('');
					$("#taskContent").val('');
					$("#expectEndTime").val('');
					$("#taskWorkTime").val('');
				}
			});
			
		  $('.tc-btn1').click(function(){
                 $('.xgmm-box').hide()
                 $('.xjrw-box').hide()
                 $('.xjrw-box1').hide()
                 $('#covered').hide()
               });
		} 
		
		
		function saveTask1(){
		var taskname = $("#taskname1").val();
     	var	taskContent = $("#taskContent1").val();
     	var	expectEndTime = $("#expectEndTime1").val();
     	var	taskWorkTime = $("#taskWorkTime1").val();
     	var	projectName = $("#proName").val();
     	var	projectCode = $("#projectCode").val();
     	var	projectStage = $("#projectStage").val();
     	//var	projectStageCode = $("#projectStageCode").val();
     	var nowDate = $("#nowDate").val();
     	//alert(projectStageCode);
     	if(taskname == "" || taskname == null){
            $("#tasknameText").show();
            return false;
        }else{
            $("#tasknameText").hide();
        }
     	
     	if(taskContent == "" || taskContent == null){
     		$("#content1").show();
	     	return false;
     	}else{
     		$("#content1").hide();
     		}
     		
     	if(expectEndTime == "" || expectEndTime == null){
     		$("#expectTime1").show();
     		return false;
     		}else{
     			$("#expectTime1").hide();
     		} 
     	 if(expectEndTime<nowDate){
			alert("亲！完成时间需要大于当前日期哦！！！");
			return false;
		} 
		
		if(taskWorkTime == "" || taskWorkTime == null){
     		$("#taskWorkTimePrompt1").show();
	     	return false;
     	}else{
     		$("#taskWorkTimePrompt1").hide();
     	}
		
		if(projectName == "" || projectName == null){
			$("#selText1").show();
			$("#projectCode1").hide();
     		return false;
     		}else{
     			$("#selText1").hide();
     			if(projectCode == "" || projectCode == null){
				$("#projectCode1").show();
	     		return false;
	     		}else{
	     			$("#projectCode1").hide();
				}
		}
		
		if(projectStage == "" || projectStage == null){
			$("#projectStage1").show();
     		return false;
     		}else{
     			$("#projectStage1").hide();
		}
		
			
		url = "${ctx}/admin/saveTask.do";
			  $.ajax({
				type : "POST", 
				url : url, 
				data : {"taskname":taskname,"taskContent":taskContent,"expectEndTime":expectEndTime,"taskWorkTime":taskWorkTime,"projectName":projectName,"projectCode":projectCode,"projectStage":projectStage},
				success : function(data){
					g_alert('success','操作成功','${ctx}/index.do?menu=10000000000001',"${ctx}");
					$("#taskname1").val('');
					$("#projectStage").val('');
					$("#projectCode").val('');
					$("#proName").val('');
					$("#taskContent1").val('');
					$("#expectEndTime1").val('');
					$("#taskWorkTime1").val('');
				}
			});
			
          $('.tc-btn11').click(function(){
                 $('.xgmm-box').hide()
                 $('.xjrw-box').hide()
                 $('.xjrw-box1').hide()
                 $('#covered').hide()
               });
		} 
		
		
    function upMessage(){
		url = "${ctx}/upMessage.do";
			  $.ajax({
				type : "POST", 
				url : url,
				data : {},
				success : function(){
					$("#num-bg").html("");
					$("#num-bg").removeClass("num-bg");
					$("#allIgnore").html("<span><font color=\"gray\">忽略全部</font></span>");
					$("#ulId li").remove();
					$('#covered').hide();
					$('.m-box').hide()
				}
			});
		} 
	function goingPage(id){
	
		  url = "${ctx}/message/messageHandle.do";
		  $.ajax({
			type : "GET", 
			url : url,
			data : "id="+id, 
			dataType : "json", 
			success : function(data){
				if(data.list!=null){
					if(data.list.length>0){
						$("#num-bg").html(data.list.length);
						$("#allIgnore").html("<a href=\"javascript:(void);\" class=\"cblue2 hl-msg\" onclick=\"upMessage()\">忽略全部</a>");
					}else{
						$("#num-bg").html("");
						$("#num-bg").removeClass("num-bg");
						$("#allIgnore").html("<span><font color=\"gray\">忽略全部</font></span>");
						$("#showMessageId").hide();
					}
					$("#ulId li").remove();
					for ( var i = 0; i < data.list.length; i++) {
						$("#ulId").append("<li><a href=\"javascript:(void);\" onclick=\"goingPage("+(data.list[i]).id+")\" >"+(data.list[i]).title+"</a></li>");
					}
					//--------------- 跳转到工作台-------
					if(data.focusMenu=="11"){
						 $(".cur").removeClass('cur');
						 $("#leftMenu0").addClass('cur');
					}
					if(data.focusMenu=="21"){
						 $(".cur").removeClass('cur');
						 $("#leftMenu0").addClass('cur');
					}
					if(data.focusMenu=="31"){
						 $(".cur").removeClass('cur');
						 $("#leftMenu0").addClass('cur');
					}
					//--------------- 跳转到我的任务-------
					if(data.focusMenu=="12"){
						 $(".cur").removeClass('cur');
						 $("#leftMenu1").addClass('cur');
					}
					if(data.focusMenu=="32"){
						 $(".cur").removeClass('cur');
						 $("#leftMenu1").addClass('cur');
					}
					//--------------- 跳转到我创建的任务-------
					if(data.focusMenu=="22"){
						 $(".cur").removeClass('cur');
						 $("#leftMenu2").addClass('cur');
					}
					//--------------- 跳转到我负责的任务-------
					if(data.focusMenu=="23"){
						 $(".cur").removeClass('cur');
						 $("#leftMenu3").addClass('cur');
					}
				}
				var newUrl="${ctx}"+data.result+"?flag="+data.flag+"&taskInfoId="+data.taskInfoId+"&taskStatus="+data.taskStatus;
				$("#mima").attr("src",newUrl);
				$('#covered').hide();
				$('.m-box').hide()
			}
		});
		
	}	
	  
	  $(document).ready(function(){
		var upPass = $("#upPass").val();
	 	if(upPass==2){
	                  $('#covered2').show()
	                  $('.xgmm-box1').show()
		}else{
			 $('#covered2').hide()
	         $('.xgmm-box1').hide()
	         }
        
      }); 
  </script>

</body>
</html>
 