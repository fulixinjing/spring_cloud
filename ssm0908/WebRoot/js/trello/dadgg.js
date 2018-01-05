$(function(){ 
	// var winwid=$(window).width();
 //    $("")
    
	$(".but_add").click(function(){
	    $(".but_add").hide();
	    $(".add_box").show();
        $(".add_h").focus();
	});

	$(".dele").click(function(){
    $(".but_add").show();
    $(".add_box").hide();
    });

    var mod="<div class='mod'>"
            +"<div class='mod_top'>"
            + "<h1 class='mod_h1'></h1>"
             + "<span class='mod_top_right'>...</span>"
            +"</div>"
              +"<div class='mod_middle' style='display:none;'>" 
                 +"<div class='haha'>" 
                   // +" <div class='s'>dwfdscs</div>" 
                   // +" <div class='s'>dwfdscs</div>" 
                   // +" <div class='s'>dwfdscs</div>" 
                   // +" <div class='s'>dwfdscs</div>" 
                   //  +" <div class='s'>dwfd是打发发了多少士大夫撒旦法塑料袋发你马拉松的烦恼asdfn.dsascs</div>" 
                   // +" <div class='s'>dwf收到断点多多多多多多多多多多多多多多多多多多多多多多多多多dscs</div>" 
                   // +" <div class='s'>dwfd 的所发生的发顺丰scs</div>" 
                   // +" <div class='s'>dwfds的点点滴滴多多多多多多多多多多多多多多多多多多多多多多多多多多多多多多多多多多多多多多多多多多多多多多多多多多多多多多多多多多多cs</div>" 
                    +"<div class='znull'></div>"
                  +"</div>"
               +"</div>"
            +"<div class='mod_bottom'>查看更多...</div>"
          +"</div>"

    $('.bc').click(function(){
    event.preventDefault();
    event.stopPropagation();
    var val=$(".add_h").val();
    if(val==""){
        return false;
    }else{
        $(".null").before(mod);
        $(".cz").children().eq(-2).children().children(".mod_h1").html(val);
        $(".add_h").val("");
        $('.jq22').dad({
            draggable: '.mod_top',
   
         });
        // $(".cz").children().eq(-2).children(".mod_bottom").click(function (){  
        //     $(".cz").children().eq(-2).children(".mod_middle").show();
        //      $(".haha").dad();
        // });

        $(".mod_bottom").click(function(){
            var xts="<div class='s'><p class='yoyo'></p>1</div>"
                    +"<div class='s'><p class='yoyo'></p>2</div>"
                    +"<div class='s'><p class='yoyo'></p>3</div>"
                    +"<div class='s'><p class='yoyo'></p>4</div>"
                    +"<div class='s'><p class='yoyo'></p>5</div>"
             $(this).siblings(".mod_middle").show();
             $(this).siblings(".mod_middle").children(".haha").children(".znull").before(xts);


             $(".s").click(function(){
                $(".yy").show();
                $(".tk").show();
                
            });
             $(".yy, .tk_x").click(function(){
                $(".yy").hide();
                $(".tk").hide();
                
            });

             // $(this).removeClass("mod_bottom");
             // $(this).addClass("mod_bottom2");
            // $(this).html("<button class='bot_tj'>添加</button><span class='bot_dele'>×</span><span class='smadd'>...</span>");
            // $(this).siblings(".mod_middle").html("<textarea class='bjk'></textarea>");
            // $(this).siblings(".mod_middle").children(".bjk").focus();
            
            // $(".bot_dele").click(function(){
            // // $(this).siblings(".mod_middle").hide();
            // $(this).parent(".bjk").hide();
            //  });

            // $(".bot_tj").click(function(){
            //      event.preventDefault();
            //      event.stopPropagation();
            //     var texta=$(".bjk").val();
            //   if(texta==''){
            //     return false
            //   }else{
                
            //    var dt="<div class='haha'></div>"
            //    $(this).parent(".mod_middle").children(".bjk").before(dt);
                

            //   }

            // })

            $(".haha").dad({
                draggable: '.yoyo',
            });

            

        });

         


        
        
        // $(".bot_tj").click(function(){
        //     if()
        // })








        }
        
    });



  





});