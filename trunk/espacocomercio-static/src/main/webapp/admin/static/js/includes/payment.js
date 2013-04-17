function loadPayment() {
	setMenuActive('payment');
	loading('contentAll');
	$('#contentAll').load('includes/payment.html', function() {
		$('#box-tab-Category').css('margin-left', '10px');
		$('span#spanTitleEcommerce').text(titlePage);
		$('a#btnConsultaPgto').click();
		activePlugins();
	});
}

function cadastropgto(data) {
	alert("Forma de pagamento criada com sucesso.", true);
	$('button[type="reset"]').click();
}
