$("#btnLogin").click(function(){
	$(this).button('loading');
	postJson(0, "/ecommerce-web/login", $('form').serialize());
});

function callback(id, data) {
	if (!data.status) {
		$("#btnLogin").button('reset');
		errorData(data);
	} else {
		if (data.generic.tk!=undefined && data.generic.tk!=null) {
			location.href=insertParam(data.generic.to, 'tk', data.generic.tk);
		} else {
			location.href=data.generic;
		}
	}
}

$('.form-signin-heading').html('<img alt="" src="img/iconeSeguro.jpg"> '+document.domain);

if (getURLParameter("error")!=null && getURLParameter("error")!=undefined && getURLParameter("error")!='' && getURLParameter("error")!='null') {
	$("#box-alert").html(getErrorMsg(getURLParameter("error")));
}