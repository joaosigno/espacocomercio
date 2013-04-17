function loadConfiguration() {
	setMenuActive('config');
	loading('contentAll');
	$('#contentAll').load('includes/configuration.html', function() {
		$('#box-tab-Category').css('margin-left', '10px');
		$('span#spanTitleEcommerce').text(titlePage);
		
		$.getJSON('/ecommerce-web/admin/site/load?tk='+new Date().getTime(), function(data) {
			$('input[name=name]').val(data.generic[0].name);
			$('input[name=description]').val(data.generic[0].description);
			$('span#nameSiteWeb').text(data.generic[1]);
		});
		
		activePlugins();
	});
}

function generateExibicao() {
	$('span#nameSiteWeb').text(normalizeStringToURL($('input[name=name]').val()));
}