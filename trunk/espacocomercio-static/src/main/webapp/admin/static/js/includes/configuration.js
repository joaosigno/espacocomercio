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