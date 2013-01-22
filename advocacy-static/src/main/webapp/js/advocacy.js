$("#btnLogin").click(function(){
	$(this).button('loading');
	postJson(0, "/advocacy-web/login", $('form').serialize());
});

function callback(id, data) {
	if (!data.status) {
		$("#btnLogin").button('reset');
		errorData(data);
	} else {
		location.href='/advocacy/admin';
	}
}

$('.form-signin-heading').html('<img alt="" src="img/iconeSeguro.jpg"> '+document.domain);

if (getURLParameter("error")!=null && getURLParameter("error")!=undefined && getURLParameter("error")!='' && getURLParameter("error")!='null') {
	$("#box-alert").html(getErrorMsg(getURLParameter("error")));
}