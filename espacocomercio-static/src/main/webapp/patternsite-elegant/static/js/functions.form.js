$('input').keydown(function() {
	$('span[data-toggle="tooltip"]').tooltip('hide');
});

$('input[name="name"]').blur(function() {
	changeValid("Name", validName($(this).val()));
});

$('input[name="phone1"]').blur(function() {
	changeValid("Phone", validPhone($(this).val()));
});

$('input[name="user"]').blur(function() {
	changeValid("Mail", validUser($(this).val()));
});

$('input[name="userConfirm"]').blur(function() {
	changeValid("Mail", validUserConfirm($(this).val()));
});

$('input[name=addressZipcode]').blur(function() {
	changeValid("Address", validCep($(this).val()));
});

$('input[name=addressNumber]').blur(function() {
	changeValid("Address", validAddressNumber($(this).val()));
});

$('input[name=password]').blur(function() {
	changeValid("Password", validPassword($(this).val()));
});

$('input[name=passwordConfirm]').blur(function() {
	changeValid("Password", validPasswordConfirm($(this).val()));
});

$('#svnButtonFormCad').click(function() {
	submitFormCadUser();
});

$('input[name="phone1"]').keypress(function() {
	mascara( this, mtel );
});

$('input[name="phone2"]').keypress(function() {
	mascara( this, mtel );
});

$('input[name=addressZipcode]').keypress(function() {
	mascara( this, mcep );
});

function validName(v) {
	var msg = '';
	if ($.trim(v)=='' || v.split(' ').length<2) {
		msg = 'Preencha corretamente com o seu nome completo.';
	}
	
	return msg;
}

function validPhone(v) {
	var msg = '';
	if ($.trim(v)=='' || v.length<14) {
		msg = 'Preencha corretamente seu número de celular.';
	}
	
	return msg;
}

function validUser(v) {
	var msg = '';
	if (!isEmail(v)) {
		msg = 'Preencha corretamente o seu endereço de email.';
	}
	
	return msg;
}

function validUserConfirm(v) {
	var msg = '';
	if ($.trim(v)=='') {
		msg = 'Preencha corretamente o seu endereço de email.';
	} else if (v!=$('input[name="user"]').val()) {
		msg = 'A confirmação falhou, verifique se os emails digitandos são iguais.';
	}
	
	return msg;
}

function validCep(v) {
	var msg = '';
	if ($.trim(v)=='' || !isCEP(v)) {
		msg = 'Preencha corretamente o seu CEP.';
	} else  {
		$.getScript("http://cep.republicavirtual.com.br/web_cep.php?formato=javascript&cep="+v, function() {
			
			if (resultadoCEP["resultado"]=='1') {
				
				var address = resultadoCEP["tipo_logradouro"] + " " + resultadoCEP["logradouro"] + " - " + resultadoCEP["bairro"];
				var uf = resultadoCEP["cidade"] + " / " + resultadoCEP["uf"];

				$('input[name=addressStreet]').val(unescape(address));
				$('input[name=addressCity]').val(unescape(uf));
				
			} else {
				
				$('input[name=addressStreet]').removeAttr("disabled");
				$('input[name=addressCity]').removeAttr("disabled");
			}
			
	    });
	}
	
	return msg;
}

function validAddressNumber(v) {
	var msg = '';
	if ($.trim(v)=='') {
		msg = 'Preencha corretamente o seu número de endereço.';
	}
	
	return msg;
}

function validPassword(v) {
	var msg = '';
	if ($.trim(v)=='') {
		msg = 'Preencha com uma senha válida que contenha números e letras.';
	}
	
	return msg;
}

function validPasswordConfirm(v) {
	var msg = '';
	if ($.trim(v)=='' || v!=$('input[name=password]').val()) {
		msg = 'As senhas digitadas não conferem, tente novamente.';
	}
	
	return msg;
}

function changeValid(id, msg) {
	if (msg=='') {
		$('#ok'+id).show();
		$('#error'+id).hide();
		$('#error'+id).tooltip('hide');
	} else {
		$('#ok'+id).hide();
		$('#error'+id).attr('data-original-title', msg);
		$('#error'+id).show();
		$('#error'+id).tooltip('show');
	}
}

/* Máscaras ER */
function mascara(o,f){
    v_obj=o
    v_fun=f
    setTimeout("execmascara()",1)
}
function execmascara(){
    v_obj.value=v_fun(v_obj.value)
}
function mtel(v){
    v=v.replace(/\D/g,"");
    v=v.replace(/^(\d{2})(\d)/g,"($1) $2");
    v=v.replace(/(\d)(\d{4})$/,"$1-$2");
    return v;
}
function mcep(v){
    v=v.replace(/\D/g,"");
    v=v.replace(/(\d)(\d{3})$/,"$1-$2");
    return v;
}
function id( el ){
	return document.getElementById( el );
}

function isEmail(email){
    var exclude=/[^@\-\.\w]|^[_@\.\-]|[\._\-]{2}|[@\.]{2}|(@)[^@]*\1/;
    var check=/@[\w\-]+\./;
    var checkend=/\.[a-zA-Z]{2,3}$/;
    if(((email.search(exclude) != -1)||(email.search(check)) == -1)||(email.search(checkend) == -1)){return false;}
    else {return true;}
}

function isCEP(strCEP) {
    var objER = /^[0-9]{5}-[0-9]{3}$/;

    strCEP = $.trim(strCEP);
    if(strCEP.length > 0) {
        if(objER.test(strCEP))
            return true;
        else
            return false;
    }
    else
        return false;
}

function submitFormCadUser() {
	var m1 = validName($('input[name="name"]').val());
	var m2 = validPhone($('input[name="phone1"]').val());
	var m3 = validUser($('input[name="user"]').val());
	var m4 = validUserConfirm($('input[name="userConfirm"]').val());
	var m5 = validCep($('input[name="addressZipcode"]').val());
	var m6 = validAddressNumber($('input[name="addressNumber"]').val());
	var m7 = validPassword($('input[name="password"]').val());
	var m8 = validPasswordConfirm($('input[name="passwordConfirm"]').val());
	var m9 = $.trim($('#recaptcha_response_field').val());
	var m10 = $('form#formCadClient').find('input[name=privacity]').is(":checked");
	
	if (m1=='' && m2=='' && m3=='' && m4=='' && m5=='' && m6=='' && m7=='' && m8=='' && m9!='' && m10) { 
		$.post('/ecommerce-web/client/new', $('form#formCadClient').serialize(), function(data){
			
			if (data.status) {
				$('form#formCadClient').html('<div style="text-align: center"><h4>Cadastro realizado com sucesso.</h4>Você receberá no seu e-mail um link para ativação do cadastro, para comprar será necessário a ativação da conta.</div>');
				$('#svnButtonFormCad').attr('onclick', '');
				$('#svnButtonFormCad').text('Ok');
				$('#svnButtonFormCad').attr('data-dismiss', 'modal');
			} else {
				$.each(data.messageError, function(key, val) {
					changeValid(key, val);
				});
			}
			
		}, "json");
	} else {
		changeValid("Name", m1);
		changeValid("Phone", m2);
		changeValid("Mail", m4);
		changeValid("Mail", m3);
		changeValid("Address", m6);
		changeValid("Address", m5);
		changeValid("Password", m8);
		changeValid("Password", m7);
		if (m9=='') {
			$('div.recaptcha_input_area').append('<span id="errorCaptcha" class="badge badge-important hide" data-toggle="tooltip" data-placement="left"><i class="icon-remove"></i></span>');
			changeValid("Captcha", "Preencha o conteúdo da imagem acima.");
		}
		if (!m10) {
			changeValid("Terms", "Para concluir o cadastro é necessário confirmar a aceitação dos termos.");
		}
	}
	
	
}