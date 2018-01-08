function TextMaxLength(obj,ln){
	if(obj.value.length>ln){
		obj.value=obj.value.substring(0,ln);
	}
}

function showMWin(url,w,h){
	if(url.indexOf('?')!=-1){
		url = url +'&sotowerTemp'+parseInt(100000*Math.random())+'='+parseInt(100000*Math.random());
	}else{
		url = url +'?sotowerTemp'+parseInt(100000*Math.random())+'='+parseInt(100000*Math.random());
	}
	var wx=window.showModalDialog(url,window,"dialogWidth:"+w+"px;dialogHeight:"+h+"px;");
	return wx
}


/**
函 数 名：validateForm
函数功能：验证提交的表单
调用方法：<onSubmit="return validateForm(this.formElementName,'validateType','validateCondition')">，参数长度不限
变量释义：
变量名含义
	i,j循环计数变量
	vc_min,vc_max最小值、最大值
	vObjvalidate object,要验证的对象
	vTypevalidate type,验证类型
	vConvalidate condition,验证条件
	eNumerror number,错误数量
	eMsgerror message,错误信息
	cItemNumchecked item number,选中项目数
	fErrMsgfinal error message,最终显示给用户看的错误信息
	reregular expression,正则表达式
验证类型：
代码含义
	FitRegEx必须完全匹配“用户自定义的正则表达式”
	AntiRegEx不能出现“匹配用户自定义的正则表达式的字串”
	NotBlank不可为空
	IsNumeric数字
	IsInt整数
	IsEmail邮件地址
	IsPlainText纯文本，不能包含HTML代码
	LengthRange字符长度范围
	NumericRange数字大小范围
	IsEqualTo确认项（如密码）和第一次输入的是否相等
	CheckLimit选中多少项vc_mintovc_max	vc_max=-1表示没有上限
	SelectValid选中有效项
	IsChinese中文
	IsEnglish英文
	IsDouble实数
	isUrl基于HTTP协议的网址格式
	isPhone电话号码格式
	IsMobile手机
	IsCurrency货币
	IsZip邮政编码
	IsIdCard身份证号码
	IsQQQQ号码
	LengthRangeB 字符长度范围(中文占2个)
**/



function validateForm() {
	var i, j, vObj, vType, vCon, eNum = 0, eMsg = '', cItemNum = 0, fErrMsg='', re;
	var args = validateForm.arguments;
	for (i = 0; i < (args.length-2); i += 3){
		cItemNum = 0;
		vObj = args[i];
		vType = args[i+1];
		vCon = args[i+2];
		switch (vType){
			case 'IsLength':
				re = vCon;
				if (vObj.value == '' || vObj.value.length != re ){
					eNum ++;
					eMsg += genErrMsg( eNum,vObj,'的数据制定长度不为('+re+')位要求' );
				}
				break;
                        case 'IsMaxLength':
				re = vCon;
				if (vObj.value.length >re ){
					eNum ++;
					eMsg += genErrMsg( eNum,vObj,'的数据值超出最大长度('+re+')位要求' );
				}
				break;
                        case 'FitRegEx':
				re = vCon;
				if ( vObj.value.search( re ) != 0 ){
					eNum ++;
					eMsg += genErrMsg( eNum,vObj,'的值不符合要求' );
				}
				break;
			case 'AntiRegEx':
				re = vCon;
				if ( vObj.value.search( re ) >= 0 ){
					eNum ++;
					eMsg += genErrMsg( eNum,vObj,'的值不符合要求' );
				}
				break;
			case 'NotBlank':
				//add by dongsheng begin
				vObj.value = vObj.value.trim();
				//add by dongsheng end
				if ( vObj.value == '' ){
					eNum ++;
					eMsg += genErrMsg( eNum,vObj,'没有填写' );
				}
				break;
			case 'IsNumeric':
				if ( vObj.value == '' ){
					break;
				}
				if ( isNaN(vObj.value) || vObj.value == '' ){
					eNum ++;
					eMsg += genErrMsg( eNum,vObj,'不是数字' );
				}else
				{  
					var temp_vObjvalue = vObj.value.trim();
					temp_vObjvalue=temp_vObjvalue.substr(temp_vObjvalue.length-1);
					if(temp_vObjvalue==".")
					{
					eNum ++;
					eMsg += genErrMsg( eNum,vObj,'系统不支持数字以小数点结尾' );
				  }
				}
				
				break;
			case 'IsInt':
				if ( vObj.value == '' ){
					break;
				}
				re = /^-?[1-9]\d*|0$/;
				if ( vObj.value.search(re) != 0 || vObj.value!=parseInt(vObj.value,10)){
					eNum ++;
					eMsg += genErrMsg( eNum,vObj,'格式不正确' );//不是整数
				}
				break;
			case 'IsEmail':
				re = /^[a-zA-Z0-9_]+@[a-zA-Z\.0-9_-]+$/;
				if ( vObj.value.search(re) != 0 ){
					eNum ++;
					eMsg += genErrMsg( eNum,vObj,'不是有效Email地址' );
				}
				break;
			case 'IsPlainText':
				re = /<[a-zA-Z]+[^>]*>/;
				if ( vObj.value.search(re) >= 0 ){
					eNum ++;
					eMsg += genErrMsg( eNum,vObj,'不是纯文本，含有HTML标签' );
				}
				break;
			case 'LengthRange':
				vc_min = parseFloat( vCon.substring( 0,vCon.indexOf("to") ) );
				vc_max = parseFloat( vCon.substring( vCon.indexOf("to")+2 ) );
				if ( vObj.value.length < vc_min || vObj.value.length > vc_max ){
					eNum ++;
					eMsg += genErrMsg( eNum,vObj,'的长度不在指定范围('+vc_min+','+vc_max+')内' );
				}
				break;
			case 'NumericRange':
				vc_min = parseFloat( vCon.substring( 0,vCon.indexOf("to") ) );
				vc_max = parseFloat( vCon.substring( vCon.indexOf("to")+2 ) );
				if ( vObj.value < vc_min || vObj.value > vc_max ){
					eNum ++;
					eMsg += genErrMsg( eNum,vObj,'的值不在指定范围('+vc_min+','+vc_max+')内' );
				}
				break;
			case 'IsEqualTo':
				if ( vObj.value != vCon.value ){
					eNum ++;
					eMsg += genErrMsg( eNum,vObj,'和第一次输入的不相等' );
				}
				break;
			case 'IsBigTo':

				if ( vObj.value != '' && vCon.value != '' && vObj.value > vCon.value ){
					eNum ++;
					eMsg += genErrMsg( eNum,vObj,'开始比结束大' );
				}
				break;
			case 'CheckLimit':
				vc_min = parseFloat( vCon.substring( 0,vCon.indexOf("to") ) );
				vc_max = parseFloat( vCon.substring( vCon.indexOf("to")+2 ) );
				if ( vc_max == -1 ){//vc_max=-1表示没有上限
					vc_max = vObj.length;
				}
				for (j = 0;j < vObj.length;j ++){
					if ( vObj[j].checked ){
						cItemNum ++;
					}
				}
				if ( cItemNum < vc_min || cItemNum > vc_max ){
					eNum ++;
					eMsg += genErrMsg( eNum,vObj[0],'的选中项目数不在指定范围('+vc_min+','+vc_max+')内' );
				}
				break;
			case 'SelectValid':
				if ( vObj.value == '' ){
					eNum ++;
					eMsg += genErrMsg( eNum,vObj,'没有选中有效项' );
				}
				break;
			case 'IsChinese':
				re = /^[\u0391-\uFFE5]+$/;
				if ( vObj.value.search(re) >= 0 ){
					eNum ++;
					eMsg += genErrMsg( eNum,vObj,'不是全部为中文' );
				}
				break;
			case 'IsEnglish':
				re = /^[A-Za-z]+$/;
				if ( vObj.value.search(re) >= 0 ){
					eNum ++;
					eMsg += genErrMsg( eNum,vObj,'不是全部为英文' );
				}
				break;
			case 'IsDouble':
				re = /^[-\+]?\d+(\.\d+)?$/;
				if ( vObj.value.search(re) >= 0 ){
					eNum ++;
					eMsg += genErrMsg( eNum,vObj,'不是实数' );
				}
				break;
			case 'IsUrl':
				re = /^http:\/\/[A-Za-z0-9]+\.[A-Za-z0-9]+[\/=\?%\-&_~`@[\]\':+!]*([^<>\"\"])*$/;
				if ( vObj.value.search(re) >= 0 ){
					eNum ++;
					eMsg += genErrMsg( eNum,vObj,'不是基于HTTP协议的网址格式' );
				}
				break;
			case 'IsPhone':
				re = /^((\(\d{3}\))|(\d{3}\-))?(\(0\d{2,3}\)|0\d{2,3}-)?[1-9]\d{6,7}$/;
				if ( vObj.value.search(re) >= 0 ){
					eNum ++;
					eMsg += genErrMsg( eNum,vObj,'不是电话号码' );
				}
				break;
			case 'IsMobile':
				re = /^((\(\d{3}\))|(\d{3}\-))?13\d{9}$/;
				if ( vObj.value.search(re) >= 0 ){
					eNum ++;
					eMsg += genErrMsg( eNum,vObj,'不是手机号码' );
				}
				break;
			case 'IsCurrency':
				re = /^\d+(\.\d+)?$/;
				if ( vObj.value.search(re) >= 0 ){
					eNum ++;
					eMsg += genErrMsg( eNum,vObj,'不是货币' );
				}
				break;
			case 'IsZip':
				re = /^[1-9]\d{5}$/;
				if ( vObj.value.search(re) >= 0 ){
					eNum ++;
					eMsg += genErrMsg( eNum,vObj,'不是邮政编码' );
				}
				break;
			case 'IsIdCard':
				re = /^\d{15}(\d{2}[A-Za-z0-9])?$/;
				if ( !(vObj.value.search(re) >= 0) ){
					eNum ++;
					eMsg += genErrMsg( eNum,vObj,'不是身份证号码' );
				}
				break;
			case 'IsQQ':
				re = /^[1-9]\d{4,8}$/;
				if ( vObj.value.search(re) >= 0 ){
					eNum ++;
					eMsg += genErrMsg( eNum,vObj,'不是QQ号码' );
				}
				break;
			case 'LengthRangeB':
				vc_min = parseFloat( vCon.substring( 0,vCon.indexOf("to") ) );
				vc_max = parseFloat( vCon.substring( vCon.indexOf("to")+2 ) );
				var newlength = vObj.value.replace(/[^\x00-\xff]/g,"**").length
				if ( newlength < vc_min || newlength > vc_max ){
					eNum ++;
					eMsg += genErrMsg( eNum,vObj,'的长度不在指定范围('+vc_min+','+vc_max+')内,中文占两个字符,现在长度为'+newlength );
				}
				break;

			}
		}
	if ( eNum >0 ){
		fErrMsg = '抱歉，您提交的数据存在' + eNum + '处错误:\n';
		fErrMsg += '==============================\n';
		fErrMsg += eMsg;
		fErrMsg += '==============================\n';
		fErrMsg += '\n请修改后再提交，谢谢！';
		alert( fErrMsg );
		return false;
	}else{
		return true;
	}
}

//Generate Error Message
function genErrMsg(eTimes,vObj,eType){
	eMsg = eTimes + '. [ ' + vObj.title + ' ] ' + eType + '。\n';
	return eMsg;
}



function isMobilePhone(s) {
	return /^1(3|4|5|8)\d{9}$/g.test(s);
}

function validateXml(obj){
	var reg=/[`~\'"><&$^*,?;:!#+=%]/g;
	if(reg.exec(obj.value)!=null){
		alert('不能包含~\'"><&$^*,?;:!#+=%');
		obj.value=obj.value.replace(/[`~\'"><&$^*,?;:!#+=%]/g,'');
	}
}
function validateNumInt(vObj){
	var re = /^-?[1-9].|\d*|0$/;
	if ( vObj.value.search(re) != 0 || vObj.value!=parseInt(vObj.value,10)){
		alert('输入必须为整数');
		vObj.value=vObj.value.replace(/[^\d]/g,'');
	}
}

function onnumber(vObj){
	
	var re = /^(\d+\.\d{1,2}|\d+)$/;
	if ( vObj.value.search(re) != 0 || vObj.value!=parseInt(vObj.value,10)){
		alert('输入必须为整数');
		//vObj.value=vObj.value.replace(/^(\d+\.\d{1,2}|\d+)$/ ,'');
	}
	
}


function disableAllButtonInForm() {
	   var oForm=document.forms[0];
	   for(i = 0; i < oForm.elements.length; i++) {
	      if(oForm.elements[i].type){
	         if(oForm.elements[i].type.toLowerCase() == 'button')
	         oForm.elements[i].disabled = true;
	      } 
	   }
}
function getXML(URL){
	var result = $.ajax({
		url: URL,
		type: "POST",
		async: false//同步
	}).responseText;
	return result
}

function setParentTXnum(num){
	var tx=	parent.document.getElementById("num-bg").textContent;
	if(tx>=1){
		tx=tx-num;
		parent.document.getElementById("num-bg").textContent=tx;
	}
}
