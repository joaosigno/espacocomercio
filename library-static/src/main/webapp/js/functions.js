function getJson(id, url, loading) {
	if (loading==undefined || loading==null || loading=='') {
		loading = false;
	}
	if (loading) {
		$.blockUI({ 
	        message: '<h3>Processando</h3>',
	        css: { 
	            border: 'none', 
	            padding: '15px', 
	            backgroundColor: '#000', 
	            '-webkit-border-radius': '10px', 
	            '-moz-border-radius': '10px', 
	            opacity: .5, 
	            color: '#fff' 
	        } 
		});
	}
	
	url = insertParam(url, "tk", new Date().getTime().toString());
	
	$.getJSON( url,  function(data) {
		callback(id, data);
		if (loading) {
			$.unblockUI();
		}
	});
}

function getJson2(id, url, loading) {
	if (loading==undefined || loading==null || loading=='') {
		loading = false;
	}
	
	if (loading) {
		$.blockUI({ 
	        message: '<h3>Processando</h3>',
	        css: { 
	            border: 'none', 
	            padding: '15px', 
	            backgroundColor: '#000', 
	            '-webkit-border-radius': '10px', 
	            '-moz-border-radius': '10px', 
	            opacity: .5, 
	            color: '#fff' 
	        } 
		});
	}
	
	url = insertParam(url, "tk", new Date().getTime().toString());
	
	$.getJSON( url,  function(data) {
		callback2(id, data);
		if (loading) {
			$.unblockUI();
		}
	});
}

function postJson(id, url, params, loading) {
	if (loading==undefined || loading==null || loading=='') {
		loading = false;
	}
	if (loading) {
		$.blockUI({ 
	        message: '<h3>Processando</h3>',
	        css: { 
	            border: 'none', 
	            padding: '15px', 
	            backgroundColor: '#000', 
	            '-webkit-border-radius': '10px', 
	            '-moz-border-radius': '10px', 
	            opacity: .5, 
	            color: '#fff' 
	        } 
		});
	}
	
	$.post(url, params, function(data){
		callback(id, data);
		if (loading) {
			$.unblockUI();
		}
	}, "json");
}

function insertParam(url, key, value) {
	if (url.indexOf("?")==-1) {
		url += '?'+key+'='+value;
	} else {
		if (url.indexOf(key+'=')==-1) {
			url += '&'+key+'='+value;
		} else {
			param = url.split('?')[1].split('&');
			url = url.split('?')[0];
			for (var i=0; i<param.length; i++) {
				keyParam = param[i].split('=');
				if (keyParam[0]==key) {
					url = insertParam(url, key, value);
				} else {
					url = insertParam(url, keyParam[0], keyParam[1]);
				}
			}
		}
	}

    return url;
}

function errorData(data) {
	var idAlert = "#box-alert";
	if (!data.status) {
		if ($(idAlert)!=null && $(idAlert)!=undefined) {
			var msg = "";
			$.each(data.messageError, function(key, val) {
				if (msg != '') {
					msg = msg + "<br>" + val;
				} else {
					msg = val;
				}
			});
			$(idAlert).html(getErrorMsg(msg));
			$(".alert").alert();
		}
	}
}

function getErrorMsg(msg) {
	return "<div class=\"alert\" style=\"margin-top: 20px;\"><button type=\"button\" class=\"close\" data-dismiss=\"alert\">×</button><strong>Atenção!</strong> "+msg+"</div>";
}

function getURLParameter(name) {
    return decodeURI(
        (RegExp(name + '=' + '(.+?)(&|$)').exec(location.search)||[,null])[1]
    );
}

function getLblOrderStatus(id) {
	if (id==1 || id=='1') {
		return "Em aberto";
	} else if (id==2 || id=='2') {
		return "Aguardando pagamento";
	} else if (id==3 || id=='3') {
		return "Enviado (em trânsito)";
	} else if (id==4 || id=='4') {
		return "Concluído";
	} else if (id==5 || id=='5') {
		return "Cancelado";
	} 
}

function getIdOrderStatus(id) {
	if (id=="Em aberto") {
		return '1';
	} else if (id=='Aguardando pagamento') {
		return "2";
	} else if (id=='Enviado (em trânsito)') {
		return "3";
	} else if (id=='Concluído') {
		return "4";
	} else if (id=='Cancelado') {
		return "5";
	} 
}

function getQtdOrderStatus() {
	return 5;
}

function convertMoeda(valDouble) {
	var newV = valDouble.toString();
	if (newV.indexOf('.')==-1) {
		newV = newV+',00';
	} else {
		newV = newV.replace('.', ',');
		var m = newV.split(',');
		if (m[1].length>2) {
			var nV = ((m[1].substring('0', '2')*1) + 1);
			newV = m[0] + "," + nV.toString();
		} else if (m[1].length<2) {
			newV = m[0] + "," + m[1] + "0";
		} else {
			newV = m[0] + "," + m[1];
		}
		
	}
	
	return newV;
}

function getDomain() {
    var url = location.href;
    url = url.split("/");
    
    return url[2];
}

function getAddress(cep) {
	$.getScript("http://cep.republicavirtual.com.br/web_cep.php?formato=javascript&cep="+cep, function() {
		
		if (resultadoCEP["tipo_logradouro"] != '') {
			if (resultadoCEP["resultado"]) {
				var address = new Array();
				address['logradouro'] = unescape(resultadoCEP["logradouro"]);
				address['bairro'] = unescape(resultadoCEP["bairro"]);
				address['cidade'] = unescape(resultadoCEP["cidade"]);
				address['estado'] = unescape(resultadoCEP["uf"]);
				
				return address;
			}
					
		}
		
    });
	
	return null;
}

function modal(conteudo) {
	$('div#myModalGeneric').modal('hide');
	$('div#myModalGeneric').remove();
	
	var html = 	'<div id="myModalGeneric" class="modal hide fade" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">';
	html += 		'<div class="modal-body">';
	html += 			conteudo;
	html += 		'</div>';
	html += 		'<div class="modal-footer">';
	html += 			'<button class="btn" data-dismiss="modal" aria-hidden="true">Fechar</button>';
	html +=     	'</div>';
	html += 	'</div>';
	
	$('body').append(html);
	$('div#myModalGeneric').modal('show');
}

function normalizeStringToURL(texto) {
	texto = texto.toLowerCase();
	
	texto = texto.replace(".", "-");
	texto = texto.replace(",", "-");
	texto = texto.replace(";", "-");
	texto = texto.replace(":", "-");
	texto = texto.replace(" ", "-");
	texto = texto.replace("~", "-");
	texto = texto.replace("^", "-");
	texto = texto.replace("]", "-");
	texto = texto.replace("}", "-");
	texto = texto.replace("[", "-");
	texto = texto.replace("{", "-");
	texto = texto.replace("'", "-");
	texto = texto.replace("`", "-");
	texto = texto.replace("=", "-");
	texto = texto.replace("+", "-");
	texto = texto.replace("_", "-");
	texto = texto.replace(")", "-");
	texto = texto.replace("(", "-");
	texto = texto.replace("*", "-");
	texto = texto.replace("&", "-");
	texto = texto.replace("%", "-");
	texto = texto.replace("$", "-");
	texto = texto.replace("#", "-");
	texto = texto.replace("@", "-");
	texto = texto.replace("!", "-");
	texto = texto.replace("'", "-");
	texto = texto.replace("\"", "-");
	texto = texto.replace("\\", "-");
	texto = texto.replace("|", "-");
	texto = texto.replace("/", "-");
	texto = texto.replace("<", "-");
	texto = texto.replace(">", "-");
	texto = texto.replace("?", "-");
	
	texto = texto.replace("ã", "a");
	texto = texto.replace("á", "a");
	texto = texto.replace("â", "a");
	texto = texto.replace("à", "a");
	
	texto = texto.replace("ẽ", "e");
	texto = texto.replace("é", "e");
	texto = texto.replace("ê", "e");
	texto = texto.replace("è", "e");
	
	texto = texto.replace("ĩ", "i");
	texto = texto.replace("í", "i");
	texto = texto.replace("î", "i");
	texto = texto.replace("ì", "i");
	
	texto = texto.replace("õ", "o");
	texto = texto.replace("ó", "o");
	texto = texto.replace("ô", "o");
	texto = texto.replace("ò", "o");
	
	texto = texto.replace("ũ", "u");
	texto = texto.replace("ú", "u");
	texto = texto.replace("û", "u");
	texto = texto.replace("ù", "u");
	
	texto = texto.replace("ç", "c");
	
	return texto;
}

function numberAleatorio(min,max){
    numPossibilidades = max - min;
    aleat = Math.random() * numPossibilidades;
    aleat = Math.floor(aleat);
    return parseInt(min) + aleat;
} 