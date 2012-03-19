$(function() {
	$("#dialog:ui-dialog").dialog("destroy");
});

function dataFilter() {
	$.post('/hits/ajax/dataFilter', $("#filterForm").serialize(),
			function(data) {
				$('#data-div').html(data);
			});
}

function createReportModal(){

	$("#dialog-modal").dialog({
		height : 240,
		width: 500,
		modal : true
	});
}