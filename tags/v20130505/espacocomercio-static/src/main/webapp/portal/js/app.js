jQuery(document).ready(function($) {	
	
	$('#forgot-password').toggle(function() {
			$('#forgot-form').slideDown('fast');
			$('#forgot-password i').removeClass('icon-arrow-down');
			$('#forgot-password i').addClass('icon-arrow-up');
		}, function() {
			$('#forgot-form').slideUp('fast');
			$('#forgot-password i').removeClass('icon-arrow-up');
			$('#forgot-password i').addClass('icon-arrow-down');
		});
	
	$('#form-trigger').keyup(function() {
		$('#create-account-button').removeAttr('disabled');
		
	});
	
	$('input, textarea').placeholder();
	
});

function loadMenu(page) {
	$('div#menuDiv').load('/ecommerce/portal/includes/menu.html', function() {
		$('li#'+page).attr('class', 'active');
	});
}

function loadSocial() {
	$('div#socialDiv').load('/ecommerce/portal/includes/social.html');
}

function loadInfo() {
	$('div#infoDiv').load('/ecommerce/portal/includes/info.html');
}