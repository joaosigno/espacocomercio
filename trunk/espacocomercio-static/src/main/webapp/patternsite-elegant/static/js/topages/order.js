function paymentOrder() {
	$.post('/ecommerce-web/order', $('form#formCartAll').serialize()+'&sid='+getSid()+'&send='+$('span#spanFrete').text()+'&total='+$('span#spanTotalvalue').text(), function(data){
		if (data.status) {
			
			var form = '<form method="post" action="https://pagseguro.uol.com.br/v2/checkout/payment.html">';
			form += 		'<input type="hidden" name="receiverEmail" value="'+data.generic.payment.url+'">';
			form += 		'<input type="hidden" name="currency" value="BRL">';
			
			$.each(data.generic.cart, function(key, val) {
				var i = 1;
				
				form += 		'<input type="hidden" name="itemId'+i.toString()+'" value="'+val.id+'">';
				form += 		'<input type="hidden" name="itemDescription'+i.toString()+'" value="'+val.name+'">';  
				form += 		'<input type="hidden" name="itemAmount'+i.toString()+'" value="'+convertMoeda(val.unityvalue).replace(',', '.')+'">  ';
				form += 		'<input type="hidden" name="itemQuantity'+i.toString()+'" value="'+$('select[name=qtdSelect'+val.id+']').val()+'">';
				form += 		'<input type="hidden" name="itemShippingCost'+i.toString()+'" value="'+$('span#spanFrete').text().replace(',', '.')+'"> ';
				
				i++;
			});
			
			form += 		'<input type="hidden" name="senderName" value="'+data.generic.client.name+'"> '; 
			form += 		'<input type="hidden" name="senderAreaCode" value="">  ';
			form += 		'<input type="hidden" name="senderPhone" value=""> '; 
			form += 		'<input type="hidden" name="senderEmail" value="'+data.generic.client.user+'">';
			
			form += 		'<input type="hidden" name="reference" value="'+data.generic.order+'">';
			
			form += 		'<input type="submit" id="submitFormPagSeg">';
			form += 	'</form>';
			
			if ($('div#divFormPagSeg').size()==0) {
				var html = '<div id="divFormPagSeg" class="hide">';
				html += form;
				html += 	'<div>';
				$('body').append(html);
			} else {
				$('div#divFormPagSeg').html(form);
			}
			
			alert('Obrigado por comprar conosco.\nAo clicar em OK você será redirecionado para o ambiente seguro do "PagSeguro" para concluir o pagamento do seu pedido.\n\nPara consultar o status do seu pedido basta clicar em "Entrar > Meu cadastro > Pedidos em aberto".\nQualquer dúvida entre em contato conosco pelo FaleConosco.');
			
			$('#submitFormPagSeg').click();
		} else {
			alert(data.messageError.generic);
		}
	}, "json");
	
}