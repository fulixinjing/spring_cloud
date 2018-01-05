<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/common/taglibs.jsp" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.0 Transitional//EN"> 
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<%@ include file="/common/meta.jsp" %>
<title>用户列表</title>
<link href="${ctx}/css/static/base-global.css" rel="stylesheet" type="text/css" />
<link href="${ctx}/css/static/common.css" rel="stylesheet" type="text/css" />
<link href="${ctx}/css/static/main.css?1=1" rel="stylesheet" type="text/css" />


<script type="text/javascript" src="${ctx}/js/static/jquery/jquery-1.7.2.min.js"></script>
<!-- 饼状图 -->
<script type="text/javascript" src="${ctx}/js/static/piechart/jsapi.js"></script>
<script type="text/javascript" src="${ctx}/js/static/piechart/corechart.js"></script>
<script type="text/javascript" src="${ctx}/js/static/piechart/jquery.gvChart-1.0.1.min.js"></script>

<link rel="stylesheet" type="text/css" href="${ctx}/components/colorbox/colorbox.css"/>
<script language="javascript" src="${ctx}/components/colorbox/jquery.colorbox.js"></script>
<script type="text/javascript">
 
</script>
<script type="text/javascript">
    gvChartInit();
    $(document).ready(function () {
    	if($("#userRole").val()=="BMJL" || $("#userRole").val()== "XMJL" || $("#userRole").val()== "TDJL" ){
			 document.getElementById("RadioGroup1_2").checked=true; 
			 document.getElementById("RadioGroup2_2").checked=true;
		}else{
			 document.getElementById("RadioGroup1_0").checked=true; 
			 document.getElementById("RadioGroup2_0").checked=true;
		}
		
        $('#myTable2').gvChart({
            chartType: 'PieChart',
            gvSettings: {
                vAxis: { title: '延期' },
                hAxis: { title: '完成' },
                width: 500,
                height: 220
            }
        });
    });
</script>
<!-- 饼状图 end-->
<script type="text/javascript">
  // 输入框内容点击变色
  function focusMeByAll(which,defalutWord){
      var val = $(which).val();
      $(which).css("color",'#3c3c3c');
      if(val==defalutWord)
        $(which).val("");
    }

    function blurMeByAll(which,defalutWord){
      var val = $(which).val().replace(/(^\s*)|(\s*$)/g, "");
        if(val==""){
        $(which).val(defalutWord);
        $(which).css("color",'#d6d6d6');
        }
    }
</script>
<script type="text/javascript">
	$(document).ready(function(){
	      $('.msg-wake').click(function(){
	          $('.m-box').show()
	        })

	      $('.mm-close').click(function(){
	          $('.m-box').hide()
	        })

	      $('.hl-msg').click(function(){
	          $('.m-box').hide()
	        })

	      $('.gly-wake').click(function(){
	          $('.m-box').hide()
	        })

	      $('.gly-wake').click(function(){
	          $('.zhxx-box').show()
	        })

	      $('.mm-close').click(function(){
	          $('.zhxx-box').hide()
	        })

	      $('.hl-msg').click(function(){
	          $('.zhxx-box').hide()
	        })

	      $('.msg-wake').click(function(){
	          $('.zhxx-box').hide()
	        })
	});
</script>
<script type="text/javascript">
	$(document).ready(function(){
		
	   // 左边随右边高度变化而变化
		function initSideBar() {
			var o1 = document.getElementById("appwrap");
			var o2 = document.getElementById("mainarea");
			var maxh1 = o1.offsetHeight;
			var maxh2 = o2.offsetHeight;
			if(maxh1>maxh2)
				o2.style.height = maxh1 + "px";
			else
				o1.style.height = maxh2 + "px";
			}
			window.onload=initSideBar;   
	});
</script>
<script type="text/javascript">
  
    /*滑动门*/
    function g(o){return document.getElementById(o);}
  function HoverLi3(n){
        //如果有N个标签,就将i<=N;
        $("#type").val(n);
        
        for(var i=1;i<=2;i++){
        g('tb3_'+i).className='fl';
        g('tbc3_0'+i).className='hide';
      }
      g('tbc3_0'+n).className='';
    g('tb3_'+n).className='fl cur';
    }
    /*点击展开.隐藏*/
    function changeMc(id){
      var content = document.getElementById(id);
      if(content != null) {
        if(content.style.display == "none") {
          content.style.display = "block";
        }
        else if(content.style.display == "block")
          content.style.display = "none";
      }
    }
	
</script>
</head>

<body>
	<input type="hidden" name="userRole" id="userRole" value="${userRole}">
 	<div class="content" id="mainarea">
        <div class="title"><h2>汇总统计</h2></div>
        <div class="form-box fn-clear mg-t20">
            <div class="fl wd918">
                <div class="tab-box fn-clear">
                    <a href="#"><span class="fl cur" id="tb3_1" onclick="x:HoverLi3(1);">任务状态</span></a>
                    <a href="#"><span class="fl" id="tb3_2" onclick="x:HoverLi3(2);">任务得分</span></a>
                </div>
                
                <input type="hidden" id="type"/>
                
                <div class="gzt-hz">
                    <div id="tbc3_01">
                        <div class="pd35" >
                            <ul class="fn-clear">
                                <li class="fl" style="border-bottom:none;">
                                    <div>
                                    	<iframe id="pieChar1" name="pieChar1"  style="height:280px;width:500px" frameborder="0" src="${ctx}/admin/PieChart.do?type=1&astj=${aswcTask}&tqtj=${tqwcTask}&yqtj=${yqwcTask}"></iframe>     
                                    </div>
                                </li>
                                <li class="fl wd308 mg-t25 sxtj-l" style="border-bottom:none;">
                                <div id="hidden1" >
                                	<c:if test="${userRole != 'BMJL' and  userRole != 'XMJL' and userRole != 'TDJL'}">
	                                    <p class="fz14"><input name="RadioGroup1" type="radio" value="00" id="RadioGroup1_0" class="vt-t" onchange="radioChange('')"/>&nbsp;科技公司</p>
	                                  </c:if> 
	                                  <p class="fz14 mg-t10"><input name="RadioGroup1" type="radio" value="01" id="RadioGroup1_2" class="vt-t" onclick="selectHidden1()"/>&nbsp;按部门分析
	                             			<select name="select" class="sel" id="select"  onchange="radioChange(this.value)"  runat="server" style="display:none;">
	                                      		<option value="">---请选择---</option>
	                                      		<c:forEach items="${list01}" var="Dictionary" varStatus="status">
	                                      			<option value="${Dictionary.code }">${Dictionary.name }</option>
	                                      		</c:forEach>
	                                      	</select>
		                             	</p>
	                                    <p class="fz14 mg-t10">
	                                    	<input name="RadioGroup1" type="radio" value="02" id="RadioGroup1_3" class="vt-t" onclick="selectHidden1()"/>&nbsp;按团队分析
		                                		<select name="select" class="sel" id="select"  onchange="radioChange(this.value)"  runat="server" style="display:none;">
		                                      		<option value="">---请选择---</option>
		                                      		<c:forEach items="${list02}" var="Dictionary" varStatus="status">
		                                      			<option value="${Dictionary.code }">${Dictionary.name }</option>
		                                      		</c:forEach>
		                                      	</select>
		                                </p>
		                                <c:if test="${userRole != 'BMJL' and  userRole != 'XMJL' and userRole != 'TDJL'}">
	                                    <p class="fz14 mg-t10">
		                                    <input name="RadioGroup1" type="radio" value="04" id="RadioGroup1_4" class="vt-t" onclick="selectHidden1()"/>&nbsp;按岗位分析
			                                    <select name="select" class="sel" id="select"  onchange="radioChange(this.value)"   runat="server" style="display:none;">
		                                      		<option value="">---请选择---</option>
		                                      		<c:forEach items="${list04}" var="Dictionary" varStatus="status">
		                                      			<option value="${Dictionary.code }">${Dictionary.name }</option>
		                                      		</c:forEach>
		                                      	</select>
		                                </p>
		                                 <p class="fz14 mg-t10">
	                                        <input name="RadioGroup1" type="radio" value="10" id="RadioGroup1_1" class="vt-t" onclick="selectHidden1()"/>&nbsp;按人员分析&nbsp;
	                                    		<select name="select"  class="sel" id="select" onchange="radioChange(this.value)" onclick="selectSingleUser(1)" runat="server" style="display:none;">
		                                      		<option value="" id="fzrid">---- 请 选 择 ----</option>
		                                      	</select>
	                                    </p>
		                                </c:if>
	                               </div>
                                </li>
                            </ul>
                            <div  id="countTask"  class="fn-clear fz14 df-box lh30 mg-t30 mg-l30">
                              <span id="alltask" class="fl wd25b">完成总任务数：<font id="f1" class="red">${totalTask }</font></span>
                                <span id="astask" class="fl wd25b">按时完成任务数：<font id="f2" class="red">${aswcTask }</font></span>
                                <span id="tqtask" class="fl wd25b">提前完成任务数：<font id="f3" class="red">${tqwcTask }</font></span>
                                <span id="yqtask" class="fl wd25b">延期完成任务数：<font id="f4" class="red">${yqwcTask }</font></span> 
                            </div>
                        </div>
                        
                    </div>
                    <div id="tbc3_02" class="hide">
                        <div class="pd35">
                            <ul class="fn-clear">
                                <li class="fl" style="border-bottom:none;">
                                    <div>
                                     <iframe id="pieChar2" name="pieChar2" width="500px" style="height:280px;" frameborder="0" src="${ctx}/admin/PieChart.do?type=2&ztot=${ztotTask}&ftos=${ftosTask}&etot=${etotTask}"></iframe>     
                                    </div>
                                </li>
                                <li class="fl wd308 mg-t25 sxtj-l" style="border-bottom:none;">
                                    <div id="hidden2" >
                                    <form id="form" >
                                    	<c:if test="${userRole != 'BMJL' and  userRole != 'XMJL' and userRole != 'TDJL'}">
	                                    <p class="fz14"><input name="RadioGroup2" type="radio" value="00" id="RadioGroup2_0" class="vt-t" onchange="radioChange2('')" />&nbsp;科技公司</p>
	                                    </c:if>
	                                    <p class="fz14 mg-t10"><input name="RadioGroup2" type="radio" value="01" id="RadioGroup2_2" class="vt-t" onclick="selectHidden2()"/>&nbsp;按部门分析
		                                    	<select name="select" class="sel" id="select" onchange="radioChange2(this.value)" runat="server" style="display:none;">
			                                      		<option value="">---请选择---</option>
			                                      		<c:forEach items="${list01}" var="Dictionary" varStatus="status">
		                                      			<option value="${Dictionary.code }">${Dictionary.name }</option>
		                                      		</c:forEach>
		                                      	</select>
	                                    </p>
	                                    <p class="fz14 mg-t10"><input name="RadioGroup2" type="radio" value="02" id="RadioGroup2_3" class="vt-t" onclick="selectHidden2()"/>&nbsp;按团队分析
	                                    	 	<select name="select" class="sel" id="select" onchange="radioChange2(this.value)" runat="server" style="display:none;">
		                                      		<option value="">---请选择---</option>
		                                      		<c:forEach items="${list02}" var="Dictionary" varStatus="status">
		                                      			<option value="${Dictionary.code }">${Dictionary.name }</option>
		                                      		</c:forEach>
		                                      	</select>
	                                    </p>
	                                    <c:if test="${userRole != 'BMJL' and  userRole != 'XMJL' and userRole != 'TDJL'}">
	                                    <p class="fz14 mg-t10"><input name="RadioGroup2" type="radio" value="04" id="RadioGroup2_4" class="vt-t" onclick="selectHidden2()"/>&nbsp;按岗位分析
	                                    		<select name="select" class="sel" id="select" onchange="radioChange2(this.value)" runat="server" style="display:none;">
		                                      		<option value="">---请选择---</option>
		                                      		<c:forEach items="${list04}" var="Dictionary" varStatus="status">
		                                      			<option value="${Dictionary.code }">${Dictionary.name }</option>
		                                      		</c:forEach>
		                                      	</select>
	                                    </p>
	                                    <p class="fz14 mg-t10">
	                                        <input name="RadioGroup2" type="radio" value="10" id="RadioGroup2_1" class="vt-t" onclick="selectHidden2()"/>&nbsp;按人员分析&nbsp;
	                                        	<select name="select" class="sel" id="select" onchange="radioChange2(this.value)" onclick="selectSingleUser(2)" runat="server" style="display:none;">
		                                      		<option value="" id="uid">---- 请 选 择 ----</option>
 		                                      	</select>
	                                    </p>
	                                    </c:if>
	                                    </form>
                                    </div>
                                </li>
                            </ul>
                            <div id="countTask" class="fn-clear fz14 df-box lh30 mg-t30 mg-l30">
                              	<span id="alltask" class="fl wd25b">完成总任务数：<font id="font1" class="red">${totalTask1 }</font></span>
                                <span id="astask" class="fl wd25b">1~3分任务数：<font id="font2" class="red">${ztotTask }</font></span>
                                <span id="tqtask" class="fl wd25b">4~7分任务数：<font id="font3" class="red">${ftosTask }</font></span>
                                <span id="yqtask" class="fl wd25b">8~10分任务数：<font id="font4" class="red">${etotTask }</font></span> 
                            </div>
                                      
                        </div>
                    </div>
                </div>
            </div>

            
        </div>
    </div>
    <div id="covered"></div>
</body>

<script type="text/javascript">

function selectSingleUser(flag){
    var url= "${ctx}/admin/userList.do?flag="+flag;
    $.colorbox({
        href:url,
        iframe:true,
        width:"680",
        height:"350"
    });
}
function selectSingleUser_callback(result,flag){
	//alert(flag);
	if(flag == 1){
		for (var i in result) {
			var tmp = result[i].split(",");
			$("#fzrid").val(tmp[0]);
			$("#fzrid").text(tmp[1]);
		}
		 radioChange(tmp[0]);
	}else if(flag == 2){
		for (var i in result) {
			var tmp = result[i].split(",");
			$("#uid").val(tmp[0]);
			$("#uid").text(tmp[1]);
		}
		 radioChange2(tmp[0]);
	}
}


function selectHidden1(){
	$("input[name=RadioGroup1]").change(function() {
		//让所有都隐藏
		$("#hidden1").find("select[name=select]").each(function(){
			$(this).hide();
		});
		//选中的显示
		$(this).parent().find("select[name=select]").show();
		$(this).parent().find("select[name=select]").val("");
		$("#fzrid").text("---- 请 选 择 ----")
	});
}
function selectHidden2(){
	$("input[name=RadioGroup2]").change(function() {
		//让所有都隐藏
		$("#hidden2").find("select[name=select]").each(function(){
			$(this).hide();
		});
		//选中的显示
		$(this).parent().find("select[name=select]").show();
		$(this).parent().find("select[name=select]").val("");
		$("#uid").text("---- 请 选 择 ----")
	});
}
		
		function radioChange(id){
			var astj;			//按时提交
			var tqtj;				//按时提交
			var yqtj;			//延期提交
			var type=$("#type").val();
			//alert(type);
			if(type == null || type == ""){
				type=1;
			}
			
			 var type_code = $("input[name='RadioGroup1']:checked ").val();
			
			 //alert(select);
			
		url = "${ctx}/admin/radioChange.do";
			$.ajax({
				type : "POST", 
				url : url, 
				data : {"type_code":type_code, "selected":id},
				dataType :"json",
				success : function(data){
					var content = data;
					$("#f1").text(content.alltask);
					$("#f2").text(content.astask);
					$("#f3").text(content.tqtask);
					$("#f4").text(content.yqtask);
					
					 astj=content.astask;	//按时提交
					 tqtj=content.tqtask;		//按时提交
					 yqtj=content.yqtask;		//按时提交
					  document.getElementById("pieChar"+type).src = "${ctx}/admin/PieChart.do?type="+type+"&astj="+astj+"&tqtj="+tqtj+"&yqtj="+yqtj;
				}
			});
			
		}
		
		
	function radioChange2(id){
			var ztot;			//0~3分
			var ftos;			//4~7分
			var etot;			//8~10分
			var type=$("#type").val();
			//alert(type);
			if(type == null || type == ""){
				type=2;
			}
			
			//var type_code = $("input[name='RadioGroup2']:checked ").val($("input[name='RadioGroup1']:checked ").val());
			var type_code = $("input[name='RadioGroup2']:checked ").val();
			 //alert(select);
			
		url = "${ctx}/admin/radioChange2.do";
			$.ajax({
				type : "POST", 
				url : url, 
				data : {"type_code":type_code, "selected":id},
				dataType :"json",
				success : function(data){
					var content = data;
					$("#font1").text(content.allTask);
					$("#font2").text(content.ztotTask);
					$("#font3").text(content.ftosTask);
					$("#font4").text(content.etotTask);
					
					ztot=content.ztotTask;			//0~3分
					ftos=content.ftosTask;			//4~7分
					etot=content.etotTask;			//8~10分
					
					  document.getElementById("pieChar"+type).src = "${ctx}/admin/PieChart.do?type="+type+"&ztot="+ztot+"&ftos="+ftos+"&etot="+etot;
				}
			});
			
		}
		




</script> 

</html>
