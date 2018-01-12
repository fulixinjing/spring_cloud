<%@ page language="java" pageEncoding="UTF-8"%>
<style>
	#jumpToPage{
		height: 19px ! important;
	}
</style>
<script>
	var totalPage = '${pageView.totalpage}';
	function jumpTo(){
		if($("#jumpToPage").val() ==""){//校验是否为空
			alert('请输入要跳转的页数!');
			return;
		}else{
			var patrn=/^(?:[1-9]\d*|0)$/;
			if(!patrn.test($("#jumpToPage").val())){//校验是否是数字
				alert('请输入合法的页数!');
				return;
			}else{
				if($("#jumpToPage").val() < 1){//校验是否是小于1
					alert('请输入大于0的数字!');
					return;
				}
			}
		}
		
		if(($("#jumpToPage").val() - totalPage)>0){
			$("#jumpToPage").val(totalPage);
			topagePagination(totalPage,$("#maxResultSelect").val());
		}else{
			topagePagination($("#jumpToPage").val(),$("#maxResultSelect").val());
		}
	}
	$(function(){
		if(totalPage == 0){
		}else{
		var length =$("#maxResultSelect")[0].length;
		for(var i = 0 ;i < length;i++){
			if($("#maxResultSelect")[0][i].value ==  ${pageView.maxresult}){
				$("#maxResultSelect")[0][i].selected = true;
			}
		}
		}
	})

	
</script>
	<c:choose>
    		<c:when test="${pageView.totalpage == 0}">暂时没有记录</c:when>
    		<c:otherwise>
    			共${pageView.totalrecord}条数据，每页
				<select id="maxResultSelect"   onChange="topagePagination(1,this.value);">
					<option value="10" >10</option>
					<option value="20" >20</option>
					<option value="30" >30</option>
					<option value="40" >40</option>
					<option value="50" >50</option>
					<option value="80" >80</option>
					<option value="100" >100</option>
					<option value="150" >150</option>
					<option value="200" >200</option>
					<option value="300" >300</option>
					<option value="500" >500</option>
				</select> 条数据，<font font-weight="bolder">${pageView.currentpage}/${pageView.totalpage}</font> &nbsp;
				<c:if test="${pageView.currentpage != 1 && pageView.totalpage > 0}">
				   <a href="javascript:topagePagination(1,$('#maxResultSelect').val())" class="a03">首页</a> 
				   <a href="javascript:topagePagination('${pageView.currentpage-1}',$('#maxResultSelect').val())" class="a03">上一页</a>
				</c:if>
				<c:if test="${pageView.currentpage != pageView.totalpage}">
				    <a href="javascript:topagePagination('${pageView.currentpage+1}',$('#maxResultSelect').val())" class="a03">下一页</a>
				    <a href="javascript:topagePagination('${pageView.totalpage}',$('#maxResultSelect').val())" class="a03">尾页</a> 
				</c:if>
				<input id="jumpToPage" style="width:35px;text-align:center; "/> 
				<input id="jumpButton" onclick="javascript:jumpTo()"  type="button" value="跳转"/>&nbsp;&nbsp;
    		</c:otherwise>
    	</c:choose>
	
