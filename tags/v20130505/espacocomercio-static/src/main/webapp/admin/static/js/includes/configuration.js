function loadConfiguration() {
	setMenuActive('config');
	loading('contentAll');
	$('#contentAll').load('includes/configuration.html?tk='+new Date().getTime(), function() {
		$('#box-tab-Category').css('margin-left', '10px');
		$('span#spanTitleEcommerce').text(titlePage);
		
		$.getJSON('/ecommerce-web/admin/site/load?tk='+new Date().getTime(), function(data) {
			$('input[name=name]').val(data.generic[0].name);
			$('input[name=description]').val(data.generic[0].description);
			$('input[name=facebook]').val(data.generic[0].facebook);
			$('input[name=email]').val(data.generic[0].email);
			$('input[name=gmailpass]').val(data.generic[0].gmailPass);
			$('img#iposition').attr('src', data.generic[0].logo);
			$('input[name=logo]').val(data.generic[0].logo);
			
			$('input#gmailDaLoja').val(data.generic[1]+"@espacocomercio.com.br");
			$('span#nameSiteWeb').text(data.generic[1]);
			
			$('#uploadFile').live('change', function(){
				//$('#visualizar').html('<img src="ajax-loader.gif" alt="Enviando..."/> Enviando...');
				$('#form_upload').ajaxForm({
					dataType: 'script',
					target:'#upload_target'
				}).submit();
		     });
		});
		
		activePlugins();
	});
}

function generateExibicao() {
	$('span#nameSiteWeb').text(normalizeStringToURL($('input[name=name]').val()));
}

function uploadLogo() {
	$('input#uploadFile').click();
}

function uploadSetLogo(urlImg) {
	$('input[name=changeLogo]').val('true');
	$('#iposition').attr('src', urlImg);
	$('input[name=logo]').val(urlImg);
}

function alteracaodeloja(data) {
	alert("Loja virtual alterada com sucesso.", true);
}

function formAdicionarUsuario() {
	$('li#liadicionarusuario').show();
	$('#btnadicionarusuario').click();
	preenchePermission();
}

function hideOpcoesMenu() {
	$('li#liadicionarusuario').hide();
	$('li#lieditarusuario').hide();
}

function submitFormAddUsuario() {
	var pass = $('form#formCadastroUsuario').find('input[name=password]').val();
	var passConf = $('form#formCadastroUsuario').find('input[name=passwordConfirm]').val();
	if (pass!='' && pass==passConf) {
		postJson('adicionarusuario', '/ecommerce-web/admin/user/save', $('form#formCadastroUsuario').serialize());
	} else {
		alert('As senhas digitadas estão diferentes ou são inválidas.');
	}
}

function cadastrodeusuario(data) {
	alert("Usuário criado com sucesso.");
	$('button[type="reset"]').click();
}

function preenchePermission() {
	var html = '';
	for (var i=1; i<=10; i++) {
		if (getLblPermissions(i)!='') {
			html += '<option value="'+i.toString()+'">'+getLblPermissions(i)+'</option>';
		}
	}
	
	$('select[name="permission"]').html(html);
}

function formEditarUsuario(id) {
	$('li#lieditarusuario').show();
	$('#btneditarusuario').click();
	preenchePermission();
	
	$.getJSON('/ecommerce-web/admin/user/load?id='+id, function(data) {
		$('form#formEditarCadastroUsuario').find('input[name=user]').val(data.generic.user);
		$('form#formEditarCadastroUsuario').find('select[name=permission]').val(data.generic.permission);
		$('form#formEditarCadastroUsuario').find('input[name=id]').val(data.generic.id);
	});
}

function alterPasswordEditarUsuario() {
	$('form#formEditarCadastroUsuario').find("input[type=password]").val('');
	if ($('form#formEditarCadastroUsuario').find('input[type="checkbox"]').is(':checked')) {
		$('form#formEditarCadastroUsuario').find("input[type=password]").attr("disabled",false);
	} else {
		$('form#formEditarCadastroUsuario').find("input[type=password]").attr("disabled",true);
	}
}

function submitFormEditUsuario() {
	if ($('form#formEditarCadastroUsuario').find('input[type="checkbox"]').is(':checked')) {
		var pass = $('form#formEditarCadastroUsuario').find('input[name=password]').val();
		var passConf = $('form#formEditarCadastroUsuario').find('input[name=passwordConfirm]').val();
		if (pass!='' && pass==passConf) {
			postJson('editarusuario', '/ecommerce-web/admin/user/save', $('form#formEditarCadastroUsuario').serialize());
		} else {
			alert('As senhas digitadas estão diferentes ou são inválidas.');
		}
	} else {
		postJson('editarusuario', '/ecommerce-web/admin/user/save', $('form#formEditarCadastroUsuario').serialize());
	}
}

function editardeusuario() {
	alert("Usuário alterado com sucesso.");
	$('#btnConsultaUsuario').click();
}