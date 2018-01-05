

function g_alert(type,cont,url,ctx){
	//html	
	if(ctx==null||ctx==undefined||ctx==""){
		ctx=url;	
		url="";
	}
	//var html = '<div id="g_all"></div><div id="g_box" style="z-index:89; position:fixed"><div id="g_title">提示</div><div id="g_cont"><img width="70px" height="70px"><span id="g_msg">'+cont+'</span></div><div id="g_buttom"><div id="g_button"><a class="butt" id="ok">确定</a> <a class="butt" id="false">取消</a></div></div></div>';
	//text-align: center;
	var html = '<div id="g_all"></div><div id="g_box" style="z-index:89;" class="success_pop"><div id="g_title" class="title_Bar">提示</div><div id="g_cont" class="prompt_Bar"> <div class="prompt_Bar_left"><img ></div> <div class="prompt_Bar_right" style="line-height: 55px;"><span id="g_msg" style="padding-left: 50px;">'+cont+'</span></div></div><div id="g_buttom" class="btn_pop" ><div id="g_button" class="btn_pop"><a class="Sure_btn" id="ok">确定</a> <a class="Sure_btn" id="false">取消</a></div></div></div>';
	$('body').append(html);

	//css
	var css = "<style id='g_css'>" +
	"";
		//	"#g_title{height:40px; background:#fff;border-radius:5px 5px 0 0; border-bottom:1px solid #eef0f1;line-height:30px;padding-left:5px; font-size:18px; font-weight:200; color:#535e66}";
	//css += "#g_cont{height:80px; background:#fff;padding-top:10px; text-align:center;}";
	css += "#g_all{width:100%; height:100%; z-index:88; position:fixed;filter:Alpha(opacity=20); background:#666666;top:0;left:0;opacity: 0.6}";
	//css += "#g_msg{position:relative; top:-20px; font-size:14px;margin-left:30px;}";
	//css += "#g_buttom{height:50px; border-top:1px solid #eef0f1; border-radius:0px 0px 5px 5px; background:#fff; line-height:50px;}";
	//css += "#g_button{width:200px; height:100%; margin-right:10px; float:right;}";
	//css += ".butt{display:block; margin-top:12px;cursor:pointer; float:left;width:65px;height:25px;line-height:25px;text-align:center;color:#FFFFFF;border-radius:5px;}"
	//css += "#ok{background:#0095d9; color:#FFFFFF; float:right;}";
	//css += "#false{background:#546a79; color:#FFFFFF; float:left;}";
	css += "#false{background:#546a79; color:#FFFFFF; }";
	css += "#g_box{width:340px;}" +
	
		
	
	$('head').append(css);
	
	//类型为alert
	if(type == 'success'){
		//$('#g_cont img').attr('src','./images/success_icon.png');
		$('#g_cont img').attr('src',ctx+'/js/alert/ok1.png');
		$('#false').hide();
	}

	if(type == 'error'){
		$('#g_cont img').attr('src',ctx+'/js/alert/fail1.png');
		$('#false').hide();
	}

	//类型为confirm
	if(type == 'warn'){
		$('#g_cont img').attr('src',ctx+'/js/alert/warn1.jpg');
		$('#false').hide();
	}

	//类型为confirm
	if(type == 'confirm'){
		$('#g_cont img').attr('src',ctx+'/js/alert/confirm1.jpg');
	}

	//点击OK
	$('#ok').click(function(){
		$('#g_all').remove();
		$('#g_box').remove();
		$('#g_css').remove();
		if(url){
			window.location = url;
		}
		return true;
	});

	//点击false
	$('#false').click(function(){
		$('#g_all').remove();
		$('#g_box').remove();
		$('#g_css').remove();
		if(type != 'confirm'){
		if(url){
			window.location = url;
		}}
		return false;
	});


	//居中
	var _widht = document.documentElement.clientWidth; //屏幕宽
    var _height = document.documentElement.clientHeight; //屏幕高

    var boxWidth = $("#g_box").width();
    var boxHeight = $("#g_box").height();

     $("#g_box").css({ top: (_height - boxHeight) / 4 + "px", left: (_widht - boxWidth) / 2 + "px" });
  
}
