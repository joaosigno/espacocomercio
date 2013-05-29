$(function() {
	
	loadSession();
	loadMenu();
	
});

var serviceContext = "/ecommerce-web";
var siteIdCripto = "${site.id.cripto}";
var siteContext = "${site.nameNormalize}";

function loadMenu() {
	$.getJSON( '../data/menu.json?tk='+new Date().getTime(),  function(data) {
		var html = '<li><a href="../home">Home</a></li>';
		$.each(data, function(key, val) {
			html += '<li id="'+val.keyUrl+'"><a href="../department/'+val.keyUrl+'.html">'+val.name+'</a></li>';
		});
		$('ul#ulMenu').html(html);
	}).error(function() { 
		$('ul#ulMenu').html('<li><a href="../home">Home</a></li>');
	});
	
}

function loadSession() {
	$.getJSON( serviceContext+'/session?sid='+siteIdCripto+'&'+new Date().getTime(),  function(data) {
		if (data.status) {
			if (data.generic.client!=null) {
				
				$('a#ancorToMyData').click(function(){
					location.href="../client";
				});
				$('a#ancorToLogin').click(function(){
					logout();
				});
				$('span#spanEntry').text(' Sair');
				$('span#spanMyData').text(' Meu cadastro');
				
			} else {
				
				linksNonSession();
			}
			
			if (data.generic.cart!=null && data.generic.cart.length>0) {
				$('span#spanQtdCart').text(data.generic.cart.length);
				$('a#ancorToMyCart').click(function(){
					location.href='../mycart';
				});
			} 
		} else {
			alert(data.messageError.generic);
			linksNonSession();
		}
	}).error(function() { 
		linksNonSession();
	});
}

function linksNonSession() {
	$('a#ancorToMyData').attr('data-toggle', 'modal');
	$('a#ancorToMyData').attr('href', '#divModal');
	$('a#ancorToMyData').attr('data-toggle', 'modal');
	$('a#ancorToMyData').click(function() {
		myFormData();
	})
	
	$('a#ancorToLogin').attr('data-toggle', 'modal');
	$('a#ancorToLogin').attr('href', '#divModal');
	$('a#ancorToLogin').attr('data-toggle', 'modal');
	$('a#ancorToLogin').click(function () {
		myData(false);
	});
}

function customizeCaptcha() {
	$('input#recaptcha_response_field').removeAttr('style');
	$('#recaptcha_logo').remove();
}

function myData(isOrder) {
	$('div#divModal').load('../static/html/form-to-login.html?isOrder='+isOrder);
}

function myFormData() {
	$('div#divModal').load('../static/html/form-cad-client.html', function() {
		Recaptcha.create("6LeE6eESAAAAADeLcwnmlNXRR3Rxp1jb8otcu-yc", 'recaptcha_div', {
            theme: "clean",
            callback: customizeCaptcha
        });
	});
}

function logout() {
	$.post(serviceContext+'/logoutSession', '', function(data){
		if (data.status) {
			$('a#ancorToHome').click();
		} else {
			alert(data.messageError.generic);
		}
	}, "json");
}