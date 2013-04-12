function loadProduct() {
	setMenuActive('product');
	loading('contentAll');
	
	$('#contentAll').load('includes/product.html', function() {
		
		relatProductPage();
				
		preencheImages();
		$("input[name='quantity']").mask("9?999");
		$("input[name='quantityFrete']").mask("9?999");
		$("input[name='unityvalue']").maskMoney({symbol:'R$ ', showSymbol:true, thousands:'.', decimal:',', symbolStay: false});
		
		$.getJSON('/ecommerce-web/admin/product/category/list?tk='+new Date().getTime(), function(data) {
			callback('cadastrodeprodutos_preencheCbCategory', data);
		});
		
		nicEditors.allTextAreas();
		$('.nicEdit-panelContain').parent().css('width', '99%');
		$('.nicEdit-main').parent().css('width', '99%');
		$('.nicEdit-main').parent().css('height', '200px');
		$('.nicEdit-main').css('width', '100%');
		$('.nicEdit-main').css('height', '100%');
		
		$('#uploadFile').live('change', function(){
			$('#form_upload').ajaxForm({
				dataType: 'script',
				target:'#upload_target'
			}).submit();
	     });
		
	});
}

function loadGridProduct() {
	loadGrid('/ecommerce-web/admin/product/consult', 'grid', 'formEdit', '/ecommerce-web/admin/product/remove', true);
}

function relatProductPage() {
	loading('divRelat');
	$.getJSON('/ecommerce-web/admin/product?tk='+new Date().getTime(), function(data) {
		
		if (data.status) {
			
			$('div#divRelat').html('<div id="products-estoque-stat" class="chart" style="padding: 0px; position: relative;"></div><p id="total-estoque" style="text-align: right"></p>');
			
			$('span#spanProductsTotal').text(data.generic[1]);
			$('span#spanProductsSemEstoqueTotal').text(data.generic[2]);
			$('span#spanTitleEcommerce').text(data.generic[0]);
			
			var html = '';
			$.each(data.generic[3], function(key, val) {
				html += '<li class="contact-alt grd-white">';
                html += '<a href="#">';
                html += '<div class="contact-item">';
                html += '<div class="contact-item-body">';
				html += '<p class="contact-item-heading bold">'+val.name+'</p>';
                html += '<p class="help-block"><small class="muted">'+val.introduction+'</small></p>';
                html += '</div>';
                html += '</div>';
                html += '</a>';
                html += '</li>';
			});
			$('ul#ulProdutosSemEstoque').html(html);
			
			generateRelat(data.generic[4], data.generic[5], "products-estoque-stat");
			$('p#total-estoque').html("<strong>Total de produtos em estoque: </strong>"+data.generic[5]);
			
			activePlugins();
			
		} else {
			
			errorForm(data);
			$('#contentAll').html('');
			
		}
	});
}

function getSequenceImages(images, w, h, update) {
	if (update==undefined || update==null || update=='') {
		update = false;
	}
	
	var newvalueSplit = images.split('[LIN]');
	var newvalue = '';
	
	var position = 1;
	while (position <= newvalueSplit.length) {
		for (var a=0; a<newvalueSplit.length; a++) {
			var nv = newvalueSplit[a].split('[COL]');
			
			if (nv[0].toString() == position.toString()) {
				if (update) {
					newvalue += '<a href="#" onclick="upload(\''+nv[0]+'\');"><img id="iposition'+nv[0]+'" src="'+nv[1]+'" width="'+w+'" height="'+h+'" style="width: '+w+'px; height: '+h+'"></a>';
				} else {
					newvalue += '<img id="iposition'+nv[0]+'" src="'+nv[1]+'" width="'+w+'" height="'+h+'" style="width: '+w+'px; height: '+h+'">';
				}
				
				position++;
			}
		}
	}
	for (var e= (newvalueSplit.length+1); e<=6; e++) {
		if (update) {
			newvalue += '<a href="#" onclick="upload(\''+e+'\');"><img id="iposition'+e+'" src="/library/img/imgfoto.png" class="img-rounded" width="'+w+'" height="'+h+'" style="width: '+w+'px; height: '+h+'"></a>';
		} else {
			newvalue += '<img id="iposition'+e+'" src="/library/img/imgfoto.png" class="img-rounded" width="'+w+'" height="'+h+'" style="width: '+w+'px; height: '+h+'">';
		}
	}
	
	return newvalue;
}

function cadastrodeprodutos_preencheCbCategory(data) {
	var opt = '';
	$.each(data.generic, function(key, val) {
		opt += '<option value="'+val.id+'">'+val.name+'</option>';
	});
	$('select#comboCategory').html(opt);
}

function cadatrodeprodutos_insert(data) {
	if ($('input[name=id]').val()=='') {
		alert("Produto criado com sucesso.", true);
		$('button[type="reset"]').click();
		preencheImages();
	} else {
		alert("Produto alterado com sucesso.", true);
		$('#btnprodutoscadastrados').click();
		$('#lieditarproduto').hide();
	}
}

function upload(position) {
	$('img#iposition'+position).attr('src', '/ecommerce/admin/static/img/imgfotoLoading.png');
	$('input#positionFile').val(position);
	$('input#uploadFile').click();
}

function preencheImages() {
	var html = 	'<a href="#" onclick="upload(\'1\');"><img id="iposition1" src="/ecommerce/admin/static/img/imgfoto.png" class="img-rounded" width="128" height="131"></a>';
	html +=		'<a href="#" onclick="upload(\'2\');"><img id="iposition2" src="/ecommerce/admin/static/img/imgfoto.png" class="img-rounded" width="128" height="131"></a>';
	html +=		'<a href="#" onclick="upload(\'3\');"><img id="iposition3" src="/ecommerce/admin/static/img/imgfoto.png" class="img-rounded" width="128" height="131"></a>';
	html +=		'<a href="#" onclick="upload(\'4\');"><img id="iposition4" src="/ecommerce/admin/static/img/imgfoto.png" class="img-rounded" width="128" height="131"></a>';
	html +=		'<a href="#" onclick="upload(\'5\');"><img id="iposition5" src="/ecommerce/admin/static/img/imgfoto.png" class="img-rounded" width="128" height="131"></a>';
	html +=		'<a href="#" onclick="upload(\'6\');"><img id="iposition6" src="/ecommerce/admin/static/img/imgfoto.png" class="img-rounded" width="128" height="131"></a>';
	$('#imagesDivUpload').html(html);
	$('#imagesForm').val('');
}

function addCategory() {
	opt = $('select#comboCategory option:selected').text();
	optId = $('select#comboCategory option:selected').val();
	
	genericAddCategory(opt, optId);
}

function genericAddCategory(opt, optId) {
	if ($('input#category').val()!='') {
		sel = $('input#category').val().split('[LIN]');
	} else {
		sel = new Array();
	}
	
	var exist = false;
	for (var i=0; i<sel.length; i++) {
		if (sel[i] == optId) {
			exist = true;
		}
	}
	
	if (!exist) {
		$('div#selectedsCategory').html($('div#selectedsCategory').html() + '<button id="optSel'+optId+'" class="btn btn-info" type="button" onclick="remCategory('+optId+');">'+opt+' <i class="icon-remove"></i></button>&nbsp;');
		
		if ($('input#category').val()!='') {
			$('input#category').val($('input#category').val()+'[LIN]'+optId);
		} else {
			$('input#category').val(optId);
		}
	}
}



function remCategory(id) {
	$('#optSel'+id).remove();
	
	var sel = $('input#category').val().split('[LIN]');
	var nv = '';
	
	for (var i=0; i<sel.length; i++) {
		if (sel[i] != id) {
			if (nv == '') {
				nv += sel[i];
			} else {
				nv += '[LIN]'+sel[i];
			}
		}
	}
	
	$('input#category').val(nv);
}

function cleanCategory() {
	$('input#category').val('');
	$('div#selectedsCategory').html('');
}

function preencheTextArea() {
	 $('textarea[name="description"]').val($('div.nicEdit-main').html());
}
function limpaTA() {
	 $('textarea[name="description"]').val('');
	 $('div.nicEdit-main').html('');
}

function uploadSet(urlImg) {
	var position = $('#positionFile').val();
	
	$('#iposition'+position).attr('src', urlImg); 
	$('#resetUpload').click();
	
	var division = '[LIN]';
	var divisionCol = '[COL]';
	
	var images = $('#imagesForm').val();
	var image = new Array();
	
	if (images!=undefined && images!=null && images!='') {
		var image = images.split(division);
	}
	
	var finalValue = '';
	
	var isPositionExist = 0;
	if (image.length>0) {
		for (var e=0; e<image.length; e++) {
			var i = image[e].split(divisionCol);
			
			if (i[0]==position) {
				isPositionExist = 1;
			}
		}
	}
	
	if (isPositionExist == 0) {
		if (image.length>0) {
			finalValue = images + division + position + divisionCol + urlImg;
		} else {
			finalValue = position + divisionCol + urlImg;
		}
	} else {
		for (var e=0; e<image.length; e++) {
			var i = image[e].split('-');
			
			if (i[0]!=position) {
				if (finalValue!='') {
					finalValue = finalValue + division + image[e];
				} else {
					finalValue = image[e];
				}
			} else {
				if (finalValue!='') {
					finalValue = finalValue + division + position + divisionCol + urlImg;
				} else {
					finalValue = position + divisionCol + urlImg;
				}
			}
		}
	}
	
	$('#imagesForm').val(finalValue);
	
}

function preencheFormularioDeEdicao(id) {
	$('#lieditarproduto').show();
	$('#btneditarproduto').click();
	
	$('#formulariocadastroproduto').hide();
	$('#processamentoeditarproduto').show();
	
	loading('processamentoeditarproduto');
	
	$.getJSON( '/ecommerce-web/admin/product/load?id='+id ,  function(data) {
		
		$('#imagesDivUpload').html(getSequenceImages(data.generic.images, '128', '131', true));
		$('#imagesForm').val(data.generic.images);
		$('input[name="name"]').val(data.generic.name);
		$('input[name="introduction"]').val(data.generic.introduction);
		$('div.nicEdit-main').html(data.generic.description);
		$('input[name="quantity"]').val(data.generic.quantity);
		$('input[name="quantityFrete"]').val(data.generic.quantityFrete);
		$('input[name="unityvalue"]').val(data.generic.unityvalue);
		$('input[name="id"]').val(data.generic.id);
		preencheInitCategory(data.genericList);
		//$('textarea[name="description"]').val(data.generic.description);
		
		$('#formulariocadastroproduto').show();
		$('#processamentoeditarproduto').hide();
		
	});
} 

function preencheInitCategory(dataList) {
	$.each(dataList, function(key, val) {
		genericAddCategory(val.category.name, val.category.id);
	});
}
