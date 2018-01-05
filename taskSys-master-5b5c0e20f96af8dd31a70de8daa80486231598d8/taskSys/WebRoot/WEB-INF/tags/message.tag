<%@ tag language="java" pageEncoding="UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>
<%@ attribute name="content" type="java.lang.String" required="true" description="消息内容"%>
<%@ attribute name="type" type="java.lang.String" description="消息类型：info、success、warning、error、loading"%>
<c:if test="${not empty content}">
	<c:if test="${not empty type && type == 'success'}">
		<div id="success_pop" >
			<div class="cboxOverlay_alert"></div>
		    <div class="success_pop">
		        <div class="title_Bar">系统提示</div>
		        <div class="prompt_Bar">
		            <div class="prompt_Bar_left"><img src="${ctx}/images/success_icon.png" /></div>
		            <div class="prompt_Bar_right">${content}</div>
		        </div>
		        <div class="btn_pop"><a class="Sure_btn" href="#" onclick="javascript:$(function(){$('#success_pop').hide();})">确定</a></div>
			</div>
		</div>
		
	</c:if>
	
	<c:if test="${not empty type && type == 'error'}">
		<div id="cancel_pop" >
			<div class="cboxOverlay_alert"></div>
		    <div class="cancel_pop">
		        <div class="title_Bar">系统提示</div>
		        <div class="prompt_Bar">
		            <div class="prompt_Bar_left"><img src="${ctx}/images/cancel_btn.png" /></div>
		            <div class="prompt_Bar_right">${content}</div>
		        </div>
		        <div class="btn_pop"><a class="Sure_btn" href="#" onclick="javascript:$(function(){$('#cancel_pop').hide();})">确定</a></div>
		    </div>
		</div>

			
	</c:if>
</c:if>