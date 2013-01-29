function loadHeaderGeneric(logoHtml, homeUrl) {
	$.getJSON( '/ecommerce/'+getPortalContext()+'/data/menu.json',  function(data) {
		var html = 	'<div class="row">';
		html += 		'<div class="span6 offset6">';
		html += 			'<small class="pull-right">';
		html += 				'<a id="aLinkMyCart" href="#" class="left-space"><i class="icon-shopping-cart"></i><span class="muted"> Meu Carrinho (<span id="spanQtdCart"></span>)</span></a>';
		html += 				'<a id="aLinkMyData" href="#" class="left-space"><i class="icon-align-justify"></i><span class="muted"> Meu Cadastro</span></a>';
		html += 				'<a id="aLinkMyDataModal" href="#" class="left-space hide"><!-- non value --></a>';
		html += 				'<a id="aLinkMyEntry" href="#" class="left-space"><i class="icon-user"></i><span class="muted" id="spanEntry"></span></a>';
		html += 			'</small>';
		html += 		'</div>';
		html += 	'</div>';
		
		html += 	'<div class="row">';
		html += 		'<div class="span6"><a href="/ecommerce/'+getPortalContext()+'">';
		html += 			logoHtml;
		html += 		'</a></div>';
		html += 		'<div class="span6">';
		html += 			'<form class="pull-right" style="padding-top: 45px;">';
		html += 				'<div class="input-append">';
		html += 					'<input class="span3" id="appendedInputButton" type="text" placeholder="Pesquisar produtos…">';
		html += 					'<button class="btn" type="button" onclick="loadProducts(\'\', $(\"#appendedInputButton\").val(), \'\');"><i class="icon-search"></i></button>';
		html += 				'</div>';
		html += 			'</form>';
		html += 		'</div>';
		html += 	'</div>';
		
		html += 	'<div class="row">';
		html += 		'<div class="span12">';
		html += 			'<div class="navbar">';
		html += 				'<div class="navbar-inner">';
		html += 					'<div class="container">';
		html += 						'<a class="btn btn-navbar" data-toggle="collapse" data-target=".nav-collapse">';
		html += 							'<span class="icon-bar"></span>';
		html += 							'<span class="icon-bar"></span>';
		html += 							'<span class="icon-bar"></span>';
		html += 						'</a>';
		html += 						'<div class="nav-collapse collapse">';
		html += 							'<ul class="nav">';
		html += 								'<li id="home"><a href="'+homeUrl+'">Home</a></li>';
		
		$.each(data, function(key, val) {
			if (getDomain()=='localhost:8080') {
				html += '<li id="'+val.keyUrl+'"><a href="/ecommerce/'+getPortalContext()+'/category/?key='+val.keyUrl+'">'+val.name+'</a></li>';
			} else {
				html += '<li id="'+val.keyUrl+'"><a href="/ecommerce/'+getPortalContext()+'/department/'+val.keyUrl+'">'+val.name+'</a></li>';
			}
		});
		
		html += 							'</ul>';
		html += 						'</div>';
		html += 					'</div>';
		html += 				'</div>';
		html += 			'</div>';
		html += 		'</div>';
		html += 	'</div>';
		
		html += 	'<div id="divModalFormCad" class="modal hide fade" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true"></div>';
		
		$('div#header').html(html);
		
		if (getURLParameter('key')!=undefined && getURLParameter('key')!=null && getURLParameter('key')!='' && getURLParameter('key')!='null') {
			$('li#'+getURLParameter('key')).attr('class', 'active');
		} else {
			$('li#home').attr('class', 'active');
		}
		
		loadSession();
	}).error(function() { 
		var html = 	'<div class="row">';
		html += 		'<div class="span6 offset6">';
		html += 			'<small class="pull-right">';
		html += 				'<a id="aLinkMyCart" href="#" class="left-space"><i class="icon-shopping-cart"></i><span class="muted"> Meu Carrinho (<span id="spanQtdCart"></span>)</span></a>';
		html += 				'<a id="aLinkMyData" href="#" class="left-space"><i class="icon-align-justify"></i><span class="muted"> Meu Cadastro</span></a>';
		html += 				'<a id="aLinkMyDataModal" href="#" class="left-space hide"><!-- non value --></a>';
		html += 				'<a id="aLinkMyEntry" href="#" class="left-space"><i class="icon-user"></i><span class="muted" id="spanEntry"></span></a>';
		html += 			'</small>';
		html += 		'</div>';
		html += 	'</div>';
		
		html += 	'<div class="row">';
		html += 		'<div class="span6"><a href="/ecommerce/'+getPortalContext()+'">';
		html += 			logoHtml;
		html += 		'</a></div>';
		html += 		'<div class="span6">';
		html += 			'<form class="pull-right" style="padding-top: 45px;">';
		html += 				'<div class="input-append">';
		html += 					'<input class="span3" id="appendedInputButton" type="text" placeholder="Pesquisar produtos…">';
		html += 					'<button class="btn" type="button" onclick="loadProducts(\'\', $(\"#appendedInputButton\").val(), \'\');"><i class="icon-search"></i></button>';
		html += 				'</div>';
		html += 			'</form>';
		html += 		'</div>';
		html += 	'</div>';
		
		html += 	'<div class="row">';
		html += 		'<div class="span12">';
		html += 			'<div class="navbar">';
		html += 				'<div class="navbar-inner">';
		html += 					'<div class="container">';
		html += 						'<a class="btn btn-navbar" data-toggle="collapse" data-target=".nav-collapse">';
		html += 							'<span class="icon-bar"></span>';
		html += 							'<span class="icon-bar"></span>';
		html += 							'<span class="icon-bar"></span>';
		html += 						'</a>';
		html += 						'<div class="nav-collapse collapse">';
		html += 							'<ul class="nav">';
		html += 								'<li id="home"><a href="'+homeUrl+'">Home</a></li>';
		html += 							'</ul>';
		html += 						'</div>';
		html += 					'</div>';
		html += 				'</div>';
		html += 			'</div>';
		html += 		'</div>';
		html += 	'</div>';
		
		html += 	'<div id="divModalFormCad" class="modal hide fade" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true"></div>';
		
		$('div#header').html(html);
		
		if (getURLParameter('key')!=undefined && getURLParameter('key')!=null && getURLParameter('key')!='' && getURLParameter('key')!='null') {
			$('li#'+getURLParameter('key')).attr('class', 'active');
		} else {
			$('li#home').attr('class', 'active');
		}
		
		loadSession();
	});
}

function loadFooterGeneric(urlQuemSomos, urlPolitica, urlIndique, urlCadastro, urlComocomprar, urlEntrega, urlFaleconosco) {
	var html = 	'<div class="container">';
	html += 		'<div class="row">';
	html += 			'<div class="span4 muted" style="padding-top: 20px;">';
	html += 				'<a href="https://pagseguro.uol.com.br/" target="_blank"><img alt="" src="/ecommerce/'+getPortalContext()+'/img/formas-de-pagamento.png"></a>';
	html += 			'</div>';
	html += 			'<div class="span3 muted" style="padding-top: 20px;">';
	html += 				'<small><strong>Institucional:</strong></small>';
	html += 				'<ul class="unstyled">';
	html += 					'<li><a href="'+urlQuemSomos+'" class="muted"><small>Quem somos</small></a></li>';
	html += 					'<li><a href="'+urlPolitica+'" class="muted"><small>Política de privacidade e segurança</small></a></li>';
	html += 					'<li><a href="'+urlIndique+'" class="muted"><small>Indique</small></a></li>';
	html += 				'</ul>';
	html += 			'</div>';
	html += 			'<div class="span3 muted" style="padding-top: 20px;">';
	html += 				'<small><strong>Atendimento:</strong></small>';
	html += 				'<ul class="unstyled">';
	html += 					'<li><a href="'+urlCadastro+'" class="muted"><small>Cadastro</small></a></li>';
	html += 					'<li><a href="'+urlComocomprar+'" class="muted"><small>Como comprar</small></a></li>';
	html += 					'<li><a href="'+urlEntrega+'" class="muted"><small>Entrega</small></a></li>';
	html += 					'<li><a href="'+urlFaleconosco+'" class="muted"><small>Fale conosco</small></a></li>';
	html += 				'</ul>';
	html += 			'</div>';
	html += 			'<div class="span2" style="padding-top: 20px;">';
	html += 				'<p class="muted credit">&copy;2012 <a href="http://danielfreire.net" class="muted">danielfreire.net</a>.</p>';
	html += 			'</div>';
	html += 		'</div>';
	html += 	'</div>';
	
	$('div#footer').html(html);
}

function loadProducts(category, search, page) {
	if (category==undefined || category==null) {
		category = '';
	}
	if (search==undefined || search==null) {
		search = '';
	}
	if (page==undefined || page==null) {
		page = 1;
	}
	
	$.getJSON( '/ecommerce-web/products?sid='+getSid()+'&page='+page+'&search='+search+'&cid='+category+'&tk'+new Date().getTime(),  function(data) {
		var totalP = 0;
		$.each(data.rows, function(key, val) {
			totalP++;
		});
		
		var i=1;
		var html = '';
		$.each(data.rows, function(key, val) {
			var coll = val;
			if (coll.name==undefined || coll.name==null) {
				coll = val.product;
			}
			
			var img = coll.images.split('[LIN]')[0].split('[COL]')[1];
			
			if (i==1 || i==4 || i==7) {
				html+='<div class="row" style="padding-top: 20px;">';
			}
			
			var linkA = '';
			if (getDomain()=='localhost:8080') {
				linkA += '<a href="/ecommerce/'+getPortalContext()+'/product/?pid='+coll.keyUrl+'">';
			} else {
				linkA += '<a href="/ecommerce/'+getPortalContext()+'/products/'+coll.keyUrl+'">';
			}
			
			html += '<div class="span3">'+linkA;
			html += '<img alt="'+coll.introduction+'" src="'+img+'">';
			html += '<p><img src="/library/img/detalhes.png" alt="clique e veja mais informações"/></p>';
			html += '<p>';
			html += '<strong>'+coll.name+'</strong>';
			html += '<small>&nbsp;&nbsp;'+coll.introduction+'</small>';
			html += '</p>';
			if (coll.quantity > 0) {
				html += '<h4><span class="text-warning pull-right">R$ '+convertMoeda(coll.unityvalue)+'</span></h4>';
			} else {
				html += '<h4><span class="text-warning pull-right">Produto indisponível</span></h4>';
			}
			html += '</a></div>';
			
			if (i==3 || i==6 || i==9 || i==totalP) {
				html+='</div>'
			} 
			
			i++;
		});
		
		var pageNow = data.page;
		var totalPages = data.totalPages;
		html += 	'<div class="pagination" style="text-align:right;padding-top: 20px;">';
		html += 		'<ul>';
		if (pageNow>1) {
			html += '<li><a href="#" onclick="loadProducts(\''+category+'\', \''+search+'\', \''+((pageNow*1)-1)+'\');">Anterior</a></li>';
		} else {
			html += '<li class="disabled"><a href="#">Anterior</a></li>';
		}
		
		for (var a=1; a <= (totalPages*1); a++) {
			if (a == pageNow) {
				html += '<li class="active"><a href="#">'+a.toString()+'</a></li>';
			} else {
				html += '<li><a href="#" onclick="loadProducts(\''+category+'\', \''+search+'\', \''+a+'\');">'+a.toString()+'</a></li>';
			}
		}
		
		if (pageNow<totalPages) {
			html += '<li><a href="#" onclick="loadProducts(\''+category+'\', \''+search+'\', \''+((pageNow*1)+1)+'\');">Próxima</a></li>';
		} else {
			html += '<li class="disabled"><a href="#">Próxima</a></li>';
		}
		
		html += 		'</ul>';
		html += 	'</div>';
		
		$('div#products').html(html);
	});
}

function loadDetailProduct(product) {
	$.getJSON( '/ecommerce/'+getPortalContext()+'/data/product'+product+'.json?tk'+new Date().getTime(),  function(data) {
		if (!data) {
			location.href='/ecommerce/'+getPortalContext();
		} else {
			var html = 	'<div style="padding-top: 20px;" class="row">';
			html += 		'<div class="span9" style="text-align: left;">';
			html +=				'<h3>'+data.name+'</h3>';
			html += 			'<h5>'+data.introduction+'</h5>';
			html += 		'</div>';
			html += 	'</div>';
			
			html += 	'<div style="padding-top: 20px;" class="row">';
			html += 		'<div class="span3">';
			html += 			'<div id="productsCarousel" class="carousel slide">';
		    html += 				'<div class="carousel-inner">';
		    var images = data.images.split('[LIN]');
		    for (var i=0; i<images.length; i++) {
		    	var image = images[i].split('[COL]');
		    	
		    	if (image[0]=='1') {
		    		html += '<div class="active item"><div><img class="imgDetail" src="'+image[1]+'" alt="'+data.introduction+'" data-zoom-image="'+image[1]+'" /></div></div>';
		    	} else {
		    		html += '<div class="item"><div><img src="'+image[1]+'" alt="'+data.introduction+'" class="imgDetail" data-zoom-image="'+image[1]+'" /></div></div>';
		    	}
		    }
		    html += 				'</div>';
		    html += 				'<a class="carousel-control left" href="#productsCarousel" data-slide="prev">&lsaquo;</a>';
		    html += 				'<a class="carousel-control right" href="#productsCarousel" data-slide="next">&rsaquo;</a>';
		    html += 			'</div>';
			html += 		'</div>';
			html += 		'<div class="span6" style="text-align: left;">';
			html +=				'<table class="table table-bordered"><tr><td>';
			html +=						'<h3>Por: R$ '+convertMoeda(data.unityvalue)+'</h3>';
			html +=						'<h4 style="color: #C09853;">18x de R$ '+convertMoeda((data.unityvalue/18))+' sem juros</h4>';
			html += 				'</td><td style="border-left: 0px; text-align:right" width="35%">';
			if (data.quantity>0) {
				html +=	'<button class="btn btn-large btn-danger btn-block" type="button" onclick="addCart(\''+data.id+'\', true)"><i class="icon-play icon-white"></i> comprar</button>';
				html +=	'<button class="btn btn-large btn-block" type="button" onclick="addCart(\''+data.id+'\', false)"><i class="icon-shopping-cart"></i> adicionar ao carrinho</button>';
			}
			if (data.quantity<=0) {
				html +=	'</td></tr><tr style="background-color: #D3D3D3; color:red"><td colspan="2"><strong>Produto indisponível</strong></td></tr></table>';
			} else {
				html +=	'</td></tr><tr style="background-color: #D3D3D3"><td colspan="2">';
				html += 	'<div class="input-append form-inline" style="color: white; text-align: left">';
				html += 		'<label><strong>Consulte o prazo de entrega do pedido: </strong></label>';
				html += 		'<input type="text" name="cepToFrete" placeholder="CEP" class="span2">';
				html += 		'<button id="btnFreteCalc" class="btn" type="button" onclick="calcFrete();" data-loading-text="Processando...">Calcular</button>';
				html += 	'</div>';
				html += 	'<div id="divQuantityDay" style="text-align: left"></div>';
				html +=	'</td></tr></table>';
			}
			html += 		'</div>';
			html += 	'</div>';
			
			html += 	'<div style="padding-top: 20px;" class="row">';
			html += 		'<div class="span9" style="text-align: left;">';
			html +=				'<table class="table table-bordered">';
			html += 				'<tr>';
			html += 					'<th>INFORMAÇÕES SOBRE O PRODUTO</th>';
			html += 				'</tr>';
			html += 				'<tr>';
			html += 					'<td><small><strong>'+data.name+'</strong><br>'+data.introduction+'<br><br>'+data.description+'</small></td>';
			html += 				'</tr>';
			html +=				'</table>';
			html += 		'</div>';
			html += 	'</div>';
			
			$('div#detail').html(html);
			
			$("input[name='cepToFrete']").mask("99999-999");
			$('.imgDetail').elevateZoom();
		}
	});
}

function loadSession() {
	$.getJSON( '/ecommerce-web/session?sid='+getSid()+'&tk'+new Date().getTime(),  function(data) {
		if (data.status) {
			if (data.generic.client!=null) {
				
				$('a#aLinkMyData').attr('href', '/ecommerce/'+getPortalContext()+'/client');
				$('span#spanEntry').text(' Sair');
				$('a#aLinkMyEntry').attr('onclick', 'logout()');
				
			} else {
				var html = 		'<div class="modal-header">';
				html += 			'<button type="button" class="close" data-dismiss="modal" aria-hidden="true">×</button>';
				html += 			'<h3 id="myModalLabel">Meu cadastro</h3>';
				html += 		'</div>';
				html += 		'<div class="modal-body">';
				html += 			'<form id="formCadClient" class="form-horizontal">';
				html += 				'<div class="control-group" id="groupBy-name">';
				html += 					'<label class="control-label" for="inputName">Nome <small><span class="help-inline success">Digite seu nome completo</span></small></label>';
				html +=						'<div class="controls">';
				html += 						'<input name="name" type="text" id="inputName" placeholder="Nome" class="input-block-level">';
				html += 						'<span class="help-inline" id="spanErrorMessage-name"></span>';
				html +=						'</div>';
				html += 				'</div>';
				html += 				'<div class="control-group" id="groupBy-user">';
				html += 					'<label class="control-label" for="inputUser">E-mail <small><span class="help-inline success">Digite um e-mail válido</span></small></label>';
				html += 					'<div class="controls form-inline">';
				html += 						'<input name="user" type="text" id="inputUser" placeholder="E-mail" style="width: 45%">&nbsp;&nbsp;';
				html += 						'<input name="userConfirm" type="text" id="inputUserConfirm" placeholder="Confirmar e-mail" style="width: 44%">';
				html += 						'<span class="help-inline" id="spanErrorMessage-user"></span>';
				html += 					'</div>';
				html += 				'</div>';
				html += 				'<div class="control-group" id="groupBy-password">';
				html += 					'<label class="control-label" for="inputPassword">Senha <small><span class="help-inline success">Combinar números e letras</span></small></label>';
				html += 					'<div class="controls form-inline">'; 
				html += 						'<input name="password" type="password" id="inputPassword" placeholder="Senha" style="width: 45%">&nbsp;&nbsp;';
				html += 						'<input name="passwordConfirm" type="password" id="inputPasswordConfirm" placeholder="Confirmar senha" style="width: 44%">';
				html += 						'<span class="help-inline" id="spanErrorMessage-password"></span>';
				html += 					'</div>';
				html += 				'</div>';
				html += 				'<div class="control-group" id="groupBy-address">';
				html += 					'<label class="control-label" for="inputAddress">Endereço <small><span class="help-inline success">Digite seu endereço para recebimento</span></small></label>';
				html += 					'<div class="controls form-inline">'; 
				html += 						'<input name="addressStreet" type="text" placeholder="Endereço" style="margin-bottom: 2px; width: 72%">&nbsp;&nbsp;';
				html += 						'<input name="addressNumber" type="text" placeholder="Nro" class="span1" style="margin-bottom: 2px;"><br>';
				html += 						'<input name="addressZipcode" type="text" placeholder="CEP" class="span1" style="width: 22%">&nbsp;&nbsp;';
				html += 						'<input name="addressComplement" type="text" placeholder="Complemento" style="width: 43%">&nbsp;&nbsp;';
				html += 						'<input name="addressCity" type="text" placeholder="Cidade" class="span1">';
				html += 						'<span class="help-inline" id="spanErrorMessage-addressStreet"></span>';
				html += 						'<span class="help-inline" id="spanErrorMessage-addressNumber"></span>';
				html += 						'<span class="help-inline" id="spanErrorMessage-addressZipcode"></span>';
				html += 						'<span class="help-inline" id="spanErrorMessage-addressCity"></span>';
				html += 					'</div>';
				html += 				'</div>';
				html += 				'<div class="control-group" id="groupBy-captcha">';
				html += 					'<label class="control-label">Segurança <small><span class="help-inline success">Digite o conteúdo da imagem no campo ao lado</span></small></label>';
				html += 					'<div class="controls form-inline">'; 
				html += 						'<img id="idimgcaptcha" src="" alt="Digite as letras e números" />&nbsp;&nbsp;';
				html += 						'<input name="captcha" type="text" class="span1">';
				html += 						'<span class="help-inline" id="spanErrorMessage-captcha"></span>';
				html += 					'</div>';
				html += 				'</div>';
				html += 				'<div class="control-group">';
				html += 					'<div class="controls">'; 
				html += 						'<small><input type="checkbox" name="newsletter" value="true"> Desejo receber notícias e promoções da '+getPortalContext()+'</small>';
				html += 					'</div>';
				html += 				'</div>';
				html += 				'<div class="control-group" id="groupBy-privacity">';
				html += 					'<div class="controls">'; 
				html += 						'<small><input type="checkbox" name="privacity" value="true"> Aceito e confirmo ter lido a <a>política de privacidade e segurança</a> da empresa</small>';
				html += 						'<span class="help-inline" id="spanErrorMessage-privacity"></span>';
				html += 					'</div>';
				html += 				'</div>';
				html += 				'<input type="hidden" name="sid" value="'+getSid()+'">';
				html += 				'<input type="hidden" name="contextid" value="'+getContextId()+'">';
				html += 			'</form>';
				html += 		'</div>';
				html += 		'<div class="modal-footer">';
				html += 			'<button class="btn" data-dismiss="modal" aria-hidden="true">Cancelar</button>';
				html += 			'<button id="svnButtonFormCad" class="btn btn-primary" onclick="submitFormCadUser();" data-loading-text="Processando...">Salvar</button>';
				html += 		'</div>';
				
				$('div#divModalFormCad').html(html);
				$('a#aLinkMyDataModal').attr('href', '#divModalFormCad');
				$('a#aLinkMyDataModal').attr('data-toggle', 'modal');
				$('a#aLinkMyData').attr('onclick', 'myData(false)');
				
				$('img#idimgcaptcha').attr('src', '/ecommerce-web/captcha.jpg');
				window.setTimeout('$(\'img#idimgcaptcha\').attr(\'src\', \'\');', 500);
				window.setTimeout('$(\'img#idimgcaptcha\').attr(\'src\', \'/ecommerce-web/captcha.jpg\');', 500);
				$('img#idimgcaptcha').attr('src', '/ecommerce-web/captcha.jpg');
				
				html = 	'<form id="formLogin" style="padding-bottom: 30px;">';
				html += 	'<input name="user" type="text" placeholder="Usuário" style="width: 93%">';
				html += 	'<input name="password" type="password" placeholder="Senha" style="width: 93%">';
				html += 	'<input type="hidden" name="sid" value="'+getSid()+'">';
				html += 	'<div id="divErrorLogin" class="alert alert-error hide"><button class="close" data-dismiss="alert" type="button">×</button> <span id="spanErrorLogin">Os dados informados estão incorretos.</span></div>';
				html += 	'<button class="btn btn-primary pull-right" type="button" onclick="login()">Acessar</button>';
				html += 	'<a class="pull-left" href="#" onclick="forgotPassword()">Esqueci minha senha</a>';
				html += '</form>';
				
				$('a#aLinkMyEntry').attr('data-content', html);
				$('a#aLinkMyEntry').attr('data-placement', 'bottom');
				$('a#aLinkMyEntry').attr('rel', 'popover');
				$('a#aLinkMyEntry').attr('data-original-title', 'Login');
				$('a#aLinkMyEntry').attr('data-html', 'true');
				$('span#spanEntry').text(' Entrar');
				
				$("a[rel=popover]").popover().click(function(e) {
					e.preventDefault()
				});
			}
			
			if (data.generic.cart!=null && data.generic.cart.length>0) {
				$('span#spanQtdCart').text(data.generic.cart.length);
				$('a#aLinkMyCart').attr('href','/ecommerce/'+getPortalContext()+'/mycart');
			} else {
				$('a#aLinkMyCart').hide();
			}
		} else {
			alert(data.messageError.generic);
		}
	});
	
}

function submitFormCadUser() {
	var user1 = $('form#formCadClient').find('input#inputUser').val();
	var user2 = $('form#formCadClient').find('input#inputUserConfirm').val();
	var pass1 = $('form#formCadClient').find('input#inputPassword').val();
	var pass2 = $('form#formCadClient').find('input#inputPasswordConfirm').val();
	var priv = $('form#formCadClient').find('input[name=privacity]').is(":checked");
	
	var i = 0;
	if (user1!=user2) {
		var key = 'user';
		$('#groupBy-'+key).attr('class', $('#groupBy-'+key).attr('class') + ' error');
		$('span#spanErrorMessage-'+key).text('E-mail inválido, verifique se o campo "E-mail" esta igual ao "Confirmar e-mail".');
		i++;
	}
	if (pass1!=pass2) {
		var key = 'password';
		$('#groupBy-'+key).attr('class', $('#groupBy-'+key).attr('class') + ' error');
		$('span#spanErrorMessage-'+key).text('Senha inválida, verifique se o campo "Senha" esta igual ao "Confirmar senha".');
		i++;
	}
	if (!priv) {
		var key = 'privacity';
		$('#groupBy-'+key).attr('class', $('#groupBy-'+key).attr('class') + ' error');
		$('span#spanErrorMessage-'+key).text('Para concluir o cadastro é necessário confirmar a aceitação dos termos acima.');
		i++;
	}
	
	if (i==0) {
		$.post('/ecommerce-web/client/new', $('form#formCadClient').serialize(), function(data){
			
			if (data.status) {
				$('form#formCadClient').html('<div style="text-align: center"><h4>Cadastro realizado com sucesso.</h4>Você receberá no seu e-mail um link para ativação do cadastro, para comprar será necessário a ativação da conta.</div>');
				$('#svnButtonFormCad').attr('onclick', '');
				$('#svnButtonFormCad').text('Ok');
				$('#svnButtonFormCad').attr('data-dismiss', 'modal');
			} else {
				$.each(data.messageError, function(key, val) {
					$('span#spanErrorMessage-'+key).text(val);
					
					if (key=='addressCity' || key=='addressNumber' || key=='addressStreet' || key=='addressZipcode') {
						$('#groupBy-address').attr('class', $('#groupBy-address').attr('class') + ' error');
					} else {
						$('#groupBy-'+key).attr('class', $('#groupBy-'+key).attr('class') + ' error');
					}
					
					$('form#formCadClient').find('input[name="'+key+'"]').blur(function(){
						var key = $(this).attr('name');
						if (key=='addressCity' || key=='addressNumber' || key=='addressStreet' || key=='addressZipcode') {
							$('#groupBy-address').attr('class', $('#groupBy-address').attr('class').replace('error', ''));
							$('span#spanErrorMessage-addressCity').text('');
							$('span#spanErrorMessage-addressNumber').text('');
							$('span#spanErrorMessage-addressStreet').text('');
							$('span#spanErrorMessage-addressZipcode').text('');
						} else {
							$('#groupBy-'+key).attr('class', $('#groupBy-'+key).attr('class').replace('error', ''));
							$('span#spanErrorMessage-'+key).text('');
						}
					});
				});
			}
			
		}, "json");
	} else {
		$('form#formCadClient').find('input[name="user"]').blur(function(){
			var key = $(this).attr('name');
			$('#groupBy-'+key).attr('class', $('#groupBy-'+key).attr('class').replace('error', ''));
			$('span#spanErrorMessage-'+key).text('');
		});
		$('form#formCadClient').find('input[name="password"]').blur(function(){
			var key = $(this).attr('name');
			$('#groupBy-'+key).attr('class', $('#groupBy-'+key).attr('class').replace('error', ''));
			$('span#spanErrorMessage-'+key).text('');
		});
		$('form#formCadClient').find('input[name=privacity]').click(function() {
			var key = $(this).attr('name');
			$('#groupBy-'+key).attr('class', $('#groupBy-'+key).attr('class').replace('error', ''));
			$('span#spanErrorMessage-'+key).text('');
		});
	}
}

function controlPainel(id) {
	
	if (id==undefined || id==null || id=='') {
		
		$.getJSON( '/ecommerce-web/session?sid='+getSid()+'&tk'+new Date().getTime(),  function(data) {
			if (data.status) {
				if (data.generic.client!=null) {
					menuControlPainel();
					
					var html = '';
					html +=	'<div class="hero-unit" style="text-align: left">';
					html += 	'<h3>Painel de controle</h3>';
					html += 	'<p>';
					html += 		'Olá <span><strong>'+data.generic.client.name+'</strong></span> <small>(clique <a href="">aqui</a> se não for você)</small>, <br>';
					html += 		'Esta àrea é exlusiva para clientes. Aqui você poderá alterar seus dados cadastrais além de consultar seus pedidos.';
					html += 	'</p>';
					html += '</div>';
					
					$('div#controlPainel').html(html);
				} else {
					location.href='/ecommerce/'+getPortalContext();
				}
			} else {
				alert(data.messageError.generic);
			}
		});
		
	} else if (id=='order1' || id=='order2' || id=='order3') {
		
		$.getJSON( '/ecommerce-web/orders?opt='+id+'&tk'+new Date().getTime(),  function(data) {
			
			if (data.status) {
				
				var html = '';
				html +=	'<div class="hero-unit" style="padding: 10px; text-align: left;">';
				html += 	'<h5>Meus pedidos</h5>';
				html += 	'<div style="background-color: white; padding: 10px; text-align: left">';
				html += 		'<ul class="unstyled">';
				
				if (data.genericList.length>0) {
					$.each(data.genericList, function(key, val) {
						html += 			'<li>';
						html += 				'<a href=""><small>Pedido: 98739</small></a>';
						html += 				'<table class="table table-bordered" style="font-size: 14px;">';
						html += 					'<tr>';
						html += 						'<th>Data:</th>';
						html += 						'<th>Produtos:</th>';
						html += 						'<th>Valor:</th>';
						html += 						'<th>Status:</th>';
						html += 					'</tr>';
						html += 					'<tr>';
						var dat = new Date();
						dat.setTime(val.dateCreate.toString());
						html += 						'<td>'+dat.getDate()+'/'+(dat.getMonth()+1)+'/'+dat.getFullYear()+' '+dat.getHours()+':'+dat.getMinutes()+'</td>';
						html += 						'<td style="text-align: center"><a onclick="viewCartByOrder(\''+val.id+'\');" href="#"><i class="icon-plus"></i></a></td>';
						html += 						'<td>R$ '+convertMoeda(val.totalValue)+'</td>';
						html += 						'<td>'+getLblOrderStatus(val.statusOrder)+'</td>';
						html += 					'</tr>';
						html += 				'</table>';
						html += 			'</li>';
					});
				} else {
					html += 			'<li>Não localizamos nenhum pedido.</li>';
				}
				html += 		'</ul>';
				html += 	'</div>';
				html += '</div>';
				
				$('div#controlPainel').html(html);
				
			} else {
				if (data.messageError.sessionInvalid!=undefined && data.messageError.sessionInvalid!=null && data.messageError.sessionInvalid!='') {
					location.href='/ecommerce/'+getPortalContext();
				} else {
					alert(data.messageError.generic);
				}
			}
			
		});
		
	} else if (id=='data1') {
		
		var html = '';
		html +=	'<div class="hero-unit" style="padding: 10px; text-align: left;">';
		html +=		'<h5>Meu cadastro</h5>';
		html +=		'<div style="background-color: white; padding: 10px; text-align: left">';
		html +=			'<form id="formUpdatePassword" class="form-horizontal">';
		html +=				'<div class="control-group">';
		html +=					'<label class="control-label" for="inputEmail">Senha atual:</label>';
		html +=					'<div class="controls pull-left" style="margin-left: 10px;">';
		html +=						'<input type="password" name="senha1">';
		html +=					'</div>';
		html +=				'</div>';
		html +=				'<div class="control-group">';
		html +=					'<label class="control-label" for="inputPassword">Nova senha:</label>';
		html +=					'<div class="controls pull-left" style="margin-left: 10px;">';
		html +=						'<input type="password" name="senha2">';
		html +=					'</div>';
		html +=				'</div>';
		html +=				'<div class="control-group">';
		html +=					'<label class="control-label" for="inputPassword">Confirmar senha:</label>';
		html +=					'<div class="controls pull-left" style="margin-left: 10px;">';
		html +=						'<input type="password" name="senha3">';
		html +=					'</div>';
		html +=				'</div>';
		html +=				'<div id="divErrorUpdatePassword" class="alert hide" style="text-align: left;">';
		html +=					'<button type="button" class="close" data-dismiss="alert">&times;</button>';
		html +=					'<small><strong>Atenção!</strong> <span id="spanErrorUpdatePassword"></span></small>';
		html +=				'</div>';
		html +=				'<div class="control-group">';
		html +=					'<button type="button" class="btn" onclick="updatePassword();" data-loading-text="Processando...">Alterar</button>';
		html +=				'</div>';
		html +=			'</form>';
		html +=		'</div>';
		html +=	'</div>';
		$('div#controlPainel').html(html);
		
	} else if (id=='data3') {
		
		$.getJSON( '/ecommerce-web/session?sid='+getSid()+'&tk'+new Date().getTime(),  function(data) {
			if (data.status) {
				if (data.generic.client!=null) {
					
					var html = '';
					html += '<div class="hero-unit" style="padding: 10px; text-align: left;">';
					html += 	'<h5>Meu cadastro</h5>';
					html += 	'<div style="background-color: white; padding: 10px; text-align: left">';
					html += 		'<form id="formUpdateAddress" class="form-horizontal">';
					html +=				'<div class="control-group">';
					html +=					'<label class="control-label" for="inputEmail">Endereço: *</label>';
					html +=					'<div class="controls pull-left" style="margin-left: 10px;">';
					html += 					'<input name="addressStreet" value="'+data.generic.client.addressStreet+'" type="text" placeholder="Digite o endereço">';
					html +=					'</div>';
					html +=				'</div>';
					html +=				'<div class="control-group">';
					html +=					'<label class="control-label" for="inputEmail">Complemento:</label>';
					html +=					'<div class="controls pull-left" style="margin-left: 10px;">';
					html += 					'<input name="addressComplement" value="'+data.generic.client.addressComplement+'" type="text" placeholder="Digite o complemento">';
					html +=					'</div>';
					html +=				'</div>';
					html +=				'<div class="control-group">';
					html +=					'<label class="control-label" for="inputEmail">Número: *</label>';
					html +=					'<div class="controls pull-left" style="margin-left: 10px;">';
					html += 					'<input name="addressNumber" value="'+data.generic.client.addressNumber+'" type="text" placeholder="Digite o número">';
					html +=					'</div>';
					html +=				'</div>';
					html +=				'<div class="control-group">';
					html +=					'<label class="control-label" for="inputEmail">CEP: *</label>';
					html +=					'<div class="controls pull-left" style="margin-left: 10px;">';
					html += 					'<input name="addressZipcode" value="'+data.generic.client.addressZipcode+'" type="text" placeholder="Digite o CEP">';
					html +=					'</div>';
					html +=				'</div>';
					html +=				'<div class="control-group">';
					html +=					'<label class="control-label" for="inputEmail">Cidade: *</label>';
					html +=					'<div class="controls pull-left" style="margin-left: 10px;">';
					html += 					'<input name="addressCity" value="'+data.generic.client.addressCity+'" type="text" placeholder="Digite a cidade">';
					html +=					'</div>';
					html +=				'</div>';
					html +=				'<div id="divErrorUpdateAddress" class="alert hide" style="text-align: left;">';
					html +=					'<button type="button" class="close" data-dismiss="alert">&times;</button>';
					html +=					'<small><strong>Atenção!</strong> <span id="spanErrorUpdateAddress"></span></small>';
					html +=				'</div>';
					html += 			'<div class="control-group">';
					html += 				'<button type="button" class="btn" onclick="updateAddress();" data-loading-text="Processando...">Alterar</button>';
					html += 			'</div>';
					html += 		'</form>';
					html += 	'</div>';
					html += '</div>';
					$('div#controlPainel').html(html);
					
				} else {
					location.href='/ecommerce/'+getPortalContext();
				}
			} else {
				alert(data.messageError.generic);
			}
		});
		
	} else if (id=='data2') {
		
		$.getJSON( '/ecommerce-web/session?sid='+getSid()+'&tk'+new Date().getTime(),  function(data) {
			if (data.status) {
				if (data.generic.client!=null) {
					
					var html = '';
					html += '<div class="hero-unit" style="padding: 10px; text-align: left;">';
					html += 	'<h5>Meu cadastro</h5>';
					html += 	'<div style="background-color: white; padding: 10px; text-align: left">';
					html += 		'<form class="form-horizontal" id="formUpdateData">';
					html += 			'<div class="control-group">';
					html += 				'<label class="control-label" for="inputName">Nome</label>';
					html +=					'<div class="controls pull-left" style="margin-left: 10px;">';
					html += 					'<input name="name" type="text" value="'+data.generic.client.name+'" placeholder="Digite seu nome completo">';
					html += 				'</div>';
					html += 			'</div>';
					html += 			'<div class="control-group">';
					html += 				'<label class="control-label" for="inputUser">E-mail</label>';
					html +=					'<div class="controls pull-left" style="margin-left: 10px;">';
					html += 					'<input name="user" type="text" value="'+data.generic.client.user+'" placeholder="Digite seu e-mail">';
					html += 				'</div>';
					html += 			'</div>';
					html += 			'<div class="control-group">';
					html += 				'<label class="control-label" for="inputUser">Confirmar e-mail</label>';
					html +=					'<div class="controls pull-left" style="margin-left: 10px;">';
					html += 					'<input name="userConfirm" type="text" value="'+data.generic.client.user+'" placeholder="Confirmar e-mail">';
					html += 				'</div>';
					html += 			'</div>';
					html += 			'<div class="control-group">';
					html += 				'<small><input type="checkbox" name="newsletter"> Desejo receber notícias e promoções</small>';
					html += 				'<input name="newsletterText" type="hidden" value="'+data.generic.client.newsletter+'">';
					html += 			'</div>';
					html +=				'<div id="divErrorUpdateData" class="alert hide" style="text-align: left;">';
					html +=					'<button type="button" class="close" data-dismiss="alert">&times;</button>';
					html +=					'<small><strong>Atenção!</strong> <span id="spanErrorUpdateData"></span></small>';
					html +=				'</div>';
					html += 			'<div class="control-group">';
					html += 				'<button type="button" class="btn" onclick="updateData()" data-loading-text="Processando...">Alterar</button>';
					html += 			'</div>';
					html += 		'</form>';
					html += 	'</div>';
					html += '</div>';
					$('div#controlPainel').html(html);
					
					if (data.generic.client.newsletter) {
						$('input[name="newsletter"]').attr("checked",true);
					}
					
				} else {
					location.href='/ecommerce/'+getPortalContext();
				}
			} else {
				alert(data.messageError.generic);
			}
		});
		
	}
	

}

function menuControlPainel() {
	var html = 	'<div class="hero-unit" style="padding: 10px; text-align: left;">';
	html +=			'<h5>Meus serviços:</h5>';
	html += 		'<div style="background-color: white; padding: 10px; text-align: left">';
	html += 			'<h5>Meus pedidos</h5>';
	html += 			'<ul class="unstyled">';
	html +=					'<li><a href="#" onclick="controlPainel(\'order1\');"><small>Pedidos em aberto</small></a></li>';
	html +=					'<li><a href="#" onclick="controlPainel(\'order2\');"><small>Pedidos entregues</small></a></li>';
	html += 				'<li><a href="#" onclick="controlPainel(\'order3\');"><small>Todos os pedidos</small></a></li>';
	html += 			'</ul>';
	html +=				'<h5>Meu cadastro</h5>';
	html +=				'<ul class="unstyled">';
	html += 				'<li><a href="#" onclick="controlPainel(\'data1\');"><small>Alterar senha</small></a></li>';
	html +=					'<li><a href="#" onclick="controlPainel(\'data2\');"><small>Alterar cadastro</small></a></li>';
	html += 				'<li><a href="#" onclick="controlPainel(\'data3\');"><small>Alterar endereço</small></a></li>';
	html +=				'</ul>';
	html +=			'</div>';
	html += 	'</div>';
	
	$('div#menuControlPainel').html(html);
}

function login(isOrder) {
	if (isOrder==undefined || isOrder==null || isOrder=='') {
		isOrder = false;
	}
	$.post('/ecommerce-web/login', $('form#formLogin').serialize()+'&contextid='+getContextId(), function(data){
		if (data.status) {
			$('div#divThisClient').modal('hide');
			loadHeader();
			if (isOrder) {
				paymentOrder();
			} else {
				location.href='/ecommerce/'+getPortalContext()+'/client/';
			}
		} else {
			$('span#spanErrorLogin').text(data.messageError.generic);
			$('div#divErrorLogin').show();
		}
	}, "json");
}

function logout() {
	$.post('/ecommerce-web/logoutSession', '', function(data){
		if (data.status) {
			location.href='/ecommerce/'+getPortalContext();
		} else {
			alert(data.messageError.generic);
		}
	}, "json");
}

function forgotPassword() {
	$.getJSON( '/ecommerce-web/forgotPassword', $('form#formLogin').serialize()+"&contextid="+getContextId(), function(data) {
		if (data.status) {
			$('a#aLinkMyEntry').popover('hide');
			$('div#divThisClient').modal('hide');
			
			$('span#spanErrorLogin').text('');
			$('div#divErrorLogin').hide();
			
			alert('Sua nova senha foi enviada com sucesso para o seu e-mail.');
		} else {
			$('span#spanErrorLogin').text(data.messageError.generic);
			$('div#divErrorLogin').show();
		}
	});
}

function viewCartByOrder(orderId) {
	$.getJSON( '/ecommerce-web/order/products?oid='+orderId,  function(data) {
		
		if (data.status) {
			
			var html = 	'<div id="modalProducts" class="modal hide fade" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">';
			html += 		'<div class="modal-header">';
			html += 			'<button type="button" class="close" data-dismiss="modal" aria-hidden="true">×</button>';
			html += 			'<h3 id="myModalLabel">Produtos</h3>';
			html += 		'</div>';
			html += 		'<div class="modal-body">';
			html += 			'<table class="table table-striped">';
			html += 				'<thead>';
			html += 					'<tr>';
			html += 						'<th>Produto</th>';
			html += 						'<th>Quantidade</th>';
			html += 						'<th>Valor Unitário</th>';
			html += 					'</tr>';
			html += 				'</thead>';
			html += 				'<tbody>';
			
			$.each(data.genericList, function(key, val) {
				var tr = '<tr>';
				tr += '<td>'+val.product.name+'</td>';
				tr += '<td>'+val.quantity+'</td>';
				var v = val.unityvalue.toString();
				if (v.indexOf('.')==-1) {
					tr += '<td>R$ '+v+',00</td>';
				} else {
					tr += '<td>R$ '+v.replace('.', ',')+'</td>';
				}
				tr += '</tr>';
				html += tr;
			});
			
			html += 				'</tbody>';
			html += 			'</table>';
			html += 		'</div>';
			html += 		'<div class="modal-footer">';
			html += 			'<button type="button" class="btn btn-primary" data-dismiss="modal" aria-hidden="true">Ok</button>';
			html += 		'</div>';
			html += 	'</div>';

			$('div#modalProducts').remove();
			$('body').append(html);
			$('div#modalProducts').modal('show');
		
		} else {
			if (data.messageError.sessionInvalid!=undefined && data.messageError.sessionInvalid!=null && data.messageError.sessionInvalid!='') {
				location.href='/ecommerce/'+getPortalContext();
			} else {
				alert(data.messageError.generic);
			}
		}
	});
}

function updatePassword() {
	$('div#divErrorUpdatePassword').hide();
	if ($('input[name="senha2"]').val()==$('input[name="senha3"]').val()) {
		$.post('/ecommerce-web/updatePassword', $('form#formUpdatePassword').serialize(), function(data){
			if (data.status) {
				alert('Senha alterada com sucesso.');
			} else {
				if (data.messageError.sessionInvalid!=undefined && data.messageError.sessionInvalid!=null && data.messageError.sessionInvalid!='') {
					location.href='/ecommerce/'+getPortalContext();
				} else {
					$('span#spanErrorUpdatePassword').text(data.messageError.generic);
					$('div#divErrorUpdatePassword').show();
				}
			}
		}, "json");
	} else {
		$('span#spanErrorUpdatePassword').text('Não foi possível confirmar o valor da nova senha pois estão diferentes, digite novamente a nova senha e a confirmação.');
		$('div#divErrorUpdatePassword').show();
	}
}

function updateAddress() {
	$('div#divErrorUpdateAddress').hide();
	$.post('/ecommerce-web/updateAddress', $('form#formUpdateAddress').serialize(), function(data){
		if (data.status) {
			alert('Endereço alterado com sucesso.');
		} else {
			if (data.messageError.sessionInvalid!=undefined && data.messageError.sessionInvalid!=null && data.messageError.sessionInvalid!='') {
				location.href='/ecommerce/'+getPortalContext();
			} else {
				$('span#spanErrorUpdateAddress').text(data.messageError.generic);
				$('div#divErrorUpdateAddress').show();
			}
		}
	}, "json");
}

function updateData() {
	$('div#divErrorUpdateData').hide();
	if ($('input[name="user"]').val()==$('input[name="userConfirm"]').val()) {
		if ($('input[name="newsletter"]').is(":checked")) {
			$('input[name="newsletterText"]').val('true');
		} else {
			$('input[name="newsletterText"]').val('false');
		}
		$.post('/ecommerce-web/updateData', $('form#formUpdateData').serialize(), function(data){
			if (data.status) {
				alert('Cadastro alterado com sucesso.');
			} else {
				if (data.messageError.sessionInvalid!=undefined && data.messageError.sessionInvalid!=null && data.messageError.sessionInvalid!='') {
					location.href='/ecommerce/'+getPortalContext();
				} else {
					$('span#spanErrorUpdateData').text(data.messageError.generic);
					$('div#divErrorUpdateData').show();
				}
			}
		}, "json");
	} else {
		$('span#spanErrorUpdateData').text('Não foi possível confirmar o valor do novo e-mail pois estão diferentes, digite novamente o novo e-mail e a confirmação.');
		$('div#divErrorUpdateData').show();
	}
}

function addCart(productId, toView) {
	if (toView==undefined || toView==null || toView=='') {
		toView = false;
	}
	
	$.post('/ecommerce-web/addCart', {'pid' : productId}, function(data){
		if (data.status) {
			loadHeader();
			if (!toView) {
				alert('O produto foi adicionado com sucesso ao seu carrinho de compras.\n Para concluir a compra ou verificar quais produtos estão no carrinho, basta clicar no link "Meu Carrinho" no topo da página.');
			} else {
				location.href='/ecommerce/'+getPortalContext()+'/mycart';
			}
		} else {
			alert(data.messageError.generic);
		}
	});
}

function myCart() {
	$.getJSON( '/ecommerce-web/mycart?&tk='+new Date().getTime(),  function(data) {
		if (data.status) {

			if (data.generic.cart!=null && data.generic.cart.length>0) {
				var html = 	'<div class="hero-unit" style="padding: 10px; text-align: left;">';
				html += 		'<h5><i class="icon-shopping-cart"></i> Produto(s) no meu carrinho</h5>';
				html += 		'<div style="background-color: white; padding: 10px; text-align: left">';
				html += 			'<form id="formCartAll"><table class="table" style="font-size: 14px;">';
				html += 				'<thead>';
				html += 					'<tr>';
				html += 						'<th colspan="2">Produto</th>';
				html += 						'<th style="text-align: center;">Qtd.</th>';
				html +=							'<th style="text-align: right; width: 15%">Valor unitário</th>';
				html += 						'<th style="text-align: right; width: 15%">Valor total</th>';
				html += 					'</tr>';
				html += 				'</thead>';
				html += 				'<tbody>';
				
				var vTotal = 0.0;
				$.each(data.generic.cart, function(key, val) {
					html += '<tr>';
					html += 	'<td style="width: 90px;"><img src="'+val.images.split('[LIN]')[0].split('[COL]')[1]+'" width="80" style="width: 80px;" /></td>';
					html += 	'<td><strong>'+val.name+'</strong> '+val.introduction.toString().substring(0,80)+' [...]<br><small><a href="#" style="color" onclick="removeItem(\''+val.id+'\');"><i class="icon-remove"></i> Remover</a></small></td>';
					html += 	'<td style="text-align: center;" id="tdQtdPdt">';
					if (val.quantity>0) {
						html += 		'<select name="qtdSelect'+val.id+'" class="span1" onchange="defineQuantity(\''+val.id+'\');">';
						for (var e=1; e<=val.quantity; e++) {
							html += '<option>'+e+'</option>';
						}
						html += 		'</select>';
					} else {
						html += 		'<span style="color: darkred">Produto<br>indisponível</span>';
					}
					html += 	'</td>';
					html += 	'<td style="text-align: right;">R$ <span id="spanUnityvalue'+val.id+'">'+convertMoeda(val.unityvalue)+'</span></td>';
					html += 	'<td style="text-align: right;" id="tdUnityTotalValue">R$ <span id="spanUnityTotalvalue'+val.id+'">'+convertMoeda(val.unityvalue)+'</span></td>';
					html += '</tr>';
					
					vTotal = vTotal + val.unityvalue;
				});
				
				html += 					'<tr>';
				html += 						'<td colspan="4" style="text-align: right;">ENVIO:</td>';
				html += 						'<td style="text-align: right;">R$ <span id="spanFrete">0,00</span></td>';
				html += 					'</tr>';
				html += 					'<tr>';
				html += 						'<td colspan="4" style="text-align: right;">SUBTOTAL:</td>';
				html += 						'<td style="text-align: right;">R$ <span id="spanSubtotal">'+convertMoeda(vTotal)+'</span></td>';
				html += 					'</tr>';
				html += 				'</tbody>';
				html += 			'</table></form>';
				html += 			'<div class="input-append form-inline">';
				html += 				'<label>Digite o seu CEP para calcularmos o frete e o prazo de entrega do seu pedido:&nbsp;</label>';
				html += 				'<input type="text" name="cepToFrete" placeholder="CEP" class="span2">';
				html += 				'<button id="btnFreteCalc" class="btn" type="button" onclick="defineFrete();" data-loading-text="Processando...">Calcular</button>';
				html += 			'</div>';
				html += 		'</div>';
				
				html += 		'<div class="row" style="padding-top: 10px;">';
				html += 			'<div class="span5 muted" style="text-align: left; width: 445px;">';
				html += 				'<h6 style="margin-bottom: 0px;padding-bottom: 0px;"><i class="icon-exclamation-sign"></i> Informações importantes</h6>';
				html += 				'<p style="font-size: 11px;">O prazo começa a contar a partir da aprovação do pedido e confirmação do pagamento.</p>';
				html += 			'</div>';
				html += 			'<div class="span4 muted" style="text-align: right;">';
				html += 				'<h4>TOTAL: R$ <span id="spanTotalvalue">'+convertMoeda(vTotal)+'</span></h4>';
				html += 				'<h6>em até 18x de R$ <span id="spanParcelamentovalue">'+convertMoeda((vTotal/18))+'</span> sem juros</h6>';
				html += 			'</div>';
				html += 		'</div>';
				html += 	'</div>';
				
				html += 	'<div class="row">';
				html += 		'<div class="span5 muted" style="text-align: left">';
				html += 			'<button class="btn" type="button" onclick="location.href=\'/ecommerce/'+getPortalContext()+'\'"><i class="icon-chevron-left"></i> Escolher mais produtos</button>';
				html += 		'</div>';
				html += 		'<div class="span4 muted" style="text-align: right;">';
				html += 			'<a href="#" onclick="nextCart()"><img src="https://p.simg.uol.com.br/out/pagseguro/i/botoes/pagamentos/120x53-comprar-laranja.gif" alt="Pague com PagSeguro - é rápido, grátis e seguro!" /></a>';
				html += 		'</div>';
				html += 	'</div>';
				
				$('div#mycart').html(html);
				$("input[name='cepToFrete']").mask("99999-999");
			} else {
				location.href='/ecommerce/'+getPortalContext();
			}
			
		} else {
			alert(data.messageError.generic);
		}
	});
	
}

function defineQuantity(id) {
	var qtd = $('select[name="qtdSelect'+id+'"]').val();
	var unityValue = $('span#spanUnityvalue'+id).text().replace(',', '.');
	
	$('span#spanUnityTotalvalue'+id).text(convertMoeda((qtd*unityValue)));
	
	var subtotal = 0.0; 
	$('td#tdUnityTotalValue').each(function (){
		subtotal = subtotal + ($(this).find('span').text().replace(',','.') * 1);
	});
	$('span#spanSubtotal').text(convertMoeda(subtotal));
	
	if ($.trim($('input[name="cepToFrete"]').val())!='') {
		defineFrete();
	} else {
		generateOrderTotal();
	}
}

function generateOrderTotal() {
	var frete = $('span#spanFrete').text().replace(',','.');
	var subtotal = $('span#spanSubtotal').text().replace(',','.');
	var total = (subtotal*1) + (frete*1);
	
	$('span#spanTotalvalue').text(convertMoeda(total));
	$('span#spanParcelamentovalue').text(convertMoeda((total/18)));
}

function defineFrete() {
	$('#btnFreteCalc').button('loading');
	
	if ($.trim($('input[name="cepToFrete"]').val())=='') {
		return false;
	}
	
	$.getScript("http://cep.republicavirtual.com.br/web_cep.php?formato=javascript&cep="+$('input[name="cepToFrete"]').val(), function() {
		
		if (resultadoCEP["resultado"]=='1') {
			
			var uf = unescape(resultadoCEP["uf"]);
			var param = '';
			$('td#tdQtdPdt').each(function () {
				var id = $(this).find('select').attr('name').replace('qtdSelect', '');
				var qtd = $(this).find('select').val();
				
				param += '&param='+id+'-'+qtd;
			});
			
			$.getJSON( '/ecommerce-web/getFrete?sid='+getSid()+param+'&tk='+new Date().getTime()+'&uf='+uf,  function(data) {
				if (data.status) {
					$('span#spanFrete').text(convertMoeda(data.generic.frete));
				} else {
					alert(data.messageError.generic);
					$('span#spanFrete').text('0,00');
				}
				
				generateOrderTotal();
				
				$('#btnFreteCalc').button('reset');
			});
			
		} else {
			
			alert("Não foi possível calcular o frete pois o CEP não foi encontrado.");
			$('span#spanFrete').text('0,00');
			
			generateOrderTotal();
			$('#btnFreteCalc').button('reset');
		}
		
    });
}

function nextCart() {
	var cepToFrete = $('input[name=cepToFrete]').val();
	
	if (cepToFrete==undefined || cepToFrete==null || cepToFrete=='') {
		alert('Por favor digite o CEP para calcular o valor do frete.');
	} else {
		loadHeader();
		defineFrete();
		$.getJSON( '/ecommerce-web/session?sid='+getSid()+'&tk='+new Date().getTime(),  function(data) {
			if (data.status) {
				if (data.generic.client!=null) {
					
					if (data.generic.cart!=null) {
						paymentOrder();
					} else {
						alert('Você passou muito tempo inativo, portanto será redirecionado para o inicío para refazer a compra.');
					}
					
				} else {
					
					myData(true);
					
				}
			} else {
				alert(data.messageError.generic);
			}
		});
	}
}

function paymentOrder() {
	$.post('/ecommerce-web/order', $('form#formCartAll').serialize()+'&sid='+getSid()+'&send='+$('span#spanFrete').text()+'&total='+$('span#spanTotalvalue').text(), function(data){
		if (data.status) {
			
			var form = '<form method="post" action="https://pagseguro.uol.com.br/v2/checkout/payment.html">';
			form += 		'<input type="hidden" name="receiverEmail" value="'+data.generic.payment.url+'">';
			form += 		'<input type="hidden" name="currency" value="BRL">';
			
			$.each(data.generic.cart, function(key, val) {
				var i = 1;
				
				form += 		'<input type="hidden" name="itemId'+i.toString()+'" value="'+val.id+'">';
				form += 		'<input type="hidden" name="itemDescription'+i.toString()+'" value="'+val.name+'">';  
				form += 		'<input type="hidden" name="itemAmount'+i.toString()+'" value="'+convertMoeda(val.unityvalue).replace(',', '.')+'">  ';
				form += 		'<input type="hidden" name="itemQuantity'+i.toString()+'" value="'+$('select[name=qtdSelect'+val.id+']').val()+'">';
				form += 		'<input type="hidden" name="itemShippingCost'+i.toString()+'" value="'+$('span#spanFrete').text().replace(',', '.')+'"> ';
				
				i++;
			});
			
			form += 		'<input type="hidden" name="senderName" value="'+data.generic.client.name+'"> '; 
			form += 		'<input type="hidden" name="senderAreaCode" value="">  ';
			form += 		'<input type="hidden" name="senderPhone" value=""> '; 
			form += 		'<input type="hidden" name="senderEmail" value="'+data.generic.client.user+'">';
			
			form += 		'<input type="hidden" name="reference" value="'+data.generic.order+'">';
			
			form += 		'<input type="submit" id="submitFormPagSeg">';
			form += 	'</form>';
			
			if ($('div#divFormPagSeg').size()==0) {
				var html = '<div id="divFormPagSeg" class="hide">';
				html += form;
				html += 	'<div>';
				$('body').append(html);
			} else {
				$('div#divFormPagSeg').html(form);
			}
			
			alert('Obrigado por comprar conosco.\nAo clicar em OK você será redirecionado para o ambiente seguro do "PagSeguro" para concluir o pagamento do seu pedido.\n\nPara consultar o status do seu pedido basta clicar em "Entrar > Meu cadastro > Pedidos em aberto".\nQualquer dúvida entre em contato conosco pelo FaleConosco.');
			
			$('#submitFormPagSeg').click();
		} else {
			alert(data.messageError.generic);
		}
	}, "json");
	
}

function calcFrete() {
	$('div#divQuantityDay').html('');
	$('#btnFreteCalc').button('loading');
	
	if ($.trim($('input[name="cepToFrete"]').val())=='') {
		return false;
	}
	
	$.getScript("http://cep.republicavirtual.com.br/web_cep.php?formato=javascript&cep="+$('input[name="cepToFrete"]').val(), function() {
		
		if (resultadoCEP["resultado"]=='1') {
			
			var uf = unescape(resultadoCEP["uf"]);
			
			$.getJSON( '/ecommerce-web/getFrete?sid='+getSid()+'&tk='+new Date().getTime()+'&uf='+uf,  function(data) {
				if (data.status) {
					$('div#divQuantityDay').html("<strong>Prazo de entrega:</strong> " + data.generic.prazo + " dia(s)");
				} else {
					alert(data.messageError.generic);
				}
				
				$('#btnFreteCalc').button('reset');
			});
			
		} else {
			
			alert("Não foi possível calcular o frete pois o CEP não foi encontrado.");
			
			$('#btnFreteCalc').button('reset');
		}
		
    });
	
}
function myData(isOrder) {
	var modal =		'<div class="modal-header">';
	modal += 			'<button type="button" class="close" data-dismiss="modal" aria-hidden="true">×</button>';
	modal += 			'<h3 id="myModalLabel">Acesse sua conta</h3>';
	modal += 		'</div>';
	modal += 		'<div class="modal-body">';
	modal += 		'<div class="row">';
	modal += 			'<div class="span3">';
	modal += 			'<form id="formLogin" style="padding-bottom: 30px;">';
	modal += 				'<input name="user" type="text" placeholder="Usuário" class="input-block-level">';
	modal += 				'<input name="password" type="password" placeholder="Senha" class="input-block-level">';
	modal += 				'<input type="hidden" name="sid" value="'+getSid()+'">';
	modal += 				'<div id="divErrorLogin" class="alert alert-error hide"><button class="close" data-dismiss="alert" type="button">×</button> <span id="spanErrorLogin">Os dados informados estão incorretos.</span></div>';
	modal += 				'<button class="btn btn-primary pull-right" type="button" onclick="login('+isOrder+')">Acessar</button>';
	modal += 				'<a class="pull-left" href="#" onclick="forgotPassword()">Esqueci minha senha</a>';
	modal += 			'</form>';
	modal += 			'</div>';
	modal += 			'<div class="span2" style="text-align: right; width: 40%">';
	modal += 				'<h4>Seja cliente</h4>';
	modal += 				'Para ser cliente basta se cadastrar.<br>';
	modal += 				'<button class="btn btn-primary" type="button" onclick="$(\'div#divThisClient\').modal(\'hide\');$(\'a#aLinkMyDataModal\').click();">Quero me cadastrar</button>';
	modal += 			'</div>';
	modal += 		'</div>';
	modal += 		'</div>';
	
	if ($('div#divThisClient').size()==0) {
		var html = '<div id="divThisClient" class="modal hide fade" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">';
		html += modal;
		html += 	'</div>';
		
		$('body').append(html);
	} else {
		$('div#divThisClient').html(modal);
	}
	
	$('div#divThisClient').modal('toggle');
}
function removeItem(id) {
	$.post('/ecommerce-web/mycart/removeItem', 'id='+id, function(data){
		if (data.status) {
			myCart();
		} else {
			alert(data.messageError.generic);
		}
	}, "json");
}