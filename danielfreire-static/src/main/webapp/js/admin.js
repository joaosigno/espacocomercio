$('input').blur(function(){
	$(this).parent().parent().attr('class', $(this).parent().parent().attr('class').replace('error', ''));
	$('#spanError-'+$(this).attr('name')).text('');
});

function errorForm(data, context) {
	if (context==undefined || context==null) {
		context = '/sso';
	}
	if (!data.status) {
		$.each(data.messageError, function(key, val) {
			if (key=='generic') {
				alert(val, false);
			} else if (key=='sessionInvalid') {
				location.href=context+'?error='+val;
			} else {
				$('[name="'+key+'"]').parent().parent().attr('class', $('[name="'+key+'"]').parent().parent().attr('class')+" error");
				$('#spanError-'+key).text(val);
			}
		});
	}
}

function errorFormGrid(data, id, divTableId) {
	if (!data.status) {
		$.each(data.messageError, function(key, val) {
			if (key=='generic') {
				alert(val, false);
			} else if (key=='sessionInvalid') {
				location.href='/sso?error='+val;
			} else {
				var idtk = divTableId + id + key;
				$('td#'+idtk).attr('class', 'control-group error');
			}
		});
	}
}

function loadFooter() {
	var html = '<div class="navbar navbar-inverse navbar-fixed-bottom"><div class="navbar-inner" style="border-color: #f5f5f5; background-color: #f5f5f5; background-image: linear-gradient(to bottom, #f5f5f5, #f5f5f5);">';
	html 		+= '<div class="container">';
	html 			+= '<p class="muted credit" style="text-align: right;">&copy;2012 <a href="danielfreire.net">danielfreire.net</a></p>';
	html		+= '</div>';
	html 	+= '</div></div>';
	
	$('div#footer').html(html);
}

function loadMenu(urlJson, initData) {
	if (initData==undefined || initData==null) {
		initData = false;
	}
	$.getJSON( urlJson,  function(data) {
		var raiz;
		
		if (initData) {
			raiz = data.generic;
			errorForm(data, '/advocacy');
		} else {
			raiz = data;
		}
		
		var html = 	'<div class="navbar navbar-inverse navbar-fixed-top"><div class="navbar-inner">';
		html += 		'<div class="container">';
		html += 			'<a class="btn btn-navbar" data-toggle="collapse" data-target=".nav-collapse">';
		html += 				'<span class="icon-bar"></span><span class="icon-bar"></span><span class="icon-bar"></span>';
		html += 			'</a>';
		html += 			'<a class="brand" href="#">'+raiz.title+'</a>';
		html += 			'<div class="nav-collapse collapse">';
		html += 				'<ul class="nav">';
		
		if (raiz.position==undefined || raiz.position==null || raiz.position==false) {
			$.each(raiz.menus, function(key, val) {
				html += generateItemMenu(key, val);
			});
		} else {
			var i = 1;
			$.each(raiz.menus, function(key, val) {
				$.each(raiz.menus, function(key2, val2) {
					if (val2.position == i) {
						html += generateItemMenu(key2, val2);
					}
				});
				i++;
			});
		}
			
		html += 				'</ul>';
		html += 			'</div>';
		html += 		'</div>';
		html += 	'</div></div>';
		
		$('div#menu').html(html);
	});
}

function generateItemMenu(key, val) {
	var html = '';
	
	if (val.type=='link') {
		html += '<li>';
		html += 	'<a href="'+val.url+'">'+key+'</a>';
		html += '</li>';
	} else if (val.type=='dropdown') {
		html += '<li class="dropdown">';
		html += 	'<a href="'+val.url+'" class="dropdown-toggle" data-toggle="dropdown">'+key+' <b class="caret"></b></a>';
		html += 	'<ul class="dropdown-menu">';
		$.each(val.options, function(keyOpt, valOpt) {
			if (keyOpt!='divider' && valOpt!='#') {
				html += '<li><a href="'+valOpt+'">'+keyOpt+'</a></li>';
			} else if (keyOpt=='divider') {
				html += '<li class="divider"></li>';
			} else {
				html += '<li class="nav-header">'+keyOpt+'</li>';
			}
		});
		html += 	'</ul>';
		html += '</li>';
	}
	
	return html;
}

function loadGrid(urlJson, divId, formEditId, urlDelete, editUrl) {
	$.blockUI({ 
        message: '<h3>Processando</h3>',
        css: { 
            border: 'none', 
            padding: '15px', 
            backgroundColor: '#000', 
            '-webkit-border-radius': '10px', 
            '-moz-border-radius': '10px', 
            opacity: .5, 
            color: '#fff' 
        } 
	});
	
	if (editUrl==undefined || editUrl==null) {
		editUrl = false;
	}
	
	urlJson = insertParam(urlJson, 'tk', new Date().getTime());
	
	$.getJSON( urlJson,  function(data) {
		if (data.status!=undefined && !data.status) {
			$.unblockUI();
			$.each(data.messageError, function(key, val) {
				if (key=='generic') {
					alert(val, false);
				} else if (key=='sessionInvalid') {
					location.href='/sso?error='+val;
				}
			});
		} else {
			var pageNow = data.page;
			var totalPages = data.totalPages;
		
			var html = 	'<table class="table table-striped">';
			html += 		'<thead>';
			html +=				'<tr>';
			
			var menu = new Array();
			var i = 0;
			$.each(data.titles, function(key, val) {
				if (val.type=='text') {
					if (val.id=='permissions') {
						html += '<th style="text-align: center" id="th'+val.id+'">'+val.title+'</th>';
					} else {
						html += '<th id="th'+val.id+'">'+val.title+'</th>';
					}
				} else if (val.type=='image') {
					html += '<th><i class="'+val.imgClass+'"></i></th>';
				}
				
				menu[i] = val.id;
				i++;
			});
			
			html += 				'<th style="text-align: center; width: 5%;"><i class="icon-align-justify"></i></th>';
			html +=				'</tr>';
			html += 		'</thead>';
			html += 		'<tbody>';
			
			$.each(data.rows, function(key, val) {
				var idcomp = divId + val.id;
				
				html += '<tr>';
				for (var e = 0; e < menu.length; e++) {
					
					if (menu[e]=='images') {
						html += '<td>'+getSequenceImages(val[menu[e]], '20', '20')+'</td>';
					} else if (menu[e]=='unityvalue' || menu[e]=='totalValue' || menu[e]=='sendCust' || menu[e]=='discount' || menu[e]=='value') {
						if (val[menu[e]]!=null && val[menu[e]]!=undefined && val[menu[e]]!='') {
							if (val[menu[e]].toString().indexOf('.')==-1) {
								html += '<td id="' + idcomp + menu[e] + '">R$ '+val[menu[e]].toString()+',00</td>';
							} else {
								html += '<td id="' + idcomp + menu[e] + '">R$ '+val[menu[e]].toString().replace('.', ',')+'</td>';
							}
						} else {
							html += '<td id="' + idcomp + menu[e] + '">R$ 0,00</td>';
						}
					} else if (menu[e]=='dateCreate' || menu[e]=='datePayment' || menu[e]=='dateExpiration') {
						if (val[menu[e]]!=null && val[menu[e]]!=undefined && val[menu[e]]!='') {
							var dat = new Date();
							dat.setTime(val[menu[e]].toString());
							var hour = '';
							if (dat.getHours()!=0 && dat.getMinutes()!=0) {
								hour = ' '+dat.getHours()+':'+dat.getMinutes();
							} 
							
							var dtAll = '';
							if (dat.getDate().toString().length==1) {
								dtAll += '0'+dat.getDate()+'/';
							} else {
								dtAll += dat.getDate()+'/';
							}
							if ((dat.getMonth()+1).toString().length==1) {
								dtAll += '0'+(dat.getMonth()+1).toString()+'/';
							} else {
								dtAll += (dat.getMonth()+1).toString()+'/';
							}
							dtAll+=dat.getFullYear();
							
							html += '<td id="' + idcomp + menu[e] + '">'+dtAll+hour+'</td>';
						} else {
							html += '<td id="' + idcomp + menu[e] + '"></td>';
						}
					} else if (menu[e]=='statusOrder') {
						html += '<td id="' + idcomp + menu[e] + '">'+getLblOrderStatus(val[menu[e]])+'</td>';
					} else if (menu[e]=='cesta') {
						html += '<td style="text-align: center"><a href="#" onclick="viewCart(\''+val.id+'\');"><i class="icon-plus"></i></a></td>';
					} else if (menu[e]=='permissions') {
						html += '<td style="text-align: center"><a href="#" onclick="viewPermissions(\''+val.id+'\');"><i class="icon-plus"></i></a></td>';
					} else {
						if(typeof(val[menu[e]]) == "string"){
							if (val[menu[e]]!=undefined && val[menu[e]]!=null) {
								html += '<td id="' + idcomp + menu[e] + '">'+val[menu[e]].toString()+'</td>';
							} else {
								html += '<td id="' + idcomp + menu[e] + '"></td>';
							}
						}else if (typeof(val[menu[e]]) == "object"){
							if (val[menu[e]]!=undefined && val[menu[e]]!=null) {
								if (val[menu[e]].title!=undefined && val[menu[e]].title!=null) {
									html += '<td id="' + idcomp + menu[e] + '">'+val[menu[e]].id + ' - ' + val[menu[e]].title+'</td>';
								} else if (val[menu[e]].name!=undefined && val[menu[e]].name!=null) {
									html += '<td id="' + idcomp + menu[e] + '">'+val[menu[e]].id + ' - ' + val[menu[e]].name+'</td>';
								}
							} else {
								html += '<td id="' + idcomp + menu[e] + '"></td>';
							}
						}else if (typeof(val[menu[e]]) == "boolean"){
							html += '<td id="' + idcomp + menu[e] + '">'+val[menu[e]]+'</td>';
						}else {
							if (val[menu[e]]!=undefined && val[menu[e]]!=null) {
								html += '<td id="' + idcomp + menu[e] + '">'+val[menu[e]].toString()+'</td>';
							} else {
								html += '<td id="' + idcomp + menu[e] + '"></td>';
							}
						}
					} 
					
				}
				
				if (!editUrl) {
					html += '<td id="' + idcomp + '" style="text-align:center;"><a href="#" onclick="editTable(\''+val.id+'\', \''+divId+'\', \''+formEditId+'\', \''+urlJson+'\', \''+urlDelete+'\');"><i class="icon-edit"></i></a>';
				} else {
					html += '<td style="text-align:center;"><a href="../update?pid='+val.id+'"><i class="icon-edit"></i></a>';
				} 
				
				if (urlDelete != undefined && urlDelete != null && urlDelete != '') {
					html += '<a href="#" onclick="removeTable(\''+ urlDelete +'\', \''+val.id+'\', \''+divId+'\', \''+formEditId+'\', \''+urlJson+'\');"><i class="icon-trash"></i></a>';
				}
				html += '</td>';
				html += '</tr>';
				
			});
			
			html += 		'</tbody>';
			html += 	'</table>';
			html += 	'<div class="pagination" style="text-align:right">';
			html += 		'<ul>';
			if (pageNow>1) {
				html += '<li><a href="javascript: loadGrid(\''+insertParam(urlJson, 'page', ((pageNow*1)-1))+'\', \''+divId+'\', \''+formEditId+'\', \''+urlDelete+'\', '+editUrl+');">Anterior</a></li>';
			} else {
				html += '<li class="disabled"><a href="#">Anterior</a></li>';
			}
			
			for (var a=1; a <= (totalPages*1); a++) {
				if (a == pageNow) {
					html += '<li class="active"><a href="#">'+a.toString()+'</a></li>';
				} else {
					html += '<li><a href="javascript: loadGrid(\''+insertParam(urlJson, 'page', a)+'\', \''+divId+'\', \''+formEditId+'\', \''+urlDelete+'\', '+editUrl+');">'+a.toString()+'</a></li>';
				}
			}
			
			if (pageNow<totalPages) {
				html += '<li><a href="javascript: loadGrid(\''+insertParam(urlJson, 'page', ((pageNow*1)+1))+'\', \''+divId+'\', \''+formEditId+'\', \''+urlDelete+'\', '+editUrl+');">Próxima</a></li>';
			} else {
				html += '<li class="disabled"><a href="#">Próxima</a></li>';
			}
			
			html += 		'</ul>';
			html += 	'</div>';
			
			$('div#'+divId).html(html);
			$.unblockUI();
		}
	});
}

function editTable(id, divId, formId, urlJson, urlDelete) {
	var idcomp = divId + id;
	
	$('form#'+formId).find('input').each(function() {
		var name = $(this).attr('name');
		var clas = $(this).attr('class');
		if (name=='id') {
			$(this).val(id);
		} else {
			var idtk = idcomp + name;
			var tdvalue = $('td#'+idtk).text();
			
			if ($(this).attr('type')=='text') {
				if (clas.indexOf('datePickerInput')!=-1) {
					$('td#'+idtk).html('<input type="text" id="input'+idtk+'" value="'+tdvalue+'" class="input-block-level datePickerInput">');
				} else if (tdvalue.indexOf('R$ ')!=-1) {
					$('td#'+idtk).html('<input type="text" id="input'+idtk+'" value="'+(tdvalue.replace('R$ ', ''))+'" class="input-block-level money">');
				} else {
					$('td#'+idtk).html('<input type="text" id="input'+idtk+'" value="'+tdvalue+'" class="input-block-level">');
				}
			}
		}
	});
	$('form#'+formId).find('select').each(function() {
		var name = $(this).attr('name');
		var idtk = idcomp + name;
		var tdvalue = $('td#'+idtk).text();
		
		$(this).val(tdvalue);
		
		$('td#'+idtk).html('<select id="select'+idtk+'" class="input-block-level">' + $(this).html() + '</select>');
		
		if (idtk=='grid1statusOrder') {
			$('#select'+idtk).val(getIdOrderStatus(tdvalue));
		} else {
			if (tdvalue.indexOf(' - ')==-1) {
				$('#select'+idtk).val(tdvalue);
			} else {
				$('#select'+idtk).val(tdvalue.split(' - ')[0]);
			}
		}
	});
	
	if ($("input.money").size()>0) {
		$("input.money").maskMoney({symbol:'R$ ', showSymbol:true, thousands:'.', decimal:',', symbolStay: false});
	}
	
	$( "#grid .datePickerInput" ).datepicker();
	$( "#grid .datePickerInput" ).datepicker( "option", "dateFormat", 'dd/mm/yy' );
	
	$('td#'+idcomp).html('<a href="#" onclick="submitEdit(\''+id+'\', \''+divId+'\', \''+formId+'\', \''+urlJson+'\', \''+urlDelete+'\');"><i class="icon-ok"></i></a><a href="#" onclick="loadGrid(\''+urlJson+'\', \''+divId+'\', \''+formId+'\', \''+urlDelete+'\');"><i class="icon-remove"></i></a>');
}

function submitEdit(id, divId, formId, urlJson, urlDelete) {
	$( ".datePickerInput" ).datepicker( "destroy" );
	var idcomp = divId + id;
	
	$('form#'+formId).find('input').each(function() {
		var name = $(this).attr('name');
		if (name!='id') {
			var idtk = idcomp + name;
			$(this).val($('#input'+idtk).val());
		}
	});
	$('form#'+formId).find('select').each(function() {
		var name = $(this).attr('name');
		var idtk = idcomp + name;
		$(this).val($('#select'+idtk).val());
	});
	
	$.post($('form#'+formId).attr('action'), $('form#'+formId).serialize(), function(data){
		if (data.status) {
			alert("Alteração realizada com sucesso.", true);
			loadGrid(urlJson, divId, formId, urlDelete);
		} else {
			errorFormGrid(data, id, divId);
		}
	}, "json");
}

function removeTable(urlDelete, id, divId, formId, urlJson) {
	$.post(urlDelete, 'id='+id, function(data){
		if (data.status) {
			alert("Alteração realizada com sucesso.", true);
			loadGrid(urlJson, divId, formId, urlDelete);
		} else {
			errorFormGrid(data, id, divId);
		}
	}, "json");
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
			var nv = newvalueSplit[a].split('-');
			
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
function generateAddButton(label, urlSubmit, urlJson, divId, formId, urlDelete) {
	html = '<div class="row">';
    html += '<div class="span6"><a href="#" onclick="add(\''+urlSubmit+'\', \''+urlJson+'\', \''+divId+'\', \''+formId+'\', \''+urlDelete+'\');" style="color: black"><i class="icon-plus-sign"></i> '+label+'</a></div>';
    html += '</div>';
	
    $('div#control').html(html);
}

function add(urlSubmit, urlJson, divId, formId, urlDelete) {
	$('form#'+formId).reset();
	if ($('tr#gridNewObject').size()==0) { 
		var array = new Array();
		var i = 0;
		$('div#grid').find('th').each(function() {
			var id = $(this).attr('id');
			if (id != undefined && id != null && id != '') {
				array[i] = id;
			} else {
				array[i] = 'control';
			}
			i++;
		});
		
		var html = '<tr id="gridNewObject">';
		for (var e=0; e<array.length; e++) {
			var id = array[e];
			if (id!='control') {
				html += '<td id="gridRowNew'+id.substring(2)+'"></td>'
			} else {
				html += '<td id="controlInsert" style="text-align: center;"><a onclick="saveGrid(\''+urlSubmit+'\', \''+urlJson+'\', \''+divId+'\', \''+formId+'\', \''+urlDelete+'\')" href="#"><i class="icon-ok-sign"></i></a><a href="#" onclick="cancelSaveGrid()"><i class="icon-minus-sign"></i></a></td>'
			}
		}
		html += '</tr>';
		
		$('div#grid table tbody').prepend(html);
		
		for (var e=0; e<array.length; e++) {
			var id = array[e];
			if (id!='control') {
				$('[name="'+id.substring(2)+'"]').clone().appendTo('#gridRowNew'+id.substring(2));
			}
		}
	}
	$( "#gridNewObject .datePickerInput" ).datepicker();
	$( "#gridNewObject .datePickerInput" ).datepicker( "option", "dateFormat", 'dd/mm/yy' );
	
	if ($("input.money").size()>0) {
		$("input.money").maskMoney({symbol:'R$ ', showSymbol:true, thousands:'.', decimal:',', symbolStay: false});
	}
}

function saveGrid(urlSubmit, urlJson, divId, formId, urlDelete) {
	$( ".datePickerInput" ).datepicker( "destroy" );
	var params = '';
	
	$('tr#gridNewObject').find('input').each(function() {
		if (params == '') {
			params += $(this).attr('name') + '=' + $(this).val();
		} else {
			params += '&' + $(this).attr('name') + '=' + $(this).val();
		}
	});
	$('tr#gridNewObject').find('select').each(function() {
		if (params == '') {
			params += $(this).attr('name') + '=' + $(this).val();
		} else {
			params += '&' + $(this).attr('name') + '=' + $(this).val();
		}
	});
	
	$.post(urlSubmit, params, function(data){
		if (data.status) {
			alert("Inserção realizada com sucesso.", true);
			loadGrid(getFilterParams(urlJson), divId, formId, urlDelete);
		} else {
			alert(data.messageError.generic, false);
		}
	}, "json");
}

function cancelSaveGrid() {
	$( ".datePickerInput" ).datepicker( "destroy" );
	$('tr#gridNewObject').remove();
}

function getFilterParams(url) {
	if ($('div#filter').size()>0) {
		var param = '';
		if (url.indexOf('?')==-1) {
			param = '?';
		}
		$('div#filter').find('input[type="text"]').each(function(){
			if (param!='?' && param!='') {
				param += '&'+$(this).attr('name') + "=" + $(this).val();
			} else {
				param += $(this).attr('name') + "=" + $(this).val();
			}
		});
		$('div#filter').find('select').each(function(){
			if (param!='?' && param!='') {
				param += '&'+$(this).attr('name') + "=" + $(this).val();
			} else {
				param += $(this).attr('name') + "=" + $(this).val();
			}
		});
		
		url += param;
	}
	
	return url;
}

function alert(msg, success) {
	$('div#divErrorAlert').remove();

	if (!success) {
		var html = 	'<div id="divErrorAlert" class="alert alert-block alert-error fade in">';
	    html += 		'<button id="divErrorAlertButtonClose" type="button" class="close" data-dismiss="alert">×</button>';
	    html += 		'<h4 class="alert-heading">Ocorreu um erro!</h4>';
	    html += 		'<p>Motivo: '+msg+'</p>';
	    html += 		'<p>';
	    html += 			'<a class="btn btn-danger" onclick="$(\'#divErrorAlertButtonClose\').click();" href="#">Ok</a>';
	    html += 		'</p>';
	    html += 	'</div>';
	} else {
		var html = 	'<div id="divErrorAlert" class="alert alert-block alert-success fade in">';
	    html += 		'<button id="divErrorAlertButtonClose" type="button" class="close" data-dismiss="alert">×</button>';
	    html += 		'<h4 class="alert-heading">Sucesso!</h4>';
	    html += 		'<p>Motivo: '+msg+'</p>';
	    html += 		'<p>';
	    html += 			'<a class="btn btn-success" onclick="$(\'#divErrorAlertButtonClose\').click();" data-dismiss="alert" href="#">Ok</a>';
	    html += 		'</p>';
	    html += 	'</div>';
	}
    
	$('.breadcrumb').after(html);
}