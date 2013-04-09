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

function loadHome() {
	setMenuActive('home');
	$('#contentAll').html('<div class="span12" style="text-align:center; min-height: 600px;"><img src="static/img/loading.gif"></div>');
	$.getJSON('/ecommerce-web/admin/home?tk='+new Date().getTime(), function(data) {
		if (data.status) {
			
			$('#contentAll').load('includes/home.html', function() {
				$('span#spanTitleEcommerce').text(titlePage);
				$('span#spanClientsTotal').text(data.generic[0]);
				$('span#spanOrdersTotal').text(data.generic[1]);
				$('div#newClientsCount').html(data.generic[5]);
				$('div#newOrdersCount').html(data.generic[6]);
				$('div#newPaymentsCount').html(data.generic[7]);
				$('div#newMessagesCount').html(data.generic[8]);
				$('a#newMessagesCount').html(data.generic[8]);
				var html = '';
				$.each(data.generic[9], function(key, val) {
					html += '<li class="contact-alt grd-white">';
	                html += '<a href="#">';
	                html += '<div class="contact-item">';
	                html += '<div class="contact-item-body">';
	                if (val.statusOrder==1) {
	                	html += '<div class="status" title="Aguardando pagamento"><i class="icofont-certificate color-orange"></i></div>';
	                } else if (val.statusOrder==2) {
	                	html += '<div class="status" title="Pagamento confirmado"><i class="icofont-certificate color-silver-dark"></i></div>';
	                } else if (val.statusOrder==3 || val.statusOrder==4) {
	                	html += '<div class="status" title="Enviado ou Conluído com sucesso"><i class="icofont-certificate color-green"></i></div>';
	                } else {
	                	html += '<div class="status" title="Cancelado"><i class="icofont-certificate color-red"></i></div>';
	                }
	                if (val.totalValue.toString().indexOf('.')==-1) {
						html += '<p class="contact-item-heading bold">Valor: R$ '+val.totalValue.toString()+',00</p>';
					} else {
						html += '<p class="contact-item-heading bold">Valor: R$ '+val.totalValue.toString().replace('.', ',')+'</p>';
					}
	                html += '<p class="help-block"><small class="muted">Cliente: '+val.client.name+'</small></p>';
	                html += '</div>';
	                html += '</div>';
	                html += '</a>';
	                html += '</li>';
				});
				$('ul#ulLastOrders').html(html);
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
				$.getJSON('/gapi/espacocomercio.php?p='+data.generic[2]+'&tk='+new Date().getTime().toString(), function(r) {
	        		$('span#spanVisitsTotal').text(r.visits);
	        		generateGrafics(r.pagesMonth, data.generic[3], data.generic[4], r.pages, r.views);
	        	});
			});
			
		} else {
			errorForm(data);
			$('#contentAll').html('');
		}
		
	});
}

function loadFinance() {
	setMenuActive('finance');
	$('#contentAll').html('<div class="span12" style="text-align:center; min-height: 600px;"><img src="static/img/loading.gif"></div>');
	$('#contentAll').load('includes/finance.html');
}

function loadCategory() {
	setMenuActive('category');
	$('#contentAll').html('<div class="span12" style="text-align:center; min-height: 600px;"><img src="static/img/loading.gif"></div>');
	$('#contentAll').load('includes/category.html', function() {
		$('#box-tab-Category').css('margin-left', '10px');
		loadGrid('/ecommerce-web/admin/product/category/consult', 'grid', 'formEdit', '/ecommerce-web/admin/product/category/remove');
	});
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

function generateGrafics(visitsByMonth, clientsByMonth, ordersByMonth, viewsByURL, totalViews) {
	$('#tab-stat > a[data-toggle="tab"]').on('shown', function(){
        if(sessionStorage.mode == 4){
            $('body,html').animate({
                scrollTop: 0
            }, 'slow');
        }
    });
    
    $("span[data-chart=peity-bar]").peity("bar");
    
    var months = new Array();
    
    var d1 = [];
    var i = 0;
    $.each(visitsByMonth, function(key, val) {
    	var m = convertMonth(val.month.toString().split(' ')[0]);
    	d1.push([m, val.visits]);
    	
    	months[i] = m;
    	i++;
    });
    
    var d3 = [];
    i = 1;
    $.each(clientsByMonth, function(key, val) {
    	d3.push([months[i], val]);
    	i++;
    });
    
    var d2 = [];
    i = 1;
    $.each(ordersByMonth, function(key, val) {
    	d2.push([months[i], val]);
    	i++;
    });
    
    var d4 = [];
    i = 0;
    var t = 0;
    $.each(viewsByURL, function(key, val) {
    	if (i<10) {
    		var percent = (((val.pageview * 100) / totalViews).toString().split('.')[0] * 1);
	    	d4[i] = {
				label: percent + "% - " + val.url,
				data: percent
			}
	    	i++;
	    	t = t + percent;
    	}
    });
    d4[i] = {
			label: (100 - t) + "% - Outros </p></td></tr><tr><td colspan=\"2\" style=\"text-align:left;font-size: 14px;\"><p><strong>Total de visualizações: </strong>"+totalViews+"</p></td></tr>",
			data: 100 - t
		}
    
    var visitor = $("#visitor-stat"),
    order = $("#order-stat"),
    user = $("#user-stat"),
    
    data_visitor = [{
            data: d1,
            color: '#00A600'
        }],
    data_order = [{
            data: d2,
            color: '#2E8DEF'
        }],
    data_user = [{
            data: d3,
            color: '#DC572E'
        }],
     
    
    options_lines = {
        series: {
            lines: {
                show: true,
                fill: true
            },
            points: {
                show: true
            },
            hoverable: true
        },
        grid: {
            backgroundColor: '#FFFFFF',
            borderWidth: 1,
            borderColor: '#CDCDCD',
            hoverable: true
        },
        legend: {
            show: false
        },
        xaxis: {
            mode: "categories",
            tickLength: 0
        },
        yaxis: {
            autoscaleMargin: 2
        }

    };
    
    $.plot(visitor, data_visitor, options_lines);
    $.plot(order, data_order, options_lines);
    $.plot(user, data_user, options_lines);
    $.plot('#views-stat', d4, {
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
    
    var previousPoint = null;
    $('#visitor-stat, #order-stat, #user-stat').bind("plothover", function (event, pos, item) {
        
        if (item) {
    		if (previousPoint != item.dataIndex) {
                previousPoint = item.dataIndex;

                $("#tooltip").remove();
                var x = item.datapoint[0].toFixed(2),
                y = item.datapoint[1].toFixed(2);
                label = item.series.xaxis.ticks[item.datapoint[0]].label;
                
                showTooltip(item.pageX, item.pageY, label + " = " + y);
            }
        }
        else {
            $("#tooltip").remove();
            previousPoint = null;            
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
	if (editUrl==undefined || editUrl==null) {
		editUrl = false;
	}
	
	urlJson = insertParam(urlJson, 'tk', new Date().getTime());
	
	$.getJSON( urlJson,  function(data) {
		if (data.status!=undefined && !data.status) {
			errorForm(data);
		} else {
			var pageNow = data.page;
			var totalPages = data.totalPages;
		
			var html = 	'<table class="table table-striped" style="margin-top: 30px;">';
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
				$('td#'+idtk).attr('class', 'control-group error');
			}
		});
	}
}