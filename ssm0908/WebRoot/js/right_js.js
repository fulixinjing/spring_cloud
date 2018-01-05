var ant=1;
	function fc(antio){
		$("#fc"+ant).removeClass("fc-state-active");
		$("#fc"+antio).addClass("fc-state-active");
		$("#fc_"+ant).hide();
		$("#fc_"+antio).show();
		ant=antio;
	}
	$(function(){
		$(".organ_content .infor_list tr").click(function(){
			$(".organ_content .infor_list tr").removeClass("selected");
			$(this).addClass("selected")
		})
	})
	$(function(){
		$(".role_side tr").click(function(){
			$(".role_side tr").removeClass("selected");
			$(this).addClass("selected")
		})
	})

/**/
/*动态判断右侧iframe部分高度*/
$(function(){	
	var window_height=$(window).height();//判断显示区域高度
	var window_width=$(window).width();//判断显示区域宽度
	var body_height=$(document).height();
	var right_content_height=$("#right_content").height();
	var content_show_width=$(".content_show").width();
	var retrieve_len=$(".retrieve_list").height();//判断检索高度
	$(".TAB_right").css({height:right_content_height});
	$(".TAB_right").css({width:content_show_width});
	$(".TAB_right_content").css({height:window_height-40});
	$("#staff_info").css({height:window_height-49});
	$("#tree_info").css({height:window_height-49});
	$(".grid_8").css({height:window_height-retrieve_len-115});
	$(".TAB_rightside").width(window_width-172);
	$(".TAB_rightside").css({height:window_height-60});
	$(".TAB_leftside").css({height:window_height-60});
	$(".TAB_rightcontrast").width(window_width-602);
	//$(".retrieve_list").css({width:window_width-20})
	
	
	
	
	var header_pop_len=$(".header_pop_content").height();//判断检索高度
	var search_Bar_len=$(".search_Bar").height();//判断检索高度
	$(".header_pop_content").css({height:window_height});
	$(".search_Bar").css({height:search_Bar_len});
	//$(".Table_Bar").css({height:header_pop_len-search_Bar_len-41});
	$("#myTable02").css("width","100%");
	$("#mybigTable").css("width","100%");
})

/*操作按钮弹出*/
$(document).ready(function(){
	
	var tr_length = $("#myTable02 tr").length - 1;//获得tr数量,减去头部的tr
	
   $(".operate_btn").mouseover(function() //给xiala添加点击事件
   {
	   var tr_zhi = $(this).parent().parent().parent().index()+1;//获得鼠标放在了第几个tr上面
	   
	    //以下是适用于子导航本身（移动子导航上下）
		$(this).parent().find(".pop_layer").show(); //slideDown：向下滑动（显示）被选元素
		
		
	   
	   if(tr_zhi + 1 >= tr_length)
	   {
		   $(this).parent().find(".pop_layer").css("top","-70px"); //slideDown：向下滑动（显示）被选
	   }
		if(tr_zhi<3){
			 $(this).parent().find(".pop_layer").css("top","10px");
		}   
		$(this).parent().hover
		(
			function(){},//鼠标移到上面时不出发事件
			function(){  //移出时激发隐藏事件
					  $(this).parent().find(".pop_layer").hide(); //当鼠标离开选中菜单后，slideUp：向上滑动（隐藏）被选元素
					  }
		 );
   });
  });
 
/* 日历控件css*/
$(function(){
	$("#d241").css({"width":"120px","border":"1px solid #ccc","padding":"12px"});
	$("#d4311").css({"width":"100px","border":"1px solid #ccc","padding":"12px","margin-right":"0px"})
	$("#d4312").css({"width":"100px","border":"1px solid #ccc","padding":"12px"})
})


/*弹出层*/
$(function(){
		var window_height=$(window).height();
		var search_height=$(".search-lay").height();
		$(".layer-wrapper").css({height:window_height});
		$(".grid-header").css({height:window_height-search_height-10});
	})
$(function(){
		$("#success_opacite").click(function(){
			$("#success_pop").show();
		})
		$("#cancel_opacite").click(function(){
			$("#cancel_pop").show();
		})
		$(".Cancel_btn").click(function(){
			$("#success_pop").hide();
		});
		$(".Sure_btn").click(function(){
			$("#success_pop").hide();
		});
		$(".Cancel_btn").click(function(){
			$("#cancel_pop").hide();
		})
		$(".Sure_btn").click(function(){
			$("#cancel_pop").hide();
		});
	})
	
	/*文档树高度判断*/
	$(function(){
		var window_height=$(window).height();
		$(".jur_tree").css({height:window_height-33-14});
		$(".menu_tree").css({height:window_height-33-14});
	})
	
	$(function(){	
	
	var maskLayerOperation = (function(){
		return function(){
			function hm_widget(){}
		hm_widget.prototype = {

			show : function(){
				if(!!$('#layer20148111625')[0]){
					$('#layer20148111625').remove();
				}

				var doc = $(window),
					_width = 62,
					_top = doc.height()/2-_width,
					_left = doc.width()/2-_width;
				var layer = '<div id="layer20148111625" style="position:absolute;top:0px;left:0px;width:100%;height:100%;z-index:9999"><div id="layer20148111625a" style="position:absolute;top:0px;left:0px;width:100%;height:100%;z-index:10;background-color:#000;filter:alpha(opacity=30);opacity:0.3;"></div><div id="layer20148111625b" style="position:absolute;top:'+_top+'px;left:'+_left+'px;z-index:11"><img src="../images/20132221211655234668.gif"></div></div>';
				$(document.body).append(layer);
			},
			hide : function(){
				if(!!$('#layer20148111625')[0]){
					$('#layer20148111625').remove();
				}
			}
		}
		return new hm_widget();
	  }
	})();
	window.maskLayer = maskLayerOperation();
	
})

  function batchconfirm(str){
    	if(!confirm(str)){
    		return false;
    	}
    	return true;
    }
