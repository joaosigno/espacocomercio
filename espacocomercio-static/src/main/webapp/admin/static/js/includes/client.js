function loadClient() {
	setMenuActive('client');
	loading('contentAll');
	$.getJSON('/ecommerce-web/admin/client?tk='+new Date().getTime(), function(data) {
		if (data.status) {
			$('#contentAll').load('includes/client.html', function() {
				$('#box-tab-Category').css('margin-left', '10px');
				loadGrid('/ecommerce-web/admin/client/consult', 'grid', 'formEdit', '');
				$('span#spanClientTotal').text(data.generic);
				$('span#spanTitleEcommerce').text(titlePage);
				activePlugins();
			});
		} else {
			errorForm(data);
			$('#contentAll').html('');
		}
	});
}

function fnPesquisarClient() {
	var inputPesquisar = $('input#inputPesquisar').val();
	if (inputPesquisar=='') {
		loadGrid('/ecommerce-web/admin/client/consult', 'grid', 'formEdit', '');
	} else {
		loadGrid('/ecommerce-web/admin/client/consult?filter='+inputPesquisar, 'grid', 'formEdit', '');
	}
}