<%@ page language="java" contentType="text/html; charset=gb2312" pageEncoding="gb2312"%>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">    
<%@include file="/common/taglibs.jsp"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.0 Transitional//EN"> 
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
    <title>�������ϵͳ</title>
    <link rel="stylesheet" type="text/css" href="${ctx}/css/common.css" />
    <link rel="stylesheet" href="${ctx}/css/ztree/zTreeStyle.css" type="text/css">
    <link href="${ctx}/css/static/base-global.css" rel="stylesheet" type="text/css" />
    <link href="${ctx}/css/static/common.css" rel="stylesheet" type="text/css" />
    <link href="${ctx}/css/static/main.css" rel="stylesheet" type="text/css" />
    
    <link rel="stylesheet" type="text/css" href="${ctx}/css/pop.css" />
    <link rel="stylesheet" type="text/css" href="${ctx}/css/right1.css" />
    <script type="text/javascript" src="${ctx}/js/jquery.min.js"></script>
    <script type="text/javascript" src="${ctx}/js/right_js.js"></script>
    <script type="text/javascript" src="${ctx}/js/ztree/jquery-1.4.4.min.js"></script>
    <script type="text/javascript" src="${ctx}/js/ztree/jquery.ztree.core-3.5.js"></script>
    <script src="${ctx}/js/trello/jquery-1.11.3.min.js"></script>
    <script src="${ctx}/js/trello/jquery.dad.min.js"></script>
    <script src="${ctx}/js/trello/dadgg.js"></script>
    <link rel="stylesheet" href="${ctx}/css/trello/jquery.dad.css">
    <link rel="stylesheet" href="${ctx}/css/trello/index.css">
    <link rel="stylesheet" type="text/css" href="${ctx}/components/colorbox/colorbox.css"/>
	<script language="javascript" src="${ctx}/components/colorbox/jquery.colorbox.js"></script>
	<link rel="stylesheet" href="${ctx}/js/jquery_ui/jquery-ui.css">
	<script src="${ctx}/js/jquery_ui/jquery-ui.js"></script>

</head>
<script type="text/javascript">
$(function(){
    var singlewidth=$('#getwidth .l_xin').outerWidth();
    var num=$('#getwidth .l_xin').length;
    var singlewidth=parseInt(singlewidth);
    var num=parseInt(num);
    var sum=singlewidth*num;
    console.log(singlewidth,num,sum);
    $('#getwidth').css({"width":sum+"px"});
    
})



$(document).ready(function(){
    // ������ұ߸߶ȱ仯���仯
     
         //tab��ǩ
         var n = $("#flag").val();
         if(n!=null && n!=''){
            for(var i=1;i<=5;i++){
                g('tb3_'+i).className='fl';
                g('tbc3_0'+i).className='hide';
              }
              g('tbc3_0'+n).className='';
            g('tb3_'+n).className='a0';
         }
 });
 
function g(o){return document.getElementById(o);}
function HoverLi3(n,flag){
    //�����N����ǩ,�ͽ�i<=N;
    
var code = document.getElementById("code").value;
	
	if(flag=="3"){
		location.href="${ctx}/trello/projectstage.do?flag="+flag+"&code="+code;
	}
	else{
		location.href="${ctx}/trello/projectstage.do?flag="+flag+"&code="+code;
	}
}

function taskselect(exec_name){
    $('#covered').hide();
    var url = "${ctx}/admin/listTask.do?exec_name="+exec_name;
    $.colorbox({
        href:url,
        iframe:true,
        width:"1100",
        height:"400"
    });
}
function more(m){
	$("#morestage").val(m);
	$("#mform").submit();  
}

function info(taskname,taskcontent,create_name,exec_name,createtime,jxzDate,expectendtime,taskworktime){
    document.getElementById('taskname').innerHTML=taskname;
    $("#taskcontent").val(taskcontent);
    $("#create_name").val(create_name);
    $("#exec_name").val(exec_name);
    $("#createtime").val(createtime);
    $("#jxzDate").val(jxzDate);
    $("#expectendtime").val(expectendtime);
    $("#taskworktime").val(taskworktime);
    $('#covered').show();
    $('.tan').show();
 } 


      function show_sub(code){
	     window.location.href="${ctx}/trello/projectstage.do?code=" + code;
	     $("#code").val(code);
	     
	  }
      
      /*var cos = document.getElementById('cos');
      var tan = document.getElementById('tan');
      cos.onclick = function(){
          tan.style.display = 'none';
      }
  */
  //�رյ�ǰ���Ԫ�صĸ�����Ҳ���ǵ���
  $(function(){
      $("#cos").click(function(){ 

      $(this).closest('#tan').hide(); 
      $('#covered').hide();

  });
      $('.bttn').click(function(){
          $(this).closest('#tan').hide();
          $('#covered').hide();
      })
      
  });
	//������
	  function search(){
	      var seartxt=$("#searid").val();
// 	      var selectedIndex =$("#sel").selectedIndex;
// 	      var code = $("#sel").options[selectedIndex ].value;
	      if(seartxt!=null||seartxt!=''||seartxt != undefined ){
	    	 // window.location.href="${ctx}/trello/projectstage.do?proName=" + seartxt+"&fcode=1&flag="+1 + "&code=" + code;
	    	  window.location.href="${ctx}/trello/projectstage.do?proName=" + seartxt+"&fcode=1&flag="+1;
	      }
	      //show_sub(this.value);
	  }
	   $(function(){

	        //��ȡ����l_xin�Ŀ��
	        var singlewidth=$('#getwidth .l_xin').outerWidth();

	        //�ڻ�����ĸ�������
	        var num=$('#getwidth .l_xin').length;

	        //���ַ���ת��Ϊ����
	        var singlewidth=parseInt(singlewidth);

	        //���ĳ���Ҳת��Ϊ����
	        var num=parseInt(num);

	        //����һ�������ĳ���*������������ܳ���
	        var sum=singlewidth*num;
	        
	        console.log(singlewidth,num,sum);

	        //������div������ʽ�����ܳ��ȣ�Ȼ��ͳ��ֹ������ˡ�
	        $('#getwidth').css({"width":sum+"px"});
	        
	    })

	    
</script>

<body style='height:auto;overflow:auto;'>
<form action="${ctx}/trello/projectstage.do" id="mform" name="mform">
     <input type="hidden" id="projectFalg" name="projectFalg" />
     <input type="hidden" id="flag" name="flag" value="${flag}"/>
     <input type="hidden" id="code" name="code" value="${code}"/>
     <input type="hidden" id="page1" name="page1" value="${page1}"/>
     <input type="hidden" id="page2" name="page2" value="${page2}"/>
     <input type="hidden" id="page3" name="page3" value="${page3}"/>
     <input type="hidden" id="page4" name="page4" value="${page4}"/>
     <input type="hidden" id="page5" name="page5" value="${page5}"/>
     <input type="hidden" id="page6" name="page6" value="${page6}"/>
     <input type="hidden" id="page7" name="page7" value="${page7}"/>
     <input type="hidden" id="page8" name="page8" value="${page8}"/>
     <input type="hidden" id="page9" name="page9" value="${page9}"/>
     <input type="hidden" id="morestage" name="morestage" value="${morestage}"/>
    
    
    <div class="r_sel">
      <div class="sel_l fl">
	      <select id="sel" name=sel class="sel pl10 pr10" onchange="show_sub(this.options[this.options.selectedIndex].value)">
	           <option>��Ŀ�б�</option>
	           <c:forEach items="${departmentList}" var="dept">
	             <option name="${dept.code}" value="${dept.code}" <c:if test="${dept.code==code }">selected</c:if>>${dept.proName}</option>
	           </c:forEach>
	         </select>
      </div>
<!--       <div id="searchid"><input class="fr serc pl10" type="text" id="searid"/></div> -->
        <div class="fr" style="position: relative;" id="searchid"> 
			<input class="serc pl10" type="text" id="searid"/> 
			<img src="${ctx}/images/serc.png" onclick="search();" style="position: absolute; top: 7px;right: 11px; cursor: pointer;"> 
		</div>
      
      
    </div>


<div class="fl wd918">
     <div class="r_tab" id="tab">
         <ul>
             <li id="tb3_1" onclick="x:HoverLi3(1,1);" class="a0">��Ŀ�����׶�</li>
             <li id="tb3_2" onclick="x:HoverLi3(2,2);">��Ŀ�滮�׶�</li>
             <li id="tb3_3" onclick="x:HoverLi3(3,3);">��Ŀʵʩ�׶�</li>
             <li id="tb3_4" onclick="x:HoverLi3(4,4);">��Ŀ��β�׶�</li>
             <li id="tb3_5" onclick="x:HoverLi3(5,5);">����</li>
         </ul>
     </div>
     
        <div id="tbc3_01" class="lists">
            <div class="list">
                  <div id="getwidth" class="widthwide">
                      <div class="fl l_xin">
                          <ul>
                              <li>
                                  <h3>����</h3>
                              </li>
                              <c:forEach var="par" items="${taskInfolistqidong}">
                              <li>
                                  <div class="con" style="background: ${par['color']}">
                                      <div class="imgs fl">
	                                      <c:if test="${empty par['head_portrait']}">
	                                        <img src=" ${ctx}/images/top.png">
	                                      </c:if>
	                                      <c:if test="${not empty par['head_portrait']}">
	                                        <img src=" ${ctx}/${par['head_portrait']}">
	                                      </c:if>
                                      </div>
                                      <div class="fl hov">
                                          <p style="word-break:break-word;" onclick="javascript:info('${par['taskname']}','${par['taskcontent']}','${par['create_name'] }','${par['exec_name'] }','${par['createtime'] }','${par['jxzdate'] }','${par['expectendtime'] }','${par['taskworktime'] }')">${par['taskname']}(${par['showstatus']})</p>
                                          <p onclick="javascript:taskselect('${par['exec_name']}')">${par['exec_name'] }</p>
                                      </div>
                                  </div>
                              </li>
                              </c:forEach>
                              <li>
                                  <a class="a01" href="javascript:;" onclick="more('1')">�鿴����>></a>
                              </li>
                          </ul>
                        </div>
                    </div>
                </div>
           </div>
      
           <div  id="tbc3_02" class="lists">
           <div class="list">
                  <div id="getwidth" class="widthwide">
                      <div class="fl l_xin">
                          <ul>
                              <li>
                                  <h3>�ƻ�</h3>
                              </li>
                              <c:forEach var="par" items="${taskInfolistguihua}">
                              <li>
                                  <div class="con" style="background: ${par['color']}">
                                      <div class="imgs fl">
                                          <c:if test="${empty par['head_portrait']}">
                                            <img src=" ${ctx}/images/top.png">
                                          </c:if>
                                          <c:if test="${not empty par['head_portrait']}">
                                            <img src=" ${ctx}/${par['head_portrait']}">
                                          </c:if>
                                      </div>
                                      <div class="fl hov">
                                          <p style="word-break:break-word;" onclick="javascript:info('${par['taskname']}','${par['taskcontent']}','${par['create_name'] }','${par['exec_name'] }','${par['createtime'] }','${par['jxzdate'] }','${par['expectendtime'] }','${par['taskworktime'] }')">${par['taskname']}(${par['showstatus']})</p>
                                          <p onclick="javascript:taskselect('${par['exec_name']}')">${par['exec_name'] }</p>
                                      </div>
                                  </div>
                              </li>
                              </c:forEach>
                              <li>
                                  <a class="a01" href="javascript:;" onclick="more('2')">�鿴����>></a>
                              </li>
                          </ul>
                        </div>
                    </div>
                </div>
           </div>
           <div id="tbc3_03" class="lists">
                <div class="list">
                  <div id="getwidth" class="widthwide">
                      <!-- ������� -->
                      <div class="fl l_xin">
                          <ul>
                              <li>
                                  <h3>�������</h3>
                              </li>
                              <c:forEach var="par" items="${taskInfolistfenxi}">
	                              <li>
	                                  <div class="con" style="background: ${par['color']}">
	                                      <div class="imgs fl">
	                                          <c:if test="${empty par['head_portrait']}">
	                                            <img src=" ${ctx}/images/top.png">
	                                          </c:if>
	                                          <c:if test="${not empty par['head_portrait']}">
	                                            <img src=" ${ctx}/${par['head_portrait']}">
	                                          </c:if>
                                          </div>
	                                      <div class="fl hov">
	                                          <p style="word-break:break-word;" onclick="javascript:info('${par['taskname']}','${par['taskcontent']}','${par['create_name'] }','${par['exec_name'] }','${par['createtime'] }','${par['jxzdate'] }','${par['expectendtime'] }','${par['taskworktime'] }')">${par['taskname']}(${par['showstatus']})</p>
                                              <p onclick="javascript:taskselect('${par['exec_name']}')">${par['exec_name'] }</p>
	                                      </div>
	                                  </div>
	                              </li>
                              </c:forEach>
                              <li>
                                  <a class="a01" href="javascript:;" onclick="more('3')">�鿴����>></a>
                              </li>
                          </ul>
                        </div>
                        
                      <!-- ������ -->
                      <div class="fl l_xin">
                          <ul>
                              <li>
                                  <h3>������</h3>
                              </li>
                              <c:forEach var="par" items="${taskInfolistsheji}">
                                  <li>
                                      <div class="con" style="background: ${par['color']}">
                                          <div class="imgs fl">
	                                          <c:if test="${empty par['head_portrait']}">
	                                            <img src=" ${ctx}/images/top.png">
	                                          </c:if>
	                                          <c:if test="${not empty par['head_portrait']}">
	                                            <img src=" ${ctx}/${par['head_portrait']}">
	                                          </c:if>
                                          </div>
                                          <div class="fl hov">
                                              <p style="word-break:break-word;" onclick="javascript:info('${par['taskname']}','${par['taskcontent']}','${par['create_name'] }','${par['exec_name'] }','${par['createtime'] }','${par['jxzdate'] }','${par['expectendtime'] }','${par['taskworktime'] }')">${par['taskname']}(${par['showstatus']})</p>
                                              <p onclick="javascript:taskselect('${par['exec_name']}')">${par['exec_name'] }</p>
                                          </div>
                                      </div>
                                  </li>
                              </c:forEach>
                              <li>
                                  <a class="a01" href="javascript:;" onclick="more('4')">�鿴����>></a>
                              </li>
                          </ul>
                        </div>
                        
                        <!-- ������� -->
                        <div class="fl l_xin">
                          <ul>
                              <li>
                                  <h3>�������</h3>
                              </li>
                              <c:forEach var="par" items="${taskInfolistbianma}">
                                  <li>
                                      <div class="con" style="background: ${par['color']}">
                                          <div class="imgs fl">
	                                          <c:if test="${empty par['head_portrait']}">
	                                            <img src=" ${ctx}/images/top.png">
	                                          </c:if>
	                                          <c:if test="${not empty par['head_portrait']}">
	                                            <img src=" ${ctx}/${par['head_portrait']}">
	                                          </c:if>
                                          </div>
                                          <div class="fl hov">
                                              <p style="word-break:break-word;" onclick="javascript:info('${par['taskname']}','${par['taskcontent']}','${par['create_name'] }','${par['exec_name'] }','${par['createtime'] }','${par['jxzdate'] }','${par['expectendtime'] }','${par['taskworktime'] }')">${par['taskname']}(${par['showstatus']})</p>
                                              <p onclick="javascript:taskselect('${par['exec_name']}')">${par['exec_name'] }</p>
                                          </div>
                                      </div>
                                  </li>
                              </c:forEach>
                              <li>
                                  <a class="a01" href="javascript:;" onclick="more('5')">�鿴����>></a>
                              </li>
                          </ul>
                        </div>
                        
                        
                        <!-- �������-->
                        <div class="fl l_xin">
                          <ul>
                              <li>
                                  <h3>�������</h3>
                              </li>
                              <c:forEach var="par" items="${taskInfolistceshi}">
                                  <li>
                                      <div class="con" style="background: ${par['color']}">
                                          <div class="imgs fl">
	                                          <c:if test="${empty par['head_portrait']}">
	                                            <img src=" ${ctx}/images/top.png">
	                                          </c:if>
	                                          <c:if test="${not empty par['head_portrait']}">
	                                            <img src=" ${ctx}/${par['head_portrait']}">
	                                          </c:if>
	                                      </div>
                                          <div class="fl hov">
                                              <p style="word-break:break-word;" onclick="javascript:info('${par['taskname']}','${par['taskcontent']}','${par['create_name'] }','${par['exec_name'] }','${par['createtime'] }','${par['jxzdate'] }','${par['expectendtime'] }','${par['taskworktime'] }')">${par['taskname']}(${par['showstatus']})</p>
                                              <p onclick="javascript:taskselect('${par['exec_name']}')">${par['exec_name'] }</p>
                                          </div>
                                      </div>
                                  </li>
                              </c:forEach>
                              <li>
                                  <a class="a01" href="javascript:;" onclick="more('7')">�鿴����>></a>
                              </li>
                          </ul>
                        </div>
                        
                        
                        <!-- ����ά��-->
                        <div class="fl l_xin">
                          <ul>
                              <li>
                                  <h3>����ά��</h3>
                              </li>
                              <c:forEach var="par" items="${taskInfolistweihu}">
                                  <li>
                                      <div class="con" style="background: ${par['color']}">
                                          <div class="imgs fl">
	                                          <c:if test="${empty par['head_portrait']}">
	                                            <img src=" ${ctx}/images/top.png">
	                                          </c:if>
	                                          <c:if test="${not empty par['head_portrait']}">
	                                            <img src=" ${ctx}/${par['head_portrait']}">
	                                          </c:if>
	                                      </div>
                                          <div class="fl hov">
                                              <p style="word-break:break-word;" onclick="javascript:info('${par['taskname']}','${par['taskcontent']}','${par['create_name'] }','${par['exec_name'] }','${par['createtime'] }','${par['jxzdate'] }','${par['expectendtime'] }','${par['taskworktime'] }')">${par['taskname']}(${par['showstatus']})</p>
                                              <p onclick="javascript:taskselect('${par['exec_name']}')">${par['exec_name'] }</p>
                                          </div>
                                      </div>
                                  </li>
                              </c:forEach>
                              <li>
                                  <a class="a01" href="javascript:;" onclick="more('9')">�鿴����>></a>
                              </li>
                          </ul>
                        </div>
                        
                    </div>
                </div>
           </div>
           <div id="tbc3_04" class="lists">
                <div class="list">
                  <div id="getwidth" class="widthwide">
                      <div class="fl l_xin">
                          <ul>
                              <li>
                                  <h3>���߲���</h3>
                              </li>
                              <c:forEach var="par" items="${taskInfolistshouwei}">
                              <li>
                                  <div class="con" style="background: ${par['color']}">
                                      <div class="imgs fl">
                                          <c:if test="${empty par['head_portrait']}">
                                            <img src=" ${ctx}/images/top.png">
                                          </c:if>
                                          <c:if test="${not empty par['head_portrait']}">
                                            <img src=" ${ctx}/${par['head_portrait']}">
                                          </c:if>
                                      </div>
                                      <div class="fl hov">
                                          <p style="word-break:break-word;" onclick="javascript:info('${par['taskname']}','${par['taskcontent']}','${par['create_name'] }','${par['exec_name'] }','${par['createtime'] }','${par['jxzdate'] }','${par['expectendtime'] }','${par['taskworktime'] }')">${par['taskname']}(${par['showstatus']})</p>
                                          <p onclick="javascript:taskselect('${par['exec_name']}')">${par['exec_name'] }</p>
                                      </div>
                                  </div>
                              </li>
                              </c:forEach>
                              <li>
                                  <a class="a01" href="javascript:;" onclick="more('8')">�鿴����>></a>
                              </li>
                          </ul>
                        </div>
                    </div>
                </div>
           </div>
           <div id="tbc3_05" class="lists">
                <div class="list">
                  <div id="getwidth" class="widthwide">
                      <div class="fl l_xin">
                          <ul>
                              <li>
                                  <h3>�ɹ�</h3>
                              </li>
                              <c:forEach var="par" items="${taskInfolistqita}">
	                              <li>
	                                  <div class="con" style="background: ${par['color']}">
	                                      <div class="imgs fl">
	                                          <c:if test="${empty par['head_portrait']}">
	                                            <img src=" ${ctx}/images/top.png">
	                                          </c:if>
	                                          <c:if test="${not empty par['head_portrait']}">
	                                            <img src=" ${ctx}/${par['head_portrait']}">
	                                          </c:if>
	                                      </div>
	                                      <div class="fl hov">
	                                          <p style="word-break:break-word;" onclick="javascript:info('${par['taskname']}','${par['taskcontent']}','${par['create_name'] }','${par['exec_name'] }','${par['createtime'] }','${par['jxzdate'] }','${par['expectendtime'] }','${par['taskworktime'] }')">${par['taskname']}(${par['showstatus']})</p>
                                              <p onclick="javascript:taskselect('${par['exec_name']}')">${par['exec_name'] }</p>
	                                      </div>
	                                  </div>
	                              </li>
                              </c:forEach>
                              <li>
                                  <a class="a01" href="javascript:;" onclick="more('6')">�鿴����>></a>
                              </li>
                          </ul>
                        </div>
                    </div>
                </div>
           </div>
     </div>
    <div id="covered"></div>
    <div class="tan" id="tan" style="z-index:10000; background:#fff;">
       <div class="t_top">
            <p class="fl">�������ƣ�<div id="taskname" name="taskname"></div></p>
            <span class="fr" id="cos"></span>
        </div>
        <div class="t_con">
            <ul>
                <li>
                    <label>�����ˣ�</label><input type="text" id="create_name" name="create_name" disabled="disabled">
                </li>
                <li>
                    <label>����ʱ�䣺</label><input type="text" id="createtime" name="createtime"  disabled="disabled">
                </li>
                <li>
                    <label>�����ˣ�</label><input type="text" id="exec_name" name="exec_name" disabled="disabled">
                </li>
                <li>
                    <label>����ʱ�䣺</label><input type="text" id="jxzDate" name="jxzDate"  disabled="disabled">
                </li>
                <li>
                    <label>���ʱ�䣺</label><input type="text" id="expectendtime" name="expectendtime"  disabled="disabled">
                </li>
                <li>
                    <label>��������</label><input type="text" id="taskworktime" name="taskworktime" disabled="disabled"><font color="#999">Сʱ</font>
                </li>
            </ul>
            <textarea name="taskcontent" id="taskcontent" disabled="disabled"></textarea>
        </div>
        <div class="t_but">
            <input type="button" class="bttn" value="ȡ��" />
        </div>
    </div> 
    </form> 
</body>
</html>