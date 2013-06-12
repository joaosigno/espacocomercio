function loadConfiguration() {
	setMenuActive('config');
	loading('contentAll');
	$('#contentAll').load('includes/configuration.html?tk='+new Date().getTime(), function() {
		$('#lojaconfig-div').load('includes/configuration/lojavirtual.html?tk='+new Date().getTime(), function() {
			$('#redessociais-div').load('includes/configuration/redessociais.html?tk='+new Date().getTime(), function() {
				$('#box-tab-Category').css('margin-left', '10px');
				$('span#spanTitleEcommerce').text(titlePage);
				
				$.getJSON('/ecommerce-web/admin/site/load?tk='+new Date().getTime(), function(data) {
					$('form#formLojaVirtual').find('input[name=name]').val(data.generic[0].name);
					$('form#formLojaVirtual').find('textarea[name=description]').val(data.generic[0].description);
					$('form#formRedessociais').find('input[name=facebook]').val(data.generic[0].facebook);
					$('form#formRedessociais').find('input[name=googleplus]').val(data.generic[0].googleplus);
					$('form#formRedessociais').find('input[name=twitter]').val(data.generic[0].twitter);
					$('form#formRedessociais').find('input[name=youtube]').val(data.generic[0].youtube);
					$('form#formLojaVirtual').find('input[name=email]').val(data.generic[0].email);
					$('form#formLojaVirtual').find('input[name=gmailpass]').val(data.generic[0].gmailPass);
					$('form#formLojaVirtual').find('img#iposition').attr('src', data.generic[0].logo);
					$('form#formLojaVirtual').find('input[name=logo]').val(data.generic[0].logo);
					
					$('form#formLojaVirtual').find('input#gmailDaLoja').val(data.generic[1]+"@espacocomercio.com.br");
					$('form#formLojaVirtual').find('span#nameSiteWeb').text(data.generic[1]);
					
					$('#uploadFile').live('change', function(){
						//$('#visualizar').html('<img src="ajax-loader.gif" alt="Enviando..."/> Enviando...');
						$('#form_upload').ajaxForm({
							dataType: 'script',
							target:'#upload_target'
						}).submit();
				     });
				});
				
				activePlugins();
				$('#redessociais-div').hide();
			});
		});
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
	alert("Loja virtual alterada com sucesso.");
	loadConfiguration();
}

function alteracaoderedessociais() {
	alert("Redes sociais alteradas com sucesso.");
	loadConfiguration();
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

function loadFormaPgto() {
	loading('formasdepagamento-div');
	$.getJSON('/ecommerce-web/admin/payment/load?tk='+new Date().getTime(), function(data) {
		$('#formasdepagamento-div').load('includes/configuration/formasdepagamento.html?tk='+new Date().getTime(), function() {
			$('form#formFormadePagamento').find('input[name=name]').val(data.generic.name);
			$('form#formFormadePagamento').find('input[name=description]').val(data.generic.description);
			$('form#formFormadePagamento').find('input[name=url]').val(data.generic.url);
			$('#lojaconfig-div').html('');
		});
	});
}