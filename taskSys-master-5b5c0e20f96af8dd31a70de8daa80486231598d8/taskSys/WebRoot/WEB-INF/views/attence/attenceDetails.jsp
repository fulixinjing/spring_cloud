<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/common/taglibs.jsp" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.0 Transitional//EN"> 
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<%@ include file="/common/meta.jsp" %>
<title>考勤明细</title>
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

<style type="text/css">
	fieldset {
		padding:10px;
		margin:10px;
		
		color:#333; 
		border:#06c dashed 1px;
	} 
	legend {
		color:#06c;
		font-weight:800; 
		background:#fff;
	} 

	#tableID2  td{
		text-align:center;
	}	
	
	.sel_btn{
		height: 21px;
		line-height: 21px;
		padding: 0 11px;
		background: #e4e4e4;
		border: 1px #26bbdb solid;
		border-radius: 3px;
		color: #565656;
		display: inline-block;
		text-decoration: none;
		font-size: 12px;
		outline: none;
	}
	.ch_cls{
		background: #02bafa;
	}
</style>
<script type="text/javascript">
	var selectValueArr=new Array();//该数组用于存放备注发生变化的值
	
	function saveBtnClick(){//备注修改后提交保存
		var isAccept = confirm("确认要修改并保存数据？");
		var flag = $("#flag").val();
		if(isAccept){
			var paramStr="";
			if(selectValueArr.length>0){
				for ( var i = 0; i < selectValueArr.length; i++) {
					if(i==selectValueArr.length-1){
						paramStr=paramStr+selectValueArr[i];
					}else{
						paramStr=paramStr+selectValueArr[i]+",";
					}
				}
				
			}
			if(paramStr==null || paramStr==''){
				alert("未有数据变更！");
				return;
			}
			$.ajax({
				type : "POST",  
				url: "${ctx}/attence/modifyAttenceDetails.do", 
				data : {"paramStr":paramStr},
				dataType : 'text',
				success: function(data){
					if(data=='ok'){
						alert("保存成功！");
						if(flag=='88888'){
							window.parent.location.href='${ctx}/attence/personSys.do';
						}else{
							window.parent.location.href='${ctx}/attence/getAttenceList.do';
						}
					}else{
						alert("保存失败！");
					}
				}
			});
		}
	}
	function cancelBtnClick(){
		window.parent.$.colorbox.close();
	}
	
	function toChange(val,index){//备注下拉菜单change事件
		var id = val.split("#")[0];
		if(selectValueArr.length>0){
			var valTemp = '';
			var k=-1;
			for ( var i = 0; i < selectValueArr.length; i++) {
				if(selectValueArr[i].split("#")[0]==id){
					valTemp = selectValueArr[i];
					k=i;
					break;
				}
			}
			if(valTemp!=null && valTemp!='' && k>=0){
				selectValueArr[k]=val;
			}else{
				selectValueArr.push(val);
			}
			
		}else{
			selectValueArr.push(val);
		}
		
		//如果选择外出和请假，显示选择面板
		var code = val.split("#")[1];
		if(code==1 || code==2 || code==4 || code==5 || code==6 || code==7 || code==8 || code==9){
			$(".remark_span"+index).show();
			$("#sel_btn"+index+"_1").addClass('ch_cls');
			$("#sel_btn"+index+"_2").removeClass('ch_cls');
			$("#sel_btn"+index+"_3").removeClass('ch_cls');
			
			if(code==1){
				$("#morningAttenceT"+index).html("09:30:00");
				$("#afternoonAttenceT"+index).html("18:30:00");
			}
			if(code==2 || code==4 || code==5 || code==6 || code==7 || code==8 || code==9){
				$("#morningAttenceT"+index).html("");
				$("#afternoonAttenceT"+index).html("");
			}
		}else{
			$("#sel_btn"+index+"_1").removeClass('ch_cls');
			$("#sel_btn"+index+"_2").removeClass('ch_cls');
			$("#sel_btn"+index+"_3").removeClass('ch_cls');
			$(".remark_span"+index).hide();
			$("#morningAttenceT"+index).html("");
			$("#afternoonAttenceT"+index).html("");
		}
		
	}
	
</script>

<script type="text/javascript">
	//当单击全天、上午、下午按钮时触发
	 function changeSelBtn(index, idTemp, order){
	 	var subValue='';
	 	
		if(index==1){//全天
			$("#"+idTemp+"1").addClass('ch_cls');
			$("#"+idTemp+"2").removeClass('ch_cls');
			$("#"+idTemp+"3").removeClass('ch_cls');
			
			subValue = $("#"+idTemp+"1").attr('value');
		}else if(index==2){//上午
			$("#"+idTemp+"1").removeClass('ch_cls');
			$("#"+idTemp+"2").addClass('ch_cls');
			$("#"+idTemp+"3").removeClass('ch_cls');
			
			subValue = $("#"+idTemp+"2").attr('value');
		}else if(index==3){//下午
			$("#"+idTemp+"1").removeClass('ch_cls');
			$("#"+idTemp+"2").removeClass('ch_cls');
			$("#"+idTemp+"3").addClass('ch_cls');
			
			subValue = $("#"+idTemp+"3").attr('value');
		}
		var remark_val = $("#remark"+order).val();//单击时获取对应下拉列表选中的值
		var newSubValue = subValue.split("#")[0] +"#"+ remark_val.split("#")[1] + subValue.split("#")[1];//单击后重新组合符合格式（格式为：id#对应按钮标志）的值 
		
		//debugger;
		if(selectValueArr.length>0){
			/*
			 *如果selectValueArr.length=0,则直接将newSubVlaue的值放入selectValueArr数组中；
			 *如果selectValueArr.length>0,则判断newSubValue中id对应的值在selectValueArr数组中是否存在，若存在则替换，不存在则直接放入
			*/
			var valTemp = '';
			var k=-1;
			var count=0;
			for ( var i = 0; i < selectValueArr.length; i++) {
				if(selectValueArr[i].split("#")[0]==subValue.split("#")[0]){
					var temp = (selectValueArr[i]).split("#")[1];
					
					if(temp.substring(0,1)=='1'){
						if(index==1){//全天
							$("#morningAttenceT"+order).html("09:30:00");
							$("#afternoonAttenceT"+order).html("18:30:00");
						}else if(index==2){//上午
							$("#morningAttenceT"+order).html("09:30:00");
							$("#afternoonAttenceT"+order).html("");
						}else if(index==3){//下午
							$("#morningAttenceT"+order).html("");
							$("#afternoonAttenceT"+order).html("18:30:00");
						}
					}else{
						$("#morningAttenceT"+order).html("");
						$("#afternoonAttenceT"+order).html("");
					}
					//单击事件后设置selectValueArr[i]值得格式为:id + # + dic.code + all/moring/afternoon
					selectValueArr[i]=selectValueArr[i].split("#")[0]+"#"+temp.substring(0,1)+subValue.split("#")[1];
					break;
				}else{
					count++;
				}
			}
			if(count==selectValueArr.length){//如果点击按钮之前没有触发下拉列表change事件，则直接将点击的按钮值放在数组里
				selectValueArr.push(newSubValue);
				if(newSubValue.split("#")[1]=='1' || newSubValue.split("#")[1]=='1all'){
					$("#morningAttenceT"+order).html("09:30:00");
					$("#afternoonAttenceT"+order).html("18:30:00");
				}else if(newSubValue.split("#")[1]=='1moring'){
					$("#morningAttenceT"+order).html("09:30:00");
					$("#afternoonAttenceT"+order).html("");
				}else if(newSubValue.split("#")[1]=='1afternoon'){
					$("#morningAttenceT"+order).html("");
					$("#afternoonAttenceT"+order).html("18:30:00");
				}
			}
		}else{
			selectValueArr.push(newSubValue);
			if(newSubValue.split("#")[1]=='1' || newSubValue.split("#")[1]=='1all'){
				$("#morningAttenceT"+order).html("09:30:00");
				$("#afternoonAttenceT"+order).html("18:30:00");
			}else if(newSubValue.split("#")[1]=='1moring'){
				$("#morningAttenceT"+order).html("09:30:00");
				$("#afternoonAttenceT"+order).html("");
			}else if(newSubValue.split("#")[1]=='1afternoon'){
				$("#morningAttenceT"+order).html("");
				$("#afternoonAttenceT"+order).html("18:30:00");
			}
			
		}
		
	 }
</script>
</head>
<body>
<form id="queryFormId" action="" method="post">
<div class="TAB_right" style="height:400px;overflow: auto;">
<div class="infor_base">
<!--   	<div class="role_side" style="height:400px;overflow: auto;">
      	<form id="queryFormId" action="" method="post">
      	<table id="tableID" width="100%" cellpadding="0" cellspacing="0"> -->
      	<input type="hidden" id="flag" name="flag" value="${flag }">
<div  class="btn_operate"></div>
<div class="container_12" style="margin-left:3px;margin-right:3px;">
  	<div class="grid_8">
      	<table class="fancyTable" id="tableID" width="100%" cellpadding="0" cellspacing="0" style="table-layout:fixed;">
          	<thead>
	          	<!-- <tr style="height:35px;background-color:#EBF2F6;"> -->
	          	<tr>
	              	<th width="30px">序号</th>
	              	<th>姓名</th>
	              	<th>考勤日期</th>
	              	<th>星期</th>
	              	<th>日期类型</th>
	              	<!-- <th>考勤制度</th> -->
	              	<th>上午考勤</th>
	              	<th>下午考勤</th>
	              	<th>上午修改考勤</th>
	              	<th>下午修改考勤</th>
	              	<th width="240px">备注</th>
	          	</tr>
          	</thead>
          	<tbody id="tableID2">
          		<c:forEach items="${attenceDetailsList}" var="attenceDetails" varStatus="status">
          			
			     	<tr>
			     		<td style="text-align: center;">${status.index + 1}</td>
			     		<td>${attenceDetails.username}</td>
			     		<td>${attenceDetails.attenceDate}</td>
			     		<td>${attenceDetails.weekName}</td>
			     		<td>
			     			<font <c:if test="${attenceDetails.isException=='1'}">color="red"</c:if>>${attenceDetails.dateTypeName}</font>
			     		</td>
			     		<%-- <td>${attenceDetails.attenceType}</td> --%>
			     		<td>${attenceDetails.morningAttence}</td>
			     		<td>${attenceDetails.afternoonAttence}</td>
			     		<td id="morningAttenceT${status.index + 1}"></td>
			     		<td id="afternoonAttenceT${status.index + 1}"></td>
			     		<td id="other_td" style="text-align: left;">
			     			<select name="remark" id="remark${status.index + 1}" class="sel" style="width:65px" <c:if test="${attenceDetails.dateType=='2'}">disabled="disabled" </c:if> onchange="toChange(this.value,'${status.index + 1}');">
                                <option value="${attenceDetails.id}#0">请选择</option>
			                   	<c:forEach items="${dictionaryLlist}" var="dic" varStatus="status2">
				                   	<c:if test="${dic.type_code=='11'}">
				                   		<c:choose>
				                   			<c:when test="${attenceDetails.remark!=null && fn:substring(attenceDetails.remark, 0, 1)=='2' }">
							                	<option value="${attenceDetails.id}#${dic.code}" <c:if test="${dic.code==fn:substring(attenceDetails.remark, 0, 1) }">selected</c:if>>${dic.name }</option>
				                   			</c:when>
				                   			<c:otherwise>
				                   				<c:if test="${dic.code!='2'}">
				                   					<option value="${attenceDetails.id}#${dic.code}" <c:if test="${dic.code==fn:substring(attenceDetails.remark, 0, 1) }">selected</c:if>>${dic.name }</option>
				                   				</c:if>
				                   			</c:otherwise>
				                   		</c:choose>
				                    </c:if>			
			                    </c:forEach>
                         	</select>
                         	<span class="remark_span${status.index + 1}" <c:if test="${attenceDetails.remark==null || attenceDetails.remark=='' || attenceDetails.remark=='0' || fn:substring(attenceDetails.remark, 0, 1)=='3' }">style="display:none"</c:if> >
                         		<c:choose>
                         			<c:when test="${attenceDetails.flag=='all' }">
                         				<a class="sel_btn ch_cls" id="sel_btn${status.index + 1}_1" href="#" onclick="changeSelBtn(1, 'sel_btn${status.index + 1}_', '${status.index + 1}')" value="${attenceDetails.id}#all">全天</a>
										<a class="sel_btn" id="sel_btn${status.index + 1}_2" href="#" onclick="changeSelBtn(2, 'sel_btn${status.index + 1}_', '${status.index + 1}')" value="${attenceDetails.id}#moring">上午</a>
										<a class="sel_btn" id="sel_btn${status.index + 1}_3" href="#" onclick="changeSelBtn(3, 'sel_btn${status.index + 1}_', '${status.index + 1}')" value="${attenceDetails.id}#afternoon">下午</a>
                         			</c:when>
                         			<c:when test="${attenceDetails.flag=='moring' }">
                         				<a class="sel_btn" id="sel_btn${status.index + 1}_1" href="#" onclick="changeSelBtn(1, 'sel_btn${status.index + 1}_', '${status.index + 1}')" value="${attenceDetails.id}#all">全天</a>
										<a class="sel_btn ch_cls" id="sel_btn${status.index + 1}_2" href="#" onclick="changeSelBtn(2, 'sel_btn${status.index + 1}_', '${status.index + 1}')" value="${attenceDetails.id}#moring">上午</a>
										<a class="sel_btn" id="sel_btn${status.index + 1}_3" href="#" onclick="changeSelBtn(3, 'sel_btn${status.index + 1}_', '${status.index + 1}')" value="${attenceDetails.id}#afternoon">下午</a>
                         			</c:when>
                         			<c:when test="${attenceDetails.flag=='afternoon' }">
                         				<a class="sel_btn" id="sel_btn${status.index + 1}_1" href="#" onclick="changeSelBtn(1, 'sel_btn${status.index + 1}_', '${status.index + 1}')" value="${attenceDetails.id}#all">全天</a>
										<a class="sel_btn" id="sel_btn${status.index + 1}_2" href="#" onclick="changeSelBtn(2, 'sel_btn${status.index + 1}_', '${status.index + 1}')" value="${attenceDetails.id}#moring">上午</a>
										<a class="sel_btn ch_cls" id="sel_btn${status.index + 1}_3" href="#" onclick="changeSelBtn(3, 'sel_btn${status.index + 1}_', '${status.index + 1}')" value="${attenceDetails.id}#afternoon">下午</a>
                         			</c:when>
                         			<c:otherwise>
		                         		<a class="sel_btn" id="sel_btn${status.index + 1}_1" href="#" onclick="changeSelBtn(1, 'sel_btn${status.index + 1}_', '${status.index + 1}')" value="${attenceDetails.id}#all">全天</a>
										<a class="sel_btn" id="sel_btn${status.index + 1}_2" href="#" onclick="changeSelBtn(2, 'sel_btn${status.index + 1}_', '${status.index + 1}')" value="${attenceDetails.id}#moring">上午</a>
										<a class="sel_btn" id="sel_btn${status.index + 1}_3" href="#" onclick="changeSelBtn(3, 'sel_btn${status.index + 1}_', '${status.index + 1}')" value="${attenceDetails.id}#afternoon">下午</a>
                         			</c:otherwise>
                         		</c:choose>
                         	</span>
			     		</td>
			     	</tr>
				 </c:forEach>
				 <tr>
          	</tbody>
      	</table>
	</div><br>
	</div>
	</div>
	
    </div>
    <hr>
    <table width="100%" cellpadding="0" cellspacing="0" style="margin-top: 10px;">
		<tr align="center">
            <td colspan="10">
            	<shiro:hasPermission name="saveBtn:permission">
            	<input class="form_now" type="button" onclick="saveBtnClick()" value="保存">&nbsp;&nbsp;&nbsp;&nbsp;
            	</shiro:hasPermission>
            	<input class="form_now" type="button" onclick="cancelBtnClick()" value="取消">
            </td>
         </tr>
   	</table>
    </form>	
</body>
</html>