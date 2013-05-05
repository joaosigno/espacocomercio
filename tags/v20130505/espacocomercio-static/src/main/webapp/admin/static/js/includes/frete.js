function loadFrete() {
	setMenuActive('send');
	loading('contentAll');
	$('#contentAll').load('includes/frete.html', function() {
		$('#box-tab-Category').css('margin-left', '10px');
		$('span#spanTitleEcommerce').text(titlePage);
		$('a#btnConsultaFrete').click();
		activePlugins();
		$("input[name='quantityDay']").mask("9?999");
		$("input[name='value']").maskMoney({symbol:'R$ ', showSymbol:true, thousands:'.', decimal:',', symbolStay: false});
	});
}

function cadastrofrete(data) {
	alert("Frete criado com sucesso.", true);
	$('button[type="reset"]').click();
}
