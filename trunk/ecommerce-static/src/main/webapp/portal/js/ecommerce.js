$("#btnLogin").click(function(){
	$(this).button('loading');
	submit();
});

function callback(id, data) {
	if (!data.status) {
		$("#btnLogin").button('reset');
		errorData(data);
	} else {
		if (data.generic!=undefined && data.generic!=null) {
			var html = '<h3>Escolha o ecommerce que deseja acessar:</h3><br><select>';
			$.each(data.generic, function(key, val) {
				html += '<option value="'+val.id+'">'+val.name+'</option>'
			});
			html+='</select><br><button class="btn btn-primary" onclick="submit();">Acessar</button>';
			
			modal(html);
		} else {
			location.href="/ecommerce/admin";
		}
	}
}

$('.form-signin-heading').html('<img alt="" src="img/iconeSeguro.jpg"> '+document.domain);

if (getURLParameter("error")!=null && getURLParameter("error")!=undefined && getURLParameter("error")!='' && getURLParameter("error")!='null') {
	$("#box-alert").html(getErrorMsg(getURLParameter("error")));
}

function submit() {
	if ($('select') != undefined) {
		$('input[name=s]').val($('select').val());
	}
	postJson(0, "/ecommerce-web/admin/login", $('form').serialize());
}