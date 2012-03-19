

function dataFilter() {
	$.post('/hits/ajax/dataFilter', $("#filterForm").serialize(),
			function(data) {
				$('#data-div').html(data);
			});
}

function createReport(){
	$("#dialog:ui-dialog").dialog("destroy");

	$("#dialog-modal").dialog({
		height : 140,
		modal : true
	});
}