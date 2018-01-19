<%@ page language="java" pageEncoding="UTF-8"%>
<style>
	#jumpToPage{
		height: 19px ! important;
	}
</style>
<script>
	var totalPage = '${page.pageTotal}';
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


	
</script>
	<c:choose>
    		<c:when test="${page.pageTotal == 0}">暂时没有记录</c:when>
    		<c:otherwise>
    			共${page.recordTotal}条数据，每页
				<select id="maxResultSelect"   onChange="topagePagination(1,this.value);">
					<option value="5" <c:if test="${page.pageSize == 5}"> selected="selected" </c:if>>5</option>
					<option value="10" <c:if test="${page.pageSize == 10}"> selected="selected" </c:if>>10</option>
					<option value="20" <c:if test="${page.pageSize == 20}"> selected="selected" </c:if>>20</option>
					<option value="30" <c:if test="${page.pageSize == 30}"> selected="selected" </c:if>>30</option>
					<option value="40" <c:if test="${page.pageSize == 40}"> selected="selected" </c:if>>40</option>
					<option value="50" <c:if test="${page.pageSize == 50}"> selected="selected" </c:if>>50</option>
				</select> 条数据，<font font-weight="bolder">${page.currentPage}/${page.pageTotal}</font> &nbsp;
				<c:if test="${page.currentPage != 1 && page.pageTotal > 0}">
				   <a href="javascript:topagePagination(1,$('#maxResultSelect').val())" class="a03">首页</a> 
				   <a href="javascript:topagePagination('${page.currentPage-1}',$('#maxResultSelect').val())" class="a03">上一页</a>
				</c:if>
				<c:if test="${page.currentPage != page.pageTotal}">
				    <a href="javascript:topagePagination('${page.currentPage+1}',$('#maxResultSelect').val())" class="a03">下一页</a>
				    <a href="javascript:topagePagination('${page.pageTotal}',$('#maxResultSelect').val())" class="a03">尾页</a> 
				</c:if>
				<c:if test="${page.pageTotal != 1}"> 
					<input id="jumpToPage" style="width:35px;text-align:center; "/>
					<input id="jumpButton" onclick="javascript:jumpTo()"  type="button"  value="跳转"/>&nbsp;&nbsp;
				</c:if>
    		</c:otherwise>
    	</c:choose>
	
