/****************************************************************************
 * DESC       ：公用函数变量定义 
 * Author     : yanjiming
 * CREATEDATE ：2015-07-15
 ************************************************************************************/
/************************************************************************************
  函数组        函数名称                函数作用
  输入域校验函数
                checkDecimal            如果输入域不为空，校验输入域的值是否为在输入许可范围内的Decimal型,如果不是则弹出提示且无法离开
                checkInteger            如果输入域不为空，校验输入域的值是否为Integer型,如果不是则弹出提示且无法离开
                checkLength             如果输入域不为空，校验输入域的值是否为不大于最大长度的字符型,如果不是则弹出提示且无法离开
                isInteger               检查数据是否是整数,即只包含字符0123456789,是返回true,否则返回false
                isNumeric               检查数据是否是数字,是返回true,否则返回false
/************************************************************************************/

var MAX_INTEGER  = Math.pow(2,31) - 1;
var MIN_INTEGER  = -MAX_INTEGER;

//离开域时的数字校验Decimal
function checkDecimal(field,p,s,MinValue,MaxValue)
{
	field.value = $.trim(field.value);
	var strValue=field.value;
	if(strValue=="")
		strValue = "0";
	
	var desc   = $(field).attr("description");
	//如果description属性不存在，则用name属性
	if(desc==null)
		desc = field.name;
	strValue = reverseFormatFloat(strValue);
	if(!isNumeric(strValue))
	{
		errorMessage("请输入合法的数字");
		window.setTimeout(function() {	
			field.focus();
		}, 0);	
		field.select();
		return false;
		}
	p = parseInt(p,10);
	s = parseInt(s,10);
	
	var pLength;
	var sLength;
	var position = strValue.indexOf(".");
	if(position>-1)
	{
		pLength = position;
		sLength = strValue.length - position - 1;
	}
	else
	{
		pLength = strValue.length;
		sLength = 0;
	}
	
	if(pLength>(p-s) || sLength>s)
	{
		errorMessage("请输入合法的" + desc +"\n类型为数字,整数位最长为" + (p-s) + ",小数位最长为" + s);
		window.setTimeout(function() {	
			field.focus();
		}, 0);	
		field.select();
		return false;
	}

	var value = parseFloat(strValue);
	if(MaxValue!=null && MinValue!=null && trim(MaxValue)!="" && trim(MinValue)!="")
	{
		MinValue = parseFloat(MinValue);
		MaxValue = parseFloat(MaxValue);
		if(isNaN(value) || value>MaxValue || value<MinValue)
		{
			errorMessage("请输入合法的" + desc +"\n类型为数字,最小值为" + MinValue + ",最大值为" +MaxValue);
			window.setTimeout(function() {	
				field.focus();
			}, 0);	
			field.select();
			return false;
		}
	}

	return true;
}

//离开域时的数字校验
function checkInteger(field,MinValue,MaxValue)
{
	field.value = $.trim(field.value);
	var strValue=field.value;
	if(strValue=="")
		strValue = "0";
	var desc   = $(field).attr("description");
	//如果description属性不存在，则用name属性

	if(desc==null)
		desc = field.name;

	if(!isInteger(strValue))
	{
		errorMessage("请输入正整数");
		window.setTimeout(function() {	
			field.focus();
		}, 0);		
		field.select();
		return false;
	}

	MinValue = parseInt(MinValue,10);
	if(isNaN(MinValue))
		MinValue = MIN_INTEGER;

	MaxValue = parseInt(MaxValue,10);
	if(isNaN(MaxValue))
		MaxValue = MAX_INTEGER;
	var value = parseInt(strValue,10);
	if(isNaN(value) || value>MaxValue || value<MinValue)
	{
		errorMessage("请输入合法的" + desc +"\n类型为数字(integer),最小值为" + MinValue + ",最大值为" +MaxValue);
		window.setTimeout(function() {
			field.focus();
		}, 0);	
		field.select();
		return false;
	}
	return true;
}

//控制输入域长度
//使用方法如下所示
//<input name = "name" maxlength="8" description="名字"  onblur="checkLength(this)">
function checkLength(field)
{
	var str;
	var count  = 0;
	var value  = field.value;
	var length = field.maxLength;
	var desc   = $(field).attr("description");
	//如果description属性不存在，则用name属性
	if(desc==null)
		desc = field.name;
	
	if(value=="")
	{
		return true;
	}
	
	if(value.indexOf("^")>-1 ||
			value.indexOf(FIELD_SEPARATOR)>-1 ||
			value.indexOf(GROUP_SEPARATOR)>-1)
	{
		errorMessage("^为系统保留字符，不允许输入！");
		window.setTimeout(function() {
			field.focus();
		}, 0);	
		field.select();
		return false;
	}

	//如果maxlength属性不存在，则返回
	if(isNaN(parseInt(length,10)))
		return true;

	for(var i=0;i<value.length;i++)
	{
		str = escape(value.charAt(i));
		if(str.substring(0,2)=="%u" && str.length==6)
			count = count + 2;
		else
			count = count + 1;
	}

	if(count>length)
	{
		errorMessage(desc + "输入的内容超长！\n" + desc + "的最大长度为" + length + "个英文字符！\n请重新输入！");
		window.setTimeout(function() {
			field.focus();
		}, 0);	
		field.select();
		return false;
	}
	return true;
}

/**
 * @description 格式化数字:千分位转非千分位
 * @author yanjiming
 * @param formatValue 值
 * @param delimiterChar 分割符 默认为','
 */
function reverseFormatFloat(formatValue,delimiterChar)
{
	var value = formatValue.toString();
	delimiterChar = delimiterChar == null?",":delimiterChar;
	while(value.indexOf(delimiterChar) > -1)
	{
		value = value.replace(delimiterChar,"");
	}
	return value;
}

//对输入域是否是整数的校验,即只包含字符0123456789
function isInteger(strValue)
{
	var result = regExpTest(strValue,/\d+/g);
	return result;
}
//RegExt Test
function regExpTest(source,re)
{
	var result = false;
	
	if(source==null || source=="")
		return false;
	
	if(source==re.exec(source))
		result = true;
	
	return result;
}
//显示错误信息
function errorMessage(strErrMsg)
{
	var strMsg = "系统信息:\n\n" + strErrMsg;
	alert(strMsg);
}
function validateXml1(obj){
	var reg=/[`~\'"><&$^*,?;:!#+=%]/g;
	if(reg.exec(obj.value)!=null){
		alert("系统信息:\n\n" +'此输入框不能包含~\'"><&$^*,?;:!#+=%');
		
		obj.value=obj.value.replace(/[`~\'"><&$^*,?;:!#+=%]/g,'');
	}
}
function validateXml2(obj){
	var reg=/[`~\'"><&$^* , ?;:!#+=%]/g;
	if(reg.exec(obj.value)!=null){
		alert("系统信息:\n\n" +'此输入框不能包含`~\'"><&$^* , ?;:!#+=%');
		
		obj.value=obj.value.replace(/[`~\'"><&$^* , ?;:!#+=%]/g,'');
	}
}
function validateXml(obj){
	var str=0;
	var reg=/[`~\'"><&$^*,?;:!#+=%]/g;
	if(reg.exec(obj)!=null){
		alert('不能包含~\'"><&$^*,?;:!#+=%');
		obj='';
		str= '1';
	}
	return str;
}
//对输入域是否是数字的校验
function isNumeric(strValue)
{
	var result = regExpTest(strValue,/\d*[.]?\d*/g);
	return result;
}
//对输入域是否是保留一位有效数字的校验
function isNumericMath(strValue)
{
	var result = regExpTest(strValue,/\d*[.]?\d/g);
	if(strValue>0&&strValue<=40){
		return result;
	}else{
		return false;
	}
}
