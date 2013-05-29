function getURLParameter(name) {
    return decodeURI(
        (RegExp(name + '=' + '(.+?)(&|$)').exec(location.search)||[,null])[1]
    );
}

function forgotPassword() {
	$.getJSON( serviceContext+'/forgotPassword', $('form#formLogin').serialize()+"&contextid="+siteContext, function(data) {
		if (data.status) {
			$('#btnClose').click();
			alert('Sua nova senha foi enviada com sucesso para o seu e-mail.');
		} else {
			$('span#spanErrorLogin').text(data.messageError.generic);
			$('div#divErrorLogin').show();
		}
	});
}

function login(isOrder) {
	if (isOrder==undefined || isOrder==null || isOrder=='') {
		isOrder = false;
	}
	$.post(serviceContext+'/login', $('form#formLogin').serialize()+'&contextid='+siteContext, function(data){
		if (data.status) {
			if (isOrder) {
				paymentOrder();
			} else {
				location.href='../client/';
			}
		} else {
			$('span#spanErrorLogin').text(data.messageError.generic);
			$('div#divErrorLogin').show();
		}
	}, "json");
}

$('button#btnAccessLogin').click(function () {
	login(getURLParameter('isOrder'));
});

$('a#btnToForgotPassword').click(function () {
	forgotPassword();
});

$('button#btnToFormMyData').click(function () {
	myFormData();
});