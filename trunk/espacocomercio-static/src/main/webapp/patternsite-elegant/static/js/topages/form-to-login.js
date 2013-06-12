function forgotPassword() {
	$.getJSON( serviceContext+'/forgotPassword', $('form#formLogin').serialize()+"&contextid="+siteContext+'&sid='+siteIdCripto, function(data) {
		if (data.status) {
			$('#btnClose').click();
			alert('Sua nova senha foi enviada com sucesso para o seu e-mail.');
		} else {
			$('span#spanErrorLogin').text(data.messageError.generic);
			$('div#divErrorLogin').show();
		}
	});
}

function login() {
	$.post(serviceContext+'/login', $('form#formLogin').serialize()+'&contextid='+siteContext+'&sid='+siteIdCripto, function(data){
		if (data.status) {
			loadSession();
			$('div#divModal').modal('hide');
			if (gotoLogin==0) {
				location.href='../client/';
			} else if (gotoLogin==1) {
				myCart();
			} else if (gotoLogin==2) {
				paymentOrder();
			}
		} else {
			$('span#spanErrorLogin').text(data.messageError.generic);
			$('div#divErrorLogin').show();
		}
	}, "json");
}

$('button#btnAccessLogin').click(function () {
	login();
});

$('a#btnToForgotPassword').click(function () {
	forgotPassword();
});

$('button#btnToFormMyData').click(function () {
	myFormData();
});