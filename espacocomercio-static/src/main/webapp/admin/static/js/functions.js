var titlePage = "";
function loadMenuAdmin() {
	$.getJSON('/ecommerce-web/admin/menu?tk='+new Date().getTime(), function(data) {
		if (data.status) {
			loadMenu('/ecommerce/admin/static/json/menu'+data.generic[0]+'.json');
			titlePage = data.generic[1];
			$('span#spanEmailPerfil').text(data.generic[2]);
			$('a#aToLoja').attr('href', '/ecommerce/'+data.generic[3]);
		} else {
			errorForm(data)
		}
		
	});
}

function loadMenu(urlJson) {
	$.getJSON( urlJson,  function(data) {
		
		var html = 	'<aside class="side-left" style="top: 60px;">';
		html += 		'<ul class="sidebar">';
		
		$.each(data.menus, function(key, val) {
			html += generateItemMenu(key, val);
		});
		
		html += 		'</ul>';
		html +=		'</aside>';
				
		$('div#divAdminMenu').html(html);
		
		$('li#liMenu-home').attr('class', 'active');
	});
}

function generateItemMenu(key, val) {
	var html = '';
	
	html += '<li id="liMenu-'+val.id+'">';
	html += 	'<a href="#" onclick="'+val.url+'" title="'+key+'">';
	html += 		'<div class="helper-font-24">';
	html += 			'<i class="'+val.icon+'"></i>';
	html += 		'</div>';
	html +=			'<span class="sidebar-text">'+key+'</span>';
	html += 	'</a>';
	html += '</li>';
	
	return html;
}

function errorForm(data) {
	if (!data.status) {
		$.each(data.messageError, function(key, val) {
			if (key=='generic') {
				alert(val);
			} else if (key=='sessionInvalid') {
				location.href='/ecommerce/portal/login';
			} else {
				$('[name="'+key+'"]').parent().parent().attr('class', $('[name="'+key+'"]').parent().parent().attr('class')+" error");
				$('#spanError-'+key).text(val);
			}
		});
	}
}

function activePlugins() {
	// tooltip helper
    $('[rel=tooltip]').tooltip();	
    $('[rel=tooltip-bottom]').tooltip({
        placement : 'bottom'
    });	
    $('[rel=tooltip-right]').tooltip({
        placement : 'right'
    });	
    $('[rel=tooltip-left]').tooltip({
        placement : 'left'
    });	
    // end tooltip helper
    
    
    // animate scroll, define class scroll will be activate this
    $(".scroll").click(function(e){
        e.preventDefault();
        $("html,body").animate({scrollTop: $(this.hash).offset().top-60}, 'slow');
    });
    // end animate scroll
    
    
    // control box
    // collapse a box
    $('.header-control [data-box=collapse]').click(function(){
        var collapse = $(this),
        box = collapse.parent().parent().parent();

        collapse.find('i').toggleClass('icofont-caret-up icofont-caret-down'); // change icon
        box.find('.box-body').slideToggle(); // toggle body box
    });

    // close a box
    $('.header-control [data-box=close]').click(function(){
        var close = $(this),
        box = close.parent().parent().parent(),
        data_anim = close.attr('data-hide'),
        animate = (data_anim == undefined || data_anim == '') ? 'fadeOut' : data_anim;

        box.addClass('animated '+animate);
        setTimeout(function(){
            box.hide()
        },1000);
    });
    // end control box
    $('#tab-stat > a[data-toggle="tab"]').on('shown', function(){
        if(sessionStorage.mode == 4){
            $('body,html').animate({
                scrollTop: 0
            }, 'slow');
        }
    });
    
    $("span[data-chart=peity-bar]").peity("bar");
}

function loading(divId) {
	$('#'+divId).html('<div style="text-align:center; min-height: 600px; width: 100%"><img src="static/img/loading.gif"></div>');
}

function setMenuActive(p) {
	$('ul.sidebar').find('li').each(function() {
		$(this).removeAttr('class');
	});
	$('li#liMenu-'+p).attr('class', 'active');
}

function checkMessages() {
	$.getJSON('/ecommerce-web/admin/msg?tk='+new Date().getTime(), function(data) {
		if (data.status) {
			$('a#newMessagesCount').html(data.generic);
		}
	});
}

function generateRelat(list, total, id) {
	var d4 = [];
    var i = 0;
    var t = 0;
    $.each(list, function(key, val) {
		var percent = (((val.quantity * 100) / total).toString().split('.')[0] * 1);
    	d4[i] = {
			label: percent + "% - " + val.name,
			data: percent
		}
    	i++;
    	t = t + percent;
    });
    d4[i] = {
    		label: (100 - t) + "% - Outros",
			data: 100 - t
		}
    
    $.plot('#'+id, d4, {
        series: {
            pie: {
                show: true
            }
        },
        legend: {
            show: true,
            labelFormatter: function(label, series) {
                return '<p style="text-align:left;font-size:12px;padding:0px;margin:0px;">' + label + '</p>';
            }
        },
        grid: {
        	hoverable: true,
        	clickable: true
        }
    });
}



function showTooltip(x, y, contents) {
    $('<div id="tooltip" class="bg-black corner-all color-white">' + contents.replace('.00','') + '</div>').css( {
        position: 'absolute',
        display: 'none',
        top: y + 5,
        left: x + 5,
        border: '0px',
        padding: '2px 10px 2px 10px',
        opacity: 0.9,
        'font-size' : '11px'
    }).appendTo("body").fadeIn(200);
}

function convertMonth(num) {
	if (num==1) return "jan";
	if (num==2) return "fev";
	if (num==3) return "mar";
	if (num==4) return "abr";
	if (num==5) return "mai";
	if (num==6) return "jun";
	if (num==7) return "jul";
	if (num==8) return "ago";
	if (num==9) return "set";
	if (num==10) return "out";
	if (num==11) return "nov";
	if (num==12) return "dez";
}

function loadGrid(urlJson, divId, formEditId, urlDelete, editUrl) {
	loading(divId);
	if (editUrl==undefined || editUrl==null) {
		editUrl = false;
	}
	
	urlJson = insertParam(urlJson, 'tk', new Date().getTime());
	
	$.getJSON( urlJson,  function(data) {
		if (data.status!=undefined && !data.status) {
			errorForm(data);
			$('#'+divId).html("");
		} else {
			var pageNow = data.page;
			var totalPages = data.totalPages;
		
			var html = 	'<table class="table table-striped table-hover" style="margin-top: 30px;">';
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
					} else if (menu[e]=='dateCreate' || menu[e]=='datePayment' || menu[e]=='dateExpiration' || menu[e]=='born') {
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
					} else if (menu[e]=='permission') {
						html += '<td id="' + idcomp + menu[e] + '">'+getLblPermissions(val[menu[e]].toString())+'</td>';
					} else if (menu[e]=='address') { 
						html += '<td style="text-align: center"><a href="#" onclick="viewAddress(\''+val.addressStreet+'\', \''+val.addressCity+'\', \''+val.addressZipcode+'\', \''+val.addressNumber+'\', \''+val.addressComplement+'\');"><i class="icon-plus"></i></a></td>';
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
					html += '<td style="text-align:center;"><a href="#" onclick="preencheFormularioDeEdicao(\''+val.id+'\');return false;"><i class="icon-edit"></i></a>';
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
		}
	});
}

function insertParam(url, key, value) {
	if (url.indexOf("?")==-1) {
		url += '?'+key+'='+value;
	} else {
		if (url.indexOf(key+'=')==-1) {
			url += '&'+key+'='+value;
		} else {
			param = url.split('?')[1].split('&');
			url = url.split('?')[0];
			for (var i=0; i<param.length; i++) {
				keyParam = param[i].split('=');
				if (keyParam[0]==key) {
					url = insertParam(url, key, value);
				} else {
					url = insertParam(url, keyParam[0], keyParam[1]);
				}
			}
		}
	}

    return url;
}

function getLblOrderStatus(id) {
	if (id==1 || id=='1') {
		return "Aguardando pagamento";
	} else if (id==2 || id=='2') {
		return "Pagamento confirmado";
	} else if (id==3 || id=='3') {
		return "Enviado (em trânsito)";
	} else if (id==4 || id=='4') {
		return "Concluído";
	} else if (id==5 || id=='5') {
		return "Cancelado";
	} 
}

function getLblPermissions(id) {
	if (id==2) {
		return "1. Administrador da empresa.";
	}
	if (id==3) {
		return "2. Gerente de vendas.";
	}
	if (id==4) {
		return "3. Vendedor.";
	} else {
		return "";
	}
}

function removeTable(urlDelete, id, divId, formId, urlJson) {
	$.post(urlDelete, 'id='+id, function(data){
		if (data.status) {
			alert("Alteração realizada com sucesso.");
			loadGrid(urlJson, divId, formId, urlDelete);
		} else {
			errorForm(data);
		}
	}, "json");
}

function editTable(id, divId, formId, urlJson, urlDelete) {
	var idcomp = divId + id;
	
	$('form#'+formId).find('input').each(function() {
		var name = $(this).attr('name');
		var clas = $(this).attr('class');
		
		if (clas==undefined || clas==null) {
			clas='';
		}
		
		if (name=='id') {
			$(this).val(id);
		} else {
			var idtk = idcomp + name;
			var tdvalue = $('td#'+idtk).text();
			
			if ($(this).attr('type')=='text') {
				if (clas.indexOf('datePickerInput')!=-1) {
					$('td#'+idtk).html('<input type="text" id="input'+idtk+'" value="'+tdvalue+'" class="input-block-level datePickerInput" style="border: 1px solid #ccc;">');
				} else if (tdvalue.indexOf('R$ ')!=-1) {
					$('td#'+idtk).html('<input type="text" id="input'+idtk+'" value="'+(tdvalue.replace('R$ ', ''))+'" class="input-block-level money" style="border: 1px solid #ccc;">');
				} else {
					$('td#'+idtk).html('<input type="text" id="input'+idtk+'" value="'+tdvalue+'" class="input-block-level" style="border: 1px solid #ccc;">');
				}
			}
		}
	});
	$('form#'+formId).find('select').each(function() {
		var name = $(this).attr('name');
		var idtk = idcomp + name;
		var tdvalue = $('td#'+idtk).text();
		
		$(this).val(tdvalue);
		
		$('td#'+idtk).html('<select id="select'+idtk+'" class="input-block-level" style="border: 1px solid #ccc;">' + $(this).html() + '</select>');
		
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
			alert("Alteração realizada com sucesso.");
			loadGrid(urlJson, divId, formId, urlDelete);
		} else {
			errorFormGrid(data, id, divId);
		}
	}, "json");
}

function errorFormGrid(data, id, divTableId) {
	if (!data.status) {
		$.each(data.messageError, function(key, val) {
			if (key=='generic') {
				alert(val);
			} else if (key=='sessionInvalid') {
				location.href='/ecommerce/portal/login';
			} else {
				var idtk = divTableId + id + key;
				$('#input'+idtk).attr('style', 'border: 1px solid #953b39;');
			}
		});
	}
}

function postJson(id, url, params) {
	$.post(url, params, function(data){
		callback(id, data);
	}, "json");
}

function callback(id, data) {
	if (data.status) {
		if (id=='cadastrocategoria') {
			
			cadastrocategoria(data);
			
		} else if (id=='cadastrodeprodutos_preencheCbCategory') {
			
			cadastrodeprodutos_preencheCbCategory(data);
			
		} else if (id=='cadatrodeprodutos_insert') {
			
			cadatrodeprodutos_insert(data);
			
		} else if (id=='cadatrodefrete') {
			
			cadastrofrete(data);
			
		} else if (id=='formadepagamento_insert') {
			
			cadastropgto(data);
			
		}
	} else {
		errorForm(data);
	}
}

function normalizeStringToURL(texto) {
	texto = texto.toLowerCase();
	
	texto = texto.replace(".", "-");
	texto = texto.replace(",", "-");
	texto = texto.replace(";", "-");
	texto = texto.replace(":", "-");
	texto = texto.replace(" ", "-");
	texto = texto.replace("~", "-");
	texto = texto.replace("^", "-");
	texto = texto.replace("]", "-");
	texto = texto.replace("}", "-");
	texto = texto.replace("[", "-");
	texto = texto.replace("{", "-");
	texto = texto.replace("'", "-");
	texto = texto.replace("`", "-");
	texto = texto.replace("=", "-");
	texto = texto.replace("+", "-");
	texto = texto.replace("_", "-");
	texto = texto.replace(")", "-");
	texto = texto.replace("(", "-");
	texto = texto.replace("*", "-");
	texto = texto.replace("&", "-");
	texto = texto.replace("%", "-");
	texto = texto.replace("$", "-");
	texto = texto.replace("#", "-");
	texto = texto.replace("@", "-");
	texto = texto.replace("!", "-");
	texto = texto.replace("'", "-");
	texto = texto.replace("\"", "-");
	texto = texto.replace("\\", "-");
	texto = texto.replace("|", "-");
	texto = texto.replace("/", "-");
	texto = texto.replace("<", "-");
	texto = texto.replace(">", "-");
	texto = texto.replace("?", "-");
	
	texto = texto.replace("ã", "a");
	texto = texto.replace("á", "a");
	texto = texto.replace("â", "a");
	texto = texto.replace("à", "a");
	
	texto = texto.replace("ẽ", "e");
	texto = texto.replace("é", "e");
	texto = texto.replace("ê", "e");
	texto = texto.replace("è", "e");
	
	texto = texto.replace("ĩ", "i");
	texto = texto.replace("í", "i");
	texto = texto.replace("î", "i");
	texto = texto.replace("ì", "i");
	
	texto = texto.replace("õ", "o");
	texto = texto.replace("ó", "o");
	texto = texto.replace("ô", "o");
	texto = texto.replace("ò", "o");
	
	texto = texto.replace("ũ", "u");
	texto = texto.replace("ú", "u");
	texto = texto.replace("û", "u");
	texto = texto.replace("ù", "u");
	
	texto = texto.replace("ç", "c");
	
	return texto;
}
