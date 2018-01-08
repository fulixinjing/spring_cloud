/*$(document).ready(function() {
    $('#myTable01').fixedHeaderTable({ footer: true, cloneHeadToFoot: true, altClass: 'odd', autoShow: false });
    
    $('#myTable01').fixedHeaderTable('show', 1000);
    
    $('#myTable02').fixedHeaderTable({ footer: true, altClass: 'odd' });
    
    $('#myTable05').fixedHeaderTable({ altClass: 'odd', footer: true, fixedColumns: 1 });
    
    $('#myTable03').fixedHeaderTable({ altClass: 'odd', footer: true, fixedColumns: 1 });
    
    $('#myTable04').fixedHeaderTable({ altClass: 'odd', footer: true, cloneHeadToFoot: true, fixedColumns: 3 });
});*/


$(document).ready(function() {
	var myTable01 = $('#myTable01');
    $('#myTable01').fixedHeaderTable({ footer: false, altClass: 'odd' });
    
    $('#myTable02').fixedHeaderTable({ footer: true, altClass: 'odd' });
    
    $('#myTable05').fixedHeaderTable({ altClass: 'odd', footer: true, fixedColumns: 1 });
    
    $('#myTable03').fixedHeaderTable({ altClass: 'odd', footer: true, fixedColumns: 1 });
    
    $('#myTable04').fixedHeaderTable({ altClass: 'odd', footer: true, cloneHeadToFoot: true, fixedColumns: 3 });
	//console.log(myTable01.parent().parent().width(myTable01.width()))
	/*setTimeout(function(){
		var tr_len = myTable01.find('tbody').find('tr');
	if(tr_len.length == 1){
		myTable01.parent().parent().width(myTable01.width());
		myTable01.parent().parent().attr('num',111)
	}
	},10);*/
	function getTableByList(tb){
		setTimeout(function(){
			var tr_len = tb.find('tbody').find('tr');
		if(tb.height() < tb.parent().height()){
			//tb.parent().parent().width(tb.width());
		}
		},100);	
	}
	
	$([$('#myTable01'),$('#myTable02')]).each(function(index,element){
		getTableByList(element);
	});
	var fancyTable = $('.fancyTable');
	fancyTable.find('tr').each(function(index,element){
		$(element).hover(
			function(){
				$(this).find('td').addClass('hover-bgcolor');
			},
			function(){
				$(this).find('td').removeClass('hover-bgcolor');
			}
		);
	});
	var myTable02 = $('#myTable02'),
		tbody = myTable02.find('tbody'),
		trs = tbody.find('tr');
	trs.each(function(index,element){
		$(element).click(function(){
			trs.removeClass('numerics-bgcolor');	
			$(this).addClass('numerics-bgcolor');
		});	
	});
});