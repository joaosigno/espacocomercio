function loadHome() {
	setMenuActive('home');
	loading('contentAll');
	
	$('#contentAll').load('includes/home.html', function() {
		
		loading('processo');
		$.getJSON('/ecommerce-web/admin/home?tk='+new Date().getTime(), function(data) {
			
			if (data.status) {
				
				$('div#processo').hide();
				$('div#conteudo').show();
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
				
				activePlugins();
				
				$.getJSON('/gapi/espacocomercio.php?p='+data.generic[2]+'&tk='+new Date().getTime().toString(), function(r) {
	        		$('span#spanVisitsTotal').text(r.visits);
	        		generateGrafics(r.pagesMonth, data.generic[3], data.generic[4], r.pages, r.views);
	        	});
				
			} else {
				
				errorForm(data);
				$('#contentAll').html('');
				
			}
			
		});
		
	});
	
}

function generateGrafics(visitsByMonth, clientsByMonth, ordersByMonth, viewsByURL, totalViews) {
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
			label: (100 - t) + "% - Outros",
			data: 100 - t
		}
    
    $('div#labelTotaldeVisualizacoes').html('<p style="text-align:right;font-size:14px;"><strong>Total de visualizações: </strong>'+totalViews+'</p>');
    
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