function loadOrderPage() {
	setMenuActive('order');
	loading('contentAll');
	$.getJSON('/ecommerce-web/admin/order?tk='+new Date().getTime(), function(data) {
		if (data.status) {
			$('#contentAll').load('includes/order.html', function() {
				$('#box-tab-Category').css('margin-left', '10px');
				loadGrid('/ecommerce-web/admin/order/consult', 'grid', 'formEdit', '');
				$('span#spanAguardandoPagamento').text(data.generic[0]);
				$('span#spanPagamentoConfirmado').text(data.generic[1]);
				$('span#spanEnviadosEFinalizados').text(data.generic[2]);
				$('span#spanCancelados').text(data.generic[3]);
				$('span#spanTitleEcommerce').text(titlePage);
				
				preencheCbSatus();
				preencheCbPayment();
				activePlugins();
			});
		} else {
			errorForm(data);
			$('#contentAll').html('');
		}
	});
}

function cadastrocategoria(data) {
	alert("Categoria criada com sucesso.", true);
	$('button[type="reset"]').click();
}

function viewCart(orderId) {
	$.getJSON( '/ecommerce-web/admin/order/product/list?oid='+orderId,  function(data) {
		
		var htmlTb = '';
		$.each(data.genericList, function(key, val) {
			var tr = '<tr>';
			tr += '<td>'+val.product.name+'</td>';
			tr += '<td>'+val.quantity+'</td>';
			var v = val.unityvalue.toString();
			if (v.indexOf('.')==-1) {
				tr += '<td>R$ '+v+',00</td>';
			} else {
				tr += '<td>R$ '+v.replace('.', ',')+'</td>';
			}
			tr += '</tr>';
			htmlTb += tr;
		});
		
		$('table#tbProductCesta').find('tbody').html(htmlTb);
		
		$('span#freteValueSpan').text($('td#grid'+orderId+'sendCust').text());
		$('span#descValueSpan').text($('td#grid'+orderId+'discount').text());
		$('span#totalValueSpan').text($('td#grid'+orderId+'totalValue').text());
		$('#modalProducts').modal('show');
	});
}

function viewClientModal(orderId) {
	$.getJSON( '/ecommerce-web/admin/order/client?oid='+orderId,  function(data) {
		
		$('td#tdNomeClient').html(data.generic.name);
		$('td#tdEmailClient').html(data.generic.user);
		$('td#tdEnderecoClient').html(data.generic.addressStreet + ', ' + data.generic.addressNumber + '. ' + data.generic.addressCity + '<br>Complemento: ' + data.generic.addressComplement + '<br>CEP: ' + data.generic.addressZipcode);
		
		$('#modalClient').modal('show');
	});
}

function preencheCbPayment() {
	$.getJSON( '/ecommerce-web/admin/payment/list',  function(data) {
		var opts = '';
		$.each(data.genericList, function(key, val) {
			opts += '<option value="'+val.id+'">'+val.id+' - '+val.name+'</option>';
		});
		$('select[name="payment"]').html(opts);
	});
}

function preencheCbSatus() {
	var opts = '';
	for (var i=1; i<=getQtdOrderStatus(); i++) {
		opts += '<option value="'+i+'">'+getLblOrderStatus(i)+'</option>';
	}
	
	$('select[name="statusOrder"]').html(opts);
}

function getQtdOrderStatus() {
	return 5;
}