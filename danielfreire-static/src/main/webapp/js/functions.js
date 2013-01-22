function getJson(id, url) {
	url = insertParam(url, "tk", new Date().getTime().toString());
	
	$.getJSON( url,  function(data) {
		callback(id, data);
	});
}

function getJson2(id, url) {
	url = insertParam(url, "tk", new Date().getTime().toString());
	
	$.getJSON( url,  function(data) {
		callback2(id, data);
	});
}

function postJson(id, url, params) {
	$.post(url, params, function(data){
		callback(id, data);
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