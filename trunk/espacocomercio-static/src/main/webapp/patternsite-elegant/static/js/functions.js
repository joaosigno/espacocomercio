$(function() {
	
	loadSite();
	
});

var serviceContext = "/ecommerce-web";
var siteContext;
var siteIdCripto;
var gotoLogin = 0;

function loadSite() {
	$.getJSON('../data/site.json?tk='+new Date().getTime(), function(data) {
		siteIdCripto = data.idc;
		siteContext = data.nameNormalize;
		
		$('a#ancorToMyCart').click(function(){
			myCart();
		});
		$('a#ancorToLogin').click(function(){
			myData();
		});
		$('a#ancorToMyData').click(function() {
			myFormData();
		});
		
		loadSession();
		
		if (data.logo!='') {
			$('a#ancorToHome').html('<img src="'+data.logo+'" alt="'+data.description+'" title="'+data.description+'">');
		} else {
			$('a#ancorToHome').html(data.name+'<span class="color">.</span>');
		}
		$('a#titleFooter').html(data.name);
		$('a#plataformFooter').html(data.plataform.name);
		$('a#plataformFooter').attr('href',data.plataform.url);
		
		var htmlSocial = '';
		if (data.googleplus!=undefined && data.googleplus!=null && data.googleplus!='') {
			htmlSocial += '<a href="'+data.googleplus+'" target="_blank"><img src="../static/img/google_b.png" alt="'+data.googleplus+'"></a>';
		}
		if (data.twitter!=undefined && data.twitter!=null && data.twitter!='') {
			htmlSocial += '<a href="'+data.twitter+'" target="_blank"><img src="../static/img/twitter_b.png" alt="'+data.twitter+'"></a>';
		}
		if (data.facebook!=undefined && data.facebook!=null && data.facebook!='') {
			htmlSocial += '<a href="'+data.facebook+'" target="_blank"><img src="../static/img/facebook_b.png" alt="'+data.facebook+'"></a>';
		}
		if (data.youtube!=undefined && data.youtube!=null && data.youtube!='') {
			htmlSocial += '<a href="'+data.youtube+'" target="_blank"><img src="../static/img/youtube_b.png" alt="'+data.youtube+'"></a>';
		}
		$('div#socialDiv').html(htmlSocial);
	});
	
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
				$('span#spanEntry').text(' Sair');
				$('span#spanMyData').text(' Meus dados');
			} else {
				$('span#spanEntry').text(' Entrar');
				$('span#spanMyData').text(' Quero me cadastrar');
			}
			
			if (data.generic.cart!=null && data.generic.cart.length>0) {
				$('span#spanQtdCart').text(data.generic.cart.length);
			} 
		} else {
			alert(data.messageError.generic);
		}
	});
}

function myCart() {
	$.getJSON( serviceContext+'/session?sid='+siteIdCripto+'&'+new Date().getTime(),  function(data) {
		if (data.status) {
			if (data.generic.client!=null) {
				var h = $(window).height() - ($(window).height() * 0.45);
				var w = $(window).width() - ($(window).width() * 0.20);
				$('div#divModalMyCart').load('../static/html/mycart.html', function() {
					$.getJSON('../data/payment.json?tk='+new Date().getTime(), function(dataPay) {
						$('iframe[name=mycart_iframe]').attr('width', w);
						$('iframe[name=mycart_iframe]').attr('height', h);
						$('iframe[name=mycart_iframe]').attr('src', 'https://www.bcash.com.br/checkout/car/?'+
								'email_loja='+dataPay.name+
								'&email='+data.generic.client.user+
								'&nome='+data.generic.client.name+
								'&cpf='+data.generic.client.cpf+
								'&telefone='+data.generic.client.phone2+
								'&celular='+data.generic.client.phone1+
								'&endereco=' + (data.generic.client.addressStreet.toString().split(' - ').length>1 ? data.generic.client.addressStreet.toString().split(' - ')[0] : data.generic.client.addressStreet) +
								'&complemento='+data.generic.client.addressComplement+
								'&bairro='+ (data.generic.client.addressStreet.toString().split(' - ').length>1 ? data.generic.client.addressStreet.toString().split(' - ')[1] : '') +
								'&cidade='+(data.generic.client.addressCity.toString().split(' / ').length>1 ? data.generic.client.addressCity.toString().split(' / ')[0] : data.generic.client.addressCity)+
								'&estado='+(data.generic.client.addressCity.toString().split(' / ').length>1 ? data.generic.client.addressCity.toString().split(' / ')[1] : '')+
								'&cep='+data.generic.client.addressZipcode);
						$('#divModalMyCart').modal('show');
						$('#divModalMyCart').css('width', w+'px');
						$('#divModalMyCart').css('margin-left', '-'+(($(window).width() * 0.20)/2)+'px');
						$('#divModalMyCart').css('left', '20%');
					});
				});
			} else {
				layerData(1);
			}
		} else {
			alert(data.messageError.generic);
		}
	});
}

function linksNonSession() {
	$('a#ancorToMyData').attr('data-toggle', 'modal');
	$('a#ancorToMyData').attr('href', '#divModal');
	$('a#ancorToMyData').attr('data-toggle', 'modal');
	$('a#ancorToMyData').click(function() {
		myFormData();
	})
}

function customizeCaptcha() {
	$('input#recaptcha_response_field').removeAttr('style');
	$('#recaptcha_logo').remove();
}

function myData() {
	$.getJSON( serviceContext+'/session?sid='+siteIdCripto+'&'+new Date().getTime(),  function(data) {
		if (data.status) {
			if (data.generic.client!=null) {
				logout();
			} else {
				layerData(0);
			}
		} else {
			alert(data.messageError.generic);
		}
	});
}

function layerData(id) {
	gotoLogin = id;
	$('div#divModal').load('../static/html/form-to-login.html');
	$('div#divModal').modal('show');
}

function myFormData() {
	$.getJSON( serviceContext+'/session?sid='+siteIdCripto+'&'+new Date().getTime(),  function(data) {
		if (data.status) {
			if (data.generic.client!=null) {
				location.href="../client";
			} else {
				$('div#divModal').load('../static/html/form-cad-client.html', function() {
					Recaptcha.create("6LeE6eESAAAAADeLcwnmlNXRR3Rxp1jb8otcu-yc", 'recaptcha_div', {
			            theme: "clean",
			            callback: customizeCaptcha
			        });
				});
				$('div#divModal').modal('show');
			}
		}
	});
}

function logout() {
	$.post(serviceContext+'/logoutSession', '', function(data){
		if (data.status) {
			loadSession();
		} else {
			alert(data.messageError.generic);
		}
	}, "json");
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