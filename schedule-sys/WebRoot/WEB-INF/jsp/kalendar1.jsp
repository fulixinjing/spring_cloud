<%@ page language="java" pageEncoding="UTF-8"%>
<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.0 Transitional//EN">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html;charset=utf-8" />
<title>任务管理系统</title>
<link rel="stylesheet" type="text/css" href="${ctx}/css/common.css" />
<link rel="stylesheet" type="text/css" href="${ctx}/css/right1.css" />
<link rel="stylesheet" type="text/css" href="${ctx}/css/colorbox.css" />
<link rel="stylesheet" type="text/css" href="${ctx}/css/pop.css" />
<link rel="stylesheet" type="text/css" href="${ctx}/css/pop.css" />
<link rel="stylesheet" type="text/css" href="${ctx}/css/kalendar/calendar-price-jquery.min.css" />

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
<script src="${ctx}/js/artDialog4.1.7/artDialog.source.js?skin=aero"></script>
<script src="${ctx}/js/artDialog4.1.7/iframeTools.source.js"></script>
<!-- jquery-ui start -->
<link rel="stylesheet" href="${ctx}/js/jquery_ui/jquery-ui.css">
<script src="${ctx}/js/jquery_ui/jquery-ui.js"></script>
 <style>
    body {
      margin: 0; padding: 0; font-family: "Microsoft YaHei UI";
    }
  </style>
<body>
<div class="container"></div>

 <script src="${ctx}/js/kalendar/jquery-1.12.4.min.js"></script>
 <script src="${ctx}/js/kalendar/calendar-price-jquery.min.js"></script>

<script>
  $(function () {

    var mockData = [
      {
        date: "2018-01-24",
        stock: 9000,
        buyNumMax: 50,
        buyNumMin: 1,
        price: "0.12",
        priceMarket: "100.00",
        priceSettlement: "90.00",
        priceRetail: "99.00"
      },{
        date: "2017-12-12",
        stock: 900,
        buyNumMax: 50,
        buyNumMin: 1,
        price: "12.00",
        priceMarket: "100.00",
        priceSettlement: "90.00",
        priceRetail: "99.00"
      }
    ];
    $.CalendarPrice({
      el: '.container',
      startDate: '2018-1-02',
      endDate: '2018-1-27',
      data: mockData,
      // 配置需要设置的字段名称
      config: [
        {
          key: 'buyNumMax',
          name: '最多购买数'
        },
        {
          key: 'buyNumMin',
          name: '最少购买数'
        },
        {
          key: 'price',
          name: '分销售价'
        },
        {
          key: 'priceMarket',
          name: '景区挂牌价'
        },
        {
          key: 'priceSettlement',
          name: '分销结算价'
        },
        {
          key: 'priceRetail',
          name: '建议零售价'
        },
        {
          key: 'cashback',
          name: '返现'
        },
        {
          key: 'stock',
          name: '当天库存'
        }
      ],
      // 配置在日历中要显示的字段
      show: [
        {
          key: 'price',
          name: '分:￥'
        },
        {
          key: 'priceSettlement',
          name: '采:￥'
        },
        {
          key: 'stock',
          name: '库:'
        }
      ],
      callback: function (data) {
        console.log('callback ....');
        console.log(data);
      },
      cancel: function () {
        console.log('取消设置 ....');
        // 取消设置
        // 这里可以触发关闭设置窗口
        // ...
      },
      reset: function () {
        console.log('数据重置成功！');
      },
      error: function (err) {
        console.error(err.msg);
        alert(err.msg);
      },
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
        footerBgColor: '#fff',
        // 重置按钮颜色
        resetBtnBgColor: '#77c351',
        resetBtnTextColor: '#fff',
        resetBtnHoverBgColor: '#55b526',
        resetBtnHoverTextColor: '#fff',
        // 确定按钮
        confirmBtnBgColor: '#098cc2',
        confirmBtnTextColor: '#fff',
        confirmBtnHoverBgColor: '#00649a',
        confirmBtnHoverTextColor: '#fff',
        // 取消按钮
        cancelBtnBgColor: '#fff',
        cancelBtnBorderColor: '#bbb',
        cancelBtnTextColor: '#999',
        cancelBtnHoverBgColor: '#fff',
        cancelBtnHoverBorderColor: '#bbb',
        cancelBtnHoverTextColor: '#666'
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

</script>
</body>

</html>