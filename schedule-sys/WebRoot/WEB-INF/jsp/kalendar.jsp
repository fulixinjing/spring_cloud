<%@ page language="java" pageEncoding="UTF-8"%>
<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>
<html lang="en">
<head>
<meta http-equiv="Content-Type" content="text/html;charset=utf-8" />

 
  <title>Capricorncd Calendar-Price-jQuery</title>
  <link rel="stylesheet" href="${ctx}/css/kalendar/calendar-price-jquery.min.css">
  <style>
    body {
      margin: 0; padding: 0; font-family: "Microsoft YaHei UI";
    }
  </style>
</head>
<body>

<div class="container"></div>

<script src="${ctx}/js/kalendar/jquery-1.12.4.min.js"></script>
<script src="${ctx}/js/kalendar/calendar-price-jquery.min.js"></script>
<script src="${ctx}/js/artDialog4.1.7/artDialog.source.js?skin=aero"></script>
<script src="${ctx}/js/artDialog4.1.7/iframeTools.source.js"></script>
<!--<script src="../src/js/calendar-price-jquery.js"></script>-->

<script>
$(function () {

    var mockData = [
      /* {
        date: "2018-01-28",
        stock: 9000,
        buyNumMax: 50,
        buyNumMin: 1,
        count: 3
      } ,{
        date: "2017-12-12",
        stock: 900,
        buyNumMax: 50,
        buyNumMin: 1,
        price: "12.00",
        priceMarket: "100.00",
        priceSettlement: "90.00",
        priceRetail: "99.00"
      }  */
    ];
	var data = JSON.parse('${count}');
	
	for(var i = 0; i<data.length;i++){
		  mockData.push({
        date: data[i].startDate,
        stock: i*21,
        buyNumMax: "50",
        buyNumMin: "1",
        count : data[i].count
      });
	}
    $.CalendarPrice({
      el: '.container',
      startDate: '2017-08-02',
      endDate: '${endDate}',
      data: mockData,
      // 配置需要设置的字段名称
      config: [],
      // 配置在日历中要显示的字段
      show: [
        {
          key: 'count',
          name: '任务数量:'
        }
      ],
      // 自定义颜色
      style: {
        // 头部背景色
        headerBgColor: '#098cc2',
        // 头部文字颜色
        headerTextColor: '#fff',
        // 周一至周日背景色，及文字颜色
        weekBgColor: '#098cc2',
        weekTextColor: '#fff',
        // 周末背景色，及文字颜色
        weekendBgColor: '#098cc2',
        weekendTextColor: '#fff',
        // 有效日期颜色
        validDateTextColor: '#333',
        validDateBgColor: '#fff',
        validDateBorderColor: '#eee',
        // Hover
        validDateHoverBgColor: '#098cc2',
        validDateHoverTextColor: '#fff',
        // 无效日期颜色
        invalidDateTextColor: '#ccc',
        invalidDateBgColor: '#fff',
        invalidDateBorderColor: '#eee',
        // 底部背景颜色
        footerBgColor: '#fff'
      }
      // 点击有效的某一触发的回调函数
      // 注意：配置了此参数，设置窗口无效，即不能针对日期做参数设置
      // 返回每天的数据
//        everyday: function (dayData) {
//            console.log('点击某日，返回当天的数据');
//            console.log(dayData);
//        },
      // 隐藏底部按钮（重置、确定、取消），前台使用该插件时，则需要隐藏底部按钮
//        hideFooterButton: true
    });

  });

  function randNum(max) {
    return Math.round(Math.random() * max);
  }

  function fd(n) {
    n = n.toString();
    return n[1] ? n : '0' + n;
  }
  function add(date){
	  art.dialog.open("${ctx}/schedule/toAdd?date="+date, {
	         id: 'flag',
	         title: '新建任务',
	         lock:true,
	         width:500,
	     	 height:300
	     });
  }

</script>

</body>
</html>
