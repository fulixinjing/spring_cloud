<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/common/taglibs.jsp" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.0 Transitional//EN"> 
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<%@ include file="/common/meta.jsp"%>
<title>用户列表</title>
<link href="${ctx}/css/static/base-global.css" rel="stylesheet"
	type="text/css" />
<link href="${ctx}/css/static/common.css" rel="stylesheet"
	type="text/css" />
<link href="${ctx}/css/static/main.css?1=1" rel="stylesheet"
	type="text/css" />


<script type="text/javascript"
	src="${ctx}/js/static/jquery/jquery-1.7.2.min.js"></script>
<!-- 饼状图 -->
<script type="text/javascript" src="${ctx}/js/static/piechart/jsapi.js"></script>
<script type="text/javascript"
	src="${ctx}/js/static/piechart/corechart.js"></script>
<script type="text/javascript"
	src="${ctx}/js/static/piechart/jquery.gvChart-1.0.1.min.js"></script>
<script type="text/javascript">
    gvChartInit();
    $(document).ready(function () {
        $('#myTable1').gvChart({
            chartType: 'PieChart',
            gvSettings: {
                vAxis: { title: '延期' },
                hAxis: { title: '完成'  },
                width: 500,
                height: 290
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
</head>

<body scroll="no" style="margin-top: 0px; ">
	<div>
		<table id='myTable1'>
			<thead>
				<tr>
					<th></th>
					<th>${message1 }</th>
					<th>${message2 }</th>
					<th>${message3 }</th>
				</tr>
			</thead>

			<tbody>

				<tr>
					<td id="yccomTask"><span id="oo">${astj}</span>
					</td>
					<td id="ascomTask"><span id="tt">${tqtj}</span>
					</td>
					<td id="tqcomTask"><span id="yqtj">${yqtj}</span>
					</td>
				</tr>
			</tbody>
		</table>
	</div>

</body>

</html>
