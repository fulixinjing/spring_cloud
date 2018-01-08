/*导航活动背景*/
$(function(){
	$("#GunDong ul li").mouseover(function(){
		var li_length=$(this).index();//判断鼠标是在第几个li-1上
		$(".T_bgs").animate({left:80*li_length},100);
	})
})
  
/*动态判断右侧iframe部分高度*/
$(function(){
	var window_height=$(window).height();
	$(".content_show").css({height:window_height-119})
})
  
/*导航滚动效果*/
var zhi=0;
$(function() {
	$(".btn_scroll span.prev").click(function() {
		zhi--;

		if (zhi < 0) {
			zhi = 0;
			return;
		}

		var shu = (zhi * 80);
		$(".v_concent_list li").animate({ left: "-" + shu + "px" }, 500);
		$("p").text(zhi);
	})

	$(".btn_scroll span.next").click(function() {
		zhi++;

		if (zhi > ($('#GunDong').find('li').length - 10)) {
			zhi = ($('#GunDong').find('li').length - 10);
			return;
		}

		var shu = (zhi * 80);
		$(".v_concent_list li").animate({ left: "-" + shu + "px" }, 500);
		$("p").text(zhi);
	})
})

/*左侧菜单展开效果*/
$(function(){
	$(".l_sidebar").click(function(){
		$(".l_sidebar ul").hide();
		$(this).find("ul").toggle();
		})
	})
	
//$(function(){
//		$("#GunDong ul li").click(function(){
//			var textadmin=$(this).text();
//			$("#NAV_ben h1 span").text(textadmin);
//		})
//	})
/*左侧下拉点击效果*/
$(function(){
	$(".l_sidebar ul li a").click(function(){
		$(".l_sidebar ul li a").removeClass("nav_hover");
		$(this).addClass("nav_hover");
	})
})