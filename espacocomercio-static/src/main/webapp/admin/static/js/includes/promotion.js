function loadPromotion() {
	setMenuActive('promotion');
	loading('contentAll');
	$.getJSON('/ecommerce-web/admin/promotion?tk='+new Date().getTime(), function(data) {
		if (data.status) {
			$('#contentAll').load('includes/promotion.html', function() {
				$('#box-tab-Category').css('margin-left', '10px');
				loadGrid('/ecommerce-web/admin/product/category/consult', 'grid', 'formEdit', '/ecommerce-web/admin/product/category/remove');
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

function formRules() {
	var opt0 = '<input id="opt0Value" type="text" disabled="disabled" value="Produtos" class="span3" style="border: 1px solid #ccc;font-size:16px">';
	
	var opt1 = '<select class="span3" id="opt1Value" style="border: 1px solid #ccc;font-size:16px">';
	opt1 += '<option value="valor-maior">com valor acima de</option>';
	opt1 += '<option value="estoque-maior">com estoque acima de</option>';
	opt1 += '<option value="valor-menor">com valor abaixo de</option>';
	opt1 += '<option value="estoque-menor">com estoque abaixo de</option>';
	opt1 += '<option value="valor-igual">com valor igual a</option>';
	opt1 += '<option value="estoque-igual">com estoque igual a</option>';
	opt1 += '</select>';
	
	var opt2 = '<input id="opt2Value" type="text" class="span3" style="border: 1px solid #ccc;font-size:16px" onkeypress="selecionaValidacao(this);">';
	
	var opt3 = '<a class="btn" href="#" onclick="addRule();return false;"><i class="icon-plus-sign"></i></a>';
	
	$('div#formRules').html(opt0 + opt1 + opt2 + opt3);
}

function selecionaValidacao(obj) {
	var opt1 = $('select#opt1Value').val();
	
	if (opt1.indexOf('valor')==-1) {
		mascara( obj, number );
	} else {
		mascara( obj, money );
	}
}

function addRule() {
	var opt1 = $('select#opt1Value').val();
	var opt2 = $('input#opt2Value').val();
	
	if (opt2!='') {
		if (opt1.indexOf('valor')!=-1) {
			opt2 = opt2.replace(',','.');
		} else {
			opt2 = opt2.split(',')[0];
		}
		
		var rules = $('input[name="rules"]').val();
		var position;
		if (rules.length==0) {
			position = 1;
		} else {
			var rulesSplit = rules.split('#LIN#');
			position = rulesSplit.length + 1;
			rules += '#LIN#';
		}
		
		var rule = position+'#COL#'+opt1+'#COL#'+opt2;
		rules += rule;
		
		$('input[name="rules"]').val(rules);
		
		var rule = '<p id="pRule'+position+'"><strong>Produtos </strong><font color="darkred">com '+opt1.split('-')[0]+' '+opt1.split('-')[1];
		if (opt1.indexOf('igual')==-1) {
			rule += ' que ';
		} else {
			rule += ' a ';
		}
		rule += '</font><strong>'+opt2.replace('.',',')+'</strong> <a class="btn" href="#" onclick="removeRule(\''+position+'\');return false;"><i class="icon-minus-sign"></i></a>';
		
		$('div#divRules').html($('div#divRules').html() + rule);
		$('div#formRules').html('<button id="btnAddRules" class="btn btn-small btn-warning" onclick="formRules(); return false;" type="button">+ Adicionar regra</button>');
	} else {
		alert('Digite o valor da regra corretamente.');
	}
}

function removeRule(position) {
	var rules = $('input[name="rules"]').val();
	var rulesSplit = rules.split('#LIN#');
	var newRules = '';
	
	for (var i=0; i<rulesSplit.length; i++) {
		var colSplit = rulesSplit[i].split('#COL#');
		
		if (colSplit[0]!=position) {
			if (newRules.length>0) {
				newRules += '#LIN#';
			}
			newRules += rulesSplit[i];
		}
	}
	
	$('#pRule'+position).remove();
	$('input[name="rules"]').val(newRules);
}

function money(v){
    v=v.replace(/\D/g,"");
    v=v.replace(/(\d)(\d{2})$/,"$1,$2");
    return v;
}

function number(v){
    v=v.replace(/\D/g,"");
    return v;
}

function mascara(o,f){
    v_obj=o
    v_fun=f
    setTimeout("execmascara()",1)
}
function execmascara(){
    v_obj.value=v_fun(v_obj.value)
}