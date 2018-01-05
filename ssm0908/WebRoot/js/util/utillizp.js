$(function() {
   $("#checkAll").click(function() {
        $('input[name="subBox"]').attr("checked",this.checked);
        totalMoney();
    });
    var $subBox = $("input[name='subBox']");
    $subBox.click(function(){
        $("#checkAll").attr("checked",$subBox.length == $("input[name='subBox']:checked").length ? true : false);
    });
});
function totalMoney(){
	var chk_value =[]; 
	var totalMoney=0;
	  $('input[name="subBox"]:checked').each(function(){    
	   chk_value.push($(this).val());    
	  });    
	for(var i=0;i<chk_value.length;i++){
		totalMoney=totalMoney+parseInt(chk_value[i]);
	}
	$("#totalMoney").html(formatCurrency(totalMoney));
	$("#totals").html(chk_value.length);
}
function formatCurrency(num) {
	num = num.toString().replace(/\$|\,/g,'');
	if(isNaN(num))
	num = "0";
	sign = (num == (num = Math.abs(num)));
	num = Math.floor(num*100+0.50000000001);
	cents = num%100;
	num = Math.floor(num/100).toString();
	if(cents<10)
	cents = "0" + cents;
	for (var i = 0; i < Math.floor((num.length-(1+i))/3); i++)
	num = num.substring(0,num.length-(4*i+3))+','+
	num.substring(num.length-(4*i+3));
	return (((sign)?'':'-')  + num + '.' + cents);
}